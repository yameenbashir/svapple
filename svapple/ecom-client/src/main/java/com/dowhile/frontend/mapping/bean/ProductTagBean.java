/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

/**
 * @author Yameen Bashir
 *
 */
public class ProductTagBean {

	private String productTagId;
	private String productTagName;
	/**
	 * 
	 */
	public ProductTagBean() {
	}
	/**
	 * @param productTagId
	 * @param productTagName
	 */
	public ProductTagBean(String productTagId, String productTagName) {
		this.productTagId = productTagId;
		this.productTagName = productTagName;
	}
	/**
	 * @return the productTagId
	 */
	public String getProductTagId() {
		return productTagId;
	}
	/**
	 * @param productTagId the productTagId to set
	 */
	public void setProductTagId(String productTagId) {
		this.productTagId = productTagId;
	}
	/**
	 * @return the productTagName
	 */
	public String getProductTagName() {
		return productTagName;
	}
	/**
	 * @param productTagName the productTagName to set
	 */
	public void setProductTagName(String productTagName) {
		this.productTagName = productTagName;
	}
}
