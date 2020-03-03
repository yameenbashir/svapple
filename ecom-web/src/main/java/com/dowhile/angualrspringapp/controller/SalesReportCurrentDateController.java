/**
 * 
 */
package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Configuration;
import com.dowhile.Outlet;
import com.dowhile.User;
import com.dowhile.beans.TableData;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.InventoryReportControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.OutletService;
import com.dowhile.service.TempSaleService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.ControllerUtil;
import com.dowhile.util.SessionValidator;

/**
 * @author Yameen Bashir
 *
 */
@Controller
@RequestMapping("/salesReportCurrentDate")
public class SalesReportCurrentDateController {

	private static Logger logger = Logger.getLogger(SalesReportCurrentDateController.class.getName());
	@Resource
	private ServiceUtil util;
	@Resource
	private TempSaleService tempSaleService;
	@Resource
	private OutletService outletService;
	
	private static List<OutletBean> outletBeans;

	@Resource
	private ConfigurationService configurationService;
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping(value = "/getSalesReportCurrentDateControllerData/{sessionId}/{outletName}", method = RequestMethod.POST)
	public @ResponseBody Response getSalesReportCurrentDateControllerData(@PathVariable("sessionId") String sessionId,
			@PathVariable("outletName") String outletName,HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
				try {
				boolean isHeadOffice = false;
				boolean isLocalInstance = false;
				boolean isAdminRestriction = false;
				TableData tableData = null;
				Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
				Configuration configurationLocalInstance = configurationMap.get("LOCAL_INSTANCE");
				if(configurationLocalInstance!=null && 
						!configurationLocalInstance.getPropertyValue().equalsIgnoreCase("")&& configurationLocalInstance.getPropertyValue().equalsIgnoreCase("true")){
					isLocalInstance = true;
				}
				
				Configuration configurationWarehouseAdminRestriction = configurationMap.get("WAREHOSE_ADMIN_RESTRICTION");
				if(configurationWarehouseAdminRestriction!=null && 
						!configurationWarehouseAdminRestriction.getPropertyValue().equalsIgnoreCase("")&& configurationWarehouseAdminRestriction.getPropertyValue().equalsIgnoreCase("true")){
					isAdminRestriction = true;
				}
				if(isAdminRestriction){
					if(currentUser.getRole().getRoleId()==1 && currentUser.getOutlet().getIsHeadOffice()!=null){
						 isHeadOffice =currentUser.getOutlet().getIsHeadOffice();
					}
				}else{
					if(currentUser.getOutlet().getIsHeadOffice()!=null){
						 isHeadOffice =currentUser.getOutlet().getIsHeadOffice();
					}
				}

				if(currentUser.getRole().getRoleId()==1 && currentUser.getOutlet().getIsHeadOffice()!=null && currentUser.getOutlet().getIsHeadOffice().toString()=="true"){
					if(outletName==null||outletName.equalsIgnoreCase("")||outletName.equalsIgnoreCase("undefined")||outletName.equalsIgnoreCase("All Outlets")){
						// All Outlet case has been disabled 
						 tableData = tempSaleService.getTodaySalesByCompanyIdOutletIdDate(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId(),new Date(),isHeadOffice);
					}else{
						int outletId = ControllerUtil.getOutletIdByOutletName(outletName,outletBeans);
						if(outletId==0)
							outletId = currentUser.getOutlet().getOutletId();
						 tableData = tempSaleService.getTodaySalesByCompanyIdOutletIdDate(currentUser.getCompany().getCompanyId(), outletId,new Date(),isHeadOffice);
					}
					
				}else{
					 tableData = tempSaleService.getTodaySalesByCompanyIdOutletIdDate(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId(),new Date(),isHeadOffice);
				}
				if (tableData != null) {
					Response response = getOutlets(sessionId, request);
					if(response.status.equals(StatusConstants.SUCCESS)){
						outletBeans = (List<OutletBean>) response.data;
					}
					InventoryReportControllerBean inventoryReportControllerBean = new InventoryReportControllerBean();
					inventoryReportControllerBean.setOutletBeans(outletBeans);
					inventoryReportControllerBean.setHideSalesDetails(isHeadOffice);
					inventoryReportControllerBean.setTableData(tableData);
					util.AuditTrail(request, currentUser, "SalesReportCurrentDateController.getSalesReportCurrentDateControllerData", 
							"User "+ currentUser.getUserEmail()+" retrived getSalesReportCurrentDateControllerData report successfully ",false);
					return new Response(inventoryReportControllerBean, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "SalesReportCurrentDateController.getSalesReportCurrentDateControllerData", 
							" getSalesReportCurrentDateControllerData report is not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SalesReportCurrentDateController.getSalesReportCurrentDateControllerData",
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
	@RequestMapping(value = "/getOutlets/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getOutlets(@PathVariable("sessionId") String sessionId,
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
					return new Response(outletBeans,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}
				else{
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
}
