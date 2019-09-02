/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.frontend.mapping.bean.CountryBean;
import com.dowhile.frontend.mapping.bean.SalesTaxBean;
import com.dowhile.frontend.mapping.bean.TimeZoneBean;

/**
 * @author Yameen Bashir
 *
 */
public class OutletControllerBean {

	public List<TimeZoneBean> getTimeZoneBeans() {
		return timeZoneBeans;
	}
	public void setTimeZoneBeans(List<TimeZoneBean> timeZoneBeans) {
		this.timeZoneBeans = timeZoneBeans;
	}
	public List<CountryBean> getCountryListBeans() {
		return countryListBeans;
	}
	public void setCountryListBeans(List<CountryBean> countryListBeans) {
		this.countryListBeans = countryListBeans;
	}
	public List<SalesTaxBean> getSalesTaxListBeans() {
		return salesTaxListBeans;
	}
	public void setSalesTaxListBeans(List<SalesTaxBean> salesTaxListBeans) {
		this.salesTaxListBeans = salesTaxListBeans;
	}
	private List<TimeZoneBean> timeZoneBeans ;
	private List<CountryBean> countryListBeans;
	private List<SalesTaxBean> salesTaxListBeans ;
	/**
	 * 
	 */
	public OutletControllerBean() {
	}
	/**
	 * @param timeZoneBeans
	 * @param countryListBeans
	 * @param salesTaxListBeans
	 */
	public OutletControllerBean(List<TimeZoneBean> timeZoneBeans,
			List<CountryBean> countryListBeans,
			List<SalesTaxBean> salesTaxListBeans) {
		this.timeZoneBeans = timeZoneBeans;
		this.countryListBeans = countryListBeans;
		this.salesTaxListBeans = salesTaxListBeans;
	}
}
