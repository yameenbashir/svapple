/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.PriceBookDetailSummary;

/**
 * @author Yameen Bashir
 *
 */
public interface PriceBookDetailSummaryDAO {

	List<PriceBookDetailSummary> getPriceBookDetailSummaryByPriceBookIdCompanyIdGroupByUuId(int priceBookId,int companyId);
	List<PriceBookDetailSummary> getPriceBookDetailSummaryByPriceBookIdCompanyId(int priceBookId,int companyId);
}
