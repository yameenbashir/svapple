/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;
import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.StockOrderDetail;
import com.dowhile.dao.StockOrderDetailDAO;
import com.dowhile.service.StockOrderDetailService;

/**
 * @author Zafar Shakeel
 *
 */
@Transactional(readOnly = false)
public class StockOrderDetailServiceImpl implements StockOrderDetailService{
	
	private StockOrderDetailDAO stockOrderDetailDAO;

	/**
	 * @return the StockOrderDetailDAO
	 */
	public StockOrderDetailDAO getStockOrderDetailDAO() {
		return stockOrderDetailDAO;
	}

	/**
	 * @param StockOrderDetailDAO the StockOrderDetailDAO to set
	 */
	public void setStockOrderDetailDAO(StockOrderDetailDAO stockOrderDetailDAO) {
		this.stockOrderDetailDAO = stockOrderDetailDAO;
	}

	@Override
	public StockOrderDetail addStockOrderDetail(StockOrderDetail stockOrderDetail,int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDetailDAO().addStockOrderDetail(stockOrderDetail,companyId);
	}

	@Override
	public void addStockOrderDetailsList(List<StockOrderDetail> stockOrderDetail,int companyId) {
		// TODO Auto-generated method stub
		getStockOrderDetailDAO().addStockOrderDetailsList(stockOrderDetail,companyId);
	}
	
	@Override
	public StockOrderDetail updateStockOrderDetail(StockOrderDetail stockOrderDetail,int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDetailDAO().updateStockOrderDetail(stockOrderDetail,companyId);
	}

	@Override
	public void updateStockOrderDetailsList(List<StockOrderDetail> stockOrderDetailsList,int companyId) {
		// TODO Auto-generated method stub
		 getStockOrderDetailDAO().updateStockOrderDetailsList(stockOrderDetailsList,companyId);
	}
	
	@Override
	public boolean deleteStockOrderDetail(StockOrderDetail stockOrderDetail,int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDetailDAO().deleteStockOrderDetail(stockOrderDetail,companyId);
	}
	
	@Override
	public void deleteStockOrderDetailsList(List<StockOrderDetail> stockOrderDetail,int companyId) {
		// TODO Auto-generated method stub
		getStockOrderDetailDAO().deleteStockOrderDetailsList(stockOrderDetail,companyId);
	}
	
	@Override
	public StockOrderDetail getStockOrderDetailByStockOrderDetailID(int stockOrderDetailID,int companyId) {
		// TODO Auto-generated method stub
		return getStockOrderDetailDAO().getStockOrderDetailByStockOrderDetailID(stockOrderDetailID,companyId);
	}

	@Override
	public List<StockOrderDetail> getStockOrderDetailByStockOrderId(int stockOrderID,int companyId){
		// TODO Auto-generated method stub
		return getStockOrderDetailDAO().getStockOrderDetailByStockOrderId(stockOrderID,companyId);
	}
	@Override
	public List<StockOrderDetail> getAllStockOrderDetails(int companyId){
		// TODO Auto-generated method stub
		return getStockOrderDetailDAO().getAllStockOrderDetails(companyId);
	}
	
	public List<StockOrderDetail> getStockOrderDetailByProductId(int productId, Date startDate, Date endDate, int companyId){
		// TODO Auto-generated method stub
		return getStockOrderDetailDAO().getStockOrderDetailByProductId(productId, startDate, endDate, companyId);
	}
	
	public List<StockOrderDetail> getStockOrderDetailByProductVariantId(int productVariantId, Date startDate, Date endDate, int companyId){
		// TODO Auto-generated method stub
		return getStockOrderDetailDAO().getStockOrderDetailByProductVariantId(productVariantId, startDate, endDate, companyId);
	}
	
	public List<StockOrderDetail> getStockOrderDetailByProductVariantsId(List<Integer> productVariantsId, Date startDate, Date endDate, int companyId){
		// TODO Auto-generated method stub
		return getStockOrderDetailDAO().getStockOrderDetailByProductVariantsId(productVariantsId, startDate, endDate, companyId);
	}
}
