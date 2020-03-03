package com.dowhile.wrapper; 

import java.util.List;

import com.dowhile.Contact;
import com.dowhile.Notification;
import com.dowhile.Product;
import com.dowhile.ProductVariant;
import com.dowhile.StockOrder;

public class StockWrapper {
	private List<Product> productUpdateList;
	private List<ProductVariant> productVariantUpdateList;
	private StockOrder stockOrder;
	private Notification notification;
	private Contact contact;
	public List<Product> getProductUpdateList() {
		return productUpdateList;
	}
	public void setProductUpdateList(List<Product> productUpdateList) {
		this.productUpdateList = productUpdateList;
	}
	public List<ProductVariant> getProductVariantUpdateList() {
		return productVariantUpdateList;
	}
	public void setProductVariantUpdateList(List<ProductVariant> productVariantUpdateList) {
		this.productVariantUpdateList = productVariantUpdateList;
	}
	public StockOrder getStockOrder() {
		return stockOrder;
	}
	public void setStockOrder(StockOrder stockOrder) {
		this.stockOrder = stockOrder;
	}
	public Notification getNotification() {
		return notification;
	}
	public void setNotification(Notification notification) {
		this.notification = notification;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
}
