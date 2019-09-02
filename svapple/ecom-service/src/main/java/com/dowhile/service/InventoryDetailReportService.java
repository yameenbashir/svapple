/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.InventoryDetailReport;

/**
 * @author Hafiz Bashir
 *
 */
public interface InventoryDetailReportService {

	List<InventoryDetailReport> getInventoryDetailReportByCompanyId(int companyId);
	List<InventoryDetailReport> getInventoryDetailReportByOutletIdCompanyId(int outletId,int companyId);

}
