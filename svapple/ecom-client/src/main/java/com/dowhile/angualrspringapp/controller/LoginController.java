package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Company;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.LoginBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.UserBean;
import com.dowhile.service.CompanyService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.util.ServiceUtil;

/**
 * Yameen Bashir
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	@Resource
	private ResourceService resourceService;
	@Resource
	private ServiceUtil util;
	@Resource
	private CompanyService companyService;

	@RequestMapping("/layout")
	public String getLoginControllerPartialPage(ModelMap modelMap) {
		return "login/layout";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public @ResponseBody Response doLogin(@RequestBody UserBean appuser,
			HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		boolean isExist = false;
		User user = null;
		if (appuser.getEmail() == null
				|| appuser.getEmail().trim().length() == 0)
			return new Response(MessageConstants.INVALID_USER,
					StatusConstants.ERROR, LayOutPageConstants.STAY_ON_PAGE);
		else if (appuser.getPassword() == null
				|| appuser.getPassword().trim().length() == 0)
			return new Response(MessageConstants.INVALID_PASSWORD,
					StatusConstants.ERROR, LayOutPageConstants.STAY_ON_PAGE);
		try {
			isExist = resourceService.IsUserExist(appuser.getEmail(),
					appuser.getPassword(),0);
			if (isExist) {
				user = resourceService.getUserByEmail(appuser.getEmail(),0);
				user.setLastLogin(new Date());
				resourceService.updateUserLoginTime(user,0);
				user.setPassword("hide");
				Company company = companyService.getCompanyDetailsByCompanyID(user.getCompany().getCompanyId());
				user.setCompany(company);
				session.setAttribute("user", user);
				LoginBean loginBean = new LoginBean();
				loginBean.setUserName(user.getFirstName() + " "
						+ user.getLastName());
				loginBean.setSessionId(session.getId());
				loginBean.setUserRole(String.valueOf(user.getRole().getRoleId()));
				loginBean.setUserId(user.getUserId().toString());
				System.out.println("ecom.....................");
				util.AuditTrail(request, user, "LoginController.doLogin", "User:  "+ appuser.getEmail()+" Login Successfuly ",false);
				return new Response(loginBean, StatusConstants.SUCCESS,
						LayOutPageConstants.HOME);
			} else
				//				util.AuditTrail(request, user, "Login", "User "+ appuser.getEmail()+" Invalid! ");
				return new Response(MessageConstants.CREDENTIALS_MISMATCHED,
						StatusConstants.USER_INVALID,
						LayOutPageConstants.STAY_ON_PAGE);
		} catch (Exception e) {
			e.printStackTrace();logger.error(e.getMessage(),e);
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			util.AuditTrail(request, user, "LoginController.doLogin","Error Occured " + errors.toString(),true);
			return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY, LayOutPageConstants.STAY_ON_PAGE);
		}
	}

}

