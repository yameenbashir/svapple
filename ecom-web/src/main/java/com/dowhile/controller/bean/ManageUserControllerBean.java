/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.RoleBean;
import com.dowhile.frontend.mapping.bean.UserBean;

/**
 * @author Yameen Bashir
 *
 */
public class ManageUserControllerBean {

	private List<RoleBean> rolesBeanList ;
	private List<OutletBean> outletBeans ;
	private UserBean userBean ;
	/**
	 * 
	 */
	public ManageUserControllerBean() {
	}
	/**
	 * @param rolesBeanList
	 * @param outletBeans
	 * @param userBean
	 */
	public ManageUserControllerBean(List<RoleBean> rolesBeanList,
			List<OutletBean> outletBeans, UserBean userBean) {
		this.rolesBeanList = rolesBeanList;
		this.outletBeans = outletBeans;
		this.userBean = userBean;
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
	 * @return the userBean
	 */
	public UserBean getUserBean() {
		return userBean;
	}
	/**
	 * @param userBean the userBean to set
	 */
	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
}
