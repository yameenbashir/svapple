package com.dowhile.angualrspringapp.beans;

public class ProductSalesHistoryBean {
	
	String productName;
	String pricewithoutTax;
	String pricewithTax;
	String quantity;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPricewithoutTax() {
		return pricewithoutTax;
	}
	public void setPricewithoutTax(String pricewithoutTax) {
		this.pricewithoutTax = pricewithoutTax;
	}
	public String getPricewithTax() {
		return pricewithTax;
	}
	public void setPricewithTax(String pricewithTax) {
		this.pricewithTax = pricewithTax;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

}
