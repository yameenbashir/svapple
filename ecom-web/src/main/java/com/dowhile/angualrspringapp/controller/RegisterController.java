package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
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

import com.dowhile.Outlet;
import com.dowhile.Register;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.RegisterBean;
import com.dowhile.service.OutletService;
import com.dowhile.service.RegisterService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * @author Yameen Bashir
 *
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

	
	@Resource
	private OutletService outletService;
	
	@Resource
	private RegisterService registerService;
	@Resource
	private ServiceUtil util;
		

	@RequestMapping("/layout")
	public String getRegisterControllerPartialPage(ModelMap modelMap) {
		return "register/layout";
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
			return new Response(MessageConstants.VALID_SESSION,StatusConstants.SUCCESS,LayOutPageConstants.REGISTER);
		else
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/addRegister/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response addRegister(@PathVariable("sessionId") String sessionId,
			@RequestBody RegisterBean registerBean, HttpServletRequest request) {
		
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				Outlet outlet = outletService.getOuletByOutletId(Integer.valueOf(registerBean.getOutletId()),currentUser.getCompany().getCompanyId());
				if(outlet!=null){
					Register register = new Register();
					register.setActiveIndicator(true);
					register.setRegisterName(registerBean.getRegisterName());
					register.setCacheManagementEnable(registerBean.getCacheManagementEnable());
					register.setCompany(outlet.getCompany());
					register.setCreatedBy(currentUser.getUserId());
					register.setCreatedDate(new Date());
					register.setEmailReceipt(registerBean.getEmailReceipt());
					register.setLastUpdated(new Date());
					register.setNotes(registerBean.getNotes());
					register.setOutlet(outlet);
					register.setPrintNotesOnReceipt(registerBean.getPrintNotesOnReceipt());
					register.setPrintReceipt(registerBean.getPrintReceipt());
					register.setReceiptNumber(registerBean.getReceiptNumber());
					register.setReceiptPrefix(registerBean.getReceiptPrefix());
					register.setReceiptSufix(registerBean.getReceiptSufix());
					register.setSelectNextUserForSale(registerBean.getSelectNextUserForSale());
					register.setShowDiscountOnReceipt(registerBean.getShowDiscountOnReceipt());
					register.setUpdatedBy(currentUser.getUserId());
					register.setCompany(currentUser.getCompany());
					registerService.addRegister(register,currentUser.getCompany().getCompanyId());
					util.AuditTrail(request, currentUser, "RegisterController.addRegister", 
							"User "+ currentUser.getUserEmail()+" Added Register+"+registerBean.getRegisterName()+" successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.OUTLETS);
				}else{
					util.AuditTrail(request, currentUser, "RegisterController.addRegister", "User "+ 
							currentUser.getUserEmail()+" Unable to add Register : "+registerBean.getRegisterName(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}
				
			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "RegisterController.addRegister",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	}

