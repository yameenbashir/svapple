package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

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
import com.dowhile.service.AddressService;
import com.dowhile.service.CompanyService;
import com.dowhile.service.CurrencyService;
import com.dowhile.service.PrinterFormatService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/storeCredit")
public class StoreCreditController {

	@Resource
	private CompanyService companyService;
	@Resource
	private CurrencyService currencyService;
	@Resource
	private PrinterFormatService printerFormatService;
	@Resource
	private com.dowhile.service.TimeZoneService timeZoneService;
	@Resource
	private AddressService addressService;
	@Resource
	private ServiceUtil util;

	@RequestMapping("/layout")
	public String getStoreCreditControllerPartialPage(ModelMap modelMap) {
		return "storeCredit/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getCompanyDetailsByCompanyID/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getCompanyDetailsByCompanyID(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		CompanyBean companyBean = new CompanyBean();
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
		try {

			Company company = companyService.getCompanyDetailsByCompanyID(currentUser.getCompany().getCompanyId()); 
			if (company != null ) {

				companyBean.setCompanyId(company.getCompanyId().toString());
				companyBean.setCompanyName(company.getCompanyName());
				companyBean.setEnableStoresCredit(company.getEnableStoresCredit());
				util.AuditTrail(request, currentUser, "StoreCreditController.getCompanyDetailsByCompanyID", 
						"User "+ currentUser.getUserEmail()+" retrived company detail for Enable Stores Credit successfully ",false);
				return new Response(companyBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} else {
				util.AuditTrail(request, currentUser, "StoreCreditController.getCompanyDetailsByCompanyID", "User "+ 
						currentUser.getUserEmail()+" Unable to retrived company detail for Enable Stores Credit ",false);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				
			}
		} catch (Exception e) {
			e.printStackTrace();logger.error(e.getMessage(),e);
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			util.AuditTrail(request, currentUser, "StoreCreditController.getCompanyDetailsByCompanyID",
					"Error Occured " + errors.toString(),true);
			return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);

		}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateStoreCreditbyCompany/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response updateStoreCreditbyCompany(@PathVariable("sessionId") String sessionId,
			@RequestBody CompanyBean companyBean, HttpServletRequest request){

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {				
				if (companyBean != null) {

					int companyId= Integer.parseInt(companyBean.getCompanyId());
					Company company = companyService.getCompanyDetailsByCompanyID(companyId);
					company.setCompanyName(companyBean.getCompanyName());					
					company.setEnableStoresCredit(companyBean.getEnableStoresCredit());
					company = companyService.updateCompany(company);     
					util.AuditTrail(request, currentUser, "StoreCreditController.updateStoreCreditbyCompany", 
							"User "+ currentUser.getUserEmail()+" updated company Enable Stores Credit information successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "StoreCreditController.updateStoreCreditbyCompany", "User "+ 
							currentUser.getUserEmail()+" Unable to update company Enable Stores Credit information ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
					
				}
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "StoreCreditController.updateStoreCreditbyCompany",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);

			}
			}else{
				return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
			}

	}
}

