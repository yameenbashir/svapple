/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.frontend.mapping.bean.ProductBean;

/**
 * @author Hafiz Yameen Bashir
 *
 */
public class ProductsControllerBean {

	private List<ProductBean> productBeanList;
	private String showProductPriceHistory;
	/**
	 * 
	 */
	public ProductsControllerBean() {
	}
	/**
	 * @param productBeanList
	 * @param showProductPriceHistory
	 */
	public ProductsControllerBean(List<ProductBean> productBeanList,
			String showProductPriceHistory) {
		this.productBeanList = productBeanList;
		this.showProductPriceHistory = showProductPriceHistory;
	}
	/**
	 * @return the productBeanList
	 */
	public List<ProductBean> getProductBeanList() {
		return productBeanList;
	}
	/**
	 * @param productBeanList the productBeanList to set
	 */
	public void setProductBeanList(List<ProductBean> productBeanList) {
		this.productBeanList = productBeanList;
	}
	/**
	 * @return the showProductPriceHistory
	 */
	public String getShowProductPriceHistory() {
		return showProductPriceHistory;
	}
	/**
	 * @param showProductPriceHistory the showProductPriceHistory to set
	 */
	public void setShowProductPriceHistory(String showProductPriceHistory) {
		this.showProductPriceHistory = showProductPriceHistory;
	}
}
