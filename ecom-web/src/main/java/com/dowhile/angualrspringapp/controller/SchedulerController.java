/**
 * 
 */
package com.dowhile.angualrspringapp.controller;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Date;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * @author HafizYameenBashir
 *
 */

public class SchedulerController {

	 String  emailHostStr ;
	 String emailHostSSLStr ;
	 String emailPortStr ;
	 String emailUserStr ;
	 String emailPasswordStr;
	 String emailSubjectStr ;
	 String emailSendCCStr ;
	 String emailSendCCTOStr;
	 String emailSendBCCTOStr;
	 String emailSendBCCStr ;
	 String emailFROMStr;
	public  void populatEmailConfig(){
		 emailUserStr  = "admin@shopvitals.com";
		 emailSubjectStr  = "Script Execution";
		 emailSendCCStr = "zafar@shopvitals.com";
		 emailSendCCTOStr = "Y";
		 emailSendBCCTOStr  = "";
		 emailSendBCCStr = "";
		 emailFROMStr = "admin@shopvitals.com";
		 emailPasswordStr = "abcd1234@";
	}
	
	 @Scheduled(cron="0 50 23 * * ?")
	    public void schedulerForMVExecution()
	    {
		 //0 0 1 * * *
		 //* 0/60 * * * *
		 //*/90 * * * * ? execute after every 5 minunte
		 //cron="*/20 * * * * ?" execute at every 20 seconds
		/* 		 0 0 12 * * ?		Fire at 12:00 PM (noon) every day
				 0 15 10 ? * *		Fire at 10:15 AM every day
				 0 15 10 * * ?		Fire at 10:15 AM every day
				 0 15 10 * * ? *	Fire at 10:15 AM every day
				 0 15 10 * * ? 2005	Fire at 10:15 AM every day during the year 2005
				 0 * 14 * * ?	Fire every minute starting at 2:00 PM and ending at 2:59 PM, every day
				 0 0/5 14 * * ?	Fire every 5 minutes starting at 2:00 PM and ending at 2:55 PM, every day
				 0 0/5 14,18 * * ?	Fire every 5 minutes starting at 2:00 PM and ending at 2:55 PM, AND fire every 5 minutes starting at 6:00 PM and ending at 6:55 PM, every day
				 0 0-5 14 * * ?	Fire every minute starting at 2:00 PM and ending at 2:05 PM, every day
				 0 10,44 14 ? 3 WED	Fire at 2:10 PM and at 2:44 PM every Wednesday in the month of March
				 0 15 10 ? * MON-FRI	Fire at 10:15 AM every Monday, Tuesday, Wednesday, Thursday and Friday
				 0 15 10 15 * ?	Fire at 10:15 AM on the 15th day of every month
				 0 15 10 L * ?	Fire at 10:15 AM on the last day of every month
				 0 15 10 ? * 6L	Fire at 10:15 AM on the last Friday of every month
				 0 15 10 ? * 6L	Fire at 10:15 AM on the last Friday of every month
				 0 15 10 ? * 6L 2002-2005	Fire at 10:15 AM on every last friday of every month during the years 2002, 2003, 2004, and 2005
				 0 15 10 ? * 6#3	Fire at 10:15 AM on the third Friday of every month
				 0 0 12 1/5 * ?	Fire at 12 PM (noon) every 5 days every month, starting on the first day of the month
				 0 11 11 11 11 ?	Fire every November 11 at 11:11 AM*/
	        System.out.println("Method schedulerForMVExecution() execution started at time :: "+ new Date());
	        try{  
	        	Class.forName("com.mysql.jdbc.Driver");  
	        	Connection con=(Connection) DriverManager.getConnection(  
	        	"jdbc:mysql://localhost:3306/ecom","root","123456");  
	        	//here ecom is database name, root is username and password  
	        	Statement stmt=(Statement) con.createStatement();  
	        	ResultSet rs=stmt.executeQuery("CALL MVExecution()");  
	        	/*while(rs.next())  
	        	System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  */
	        	con.close();  
	        	System.out.println("Method schedulerForMVExecution() execution completed at time :: "+ new Date());
	        	sendEmailDemo("bashir@shopvitals.com", "Yameen/Zafar");
	        	}catch(Exception e){ System.out.println(e);} 
	        
	    }
	 public  boolean sendEmailDemo (String toEmail,String name) {
			populatEmailConfig();
			Email email = new SimpleEmail();
			email.setHostName("smtp.zoho.eu");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator(emailUserStr, emailPasswordStr));
					
			try {
				email.setFrom(emailFROMStr);
				email.setSubject(emailSubjectStr);
				email.setContent(
						"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"+
						"<html xmlns=\"http://www.w3.org/1999/xhtml\">"+
						"<head>"+
						"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"+
						"<title>Scipt Executed Successfully</title>"+
						"</head>"+

						"<body>"+
						"<table width=\"700\" border=\"0\" cellpadding=\"15\" cellspacing=\"0\" align=\"center\" style=\"font-size:14px;font-family:Arial, Helvetica, sans-serif;\">"+
						  "<tr>"+
						   " <td height=\"52px\" bgcolor=\"#3c8dbc\" style=\"font-size:16px;color:white;\">ShopVitals</td>"+
						   " <td height=\"52px\" bgcolor=\"#3c8dbc\" ><img src=\"https://web.ShopVitals.com/app/resources/images/150x110.png\" Style=\"margin-left: 65%;max-height: 60px;\" /></td>"+
									
						  "</tr>"+
						  "<tr>"+
						   " <td colspan=\"2\" bgcolor=\"#f7f7f7\">"+
						    	"Dear "+name+","+
						        "<br /><br />"+
						         "This email has been sent from <b>ShopVitals</b>.<br><br>"+
						        "Srcipt has been executed successfully."
						        
						        + "<br />Happy working."
						        + "<br /><b>ShopVitals Team</b><br>"+
						    "</td>"+
						  "</tr>"+
						  "<tr>"+
						    "<td style=\"font-size:12px;\"><a href=\"#\" target=\"_blank\">Contact info</a>�|�<a href=\"#\" target=\"_blank\">Privacy policy</a><br />"+
						      "<br />"+
						    "�2019 ShopVitals.</td>"+
						    "<td>&nbsp;</td>"+
						  "</tr>"+
						"</table>"+
						"</body>"
						
								,
						"text/html");
				email.addTo(toEmail);
				//email.setTo(aCollection)
				
				if (emailSendBCCTOStr.equals("Y")) {
					email.addBcc(emailSendBCCStr);
				}
				if (emailSendCCTOStr.equals("Y")) {
					email.addCc(emailSendCCStr);
				}
				email.setSSLOnConnect(true);
				
				
//				if(userDAO.updatePassword(toEmail, newPassword)){
//					email.send();
//					return true;
//				}
				email.send();
			} catch (Exception e) {
				
				e.printStackTrace();
				return false;
			}
			return true;
		}
	 public static void main (String args []){
		 SchedulerController obj = new SchedulerController();
		 obj.sendEmailDemo("bashir@shopvitals.com", "Yameen/Zafar");
	 }
}