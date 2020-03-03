package com.dowhile.wrapper; 

import java.util.List;

import com.dowhile.InventoryCount;
import com.dowhile.Product;
import com.dowhile.ProductVariant;


public class InventoryCountWrapper {
	private List<Product> productList;
	private List<ProductVariant> productVariantList;
	private InventoryCount inventoryCount;
	
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
	public InventoryCount getInventoryCount() {
		return inventoryCount;
	}
	public void setInventoryCount(InventoryCount inventoryCount) {
		this.inventoryCount = inventoryCount;
	}
}
