/**
 * 
 */
package com.dowhile.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * @author Yameen Bashir
 *
 */
public class SessionValidator {
	
	private static Logger logger = Logger.getLogger(SessionValidator.class.getName());
	
	public static boolean isSessionValid(String sessionId,HttpServletRequest request){

		boolean isValidSession = true;
		HttpSession session =  request.getSession(false);
		String subDomianName = (String) session.getAttribute("subDomianName");
		String subDomianFromRequest = "localhost";
		if(request.getServerName()!=null){
			if(request.getServerName().split("\\.").length>0){
				subDomianFromRequest=request.getServerName().split("\\.")[0];
			}
			
		}
			//Un comment for live deployment
		if (session==null || (!session.getId().equals(sessionId))){
		isValidSession = false;
		}else{
			if(subDomianName!=null && (subDomianName.equalsIgnoreCase(subDomianFromRequest) || subDomianFromRequest.equals("localhost"))){
				isValidSession = true;
			}else{
				isValidSession = false;
			}
		}
		//Un Comment for local deployment
		/*if (session==null || (!session.getId().equals(sessionId))||
				!SessionValidator.getSystemMacAddress().equalsIgnoreCase(ControllersConstants.MAC_ADDRESS)){
			isValidSession = false;
		}*/
		return isValidSession;
	}
	
	public static String getSystemMacAddress(){
		
		InetAddress ip;
		try {

			ip = InetAddress.getLocalHost();
			//System.out.println("Current IP address : " + ip.getHostAddress());

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			System.out.print("Current MAC address : ");

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			//System.out.println(sb.toString());
			return sb.toString();

		} catch (UnknownHostException e) {

			e.printStackTrace();logger.error(e.getMessage(),e);

		} catch (SocketException e){

			e.printStackTrace();logger.error(e.getMessage(),e);

		}
		System.out.println("Mac address not found");
		return "";

	}
	
	public static void main(String[] args){

		InetAddress ip;
		try {

			ip = InetAddress.getLocalHost();
			System.out.println("Current IP address : " + ip.getHostAddress());

			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			System.out.print("Current MAC address : ");

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			System.out.println(sb.toString());

		} catch (UnknownHostException e) {

			e.printStackTrace();logger.error(e.getMessage(),e);

		} catch (SocketException e){

			e.printStackTrace();logger.error(e.getMessage(),e);

		}

	   }

}
