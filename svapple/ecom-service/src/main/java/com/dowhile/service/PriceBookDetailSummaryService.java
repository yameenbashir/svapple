/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.PriceBookDetailSummary;

/**
 * @author Yameen Bashir
 *
 */
public interface PriceBookDetailSummaryService {

	List<PriceBookDetailSummary> getPriceBookDetailSummaryByPriceBookIdCompanyIdGroupByUuId(int priceBookId,int companyId);
	List<PriceBookDetailSummary> getPriceBookDetailSummaryByPriceBookIdCompanyId(int priceBookId,int companyId);
}
