/**
 * 
 */
package com.dowhile.dao; 

import java.util.Date;
import java.util.List;

import com.dowhile.StockDetByProductUuid;
import com.dowhile.StockOrderDetail;
import com.dowhile.StockOrderDetailCustom;
/**
 * @author Zafar Shakeel
 *
 */
public interface StockOrderDetailDAO { 
	
	StockOrderDetail addStockOrderDetail(StockOrderDetail stockOrderDetail,int companyId);
	void addStockOrderDetailsList(List<StockOrderDetail> stockOrderDetail,int companyId);
	StockOrderDetail updateStockOrderDetail(StockOrderDetail stockOrderDetail,int companyId);
	void updateStockOrderDetailsList(List<StockOrderDetail> stockOrderDetailsList,int companyId);
	void saveorUpdateStockOrderDetailsList(List<StockOrderDetail> stockOrderDetailsList,int companyId);
	boolean deleteStockOrderDetail(StockOrderDetail stockOrderDetail,int companyId);
	void deleteStockOrderDetailsList(List<StockOrderDetail> stockOrderDetail,int companyId);
	StockOrderDetail getStockOrderDetailByStockOrderDetailID(int stockOrderDetailID,int companyId);
	List<StockOrderDetail> getStockOrderDetailByStockOrderId(int stockOrderID,int companyId);
	List<StockOrderDetail> getAllStockOrderDetails(int companyId);
	List<StockOrderDetail> getStockOrderDetailByProductId(int productId, Date  startDate, Date endDate, int companyId);
	List<StockOrderDetail> getStockOrderDetailByProductVariantId(int productVariantId, Date  startDate, Date endDate, int companyId);
	List<StockOrderDetail> getStockOrderDetailByProductVariantsId(List<Integer> productVariantsId, Date  startDate, Date endDate, int companyId);	
	List<StockOrderDetailCustom> getStockOrderDetailCustom(int stockOrderId, int outletId);
	List<StockDetByProductUuid> getStockOrderDetailByProductUUID(int companyId, int status, int stockOrdeType, String productUuid,Date startDate,Date endDate);
}
