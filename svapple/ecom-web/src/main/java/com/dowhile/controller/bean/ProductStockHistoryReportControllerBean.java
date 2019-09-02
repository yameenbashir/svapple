/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;


import com.dowhile.frontend.mapping.bean.ProductVariantBean;

/**
 * @author Zafar Shakeel
 *
 */
public class ProductStockHistoryReportControllerBean {

	private List<ProductVariantBean> productBeansList;
	private List<ProductVariantBean> productVariantBeansList;
	
	/**
	 * 
	 */
	public ProductStockHistoryReportControllerBean() {
	}
	/**
	 * @param productBeansList
	 * @param productVariantBeansList
	 */
	public ProductStockHistoryReportControllerBean(List<ProductVariantBean> productBeansList,
			List<ProductVariantBean> productVariantBeansList) {
		this.setProductBeansList(productBeansList);
		this.setProductVariantBeansList(productVariantBeansList);
	}
	public List<ProductVariantBean> getProductBeansList() {
		return productBeansList;
	}
	public void setProductBeansList(List<ProductVariantBean> productBeansList) {
		this.productBeansList = productBeansList;
	}
	public List<ProductVariantBean> getProductVariantBeansList() {
		return productVariantBeansList;
	}
	public void setProductVariantBeansList(List<ProductVariantBean> productVariantBeansList) {
		this.productVariantBeansList = productVariantBeansList;
	}
}