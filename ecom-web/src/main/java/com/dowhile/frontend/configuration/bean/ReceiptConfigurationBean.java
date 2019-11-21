/**
 * 
 */
package com.dowhile.frontend.configuration.bean;

/**
 * @author HafizYameenBashir
 *
 */
public class ReceiptConfigurationBean {

	private boolean starndardReceipt;
	private boolean xpressionsReceipt;
	
	/**
	 * 
	 */
	public ReceiptConfigurationBean() {
	}
	/**
	 * @param starndardReceipt
	 * @param xpressionsReceipt
	 */
	public ReceiptConfigurationBean(boolean starndardReceipt,
			boolean xpressionsReceipt) {
		this.starndardReceipt = starndardReceipt;
		this.xpressionsReceipt = xpressionsReceipt;
	}
	public boolean isStarndardReceipt() {
		return starndardReceipt;
	}
	public void setStarndardReceipt(boolean starndardReceipt) {
		this.starndardReceipt = starndardReceipt;
	}
	public boolean isXpressionsReceipt() {
		return xpressionsReceipt;
	}
	public void setXpressionsReceipt(boolean xpressionsReceipt) {
		this.xpressionsReceipt = xpressionsReceipt;
	}
}
