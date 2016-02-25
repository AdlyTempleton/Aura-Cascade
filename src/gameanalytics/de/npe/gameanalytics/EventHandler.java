/**
 * (C) 2015 NPException
 */
package de.npe.gameanalytics;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Semaphore;

import javax.xml.bind.DatatypeConverter;

import com.google.gson.Gson;

import de.npe.gameanalytics.Analytics.KeyPair;
import de.npe.gameanalytics.events.GAErrorEvent;
import de.npe.gameanalytics.events.GAEvent;
import de.npe.gameanalytics.util.ACLock;


/**
 * @author NPException
 *
 */
final class EventHandler {
	private static boolean init = true;

	private static final Queue<GAEvent> immediateEvents = new ArrayDeque<>(32);
	private static Thread sendImmediateThread;
	private static ACLock immediateEvents_lock = new ACLock(true);
	private static Semaphore sendSemaphore = new Semaphore(0);

	private static ACLock getEventsForGame_lock = new ACLock(true);
	private static ACLock getCategoryEvents_lock = new ACLock(true);
	private static ACLock sendData_lock = new ACLock(true);
	private static ACLock errorSend_lock = new ACLock(true);

	/**
	 * Map containing all not yet sent events.<br>
	 * <br>
	 * Map: KeyPair -> Map: category -> event list
	 */
	private static final Map<KeyPair, Map<String, List<GAEvent>>> events = new HashMap<>(8);

	private static Map<String, List<GAEvent>> getEventsForGame(KeyPair keyPair) {
		try (ACLock acl = getEventsForGame_lock.lockAC()) {
			Map<String, List<GAEvent>> gameEvents = events.get(keyPair);
			if (gameEvents == null) {
				gameEvents = new HashMap<>();
				events.put(keyPair, gameEvents);
			}
			return gameEvents;
		}
	}

	private static List<GAEvent> getCategoryEvents(Map<String, List<GAEvent>> gameEvents, String category) {
		try (ACLock acl = getCategoryEvents_lock.lockAC()) {
			List<GAEvent> categoryEvents = gameEvents.get(category);
			if (categoryEvents == null) {
				categoryEvents = new ArrayList<>(16);
				gameEvents.put(category, categoryEvents);
			}
			return categoryEvents;
		}
	}

	static void add(GAEvent event) {
		try {
			Map<String, List<GAEvent>> gameEvents = getEventsForGame(event.keyPair);
			List<GAEvent> categoryEvents = getCategoryEvents(gameEvents, event.category());
			synchronized (categoryEvents) {
				categoryEvents.add(event);
			}
		} catch (Exception ex) {
			System.err.println("Failed to add GAEvent to event queue: " + event);
			ex.printStackTrace(System.err);
		}
		init();
	}

	static void queueImmediateSend(GAEvent event) {
		boolean added = false;
		try (ACLock acl = immediateEvents_lock.lockAC()) {
			added = immediateEvents.offer(event);
		}

		if (added) {
			sendSemaphore.release(); // increase free permits on semaphore by 1
		} else {
			System.err.println("Could not add event to immediate events queue: " + event);
		}
		init();
	}

	static void sendErrorNow(final GAErrorEvent event, boolean useThread) {
		if (useThread) {
			Thread errorSendThread = new Thread("GA-send-error-now") {
				@Override
				public void run() {
					try (ACLock acl = errorSend_lock.lockAC()) {
						RESTHelper.sendSingleEvent(event);
					}
				}
			};
			errorSendThread.start();
		} else {
			try (ACLock acl = errorSend_lock.lockAC()) {
				RESTHelper.sendSingleEvent(event);
			}
		}
	}

	private static void init() {
		if (!init)
			return;

		synchronized (EventHandler.class) {
			if (!init)
				return;
			init = false;
		}

		final int sleepTime = APIProps.PUSH_INTERVAL_SECONDS * 1000;

		Thread sendThread = new Thread("GA-DataSendThread") {
			@Override
			public void run() {
				while (true) {
					try {
						sleep(sleepTime);
						sendData();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		sendThread.setDaemon(true);
		sendThread.start();

		sendImmediateThread = new Thread("GA-DataSendImmediatelyThread") {
			@Override
			public void run() {
				while (true) {
					sendSemaphore.acquireUninterruptibly(); // try to aquire a permit. will only happen if something is in the queue
					GAEvent event;
					try (ACLock acl = immediateEvents_lock.lockAC()) {
						event = immediateEvents.poll();
					}
					if (event != null) {
						RESTHelper.sendSingleEvent(event);
					} else {
						System.err.println("Immediate event queue did not contain an event. Something released a permit without adding an event first.");
					}
				}
			}
		};
		sendImmediateThread.setDaemon(true);
		sendImmediateThread.start();
	}

	private static void sendData() {
		try (ACLock acl = sendData_lock.lockAC()) {
			Set<KeyPair> keyPairs = events.keySet();
			for (KeyPair keyPair : keyPairs) {
				Map<String, List<GAEvent>> gameEvents = getEventsForGame(keyPair);
				if (gameEvents.isEmpty()) {
					continue;
				}
				List<String> categories = new ArrayList<>(gameEvents.keySet());

				for (String category : categories) {
					// category already exists so we don't need to use the synchronized method.
					List<GAEvent> categoryEvents = gameEvents.get(category);
					List<GAEvent> categoryEventsCopy;

					synchronized (categoryEvents) {
						if (categoryEvents.isEmpty()) {
							continue;
						}
						categoryEventsCopy = new ArrayList<>(categoryEvents);
						categoryEvents.clear();
					}

					RESTHelper.sendData(keyPair, category, categoryEventsCopy);
				}
			}
		}
	}

	private static class RESTHelper {
		private RESTHelper() {}

		private static final Gson gson = new Gson();

		private static final String contentType = "application/json; charset=utf-8";
		private static final String accept = "application/json";

		static void sendSingleEvent(GAEvent event) {
			try {
				sendData(event.keyPair, event.category(), Arrays.asList(event));
			} catch (Exception e) {
				// System.err.println("Tried to send single event, but failed.");
			}
		}

		static void sendData(KeyPair keyPair, String category, List<GAEvent> events) {
			String[] result = sendAndGetResponse(keyPair, category, events);
			String status = result[0];
			// While we expect JSON here, GA does not seem to care about the requested response
			// type all the time. That's why we check for plaintext "ok" as well.
			if (!"{\"status\":\"ok\"}".equals(status) && !"ok".equals(status)) {
				System.err.println("Failed to send analytics event data. Result of attempt: " + status + " | Authentication hash used: " + result[1] + " | Data sent: " + result[2]);
			}
		}

		/**
		 * Sends the events to GA and returns a String array with the following
		 * contents:<br>
		 * <ul>
		 * <li>Index 0: The response from GA, or exception message if one was
		 * thrown.</li>
		 * <li>Index 1: The authentication hash used to sent the data to GA, or
		 * "null" if an exception was thrown.</li>
		 * <li>Index 2: The json data that was sent to GA, or "null" if an
		 * exception was thrown.</li>
		 * </ul>
		 */
		private static String[] sendAndGetResponse(KeyPair keyPair, String category, List<GAEvent> events) {
			try {
				String postData = gson.toJson(events);
				byte[] postBytes = postData.getBytes("UTF-8");

				byte[] authData = (postData + keyPair.secretKey).getBytes("UTF-8");
				String hashedAuthData = DatatypeConverter.printHexBinary(MessageDigest.getInstance("MD5").digest(authData)).toLowerCase();

				URL url = new URL(APIProps.GA_API_URL + APIProps.GA_API_VERSION + "/" + keyPair.gameKey + "/" + category);

				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Authorization", hashedAuthData);
				connection.setRequestProperty("Accept", accept);
				connection.setRequestProperty("Content-Type", contentType);
				connection.setRequestProperty("Content-Length", String.valueOf(postBytes.length));

				StringBuilder responseSB = new StringBuilder();

				try (OutputStream os = connection.getOutputStream()) {
					// Write data
					os.write(postBytes);

					// Read response
					try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
						String line;
						while ((line = br.readLine()) != null) {
							responseSB.append(line);
						}
					}
				}

				return new String[] { responseSB.toString(), hashedAuthData, postData };
			} catch (Exception ex) {
				return new String[] { ex.getMessage(), "null", "null" };
			}
		}
	}
}
