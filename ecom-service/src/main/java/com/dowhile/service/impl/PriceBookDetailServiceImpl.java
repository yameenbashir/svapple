/**
 * 
 */
package com.dowhile.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.PriceBookDetail;
import com.dowhile.dao.PriceBookDetailDAO;
import com.dowhile.service.PriceBookDetailService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class PriceBookDetailServiceImpl implements PriceBookDetailService{

	private PriceBookDetailDAO priceBookDetailDAO;
	
	/**
	 * @return the priceBookDetailDAO
	 */
	public PriceBookDetailDAO getPriceBookDetailDAO() {
		return priceBookDetailDAO;
	}

	/**
	 * @param priceBookDetailDAO the priceBookDetailDAO to set
	 */
	public void setPriceBookDetailDAO(PriceBookDetailDAO priceBookDetailDAO) {
		this.priceBookDetailDAO = priceBookDetailDAO;
	}

	@Override
	public PriceBookDetail addPriceBookDetail(PriceBookDetail priceBookDetail) {
		// TODO Auto-generated method stub
		return getPriceBookDetailDAO().addPriceBookDetail(priceBookDetail);
	}

	@Override
	public PriceBookDetail updatePriceBookDetail(PriceBookDetail priceBookDetail) {
		// TODO Auto-generated method stub
		return getPriceBookDetailDAO().updatePriceBookDetail(priceBookDetail);
	}

	@Override
	public boolean deletePriceBookDetail(PriceBookDetail priceBookDetail) {
		// TODO Auto-generated method stub
		return getPriceBookDetailDAO().deletePriceBookDetail(priceBookDetail);
	}

	@Override
	public PriceBookDetail getPriceBookDetailByPriceBookDetailIdCompanyId(
			int priceBookDetail, int companyId) {
		// TODO Auto-generated method stub
		return getPriceBookDetailDAO().getPriceBookDetailByPriceBookDetailIdCompanyId(priceBookDetail, companyId);
	}


	@Override
	public List<PriceBookDetail> getAllPriceBookDetailsByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		return getPriceBookDetailDAO().getAllPriceBookDetailsByCompanyId(companyId);
	}

	@Override
	public List<PriceBookDetail> getPriceBookDetailsByPriceBookId(int pricebookId) {
		// TODO Auto-generated method stub
				return getPriceBookDetailDAO().getPriceBookDetailsByPriceBookId(pricebookId);
	}

	@Override
	public List<PriceBookDetail> getPriceBookDetailsByPriceBookIdGroupByUuidCompanyId(
			int pricebookId, String uUId, int companyId) {
		// TODO Auto-generated method stub
		return getPriceBookDetailDAO().getPriceBookDetailsByPriceBookIdGroupByUuidCompanyId(pricebookId, uUId, companyId);
	}

	@Override
	public Map<Integer, PriceBookDetail> getPriceBookDetailMapByPriceBookDetailIdCompanyId(
			int companyId) {
		Map<Integer, PriceBookDetail> map = new HashMap<Integer, PriceBookDetail>();
		List<PriceBookDetail> priceBookDetails = getPriceBookDetailDAO().getAllPriceBookDetailsByCompanyId(companyId);
		if(priceBookDetails!=null){
			for(PriceBookDetail priceBook : priceBookDetails){
				map.put(priceBook.getPriceBookDetailId(), priceBook);
			}
		}
		return map;
	}

	@Override
	public void addPriceBookDetail(List<PriceBookDetail> priceBookDetails) {
	  getPriceBookDetailDAO().addPriceBookDetail(priceBookDetails);
		
	}

	@Override
	public void updatePriceBookDetailList(List<PriceBookDetail> priceBookDetails) {
		// TODO Auto-generated method stub
		getPriceBookDetailDAO().updatePriceBookDetailList(priceBookDetails);
	}

	@Override
	public Map<Integer, List<PriceBookDetail>> getPriceBookDetailMapByPriceBookIdCompanyId(
			int companyId) {
		Map<Integer, List<PriceBookDetail>> map = new HashMap<Integer, List<PriceBookDetail>>();
		List<PriceBookDetail> priceBookDetails = getPriceBookDetailDAO().getAllPriceBookDetailsByCompanyId(companyId);
		if(priceBookDetails!=null){
			for(PriceBookDetail priceBook : priceBookDetails){
				List<PriceBookDetail> bookDetails = new ArrayList<PriceBookDetail>();
				
				if(map.get(priceBook.getPriceBook().getPriceBookId())==null){
					bookDetails.add(priceBook);
					map.put(priceBook.getPriceBook().getPriceBookId(), bookDetails);
				}else{
					bookDetails = map.get(priceBook.getPriceBook().getPriceBookId());
					bookDetails.add(priceBook);
					map.put(priceBook.getPriceBook().getPriceBookId(), bookDetails);
				}
				
			}
		}
		return map;
	}

	@Override
	public Map<Integer, PriceBookDetail> getAllActivePriceBookDetailsMapByPriceBookIdCompanyId(
			int pricebookId, int companyId) {
		// TODO Auto-generated method stub
		Map<Integer, PriceBookDetail> map = new HashMap<Integer, PriceBookDetail>();
		List<PriceBookDetail> priceBookDetails = getPriceBookDetailDAO().getAllActivePriceBookDetailsByPriceBookIdCompanyId(pricebookId, companyId);
		if(priceBookDetails!=null){
			for(PriceBookDetail priceBook : priceBookDetails){
				map.put(priceBook.getPriceBookDetailId(), priceBook);
			}
		}
		return map;
	}

}
