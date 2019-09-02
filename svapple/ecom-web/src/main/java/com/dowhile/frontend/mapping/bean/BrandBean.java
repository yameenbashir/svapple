/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

/**
 * @author Yameen Bashir
 *
 */
public class BrandBean {
	
	private String brandId;
	private String brandName;
	private String brandDescription;
	private String numberOfProducts;
	/**
	 * 
	 */
	public BrandBean() {
	}
	/**
	 * @param brandId
	 * @param brandName
	 * @param brandDescription
	 * @param numberOfProducts
	 */
	public BrandBean(String brandId, String brandName, String brandDescription,
			String numberOfProducts) {
		this.brandId = brandId;
		this.brandName = brandName;
		this.brandDescription = brandDescription;
		this.numberOfProducts = numberOfProducts;
	}
	/**
	 * @return the brandId
	 */
	public String getBrandId() {
		return brandId;
	}
	/**
	 * @param brandId the brandId to set
	 */
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}
	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	/**
	 * @return the brandDescription
	 */
	public String getBrandDescription() {
		return brandDescription;
	}
	/**
	 * @param brandDescription the brandDescription to set
	 */
	public void setBrandDescription(String brandDescription) {
		this.brandDescription = brandDescription;
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
