/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dowhile.ProductVariant;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.ProductBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.frontend.mapping.bean.StockOrderDetailBean;
import com.dowhile.frontend.mapping.bean.StockOrderTypeBean;
import com.dowhile.frontend.mapping.bean.SupplierBean;

/**
 * @author Zafar Shakeel
 *
 */
public class POCreateandReceiveControllerBean {

	private List<OutletBean> outletBeansList ;
	private List<StockOrderTypeBean> stockOrderTypeBeansList;
	private List<SupplierBean> supplierBeansList ;
	private List<ProductVariantBean> productBeansList;
	private List<ProductVariantBean> productVariantBeansList;
	private List<StockOrderDetailBean> stockOrderDetailBeansList ;
	private Map<String, ProductVariantBean> productVariantMap = new HashMap<>();
	private Map<String, ProductBean> productMap = new HashMap<>();
	/**
	 * 
	 */
	public POCreateandReceiveControllerBean() {
	}
	/**
	 * @param outletBeansList
	 * @param stockOrderTypeBeansList
	 * @param supplierBeansList
	 * @param productVariantBeansList
	 */
	public POCreateandReceiveControllerBean(List<OutletBean> outletBeansList,
			List<StockOrderTypeBean> stockOrderTypeBeansList,
			List<SupplierBean> supplierBeansList, List<ProductVariantBean> productBeansList, List<ProductVariantBean> proudctVariantBeansList, List<StockOrderDetailBean> stockOrderDetailBeansList) {
		this.outletBeansList = outletBeansList;
		this.stockOrderTypeBeansList = stockOrderTypeBeansList;
		this.supplierBeansList = supplierBeansList;
		this.setProductBeansList(productBeansList);
		this.setProductVariantBeansList(proudctVariantBeansList);
		this.setStockOrderDetailBeansList(stockOrderDetailBeansList);
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
	 * @return the productVariantBeansList
	 */
	public List<ProductVariantBean> getProductVariantBeansList() {
		return productVariantBeansList;
	}
	/**
	 * @param productVariantBeansList the productVariantBeansList to set
	 */
	public void setProductVariantBeansList(List<ProductVariantBean> productVariantBeansList) {
		this.productVariantBeansList = productVariantBeansList;
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
	 * @return the stockOrderDetailBeansList
	 */
	public List<StockOrderDetailBean> getStockOrderDetailBeansList() {
		return stockOrderDetailBeansList;
	}
	/**
	 * @param stockOrderDetailBeansList the stockOrderDetailBeansList to set
	 */
	public void setStockOrderDetailBeansList(
			List<StockOrderDetailBean> stockOrderDetailBeansList) {
		this.stockOrderDetailBeansList = stockOrderDetailBeansList;
	}
	public Map<String, ProductBean> getProductMap() {
		return productMap;
	}
	public void setProductMap(Map<String, ProductBean> productMap) {
		this.productMap = productMap;
	}
	public Map<String, ProductVariantBean> getProductVariantMap() {
		return productVariantMap;
	}
	public void setProductVariantMap(Map<String, ProductVariantBean> productVariantMap) {
		this.productVariantMap = productVariantMap;
	}
}
