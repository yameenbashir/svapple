/**
 * 
 */
package com.dowhile.service;

import java.util.Date;

import com.dowhile.beans.ReportParams;
import com.dowhile.beans.TableData;

/**
 * @author Yameen Bashir
 *
 */
public interface TempSaleService {

	TableData getAllTempSaleByCompanyId(ReportParams reportParams);
	TableData getSalesReportByStartDateEndDate(Date startDate,Date endDate,int companyId);
	boolean runDailyScript();
	TableData getTodaySalesByCompanyIdOutletIdDate(int companyId, int outletId,Date date,boolean isHeadOffice);
}
