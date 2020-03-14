/**
 * 
 */
package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import com.dowhile.Severity;
import com.dowhile.Ticket;
import com.dowhile.TicketActivity;
import com.dowhile.User;
import com.dowhile.angualrspringapp.beans.Select;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.controller.bean.TicketActivityControllerBean;
import com.dowhile.frontend.mapping.bean.TicketActivityBean;
import com.dowhile.frontend.mapping.bean.TicketBean;
import com.dowhile.service.ResourceService;
import com.dowhile.service.SeverityService;
import com.dowhile.service.TicketActivityService;
import com.dowhile.service.TicketService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * @author Yameen Bashir
 *
 */
@Controller
@RequestMapping("/ticketActivity")
public class TicketActivityController {

	// private static Logger logger = Logger.getLogger(TicketActivityController.class.getName());
	@Resource
	private SeverityService severityService;
	@Resource
	private ServiceUtil util ;
	@Resource
	private TicketService ticketService;
	@Resource
	private TicketActivityService ticketActivityService;
	@Resource
	private ResourceService resourceService;

	@RequestMapping("/layout")
	public String getTicketActivityControllerPartialPage(ModelMap modelMap) {
		return "ticketActivity/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getTicketActivityControllerData/{sessionId}/{ticketId}", method = RequestMethod.POST)
	public @ResponseBody Response getTicketActivityControllerData(@PathVariable("sessionId") String sessionId,
			@PathVariable("ticketId") String ticketId,
			HttpServletRequest request) {

		List<TicketActivityBean> ticketActivitiesList = null;
		TicketBean ticketBean = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				
				if(ticketId!=null && !ticketId.equalsIgnoreCase("")){
					Response response = getTicketActivitiesByTicketId(sessionId, ticketId, request);
					if(response.status.equals(StatusConstants.SUCCESS)){
						ticketActivitiesList = (List<TicketActivityBean>) response.data;
					}
					response = getTicketByTicketId(sessionId, ticketId, request);
					if(response.status.equals(StatusConstants.SUCCESS)){
						ticketBean = (TicketBean) response.data;
					}
					
				}
				TicketActivityControllerBean ticketActivityControllerBean = new TicketActivityControllerBean();
				ticketActivityControllerBean.setTicketActivitiesList(ticketActivitiesList);
				ticketActivityControllerBean.setTicketBean(ticketBean);

				util.AuditTrail(request, currentUser, "TicketActivityController.getTicketActivityControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived TicketActivityController data successfully ",false);
				return new Response(ticketActivityControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "TicketActivityController.getTicketActivityControllerData",
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
	@RequestMapping(value = "/addTicketActivity/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response addTicketActivity(@PathVariable("sessionId") String sessionId,
			@RequestBody TicketBean ticketBean, HttpServletRequest request) {

		boolean isAdded = false;
		int ticketActivityId =-1;
		HttpSession session =  request.getSession(false);
		if (SessionValidator.isSessionValid(sessionId, request)) {
			User manager = (User) session.getAttribute("user");
			try{
				Ticket ticket = ticketService.getTicketByTicketId(Integer.valueOf(ticketBean.getTicketId()));
				TicketActivity ticketActivity = new TicketActivity();
				ticketActivity.setCreatedDate(new Date());
				ticketActivity.setDescription(ticketBean.getDescription());
				ticketActivity.setLastUpdated(new Date());
				ticketActivity.setResolverFeedback(ControllersConstants.BLANK);
				ticketActivity.setTicket(ticket);
				ticketActivity.setCompany(manager.getCompany());
				if(ticket.getTicketStatus().equalsIgnoreCase(ControllersConstants.STATUS_OPEN)){
					ticketActivity.setNewStatus(ControllersConstants.STATUS_INPROGRESS);
					if(ticket.getReOpen().equalsIgnoreCase(ControllersConstants.FALSE)){
						ticketActivity.setOldStatus(ControllersConstants.STATUS_OPEN);
					}
					else{
						ticketActivity.setOldStatus(ControllersConstants.STATUS_REOPEN);
					}
				}
				else if(ticket.getTicketStatus().equalsIgnoreCase(ControllersConstants.STATUS_CLOSE)){
					ticketActivity.setNewStatus(ControllersConstants.STATUS_CLOSE);
					ticketActivity.setDescription("System is Closing Ticket Id:"+ticket.getTicketId()+" requested by: "+manager.getFirstName() +" having employee Id:"+manager.getUserId());
					if(ticket.getReOpen().equalsIgnoreCase(ControllersConstants.FALSE)){
						ticketActivity.setOldStatus(ControllersConstants.STATUS_INPROGRESS);
					}
					else{
						ticketActivity.setOldStatus(ControllersConstants.STATUS_REOPEN);
					}
				}

				ticketActivity.setUserReply(manager.getFirstName()+" "+manager.getLastName());
				ticketActivityId = ticketActivityService.addTicketActivity(ticketActivity);
				//ticketId = ticketService.addTicket(ticket);
				if(ticketActivityId!=-1){
					isAdded = true;
				}
			}catch(Exception ex){
				ex.printStackTrace();// logger.error(ex.getMessage(),ex);
				StringWriter errors = new StringWriter();
				ex.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, manager, "TicketActivityController.addTicketActivity", "Error Occured "+ errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}

			if(isAdded){
				util.AuditTrail(request, manager, "TicketActivityController.addTicketActivity", "New Ticket activity added successfully",false);
				return new Response(ticketActivityId,StatusConstants.SUCCESS,LayOutPageConstants.SUPPORT);
			}
			else{
				util.AuditTrail(request, manager, "TicketActivityController.addTicketActivity", "Unable to add new ticket activity ",false);
			}

			return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
		}
		else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/closeTicket/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response closeTicket(@PathVariable("sessionId") String sessionId,
			@RequestBody TicketBean ticketBean, HttpServletRequest request) {

		boolean isAdded = false;
		int ticketId =-1;
		HttpSession session =  request.getSession(false);
		if (SessionValidator.isSessionValid(sessionId, request)) {
			User manager = (User) session.getAttribute("user");
			try{
				Ticket ticket = ticketService.getTicketByTicketId(Integer.valueOf(ticketBean.getTicketId()));
				ticket.setLastUpdated(new Date());
				ticket.setTicketStatus(ControllersConstants.STATUS_CLOSE);
				Ticket tickett = ticketService.updateTicket(ticket);

				if(tickett!=null){
					TicketActivity ticketActivity = new TicketActivity();
					ticketActivity.setCreatedDate(new Date());
					ticketActivity.setDescription("Ticket with Id: "+ticket.getTicketId()+" has been closed. Requested by: "+manager.getFirstName()+" with employee Id: "+manager.getUserId());
					ticketActivity.setLastUpdated(new Date());
					ticketActivity.setResolverFeedback(ControllersConstants.BLANK);
					ticketActivity.setTicket(tickett);
					ticketActivity.setUserReply("System");
					ticketActivity.setCompany(manager.getCompany());
					ticketActivity.setOldStatus(ControllersConstants.STATUS_INPROGRESS);
					ticketActivity.setNewStatus(ControllersConstants.STATUS_CLOSE);
					ticketActivityService.addTicketActivity(ticketActivity);
					isAdded = true;
				}
			}catch(Exception ex){
				ex.printStackTrace();// logger.error(ex.getMessage(),ex);
				StringWriter errors = new StringWriter();
				ex.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, manager, "SupportController.addTicket", "Error Occured "+ errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
			if(isAdded){
				util.AuditTrail(request, manager, "SupportController.addTicket", "New Ticket added successfully",false);
				return new Response(ticketId,StatusConstants.SUCCESS,LayOutPageConstants.SUPPORT);
			}
			else{
				util.AuditTrail(request, manager, "SupportController.addTicket", "Unable to add new ticket ",false);
			}

			return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
		}
		else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/reOpenTicket/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response reOpenTicket(@PathVariable("sessionId") String sessionId,
			@RequestBody TicketBean ticketBean, HttpServletRequest request) {

		boolean isAdded = false;
		int ticketId =-1;
		HttpSession session =  request.getSession(false);
		if (SessionValidator.isSessionValid(sessionId, request)) {
			User manager = (User) session.getAttribute("user");
			try{
				Ticket ticket = ticketService.getTicketByTicketId(Integer.valueOf(ticketBean.getTicketId()));
				ticket.setLastUpdated(new Date());
				ticket.setTicketStatus(ControllersConstants.STATUS_OPEN);
				ticket.setReOpen(ControllersConstants.TRUE);
				ticket.setReOpenCount(ticket.getReOpenCount()+1);
				Ticket tickett = ticketService.updateTicket(ticket);

				if(tickett!=null){
					TicketActivity ticketActivity = new TicketActivity();
					ticketActivity.setCreatedDate(new Date());
					ticketActivity.setDescription("Ticket with Id: "+ticket.getTicketId()+" has been reopn. You will be responded soon. Requested by: "+manager.getFirstName()+" with employee Id: "+manager.getUserId());
					ticketActivity.setLastUpdated(new Date());
					ticketActivity.setResolverFeedback(ControllersConstants.BLANK);
					ticketActivity.setTicket(tickett);
					ticketActivity.setUserReply("System");
					ticketActivity.setCompany(manager.getCompany());
					ticketActivity.setOldStatus(ControllersConstants.STATUS_CLOSE);
					ticketActivity.setNewStatus(ControllersConstants.STATUS_REOPEN);
					ticketActivityService.addTicketActivity(ticketActivity);
					isAdded = true;
				}
			}catch(Exception ex){
				ex.printStackTrace();// logger.error(ex.getMessage(),ex);
				StringWriter errors = new StringWriter();
				ex.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, manager, "SupportController.addTicket", "Error Occured "+ errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
			if(isAdded){
				util.AuditTrail(request, manager, "SupportController.addTicket", "New Ticket added successfully",false);
				return new Response(ticketId,StatusConstants.SUCCESS,LayOutPageConstants.SUPPORT);
			}
			else{
				util.AuditTrail(request, manager, "SupportController.addTicket", "Unable to add new ticket ",false);
			}

			return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
		}
		else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getTicketActivitiesByTicketId/{sessionId}/{ticketId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getTicketActivitiesByTicketId(@PathVariable("sessionId") String sessionId,
			@PathVariable("ticketId") String ticketId,
			HttpServletRequest request) {

		List<TicketActivityBean> ticketActivitiesList = new ArrayList<>();
		List<TicketActivity> ticketActivities = null;
		HttpSession session =  request.getSession(false);
		if (SessionValidator.isSessionValid(sessionId, request)) {
			User currentUser = (User) session.getAttribute("user");
			try {
				DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd KK:mm a");
				Ticket ticket = ticketService.getTicketByTicketId(Integer.valueOf(ticketId));
				ticketActivities = ticketActivityService.getTicketActivitiesByCompanyIdTicketId(ticket.getCompany().getCompanyId(), ticket.getTicketId());
				if (ticketActivities != null) {
					for (TicketActivity ticketActivity : ticketActivities) {

						TicketActivityBean ticketActivityBean = new TicketActivityBean();
						ticketActivityBean.setCreatedDate(ticketActivity.getCreatedDate().toString());

						ticketActivityBean.setDescription(ticketActivity.getDescription());
						ticketActivityBean.setLastUpdated(ticketActivity.getLastUpdated().toString());
						ticketActivityBean.setResolverFeedBack(ticketActivity.getResolverFeedback());
						ticketActivityBean.setTicketActivityId(ticketActivity.getTicketActivityId().toString());
						ticketActivityBean.setTicketAssociationId(ticketActivity.getTicket().getTicketId().toString());
						ticketActivityBean.setOldStatus(ticketActivity.getOldStatus());
						ticketActivityBean.setNewStatus(ticketActivity.getNewStatus());

						ticketActivityBean.setUserReply(ticketActivity.getUserReply());



						String createdDate = ticket.getCreatedDate().toString();
						createdDate = createdDate.substring(0, createdDate.lastIndexOf("."));
						ticketActivityBean.setCreatedDate(outputFormat.format(inputFormat.parse(createdDate)));


						String createdTime = ticketActivity.getCreatedDate().toString();
						createdTime = createdTime.substring(0, createdTime.lastIndexOf("."));
						ticketActivityBean.setCreatedTime(outputFormat.format(inputFormat.parse(createdTime)));

						String updatedTime = ticketActivity.getLastUpdated().toString();
						updatedTime = updatedTime.substring(0, updatedTime.lastIndexOf("."));
						ticketActivityBean.setUpdatedTime(outputFormat.format(inputFormat.parse(updatedTime)));


						ticketActivitiesList.add(ticketActivityBean);
					}
					util.AuditTrail(request, currentUser, "TicketActivityController.getTicketActivities", "Ticket activities are found against comany Id :"+currentUser.getCompany().getCompanyId(),false);
					return new Response(ticketActivitiesList,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}
				else{
					util.AuditTrail(request, currentUser, "TicketActivityController.getTicketActivities", "No ticket activity is found against comany Id :"+currentUser.getCompany().getCompanyId(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}

			}
			catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "TicketActivityController.getTicketActivities", "Error Occured "+ errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}
		else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getTicketByTicketId/{sessionId}/{ticketId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getTicketByTicketId(@PathVariable("sessionId") String sessionId,
			@PathVariable("ticketId") String ticketId,HttpServletRequest request) {
		
		HttpSession session =  request.getSession(false);
		if (SessionValidator.isSessionValid(sessionId, request)) {
			User currentUser = (User) session.getAttribute("user");
			try {
					DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd KK:mm a");
					Ticket ticket = ticketService.getTicketByTicketId(Integer.valueOf(ticketId));
					if (ticket != null) {
						TicketBean ticketBean = new TicketBean();
							byte[] array = ticket.getTicketDetail();
							String s = new String(array);
							ticketBean.setTicketDetail(s);
							Severity severity = severityService.getSeverityBySeverityId(ticket.getSeverity().getSeverityId());
							ticketBean.setSeverityAssociationId(String.valueOf(severity.getSeverityId()));
							ticketBean.setSeverityDescription(severity.getSeverityDescription());
							ticketBean.setTicketId(ticket.getTicketId().toString());
							ticketBean.setTicketStatus(ticket.getTicketStatus());
							ticketBean.setDescription(ticket.getDescription());
							User user= resourceService.getUserById(ticket.getUserByCreatedBy().getUserId(),ticket.getCompany().getCompanyId());
							ticketBean.setCreatedBy(user.getFirstName());
							String createdDate = ticket.getCreatedDate().toString();
							createdDate = createdDate.substring(0, createdDate.lastIndexOf("."));
							ticketBean.setCreatedDate(outputFormat.format(inputFormat.parse(createdDate)));
						
						util.AuditTrail(request, currentUser, "TicketActivityController.getTicketByTicketId", "Ticket is found against ticket Id :"+ticketId,false);
						return new Response(ticketBean,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
					}
					else{
						util.AuditTrail(request, currentUser, "TicketActivityController.getTicketByTicketId", "No ticket is found against ticketId Id :"+ticketId,false);
						return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
					}
			}
			catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "TicketActivityController.getTicketByTicketId", "Error Occured "+ errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}
		else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

}
