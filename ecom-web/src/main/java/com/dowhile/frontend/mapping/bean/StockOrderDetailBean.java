package com.dowhile.frontend.mapping.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dowhile.Outlet;
import com.dowhile.Product;
import com.dowhile.ProductVariant;
import com.dowhile.Status;
import com.dowhile.StockOrder;
import com.dowhile.StockOrderType;

/**
 * @author Zafar Shakeel
 *
 */
public class StockOrderDetailBean {
	 private String stockOrderDetailId;
     private String stockOrderId;
     private String stockOrderRefNo;
     private String outletId;
     private String outletName;
     private String sourceOutletId;
     private String sourceOutletName;
     private String contactId;
     private String contactName;
     private String productVariantId;
     private String variantAttributeName;
     private String productVariantCurrInventory;
     private String productVariantRecvOutletInventory;
     private String isProduct;
     private String orderProdQty;
     private String ordrSupplyPrice;
     private String recvProdQty;
     private String recvSupplyPrice;
     private String retailPrice;
     private String total;
     private String recvTotal;
     private String order;
     private String greaterThanStock;
     private String activeIndicator;
     private String isDirty;
     private String createdDate;
     private String lastUpdated;
     private String createdBy;
     private String updatedBy;
     
	public StockOrderDetailBean()
	{
		
	}	
	public StockOrderDetailBean(String stockOrderDetailId, String stockOrderId,String productVariantId, String orderProdQty, String ordrSupplyPrice, String recvProdQty,
	String recvSupplyPrice, String activeIndicator, String createdDate, String lastUpdated, String createdBy, String updatedBy)
	{
		this.setStockOrderId(stockOrderId);
		this.setStockOrderId(stockOrderId);
		this.setProductVariantId(productVariantId);
		this.setOrderProdQty(orderProdQty);
		this.setOrdrSupplyPrice(ordrSupplyPrice);
		this.setRecvProdQty(recvProdQty);
		this.setRecvSupplyPrice(recvSupplyPrice);
		this.setActiveIndicator(activeIndicator);
		this.setCreatedDate(createdDate);
		this.setLastUpdated(lastUpdated);
		this.setCreatedBy(createdBy);
		this.setUpdatedBy(updatedBy);
	}
	/**
	 * @return the stockOrderDetailId
	 */
	public String getStockOrderDetailId() {
		return stockOrderDetailId;
	}
	/**
	 * @param stockOrderDetailId the stockOrderDetailId to set
	 */
	public void setStockOrderDetailId(String stockOrderDetailId) {
		this.stockOrderDetailId = stockOrderDetailId;
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
	 * @return the productId
	 */
	public String getProductVariantId() {
		return productVariantId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductVariantId(String productVariantId) {
		this.productVariantId = productVariantId;
	}
	/**
	 * @return the orderProdQty
	 */
	public String getOrderProdQty() {
		return orderProdQty;
	}
	/**
	 * @param orderProdQty the orderProdQty to set
	 */
	public void setOrderProdQty(String orderProdQty) {
		this.orderProdQty = orderProdQty;
	}
	/**
	 * @return the ordrSupplyPrice
	 */
	public String getOrdrSupplyPrice() {
		return ordrSupplyPrice;
	}
	/**
	 * @param ordrSupplyPrice the ordrSupplyPrice to set
	 */
	public void setOrdrSupplyPrice(String ordrSupplyPrice) {
		this.ordrSupplyPrice = ordrSupplyPrice;
	}
	/**
	 * @return the recvProdQty
	 */
	public String getRecvProdQty() {
		return recvProdQty;
	}
	/**
	 * @param recvProdQty the recvProdQty to set
	 */
	public void setRecvProdQty(String recvProdQty) {
		this.recvProdQty = recvProdQty;
	}
	/**
	 * @return the recvSupplyPrice
	 */
	public String getRecvSupplyPrice() {
		return recvSupplyPrice;
	}
	/**
	 * @param recvSupplyPrice the recvSupplyPrice to set
	 */
	public void setRecvSupplyPrice(String recvSupplyPrice) {
		this.recvSupplyPrice = recvSupplyPrice;
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
	 * @return the productVariantCurrInventory
	 */
	public String getProductVariantCurrInventory() {
		return productVariantCurrInventory;
	}
	/**
	 * @param productVariantCurrInventory the productVariantCurrInventory to set
	 */
	public void setProductVariantCurrInventory(
			String productVariantCurrInventory) {
		this.productVariantCurrInventory = productVariantCurrInventory;
	}
	/**
	 * @return the variantAttributeName
	 */
	public String getVariantAttributeName() {
		return variantAttributeName;
	}
	/**
	 * @param variantAttributeName the variantAttributeName to set
	 */
	public void setVariantAttributeName(String variantAttributeName) {
		this.variantAttributeName = variantAttributeName;
	}
	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}
	/**
	 * @return the order
	 */
	public String getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}
	/**
	 * @return the recvTotal
	 */
	public String getRecvTotal() {
		return recvTotal;
	}
	/**
	 * @param recvTotal the recvTotal to set
	 */
	public void setRecvTotal(String recvTotal) {
		this.recvTotal = recvTotal;
	}
	/**
	 * @return the productVariantRecvOutletInventory
	 */
	public String getProductVariantRecvOutletInventory() {
		return productVariantRecvOutletInventory;
	}
	/**
	 * @param productVariantRecvOutletInventory the productVariantRecvOutletInventory to set
	 */
	public void setProductVariantRecvOutletInventory(
			String productVariantRecvOutletInventory) {
		this.productVariantRecvOutletInventory = productVariantRecvOutletInventory;
	}
	/**
	 * @return the isProduct
	 */
	public String getIsProduct() {
		return isProduct;
	}
	/**
	 * @param isProduct the isProduct to set
	 */
	public void setIsProduct(String isProduct) {
		this.isProduct = isProduct;
	}
	/**
	 * @return the greaterThanStock
	 */
	public String getGreaterThanStock() {
		return greaterThanStock;
	}
	/**
	 * @param greaterThanStock the greaterThanStock to set
	 */
	public void setGreaterThanStock(String greaterThanStock) {
		this.greaterThanStock = greaterThanStock;
	}
	/**
	 * @return the retailPrice
	 */
	public String getRetailPrice() {
		return retailPrice;
	}
	/**
	 * @param retailPrice the retailPrice to set
	 */
	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}
	public String getOutletId() {
		return outletId;
	}
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
	public String getStockOrderRefNo() {
		return stockOrderRefNo;
	}
	public void setStockOrderRefNo(String stockOrderRefNo) {
		this.stockOrderRefNo = stockOrderRefNo;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public String getSourceOutletName() {
		return sourceOutletName;
	}
	public void setSourceOutletName(String sourceOutletName) {
		this.sourceOutletName = sourceOutletName;
	}
	public String getSourceOutletId() {
		return sourceOutletId;
	}
	public void setSourceOutletId(String sourceOutletId) {
		this.sourceOutletId = sourceOutletId;
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getIsDirty() {
		return isDirty;
	}
	public void setIsDirty(String isDirty) {
		this.isDirty = isDirty;
	}
}
