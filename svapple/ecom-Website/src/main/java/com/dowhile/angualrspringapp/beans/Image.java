/**
 * 
 */
package com.dowhile.angualrspringapp.beans;

/**
 * @author imran.latif
 *
 */
public class Image {
	private String data;
	private String userEmail;
	private String sessionId;
	private String testTakerId;
	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	public final String getUserEmail() {
		return userEmail;
	}

	public final void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public final String getSessionId() {
		return sessionId;
	}

	public final void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public final String getTestTakerId() {
		return testTakerId;
	}

	public final void setTestTakerId(String testTakerId) {
		this.testTakerId = testTakerId;
	}
}
