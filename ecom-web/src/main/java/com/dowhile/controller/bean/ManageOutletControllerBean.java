/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.frontend.mapping.bean.CountryBean;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.SalesTaxBean;
import com.dowhile.frontend.mapping.bean.TimeZoneBean;

/**
 * @author Yameen Bashir
 *
 */
public class ManageOutletControllerBean {

	private List<TimeZoneBean> timeZoneBeans ;
	private List<CountryBean> countryListBeans;
	private List<SalesTaxBean> salesTaxListBeans ;
	private OutletBean outletBean ;
	/**
	 * 
	 */
	public ManageOutletControllerBean() {
	}
	/**
	 * @param timeZoneBeans
	 * @param countryListBeans
	 * @param salesTaxListBeans
	 * @param outletBean
	 */
	public ManageOutletControllerBean(List<TimeZoneBean> timeZoneBeans,
			List<CountryBean> countryListBeans,
			List<SalesTaxBean> salesTaxListBeans, OutletBean outletBean) {
		this.timeZoneBeans = timeZoneBeans;
		this.countryListBeans = countryListBeans;
		this.salesTaxListBeans = salesTaxListBeans;
		this.outletBean = outletBean;
	}
	/**
	 * @return the timeZoneBeans
	 */
	public List<TimeZoneBean> getTimeZoneBeans() {
		return timeZoneBeans;
	}
	/**
	 * @param timeZoneBeans the timeZoneBeans to set
	 */
	public void setTimeZoneBeans(List<TimeZoneBean> timeZoneBeans) {
		this.timeZoneBeans = timeZoneBeans;
	}
	/**
	 * @return the countryListBeans
	 */
	public List<CountryBean> getCountryListBeans() {
		return countryListBeans;
	}
	/**
	 * @param countryListBeans the countryListBeans to set
	 */
	public void setCountryListBeans(List<CountryBean> countryListBeans) {
		this.countryListBeans = countryListBeans;
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
	/**
	 * @return the outletBean
	 */
	public OutletBean getOutletBean() {
		return outletBean;
	}
	/**
	 * @param outletBean the outletBean to set
	 */
	public void setOutletBean(OutletBean outletBean) {
		this.outletBean = outletBean;
	}
}
