/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.RoleBean;

/**
 * @author Yameen Bashir
 *
 */
public class NewUserControllerBean {
	
	private List<RoleBean> rolesBeanList ;
	private List<OutletBean> outletBeans ;
	private String companyNameForEmail;
	/**
	 * 
	 */
	public NewUserControllerBean() {
	}
	/**
	 * @param rolesBeanList
	 * @param outletBeans
	 * @param companyNameForEmail
	 */
	public NewUserControllerBean(List<RoleBean> rolesBeanList,
			List<OutletBean> outletBeans, String companyNameForEmail) {
		this.rolesBeanList = rolesBeanList;
		this.outletBeans = outletBeans;
		this.companyNameForEmail = companyNameForEmail;
	}
	
	/**
	 * @return the rolesBeanList
	 */
	public List<RoleBean> getRolesBeanList() {
		return rolesBeanList;
	}
	/**
	 * @param rolesBeanList the rolesBeanList to set
	 */
	public void setRolesBeanList(List<RoleBean> rolesBeanList) {
		this.rolesBeanList = rolesBeanList;
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
	 * @return the companyNameForEmail
	 */
	public String getCompanyNameForEmail() {
		return companyNameForEmail;
	}
	/**
	 * @param companyNameForEmail the companyNameForEmail to set
	 */
	public void setCompanyNameForEmail(String companyNameForEmail) {
		this.companyNameForEmail = companyNameForEmail;
	}
	
}
