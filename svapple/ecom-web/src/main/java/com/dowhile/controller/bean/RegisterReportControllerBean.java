/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.RegisterReportBean;

/**
 * @author Yameen Bashir
 *
 */
public class RegisterReportControllerBean {
	
	private List<RegisterReportBean>  registerReportBeansList;
	private List<OutletBean> outletBeans;
	private boolean isHeadOffice;
	/**
	 * 
	 */
	public RegisterReportControllerBean() {
	}
	/**
	 * @param registerReportBeansList
	 * @param outletBeans
	 * @param isHeadOffice
	 */
	public RegisterReportControllerBean(
			List<RegisterReportBean> registerReportBeansList,
			List<OutletBean> outletBeans, boolean isHeadOffice) {
		this.registerReportBeansList = registerReportBeansList;
		this.outletBeans = outletBeans;
		this.isHeadOffice = isHeadOffice;
	}
	/**
	 * @return the registerReportBeansList
	 */
	public List<RegisterReportBean> getRegisterReportBeansList() {
		return registerReportBeansList;
	}
	/**
	 * @param registerReportBeansList the registerReportBeansList to set
	 */
	public void setRegisterReportBeansList(
			List<RegisterReportBean> registerReportBeansList) {
		this.registerReportBeansList = registerReportBeansList;
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
	 * @return the isHeadOffice
	 */
	public boolean isHeadOffice() {
		return isHeadOffice;
	}
	/**
	 * @param isHeadOffice the isHeadOffice to set
	 */
	public void setHeadOffice(boolean isHeadOffice) {
		this.isHeadOffice = isHeadOffice;
	}
}
