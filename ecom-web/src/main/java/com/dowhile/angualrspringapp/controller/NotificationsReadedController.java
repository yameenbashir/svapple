package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Configuration;
import com.dowhile.Contact;
import com.dowhile.Notification;
import com.dowhile.Outlet;
import com.dowhile.Tag;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.NotificationBean;
import com.dowhile.frontend.mapping.bean.TagBean;
import com.dowhile.service.NotificationService;
import com.dowhile.service.OutletService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductTagService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.TagService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;/**
 /**
 * @author Mohammad Asif
 *
 */
@Controller
@RequestMapping("/notificationsReaded")
public class NotificationsReadedController {
	
	private static Logger logger = Logger.getLogger(NotificationsReadedController.class.getName());
	@Resource
	private ResourceService resourceService;
	@Resource
	private ServiceUtil util;
	@Resource
	private TagService tagService;
	@Resource
	private ProductTagService productTagService;
	@Resource
	private ProductService productService;
	@Resource
	private NotificationService notificationService;
	@Resource
	private OutletService outletService;
	@RequestMapping("/layout")
	public String getNotificationsReadedControllerPartialPage(ModelMap modelMap) {
		return "notificationsReaded/layout";
	}



	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getAllNotifications/{sessionId}/{loadAllNotifications}", method = RequestMethod.POST)
	public @ResponseBody Response getAllNotifications(@PathVariable("sessionId") String sessionId,@PathVariable("loadAllNotifications") String loadAllNotifications,
			HttpServletRequest request) {

		List<Notification> notificationsList = null;
		List<NotificationBean> notificationBeanList = new ArrayList<>();
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {

				Map<Integer, Outlet> outletMap = outletService.getAllOutletsMapByCompanyId(currentUser.getCompany().getCompanyId());
				if(currentUser.getRole().getRoleId()==1 && currentUser.getOutlet().getIsHeadOffice()!=null && currentUser.getOutlet().getIsHeadOffice().toString()=="true"){
					if(loadAllNotifications.equalsIgnoreCase("true")) {
						notificationsList = notificationService.getAllReadedNotificationsByCompanyId(currentUser.getCompany().getCompanyId());
					}else {
						notificationsList = notificationService.getTenReadedNotificationsByCompanyId(currentUser.getCompany().getCompanyId());
					}

				}else{
					if(loadAllNotifications.equalsIgnoreCase("true")) {
						notificationsList = notificationService.getAllReadedNotificationsByOutletIdCompanyId(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
					}else {
						notificationsList = notificationService.getTenReadedNotificationsByOutletIdCompanyId(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
					}


				}
				if (notificationsList != null) {
					for (Notification notification : notificationsList) {
						NotificationBean notificationBean = new NotificationBean();
						BeanUtils.copyProperties(notification, notificationBean);
						notificationBean.setOutletByOutletIdFrom(notification.getOutletByOutletIdFrom().getOutletId());
						Outlet outletFrom = outletMap.get(notification.getOutletByOutletIdFrom().getOutletId());
						notificationBean.setOutletByOutletIdFromName(outletFrom.getOutletName());
						notificationBean.setOutletByOutletIdTo(notification.getOutletByOutletIdTo().getOutletId());
						Outlet outletTo = outletMap.get(notification.getOutletByOutletIdTo().getOutletId());
						notificationBean.setOutletByOutletIdToName(outletTo.getOutletName());
						notificationBean.setCompanyId(notification.getCompany().getCompanyId());
						notificationBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(notification.getCreatedDate()));
						notificationBean.setLastUpdated(DateTimeUtil.convertDBDateTimeToGuiFormat(notification.getLastUpdated()));
						notificationBean.setDescription(notification.getDescription());
						notificationBean.setMarkAsRead(notification.isMarkAsRead());

						notificationBeanList.add(notificationBean);


					}

					util.AuditTrail(request, currentUser, "NotificationsReadedController.getAllNotifications", 
							"User "+ currentUser.getUserEmail()+" retrived all Notifications successfully ",false);
					return new Response(notificationBeanList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "NotificationsReadedController.getAllNotifications", 
							" Notifications are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NotificationsController.getAllNotifications",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}







	/*@SuppressWarnings({ "unchecked", "rawtypes" })
@RequestMapping(value = "/getAllReadedNotifications/{sessionId}", method = RequestMethod.GET)
public @ResponseBody
Response getAllReadedNotifications(@PathVariable("sessionId") String sessionId,
		HttpServletRequest request) {
	if(SessionValidator.isSessionValid(sessionId, request)){
		HttpSession session =  request.getSession(false);
		User currentUser = (User) session.getAttribute("user");
		try {
			List<Notification> notificationsList= notificationService.getAllReadedNotificationsByOutletIdCompanyId(currentUser.getOutlet().getOutletId(),currentUser.getCompany().getCompanyId());
			if(notificationsList!=null && notificationsList.size()>0){

				return new Response(notificationsList.size(),StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
			}else{
				return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
			}

		} catch (Exception e) {
			e.printStackTrace();logger.error(e.getMessage(),e);
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			return new Response(MessageConstants.SYSTEM_BUSY,
					StatusConstants.BUSY,
					LayOutPageConstants.STAY_ON_PAGE);
		}
	}
	else{
		return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
	}

}*/




}
