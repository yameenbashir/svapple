package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.frontend.mapping.bean.CustomerBean;

public class SendSMSControllerBean {

	List<CustomerBean> customersBean;
	String smsUsedCount;
	String smsRemained;
	String isExpired;
	public List<CustomerBean> getCustomersBean() {
		return customersBean;
	}
	public void setCustomersBean(List<CustomerBean> customersBean) {
		this.customersBean = customersBean;
	}
	public String getSmsUsedCount() {
		return smsUsedCount;
	}
	public void setSmsUsedCount(String smsUsedCount) {
		this.smsUsedCount = smsUsedCount;
	}
	public String getSmsRemained() {
		return smsRemained;
	}
	public void setSmsRemained(String smsRemained) {
		this.smsRemained = smsRemained;
	}
	public String getIsExpired() {
		return isExpired;
	}
	public void setIsExpired(String isExpired) {
		this.isExpired = isExpired;
	}
}
