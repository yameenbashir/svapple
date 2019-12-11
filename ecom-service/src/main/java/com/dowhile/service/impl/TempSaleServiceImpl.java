/**
 * 
 */
package com.dowhile.service.impl;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.beans.ReportParams;
import com.dowhile.beans.TableData;
import com.dowhile.dao.TempSaleDAO;
import com.dowhile.service.TempSaleService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class TempSaleServiceImpl implements TempSaleService{

	private TempSaleDAO tempSaleDAO;
	
	/**
	 * @return the tempSaleDAO
	 */
	public TempSaleDAO getTempSaleDAO() {
		return tempSaleDAO;
	}

	/**
	 * @param tempSaleDAO the tempSaleDAO to set
	 */
	public void setTempSaleDAO(TempSaleDAO tempSaleDAO) {
		this.tempSaleDAO = tempSaleDAO;
	}

	
	@Override
	public TableData getSalesReportByStartDateEndDate(Date startDate,
			Date endDate, int companyId) {
		// TODO Auto-generated method stub
		return getTempSaleDAO().getSalesReportByStartDateEndDate(startDate, endDate, companyId);
	}

	@Override
	public TableData getAllTempSaleByCompanyId(ReportParams reportParams) {
		// TODO Auto-generated method stub
		return getTempSaleDAO().getAllTempSaleByCompanyId(reportParams);
	}

	@Override
	public boolean runDailyScript() {
		// TODO Auto-generated method stub
		return getTempSaleDAO().runDailyScript();
	}

	@Override
	public TableData getTodaySalesByCompanyIdOutletIdDate(int companyId,
			int outletId, Date date, boolean isHeadOffice) {
		// TODO Auto-generated method stub
		return getTempSaleDAO().getTodaySalesByCompanyIdOutletIdDate(companyId, outletId, date, isHeadOffice);
	}

}
