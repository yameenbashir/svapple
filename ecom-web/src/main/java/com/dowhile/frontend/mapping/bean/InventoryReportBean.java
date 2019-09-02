/**
 * 
 */
package com.dowhile.frontend.mapping.bean;


/**
 * @author Hafiz Yameen Bashir
 *
 */
public class InventoryReportBean {
	
	private String productId;
	private String varinatAttributeName;
	private String outletName;
	private String sku;
	private String brand;
	private String supplierName;
	private String type;
	private String tag;
	private String currentStock;
	private String stockValue;
	private String itemValue;
	private String reorderPoint;
	private String reorderAmount;
	private String totalCurrentStock;
	private String totalCurrentValue;
	private String totalStockSent;
	private String sold;
	/**
	 * 
	 */
	public InventoryReportBean() {
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
	public InventoryReportBean(String productId, String varinatAttributeName,
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
		this.currentStock = currentStock;
		this.stockValue = stockValue;
		this.itemValue = itemValue;
		this.reorderPoint = reorderPoint;
		this.reorderAmount = reorderAmount;
		this.totalCurrentStock = totalCurrentStock;
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
	 * @return the currentStock
	 */
	public String getCurrentStock() {
		return currentStock;
	}
	/**
	 * @param currentStock the currentStock to set
	 */
	public void setCurrentStock(String currentStock) {
		this.currentStock = currentStock;
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
	/**
	 * @return the reorderPoint
	 */
	public String getReorderPoint() {
		return reorderPoint;
	}
	/**
	 * @param reorderPoint the reorderPoint to set
	 */
	public void setReorderPoint(String reorderPoint) {
		this.reorderPoint = reorderPoint;
	}
	/**
	 * @return the reorderAmount
	 */
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
	 * @return the totalCurrentStock
	 */
	public String getTotalCurrentStock() {
		return totalCurrentStock;
	}
	/**
	 * @param totalCurrentStock the totalCurrentStock to set
	 */
	public void setTotalCurrentStock(String totalCurrentStock) {
		this.totalCurrentStock = totalCurrentStock;
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
}
