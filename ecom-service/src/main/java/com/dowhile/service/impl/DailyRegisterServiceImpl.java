/**
 * 
 */
package com.dowhile.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.DailyRegister;
import com.dowhile.dao.DailyRegisterDAO;
import com.dowhile.service.DailyRegisterService;

/**
 * @author hassan
 *
 */
@Transactional(readOnly = false)
public class DailyRegisterServiceImpl implements DailyRegisterService{

	private DailyRegisterDAO dailyRegisterDAO;

	public DailyRegisterDAO getDailyRegisterDAO() {
		return dailyRegisterDAO;
	}

	public void setDailyRegisterDAO(DailyRegisterDAO dailyRegisterDAO) {
		this.dailyRegisterDAO = dailyRegisterDAO;
	}

	@Override
	public com.dowhile.DailyRegister addDailyRegister(
			com.dowhile.DailyRegister dailyRegister, int companyId) {
		// TODO Auto-generated method stub
		return dailyRegisterDAO.addDailyRegister(dailyRegister,companyId) ;
	}

	@Override
	public com.dowhile.DailyRegister updateDailyRegister(
			com.dowhile.DailyRegister dailyRegister, int companyId) {
		// TODO Auto-generated method stub
		return dailyRegisterDAO.updateDailyRegister(dailyRegister,companyId);
	}



	@Override
	public com.dowhile.DailyRegister getOpenDailyRegister(int companyId,
			int OutletId) {
		// TODO Auto-generated method stub
		return dailyRegisterDAO.getOpenDailyRegister(companyId, OutletId);
	}

	@Override
	public DailyRegister getDailyRegisterById(int dailyRegisterId, int companyId) {
		// TODO Auto-generated method stub
		return dailyRegisterDAO.getDailyRegisterById(dailyRegisterId,companyId);
	}

	@Override
	public DailyRegister getLastColsedDailyRegister(int companyId, int OutletId) {
		// TODO Auto-generated method stub
		return dailyRegisterDAO.getLastColsedDailyRegister(companyId, OutletId);
	}
	
	
}
