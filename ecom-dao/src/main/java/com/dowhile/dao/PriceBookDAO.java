/**
 * 
 */
package com.dowhile.dao;

import java.util.Date;
import java.util.List;

import com.dowhile.PriceBook;

/**
 * @author Yameen Bashir
 *
 */
public interface PriceBookDAO {

	PriceBook addPriceBook(PriceBook priceBook);
	PriceBook updatePriceBook(PriceBook priceBook);
	boolean deletePriceBook(PriceBook priceBook);
	PriceBook getPriceBookByPriceBookIdCompanyId(int priceBookId,int companyId);
	List<PriceBook> getAllActivePriceBooksByCompanyId(int companyId);
	List<PriceBook> getAllInActivePriceBooksByCompanyId(int companyId);
	List<PriceBook> getAllValidPriceBooks(int companyId, int outletId, int contactgroupId);
	List<PriceBook> getPriceBooksByDateRangeCompanyIdOutletIdGroupId(Date validFrom,Date validTo,int companyId,int outletId,int contactgroupId);
	List<PriceBook> getActivePriceBooksByDateRangeCompanyId(Date validFrom,Date validTo,int companyId);
}
