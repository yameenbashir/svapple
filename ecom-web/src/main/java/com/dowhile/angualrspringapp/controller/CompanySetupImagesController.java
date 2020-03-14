/**
 * 
 */
package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Configuration;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.ConfigurationBean;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * @author Yameen Bashir
 *
 */
@Controller
@RequestMapping("/companySetupImages")
public class CompanySetupImagesController {

	// private static Logger logger = Logger.getLogger(CompanySetupImagesController.class.getName());
	@Resource
	private ResourceService resourceService;
	@Resource
	private ConfigurationService configurationService;
	@Resource
	private ServiceUtil util;

	@RequestMapping("/layout")
	public String getCompanySetupImagesControllerPartialPage(ModelMap modelMap) {
		return "companySetupImages/layout";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getCompanySetupImagesControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getCompanySetupImagesControllerData(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			List<Configuration> configurations = null;
			List<ConfigurationBean> configurationList =  new ArrayList<>();
			try {
				configurations = configurationService.getAllConfigurationsForCompanySetupImgagesByCompanyId(currentUser.getCompany().getCompanyId());
				if(configurations!=null){
					for(Configuration configuration:configurations){
						ConfigurationBean configurationBean = new ConfigurationBean();
						configurationBean.setConfigurationId(configuration.getConfigurationId().toString());
						configurationBean.setPropertyName(configuration.getPropertyName());
						configurationBean.setPropertyValue(configuration.getPropertyValue());
						configurationList.add(configurationBean);
						
					}
					util.AuditTrail(request, currentUser, "CompanySetupImagesController.getCompanySetupImagesControllerData", 
							"User: "+ currentUser.getUserEmail()+" retrived company setup images successfully ",false);
					return new Response(configurationList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "CompanySetupImagesController.getCompanySetupImagesControllerData", 
							"For User: "+ currentUser.getUserEmail()+" no company setup images exist. ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND, StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "CompanySetupImagesController.getCompanySetupImagesControllerData",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
}
