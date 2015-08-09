/**
 * (C) 2015 NPException
 */
package de.npe.gameanalytics;


/**
 * @author NPException
 *
 */
public class SimpleAnalytics extends Analytics {

	private final String gameKey;
	private final String secretKey;
	private final String build;

	public SimpleAnalytics(String build, String gameKey, String secretKey) {
		this.gameKey = gameKey;
		this.secretKey = secretKey;
		this.build = build;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public String gameKey() {
		return gameKey;
	}

	@Override
	public String secretKey() {
		return secretKey;
	}

	@Override
	public String build() {
		return build;
	}

	@Override
	public String userPrefix() {
		return "user";
	}
}
