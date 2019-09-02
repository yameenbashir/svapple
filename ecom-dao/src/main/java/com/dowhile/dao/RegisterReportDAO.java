/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.RegisterReport;

/**
 * @author Yameen Bashir
 *
 */
public interface RegisterReportDAO {

	List<RegisterReport> getRegisterReportByCompanyId(int companyId);
	List<RegisterReport> getRegisterReportByOutletIdCompanyId(int outletId,int companyId);
}
