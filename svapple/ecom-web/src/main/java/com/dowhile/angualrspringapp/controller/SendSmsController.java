package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
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
import com.dowhile.Configuration;
import com.dowhile.Contact;
import com.dowhile.ContactsSummmary;
import com.dowhile.Message;
import com.dowhile.MessageDetail;
import com.dowhile.User;
import com.dowhile.angualrspringapp.beans.SendSMSBean;
import com.dowhile.angualrspringapp.beans.SendSingleSmsBean;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.controller.bean.SendSMSControllerBean;
import com.dowhile.frontend.mapping.bean.CustomerBean;
import com.dowhile.frontend.mapping.bean.customerSmsBean;
import com.dowhile.service.AddressService;
import com.dowhile.service.CompanyService;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.ContactService;
import com.dowhile.service.ContactsSummmaryService;
import com.dowhile.service.MessageDetailService;
import com.dowhile.service.MessageService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SendSMS;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/sendSms")
public class SendSmsController {

	@Resource
	private ServiceUtil util;

	@Resource
	private CompanyService companyService;

	@Resource
	private ContactService customerService;
	
	@Resource
	private ContactsSummmaryService contactsSummmaryService;
	
	@Resource
	private AddressService addressService;

	@Resource
	private ResourceService resourceService;

	@Resource
	private MessageService messageService;

	@Resource
	private MessageDetailService messageDetailService;

	
	@RequestMapping("/layout")
	public String getSellControllerPartialPage(ModelMap modelMap) {
		return "sell/layout";
	}

	@Resource
	private ConfigurationService configurationService;

	@RequestMapping(value = "/getAllCustomers/{sessionId}", method = RequestMethod.GET)
	public @ResponseBody
	SendSMSControllerBean getAllCustomers(
			@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {
		

		SendSMSControllerBean sellControllerBean = new SendSMSControllerBean();
		List<CustomerBean> customersBeans = new ArrayList<>();
		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				Message messageObj = messageService.getMessageByCompanyId(currentUser.getCompany().getCompanyId().toString());
				if(messageObj.getPackageRenewDate().before(new Date())){
					sellControllerBean.setIsExpired("true");
				}else{
					sellControllerBean.setIsExpired("false");
				}
				sellControllerBean.setSmsRemained(String.valueOf(messageObj.getMessageBundleCount()-messageObj.getMessageTextLimit()));
				sellControllerBean.setSmsUsedCount(String.valueOf(messageObj.getMessageTextLimit()));
				List<ContactsSummmary> contacts = contactsSummmaryService.getActiveContactsSummmaryByCompanyOutletId(currentUser.getCompany().getCompanyId(),currentUser.getOutlet().getOutletId());;
				if (contacts != null) {
					for (ContactsSummmary customer : contacts) {
						if(customer.getId().getContactType()!=null && customer.getId().getContactType().contains("CUSTOMER")){
							CustomerBean customerBean = new CustomerBean();
							customerBean.setCustomerId(String.valueOf(customer.getId().getContactId()) );
							customerBean.setFirstName(customer.getId().getFirstName());
							customerBean.setPhoneNumber(customer.getId().getPhone());
									customerBean.setLastName(customer.getId().getLastName());
							customersBeans.add(customerBean);
						}
					}
					sellControllerBean.setCustomersBean(customersBeans);
				}
			
				return sellControllerBean;
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser,
						"SellController.getAllProducts", "Error Occured "
								+ errors.toString(), true);
				return sellControllerBean;

			}
		}

		else {

			return sellControllerBean;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/sendMessage/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response sendMessage(@PathVariable("sessionId") String sessionId,
			@RequestBody SendSMSBean sendSMSBean,
			HttpServletRequest request) {
		SendSMS sendSMS = new SendSMS();
		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
//			Message messageObj = messageService.getMessageByMaskName(sendSMSBean.getUser());
			List<MessageDetail> messageDetails = new ArrayList<>();
//			Configuration smsconfiguration = configurationService.getConfigurationByPropertyNameByCompanyId("SMS_ENABLED",currentUser.getCompany().getCompanyId());
			Message messageObj = messageService.getMessageByCompanyId(currentUser.getCompany().getCompanyId().toString());
			int smsCount = 0;
			try {
				
			    smsCount= messageObj.getMessageTextLimit();		
				
				if(sendSMSBean.getSendAllSms()!=null && sendSMSBean.getSendAllSms().equals("true")){
					List<ContactsSummmary> contacts = contactsSummmaryService.getActiveContactsSummmaryByCompanyOutletId(currentUser.getCompany().getCompanyId(),currentUser.getOutlet().getOutletId());;
					
					if(messageObj!=null && contacts!=null && messageObj.getMessageBundleCount()>=messageObj.getMessageTextLimit()+contacts.size() && new Date().before(messageObj.getPackageRenewDate())){
						String phoneNum = "";	
						String receiverId = "";
						for(ContactsSummmary bean:contacts){
							
							if(bean.getId().getContactType().equals("CUSTOMER")){
								if( bean.getId().getPhone()!=null && ! bean.getId().getPhone().equals("")){
									  phoneNum = phoneNum + bean.getId().getPhone() +",";
								}
								receiverId = receiverId + bean.getId().getContactId() +",";
								smsCount = smsCount+1;
	
							}
						}
						if(phoneNum.substring(phoneNum.length()-1, phoneNum.length()).equals(",")){
							phoneNum = phoneNum.substring(0, phoneNum.length()-1);
						}
				
						String deliverId = sendSMS.sendSMSPost(request,currentUser,phoneNum, sendSMSBean.getMessage(), messageObj.getUserId(), messageObj.getPassword(), messageObj.getMaskName());
						
						MessageDetail messageDetail = new MessageDetail();
						messageDetail.setCompany(currentUser.getCompany());
						messageDetail.setActiveIndicator(true);
						messageDetail.setCreatedBy(currentUser.getUserId());
						messageDetail.setCreatedDate(new Date());
						messageDetail.setDeliveryId(deliverId.getBytes());
						messageDetail.setDeliveryStatus("SUCCESS");
						messageDetail.setLastUpdated(new Date());
						messageDetail.setMessageDescription(sendSMSBean.getMessage());
						messageDetail.setOutlet(currentUser.getOutlet());
						messageDetail.setMessage(messageObj);
						messageDetail.setReceiverId("ALL");
						messageDetail.setSenderId(String.valueOf(currentUser.getUserId()));
						messageDetail.setUpdatedBy(currentUser.getUserId());
						messageDetails.add(messageDetail);
						messageDetailService.addMessageDetailList(messageDetails, currentUser.getCompany());
				}else{
					util.AuditTrail(request, currentUser, "sendSms.sendMessage","SMS Package Expired ", true);
					return new Response(MessageConstants.SMS_EXPIRED, StatusConstants.ACCESS_DENIED,LayOutPageConstants.SEND_SMS);
					}
					
				}else{
					if(messageObj!=null && sendSMSBean.getCustomerSmsBeans()!=null && messageObj.getMessageBundleCount()>=messageObj.getMessageTextLimit()+sendSMSBean.getCustomerSmsBeans().size() && new Date().before(messageObj.getPackageRenewDate())){
					String phoneNum = "";	
					String receiverId = "";	
					for(customerSmsBean bean:sendSMSBean.getCustomerSmsBeans()){
						if( bean.getPhoneNumber()!=null && ! bean.equals("")){
							  phoneNum = phoneNum + bean.getPhoneNumber() +",";
						}
						receiverId = receiverId + bean.getCustomerId() +",";
						smsCount = smsCount+1;
					}
					if(phoneNum.substring(phoneNum.length()-1, phoneNum.length()).equals(",")){
						phoneNum = phoneNum.substring(0, phoneNum.length()-1);
					}
					String deliverId = sendSMS.sendSMSPost(request,currentUser,phoneNum, sendSMSBean.getMessage(), messageObj.getUserId(), messageObj.getPassword(), messageObj.getMaskName());
					
					MessageDetail messageDetail = new MessageDetail();
					messageDetail.setCompany(currentUser.getCompany());
					messageDetail.setActiveIndicator(true);
					messageDetail.setCreatedBy(currentUser.getUserId());
					messageDetail.setCreatedDate(new Date());
					messageDetail.setDeliveryId(deliverId.getBytes());
					messageDetail.setDeliveryStatus("SUCCESS");
					messageDetail.setLastUpdated(new Date());
					messageDetail.setMessageDescription(sendSMSBean.getMessage());
					messageDetail.setOutlet(currentUser.getOutlet());
					messageDetail.setMessage(messageObj);
					messageDetail.setReceiverId("ALL");
					messageDetail.setSenderId(String.valueOf(currentUser.getUserId()));
					messageDetail.setUpdatedBy(currentUser.getUserId());
					messageDetails.add(messageDetail);
					messageDetailService.addMessageDetailList(messageDetails, currentUser.getCompany());
					}else{
						util.AuditTrail(request, currentUser, "sendSms.sendMessage","SMS Package Expired ", true);
						return new Response(MessageConstants.SMS_EXPIRED, StatusConstants.ACCESS_DENIED,LayOutPageConstants.SEND_SMS);
						}
				}
				messageObj.setMessageTextLimit(smsCount);
				messageService.updateMessage(messageObj);
				return new Response(MessageConstants.VALID_SESSION, StatusConstants.SUCCESS,
						LayOutPageConstants.SEND_SMS);
				
//				}else{
//					return new Response(MessageConstants.SMS_EXPIRED, StatusConstants.ACCESS_DENIED,
//							LayOutPageConstants.SEND_SMS);
//					
//				}
				
			} catch (Exception e) {
				messageObj.setMessageTextLimit(smsCount);
				messageService.updateMessage(messageObj);
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "sendSms.sendMessage","Error Occured " + errors.toString(), true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
			
		} else {
			return new Response(MessageConstants.INVALID_SESSION,
					StatusConstants.INVALID, LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/sendSingleMessage/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response sendSingleMessage(@PathVariable("sessionId") String sessionId,
			@RequestBody SendSingleSmsBean singleSmsBean,
			HttpServletRequest request) {
		SendSMS sendSMS = new SendSMS();
		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			Contact bean = customerService.getContactByID(Integer.parseInt(singleSmsBean.getCustomerId()), currentUser.getCompany().getCompanyId());
//			Message messageObj = messageService.getMessageByMaskName(singleSmsBean.getUser());
			List<MessageDetail> messageDetails = new ArrayList<>();
//			Configuration smsconfiguration = configurationService.getConfigurationByPropertyNameByCompanyId("SMS_ENABLED",currentUser.getCompany().getCompanyId());
			Message messageObj = messageService.getMessageByCompanyId(currentUser.getCompany().getCompanyId().toString());
			
			try {
				if(messageObj!=null && messageObj.getMessageBundleCount()>=messageObj.getMessageTextLimit() && new Date().before(messageObj.getPackageRenewDate())){
					Configuration configuration = configurationService.getConfigurationByPropertyNameByCompanyId("SALE_SMS",currentUser.getCompany().getCompanyId());

					Map<String, Address> cutomerAddressMap = addressService.getALLAddressByCustomerId(currentUser.getCompany().getCompanyId());
					Address customerAddress = cutomerAddressMap.get(bean.getContactId().toString());
					String phoneNum = "";
					if(customerAddress!=null ){
						if(customerAddress.getPhone()!=null){
							phoneNum = customerAddress.getPhone();
						}
					}

				
							String deliverId = sendSMS.sendSMS(request,currentUser,phoneNum, configuration.getPropertyValue()+" "+"You'r current bill is PKR "+singleSmsBean.getAmount(), messageObj.getUserId(), messageObj.getPassword(), messageObj.getMaskName());
							MessageDetail messageDetail = new MessageDetail();
							messageDetail.setCompany(currentUser.getCompany());
							messageDetail.setActiveIndicator(true);
							messageDetail.setCreatedBy(currentUser.getUserId());
							messageDetail.setCreatedDate(new Date());
							messageDetail.setDeliveryId(deliverId.getBytes());
							messageDetail.setDeliveryStatus("SUCCESS");
							messageDetail.setLastUpdated(new Date());
							messageDetail.setMessageDescription(configuration.getPropertyValue());
							messageDetail.setOutlet(currentUser.getOutlet());
							messageDetail.setMessage(messageObj);
							messageDetail.setReceiverId(singleSmsBean.getCustomerId());
							messageDetail.setSenderId(String.valueOf(currentUser.getUserId()));
							messageDetail.setUpdatedBy(currentUser.getUserId());
							messageDetails.add(messageDetail);
						
							messageDetailService.addMessageDetailList(messageDetails, currentUser.getCompany());
							int smsCount= messageObj.getMessageTextLimit()+1;		
	
							messageObj.setMessageTextLimit(smsCount);
							messageService.updateMessage(messageObj);
					
				}else{
					util.AuditTrail(request, currentUser, "sendSms.sendMessage","SMS Package Expired ", true);
				}
				
				
				return new Response(MessageConstants.VALID_SESSION, StatusConstants.SUCCESS,
						LayOutPageConstants.SEND_SMS);
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "sendSingleMessage.sendMessage","Error Occured " + errors.toString(), true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		} else {
			return new Response(MessageConstants.INVALID_SESSION,
					StatusConstants.INVALID, LayOutPageConstants.LOGIN);
		}
	}
}
