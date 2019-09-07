/**
 * 
 */
package com.dowhile.angualrspringapp.beans;

import java.math.BigDecimal;
import java.util.Date;

import com.dowhile.Currency;
import com.dowhile.PrinterFormat;
import com.dowhile.TimeZone;

/**
 * @author Yameen Bashir
 *
 */

public class CompanyBean {
	private int companyId;
	private TimeZone timeZone;
	private PrinterFormat printerFormat;
	private Currency currency;
	private String companyName;
	private String displayPrices;
	private String skuGeneration;
	private String currentSequenceNumber;
	private Boolean loyaltyEnabled;
	private BigDecimal loyaltyInvoiceAmount;
	private BigDecimal loyaltyAmount;
	private Date loyaltyEnabledDate;
	private String userSwitchSecurity;
	private Boolean activeIndicator;
	
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the timeZone
	 */
	public TimeZone getTimeZone() {
		return timeZone;
	}
	/**
	 * @param timeZone the timeZone to set
	 */
	public void setTimeZone(TimeZone timeZone) {
		this.timeZone = timeZone;
	}
	/**
	 * @return the printerFormat
	 */
	public PrinterFormat getPrinterFormat() {
		return printerFormat;
	}
	/**
	 * @param printerFormat the printerFormat to set
	 */
	public void setPrinterFormat(PrinterFormat printerFormat) {
		this.printerFormat = printerFormat;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the currency
	 */
	public Currency getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	/**
	 * @return the loyaltyAmount
	 */
	public BigDecimal getLoyaltyAmount() {
		return loyaltyAmount;
	}
	/**
	 * @param loyaltyAmount the loyaltyAmount to set
	 */
	public void setLoyaltyAmount(BigDecimal loyaltyAmount) {
		this.loyaltyAmount = loyaltyAmount;
	}
	/**
	 * @return the skuGeneration
	 */
	public String getSkuGeneration() {
		return skuGeneration;
	}
	/**
	 * @param skuGeneration the skuGeneration to set
	 */
	public void setSkuGeneration(String skuGeneration) {
		this.skuGeneration = skuGeneration;
	}
	/**
	 * @return the currentSequenceNumber
	 */
	public String getCurrentSequenceNumber() {
		return currentSequenceNumber;
	}
	/**
	 * @param currentSequenceNumber the currentSequenceNumber to set
	 */
	public void setCurrentSequenceNumber(String currentSequenceNumber) {
		this.currentSequenceNumber = currentSequenceNumber;
	}
	/**
	 * @return the loyaltyEnabledDate
	 */
	public Date getLoyaltyEnabledDate() {
		return loyaltyEnabledDate;
	}
	/**
	 * @param loyaltyEnabledDate the loyaltyEnabledDate to set
	 */
	public void setLoyaltyEnabledDate(Date loyaltyEnabledDate) {
		this.loyaltyEnabledDate = loyaltyEnabledDate;
	}
	/**
	 * @return the displayPrices
	 */
	public String getDisplayPrices() {
		return displayPrices;
	}
	/**
	 * @param displayPrices the displayPrices to set
	 */
	public void setDisplayPrices(String displayPrices) {
		this.displayPrices = displayPrices;
	}
	/**
	 * @return the loyaltyEnabled
	 */
	public Boolean getLoyaltyEnabled() {
		return loyaltyEnabled;
	}
	/**
	 * @param loyaltyEnabled the loyaltyEnabled to set
	 */
	public void setLoyaltyEnabled(Boolean loyaltyEnabled) {
		this.loyaltyEnabled = loyaltyEnabled;
	}
	/**
	 * @return the loyaltyInvoiceAmount
	 */
	public BigDecimal getLoyaltyInvoiceAmount() {
		return loyaltyInvoiceAmount;
	}
	/**
	 * @param loyaltyInvoiceAmount the loyaltyInvoiceAmount to set
	 */
	public void setLoyaltyInvoiceAmount(BigDecimal loyaltyInvoiceAmount) {
		this.loyaltyInvoiceAmount = loyaltyInvoiceAmount;
	}
	/**
	 * @return the activeIndicator
	 */
	public Boolean getActiveIndicator() {
		return activeIndicator;
	}
	/**
	 * @param activeIndicator the activeIndicator to set
	 */
	public void setActiveIndicator(Boolean activeIndicator) {
		this.activeIndicator = activeIndicator;
	}
	/**
	 * @return the userSwitchSecurity
	 */
	public String getUserSwitchSecurity() {
		return userSwitchSecurity;
	}
	/**
	 * @param userSwitchSecurity the userSwitchSecurity to set
	 */
	public void setUserSwitchSecurity(String userSwitchSecurity) {
		this.userSwitchSecurity = userSwitchSecurity;
	}
}

		
