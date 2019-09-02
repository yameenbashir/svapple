/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

import java.util.Date;

/**
 * @author T430s
 *
 */
public class TimeZoneBean {
	
	public String getTimeZoneId() {
		return timeZoneId;
	}
	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}
	public String getTimeZoneValue() {
		return timeZoneValue;
	}
	public void setTimeZoneValue(String timeZoneValue) {
		this.timeZoneValue = timeZoneValue;
	}
	public String getActiveIndicator() {
		return activeIndicator;
	}
	public void setActiveIndicator(String activeIndicator) {
		this.activeIndicator = activeIndicator;
	}
	private String timeZoneId;
	private String timeZoneValue;
	private String activeIndicator;
	
}
