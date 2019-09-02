/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

import java.util.List;


/**
 * @author Yameen Bashir
 *
 */
public class PriceBookBean {
	
	private String priceBookId;
	private String companyId;
	private String companyName;
	private String contactGroupId;
	private String contactGroupName;
	private String outletId;
	private String outletName;
	private String priceBookName;
	private String validFrom;
	private String validTo;
	private String isValidOnStore;
	private String isValidOnEcom;
	private String createdDate;
	private String flatSale;
    private String flatDiscount; 
    private String active;
	private List<PriceBookDetailBean> PriceBookDetailBeans;
	/**
	 * 
	 */
	public PriceBookBean() {
	}
	/**
	 * @param priceBookId
	 * @param companyId
	 * @param companyName
	 * @param contactGroupId
	 * @param contactGroupName
	 * @param outletId
	 * @param outletName
	 * @param priceBookName
	 * @param validFrom
	 * @param validTo
	 * @param isValidOnStore
	 * @param isValidOnEcom
	 * @param createdDate
	 * @param flatSale
	 * @param flatDiscount
	 * @param active
	 * @param priceBookDetailBeans
	 */
	public PriceBookBean(String priceBookId, String companyId,
			String companyName, String contactGroupId, String contactGroupName,
			String outletId, String outletName, String priceBookName,
			String validFrom, String validTo, String isValidOnStore,
			String isValidOnEcom, String createdDate, String flatSale,
			String flatDiscount, String active,
			List<PriceBookDetailBean> priceBookDetailBeans) {
		this.priceBookId = priceBookId;
		this.companyId = companyId;
		this.companyName = companyName;
		this.contactGroupId = contactGroupId;
		this.contactGroupName = contactGroupName;
		this.outletId = outletId;
		this.outletName = outletName;
		this.priceBookName = priceBookName;
		this.validFrom = validFrom;
		this.validTo = validTo;
		this.isValidOnStore = isValidOnStore;
		this.isValidOnEcom = isValidOnEcom;
		this.createdDate = createdDate;
		this.flatSale = flatSale;
		this.flatDiscount = flatDiscount;
		this.active = active;
		PriceBookDetailBeans = priceBookDetailBeans;
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
	 * @return the companyId
	 */
	public String getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
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
	 * @return the contactGroupId
	 */
	public String getContactGroupId() {
		return contactGroupId;
	}
	/**
	 * @param contactGroupId the contactGroupId to set
	 */
	public void setContactGroupId(String contactGroupId) {
		this.contactGroupId = contactGroupId;
	}
	/**
	 * @return the contactGroupName
	 */
	public String getContactGroupName() {
		return contactGroupName;
	}
	/**
	 * @param contactGroupName the contactGroupName to set
	 */
	public void setContactGroupName(String contactGroupName) {
		this.contactGroupName = contactGroupName;
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
	 * @return the priceBookName
	 */
	public String getPriceBookName() {
		return priceBookName;
	}
	/**
	 * @param priceBookName the priceBookName to set
	 */
	public void setPriceBookName(String priceBookName) {
		this.priceBookName = priceBookName;
	}
	/**
	 * @return the validFrom
	 */
	public String getValidFrom() {
		return validFrom;
	}
	/**
	 * @param validFrom the validFrom to set
	 */
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	/**
	 * @return the validTo
	 */
	public String getValidTo() {
		return validTo;
	}
	/**
	 * @param validTo the validTo to set
	 */
	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}
	/**
	 * @return the isValidOnStore
	 */
	public String getIsValidOnStore() {
		return isValidOnStore;
	}
	/**
	 * @param isValidOnStore the isValidOnStore to set
	 */
	public void setIsValidOnStore(String isValidOnStore) {
		this.isValidOnStore = isValidOnStore;
	}
	/**
	 * @return the isValidOnEcom
	 */
	public String getIsValidOnEcom() {
		return isValidOnEcom;
	}
	/**
	 * @param isValidOnEcom the isValidOnEcom to set
	 */
	public void setIsValidOnEcom(String isValidOnEcom) {
		this.isValidOnEcom = isValidOnEcom;
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
	 * @return the flatSale
	 */
	public String getFlatSale() {
		return flatSale;
	}
	/**
	 * @param flatSale the flatSale to set
	 */
	public void setFlatSale(String flatSale) {
		this.flatSale = flatSale;
	}
	/**
	 * @return the flatDiscount
	 */
	public String getFlatDiscount() {
		return flatDiscount;
	}
	/**
	 * @param flatDiscount the flatDiscount to set
	 */
	public void setFlatDiscount(String flatDiscount) {
		this.flatDiscount = flatDiscount;
	}
	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}
	/**
	 * @return the priceBookDetailBeans
	 */
	public List<PriceBookDetailBean> getPriceBookDetailBeans() {
		return PriceBookDetailBeans;
	}
	/**
	 * @param priceBookDetailBeans the priceBookDetailBeans to set
	 */
	public void setPriceBookDetailBeans(
			List<PriceBookDetailBean> priceBookDetailBeans) {
		PriceBookDetailBeans = priceBookDetailBeans;
	}
}
