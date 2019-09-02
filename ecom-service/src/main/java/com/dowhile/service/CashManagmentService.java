/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.CashManagment;

/**
 * @author Yameen Bashir
 *
 */
public interface CashManagmentService {

	CashManagment addCashManagment(CashManagment cashManagment, int companyId);
	CashManagment updateCashManagment(CashManagment cashManagment, int companyId);
	List<CashManagment> getCashManagmentDailyRegister(int companyId, int OutletId, int dailyRegisterid);
	
}
