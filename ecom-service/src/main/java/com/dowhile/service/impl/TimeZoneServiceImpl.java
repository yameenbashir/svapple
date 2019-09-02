/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Currency;
import com.dowhile.Register;
import com.dowhile.TimeZone;
import com.dowhile.dao.CurrencyDAO;
import com.dowhile.dao.RegisterDAO;
import com.dowhile.dao.TimeZoneDAO;
import com.dowhile.service.CurrencyService;
import com.dowhile.service.RegisterService;
import com.dowhile.service.TimeZoneService;

/**
 * @author Hassan Rasheed
 *
 */
@Transactional(readOnly = false)
public class TimeZoneServiceImpl implements TimeZoneService{
	
	private TimeZoneDAO timeZoneDAO;

	
	
	@Override
	public List<TimeZone> GetAllTimeZones() {
		return getTimeZoneDAO().GetAllTimeZones();
	}



	/**
	 * @return the timeZoneDAO
	 */
	public TimeZoneDAO getTimeZoneDAO() {
		return timeZoneDAO;
	}



	/**
	 * @param timeZoneDAO the timeZoneDAO to set
	 */
	public void setTimeZoneDAO(TimeZoneDAO timeZoneDAO) {
		this.timeZoneDAO = timeZoneDAO;
	}



	@Override
	public TimeZone getTimeZoneByTimeZoneId(int timeZone) {
		// TODO Auto-generated method stub
		return getTimeZoneDAO().getTimeZoneByTimeZoneId(timeZone);
	}

	




	
	
}
