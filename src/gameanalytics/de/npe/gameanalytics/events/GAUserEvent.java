/**
 * (C) 2015 NPException
 */
package de.npe.gameanalytics.events;

import com.google.gson.annotations.SerializedName;

import de.npe.gameanalytics.Analytics;


/**
 * Event class for collecting stats about the users system
 *
 * @author NPException
 *
 */
public class GAUserEvent extends GAEvent {

	@SerializedName("gender")
	private String gender; // The gender of the user (M/F).

	@SerializedName("birth_year")
	private Integer birthYear; // The year the user was born.

	@SerializedName("facebook_id")
	private String facebookID; // The Facebook ID of the user, in clear.

	@SerializedName("ios_id")
	private String iosID; // The IDFA of the user, in clear.

	@SerializedName("android_id")
	private String androidID; // The Android ID of the user, in clear.

	@SerializedName("adtruth_id")
	private String adtruthID; // The AdTruth ID of the user, in clear.

	@SerializedName("platform")
	private String platform; // The platform that this user plays the game on.

	@SerializedName("device")
	private String device; // The device that this user plays the game on.

	@SerializedName("os_major")
	private String osMajor; // The major version of the OS that this user plays on.

	@SerializedName("os_minor")
	private String osMinor; // The minor version of the OS that this user plays on.

	@SerializedName("install_publisher")
	private String installPublisher; // The name of the ad publisher.

	@SerializedName("install_site")
	private String installSite; // The website or app where the ad for your game was shown.

	@SerializedName("install_campaign")
	private String installCampaign; // The name of your ad campaign this user comes from.

	@SerializedName("install_adgroup")
	private String installAdGroup; // The name of the ad group this user comes from.

	@SerializedName("install_ad")
	private String installAd; // The name of the ad this user comes from.

	@SerializedName("install_keyword")
	private String installKeyword; // A keyword to associate with this user and the campaign ad.

	public GAUserEvent(Analytics an) {
		super(an);
	}

	public GAUserEvent genderMale() {
		gender = "M";
		return this;
	}

	public GAUserEvent genderFemale() {
		gender = "F";
		return this;
	}

	public GAUserEvent birthYear(int birthYear) {
		this.birthYear = Integer.valueOf(birthYear);
		return this;
	}

	public GAUserEvent facebookID(String facebookID) {
		this.facebookID = facebookID;
		return this;
	}

	public GAUserEvent iosID(String iosID) {
		this.iosID = iosID;
		return this;
	}

	public GAUserEvent androidID(String androidID) {
		this.androidID = androidID;
		return this;
	}

	public GAUserEvent adtruthID(String adtruthID) {
		this.adtruthID = adtruthID;
		return this;
	}

	public GAUserEvent platform(String platform) {
		this.platform = platform;
		return this;
	}

	public GAUserEvent device(String device) {
		this.device = device;
		return this;
	}

	public GAUserEvent osMajor(String osMajor) {
		this.osMajor = osMajor;
		return this;
	}

	public GAUserEvent osMinor(String osMinor) {
		this.osMinor = osMinor;
		return this;
	}

	public GAUserEvent installPublisher(String installPublisher) {
		this.installPublisher = installPublisher;
		return this;
	}

	public GAUserEvent installSite(String installSite) {
		this.installSite = installSite;
		return this;
	}

	public GAUserEvent installCampaign(String installCampaign) {
		this.installCampaign = installCampaign;
		return this;
	}

	public GAUserEvent installAdGroup(String installAdGroup) {
		this.installAdGroup = installAdGroup;
		return this;
	}

	public GAUserEvent installAd(String installAd) {
		this.installAd = installAd;
		return this;
	}

	public GAUserEvent installKeyword(String installKeyword) {
		this.installKeyword = installKeyword;
		return this;
	}

	@Override
	public String category() {
		return "user";
	}
}
