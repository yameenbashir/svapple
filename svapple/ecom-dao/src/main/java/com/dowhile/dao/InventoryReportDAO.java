/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.InventoryReport;

/**
 * @author Yameen Bashir
 *
 */
public interface InventoryReportDAO {

	List<InventoryReport> getInventoryReportByCompanyId(int companyId);
	List<InventoryReport> getInventoryReportByOutletIdCompanyId(int outletId,int companyId);
}
