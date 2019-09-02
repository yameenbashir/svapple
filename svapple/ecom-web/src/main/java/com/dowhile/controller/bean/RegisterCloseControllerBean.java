/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.frontend.mapping.bean.CashManagmenBean;
import com.dowhile.frontend.mapping.bean.DailyRegisterBean;

/**
 * @author imran latif
 *
 */
public class RegisterCloseControllerBean {
	
	boolean lastDayilyRegisterExist;
	boolean openDayilyRegisterExist;
	DailyRegisterBean dailyRegisterBean;
	private String outletName;
	List<CashManagmenBean> cashManagmenBeans;
	public DailyRegisterBean getDailyRegisterBean() {
		return dailyRegisterBean;
	}
	public void setDailyRegisterBean(DailyRegisterBean dailyRegisterBean) {
		this.dailyRegisterBean = dailyRegisterBean;
	}
	public List<CashManagmenBean> getCashManagmenBeans() {
		return cashManagmenBeans;
	}
	public void setCashManagmenBeans(List<CashManagmenBean> cashManagmenBeans) {
		this.cashManagmenBeans = cashManagmenBeans;
	}
	public boolean getLastDayilyRegisterExist() {
		return lastDayilyRegisterExist;
	}
	public void setLastDayilyRegisterExist(boolean lastDayilyRegisterExist) {
		this.lastDayilyRegisterExist = lastDayilyRegisterExist;
	}
	public boolean getOpenDayilyRegisterExist() {
		return openDayilyRegisterExist;
	}
	public void setOpenDayilyRegisterExist(boolean openDayilyRegisterExist) {
		this.openDayilyRegisterExist = openDayilyRegisterExist;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

}
