/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.Currency;
import com.dowhile.Register;
import com.dowhile.TimeZone;

/**
 * @author Hassan 
 *
 */
public interface TimeZoneService {

	public List<TimeZone> GetAllTimeZones();
	TimeZone getTimeZoneByTimeZoneId(int timeZone);

}
