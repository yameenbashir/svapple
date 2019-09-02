/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.ProductPriceHistory;
import com.dowhile.dao.ProductPriceHistoryDAO;
import com.dowhile.service.ProductPriceHistoryService;

/**
 * @author Hafiz Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class ProductPriceHistoryServiceImpl implements ProductPriceHistoryService{

	private ProductPriceHistoryDAO productPriceHistoryDAO;
	/**
	 * @return the productPriceHistoryDAO
	 */
	public ProductPriceHistoryDAO getProductPriceHistoryDAO() {
		return productPriceHistoryDAO;
	}

	/**
	 * @param productPriceHistoryDAO the productPriceHistoryDAO to set
	 */
	public void setProductPriceHistoryDAO(
			ProductPriceHistoryDAO productPriceHistoryDAO) {
		this.productPriceHistoryDAO = productPriceHistoryDAO;
	}

	@Override
	public ProductPriceHistory addProductPriceHistory(
			ProductPriceHistory productPriceHistory) {
		// TODO Auto-generated method stub
		return getProductPriceHistoryDAO().addProductPriceHistory(productPriceHistory);
	}

	@Override
	public ProductPriceHistory updateProductPriceHistory(
			ProductPriceHistory productPriceHistory) {
		// TODO Auto-generated method stub
		return getProductPriceHistoryDAO().updateProductPriceHistory(productPriceHistory);
	}

	@Override
	public boolean deleteProductPriceHistory(
			ProductPriceHistory productPriceHistory) {
		// TODO Auto-generated method stub
		return getProductPriceHistoryDAO().deleteProductPriceHistory(productPriceHistory);
	}

	@Override
	public List<ProductPriceHistory> getAllProductPriceHistoryByCompanyIdOutletId(
			int companyId, int outletId) {
		// TODO Auto-generated method stub
		return getProductPriceHistoryDAO().getAllProductPriceHistoryByCompanyIdOutletId(companyId, outletId);
	}

	@Override
	public List<ProductPriceHistory> getAllProductPriceHistoryByCompanyId(
			int companyId) {
		// TODO Auto-generated method stub
		return getProductPriceHistoryDAO().getAllProductPriceHistoryByCompanyId(companyId);
	}

	@Override
	public List<ProductPriceHistory> getAllProductPriceHistoryByCompanyIdOutletIdProductId(
			int companyId, int outletId, int productId) {
		// TODO Auto-generated method stub
		return getProductPriceHistoryDAO().getAllProductPriceHistoryByCompanyIdOutletIdProductId(companyId, outletId, productId);
	}

}
