/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.ProductType;
import com.dowhile.dao.ProductTypeDAO;
import com.dowhile.service.ProductTypeService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class ProductTypeServiceImpl implements ProductTypeService{


	private ProductTypeDAO productTypeDAO;
	
	/**
	 * @return the productTypeDAO
	 */
	public ProductTypeDAO getProductTypeDAO() {
		return productTypeDAO;
	}

	/**
	 * @param productTypeDAO the productTypeDAO to set
	 */
	public void setProductTypeDAO(ProductTypeDAO productTypeDAO) {
		this.productTypeDAO = productTypeDAO;
	}

	@Override
	public ProductType addProductType(ProductType productType, int companyId) {
		// TODO Auto-generated method stub
		return getProductTypeDAO().addProductType(productType,companyId);
	}

	@Override
	public ProductType updateProductType(ProductType productType, int companyId) {
		// TODO Auto-generated method stub
		return getProductTypeDAO().updateProductType(productType,companyId);
	}

	@Override
	public boolean deleteProductType(ProductType productType, int companyId) {
		// TODO Auto-generated method stub
		return getProductTypeDAO().deleteProductType(productType,companyId);
	}

	@Override
	public ProductType getProductTypeByProductTypeId(int productTypeId, int companyId) {
		// TODO Auto-generated method stub
		return getProductTypeDAO().getProductTypeByProductTypeId(productTypeId,companyId);
	}

	@Override
	public List<ProductType> getAllProductTypes(int companyId) {
		// TODO Auto-generated method stub
		return getProductTypeDAO().getAllProductTypes(companyId);
	}

	@Override
	public List<ProductType> getTopProductTypes(int companyId, int count) {
		// TODO Auto-generated method stub
		return getProductTypeDAO().getTopProductTypes(companyId, count);
	}

}
