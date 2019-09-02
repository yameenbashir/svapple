package com.dowhile.frontend.mapping.bean;

import java.util.Date;

import com.dowhile.Outlet;
import com.dowhile.Status;
import com.dowhile.StockOrder;

/**
 * @author Zafar Shakeel
 *
 */
public class StockOrderTypeBean {
	private String stockOrderTypeId;
	private String stockOrderTypeCode;
	private String stockOrderTypeDesc;
	private String activeIndicator;
	private String createdDate;
	private String lastUpdated;
	private String createdBy;
	private String updatedBy;
	
	public StockOrderTypeBean(){
		
	}
	public StockOrderTypeBean(String stockOrderTypeId, String stockOrderTypeCode, String stockOrderTypeDesc, String activeIndicator, String createdDate, String lastUpdated, String createdBy, String updatedBy)
	{
		this.setStockOrderTypeId(stockOrderTypeId);
		this.setStockOrderTypeCode(stockOrderTypeCode);
		this.setStockOrderTypeDesc(stockOrderTypeDesc);
		this.setActiveIndicator(activeIndicator);
		this.setCreatedDate(createdDate);
		this.setLastUpdated(lastUpdated);
		this.setCreatedBy(createdBy);
		this.setUpdatedBy(updatedBy);
	}
	/**
	 * @return the stockOrderTypeId
	 */
	public String getStockOrderTypeId() {
		return stockOrderTypeId;
	}
	/**
	 * @param stockOrderTypeId the stockOrderTypeId to set
	 */
	public void setStockOrderTypeId(String stockOrderTypeId) {
		this.stockOrderTypeId = stockOrderTypeId;
	}
	/**
	 * @return the stockOrderTypeCode
	 */
	public String getStockOrderTypeCode() {
		return stockOrderTypeCode;
	}
	/**
	 * @param stockOrderTypeCode the stockOrderTypeCode to set
	 */
	public void setStockOrderTypeCode(String stockOrderTypeCode) {
		this.stockOrderTypeCode = stockOrderTypeCode;
	}
	/**
	 * @return the stockOrderTypeDesc
	 */
	public String getStockOrderTypeDesc() {
		return stockOrderTypeDesc;
	}
	/**
	 * @param stockOrderTypeDesc the stockOrderTypeDesc to set
	 */
	public void setStockOrderTypeDesc(String stockOrderTypeDesc) {
		this.stockOrderTypeDesc = stockOrderTypeDesc;
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


















