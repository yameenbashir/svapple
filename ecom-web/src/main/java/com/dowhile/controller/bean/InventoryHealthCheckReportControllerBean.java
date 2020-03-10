package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.beans.TableData;
import com.dowhile.frontend.mapping.bean.InventoryHealthCheckReportBean;
import com.dowhile.frontend.mapping.bean.OutletBean;

/**
 * @author Ali
 *
 */
public class InventoryHealthCheckReportControllerBean {

	private List<InventoryHealthCheckReportBean>  inventoryHealthCheckReportBeansList;
	private List<OutletBean> outletBeans;
	private boolean hideSalesDetails;
	private TableData tableData;
	/**
	 * 
	 */
	public InventoryHealthCheckReportControllerBean() {
	}
	/**
	 * @param inventoryHealthCheckReportBeansList
	 * @param outletBeans
	 */
	public InventoryHealthCheckReportControllerBean(
			List<InventoryHealthCheckReportBean> inventoryHealthCheckReportBeansList,
			List<OutletBean> outletBeans) {
		this.inventoryHealthCheckReportBeansList = inventoryHealthCheckReportBeansList;
		this.outletBeans = outletBeans;
	}
	/**
	 * @return the inventoryHealthCheckReportBeansList
	 */
	public List<InventoryHealthCheckReportBean> getInventoryHealthCheckReportBeansList() {
		return inventoryHealthCheckReportBeansList;
	}
	/**
	 * @param inventoryReportBeansList the inventoryReportBeansList to set
	 */
	public void setInventoryHealthCheckReportBeansList(
			List<InventoryHealthCheckReportBean> inventoryHealthCheckReportBeansList) {
		this.inventoryHealthCheckReportBeansList = inventoryHealthCheckReportBeansList;
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
	public boolean getHideSalesDetails() {
		return hideSalesDetails;
	}
	public void setHideSalesDetails(boolean hideSalesDetails) {
		this.hideSalesDetails = hideSalesDetails;
	}
	/**
	 * @return the tableData
	 */
	public TableData getTableData() {
		return tableData;
	}
	/**
	 * @param tableData the tableData to set
	 */
	public void setTableData(TableData tableData) {
		this.tableData = tableData;
	}
}
