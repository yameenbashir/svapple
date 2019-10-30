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

import com.dowhile.Address;
import com.dowhile.Brand;
import com.dowhile.Company;
import com.dowhile.Configuration;
import com.dowhile.Contact;
import com.dowhile.ContactGroup;
import com.dowhile.Country;
import com.dowhile.Currency;
import com.dowhile.Menu;
import com.dowhile.Outlet;
import com.dowhile.PrinterFormat;
import com.dowhile.Register;
import com.dowhile.Role;
import com.dowhile.SalesTax;
import com.dowhile.TimeZone;
import com.dowhile.User;
import com.dowhile.UserOutlets;
import com.dowhile.angualrspringapp.beans.CurrencyBean;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.CompanyBean;
import com.dowhile.frontend.mapping.bean.PrinterFormatBean;
import com.dowhile.frontend.mapping.bean.TimeZoneBean;
import com.dowhile.service.AddressService;
import com.dowhile.service.BrandService;
import com.dowhile.service.CompanyService;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.ContactGroupService;
import com.dowhile.service.ContactService;
import com.dowhile.service.CurrencyService;
import com.dowhile.service.MenuService;
import com.dowhile.service.OutletService;
import com.dowhile.service.PrinterFormatService;
import com.dowhile.service.RegisterService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.SalesTaxService;
import com.dowhile.service.UserOutletsService;
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
	private ResourceService resourceService;
	 
	 @Resource
	 private OutletService outletService;
	 
	 @Resource
	 private RegisterService registerService;
	 
	 @Resource
	 private ServiceUtil util;
	 
	 @Resource
	 private SalesTaxService salesTaxService;
	 
	 @Resource 
	 private AddressService addressService;
	 
	 @Resource
	 private ContactGroupService contactGroupService;
	 @Resource
	 private UserOutletsService userOutletService;
	 @Resource
	 private ContactService contactService;
	 @Resource
	 private BrandService brandService;
	 @Resource
	 private ConfigurationService configurationService;
	 @Resource
	 private MenuService menuService;
	
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
		   

			    	 List<Company> companiesList = companyService.getCompanies();
			 	    int size = companiesList.size();
			 	    for(int i=0;i<size;i++) {
			 	        if(companiesList.get(i).getCompanyName().equals(company.getCompanyName())) {
			 	        	
			 	        	util.AuditTrail(request, currentUser, "CompanyController.addNewCompany", "User "+ 
			 	        	       currentUser.getUserEmail()+" Company with same name is already exist. : "+companyBean.getCompanyName(),false);
			 	        	     return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			 	        }
			 	    }
			 	    
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
		    Company newCompany = companyService.addCompany(company);
		    if(newCompany!=null){
		    	 SalesTax salesTax = new SalesTax();
			     salesTax.setSalesTaxName("No Tax");
			     salesTax.setSalesTaxPercentage(new Double("0"));
			     salesTax.setEffectiveDate(new Date());
			     salesTax.setActiveIndicator(true);
			     salesTax.setCreatedDate(new Date());
			     salesTax.setLastUpdated(new Date());
			     salesTax.setCreatedBy(currentUser.getUserId());
			     salesTax.setUpdatedBy(currentUser.getUserId());
			     salesTax.setCompany(newCompany);
			     SalesTax SalesTax1 = salesTaxService.addSalesTax(salesTax, newCompany.getCompanyId());
			     Outlet outlet =  new Outlet();
			     outlet.setActiveIndicator(true);
			     outlet.setIsHeadOffice(true);
			     outlet.setTimeZone(timeZone);
			     outlet.setCompany(newCompany);
			     outlet.setCreatedBy(currentUser.getUserId());
			     outlet.setCreatedDate(new Date());
			     outlet.setLastUpdated(new Date());
			     outlet.setUpdatedBy(currentUser.getUserId());
			     outlet.setOutletName("Main Outlet");
			     outlet.setSalesTax(SalesTax1);
			     Outlet newOutlet =  outletService.addOutlet(outlet, newCompany.getCompanyId());
			     User newUser = new User();
			     Role role =  new Role();
			     role.setRoleId(1);
			     newUser.setRole(role);
			     newUser.setPassword(companyBean.getPassword());
			     newUser.setFirstName(companyBean.getFirstName());
			     newUser.setLastName(companyBean.getLastName());
			     newUser.setLastLogin(new Date());
			     newUser.setActiveIndicator(true);
			     newUser.setCreatedDate(new Date());
			     newUser.setLastUpdated(new Date());
			     newUser.setCreatedBy(currentUser.getUserId());
			     newUser.setUpdatedBy(currentUser.getUserId());
			     newUser.setCompany(newCompany);
			     newUser.setOutlet(newOutlet);
			     newUser.setUserEmail(companyBean.getEmail());
			     
			     
			     boolean usersList  = resourceService.IsUserExist(companyBean.getEmail(), companyBean.getPassword(), company.getCompanyId());
			 	    
			 	        if(usersList!=false) {
			 	        	
			 	        	util.AuditTrail(request, currentUser, "CompanyController.addNewCompany", "User "+ 
			 	        	       currentUser.getUserEmail()+" User with same Email is already exist. : "+companyBean.getEmail(),false);
			 	        	     return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			 	        }
			 	        
			     User user = resourceService.addUser(newUser,newCompany.getCompanyId());
			     newCompany.setCreatedBy(user.getUserId());
			     newCompany.setUpdatedBy(user.getUserId());
			     companyService.updateCompany(newCompany);
			     SalesTax1.setCreatedBy(user.getUserId());
			     SalesTax1.setUpdatedBy(user.getUserId());
			     salesTaxService.updateSalesTax(SalesTax1, newCompany.getCompanyId());
			     newOutlet.setCreatedBy(user.getUserId());
			     newOutlet.setUpdatedBy(user.getUserId());
			     outletService.updateOutlet(newOutlet, newCompany.getCompanyId());
			     Address newAddress = new Address();
			     newAddress.setAddressType("Physcial Address");
			     newAddress.setContactName("");
			     newAddress.setFirstName(companyBean.getFirstName());
			     newAddress.setLastName(companyBean.getLastName());
			     newAddress.setEmail(newUser.getUserEmail());
			     newAddress.setPhone(companyBean.getPhone());
			     newAddress.setFax("");
			     newAddress.setWebsite("");
			     newAddress.setTwitter("");
			     newAddress.setStreet("");
			     newAddress.setSuburb("");
			     newAddress.setCity("");
			     newAddress.setPostalCode("");
			     newAddress.setState("");
			     newAddress.setCounty("");
			     newAddress.setXCoordinate("");
			     newAddress.setYCoordinate("");
			     newAddress.setActiveIndicator(true);
			     newAddress.setCreatedDate(new Date());
			     newAddress.setLastUpdated(new Date());
			     /*newAddress.setCreatedBy(newUser.getUserId());
			     newAddress.setUpdatedBy(newUser.getUserId());
			     newAddress.setContact(newUser.getContact());
			     newAddress.setCountry( new Country());*/
			     newAddress.setCompany(newCompany);
			     Address addres = addressService.addAddress(newAddress, newCompany.getCompanyId());
			     UserOutlets userOutlets = new UserOutlets();
			     userOutlets.setCompany(newCompany);
			     userOutlets.setUserByUserAssociationId(newUser);
			     userOutlets.setOutlet(newOutlet);
			     userOutlets.setAddressAssocicationId(newAddress.getAddressId());
			     TimeZone timeZone1 = timeZoneService.getTimeZoneByTimeZoneId(Integer.valueOf(companyBean.getTimeZoneID()));
			     userOutlets.setTimeZoneAssocicationId(timeZone1.getTimeZoneId());
			     userOutlets.setActiveIndicator(true);
			     userOutlets.setCreatedDate(new Date());
			     userOutlets.setLastUpdated(new Date());
			     userOutlets.setUserByCreatedBy(newUser);
			     userOutlets.setUserByUpdatedBy(newUser);
			     UserOutlets newusUserOutlets = userOutletService.addUserOutlets(userOutlets);
			     ContactGroup contactGroup = new ContactGroup();
			     contactGroup.setContactGroupName("All Contact");
			     Country country = new Country();
			     country.setCountryId(1);
			     contactGroup.setCountry(country);
			     contactGroup.setActiveIndicator(true);
			     contactGroup.setCreatedDate(new Date());
			     contactGroup.setLastUpdated(new Date());
			     contactGroup.setCreatedBy(newUser.getUserId());
			     contactGroup.setUpdatedBy(newUser.getUserId());
			     contactGroup.setCompany(newCompany);
			     ContactGroup newContactGroup = contactGroupService.addContactGroup(contactGroup,newCompany.getCompanyId());
			     if(outlet!=null){
			      Register register = new Register();
			      register.setActiveIndicator(true);
			      register.setRegisterName("Main Register");
			      register.setCacheManagementEnable("No");
			      register.setCompany(company);
			      register.setCreatedBy(newUser.getUserId());
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
			      register.setUpdatedBy(newUser.getUserId());
			      register.setCompany(newCompany);
			      Register newRegistr = registerService.addRegister(register,newUser.getCompany().getCompanyId());
			      Contact contact = new Contact();
			      contact.setContactName("Admin");
			      contact.setCompanyName(newCompany.getCompanyName());
			      contact.setActiveIndicator(true);
			      contact.setCreatedDate(new Date());
			      contact.setLastUpdated(new Date());
			      contact.setContactOutletId(outlet.getOutletId());
			      contact.setCreatedBy(newUser.getUserId());
			      contact.setUpdatedBy(newUser.getUserId());
			      contact.setCompany(newCompany);
			      contact.setContactType(contactGroup.getContactGroupName());
			      contact.setOutlet(newOutlet);
			      Contact newContact = contactService.addContact(contact, newCompany.getCompanyId());
			      Brand brand = new Brand();
			      brand.setBrandName("Mini Minors");
			      brand.setBrandDescription("");
			      brand.setActiveIndicator(true);
			      brand.setCreatedDate(new Date());
			      brand.setLastUpdated(new Date());
			      brand.setCreatedBy(newUser.getUserId());
			      brand.setUpdatedBy(newUser.getUserId());
			      brand.setCompany(newCompany);
			      Brand newBrand = brandService.addBrand(brand, newCompany.getCompanyId());
			      
			      Map<String ,Configuration> configurationMap =   configurationService.getAllConfigurationsByCompanyId(1);
			      List<Configuration> configuationList = new ArrayList<>();
			      
			      for(Map.Entry<String, Configuration> entry : configurationMap.entrySet())
			      {
			    	  String key = entry.getKey();
			    	  Configuration configuration_kites = entry.getValue();
			    	  Configuration configuration = new Configuration();
					      configuration.setPropertyName(configuration_kites.getPropertyName());
					      if (configuration.getPropertyName().equals("SUB_DOMAIN_NAME")) {
				    		  	configuration.setPropertyValue(companyBean.getDomainName());
						}else {
							configuration.setPropertyValue(configuration_kites.getPropertyValue());
						}
				    	 // configuration.setPropertyValue(configuration_kites.getPropertyValue());
				    	  configuration.setCompany(newCompany);
				    	  configuration.setActiveIndicator(true);
				    	  configuration.setUserByCreatedBy(newUser);
				    	  configuration.setUserByUpdatedBy(newUser);
				    	  configuration.setCreatedDate(new Date());
				    	  configuration.setLastUpdated(new Date());
				    	  configuationList.add(configuration);
					
			    	 
			    	  //Configuration configuration2 = configurationService.addConfiguration(configuration);
			    	  }
			      configurationService.addConfigurationList(configuationList);
			      List<Menu> menuList = new ArrayList<>();
			      List<Menu> menusList = menuService.getAllMenuByCompanyId(1);
			      if(menusList!=null && menusList.size()>0){
			    	  for (int i=0;i<menusList.size();i++) {
			    		  Menu newMenu = new Menu();
			    		  newMenu.setMenuName(menusList.get(i).getMenuName());
			    		  newMenu.setRole(menusList.get(i).getRole());
			    		  newMenu.setActionType(menusList.get(i).getActionType());
			    		  newMenu.setActiveIndicator(menusList.get(i).isActiveIndicator());
			    		  newMenu.setCreatedDate(new Date());
			    		  newMenu.setLastUpdated(new Date());
			    		  newMenu.setCreatedBy(newUser.getUserId());
			    		  newMenu.setUpdatedBy(newUser.getUserId());
			    		  newMenu.setCompany(newCompany);
			    		  menuList.add(newMenu);
			    		  
			    	  }
			    	  menuService.addMenuList(menuList);
			      }
			     
			     
			      
			   
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
	    e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			return new Response(MessageConstants.SYSTEM_BUSY,
					StatusConstants.RECORD_NOT_FOUND,
					LayOutPageConstants.STAY_ON_PAGE);

		}

	}

}

