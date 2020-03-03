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

import com.dowhile.Message;
import com.dowhile.SmsReport;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.controller.bean.SmsDetailControllerBean;
import com.dowhile.controller.bean.SmsReportBean;
import com.dowhile.service.MessageService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.SmsReportService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/smsDetail")
public class SmsDetailController {

	@Resource
	private ResourceService resourceService;
	@Resource
	private ServiceUtil util;
	
	@Resource
	private SmsReportService smsReportsService;
	
	@Resource
	private MessageService messageService;

	
	

	@RequestMapping("/layout")
	public String getSmsDetailControllerPartialPage(ModelMap modelMap) {
		return "smsDetail/layout";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllSmsDetail/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllSmsDetail(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<SmsReportBean> smsReportBeanList = new ArrayList<>();
		SmsDetailControllerBean smsDetailControllerBean = new SmsDetailControllerBean();
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");


			try {
				List<SmsReport> smsReportsList = smsReportsService.getAllSmsByCompanyIdOutletId(currentUser.getCompany().getCompanyId(),currentUser.getOutlet().getOutletId());
				if(smsReportsList!=null){
					for(SmsReport smsReport:smsReportsList){
						SmsReportBean smsReportBean= new SmsReportBean();
						smsReportBean.setCompanyAssociationId(smsReport.getId().getCompanyAssociationId()+"");
						smsReportBean.setCreatedDate(smsReport.getId().getCreatedDate().toString());
						smsReportBean.setDeliveryId(smsReport.getId().getDeliveryId());
						smsReportBean.setDeliveryStatus(smsReport.getId().getDeliveryStatus());
						smsReportBean.setMessageAssociationId(smsReport.getId().getMessageAssociationId()+"");
						smsReportBean.setMessageBundleCount(smsReport.getId().getMessageBundleCount()+"");
						smsReportBean.setMessageDescription(smsReport.getId().getMessageDescription());
						smsReportBean.setMessageDetailId(smsReport.getId().getMessageDetailId()+"");
						smsReportBean.setOutletAssocicationId(smsReport.getId().getOutletAssocicationId());
						smsReportBean.setOutletName(smsReport.getId().getOutletName());
						smsReportBean.setReceiverId(smsReport.getId().getReceiverId());
						smsReportBean.setSenderId(smsReport.getId().getSenderId());
						smsReportBean.setSenderName(smsReport.getId().getSenderName());
						smsReportBean.setSmsSentDate(DateTimeUtil.convertDBDateTimeToGuiFormat(smsReport.getId().getCreatedDate()));
						smsReportBean.setCustomerName(smsReport.getId().getCustomerName());
						smsReportBeanList.add(smsReportBean);
					}
					smsDetailControllerBean.setSmsReportBeanList(smsReportBeanList);
					Message messageObj = messageService.getMessageByCompanyId(currentUser.getCompany().getCompanyId().toString());
					smsDetailControllerBean.setSmsRemained(messageObj.getMessageBundleCount()-messageObj.getMessageTextLimit());
					smsDetailControllerBean.setSmsUsedCount(messageObj.getMessageTextLimit());
					util.AuditTrail(request, currentUser, "SmsDetailController.getAllSmsDetail", 
							"User "+ currentUser.getUserEmail()+" retrived all sms detail successfully ",false);
					return new Response(smsDetailControllerBean, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				}
				else {
					util.AuditTrail(request, currentUser, "SmsDetailController.getAllSmsDetail", 
							" sms details are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
				
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SmsDetailController.getAllSmsDetail",
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

