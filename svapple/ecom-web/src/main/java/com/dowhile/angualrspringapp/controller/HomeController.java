package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

import com.dowhile.Configuration;
import com.dowhile.User;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.HomeControllerBean;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.DataPoints;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.SaleService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.ControllerUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/home")
public class HomeController {

	@Resource
	private SaleService saleService;
	private Configuration configuration =null;
	@Resource
	private ConfigurationService configurationService;
	@RequestMapping("/layout")
	public String getHomeControllerPartialPage(ModelMap modelMap) {
		return "home/layout";
	}
	@Resource
	private ServiceUtil util;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getDashBoard/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getDashBoard(@PathVariable("sessionId") String sessionId, @RequestBody HomeControllerBean homeControllerBean,
			HttpServletRequest request) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		HomeControllerBean controllerBean = new HomeControllerBean();
		List graphData = null;
		Date startDate = null , endDate = null;
		Calendar calendar = Calendar.getInstance();
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			controllerBean.setOutletName(currentUser.getOutlet().getOutletName());
			configuration = configurationService.getConfigurationByPropertyNameByCompanyId("HIDE_ORIGNAL_PRICE_INFO_REPORTS",currentUser.getCompany().getCompanyId());

			try {
				boolean isHeadOffice = false;
				if(currentUser.getOutlet().getIsHeadOffice()!=null){
					 isHeadOffice =currentUser.getOutlet().getIsHeadOffice();
					
				}
				if(!isHeadOffice && configuration!=null && configuration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
					controllerBean.setHideSalesDetails(true);
				}
				if(homeControllerBean.getStartDate()!=null && !homeControllerBean.getStartDate().equals("")){
					startDate = simpleDateFormat.parse(homeControllerBean.getStartDate());
					
				}else{
					calendar.setTime(new Date());
					calendar.add(Calendar.DAY_OF_MONTH, -6);
					startDate = calendar.getTime();
					
				}
				if(homeControllerBean.getLasttDate()!=null && !homeControllerBean.getLasttDate().equals("")){
					endDate = simpleDateFormat.parse(homeControllerBean.getLasttDate());
						
				}else{
					endDate = new Date();
					
				}
				graphData = saleService.getRevenue(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId(), startDate, endDate);
				Calendar revenueCalendar = (Calendar) calendar.clone();
				Calendar saleCountCalendar = (Calendar) calendar.clone();
				Calendar customerCountCalendar = (Calendar) calendar.clone();
				Calendar discountCalendar = (Calendar) calendar.clone();
				Calendar grossProfitCalendar = (Calendar) calendar.clone();
				Calendar basketSizeCalendar = (Calendar) calendar.clone();
				Calendar basketValueCalendar = (Calendar) calendar.clone();
				Calendar discountPercCalendar = (Calendar) calendar.clone();
				ControllerUtil.getDefaultData(graphData, revenueCalendar, controllerBean,currentUser,request,"revenue");
				graphData = saleService.getSalesCount(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId(), startDate, endDate);
				ControllerUtil.getDefaultData(graphData, saleCountCalendar, controllerBean,currentUser,request,"salesCount");
				
				graphData = saleService.getCustomersCount(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId(), startDate, endDate);
				ControllerUtil.getDefaultData(graphData, customerCountCalendar, controllerBean,currentUser,request,"customerCount");

				graphData = saleService.getGrossProfit(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId(), startDate, endDate);
				ControllerUtil.getDefaultData(graphData, grossProfitCalendar, controllerBean,currentUser,request,"grossProfit");
				
				graphData = saleService.getDiscounts(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId(), startDate, endDate);
				ControllerUtil.getDefaultData(graphData, discountCalendar, controllerBean,currentUser,request,"discount");
				
				graphData = saleService.getBasketSize(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId(), startDate, endDate);
				ControllerUtil.getDefaultData(graphData, basketSizeCalendar, controllerBean,currentUser,request,"basketSize");
				
				graphData = saleService.getBasketValue(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId(), startDate, endDate);
				ControllerUtil.getDefaultData(graphData, basketValueCalendar, controllerBean,currentUser,request,"basketValue");
				
				graphData = saleService.getDiscountPercent(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId(), startDate, endDate);
				ControllerUtil.getDefaultData(graphData, discountPercCalendar, controllerBean,currentUser,request,"discountPerc");
//				
				util.AuditTrail(request, currentUser, "HomeController.getDashBoard", 
						"User "+ currentUser.getUserEmail()+" dashboard loaded successfully ",false);
				return new Response(controllerBean,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				
			}
			catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "HomeController.getDashBoard",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
	

}

