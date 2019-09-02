/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.Currency;
import com.dowhile.TimeZone;

/**
 * @author Hassan
 *
 */
public interface TimeZoneDAO {
	
	List<TimeZone> GetAllTimeZones();
	TimeZone getTimeZoneByTimeZoneId(int timeZoneId);
	

}
