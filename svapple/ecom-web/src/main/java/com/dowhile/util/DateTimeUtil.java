/**
 * 
 */
package com.dowhile.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author Yameen Bashir
 *
 */
public class DateTimeUtil {

//	private static Configuration configuration =null;
//	
//	@Resource
//	private static ConfigurationService configurationService;
//	configuration = configurationService.getConfigurationByPropertyNameByCompanyId("HIDE_ORIGNAL_PRICE_INFO_REPORTS",currentUser.getCompany().getCompanyId());

	public static String convertDBDateTimeToGuiFormat(Date dbDateTime){
		
		try{
			
			Calendar localTIme = new GregorianCalendar(TimeZone.getTimeZone("UTC +7"));
			localTIme.setTime(dbDateTime);
//			System.out.println(localTIme.getTimeZone());
			localTIme.add(Calendar.HOUR_OF_DAY, 9);//For Live Deployment
//			localTIme.add(Calendar.HOUR_OF_DAY, 0);//For Local Deployment
			 DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dbDateTimeTOString = inputFormat.format(localTIme.getTime()). toString();
			dbDateTimeTOString = dbDateTimeTOString.substring(0, dbDateTimeTOString.length());
			
			DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
			return outputFormat.format(inputFormat.parse(dbDateTimeTOString));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return null;
	}
	
	public static Date convertGuiDateFormatYYYYMMDDToDBDateFormat(String guiDate){
		
		try{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			return dateFormat.parse(guiDate);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return null;
	}
	public static void main(String args[]){
		
		try{
			
//			String start_dt = "February 14, 2017";
//			DateFormat parser = new SimpleDateFormat("MMM dd, yyyy"); 
//			Date date = (Date) parser.parse(start_dt);
//			System.out.println(date);
			 
			// System.out.println(convertDBDateTimeToGuiFormat(new Date()));

			String original = "2016-05-19 13:43:05.0";//2016-05-19 03:18:11.0
			original = original.substring(0, original.lastIndexOf("."));
			System.out.println(original);
			
			String input = "2016-05-19 12:43:05";
			DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//DateFormat outputFormat = new SimpleDateFormat("KK:mm a");
			DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
			
			System.out.println(outputFormat.format(inputFormat.parse(original)));
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
