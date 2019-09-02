/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.SmsReport;
import com.dowhile.dao.SmsReportDAO;
import com.dowhile.service.SmsReportService;

/**
 * @author Hafiz Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class SmsReportServiceImpl implements SmsReportService{

	private SmsReportDAO smsReportDAO;
	
	/**
	 * @return the smsReportDAO
	 */
	public SmsReportDAO getSmsReportDAO() {
		return smsReportDAO;
	}

	/**
	 * @param smsReportDAO the smsReportDAO to set
	 */
	public void setSmsReportDAO(SmsReportDAO smsReportDAO) {
		this.smsReportDAO = smsReportDAO;
	}

	@Override
	public List<SmsReport> getAllSmsByCompanyIdOutletId(int companyId,
			int outletId) {
		// TODO Auto-generated method stub
		return getSmsReportDAO().getAllSmsByCompanyIdOutletId(companyId, outletId);
	}

}
