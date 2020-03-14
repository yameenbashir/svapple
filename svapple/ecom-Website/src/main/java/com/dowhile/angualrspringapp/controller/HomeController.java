package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.ContactUs;
import com.dowhile.User;
import com.dowhile.WebActivityDetail;
import com.dowhile.angualrspringapp.beans.ContactUsBean;
import com.dowhile.angualrspringapp.beans.Response;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.service.ContactUsService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.util.ServiceUtil;

/**
 * imran latif
 */
@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Resource
	ResourceService resourceService;
	@Resource
	private ServiceUtil util;
	@Resource
	private ContactUsService contactUsService;
	  
	  @RequestMapping("/layout")
		public String getHomeControllerPartialPage(ModelMap modelMap) {
			return "home/layout";
		}
	  @SuppressWarnings({ "rawtypes", "unchecked" })
			@RequestMapping(value = "/addContactusRequest", method = RequestMethod.POST)
			public @ResponseBody Response addContactusRequest(@RequestBody ContactUsBean contactUsBean,
					HttpServletRequest request) {
			  User superUser =  resourceService.getUserById(1, 1);//On live server Imran is super user and his id is 1
				try {
					
					WebActivityDetail activityDetail = util.WebAuditTrail(request, superUser, "HomeController.addContactusRequest",
							"Contact us request recived from user  " + contactUsBean.getName()+" having email address: "+contactUsBean.getEmail(),false);
					
					ContactUs contactUs = new ContactUs();
					contactUs.setWebActivityDetail(activityDetail);
					contactUs.setContactNumber(contactUsBean.getContactNumber());
					contactUs.setEmail(contactUsBean.getEmail());
					contactUs.setMessage(contactUsBean.getMessage());
					contactUs.setName(contactUsBean.getName());
					contactUs.setRequestStatus(ControllersConstants.STATUS_NEW);
					contactUsService.addContactUs(contactUs);
					
					util.WebAuditTrail(request, superUser, "Contact US","Contact US",false);
					
				} catch (Exception e) {
					e.printStackTrace();// logger.error(e.getMessage(),e);
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					util.WebAuditTrail(request, superUser, "HomeController.addContactusRequest",
							"Error Occured " + errors.toString(),true);

					return new Response("Sorry for the inconvenience please try again at a later time.",
							StatusConstants.BUSY, LayOutPageConstants.STAY_ON_PAGE);
				}
				return new Response("Your demo request has been received, you will be received an email on provided email address.",
						StatusConstants.SUCCESS, LayOutPageConstants.STAY_ON_PAGE);
			}
		  
		  
		  @SuppressWarnings({ "rawtypes", "unchecked" })
			@RequestMapping(value = "/visitWebsite", method = RequestMethod.GET)
			public @ResponseBody Response visitWebsite(	HttpServletRequest request) {
			  User superUser =  resourceService.getUserById(1, 1);//On live server Imran is super user and his id is 1
				try {
					
					 util.WebAuditTrail(request, superUser, "Website Hit","Website Hit",false);
					
					
					
				} catch (Exception e) {
					e.printStackTrace();// logger.error(e.getMessage(),e);
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					util.WebAuditTrail(request, superUser, "HomeController.visitWebsite",
							"Error Occured " + errors.toString(),true);

					return new Response("Sorry for the inconvenience please try again at a later time.",
							StatusConstants.BUSY, LayOutPageConstants.STAY_ON_PAGE);
				}
				return new Response("Visit found",
						StatusConstants.SUCCESS, LayOutPageConstants.STAY_ON_PAGE);
			}
	  
	 
}

