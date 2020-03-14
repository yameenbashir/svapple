/**
 * 
 */
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
@RequestMapping("/manageRegister")
public class ManageRegisterController {


	@Resource
	private OutletService outletService;

	@Resource
	private RegisterService registerService;
	@Resource
	private ServiceUtil util;


	@RequestMapping("/layout")
	public String getManageRegisterControllerPartialPage(ModelMap modelMap) {
		return "manageRegister/layout";
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
			return new Response(MessageConstants.VALID_SESSION,StatusConstants.SUCCESS,LayOutPageConstants.MANAGE_REGISTER);
		else
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getRegisterByRegisterId/{sessionId}/{registerId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getRegisterByRegisterId(@PathVariable("sessionId") String sessionId,
			@PathVariable("registerId") String registerId,
			HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				Register register = registerService.getRegisterByRegisterId(Integer.valueOf(registerId),currentUser.getCompany().getCompanyId());
				if(register!=null){
					RegisterBean registerBean = new RegisterBean();
					registerBean.setCacheManagementEnable(register.getCacheManagementEnable());
					registerBean.setEmailReceipt(register.getEmailReceipt());
					registerBean.setNotes(register.getNotes());
					Outlet outlet = outletService.getOuletByOutletId(register.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
					registerBean.setOutletId(outlet.getOutletId().toString());
					registerBean.setOutletName(outlet.getOutletName());
					registerBean.setPrintNotesOnReceipt(register.getPrintNotesOnReceipt());
					registerBean.setPrintReceipt(register.getPrintReceipt());
					registerBean.setReceiptNumber(register.getReceiptNumber());
					registerBean.setReceiptPrefix(register.getReceiptPrefix());
					registerBean.setReceiptSufix(register.getReceiptSufix());
					registerBean.setRegisterId(register.getRegisterId().toString());
					registerBean.setRegisterName(register.getRegisterName());
					registerBean.setSelectNextUserForSale(register.getSelectNextUserForSale());
					registerBean.setShowDiscountOnReceipt(register.getShowDiscountOnReceipt());
					util.AuditTrail(request, currentUser, "ManageRegisterController.getRegisterByRegisterId", 
							"User "+ currentUser.getUserEmail()+" Updated Register+"+registerBean.getRegisterName()+" successfully ",false);
					return new Response(registerBean,StatusConstants.SUCCESS,LayOutPageConstants.MANAGE_REGISTER);
				}else{
					util.AuditTrail(request, currentUser, "ManageRegisterController.getRegisterByRegisterId", "User "+ 
							currentUser.getUserEmail()+" Unable to update Register ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManageRegisterController.getRegisterByRegisterId",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/updateRegister/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response updateRegister(@PathVariable("sessionId") String sessionId,
			@RequestBody RegisterBean registerBean, HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				Register register = registerService.getRegisterByRegisterId(Integer.valueOf(registerBean.getRegisterId()),currentUser.getCompany().getCompanyId());
				if(register!=null){
					//register.setActiveIndicator(true);
					register.setRegisterName(registerBean.getRegisterName());
					register.setCacheManagementEnable(registerBean.getCacheManagementEnable());
					//register.setCompany(outlet.getCompany());
					//register.setCreatedBy(currentUser.getUserId());
					//register.setCreatedDate(new Date());
					register.setEmailReceipt(registerBean.getEmailReceipt());
					register.setLastUpdated(new Date());
					register.setNotes(registerBean.getNotes());
					//register.setOutlet(outlet);
					register.setPrintNotesOnReceipt(registerBean.getPrintNotesOnReceipt());
					register.setPrintReceipt(registerBean.getPrintReceipt());
					register.setReceiptNumber(registerBean.getReceiptNumber());
					register.setReceiptPrefix(registerBean.getReceiptPrefix());
					register.setReceiptSufix(registerBean.getReceiptSufix());
					register.setSelectNextUserForSale(registerBean.getSelectNextUserForSale());
					register.setShowDiscountOnReceipt(registerBean.getShowDiscountOnReceipt());
					register.setUpdatedBy(currentUser.getUserId());
					registerService.updateRegister(register,currentUser.getCompany().getCompanyId());
					util.AuditTrail(request, currentUser, "ManageRegisterController.updateRegister", 
							"User "+ currentUser.getUserEmail()+" Updated Register+"+registerBean.getRegisterName()+" successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.OUTLETS);
				}else{
					util.AuditTrail(request, currentUser, "ManageRegisterController.updateRegister", "User "+ 
							currentUser.getUserEmail()+" Unable to update Register : "+registerBean.getRegisterName(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManageRegisterController.updateRegister",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}


}
