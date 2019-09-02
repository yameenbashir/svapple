package com.dowhile.frontend.mapping.bean;

import java.util.List;

public class CheckoutBean {
	
	private List<ProductBean> productBeans;
	private CustomerBean customerBean;
	private String notes;
	private int paymentType;
	private int deliveryMethod;
	private String billAmount;
	public List<ProductBean> getProductBeans() {
		return productBeans;
	}
	public void setProductBeans(List<ProductBean> productBeans) {
		this.productBeans = productBeans;
	}
	public CustomerBean getCustomerBean() {
		return customerBean;
	}
	public void setCustomerBean(CustomerBean customerBean) {
		this.customerBean = customerBean;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public int getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}
	public int getDeliveryMethod() {
		return deliveryMethod;
	}
	public void setDeliveryMethod(int deliveryMethod) {
		this.deliveryMethod = deliveryMethod;
	}
	public String getBillAmount() {
		return billAmount;
	}
	public void setBillAmount(String billAmount) {
		this.billAmount = billAmount;
	}

}
