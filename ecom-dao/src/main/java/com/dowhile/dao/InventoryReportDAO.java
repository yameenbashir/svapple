/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.InventoryHealthCheckReport;
import com.dowhile.InventoryReport;
import com.dowhile.beans.ReportParams;
import com.dowhile.beans.TableData;

/**
 * @author Yameen Bashir
 *
 */
public interface InventoryReportDAO {

	List<InventoryReport> getInventoryReportByCompanyId(int companyId);
	List<InventoryReport> getInventoryReportByOutletIdCompanyId(int outletId,int companyId);
	TableData getInventoryReportByCompanyIdOutletId(int companyId,int outletId,boolean isHeadOffice);
	List<InventoryHealthCheckReport> getInventoryHealthCheckReportByCompanyIdOutletId(int companyId,int outletId);
}
