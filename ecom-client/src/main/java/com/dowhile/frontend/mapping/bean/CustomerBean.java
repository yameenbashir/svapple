/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

import java.util.List;

/**
 * @author Yameen Bashir
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
    private String password;
    private String confirmPassword;
    private String newsletter;
    private String email;
	/**
	 * 
	 */
	public CustomerBean() {
	}
	/**
	 * @param customerId
	 * @param customerGroupId
	 * @param firstName
	 * @param lastName
	 * @param customerGroupName
	 * @param companyId
	 * @param companyName
	 * @param loyaltyEnabled
	 * @param loyaltyDate
	 * @param customerCode
	 * @param customerDob
	 * @param gender
	 * @param mths12
	 * @param balance12
	 * @param customerBalance
	 * @param totalSpent
	 * @param addressBeanList
	 * @param activeIndicator
	 * @param createdDate
	 * @param lastUpdated
	 * @param createdBy
	 * @param updatedBy
	 * @param phoneNumber
	 * @param password
	 * @param confirmPassword
	 * @param newsletter
	 * @param email
	 */
	public CustomerBean(String customerId, String customerGroupId,
			String firstName, String lastName, String customerGroupName,
			String companyId, String companyName, String loyaltyEnabled,
			String loyaltyDate, String customerCode, String customerDob,
			String gender, String mths12, String balance12,
			String customerBalance, String totalSpent,
			List<AddressBean> addressBeanList, String activeIndicator,
			String createdDate, String lastUpdated, String createdBy,
			String updatedBy, String phoneNumber, String password,
			String confirmPassword, String newsletter, String email) {
		this.customerId = customerId;
		this.customerGroupId = customerGroupId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.customerGroupName = customerGroupName;
		this.companyId = companyId;
		this.companyName = companyName;
		this.loyaltyEnabled = loyaltyEnabled;
		this.loyaltyDate = loyaltyDate;
		this.customerCode = customerCode;
		this.customerDob = customerDob;
		this.gender = gender;
		this.mths12 = mths12;
		this.balance12 = balance12;
		this.customerBalance = customerBalance;
		this.totalSpent = totalSpent;
		this.addressBeanList = addressBeanList;
		this.activeIndicator = activeIndicator;
		this.createdDate = createdDate;
		this.lastUpdated = lastUpdated;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.newsletter = newsletter;
		this.email = email;
	}
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
	 * @return the customerGroupId
	 */
	public String getCustomerGroupId() {
		return customerGroupId;
	}
	/**
	 * @param customerGroupId the customerGroupId to set
	 */
	public void setCustomerGroupId(String customerGroupId) {
		this.customerGroupId = customerGroupId;
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
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	 * @return the confirmPassword
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}
	/**
	 * @param confirmPassword the confirmPassword to set
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	/**
	 * @return the newsletter
	 */
	public String getNewsletter() {
		return newsletter;
	}
	/**
	 * @param newsletter the newsletter to set
	 */
	public void setNewsletter(String newsletter) {
		this.newsletter = newsletter;
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
}