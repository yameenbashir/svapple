/**
 * 
 */
package com.dowhile.service.impl; 

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.StockOrderType;
import com.dowhile.dao.StockOrderTypeDAO;
import com.dowhile.service.StockOrderTypeService;

/**
 * @author Zafar Shakeel
 *
 */
@Transactional(readOnly = false)
public class StockOrderTypeServiceImpl implements StockOrderTypeService{


	private StockOrderTypeDAO stockOrderTypeDAO;
	
	/**
	 * @return the StockOrderTypeDAO
	 */
	public StockOrderTypeDAO getStockOrderTypeDAO() {
		return stockOrderTypeDAO;
	}

	/**
	 * @param stockOrderTypeDAO the StockOrderTypeDAO to set
	 */
	public void setStockOrderTypeDAO(StockOrderTypeDAO stockOrderTypeDAO) {
		this.stockOrderTypeDAO = stockOrderTypeDAO;
	}

	@Override
	public StockOrderType addStockOrderType(StockOrderType stockOrderType) {
		// TODO Auto-generated method stub
		return getStockOrderTypeDAO().addStockOrderType(stockOrderType);
	}

	@Override
	public StockOrderType updateStockOrderType(StockOrderType stockOrderType) {
		// TODO Auto-generated method stub
		return getStockOrderTypeDAO().updateStockOrderType(stockOrderType);
	}

	@Override
	public boolean deleteStockOrderType(StockOrderType stockOrderType) {
		// TODO Auto-generated method stub
		return getStockOrderTypeDAO().deleteStockOrderType(stockOrderType);
	}

	@Override
	public StockOrderType getStockOrderTypeByStockOrderTypeId(int stockOrderTypeId) {
		// TODO Auto-generated method stub
		return getStockOrderTypeDAO().getStockOrderTypeByStockOrderTypeId(stockOrderTypeId);
	}

	@Override
	public List<StockOrderType> getAllStockOrderType() {
		// TODO Auto-generated method stub
		return getStockOrderTypeDAO().getAllStockOrderType();
	}

}
