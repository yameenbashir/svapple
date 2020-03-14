package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.dowhile.Contact;
import com.dowhile.ContactGroup;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.AddressBean;
import com.dowhile.frontend.mapping.bean.CustomerBean;
import com.dowhile.frontend.mapping.bean.CustomerGroupBean;
import com.dowhile.service.AddressService;
import com.dowhile.service.CompanyService;
import com.dowhile.service.ContactGroupService;
import com.dowhile.service.ContactService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/newCustomer")
public class NewCustomerController {

	
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
	public String getNewCustomerControllerPartialPage(ModelMap modelMap) {
		return "newCustomer/layout";
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/addNewCustomer/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response addNewCustomer(@PathVariable("sessionId") String sessionId,
			@RequestBody CustomerBean customerBean, HttpServletRequest request) {
		
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				if(customerBean != null){
					Contact customer = new Contact();
					customer.setCompanyName(customerBean.getCompanyName());
					customer.setContactCode(customerBean.getCustomerCode());
					if(customerBean.getCustomerGroupId()!=null){
						customer.setContactGroup(customerGroupService.getContactGroupByContactGroupId(Integer.parseInt(customerBean.getCustomerGroupId()),currentUser.getCompany().getCompanyId()));
					}
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
					customer.setCompanyName(customerBean.getCompanyName());
					customer.setCreatedBy(currentUser.getUserId());
					customer.setUpdatedBy(currentUser.getUserId());
					customer.setCreatedDate(new Date());
					customer.setLastUpdated(new Date());	
					customer.setCompany(currentUser.getCompany());
					customer.setContactType("CUSTOMER");
					customer.setOutlet(currentUser.getOutlet());
					customer = customerService.addContact(customer,currentUser.getCompany().getCompanyId());
					List<AddressBean> addressBeanList = customerBean.getAddressBeanList(); 
					if(addressBeanList.isEmpty()==false){
					for (AddressBean addressBean : addressBeanList) {
						Address address = new Address();
						address.setContact(customer);
						address.setAddressType(addressBean.getAddressType());
						address.setFirstName(addressBean.getFirstName());
						address.setLastName(addressBean.getLastName());
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
						address.setCreatedBy(currentUser.getUserId());
						address.setUpdatedBy(currentUser.getUserId());
						address.setCreatedDate(new Date());
						address.setLastUpdated(new Date());	
						address.setCompany(currentUser.getCompany());
						addressService.addAddress(address,currentUser.getCompany().getCompanyId());
						}
					}
					customerBean.setCustomerId(customer.getContactId().toString());
				util.AuditTrail(request, currentUser, "NewCustomerController.addNewCustomer", 
							"User "+ currentUser.getUserEmail()+" Added Customer :+"+customerBean.getAddressBeanList().get(0).getFirstName()+" successfully ",false);
					return new Response(customerBean,StatusConstants.SUCCESS,LayOutPageConstants.CUSTOMERS);
				}else{
					util.AuditTrail(request, currentUser, "NewCustomerController.addNewCustomer", "User "+ 
							currentUser.getUserEmail()+" Unable to add Customer from user: "+currentUser.getUserId(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}
				
			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NewCustomerController.addNewCustomer",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllCustomerGroups/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllCustomerGroups(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<CustomerGroupBean> customerGroupBeansList = new ArrayList<>();
		List<ContactGroup> customerGroupList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				customerGroupList = customerGroupService.GetAllContactGroup(currentUser.getCompany().getCompanyId());
				if (customerGroupList != null) {
					for (ContactGroup customerGroup : customerGroupList) {
						CustomerGroupBean customerGroupBean = new CustomerGroupBean();
						customerGroupBean.setCustomerGroupId(customerGroup.getContactGroupId().toString());
						customerGroupBean.setCustomerGroupName(customerGroup.getContactGroupName().toString());
						customerGroupBeansList.add(customerGroupBean);
					}
					util.AuditTrail(request, currentUser, "NewCustomerController.getAllCustomerGroups", 
							"User "+ currentUser.getUserEmail()+" retrived all Customer Groups successfully ",false);
					return new Response(customerGroupBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "NewCustomerController.getAllCustomerGroups", 
							" Customer Groups are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NewCustomerController.getAllCustomerGroups",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
}

