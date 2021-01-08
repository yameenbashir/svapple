/**
 * 
 */
package com.dowhile.service;

import com.dowhile.wrapper.ProductControllerWrapper;

/**
 * @author HafizYameenBashir
 *
 */
public interface ProductControllerWrapperService {

	public ProductControllerWrapper getProductControllerWrapperDataByOutletIdCompanyId(int outletId,int companyId);
	public ProductControllerWrapper getProductControllerWrapperDataForManageProductByOutletIdCompanyId(int outletId,int companyId);
	public ProductControllerWrapper getProductControllerWrapperDataForProductHistoryByProductUuidOutletIdCompanyId(String productUuid,int outletId,int companyId,boolean isHeadOffice);
}
