package com.dowhile.service.util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.dowhile.ActivityDetail;
import com.dowhile.Severity;
import com.dowhile.User;
import com.dowhile.WebActivityDetail;
import com.dowhile.dao.ActivityDetailDAO;
import com.dowhile.dao.SeverityDAO;
import com.dowhile.dao.WebActivityDetailDAO;
import com.dowhile.service.impl.EmailService;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class ServiceUtil {
	
	// private static Logger logger = Logger.getLogger(ServiceUtil.class.getName());
	private SeverityDAO severityDAO;
	private ActivityDetailDAO activityDetailDAO;
	private WebActivityDetailDAO webActivityDetailDAO;
	

	/**
	 * @return the activityDetailDAO
	 */
	public ActivityDetailDAO getActivityDetailDAO() {
		return activityDetailDAO;
	}


	/**
	 * @param activityDetailDAO the activityDetailDAO to set
	 */
	public void setActivityDetailDAO(ActivityDetailDAO activityDetailDAO) {
		this.activityDetailDAO = activityDetailDAO;
	}


	/**
	 * @return the severityDAO
	 */
	public SeverityDAO getSeverityDAO() {
		return severityDAO;
	}


	/**
	 * @param severityDAO the severityDAO to set
	 */
	public void setSeverityDAO(SeverityDAO severityDAO) {
		this.severityDAO = severityDAO;
	}


	/**
	 * @return the webActivityDetailDAO
	 */
	public WebActivityDetailDAO getWebActivityDetailDAO() {
		return webActivityDetailDAO;
	}


	/**
	 * @param webActivityDetailDAO the webActivityDetailDAO to set
	 */
	public void setWebActivityDetailDAO(WebActivityDetailDAO webActivityDetailDAO) {
		this.webActivityDetailDAO = webActivityDetailDAO;
	}


	public ActivityDetail AuditTrail (HttpServletRequest request,  User user, 
			String PageNameDetails,String activityDetailDesc, boolean isException){
		
		ActivityDetail activityDetail = new ActivityDetail();
		try{
			
			UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
			
		    Browser browser = userAgent.getBrowser();
		    OperatingSystem os = userAgent.getOperatingSystem();
		    String ipAddress = request.getHeader("X-FORWARDED-FOR");
		    if (ipAddress == null) {
		    	   ipAddress = request.getRemoteAddr();
		    }
		   
			activityDetail.setActivityDetail(PageNameDetails);
			activityDetail.setBrowserName(browser.getName());
			activityDetail.setBrowserVersion(userAgent.getBrowserVersion().getVersion());
			activityDetail.setCreatedDate(new Date());
			activityDetail.setIpAddress(ipAddress);
			activityDetail.setOperatingSystem(os.getName());
			activityDetail.setOtherInformation(activityDetailDesc.getBytes());
			activityDetail.setSessionId(request.getSession().getId());
			Severity severity = getSeverityDAO().getSeverityBySeverityId(1);
			activityDetail.setSeverity(severity);
			activityDetail.setUserByCreatedByManagerId(user);
			activityDetail.setEmloyeeEmail(user.getUserEmail());
			activityDetail.setEmployeeName(user.getFirstName());
			activityDetail.setDeviceType(os.getDeviceType().getName());
			activityDetail.setUserByEmployeeAssociationId(user);
			activityDetail.setIsException(String.valueOf(isException));
			activityDetail.setCompany(user.getCompany());
			getActivityDetailDAO().addActivityDetail(activityDetail,user.getCompany().getCompanyId());
			
		}catch(Exception ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		
		return activityDetail;
	}
	
	public WebActivityDetail WebAuditTrail (HttpServletRequest request,  User user, 
			String PageNameDetails,String activityDetailDesc, boolean isException){
		
		//ActivityDetail activityDetail = new ActivityDetail();
		WebActivityDetail webActivityDetail = new WebActivityDetail();
		try{
			UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
			
		    Browser browser = userAgent.getBrowser();
		    OperatingSystem os = userAgent.getOperatingSystem();
		    String ipAddress = request.getHeader("X-FORWARDED-FOR");
		    if (ipAddress == null) {
		    	   ipAddress = request.getRemoteAddr();
		    }
		   
			webActivityDetail.setActivityDetail(PageNameDetails);
			webActivityDetail.setBrowserName(browser.getName());
			webActivityDetail.setBrowserVersion(userAgent.getBrowserVersion().getVersion());
			webActivityDetail.setCreatedDate(new Date());
			webActivityDetail.setIpAddress(ipAddress);
			webActivityDetail.setOperatingSystem(os.getName());
			webActivityDetail.setOtherInformation(activityDetailDesc.getBytes());
			webActivityDetail.setSessionId(request.getSession().getId());
			Severity severity = getSeverityDAO().getSeverityBySeverityId(1);
			webActivityDetail.setSeverity(severity);
			webActivityDetail.setUserByCreatedByManagerId(user);
			webActivityDetail.setEmloyeeEmail(user.getUserEmail());
			webActivityDetail.setEmployeeName(user.getFirstName());
			webActivityDetail.setDeviceType(os.getDeviceType().getName());
			webActivityDetail.setUserByEmployeeAssociationId(user);
			webActivityDetail.setIsException(String.valueOf(isException));
			getWebActivityDetailDAO().addWebActivityDetail(webActivityDetail);
		}catch(Exception ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		
		return webActivityDetail;
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
