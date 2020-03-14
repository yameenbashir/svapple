package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.dowhile.Contact;
import com.dowhile.User;
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
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * Zafar Shakeel
 */
@Controller
@RequestMapping("/manageCustomer")
public class ManageCustomerController {

	// private static Logger logger = Logger.getLogger(ManageCustomerController.class.getName());
	@Resource
	private CompanyService companyService;
	
	@Resource
	private ContactService customerService;
	
	@Resource
	private ContactGroupService customerGroupService;
	
	@Resource
	private AddressService addressService;
	
	@Resource
	private ServiceUtil util;

	@RequestMapping("/layout")
	public String getManageCustomerControllerPartialPage(ModelMap modelMap) {
		return "manageCustomer/layout";
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/updateCustomer/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response updateCustomer(@PathVariable("sessionId") String sessionId,
			@RequestBody CustomerBean customerBean, HttpServletRequest request) {
		
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Contact customer = customerService.getContactByID(Integer.parseInt(customerBean.getCustomerId()), currentUser.getCompany().getCompanyId());
				if(customerBean != null && customer!=null){
					customer.setContactId(Integer.parseInt(customerBean.getCustomerId()));
					customer.setCompanyName(customerBean.getCompanyName());
					customer.setContactCode(customerBean.getCustomerCode());
					customer.setContactGroup(customerGroupService.getContactGroupByContactGroupId(Integer.parseInt(customerBean.getCustomerGroupId()),currentUser.getCompany().getCompanyId()));
					if(customerBean.getCustomerDob()!=null){
						customer.setDob(dateFormat.parse(customerBean.getCustomerDob()));
					}
					if(customerBean.getGender()!=null){
						customer.setGender(customerBean.getGender());
					}
					customer.setLoyaltyEnabled("true");
					customer.setActiveIndicator(true);
					customer.setFirstName(customerBean.getFirstName());
					customer.setLastName(customerBean.getLastName());
					//customer.setCreatedBy(currentUser.getUserId());
					customer.setUpdatedBy(currentUser.getUserId());
					//customer.setCreatedDate(new Date());
					customer.setLastUpdated(new Date());	
					customerService.updateContact(customer,currentUser.getCompany().getCompanyId());
					List<AddressBean> addressBeanList = customerBean.getAddressBeanList(); 
					if(addressBeanList.isEmpty()==false){
					for (AddressBean addressBean : addressBeanList) {
						Address address = new Address();
						address.setAddressId(Integer.parseInt(addressBean.getAddressId()));
						address.setContact(customer);
						address.setAddressType(addressBean.getAddressType());
						address.setFirstName(customerBean.getFirstName());
						address.setLastName(customerBean.getLastName());
						address.setContactName(addressBean.getContactName());
						address.setEmail(addressBean.getEmail());
						address.setPhone(addressBean.getPhone());
						address.setWebsite(addressBean.getWebsite());
						address.setTwitter(addressBean.getTwitter());
						address.setStreet(addressBean.getStreet());
						address.setSuburb(addressBean.getSuburb());
						address.setCity(addressBean.getCity());
						address.setPostalCode(addressBean.getPostalCode());
						address.setState(addressBean.getState());
						address.setActiveIndicator(true); 						
						//address.setCreatedBy(currentUser.getUserId());
						address.setUpdatedBy(currentUser.getUserId());
						//address.setCreatedDate(new Date());
						address.setLastUpdated(new Date());	
						address.setCompany(customer.getCompany());
						addressService.updateAddress(address,currentUser.getCompany().getCompanyId());
						}
					}
				util.AuditTrail(request, currentUser, "ManageCustomerController.updateCustomer", 
							"User "+ currentUser.getUserEmail()+" Updated Customer :+"+customerBean.getAddressBeanList().get(0).getFirstName()+" successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.CUSTOMERS);
				}else{
					util.AuditTrail(request, currentUser, "ManageCustomerController.updateCustomer", "User "+ 
							currentUser.getUserEmail()+" Unable to update Customer : "+customerBean.getAddressBeanList().get(0).getFirstName(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}
				
			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManageCustomerController.updateCustomer",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
}

