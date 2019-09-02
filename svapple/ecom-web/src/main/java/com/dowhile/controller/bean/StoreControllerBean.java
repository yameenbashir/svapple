/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.angualrspringapp.beans.CurrencyBean;
import com.dowhile.frontend.mapping.bean.CompanyBean;
import com.dowhile.frontend.mapping.bean.PrinterFormatBean;
import com.dowhile.frontend.mapping.bean.TimeZoneBean;

/**
 * @author Yameen Bashir
 *
 */
public class StoreControllerBean {

	public List<TimeZoneBean> getTimeZoneBeans() {
		return timeZoneBeans;
	}
	public void setTimeZoneBeans(List<TimeZoneBean> timeZoneBeans) {
		this.timeZoneBeans = timeZoneBeans;
	}
	public List<CurrencyBean> getCurrencyBeans() {
		return currencyBeans;
	}
	public void setCurrencyBeans(List<CurrencyBean> currencyBeans) {
		this.currencyBeans = currencyBeans;
	}
	public List<PrinterFormatBean> getPrinterFormatBeans() {
		return printerFormatBeans;
	}
	public void setPrinterFormatBeans(List<PrinterFormatBean> printerFormatBeans) {
		this.printerFormatBeans = printerFormatBeans;
	}
	public CompanyBean getCompanyBean() {
		return companyBean;
	}
	public void setCompanyBean(CompanyBean companyBean) {
		this.companyBean = companyBean;
	}
	private List<TimeZoneBean> timeZoneBeans;
	private List<CurrencyBean> currencyBeans;
	private List<PrinterFormatBean> printerFormatBeans;
	private CompanyBean companyBean;
	/**
	 * 
	 */
	public StoreControllerBean() {
	}
	/**
	 * @param timeZoneBeans
	 * @param currencyBeans
	 * @param printerFormatBeans
	 * @param companyBean
	 */
	public StoreControllerBean(List<TimeZoneBean> timeZoneBeans,
			List<CurrencyBean> currencyBeans,
			List<PrinterFormatBean> printerFormatBeans, CompanyBean companyBean) {
		this.timeZoneBeans = timeZoneBeans;
		this.currencyBeans = currencyBeans;
		this.printerFormatBeans = printerFormatBeans;
		this.companyBean = companyBean;
	}
}
