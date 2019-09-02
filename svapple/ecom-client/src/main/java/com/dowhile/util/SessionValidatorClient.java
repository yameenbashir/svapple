/**
 * 
 */
package com.dowhile.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Yameen Bashir
 *
 */
public class SessionValidatorClient {
	
	public static boolean isSessionValid(String sessionId,HttpServletRequest request){

		boolean isValidSession = true;
		HttpSession session =  request.getSession(false);
		if (session==null || (!session.getId().equals(sessionId))){
			isValidSession = false;
		}
		return isValidSession;
	}

}
