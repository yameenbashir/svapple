/**
 * 
 */
package com.dowhile.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.PriceBook;
import com.dowhile.dao.PriceBookDAO;
import com.dowhile.service.PriceBookService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class PriceBookServiceImpl implements PriceBookService{

	private PriceBookDAO priceBookDAO;
	
	/**
	 * @return the priceBookDAO
	 */
	public PriceBookDAO getPriceBookDAO() {
		return priceBookDAO;
	}

	/**
	 * @param priceBookDAO the priceBookDAO to set
	 */
	public void setPriceBookDAO(PriceBookDAO priceBookDAO) {
		this.priceBookDAO = priceBookDAO;
	}

	@Override
	public PriceBook addPriceBook(PriceBook priceBook) {
		// TODO Auto-generated method stub
		return getPriceBookDAO().addPriceBook(priceBook);
	}

	@Override
	public PriceBook updatePriceBook(PriceBook priceBook) {
		// TODO Auto-generated method stub
		return getPriceBookDAO().updatePriceBook(priceBook);
	}

	@Override
	public boolean deletePriceBook(PriceBook priceBook) {
		// TODO Auto-generated method stub
		return getPriceBookDAO().deletePriceBook(priceBook);
	}

	@Override
	public PriceBook getPriceBookByPriceBookIdCompanyId(int priceBookId,
			int companyId) {
		// TODO Auto-generated method stub
		return getPriceBookDAO().getPriceBookByPriceBookIdCompanyId(priceBookId, companyId);
	}

	@Override
	public List<PriceBook> getAllActivePriceBooksByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		return getPriceBookDAO().getAllActivePriceBooksByCompanyId(companyId);
	}

	@Override
	public List<PriceBook> getAllValidPriceBooks(int companyId, int outletId,
			int contactgroupId) {
		// TODO Auto-generated method stub
				return getPriceBookDAO().getAllValidPriceBooks(companyId, outletId,contactgroupId);
	}

	@Override
	public List<PriceBook> getPriceBooksByDateRangeCompanyIdOutletIdGroupId(
			Date validFrom, Date validTo, int companyId, int outletId,
			int contactgroupId) {
		// TODO Auto-generated method stub
		return getPriceBookDAO().getPriceBooksByDateRangeCompanyIdOutletIdGroupId(validFrom, validTo, companyId, outletId, contactgroupId);
	}

	@Override
	public List<PriceBook> getAllInActivePriceBooksByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		return getPriceBookDAO().getAllInActivePriceBooksByCompanyId(companyId);
	}

}
