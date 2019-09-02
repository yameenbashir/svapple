package com.dowhile.controller.bean;

import java.util.ArrayList;
import java.util.List;

import com.dowhile.frontend.mapping.bean.ProductBean;

public class LayoutDesignControllerBean {
	List<ProductBean> productsBean = new ArrayList<>();
	private String registerStatus ;
	private String dailyRegisterId ;
	public List<ProductBean> getProductsBean() {
		return productsBean;
	}
	public void setProductsBean(List<ProductBean> productsBean) {
		this.productsBean = productsBean;
	}
	public String getRegisterStatus() {
		return registerStatus;
	}
	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}
	public String getDailyRegisterId() {
		return dailyRegisterId;
	}
	public void setDailyRegisterId(String dailyRegisterId) {
		this.dailyRegisterId = dailyRegisterId;
	}

}
