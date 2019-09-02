/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.RegisterReport;
import com.dowhile.dao.RegisterReportDAO;
import com.dowhile.service.RegisterReportService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class RegisterReportServiceImpl implements RegisterReportService{

	private RegisterReportDAO registerReportDAO;
	
	/**
	 * @return the registerReportDAO
	 */
	public RegisterReportDAO getRegisterReportDAO() {
		return registerReportDAO;
	}

	/**
	 * @param registerReportDAO the registerReportDAO to set
	 */
	public void setRegisterReportDAO(RegisterReportDAO registerReportDAO) {
		this.registerReportDAO = registerReportDAO;
	}

	@Override
	public List<RegisterReport> getRegisterReportByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		return getRegisterReportDAO().getRegisterReportByCompanyId(companyId);
	}

	@Override
	public List<RegisterReport> getRegisterReportByOutletIdCompanyId(
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getRegisterReportDAO().getRegisterReportByOutletIdCompanyId(outletId, companyId);
	}

}
