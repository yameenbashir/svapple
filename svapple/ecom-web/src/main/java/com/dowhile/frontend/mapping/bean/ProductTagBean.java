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
	private String tagName;
	private String tagId;
	private String productId;
	private String numberOfProducts;
	private String value;
	private String productUuid;
	/**
	 * 
	 */
	public ProductTagBean() {
	}
	/**
	 * @param productTagId
	 * @param productTagName
	 * @param tagName
	 * @param tagId
	 * @param productId
	 * @param numberOfProducts
	 * @param value
	 * @param productUuid
	 */
	public ProductTagBean(String productTagId, String productTagName,
			String tagName, String tagId, String productId,
			String numberOfProducts, String value, String productUuid) {
		this.productTagId = productTagId;
		this.productTagName = productTagName;
		this.tagName = tagName;
		this.tagId = tagId;
		this.productId = productId;
		this.numberOfProducts = numberOfProducts;
		this.value = value;
		this.productUuid = productUuid;
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
	/**
	 * @return the tagName
	 */
	public String getTagName() {
		return tagName;
	}
	/**
	 * @param tagName the tagName to set
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	/**
	 * @return the tagId
	 */
	public String getTagId() {
		return tagId;
	}
	/**
	 * @param tagId the tagId to set
	 */
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * @return the numberOfProducts
	 */
	public String getNumberOfProducts() {
		return numberOfProducts;
	}
	/**
	 * @param numberOfProducts the numberOfProducts to set
	 */
	public void setNumberOfProducts(String numberOfProducts) {
		this.numberOfProducts = numberOfProducts;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the productUuid
	 */
	public String getProductUuid() {
		return productUuid;
	}
	/**
	 * @param productUuid the productUuid to set
	 */
	public void setProductUuid(String productUuid) {
		this.productUuid = productUuid;
	}
}
