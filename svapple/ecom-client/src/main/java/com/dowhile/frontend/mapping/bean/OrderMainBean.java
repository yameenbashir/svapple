/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

import java.util.List;





/**
 * @author T430s
 *
 */
public class OrderMainBean {
	public String getOrderMainId() {
		return orderMainId;
	}
	public void setOrderMainId(String orderMainId) {
		this.orderMainId = orderMainId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getDeliveryMethodId() {
		return deliveryMethodId;
	}
	public void setDeliveryMethodId(String deliveryMethodId) {
		this.deliveryMethodId = deliveryMethodId;
	}
	public String getDeliveryMethodName() {
		return deliveryMethodName;
	}
	public void setDeliveryMethodName(String deliveryMethodName) {
		this.deliveryMethodName = deliveryMethodName;
	}
	public String getPaymentTypeName() {
		return paymentTypeName;
	}
	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}
	public String getPaymentTypeId() {
		return paymentTypeId;
	}
	public void setPaymentTypeId(String paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}
	public String getOutletId() {
		return outletId;
	}
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderRefNbr() {
		return orderRefNbr;
	}
	public void setOrderRefNbr(String orderRefNbr) {
		this.orderRefNbr = orderRefNbr;
	}
	public String getOrderTrackingNbr() {
		return orderTrackingNbr;
	}
	public void setOrderTrackingNbr(String orderTrackingNbr) {
		this.orderTrackingNbr = orderTrackingNbr;
	}
	public String getOrderNotes() {
		return orderNotes;
	}
	public void setOrderNotes(String orderNotes) {
		this.orderNotes = orderNotes;
	}
	public String getOrderGenerationDte() {
		return orderGenerationDte;
	}
	public void setOrderGenerationDte(String orderGenerationDte) {
		this.orderGenerationDte = orderGenerationDte;
	}
	public String getOrderCancelDte() {
		return orderCancelDte;
	}
	public void setOrderCancelDte(String orderCancelDte) {
		this.orderCancelDte = orderCancelDte;
	}
	public String getOrderExpectedDeliveryDte() {
		return orderExpectedDeliveryDte;
	}
	public void setOrderExpectedDeliveryDte(String orderExpectedDeliveryDte) {
		this.orderExpectedDeliveryDte = orderExpectedDeliveryDte;
	}
	public String getOrderDeliveryDte() {
		return orderDeliveryDte;
	}
	public void setOrderDeliveryDte(String orderDeliveryDte) {
		this.orderDeliveryDte = orderDeliveryDte;
	}
	public String getOrderBillAmt() {
		return orderBillAmt;
	}
	public void setOrderBillAmt(String orderBillAmt) {
		this.orderBillAmt = orderBillAmt;
	}
	public String getTaxAmt() {
		return taxAmt;
	}
	public void setTaxAmt(String taxAmt) {
		this.taxAmt = taxAmt;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public List<OrderDetailBean> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetailBean> orderDetails) {
		this.orderDetails = orderDetails;
	}
	private String orderMainId;
	private String companyId;
	private String companyName;
	private String contactId;
	private String contactName;
	private String deliveryMethodId;
	private String deliveryMethodName;
	private String paymentTypeName;
	private String paymentTypeId;
	private String outletId;
	private String outletName;
	private String statusId;
	private String status;
	private String orderRefNbr;
	private String orderTrackingNbr;
	private String orderNotes;
	private String orderGenerationDte;
	private String orderCancelDte;
	private String orderExpectedDeliveryDte;
	private String orderDeliveryDte;
	private String orderBillAmt;
	private String taxAmt;
	private String createdDate;
	private String lastUpdated;
	private String createdBy;
	private String updatedBy;
	private List<OrderDetailBean> orderDetails;

}