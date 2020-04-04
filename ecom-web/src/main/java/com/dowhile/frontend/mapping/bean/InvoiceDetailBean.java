/**
 * 
 */
package com.dowhile.frontend.mapping.bean;


/**
 *
 */
public class InvoiceDetailBean {
	public String getItemRetailPrice() {
		return itemRetailPrice;
	}
	public void setItemRetailPrice(String itemRetailPrice) {
		this.itemRetailPrice = itemRetailPrice;
	}
	public String getItemTaxAmount() {
		return itemTaxAmount;
	}
	public void setItemTaxAmount(String itemTaxAmount) {
		this.itemTaxAmount = itemTaxAmount;
	}
	/**
	 * @param invoiceDetailId
	 * @param productId
	 * @param productName
	 * @param productQuantity
	 * @param status
	 * @param amntCmptCde
	 * @param amntSubCmptCde
	 * @param itemAmount
	 * @param itemNotes
	 * @param itemDiscount
	 * @param invoiceSettleDte
	 * @param settledAmt
	 * @param activeIndicator
	 * @param createdDate
	 * @param lastUpdated
	 * @param createdBy
	 * @param updatedBy
	 */
	
	public String getInvoiceDetailId() {
		return invoiceDetailId;
	}
	public void setInvoiceDetailId(String invoiceDetailId) {
		this.invoiceDetailId = invoiceDetailId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAmntCmptCde() {
		return amntCmptCde;
	}
	public void setAmntCmptCde(String amntCmptCde) {
		this.amntCmptCde = amntCmptCde;
	}
	public String getAmntSubCmptCde() {
		return amntSubCmptCde;
	}
	public void setAmntSubCmptCde(String amntSubCmptCde) {
		this.amntSubCmptCde = amntSubCmptCde;
	}
	
	public String getItemNotes() {
		return itemNotes;
	}
	public void setItemNotes(String itemNotes) {
		this.itemNotes = itemNotes;
	}
	
	public String getInvoiceSettleDte() {
		return invoiceSettleDte;
	}
	public void setInvoiceSettleDte(String invoiceSettleDte) {
		this.invoiceSettleDte = invoiceSettleDte;
	}
	public String getSettledAmt() {
		return settledAmt;
	}
	public void setSettledAmt(String settledAmt) {
		this.settledAmt = settledAmt;
	}
	public String getActiveIndicator() {
		return activeIndicator;
	}
	public void setActiveIndicator(String activeIndicator) {
		this.activeIndicator = activeIndicator;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(String productQuantity) {
		this.productQuantity = productQuantity;
	}
	
	public String getProductVarientId() {
		return productVarientId;
	}
	public void setProductVarientId(String productVarientId) {
		this.productVarientId = productVarientId;
	}
	public String getProductVarientName() {
		return productVarientName;
	}
	public void setProductVarientName(String productVarientName) {
		this.productVarientName = productVarientName;
	}
	
	private String invoiceDetailId;
	private String productId;
	private String productVarientId;
	private String productName;
	private String productVarientName;
	private String productQuantity;
	private String orignalProductQuantity;
	private String status;
	private String amntCmptCde;
	private String amntSubCmptCde;
	private String itemSalePrice;
	private String itemRetailPrice;
	private String itemNetPrice;
	private String itemTaxAmount;
	private String itemNotes;
	private String itemDiscountPrct;
	private String itemDiscountAmount;
	
	private String invoiceSettleDte;
	private String settledAmt;
	private String activeIndicator;
	private String createdDate;
	private String lastUpdated;
	private String createdBy;
	private String updatedBy;
	private String productInventoryCount;
	private String orignalPrice;
	private boolean isreturn = false;
	
	/**
	 * @return the isreturn
	 */
	public boolean isIsreturn() {
		return isreturn;
	}
	/**
	 * @param isreturn the isreturn to set
	 */
	public void setIsreturn(boolean isreturn) {
		this.isreturn = isreturn;
	}

	private String showDetails = "false";
	public String getShowDetails() {
		return showDetails;
	}
	public void setShowDetails(String showDetails) {
		this.showDetails = showDetails;
	}
	public String getItemSalePrice() {
		return itemSalePrice;
	}
	public void setItemSalePrice(String itemSalePrice) {
		this.itemSalePrice = itemSalePrice;
	}
	public String getItemDiscountPrct() {
		return itemDiscountPrct;
	}
	public void setItemDiscountPrct(String itemDiscountPrct) {
		this.itemDiscountPrct = itemDiscountPrct;
	}
	public String getItemNetPrice() {
		return itemNetPrice;
	}
	public void setItemNetPrice(String itemNetPrice) {
		this.itemNetPrice = itemNetPrice;
	}
	public String getProductInventoryCount() {
		return productInventoryCount;
	}
	public void setProductInventoryCount(String productInventoryCount) {
		this.productInventoryCount = productInventoryCount;
	}
	public String getOrignalProductQuantity() {
		return orignalProductQuantity;
	}
	public void setOrignalProductQuantity(String orignalProductQuantity) {
		this.orignalProductQuantity = orignalProductQuantity;
	}
	public String getOrignalPrice() {
		return orignalPrice;
	}
	public void setOrignalPrice(String orignalPrice) {
		this.orignalPrice = orignalPrice;
	}
	/*public String getIsreturn() {
		return isreturn;
	}
	public void setIsreturn(String isreturn) {
		this.isreturn = isreturn;
	}*/
	public String getItemDiscountAmount() {
		return itemDiscountAmount;
	}
	public void setItemDiscountAmount(String itemDiscountAmount) {
		this.itemDiscountAmount = itemDiscountAmount;
	}
	
}
