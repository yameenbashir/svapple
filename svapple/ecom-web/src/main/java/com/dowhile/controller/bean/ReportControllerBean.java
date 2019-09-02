/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.beans.TableData;
import com.dowhile.frontend.mapping.bean.OutletBean;

/**
 * @author Yameen Bashir
 *
 */
public class ReportControllerBean {

	private List<OutletBean> outletBeans;
	private TableData tableData;
	private boolean hideSalesDetails;
	/**
	 * 
	 */
	public ReportControllerBean() {
	}
	/**
	 * @param outletBeans
	 * @param tableData
	 */
	public ReportControllerBean(List<OutletBean> outletBeans,
			TableData tableData) {
		this.outletBeans = outletBeans;
		this.tableData = tableData;
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
	public boolean getHideSalesDetails() {
		return hideSalesDetails;
	}
	public void setHideSalesDetails(boolean hideSalesDetails) {
		this.hideSalesDetails = hideSalesDetails;
	}
}
