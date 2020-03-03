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
import com.dowhile.controller.bean.ManageUserControllerBean;
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
 * Yameen Bashir
 */
@Controller
@RequestMapping("/manageUser")
public class ManageUserController {

	private static Logger logger = Logger.getLogger(ManageUserController.class.getName());
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
	public String getManageUserControllerPartialPage(ModelMap modelMap) {
		return "manageUser/layout";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getManageUserControllerData/{sessionId}/{userId}", method = RequestMethod.POST)
	public @ResponseBody Response getManageUserControllerData(@PathVariable("sessionId") String sessionId,
			@PathVariable("userId") String userId,HttpServletRequest request) {

		List<RoleBean> rolesBeanList = null;
		List<OutletBean> outletBeans = null;
		UserBean userBean = null;
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
				response = getUserByUserId(sessionId, userId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					userBean = (UserBean) response.data;
				}
				ManageUserControllerBean manageUserControllerBean = new ManageUserControllerBean();
				manageUserControllerBean.setOutletBeans(outletBeans);
				manageUserControllerBean.setRolesBeanList(rolesBeanList);
				manageUserControllerBean.setUserBean(userBean);
				
				util.AuditTrail(request, currentUser, "ManageUserController.getManageUserControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived ManageUserControllerData successfully ",false);
				return new Response(manageUserControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManageUserController.getManageUserControllerData",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getUserByUserId/{sessionId}/{userId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getUserByUserId(@PathVariable("sessionId") String sessionId,
			@PathVariable("userId") String userId, HttpServletRequest request) {
		
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try{
				
				User user = resourceService.getUserById(Integer.valueOf(userId), currentUser.getCompany().getCompanyId());
				if(user!=null){
						UserBean userBean = new UserBean();
						userBean.setCompanyId(user.getCompany().getCompanyId().toString());
						userBean.setEmail(user.getUserEmail());
						userBean.setFirstName(user.getFirstName());
						userBean.setLastName(user.getLastName());
						userBean.setPassword(user.getPassword());
						userBean.setConfirmPassword(user.getPassword());
						if(user.getOutlet()!=null){
							Outlet outlet = outletService.getOuletByOutletId(user.getOutlet().getOutletId(),user.getCompany().getCompanyId());
							userBean.setOutletId(outlet.getOutletId().toString());
							userBean.setOutletName(outlet.getOutletName());
						}
						Role role = roleService.getRoleByRoleId(user.getRole().getRoleId());
						userBean.setRoleId(role.getRoleId().toString());
						userBean.setRoleDescription(role.getDescription());
						userBean.setLastLogin(DateTimeUtil.convertDBDateTimeToGuiFormat(user.getLastLogin()));
						userBean.setUserId(user.getUserId().toString());
					
					util.AuditTrail(request, currentUser, "ManageUserController.getUserByUserId", 
							"User "+ currentUser.getUserEmail()+" fetched user with id: "+userBean.getUserId()+" successfully ",false);
					return new Response(userBean,StatusConstants.SUCCESS,LayOutPageConstants.USERS);
				}else{
					util.AuditTrail(request, currentUser, "ManageUserController.getUserByUserId", 
							"User "+ currentUser.getUserEmail()+" request for user does not found ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}
				
			}catch(Exception e){
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManageUserController.getUserByUserId",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/updateUser/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response updateUser(@PathVariable("sessionId") String sessionId,
			@RequestBody UserBean userBean, HttpServletRequest request) {
		
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				User user = resourceService.getUserById(Integer.valueOf(userBean.getUserId()), currentUser.getCompany().getCompanyId());
				User userFound = resourceService.getUserByEmail(userBean.getEmail(),currentUser.getCompany().getCompanyId());
				if(user!=null && userFound!=null &&  user.getUserId()!=userFound.getUserId()){
					util.AuditTrail(request, currentUser, "ManageUserController.updateUser", "Current User "+ 
							currentUser.getUserEmail()+" updating user email addres but, User already exits with email address : "+userBean.getEmail(),false);
					return new Response(MessageConstants.USER_ALREADY_EXIST,StatusConstants.BUSY,LayOutPageConstants.MANAGE_USER);
				}
				if(user!=null){
					user.setFirstName(userBean.getFirstName());
					user.setLastName(userBean.getLastName());
					user.setLastUpdated(new Date());
					Outlet outlet = outletService.getOuletByOutletId(Integer.valueOf(userBean.getOutletId()),currentUser.getCompany().getCompanyId());
					user.setOutlet(outlet);
					user.setPassword(userBean.getPassword());
					Role role= roleService.getRoleByRoleId(Integer.valueOf(userBean.getRoleId()));
					user.setRole(role);
					user.setUpdatedBy(currentUser.getUserId());
					user.setUserEmail(userBean.getEmail());
					resourceService.UpdateUser(user, currentUser.getCompany().getCompanyId());
					UserOutlets userOutlets = userOutletsService.getUserDefaultOutlet(user.getUserId(), currentUser.getCompany().getCompanyId());
					userOutlets.setLastUpdated(new Date());
					userOutlets.setOutlet(outlet);
					userOutlets.setUserByUpdatedBy(currentUser);
					userOutletsService.updateUserOutlets(userOutlets);
					/*UserOutlets userOutlets = new UserOutlets();
					userOutlets.setActiveIndicator(true);
					userOutlets.setCompany(currentUser.getCompany());
					userOutlets.setCreatedDate(new Date());
					userOutlets.setLastUpdated(new Date());
					userOutlets.setOutlet(outlet);
					userOutlets.setUserByCreatedBy(currentUser);
					userOutlets.setUserByUpdatedBy(currentUser);
					userOutlets.setUserByUserAssociationId(user);
					userOutletsService.updateUserOutlets(userOutlets);*/
					
					util.AuditTrail(request, currentUser, "ManageUserController.updateUser", 
							"User "+ currentUser.getUserEmail()+" Updated User+"+userBean.getFirstName()+" successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.USERS);
				}else{
					util.AuditTrail(request, currentUser, "ManageUserController.updateUser", "User "+ 
							currentUser.getUserEmail()+" Unable to update User : "+userBean.getFirstName(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}
				
			}catch(Exception e){
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManageUserController.updateUser",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
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
					util.AuditTrail(request, currentUser, "ManageUserController.getAllRoles", 
							"User "+ currentUser.getUserEmail()+" retrived all roles successfully ",false);

					return new Response(rolesBeanList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "ManageUserController.getAllRoles", 
							"User "+ currentUser.getUserEmail()+" unbale to  retrive all roles ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManageUserController.getAllRoles",
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
					util.AuditTrail(request, currentUser, "ManageUserController.getOutletsForDropDown", 
							"User "+ currentUser.getUserEmail()+" retrived all outlets successfully ",false);
					return new Response(outletBeans,StatusConstants.SUCCESS,LayOutPageConstants.OUTLETS);
				}else{
					util.AuditTrail(request, currentUser, "ManageUserController.getOutletsForDropDown", "User "+ 
							currentUser.getUserEmail()+" Unable to retrived all outlets",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}
				
			}catch(Exception e){
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManageUserController.getOutletsForDropDown",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
	

	
}

