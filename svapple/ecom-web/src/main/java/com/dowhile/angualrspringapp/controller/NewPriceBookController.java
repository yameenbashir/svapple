package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
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

import com.dowhile.ContactGroup;
import com.dowhile.Outlet;
import com.dowhile.PriceBook;
import com.dowhile.User;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.NewPriceBookControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.CustomerGroupBean;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.PriceBookBean;
import com.dowhile.service.ContactGroupService;
import com.dowhile.service.OutletService;
import com.dowhile.service.PriceBookService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * Yameen Bashir
 */
@Controller
@RequestMapping("/newPriceBook")
public class NewPriceBookController {

	@Resource
	private ContactGroupService customerGroupService;
	@Resource
	private ServiceUtil util;
	@Resource
	private OutletService outletService;
	@Resource
	private PriceBookService priceBookService;

	@RequestMapping("/layout")
	public String getNewPriceBookControllerPartialPage(ModelMap modelMap) {
		return "newPriceBook/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getNewPriceBookControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getNewPriceBookControllerData(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<OutletBean> outletBeans = null;
		List<CustomerGroupBean> customerGroupBeansList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {

				Response response = getOutletsForDropDown(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					outletBeans = (List<OutletBean>) response.data;
				}
				response = getAllCustomerGroups(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					customerGroupBeansList = (List<CustomerGroupBean>) response.data;
				}

				NewPriceBookControllerBean newPriceBookControllerBean = new NewPriceBookControllerBean();
				newPriceBookControllerBean.setCustomerGroupBeansList(customerGroupBeansList);
				newPriceBookControllerBean.setOutletBeans(outletBeans);

				util.AuditTrail(request, currentUser, "NewPriceBookController.getNewPriceBookControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived NewPriceBookControllerData successfully ",false);
				return new Response(newPriceBookControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NewPriceBookController.getNewPriceBookControllerData",
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
	@RequestMapping(value = "/getOutletsForDropDown/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getOutletsForDropDown(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<OutletBean> outletBeans = new ArrayList<>();
		List<Outlet> outlets = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				outlets = outletService.getOutlets(currentUser.getCompany().getCompanyId());
				if(outlets!=null){
					for(Outlet outlet:outlets){
						OutletBean outletBean = new OutletBean();
						outletBean.setOutletId(outlet.getOutletId().toString());
						outletBean.setOutletName(outlet.getOutletName());

						outletBeans.add(outletBean);
					}
					util.AuditTrail(request, currentUser, "NewPriceBookController.getOutletsForDropDown", 
							"User "+ currentUser.getUserEmail()+" retrived all outlets successfully ",false);
					return new Response(outletBeans,StatusConstants.SUCCESS,LayOutPageConstants.NEW_PRICEBOOK);
				}else{
					util.AuditTrail(request, currentUser, "NewPriceBookController.getOutletsForDropDown", "User "+ 
							currentUser.getUserEmail()+" Unable to retrived all outlets",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NewPriceBookController.getOutletsForDropDown",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllCustomerGroups/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllCustomerGroups(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<CustomerGroupBean> customerGroupBeansList = new ArrayList<>();
		List<ContactGroup> customerGroupList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				customerGroupList = customerGroupService.GetAllContactGroup(currentUser.getCompany().getCompanyId());
				if (customerGroupList != null) {
					for (ContactGroup customerGroup : customerGroupList) {
						CustomerGroupBean customerGroupBean = new CustomerGroupBean();
						customerGroupBean.setCustomerGroupId(customerGroup.getContactGroupId().toString());
						customerGroupBean.setCustomerGroupName(customerGroup.getContactGroupName().toString());
						customerGroupBeansList.add(customerGroupBean);
					}
					util.AuditTrail(request, currentUser, "NewPriceBookController.getAllCustomerGroups", 
							"User "+ currentUser.getUserEmail()+" retrived all Customer Groups successfully ",false);
					return new Response(customerGroupBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "NewPriceBookController.getAllCustomerGroups", 
							" Customer Groups are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NewPriceBookController.getAllCustomerGroups",
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
	@RequestMapping(value = "/addPriceBook/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response addPriceBook(@PathVariable("sessionId") String sessionId,
			@RequestBody PriceBookBean priceBookBean,HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				int outletId = !priceBookBean.getOutletId().equalsIgnoreCase("-1") && priceBookBean.getOutletId().equalsIgnoreCase("") ?Integer.valueOf(priceBookBean.getOutletId()): currentUser.getOutlet().getOutletId();
				
				List<PriceBook> priceBookList= priceBookService.getPriceBooksByDateRangeCompanyIdOutletIdGroupId(DateTimeUtil.convertGuiDateFormatYYYYMMDDToDBDateFormat(priceBookBean.getValidFrom()),
						DateTimeUtil.convertGuiDateFormatYYYYMMDDToDBDateFormat(priceBookBean.getValidTo()), currentUser.getCompany().getCompanyId(), outletId, Integer.valueOf(priceBookBean.getContactGroupId()));
				if(priceBookList==null){
					PriceBook priceBook = new PriceBook();
					
					priceBook.setCompany(currentUser.getCompany());
					ContactGroup contactGroup = customerGroupService.getContactGroupByContactGroupId(Integer.valueOf(priceBookBean.getContactGroupId()), currentUser.getCompany().getCompanyId());
					priceBook.setContactGroup(contactGroup);
					priceBook.setUserByCreatedBy(currentUser);
					priceBook.setCreatedDate(new Date());
					if(priceBookBean.getIsValidOnStore().equalsIgnoreCase(ControllersConstants.TRUE)){
						priceBook.setIsValidOnStore(true);
						priceBook.setIsValidOnEcom(false);
					}else{
						priceBook.setIsValidOnStore(false);
						priceBook.setIsValidOnEcom(true);
					}
					if(priceBookBean.getActive()!=null && priceBookBean.getActive().equalsIgnoreCase(ControllersConstants.TRUE)){
						priceBook.setActiveIndicator(true);
					}else{
						priceBook.setActiveIndicator(false);
					}
					priceBook.setLastUpdated(new Date());
					if(priceBookBean.getOutletId()!=null && !priceBookBean.getOutletId().equalsIgnoreCase("-1")){
						Outlet outlet = outletService.getOuletByOutletId(Integer.valueOf(priceBookBean.getOutletId()), currentUser.getCompany().getCompanyId());
						priceBook.setOutlet(outlet);
					}
					priceBook.setPriceBookName(priceBookBean.getPriceBookName());
					priceBook.setUserByUpdatedBy(currentUser);
					priceBook.setValidFrom(DateTimeUtil.convertGuiDateFormatYYYYMMDDToDBDateFormat(priceBookBean.getValidFrom()));
					priceBook.setValidTo(DateTimeUtil.convertGuiDateFormatYYYYMMDDToDBDateFormat(priceBookBean.getValidTo()));
					if(priceBookBean.getFlatDiscount()!=null && !priceBookBean.getFlatDiscount().equalsIgnoreCase("")){
						priceBook.setFlatDiscount(new BigDecimal(priceBookBean.getFlatDiscount()));
					}else{
						priceBook.setFlatDiscount(new BigDecimal(0));
					}
					if(priceBookBean.getFlatSale().equalsIgnoreCase("true")){
						priceBook.setFlatSale("true");
					}else{
						priceBook.setFlatSale("false");
					}
					
					PriceBook prceBook = priceBookService.addPriceBook(priceBook);
					util.AuditTrail(request, currentUser, "NewPriceBookController.addPriceBook", 
							"User "+ currentUser.getUserEmail()+" added PriceBook successfully ",false);
					return new Response(prceBook.getPriceBookId()+"", StatusConstants.SUCCESS,prceBook.getFlatSale());
				}else{
					util.AuditTrail(request, currentUser, "NewPriceBookController.addPriceBook", 
							"User "+ currentUser.getUserEmail()+" unable to add price book because pricebook already exist with date range. ",false);
					return new Response(MessageConstants.PRICEBOOK_ALREADY_EXIST, StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
				}
				

				
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NewPriceBookController.addPriceBook",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}


}

