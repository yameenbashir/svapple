/**
 * 
 */
package com.dowhile.service;

import java.util.List;
import java.util.Map;

import com.dowhile.PriceBookDetail;

/**
 * @author Yameen Bashir
 *
 */
public interface PriceBookDetailService {

	PriceBookDetail addPriceBookDetail(PriceBookDetail priceBookDetail);
	PriceBookDetail updatePriceBookDetail(PriceBookDetail priceBookDetail);
	boolean deletePriceBookDetail(PriceBookDetail priceBookDetail);
	PriceBookDetail getPriceBookDetailByPriceBookDetailIdCompanyId(int priceBookDetail,int companyId);
	List<PriceBookDetail> getAllPriceBookDetailsByCompanyId(int companyId);
	List<PriceBookDetail> getPriceBookDetailsByPriceBookId(int pricebookId);
	List<PriceBookDetail> getPriceBookDetailsByPriceBookIdGroupByUuidCompanyId(int pricebookId,String uUId,int companyId);
	void updatePriceBookDetailList(List<PriceBookDetail> priceBookDetails);
	Map<Integer, PriceBookDetail> getPriceBookDetailMapByPriceBookDetailIdCompanyId(int companyId);
	Map<Integer, List<PriceBookDetail>> getPriceBookDetailMapByPriceBookIdCompanyId(int companyId);
	void addPriceBookDetail(List<PriceBookDetail> priceBookDetails);
}
