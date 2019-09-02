/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.RegisterReport;

/**
 * @author Yameen Bashir
 *
 */
public interface RegisterReportService {

	List<RegisterReport> getRegisterReportByCompanyId(int companyId);
	List<RegisterReport> getRegisterReportByOutletIdCompanyId(int outletId,int companyId);
}
