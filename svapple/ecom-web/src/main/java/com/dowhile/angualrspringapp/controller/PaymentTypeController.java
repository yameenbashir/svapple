package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.dowhile.PaymentType;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.PaymentTypeBean;
import com.dowhile.service.OutletService;
import com.dowhile.service.PaymentTypeService;
import com.dowhile.service.RegisterService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/paymentType")
public class PaymentTypeController {

	@Resource
	private RegisterService registerService;
	@Resource
	private OutletService outletService;
	@Resource
	private ServiceUtil util;
	@Resource
	private PaymentTypeService paymentTypeService;

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/addPaymentType/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response addPaymentType(@PathVariable("sessionId") String sessionId,
			@RequestBody PaymentTypeBean paymentTypeBean, HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				PaymentType paymentType = new PaymentType();
				paymentType.setGateway(paymentTypeBean.getGateway());
				paymentType.setPaymentTypeValue(paymentTypeBean.getPaymentTypeValue());
				if(paymentTypeBean.getPaymentTypeValue().equalsIgnoreCase("Cash")){
					paymentType.setRoundTo(paymentTypeBean.getRoundTo());
				}
				paymentType.setActiveIndicator(true);
				paymentType.setCreatedBy(currentUser.getUserId());
				paymentType.setUpdatedBy(currentUser.getUserId());
				paymentType.setCreatedDate(new Date());
				paymentType.setLastUpdated(new Date());
				paymentTypeService.addPaymentType(paymentType,currentUser.getCompany().getCompanyId());

				util.AuditTrail(request, currentUser, "PaymentTypeController.addPaymentType", 
						"User "+ currentUser.getUserEmail()+" Added Payment type +"+paymentTypeBean.getPaymentTypeValue()+" successfully ",false);
				return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.SALES_TAX);

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PaymentTypeController.addPaymentType",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/updatePaymentType/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response updatePaymentType(@PathVariable("sessionId") String sessionId,
			@RequestBody PaymentTypeBean paymentTypeBean, HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				PaymentType paymentType = paymentTypeService.getPaymentTypeByPaymentTypeId(Integer.valueOf(paymentTypeBean.getPaymentTypeId()),currentUser.getCompany().getCompanyId());
				paymentType.setGateway(paymentTypeBean.getGateway());
				paymentType.setPaymentTypeValue(paymentTypeBean.getPaymentTypeValue());
				if(paymentTypeBean.getPaymentTypeValue().equalsIgnoreCase("Cash")){
					paymentType.setRoundTo(paymentTypeBean.getRoundTo());
				}
				paymentType.setActiveIndicator(true);
				paymentType.setUpdatedBy(currentUser.getUserId());
				paymentType.setLastUpdated(new Date());
				paymentTypeService.updatePaymentType(paymentType,currentUser.getCompany().getCompanyId());

				util.AuditTrail(request, currentUser, "PaymentTypeController.updatePaymentType", 
						"User "+ currentUser.getUserEmail()+" Updated Payment type +"+paymentTypeBean.getPaymentTypeValue()+" successfully ",false);
				return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.SALES_TAX);

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PaymentTypeController.updatePaymentType",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllPaymentTypes/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllPaymentTypes(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<PaymentTypeBean> paymentTypeBeanList = new ArrayList<>();
		List<PaymentType> paymentTypesList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");


			try {

				paymentTypesList = paymentTypeService.getAllPaymentTypes(currentUser.getCompany().getCompanyId());
				
				if (paymentTypesList != null) {
					for (PaymentType paymentType : paymentTypesList) {

						PaymentTypeBean paymentTypeBean = new PaymentTypeBean();
						paymentTypeBean.setGateway(paymentType.getGateway());
						paymentTypeBean.setPaymentTypeId(paymentType.getPaymentTypeId().toString());
						paymentTypeBean.setPaymentTypeValue(paymentType.getPaymentTypeValue());
						paymentTypeBean.setRoundTo(paymentType.getRoundTo());
						paymentTypeBeanList.add(paymentTypeBean);
						
						}
					util.AuditTrail(request, currentUser, "PaymentTypeController.getAllPaymentTypes", 
							"User "+ currentUser.getUserEmail()+" retrived all paymentTypes successfully ",false);
					return new Response(paymentTypeBeanList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "PaymentTypeController.getAllPaymentTypes", 
							" PaymentTypes are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PaymentTypeController.getAllPaymentTypes",
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

