/**
 * 
 */
package com.dowhile.frontend.mapping.bean;


/**
 * @author Yameen Bashir
 *
 */
public class ProductHistoryBean {
	
	private String productHistoryId;
	private String userName;
	private String productVariantName;
	private String productName;
	private String outletName;
	private String actionDate;
	private String quantity;
	private String changeQuantity;
	private String outletQuantity;
	private String action;
	/**
	 * 
	 */
	public ProductHistoryBean() {
	}
	/**
	 * @param productHistoryId
	 * @param userName
	 * @param productVariantName
	 * @param productName
	 * @param outletName
	 * @param actionDate
	 * @param quantity
	 * @param changeQuantity
	 * @param outletQuantity
	 * @param action
	 */
	public ProductHistoryBean(String productHistoryId, String userName,
			String productVariantName, String productName, String outletName,
			String actionDate, String quantity, String changeQuantity,
			String outletQuantity, String action) {
		this.productHistoryId = productHistoryId;
		this.userName = userName;
		this.productVariantName = productVariantName;
		this.productName = productName;
		this.outletName = outletName;
		this.actionDate = actionDate;
		this.quantity = quantity;
		this.changeQuantity = changeQuantity;
		this.outletQuantity = outletQuantity;
		this.action = action;
	}
	/**
	 * @return the productHistoryId
	 */
	public String getProductHistoryId() {
		return productHistoryId;
	}
	/**
	 * @param productHistoryId the productHistoryId to set
	 */
	public void setProductHistoryId(String productHistoryId) {
		this.productHistoryId = productHistoryId;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the productVariantName
	 */
	public String getProductVariantName() {
		return productVariantName;
	}
	/**
	 * @param productVariantName the productVariantName to set
	 */
	public void setProductVariantName(String productVariantName) {
		this.productVariantName = productVariantName;
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
	 * @return the actionDate
	 */
	public String getActionDate() {
		return actionDate;
	}
	/**
	 * @param actionDate the actionDate to set
	 */
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}
	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}
	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	/**
	 * @return the changeQuantity
	 */
	public String getChangeQuantity() {
		return changeQuantity;
	}
	/**
	 * @param changeQuantity the changeQuantity to set
	 */
	public void setChangeQuantity(String changeQuantity) {
		this.changeQuantity = changeQuantity;
	}
	/**
	 * @return the outletQuantity
	 */
	public String getOutletQuantity() {
		return outletQuantity;
	}
	/**
	 * @param outletQuantity the outletQuantity to set
	 */
	public void setOutletQuantity(String outletQuantity) {
		this.outletQuantity = outletQuantity;
	}
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
}
