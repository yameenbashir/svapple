/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

import java.util.List;

/**
 * @author T430s
 *
 */
public class SupplierBean {

	private String supplierId;
	private String supplierName;
	private String defaultMarkup;
	private String description;
	private String companyName;
	private String supplierBalance;
	private String activeIndicator;
	private String createdDate;
	private String lastUpdated;
	private String createdBy;
	private String updatedBy;
	private String productCount;
	private List<AddressBean> addresses;
	public SupplierBean() {
	}
	public SupplierBean(String supplierId, String supplierName,
			String defaultMarkup, String description, String companyName,
			String activeIndicator, String createdDate, String lastUpdated,
			String createdBy, String updatedBy, List<AddressBean> addresses) {
		this.supplierId = supplierId;
		this.supplierName = supplierName;
		this.defaultMarkup = defaultMarkup;
		this.description = description;
		this.companyName = companyName;
		this.activeIndicator = activeIndicator;
		this.createdDate = createdDate;
		this.lastUpdated = lastUpdated;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
		this.addresses = addresses;
	}
	public String getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getDefaultMarkup() {
		return defaultMarkup;
	}
	public void setDefaultMarkup(String defaultMarkup) {
		this.defaultMarkup = defaultMarkup;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	public List<AddressBean> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<AddressBean> addresses) {
		this.addresses = addresses;
	}
	/**
	 * @return the productCount
	 */
	public String getProductCount() {
		return productCount;
	}
	/**
	 * @param productCount the productCount to set
	 */
	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}
	/**
	 * @return the supplierBalance
	 */
	public String getSupplierBalance() {
		return supplierBalance;
	}
	/**
	 * @param supplierBalance the supplierBalance to set
	 */
	public void setSupplierBalance(String supplierBalance) {
		this.supplierBalance = supplierBalance;
	}
	
}
