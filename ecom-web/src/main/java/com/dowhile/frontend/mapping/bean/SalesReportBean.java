/**
 * 
 */
package com.dowhile.frontend.mapping.bean;


/**
 * @author Yameen Bashir
 *
 */
public class SalesReportBean {

	private String productName;
	private String createdDate;
	private String revenue;
	private String orignal;
	private String profit;
	private String margin;
	/**
	 * 
	 */
	public SalesReportBean() {
	}
	/**
	 * @param productName
	 * @param createdDate
	 * @param revenue
	 * @param orignal
	 * @param profit
	 * @param margin
	 */
	public SalesReportBean(String productName, String createdDate,
			String revenue, String orignal, String profit, String margin) {
		this.productName = productName;
		this.createdDate = createdDate;
		this.revenue = revenue;
		this.orignal = orignal;
		this.profit = profit;
		this.margin = margin;
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
	 * @return the revenue
	 */
	public String getRevenue() {
		return revenue;
	}
	/**
	 * @param revenue the revenue to set
	 */
	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}
	/**
	 * @return the orignal
	 */
	public String getOrignal() {
		return orignal;
	}
	/**
	 * @param orignal the orignal to set
	 */
	public void setOrignal(String orignal) {
		this.orignal = orignal;
	}
	/**
	 * @return the profit
	 */
	public String getProfit() {
		return profit;
	}
	/**
	 * @param profit the profit to set
	 */
	public void setProfit(String profit) {
		this.profit = profit;
	}
	/**
	 * @return the margin
	 */
	public String getMargin() {
		return margin;
	}
	/**
	 * @param margin the margin to set
	 */
	public void setMargin(String margin) {
		this.margin = margin;
	}
}
