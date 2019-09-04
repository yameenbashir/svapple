package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.angualrspringapp.beans.SalesHistory;
import com.dowhile.frontend.mapping.bean.CustomerPaymentsBean;

public class CustomerDetailControllerBean {
	
	private String customerGroup;
	private String contactName;
	private String balance;
	private String totalSpent;
	private String storeCredit;
	private String totalEarned;
	private SalesHistory salesHistory;
	
	private List<CustomerPaymentsBean> customerPaymentsBeans;

	/**
	 * 
	 */
	public CustomerDetailControllerBean() {
	}

	/**
	 * @param customerGroup
	 * @param contactName
	 * @param balance
	 * @param totalSpent
	 * @param storeCredit
	 * @param totalEarned
	 * @param salesHistory
	 * @param customerPaymentsBeans
	 */
	public CustomerDetailControllerBean(String customerGroup,
			String contactName, String balance, String totalSpent,
			String storeCredit, String totalEarned, SalesHistory salesHistory,
			List<CustomerPaymentsBean> customerPaymentsBeans) {
		this.customerGroup = customerGroup;
		this.contactName = contactName;
		this.balance = balance;
		this.totalSpent = totalSpent;
		this.storeCredit = storeCredit;
		this.totalEarned = totalEarned;
		this.salesHistory = salesHistory;
		this.customerPaymentsBeans = customerPaymentsBeans;
	}

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

	public SalesHistory getSalesHistory() {
		return salesHistory;
	}

	public void setSalesHistory(SalesHistory salesHistory) {
		this.salesHistory = salesHistory;
	}

	public List<CustomerPaymentsBean> getCustomerPaymentsBeans() {
		return customerPaymentsBeans;
	}

	public void setCustomerPaymentsBeans(
			List<CustomerPaymentsBean> customerPaymentsBeans) {
		this.customerPaymentsBeans = customerPaymentsBeans;
	}
}
