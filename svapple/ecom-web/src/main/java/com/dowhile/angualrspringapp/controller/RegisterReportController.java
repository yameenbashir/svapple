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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Outlet;
import com.dowhile.RegisterReport;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.RegisterReportControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.RegisterReportBean;
import com.dowhile.service.InventoryReportService;
import com.dowhile.service.OutletService;
import com.dowhile.service.RegisterReportService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.ControllerUtil;
import com.dowhile.util.SessionValidator;

/**
 * @author Yameen Bashir
 *
 */
@Controller
@RequestMapping("/registerReport")
public class RegisterReportController {

	@Resource
	private ServiceUtil util;
	@Resource
	private RegisterReportService registerReportService;
	@Resource
	private InventoryReportService inventoryReportService;
	@Resource
	private OutletService outletService;
	
	private static List<OutletBean> outletBeans;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getRegisterReportControllerData/{sessionId}/{outletName}", method = RequestMethod.POST)
	public @ResponseBody Response getRegisterReportControllerData(@PathVariable("sessionId") String sessionId,
			@PathVariable("outletName") String outletName,HttpServletRequest request) {

		List<RegisterReportBean>  registerReportBeansList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				
				boolean isHeadOffice = false;
				if(currentUser.getOutlet().getIsHeadOffice()!=null){
					 isHeadOffice =currentUser.getOutlet().getIsHeadOffice();
					
				}
				
				if(currentUser.getRole().getRoleId()==1 && currentUser.getOutlet().getIsHeadOffice()!=null && currentUser.getOutlet().getIsHeadOffice().toString()=="true"){
					Response response = getOutlets(sessionId, request);
					if(response.status.equals(StatusConstants.SUCCESS)){
						outletBeans = (List<OutletBean>) response.data;
					}
				}
				Response response = getRegisterReport(sessionId, outletName, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					registerReportBeansList = (List<RegisterReportBean>) response.data;
				}

				RegisterReportControllerBean registerReportControllerBean = new RegisterReportControllerBean();
				registerReportControllerBean.setRegisterReportBeansList(registerReportBeansList);
				registerReportControllerBean.setOutletBeans(outletBeans);
				registerReportControllerBean.setHeadOffice(isHeadOffice);

				util.AuditTrail(request, currentUser, "RegisterReportController.getRegisterReportControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived InventoryReportControllerData data successfully ",false);
				return new Response(registerReportControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "RegisterReportController.getRegisterReportControllerData",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getRegisterReport/{sessionId}/{outletName}", method = RequestMethod.POST)
	public @ResponseBody Response getRegisterReport(@PathVariable("sessionId") String sessionId,
			@PathVariable("outletName") String outletName,HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			List<RegisterReportBean>  registerReportBeansList = new ArrayList<>();
			List<RegisterReport> registerReports = null;
			
			try {

				if(currentUser.getRole().getRoleId()==1 && currentUser.getOutlet().getIsHeadOffice()!=null && currentUser.getOutlet().getIsHeadOffice().toString()=="true"){
					if(outletName==null||outletName.equalsIgnoreCase("")||outletName.equalsIgnoreCase("undefined")||outletName.equalsIgnoreCase("All Outlets")){
						registerReports = registerReportService.getRegisterReportByCompanyId(currentUser.getCompany().getCompanyId());
					}else{
						int outletId = ControllerUtil.getOutletIdByOutletName(outletName,outletBeans);
						if(outletId==0)
							outletId = currentUser.getOutlet().getOutletId();
						registerReports = registerReportService.getRegisterReportByOutletIdCompanyId(outletId, currentUser.getCompany().getCompanyId());
					}
					
				}else{
					registerReports = registerReportService.getRegisterReportByOutletIdCompanyId(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
				}
				if (registerReports != null) {
					for (RegisterReport registerReport : registerReports) {
						RegisterReportBean registrReprotBean = new RegisterReportBean();
						if(registerReport!=null){
							registrReprotBean.setCashAmtActual(registerReport.getId().getCashAmtActual()!=null?registerReport.getId().getCashAmtActual().toString():"");
							
							registrReprotBean.setDailyRegisterId(String.valueOf(registerReport.getId().getDailyRegisterId()));
							registrReprotBean.setCreditCardAmtActual(registerReport.getId().getCreditCardAmtActual()!=null?registerReport.getId().getCreditCardAmtActual().toPlainString():"");
							registrReprotBean.setOpenBy(registerReport.getId().getOpenBy());
							registrReprotBean.setOpeningDate(new String(registerReport.getId().getOpeningDate()));
							registrReprotBean.setOutletAssocicationId(registerReport.getId().getOutletAssocicationId().toString());
							registrReprotBean.setOutletName(registerReport.getId().getOutletName());
							registrReprotBean.setRegisterClosingNotes(registerReport.getId().getRegisterClosingNotes());
							registrReprotBean.setRegisterOpeningNotes(registerReport.getId().getRegisterOpeningNotes());
							registrReprotBean.setStatus(registerReport.getId().getStatus());
							if(registrReprotBean.getStatus().equalsIgnoreCase("Open")){
								registrReprotBean.setClosingDate("-");
								registrReprotBean.setCloseBy("-");
							}else{
								registrReprotBean.setClosingDate(new String(registerReport.getId().getClosingDate()));
								registrReprotBean.setCloseBy(new String(registerReport.getId().getCloseBy()));
							}
							registerReportBeansList.add(registrReprotBean);
						}else{
							System.out.println("something wrong with register report............");
						}
					}
					util.AuditTrail(request, currentUser, "RegisterReportController.getRegisterReport", 
							"User "+ currentUser.getUserEmail()+" retrived Register report successfully ",false);
					return new Response(registerReportBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "RegisterReportController.getRegisterReport", 
							" Register report is not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "RegisterReportController.getRegisterReport",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getOutlets/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getOutlets(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<OutletBean> outletBeans = new ArrayList<>();
		List<Outlet> outlets = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				outlets = outletService.getOutlets(currentUser.getCompany().getCompanyId());
				if(outlets!=null){
					for(Outlet outlet:outlets){
						OutletBean outletBean = new OutletBean();
						outletBean.setOutletId(outlet.getOutletId().toString());
						outletBean.setOutletName(outlet.getOutletName());
						outletBeans.add(outletBean);
					}
					util.AuditTrail(request, currentUser, "RegisterReportController.getOutlets", 
							" retrived all outlets requested by User "+currentUser.getUserEmail(),false);
					return new Response(outletBeans,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}
				else{
					util.AuditTrail(request, currentUser, "RegisterReportController.getOutlets", 
							" outlets are not fount requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "RegisterReportController.getOutlets",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
}
