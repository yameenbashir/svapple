package com.dowhile.wrapper;

import java.util.List;

import com.dowhile.Product;

public class ProductVariantListsWrapper {
	private List<Product> outletProducts;
	private List<Product> warehouseProducts;
	public List<Product> getWarehouseProducts() {
		return warehouseProducts;
	}
	public void setWarehouseProducts(List<Product> warehouseProducts) {
		this.warehouseProducts = warehouseProducts;
	}
	public List<Product> getOutletProducts() {
		return outletProducts;
	}
	public void setOutletProducts(List<Product> outletProducts) {
		this.outletProducts = outletProducts;
	}
}
