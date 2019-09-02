/**
 * 
 */
package com.dowhile.dao;

import java.util.Date;

import com.dowhile.beans.ReportParams;
import com.dowhile.beans.TableData;

/**
 * @author Yameen Bashir
 *
 */
public interface TempSaleDAO {

	TableData getAllTempSaleByCompanyId(ReportParams reportParams);
	TableData getSalesReportByStartDateEndDate(Date startDate,Date endDate,int companyId);
}
