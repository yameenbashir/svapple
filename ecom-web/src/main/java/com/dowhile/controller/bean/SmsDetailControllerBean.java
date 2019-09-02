package com.dowhile.controller.bean;

import java.util.List;

public class SmsDetailControllerBean {

	List<SmsReportBean> smsReportBeanList;
	int smsUsedCount;
	int smsRemained;
	public List<SmsReportBean> getSmsReportBeanList() {
		return smsReportBeanList;
	}
	public void setSmsReportBeanList(List<SmsReportBean> smsReportBeanList) {
		this.smsReportBeanList = smsReportBeanList;
	}
	public int getSmsUsedCount() {
		return smsUsedCount;
	}
	public void setSmsUsedCount(int smsUsedCount) {
		this.smsUsedCount = smsUsedCount;
	}
	public int getSmsRemained() {
		return smsRemained;
	}
	public void setSmsRemained(int smsRemained) {
		this.smsRemained = smsRemained;
	}
	
}
