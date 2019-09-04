/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.CompositeProductHistory;

/**
 * @author Hafiz Yameen Bashir
 *
 */
public interface CompositeProductHistoryDAO {

	CompositeProductHistory addCompositeProductHistory(CompositeProductHistory compositeProductHistory);
	CompositeProductHistory updateCompositeProductHistory(CompositeProductHistory compositeProductHistory);
	boolean deleteCompositeProductHistory(CompositeProductHistory compositeProductHistory);
	List<CompositeProductHistory> getAllCompositeProductHistoryByCompanyId(int companyId);
}
