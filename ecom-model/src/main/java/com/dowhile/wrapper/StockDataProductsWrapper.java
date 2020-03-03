package com.dowhile.wrapper; 

import java.util.List;

import com.dowhile.Contact;
import com.dowhile.Outlet;
import com.dowhile.Product;
import com.dowhile.ProductVariant;
import com.dowhile.StockOrderType;

public class StockDataProductsWrapper {
	private List<Outlet> outletList;
	private List<StockOrderType> stockOrderTypeList;
	private List<Contact> supplierList;
	private List<Product> productList;
	private List<ProductVariant> productVariantList;
	public List<Outlet> getOutletList() {
		return outletList;
	}
	public void setOutletList(List<Outlet> outletList) {
		this.outletList = outletList;
	}
	public List<StockOrderType> getStockOrderTypeList() {
		return stockOrderTypeList;
	}
	public void setStockOrderTypeList(List<StockOrderType> stockOrderTypeList) {
		this.stockOrderTypeList = stockOrderTypeList;
	}
	public List<Contact> getSupplierList() {
		return supplierList;
	}
	public void setSupplierList(List<Contact> supplierList) {
		this.supplierList = supplierList;
	}
	public List<ProductVariant> getProductVariantList() {
		return productVariantList;
	}
	public void setProductVariantList(List<ProductVariant> productVariantList) {
		this.productVariantList = productVariantList;
	}
	public List<Product> getProductList() {
		return productList;
	}
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
}
