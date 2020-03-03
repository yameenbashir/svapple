package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Contact;
import com.dowhile.ContactPayments;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.CustomerDetailControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.CustomerPaymentsBean;
import com.dowhile.service.AddressService;
import com.dowhile.service.CompanyService;
import com.dowhile.service.ContactGroupService;
import com.dowhile.service.ContactPaymentsService;
import com.dowhile.service.ContactService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/customerDetails")
public class CustomerDetailsController {

	@Resource
	private CompanyService companyService;
	
	@Resource
	private ContactService contactService;
	
	@Resource
	private ContactPaymentsService contactPaymentsService;
	
	@Resource
	private ContactGroupService customerGroupService;
	
	@Resource
	private AddressService addressService;
	
	@Resource
	private ResourceService resourceService;
	
	
	@Resource
	private ServiceUtil util;

	@RequestMapping("/layout")
	public String getCustomerDetailsControllerPartialPage(ModelMap modelMap) {
		return "customerDetails/layout";
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/loadCustomerDetails/{sessionId}/{customerId}", method = RequestMethod.POST)
	public @ResponseBody Response loadCustomerDetails(@PathVariable("sessionId") String sessionId,@PathVariable("customerId")
			String customerId,
			HttpServletRequest request) {

		CustomerDetailControllerBean customerDetailControllerBean = new CustomerDetailControllerBean();
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			List<CustomerPaymentsBean> customerPaymentsBeans = new ArrayList<>();
			try {
				Contact contact = contactService.getContactByID(Integer.parseInt(customerId), currentUser.getCompany().getCompanyId());
				if(contact.getContactBalance()!=null){
					 DecimalFormat twoDForm = new DecimalFormat("#.##");
					 
					customerDetailControllerBean.setBalance(twoDForm.format(contact.getContactBalance()));
				}else{
					customerDetailControllerBean.setBalance("0");
				}
				if(contact.getLastName()!=null){
					customerDetailControllerBean.setContactName(contact.getFirstName()+" "+ contact.getLastName());
					
				}else{
					customerDetailControllerBean.setContactName(contact.getFirstName());
					
				}
				if(contact.getContactGroup()!=null){
					customerDetailControllerBean.setCustomerGroup(customerGroupService.getContactGroupByContactGroupId(contact.getContactGroup().getContactGroupId(),
							currentUser.getCompany().getCompanyId()).getContactGroupName());
				
				}else{
					customerDetailControllerBean.setCustomerGroup("-");
				}
				customerDetailControllerBean.setStoreCredit("0");
				customerDetailControllerBean.setTotalEarned("0");
				List<ContactPayments>  contactPayments = contactPaymentsService.getCustonerPaymentsByContactId(contact.getContactId(), currentUser.getCompany().getCompanyId());
				if(contactPayments!=null){
					double totalSpent = 0.0;
					for(ContactPayments payments : contactPayments){
						CustomerPaymentsBean customerPaymentsBean = new CustomerPaymentsBean();
						customerPaymentsBean.setDate(DateTimeUtil.convertDBDateTimeToGuiFormat(payments.getCreatedDate()));
						customerPaymentsBean.setNote(payments.getDescription());
						customerPaymentsBean.setReceipt(payments.getPaymentRefNum());
						//customerPaymentsBean.setRegister(payments.get);
						//customerPaymentsBean.setStatus(status);
						if(payments.getPaymentCash()!=null){
							customerPaymentsBean.setTotal(payments.getPaymentCash().toString());
							totalSpent = totalSpent + payments.getPaymentCash().doubleValue();
						}else{
							customerPaymentsBean.setTotal("0");
							totalSpent = totalSpent + 0;
						}
						
						
						customerPaymentsBean.setUser(resourceService.getUserById(payments.getCreatedBy(), currentUser.getCompany().getCompanyId()).getUserEmail());
						customerPaymentsBeans.add(customerPaymentsBean);
					
					}
					customerDetailControllerBean.setTotalSpent(String.valueOf(totalSpent));
				}
				customerDetailControllerBean.setCustomerPaymentsBeans(customerPaymentsBeans);

					util.AuditTrail(request, currentUser, "CustomerDetailsController.loadCustomerDetails", 
							"Customer id  "+ customerId+" retrived all Customer Details ",false);
					return new Response(customerDetailControllerBean, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "CustomerDetailsController.loadCustomerDetails",
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

