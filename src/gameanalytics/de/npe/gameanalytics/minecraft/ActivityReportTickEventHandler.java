package de.npe.gameanalytics.minecraft;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.npe.gameanalytics.events.GAUserEvent;
import de.npe.gameanalytics.util.ACLock;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;


public final class ActivityReportTickEventHandler {
	private static ACLock lock = new ACLock();
	private static boolean initialized;
	private static List<MCSimpleAnalytics> analyticsList = new ArrayList<>();
	private static long nextActivityReport = 0;
	private static GAUserEvent activityGAEvent;

	private ActivityReportTickEventHandler() {
	} // singleton

	static void addToReportList(MCSimpleAnalytics mcSimpleAnalytics) {
		try (ACLock acl = lock.lockAC()) {
			if (!initialized) {
				initialized = true;
				FMLCommonHandler.instance().bus().register(new ActivityReportTickEventHandler());
			}

			// check if an analytics object with the same keys and client status is already in the list
			for (MCSimpleAnalytics analytics : analyticsList) {
				if (analytics.gameKey().equals(mcSimpleAnalytics.gameKey())
						&& analytics.secretKey().equals(mcSimpleAnalytics.secretKey())
						&& (analytics.isClient == mcSimpleAnalytics.isClient)) {
					String mod;
					try {
						ModContainer activeMod = Loader.instance().activeModContainer();
						StringBuilder sb = new StringBuilder(activeMod.getModId());
						sb.append(" -> ").append(activeMod.getName());
						mod = sb.toString();
					} catch (Exception e) {
						mod = "Some mod";
					}
					System.err.println(mod + " tried to instantiate an MCSimpleAnalytics instance with a key pair that was already registered!"
							+ " If you see this message, please report it to the mod developer!");
					return;
				}
			}

			// otherwise add to the list
			analyticsList.add(mcSimpleAnalytics);
		}
	}

	private static void sendAnalyticsActivityEvent() {
		long now = System.currentTimeMillis();
		if (now >= nextActivityReport) {
			// lock and check again
			try (ACLock acl = lock.lockAC()) {
				if (now >= nextActivityReport) {
					nextActivityReport = now + 10000;
					for (MCSimpleAnalytics analytics : analyticsList) {
						if (analytics.isActive()) {
							if (activityGAEvent == null) {
								GAUserEvent ae = new GAUserEvent(analytics);
								try {
									// lets abuse some event fields for user system properties
									ae.installSite(analytics.isClient ? Minecraft.getMinecraft().gameSettings.language : "server"); // language
									ae.installAdGroup(System.getProperty("os.arch")); // os/processor info
									ae.installAd(System.getProperty("os.name"));
									ae.installCampaign(System.getProperty("os.version"));
									ae.installPublisher(System.getProperty("java.runtime.version"));
									ae.installKeyword(analytics.isClient ? "client" : "server");
								} catch (Exception e) {
									System.err.println("Couldnot get all system properties: " + e);
								}
								activityGAEvent = ae;
							}
							analytics.event(activityGAEvent, false);
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("static-method")
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void clientTick(ClientTickEvent event) {
		if (event.phase == Phase.START) {
			sendAnalyticsActivityEvent();
		}
	}

	@SuppressWarnings("static-method")
	@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void serverTick(ServerTickEvent event) {
		if (event.phase == Phase.START) {
			sendAnalyticsActivityEvent();
		}
	}
}
