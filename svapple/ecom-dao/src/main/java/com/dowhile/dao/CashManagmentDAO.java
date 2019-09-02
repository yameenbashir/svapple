/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.CashManagment;

/**
 * @author Yameen Bashir
 *
 */
public interface CashManagmentDAO {

	CashManagment addCashManagment(CashManagment cashManagment, int companyId);
	CashManagment updateCashManagment(CashManagment cashManagment, int companyId);
	List<CashManagment> getCashManagmentDailyRegister(int companyId, int OutletId, int dailyRegisterid);
	
}
