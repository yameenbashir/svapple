package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.frontend.mapping.bean.ProductTypeBean;

public class HomeControllerBean {
	
	List<ProductTypeBean> productTypeBeanList;

	public List<ProductTypeBean> getProductTypeBeanList() {
		return productTypeBeanList;
	}

	public void setProductTypeBeanList(List<ProductTypeBean> productTypeBeanList) {
		this.productTypeBeanList = productTypeBeanList;
	}

}
