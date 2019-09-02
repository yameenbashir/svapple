/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.ProductHistory;
import com.dowhile.dao.ProductHistoryDAO;
import com.dowhile.service.ProductHistoryService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class ProductHistoryServiceImpl implements ProductHistoryService{

	private ProductHistoryDAO productHistoryDAO;
	
	/**
	 * @return the productHistoryDAO
	 */
	public ProductHistoryDAO getProductHistoryDAO() {
		return productHistoryDAO;
	}

	/**
	 * @param productHistoryDAO the productHistoryDAO to set
	 */
	public void setProductHistoryDAO(ProductHistoryDAO productHistoryDAO) {
		this.productHistoryDAO = productHistoryDAO;
	}
	
	@Override
	public ProductHistory addProductHistory(ProductHistory productHistory, int companyId) {
		// TODO Auto-generated method stub
		return getProductHistoryDAO().addProductHistory(productHistory,companyId);
	}

	@Override
	public List<ProductHistory> getProductHistoryByProductIdOutletId(
			int productId, int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getProductHistoryDAO().getProductHistoryByProductIdOutletId(productId, outletId, companyId);
	}

	@Override
	public List<ProductHistory> getProductHistoryByUuid(String uUid, int companyId) {
		// TODO Auto-generated method stub
		return getProductHistoryDAO().getProductHistoryByUuid(uUid, companyId);
	}

	@Override
	public List<ProductHistory> getProductHistoryByProductId(int productId, int companyId) {
		// TODO Auto-generated method stub
		return getProductHistoryDAO().getProductHistoryByProductId(productId,companyId);
	}

	

}
