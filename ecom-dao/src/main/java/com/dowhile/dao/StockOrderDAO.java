/**
 * 
 */
package com.dowhile.dao; 

import java.util.List;

import com.dowhile.StockOrder;
import com.dowhile.Company;
import com.dowhile.wrapper.StockBasicDataWrapper;
import com.dowhile.wrapper.StockDataProductsWrapper;
import com.dowhile.wrapper.StockWrapper;

/**
 * @author Zafar Shakeel
 *
 */
public interface StockOrderDAO {
	
	StockOrder addStockOrder(StockOrder stockOrder,int companyId);
	StockOrder updateStockOrder(StockOrder stockOrder,int companyId);
	boolean deleteStockOrder(StockOrder stockOrder,int companyId);
	StockOrder getStockOrderByStockOrderID(int stockOrderID,int companyId);	
	List<StockOrder> getStockOrderBySupplierId(int supplierID,int companyId);
	List<StockOrder> getStockOrderByOutletId(int outletID,int companyId);
	List<StockOrder> getTenStockOrderByOutletId(int outletID,int companyId);
	List<StockOrder> getStockOrderCompletedByOutletId(int outletID,int companyId);
	List<StockOrder> getTenStockOrderCompletedByOutletId(int outletID,int companyId);
	List<StockOrder> getStockOrderByOutletIdNotComp(int outletID,int companyId);
	List<StockOrder> GetAllStockOrder(int companyId);
	List<StockOrder> GetTenStockOrder(int companyId);
	List<StockOrder> GetAllStockTransferOrder(int companyId);
	List<StockOrder> GetAllStockReturntoWarehouseOrder(int companyId);
	List<StockOrder> GetAllStockReturnOrderForOutlet(int outletId, int companyId);
	boolean UpdateStockComplete(StockWrapper stockWrapper, Company company);
	StockBasicDataWrapper GetStockBasicData(int companyId);
	StockDataProductsWrapper GetStockWithProductsData(int companyId);
}
