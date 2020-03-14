package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Address;
import com.dowhile.Contact;
import com.dowhile.ContactGroup;
import com.dowhile.ContactsSummmary;
import com.dowhile.User;
import com.dowhile.constants.HomeControllerBean;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.AddressBean;
import com.dowhile.frontend.mapping.bean.CustomerBean;
import com.dowhile.service.AddressService;
import com.dowhile.service.CompanyService;
import com.dowhile.service.ContactGroupService;
import com.dowhile.service.ContactService;
import com.dowhile.service.ContactsSummmaryService;
import com.dowhile.service.SaleService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.ControllerUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/customers")
public class CustomersController {

	// private static Logger logger = Logger.getLogger(CustomersController.class.getName());
	@Resource
	private CompanyService companyService;

	@Resource
	private ContactService contactService;

	@Resource
	private ContactGroupService customerGroupService;

	@Resource
	private AddressService addressService;

	@Resource
	private ServiceUtil util;

	@Resource
	private ContactsSummmaryService contactsSummmaryService;


	@Resource
	private SaleService saleService;
	
	
	@RequestMapping("/layout")
	public String getCustomersControllerPartialPage(ModelMap modelMap) {
		return "customers/layout";
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllCustomers/{sessionId}/{loadAllCustomers}", method = RequestMethod.POST)
	public @ResponseBody
	Response getAllCustomers(@PathVariable("sessionId") String sessionId,@PathVariable("loadAllCustomers") String loadAllCustomers,
			HttpServletRequest request) {
		List<CustomerBean> customerBeansList = new ArrayList<>();
		List<ContactsSummmary> customerList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				if(loadAllCustomers.equalsIgnoreCase("true")){
					customerList= contactsSummmaryService.getAllContactsSummmaryByCompanyIdOutletIdConctactType(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId(),"CUSTOMER");
				}else{
					customerList= contactsSummmaryService.getTenNewContactsSummmaryByCompanyIdOutletIdConctactType(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId(),"CUSTOMER");
				}
				if (customerList != null) {
					
					for (ContactsSummmary customer : customerList) {
						if(customer.getId().getContactType()!=null && customer.getId().getContactType().contains("CUSTOMER")){
						CustomerBean customerBean = new CustomerBean();
						
						customerBean.setPhoneNumber(customer.getId().getPhone());
						customerBean.setCustomerId(String.valueOf(customer.getId().getContactId()));
						customerBean.setContactName(customer.getId().getContactName());
						customerBean.setCustomerGroupName(customer.getId().getContactGroup());
						customerBean.setCompanyName(customer.getId().getCompanyName());
						customerBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(customer.getId().getCreatedDate()));
						customerBean.setActiveIndicator(String.valueOf(customer.getId().isActiveIndicator()));
						if(customer.getId().getBalance() != null){
							customerBean.setCustomerBalance((new BigDecimal(customer.getId().getBalance().doubleValue()).setScale(2,RoundingMode.HALF_EVEN)).toString());
						}
						else
						{
							customerBean.setCustomerBalance("0.00");
						}
						
						customerBeansList.add(customerBean);
					}}
					util.AuditTrail(request, currentUser, "CustomersController.getAllCustomers", 
							"User "+ currentUser.getUserEmail()+" retrived all Customers successfully ",false);
					return new Response(customerBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "CustomersController.getAllCustomers", 
							" Customers are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "CustomersController.getAllCustomers",
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
	@RequestMapping(value = "/updateSelectedCustomer/{sessionId}/{customerId}", method = RequestMethod.GET)
	public @ResponseBody
	Response updateSelectedCustomer(@PathVariable("sessionId") String sessionId,@PathVariable("customerId") int customerId,
			HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				Contact customer= contactService.getContactByID(customerId, currentUser.getCompany().getCompanyId());
				if(customer.isActiveIndicator()){
					customer.setActiveIndicator(false);
				}else{
					customer.setActiveIndicator(true);
				}
				contactService.updateContact(customer, currentUser.getCompany().getCompanyId())	;
					util.AuditTrail(request, currentUser, "CustomersController.updateSelectedCustomer", 
							"User "+ currentUser.getUserEmail()+" updated  Customer successfully ",false);
					return new Response(StatusConstants.SUCCESS, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "CustomersController.updateSelectedCustomer",
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
	@RequestMapping(value = "/activeInactiveAllCustomers/{sessionId}/{customerId}", method = RequestMethod.GET)
	public @ResponseBody
	Response activeInactiveAllCustomers(@PathVariable("sessionId") String sessionId,@PathVariable("customerId") int customerId,
			HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				
				Contact customer= contactService.getContactByID(customerId, currentUser.getCompany().getCompanyId());
				if(customer.isActiveIndicator()){
					customer.setActiveIndicator(false);
				}else{
					customer.setActiveIndicator(true);
				}
				contactService.updateContact(customer, currentUser.getCompany().getCompanyId())	;
					util.AuditTrail(request, currentUser, "CustomersController.activeInactiveAllCustomers", 
							"User "+ currentUser.getUserEmail()+" updated  Customer successfully ",false);
					return new Response(StatusConstants.SUCCESS, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "CustomersController.activeInactiveAllCustomers",
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
	@RequestMapping(value = "/getSelectedCustomer/{sessionId}/{customerId}", method = RequestMethod.GET)
	public @ResponseBody
	Response getSelectedCustomer(@PathVariable("sessionId") String sessionId,@PathVariable("customerId") int customerId,
			HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
//				Map<String, Address> cutomerAddressMap = addressService.getALLAddressByCustomerId(currentUser.getCompany().getCompanyId());
				
				Contact customer= contactService.getContactByID(customerId, currentUser.getCompany().getCompanyId());
				
						if(customer.getContactType()!=null && customer.getContactType().contains("CUSTOMER")){
						CustomerBean customerBean = new CustomerBean();
						List<Address> addressList = addressService.getAddressByCustomerId(customer.getContactId(),currentUser.getCompany().getCompanyId());					
						List<AddressBean> addressBeansList = new ArrayList<>();
						if(addressList!=null){
							for(Address address : addressList){
								AddressBean addressBean = new AddressBean();
								addressBean.setAddressId(address.getAddressId().toString());
								addressBean.setFirstName(address.getFirstName());
								addressBean.setLastName(address.getLastName());
								addressBean.setContactName(address.getContactName());
								addressBean.setEmail(address.getEmail());
								addressBean.setPhone(address.getPhone());
								addressBean.setWebsite(address.getWebsite());
								addressBean.setTwitter(address.getTwitter());
								addressBean.setStreet(address.getStreet());
								addressBean.setSuburb(address.getSuburb());
								addressBean.setCity(address.getCity());
								addressBean.setPostalCode(address.getPostalCode());
								addressBean.setState(address.getState());
								addressBeansList.add(addressBean);
							}
						}
						customerBean.setAddressBeanList(addressBeansList);
						customerBean.setCustomerId(customer.getContactId().toString());
						customerBean.setCustomerCode(customer.getContactCode());
						customerBean.setFirstName(customer.getFirstName());
						customerBean.setLastName(customer.getLastName());
						customerBean.setCompanyName(customer.getCompanyName());
						if(customer.getContactBalance() != null){
							customerBean.setCustomerBalance((new BigDecimal(Objects.toString(customer.getContactBalance(),"0.00")).setScale(2,RoundingMode.HALF_EVEN)).toString());
						}
						else
						{
							customerBean.setCustomerBalance("0.00");
						}
						if(customer.getDob()!=null){
							customerBean.setCustomerDob(customer.getDob().toString());

						}
						if(customer.getGender()!=null){
							customerBean.setGender(customer.getGender().toString());
						}
						
						if(customer.getContactGroup()!=null){	
							ContactGroup customerGroup = customerGroupService.getContactGroupByContactGroupId(customer.getContactGroup().getContactGroupId(),currentUser.getCompany().getCompanyId());
							customerBean.setCustomerGroupId(customerGroup.getContactGroupId().toString());
							customerBean.setCustomerGroupName(customerGroup.getContactGroupName());
						}			
//						Address customerAddress = cutomerAddressMap.get(customer.getContactId().toString());
						customerBean.setPhoneNumber("");
						if(addressList!=null && addressList!=null){
							if(addressBeansList.get(0).getPhone()!=null){
								AddressBean  addressBean = addressBeansList.get(0);
								addressBean.setPhone(addressBeansList.get(0).getPhone());
								addressBeansList.set(0, addressBean);
								
							}
						}
						customerBean.setCustomerCode(customer.getContactCode());
						
					util.AuditTrail(request, currentUser, "CustomersController.getSelectedCustomer", 
							"User "+ currentUser.getUserEmail()+" retrived  Customer successfully ",false);
					return new Response(customerBean, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "CustomersController.getSelectedCustomer", 
							" Customers are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "CustomersController.getSelectedCustomer",
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
	@RequestMapping(value = "/getAllCustomersOrderBYPhone/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getAllCustomersOrderBYPhone(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {
		List<CustomerBean> customerBeansList = new ArrayList<>();
		List<ContactsSummmary> customerList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				customerList = contactsSummmaryService.getActiveContactsSummmaryByCompanyId(currentUser.getCompany().getCompanyId());
				if (customerList != null) {
					
						for (ContactsSummmary customer : customerList) {
							if(customer.getId().getContactType()!=null && customer.getId().getContactType().contains("CUSTOMER")){
								CustomerBean customerBean = new CustomerBean();
								customerBean.setCustomerId(String.valueOf(customer.getId().getContactId()));
								customerBean.setFirstName(customer.getId().getFirstName());
								customerBean.setPhoneNumber(customer.getId().getPhone());
								customerBean.setLastName(customer.getId().getLastName());
								customerBean.setCustomerBalance(customer.getId().getBalance().toString());
								customerBean.setLaybyAmount(customer.getId().getBalance().toString());
								
								customerBeansList.add(customerBean);
							}
						}
					
					
					util.AuditTrail(request, currentUser, "CustomersController.getAllCustomersOrderBYPhone", 
							"User "+ currentUser.getUserEmail()+" retrived all Customers successfully ",false);
					return new Response(customerBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "CustomersController.getAllCustomersOrderBYPhone", 
							" Customers are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "CustomersController.getAllCustomersOrderBYPhone",
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
	@RequestMapping(value = "/getCustomerDashBoard/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getDashBoard(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {
		HomeControllerBean controllerBean = new HomeControllerBean();
		List graphData = null;
		Date startDate = null , endDate = null;
		Calendar calendar = Calendar.getInstance();
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			controllerBean.setOutletName(currentUser.getOutlet().getOutletName());

			try {

				calendar.setTime(new Date());
				calendar.add(Calendar.DAY_OF_MONTH, -6);
				startDate = calendar.getTime();
				endDate = new Date();

				Calendar customerCountCalendar = (Calendar) calendar.clone();

				graphData = saleService.getCustomersCount(currentUser
						.getOutlet().getOutletId(), currentUser.getCompany()
						.getCompanyId(), startDate, endDate);
				ControllerUtil.getDefaultData(graphData, customerCountCalendar,
						controllerBean, currentUser, request, "customerCount");

				//
				util.AuditTrail(request, currentUser,
						"CustomersController.getCustomerDashBoard",
						"User " + currentUser.getUserEmail()
								+ " dashboard loaded successfully ", false);
				return new Response(controllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);

			}
			catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "CustomersController.getCustomerDashBoard",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
}

