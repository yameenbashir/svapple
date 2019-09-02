package com.dowhile.frontend.mapping.bean;



public class ProductVariantBean {
	
	private String iata;
	private String isProduct;
	private String isVariant;
	private String name;
	private String productVariantId;
	private String variantAttributeName;
	private String variantAttributeValue1;
	private String variantAttributeValue2;
	private String variantAttributeValue3;
	private String variantAttributeId1;
	private String variantAttributeId2;
	private String variantAttributeId3;
	private String variantAttributeIdValue1;
	private String variantAttributeIdValue2;
	private String variantAttributeIdValue3;	
	private String currentInventory;
	private String oldInventory;
	private String recCurrentInventory;
	private String reorderPoint;
	private String reorderAmount;
	private String supplyPriceExclTax;
	private String retailPriceExclTax;
	private String tax;
	private String markupPrct;
	private String sku;
	private String productId;
	private String productName;
	private String netPrice;
	private String outletName;
	private String outletId;
	private String supplierCode;
	private String productVariantUuid;
	private String orignalPrice;
	private String productSku;
	private String varientProducts;
	private String retailPrice;
	private String printCount;
	private String discount;
    private String minUnits;
    private String maxUnits;
    private String priceBookDetailId;
    private String priceBookId;
    private String productUuid;
    private String auditTransfer;
    private String productDesc;
	
	/**
	 * 
	 */
	public ProductVariantBean() {
	}

	/**
	 * @param iata
	 * @param isProduct
	 * @param isVariant
	 * @param name
	 * @param productVariantId
	 * @param variantAttributeName
	 * @param variantAttributeValue1
	 * @param variantAttributeValue2
	 * @param variantAttributeValue3
	 * @param variantAttributeId1
	 * @param variantAttributeId2
	 * @param variantAttributeId3
	 * @param variantAttributeIdValue1
	 * @param variantAttributeIdValue2
	 * @param variantAttributeIdValue3
	 * @param currentInventory
	 * @param oldInventory
	 * @param recCurrentInventory
	 * @param reorderPoint
	 * @param reorderAmount
	 * @param supplyPriceExclTax
	 * @param retailPriceExclTax
	 * @param tax
	 * @param markupPrct
	 * @param sku
	 * @param productId
	 * @param productName
	 * @param netPrice
	 * @param outletName
	 * @param outletId
	 * @param supplierCode
	 * @param productVariantUuid
	 * @param orignalPrice
	 * @param productSku
	 * @param varientProducts
	 * @param retailPrice
	 * @param printCount
	 * @param discount
	 * @param minUnits
	 * @param maxUnits
	 * @param priceBookDetailId
	 * @param priceBookId
	 * @param productUuid
	 * @param auditTransfer
	 * @param productDesc
	 */
	public ProductVariantBean(String iata, String isProduct, String isVariant,
			String name, String productVariantId, String variantAttributeName,
			String variantAttributeValue1, String variantAttributeValue2,
			String variantAttributeValue3, String variantAttributeId1,
			String variantAttributeId2, String variantAttributeId3,
			String variantAttributeIdValue1, String variantAttributeIdValue2,
			String variantAttributeIdValue3, String currentInventory,
			String oldInventory, String recCurrentInventory,
			String reorderPoint, String reorderAmount,
			String supplyPriceExclTax, String retailPriceExclTax, String tax,
			String markupPrct, String sku, String productId,
			String productName, String netPrice, String outletName,
			String outletId, String supplierCode, String productVariantUuid,
			String orignalPrice, String productSku, String varientProducts,
			String retailPrice, String printCount, String discount,
			String minUnits, String maxUnits, String priceBookDetailId,
			String priceBookId, String productUuid, String auditTransfer,
			String productDesc) {
		this.iata = iata;
		this.isProduct = isProduct;
		this.isVariant = isVariant;
		this.name = name;
		this.productVariantId = productVariantId;
		this.variantAttributeName = variantAttributeName;
		this.variantAttributeValue1 = variantAttributeValue1;
		this.variantAttributeValue2 = variantAttributeValue2;
		this.variantAttributeValue3 = variantAttributeValue3;
		this.variantAttributeId1 = variantAttributeId1;
		this.variantAttributeId2 = variantAttributeId2;
		this.variantAttributeId3 = variantAttributeId3;
		this.variantAttributeIdValue1 = variantAttributeIdValue1;
		this.variantAttributeIdValue2 = variantAttributeIdValue2;
		this.variantAttributeIdValue3 = variantAttributeIdValue3;
		this.currentInventory = currentInventory;
		this.oldInventory = oldInventory;
		this.recCurrentInventory = recCurrentInventory;
		this.reorderPoint = reorderPoint;
		this.reorderAmount = reorderAmount;
		this.supplyPriceExclTax = supplyPriceExclTax;
		this.retailPriceExclTax = retailPriceExclTax;
		this.tax = tax;
		this.markupPrct = markupPrct;
		this.sku = sku;
		this.productId = productId;
		this.productName = productName;
		this.netPrice = netPrice;
		this.outletName = outletName;
		this.outletId = outletId;
		this.supplierCode = supplierCode;
		this.productVariantUuid = productVariantUuid;
		this.orignalPrice = orignalPrice;
		this.productSku = productSku;
		this.varientProducts = varientProducts;
		this.retailPrice = retailPrice;
		this.printCount = printCount;
		this.discount = discount;
		this.minUnits = minUnits;
		this.maxUnits = maxUnits;
		this.priceBookDetailId = priceBookDetailId;
		this.priceBookId = priceBookId;
		this.productUuid = productUuid;
		this.auditTransfer = auditTransfer;
		this.productDesc = productDesc;
	}

	/**
	 * @return the iata
	 */
	public String getIata() {
		return iata;
	}

	/**
	 * @param iata the iata to set
	 */
	public void setIata(String iata) {
		this.iata = iata;
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
	 * @return the isVariant
	 */
	public String getIsVariant() {
		return isVariant;
	}

	/**
	 * @param isVariant the isVariant to set
	 */
	public void setIsVariant(String isVariant) {
		this.isVariant = isVariant;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the variantAttributeValue1
	 */
	public String getVariantAttributeValue1() {
		return variantAttributeValue1;
	}

	/**
	 * @param variantAttributeValue1 the variantAttributeValue1 to set
	 */
	public void setVariantAttributeValue1(String variantAttributeValue1) {
		this.variantAttributeValue1 = variantAttributeValue1;
	}

	/**
	 * @return the variantAttributeValue2
	 */
	public String getVariantAttributeValue2() {
		return variantAttributeValue2;
	}

	/**
	 * @param variantAttributeValue2 the variantAttributeValue2 to set
	 */
	public void setVariantAttributeValue2(String variantAttributeValue2) {
		this.variantAttributeValue2 = variantAttributeValue2;
	}

	/**
	 * @return the variantAttributeValue3
	 */
	public String getVariantAttributeValue3() {
		return variantAttributeValue3;
	}

	/**
	 * @param variantAttributeValue3 the variantAttributeValue3 to set
	 */
	public void setVariantAttributeValue3(String variantAttributeValue3) {
		this.variantAttributeValue3 = variantAttributeValue3;
	}

	/**
	 * @return the variantAttributeId1
	 */
	public String getVariantAttributeId1() {
		return variantAttributeId1;
	}

	/**
	 * @param variantAttributeId1 the variantAttributeId1 to set
	 */
	public void setVariantAttributeId1(String variantAttributeId1) {
		this.variantAttributeId1 = variantAttributeId1;
	}

	/**
	 * @return the variantAttributeId2
	 */
	public String getVariantAttributeId2() {
		return variantAttributeId2;
	}

	/**
	 * @param variantAttributeId2 the variantAttributeId2 to set
	 */
	public void setVariantAttributeId2(String variantAttributeId2) {
		this.variantAttributeId2 = variantAttributeId2;
	}

	/**
	 * @return the variantAttributeId3
	 */
	public String getVariantAttributeId3() {
		return variantAttributeId3;
	}

	/**
	 * @param variantAttributeId3 the variantAttributeId3 to set
	 */
	public void setVariantAttributeId3(String variantAttributeId3) {
		this.variantAttributeId3 = variantAttributeId3;
	}

	/**
	 * @return the variantAttributeIdValue1
	 */
	public String getVariantAttributeIdValue1() {
		return variantAttributeIdValue1;
	}

	/**
	 * @param variantAttributeIdValue1 the variantAttributeIdValue1 to set
	 */
	public void setVariantAttributeIdValue1(String variantAttributeIdValue1) {
		this.variantAttributeIdValue1 = variantAttributeIdValue1;
	}

	/**
	 * @return the variantAttributeIdValue2
	 */
	public String getVariantAttributeIdValue2() {
		return variantAttributeIdValue2;
	}

	/**
	 * @param variantAttributeIdValue2 the variantAttributeIdValue2 to set
	 */
	public void setVariantAttributeIdValue2(String variantAttributeIdValue2) {
		this.variantAttributeIdValue2 = variantAttributeIdValue2;
	}

	/**
	 * @return the variantAttributeIdValue3
	 */
	public String getVariantAttributeIdValue3() {
		return variantAttributeIdValue3;
	}

	/**
	 * @param variantAttributeIdValue3 the variantAttributeIdValue3 to set
	 */
	public void setVariantAttributeIdValue3(String variantAttributeIdValue3) {
		this.variantAttributeIdValue3 = variantAttributeIdValue3;
	}

	/**
	 * @return the currentInventory
	 */
	public String getCurrentInventory() {
		return currentInventory;
	}

	/**
	 * @param currentInventory the currentInventory to set
	 */
	public void setCurrentInventory(String currentInventory) {
		this.currentInventory = currentInventory;
	}

	/**
	 * @return the oldInventory
	 */
	public String getOldInventory() {
		return oldInventory;
	}

	/**
	 * @param oldInventory the oldInventory to set
	 */
	public void setOldInventory(String oldInventory) {
		this.oldInventory = oldInventory;
	}

	/**
	 * @return the recCurrentInventory
	 */
	public String getRecCurrentInventory() {
		return recCurrentInventory;
	}

	/**
	 * @param recCurrentInventory the recCurrentInventory to set
	 */
	public void setRecCurrentInventory(String recCurrentInventory) {
		this.recCurrentInventory = recCurrentInventory;
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
	 * @return the supplyPriceExclTax
	 */
	public String getSupplyPriceExclTax() {
		return supplyPriceExclTax;
	}

	/**
	 * @param supplyPriceExclTax the supplyPriceExclTax to set
	 */
	public void setSupplyPriceExclTax(String supplyPriceExclTax) {
		this.supplyPriceExclTax = supplyPriceExclTax;
	}

	/**
	 * @return the retailPriceExclTax
	 */
	public String getRetailPriceExclTax() {
		return retailPriceExclTax;
	}

	/**
	 * @param retailPriceExclTax the retailPriceExclTax to set
	 */
	public void setRetailPriceExclTax(String retailPriceExclTax) {
		this.retailPriceExclTax = retailPriceExclTax;
	}

	/**
	 * @return the tax
	 */
	public String getTax() {
		return tax;
	}

	/**
	 * @param tax the tax to set
	 */
	public void setTax(String tax) {
		this.tax = tax;
	}

	/**
	 * @return the markupPrct
	 */
	public String getMarkupPrct() {
		return markupPrct;
	}

	/**
	 * @param markupPrct the markupPrct to set
	 */
	public void setMarkupPrct(String markupPrct) {
		this.markupPrct = markupPrct;
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
	 * @return the netPrice
	 */
	public String getNetPrice() {
		return netPrice;
	}

	/**
	 * @param netPrice the netPrice to set
	 */
	public void setNetPrice(String netPrice) {
		this.netPrice = netPrice;
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
	 * @return the supplierCode
	 */
	public String getSupplierCode() {
		return supplierCode;
	}

	/**
	 * @param supplierCode the supplierCode to set
	 */
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}

	/**
	 * @return the productVariantUuid
	 */
	public String getProductVariantUuid() {
		return productVariantUuid;
	}

	/**
	 * @param productVariantUuid the productVariantUuid to set
	 */
	public void setProductVariantUuid(String productVariantUuid) {
		this.productVariantUuid = productVariantUuid;
	}

	/**
	 * @return the orignalPrice
	 */
	public String getOrignalPrice() {
		return orignalPrice;
	}

	/**
	 * @param orignalPrice the orignalPrice to set
	 */
	public void setOrignalPrice(String orignalPrice) {
		this.orignalPrice = orignalPrice;
	}

	/**
	 * @return the productSku
	 */
	public String getProductSku() {
		return productSku;
	}

	/**
	 * @param productSku the productSku to set
	 */
	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}

	/**
	 * @return the varientProducts
	 */
	public String getVarientProducts() {
		return varientProducts;
	}

	/**
	 * @param varientProducts the varientProducts to set
	 */
	public void setVarientProducts(String varientProducts) {
		this.varientProducts = varientProducts;
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

	/**
	 * @return the printCount
	 */
	public String getPrintCount() {
		return printCount;
	}

	/**
	 * @param printCount the printCount to set
	 */
	public void setPrintCount(String printCount) {
		this.printCount = printCount;
	}

	/**
	 * @return the discount
	 */
	public String getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(String discount) {
		this.discount = discount;
	}

	/**
	 * @return the minUnits
	 */
	public String getMinUnits() {
		return minUnits;
	}

	/**
	 * @param minUnits the minUnits to set
	 */
	public void setMinUnits(String minUnits) {
		this.minUnits = minUnits;
	}

	/**
	 * @return the maxUnits
	 */
	public String getMaxUnits() {
		return maxUnits;
	}

	/**
	 * @param maxUnits the maxUnits to set
	 */
	public void setMaxUnits(String maxUnits) {
		this.maxUnits = maxUnits;
	}

	/**
	 * @return the priceBookDetailId
	 */
	public String getPriceBookDetailId() {
		return priceBookDetailId;
	}

	/**
	 * @param priceBookDetailId the priceBookDetailId to set
	 */
	public void setPriceBookDetailId(String priceBookDetailId) {
		this.priceBookDetailId = priceBookDetailId;
	}

	/**
	 * @return the priceBookId
	 */
	public String getPriceBookId() {
		return priceBookId;
	}

	/**
	 * @param priceBookId the priceBookId to set
	 */
	public void setPriceBookId(String priceBookId) {
		this.priceBookId = priceBookId;
	}

	/**
	 * @return the productUuid
	 */
	public String getProductUuid() {
		return productUuid;
	}

	/**
	 * @param productUuid the productUuid to set
	 */
	public void setProductUuid(String productUuid) {
		this.productUuid = productUuid;
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

	/**
	 * @return the productDesc
	 */
	public String getProductDesc() {
		return productDesc;
	}

	/**
	 * @param productDesc the productDesc to set
	 */
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
}
