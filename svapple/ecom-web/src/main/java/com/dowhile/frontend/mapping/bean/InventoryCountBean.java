package com.dowhile.frontend.mapping.bean;

import java.util.Date;
import java.util.List;

import com.dowhile.Outlet;
import com.dowhile.Status;

/**
 * @author Zafar Shakeel
 *
 */
public class InventoryCountBean {
	private String inventoryCountId;
	private String statusId;
	private String status;
	private String inventoryCountTypeId;
	private String inventoryCountTypeDesc; 
	private String outletId;
	private String outletName;
	private String outletAddress;
	private String inventoryCountRefNo;
	private String remarks;
	private String itemCountExp;
	private String itemCountCounted;
	private String countDiff;
	private String priceDiff;
	private String supplyPriceExp;
	private String retailPriceExp;
	private String supplyPriceCounted;
	private String retailPriceCounted;
	private List<InventoryCountDetailBean> inventoryCountDetailBeansList;
	private String activeIndicator;
	private String auditTransfer;
	private String createdDate;
	private String lastUpdated;
	private String createdBy;
	private String updatedBy;
	
	public InventoryCountBean()
	{
		
	}

	/**
	 * @return the inventoryCountId
	 */
	public String getInventoryCountId() {
		return inventoryCountId;
	}

	/**
	 * @param inventoryCountId the inventoryCountId to set
	 */
	public void setInventoryCountId(String inventoryCountId) {
		this.inventoryCountId = inventoryCountId;
	}

	/**
	 * @return the statusId
	 */
	public String getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return the outletId
	 */
	public String getOutletId() {
		return outletId;
	}

	/**
	 * @param outletId the outletId to set
	 */
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	/**
	 * @return the outletName
	 */
	public String getOutletName() {
		return outletName;
	}

	/**
	 * @param outletName the outletName to set
	 */
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	/**
	 * @return the outletAddress
	 */
	public String getOutletAddress() {
		return outletAddress;
	}

	/**
	 * @param outletAddress the outletAddress to set
	 */
	public void setOutletAddress(String outletAddress) {
		this.outletAddress = outletAddress;
	}

	/**
	 * @return the inventoryCountRefNo
	 */
	public String getInventoryCountRefNo() {
		return inventoryCountRefNo;
	}

	/**
	 * @param inventoryCountRefNo the inventoryCountRefNo to set
	 */
	public void setInventoryCountRefNo(String inventoryCountRefNo) {
		this.inventoryCountRefNo = inventoryCountRefNo;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the itemCountExp
	 */
	public String getItemCountExp() {
		return itemCountExp;
	}

	/**
	 * @param itemCountExp the itemCountExp to set
	 */
	public void setItemCountExp(String itemCountExp) {
		this.itemCountExp = itemCountExp;
	}

	/**
	 * @return the itemCountCounted
	 */
	public String getItemCountCounted() {
		return itemCountCounted;
	}

	/**
	 * @param itemCountCounted the itemCountCounted to set
	 */
	public void setItemCountCounted(String itemCountCounted) {
		this.itemCountCounted = itemCountCounted;
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

	
	/**
	 * @return the countDiff
	 */
	public String getCountDiff() {
		return countDiff;
	}

	/**
	 * @param countDiff the countDiff to set
	 */
	public void setCountDiff(String countDiff) {
		this.countDiff = countDiff;
	}

	/**
	 * @return the priceDiff
	 */
	public String getPriceDiff() {
		return priceDiff;
	}

	/**
	 * @param priceDiff the priceDiff to set
	 */
	public void setPriceDiff(String priceDiff) {
		this.priceDiff = priceDiff;
	}

	/**
	 * @return the supplyPriceExp
	 */
	public String getSupplyPriceExp() {
		return supplyPriceExp;
	}

	/**
	 * @param supplyPriceExp the supplyPriceExp to set
	 */
	public void setSupplyPriceExp(String supplyPriceExp) {
		this.supplyPriceExp = supplyPriceExp;
	}

	/**
	 * @return the retailPriceExp
	 */
	public String getRetailPriceExp() {
		return retailPriceExp;
	}

	/**
	 * @param retailPriceExp the retailPriceExp to set
	 */
	public void setRetailPriceExp(String retailPriceExp) {
		this.retailPriceExp = retailPriceExp;
	}

	/**
	 * @return the supplyPriceCounted
	 */
	public String getSupplyPriceCounted() {
		return supplyPriceCounted;
	}

	/**
	 * @param supplyPriceCounted the supplyPriceCounted to set
	 */
	public void setSupplyPriceCounted(String supplyPriceCounted) {
		this.supplyPriceCounted = supplyPriceCounted;
	}

	/**
	 * @return the retailPriceCounted
	 */
	public String getRetailPriceCounted() {
		return retailPriceCounted;
	}

	/**
	 * @param retailPriceCounted the retailPriceCounted to set
	 */
	public void setRetailPriceCounted(String retailPriceCounted) {
		this.retailPriceCounted = retailPriceCounted;
	}

	/**
	 * @return the inventoryCountDetailBeansList
	 */
	public List<InventoryCountDetailBean> getInventoryCountDetailBeansList() {
		return inventoryCountDetailBeansList;
	}

	/**
	 * @param inventoryCountDetailBeansList the inventoryCountDetailBeansList to set
	 */
	public void setInventoryCountDetailBeansList(
			List<InventoryCountDetailBean> inventoryCountDetailBeansList) {
		this.inventoryCountDetailBeansList = inventoryCountDetailBeansList;
	}

	/**
	 * @return the auditTransfer
	 */
	public String getAuditTransfer() {
		return auditTransfer;
	}

	/**
	 * @param auditTransfer the auditTransfer to set
	 */
	public void setAuditTransfer(String auditTransfer) {
		this.auditTransfer = auditTransfer;
	}
	
}
