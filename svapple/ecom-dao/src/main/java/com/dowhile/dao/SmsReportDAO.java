/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.ProductSummmary;
import com.dowhile.SmsReport;

/**
 * @author Yameen Bashir
 *
 */
public interface SmsReportDAO {

	List<SmsReport> getAllSmsByCompanyIdOutletId(int companyId, int outletId);
}
