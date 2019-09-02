package com.dowhile.controller.bean;

import java.util.ArrayList;
import java.util.List;

import com.dowhile.angualrspringapp.controller.ExpenseTypeBean;
import com.dowhile.frontend.mapping.bean.CashManagmenBean;

public class CashManagementControllerBean {
	List<CashManagmenBean> cashManagmentControllerBeans = new ArrayList<>();
	List<ExpenseTypeBean> expenseTypes  = new ArrayList<>();
	public List<CashManagmenBean> getCashManagmentControllerBeans() {
		return cashManagmentControllerBeans;
	}
	public void setCashManagmentControllerBeans(
			List<CashManagmenBean> cashManagmentControllerBeans) {
		this.cashManagmentControllerBeans = cashManagmentControllerBeans;
	}
	public List<ExpenseTypeBean> getExpenseTypes() {
		return expenseTypes;
	}
	public void setExpenseTypes(List<ExpenseTypeBean> expenseTypes) {
		this.expenseTypes = expenseTypes;
	}
	
}
