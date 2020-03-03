/**
 * 
 */
package com.dowhile.controller.bean; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dowhile.frontend.mapping.bean.ProductBean;
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

	private Map<String, ProductVariantBean> productVariantMap = new HashMap<>();
	private Map<String, ProductBean> productMap = new HashMap<>();
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
