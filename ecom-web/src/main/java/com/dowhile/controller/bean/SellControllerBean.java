package com.dowhile.controller.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dowhile.frontend.configuration.bean.ReceiptConfigurationBean;
import com.dowhile.frontend.mapping.bean.CustomerBean;
import com.dowhile.frontend.mapping.bean.CustomerGroupBean;
import com.dowhile.frontend.mapping.bean.PriceBookBean;
import com.dowhile.frontend.mapping.bean.ProductBean;
import com.dowhile.frontend.mapping.bean.UserBean;

public class SellControllerBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3410565879523218586L;
	public List<PriceBookBean> getPriceBookBean() {
		return priceBookBean;
	}
	public void setPriceBookBean(List<PriceBookBean> priceBookBean) {
		this.priceBookBean = priceBookBean;
	}
	List<ProductBean> productsBean = new ArrayList<>();
	List<ProductBean> displayProductsBean = new ArrayList<>();
	List<CustomerBean> customersBean = new ArrayList<>();
	List<CustomerGroupBean> customerGroupBeansList = new ArrayList<>();
	List<UserBean> users = new ArrayList<>();
	List<PriceBookBean> priceBookBean = new ArrayList<>();
	private String registerStatus ;
	private String dailyRegisterId ;
	private String companyName ;
	private String companyAddress ;
	private String outletAddress ;
	private String phoneNumber ;
	private String isReturnEnableonSell = "false"; 
	private String isInvoiceLevelDiscountEnable = "true";
	private String isInvoiceDetailLevelDiscountEnable = "true";
	private String isInvoiceTaxAmountEnable = "true";
	private String companyImagePath;
	private String autoCreateStandardVariant;
	private String defaultVariantName;
	private String termsAndConditions;
	private ReceiptConfigurationBean receiptConfigurationBean;
	public List<ProductBean> getProductsBean() {
		return productsBean;
	}
	public void setProductsBean(List<ProductBean> productsBean) {
		this.productsBean = productsBean;
	}
	public List<CustomerBean> getCustomersBean() {
		return customersBean;
	}
	public void setCustomersBean(List<CustomerBean> customersBean) {
		this.customersBean = customersBean;
	}
	public String getRegisterStatus() {
		return registerStatus;
	}
	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}
	public String getDailyRegisterId() {
		return dailyRegisterId;
	}
	public void setDailyRegisterId(String dailyRegisterId) {
		this.dailyRegisterId = dailyRegisterId;
	}
	public List<ProductBean> getDisplayProductsBean() {
		return displayProductsBean;
	}
	public void setDisplayProductsBean(List<ProductBean> displayProductsBean) {
		this.displayProductsBean = displayProductsBean;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getOutletAddress() {
		return outletAddress;
	}
	public void setOutletAddress(String outletAddress) {
		this.outletAddress = outletAddress;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public List<UserBean> getUsers() {
		return users;
	}
	public void setUsers(List<UserBean> users) {
		this.users = users;
	}
	public String getIsReturnEnableonSell() {
		return isReturnEnableonSell;
	}
	public void setIsReturnEnableonSell(String isReturnEnableonSell) {
		this.isReturnEnableonSell = isReturnEnableonSell;
	}
	/**
	 * @return the customerGroupBeansList
	 */
	public List<CustomerGroupBean> getCustomerGroupBeansList() {
		return customerGroupBeansList;
	}
	/**
	 * @param customerGroupBeansList the customerGroupBeansList to set
	 */
	public void setCustomerGroupBeansList(
			List<CustomerGroupBean> customerGroupBeansList) {
		this.customerGroupBeansList = customerGroupBeansList;
	}
	public String getIsInvoiceLevelDiscountEnable() {
		return isInvoiceLevelDiscountEnable;
	}
	public void setIsInvoiceLevelDiscountEnable(
			String isInvoiceLevelDiscountEnable) {
		this.isInvoiceLevelDiscountEnable = isInvoiceLevelDiscountEnable;
	}
	/**
	 * @return the companyImagePath
	 */
	public String getCompanyImagePath() {
		return companyImagePath;
	}
	/**
	 * @param companyImagePath the companyImagePath to set
	 */
	public void setCompanyImagePath(String companyImagePath) {
		this.companyImagePath = companyImagePath;
	}
	public String getAutoCreateStandardVariant() {
		return autoCreateStandardVariant;
	}
	public void setAutoCreateStandardVariant(String autoCreateStandardVariant) {
		this.autoCreateStandardVariant = autoCreateStandardVariant;
	}
	public String getDefaultVariantName() {
		return defaultVariantName;
	}
	public void setDefaultVariantName(String defaultVariantName) {
		this.defaultVariantName = defaultVariantName;
	}
	public String getTermsAndConditions() {
		return termsAndConditions;
	}
	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}
	/**
	 * @return the isInvoiceDetailLevelDiscountEnable
	 */
	public String getIsInvoiceDetailLevelDiscountEnable() {
		return isInvoiceDetailLevelDiscountEnable;
	}
	/**
	 * @param isInvoiceDetailLevelDiscountEnable the isInvoiceDetailLevelDiscountEnable to set
	 */
	public void setIsInvoiceDetailLevelDiscountEnable(
			String isInvoiceDetailLevelDiscountEnable) {
		this.isInvoiceDetailLevelDiscountEnable = isInvoiceDetailLevelDiscountEnable;
	}
	/**
	 * @return the receiptConfigurationBean
	 */
	public ReceiptConfigurationBean getReceiptConfigurationBean() {
		return receiptConfigurationBean;
	}
	/**
	 * @param receiptConfigurationBean the receiptConfigurationBean to set
	 */
	public void setReceiptConfigurationBean(
			ReceiptConfigurationBean receiptConfigurationBean) {
		this.receiptConfigurationBean = receiptConfigurationBean;
	}
	/**
	 * @return the isInvoiceTaxAmountEnable
	 */
	public String getIsInvoiceTaxAmountEnable() {
		return isInvoiceTaxAmountEnable;
	}
	/**
	 * @param isInvoiceTaxAmountEnable the isInvoiceTaxAmountEnable to set
	 */
	public void setIsInvoiceTaxAmountEnable(String isInvoiceTaxAmountEnable) {
		this.isInvoiceTaxAmountEnable = isInvoiceTaxAmountEnable;
	}
	

}
