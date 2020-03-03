/**
 * 
 */
package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Configuration;
import com.dowhile.InventoryReport;
import com.dowhile.Outlet;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.InventoryReportControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.InventoryReportBean;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.InventoryReportService;
import com.dowhile.service.OutletService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.ControllerUtil;
import com.dowhile.util.SessionValidator;

/**
 * @author Yameen Bashir
 *
 */
@Controller
@RequestMapping("/inventoryReport")
public class InventoryReportController {

	@Resource
	private ServiceUtil util;
	@Resource
	private InventoryReportService inventoryReportService;
	@Resource
	private OutletService outletService;
	
	private Configuration configuration =null;
	private static List<OutletBean> outletBeans;

	@Resource
	private ConfigurationService configurationService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getInventoryReportControllerData/{sessionId}/{outletName}", method = RequestMethod.POST)
	public @ResponseBody Response getInventoryReportControllerData(@PathVariable("sessionId") String sessionId,
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
				util.AuditTrail(request, currentUser, "InventoryReportController.getInventoryReportControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived InventoryReportControllerData data successfully ",false);
				return new Response(inventoryReportControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
						inventoryReports = inventoryReportService.getInventoryReportByOutletIdCompanyId(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
					}else{
						int outletId = ControllerUtil.getOutletIdByOutletName(outletName,outletBeans);
						if(outletId==0)
							outletId = currentUser.getOutlet().getOutletId();
						inventoryReports = inventoryReportService.getInventoryReportByOutletIdCompanyId(outletId, currentUser.getCompany().getCompanyId());
					}
					
				}else{
					inventoryReports = inventoryReportService.getInventoryReportByOutletIdCompanyId(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
				}
				if (inventoryReports != null) {
					for (InventoryReport inventoryReport : inventoryReports) {
						InventoryReportBean inventoryReportBean = new InventoryReportBean();
						if(inventoryReport!=null){
							inventoryReportBean.setProductId(String.valueOf(inventoryReport.getId().getId()));
							inventoryReportBean.setVarinatAttributeName(inventoryReport.getId().getProductName());
							inventoryReportBean.setSupplierName(inventoryReport.getId().getContactName());
							inventoryReportBean.setBrand(inventoryReport.getId().getBrandName());
							inventoryReportBean.setOutletName(inventoryReport.getId().getOutletName());
							inventoryReportBean.setType(inventoryReport.getId().getProductType());
							inventoryReportBean.setSku(String.valueOf(inventoryReport.getId().getSku()));
							inventoryReportBean.setCurrentStock(String.valueOf(inventoryReport.getId().getCurrentInventory()));
							inventoryReportBean.setReorderPoint(String.valueOf(inventoryReport.getId().getReorderPoint()));
							inventoryReportBean.setReorderAmount(String.valueOf(inventoryReport.getId().getReorderAmount()));
							if(isHeadOffice){
								inventoryReportBean.setStockValue(String.valueOf(inventoryReport.getId().getStockValue().setScale(2, RoundingMode.HALF_EVEN)));
								inventoryReportBean.setItemValue(String.valueOf(inventoryReport.getId().getSupplyPriceExclTax().setScale(2, RoundingMode.HALF_EVEN)));
							}else{
								
								BigDecimal retialPriceExclTax = (inventoryReport.getId().getSupplyPriceExclTax().multiply(inventoryReport.getId().getMarkupPrct().divide(new BigDecimal(100)))).add(inventoryReport.getId().getSupplyPriceExclTax());
								retialPriceExclTax =retialPriceExclTax.setScale(2,RoundingMode.HALF_EVEN);
								
								inventoryReportBean.setStockValue((retialPriceExclTax.multiply(new BigDecimal(inventoryReport.getId().getCurrentInventory()))).toString());
								inventoryReportBean.setItemValue(retialPriceExclTax.toString());
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
				e.printStackTrace();logger.error(e.getMessage(),e);
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
