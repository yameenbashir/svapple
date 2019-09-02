/**
 * 
 */
package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.service.CompanyService;
import com.dowhile.service.ResourceService;
import com.dowhile.util.SessionValidator;

/**
 * @author Zafar Shakeel
 *
 */
@Controller
@RequestMapping("/changePassword")

public class ChangePasswordController {

	@Resource
	private ResourceService resourceService;
	@Resource
	private CompanyService companyService;
	

	@RequestMapping("/layout")
	public String getContactInfoControllerPartialPage(ModelMap modelMap) {
		return "changePassword/layout";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "changePasswordReq/{sessionId}/{email}/{password}/{newPassword}", method = RequestMethod.POST)
	public @ResponseBody
	Response changePasswordReq(@PathVariable("sessionId") String sessionId,@PathVariable("email") String email,@PathVariable("password") String password,@PathVariable("newPassword") String newPassword, HttpServletRequest request){
		boolean response = false;
		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
			 response = resourceService.UpdatePassword(email, password, newPassword, currentUser.getCompany().getCompanyId());
		}
		catch (Exception e) {
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			/*util.AuditTrail(request, currentUser, "ManageEmployeeController.getEmployeeByEmployeeId",
					"Error Occured " + errors.toString(),true);
			return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,
					currentUser.getRole().getRoleId().equalsIgnoreCase(ControllersConstants.MANAGER_ROLE_ID)?LayOutPageConstants.LOGIN_REDIRECTED_MANAGER:LayOutPageConstants.LOGIN_REDIRECTED);*/
		}
			if(currentUser.getRole().getRoleId()==1){
				return new Response(response, StatusConstants.SUCCESS,
						LayOutPageConstants.HOME);
			}else{
				return new Response(response, StatusConstants.SUCCESS,
						LayOutPageConstants.SELL);
			}
	}
		else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	
	}
}

