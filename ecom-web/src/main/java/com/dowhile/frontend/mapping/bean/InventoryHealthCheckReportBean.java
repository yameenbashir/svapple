package com.dowhile.frontend.mapping.bean;


/**
 * @author Ali Zafar
 *
 */
public class InventoryHealthCheckReportBean {
	
	private String productId;
	private String productVariantId;
	private String productName;
	private String varinatAttributeName;
	private String outletName;
	private String sku;
	private String brand;
	private String supplierName;
	private String type;
	private String tag;
	private String stockValue;
	private String itemValue;
	private String auditQuantity;
	private String stockTransferred;
	private String stockRtw;
	private String sale;
	private String saleReturn;
	private String expCurrentInventory;
	private String sysCurrentInventory;
	private String conflictedQuantity;
	private String totalAuditQuantity;
	private String totalSt;
	private String totalRtw;
	private String totalExpCurrentInventory;
	private String totalConflictedQuantity;	
	private String reorderAmount;
	private String totalCurrentInventory;
	private String totalCurrentValue;
	private String totalStockSent;
	private String sold;
	/**
	 * 
	 */
	public InventoryHealthCheckReportBean() {
	}
	/**
	 * @param productId
	 * @param varinatAttributeName
	 * @param outletName
	 * @param sku
	 * @param brand
	 * @param supplierName
	 * @param type
	 * @param tag
	 * @param currentStock
	 * @param stockValue
	 * @param itemValue
	 * @param reorderPoint
	 * @param reorderAmount
	 * @param totalCurrentStock
	 * @param totalCurrentValue
	 * @param totalStockSent
	 * @param sold
	 */
	public InventoryHealthCheckReportBean(String productId, String varinatAttributeName,
			String outletName, String sku, String brand, String supplierName,
			String type, String tag, String currentStock, String stockValue,
			String itemValue, String reorderPoint, String reorderAmount,
			String totalCurrentStock, String totalCurrentValue,
			String totalStockSent, String sold) {
		this.productId = productId;
		this.varinatAttributeName = varinatAttributeName;
		this.outletName = outletName;
		this.sku = sku;
		this.brand = brand;
		this.supplierName = supplierName;
		this.type = type;
		this.tag = tag;
		this.stockValue = stockValue;
		this.itemValue = itemValue;
		this.reorderAmount = reorderAmount;
		this.totalCurrentValue = totalCurrentValue;
		this.totalStockSent = totalStockSent;
		this.sold = sold;
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
	 * @return the varinatAttributeName
	 */
	public String getVarinatAttributeName() {
		return varinatAttributeName;
	}
	/**
	 * @param varinatAttributeName the varinatAttributeName to set
	 */
	public void setVarinatAttributeName(String varinatAttributeName) {
		this.varinatAttributeName = varinatAttributeName;
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
	 * @return the sku
	 */
	public String getSku() {
		return sku;
	}
	/**
	 * @param sku the sku to set
	 */
	public void setSku(String sku) {
		this.sku = sku;
	}
	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}
	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}
	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}
	/**
	 * @return the stockValue
	 */
	public String getStockValue() {
		return stockValue;
	}
	/**
	 * @param stockValue the stockValue to set
	 */
	public void setStockValue(String stockValue) {
		this.stockValue = stockValue;
	}
	/**
	 * @return the itemValue
	 */
	public String getItemValue() {
		return itemValue;
	}
	/**
	 * @param itemValue the itemValue to set
	 */
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
	public String getReorderAmount() {
		return reorderAmount;
	}
	/**
	 * @param reorderAmount the reorderAmount to set
	 */
	public void setReorderAmount(String reorderAmount) {
		this.reorderAmount = reorderAmount;
	}
	/**
	 * @return the totalCurrentValue
	 */
	public String getTotalCurrentValue() {
		return totalCurrentValue;
	}
	/**
	 * @param totalCurrentValue the totalCurrentValue to set
	 */
	public void setTotalCurrentValue(String totalCurrentValue) {
		this.totalCurrentValue = totalCurrentValue;
	}
	/**
	 * @return the totalStockSent
	 */
	public String getTotalStockSent() {
		return totalStockSent;
	}
	/**
	 * @param totalStockSent the totalStockSent to set
	 */
	public void setTotalStockSent(String totalStockSent) {
		this.totalStockSent = totalStockSent;
	}
	/**
	 * @return the sold
	 */
	public String getSold() {
		return sold;
	}
	/**
	 * @param sold the sold to set
	 */
	public void setSold(String sold) {
		this.sold = sold;
	}
	public String getAuditQuantity() {
		return auditQuantity;
	}
	public void setAuditQuantity(String auditQuantity) {
		this.auditQuantity = auditQuantity;
	}
	/**
	 * @return the stockRtw
	 */
	public String getStockRtw() {
		return stockRtw;
	}
	/**
	 * @param stockRtw the stockRtw to set
	 */
	public void setStockRtw(String stockRtw) {
		this.stockRtw = stockRtw;
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
	 * @return the stockTransferred
	 */
	public String getStockTransferred() {
		return stockTransferred;
	}
	/**
	 * @param stockTransferred the stockTransferred to set
	 */
	public void setStockTransferred(String stockTransferred) {
		this.stockTransferred = stockTransferred;
	}
	/**
	 * @return the sale
	 */
	public String getSale() {
		return sale;
	}
	/**
	 * @param sale the sale to set
	 */
	public void setSale(String sale) {
		this.sale = sale;
	}
	/**
	 * @return the saleReturn
	 */
	public String getSaleReturn() {
		return saleReturn;
	}
	/**
	 * @param saleReturn the saleReturn to set
	 */
	public void setSaleReturn(String saleReturn) {
		this.saleReturn = saleReturn;
	}
	/**
	 * @return the expCurrentInventory
	 */
	public String getExpCurrentInventory() {
		return expCurrentInventory;
	}
	/**
	 * @param expCurrentInventory the expCurrentInventory to set
	 */
	public void setExpCurrentInventory(String expCurrentInventory) {
		this.expCurrentInventory = expCurrentInventory;
	}
	/**
	 * @return the sysCurrentInventory
	 */
	public String getSysCurrentInventory() {
		return sysCurrentInventory;
	}
	/**
	 * @param sysCurrentInventory the sysCurrentInventory to set
	 */
	public void setSysCurrentInventory(String sysCurrentInventory) {
		this.sysCurrentInventory = sysCurrentInventory;
	}
	/**
	 * @return the conflictedQuantity
	 */
	public String getConflictedQuantity() {
		return conflictedQuantity;
	}
	/**
	 * @param conflictedQuantity the conflictedQuantity to set
	 */
	public void setConflictedQuantity(String conflictedQuantity) {
		this.conflictedQuantity = conflictedQuantity;
	}
	/**
	 * @return the totalAuditQuantity
	 */
	public String getTotalAuditQuantity() {
		return totalAuditQuantity;
	}
	/**
	 * @param totalAuditQuantity the totalAuditQuantity to set
	 */
	public void setTotalAuditQuantity(String totalAuditQuantity) {
		this.totalAuditQuantity = totalAuditQuantity;
	}
	/**
	 * @return the totalSt
	 */
	public String getTotalSt() {
		return totalSt;
	}
	/**
	 * @param totalSt the totalSt to set
	 */
	public void setTotalSt(String totalSt) {
		this.totalSt = totalSt;
	}
	/**
	 * @return the totalRtw
	 */
	public String getTotalRtw() {
		return totalRtw;
	}
	/**
	 * @param totalRtw the totalRtw to set
	 */
	public void setTotalRtw(String totalRtw) {
		this.totalRtw = totalRtw;
	}
	/**
	 * @return the totalConflictedQuantity
	 */
	public String getTotalConflictedQuantity() {
		return totalConflictedQuantity;
	}
	/**
	 * @param totalConflictedQuantity the totalConflictedQuantity to set
	 */
	public void setTotalConflictedQuantity(String totalConflictedQuantity) {
		this.totalConflictedQuantity = totalConflictedQuantity;
	}
	/**
	 * @return the totalExpCurrentInventory
	 */
	public String getTotalExpCurrentInventory() {
		return totalExpCurrentInventory;
	}
	/**
	 * @param totalExpCurrentInventory the totalExpCurrentInventory to set
	 */
	public void setTotalExpCurrentInventory(String totalExpCurrentInventory) {
		this.totalExpCurrentInventory = totalExpCurrentInventory;
	}
	/**
	 * @return the totalCurrentInventory
	 */
	public String getTotalCurrentInventory() {
		return totalCurrentInventory;
	}
	/**
	 * @param totalCurrentInventory the totalCurrentInventory to set
	 */
	public void setTotalCurrentInventory(String totalCurrentInventory) {
		this.totalCurrentInventory = totalCurrentInventory;
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
}
