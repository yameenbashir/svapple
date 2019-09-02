/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.PriceBookDetail;

/**
 * @author Yameen Bashir
 *
 */
public interface PriceBookDetailDAO {

	PriceBookDetail addPriceBookDetail(PriceBookDetail priceBookDetail);
	PriceBookDetail updatePriceBookDetail(PriceBookDetail priceBookDetail);
	void updatePriceBookDetailList(List<PriceBookDetail> priceBookDetails);
	boolean deletePriceBookDetail(PriceBookDetail priceBookDetail);
	PriceBookDetail getPriceBookDetailByPriceBookDetailIdCompanyId(int priceBookDetail,int companyId);
	List<PriceBookDetail> getAllPriceBookDetailsByCompanyId(int companyId);
	List<PriceBookDetail> getPriceBookDetailsByPriceBookId(int pricebookId);
	List<PriceBookDetail> getPriceBookDetailsByPriceBookIdGroupByUuidCompanyId(int pricebookId,String uUId,int companyId);
	void addPriceBookDetail(List<PriceBookDetail> priceBookDetails);
}
