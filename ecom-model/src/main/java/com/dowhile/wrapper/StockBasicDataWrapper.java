package com.dowhile.wrapper;

import java.util.List;

import com.dowhile.Contact;
import com.dowhile.Outlet;
import com.dowhile.StockOrderType;

public class StockBasicDataWrapper {
	private List<Outlet> outletList;
	private List<StockOrderType> stockOrderTypeList;
	private List<Contact> supplierList;
	public List<Outlet> getOutletList() {
		return outletList;
	}
	public void setOutletList(List<Outlet> outletList) {
		this.outletList = outletList;
	}
	public List<StockOrderType> getStockOrderTypeList() {
		return stockOrderTypeList;
	}
	public void setStockOrderTypeList(List<StockOrderType> stockOrderTypeList) {
		this.stockOrderTypeList = stockOrderTypeList;
	}
	public List<Contact> getSupplierList() {
		return supplierList;
	}
	public void setSupplierList(List<Contact> supplierList) {
		this.supplierList = supplierList;
	}
}
