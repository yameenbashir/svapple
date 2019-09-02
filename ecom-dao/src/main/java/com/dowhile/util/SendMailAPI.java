/**
 * 
 */
package com.dowhile.util;


 
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 * @author imran.latif
 *
 */
public class SendMailAPI {
	
	public static void main(String[] args) throws EmailException {
//		ConfigDAO configDAO = new ConfigDAOImpl();
//		configDAO.getConfiguration("");
		Email email = new SimpleEmail();
		email.setHostName("mail.websitedevelopmentsolution.com");
		email.setSmtpPort(587);
		email.setAuthenticator(new DefaultAuthenticator("sender@websitedevelopmentsolution.com", "Sender123"));
		email.setFrom("sender@websitedevelopmentsolution.com");
		email.setSubject("Your request for new password");
		email.setContent("<font face=\"Trebuchet MS,Arial,Helvetica,sans-serif\" size=\"1\" color=\"black\">"
				+ "<span style=\"font-size:12px;font-weight:normal;\"><font size=\"3\" color=\"#FF4800\">"
				+ "<span style=\"font-size:20px;\"><b>Dear Dr. Umar</b></span></font><br>"
				+ "<br>This email has been sent from <b>EWS Issue Space</b> application<br><br>"
				+ "<a href=\"http://issuespace.ewsystemsinc.com/forgot_password.php?sid=UDUzNWU2YjI1ZWYyZTc=&amp;mid=MzAx\" target=\"_blank\">"
				+ "<font color=\"#FF4800\">Click here</font></a> to complete your change password request.<br>"
				+ "<br>....................................................................<br>"
				+ "Please do not reply to this email.<br>"
				+ "<a href=\"http://issuespace.ewsystemsinc.com/\" target=\"_blank\"><font color=\"#FF4800\">"
				+ "<b>System Administrator<br>"
				+ "Issue Space EWS.</b></font></a> </span></font>", "text/html");
		email.addTo("imran.latif@pk.ewsystemsinc.com");
		email.send();
	}

}
