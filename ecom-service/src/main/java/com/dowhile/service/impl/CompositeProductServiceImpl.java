/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.CompositeProduct;
import com.dowhile.constant.Actions;
import com.dowhile.dao.CompositeProductDAO;
import com.dowhile.service.CompositeProductService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class CompositeProductServiceImpl implements CompositeProductService{

	private CompositeProductDAO compositeProductDAO;
	
	/**
	 * @return the compositeProductDAO
	 */
	public CompositeProductDAO getCompositeProductDAO() {
		return compositeProductDAO;
	}

	/**
	 * @param compositeProductDAO the compositeProductDAO to set
	 */
	public void setCompositeProductDAO(CompositeProductDAO compositeProductDAO) {
		this.compositeProductDAO = compositeProductDAO;
	}

	@Override
	public CompositeProduct addCompositeProduct(
			CompositeProduct compositeProduct,Actions actionName,int totalQunatity, int companyId,String productUuId) {
		// TODO Auto-generated method stub
		compositeProduct.setProductUuid(productUuId);
		return getCompositeProductDAO().addCompositeProduct(compositeProduct, actionName, totalQunatity, companyId);
	}

	@Override
	public CompositeProduct updateCompositeProduct(
			CompositeProduct compositeProduct,Actions actionName,int totalQunatity, int companyId) {
		// TODO Auto-generated method stub
		return getCompositeProductDAO().updateCompositeProduct(compositeProduct, actionName, totalQunatity,companyId);
	}

	@Override
	public boolean deleteCompositeProduct(CompositeProduct compositeProduct,Actions actionName, int companyId) {
		// TODO Auto-generated method stub
		return getCompositeProductDAO().deleteCompositeProduct(compositeProduct, actionName,companyId);
	}

	@Override
	public CompositeProduct getCompositeProductByCompositeProductId(
			int compositeProductId, int companyId) {
		// TODO Auto-generated method stub
		return getCompositeProductDAO().getCompositeProductByCompositeProductId(compositeProductId,companyId);
	}

	@Override
	public List<CompositeProduct> getAllCompositeProducts( int companyId) {
		// TODO Auto-generated method stub
		return getCompositeProductDAO().getAllCompositeProducts(companyId);
	}

	@Override
	public List<CompositeProduct> getAllCompositeProductsByProductIdOultetIdCompanyId(
			int productId,int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getCompositeProductDAO().getAllCompositeProductsByProductIdOultetIdCompanyId(productId, outletId, companyId);
	}

	@Override
	public List<CompositeProduct> getAllCompositeProductsByUuid(String uUid, int companyId) {
		// TODO Auto-generated method stub
		return getCompositeProductDAO().getAllCompositeProductsByUuid(uUid,companyId);
	}

	@Override
	public int getCountOfInventoryByProductUuId(String productUuId, int companyId) {
		// TODO Auto-generated method stub
		return getCompositeProductDAO().getCountOfInventoryByProductUuId(productUuId, companyId);
	}

	@Override
	public int getCountOfInventoryByCompositeProductUuId(
			String compositeProductUuId, int companyId) {
		// TODO Auto-generated method stub
		return getCompositeProductDAO().getCountOfInventoryByCompositeProductUuId(compositeProductUuId, companyId);
	}

	@Override
	public boolean addCompositeProductList(
			List<CompositeProduct> compositeProductList) {
		// TODO Auto-generated method stub
		return getCompositeProductDAO().addCompositeProductList(compositeProductList);
	}

}
