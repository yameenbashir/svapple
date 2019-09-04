package com.dowhile.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.dowhile.User;
import com.dowhile.service.util.ServiceUtil;
/**
 * @author Yameen Bashir
 *
 */

public class SendSMS {
	@Resource
	private ServiceUtil util;

	public String sendSMS(HttpServletRequest request, User user, String phoneNum,String message,String userName, String password, String mask) {
		String finalResponse = "";
		message = message.replaceAll("%", "%25");
		message = message.replaceAll(" ", "%20");
		message = message.replaceAll("\r", "%0A");
		message = message.replaceAll("\n", "%0A");
		message = message.replaceAll("!", "%21");
		message = message.replaceAll("'", "%27");
		message = message.replaceAll(",", "%2C");
		message = message.replaceAll("-", "%2D");
		message = message.replaceAll("'", "%27");
		message = message.replaceAll("�", "%27");
	
		
//		message = message.replaceAll("#", "23");
//		message = message.replaceAll("$", "24");
//		message = message.replaceAll("%", "25");
//		message = message.replaceAll("&", "26");
//		message = message.replaceAll("'", "%27");
//		message = message.replaceAll(",", "%2B");
//		message = message.replaceAll("-", "%2D");
//		message = message.replaceAll("/", "2F%");
//		message = message.replaceAll(":", "3A%");
//		message = message.replaceAll(";", "3B%");
//		message = message.replaceAll("<", "3C%");
//		message = message.replaceAll("=", "3D%");
//		message = message.replaceAll(">", "3E%");
//		message = message.replaceAll("@", "40%");
//		message = message.replaceAll("^", "%5E");
//		message = message.replaceAll("_", "%5F");
//		message = message.replaceAll("�", "%CB%86");
//		message = message.replaceAll("�", "%E2%80%98");
//		message = message.replaceAll("�", "%E2%80%99");
//		message = message.replaceAll("�", "%E2%80%9C");
//		message = message.replaceAll("�", "%E2%80%9D");
//		message = message.replaceAll("�", "%E2%80%A2");
//		message = message.replaceAll("�", "%C2%A9");
//		message = message.replaceAll("�", "%C2%AB");
//		message = message.replaceAll("�", "%C2%AE");
//		message = message.replaceAll("�", "%C2%AF");
//		message = message.replaceAll("�", "%C2%B1");
//		message = message.replaceAll("�", "%C2%B4");
//		message = message.replaceAll("�", "%C2%B7");
//		message = message.replaceAll("�", "%C2%B8");
		
		
		StringBuffer response = new StringBuffer();
		String url = "http://sendpk.com/api/sms.php?username="+userName+"&password="+password+"&sender="+mask+"&mobile="+phoneNum+"&message="+message;
		try{
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		}catch(Exception exception){
			exception.printStackTrace();
			StringWriter errors = new StringWriter();
			exception.printStackTrace(new PrintWriter(errors));
//			util.AuditTrail(request, user, "SendSMS.sendSMS","Error Occured " + errors.toString(), true);
			return "Technical Error!";
			
		}
		finalResponse = response.toString();
		return finalResponse;
	
		}
	
	// HTTP POST request
	public String sendSMSPost(HttpServletRequest request, User user, String phoneNum,String message,String userName, String password, String mask) {
	
			message = message.replaceAll("%", "%25");
			message = message.replaceAll(" ", "%20");
			message = message.replaceAll("\r", "%0A");
			message = message.replaceAll("\n", "%0A");
			message = message.replaceAll("!", "%21");
			message = message.replaceAll("'", "%27");
			message = message.replaceAll(",", "%2C");
			message = message.replaceAll("-", "%2D");
			message = message.replaceAll("'", "%27");
			message = message.replaceAll("�", "%27");
			String url = "http://sendpk.com/api/sms.php";
			StringBuffer response = new StringBuffer();

			try{
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	
				//add reuqest header
				con.setRequestMethod("POST");
				con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	
				String urlParameters = "username="+userName+"&password="+password+"&sender="+mask+"&mobile="+phoneNum+"&message="+message;
	
				// Send post request
				con.setDoOutput(true);
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.writeBytes(urlParameters);
				wr.flush();
				wr.close();
				BufferedReader in = new BufferedReader(
				        new InputStreamReader(con.getInputStream()));
				String inputLine;
				
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

			}catch(Exception exception){
				exception.printStackTrace();
				StringWriter errors = new StringWriter();
				exception.printStackTrace(new PrintWriter(errors));
//					util.AuditTrail(request, user, "SendSMS.sendSMS","Error Occured " + errors.toString(), true);
				return "Technical Error!";
				
			}


			//print result
			return response.toString();

		
	}
	
	public static void main (String args[]){
		try{
			StringBuffer response = new StringBuffer();
			String url = "https://pk.eocean.us/APIManagement/API/RequestAPI?user=eoceantest&pwd=APjmG4FwuMXoDDgdGzyBI%2brF%2boDpuQAxjmFSrAISmVDk0Z6wKREJfDpcfod9VnRQcA%3d%3d&sender=Eocean%20Test&reciever=03046664547,03214179684,03214823566,03211660055&msg-data=Welcome%20SV%20Testing%20on%20Eocen&response=string";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			System.out.println(response.toString());
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
}
