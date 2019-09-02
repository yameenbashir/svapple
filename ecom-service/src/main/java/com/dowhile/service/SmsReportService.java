/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.SmsReport;

/**
 * @author Hafiz Yameen Bashir
 *
 */
public interface SmsReportService {

	List<SmsReport> getAllSmsByCompanyIdOutletId(int companyId, int outletId);
}
