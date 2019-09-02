/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.PriceBookDetailSummary;
import com.dowhile.dao.PriceBookDetailSummaryDAO;
import com.dowhile.service.PriceBookDetailSummaryService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class PriceBookDetailSummaryServiceImpl implements PriceBookDetailSummaryService{

	private PriceBookDetailSummaryDAO priceBookDetailSummaryDAO;
	
	
	/**
	 * @return the priceBookDetailSummaryDAO
	 */
	public PriceBookDetailSummaryDAO getPriceBookDetailSummaryDAO() {
		return priceBookDetailSummaryDAO;
	}

	/**
	 * @param priceBookDetailSummaryDAO the priceBookDetailSummaryDAO to set
	 */
	public void setPriceBookDetailSummaryDAO(
			PriceBookDetailSummaryDAO priceBookDetailSummaryDAO) {
		this.priceBookDetailSummaryDAO = priceBookDetailSummaryDAO;
	}
	
	@Override
	public List<PriceBookDetailSummary> getPriceBookDetailSummaryByPriceBookIdCompanyIdGroupByUuId(
			int priceBookId, int companyId) {
		// TODO Auto-generated method stub
		return getPriceBookDetailSummaryDAO().getPriceBookDetailSummaryByPriceBookIdCompanyIdGroupByUuId(priceBookId, companyId);
	}

	@Override
	public List<PriceBookDetailSummary> getPriceBookDetailSummaryByPriceBookIdCompanyId(
			int priceBookId, int companyId) {
		// TODO Auto-generated method stub
		return getPriceBookDetailSummaryDAO().getPriceBookDetailSummaryByPriceBookIdCompanyId(priceBookId, companyId);
	}


}
