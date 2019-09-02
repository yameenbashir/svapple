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

import com.dowhile.Company;
import com.dowhile.Outlet;
import com.dowhile.SalesTax;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.controller.bean.SalesTaxControllerBean;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.SalesTaxBean;
import com.dowhile.service.CompanyService;
import com.dowhile.service.OutletService;
import com.dowhile.service.RegisterService;
import com.dowhile.service.SalesTaxService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * Yameen Bashir
 */
@Controller
@RequestMapping("/salesTax")
public class SalesTaxController {

	@Resource
	private RegisterService registerService;
	@Resource
	private OutletService outletService;
	@Resource
	private ServiceUtil util;
	@Resource
	private CompanyService companyService;
	@Resource
	private SalesTaxService salesTaxService;
	@RequestMapping("/layout")
	public String getSalesTaxControllerPartialPage(ModelMap modelMap) {
		return "salesTax/layout";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/geSalesTaxControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response geSalesTaxControllerData(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<OutletBean> outletBeans = null;
		List<SalesTaxBean> salesTaxListBeans = null;
		
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				Response response = getOutlets(sessionId,request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					outletBeans = (List<OutletBean>) response.data;
				}
				response = getAllSalesTax(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					salesTaxListBeans = (List<SalesTaxBean>) response.data;
				}
				
				SalesTaxControllerBean salesTaxControllerBean = new SalesTaxControllerBean();
				salesTaxControllerBean.setOutletBeans(outletBeans);
				salesTaxControllerBean.setSalesTaxListBeans(salesTaxListBeans);

				util.AuditTrail(request, currentUser, "SalesTaxController.geSalesTaxControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived geSalesTaxControllerData successfully ",false);
				return new Response(salesTaxControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SalesTaxController.geSalesTaxControllerData",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/addSalesTax/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response addSalesTax(@PathVariable("sessionId") String sessionId,
			@RequestBody SalesTaxBean salesTaxBean, HttpServletRequest request) {
		
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				SalesTax salesTax = new SalesTax();
				Company company = companyService.getCompanyDetailsByCompanyID(currentUser.getCompany().getCompanyId());
				salesTax.setCompany(company);
				salesTax.setSalesTaxName(salesTaxBean.getSalesTaxName());
				salesTax.setSalesTaxPercentage(Double.valueOf(salesTaxBean.getSalesTaxPercentage()));
				salesTax.setEffectiveDate(new Date());
				salesTax.setActiveIndicator(true);
				salesTax.setCreatedDate(new Date());
				salesTax.setLastUpdated(new Date());
				salesTax.setCreatedBy(currentUser.getUserId());
				salesTax.setUpdatedBy(currentUser.getUserId());
				salesTaxService.addSalesTax(salesTax,currentUser.getCompany().getCompanyId());
					
					util.AuditTrail(request, currentUser, "SalesTaxController.addSalesTax", 
							"User "+ currentUser.getUserEmail()+" Added Sales Tax +"+salesTaxBean.getSalesTaxName()+" successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.SALES_TAX);
				
				
			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SalesTaxController.addSalesTax",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/updateSalesTax/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response updateSalesTax(@PathVariable("sessionId") String sessionId,
			@RequestBody SalesTaxBean salesTaxBean, HttpServletRequest request) {
		
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				SalesTax salesTax = salesTaxService.getSalesTaxBySalesTaxId(Integer.valueOf(salesTaxBean.getSalesTaxId()),currentUser.getCompany().getCompanyId());
				salesTax.setSalesTaxName(salesTaxBean.getSalesTaxName());
				salesTax.setSalesTaxPercentage(Double.valueOf(salesTaxBean.getSalesTaxPercentage()));
				salesTax.setLastUpdated(new Date());
				salesTax.setUpdatedBy(currentUser.getUserId());
				salesTaxService.updateSalesTax(salesTax,currentUser.getCompany().getCompanyId());
					
					util.AuditTrail(request, currentUser, "SalesTaxController.addSalesTax", 
							"User "+ currentUser.getUserEmail()+" Added Sales Tax +"+salesTaxBean.getSalesTaxName()+" successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.SALES_TAX);
				
				
			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SalesTaxController.addSalesTax",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getOutlets{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getOutlets(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<OutletBean> outletBeans = new ArrayList<>();
		List<Outlet> outlets = new ArrayList<>();
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				if(currentUser.getRole().getRoleId()==1){
					outlets = outletService.getOutlets(currentUser.getCompany().getCompanyId());
				}else{
					outlets.add(currentUser.getOutlet());
				}
				
				if(outlets!=null && outlets.size()>0){
					for(Outlet outlet:outlets){
						
							OutletBean outletBean = new OutletBean();
							outletBean.setOutletId(outlet.getOutletId().toString());
							outletBean.setOutletName(outlet.getOutletName());
							SalesTax salesTax = salesTaxService.getSalesTaxBySalesTaxId(outlet.getSalesTax().getSalesTaxId(),currentUser.getCompany().getCompanyId());
							outletBean.setSalesTaxId(salesTax.getSalesTaxId().toString());
							outletBean.setSalesTaxName(salesTax.getSalesTaxName()+"("+salesTax.getSalesTaxPercentage().toString()+")");
							outletBeans.add(outletBean);
					}
					util.AuditTrail(request, currentUser, "SalesTaxController.getOutlets", 
							"User "+ currentUser.getUserEmail()+" retrived all outlets successfully ",false);
					return new Response(outletBeans, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "SalesTaxController.getOutlets", "User "+ 
							currentUser.getUserEmail()+" Unable to retrived all outlets ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SalesTaxController.getOutlets",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);

			}
			}else{
				return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
			}

		}
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@RequestMapping(value = "/getAllSalesTax/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllSalesTax(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<SalesTaxBean> salesTaxListBeans = new ArrayList<>();
		List<SalesTax> salesTaxList = new ArrayList<>();
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

		try {
			if(currentUser.getRole().getRoleId()==1){
				salesTaxList = salesTaxService.GetAllSalesTax(currentUser.getCompany().getCompanyId());
			}else{
				SalesTax salesTax = salesTaxService.getSalesTaxBySalesTaxId(currentUser.getOutlet().getSalesTax().getSalesTaxId(), currentUser.getCompany().getCompanyId());
				if(salesTax!=null)
				salesTaxList.add(salesTax);
			}

			
			if (salesTaxList != null && salesTaxList.size()>0) {
				for (SalesTax salesTax : salesTaxList) {

					SalesTaxBean salesTaxBean = new SalesTaxBean();
					salesTaxBean.setSalesTaxId(salesTax.getSalesTaxId().toString());
					salesTaxBean.setSalesTaxName(salesTax.getSalesTaxName().toString());
					salesTaxBean.setSalesTaxPercentage(salesTax.getSalesTaxPercentage().toString());
					salesTaxListBeans.add(salesTaxBean);
				}
				util.AuditTrail(request, currentUser, "SalesTaxController.getAllSalesTax", 
						"User "+ currentUser.getUserEmail()+" retrived all sales tax successfully ",false);
				return new Response(salesTaxListBeans, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} else {
				util.AuditTrail(request, currentUser, "SalesTaxController.getAllSalesTax", "User "+ 
						currentUser.getUserEmail()+" Unable to retrived all sales tax ",false);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			util.AuditTrail(request, currentUser, "SalesTaxController.getAllSalesTax",
					"Error Occured " + errors.toString(),true);
			return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);

		}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
}

