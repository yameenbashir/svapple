/**
 * 
 */
package com.dowhile.frontend.mapping.bean;


/**
 * @author Zafar
 *
 */
public class SalesTaxBean {
	
	
	private String salesTaxId;
	private String salesTaxName;
	private String salesTaxPercentage;
	private String activeIndicator;
	private String effectiveDate;
	/**
	 * 
	 */
	public SalesTaxBean() {
	}
	/**
	 * @param salesTaxId
	 * @param salesTaxName
	 * @param salesTaxPercentage
	 * @param activeIndicator
	 * @param effectiveDate
	 */
	public SalesTaxBean(String salesTaxId, String salesTaxName,
			String salesTaxPercentage, String activeIndicator,
			String effectiveDate) {
		this.salesTaxId = salesTaxId;
		this.salesTaxName = salesTaxName;
		this.salesTaxPercentage = salesTaxPercentage;
		this.activeIndicator = activeIndicator;
		this.effectiveDate = effectiveDate;
	}
	/**
	 * @return the salesTaxId
	 */
	public String getSalesTaxId() {
		return salesTaxId;
	}
	/**
	 * @param salesTaxId the salesTaxId to set
	 */
	public void setSalesTaxId(String salesTaxId) {
		this.salesTaxId = salesTaxId;
	}
	/**
	 * @return the salesTaxName
	 */
	public String getSalesTaxName() {
		return salesTaxName;
	}
	/**
	 * @param salesTaxName the salesTaxName to set
	 */
	public void setSalesTaxName(String salesTaxName) {
		this.salesTaxName = salesTaxName;
	}
	/**
	 * @return the salesTaxPercentage
	 */
	public String getSalesTaxPercentage() {
		return salesTaxPercentage;
	}
	/**
	 * @param salesTaxPercentage the salesTaxPercentage to set
	 */
	public void setSalesTaxPercentage(String salesTaxPercentage) {
		this.salesTaxPercentage = salesTaxPercentage;
	}
	/**
	 * @return the activeIndicator
	 */
	public String getActiveIndicator() {
		return activeIndicator;
	}
	/**
	 * @param activeIndicator the activeIndicator to set
	 */
	public void setActiveIndicator(String activeIndicator) {
		this.activeIndicator = activeIndicator;
	}
	/**
	 * @return the effectiveDate
	 */
	public String getEffectiveDate() {
		return effectiveDate;
	}
	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
		
	
}
