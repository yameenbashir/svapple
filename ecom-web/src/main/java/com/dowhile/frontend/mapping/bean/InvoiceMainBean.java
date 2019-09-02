/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

import java.util.List;




/**
 * @author T430s
 *
 */
public class InvoiceMainBean {
	public String getDailyRegesterId() {
		return dailyRegesterId;
	}
	public void setDailyRegesterId(String dailyRegesterId) {
		this.dailyRegesterId = dailyRegesterId;
	}
	public String getInvoiceMainId() {
		return invoiceMainId;
	}
	public void setInvoiceMainId(String invoiceMainId) {
		this.invoiceMainId = invoiceMainId;
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
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInvoiceRefNbr() {
		return invoiceRefNbr;
	}
	public void setInvoiceRefNbr(String invoiceRefNbr) {
		this.invoiceRefNbr = invoiceRefNbr;
	}
	public String getInvoiceNotes() {
		return invoiceNotes;
	}
	public void setInvoiceNotes(String invoiceNotes) {
		this.invoiceNotes = invoiceNotes;
	}
	public String getInvoiceDiscount() {
		return invoiceDiscount;
	}
	public void setInvoiceDiscount(String invoiceDiscount) {
		this.invoiceDiscount = invoiceDiscount;
	}
	public String getInvoiceTax() {
		return invoiceTax;
	}
	public void setInvoiceTax(String invoiceTax) {
		this.invoiceTax = invoiceTax;
	}
	public String getInvcTypeCde() {
		return invcTypeCde;
	}
	public void setInvcTypeCde(String invcTypeCde) {
		this.invcTypeCde = invcTypeCde;
	}
	public String getInvoiceGenerationDte() {
		return invoiceGenerationDte;
	}
	public void setInvoiceGenerationDte(String invoiceGenerationDte) {
		this.invoiceGenerationDte = invoiceGenerationDte;
	}
	public String getInvoiceCancelDte() {
		return invoiceCancelDte;
	}
	public void setInvoiceCancelDte(String invoiceCancelDte) {
		this.invoiceCancelDte = invoiceCancelDte;
	}
	public String getInvoiceAmt() {
		return invoiceAmt;
	}
	public void setInvoiceAmt(String invoiceAmt) {
		this.invoiceAmt = invoiceAmt;
	}
	public String getInvoiceDiscountAmt() {
		return invoiceDiscountAmt;
	}
	public void setInvoiceDiscountAmt(String invoiceDiscountAmt) {
		this.invoiceDiscountAmt = invoiceDiscountAmt;
	}
	public String getInvoiceNetAmt() {
		return invoiceNetAmt;
	}
	public void setInvoiceNetAmt(String invoiceNetAmt) {
		this.invoiceNetAmt = invoiceNetAmt;
	}
	public String getInvoiceGivenAmt() {
		return invoiceGivenAmt;
	}
	public void setInvoiceGivenAmt(String invoiceGivenAmt) {
		this.invoiceGivenAmt = invoiceGivenAmt;
	}
	public String getSettledAmt() {
		return settledAmt;
	}
	public void setSettledAmt(String settledAmt) {
		this.settledAmt = settledAmt;
	}
	public List<InvoiceDetailBean> getInvoiceDetails() {
		return invoiceDetails;
	}
	public void setInvoiceDetails(List<InvoiceDetailBean> invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	private String dailyRegesterId;
	private String invoiceMainId;
	private String paymentTypeId;
	private String outletId;
	private String customerId;
	private String company;
	private String status;
	private String invoiceRefNbr;
	private String invoiceNotes;
	private String invoiceDiscount;
	private String invoiceTax;
	private String invcTypeCde;
	private String invoiceGenerationDte;
	private String invoiceCancelDte;
	private String invoiceAmt;
	private String invoiceDiscountAmt;
	private String invoiceNetAmt;
	private String invoiceGivenAmt;
	private String settledAmt;
	private String transactionType;
	private String returnvalue;
	private String OrignalPrice;
	private String laybyamount;
	private String synchedInd;
	private String customername;
	private String insertedby;
	private String insertedbyname;
	private String salesUser;
	private String invoiceSubTotal;
	private String itemsCount;
	
	public String getitemsCount() {
		return itemsCount;
	}
	public void setitemsCount(String itemsCount) {
		this.itemsCount = itemsCount;
	}
	
	public String getReturnvalue() {
		return returnvalue;
	}
	public void setReturnvalue(String returnvalue) {
		this.returnvalue = returnvalue;
	}
	private List<InvoiceDetailBean> invoiceDetails;
	public String getOrignalPrice() {
		return OrignalPrice;
	}
	public void setOrignalPrice(String orignalPrice) {
		OrignalPrice = orignalPrice;
	}
	public String getLaybyamount() {
		return laybyamount;
	}
	public void setLaybyamount(String laybyamount) {
		this.laybyamount = laybyamount;
	}
	public String getSynchedInd() {
		return synchedInd;
	}
	public void setSynchedInd(String synchedInd) {
		this.synchedInd = synchedInd;
	}
	public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getInsertedby() {
		return insertedby;
	}
	public void setInsertedby(String insertedby) {
		this.insertedby = insertedby;
	}
	public String getInsertedbyname() {
		return insertedbyname;
	}
	public void setInsertedbyname(String insertedbyname) {
		this.insertedbyname = insertedbyname;
	}
	public String getSalesUser() {
		return salesUser;
	}
	public void setSalesUser(String salesUser) {
		this.salesUser = salesUser;
	}
	public String getInvoiceSubTotal() {
		return invoiceSubTotal;
	}
	public void setInvoiceSubTotal(String invoiceSubTotal) {
		this.invoiceSubTotal = invoiceSubTotal;
	}

}