/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

/**
 * @author Yameen Bashir
 *
 */
public class TagBean {
	
	private String tagId;
	private String tagName;
	private String value;
	private String numberOfProducts;
	/**
	 * 
	 */
	public TagBean() {
	}
	/**
	 * @param tagId
	 * @param tagName
	 * @param value
	 * @param numberOfProducts
	 */
	public TagBean(String tagId, String tagName, String value,
			String numberOfProducts) {
		this.tagId = tagId;
		this.tagName = tagName;
		this.value = value;
		this.numberOfProducts = numberOfProducts;
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
}
