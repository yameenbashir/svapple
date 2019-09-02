/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.frontend.mapping.bean.StockOrderDetailBean;

/**
 * @author Yameen Bashir
 *
 */
public class PurchaseOrderEditProductsControllerBean {

	List<StockOrderDetailBean> stockOrderDetailBeansList ;
	List<ProductVariantBean> productVariantBeansList ;

	private List<ProductVariantBean> productBeansList;
	/**
	 * 
	 */
	public PurchaseOrderEditProductsControllerBean() {
	}
	/**
	 * @param stockOrderDetailBeansList
	 * @param productVariantBeansList
	 */
	public PurchaseOrderEditProductsControllerBean(
			List<StockOrderDetailBean> stockOrderDetailBeansList,
			List<ProductVariantBean> productVariantBeansList) {
		this.stockOrderDetailBeansList = stockOrderDetailBeansList;
		this.productVariantBeansList = productVariantBeansList;
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
}
