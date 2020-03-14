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
import com.dowhile.Role;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.LoginBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.AddressBean;
import com.dowhile.frontend.mapping.bean.CustomerBean;
import com.dowhile.service.AddressService;
import com.dowhile.service.CompanyService;
import com.dowhile.service.ContactGroupService;
import com.dowhile.service.ContactService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.RoleService;
import com.dowhile.service.util.ServiceUtil;

/**
 * Yameen Bashir
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

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
	@Resource
	private RoleService roleService;
	@Resource
	private ResourceService resourceService;
	

	@RequestMapping("/layout")
	public String getRegisterControllerPartialPage(ModelMap modelMap) {
		return "register/layout";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/addNewCustomer/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response addNewCustomer(@PathVariable("sessionId") String sessionId,
			@RequestBody CustomerBean customerBean, HttpServletRequest request) {
			HttpSession session = request.getSession(true);
			User ecomAdminUser = resourceService.getUserByEmail("ecom_admin@shopvitals.com",0);
			ecomAdminUser.setPassword("hide");
			
			Company company = companyService.getCompanyDetailsByCompanyID(ecomAdminUser.getCompany().getCompanyId());
			ecomAdminUser.setCompany(company);
			
			
			try{
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				if(customerBean != null){
					Contact customer = new Contact();
					customer.setCompanyName(customerBean.getCompanyName());
					customer.setContactCode(customerBean.getCustomerCode());
					customer.setContactGroup(customerGroupService.getContactGroupByContactGroupId(1,ecomAdminUser.getCompany().getCompanyId()));
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
					customer.setCreatedBy(ecomAdminUser.getUserId());
					customer.setUpdatedBy(ecomAdminUser.getUserId());
					customer.setCreatedDate(new Date());
					customer.setLastUpdated(new Date());	
					customer.setCompany(ecomAdminUser.getCompany());
					customer.setContactType("CUSTOMER");
					customer = customerService.addContact(customer,ecomAdminUser.getCompany().getCompanyId());
					List<AddressBean> addressBeanList = customerBean.getAddressBeanList(); 
					if(addressBeanList.isEmpty()==false){
					for (AddressBean addressBean : addressBeanList) {
						Address address = new Address();
						address.setContact(customer);
						address.setAddressType(addressBean.getAddressType());
						address.setFirstName(addressBean.getFirstName());
						address.setLastName(addressBean.getLastName());
						address.setContactName(addressBean.getContactName());
						address.setEmail(customerBean.getEmail());
						address.setPhone(addressBean.getPhone());
						address.setWebsite(addressBean.getWebsite());
						address.setTwitter(addressBean.getTwitter());
						address.setStreet(addressBean.getStreet());
						address.setSuburb(addressBean.getSuburb());
						address.setCity(addressBean.getCity());
						address.setPostalCode(addressBean.getPostalCode());
						address.setState(addressBean.getState());
						address.setActiveIndicator(true); 						
						address.setCreatedBy(ecomAdminUser.getUserId());
						address.setUpdatedBy(ecomAdminUser.getUserId());
						address.setCreatedDate(new Date());
						address.setLastUpdated(new Date());	
						address.setCompany(ecomAdminUser.getCompany());
						addressService.addAddress(address,ecomAdminUser.getCompany().getCompanyId());
						}
					}
					
					User user = new User();
					user.setActiveIndicator(true);
					user.setCreatedBy(ecomAdminUser.getUserId());
					user.setCreatedDate(new Date());
					user.setFirstName(customerBean.getFirstName());
					user.setLastName(customerBean.getLastName());
					user.setLastUpdated(new Date());
					//user.setOutlet(outlet);
					user.setPassword(customerBean.getPassword());
					Role role= roleService.getRoleByRoleId(5);
					user.setRole(role);
					user.setUpdatedBy(ecomAdminUser.getUserId());
					user.setUserEmail(customerBean.getEmail());
					user.setLastLogin(new Date());
					user.setCompany(ecomAdminUser.getCompany());
					user.setContact(customer);
					User currentUser = resourceService.addUser(user,ecomAdminUser.getCompany().getCompanyId());
					currentUser.setCompany(company);
					session.setAttribute("user", currentUser);
					LoginBean loginBean = new LoginBean();
					loginBean.setUserName(user.getFirstName() + " "
							+ user.getLastName());
					loginBean.setSessionId(session.getId());
					loginBean.setUserRole(String.valueOf(currentUser.getRole().getRoleId()));
					loginBean.setUserId(user.getUserId().toString());
					

					/*util.AuditTrail(request, user, "LoginController.doLogin", "User:  "+ currentUser.getUserEmail()+" Login Successfuly ",false);
					customerBean.setCustomerId(customer.getContactId().toString());*/
				util.AuditTrail(request, currentUser, "RegisterController.addNewCustomer", 
							"Ecom admin "+ currentUser.getUserEmail()+" Added Customer :+"+customerBean.getAddressBeanList().get(0).getFirstName()+" successfully ",false);
					return new Response(customerBean,StatusConstants.SUCCESS,LayOutPageConstants.HOME);
				}else{
					util.AuditTrail(request, ecomAdminUser, "RegisterController.addNewCustomer", "Ecom admin "+ 
							ecomAdminUser.getUserEmail()+" Unable to add Customer ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}
				
			}catch(Exception e){
				e.printStackTrace();
//				// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, ecomAdminUser, "RegisterController.addNewCustomer",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		
	}

	

}

