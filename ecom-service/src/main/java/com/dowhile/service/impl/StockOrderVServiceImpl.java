/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.StockOrderV;
import com.dowhile.dao.StockOrderVDAO;
import com.dowhile.service.StockOrderVService;


/**
 * @author Zafar Shakeel
 *
 */
@Transactional(readOnly = false)
public class StockOrderVServiceImpl implements StockOrderVService{
	
	private StockOrderVDAO stockOrderVDAO;

	/**
	 * @return the StockOrderVDAO
	 */
	public StockOrderVDAO getStockOrderVDAO() {
		return stockOrderVDAO;
	}

	/**
	 * @param StockOrderVDAO the StockOrderVDAO to set
	 */
	public void setStockOrderVDAO(StockOrderVDAO stockOrderVDAO) {
		this.stockOrderVDAO = stockOrderVDAO;
	}

	@Override
	public List<StockOrderV> getAllStockOrderByOutletId(int outletID,int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderVDAO().getAllStockOrderByOutletId(outletID,companyId);
	}
	
	@Override
	public List<StockOrderV> getTenStockOrderByOutletId(int outletID,int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderVDAO().getTenStockOrderByOutletId(outletID,companyId);
	}
	
	@Override
	public List<StockOrderV> getAllStockOrderCompletedByOutletId(int outletID,int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderVDAO().getAllStockOrderCompletedByOutletId(outletID,companyId);
	}
	
	@Override
	public List<StockOrderV> getTenStockOrderCompletedByOutletId(int outletID,int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderVDAO().getTenStockOrderCompletedByOutletId(outletID,companyId);
	}
	@Override
	public List<StockOrderV> getAllStockOrder(int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderVDAO().getAllStockOrder(companyId);
	}
	
	@Override
	public List<StockOrderV> getTenStockOrder(int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderVDAO().getTenStockOrder(companyId);
	}
}
