/**
 * 
 */
package com.dowhile.service; 

import java.util.List;

import com.dowhile.StockOrderType;

/**
 * @author Zafar Shakeel
 *
 */
public interface StockOrderTypeService {

	StockOrderType addStockOrderType(StockOrderType stockOrderType);
	StockOrderType updateStockOrderType(StockOrderType stockOrderType);
	boolean deleteStockOrderType(StockOrderType stockOrderType);
	StockOrderType getStockOrderTypeByStockOrderTypeId(int stockOrderTypeId);
	List<StockOrderType> getAllStockOrderType();
}
