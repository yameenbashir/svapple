/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.beans.TableData;
import com.dowhile.frontend.mapping.bean.InventoryReportBean;
import com.dowhile.frontend.mapping.bean.OutletBean;

/**
 * @author Yameen Bashir
 *
 */
public class InventoryReportControllerBean {

	private List<InventoryReportBean>  inventoryReportBeansList;
	private List<OutletBean> outletBeans;
	private boolean hideSalesDetails;
	private TableData tableData;
	/**
	 * 
	 */
	public InventoryReportControllerBean() {
	}
	/**
	 * @param inventoryReportBeansList
	 * @param outletBeans
	 */
	public InventoryReportControllerBean(
			List<InventoryReportBean> inventoryReportBeansList,
			List<OutletBean> outletBeans) {
		this.inventoryReportBeansList = inventoryReportBeansList;
		this.outletBeans = outletBeans;
	}
	/**
	 * @return the inventoryReportBeansList
	 */
	public List<InventoryReportBean> getInventoryReportBeansList() {
		return inventoryReportBeansList;
	}
	/**
	 * @param inventoryReportBeansList the inventoryReportBeansList to set
	 */
	public void setInventoryReportBeansList(
			List<InventoryReportBean> inventoryReportBeansList) {
		this.inventoryReportBeansList = inventoryReportBeansList;
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
