/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.ProductTag;
import com.dowhile.dao.ProductTagDAO;
import com.dowhile.service.ProductTagService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class ProductTagServiceImpl implements ProductTagService{

	private ProductTagDAO productTagDAO;
	
	/**
	 * @return the productTagDAO
	 */
	public ProductTagDAO getProductTagDAO() {
		return productTagDAO;
	}

	/**
	 * @param productTagDAO the productTagDAO to set
	 */
	public void setProductTagDAO(ProductTagDAO productTagDAO) {
		this.productTagDAO = productTagDAO;
	}

	@Override
	public ProductTag addProductTag(ProductTag productTag, int companyId) {
		// TODO Auto-generated method stub
		return getProductTagDAO().addProductTag(productTag,companyId);
	}

	@Override
	public ProductTag updateProductTag(ProductTag productTag, int companyId) {
		// TODO Auto-generated method stub
		return getProductTagDAO().updateProductTag(productTag,companyId);
	}

	@Override
	public boolean deleteProductTag(ProductTag productTag, int companyId) {
		// TODO Auto-generated method stub
		return getProductTagDAO().deleteProductTag(productTag,companyId);
	}

	@Override
	public ProductTag getProductTagByProductTagIdProductIdCompanyId(int productTagId, int productId, int companyId) {
		// TODO Auto-generated method stub
		return getProductTagDAO().getProductTagByProductTagIdProductIdCompanyId(productTagId, productId, companyId);
	}

	@Override
	public int getCountOfProductTagsByTagId(int tagId, int companyId) {
		// TODO Auto-generated method stub
		return getProductTagDAO().getCountOfProductTagsByTagId(tagId,companyId);
	}

	@Override
	public List<ProductTag> getAllProductTagsByProductId(int productId, int companyId) {
		// TODO Auto-generated method stub
		return getProductTagDAO().getAllProductTagsByProductId(productId,companyId);
	}

	@Override
	public List<ProductTag> getAllProductTagsByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		return getProductTagDAO().getAllProductTagsByCompanyId(companyId);
	}

}
