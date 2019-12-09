/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.InventoryReport;
import com.dowhile.beans.TableData;

/**
 * @author Yameen Bashir
 *
 */
public interface InventoryReportService {

	List<InventoryReport> getInventoryReportByCompanyId(int companyId);
	List<InventoryReport> getInventoryReportByOutletIdCompanyId(int outletId,int companyId);
	TableData getInventoryReportByCompanyIdOutletId(int companyId,int outletId,boolean isHeadOffice);
}
