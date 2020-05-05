/**
 * 
 */
package com.dowhile.service.impl; 

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.StockDetByProductUuid;
import com.dowhile.StockOrderDetail;
import com.dowhile.StockOrderDetailCustom;
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
	public void saveorUpdateStockOrderDetailsList(List<StockOrderDetail> stockOrderDetailsList,int companyId) {
		// TODO Auto-generated method stub
		 getStockOrderDetailDAO().saveorUpdateStockOrderDetailsList(stockOrderDetailsList,companyId);
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
	
	@Override
	public List<StockOrderDetail> getStockOrderDetailByProductId(int productId, Date startDate, Date endDate, int companyId){
		// TODO Auto-generated method stub
		return getStockOrderDetailDAO().getStockOrderDetailByProductId(productId, startDate, endDate, companyId);
	}
	
	@Override
	public List<StockOrderDetail> getStockOrderDetailByProductVariantId(int productVariantId, Date startDate, Date endDate, int companyId){
		// TODO Auto-generated method stub
		return getStockOrderDetailDAO().getStockOrderDetailByProductVariantId(productVariantId, startDate, endDate, companyId);
	}
	
	@Override
	public List<StockOrderDetail> getStockOrderDetailByProductVariantsId(List<Integer> productVariantsId, Date startDate, Date endDate, int companyId){
		// TODO Auto-generated method stub
		return getStockOrderDetailDAO().getStockOrderDetailByProductVariantsId(productVariantsId, startDate, endDate, companyId);
	}
	
	@Override
	public List<StockOrderDetailCustom> getStockOrderDetailCustom(int stockOrderId, int outletId){
		// TODO Auto-generated method stub
		return getStockOrderDetailDAO().getStockOrderDetailCustom(stockOrderId, outletId);
	}
	
	@Override
	public List<StockDetByProductUuid> getStockOrderDetailByProductUUID(int companyId, int status, int stockOrdeType,
			String productUuid, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return getStockOrderDetailDAO().getStockOrderDetailByProductUUID(companyId,status,stockOrdeType,productUuid,startDate,endDate);
	}

}
