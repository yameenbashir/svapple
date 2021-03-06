/**
 * 
 */
package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Configuration;
import com.dowhile.InventoryHealthCheckReport;
import com.dowhile.Outlet;
import com.dowhile.User;
import com.dowhile.beans.TableData;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.InventoryHealthCheckReportControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.InventoryHealthCheckReportBean;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.InventoryReportService;
import com.dowhile.service.OutletService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * @author Ali
 *
 */
@Controller
@RequestMapping("/inventoryHealthCheckReport")
public class InventoryHealthCheckReportController {

	@Resource
	private ServiceUtil util;
	@Resource
	private InventoryReportService inventoryReportService;
	@Resource
	private OutletService outletService;
	
//	private Configuration configuration =null;
//	private static List<OutletBean> outletBeans;

	@Resource
	private ConfigurationService configurationService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getInventoryHealthCheckReportControllerData/{sessionId}/{outletName}", method = RequestMethod.POST)
	public @ResponseBody Response getInventoryHealthCheckReportControllerData(@PathVariable("sessionId") String sessionId,
			@PathVariable("outletName") String outletName,HttpServletRequest request) {
		//List<InventoryHealthCheckReportBean>  inventoryHealthCheckReportBeansList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			List<OutletBean> outletBeans = new ArrayList<>();
			try {
				boolean isHeadOffice = false;
				if(currentUser.getOutlet().getIsHeadOffice()!=null){
					isHeadOffice =currentUser.getOutlet().getIsHeadOffice();					
				}
				//configuration = configurationService.getConfigurationByPropertyNameByCompanyId("HIDE_ORIGNAL_PRICE_INFO_REPORTS",currentUser.getCompany().getCompanyId());
				if(currentUser.getRole().getRoleId()== 1 && currentUser.getOutlet().getIsHeadOffice()!=null && currentUser.getOutlet().getIsHeadOffice().toString()=="true"){
					Response response = getOutlets(sessionId, request);
					if(response.status.equals(StatusConstants.SUCCESS)){
						outletBeans = (List<OutletBean>) response.data;
					}					
				}
				/*Response response = getInventoryReportCustom(sessionId, outletName, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					tableData = (TableData) response.data;
				}*/
				InventoryHealthCheckReportControllerBean inventoryHealthCheckReportControllerBean = new InventoryHealthCheckReportControllerBean();
				/*inventoryHealthCheckReportControllerBean.setInventoryHealthCheckReportBeansList(inventoryHealthCheckReportBeansList);*/
				inventoryHealthCheckReportControllerBean.setOutletBeans(outletBeans);
				//inventoryHealthCheckReportControllerBean.setHideSalesDetails(isHeadOffice);
				//inventoryHealthCheckReportControllerBean.setTableData(tableData);
				util.AuditTrail(request, currentUser, "InventoryHealthCheckReportController.getInventoryHealthCheckReportControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived InventoryHealthCheckReportControllerData data successfully ",false);
				return new Response(inventoryHealthCheckReportControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryReportController.getInventoryReportControllerData",
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
	@RequestMapping(value = "/getInventoryReportControllerDataLive/{sessionId}/{outletName}", method = RequestMethod.POST)
	public @ResponseBody Response getInventoryReportControllerDataLive(@PathVariable("sessionId") String sessionId,
			@PathVariable("outletName") String outletName,HttpServletRequest request) {

		List<InventoryReportBean>  inventoryReportBeansList = null;

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				boolean isHeadOffice = false;
				if(currentUser.getOutlet().getIsHeadOffice()!=null){
					 isHeadOffice =currentUser.getOutlet().getIsHeadOffice();
					
				}
				configuration = configurationService.getConfigurationByPropertyNameByCompanyId("HIDE_ORIGNAL_PRICE_INFO_REPORTS",currentUser.getCompany().getCompanyId());

				if(currentUser.getRole().getRoleId()==1 && currentUser.getOutlet().getIsHeadOffice()!=null && currentUser.getOutlet().getIsHeadOffice().toString()=="true"){
					Response response = getOutlets(sessionId, request);
					if(response.status.equals(StatusConstants.SUCCESS)){
						outletBeans = (List<OutletBean>) response.data;
					}
				}
				Response response = getInventoryReport(sessionId, outletName, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					inventoryReportBeansList = (List<InventoryReportBean>) response.data;
				}
				InventoryReportControllerBean inventoryReportControllerBean = new InventoryReportControllerBean();
				inventoryReportControllerBean.setInventoryReportBeansList(inventoryReportBeansList);
				inventoryReportControllerBean.setOutletBeans(outletBeans);
				inventoryReportControllerBean.setHideSalesDetails(isHeadOffice);
//				inventoryReportControllerBean.setTableData(tableData);
				util.AuditTrail(request, currentUser, "InventoryReportController.getInventoryReportControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived InventoryReportControllerData data successfully ",false);
				return new Response(inventoryReportControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryReportController.getInventoryReportControllerData",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}*/
	
/*	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getInventoryReport/{sessionId}/{outletName}", method = RequestMethod.POST)
	public @ResponseBody Response getInventoryReport(@PathVariable("sessionId") String sessionId,
			@PathVariable("outletName") String outletName,HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			List<InventoryReportBean>  inventoryReportBeansList = new ArrayList<>();
			List<InventoryReport> inventoryReports = null;
			try {
				boolean isHeadOffice = false;
				if(currentUser.getOutlet().getIsHeadOffice()!=null){
					 isHeadOffice =currentUser.getOutlet().getIsHeadOffice();
					
				}

				if(currentUser.getRole().getRoleId()==1 && currentUser.getOutlet().getIsHeadOffice()!=null && currentUser.getOutlet().getIsHeadOffice().toString()=="true"){
					if(outletName==null||outletName.equalsIgnoreCase("")||outletName.equalsIgnoreCase("undefined")||outletName.equalsIgnoreCase("All Outlets")){
						// All Outlet case has been disabled 
						//inventoryReports = inventoryReportService.getInventoryReportByCompanyId(currentUser.getCompany().getCompanyId());
//						TableData tableData = inventoryReportService.getInventoryReportByCompanyIdOutletId(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId());
						inventoryReports = inventoryReportService.getInventoryReportByOutletIdCompanyId(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
					}else{
						int outletId = ControllerUtil.getOutletIdByOutletName(outletName,outletBeans);
						if(outletId==0)
							outletId = currentUser.getOutlet().getOutletId();
//						TableData tableData = inventoryReportService.getInventoryReportByCompanyIdOutletId(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId());
						inventoryReports = inventoryReportService.getInventoryReportByOutletIdCompanyId(outletId, currentUser.getCompany().getCompanyId());
					}
					
				}else{
//					TableData tableData = inventoryReportService.getInventoryReportByCompanyIdOutletId(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId());
					inventoryReports = inventoryReportService.getInventoryReportByOutletIdCompanyId(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
				}
				if (inventoryReports != null) {
					for (InventoryReport inventoryReport : inventoryReports) {
						InventoryReportBean inventoryReportBean = new InventoryReportBean();
						if(inventoryReport!=null){
							inventoryReportBean.setProductId(String.valueOf(inventoryReport.getId().getId()));
							inventoryReportBean.setVarinatAttributeName(inventoryReport.getId().getProductName());
							
							inventoryReportBean.setOutletName(inventoryReport.getId().getOutletName());
							inventoryReportBean.setType(inventoryReport.getId().getProductType());
							inventoryReportBean.setSku(String.valueOf(inventoryReport.getId().getSku()));
							inventoryReportBean.setCurrentStock(String.valueOf(inventoryReport.getId().getCurrentInventory()));
							inventoryReportBean.setReorderPoint(String.valueOf(inventoryReport.getId().getReorderPoint()));
							inventoryReportBean.setReorderAmount(String.valueOf(inventoryReport.getId().getReorderAmount()));
							
							if(isHeadOffice){
								inventoryReportBean.setStockValue(String.valueOf(inventoryReport.getId().getStockValue().setScale(2, RoundingMode.HALF_EVEN)));
								inventoryReportBean.setItemValue(String.valueOf(inventoryReport.getId().getSupplyPriceExclTax().setScale(2, RoundingMode.HALF_EVEN)));
								inventoryReportBean.setSupplierName(inventoryReport.getId().getContactName());
								inventoryReportBean.setBrand(inventoryReport.getId().getBrandName());
								
							}else{
								inventoryReportBean.setSupplierName("-");
								inventoryReportBean.setBrand("-");
																
								//BigDecimal netPricee = (new BigDecimal(inventoryReport.getId().getSupplyPriceExclTax()).multiply(new BigDecimal(productBean.getMarkupPrct()).divide(new BigDecimal(100)))).add(new BigDecimal(productBean.getSupplyPriceExclTax())).setScale(5,RoundingMode.HALF_EVEN);
								//BigDecimal newNetPrices =netPricee.setScale(2,RoundingMode.HALF_EVEN);
								
								//BigDecimal retialPriceExclTax = (inventoryReport.getId().getSupplyPriceExclTax().setScale(2, RoundingMode.HALF_EVEN).multiply(inventoryReport.getId().getMarkupPrct().setScale(5, RoundingMode.HALF_EVEN).divide(new BigDecimal(100)))).add(inventoryReport.getId().getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
								//retialPriceExclTax =retialPriceExclTax.setScale(5,RoundingMode.HALF_EVEN);
								
								inventoryReportBean.setStockValue((inventoryReport.getId().getNetPrice().setScale(2, RoundingMode.HALF_EVEN).multiply(new BigDecimal(inventoryReport.getId().getCurrentInventory()))).toString());
								inventoryReportBean.setItemValue(inventoryReport.getId().getNetPrice().setScale(2, RoundingMode.HALF_EVEN).toString());
							}
							
							
							inventoryReportBeansList.add(inventoryReportBean);
						}else{
							System.out.println("something wrong............");
						}
						
					}
					util.AuditTrail(request, currentUser, "InventoryReportController.getInventoryReport", 
							"User "+ currentUser.getUserEmail()+" retrived Inventory report successfully ",false);
					return new Response(inventoryReportBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "InventoryReportController.getInventoryReport", 
							" Inventory report is not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryReportController.getInventoryReport",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}*/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getInventoryHealthCheckReportCustom/{sessionId}/{outletId}", method = RequestMethod.POST)
	public @ResponseBody Response getInventoryReportCustom(@PathVariable("sessionId") String sessionId,
			@PathVariable("outletId") String outletId,HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			List<InventoryHealthCheckReportBean> inventoryHealthCheckReportBeansList = new ArrayList<>();
			List<InventoryHealthCheckReport> inventoryHealthCheckReportList = null;
			/*String totalSt = "0";
			String totalRtw = "0";
			String totalExpCurrentInventory = "0";
			String totalConflictedQuantity = "0";
			String totalCurrentInventory = "0";
			String totalSold = "0";
			String totalSaleReturn = "0";*/
			try {
				inventoryHealthCheckReportList = inventoryReportService.getInventoryHealthCheckReportByCompanyIdOutletId(currentUser.getCompany().getCompanyId(), Integer.parseInt(outletId));
				if (inventoryHealthCheckReportList != null) {
					for (InventoryHealthCheckReport inventoryHealthCheckReport : inventoryHealthCheckReportList) {
						InventoryHealthCheckReportBean inventoryHealthCheckReportBean = new InventoryHealthCheckReportBean();
						inventoryHealthCheckReportBean.setProductName(inventoryHealthCheckReport.getPRODUCT_NAME());
						inventoryHealthCheckReportBean.setVarinatAttributeName(inventoryHealthCheckReport.getVARIANT_ATTRIBUTE_NAME());
						inventoryHealthCheckReportBean.setOutletName(inventoryHealthCheckReport.getOUTLET_NAME());
						inventoryHealthCheckReportBean.setSku(inventoryHealthCheckReport.getSKU());
						inventoryHealthCheckReportBean.setSupplierName(inventoryHealthCheckReport.getSUPPLIER());
						inventoryHealthCheckReportBean.setType(inventoryHealthCheckReport.getPRODUCT_TYPE_NAME());
						inventoryHealthCheckReportBean.setAuditQuantity(Objects.toString(inventoryHealthCheckReport.getAUDIT_QUANTITY(),""));
						inventoryHealthCheckReportBean.setStockTransferred(Objects.toString(inventoryHealthCheckReport.getSTOCK_TRANSFERRED(),""));
						inventoryHealthCheckReportBean.setStockRtw(Objects.toString(inventoryHealthCheckReport.getSTOCK_RETURN_TO_WAREHOUSE(),""));
						inventoryHealthCheckReportBean.setSale(Objects.toString(inventoryHealthCheckReport.getSALE(),""));
						inventoryHealthCheckReportBean.setSaleReturn(Objects.toString(inventoryHealthCheckReport.getSALE_RETURN(),""));
						inventoryHealthCheckReportBean.setExpCurrentInventory(Objects.toString(inventoryHealthCheckReport.getEXPECTED_CURRENT_INVENTORY(),""));
						inventoryHealthCheckReportBean.setSysCurrentInventory(Objects.toString(inventoryHealthCheckReport.getSYSTEM_CURRENT_INVENTORY(),""));
						inventoryHealthCheckReportBean.setConflictedQuantity(Objects.toString(inventoryHealthCheckReport.getCONFLICTED_QUANTITY(),""));			
						/*totalSt = inventoryHealthCheckReport.getSTOCK_TRANSFERRED().add(BigDecimal.parseBigDecimal(totalSt));
						String totalRtw = "0";
						String totalExpCurrentInventory = "0";
						String totalConflictedQuantity = "0";
						String totalCurrentInventory = "0";
						String totalSold = "0";
						String totalSaleReturn = "0";*/
						inventoryHealthCheckReportBeansList.add(inventoryHealthCheckReportBean);
						
					}
					util.AuditTrail(request, currentUser, "InventoryHealthCheckReportController.getInventoryHealthCheckReport", 
							"User "+ currentUser.getUserEmail()+" retrived Inventory health check report successfully ",false);
					return new Response(inventoryHealthCheckReportBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "InventoryHealthCheckReportController.getInventoryHealthCheckReport", 
							" Inventory Health Check report is not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryHealthCheckReportController.getInventoryReport",
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
							if(outlet.getOutletId() != currentUser.getOutlet().getOutletId()) {
								outletBeans.add(outletBean);
							}
						
						
					}
					return new Response(outletBeans,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}
				else{
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
}
