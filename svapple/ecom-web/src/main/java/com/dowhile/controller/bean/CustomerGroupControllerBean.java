/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.frontend.mapping.bean.CountryBean;
import com.dowhile.frontend.mapping.bean.CustomerGroupBean;

/**
 * @author Yameen Bashir
 *
 */
public class CustomerGroupControllerBean {

	private List<CustomerGroupBean> customerGroupBeanList ;
	private List<CountryBean> countryListBeans ;
	/**
	 * 
	 */
	public CustomerGroupControllerBean() {
	}
	/**
	 * @param customerGroupBeanList
	 * @param countryListBeans
	 */
	public CustomerGroupControllerBean(
			List<CustomerGroupBean> customerGroupBeanList,
			List<CountryBean> countryListBeans) {
		this.customerGroupBeanList = customerGroupBeanList;
		this.countryListBeans = countryListBeans;
	}
	/**
	 * @return the customerGroupBeanList
	 */
	public List<CustomerGroupBean> getCustomerGroupBeanList() {
		return customerGroupBeanList;
	}
	/**
	 * @param customerGroupBeanList the customerGroupBeanList to set
	 */
	public void setCustomerGroupBeanList(
			List<CustomerGroupBean> customerGroupBeanList) {
		this.customerGroupBeanList = customerGroupBeanList;
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
}
