package com.dowhile.frontend.mapping.bean;

/**
 * @author Zafar Shakeel
 *
 */
public class InventoryCountTypeBean {
	private String inventoryCountTypeId;
	private String inventoryCountTypeCode;
	private String inventoryCountTypeDesc;
	private String activeIndicator;
	private String createdDate;
	private String lastUpdated;
	private String createdBy;
	private String updatedBy;
	
	public InventoryCountTypeBean(){
		
	}

	/**
	 * @return the inventoryCountTypeId
	 */
	public String getInventoryCountTypeId() {
		return inventoryCountTypeId;
	}

	/**
	 * @param inventoryCountTypeId the inventoryCountTypeId to set
	 */
	public void setInventoryCountTypeId(String inventoryCountTypeId) {
		this.inventoryCountTypeId = inventoryCountTypeId;
	}

	/**
	 * @return the inventoryCountTypeCode
	 */
	public String getInventoryCountTypeCode() {
		return inventoryCountTypeCode;
	}

	/**
	 * @param inventoryCountTypeCode the inventoryCountTypeCode to set
	 */
	public void setInventoryCountTypeCode(String inventoryCountTypeCode) {
		this.inventoryCountTypeCode = inventoryCountTypeCode;
	}

	/**
	 * @return the inventoryCountTypeDesc
	 */
	public String getInventoryCountTypeDesc() {
		return inventoryCountTypeDesc;
	}

	/**
	 * @param inventoryCountTypeDesc the inventoryCountTypeDesc to set
	 */
	public void setInventoryCountTypeDesc(String inventoryCountTypeDesc) {
		this.inventoryCountTypeDesc = inventoryCountTypeDesc;
	}

	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the activeIndicator
	 */
	public String getActiveIndicator() {
		return activeIndicator;
	}

	/**
	 * @param activeIndicator the activeIndicator to set
	 */
	public void setActiveIndicator(String activeIndicator) {
		this.activeIndicator = activeIndicator;
	}

	/**
	 * @return the lastUpdated
	 */
	public String getLastUpdated() {
		return lastUpdated;
	}

	/**
	 * @param lastUpdated the lastUpdated to set
	 */
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedBy
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}