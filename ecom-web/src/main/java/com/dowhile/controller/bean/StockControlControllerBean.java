/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.frontend.mapping.bean.StockOrderBean;

/**
 * @author Hafiz Yameen Bashir
 *
 */
public class StockControlControllerBean {

	private List<StockOrderBean> stockOrderBeansList;
	private boolean stockTransferToSupplier;
	/**
	 * 
	 */
	public StockControlControllerBean() {
	}
	/**
	 * @param stockOrderBeansList
	 * @param stockTransferToSupplier
	 */
	public StockControlControllerBean(List<StockOrderBean> stockOrderBeansList,
			boolean stockTransferToSupplier) {
		this.stockOrderBeansList = stockOrderBeansList;
		this.stockTransferToSupplier = stockTransferToSupplier;
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
	 * @return the stockTransferToSupplier
	 */
	public boolean isStockTransferToSupplier() {
		return stockTransferToSupplier;
	}
	/**
	 * @param stockTransferToSupplier the stockTransferToSupplier to set
	 */
	public void setStockTransferToSupplier(boolean stockTransferToSupplier) {
		this.stockTransferToSupplier = stockTransferToSupplier;
	}
}
