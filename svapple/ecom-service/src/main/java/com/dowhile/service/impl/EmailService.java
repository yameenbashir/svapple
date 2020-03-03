/**
 * 
 */
package com.dowhile.service.impl;

import java.util.Random;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author imran.latif
 *
 */
@Transactional(readOnly = false)
public class EmailService {
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(EmailService.class);
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
		 emailSubjectStr  = "Welcome On Board by ShopVitals Team!";
		 emailSendCCStr = "";
		 emailSendCCTOStr = "";
		 emailSendBCCTOStr  = "";
		 emailSendBCCStr = "";
		 emailFROMStr = "admin@shopvitals.com";
		 emailPasswordStr = "abcd1234@";
	}
	public  long generateRandom(int length) {
	    Random random = new Random();
	    char[] digits = new char[length];
	    digits[0] = (char) (random.nextInt(9) + '1');
	    for (int i = 1; i < length; i++) {
	        digits[i] = (char) (random.nextInt(10) + '0');
	    }
	    return Long.parseLong(new String(digits));
	}
	
	public static  String createRandomPassword()
	{
	    // syntax we would like to generate is DIA123456-A1B34      
	    String val = "";      

	    // char (1), random A-Z
	    int ranChar = 65 + (new Random()).nextInt(90-65);
	    char ch = (char)ranChar;        
	    val += ch;      

	    // numbers (6), random 0-9
	    Random r = new Random();
	    int numbers = 100000 + (int)(r.nextFloat() * 899900);
	    val += String.valueOf(numbers);

	    // char or numbers (5), random 0-9 A-Z
	    for(int i = 0; i<2;){
	        int ranAny = 48 + (new Random()).nextInt(90-65);

	        if(!(57 < ranAny && ranAny<= 65)){
	        char c = (char)ranAny;      
	        val += c;
	        i++;
	        }

	    }

	    return val;
	}

	public  boolean sendEmailAddUser (String toEmail, String requestPath, String password,String firstName) {
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
					"<title>Welcome onBoard</title>"+
					"</head>"+

					"<body>"+
					"<table width=\"700\" border=\"0\" cellpadding=\"15\" cellspacing=\"0\" align=\"center\" style=\"font-size:14px;font-family:Arial, Helvetica, sans-serif;\">"+
					  "<tr>"+
					   " <td height=\"52px\" bgcolor=\"#3c8dbc\" style=\"font-size:16px;color:white;\">ShopVitals</td>"+
					   " <td height=\"52px\" bgcolor=\"#3c8dbc\" ><img src=\"https://web.ShopVitals.com/app/resources/images/150x110.png\" Style=\"margin-left: 65%;max-height: 60px;\" /></td>"+
								
					  "</tr>"+
					  "<tr>"+
					   " <td colspan=\"2\" bgcolor=\"#f7f7f7\">"+
					    	"Hello "+ firstName+","+
					        "<br /><br />"+
					         "This email has been sent from <b>ShopVitals</b>.<br><br>"+
					        "Your account is created for ShopVitals. We are excited to assist you in making your work a slick."
					        + "For login detail of your account, please contact system administrator."
					        
					        + "<br />Happy working."
					        + "<br /><b>ShopVitals Team</b><br>"+
					    "</td>"+
					  "</tr>"+
					  "<tr>"+
					    "<td style=\"font-size:12px;\"><a href=\"#\" target=\"_blank\">Contact info</a> | <a href=\"#\" target=\"_blank\">Privacy policy</a><br />"+
					      "<br />"+
					    "©2018 ShopVitals.</td>"+
					    "<td>&nbsp;</td>"+
					  "</tr>"+
					"</table>"+
					"</body>"
					
							,
					"text/html");
			email.addTo(toEmail);
			if (emailSendBCCTOStr.equals("Y")) {
				email.addBcc(emailSendBCCStr,"imisweet1@gmail.com","yameen.like@gmail.com");
			}
			if (emailSendCCTOStr.equals("Y")) {
				email.addCc(emailSendCCStr);
			}
			email.setSSLOnConnect(true);
			
			
//			if(userDAO.updatePassword(toEmail, newPassword)){
//				email.send();
//				return true;
//			}
			email.send();
		} catch (Exception e) {
			LOGGER.error("Unable to send Email: "+ e.getMessage());
			e.printStackTrace();logger.error(e.getMessage(),e);
			return false;
		}
		return true;
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
					"<title>Welcome onBoard</title>"+
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
					        "Your request has been received for ShopVitals Demo. We are excited to assist you in making your work a slick."
					        + "For Further detail of your account, our team will reach you very soon."
					        
					        + "<br />Happy working."
					        + "<br /><b>ShopVitals Team</b><br>"+
					    "</td>"+
					  "</tr>"+
					  "<tr>"+
					    "<td style=\"font-size:12px;\"><a href=\"#\" target=\"_blank\">Contact info</a> | <a href=\"#\" target=\"_blank\">Privacy policy</a><br />"+
					      "<br />"+
					    "©2018 ShopVitals.</td>"+
					    "<td>&nbsp;</td>"+
					  "</tr>"+
					"</table>"+
					"</body>"
					
							,
					"text/html");
			email.addTo(toEmail);
			if (emailSendBCCTOStr.equals("Y")) {
				email.addBcc(emailSendBCCStr);
			}
			if (emailSendCCTOStr.equals("Y")) {
				email.addCc(emailSendCCStr);
			}
			email.setSSLOnConnect(true);
			
			
//			if(userDAO.updatePassword(toEmail, newPassword)){
//				email.send();
//				return true;
//			}
			email.send();
		} catch (Exception e) {
			LOGGER.error("Unable to send Email: "+ e.getMessage());
			e.printStackTrace();logger.error(e.getMessage(),e);
			return false;
		}
		return true;
	}
	public  boolean sendEmail (String toEmail, String emailSubject, String emailText,String requestPath) {
		populatEmailConfig();
		Email email = new SimpleEmail();
		email.setHostName("smtp.zoho.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator(emailUserStr, emailPasswordStr));
		String userName = "";
		
		try {
			email.setFrom(emailFROMStr);
			email.setSubject(emailSubject);
			email.setContent(
					"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"+
					"<html xmlns=\"http://www.w3.org/1999/xhtml\">"+
					"<head>"+
					"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"+
					"<title>Password Change</title>"+
					"</head>"+

					"<body>"+
					"<table width=\"700\" border=\"0\" cellpadding=\"15\" cellspacing=\"0\" align=\"center\" style=\"font-size:14px;font-family:Arial, Helvetica, sans-serif;\">"+
					  "<tr>"+
					   " <td height=\"52px\" bgcolor=\"#3c8dbc\" >ShopVitals</td>"+
					   " <td height=\"52px\" bgcolor=\"#3c8dbc\" ><img src=\"https://web.ShopVitals.com/app/resources/images/150x110.png\" Style=\"margin-left: 65%;max-height: 60px;\" /></td>"+
					  "</tr>"+
					  "<tr>"+
					   " <td colspan=\"2\" bgcolor=\"#f7f7f7\">"+
					   emailText+
					    "</td>"+
					  "</tr>"+
					  "<tr>"+
					    "<td style=\"font-size:12px;\"><a href=\"#\" target=\"_blank\">Contact info</a> | <a href=\"#\" target=\"_blank\">Privacy policy</a><br />"+
					      "<br />"+
					    "©2018 ShopVitals.</td>"+
					    "<td>&nbsp;</td>"+
					  "</tr>"+
					"</table>"+
					"</body>"
					
							,
					"text/html");
			email.addTo(toEmail);
			if (emailSendBCCTOStr.equals("Y")) {
				email.addBcc(emailSendBCCStr);
			}
			if (emailSendCCTOStr.equals("Y")) {
				email.addCc(emailSendCCStr);
			}
			email.setSSLOnConnect(true);

			email.send();
		} catch (Exception e) {
			LOGGER.error("Unable to send Email: "+ e.getMessage());
			return false;
		}
		return true;
	}
//	public boolean sendEmailTestTaker(String toEmail, String requestPath, String note,
//			String subject, String testCode){
//		
//		populatEmailConfig();
//		Email email = new SimpleEmail();
//		email.setHostName(emailHostStr);
//		email.setSmtpPort(Integer.parseInt(emailPortStr));
//		email.setAuthenticator(new DefaultAuthenticator(emailUserStr, emailPasswordStr));
//		AuAppuser appuser = userDAO.getUserByEmail(toEmail);
//		String userName = "";
//		if(appuser!=null && appuser.getAuFname()!=null){
//			userName =appuser.getAuFname();
//		}else{
//			return false;
//		}
//		try {
//			email.setFrom(emailFROMStr);
//			//String newPassword = createRandomPassword();
//			email.setSubject(subject);
//			email.setContent(
//					"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"+
//					"<html xmlns=\"http://www.w3.org/1999/xhtml\">"+
//					"<head>"+
//					"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"+
//					"<title>NOTE:</title>"+
//					"</head>"+
//
//					"<body>"+
//					"<table width=\"700\" border=\"0\" cellpadding=\"15\" cellspacing=\"0\" align=\"center\" style=\"font-size:14px;font-family:Arial, Helvetica, sans-serif;\">"+
//					  "<tr>"+
//					   " <td height=\"52px\" bgcolor=\"#c7181d\" ><img src=\""+requestPath+"/logo_1.png"+"\" /></td>"+
//					   " <td height=\"52px\" bgcolor=\"#c7181d\" >&nbsp;</td>"+
//					  "</tr>"+
//					  "<tr>"+
//					   " <td colspan=\"2\" bgcolor=\"#f7f7f7\">"+
//					    	"Hello "+ userName+","+
//					        "<br /><br />"+
//					         "This email has been sent from <b>Exam Meter Application</b>."
//					         + "<br>"
//					         + "<br>The test details are, Test Code: <b>"+testCode+"</b><br>"
//					         + "<br>Following are special instructions for test by sender: <b><br>"+note+"</b><br>"+
//					    "</td>"+
//					  "</tr>"+
//					  "<tr>"+
//					    "<td style=\"font-size:12px;\"><a href=\"#\" target=\"_blank\">Contact info</a> | <a href=\"#\" target=\"_blank\">Privacy policy</a><br />"+
//					      "<br />"+
//					    "©2014 Interview Application.</td>"+
//					    "<td>&nbsp;</td>"+
//					  "</tr>"+
//					"</table>"+
//					"</body>"
//					
//							,
//					"text/html");
//			email.addTo(toEmail);
//			if (emailSendBCCTOStr.equals("Y")) {
//				email.addBcc(emailSendBCCStr);
//			}
//			if (emailSendCCTOStr.equals("Y")) {
//				email.addCc(emailSendCCStr);
//			}
//			if (emailHostSSLStr.equals("Y")) {
//				email.setSSLOnConnect(true);
//			}
//
//			email.send();
//			
//			
//		} catch (EmailException e) {
//			LOGGER.error("Unable to send Email: "+ e.getMessage());
//		}
//		
//		return true;
//		
//	}
//	
	
//public boolean sendEmailTestTakerCF(String toEmail,String emailFrom, String requestPath, String note){
//		
//		populatEmailConfig();
//		emailFROMStr=emailFrom;
//		Email email = new SimpleEmail();
//		email.setHostName(emailHostStr);
//		email.setSmtpPort(Integer.parseInt(emailPortStr));
//		email.setAuthenticator(new DefaultAuthenticator(emailUserStr, emailPasswordStr));
//		AuAppuser appuser = userDAO.getUserByEmail(toEmail);
//		String userName = "";
//		if(appuser!=null && appuser.getAuFname()!=null){
//			userName =appuser.getAuFname();
//		}else{
//			return false;
//		}
//		try {
//			email.setFrom(emailFROMStr);
//			//String newPassword = createRandomPassword();
//			email.setSubject("TEST");
//			email.setContent(
//					"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"+
//					"<html xmlns=\"http://www.w3.org/1999/xhtml\">"+
//					"<head>"+
//					"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"+
//					"<title>NOTE:</title>"+
//					"</head>"+
//
//					"<body>"+
//					"<table width=\"700\" border=\"0\" cellpadding=\"15\" cellspacing=\"0\" align=\"center\" style=\"font-size:14px;font-family:Arial, Helvetica, sans-serif;\">"+
//					  "<tr>"+
//					   " <td height=\"52px\" bgcolor=\"#c7181d\" ><img src=\""+requestPath+"/logo_1.png"+"\" /></td>"+
//					   " <td height=\"52px\" bgcolor=\"#c7181d\" >&nbsp;</td>"+
//					  "</tr>"+
//					  "<tr>"+
//					   " <td colspan=\"2\" bgcolor=\"#f7f7f7\">"+
//					    	"Hello "+ userName+","+
//					        "<br /><br />"+
//					         "This email has been sent from <b>Interview Application</b>.<br><br>"+
//					        " The test details are: <b>"+note+"</b><br>"+
//					    "</td>"+
//					  "</tr>"+
//					  "<tr>"+
//					    "<td style=\"font-size:12px;\"><a href=\"#\" target=\"_blank\">Contact info</a> | <a href=\"#\" target=\"_blank\">Privacy policy</a><br />"+
//					      "<br />"+
//					    "©2014 Interview Application.</td>"+
//					    "<td>&nbsp;</td>"+
//					  "</tr>"+
//					"</table>"+
//					"</body>"
//					
//							,
//					"text/html");
//			email.addTo(toEmail);
//			if (emailSendBCCTOStr.equals("Y")) {
//				email.addBcc(emailSendBCCStr);
//			}
//			if (emailSendCCTOStr.equals("Y")) {
//				email.addCc(emailSendCCStr);
//			}
//			if (emailHostSSLStr.equals("Y")) {
//				email.setSSLOnConnect(true);
//			}
//
//			email.send();
//			
//			
//		} catch (EmailException e) {
//			LOGGER.error("Unable to send Email: "+ e.getMessage());
//		}
//		
//		return true;
//		
//	}


public boolean sendEmailContactUs(String toEmail, String  requestPath,String message,String name,String telephone){
	
	populatEmailConfig();
	emailFROMStr=toEmail;
	Email email = new SimpleEmail();
	email.setHostName(emailHostStr);
	email.setSmtpPort(Integer.parseInt(emailPortStr));
	email.setAuthenticator(new DefaultAuthenticator(emailUserStr, emailPasswordStr));
	emailSendCCTOStr="imran.latif@pk.ewsystemsinc.com";
	//AuAppuser appuser = userDAO.getUserByEmail(toEmail);
	//String userName = "";
	//if(appuser!=null && appuser.getAuFname()!=null){
	//	userName =appuser.getAuFname();
	//}else{
		//return false;
	//}
	try {
		email.setFrom(emailFROMStr);
		//String newPassword = createRandomPassword();
		email.setSubject("CONTACT US QUESTION");
		email.setContent(
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"+
				"<html xmlns=\"http://www.w3.org/1999/xhtml\">"+
				"<head>"+
				"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"+
				"<title>NOTE:</title>"+
				"</head>"+

				"<body>"+
				"<table width=\"700\" border=\"0\" cellpadding=\"15\" cellspacing=\"0\" align=\"center\" style=\"font-size:14px;font-family:Arial, Helvetica, sans-serif;\">"+
				  "<tr>"+
				   " <td height=\"52px\" bgcolor=\"#c7181d\" ><img src=\""+requestPath+"/logo_1.png"+"\" /></td>"+
				   " <td height=\"52px\" bgcolor=\"#c7181d\" >&nbsp;</td>"+
				  "</tr>"+
				  "<tr>"+
				   " <td colspan=\"2\" bgcolor=\"#f7f7f7\">"+
				    	"Hello Admin"+
				        "<br /><br />"+
				         "This email has been sent from <b>Interview Application</b>.<br><br>"+
				        " The question is: <b>"+message+"</b><br>"+
				    "</td>"+
				  "</tr>"+
				  "<tr>"+
				    "<td style=\"font-size:12px;\"><a href=\"#\" target=\"_blank\">Contact info</a> | <a href=\"#\" target=\"_blank\">Privacy policy</a><br />"+
				      "<br />"+
				    "©2014 Interview Application.</td>"+
				    "<td>&nbsp;</td>"+
				  "</tr>"+
				"</table>"+
				"</body>"
				
						,
				"text/html");
		email.addTo(toEmail);
		if (emailSendBCCTOStr.equals("Y")) {
			email.addBcc(emailSendBCCStr);
		}
		if (emailSendCCTOStr.equals("Y")) {
			email.addCc(emailSendCCStr);
		}
		if (emailHostSSLStr.equals("Y")) {
			email.setSSLOnConnect(true);
		}

		email.send();
		
		
	} catch (EmailException e) {
		LOGGER.error("Unable to send Email: "+ e.getMessage());
	}
	
	return true;
	
}
	

}
