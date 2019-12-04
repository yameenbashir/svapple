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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Address;
import com.dowhile.Company;
import com.dowhile.Currency;
import com.dowhile.PrinterFormat;
import com.dowhile.TimeZone;
import com.dowhile.User;
import com.dowhile.angualrspringapp.beans.CurrencyBean;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.controller.bean.StoreControllerBean;
import com.dowhile.frontend.mapping.bean.AddressBean;
import com.dowhile.frontend.mapping.bean.CompanyBean;
import com.dowhile.frontend.mapping.bean.PrinterFormatBean;
import com.dowhile.frontend.mapping.bean.TimeZoneBean;
import com.dowhile.service.AddressService;
import com.dowhile.service.CompanyService;
import com.dowhile.service.CurrencyService;
import com.dowhile.service.PrinterFormatService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * @author Hassan
 *
 */
@Controller
@RequestMapping("/store")
public class StoreController {

	@Resource
	private CompanyService companyService;
	@Resource
	private CurrencyService currencyService;
	@Resource
	private PrinterFormatService printerFormatService;
	@Resource
	private ServiceUtil util;
	@Resource
	private com.dowhile.service.TimeZoneService timeZoneService;
	@Resource
	private AddressService addressService;

	@RequestMapping("/layout")
	public String getStoreControllerPartialPage(ModelMap modelMap) {
		return "store/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getStoreControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getStoreControllerData(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<TimeZoneBean> timeZoneBeans = null;
		List<CurrencyBean> currencyBeans= null;
		List<PrinterFormatBean> printerFormatBeans = null;
		CompanyBean companyBean = null;

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {

				Response response = getAllTimeZones(sessionId,request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					timeZoneBeans = (List<TimeZoneBean>) response.data;
				}
				response = getAllCurrencies(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					currencyBeans = (List<CurrencyBean>) response.data;
				}
				response = getAllPrinterFormat(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					printerFormatBeans = (List<PrinterFormatBean>) response.data;
				}
				response = getCompanyDetailsByCompanyID(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					companyBean = (CompanyBean) response.data;
				}

				StoreControllerBean storeControllerBean = new StoreControllerBean();
				storeControllerBean.setCompanyBean(companyBean);
				storeControllerBean.setCurrencyBeans(currencyBeans);
				storeControllerBean.setPrinterFormatBeans(printerFormatBeans);
				storeControllerBean.setTimeZoneBeans(timeZoneBeans);

				util.AuditTrail(request, currentUser, "StoreController.getStoreControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived StoreControllerData successfully ",false);
				return new Response(storeControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "StoreController.getStoreControllerData",
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
	@RequestMapping(value = "/getAllCurrencies/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllCurrencies(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<CurrencyBean> currencyBeans = new ArrayList<>();
		List<Currency> currencyList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
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
					util.AuditTrail(request, currentUser, "StoreController.getAllCurrencies", 
							"User "+ currentUser.getUserEmail()+" retrived all currencies successfully ",false);
					return new Response(currencyBeans, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "StoreController.getAllCurrencies", "User "+ 
							currentUser.getUserEmail()+" Unable to retrived all currencies ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);

				}
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "StoreController.getAllCurrencies",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}


	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllPrinterFormat/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllPrinterFormat(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<PrinterFormatBean> printerFormatBeans = new ArrayList<>();
		List<PrinterFormat> printerFormatList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {

				printerFormatList = printerFormatService.GetAllPrinterFormats();
				if (printerFormatList != null) {
					for (PrinterFormat printerFormat : printerFormatList) {

						PrinterFormatBean printerFormatBean = new PrinterFormatBean();
						printerFormatBean.setPrinterFormatId(printerFormat.getPrinterFormatId().toString());

						printerFormatBean.setPrinterFormatValue(printerFormat.getPrinterFormatValue());

						printerFormatBeans.add(printerFormatBean);
					}
					util.AuditTrail(request, currentUser, "StoreController.getAllPrinterFormat", 
							"User "+ currentUser.getUserEmail()+" retrived all printer format successfully ",false);
					return new Response(printerFormatBeans, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "StoreController.getAllPrinterFormat", "User "+ 
							currentUser.getUserEmail()+" Unable to retrived all printer format ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);

				}
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "StoreController.getAllPrinterFormat",
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
					util.AuditTrail(request, currentUser, "StoreController.getAllTimeZones", 
							"User "+ currentUser.getUserEmail()+" retrived all time zones successfully ",false);
					return new Response(timeZoneBeans, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "StoreController.getAllTimeZones", "User "+ 
							currentUser.getUserEmail()+" Unable to retrived all time zones ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);

				}
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "StoreController.getAllTimeZones",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

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
//					List<Address> addresses =  addressService.getAddressByCompanyId(company.getCompanyId());
					List<Address> addresses = new ArrayList<>();
					companyBean.setCompanyId(company.getCompanyId().toString());
					if(company.getCurrency()!=null){
						companyBean.setCurrencyID(company.getCurrency().getCurrencyId().toString());
					}else{
						companyBean.setCurrencyID("");
					}
					companyBean.setCompanyName(company.getCompanyName());
					if(company.getPrinterFormat()!=null){
						companyBean.setPrinterformatID(company.getPrinterFormat().getPrinterFormatId().toString());

					}else{
						companyBean.setPrinterformatID("");
					}
					if(company.getTimeZone()!=null){
						companyBean.setTimeZoneID(company.getTimeZone().getTimeZoneId().toString());

					}else{
						companyBean.setTimeZoneID("");
					}
					companyBean.setSkuGeneration(company.getSkuGeneration());
					companyBean.setCurrentSequenceNumber(company.getCurrentSequenceNumber());
					companyBean.setDisplayPrices(company.getDisplayPrices());
					companyBean.setUserSecurity(company.getUserSwitchSecurity());
					companyBean.setUrl(company.getUrl());
					List<AddressBean> listAddressBean =  new ArrayList<>();
					if(addresses != null && !addresses.isEmpty())
					{
						for (Address item : addresses) {
							AddressBean addressBean = new AddressBean();
							addressBean.setAddressId( item.getAddressId().toString() );
							addressBean.setAddressType(item.getAddressType());
							addressBean.setCity(item.getCity());
							addressBean.setContactName(item.getContactName());
							addressBean.setCountry(item.getCounty());
							addressBean.setEmail(item.getEmail());
							addressBean.setFax(item.getFax());
							addressBean.setFirstName(item.getFirstName());
							addressBean.setLastName(item.getLastName());
							addressBean.setPhone(item.getPhone());
							addressBean.setPostalCode(item.getPostalCode());
							addressBean.setState(item.getState());
							addressBean.setPhone(item.getPhone());
							addressBean.setStreet(item.getStreet());
							addressBean.setSuburb(item.getSuburb());
							addressBean.setTwitter(item.getTwitter());
							addressBean.setWebsite(item.getWebsite());
							listAddressBean.add(addressBean);
						}
						companyBean.setAddresses(listAddressBean);
					}
					/*else if(addresses == null )
					{
						AddressBean physicalAddressBean = new AddressBean();
						AddressBean postalAddressBean = new AddressBean();
						listAddressBean.add(physicalAddressBean);
						listAddressBean.add(postalAddressBean);
						companyBean.setAddresses(listAddressBean);
					}*/
					util.AuditTrail(request, currentUser, "StoreController.getCompanyDetailsByCompanyID", 
							"User "+ currentUser.getUserEmail()+" retrived company detail successfully ",false);
					return new Response(companyBean, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "StoreController.getCompanyDetailsByCompanyID", "User "+ 
							currentUser.getUserEmail()+" Unable to retrived company detail ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);

				}
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "StoreController.getCompanyDetailsByCompanyID",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateCompany/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response updateCompanyDetails(@PathVariable("sessionId") String sessionId,
			@RequestBody CompanyBean companyBean, HttpServletRequest request){

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				if (companyBean != null) {
					int companyId= Integer.parseInt(companyBean.getCompanyId());
					Company company = companyService.getCompanyDetailsByCompanyID(companyId);
					company.setCompanyName(companyBean.getCompanyName());
					company.setDisplayPrices(companyBean.getDisplayPrices());
					company.setSkuGeneration(companyBean.getSkuGeneration());
					company.setCurrentSequenceNumber(companyBean.getCurrentSequenceNumber());
					company.setUserSwitchSecurity(companyBean.getUserSecurity());
					company.setUrl(companyBean.getUrl());
					PrinterFormat printerFormat = printerFormatService.GetPrinterFormatByID(Integer.parseInt(companyBean.getPrinterformatID()));
					company.setPrinterFormat(printerFormat);				
					TimeZone timeZone =timeZoneService.getTimeZoneByTimeZoneId(Integer.parseInt(companyBean.getTimeZoneID()));
					company.setTimeZone(timeZone);				
					Currency currency = currencyService.GetCurrencyById(Integer.parseInt(companyBean.getCurrencyID()));
					company.setCurrency(currency);				
//					List<Address> addresses =  addressService.getAddressByCompanyId(companyId);
					List<Address> addresses = new ArrayList<>();
					if(companyBean != null && companyBean.getAddresses()!=null &&  !companyBean.getAddresses().isEmpty()){
						for (AddressBean item : companyBean.getAddresses()) {
							for (Address address : addresses) {
								if(item.getAddressId() == address.getAddressId().toString() ) {
									address.setAddressType(item.getAddressType());
									address.setCity(item.getCity());
									address.setContactName(item.getContactName());
									address.setCounty(item.getCounty());
									address.setEmail(item.getEmail());
									address.setFax(item.getFax());
									address.setFirstName(item.getFirstName());
									address.setLastName(item.getLastName());
									address.setPhone(item.getPhone());
									address.setPostalCode(item.getPostalCode());
									address.setState(item.getState());
									address.setPhone(item.getPhone());
									address.setStreet(item.getStreet());
									address.setSuburb(item.getSuburb());
									address.setTwitter(item.getTwitter());
									address.setWebsite(item.getWebsite());
								}
							}

						}		    
					}
					company = companyService.updateCompany(company); 
					util.AuditTrail(request, currentUser, "StoreController.updateCompany", 
							"User "+ currentUser.getUserEmail()+" updated company information successfully ",false);
					return new Response(companyBean, StatusConstants.SUCCESS,
							LayOutPageConstants.HOME);
				}
				else {
					util.AuditTrail(request, currentUser, "StoreController.updateCompany", "User "+ 
							currentUser.getUserEmail()+" Unable to update company information ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "StoreCreditController.updateCompany",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateContactInformation/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response updateContactInformation(@PathVariable("sessionId") String sessionId,
			@RequestBody CompanyBean companyBean, HttpServletRequest request){

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				if (companyBean != null) {
					int companyId= Integer.parseInt(companyBean.getCompanyId());
					List<Address> addresses =  addressService.getAddressByCompanyId(companyId);
					if(companyBean != null && !companyBean.getAddresses().isEmpty() && addresses != null){
						for (AddressBean item : companyBean.getAddresses()) {
							for (Address address : addresses) {
								if(Integer.parseInt( item.getAddressId()) ==   address.getAddressId()   ) {
									address.setContactName(item.getContactName());
									address.setEmail(item.getEmail());
									address.setFirstName(item.getFirstName());
									address.setLastName(item.getLastName());
									address.setPhone(item.getPhone());
									address.setTwitter(item.getTwitter());
									address.setWebsite(item.getWebsite());
									addressService.updateAddress(address,currentUser.getCompany().getCompanyId()); 
								}
							}
						}		    
					}
					else if(companyBean != null && !companyBean.getAddresses().isEmpty() && addresses == null){
						Address address = new Address();
						AddressBean item = companyBean.getAddresses().get(0);
						// Company company = new Company();
						// company.setCompanyId(companyId); 
						address.setCompany(currentUser.getCompany());
						address.setContactName(item.getContactName());
						address.setEmail(item.getEmail());
						address.setFirstName(item.getFirstName());
						address.setLastName(item.getLastName());
						address.setPhone(item.getPhone());
						address.setTwitter(item.getTwitter());
						address.setWebsite(item.getWebsite());
						addressService.addAddress(address,currentUser.getCompany().getCompanyId()); 
					}
					util.AuditTrail(request, currentUser, "StoreController.updateContactInformation", 
							"User "+ currentUser.getUserEmail()+" updated company Contact information successfully ",false);
					return new Response(companyBean, StatusConstants.SUCCESS,
							LayOutPageConstants.HOME);
				}
				else {
					util.AuditTrail(request, currentUser, "StoreController.updateContactInformation", "User "+ 
							currentUser.getUserEmail()+" Unable to update company Contact information ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "StoreCreditController.updateContactInformation",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateAddress/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response updateAddress(@PathVariable("sessionId") String sessionId,
			@RequestBody CompanyBean companyBean, HttpServletRequest request){

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				if (companyBean != null) {
					int companyId= Integer.parseInt(companyBean.getCompanyId());
					List<Address> addresses =  addressService.getAddressByCompanyId(companyId);
					if(companyBean != null && !companyBean.getAddresses().isEmpty()){
						for (AddressBean item : companyBean.getAddresses()) {
							for (Address address : addresses) {
								if(Integer.parseInt( item.getAddressId()) ==   address.getAddressId()   ) {
									address.setAddressType(item.getAddressType());
									address.setCity(item.getCity());
									address.setCounty(item.getCounty());
									address.setFirstName(item.getFirstName());
									address.setLastName(item.getLastName());
									address.setContactName(item.getContactName());
									address.setEmail(item.getEmail());
									address.setFirstName(item.getFirstName());
									address.setLastName(item.getLastName());
									address.setPhone(item.getPhone());
									address.setTwitter(item.getTwitter());
									address.setWebsite(item.getWebsite());
									address.setPostalCode(item.getPostalCode());
									address.setState(item.getState());
									address.setPhone(item.getPhone());
									address.setStreet(item.getStreet());
									address.setSuburb(item.getSuburb());
									addressService.updateAddress(address,currentUser.getCompany().getCompanyId()); 
								}
								else{
									address.setAddressType(item.getAddressType());
									address.setCity(item.getCity());
									address.setCounty(item.getCounty());
									address.setCompany(currentUser.getCompany());
									address.setFirstName(item.getFirstName());
									address.setLastName(item.getLastName());
									address.setContactName(item.getContactName());
									address.setEmail(item.getEmail());
									address.setFirstName(item.getFirstName());
									address.setLastName(item.getLastName());
									address.setPhone(item.getPhone());
									address.setTwitter(item.getTwitter());
									address.setWebsite(item.getWebsite());
									address.setPostalCode(item.getPostalCode());
									address.setState(item.getState());
									address.setPhone(item.getPhone());
									address.setStreet(item.getStreet());
									address.setSuburb(item.getSuburb());
									addressService.addAddress(address,currentUser.getCompany().getCompanyId()); 
								}
							}
						}		    
					}
					util.AuditTrail(request, currentUser, "StoreController.updateAddress", 
							"User "+ currentUser.getUserEmail()+" updated company Address information successfully ",false);
					return new Response(companyBean, StatusConstants.SUCCESS,
							LayOutPageConstants.HOME);
				}
				else {
					util.AuditTrail(request, currentUser, "StoreController.updateAddress", "User "+ 
							currentUser.getUserEmail()+" Unable to update company Address information ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "StoreCreditController.updateAddress",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}

}
	
