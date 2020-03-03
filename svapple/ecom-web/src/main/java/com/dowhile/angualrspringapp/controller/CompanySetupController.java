/**
 * 
 */
package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
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

import com.dowhile.Company;
import com.dowhile.Currency;
import com.dowhile.Outlet;
import com.dowhile.PrinterFormat;
import com.dowhile.Register;
import com.dowhile.SalesTax;
import com.dowhile.TimeZone;
import com.dowhile.User;
import com.dowhile.angualrspringapp.beans.CurrencyBean;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.CompanyBean;
import com.dowhile.frontend.mapping.bean.PrinterFormatBean;
import com.dowhile.frontend.mapping.bean.TimeZoneBean;
import com.dowhile.service.CompanyService;
import com.dowhile.service.CurrencyService;
import com.dowhile.service.OutletService;
import com.dowhile.service.PrinterFormatService;
import com.dowhile.service.RegisterService;
import com.dowhile.service.SalesTaxService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * @author Yameen Bashir
 *
 */
@Controller
@RequestMapping("/companySetup")
public class CompanySetupController {
	
	@Resource
	private CurrencyService currencyService;
	
	@Resource
	private PrinterFormatService printerFormatService;
	
	@Resource
	private com.dowhile.service.TimeZoneService	timeZoneService;
	
	@Resource
	 private CompanyService companyService;
	 
	 @Resource
	 private OutletService outletService;
	 
	 @Resource
	 private RegisterService registerService;
	 @Resource
	 private ServiceUtil util;
	 @Resource
	 private SalesTaxService salesTaxService;
	
	@RequestMapping("/layout")
	public String getCompanySetupControllerPartialPage(ModelMap modelMap) {
		return "companySetup/layout";
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
			return new Response(MessageConstants.VALID_SESSION,StatusConstants.SUCCESS,LayOutPageConstants.COMPANY_SETUP);
		else
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
	}
	
	/*@SuppressWarnings({ "rawtypes", "unchecked" })
	 @RequestMapping(value = "/addNewCompany/{sessionId}", method = RequestMethod.POST)
	 public @ResponseBody
	 Response AddNewCompany(@PathVariable("sessionId") String sessionId, CompanyBean companyBean, HttpServletRequest request) {

	  boolean isAdded = true;
	  
	  if(SessionValidator.isSessionValid(sessionId, request)){
	  // User currentUser = (User) session.getAttribute("user");
		  Company company = new Company();
		  company.setActiveIndicator(true);
		  company.setAddresses(null);
		  //company.setCompanyId(companyId);
		  company.setCompanyName(companyBean.getCompanyName());
		 // company.setCompanyName(companyName);
		  company.setCreatedBy(null);
		  company.setCreatedDate(new Date());
		  company.setCurrency(null);
		  company.setCurrentSequenceNumber(companyBean.getCurrentSequenceNumber());
		  company.setDisplayPrices(companyBean.getDisplayPrices());
		 // company.setInvoiceMains(invoiceMains);
		  company.setLastUpdated(null);
		//  company.setLoyalties(loyalties);
		  company.setLoyaltyEnabled(true);;
		  company.setLoyaltyAmount(new BigDecimal("100.00"));
		  company.setPrinterFormat(null);
		  company.setRegisters(null);
		  company.setSkuGeneration(companyBean.getSkuGeneration());
		  company.setTimeZone(null);
		  company.setUpdatedBy(null);
		 // company.setUserSecurity(companyBean.getUserSecurity());
		  company = companyService.addCompany(company);
		  Outlet outlet =  new Outlet();
		  outlet.setActiveInd(true);
		  outlet.setCompany(company);
		  outlet.setCreatedBy(null);
		  outlet.setCreatedDate(new Date());
		  outlet.setUpdatedBy(null);
		  outlet.setOutletName("Main Outlet");
		  outlet.setSelsTaxPercentage(5.00);;
		  outlet = outletService.addOutlet(outlet);
	   
		  Register register = new Register();
		  register.setActiveIndicator(true);
		  register.setRegisterName("Main Register");
		  register.setCacheManagementEnable("No");
		  register.setCompany(company);
		  register.setCreatedBy(null);
		  register.setCreatedDate(new Date());
		  register.setEmailReceipt("Yes");
		  register.setLastUpdated(null);
		  register.setNotes("");
		  register.setOutlet(outlet);
		  register.setPrintNotesOnReceipt("No");
		  register.setPrintReceipt("Yes");
		  register.setReceiptNumber("0");
		  register.setReceiptPrefix("");
		  register.setReceiptSufix("");
		  register.setSelectNextUserForSale("");
		  register.setShowDiscountOnReceipt("Yes");
		  register.setUpdatedBy(null);
		  registerService.addRegister(register);
	   }
	  return new Response("Organization already exist with same email address","","");
	  
	  }
	*/
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	 @RequestMapping(value = "/addNewCompany/{sessionId}", method = RequestMethod.POST)
	 public @ResponseBody
	 Response addNewCompany(@PathVariable("sessionId") String sessionId,
	   @RequestBody CompanyBean companyBean, HttpServletRequest request) {
	  if(SessionValidator.isSessionValid(sessionId, request)){
	   HttpSession session =  request.getSession(false);
	   User currentUser = (User) session.getAttribute("user");

	   try{
	    Company company = new Company();
	    company.setActiveIndicator(true);
	    company.setAddresses(null);
	    company.setCompanyName(companyBean.getCompanyName());
	    company.setCreatedBy(currentUser.getUserId());
	    company.setCreatedDate(new Date());  
	    company.setCurrency(null);
	    company.setCurrentSequenceNumber(companyBean.getCurrentSequenceNumber());
	    company.setDisplayPrices(companyBean.getDisplayPrices());
	    // company.setInvoiceMains(invoiceMains);
	    company.setLastUpdated(new Date());
	    //  company.setLoyalties(loyalties);
	    company.setLoyaltyEnabled(true);;
	    company.setLoyaltyAmount(new BigDecimal("100.00"));
	    company.setPrinterFormat(null);
	    company.setRegisters(null);
	    company.setSkuGeneration(companyBean.getSkuGeneration());
	    TimeZone timeZone = timeZoneService.getTimeZoneByTimeZoneId(Integer.valueOf(companyBean.getTimeZoneID()));
	    company.setTimeZone(timeZone);
	    company.setUpdatedBy(currentUser.getUserId());
	    company.setEnableStoresCredit("False");
	    // company.setUserSecurity(companyBean.getUserSecurity());
	    Company newCompany = companyService.addCompany(company);
	    if(newCompany!=null){
	     Outlet outlet =  new Outlet();
	     outlet.setActiveIndicator(true);
	     outlet.setCompany(company);
	     outlet.setCreatedBy(currentUser.getUserId());
	     outlet.setCreatedDate(new Date());
	     outlet.setLastUpdated(new Date());
	     outlet.setUpdatedBy(currentUser.getUserId());
	     outlet.setOutletName("Main Outlet");
	     SalesTax salesTax = salesTaxService.getSalesTaxBySalesTaxId(1,currentUser.getCompany().getCompanyId());
	     outlet.setSalesTax(salesTax);
	     Outlet newOutlet = outletService.addOutlet(outlet,currentUser.getCompany().getCompanyId());
	     if(newOutlet!=null){
	      Register register = new Register();
	      register.setActiveIndicator(true);
	      register.setRegisterName("Main Register");
	      register.setCacheManagementEnable("No");
	      register.setCompany(company);
	      register.setCreatedBy(currentUser.getUserId());
	      register.setCreatedDate(new Date());
	      register.setEmailReceipt("Yes");
	      register.setLastUpdated(new Date());
	      register.setNotes("");
	      register.setOutlet(outlet);
	      register.setPrintNotesOnReceipt("No");
	      register.setPrintReceipt("Yes");
	      register.setReceiptNumber("0");
	      register.setReceiptPrefix("");
	      register.setReceiptSufix("");
	      register.setSelectNextUserForSale("");
	      register.setShowDiscountOnReceipt("Yes");
	      register.setUpdatedBy(currentUser.getUserId());
	      register.setCompany(newCompany);
	      registerService.addRegister(register,currentUser.getCompany().getCompanyId());
	     }
	     util.AuditTrail(request, currentUser, "CompanyController.addNewCompany", 
	       "User "+ currentUser.getUserEmail()+" Added Company+"+companyBean.getCompanyName()+" successfully ",false);
	     return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.OUTLETS);
	    }else{
	     util.AuditTrail(request, currentUser, "CompanyController.addNewCompany", "User "+ 
	       currentUser.getUserEmail()+" Unable to added/Update Company : "+companyBean.getCompanyName(),false);
	     return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
	    }
	    
	   }catch(Exception e){
	    e.printStackTrace();logger.error(e.getMessage(),e);
	    StringWriter errors = new StringWriter();
	    e.printStackTrace(new PrintWriter(errors));
	    util.AuditTrail(request, currentUser, "CompanyController.addNewCompany",
	      "Error Occured " + errors.toString(),true);
	    return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
	   }
	  }else{
	   return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
	  }
	 }
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllCurrencies", method = RequestMethod.POST)
	public @ResponseBody Response GetAllCurrencies(HttpServletRequest request) {

		List<CurrencyBean> currencyBeans = new ArrayList<>();
		List<Currency> currencyList = null;
		HttpSession session = request.getSession(false);

		try {

			currencyList = currencyService.GetAllCurrencies();
			if (currencyList != null) {
				for (Currency currency : currencyList) {

					CurrencyBean currencyBean = new CurrencyBean();
					currencyBean.setCurrencyId(currency.getCurrencyId()
							.toString());
					currencyBean.setCurrencyName(currency.getCurrencyName());
					currencyBean.setCurrencyValue(currency.getCurrencyValue());
					currencyBeans.add(currencyBean);
				}
				return new Response(currencyBeans, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} else {
				return new Response(MessageConstants.RECORD_NOT_FOUND,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();logger.error(e.getMessage(),e);
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			return new Response(MessageConstants.SYSTEM_BUSY,
					StatusConstants.RECORD_NOT_FOUND,
					LayOutPageConstants.STAY_ON_PAGE);

		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllPrinterFormats", method = RequestMethod.POST)
	public @ResponseBody Response GetAllPrinterFormats(HttpServletRequest request) {

		List<PrinterFormatBean> printerFormatBeans = new ArrayList<>();
		List<PrinterFormat> printerFormatList = null;
		HttpSession session = request.getSession(false);

		try {

			printerFormatList = printerFormatService.GetAllPrinterFormats();
			if (printerFormatList != null) {
				for (PrinterFormat printerFormat : printerFormatList) {

					PrinterFormatBean printerFormatBean = new PrinterFormatBean();
					printerFormatBean.setPrinterFormatId(printerFormat.getPrinterFormatId().toString());
							
					printerFormatBean.setPrinterFormatValue(printerFormat.getPrinterFormatValue());
					
					printerFormatBeans.add(printerFormatBean);
				}
				return new Response(printerFormatBeans, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} else {
				return new Response(MessageConstants.RECORD_NOT_FOUND,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();logger.error(e.getMessage(),e);
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			return new Response(MessageConstants.SYSTEM_BUSY,
					StatusConstants.RECORD_NOT_FOUND,
					LayOutPageConstants.STAY_ON_PAGE);

		}

	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllTimeZones", method = RequestMethod.POST)
	public @ResponseBody Response GetAllTimeZones(HttpServletRequest request) {

		List<TimeZoneBean> timeZoneBeans = new ArrayList<>();
		List<TimeZone> timeZoneList = null;
		HttpSession session = request.getSession(false);

		try {

			timeZoneList = timeZoneService.GetAllTimeZones();
			if (timeZoneList != null) {
				for (TimeZone timeZone : timeZoneList) {

					TimeZoneBean timeZoneBean = new TimeZoneBean();
					timeZoneBean.setTimeZoneId(timeZone.getTimeZoneId().toString());
					timeZoneBean.setTimeZoneValue(timeZone.getTimeZoneValue().toString());
					
					timeZoneBeans.add(timeZoneBean);
				}
				return new Response(timeZoneBeans, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} else {
				return new Response(MessageConstants.RECORD_NOT_FOUND,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();logger.error(e.getMessage(),e);
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			return new Response(MessageConstants.SYSTEM_BUSY,
					StatusConstants.RECORD_NOT_FOUND,
					LayOutPageConstants.STAY_ON_PAGE);

		}

	}

}

