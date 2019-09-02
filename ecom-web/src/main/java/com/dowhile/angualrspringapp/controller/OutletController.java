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

import com.dowhile.Address;
import com.dowhile.Company;
import com.dowhile.Contact;
import com.dowhile.Country;
import com.dowhile.Outlet;
import com.dowhile.SalesTax;
import com.dowhile.TimeZone;
import com.dowhile.User;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.OutletControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.CountryBean;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.SalesTaxBean;
import com.dowhile.frontend.mapping.bean.TimeZoneBean;
import com.dowhile.service.AddressService;
import com.dowhile.service.CompanyService;
import com.dowhile.service.ContactService;
import com.dowhile.service.CountryService;
import com.dowhile.service.OutletService;
import com.dowhile.service.RegisterService;
import com.dowhile.service.SalesTaxService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * @author Yameen Bashir
 *
 */
@Controller
@RequestMapping("/outlet")
public class OutletController {

	@Resource
	private CompanyService companyService;
	@Resource
	private ContactService contactService;
	@Resource
	private OutletService outletService;
	
	@Resource
	private RegisterService registerService;
	@Resource
	private ServiceUtil util;
	@Resource
	private SalesTaxService salesTaxService;
	@Resource
	private CountryService countryService;
	@Resource
	private AddressService addressService;
	@Resource
	private com.dowhile.service.TimeZoneService timeZoneService;	
	@RequestMapping("/layout")
	public String getOutletControllerPartialPage(ModelMap modelMap) {
		return "outlet/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getOutletControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getOutletControllerData(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<TimeZoneBean> timeZoneBeans = null;
		List<CountryBean> countryListBeans= null;
		List<SalesTaxBean> salesTaxListBeans = null;
		
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");


			try {

				Response response = getAllTimeZones(sessionId,request);

				if(response.status.equals(StatusConstants.SUCCESS)){
					timeZoneBeans = (List<TimeZoneBean>) response.data;
				}


				response = getAllCountry(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					countryListBeans = (List<CountryBean>) response.data;
				}


				response = getAllSalesTax(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					salesTaxListBeans = (List<SalesTaxBean>) response.data;
				}
				OutletControllerBean outletControllerBean = new OutletControllerBean();
				outletControllerBean.setCountryListBeans(countryListBeans);
				outletControllerBean.setSalesTaxListBeans(salesTaxListBeans);
				outletControllerBean.setTimeZoneBeans(timeZoneBeans);


				util.AuditTrail(request, currentUser, "OutletController.getOutletControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived OutletControllerData successfully ",false);
				return new Response(outletControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "OutletController.getOutletControllerData",
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
	@RequestMapping(value = "/addOutlet/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response addOutlet(@PathVariable("sessionId") String sessionId,
			@RequestBody OutletBean outletBean, HttpServletRequest request) {
		
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				Company company = companyService.getCompanyDetailsByCompanyID(currentUser.getCompany().getCompanyId()); //Need to fix companyId cases
				if(company!=null){
					Address address = new Address(); 
					address.setActiveIndicator(true);
					address.setAddressType(ControllersConstants.CONTACT_TYPE_OUTLET);
					address.setCity(outletBean.getAddressbean().getCity());
					address.setCompany(company);
					Country country = countryService.getCountryByCountryId(Integer.valueOf(outletBean.getAddressbean().getCountryId()));
					address.setCountry(country);
					address.setCreatedBy(currentUser.getUserId());
					address.setUpdatedBy(currentUser.getUserId());
					address.setCreatedDate(new Date());
					address.setLastUpdated(new Date());
					address.setEmail(outletBean.getAddressbean().getEmail());
					address.setFax(outletBean.getAddressbean().getFax());
					address.setPhone(outletBean.getAddressbean().getPhone());
					address.setPostalCode(outletBean.getAddressbean().getPostalCode());
					address.setState(outletBean.getAddressbean().getState());
					address.setStreet(outletBean.getAddressbean().getStreet());
					address.setTwitter(outletBean.getAddressbean().getTwitter());
					address.setCompany(currentUser.getCompany());
					Address newAddress = addressService.addAddress(address,currentUser.getCompany().getCompanyId());
					
					if(newAddress!=null){
						Outlet outlet =  new Outlet();
						outlet.setActiveIndicator(true);
						outlet.setCompany(company);
						outlet.setCreatedBy(currentUser.getUserId());
						outlet.setCreatedDate(new Date());
						outlet.setLastUpdated(new Date());
						outlet.setOrderNumber(outletBean.getOrderNumber());
						outlet.setOrderNumberPrefix(outletBean.getOrderNumberPrefix());
						outlet.setOutletName(outletBean.getOutletName());
						TimeZone timeZone = timeZoneService.getTimeZoneByTimeZoneId(Integer.valueOf(outletBean.getAddressbean().getTimeZoneId()));
						outlet.setTimeZone(timeZone);
						SalesTax salesTax = salesTaxService.getSalesTaxBySalesTaxId(Integer.valueOf(outletBean.getSalesTaxId()),currentUser.getCompany().getCompanyId());
						outlet.setSalesTax(salesTax);
						outlet.setContactNumberPrefix(outletBean.getSupplieNumberPrefix());
						outlet.setContactReturnNumber(outletBean.getSupplierReturnNumber());
						outlet.setUpdatedBy(currentUser.getUserId());
						outlet.setAddress(address);
						outlet.setCompany(currentUser.getCompany());
						outlet = outletService.addOutlet(outlet,currentUser.getCompany().getCompanyId());
						Contact contact = new Contact();
						
						contact.setActiveIndicator(true);
						contact.setContactBalance(BigDecimal.ZERO);
						contact.setContactName(outlet.getOutletName());
						contact.setCreatedBy(currentUser.getUserId());
						contact.setCreatedDate(new Date());
						contact.setLastUpdated(new Date());
						contact.setCompany(company);
						contact.setContactOutletId(outlet.getOutletId());
						contact.setContactType("OUTLET");
						contact.setOutlet(outlet);
						contactService.addContact(contact, company.getCompanyId());
					}
					util.AuditTrail(request, currentUser, "OutletController.addOutlet", 
							"User "+ currentUser.getUserEmail()+" Added Outlet+"+outletBean.getOutletName()+" successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.OUTLETS);
				}else{
					util.AuditTrail(request, currentUser, "OutletController.addOutlet", "User "+ 
							currentUser.getUserEmail()+" Unable to add Outlet : "+outletBean.getOutletName(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}
				
			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "OutletController.addOutlet",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllTimeZones/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllTimeZones(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<TimeZoneBean> timeZoneBeans = new ArrayList<>();
		List<TimeZone> timeZoneList = null;
		//HttpSession session = request.getSession(false);
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
		try {

			timeZoneList = timeZoneService.GetAllTimeZones();
			if (timeZoneList != null) {
				for (TimeZone timeZone : timeZoneList) {

					TimeZoneBean timeZoneBean = new TimeZoneBean();
					timeZoneBean.setTimeZoneId(timeZone.getTimeZoneId().toString());
					timeZoneBean.setTimeZoneValue(timeZone.getTimeZoneValue().toString());
					
					timeZoneBeans.add(timeZoneBean);
				}
				util.AuditTrail(request, currentUser, "OutletController.getAllTimeZones", 
						"User "+ currentUser.getUserEmail()+" retrived all time zones successfully ",false);
				return new Response(timeZoneBeans, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} else {
				util.AuditTrail(request, currentUser, "OutletController.getAllTimeZones", "User "+ 
						currentUser.getUserEmail()+" Unable to retrived all time zones ",false);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			util.AuditTrail(request, currentUser, "OutletController.getAllTimeZones",
					"Error Occured " + errors.toString(),true);
			return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);

		}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllCountry/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllCountry(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<CountryBean> countryListBeans = new ArrayList<>();
		List<Country> countryList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

		try {

			countryList = countryService.GetAllCountry();
			if (countryList != null) {
				for (Country country : countryList) {

					CountryBean countryBean = new CountryBean();
					countryBean.setCountryId(country.getCountryId().toString());
					countryBean.setCountryName(country.getCountryName().toString());
					countryListBeans.add(countryBean);
				}
				util.AuditTrail(request, currentUser, "OutletController.getAllCountry", 
						"User "+ currentUser.getUserEmail()+" retrived all countries successfully ",false);
				return new Response(countryListBeans, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} else {
				util.AuditTrail(request, currentUser, "OutletController.getAllCountry", "User "+ 
						currentUser.getUserEmail()+" Unable to retrived all countries ",false);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			util.AuditTrail(request, currentUser, "OutletController.getAllCountry",
					"Error Occured " + errors.toString(),true);
			return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);

		}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllSalesTax/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllSalesTax(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<SalesTaxBean> salesTaxListBeans = new ArrayList<>();
		List<SalesTax> salesTaxList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

		try {

			salesTaxList = salesTaxService.GetAllSalesTax(currentUser.getCompany().getCompanyId());
			if (salesTaxList != null) {
				for (SalesTax salesTax : salesTaxList) {

					SalesTaxBean salesTaxBean = new SalesTaxBean();
					salesTaxBean.setSalesTaxId(salesTax.getSalesTaxId().toString());
					salesTaxBean.setSalesTaxName(salesTax.getSalesTaxName().toString());
					salesTaxBean.setSalesTaxPercentage(salesTax.getSalesTaxPercentage().toString());
					salesTaxListBeans.add(salesTaxBean);
				}
				util.AuditTrail(request, currentUser, "OutletController.getAllSalesTax", 
						"User "+ currentUser.getUserEmail()+" retrived all sales tax successfully ",false);
				return new Response(salesTaxListBeans, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} else {
				util.AuditTrail(request, currentUser, "OutletController.getAllSalesTax", "User "+ 
						currentUser.getUserEmail()+" Unable to retrived all sales tax ",false);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			util.AuditTrail(request, currentUser, "OutletController.getAllSalesTax",
					"Error Occured " + errors.toString(),true);
			return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);

		}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
}

