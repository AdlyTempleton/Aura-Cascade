/**
 * (C) 2015 NPException
 */
package de.npe.gameanalytics.events;

import java.security.InvalidParameterException;

import com.google.gson.annotations.SerializedName;

import de.npe.gameanalytics.Analytics;


/**
 * Base event class for events that have an event_id. (business and design
 * events)
 *
 * @author NPException
 *
 */
abstract class GAEventWithID extends GAEvent {

	@SerializedName("event_id")
	private final String eventID;

	GAEventWithID(Analytics an, String eventID) {
		super(an);

		if (eventID == null || eventID.isEmpty())
			throw new InvalidParameterException("eventID must not be null or empty");

		this.eventID = eventID;
	}
}
