package com.dowhile.frontend.mapping.bean;

import java.util.List;

/**
 * @author Yameen Bashir
 *
 */
public class OutletBean {
	
	private String outletId;
	private String outletName;
	private String defaultTax;
	private String defaultTaxId;
	private String orderNumber;
	private String orderNumberPrefix;
	private String supplieNumberPrefix;
	private String supplierReturnNumber;
	private AddressBean addressbean;
	private List<RegisterBean> registerBeanList;
	private String registers;
	private String registerId;
	private String status;
	private String details;
	private String salesTaxId;
	private String salesTaxName;
	//Below fields For add product page
	private String taxAmount;
	private String retailPrice;
	private String currentInventory;
	private String oldInventory;
	private String reorderPoint;
	private String reorderAmount;
	private String supplierId;
	private String supplierCode;
	private String supplierName; 
	private String supplyPriceExclTax;
	private String markupPrct;
	private String retailPriceExclTax;
	private String sku;
	private String dailyRegesterId;
	private String isHeadOffice;
	private String isDuplicateBarCode;
	/**
	 * 
	 */
	public OutletBean() {
	}
	/**
	 * @param outletId
	 * @param outletName
	 * @param defaultTax
	 * @param defaultTaxId
	 * @param orderNumber
	 * @param orderNumberPrefix
	 * @param supplieNumberPrefix
	 * @param supplierReturnNumber
	 * @param addressbean
	 * @param registerBeanList
	 * @param registers
	 * @param registerId
	 * @param status
	 * @param details
	 * @param salesTaxId
	 * @param salesTaxName
	 * @param taxAmount
	 * @param retailPrice
	 * @param currentInventory
	 * @param oldInventory
	 * @param reorderPoint
	 * @param reorderAmount
	 * @param supplierId
	 * @param supplierCode
	 * @param supplierName
	 * @param supplyPriceExclTax
	 * @param markupPrct
	 * @param retailPriceExclTax
	 * @param sku
	 * @param dailyRegesterId
	 * @param isHeadOffice
	 * @param isDuplicateBarCode
	 */
	public OutletBean(String outletId, String outletName, String defaultTax,
			String defaultTaxId, String orderNumber, String orderNumberPrefix,
			String supplieNumberPrefix, String supplierReturnNumber,
			AddressBean addressbean, List<RegisterBean> registerBeanList,
			String registers, String registerId, String status, String details,
			String salesTaxId, String salesTaxName, String taxAmount,
			String retailPrice, String currentInventory, String oldInventory,
			String reorderPoint, String reorderAmount, String supplierId,
			String supplierCode, String supplierName,
			String supplyPriceExclTax, String markupPrct,
			String retailPriceExclTax, String sku, String dailyRegesterId,
			String isHeadOffice, String isDuplicateBarCode) {
		this.outletId = outletId;
		this.outletName = outletName;
		this.defaultTax = defaultTax;
		this.defaultTaxId = defaultTaxId;
		this.orderNumber = orderNumber;
		this.orderNumberPrefix = orderNumberPrefix;
		this.supplieNumberPrefix = supplieNumberPrefix;
		this.supplierReturnNumber = supplierReturnNumber;
		this.addressbean = addressbean;
		this.registerBeanList = registerBeanList;
		this.registers = registers;
		this.registerId = registerId;
		this.status = status;
		this.details = details;
		this.salesTaxId = salesTaxId;
		this.salesTaxName = salesTaxName;
		this.taxAmount = taxAmount;
		this.retailPrice = retailPrice;
		this.currentInventory = currentInventory;
		this.oldInventory = oldInventory;
		this.reorderPoint = reorderPoint;
		this.reorderAmount = reorderAmount;
		this.supplierId = supplierId;
		this.supplierCode = supplierCode;
		this.supplierName = supplierName;
		this.supplyPriceExclTax = supplyPriceExclTax;
		this.markupPrct = markupPrct;
		this.retailPriceExclTax = retailPriceExclTax;
		this.sku = sku;
		this.dailyRegesterId = dailyRegesterId;
		this.isHeadOffice = isHeadOffice;
		this.isDuplicateBarCode = isDuplicateBarCode;
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
	 * @return the defaultTax
	 */
	public String getDefaultTax() {
		return defaultTax;
	}
	/**
	 * @param defaultTax the defaultTax to set
	 */
	public void setDefaultTax(String defaultTax) {
		this.defaultTax = defaultTax;
	}
	/**
	 * @return the defaultTaxId
	 */
	public String getDefaultTaxId() {
		return defaultTaxId;
	}
	/**
	 * @param defaultTaxId the defaultTaxId to set
	 */
	public void setDefaultTaxId(String defaultTaxId) {
		this.defaultTaxId = defaultTaxId;
	}
	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * @return the orderNumberPrefix
	 */
	public String getOrderNumberPrefix() {
		return orderNumberPrefix;
	}
	/**
	 * @param orderNumberPrefix the orderNumberPrefix to set
	 */
	public void setOrderNumberPrefix(String orderNumberPrefix) {
		this.orderNumberPrefix = orderNumberPrefix;
	}
	/**
	 * @return the supplieNumberPrefix
	 */
	public String getSupplieNumberPrefix() {
		return supplieNumberPrefix;
	}
	/**
	 * @param supplieNumberPrefix the supplieNumberPrefix to set
	 */
	public void setSupplieNumberPrefix(String supplieNumberPrefix) {
		this.supplieNumberPrefix = supplieNumberPrefix;
	}
	/**
	 * @return the supplierReturnNumber
	 */
	public String getSupplierReturnNumber() {
		return supplierReturnNumber;
	}
	/**
	 * @param supplierReturnNumber the supplierReturnNumber to set
	 */
	public void setSupplierReturnNumber(String supplierReturnNumber) {
		this.supplierReturnNumber = supplierReturnNumber;
	}
	/**
	 * @return the addressbean
	 */
	public AddressBean getAddressbean() {
		return addressbean;
	}
	/**
	 * @param addressbean the addressbean to set
	 */
	public void setAddressbean(AddressBean addressbean) {
		this.addressbean = addressbean;
	}
	/**
	 * @return the registerBeanList
	 */
	public List<RegisterBean> getRegisterBeanList() {
		return registerBeanList;
	}
	/**
	 * @param registerBeanList the registerBeanList to set
	 */
	public void setRegisterBeanList(List<RegisterBean> registerBeanList) {
		this.registerBeanList = registerBeanList;
	}
	/**
	 * @return the registers
	 */
	public String getRegisters() {
		return registers;
	}
	/**
	 * @param registers the registers to set
	 */
	public void setRegisters(String registers) {
		this.registers = registers;
	}
	/**
	 * @return the registerId
	 */
	public String getRegisterId() {
		return registerId;
	}
	/**
	 * @param registerId the registerId to set
	 */
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
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
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}
	/**
	 * @param details the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
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
	 * @return the salesTaxName
	 */
	public String getSalesTaxName() {
		return salesTaxName;
	}
	/**
	 * @param salesTaxName the salesTaxName to set
	 */
	public void setSalesTaxName(String salesTaxName) {
		this.salesTaxName = salesTaxName;
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
	 * @return the isHeadOffice
	 */
	public String getIsHeadOffice() {
		return isHeadOffice;
	}
	/**
	 * @param isHeadOffice the isHeadOffice to set
	 */
	public void setIsHeadOffice(String isHeadOffice) {
		this.isHeadOffice = isHeadOffice;
	}
	/**
	 * @return the isDuplicateBarCode
	 */
	public String getIsDuplicateBarCode() {
		return isDuplicateBarCode;
	}
	/**
	 * @param isDuplicateBarCode the isDuplicateBarCode to set
	 */
	public void setIsDuplicateBarCode(String isDuplicateBarCode) {
		this.isDuplicateBarCode = isDuplicateBarCode;
	}
}
