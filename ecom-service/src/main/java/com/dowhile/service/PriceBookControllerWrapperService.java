/**
 * 
 */
package com.dowhile.service;

import java.util.Date;

import com.dowhile.wrapper.PriceBookControllerWrapper;

/**
 * @author HafizYameenBashir
 *
 */
public interface PriceBookControllerWrapperService {

	PriceBookControllerWrapper getNewPriceBookControllerDataByCompanyId(int companyId);
	PriceBookControllerWrapper getActivePriceBooksAndContactGroupByDateRangeCompanyIdContactGroupId(Date validFrom, Date validTo, int companyId,int contactGroupId);
	PriceBookControllerWrapper getManagePriceBookControllerDataByCompanyIdOutletIdPriceBookId(int companyId,int outletId,int priceBookId);
	PriceBookControllerWrapper manageProductsInPriceBookByCompanyIdOutletIdPriceBookId(int companyId,int outletId,int priceBookId);
}
