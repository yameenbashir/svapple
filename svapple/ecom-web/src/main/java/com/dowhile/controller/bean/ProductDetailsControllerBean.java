/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.frontend.mapping.bean.ProductBean;
import com.dowhile.frontend.mapping.bean.ProductHistoryBean;

/**
 * @author Yameen Bashir
 *
 */
public class ProductDetailsControllerBean {

	private ProductBean productBean;
	private List<ProductHistoryBean> productHistoryBeans;
	/**
	 * 
	 */
	public ProductDetailsControllerBean() {
	}
	/**
	 * @param productBean
	 * @param productHistoryBeans
	 */
	public ProductDetailsControllerBean(ProductBean productBean,
			List<ProductHistoryBean> productHistoryBeans) {
		this.productBean = productBean;
		this.productHistoryBeans = productHistoryBeans;
	}
	/**
	 * @return the productBean
	 */
	public ProductBean getProductBean() {
		return productBean;
	}
	/**
	 * @param productBean the productBean to set
	 */
	public void setProductBean(ProductBean productBean) {
		this.productBean = productBean;
	}
	/**
	 * @return the productHistoryBeans
	 */
	public List<ProductHistoryBean> getProductHistoryBeans() {
		return productHistoryBeans;
	}
	/**
	 * @param productHistoryBeans the productHistoryBeans to set
	 */
	public void setProductHistoryBeans(List<ProductHistoryBean> productHistoryBeans) {
		this.productHistoryBeans = productHistoryBeans;
	}
}
