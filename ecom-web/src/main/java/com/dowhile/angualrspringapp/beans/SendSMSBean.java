/**
 * 
 */
package com.dowhile.angualrspringapp.beans;

import java.util.List;

import com.dowhile.frontend.mapping.bean.customerSmsBean;

/**
 * @author Yameen Bashir
 *
 */
public class SendSMSBean {
	private List<customerSmsBean> customerSmsBeans;
	private String message;
	private String user;
	private String sendAllSms;
	public List<customerSmsBean> getCustomerSmsBeans() {
		return customerSmsBeans;
	}
	public void setCustomerSmsBeans(List<customerSmsBean> customerSmsBeans) {
		this.customerSmsBeans = customerSmsBeans;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSendAllSms() {
		return sendAllSms;
	}
	public void setSendAllSms(String sendAllSms) {
		this.sendAllSms = sendAllSms;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
}

		
