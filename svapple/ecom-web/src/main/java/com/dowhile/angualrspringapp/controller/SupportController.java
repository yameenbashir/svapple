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
import com.dowhile.controller.bean.SupportControllerBean;
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
@RequestMapping("/support")

public class SupportController {

	@Resource
	private SeverityService severityService;
	@Resource
	private ServiceUtil util ;
	@Resource
	private TicketService ticketService;
	@Resource
	private ResourceService resourceService;
	@Resource
	private TicketActivityService ticketActivityService;


	@RequestMapping("/layout")
	public String getSupportControllerPartialPage(ModelMap modelMap) {
		return "support/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getSupportControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getSupportControllerData(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<TicketBean> ticketsList = null;
		List<Select> severityList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				Response response = getAllTickets(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					ticketsList = (List<TicketBean>) response.data;
				}
				response = getSeverities(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					severityList = (List<Select>) response.data;
				}
				SupportControllerBean supportControllerBean = new SupportControllerBean();
				supportControllerBean.setSeverityList(severityList);
				supportControllerBean.setTicketsList(ticketsList);

				util.AuditTrail(request, currentUser, "SupportController.getSupportControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived TicketActivityController data successfully ",false);
				return new Response(supportControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SupportController.getSupportControllerData",
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
	@RequestMapping(value = "/addTicket/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response addTicket(@PathVariable("sessionId") String sessionId,
			@RequestBody TicketBean ticketBean, HttpServletRequest request) {

		boolean isAdded = false;
		TicketBean ticketbean = new TicketBean();
		HttpSession session =  request.getSession(false);
		if (SessionValidator.isSessionValid(sessionId, request)) {
			User manager = (User) session.getAttribute("user");
			try{
				Ticket ticket =  new Ticket();
				ticket.setCompany(manager.getCompany());
				ticket.setCreatedDate(new Date());
				ticket.setLastUpdated(new Date());
				ticket.setReOpen(ControllersConstants.FALSE);
				ticket.setReOpenCount(0);
				Severity severity = severityService.getSeverityBySeverityId(Integer.valueOf(ticketBean.getSeverityAssociationId()));
				ticket.setSeverity(severity);
				ticket.setTicketDetail(ticketBean.getTicketDetail().getBytes());
				ticket.setTicketStatus(ControllersConstants.STATUS_OPEN);
				ticket.setUserByCreatedBy(manager);
				ticket.setDescription(ticketBean.getDescription());
				Ticket savedTick = ticketService.addTicket(ticket);

				if(savedTick!=null){

					TicketActivity ticketActivity = new TicketActivity();
					ticketActivity.setCreatedDate(new Date());
					ticketActivity.setDescription("Ticket with Id: "+savedTick.getTicketId()+" has been created you will be responded soon.");
					ticketActivity.setLastUpdated(new Date());
					ticketActivity.setResolverFeedback(ControllersConstants.BLANK);
					ticketActivity.setTicket(savedTick);
					ticketActivity.setUserReply("System");
					ticketActivity.setCompany(manager.getCompany());
					ticketActivity.setOldStatus(ControllersConstants.STATUS_NEW);
					ticketActivity.setNewStatus(ControllersConstants.STATUS_OPEN);
					ticketActivityService.addTicketActivity(ticketActivity);
					isAdded = true;
				}
			}catch(Exception ex){
				ex.printStackTrace();logger.error(ex.getMessage(),ex);
				StringWriter errors = new StringWriter();
				ex.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, manager, "SupportController.addTicket", "Error Occured "+ errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
			if(isAdded){
				util.AuditTrail(request, manager, "SupportController.addTicket", "New Ticket added successfully",false);
				return new Response(ticketbean,StatusConstants.SUCCESS,LayOutPageConstants.SUPPORT);
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
	@RequestMapping(value = "/getAllTickets/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getAllTickets(@PathVariable("sessionId") String sessionId,HttpServletRequest request) {

		List<TicketBean> ticketsList = new ArrayList<>();
		List<Ticket> tickets = null;
		HttpSession session =  request.getSession(false);
		if (SessionValidator.isSessionValid(sessionId, request)) {
			User currentUser = (User) session.getAttribute("user");
			try {

				DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd KK:mm a");
				tickets = ticketService.getAllTicketsByCompanyId(currentUser.getCompany().getCompanyId());
				if (tickets != null) {
					for (Ticket ticket : tickets) {

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


						ticketsList.add(ticketBean);
					}
					util.AuditTrail(request, currentUser, "SupportController.getAllTickets", "Tickets are found against comany Id :"+currentUser.getCompany().getCompanyId(),false);
					return new Response(ticketsList,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}
				else{
					util.AuditTrail(request, currentUser, "SupportController.getAllTickets", "No tickets are found against comany Id :"+currentUser.getCompany().getCompanyId(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SupportController.getAllTickets", "Error Occured "+ errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}
		else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}



	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getSeverities/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getSeverities(@PathVariable("sessionId") String sessionId,HttpServletRequest request) {

		List<Select> severityList = new ArrayList<>();
		List<Severity> severities = null;
		HttpSession session =  request.getSession(false);
		if (SessionValidator.isSessionValid(sessionId, request)) {
			User currentUser = (User) session.getAttribute("user");
			try {
				severities = severityService.getAllSeverities();
				if (severities != null) {
					for(Severity severity:severities){
						Select select = new Select();
						select.setId(String.valueOf(severity.getSeverityId()));
						select.setValue(severity.getSeverityDescription());
						severityList.add(select);
					}
					util.AuditTrail(request, currentUser, "SupportController.getSeverities", "Severities are retrived requested by :"+currentUser.getFirstName(),false);
					return new Response(severityList,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}
				else{
					util.AuditTrail(request, currentUser, "SupportController.getSeverities", "No Severities are found requested by :"+currentUser.getFirstName(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SupportController.getSeverities", "Error Occured "+ errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}
		else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

}
