/**
 * 
 */
package com.dowhile.service.impl; 

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.StockOrder;
import com.dowhile.dao.StockOrderDAO;
import com.dowhile.Company;
import com.dowhile.service.StockOrderService;
import com.dowhile.wrapper.StockBasicDataWrapper;
import com.dowhile.wrapper.StockDataProductsWrapper;
import com.dowhile.wrapper.StockWrapper;


/**
 * @author Zafar Shakeel
 *
 */
@Transactional(readOnly = false)
public class StockOrderServiceImpl implements StockOrderService{
	
	private StockOrderDAO stockOrderDAO;

	/**
	 * @return the StockOrderDAO
	 */
	public StockOrderDAO getStockOrderDAO() {
		return stockOrderDAO;
	}

	/**
	 * @param StockOrderDAO the StockOrderDAO to set
	 */
	public void setStockOrderDAO(StockOrderDAO stockOrderDAO) {
		this.stockOrderDAO = stockOrderDAO;
	}

	@Override
	public StockOrder addStockOrder(StockOrder stockOrder,int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDAO().addStockOrder(stockOrder,companyId);
	}

	@Override
	public StockOrder updateStockOrder(StockOrder stockOrder,int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDAO().updateStockOrder(stockOrder,companyId);
	}

	@Override
	public boolean deleteStockOrder(StockOrder stockOrder,int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDAO().deleteStockOrder(stockOrder,companyId);
	}

	@Override
	public StockOrder getStockOrderByStockOrderID(int stockOrderID,int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDAO().getStockOrderByStockOrderID(stockOrderID,companyId);
	}
	
	@Override
	public List<StockOrder> getStockOrderBySupplierId(int supplierID,int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDAO().getStockOrderBySupplierId(supplierID,companyId);
	}
	
	@Override
	public List<StockOrder> getStockOrderByOutletId(int outletID,int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDAO().getStockOrderByOutletId(outletID,companyId);
	}
	
	@Override
	public List<StockOrder> getTenStockOrderByOutletId(int outletID,int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDAO().getTenStockOrderByOutletId(outletID,companyId);
	}
	@Override
	public List<StockOrder> getStockOrderByOutletIdNotComp(int outletID,int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDAO().getStockOrderByOutletIdNotComp(outletID,companyId);
	}
	
	@Override
	public List<StockOrder> getStockOrderCompletedByOutletId(int outletID,int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDAO().getStockOrderCompletedByOutletId(outletID,companyId);
	}
	
	@Override
	public List<StockOrder> getTenStockOrderCompletedByOutletId(int outletID,int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDAO().getTenStockOrderCompletedByOutletId(outletID,companyId);
	}
	
	@Override
	public List<StockOrder> GetAllStockOrder(int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDAO().GetAllStockOrder(companyId);
	}
	
	@Override
	public List<StockOrder> GetTenStockOrder(int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDAO().GetTenStockOrder(companyId);
	}
	
	@Override
	public List<StockOrder> GetAllStockTransferOrder(int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDAO().GetAllStockTransferOrder(companyId);
	}
	
	@Override
	public List<StockOrder> GetAllStockReturntoWarehouseOrder(int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDAO().GetAllStockReturntoWarehouseOrder(companyId);
	}
	
	@Override
	public List<StockOrder> GetAllStockReturnOrderForOutlet(int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDAO().GetAllStockReturnOrderForOutlet(outletId, companyId);
	}
	
	@Override
	public boolean UpdateStockComplete(StockWrapper stockWrapper, Company company) {
		// TODO Auto-generated method stub
		return getStockOrderDAO().UpdateStockComplete(stockWrapper, company);
	}
	
	@Override
	public StockBasicDataWrapper GetStockBasicData(int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDAO().GetStockBasicData(companyId);
	}
	
	@Override
	public StockDataProductsWrapper GetStockWithProductsData(int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDAO().GetStockWithProductsData(outletId, companyId);
	}
}
