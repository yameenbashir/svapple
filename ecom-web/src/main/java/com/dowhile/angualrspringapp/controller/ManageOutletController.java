/**
 * 
 */
package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Address;
import com.dowhile.Country;
import com.dowhile.Outlet;
import com.dowhile.Register;
import com.dowhile.SalesTax;
import com.dowhile.TimeZone;
import com.dowhile.User;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.ManageOutletControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.AddressBean;
import com.dowhile.frontend.mapping.bean.CountryBean;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.RegisterBean;
import com.dowhile.frontend.mapping.bean.SalesTaxBean;
import com.dowhile.frontend.mapping.bean.TimeZoneBean;
import com.dowhile.service.AddressService;
import com.dowhile.service.CompanyService;
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
@RequestMapping("/manageOutlet")
public class ManageOutletController {

	// private static Logger logger = Logger.getLogger(ManageOutletController.class.getName());
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
	@Resource
	private CountryService countryService;
	@Resource
	private AddressService addressService;
	@Resource
	private com.dowhile.service.TimeZoneService timeZoneService;
	@RequestMapping("/layout")
	public String getManageOutletControllerPartialPage(ModelMap modelMap) {
		return "manageOutlet/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getManageOutletControllerData/{sessionId}/{outletId}", method = RequestMethod.POST)
	public @ResponseBody Response getManageOutletControllerData(@PathVariable("sessionId") String sessionId,
			@PathVariable("outletId") String outletId,HttpServletRequest request) {

		List<TimeZoneBean> timeZoneBeans = null;
		List<CountryBean> countryListBeans= null;
		List<SalesTaxBean> salesTaxListBeans = null;
		OutletBean outletBean = null;
		
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
				if(outletId!=null && !outletId.equalsIgnoreCase("")){
					response = getOutletByOutletId(sessionId, outletId, request);
					if(response.status.equals(StatusConstants.SUCCESS)){
						outletBean = (OutletBean) response.data;
					}
				}
				
				ManageOutletControllerBean manageOutletControllerBean = new ManageOutletControllerBean();
				manageOutletControllerBean.setCountryListBeans(countryListBeans);
				manageOutletControllerBean.setOutletBean(outletBean);
				manageOutletControllerBean.setSalesTaxListBeans(salesTaxListBeans);
				manageOutletControllerBean.setTimeZoneBeans(timeZoneBeans);

				util.AuditTrail(request, currentUser, "ManageOutletController.getManageOutletControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived ManageOutletControllerData successfully ",false);
				return new Response(manageOutletControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManageOutletController.getManageOutletControllerData",
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
	@RequestMapping(value = "/updateOutlet/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response updateOutlet(@PathVariable("sessionId") String sessionId,
			@RequestBody OutletBean outletBean, HttpServletRequest request) {
		Address newAddress = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				Outlet outlet = outletService.getOuletByOutletId(Integer.valueOf(outletBean.getOutletId()),currentUser.getCompany().getCompanyId());
				if(outlet!=null){
					
					if(outlet.getAddress()!=null){
						Address address = addressService.getAddressByAddressId(outlet.getAddress().getAddressId(),currentUser.getCompany().getCompanyId()); 
						address.setCity(outletBean.getAddressbean().getCity());
						Country country = countryService.getCountryByCountryId(Integer.valueOf(outletBean.getAddressbean().getCountryId()));
						address.setCountry(country);
						address.setEmail(outletBean.getAddressbean().getEmail());
						address.setFax(outletBean.getAddressbean().getFax());
						address.setPhone(outletBean.getAddressbean().getPhone());
						address.setPostalCode(outletBean.getAddressbean().getPostalCode());
						address.setState(outletBean.getAddressbean().getState());
						address.setStreet(outletBean.getAddressbean().getStreet());
						address.setTwitter(outletBean.getAddressbean().getTwitter());
						address.setLastUpdated(new Date());
						address.setUpdatedBy(currentUser.getUserId());
						newAddress = addressService.updateAddress(address,currentUser.getCompany().getCompanyId());

					}else{
						Address address = new Address(); 
						address.setActiveIndicator(true);
						address.setAddressType(ControllersConstants.CONTACT_TYPE_OUTLET);
						address.setCity(outletBean.getAddressbean().getCity());
						address.setCompany(currentUser.getCompany());
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
						newAddress = addressService.addAddress(address,currentUser.getCompany().getCompanyId());
						
					}
					outlet.setAddress(newAddress);
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
					outletService.updateOutlet(outlet,currentUser.getCompany().getCompanyId());
					
					util.AuditTrail(request, currentUser, "ManageOutletController.updateOutlet", 
							"User "+ currentUser.getUserEmail()+" Updated Outlet+"+outletBean.getOutletName()+" successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.OUTLETS);
				}else{
					util.AuditTrail(request, currentUser, "ManageOutletController.updateOutlet", "User "+ 
							currentUser.getUserEmail()+" Unable to update Outlet : "+outletBean.getOutletName(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}
				
			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManageOutletController.updateOutlet",
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
				util.AuditTrail(request, currentUser, "ManageOutletController.getAllTimeZones", 
						"User "+ currentUser.getUserEmail()+" retrived all time zones successfully ",false);
				return new Response(timeZoneBeans, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} else {
				util.AuditTrail(request, currentUser, "ManageOutletController.getAllTimeZones", "User "+ 
						currentUser.getUserEmail()+" Unable to retrived all time zones ",false);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				
			}
		} catch (Exception e) {
			e.printStackTrace();// logger.error(e.getMessage(),e);
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			util.AuditTrail(request, currentUser, "ManageOutletController.getAllTimeZones",
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
				util.AuditTrail(request, currentUser, "ManageOutletController.getAllCountry", 
						"User "+ currentUser.getUserEmail()+" retrived all countries successfully ",false);
				return new Response(countryListBeans, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} else {
				util.AuditTrail(request, currentUser, "ManageOutletController.getAllCountry", "User "+ 
						currentUser.getUserEmail()+" Unable to retrived all countries ",false);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				
			}
		} catch (Exception e) {
			e.printStackTrace();// logger.error(e.getMessage(),e);
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			util.AuditTrail(request, currentUser, "ManageOutletController.getAllCountry",
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
				util.AuditTrail(request, currentUser, "ManageOutletController.getAllSalesTax", 
						"User "+ currentUser.getUserEmail()+" retrived all sales tax successfully ",false);
				return new Response(salesTaxListBeans, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} else {
				util.AuditTrail(request, currentUser, "ManageOutletController.getAllSalesTax", "User "+ 
						currentUser.getUserEmail()+" Unable to retrived all sales tax ",false);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				
			}
		} catch (Exception e) {
			e.printStackTrace();// logger.error(e.getMessage(),e);
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			util.AuditTrail(request, currentUser, "ManageOutletController.getAllSalesTax",
					"Error Occured " + errors.toString(),true);
			return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);

		}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getOutletByOutletId/{sessionId}/{outletId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getOutletByOutletId(@PathVariable("sessionId") String sessionId,
			@PathVariable("outletId") String outletId,HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				Outlet outlet = outletService.getOuletByOutletId(Integer.valueOf(outletId), currentUser.getCompany().getCompanyId());
				if(outlet!=null){
						OutletBean outletBean = new OutletBean();
						List<RegisterBean> registerBeanList = new ArrayList<>();
						//	outletBean.setDefaultTax(outlet.getSelsTaxPercentage().toString());
						//outletBean.setDetails("");
						outletBean.setOutletId(outlet.getOutletId().toString());
						outletBean.setOutletName(outlet.getOutletName());
						SalesTax salesTax = salesTaxService.getSalesTaxBySalesTaxId(outlet.getSalesTax().getSalesTaxId(),currentUser.getCompany().getCompanyId());
						
						outletBean.setSalesTaxId(salesTax.getSalesTaxId().toString());
						outletBean.setSalesTaxName(salesTax.getSalesTaxName()+"("+salesTax.getSalesTaxPercentage().toString()+")");
						outletBean.setDefaultTax(salesTax.getSalesTaxPercentage().toString());
						outletBean.setOrderNumber(outlet.getOrderNumber());
						outletBean.setOrderNumberPrefix(outlet.getOrderNumberPrefix());
						outletBean.setSupplieNumberPrefix(outlet.getContactNumberPrefix());
						outletBean.setSupplierReturnNumber(outlet.getContactReturnNumber());
						AddressBean  addressBean = new AddressBean();
						if(outlet.getAddress()!=null){
							Address address = addressService.getAddressByAddressId(outlet.getAddress().getAddressId(),currentUser.getCompany().getCompanyId());
							addressBean.setAddressId(address.getAddressId().toString());
							addressBean.setCity(address.getCity());
							addressBean.setPostalCode(address.getPostalCode());
							addressBean.setState(address.getState());
							addressBean.setEmail(address.getEmail());
							addressBean.setTwitter(address.getTwitter());
							addressBean.setStreet(address.getStreet());
							addressBean.setPhone(address.getPhone());
							if(address.getCountry()!=null){
							Country country = countryService.getCountryByCountryId(address.getCountry().getCountryId());
							addressBean.setCountryId(country.getCountryId().toString());
							addressBean.setCountry(country.getCountryName());
							}
						}
						if(outlet.getTimeZone()!=null){
							TimeZone timeZone = timeZoneService.getTimeZoneByTimeZoneId(outlet.getTimeZone().getTimeZoneId());
							addressBean.setTimeZoneId(timeZone.getTimeZoneId().toString());
							addressBean.setTimeZone(timeZone.getTimeZoneValue());
						}
						outletBean.setAddressbean(addressBean);
						
						try {
							if(registerService.getRegestersByOutletId(outlet.getOutletId(),currentUser.getCompany().getCompanyId(),currentUser.getUserId())!=null){
								List<Register> regiserList = registerService.getRegestersByOutletId(outlet.getOutletId(),currentUser.getCompany().getCompanyId(),currentUser.getUserId());
								if(regiserList!=null && regiserList.size()>0){
									for(Register register:regiserList){
										RegisterBean regiseBean = new RegisterBean();
										regiseBean.setCacheManagementEnable(register.getCacheManagementEnable());
										regiseBean.setEmailReceipt(register.getEmailReceipt());
										regiseBean.setNotes(register.getNotes());
										regiseBean.setOutletId(outlet.getOutletId().toString());
										regiseBean.setOutletName(outlet.getOutletName());
										regiseBean.setPrintNotesOnReceipt(register.getPrintNotesOnReceipt());
										regiseBean.setPrintReceipt(register.getPrintReceipt());
										regiseBean.setReceiptNumber(register.getReceiptNumber());
										regiseBean.setReceiptPrefix(register.getReceiptPrefix());
										regiseBean.setReceiptSufix(register.getReceiptSufix());
										regiseBean.setRegisterId(register.getRegisterId().toString());
										regiseBean.setRegisterName(register.getRegisterName());
										regiseBean.setSelectNextUserForSale(register.getSelectNextUserForSale());
										regiseBean.setShowDiscountOnReceipt(register.getShowDiscountOnReceipt());
										registerBeanList.add(regiseBean);
										
									}
								}
								
								outletBean.setRegisterBeanList(registerBeanList);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();// logger.error(e.getMessage(),e);e.printStackTrace();// logger.error(e.getMessage(),e);
							StringWriter errors = new StringWriter();
							e.printStackTrace(new PrintWriter(errors));
							util.AuditTrail(request, currentUser, "ManageOutletController.getOutletByOutletId",
									"Error Occured " + errors.toString(),true);
						}

						outletBean.setStatus(String.valueOf(outlet.isActiveIndicator()));
						util.AuditTrail(request, currentUser, "ManageOutletController.getOutletByOutletId", 
								"User "+ currentUser.getUserEmail()+" retrived Outlet+"+outletBean.getOutletName()+" successfully ",false);
						return new Response(outletBean,StatusConstants.SUCCESS,LayOutPageConstants.MANAGE_OUTLET);
					}else{
						util.AuditTrail(request, currentUser, "ManageOutletController.getOutletByOutletId", "User "+ 
								currentUser.getUserEmail()+" Unable to retrived Outlet",false);
						return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
					}
					
				}catch(Exception e){
					e.printStackTrace();// logger.error(e.getMessage(),e);
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					util.AuditTrail(request, currentUser, "ManageOutletController.getOutletByOutletId",
							"Error Occured " + errors.toString(),true);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
				}
			}else{
				return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
			}
	}

}
