/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

import java.util.List;

import com.dowhile.angualrspringapp.beans.ImageData;


/**
 * @author Yameen Bashir
 *
 */
public class ProductBean {
	
	private String category;
	private String productId;
	private String companyName;
	private String css;
	private String display;
	private String dailyRegesterId;
	private String productTypeName;
	private String productTypeId;
	private String brandId;
	private String brandName;
	private String supplierId;
	private String supplierName;
	private String outletId;
	private String quantity;
	private String outletName;
	private String productName;
	private String productDesc;
	private String productHandler;
	private String salesAccountCode;
	private String purchaseAccountCode;
	private String imagePath;
	private String supplyPriceExclTax;
	private String markupPrct;
	private String retailPriceExclTax;
	private String sku;
	private String netPrice;
	private String varinatsCount;
	private String inventoryCount;
	private String currentInventory;
	private String reorderPoint;
	private String reorderAmount;
	private String salesTaxId;
	private String salesTaxValue;
	private String retailPriceInclTax;
	private String taxAmount;
	private String createdDate;
	private String productUuid;
	private List<TagBean> tagList;
	private List<OutletBean> outletList;
	private List<OutletBean> outletBeans;
	
	private String readyForSold;
	private String variantProductsFlag;
	private String trackingInventoryFlag;
	private String varientAttributeId;
	private String varientAttributeName;
	private String value;
	private String productCanBeSold;
	private String standardProduct;
	private String trackingProduct;
	private String varientProducts;
	private List<VarientValueBean> productVariantValuesCollection;
	private List<VarientAttributeBean> productVariantAttributeCollection;
	private List<VarientAttributeValueBean> productVariantValuesCollectionOne;
	private List<VarientAttributeValueBean> productVariantValuesCollectionTwo;
	private List<VarientAttributeValueBean> productVariantValuesCollectionThree;
	
	private List<ProductVariantBean> productVariantsBeans;
	private List<CompositVariantBean> compositVariantBeanList;
	private ProductVaraintDetailBean productVaraintDetailBean;
	private List<ProductTagBean> productTagBeanList;
	private List<CompositVariantBean> compositProductCollection;
	private ImageData imageData;
	private String orignalPrice;
	
	/**
	 * 
	 */
	public ProductBean() {
	}

	/**
	 * @param productId
	 * @param companyName
	 * @param css
	 * @param display
	 * @param dailyRegesterId
	 * @param productTypeName
	 * @param productTypeId
	 * @param brandId
	 * @param brandName
	 * @param supplierId
	 * @param supplierName
	 * @param outletId
	 * @param outletName
	 * @param productName
	 * @param productDesc
	 * @param productHandler
	 * @param salesAccountCode
	 * @param purchaseAccountCode
	 * @param imagePath
	 * @param supplyPriceExclTax
	 * @param markupPrct
	 * @param retailPriceExclTax
	 * @param sku
	 * @param netPrice
	 * @param varinatsCount
	 * @param inventoryCount
	 * @param currentInventory
	 * @param reorderPoint
	 * @param reorderAmount
	 * @param salesTaxId
	 * @param salesTaxValue
	 * @param retailPriceInclTax
	 * @param taxAmount
	 * @param createdDate
	 * @param productUuid
	 * @param tagList
	 * @param outletList
	 * @param outletBeans
	 * @param readyForSold
	 * @param variantProductsFlag
	 * @param trackingInventoryFlag
	 * @param varientAttributeId
	 * @param varientAttributeName
	 * @param value
	 * @param productCanBeSold
	 * @param standardProduct
	 * @param trackingProduct
	 * @param varientProducts
	 * @param productVariantValuesCollection
	 * @param productVariantAttributeCollection
	 * @param productVariantValuesCollectionOne
	 * @param productVariantValuesCollectionTwo
	 * @param productVariantValuesCollectionThree
	 * @param productVariantsBeans
	 * @param compositVariantBeanList
	 * @param productVaraintDetailBean
	 * @param productTagBeanList
	 * @param compositProductCollection
	 * @param imageData
	 * @param orignalPrice
	 */
	public ProductBean(
			String productId,
			String companyName,
			String css,
			String display,
			String dailyRegesterId,
			String productTypeName,
			String productTypeId,
			String brandId,
			String brandName,
			String supplierId,
			String supplierName,
			String outletId,
			String outletName,
			String productName,
			String productDesc,
			String productHandler,
			String salesAccountCode,
			String purchaseAccountCode,
			String imagePath,
			String supplyPriceExclTax,
			String markupPrct,
			String retailPriceExclTax,
			String sku,
			String netPrice,
			String varinatsCount,
			String inventoryCount,
			String currentInventory,
			String reorderPoint,
			String reorderAmount,
			String salesTaxId,
			String salesTaxValue,
			String retailPriceInclTax,
			String taxAmount,
			String createdDate,
			String productUuid,
			List<TagBean> tagList,
			List<OutletBean> outletList,
			List<OutletBean> outletBeans,
			String readyForSold,
			String variantProductsFlag,
			String trackingInventoryFlag,
			String varientAttributeId,
			String varientAttributeName,
			String value,
			String productCanBeSold,
			String standardProduct,
			String trackingProduct,
			String varientProducts,
			List<VarientValueBean> productVariantValuesCollection,
			List<VarientAttributeBean> productVariantAttributeCollection,
			List<VarientAttributeValueBean> productVariantValuesCollectionOne,
			List<VarientAttributeValueBean> productVariantValuesCollectionTwo,
			List<VarientAttributeValueBean> productVariantValuesCollectionThree,
			List<ProductVariantBean> productVariantsBeans,
			List<CompositVariantBean> compositVariantBeanList,
			ProductVaraintDetailBean productVaraintDetailBean,
			List<ProductTagBean> productTagBeanList,
			List<CompositVariantBean> compositProductCollection,
			ImageData imageData, String orignalPrice) {
		this.productId = productId;
		this.companyName = companyName;
		this.css = css;
		this.display = display;
		this.dailyRegesterId = dailyRegesterId;
		this.productTypeName = productTypeName;
		this.productTypeId = productTypeId;
		this.brandId = brandId;
		this.brandName = brandName;
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.outletId = outletId;
		this.outletName = outletName;
		this.productName = productName;
		this.productDesc = productDesc;
		this.productHandler = productHandler;
		this.salesAccountCode = salesAccountCode;
		this.purchaseAccountCode = purchaseAccountCode;
		this.imagePath = imagePath;
		this.supplyPriceExclTax = supplyPriceExclTax;
		this.markupPrct = markupPrct;
		this.retailPriceExclTax = retailPriceExclTax;
		this.sku = sku;
		this.netPrice = netPrice;
		this.varinatsCount = varinatsCount;
		this.inventoryCount = inventoryCount;
		this.currentInventory = currentInventory;
		this.reorderPoint = reorderPoint;
		this.reorderAmount = reorderAmount;
		this.salesTaxId = salesTaxId;
		this.salesTaxValue = salesTaxValue;
		this.retailPriceInclTax = retailPriceInclTax;
		this.taxAmount = taxAmount;
		this.createdDate = createdDate;
		this.productUuid = productUuid;
		this.tagList = tagList;
		this.outletList = outletList;
		this.outletBeans = outletBeans;
		this.readyForSold = readyForSold;
		this.variantProductsFlag = variantProductsFlag;
		this.trackingInventoryFlag = trackingInventoryFlag;
		this.varientAttributeId = varientAttributeId;
		this.varientAttributeName = varientAttributeName;
		this.value = value;
		this.productCanBeSold = productCanBeSold;
		this.standardProduct = standardProduct;
		this.trackingProduct = trackingProduct;
		this.varientProducts = varientProducts;
		this.productVariantValuesCollection = productVariantValuesCollection;
		this.productVariantAttributeCollection = productVariantAttributeCollection;
		this.productVariantValuesCollectionOne = productVariantValuesCollectionOne;
		this.productVariantValuesCollectionTwo = productVariantValuesCollectionTwo;
		this.productVariantValuesCollectionThree = productVariantValuesCollectionThree;
		this.productVariantsBeans = productVariantsBeans;
		this.compositVariantBeanList = compositVariantBeanList;
		this.productVaraintDetailBean = productVaraintDetailBean;
		this.productTagBeanList = productTagBeanList;
		this.compositProductCollection = compositProductCollection;
		this.imageData = imageData;
		this.orignalPrice = orignalPrice;
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
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	/**
	 * @return the css
	 */
	public String getCss() {
		return css;
	}

	/**
	 * @param css the css to set
	 */
	public void setCss(String css) {
		this.css = css;
	}

	/**
	 * @return the display
	 */
	public String getDisplay() {
		return display;
	}

	/**
	 * @param display the display to set
	 */
	public void setDisplay(String display) {
		this.display = display;
	}

	/**
	 * @return the dailyRegesterId
	 */
	public String getDailyRegesterId() {
		return dailyRegesterId;
	}

	/**
	 * @param dailyRegesterId the dailyRegesterId to set
	 */
	public void setDailyRegesterId(String dailyRegesterId) {
		this.dailyRegesterId = dailyRegesterId;
	}

	/**
	 * @return the productTypeName
	 */
	public String getProductTypeName() {
		return productTypeName;
	}

	/**
	 * @param productTypeName the productTypeName to set
	 */
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	/**
	 * @return the productTypeId
	 */
	public String getProductTypeId() {
		return productTypeId;
	}

	/**
	 * @param productTypeId the productTypeId to set
	 */
	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

	/**
	 * @return the brandId
	 */
	public String getBrandId() {
		return brandId;
	}

	/**
	 * @param brandId the brandId to set
	 */
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	/**
	 * @return the brandName
	 */
	public String getBrandName() {
		return brandName;
	}

	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
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

	/**
	 * @return the productHandler
	 */
	public String getProductHandler() {
		return productHandler;
	}

	/**
	 * @param productHandler the productHandler to set
	 */
	public void setProductHandler(String productHandler) {
		this.productHandler = productHandler;
	}

	/**
	 * @return the salesAccountCode
	 */
	public String getSalesAccountCode() {
		return salesAccountCode;
	}

	/**
	 * @param salesAccountCode the salesAccountCode to set
	 */
	public void setSalesAccountCode(String salesAccountCode) {
		this.salesAccountCode = salesAccountCode;
	}

	/**
	 * @return the purchaseAccountCode
	 */
	public String getPurchaseAccountCode() {
		return purchaseAccountCode;
	}

	/**
	 * @param purchaseAccountCode the purchaseAccountCode to set
	 */
	public void setPurchaseAccountCode(String purchaseAccountCode) {
		this.purchaseAccountCode = purchaseAccountCode;
	}

	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * @param imagePath the imagePath to set
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
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
	 * @return the varinatsCount
	 */
	public String getVarinatsCount() {
		return varinatsCount;
	}

	/**
	 * @param varinatsCount the varinatsCount to set
	 */
	public void setVarinatsCount(String varinatsCount) {
		this.varinatsCount = varinatsCount;
	}

	/**
	 * @return the inventoryCount
	 */
	public String getInventoryCount() {
		return inventoryCount;
	}

	/**
	 * @param inventoryCount the inventoryCount to set
	 */
	public void setInventoryCount(String inventoryCount) {
		this.inventoryCount = inventoryCount;
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
	 * @return the salesTaxId
	 */
	public String getSalesTaxId() {
		return salesTaxId;
	}

	/**
	 * @param salesTaxId the salesTaxId to set
	 */
	public void setSalesTaxId(String salesTaxId) {
		this.salesTaxId = salesTaxId;
	}

	/**
	 * @return the salesTaxValue
	 */
	public String getSalesTaxValue() {
		return salesTaxValue;
	}

	/**
	 * @param salesTaxValue the salesTaxValue to set
	 */
	public void setSalesTaxValue(String salesTaxValue) {
		this.salesTaxValue = salesTaxValue;
	}

	/**
	 * @return the retailPriceInclTax
	 */
	public String getRetailPriceInclTax() {
		return retailPriceInclTax;
	}

	/**
	 * @param retailPriceInclTax the retailPriceInclTax to set
	 */
	public void setRetailPriceInclTax(String retailPriceInclTax) {
		this.retailPriceInclTax = retailPriceInclTax;
	}

	/**
	 * @return the taxAmount
	 */
	public String getTaxAmount() {
		return taxAmount;
	}

	/**
	 * @param taxAmount the taxAmount to set
	 */
	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
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
	 * @return the tagList
	 */
	public List<TagBean> getTagList() {
		return tagList;
	}

	/**
	 * @param tagList the tagList to set
	 */
	public void setTagList(List<TagBean> tagList) {
		this.tagList = tagList;
	}

	/**
	 * @return the outletList
	 */
	public List<OutletBean> getOutletList() {
		return outletList;
	}

	/**
	 * @param outletList the outletList to set
	 */
	public void setOutletList(List<OutletBean> outletList) {
		this.outletList = outletList;
	}

	/**
	 * @return the outletBeans
	 */
	public List<OutletBean> getOutletBeans() {
		return outletBeans;
	}

	/**
	 * @param outletBeans the outletBeans to set
	 */
	public void setOutletBeans(List<OutletBean> outletBeans) {
		this.outletBeans = outletBeans;
	}

	/**
	 * @return the readyForSold
	 */
	public String getReadyForSold() {
		return readyForSold;
	}

	/**
	 * @param readyForSold the readyForSold to set
	 */
	public void setReadyForSold(String readyForSold) {
		this.readyForSold = readyForSold;
	}

	/**
	 * @return the variantProductsFlag
	 */
	public String getVariantProductsFlag() {
		return variantProductsFlag;
	}

	/**
	 * @param variantProductsFlag the variantProductsFlag to set
	 */
	public void setVariantProductsFlag(String variantProductsFlag) {
		this.variantProductsFlag = variantProductsFlag;
	}

	/**
	 * @return the trackingInventoryFlag
	 */
	public String getTrackingInventoryFlag() {
		return trackingInventoryFlag;
	}

	/**
	 * @param trackingInventoryFlag the trackingInventoryFlag to set
	 */
	public void setTrackingInventoryFlag(String trackingInventoryFlag) {
		this.trackingInventoryFlag = trackingInventoryFlag;
	}

	/**
	 * @return the varientAttributeId
	 */
	public String getVarientAttributeId() {
		return varientAttributeId;
	}

	/**
	 * @param varientAttributeId the varientAttributeId to set
	 */
	public void setVarientAttributeId(String varientAttributeId) {
		this.varientAttributeId = varientAttributeId;
	}

	/**
	 * @return the varientAttributeName
	 */
	public String getVarientAttributeName() {
		return varientAttributeName;
	}

	/**
	 * @param varientAttributeName the varientAttributeName to set
	 */
	public void setVarientAttributeName(String varientAttributeName) {
		this.varientAttributeName = varientAttributeName;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the productCanBeSold
	 */
	public String getProductCanBeSold() {
		return productCanBeSold;
	}

	/**
	 * @param productCanBeSold the productCanBeSold to set
	 */
	public void setProductCanBeSold(String productCanBeSold) {
		this.productCanBeSold = productCanBeSold;
	}

	/**
	 * @return the standardProduct
	 */
	public String getStandardProduct() {
		return standardProduct;
	}

	/**
	 * @param standardProduct the standardProduct to set
	 */
	public void setStandardProduct(String standardProduct) {
		this.standardProduct = standardProduct;
	}

	/**
	 * @return the trackingProduct
	 */
	public String getTrackingProduct() {
		return trackingProduct;
	}

	/**
	 * @param trackingProduct the trackingProduct to set
	 */
	public void setTrackingProduct(String trackingProduct) {
		this.trackingProduct = trackingProduct;
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
	 * @return the productVariantValuesCollection
	 */
	public List<VarientValueBean> getProductVariantValuesCollection() {
		return productVariantValuesCollection;
	}

	/**
	 * @param productVariantValuesCollection the productVariantValuesCollection to set
	 */
	public void setProductVariantValuesCollection(
			List<VarientValueBean> productVariantValuesCollection) {
		this.productVariantValuesCollection = productVariantValuesCollection;
	}

	/**
	 * @return the productVariantAttributeCollection
	 */
	public List<VarientAttributeBean> getProductVariantAttributeCollection() {
		return productVariantAttributeCollection;
	}

	/**
	 * @param productVariantAttributeCollection the productVariantAttributeCollection to set
	 */
	public void setProductVariantAttributeCollection(
			List<VarientAttributeBean> productVariantAttributeCollection) {
		this.productVariantAttributeCollection = productVariantAttributeCollection;
	}

	/**
	 * @return the productVariantValuesCollectionOne
	 */
	public List<VarientAttributeValueBean> getProductVariantValuesCollectionOne() {
		return productVariantValuesCollectionOne;
	}

	/**
	 * @param productVariantValuesCollectionOne the productVariantValuesCollectionOne to set
	 */
	public void setProductVariantValuesCollectionOne(
			List<VarientAttributeValueBean> productVariantValuesCollectionOne) {
		this.productVariantValuesCollectionOne = productVariantValuesCollectionOne;
	}

	/**
	 * @return the productVariantValuesCollectionTwo
	 */
	public List<VarientAttributeValueBean> getProductVariantValuesCollectionTwo() {
		return productVariantValuesCollectionTwo;
	}

	/**
	 * @param productVariantValuesCollectionTwo the productVariantValuesCollectionTwo to set
	 */
	public void setProductVariantValuesCollectionTwo(
			List<VarientAttributeValueBean> productVariantValuesCollectionTwo) {
		this.productVariantValuesCollectionTwo = productVariantValuesCollectionTwo;
	}

	/**
	 * @return the productVariantValuesCollectionThree
	 */
	public List<VarientAttributeValueBean> getProductVariantValuesCollectionThree() {
		return productVariantValuesCollectionThree;
	}

	/**
	 * @param productVariantValuesCollectionThree the productVariantValuesCollectionThree to set
	 */
	public void setProductVariantValuesCollectionThree(
			List<VarientAttributeValueBean> productVariantValuesCollectionThree) {
		this.productVariantValuesCollectionThree = productVariantValuesCollectionThree;
	}

	/**
	 * @return the productVariantsBeans
	 */
	public List<ProductVariantBean> getProductVariantsBeans() {
		return productVariantsBeans;
	}

	/**
	 * @param productVariantsBeans the productVariantsBeans to set
	 */
	public void setProductVariantsBeans(
			List<ProductVariantBean> productVariantsBeans) {
		this.productVariantsBeans = productVariantsBeans;
	}

	/**
	 * @return the compositVariantBeanList
	 */
	public List<CompositVariantBean> getCompositVariantBeanList() {
		return compositVariantBeanList;
	}

	/**
	 * @param compositVariantBeanList the compositVariantBeanList to set
	 */
	public void setCompositVariantBeanList(
			List<CompositVariantBean> compositVariantBeanList) {
		this.compositVariantBeanList = compositVariantBeanList;
	}

	/**
	 * @return the productVaraintDetailBean
	 */
	public ProductVaraintDetailBean getProductVaraintDetailBean() {
		return productVaraintDetailBean;
	}

	/**
	 * @param productVaraintDetailBean the productVaraintDetailBean to set
	 */
	public void setProductVaraintDetailBean(
			ProductVaraintDetailBean productVaraintDetailBean) {
		this.productVaraintDetailBean = productVaraintDetailBean;
	}

	/**
	 * @return the productTagBeanList
	 */
	public List<ProductTagBean> getProductTagBeanList() {
		return productTagBeanList;
	}

	/**
	 * @param productTagBeanList the productTagBeanList to set
	 */
	public void setProductTagBeanList(List<ProductTagBean> productTagBeanList) {
		this.productTagBeanList = productTagBeanList;
	}

	/**
	 * @return the compositProductCollection
	 */
	public List<CompositVariantBean> getCompositProductCollection() {
		return compositProductCollection;
	}

	/**
	 * @param compositProductCollection the compositProductCollection to set
	 */
	public void setCompositProductCollection(
			List<CompositVariantBean> compositProductCollection) {
		this.compositProductCollection = compositProductCollection;
	}

	/**
	 * @return the imageData
	 */
	public ImageData getImageData() {
		return imageData;
	}

	/**
	 * @param imageData the imageData to set
	 */
	public void setImageData(ImageData imageData) {
		this.imageData = imageData;
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

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
