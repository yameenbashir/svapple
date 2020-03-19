/**
 * 
 */
package com.dowhile.service.impl;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.dao.PriceBookControllerWrapperDAO;
import com.dowhile.service.PriceBookControllerWrapperService;
import com.dowhile.wrapper.PriceBookControllerWrapper;

/**
 * @author HafizYameenBashir
 *
 */
@Transactional(readOnly = false)
public class PriceBookControllerWrapperServiceImpl implements PriceBookControllerWrapperService{

	private PriceBookControllerWrapperDAO priceBookControllerWrapperDAO;
	
	/**
	 * @return the priceBookControllerWrapperDAO
	 */
	public PriceBookControllerWrapperDAO getPriceBookControllerWrapperDAO() {
		return priceBookControllerWrapperDAO;
	}
	/**
	 * @param priceBookControllerWrapperDAO the priceBookControllerWrapperDAO to set
	 */
	public void setPriceBookControllerWrapperDAO(PriceBookControllerWrapperDAO priceBookControllerWrapperDAO) {
		this.priceBookControllerWrapperDAO = priceBookControllerWrapperDAO;
	}
	
	@Override
	public PriceBookControllerWrapper getNewPriceBookControllerDataByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		return getPriceBookControllerWrapperDAO().getNewPriceBookControllerDataByCompanyId(companyId);
	}
	@Override
	public PriceBookControllerWrapper getActivePriceBooksAndContactGroupByDateRangeCompanyIdContactGroupId(
			Date validFrom, Date validTo, int companyId, int contactGroupId) {
		// TODO Auto-generated method stub
		return getPriceBookControllerWrapperDAO().getActivePriceBooksAndContactGroupByDateRangeCompanyIdContactGroupId(validFrom, validTo, companyId, contactGroupId);
	}
	@Override
	public PriceBookControllerWrapper getManagePriceBookControllerDataByCompanyIdOutletIdPriceBookId(int companyId,
			int outletId, int priceBookId) {
		// TODO Auto-generated method stub
		return getPriceBookControllerWrapperDAO().getManagePriceBookControllerDataByCompanyIdOutletIdPriceBookId(companyId, outletId, priceBookId);
	}
	@Override
	public PriceBookControllerWrapper manageProductsInPriceBookByCompanyIdOutletIdPriceBookId(int companyId,
			int outletId, int priceBookId) {
		// TODO Auto-generated method stub
		return getPriceBookControllerWrapperDAO().manageProductsInPriceBookByCompanyIdOutletIdPriceBookId(companyId, outletId, priceBookId);
	}

}
