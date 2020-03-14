package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
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

import com.dowhile.Role;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.RoleBean;
import com.dowhile.service.OutletService;
import com.dowhile.service.RegisterService;
import com.dowhile.service.RoleService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/roles")
public class RolesController {

	

	

	@Resource
	private OutletService outletService;
	@Resource
	private ServiceUtil util;
	@Resource
	private RoleService roleService;

	@RequestMapping("/layout")
	public String getRolesControllerPartialPage(ModelMap modelMap) {
		return "roles/layout";
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

				rolesList = roleService.getAllRoles();
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
					util.AuditTrail(request, currentUser, "UtilitiesController.getAllRoles", 
							"User "+ currentUser.getUserEmail()+" retrived all roles successfully ",false);

					return new Response(rolesBeanList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "UtilitiesController.getAllRoles", 
							"User "+ currentUser.getUserEmail()+" unbale to  retrive all roles ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "UtilitiesController.getAllRoles",
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

