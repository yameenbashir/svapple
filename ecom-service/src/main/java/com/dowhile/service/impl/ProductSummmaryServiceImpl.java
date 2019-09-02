/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.ProductSummmary;
import com.dowhile.dao.ProductSummmaryDAO;
import com.dowhile.service.ProductSummmaryService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class ProductSummmaryServiceImpl implements ProductSummmaryService{

	private ProductSummmaryDAO productSummmaryDAO;
	/**
	 * @return the productSummmaryDAO
	 */
	public ProductSummmaryDAO getProductSummmaryDAO() {
		return productSummmaryDAO;
	}
	/**
	 * @param productSummmaryDAO the productSummmaryDAO to set
	 */
	public void setProductSummmaryDAO(ProductSummmaryDAO productSummmaryDAO) {
		this.productSummmaryDAO = productSummmaryDAO;
	}
	@Override
	public List<ProductSummmary> getAllProductSummmaryByCompanyIdOutletId(int companyId, int outletId) {
		// TODO Auto-generated method stub
		return getProductSummmaryDAO().getAllProductSummmaryByCompanyIdOutletId(companyId,outletId);
	}
	@Override
	public List<ProductSummmary> getTenNewProductSummmaryByCompanyIdOutletId(
			int companyId, int outletId) {
		// TODO Auto-generated method stub
		return getProductSummmaryDAO().getTenNewProductSummmaryByCompanyIdOutletId(companyId, outletId);
	}
	@Override
	public List<ProductSummmary> getAllProductSummmaryByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		return null;
	}

}
