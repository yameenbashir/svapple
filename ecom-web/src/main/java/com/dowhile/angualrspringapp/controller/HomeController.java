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

import org.apache.log4j.Logger;
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

	private static Logger logger = Logger.getLogger(HomeController.class.getName());
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
			Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
			configuration = configurationMap.get("HIDE_ORIGNAL_PRICE_INFO_REPORTS");
			setHomeControllerBeanGraphProperties(controllerBean, configurationMap,currentUser);
			//configuration = configurationService.getConfigurationByPropertyNameByCompanyId("HIDE_ORIGNAL_PRICE_INFO_REPORTS",currentUser.getCompany().getCompanyId());

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
				e.printStackTrace();logger.error(e.getMessage(),e);
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
	
	public void setHomeControllerBeanGraphProperties(HomeControllerBean controllerBean,Map<String ,Configuration> configurationMap,User currentUser){
		try{
			Configuration configurationRevenue = configurationMap.get("HOME_REVENUE");
			if(configurationRevenue!=null && !configurationRevenue.getPropertyValue().equalsIgnoreCase("")){
				String roleList[] = configurationRevenue.getPropertyValue().split(",");
				//Admin
				if(currentUser.getRole().getRoleId()==1){
					if(roleList[0].equalsIgnoreCase("1")){
						controllerBean.setRevenue(true);
					}else{
						controllerBean.setRevenue(false);
					}
				}//Manager
				else if(currentUser.getRole().getRoleId()==2){
					if(roleList[1].equalsIgnoreCase("1")){
						controllerBean.setRevenue(true);
					}else{
						controllerBean.setRevenue(false);
					}
				}//Cashier
				else {
					if(roleList[2].equalsIgnoreCase("1")){
						controllerBean.setRevenue(true);
					}else{
						controllerBean.setRevenue(false);
					}
				}
			}else{
				controllerBean.setRevenue(true);
			}
			Configuration configurationSaleCount = configurationMap.get("HOME_SALE_COUNT");
			if(configurationSaleCount!=null && !configurationSaleCount.getPropertyValue().equalsIgnoreCase("")){
				String roleList[] = configurationSaleCount.getPropertyValue().split(",");
				//Admin
				if(currentUser.getRole().getRoleId()==1){
					if(roleList[0].equalsIgnoreCase("1")){
						controllerBean.setSaleCount(true);
					}else{
						controllerBean.setSaleCount(false);
					}
				}//Manager
				else if(currentUser.getRole().getRoleId()==2){
					if(roleList[1].equalsIgnoreCase("1")){
						controllerBean.setSaleCount(true);
					}else{
						controllerBean.setSaleCount(false);
					}
				}//Cashier
				else {
					if(roleList[2].equalsIgnoreCase("1")){
						controllerBean.setSaleCount(true);
					}else{
						controllerBean.setSaleCount(false);
					}
				}
			}else{
				controllerBean.setSaleCount(true);
			}
			Configuration configurationCustomerCount = configurationMap.get("HOME_CUSTOMER_COUNT");
			if(configurationCustomerCount!=null && !configurationCustomerCount.getPropertyValue().equalsIgnoreCase("")){
				String roleList[] = configurationCustomerCount.getPropertyValue().split(",");
				//Admin
				if(currentUser.getRole().getRoleId()==1){
					if(roleList[0].equalsIgnoreCase("1")){
						controllerBean.setCustomerCount(true);
					}else{
						controllerBean.setCustomerCount(false);
					}
				}//Manager
				else if(currentUser.getRole().getRoleId()==2){
					if(roleList[1].equalsIgnoreCase("1")){
						controllerBean.setCustomerCount(true);
					}else{
						controllerBean.setCustomerCount(false);
					}
				}//Cashier
				else {
					if(roleList[2].equalsIgnoreCase("1")){
						controllerBean.setCustomerCount(true);
					}else{
						controllerBean.setCustomerCount(false);
					}
				}
			}else{
				controllerBean.setCustomerCount(true);
			}
			Configuration configurationGross = configurationMap.get("HOME_GROSS");
			if(configurationGross!=null && !configurationGross.getPropertyValue().equalsIgnoreCase("")){
				String roleList[] = configurationGross.getPropertyValue().split(",");
				//Admin
				if(currentUser.getRole().getRoleId()==1){
					if(roleList[0].equalsIgnoreCase("1")){
						controllerBean.setGrossProfit(true);
					}else{
						controllerBean.setGrossProfit(false);
					}
				}//Manager
				else if(currentUser.getRole().getRoleId()==2){
					if(roleList[1].equalsIgnoreCase("1")){
						controllerBean.setGrossProfit(true);
					}else{
						controllerBean.setGrossProfit(false);
					}
				}//Cashier
				else {
					if(roleList[2].equalsIgnoreCase("1")){
						controllerBean.setGrossProfit(true);
					}else{
						controllerBean.setGrossProfit(false);
					}
				}
			}else{
				controllerBean.setGrossProfit(true);
			}
			Configuration configurationHomeDiscount = configurationMap.get("HOME_DISCOUNT");
			if(configurationHomeDiscount!=null && !configurationHomeDiscount.getPropertyValue().equalsIgnoreCase("")){
				String roleList[] = configurationHomeDiscount.getPropertyValue().split(",");
				//Admin
				if(currentUser.getRole().getRoleId()==1){
					if(roleList[0].equalsIgnoreCase("1")){
						controllerBean.setDiscount(true);
					}else{
						controllerBean.setDiscount(false);
					}
				}//Manager
				else if(currentUser.getRole().getRoleId()==2){
					if(roleList[1].equalsIgnoreCase("1")){
						controllerBean.setDiscount(true);
					}else{
						controllerBean.setDiscount(false);
					}
				}//Cashier
				else {
					if(roleList[2].equalsIgnoreCase("1")){
						controllerBean.setDiscount(true);
					}else{
						controllerBean.setDiscount(false);
					}
				}
			}else{
				controllerBean.setDiscount(true);
			}
			Configuration configurationHomeDiscountPercent = configurationMap.get("HOME_DISCOUNT_PERCENT");
			if(configurationHomeDiscountPercent!=null && !configurationHomeDiscountPercent.getPropertyValue().equalsIgnoreCase("")){
				String roleList[] = configurationHomeDiscountPercent.getPropertyValue().split(",");
				//Admin
				if(currentUser.getRole().getRoleId()==1){
					if(roleList[0].equalsIgnoreCase("1")){
						controllerBean.setDiscountPercent(true);
					}else{
						controllerBean.setDiscountPercent(false);
					}
				}//Manager
				else if(currentUser.getRole().getRoleId()==2){
					if(roleList[1].equalsIgnoreCase("1")){
						controllerBean.setDiscountPercent(true);
					}else{
						controllerBean.setDiscountPercent(false);
					}
				}//Cashier
				else {
					if(roleList[2].equalsIgnoreCase("1")){
						controllerBean.setDiscountPercent(true);
					}else{
						controllerBean.setDiscountPercent(false);
					}
				}
			}else{
				controllerBean.setDiscountPercent(true);
			}
			Configuration configurationHomeBasketValue = configurationMap.get("HOME_BASKET_VALUE");
			if(configurationHomeBasketValue!=null && !configurationHomeBasketValue.getPropertyValue().equalsIgnoreCase("")){
				String roleList[] = configurationHomeBasketValue.getPropertyValue().split(",");
				//Admin
				if(currentUser.getRole().getRoleId()==1){
					if(roleList[0].equalsIgnoreCase("1")){
						controllerBean.setBasketValue(true);
					}else{
						controllerBean.setBasketValue(false);
					}
				}//Manager
				else if(currentUser.getRole().getRoleId()==2){
					if(roleList[1].equalsIgnoreCase("1")){
						controllerBean.setBasketValue(true);
					}else{
						controllerBean.setBasketValue(false);
					}
				}//Cashier
				else {
					if(roleList[2].equalsIgnoreCase("1")){
						controllerBean.setBasketValue(true);
					}else{
						controllerBean.setBasketValue(false);
					}
				}
			}else{
				controllerBean.setBasketValue(true);
			}
			Configuration configurationHomeBasketSize = configurationMap.get("HOME_BASKET_SIZE");
			if(configurationHomeBasketSize!=null && !configurationHomeBasketSize.getPropertyValue().equalsIgnoreCase("")){
				String roleList[] = configurationHomeBasketSize.getPropertyValue().split(",");
				//Admin
				if(currentUser.getRole().getRoleId()==1){
					if(roleList[0].equalsIgnoreCase("1")){
						controllerBean.setBasketSize(true);
					}else{
						controllerBean.setBasketSize(false);
					}
				}//Manager
				else if(currentUser.getRole().getRoleId()==2){
					if(roleList[1].equalsIgnoreCase("1")){
						controllerBean.setBasketSize(true);
					}else{
						controllerBean.setBasketSize(false);
					}
				}//Cashier
				else {
					if(roleList[2].equalsIgnoreCase("1")){
						controllerBean.setBasketSize(true);
					}else{
						controllerBean.setBasketSize(false);
					}
				}
			}else{
				controllerBean.setBasketSize(true);
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
	}
	
	

}

