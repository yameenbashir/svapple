package com.dowhile.frontend.mapping.bean;

import java.util.Date;

import com.dowhile.Outlet;
import com.dowhile.Status;
import com.dowhile.StockOrderType;

/**
 * @author Zafar Shakeel
 *
 */
public class StockOrderBean {
	private String stockOrderId;
	private String statusId;
	private String status;
	private String stockOrderTypeId;
	private String stockOrderTypeDesc; 
	private String outletId;
	private String outletName;
	private String stockRefNo;
	private String diliveryDueDate;
	private String supplierId;
	private String supplierName;
	private String sourceOutletId;
	private String sourceOutletName;
	private String orderNo;
	private String supplierInvoiceNo;
	private String stockOrderDate;
	private String remarks;
	private String ordrRecvDate;
	private String returnNo;
	private String autofillReorder;
	private String itemCount;
	private String totalCost;
	private String activeIndicator;
	private String createdDate;
	private String lastUpdated;
	private String createdBy;
	private String updatedBy;
	
	public StockOrderBean()
	{
		
	}
	
	public StockOrderBean(String stockOrderId, String statusId,String stockOrderTypeId, String stockOrderTypeDesc, String outletId, String stockRefNo, String diliveryDueDate,
	String supplierId, String orderNo, String supplierInvoiceNo, String stockOrderDate, String remarks, String ordrRecvDate, String returnNo, 
	String autofillReorder, String activeIndicator, String createdDate, String lastUpdated, String createdBy, String updatedBy)
	{
		this.stockOrderId = stockOrderId;
		this.statusId = statusId;
		this.stockOrderTypeId = stockOrderTypeId;
		this.stockOrderTypeDesc = stockOrderTypeDesc; 
		this.outletId = outletId;
		this.stockRefNo = stockRefNo;
		this.diliveryDueDate = diliveryDueDate;
		this.supplierId = supplierId;
		this.orderNo = orderNo;
		this.supplierInvoiceNo = supplierInvoiceNo;
		this.stockOrderDate = stockOrderDate;
		this.remarks = remarks;
		this.ordrRecvDate = ordrRecvDate;
		this.returnNo = returnNo;
		this.autofillReorder= autofillReorder;
		this.activeIndicator = activeIndicator;
		this.createdDate = createdDate;
		this.lastUpdated = lastUpdated;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}

	/**
	 * @return the stockOrderId
	 */
	public String getStockOrderId() {
		return stockOrderId;
	}

	/**
	 * @param stockOrderId the stockOrderId to set
	 */
	public void setStockOrderId(String stockOrderId) {
		this.stockOrderId = stockOrderId;
	}

	/**
	 * @return the status
	 */
	public String getStatusId() {
		return statusId;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	/**
	 * @return the stockOrderType
	 */
	public String getStockOrderTypeId() {
		return stockOrderTypeId;
	}

	/**
	 * @param stockOrderType the stockOrderType to set
	 */
	public void setStockOrderTypeId(String stockOrderType) {
		this.stockOrderTypeId = stockOrderType;
	}

	/**
	 * @return the outlet
	 */
	public String getOutletId() {
		return outletId;
	}

	/**
	 * @param outlet the outlet to set
	 */
	public void setOutlet(String outlet) {
		this.outletId = outlet;
	}

	/**
	 * @return the stockRefNo
	 */
	public String getStockRefNo() {
		return stockRefNo;
	}

	/**
	 * @param stockRefNo the stockRefNo to set
	 */
	public void setStockRefNo(String stockRefNo) {
		this.stockRefNo = stockRefNo;
	}

	/**
	 * @return the diliveryDueDate
	 */
	public String getDiliveryDueDate() {
		return diliveryDueDate;
	}

	/**
	 * @param diliveryDueDate the diliveryDueDate to set
	 */
	public void setDiliveryDueDate(String diliveryDueDate) {
		this.diliveryDueDate = diliveryDueDate;
	}

	/**
	 * @return the supplierId
	 */
	public String getSupplierId() {
		return supplierId;
	}

	/**
	 * @param supplierId the supplierId to set
	 */
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	/**
	 * @return the orderNo
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * @param orderNo the orderNo to set
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the supplierInvoiceNo
	 */
	public String getSupplierInvoiceNo() {
		return supplierInvoiceNo;
	}

	/**
	 * @param supplierInvoiceNo the supplierInvoiceNo to set
	 */
	public void setSupplierInvoiceNo(String supplierInvoiceNo) {
		this.supplierInvoiceNo = supplierInvoiceNo;
	}

	/**
	 * @return the stockOrderDate
	 */
	public String getStockOrderDate() {
		return stockOrderDate;
	}

	/**
	 * @param stockOrderDate the stockOrderDate to set
	 */
	public void setStockOrderDate(String stockOrderDate) {
		this.stockOrderDate = stockOrderDate;
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
	 * @return the ordrRecvDate
	 */
	public String getOrdrRecvDate() {
		return ordrRecvDate;
	}

	/**
	 * @param ordrRecvDate the ordrRecvDate to set
	 */
	public void setOrdrRecvDate(String ordrRecvDate) {
		this.ordrRecvDate = ordrRecvDate;
	}

	/**
	 * @return the returnNo
	 */
	public String getReturnNo() {
		return returnNo;
	}

	/**
	 * @param returnNo the returnNo to set
	 */
	public void setReturnNo(String returnNo) {
		this.returnNo = returnNo;
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
	 * @return the autofillReorder
	 */
	public String getAutofillReorder() {
		return autofillReorder;
	}

	/**
	 * @param autofillReorder the autofillReorder to set
	 */
	public void setAutofillReorder(String autofillReorder) {
		this.autofillReorder = autofillReorder;
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
	 * @return the supplierName
	 */
	public String getSupplierName() {
		return supplierName;
	}

	/**
	 * @param supplierName the supplierName to set
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
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
	 * @return the items
	 */
	public String getItemCount() {
		return itemCount;
	}

	/**
	 * @param items the items to set
	 */
	public void setItemCount(String itemCount) {
		this.itemCount = itemCount;
	}

	/**
	 * @return the totalCost
	 */
	public String getTotalCost() {
		return totalCost;
	}

	/**
	 * @param totalCost the totalCost to set
	 */
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
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
	 * @return the sourceOutletId
	 */
	public String getSourceOutletId() {
		return sourceOutletId;
	}

	/**
	 * @param sourceOutletId the sourceOutletId to set
	 */
	public void setSourceOutletId(String sourceOutletId) {
		this.sourceOutletId = sourceOutletId;
	}

	/**
	 * @return the sourceOutletName
	 */
	public String getSourceOutletName() {
		return sourceOutletName;
	}

	/**
	 * @param sourceOutletName the sourceOutletName to set
	 */
	public void setSourceOutletName(String sourceOutletName) {
		this.sourceOutletName = sourceOutletName;
	}
}
