/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.CompositeProductHistory;
import com.dowhile.dao.CompositeProductHistoryDAO;
import com.dowhile.service.CompositeProductHistoryService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class CompositeProductHistoryServiceImpl implements CompositeProductHistoryService{

	private CompositeProductHistoryDAO compositeProductHistoryDAO;

	/**
	 * @return the compositeProductHistoryDAO
	 */
	public CompositeProductHistoryDAO getCompositeProductHistoryDAO() {
		return compositeProductHistoryDAO;
	}

	/**
	 * @param compositeProductHistoryDAO the compositeProductHistoryDAO to set
	 */
	public void setCompositeProductHistoryDAO(
			CompositeProductHistoryDAO compositeProductHistoryDAO) {
		this.compositeProductHistoryDAO = compositeProductHistoryDAO;
	}

	@Override
	public CompositeProductHistory addCompositeProductHistory(
			CompositeProductHistory compositeProductHistory) {
		// TODO Auto-generated method stub
		return getCompositeProductHistoryDAO().addCompositeProductHistory(compositeProductHistory);
	}

	@Override
	public CompositeProductHistory updateCompositeProductHistory(
			CompositeProductHistory compositeProductHistory) {
		// TODO Auto-generated method stub
		return getCompositeProductHistoryDAO().updateCompositeProductHistory(compositeProductHistory);
	}

	@Override
	public boolean deleteCompositeProductHistory(
			CompositeProductHistory compositeProductHistory) {
		// TODO Auto-generated method stub
		return getCompositeProductHistoryDAO().deleteCompositeProductHistory(compositeProductHistory);
	}

	@Override
	public List<CompositeProductHistory> getAllCompositeProductHistoryByCompanyId(
			int companyId) {
		// TODO Auto-generated method stub
		return getCompositeProductHistoryDAO().getAllCompositeProductHistoryByCompanyId(companyId);
	}
}
