/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

import java.util.Date;

/**
 * @author Zafar
 *
 */
public class CountryBean {
	
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getActiveIndicator() {
		return activeIndicator;
	}
	public void setActiveIndicator(String activeIndicator) {
		this.activeIndicator = activeIndicator;
	}
	private String countryId;
	private String countryName;
	private String activeIndicator;
	
}
