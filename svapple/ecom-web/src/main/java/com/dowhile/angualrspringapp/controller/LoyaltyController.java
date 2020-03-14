package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Company;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.CompanyBean;
import com.dowhile.service.CompanyService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/loyalty")
public class LoyaltyController {

	@Resource
	private CompanyService companyService;
	@Resource
	private ServiceUtil util;
	

	@RequestMapping("/layout")
	public String getLoyaltyControllerPartialPage(ModelMap modelMap) {
		return "loyalty/layout";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/ValidateSession/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response ValidateSession(@PathVariable("sessionId") String sessionId,HttpServletRequest request) {
		boolean isValidSession = false;
		if (SessionValidator.isSessionValid(sessionId, request)){
			isValidSession = true;
		}
		if(isValidSession)
			return new Response(MessageConstants.VALID_SESSION,StatusConstants.SUCCESS,LayOutPageConstants.LOYALTY);
		else
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateCompanyLoyalty/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response updateCompanyLoyalty(@PathVariable("sessionId") String sessionId,
			@RequestBody CompanyBean companyBean, HttpServletRequest request){

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
	
		try {

			Company company = companyService.getCompanyDetailsByCompanyID(Integer.valueOf(companyBean.getCompanyId()));
			if (company!= null) {
				
				company.setLoyaltyInvoiceAmount(new BigDecimal(companyBean.getLoyaltyInvoiceAmount()));
				company.setLoyaltyAmount(new BigDecimal(companyBean.getLoyaltyAmount()));
				company.setLoyaltyEnabled(Boolean.valueOf(companyBean.getLoyaltyEnabled()));
				company.setLoyaltyEnabledDate(new Date());
				company.setLastUpdated(new Date());
				company.setLoyaltyBonusAmount(new BigDecimal(companyBean.getLoyaltyBonusAmount()));
				company.setLoyaltyBonusEnabled(Boolean.valueOf(companyBean.getLoyaltyBonusEnabled()));
				company.setLoyaltyBonusEnabledDate(new Date());
				company.setUpdatedBy(currentUser.getUserId());
				company = companyService.updateCompany(company); 
				
				util.AuditTrail(request, currentUser, "LoyaltyController.updateCompanyLoyalty", 
						" Updated Loyalty requested by User +"+currentUser.getUserEmail(),false);
				return new Response(MessageConstants.REQUREST_PROCESSED, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} else {
				util.AuditTrail(request, currentUser, "LoyaltyController.updateCompanyLoyalty", 
						"Unable to  Update Loyalty requested by User +"+currentUser.getUserEmail(),false);
				return new Response(MessageConstants.RECORD_NOT_FOUND,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();// logger.error(e.getMessage(),e);
			StringWriter errors = new StringWriter();
			util.AuditTrail(request, currentUser, "LoyaltyController.updateCompanyLoyalty",
					"Error Occured " + errors.toString(),true);
			e.printStackTrace(new PrintWriter(errors));
			return new Response(MessageConstants.SYSTEM_BUSY,
					StatusConstants.RECORD_NOT_FOUND,
					LayOutPageConstants.STAY_ON_PAGE);

		}

		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getCompanyLoyalty/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getCompanyLoyalty(@PathVariable("sessionId") String sessionId,
			 HttpServletRequest request){

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
	
		try {

			Company company = companyService.getCompanyDetailsByCompanyID(currentUser.getCompany().getCompanyId());
			CompanyBean companyBean = new CompanyBean();
			companyBean.setCompanyId(company.getCompanyId().toString());
			companyBean.setLoyaltyInvoiceAmount(company.getLoyaltyInvoiceAmount().toString());
			companyBean.setLoyaltyAmount(company.getLoyaltyAmount().toString());
			companyBean.setLoyaltyEnabled(company.getLoyaltyEnabled().toString());
			companyBean.setLoyaltyBonusAmount(company.getLoyaltyBonusAmount().toString());
			companyBean.setLoyaltyBonusEnabled(company.getLoyaltyBonusEnabled().toString());
			
			util.AuditTrail(request, currentUser, "LoyaltyController.getCompanyLoyalty", 
					" Updated Loyalty requested by User +"+currentUser.getUserEmail(),false);
			return new Response(companyBean, StatusConstants.SUCCESS,
					LayOutPageConstants.STAY_ON_PAGE);
		} catch (Exception e) {
			e.printStackTrace();// logger.error(e.getMessage(),e);
			StringWriter errors = new StringWriter();
			util.AuditTrail(request, currentUser, "LoyaltyController.getCompanyLoyalty",
					"Error Occured " + errors.toString(),true);
			e.printStackTrace(new PrintWriter(errors));
			return new Response(MessageConstants.SYSTEM_BUSY,
					StatusConstants.RECORD_NOT_FOUND,
					LayOutPageConstants.STAY_ON_PAGE);

		}

		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
		
	}
	
	
	
}

