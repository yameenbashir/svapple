/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.frontend.mapping.bean.ProductVariantBean;

/**
 * @author asif
 *
 */
public class StockDetByProductUuidControllerBean {
	
	
	private List<ProductVariantBean> productBeansList;
	
	public StockDetByProductUuidControllerBean() {
	}

	/**
	 * @param productBeansList
	 */
	public StockDetByProductUuidControllerBean(List<ProductVariantBean> productBeansList) {
		
		this.productBeansList = productBeansList;
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
