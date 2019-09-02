package com.dowhile.angualrspringapp.beans;

import java.util.List;

public class SalesHistory {
	private List<SalesHistoryBean> data;
	private String companyAddress;
	private String phoneNumber;
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
	;

}
