package com.dowhile.angualrspringapp.beans;

import java.util.List;

import com.dowhile.frontend.mapping.bean.ReceiptBean;

public class SalesHistoryBean {
	String date;
	String receipt;
	String soldby;
	String customer;
	String note;
	String saleTotal;
	String status;
	String subtotal;
	String discount;
	String totalTax;
	String total;
	String cashGiven;
	String cashReturn;
	String cashGivenDate;
	String cashReturnDate;
	String balance;
	String customerName;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	private String html;
	List<ProductSalesHistoryBean> products;
	List<ReceiptBean> receipts;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	public String getSoldby() {
		return soldby;
	}
	public void setSoldby(String soldby) {
		this.soldby = soldby;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getSaleTotal() {
		return saleTotal;
	}
	public void setSaleTotal(String saleTotal) {
		this.saleTotal = saleTotal;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getTotalTax() {
		return totalTax;
	}
	public void setTotalTax(String totalTax) {
		this.totalTax = totalTax;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getCashGiven() {
		return cashGiven;
	}
	public void setCashGiven(String cashGiven) {
		this.cashGiven = cashGiven;
	}
	public String getCashReturn() {
		return cashReturn;
	}
	public void setCashReturn(String cashReturn) {
		this.cashReturn = cashReturn;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public List<ProductSalesHistoryBean> getProducts() {
		return products;
	}
	public void setProducts(List<ProductSalesHistoryBean> products) {
		this.products = products;
	}
	public String getCashGivenDate() {
		return cashGivenDate;
	}
	public void setCashGivenDate(String cashGivenDate) {
		this.cashGivenDate = cashGivenDate;
	}
	public String getCashReturnDate() {
		return cashReturnDate;
	}
	public void setCashReturnDate(String cashReturnDate) {
		this.cashReturnDate = cashReturnDate;
	}
	/**
	 * @return the html
	 */
	public String getHtml() {
		return html;
	}
	/**
	 * @param html the html to set
	 */
	public void setHtml(String html) {
		this.html = html;
	}
	public List<ReceiptBean> getReceipts() {
		return receipts;
	}
	public void setReceipts(List<ReceiptBean> receipts) {
		this.receipts = receipts;
	}

}
