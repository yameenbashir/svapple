package com.dowhile.frontend.mapping.bean;
/**
 * @author Zafar Shakeel
 *
 */
public class ProductTypeBean {
	private String productTypeId;
	private String productTypeName;
	private String productTypeDescription;
	private String numberOfProducts;
	
	public ProductTypeBean() {
	}
	/**
	 * @param productTypeId
	 * @param productTypeName
	 * @param productTypeDescription
	 * @param numberOfProductType
	 */
	public ProductTypeBean(String productTypeId, String productTypeName, String productTypeDescription,
			String numberOfProducts) {
		this.productTypeId = productTypeId;
		this.productTypeName = productTypeName;
		this.productTypeDescription = productTypeDescription;
		this.numberOfProducts = numberOfProducts;
	}
	/**
	 * @return the productTypeId
	 */
	public String getProductTypeId() {
		return productTypeId;
	}
	/**
	 * @param productTypeId the productTypeId to set
	 */
	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}
	/**
	 * @return the productTypeName
	 */
	public String getProductTypeName() {
		return productTypeName;
	}
	/**
	 * @param productTypeName the productTypeName to set
	 */
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	/**
	 * @return the productTypeDescription
	 */
	public String getProductTypeDescription() {
		return productTypeDescription;
	}
	/**
	 * @param productTypeDescription the productTypeDescription to set
	 */
	public void setProductTypeDescription(String productTypeDescription) {
		this.productTypeDescription = productTypeDescription;
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
