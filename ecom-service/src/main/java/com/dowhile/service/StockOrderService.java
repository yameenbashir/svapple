/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.StockOrder;

/**
 * @author Zafar Shakeel
 *
 */
public interface StockOrderService {

	StockOrder addStockOrder(StockOrder stockOrder,int companyId);
	StockOrder updateStockOrder(StockOrder stockOrder,int companyId);
	boolean deleteStockOrder(StockOrder stockOrder,int companyId);
	StockOrder getStockOrderByStockOrderID(int stockOrderID,int companyId);
	List<StockOrder> getStockOrderBySupplierId(int supplierID,int companyId);
	List<StockOrder> getStockOrderByOutletId(int outletID,int companyId);
	List<StockOrder> getStockOrderCompletedByOutletId(int outletID,int companyId);
	List<StockOrder> GetAllStockOrder(int companyId);
	List<StockOrder> GetAllStockTransferOrder(int companyId);
	List<StockOrder> GetAllStockReturntoWarehouseOrder(int companyId);
	List<StockOrder> GetAllStockReturnOrderForOutlet(int outletId, int companyId);
}
