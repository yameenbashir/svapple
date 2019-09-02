package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
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

import com.dowhile.ContactGroup;
import com.dowhile.Country;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.CustomerGroupControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.CountryBean;
import com.dowhile.frontend.mapping.bean.CustomerGroupBean;
import com.dowhile.service.ContactGroupService;
import com.dowhile.service.ContactService;
import com.dowhile.service.CountryService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/customerGroup")
public class CustomerGroupController {
	
	@Resource
	private ResourceService resourceService;
	@Resource
	private ServiceUtil util;
	@Resource
	private ContactGroupService customerGroupService;
	@Resource
	private ContactService contactService;
	
	@Resource
	private CountryService countryService;

	@RequestMapping("/layout")
	public String getCustomerGroupControllerPartialPage(ModelMap modelMap) {
		return "customerGroup/layout";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getCustomerGroupControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getCustomerGroupControllerData(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<CustomerGroupBean> customerGroupBeanList = null;
		List<CountryBean> countryListBeans = null;
			
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {

				Response response = getAllCustomerGroups(sessionId,request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					customerGroupBeanList = (List<CustomerGroupBean>) response.data;
				}
				response = getAllCountries(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					countryListBeans = (List<CountryBean>) response.data;
				}
				
				
				CustomerGroupControllerBean customerGroupControllerBean = new CustomerGroupControllerBean();
				customerGroupControllerBean.setCountryListBeans(countryListBeans);
				customerGroupControllerBean.setCustomerGroupBeanList(customerGroupBeanList);

				util.AuditTrail(request, currentUser, "CustomerGroupController.getCustomerGroupControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived CustomerGroupControllerData successfully ",false);
				return new Response(customerGroupControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "CustomerGroupController.getCustomerGroupControllerData",
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
	@RequestMapping(value = "/addCustomerGroup/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response addCustomerGroup(@PathVariable("sessionId") String sessionId,
			@RequestBody CustomerGroupBean customerGroupBean, HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{

				ContactGroup customerGroup = new ContactGroup();
				customerGroup.setActiveIndicator(true);
				customerGroup.setContactGroupName(customerGroupBean.getCustomerGroupName());
				if(customerGroupBean.getCountryId()!= null){
					customerGroup.setCountry(countryService.getCountryByCountryId(Integer.parseInt(customerGroupBean.getCountryId())));
				}
				customerGroup.setCreatedBy(currentUser.getUserId());
				customerGroup.setCreatedDate(new Date());
				customerGroup.setLastUpdated(new Date());
				customerGroup.setUpdatedBy(currentUser.getUserId());
				customerGroup.setCompany(currentUser.getCompany());
				customerGroupService.addContactGroup(customerGroup,currentUser.getCompany().getCompanyId());
				

				util.AuditTrail(request, currentUser, "CustomerGroupController.addCustomerGroup", 
						"User "+ currentUser.getUserEmail()+" Added CustomerGroup +"+customerGroupBean.getCustomerGroupName()+" successfully ",false);
				return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.PRODUCTTYPE);


			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "CustomerGroupController.addCustomerGroup",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/updateCustomerGroup/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response updateCustomerGroup(@PathVariable("sessionId") String sessionId,
			@RequestBody CustomerGroupBean customerGroupBean, HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{

				ContactGroup customerGroup = customerGroupService.getContactGroupByContactGroupId(Integer.valueOf(customerGroupBean.getCustomerGroupId()),currentUser.getCompany().getCompanyId());
				customerGroup.setContactGroupName(customerGroupBean.getCustomerGroupName());
				if(customerGroupBean.getCountryId()!= null){
					customerGroup.setCountry(countryService.getCountryByCountryId(Integer.parseInt(customerGroupBean.getCountryId())));
				}
				customerGroup.setLastUpdated(new Date());
				customerGroup.setUpdatedBy(currentUser.getUserId());
				customerGroupService.updateContactGroup(customerGroup,currentUser.getCompany().getCompanyId());
				

				util.AuditTrail(request, currentUser, "CustomerGroupController.updateCustomerGroup", 
						"User "+ currentUser.getUserEmail()+" Updated Customer Group +"+customerGroupBean.getCustomerGroupName()+" successfully ",false);
				return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.PRODUCTTYPE);


			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "CustomerGroupController.updateCustomerGroup",
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

		List<CustomerGroupBean> customerGroupBeanList = new ArrayList<>();
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
						customerGroupBean.setCustomerGroupName(customerGroup.getContactGroupName());
						if(customerGroup.getCountry()!=null){
							Country country = countryService.getCountryByCountryId(customerGroup.getCountry().getCountryId());
							customerGroupBean.setCountryId(country.getCountryId().toString());
							customerGroupBean.setCountryName(country.getCountryName());
						}
						customerGroupBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(customerGroup.getCreatedDate()));
						customerGroupBeanList.add(customerGroupBean);
					}
					util.AuditTrail(request, currentUser, "CustomerGroupController.getAllCustomerGroups", 
							"User "+ currentUser.getUserEmail()+" retrived all Customer Groups successfully ",false);
					return new Response(customerGroupBeanList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "CustomerGroupController.getAllCustomerGroups", 
							" Customer Groups are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "CustomerGroupController.getAllCustomerGroups",
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
	@RequestMapping(value = "/getAllCountries/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllCountries(@PathVariable("sessionId") String sessionId,
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
				util.AuditTrail(request, currentUser, "CustomerGroupController.getAllCountries", 
						"User "+ currentUser.getUserEmail()+" retrived all countries successfully ",false);
				return new Response(countryListBeans, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} else {
				util.AuditTrail(request, currentUser, "CustomerGroupController.getAllCountries", "User "+ 
						currentUser.getUserEmail()+" Unable to retrived all countries ",false);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			util.AuditTrail(request, currentUser, "CustomerGroupController.getAllCountries",
					"Error Occured " + errors.toString(),true);
			return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);

		}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
}

