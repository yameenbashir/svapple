/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.CashManagment;
import com.dowhile.dao.CashManagmentDAO;
import com.dowhile.service.CashManagmentService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class CashManagmentServiceImpl implements CashManagmentService{

	private CashManagmentDAO cashManagmentDAO;

	/**
	 * @return the cashManagmentDAO
	 */
	public CashManagmentDAO getCashManagmentDAO() {
		return cashManagmentDAO;
	}

	/**
	 * @param cashManagmentDAO the cashManagmentDAO to set
	 */
	public void setCashManagmentDAO(CashManagmentDAO cashManagmentDAO) {
		this.cashManagmentDAO = cashManagmentDAO;
	}

	@Override
	public CashManagment addCashManagment(CashManagment cashManagment, int companyId) {
		// TODO Auto-generated method stub
		return cashManagmentDAO.addCashManagment(cashManagment,companyId);
	}

	@Override
	public CashManagment updateCashManagment(CashManagment cashManagment, int companyId) {
		// TODO Auto-generated method stub
		return cashManagmentDAO.updateCashManagment(cashManagment,companyId);
	}

	@Override
	public List<CashManagment> getCashManagmentDailyRegister(int companyId,
			int OutletId, int dailyRegisterid) {
		// TODO Auto-generated method stub
		return cashManagmentDAO.getCashManagmentDailyRegister(companyId, OutletId, dailyRegisterid);
	} 

	
}
