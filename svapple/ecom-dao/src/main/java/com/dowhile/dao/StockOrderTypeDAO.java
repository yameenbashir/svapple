/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.StockOrderType;

/**
 * @author Zafar Shakeel
 *
 */
public interface StockOrderTypeDAO {

	StockOrderType addStockOrderType(StockOrderType stockOrderType);
	StockOrderType updateStockOrderType(StockOrderType stockOrderType);
	boolean deleteStockOrderType(StockOrderType stockOrderType);
	StockOrderType getStockOrderTypeByStockOrderTypeId(int stockOrderTypeId);
	List<StockOrderType> getAllStockOrderType();
}
