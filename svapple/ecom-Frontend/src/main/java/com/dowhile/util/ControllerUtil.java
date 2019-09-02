package com.dowhile.util;

import javax.servlet.http.HttpServletRequest;
public class ControllerUtil {
	
	public static String getRankDescription(String rankId){

		String rankDescription=null;
		switch(rankId){
		case "1":
			rankDescription ="Excellent";
			break;
		case "2":
			rankDescription = "Very Good";
			break;
		case "3":
			rankDescription = "Good";
			break;
		case "4":
			rankDescription = "Satisfactory";
			break;
		case "5":
			rankDescription = "Poor";
			break;
		}
		return rankDescription;
	}
	public static String getRoleDescription(String roleId){
		String roleDescription = null;

		switch(roleId){
		case "1":
			roleDescription ="Super User";
			break;
		case "2":
			roleDescription = "Company Honor";
			break;
		case "3":
			roleDescription = "Manager";
			break;
		case "4":
			roleDescription = "Worker";
			break;

		}
		return roleDescription;

	}
	public static String getStausDescription(String statusId){
		String statusDescription = null;
		switch(statusId){
		case "1":
			statusDescription ="Active";
			break;
		case "2":
			statusDescription = "Sick";
			break;
		case "3":
			statusDescription = "Free";
			break;
		case "4":
			statusDescription = "On Leave";
			break;
		case "5":
			statusDescription = "Left Job";
			break;
		case "6":
			statusDescription = "Hiring in Process";
			break;


		}
		return statusDescription;
	}
	
	
	
	public String getOperatingSystem (HttpServletRequest request){
		 String  browserDetails  =   request.getHeader("User-Agent");
	        String  userAgent       =   browserDetails;
	        
	        String os = "";
	        
	        //=================OS=======================
	         if (userAgent.toLowerCase().indexOf("windows") >= 0 )
	         {
	             os = "Windows";
	         } else if(userAgent.toLowerCase().indexOf("mac") >= 0)
	         {
	             os = "Mac";
	         } else if(userAgent.toLowerCase().indexOf("x11") >= 0)
	         {
	             os = "Unix";
	         } else if(userAgent.toLowerCase().indexOf("android") >= 0)
	         {
	             os = "Android";
	         } else if(userAgent.toLowerCase().indexOf("iphone") >= 0)
	         {
	             os = "IPhone";
	         }else{
	             os = "UnKnown, More-Info: "+userAgent;
	         }
	         return os;
	}
	public String getBrowser (HttpServletRequest request){
		 String  browserDetails  =   request.getHeader("User-Agent");
	        String  userAgent       =   browserDetails;
	        String  user            =   userAgent.toLowerCase();
	        String browser = "";

	      
	         //===============Browser===========================
	        if (user.contains("msie"))
	        {
	            String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
	            browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
	        } else if (user.contains("safari") && user.contains("version"))
	        {
	            browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
	        } else if ( user.contains("opr") || user.contains("opera"))
	        {
	            if(user.contains("opera"))
	                browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]+"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
	            else if(user.contains("opr"))
	                browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-")).replace("OPR", "Opera");
	        } else if (user.contains("chrome"))
	        {
	            browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
	        } else if ((user.indexOf("mozilla/7.0") > -1) || (user.indexOf("netscape6") != -1)  || (user.indexOf("mozilla/4.7") != -1) || (user.indexOf("mozilla/4.78") != -1) || (user.indexOf("mozilla/4.08") != -1) || (user.indexOf("mozilla/3") != -1) )
	        {
	            //browser=(userAgent.substring(userAgent.indexOf("MSIE")).split(" ")[0]).replace("/", "-");
	            browser = "Netscape-?";

	        } else if (user.contains("firefox"))
	        {
	            browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
	        } else if(user.contains("rv"))
	        {
	            browser="IE";
	        } else
	        {
	            browser = "UnKnown, More-Info: "+userAgent;
	        }
	      return browser;
	}
}
