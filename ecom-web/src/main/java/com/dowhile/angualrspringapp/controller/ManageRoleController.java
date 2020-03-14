/**
 * 
 */
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

import com.dowhile.Menu;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.MenuBean;
import com.dowhile.service.MenuService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * @author Yameen Bashir
 *
 */
@Controller
@RequestMapping("/manageRole")
public class ManageRoleController {

	// private static Logger logger = Logger.getLogger(ManageRoleController.class.getName());
	@Resource
	private ServiceUtil util;
	@Resource
	private MenuService menuService;

	@RequestMapping("/layout")
	public String getManageRoleControllerPartialPage(ModelMap modelMap) {
		return "manageRole/layout";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getPageRightsListByRoleId/{sessionId}/{roleId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getPageRightsListByRoleId(@PathVariable("sessionId") String sessionId,
			@PathVariable("roleId") String roleId,
			HttpServletRequest request) {
		List<MenuBean> menuBeanList = new ArrayList<>();
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				List<Menu> menuList = menuService.getMenuByRoleId(Integer.valueOf(roleId),currentUser.getCompany().getCompanyId());
				if(menuList!=null){
					for(Menu menu:menuList){
						MenuBean menuBean = new MenuBean();
						menuBean.setActiveIndicatior(menu.isActiveIndicator());
						menuBean.setMenuId(menu.getMenuId().toString());
						menuBean.setPageName(menu.getMenuName());
						menuBeanList.add(menuBean);
					}
					util.AuditTrail(request, currentUser, "ManageRoleController.getPageRightsListByRoleId", 
							"User "+ currentUser.getUserEmail()+" retrived menu list against role id "+roleId+" successfully ",false);
					return new Response(menuBeanList,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "ManageRoleController.getPageRightsListByRoleId", "User "+ 
							currentUser.getUserEmail()+" Unable to retrived menu list ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManageRoleController.getPageRightsListByRoleId",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/updatePageRightsByRoleId/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response updatePageRightsByRoleId(@PathVariable("sessionId") String sessionId,
			@RequestBody List<MenuBean> menuBeanList,HttpServletRequest request) {
		
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				if(menuBeanList!=null){
					for(MenuBean menuBean:menuBeanList){
						Menu menu = menuService.getMenuByMenuIdCompanyId(Integer.valueOf(menuBean.getMenuId()), currentUser.getCompany().getCompanyId());
						menu.setActiveIndicator(menuBean.getActiveIndicatior());
						menu.setLastUpdated(new Date());
						menu.setUpdatedBy(currentUser.getUserId());
						menuService.updateMenu(menu, currentUser.getCompany().getCompanyId());
					}
					util.AuditTrail(request, currentUser, "ManageRoleController.updatePageRightsByRoleId", 
							"User "+ currentUser.getUserEmail()+" page rights updated successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "ManageRoleController.updatePageRightsByRoleId", "User "+ 
							currentUser.getUserEmail()+" Unable to update page rights ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManageRoleController.updatePageRightsByRoleId",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
}
