/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.InventoryDetailReport;

/**
 * @author Hafiz Bashir
 *
 */
public interface InventoryDetailReportDAO {

	List<InventoryDetailReport> getInventoryDetailReportByCompanyId(int companyId);
	List<InventoryDetailReport> getInventoryDetailReportByOutletIdCompanyId(int outletId,int companyId);
}
