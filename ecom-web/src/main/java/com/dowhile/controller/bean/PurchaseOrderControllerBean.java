/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dowhile.Product;
import com.dowhile.ProductVariant;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.ProductBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.frontend.mapping.bean.StockOrderBean;
import com.dowhile.frontend.mapping.bean.StockOrderTypeBean;
import com.dowhile.frontend.mapping.bean.SupplierBean;

/**
 * @author Yameen Bashir
 *
 */
public class PurchaseOrderControllerBean {
	
	private List<OutletBean> outletBeansList ;
	private List<StockOrderTypeBean> stockOrderTypeBeansList;
	private List<SupplierBean> supplierBeansList ;
	private List<ProductVariantBean> productBeansList;
	private List<ProductVariantBean> productVariantBeansList;
	private String retailPriceBill;
	private List<StockOrderBean> stockTransferOrderBeansList;
	private Map<String, ProductVariantBean> productVariantMap = new HashMap<>();
	private Map<String, ProductBean> productMap = new HashMap<>();
	private Map<Object, Object> productVariantIdsMap = new HashMap<>();
	private Map<Object, Object> productIdsMap = new HashMap<>();
	
	/**
	 * 
	 */
	public PurchaseOrderControllerBean() {
	}

	/**
	 * @param outletBeansList
	 * @param stockOrderTypeBeansList
	 * @param supplierBeansList
	 * @param productBeansList
	 * @param productVariantBeansList
	 * @param retailPriceBill
	 * @param stockTransferOrderBeansList
	 */
	public PurchaseOrderControllerBean(List<OutletBean> outletBeansList,
			List<StockOrderTypeBean> stockOrderTypeBeansList,
			List<SupplierBean> supplierBeansList,
			List<ProductVariantBean> productBeansList,
			List<ProductVariantBean> productVariantBeansList,
			String retailPriceBill,
			List<StockOrderBean> stockTransferOrderBeansList) {
		this.outletBeansList = outletBeansList;
		this.stockOrderTypeBeansList = stockOrderTypeBeansList;
		this.supplierBeansList = supplierBeansList;
		this.productBeansList = productBeansList;
		this.productVariantBeansList = productVariantBeansList;
		this.retailPriceBill = retailPriceBill;
		this.stockTransferOrderBeansList = stockTransferOrderBeansList;
	}

	/**
	 * @return the outletBeansList
	 */
	public List<OutletBean> getOutletBeansList() {
		return outletBeansList;
	}

	/**
	 * @param outletBeansList the outletBeansList to set
	 */
	public void setOutletBeansList(List<OutletBean> outletBeansList) {
		this.outletBeansList = outletBeansList;
	}

	/**
	 * @return the stockOrderTypeBeansList
	 */
	public List<StockOrderTypeBean> getStockOrderTypeBeansList() {
		return stockOrderTypeBeansList;
	}

	/**
	 * @param stockOrderTypeBeansList the stockOrderTypeBeansList to set
	 */
	public void setStockOrderTypeBeansList(
			List<StockOrderTypeBean> stockOrderTypeBeansList) {
		this.stockOrderTypeBeansList = stockOrderTypeBeansList;
	}

	/**
	 * @return the supplierBeansList
	 */
	public List<SupplierBean> getSupplierBeansList() {
		return supplierBeansList;
	}

	/**
	 * @param supplierBeansList the supplierBeansList to set
	 */
	public void setSupplierBeansList(List<SupplierBean> supplierBeansList) {
		this.supplierBeansList = supplierBeansList;
	}

	/**
	 * @return the productBeansList
	 */
	public List<ProductVariantBean> getProductBeansList() {
		return productBeansList;
	}

	/**
	 * @param productBeansList the productBeansList to set
	 */
	public void setProductBeansList(List<ProductVariantBean> productBeansList) {
		this.productBeansList = productBeansList;
	}

	/**
	 * @return the productVariantBeansList
	 */
	public List<ProductVariantBean> getProductVariantBeansList() {
		return productVariantBeansList;
	}

	/**
	 * @param productVariantBeansList the productVariantBeansList to set
	 */
	public void setProductVariantBeansList(
			List<ProductVariantBean> productVariantBeansList) {
		this.productVariantBeansList = productVariantBeansList;
	}

	/**
	 * @return the retailPriceBill
	 */
	public String getRetailPriceBill() {
		return retailPriceBill;
	}

	/**
	 * @param retailPriceBill the retailPriceBill to set
	 */
	public void setRetailPriceBill(String retailPriceBill) {
		this.retailPriceBill = retailPriceBill;
	}

	/**
	 * @return the stockTransferOrderBeansList
	 */
	public List<StockOrderBean> getStockTransferOrderBeansList() {
		return stockTransferOrderBeansList;
	}

	/**
	 * @param stockTransferOrderBeansList the stockTransferOrderBeansList to set
	 */
	public void setStockTransferOrderBeansList(
			List<StockOrderBean> stockTransferOrderBeansList) {
		this.stockTransferOrderBeansList = stockTransferOrderBeansList;
	}

	public Map<String, ProductVariantBean> getProductVariantMap() {
		return productVariantMap;
	}

	public void setProductVariantMap(Map<String, ProductVariantBean> productVariantMap) {
		this.productVariantMap = productVariantMap;
	}

	public Map<String, ProductBean> getProductMap() {
		return productMap;
	}

	public void setProductMap(Map<String, ProductBean> productMap) {
		this.productMap = productMap;
	}

	public Map<Object, Object> getProductVariantIdsMap() {
		return productVariantIdsMap;
	}

	public void setProductVariantIdsMap(Map<Object, Object> productVariantIdsMap) {
		this.productVariantIdsMap = productVariantIdsMap;
	}

	public Map<Object, Object> getProductIdsMap() {
		return productIdsMap;
	}

	public void setProductIdsMap(Map<Object, Object> productIdsMap) {
		this.productIdsMap = productIdsMap;
	}
}
