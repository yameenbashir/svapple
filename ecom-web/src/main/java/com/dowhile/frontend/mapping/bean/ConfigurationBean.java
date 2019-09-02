/**
 * 
 */
package com.dowhile.frontend.mapping.bean;


/**
 * @author Yameen Bashir
 *
 */
public class ConfigurationBean {
	
	private String configurationId;
	private String propertyName;
	private String propertyValue;
	/**
	 * 
	 */
	public ConfigurationBean() {
	}
	/**
	 * @param configurationId
	 * @param propertyName
	 * @param propertyValue
	 */
	public ConfigurationBean(String configurationId, String propertyName,
			String propertyValue) {
		this.configurationId = configurationId;
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}
	/**
	 * @return the configurationId
	 */
	public String getConfigurationId() {
		return configurationId;
	}
	/**
	 * @param configurationId the configurationId to set
	 */
	public void setConfigurationId(String configurationId) {
		this.configurationId = configurationId;
	}
	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}
	/**
	 * @param propertyName the propertyName to set
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	/**
	 * @return the propertyValue
	 */
	public String getPropertyValue() {
		return propertyValue;
	}
	/**
	 * @param propertyValue the propertyValue to set
	 */
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}
}
