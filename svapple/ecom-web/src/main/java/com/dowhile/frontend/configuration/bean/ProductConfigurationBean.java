/**
 * 
 */
package com.dowhile.frontend.configuration.bean;

/**
 * @author Hafiz Yameen Bashir
 *
 */
public class ProductConfigurationBean {
	
	private boolean productName;
	private boolean sku;
	private boolean supplierId;
	private boolean productDesc;
	private boolean productCode;
	private boolean purchaseAccountCode;
	private boolean salesAccountCode;
	private boolean supplyPriceExclusiveTax;
	private boolean markupPrct;
	private boolean retailPriceExclusiveTax;
	private boolean variantProducts;
	private boolean trackingInventory;
	private boolean currentInventory;
	private boolean reOrderPoint;
	private boolean reOrderAmount;
	private boolean productTag;
	private boolean productImage;
	/**
	 * 
	 */
	public ProductConfigurationBean() {
	}
	/**
	 * @param productName
	 * @param sku
	 * @param supplierId
	 * @param productDesc
	 * @param productCode
	 * @param purchaseAccountCode
	 * @param salesAccountCode
	 * @param supplyPriceExclusiveTax
	 * @param markupPrct
	 * @param retailPriceExclusiveTax
	 * @param variantProducts
	 * @param trackingInventory
	 * @param currentInventory
	 * @param reOrderPoint
	 * @param reOrderAmount
	 * @param productTag
	 * @param productImage
	 */
	public ProductConfigurationBean(boolean productName, boolean sku,
			boolean supplierId, boolean productDesc, boolean productCode,
			boolean purchaseAccountCode, boolean salesAccountCode,
			boolean supplyPriceExclusiveTax, boolean markupPrct,
			boolean retailPriceExclusiveTax, boolean variantProducts,
			boolean trackingInventory, boolean currentInventory,
			boolean reOrderPoint, boolean reOrderAmount, boolean productTag,
			boolean productImage) {
		this.productName = productName;
		this.sku = sku;
		this.supplierId = supplierId;
		this.productDesc = productDesc;
		this.productCode = productCode;
		this.purchaseAccountCode = purchaseAccountCode;
		this.salesAccountCode = salesAccountCode;
		this.supplyPriceExclusiveTax = supplyPriceExclusiveTax;
		this.markupPrct = markupPrct;
		this.retailPriceExclusiveTax = retailPriceExclusiveTax;
		this.variantProducts = variantProducts;
		this.trackingInventory = trackingInventory;
		this.currentInventory = currentInventory;
		this.reOrderPoint = reOrderPoint;
		this.reOrderAmount = reOrderAmount;
		this.productTag = productTag;
		this.productImage = productImage;
	}
	/**
	 * @return the productName
	 */
	public boolean isProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(boolean productName) {
		this.productName = productName;
	}
	/**
	 * @return the sku
	 */
	public boolean isSku() {
		return sku;
	}
	/**
	 * @param sku the sku to set
	 */
	public void setSku(boolean sku) {
		this.sku = sku;
	}
	/**
	 * @return the supplierId
	 */
	public boolean isSupplierId() {
		return supplierId;
	}
	/**
	 * @param supplierId the supplierId to set
	 */
	public void setSupplierId(boolean supplierId) {
		this.supplierId = supplierId;
	}
	/**
	 * @return the productDesc
	 */
	public boolean isProductDesc() {
		return productDesc;
	}
	/**
	 * @param productDesc the productDesc to set
	 */
	public void setProductDesc(boolean productDesc) {
		this.productDesc = productDesc;
	}
	/**
	 * @return the productCode
	 */
	public boolean isProductCode() {
		return productCode;
	}
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(boolean productCode) {
		this.productCode = productCode;
	}
	/**
	 * @return the purchaseAccountCode
	 */
	public boolean isPurchaseAccountCode() {
		return purchaseAccountCode;
	}
	/**
	 * @param purchaseAccountCode the purchaseAccountCode to set
	 */
	public void setPurchaseAccountCode(boolean purchaseAccountCode) {
		this.purchaseAccountCode = purchaseAccountCode;
	}
	/**
	 * @return the salesAccountCode
	 */
	public boolean isSalesAccountCode() {
		return salesAccountCode;
	}
	/**
	 * @param salesAccountCode the salesAccountCode to set
	 */
	public void setSalesAccountCode(boolean salesAccountCode) {
		this.salesAccountCode = salesAccountCode;
	}
	/**
	 * @return the supplyPriceExclusiveTax
	 */
	public boolean isSupplyPriceExclusiveTax() {
		return supplyPriceExclusiveTax;
	}
	/**
	 * @param supplyPriceExclusiveTax the supplyPriceExclusiveTax to set
	 */
	public void setSupplyPriceExclusiveTax(boolean supplyPriceExclusiveTax) {
		this.supplyPriceExclusiveTax = supplyPriceExclusiveTax;
	}
	/**
	 * @return the markupPrct
	 */
	public boolean isMarkupPrct() {
		return markupPrct;
	}
	/**
	 * @param markupPrct the markupPrct to set
	 */
	public void setMarkupPrct(boolean markupPrct) {
		this.markupPrct = markupPrct;
	}
	/**
	 * @return the retailPriceExclusiveTax
	 */
	public boolean isRetailPriceExclusiveTax() {
		return retailPriceExclusiveTax;
	}
	/**
	 * @param retailPriceExclusiveTax the retailPriceExclusiveTax to set
	 */
	public void setRetailPriceExclusiveTax(boolean retailPriceExclusiveTax) {
		this.retailPriceExclusiveTax = retailPriceExclusiveTax;
	}
	/**
	 * @return the variantProducts
	 */
	public boolean isVariantProducts() {
		return variantProducts;
	}
	/**
	 * @param variantProducts the variantProducts to set
	 */
	public void setVariantProducts(boolean variantProducts) {
		this.variantProducts = variantProducts;
	}
	/**
	 * @return the trackingInventory
	 */
	public boolean isTrackingInventory() {
		return trackingInventory;
	}
	/**
	 * @param trackingInventory the trackingInventory to set
	 */
	public void setTrackingInventory(boolean trackingInventory) {
		this.trackingInventory = trackingInventory;
	}
	/**
	 * @return the currentInventory
	 */
	public boolean isCurrentInventory() {
		return currentInventory;
	}
	/**
	 * @param currentInventory the currentInventory to set
	 */
	public void setCurrentInventory(boolean currentInventory) {
		this.currentInventory = currentInventory;
	}
	/**
	 * @return the reOrderPoint
	 */
	public boolean isReOrderPoint() {
		return reOrderPoint;
	}
	/**
	 * @param reOrderPoint the reOrderPoint to set
	 */
	public void setReOrderPoint(boolean reOrderPoint) {
		this.reOrderPoint = reOrderPoint;
	}
	/**
	 * @return the reOrderAmount
	 */
	public boolean isReOrderAmount() {
		return reOrderAmount;
	}
	/**
	 * @param reOrderAmount the reOrderAmount to set
	 */
	public void setReOrderAmount(boolean reOrderAmount) {
		this.reOrderAmount = reOrderAmount;
	}
	/**
	 * @return the productTag
	 */
	public boolean isProductTag() {
		return productTag;
	}
	/**
	 * @param productTag the productTag to set
	 */
	public void setProductTag(boolean productTag) {
		this.productTag = productTag;
	}
	/**
	 * @return the productImage
	 */
	public boolean isProductImage() {
		return productImage;
	}
	/**
	 * @param productImage the productImage to set
	 */
	public void setProductImage(boolean productImage) {
		this.productImage = productImage;
	}
}
