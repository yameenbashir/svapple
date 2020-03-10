package com.dowhile.frontend.mapping.bean;

import java.util.List;

public class InventoryHealthCheckReportCustomBean {
private List<InventoryHealthCheckReportBean> inventoryHealthCheckReportBeansList;
private String totalSt;
private String totalRtw;
private String totalExpCurrentInventory;
private String totalConflictedQuantity;	
private String totalCurrentInventory;
private String totalSold;
private String totalSaleReturn;
/**
 * @return the inventoryHealthCheckReportBeansList
 */
public List<InventoryHealthCheckReportBean> getInventoryHealthCheckReportBeansList() {
	return inventoryHealthCheckReportBeansList;
}

/**
 * @param inventoryHealthCheckReportBeansList the inventoryHealthCheckReportBeansList to set
 */
public void setInventoryHealthCheckReportBeansList(List<InventoryHealthCheckReportBean> inventoryHealthCheckReportBeansList) {
	this.inventoryHealthCheckReportBeansList = inventoryHealthCheckReportBeansList;
}

/**
 * @return the totalRtw
 */
public String getTotalRtw() {
	return totalRtw;
}

/**
 * @param totalRtw the totalRtw to set
 */
public void setTotalRtw(String totalRtw) {
	this.totalRtw = totalRtw;
}

/**
 * @return the totalSt
 */
public String getTotalSt() {
	return totalSt;
}

/**
 * @param totalSt the totalSt to set
 */
public void setTotalSt(String totalSt) {
	this.totalSt = totalSt;
}

/**
 * @return the totalExpCurrentInventory
 */
public String getTotalExpCurrentInventory() {
	return totalExpCurrentInventory;
}

/**
 * @param totalExpCurrentInventory the totalExpCurrentInventory to set
 */
public void setTotalExpCurrentInventory(String totalExpCurrentInventory) {
	this.totalExpCurrentInventory = totalExpCurrentInventory;
}

/**
 * @return the totalConflictedQuantity
 */
public String getTotalConflictedQuantity() {
	return totalConflictedQuantity;
}

/**
 * @param totalConflictedQuantity the totalConflictedQuantity to set
 */
public void setTotalConflictedQuantity(String totalConflictedQuantity) {
	this.totalConflictedQuantity = totalConflictedQuantity;
}

/**
 * @return the totalCurrentInventory
 */
public String getTotalCurrentInventory() {
	return totalCurrentInventory;
}

/**
 * @param totalCurrentInventory the totalCurrentInventory to set
 */
public void setTotalCurrentInventory(String totalCurrentInventory) {
	this.totalCurrentInventory = totalCurrentInventory;
}



/**
 * @return the totalSold
 */
public String getTotalSold() {
	return totalSold;
}

/**
 * @param totalSold the totalSold to set
 */
public void setTotalSold(String totalSold) {
	this.totalSold = totalSold;
}

/**
 * @return the totalSaleReturn
 */
public String getTotalSaleReturn() {
	return totalSaleReturn;
}

/**
 * @param totalSaleReturn the totalSaleReturn to set
 */
public void setTotalSaleReturn(String totalSaleReturn) {
	this.totalSaleReturn = totalSaleReturn;
}
}
