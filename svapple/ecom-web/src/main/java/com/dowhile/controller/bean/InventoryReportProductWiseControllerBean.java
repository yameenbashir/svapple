/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.ProductBean;

/**
 * @author Hafiz Yameen Bashir
 *
 */
public class InventoryReportProductWiseControllerBean {

	private List<ProductBean> productBeanList;
	private List<OutletBean> outletBeans;
	/**
	 * 
	 */
	public InventoryReportProductWiseControllerBean() {
	}
	/**
	 * @param productBeanList
	 * @param outletBeans
	 */
	public InventoryReportProductWiseControllerBean(
			List<ProductBean> productBeanList, List<OutletBean> outletBeans) {
		this.productBeanList = productBeanList;
		this.outletBeans = outletBeans;
	}
	/**
	 * @return the productBeanList
	 */
	public List<ProductBean> getProductBeanList() {
		return productBeanList;
	}
	/**
	 * @param productBeanList the productBeanList to set
	 */
	public void setProductBeanList(List<ProductBean> productBeanList) {
		this.productBeanList = productBeanList;
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
}
