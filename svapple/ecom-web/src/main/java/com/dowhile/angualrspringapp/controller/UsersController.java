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

import com.dowhile.Outlet;
import com.dowhile.Role;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.UserBean;
import com.dowhile.service.OutletService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.RoleService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/users")
public class UsersController {

	@Resource
	private ResourceService resourceService;
	@Resource
	private ServiceUtil util;
	@Resource
	private OutletService outletService;
	@Resource
	private RoleService roleService;

	@RequestMapping("/layout")
	public String getUsersControllerPartialPage(ModelMap modelMap) {
		return "users/layout";
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getAllUsers/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getAllUsers(@PathVariable("sessionId") String sessionId,
			 HttpServletRequest request) {
		
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			List<UserBean> userBeanList = new ArrayList<>();

			try{
				
				List<User> users = resourceService.getAllEmployeesByCompanyId(currentUser.getCompany().getCompanyId());
				if(users!=null){
					for(User user:users){
						UserBean userBean = new UserBean();
						userBean.setCompanyId(user.getCompany().getCompanyId().toString());
						userBean.setEmail(user.getUserEmail());
						userBean.setFirstName(user.getFirstName());
						userBean.setLastName(user.getLastName());
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
						userBeanList.add(userBean);
					}
					util.AuditTrail(request, currentUser, "UsersController.getAllUsers", 
							"User "+ currentUser.getUserEmail()+" fetched all users successfully ",false);
					return new Response(userBeanList,StatusConstants.SUCCESS,LayOutPageConstants.USERS);
				}else{
					util.AuditTrail(request, currentUser, "UsersController.getAllUsers", 
							"User "+ currentUser.getUserEmail()+" request for all users does not found ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}
				
			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "UsersController.getAllUsers",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	
	
	
}

