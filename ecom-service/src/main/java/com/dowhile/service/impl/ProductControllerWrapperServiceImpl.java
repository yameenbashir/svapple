/**
 * 
 */
package com.dowhile.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.dao.ProductControllerWrapperDAO;
import com.dowhile.service.ProductControllerWrapperService;
import com.dowhile.wrapper.ProductControllerWrapper;

/**
 * @author HafizYameenBashir
 *
 */
@Transactional(readOnly = false)
public class ProductControllerWrapperServiceImpl implements ProductControllerWrapperService{

	private ProductControllerWrapperDAO productControllerWrapperDAO;

	/**
	 * @return the productControllerWrapperDAO
	 */
	public ProductControllerWrapperDAO getProductControllerWrapperDAO() {
		return productControllerWrapperDAO;
	}

	/**
	 * @param productControllerWrapperDAO the productControllerWrapperDAO to set
	 */
	public void setProductControllerWrapperDAO(
			ProductControllerWrapperDAO productControllerWrapperDAO) {
		this.productControllerWrapperDAO = productControllerWrapperDAO;
	}

	@Override
	public ProductControllerWrapper getProductControllerWrapperDataByOutletIdCompanyId(
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getProductControllerWrapperDAO().getProductControllerWrapperDataByOutletIdCompanyId(outletId, companyId);
	}

	@Override
	public ProductControllerWrapper getProductControllerWrapperDataForManageProductByOutletIdCompanyId(
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getProductControllerWrapperDAO().getProductControllerWrapperDataForManageProductByOutletIdCompanyId(outletId, companyId);
	}

	@Override
	public ProductControllerWrapper getProductControllerWrapperDataForProductHistoryByProductUuidOutletIdCompanyId(
			String productUuid, int outletId, int companyId, boolean isHeadOffice) {
		// TODO Auto-generated method stub
		return getProductControllerWrapperDAO().getProductControllerWrapperDataForProductHistoryByProductUuidOutletIdCompanyId(productUuid, outletId, companyId, isHeadOffice);
	}
}
