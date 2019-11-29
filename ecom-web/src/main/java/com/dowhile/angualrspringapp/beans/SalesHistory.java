package com.dowhile.angualrspringapp.beans;

import java.util.List;

import com.dowhile.frontend.configuration.bean.ReceiptConfigurationBean;

public class SalesHistory {
	private List<SalesHistoryBean> data;
	private String companyAddress;
	private String phoneNumber;
	private ReceiptConfigurationBean receiptConfigurationBean;
	private String termsAndConditions;
	/**
	 * @return the data
	 */
	public List<SalesHistoryBean> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<SalesHistoryBean> data) {
		this.data = data;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the receiptConfigurationBean
	 */
	public ReceiptConfigurationBean getReceiptConfigurationBean() {
		return receiptConfigurationBean;
	}

	/**
	 * @param receiptConfigurationBean the receiptConfigurationBean to set
	 */
	public void setReceiptConfigurationBean(
			ReceiptConfigurationBean receiptConfigurationBean) {
		this.receiptConfigurationBean = receiptConfigurationBean;
	}

	/**
	 * @return the termsAndConditions
	 */
	public String getTermsAndConditions() {
		return termsAndConditions;
	}

	/**
	 * @param termsAndConditions the termsAndConditions to set
	 */
	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}
	;

}
