/**
 * (C) 2015 NPException
 */
package de.npe.gameanalytics.events;

import java.security.InvalidParameterException;

import com.google.gson.annotations.SerializedName;

import de.npe.gameanalytics.Analytics;


/**
 * @author NPException
 *
 */
public class GABusinessEvent extends GADesignEvent {

	@SerializedName("currency")
	private final String currency;

	@SerializedName("amount")
	private final int amount;

	/**
	 * Currency must be one that is specifically accepted by GA. see <a href=
	 * "http://support.gameanalytics.com/hc/en-us/articles/200841576-Supported-currencies"
	 * >Supported currencies</a><br>
	 * <br>
	 * The amount is the given monetary unit (currency) times 100. So in case of
	 * USD the amount would be given in cents, so to speak.
	 *
	 * @param an
	 * @param eventID
	 * @param amount
	 * @param currency
	 */
	public GABusinessEvent(Analytics an, String eventID, String area, int amount, String currency) {
		super(an, eventID, area, null);
		this.amount = amount;
		if (currency == null || currency.isEmpty())
			throw new InvalidParameterException("currency must not be null or empty");
		this.currency = currency;
	}

	@Override
	public String category() {
		return "business";
	}
}
