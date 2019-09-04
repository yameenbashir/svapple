/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.StockOrderV;

/**
 * @author Zafar Shakeel
 *
 */
public interface StockOrderVDAO {
	List<StockOrderV> getAllStockOrderByOutletId(int outletID,int companyId);
	List<StockOrderV> getTenStockOrderByOutletId(int outletID,int companyId);
	List<StockOrderV> getAllStockOrder(int companyId);
	List<StockOrderV> getTenStockOrder(int companyId);
	List<StockOrderV> getAllStockOrderCompletedByOutletId(int outletID,int companyId);
	List<StockOrderV> getTenStockOrderCompletedByOutletId(int outletID,int companyId);
}
