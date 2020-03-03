/**
 * 
 */
package com.dowhile.util;

import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yameen Bashir
 *
 */
public class DateTimeUtil {

	public static String convertDBDateTimeToGuiFormat(Date dbDateTime){
		
		try{
			String dbDateTimeTOString = dbDateTime.toString();
			dbDateTimeTOString = dbDateTimeTOString.substring(0, dbDateTimeTOString.lastIndexOf("."));
			
			DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy KK:mm a");
			return outputFormat.format(inputFormat.parse(dbDateTimeTOString));
		}catch(Exception ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		
		return null;
	}
	public static void main(String args[]){
		
		try{
			
			String start_dt = "February 14, 2017";
			DateFormat parser = new SimpleDateFormat("MMM dd, yyyy"); 
			Date date = (Date) parser.parse(start_dt);
			System.out.println(date);
			
			/*String original = "2016-05-19 13:43:05.0";//2016-05-19 03:18:11.0
			original = original.substring(0, original.lastIndexOf("."));
			System.out.println(original);
			
			String input = "2016-05-19 12:43:05";
			DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//DateFormat outputFormat = new SimpleDateFormat("KK:mm a");
			DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy KK:mm a");
			
			System.out.println(outputFormat.format(inputFormat.parse(original)));*/
			
		}catch(Exception ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
	}
}
