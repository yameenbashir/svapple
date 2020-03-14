package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.dowhile.CashManagment;
import com.dowhile.DailyRegister;
import com.dowhile.ExpenseType;
import com.dowhile.Register;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.CashManagementControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.CashManagmenBean;
import com.dowhile.frontend.mapping.bean.RegisterBean;
import com.dowhile.service.CashManagmentService;
import com.dowhile.service.CompanyService;
import com.dowhile.service.DailyRegisterService;
import com.dowhile.service.ExpenseTypeService;
import com.dowhile.service.OutletService;
import com.dowhile.service.RegisterService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/cashManagement")
public class CashManagementController {

	@Resource
	private ServiceUtil util;

	@Resource
	private ResourceService resourceService;
	@Resource
	private RegisterService registerService;
	@Resource
	private OutletService outletService;
	@Resource
	private CashManagmentService cashManagmentService;
	@Resource
	private CompanyService companyService;
	
	@Resource
	private DailyRegisterService dailyRegisterService;
	
	@Resource
	private ExpenseTypeService expenseTypeService;
	
	@RequestMapping("/layout")
	public String getCashManagementControllerPartialPage(ModelMap modelMap) {
		return "cashManagement/layout";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/cashInOut/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response cashInOut(@PathVariable("sessionId") String sessionId,@RequestBody CashManagmenBean cashManagmentBean, HttpServletRequest request) {
		
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			
			try{
				CashManagment cashManagment = new CashManagment();	
				cashManagment.setActiveIndicator(true);
				BigDecimal cashAmount = new BigDecimal(cashManagmentBean.getCashAmount());
				
				if(cashManagmentBean.getCashManagmentType().equalsIgnoreCase("IN")){
					if(cashAmount.doubleValue()<=0){
						cashManagment.setCashAmt(cashAmount.multiply(new BigDecimal("-1")));
						
					}else{
						cashManagment.setCashAmt(cashAmount);
					}
					
				}else{
					if(cashAmount.doubleValue()<=0){
						cashManagment.setCashAmt(cashAmount);
					}else{
						cashManagment.setCashAmt(cashAmount.multiply(new BigDecimal("-1")));
					}
					
					
				}
				
				cashManagment.setCashManagmentNotes(cashManagmentBean.getNotes());
				cashManagment.setCashManagmentType(cashManagmentBean.getCashManagmentType());
				cashManagment.setCompany(companyService.getCompanyDetailsByCompanyID(currentUser.getCompany().getCompanyId()));
				cashManagment.setCreatedBy(currentUser.getUserId());
				cashManagment.setCreatedDate(new Date());
				ExpenseType expenseType = null;
				if(cashManagmentBean.getExpenseType()==null){
					expenseType = expenseTypeService.getExpenseTypeByExpenseTypeId(1);
				}else{
					expenseType = expenseTypeService.getExpenseTypeByExpenseTypeId(Integer.parseInt(cashManagmentBean.getExpenseType()));
					
				}
				
				cashManagment.setExpenseType(expenseType);
				
				DailyRegister dailyRegister = dailyRegisterService.getOpenDailyRegister(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId());
				if(dailyRegister==null){
					util.AuditTrail(request, currentUser, "CashManagementController.cashInOut", 
							"User "+ currentUser.getUserEmail()+"No Open Register ",false);
					return new Response(MessageConstants.REGISTER_CLOSED,StatusConstants.REGISTER_CLOSED,LayOutPageConstants.CASHMANAGMENT);
				}
				cashManagment.setDailyRegister(dailyRegister);
				cashManagment.setOutlet(outletService.getOuletByOutletId(currentUser.getOutlet().getOutletId(),currentUser.getCompany().getCompanyId()));
				cashManagment.setCompany(currentUser.getCompany());
				cashManagmentService.addCashManagment(cashManagment,currentUser.getCompany().getCompanyId());
					
				List<CashManagment> cashManagments =  cashManagmentService.getCashManagmentDailyRegister(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId(), dailyRegister.getDailyRegisterId());
				List<CashManagmenBean> cashManagmentControllerBeans = new ArrayList<>();
				if(cashManagments!=null){
					for(CashManagment managment: cashManagments){
						CashManagmenBean managmentControllerBean = new CashManagmenBean();
						if(managment.getCashManagmentType().equalsIgnoreCase("IN")){
							managmentControllerBean.setCashAmount(managment.getCashAmt().toString());
						}else{
							managmentControllerBean.setCashAmount(managment.getCashAmt().toString());
						}
					
						managmentControllerBean.setTime(DateTimeUtil.convertDBDateTimeToGuiFormat(managment.getCreatedDate()));
						managmentControllerBean.setCreatedBy(resourceService.getUserById(managment.getCreatedBy(),currentUser.getCompany().getCompanyId()).getFirstName());
						managmentControllerBean.setNotes(managment.getCashManagmentNotes());
						cashManagmentControllerBeans.add(managmentControllerBean);
					}
					
				}
				util.AuditTrail(request, currentUser, "CashManagementController.cashInOut", 
							"User "+ currentUser.getUserEmail()+" Cash InOUT Successfull ",false);
					return new Response(cashManagmentControllerBeans,StatusConstants.SUCCESS,LayOutPageConstants.CASHMANAGMENT);
			
			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "CashManagementController.enableCash",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getCashInOut/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getCashInOut(@PathVariable("sessionId") String sessionId, HttpServletRequest request) {
		
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			
			try{
				DailyRegister dailyRegister = dailyRegisterService.getOpenDailyRegister(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId());
				if(dailyRegister==null){
					util.AuditTrail(request, currentUser, "CashManagementController.getCashInOut", 
							"User "+ currentUser.getUserEmail()+"No Open Register ",false);
					return new Response(MessageConstants.REGISTER_CLOSED,StatusConstants.REGISTER_CLOSED,LayOutPageConstants.CASHMANAGMENT);
				}
				List<ExpenseTypeBean> expenseTypeBeans = new ArrayList<>();
				List<ExpenseType> expenseTypes = expenseTypeService.GetAllExpenseType();
				List<CashManagmenBean> cashManagmentControllerBeans = new ArrayList<>();
				CashManagementControllerBean cashManagementControllerBean = new CashManagementControllerBean();
				Map<Integer, ExpenseType> expenseTypeMap= new HashMap<Integer, ExpenseType>();
				if(expenseTypes!=null){
					for(ExpenseType expenseType: expenseTypes){
						ExpenseTypeBean bean = new ExpenseTypeBean();
						bean.setExpenseTypeId(expenseType.getExpenseTypeId().toString());
						bean.setExpenseTypeName(expenseType.getExpenseTypeName());
						expenseTypeBeans.add(bean);
						expenseTypeMap.put(expenseType.getExpenseTypeId(), expenseType);
					}
				}
				
				cashManagementControllerBean.setExpenseTypes(expenseTypeBeans);
				List<CashManagment> cashManagments =  cashManagmentService.getCashManagmentDailyRegister(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId(), dailyRegister.getDailyRegisterId());
				if(cashManagments!=null){
					for(CashManagment managment: cashManagments){
						CashManagmenBean managmentControllerBean = new CashManagmenBean();
						if(managment.getCashManagmentType().equalsIgnoreCase("IN")){
							managmentControllerBean.setCashAmount(managment.getCashAmt().toString());
						}else{
							managmentControllerBean.setCashAmount(managment.getCashAmt().toString());
						}
					
						managmentControllerBean.setTime(DateTimeUtil.convertDBDateTimeToGuiFormat(managment.getCreatedDate()));
						managmentControllerBean.setCreatedBy(resourceService.getUserById(managment.getCreatedBy(),currentUser.getCompany().getCompanyId()).getFirstName());
						managmentControllerBean.setNotes(managment.getCashManagmentNotes());
						managmentControllerBean.setExpenseType(expenseTypeMap.get(managment.getExpenseType().getExpenseTypeId()).getExpenseTypeName());
						cashManagmentControllerBeans.add(managmentControllerBean);
					}
					
				}
				cashManagementControllerBean.setCashManagmentControllerBeans(cashManagmentControllerBeans);
				
				util.AuditTrail(request, currentUser, "CashManagementController.getCashInOut", 
							"User "+ currentUser.getUserEmail()+" Cash InOUT Successfull ",false);
					return new Response(cashManagementControllerBean,StatusConstants.SUCCESS,LayOutPageConstants.CASHMANAGMENT);
			
			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "CashManagementController.getCashInOut",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/enableCash/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response enableCash(@PathVariable("sessionId") String sessionId, HttpServletRequest request) {
		
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			List<Register> regiserList = registerService.getRegestersByOutletId(currentUser.getOutlet().getOutletId(),currentUser.getCompany().getCompanyId());
			RegisterBean regiseBean = new RegisterBean();
			
			try{
					
					if(regiserList!=null && regiserList.size()>0){
						for(Register register:regiserList){
						regiseBean.setCacheManagementEnable(register.getCacheManagementEnable());
							regiseBean.setEmailReceipt(register.getEmailReceipt());
							regiseBean.setNotes(register.getNotes());
							regiseBean.setOutletId(currentUser.getOutlet().getOutletId().toString());
							regiseBean.setOutletName(outletService.getOuletByOutletId(currentUser.getOutlet().getOutletId(),currentUser.getCompany().getCompanyId()).getOutletName());
							regiseBean.setPrintNotesOnReceipt(register.getPrintNotesOnReceipt());
							regiseBean.setPrintReceipt(register.getPrintReceipt());
							regiseBean.setReceiptNumber(register.getReceiptNumber());
							regiseBean.setReceiptPrefix(register.getReceiptPrefix());
							regiseBean.setReceiptSufix(register.getReceiptSufix());
							regiseBean.setRegisterId(register.getRegisterId().toString());
							regiseBean.setRegisterName(register.getRegisterName());
							regiseBean.setSelectNextUserForSale(register.getSelectNextUserForSale());
							regiseBean.setShowDiscountOnReceipt(register.getShowDiscountOnReceipt());
							
							
						}
					}
					
					
			
				util.AuditTrail(request, currentUser, "CashManagementController.enableCash", 
							"User "+ currentUser.getUserEmail()+" enableCash :+ "+regiseBean.getRegisterName()+" successfully ",false);
					return new Response(regiseBean,StatusConstants.SUCCESS,LayOutPageConstants.CUSTOMERS);
			
			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "CashManagementController.enableCash",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/enableCashStatus/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response enableCashStatus(@PathVariable("sessionId") String sessionId, HttpServletRequest request) {
		
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			List<Register> regiserList = registerService.getRegestersByOutletId(currentUser.getOutlet().getOutletId(),currentUser.getCompany().getCompanyId());
			RegisterBean regiseBean = new RegisterBean();
			
			try{
					
					if(regiserList!=null && regiserList.size()>0){
						for(Register register:regiserList){
						
							if(register.getCacheManagementEnable().equalsIgnoreCase("Yes")){
								util.AuditTrail(request, currentUser, "CashManagementController.enableCashStatus", 
										"User "+ currentUser.getUserEmail()+" enableCash :+ "+regiseBean.getRegisterName()+" successfully ",false);
								return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.CASHMANAGMENT);
								
							}
							
						}
					}
					
					util.AuditTrail(request, currentUser, "CashManagementController.enableCashStatus", 
							"User "+ currentUser.getUserEmail()+" enableCash :+ "+regiseBean.getRegisterName()+" successfully ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.ERROR,LayOutPageConstants.CASHMANAGMENT);
			
			
			
			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "CashManagementController.enableCashStatus",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

}
