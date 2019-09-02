/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.SalesTaxBean;

/**
 * @author Yameen Bashir
 *
 */
public class SalesTaxControllerBean {

	private List<OutletBean> outletBeans ;
	private List<SalesTaxBean> salesTaxListBeans;
	/**
	 * 
	 */
	public SalesTaxControllerBean() {
	}
	/**
	 * @param outletBeans
	 * @param salesTaxListBeans
	 */
	public SalesTaxControllerBean(List<OutletBean> outletBeans,
			List<SalesTaxBean> salesTaxListBeans) {
		this.outletBeans = outletBeans;
		this.salesTaxListBeans = salesTaxListBeans;
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
	 * @return the salesTaxListBeans
	 */
	public List<SalesTaxBean> getSalesTaxListBeans() {
		return salesTaxListBeans;
	}
	/**
	 * @param salesTaxListBeans the salesTaxListBeans to set
	 */
	public void setSalesTaxListBeans(List<SalesTaxBean> salesTaxListBeans) {
		this.salesTaxListBeans = salesTaxListBeans;
	}
}
