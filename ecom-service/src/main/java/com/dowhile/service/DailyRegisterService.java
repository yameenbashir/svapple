/**
 * 
 */
package com.dowhile.service;

import com.dowhile.DailyRegister;

/**
 * @author Yameen Bashir
 *
 */
public interface DailyRegisterService {

	DailyRegister addDailyRegister(DailyRegister dailyRegister, int companyId);
	DailyRegister updateDailyRegister(DailyRegister dailyRegister, int companyId);
	DailyRegister getOpenDailyRegister(int companyId, int OutletId,int userId);
	DailyRegister getDailyRegisterById( int dailyRegisterId, int companyId);
	DailyRegister getLastColsedDailyRegister( int companyId, int OutletId);

}
