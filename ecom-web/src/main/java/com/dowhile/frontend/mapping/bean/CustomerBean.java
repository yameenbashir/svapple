/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

import java.util.List;

/**
 * @author Zafar Shakeel
 *
 */
public class CustomerBean {

	private String customerId;
    private String customerGroupId;
    private String firstName;
    private String lastName;
    private String customerGroupName;
    private String companyId;
    private String companyName;
    private String loyaltyEnabled;
    private String loyaltyDate;
    private String customerCode;
    private String customerDob;
    private String gender;
    private String mths12;
    private String balance12;
    private String customerBalance;
    private String totalSpent;
    private List<AddressBean> addressBeanList;
    private String activeIndicator;
    private String createdDate;
    private String lastUpdated;
    private String createdBy;
    private String updatedBy;
    private String phoneNumber;
    private String contactName;
	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	/**
	 * @return the customerGroup
	 */
	public String getCustomerGroupId() {
		return customerGroupId;
	}
	/**
	 * @param customerGroup the customerGroup to set
	 */
	public void setCustomerGroupId(String customerGroupId) {
		this.customerGroupId = customerGroupId;
	}
	/**
	 * @return the company
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * @param company the company to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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
	 * @return the loyaltyDate
	 */
	public String getLoyaltyDate() {
		return loyaltyDate;
	}
	/**
	 * @param loyaltyDate the loyaltyDate to set
	 */
	public void setLoyaltyDate(String loyaltyDate) {
		this.loyaltyDate = loyaltyDate;
	}
	/**
	 * @return the customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param customerCode the customerCode to set
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return the customerDob
	 */
	public String getCustomerDob() {
		return customerDob;
	}
	/**
	 * @param customerDob the customerDob to set
	 */
	public void setCustomerDob(String customerDob) {
		this.customerDob = customerDob;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the addressBeanList
	 */
	public List<AddressBean> getAddressBeanList() {
		return addressBeanList;
	}
	/**
	 * @param addressBeanList the addressBeanList to set
	 */
	public void setAddressBeanList(List<AddressBean> addressBeanList) {
		this.addressBeanList = addressBeanList;
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
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}
	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	/**
	 * @return the customerGroupName
	 */
	public String getCustomerGroupName() {
		return customerGroupName;
	}
	/**
	 * @param customerGroupName the customerGroupName to set
	 */
	public void setCustomerGroupName(String customerGroupName) {
		this.customerGroupName = customerGroupName;
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
	 * @return the mths12
	 */
	public String getMths12() {
		return mths12;
	}
	/**
	 * @param mths12 the mths12 to set
	 */
	public void setMths12(String mths12) {
		this.mths12 = mths12;
	}
	/**
	 * @return the balance12
	 */
	public String getBalance12() {
		return balance12;
	}
	/**
	 * @param balance12 the balance12 to set
	 */
	public void setBalance12(String balance12) {
		this.balance12 = balance12;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the customerBalance
	 */
	public String getCustomerBalance() {
		return customerBalance;
	}
	/**
	 * @param customerBalance the customerBalance to set
	 */
	public void setCustomerBalance(String customerBalance) {
		this.customerBalance = customerBalance;
	}
	/**
	 * @return the totalSpent
	 */
	public String getTotalSpent() {
		return totalSpent;
	}
	/**
	 * @param totalSpent the totalSpent to set
	 */
	public void setTotalSpent(String totalSpent) {
		this.totalSpent = totalSpent;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
}