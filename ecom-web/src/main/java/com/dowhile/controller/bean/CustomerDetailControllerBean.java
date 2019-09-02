package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.frontend.mapping.bean.CustomerPaymentsBean;

public class CustomerDetailControllerBean {
	
	private String customerGroup;
	private String contactName;
	private String balance;
	private String totalSpent;
	private String storeCredit;
	private String totalEarned;
	
	private List<CustomerPaymentsBean> customerPaymentsBeans;

	public String getCustomerGroup() {
		return customerGroup;
	}

	public void setCustomerGroup(String customerGroup) {
		this.customerGroup = customerGroup;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getTotalSpent() {
		return totalSpent;
	}

	public void setTotalSpent(String totalSpent) {
		this.totalSpent = totalSpent;
	}

	public String getStoreCredit() {
		return storeCredit;
	}

	public void setStoreCredit(String storeCredit) {
		this.storeCredit = storeCredit;
	}

	public String getTotalEarned() {
		return totalEarned;
	}

	public void setTotalEarned(String totalEarned) {
		this.totalEarned = totalEarned;
	}

	public List<CustomerPaymentsBean> getCustomerPaymentsBeans() {
		return customerPaymentsBeans;
	}

	public void setCustomerPaymentsBeans(
			List<CustomerPaymentsBean> customerPaymentsBeans) {
		this.customerPaymentsBeans = customerPaymentsBeans;
	}


}
