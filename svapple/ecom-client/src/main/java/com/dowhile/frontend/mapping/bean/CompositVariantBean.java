/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

/**
 * @author Yameen Bashir
 *
 */
public class CompositVariantBean {
	
	private String id;
	private String productVariantId;
	private String productVariantName;
	private String productId;
	private String productName;
	private String compositQunatity;
	private String compositeProductId;
	private String uuid;
	
	/**
	 * 
	 */
	public CompositVariantBean() {
	}

	/**
	 * @param id
	 * @param productVariantId
	 * @param productVariantName
	 * @param productId
	 * @param productName
	 * @param compositQunatity
	 * @param compositeProductId
	 * @param uuid
	 */
	public CompositVariantBean(String id, String productVariantId,
			String productVariantName, String productId, String productName,
			String compositQunatity, String compositeProductId, String uuid) {
		this.id = id;
		this.productVariantId = productVariantId;
		this.productVariantName = productVariantName;
		this.productId = productId;
		this.productName = productName;
		this.compositQunatity = compositQunatity;
		this.compositeProductId = compositeProductId;
		this.uuid = uuid;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the productVariantId
	 */
	public String getProductVariantId() {
		return productVariantId;
	}

	/**
	 * @param productVariantId the productVariantId to set
	 */
	public void setProductVariantId(String productVariantId) {
		this.productVariantId = productVariantId;
	}

	/**
	 * @return the productVariantName
	 */
	public String getProductVariantName() {
		return productVariantName;
	}

	/**
	 * @param productVariantName the productVariantName to set
	 */
	public void setProductVariantName(String productVariantName) {
		this.productVariantName = productVariantName;
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
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the compositQunatity
	 */
	public String getCompositQunatity() {
		return compositQunatity;
	}

	/**
	 * @param compositQunatity the compositQunatity to set
	 */
	public void setCompositQunatity(String compositQunatity) {
		this.compositQunatity = compositQunatity;
	}

	/**
	 * @return the compositeProductId
	 */
	public String getCompositeProductId() {
		return compositeProductId;
	}

	/**
	 * @param compositeProductId the compositeProductId to set
	 */
	public void setCompositeProductId(String compositeProductId) {
		this.compositeProductId = compositeProductId;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
