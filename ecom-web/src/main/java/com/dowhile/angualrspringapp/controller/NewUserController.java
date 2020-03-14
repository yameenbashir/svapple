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

import com.dowhile.Outlet;
import com.dowhile.Role;
import com.dowhile.User;
import com.dowhile.UserOutlets;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.NewUserControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.RoleBean;
import com.dowhile.frontend.mapping.bean.UserBean;
import com.dowhile.service.OutletService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.RoleService;
import com.dowhile.service.UserOutletsService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/newUser")
public class NewUserController {

	// private static Logger logger = Logger.getLogger(NewUserController.class.getName());
	@Resource
	private ResourceService resourceService;
	@Resource
	private ServiceUtil util;
	@Resource
	private OutletService outletService;
	@Resource
	private RoleService roleService;
	@Resource
	private UserOutletsService userOutletsService;

	@RequestMapping("/layout")
	public String getNewUserControllerPartialPage(ModelMap modelMap) {
		return "newUser/layout";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getNewUserControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getNewUserControllerData(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<RoleBean> rolesBeanList = null;
		List<OutletBean> outletBeans = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");


			try {

				Response response = getAllRoles(sessionId,request);

				if(response.status.equals(StatusConstants.SUCCESS)){
					rolesBeanList = (List<RoleBean>) response.data;
				}


				response = getOutletsForDropDown(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					outletBeans = (List<OutletBean>) response.data;
				}

				NewUserControllerBean newUserControllerBean = new NewUserControllerBean();
				newUserControllerBean.setOutletBeans(outletBeans);
				newUserControllerBean.setRolesBeanList(rolesBeanList);

				
				util.AuditTrail(request, currentUser, "NewUserController.getNewUserControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived NewUserControllerData successfully ",false);
				return new Response(newUserControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NewUserController.getNewUserControllerData",
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
	@RequestMapping(value = "/addUser/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response addUser(@PathVariable("sessionId") String sessionId,
			@RequestBody UserBean userBean, HttpServletRequest request) {
		
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				User userFound = resourceService.getUserByEmail(userBean.getEmail(),currentUser.getCompany().getCompanyId());
				if(userFound!=null){
					util.AuditTrail(request, currentUser, "NewUserController.addUser", "Current User "+ 
							currentUser.getUserEmail()+" adding user but, User already exits with email address : "+userBean.getEmail(),false);
					return new Response(MessageConstants.USER_ALREADY_EXIST,StatusConstants.BUSY,LayOutPageConstants.NEW_USER);
				}
				Outlet outlet = outletService.getOuletByOutletId(Integer.valueOf(userBean.getOutletId()),currentUser.getCompany().getCompanyId());
				if(outlet!=null){
					
					User user = new User();
					user.setActiveIndicator(true);
					user.setCreatedBy(currentUser.getUserId());
					user.setCreatedDate(new Date());
					user.setFirstName(userBean.getFirstName());
					user.setLastName(userBean.getLastName());
					user.setLastUpdated(new Date());
					user.setOutlet(outlet);
					user.setPassword(userBean.getPassword());
					Role role= roleService.getRoleByRoleId(Integer.valueOf(userBean.getRoleId()));
					user.setRole(role);
					user.setUpdatedBy(currentUser.getUserId());
					user.setUserEmail(userBean.getEmail());
					user.setLastLogin(new Date());
					user.setCompany(currentUser.getCompany());
					User tempUser = resourceService.addUser(user,currentUser.getCompany().getCompanyId());
					UserOutlets userOutlets = new UserOutlets();
					userOutlets.setActiveIndicator(true);
					userOutlets.setCompany(currentUser.getCompany());
					userOutlets.setCreatedDate(new Date());
					userOutlets.setLastUpdated(new Date());
					userOutlets.setOutlet(outlet);
					userOutlets.setUserByCreatedBy(currentUser);
					userOutlets.setUserByUpdatedBy(currentUser);
					userOutlets.setUserByUserAssociationId(tempUser);
					userOutletsService.addUserOutlets(userOutlets);
					
					util.AuditTrail(request, currentUser, "NewUserController.addUser", 
							"User "+ currentUser.getUserEmail()+" Added User+"+userBean.getFirstName()+" successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.USERS);
				}else{
					util.AuditTrail(request, currentUser, "NewUserController.addUser", "User "+ 
							currentUser.getUserEmail()+" Unable to add User : "+userBean.getFirstName(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.NEW_USER);
				}
				
			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NewUserController.addUser",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.NEW_USER);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllRoles/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllRoles
	(@PathVariable("sessionId") String sessionId,HttpServletRequest request) {

		List<RoleBean> rolesBeanList = new ArrayList<>();
		List<Role> rolesList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {

				rolesList = roleService.getAllRolesWithoutAdmin();
				if (rolesList != null) {
					for (Role role : rolesList) {

						RoleBean roleBean = new RoleBean();
						roleBean.setRoleId(role.getRoleId().toString());
						roleBean.setDescription(role.getDescription());
						roleBean.setActionType(role.getActionType());
						roleBean.setActiveIndicator(String.valueOf(role.isActiveIndicator()));
						roleBean.setLastUpdated(DateTimeUtil.convertDBDateTimeToGuiFormat(role.getLastUpdated()));

						rolesBeanList.add(roleBean);
					}
					util.AuditTrail(request, currentUser, "NewUserController.getAllRoles", 
							"User "+ currentUser.getUserEmail()+" retrived all roles successfully ",false);

					return new Response(rolesBeanList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "NewUserController.getAllRoles", 
							"User "+ currentUser.getUserEmail()+" unbale to  retrive all roles ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NewUserController.getAllRoles",
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
	@RequestMapping(value = "/getOutletsForDropDown/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getOutletsForDropDown(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<OutletBean> outletBeans = new ArrayList<>();
		List<Outlet> outlets = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				outlets = outletService.getOutlets(currentUser.getCompany().getCompanyId());
				if(outlets!=null){
					for(Outlet outlet:outlets){
						OutletBean outletBean = new OutletBean();
						outletBean.setOutletId(outlet.getOutletId().toString());
						outletBean.setOutletName(outlet.getOutletName());
						
						outletBeans.add(outletBean);
					}
					util.AuditTrail(request, currentUser, "NewUserController.getOutletsForDropDown", 
							"User "+ currentUser.getUserEmail()+" retrived all outlets successfully ",false);
					return new Response(outletBeans,StatusConstants.SUCCESS,LayOutPageConstants.OUTLETS);
				}else{
					util.AuditTrail(request, currentUser, "NewUserController.getOutletsForDropDown", "User "+ 
							currentUser.getUserEmail()+" Unable to retrived all outlets",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}
				
			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NewUserController.getOutletsForDropDown",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
	

	
}

