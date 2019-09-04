package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.ContactUs;
import com.dowhile.User;
import com.dowhile.angualrspringapp.beans.ContactUsBean;
import com.dowhile.angualrspringapp.beans.Response;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.service.ContactUsService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;

/**
 * Yameen Bashir
 */
@Controller
@RequestMapping("/contactUs")
public class ContactUsController {
	
	@Resource
	ResourceService resourceService;
	@Resource
	private ServiceUtil util;
	@Resource
	private ContactUsService contactUsService;
	  
	  @RequestMapping("/layout")
		public String getContactUsControllerPartialPage(ModelMap modelMap) {
			return "contactUs/layout";
		}
	  @SuppressWarnings({ "rawtypes", "unchecked" })
			@RequestMapping(value = "/getContactUsControllerData", method = RequestMethod.POST)
			public @ResponseBody Response getContactUsControllerData(
					HttpServletRequest request) {
			 
				try {
					List<ContactUsBean> ContactUsBeanList = new ArrayList<>();
					List<ContactUs> contactUsList = contactUsService.getAllContactUs();
					if(contactUsList!=null && contactUsList.size()>0){
						for(ContactUs contactUs:contactUsList){
							ContactUsBean contactUsBean  = new ContactUsBean();
							BeanUtils.copyProperties(contactUs, contactUsBean);
							contactUsBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(contactUs.getCreatedDate()));
							ContactUsBeanList.add(contactUsBean);
						}
						return new Response(ContactUsBeanList,
								StatusConstants.SUCCESS, LayOutPageConstants.STAY_ON_PAGE);
					}else{
						return new Response(MessageConstants.REQUREST_PROCESSED,
								StatusConstants.RECORD_NOT_FOUND, LayOutPageConstants.STAY_ON_PAGE);
					}
				} catch (Exception e) {
					e.printStackTrace();
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					 User superUser =  resourceService.getUserById(1, 1);//On live server Imran is super user and his id is 1
					util.WebAuditTrail(request, superUser, "ContactUsController.getContactUsControllerData",
							"Error Occured " + errors.toString(),true);

					return new Response(MessageConstants.SYSTEM_BUSY,
							StatusConstants.BUSY, LayOutPageConstants.STAY_ON_PAGE);
				}
				
			}
		  
		 
}

