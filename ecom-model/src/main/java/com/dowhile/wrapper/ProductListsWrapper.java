package com.dowhile.wrapper;

import java.util.List;

import com.dowhile.Product;
import com.dowhile.ProductVariant;


public class ProductListsWrapper {
	private List<Product> outletProducts;
	private List<Product> warehouseProducts;
	private List<ProductVariant> outletProductVariants;
	private List<ProductVariant> warehouseProductVariants;
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
	public List<ProductVariant> getWarehouseProductVariants() {
		return warehouseProductVariants;
	}
	public void setWarehouseProductVariants(List<ProductVariant> warehouseProductVariants) {
		this.warehouseProductVariants = warehouseProductVariants;
	}
	public List<ProductVariant> getOutletProductVariants() {
		return outletProductVariants;
	}
	public void setOutletProductVariants(List<ProductVariant> outletProductVariants) {
		this.outletProductVariants = outletProductVariants;
	}
}
