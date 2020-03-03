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

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Configuration;
import com.dowhile.InventoryDetailReport;
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
import com.dowhile.service.InventoryDetailReportService;
import com.dowhile.service.OutletService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.ControllerUtil;
import com.dowhile.util.SessionValidator;

/**
 * @author Hafiz Yameen Bashir
 *
 */
@Controller
@RequestMapping("/inventoryDetailReport")
public class InventoryDetailReportController {

	private static Logger logger = Logger.getLogger(InventoryDetailReportController.class.getName());
	@Resource
	private ServiceUtil util;
	@Resource
	private InventoryDetailReportService inventoryDetailReportService;
	@Resource
	private OutletService outletService;
	
	private Configuration configuration =null;
	
	private static List<OutletBean> outletBeans;

	@Resource
	private ConfigurationService configurationService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getInventoryDetailReportControllerData/{sessionId}/{outletName}", method = RequestMethod.POST)
	public @ResponseBody Response getInventoryDetailReportControllerData(@PathVariable("sessionId") String sessionId,
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
				Response response = getInventoryDetailReport(sessionId, outletName, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					inventoryReportBeansList = (List<InventoryReportBean>) response.data;
				}
				

				InventoryReportControllerBean inventoryReportControllerBean = new InventoryReportControllerBean();
				inventoryReportControllerBean.setInventoryReportBeansList(inventoryReportBeansList);
				inventoryReportControllerBean.setOutletBeans(outletBeans);
				inventoryReportControllerBean.setHideSalesDetails(isHeadOffice);
				util.AuditTrail(request, currentUser, "InventoryDetailReport.getInventoryDetailReportControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived getInventoryDetailReportControllerData data successfully ",false);
				return new Response(inventoryReportControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryDetailReport.getInventoryDetailReportControllerData",
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
	@RequestMapping(value = "/getInventoryDetailReport/{sessionId}/{outletName}", method = RequestMethod.POST)
	public @ResponseBody Response getInventoryDetailReport(@PathVariable("sessionId") String sessionId,
			@PathVariable("outletName") String outletName,HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			List<InventoryReportBean>  inventoryReportBeansList = new ArrayList<>();
			List<InventoryDetailReport> inventoryDetailReports = null;
			try {
				boolean isHeadOffice = false;
				if(currentUser.getOutlet().getIsHeadOffice()!=null){
					 isHeadOffice =currentUser.getOutlet().getIsHeadOffice();
					
				}

				if(currentUser.getRole().getRoleId()==1 && currentUser.getOutlet().getIsHeadOffice()!=null && currentUser.getOutlet().getIsHeadOffice().toString()=="true"){
					if(outletName==null||outletName.equalsIgnoreCase("")||outletName.equalsIgnoreCase("undefined")||outletName.equalsIgnoreCase("All Outlets")){
						inventoryDetailReports = inventoryDetailReportService.getInventoryDetailReportByCompanyId(currentUser.getCompany().getCompanyId());
					}else{
						int outletId = ControllerUtil.getOutletIdByOutletName(outletName,outletBeans);
						if(outletId==0)
							outletId = currentUser.getOutlet().getOutletId();
						//inventoryDetailReports = inventoryDetailReportService.getInventoryDetailReportByOutletIdCompanyId(outletId, currentUser.getCompany().getCompanyId());
						inventoryDetailReports = inventoryDetailReportService.getInventoryDetailReportByOutletIdCompanyId(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
					}
					
				}else{
					inventoryDetailReports = inventoryDetailReportService.getInventoryDetailReportByOutletIdCompanyId(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
				}
				if (inventoryDetailReports != null) {
					for (InventoryDetailReport inventoryDetailReport : inventoryDetailReports) {
						InventoryReportBean inventoryReportBean = new InventoryReportBean();
						if(inventoryDetailReport!=null){
							inventoryReportBean.setProductId(String.valueOf(inventoryDetailReport.getId().getId()));
							inventoryReportBean.setVarinatAttributeName(inventoryDetailReport.getId().getProductName());
							inventoryReportBean.setSupplierName(inventoryDetailReport.getId().getContactName());
							inventoryReportBean.setBrand(inventoryDetailReport.getId().getBrandName());
							inventoryReportBean.setOutletName(inventoryDetailReport.getId().getOutletName());
							inventoryReportBean.setType(inventoryDetailReport.getId().getProductType());
							inventoryReportBean.setSku(String.valueOf(inventoryDetailReport.getId().getSku()));
							inventoryReportBean.setCurrentStock(String.valueOf(inventoryDetailReport.getId().getCurrentInventory()));
							inventoryReportBean.setReorderPoint(String.valueOf(inventoryDetailReport.getId().getReorderPoint()));
							inventoryReportBean.setReorderAmount(String.valueOf(inventoryDetailReport.getId().getReorderAmount()));
							if(isHeadOffice){
								inventoryReportBean.setStockValue(String.valueOf(inventoryDetailReport.getId().getStockValue().setScale(2, RoundingMode.HALF_EVEN)));
								inventoryReportBean.setItemValue(String.valueOf(inventoryDetailReport.getId().getSupplyPriceExclTax().setScale(2, RoundingMode.HALF_EVEN)));
							}else{
								
								BigDecimal retialPriceExclTax = (inventoryDetailReport.getId().getSupplyPriceExclTax().multiply(inventoryDetailReport.getId().getMarkupPrct().divide(new BigDecimal(100)))).add(inventoryDetailReport.getId().getSupplyPriceExclTax());
								retialPriceExclTax =retialPriceExclTax.setScale(2,RoundingMode.HALF_EVEN);
								
								inventoryReportBean.setStockValue((retialPriceExclTax.multiply(new BigDecimal(inventoryDetailReport.getId().getCurrentInventory()))).toString());
								inventoryReportBean.setItemValue(retialPriceExclTax.toString());
							}
							
							inventoryReportBean.setTotalStockSent(inventoryDetailReport.getId().getTotalStockSent().toString());
							if(inventoryDetailReport.getId().getTotalStockSent().doubleValue()>0){
								BigDecimal sold = inventoryDetailReport.getId().getTotalStockSent().subtract(new BigDecimal(inventoryDetailReport.getId().getCurrentInventory()));
								inventoryReportBean.setSold(String.valueOf(sold));
							}else{
								inventoryReportBean.setSold("0");
							}
							
							inventoryReportBeansList.add(inventoryReportBean);
						}else{
							logger.info("something wrong............");
						}
						
					}
					util.AuditTrail(request, currentUser, "InventoryDetailReport.getInventoryDetailReport", 
							"User "+ currentUser.getUserEmail()+" retrived Inventory report successfully ",false);
					return new Response(inventoryReportBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "InventoryDetailReport.getInventoryDetailReport", 
							" Inventory report is not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryDetailReport.getInventoryDetailReport",
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
