/**
 * 
 */
package com.dowhile.dao;

import com.dowhile.DailyRegister;

/**
 * @author Yameen Bashir
 *
 */
public interface DailyRegisterDAO {

	DailyRegister addDailyRegister(DailyRegister dailyRegister, int companyId);
	DailyRegister updateDailyRegister(DailyRegister dailyRegister, int companyId);
	DailyRegister getOpenDailyRegister(int companyId, int OutletId);
	DailyRegister getDailyRegisterById( int dailyRegisterId, int companyId);
	DailyRegister getLastColsedDailyRegister( int companyId, int OutletId);
	
}
