/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

import java.util.List;




/**
 * @author Yameen Bashir
 *
 */
public class CompanyBean {
	
	private String companyId;
	private String companyName;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String displayPrices;
	private String skuGeneration;
	private String currentSequenceNumber;
	private String loyaltyEnabled;
	private String loyaltyPercentage;
	private String loyaltyEnabledDate;
	private String userSecurity;
	private String activeIndicator;
	private String createdDate;
	private String lastUpdated;
	private String printerformatID;
	private String timeZoneID;
	private String currencyID;
	private List<AddressBean> addresses;
	private String loyaltyInvoiceAmount;
	private String loyaltyAmount;
	private String loyaltyBonusAmount;
	private String loyaltyBonusEnabled;
	private String loyaltyBonusEnabledDate;
	private String url;
	private String enableStoresCredit;
	private String privateUrl;
	private String domainName;
	private String phone;
	/**
	 * 
	 */
	public CompanyBean() {

	}
	/**
	 * @param companyId
	 * @param companyName
	 * @param displayPrices
	 * @param skuGeneration
	 * @param currentSequenceNumber
	 * @param loyaltyEnabled
	 * @param loyaltyPercentage
	 * @param loyaltyEnabledDate
	 * @param userSecurity
	 * @param activeIndicator
	 * @param createdDate
	 * @param lastUpdated
	 * @param printerformatID
	 * @param timeZoneID
	 * @param currencyID
	 * @param addresses
	 * @param loyaltyInvoiceAmount
	 * @param loyaltyAmount
	 * @param loyaltyBonusAmount
	 * @param loyaltyBonusEnabled
	 * @param loyaltyBonusEnabledDate
	 */
	public CompanyBean(String companyId, String companyName,
			String displayPrices, String skuGeneration,
			String currentSequenceNumber, String loyaltyEnabled,
			String loyaltyPercentage, String loyaltyEnabledDate,
			String userSecurity, String activeIndicator, String createdDate,
			String lastUpdated, String printerformatID, String timeZoneID,
			String currencyID, List<AddressBean> addresses,
			String loyaltyInvoiceAmount, String loyaltyAmount,
			String loyaltyBonusAmount, String loyaltyBonusEnabled,
			String loyaltyBonusEnabledDate, String enableStoresCredit,String privateUrl,String domainName,String phone) {
		this.companyId = companyId;
		this.companyName = companyName;
		this.displayPrices = displayPrices;
		this.skuGeneration = skuGeneration;
		this.currentSequenceNumber = currentSequenceNumber;
		this.loyaltyEnabled = loyaltyEnabled;
		this.loyaltyPercentage = loyaltyPercentage;
		this.loyaltyEnabledDate = loyaltyEnabledDate;
		this.userSecurity = userSecurity;
		this.activeIndicator = activeIndicator;
		this.createdDate = createdDate;
		this.lastUpdated = lastUpdated;
		this.printerformatID = printerformatID;
		this.timeZoneID = timeZoneID;
		this.currencyID = currencyID;
		this.addresses = addresses;
		this.loyaltyInvoiceAmount = loyaltyInvoiceAmount;
		this.loyaltyAmount = loyaltyAmount;
		this.loyaltyBonusAmount = loyaltyBonusAmount;
		this.loyaltyBonusEnabled = loyaltyBonusEnabled;
		this.loyaltyBonusEnabledDate = loyaltyBonusEnabledDate;
		this.enableStoresCredit = enableStoresCredit;
		this.privateUrl = privateUrl;
		this.domainName = domainName;
		this.phone = phone;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the privateUrl
	 */
	public String getPrivateUrl() {
		return privateUrl;
	}
	/**
	 * @param privateUrl the privateUrl to set
	 */
	public void setPrivateUrl(String privateUrl) {
		this.privateUrl = privateUrl;
	}
	/**
	 * @return the domainName
	 */
	public String getDomainName() {
		return domainName;
	}
	/**
	 * @param domainName the domainName to set
	 */
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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
	 * @return the loyaltyEnabled
	 */
	public String getLoyaltyEnabled() {
		return loyaltyEnabled;
	}
	/**
	 * @param loyaltyEnabled the loyaltyEnabled to set
	 */
	public void setLoyaltyEnabled(String loyaltyEnabled) {
		this.loyaltyEnabled = loyaltyEnabled;
	}
	/**
	 * @return the loyaltyPercentage
	 */
	public String getLoyaltyPercentage() {
		return loyaltyPercentage;
	}
	/**
	 * @param loyaltyPercentage the loyaltyPercentage to set
	 */
	public void setLoyaltyPercentage(String loyaltyPercentage) {
		this.loyaltyPercentage = loyaltyPercentage;
	}
	/**
	 * @return the loyaltyEnabledDate
	 */
	public String getLoyaltyEnabledDate() {
		return loyaltyEnabledDate;
	}
	/**
	 * @param loyaltyEnabledDate the loyaltyEnabledDate to set
	 */
	public void setLoyaltyEnabledDate(String loyaltyEnabledDate) {
		this.loyaltyEnabledDate = loyaltyEnabledDate;
	}
	/**
	 * @return the userSecurity
	 */
	public String getUserSecurity() {
		return userSecurity;
	}
	/**
	 * @param userSecurity the userSecurity to set
	 */
	public void setUserSecurity(String userSecurity) {
		this.userSecurity = userSecurity;
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
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the lastUpdated
	 */
	public String getLastUpdated() {
		return lastUpdated;
	}
	/**
	 * @param lastUpdated the lastUpdated to set
	 */
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	/**
	 * @return the printerformatID
	 */
	public String getPrinterformatID() {
		return printerformatID;
	}
	/**
	 * @param printerformatID the printerformatID to set
	 */
	public void setPrinterformatID(String printerformatID) {
		this.printerformatID = printerformatID;
	}
	/**
	 * @return the timeZoneID
	 */
	public String getTimeZoneID() {
		return timeZoneID;
	}
	/**
	 * @param timeZoneID the timeZoneID to set
	 */
	public void setTimeZoneID(String timeZoneID) {
		this.timeZoneID = timeZoneID;
	}
	/**
	 * @return the currencyID
	 */
	public String getCurrencyID() {
		return currencyID;
	}
	/**
	 * @param currencyID the currencyID to set
	 */
	public void setCurrencyID(String currencyID) {
		this.currencyID = currencyID;
	}
	/**
	 * @return the addresses
	 */
	public List<AddressBean> getAddresses() {
		return addresses;
	}
	/**
	 * @param addresses the addresses to set
	 */
	public void setAddresses(List<AddressBean> addresses) {
		this.addresses = addresses;
	}
	/**
	 * @return the loyaltyInvoiceAmount
	 */
	public String getLoyaltyInvoiceAmount() {
		return loyaltyInvoiceAmount;
	}
	/**
	 * @param loyaltyInvoiceAmount the loyaltyInvoiceAmount to set
	 */
	public void setLoyaltyInvoiceAmount(String loyaltyInvoiceAmount) {
		this.loyaltyInvoiceAmount = loyaltyInvoiceAmount;
	}
	/**
	 * @return the loyaltyAmount
	 */
	public String getLoyaltyAmount() {
		return loyaltyAmount;
	}
	/**
	 * @param loyaltyAmount the loyaltyAmount to set
	 */
	public void setLoyaltyAmount(String loyaltyAmount) {
		this.loyaltyAmount = loyaltyAmount;
	}
	/**
	 * @return the loyaltyBonusAmount
	 */
	public String getLoyaltyBonusAmount() {
		return loyaltyBonusAmount;
	}
	/**
	 * @param loyaltyBonusAmount the loyaltyBonusAmount to set
	 */
	public void setLoyaltyBonusAmount(String loyaltyBonusAmount) {
		this.loyaltyBonusAmount = loyaltyBonusAmount;
	}
	/**
	 * @return the loyaltyBonusEnabled
	 */
	public String getLoyaltyBonusEnabled() {
		return loyaltyBonusEnabled;
	}
	/**
	 * @param loyaltyBonusEnabled the loyaltyBonusEnabled to set
	 */
	public void setLoyaltyBonusEnabled(String loyaltyBonusEnabled) {
		this.loyaltyBonusEnabled = loyaltyBonusEnabled;
	}
	/**
	 * @return the loyaltyBonusEnabledDate
	 */
	public String getLoyaltyBonusEnabledDate() {
		return loyaltyBonusEnabledDate;
	}
	/**
	 * @param loyaltyBonusEnabledDate the loyaltyBonusEnabledDate to set
	 */
	public void setLoyaltyBonusEnabledDate(String loyaltyBonusEnabledDate) {
		this.loyaltyBonusEnabledDate = loyaltyBonusEnabledDate;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEnableStoresCredit() {
		return enableStoresCredit;
	}
	public void setEnableStoresCredit(String enableStoresCredit) {
		this.enableStoresCredit = enableStoresCredit;
	}
}
