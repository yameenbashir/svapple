package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.ContactGroup;
import com.dowhile.Outlet;
import com.dowhile.PriceBook;
import com.dowhile.User;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.PriceBookBean;
import com.dowhile.service.ContactGroupService;
import com.dowhile.service.OutletService;
import com.dowhile.service.PriceBookService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/priceBook")
public class PriceBookController {

	// private static Logger logger = Logger.getLogger(PriceBookController.class.getName());
	@Resource
	private ContactGroupService customerGroupService;
	@Resource
	private ServiceUtil util;
	@Resource
	private OutletService outletService;
	@Resource
	private PriceBookService priceBookService;
	

	@RequestMapping("/layout")
	public String getPriceBookControllerPartialPage(ModelMap modelMap) {
		return "priceBook/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllPriceBooks/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllPriceBooks(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<PriceBookBean> priceBookBeansList = new ArrayList<>();
		List<PriceBook> priceBookList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			Date todayDate = null;
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				todayDate = dateFormatter.parse(dateFormatter.format(new Date() ));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				StringWriter errors = new StringWriter();
				e1.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PriceBookController.getAllPriceBooks",
						"Error Occured " + errors.toString(),true);
			}
			try {
				if(todayDate==null){
					todayDate = new Date();
				}
				
				priceBookList = priceBookService.getAllActivePriceBooksByCompanyId(currentUser.getCompany().getCompanyId());
				if (priceBookList != null) {
					List<Outlet> outlets = outletService.getOutlets(currentUser.getCompany().getCompanyId());
					Map outletMap = new HashMap<>();
					for(Outlet outlet:outlets){
						outletMap.put(outlet.getOutletId(), outlet);
					}
					List<ContactGroup> contactGrupList = customerGroupService.GetAllContactGroup(currentUser.getCompany().getCompanyId());
					Map contactGroupMap = new HashMap<>();
					for(ContactGroup contactGroup:contactGrupList){
						contactGroupMap.put(contactGroup.getContactGroupId(), contactGroup);
					}
					for (PriceBook priceBook : priceBookList) {
						PriceBookBean priceBookBean = new PriceBookBean();
						if(priceBook.getOutlet()!=null){
							Outlet outlet = (Outlet)outletMap.get(priceBook.getOutlet().getOutletId());
							priceBookBean.setOutletId(outlet.getOutletId().toString());
							priceBookBean.setOutletName(outlet.getOutletName());
						}else{
							priceBookBean.setOutletName(ControllersConstants.ALL_OUTLETS);
						}
						ContactGroup contactGroup = (ContactGroup)contactGroupMap.get(priceBook.getContactGroup().getContactGroupId());
						priceBookBean.setContactGroupId(contactGroup.getContactGroupId().toString());
						priceBookBean.setContactGroupName(contactGroup.getContactGroupName());
						priceBookBean.setIsValidOnStore(priceBook.getIsValidOnStore()==true?ControllersConstants.TRUE:ControllersConstants.FALSE);
						priceBookBean.setPriceBookId(priceBook.getPriceBookId().toString());
						priceBookBean.setPriceBookName(priceBook.getPriceBookName());
						priceBookBean.setValidFrom(priceBook.getValidFrom().toString());
						priceBookBean.setValidTo(priceBook.getValidTo().toString());
						priceBookBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(priceBook.getCreatedDate()));
						String [] outletGroups = priceBook.getOuteletsGroup().split(",");
						String outletNameGroup = "";
						for(String outletId:outletGroups){
							Outlet outlet = (Outlet) outletMap.get(Integer.valueOf(outletId));
							if(outletNameGroup.equalsIgnoreCase("")){
								
								outletNameGroup = outlet.getOutletName();
							}else{
								outletNameGroup = outletNameGroup+","+outlet.getOutletName();
							}
						}
						priceBookBean.setOuteletsGroup(outletNameGroup);
						
						Calendar localTIme = new GregorianCalendar(TimeZone.getTimeZone("UTC +7"));
						localTIme.setTime(priceBook.getValidTo());
						localTIme.add(Calendar.HOUR_OF_DAY, 9);//For live
//						localTIme.add(Calendar.HOUR_OF_DAY, 0);//For local
						/*System.out.println("priceBook.getValidTo(): "+priceBook.getValidTo());
						System.out.println("todayDate: "+todayDate);
						System.out.println("localTIme.getTime().equals(new Date())||localTIme.getTime().after(new Date()): "+localTIme.getTime().equals(todayDate)+localTIme.getTime().after(todayDate));*/
						if(localTIme.getTime().equals(todayDate)||localTIme.getTime().after(todayDate)){
							priceBookBean.setActive(priceBook.isActiveIndicator()==true?ControllersConstants.TRUE:ControllersConstants.FALSE);
						}else if(localTIme.getTime().before(todayDate)){
							priceBookBean.setActive(priceBook.isActiveIndicator()==true?ControllersConstants.EXPIRE:ControllersConstants.FALSE);
						}
						else{
							priceBookBean.setActive(priceBook.isActiveIndicator()==true?ControllersConstants.TRUE:ControllersConstants.FALSE);
						}
						
						priceBookBeansList.add(priceBookBean);
					}
					util.AuditTrail(request, currentUser, "PriceBookController.getAllPriceBooks", 
							"User "+ currentUser.getUserEmail()+" retrived all PriceBooks successfully ",false);
					return new Response(priceBookBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.PRICEBOOK);
				} else {
					util.AuditTrail(request, currentUser, "PriceBookController.getAllPriceBooks", 
							" PriceBooks are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.PRICEBOOK);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PriceBookController.getAllPriceBooks",
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
	@RequestMapping(value = "/getAllInActivePriceBooks/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllInActivePriceBooks(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<PriceBookBean> priceBookBeansList = new ArrayList<>();
		List<PriceBook> priceBookList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			Date todayDate = null;
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				todayDate = dateFormatter.parse(dateFormatter.format(new Date() ));
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				StringWriter errors = new StringWriter();
				e1.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PriceBookController.getAllInActivePriceBooks",
						"Error Occured " + errors.toString(),true);
			}
			try {
				if(todayDate==null){
					todayDate = new Date();
				}
				
				priceBookList = priceBookService.getAllInActivePriceBooksByCompanyId(currentUser.getCompany().getCompanyId());
				if (priceBookList != null) {
					List<Outlet> outlets = outletService.getOutlets(currentUser.getCompany().getCompanyId());
					Map outletMap = new HashMap<>();
					for(Outlet outlet:outlets){
						outletMap.put(outlet.getOutletId(), outlet);
					}
					List<ContactGroup> contactGrupList = customerGroupService.GetAllContactGroup(currentUser.getCompany().getCompanyId());
					Map contactGroupMap = new HashMap<>();
					for(ContactGroup contactGroup:contactGrupList){
						contactGroupMap.put(contactGroup.getContactGroupId(), contactGroup);
					}
					for (PriceBook priceBook : priceBookList) {
						PriceBookBean priceBookBean = new PriceBookBean();
						if(priceBook.getOutlet()!=null){
							Outlet outlet = (Outlet)outletMap.get(priceBook.getOutlet().getOutletId());
							priceBookBean.setOutletId(outlet.getOutletId().toString());
							priceBookBean.setOutletName(outlet.getOutletName());
						}else{
							priceBookBean.setOutletName(ControllersConstants.ALL_OUTLETS);
						}
						ContactGroup contactGroup = (ContactGroup)contactGroupMap.get(priceBook.getContactGroup().getContactGroupId());
						priceBookBean.setContactGroupId(contactGroup.getContactGroupId().toString());
						priceBookBean.setContactGroupName(contactGroup.getContactGroupName());
						priceBookBean.setIsValidOnStore(priceBook.getIsValidOnStore()==true?ControllersConstants.TRUE:ControllersConstants.FALSE);
						priceBookBean.setPriceBookId(priceBook.getPriceBookId().toString());
						priceBookBean.setPriceBookName(priceBook.getPriceBookName());
						priceBookBean.setValidFrom(priceBook.getValidFrom().toString());
						priceBookBean.setValidTo(priceBook.getValidTo().toString());
						priceBookBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(priceBook.getCreatedDate()));
						
						Calendar localTIme = new GregorianCalendar(TimeZone.getTimeZone("UTC +7"));
						localTIme.setTime(priceBook.getValidTo());
						localTIme.add(Calendar.HOUR_OF_DAY, 10);//For live
//						localTIme.add(Calendar.HOUR_OF_DAY, 0);//For local
						/*System.out.println("priceBook.getValidTo(): "+priceBook.getValidTo());
						System.out.println("todayDate: "+todayDate);
						System.out.println("localTIme.getTime().equals(new Date())||localTIme.getTime().after(new Date()): "+localTIme.getTime().equals(todayDate)+localTIme.getTime().after(todayDate));*/
						if(localTIme.getTime().equals(todayDate)||localTIme.getTime().after(todayDate)){
							priceBookBean.setActive(priceBook.isActiveIndicator()==true?ControllersConstants.TRUE:ControllersConstants.FALSE);
						}else if(localTIme.getTime().before(todayDate)){
							priceBookBean.setActive(priceBook.isActiveIndicator()==true?ControllersConstants.EXPIRE:ControllersConstants.FALSE);
						}
						else{
							priceBookBean.setActive(priceBook.isActiveIndicator()==true?ControllersConstants.TRUE:ControllersConstants.FALSE);
						}
						
						priceBookBeansList.add(priceBookBean);
					}
					util.AuditTrail(request, currentUser, "PriceBookController.getAllInActivePriceBooks", 
							"User "+ currentUser.getUserEmail()+" retrived all PriceBooks successfully ",false);
					return new Response(priceBookBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.PRICEBOOK);
				} else {
					util.AuditTrail(request, currentUser, "PriceBookController.getAllInActivePriceBooks", 
							" PriceBooks are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.PRICEBOOK);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PriceBookController.getAllInActivePriceBooks",
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

