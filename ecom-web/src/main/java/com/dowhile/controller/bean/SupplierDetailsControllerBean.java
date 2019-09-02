/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.frontend.mapping.bean.StockOrderBean;
import com.dowhile.frontend.mapping.bean.SupplierBean;
import com.dowhile.frontend.mapping.bean.SupplierPaymentsBean;

/**
 * @author Yameen Bashir
 *
 */
public class SupplierDetailsControllerBean {

	private SupplierBean supplierBean ;
	private List<StockOrderBean> stockOrderBeansList ;
	private List<SupplierPaymentsBean> supplierPaymentsBeanList ;
	/**
	 * 
	 */
	public SupplierDetailsControllerBean() {
	}
	/**
	 * @param supplierBean
	 * @param stockOrderBeansList
	 * @param supplierPaymentsBeanList
	 */
	public SupplierDetailsControllerBean(SupplierBean supplierBean,
			List<StockOrderBean> stockOrderBeansList,
			List<SupplierPaymentsBean> supplierPaymentsBeanList) {
		this.supplierBean = supplierBean;
		this.stockOrderBeansList = stockOrderBeansList;
		this.supplierPaymentsBeanList = supplierPaymentsBeanList;
	}
	/**
	 * @return the supplierBean
	 */
	public SupplierBean getSupplierBean() {
		return supplierBean;
	}
	/**
	 * @param supplierBean the supplierBean to set
	 */
	public void setSupplierBean(SupplierBean supplierBean) {
		this.supplierBean = supplierBean;
	}
	/**
	 * @return the stockOrderBeansList
	 */
	public List<StockOrderBean> getStockOrderBeansList() {
		return stockOrderBeansList;
	}
	/**
	 * @param stockOrderBeansList the stockOrderBeansList to set
	 */
	public void setStockOrderBeansList(List<StockOrderBean> stockOrderBeansList) {
		this.stockOrderBeansList = stockOrderBeansList;
	}
	/**
	 * @return the supplierPaymentsBeanList
	 */
	public List<SupplierPaymentsBean> getSupplierPaymentsBeanList() {
		return supplierPaymentsBeanList;
	}
	/**
	 * @param supplierPaymentsBeanList the supplierPaymentsBeanList to set
	 */
	public void setSupplierPaymentsBeanList(
			List<SupplierPaymentsBean> supplierPaymentsBeanList) {
		this.supplierPaymentsBeanList = supplierPaymentsBeanList;
	}
}
