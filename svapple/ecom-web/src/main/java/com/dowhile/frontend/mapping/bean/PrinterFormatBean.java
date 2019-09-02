/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

import java.util.Date;

/**
 * @author T430s
 *
 */
public class PrinterFormatBean {
	
	
	public String getPrinterFormatId() {
		return printerFormatId;
	}
	public void setPrinterFormatId(String printerFormatId) {
		this.printerFormatId = printerFormatId;
	}
	public String getPrinterFormatValue() {
		return printerFormatValue;
	}
	public void setPrinterFormatValue(String printerFormatValue) {
		this.printerFormatValue = printerFormatValue;
	}
	public String getActiveIndicator() {
		return activeIndicator;
	}
	public void setActiveIndicator(String activeIndicator) {
		this.activeIndicator = activeIndicator;
	}
	private String printerFormatId;
	private String printerFormatValue;
	private String activeIndicator;

}
