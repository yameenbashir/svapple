/**
 * 
 */
package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.dowhile.beans.ReportParams;
import com.dowhile.beans.TableData;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.ReportControllerBean;
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
@RequestMapping("/cashSalesReport")
public class CashSalesReportController {

	private static Logger logger = Logger.getLogger(CashSalesReportController.class.getName());
	@Resource
	private ServiceUtil util;
	@Resource
	private TempSaleService tempSaleService;
	@Resource
	private OutletService outletService;
	private Configuration configuration =null;
	
	private static List<OutletBean> outletBeans;

	@Resource
	private ConfigurationService configurationService;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getCashSalesReportByDateRange/{sessionId}/{startDate}/{endDate}/{reportType}/{reportDateType}/{outletName}/{isLiveData}", method = RequestMethod.POST)
	public @ResponseBody Response getCashSalesReportByDateRange(@PathVariable("sessionId") String sessionId,
			@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate,
			@PathVariable("reportType") String reportType,@PathVariable("reportDateType") String reportDateType,
			@PathVariable("outletName") String outletName,@PathVariable("isLiveData") String isLiveData,HttpServletRequest request) {


		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				boolean completeReport = false;
				boolean isHeadOffice = false;
				boolean isLocalInstance = false;
				boolean isAdminRestriction = false;
				StringBuilder tableName = null;
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
				
				if(isLiveData!=null && !isLiveData.equalsIgnoreCase("") && isLiveData.equalsIgnoreCase("true")||isLocalInstance){
					tableName = new StringBuilder("cash_sale");
				}else{
					tableName = new StringBuilder("mv_cash_sale");
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
				
				configuration = configurationMap.get("HIDE_ORIGNAL_PRICE_INFO_REPORTS");
				//configuration = configurationService.getConfigurationByPropertyNameByCompanyId("HIDE_ORIGNAL_PRICE_INFO_REPORTS",currentUser.getCompany().getCompanyId());

				int outletId = 0;
				if(currentUser.getRole().getRoleId()==1 && currentUser.getOutlet().getIsHeadOffice()!=null && currentUser.getOutlet().getIsHeadOffice().toString()=="true"){
					Response response = getOutlets(sessionId, request);
					if(response.status.equals(StatusConstants.SUCCESS)){
						outletBeans = (List<OutletBean>) response.data;
					}
					if(outletName==null||outletName.equalsIgnoreCase("")||outletName.equalsIgnoreCase("undefined")||outletName.equalsIgnoreCase("All Outlets")){
						completeReport = true;
					}else{
						outletId = ControllerUtil.getOutletIdByOutletName(outletName,outletBeans);
						if(outletId==0)
							outletId = currentUser.getOutlet().getOutletId();
					}
				}else{
					outletId = currentUser.getOutlet().getOutletId();
				}

				SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
				Date startDat =  null;
				Date endDat =  null;
				DateFormat parser = new SimpleDateFormat("MMM dd, yyyy");
				if(reportType.equalsIgnoreCase("undefined")){
					reportType = "Revenue";
				}
				if(startDate.equalsIgnoreCase("undefined") || endDate.equalsIgnoreCase("undefined")
						|| startDate.equals("") || endDate.equals("") ){
					Date today = new Date();
					Calendar calendar =  Calendar.getInstance();
					calendar.setTime(today);
					calendar.add(Calendar.DAY_OF_MONTH, -7);
					endDat = today;
					startDat  = calendar.getTime();
				}else{
					startDat = (Date) parser.parse(startDate.trim());
					endDat = (Date) parser.parse(endDate);
				}
				TableData tableData = null;
				if(reportType.equals("Revenue")){
					ReportParams reportParams = new ReportParams();
					if(!isHeadOffice && configuration!=null && configuration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
						reportParams.setBaseColumn("sum(Revenue) as Revenue,Tax");
						reportParams.setPrintColumns( reportType+","+ "Tax");
					}else{
						reportParams.setBaseColumn("sum(Revenue) as Revenue,sum(Cost_of_Goods) as Cost,sum(Gross_Profit) as Profit,avg(Margin),Tax");
						reportParams.setPrintColumns( reportType+","+ "Cost of Goods,Gross Profit,Margin,Tax");
					}
					reportParams.setCompanyId(currentUser.getCompany().getCompanyId());
					reportParams.setEndDate(endDat);
					reportParams.setGroupBy("Product");
					reportParams.setMainBaseColumn("Product");
					reportParams.setOrderBy("");
					reportParams.setPivotColumn( "CREATED_DATE");
					reportParams.setReportDateType(reportDateType);
					reportParams.setStartDate(startDat);
					reportParams.setTableName(tableName.toString());
					reportParams.setTallyColumn(reportType);
					if(completeReport){
						reportParams.setWhereClause("where CREATED_DATE BETWEEN '"+dt1.format(startDat)+"' and '"+dt1.format(endDat)+"'" + " AND COMPANY_ASSOCIATION_ID = "+currentUser.getCompany().getCompanyId());
					}else{
						reportParams.setWhereClause("where CREATED_DATE BETWEEN '"+dt1.format(startDat)+"' and '"+dt1.format(endDat)+"'"+" AND OUTLET_ASSOCICATION_ID ='"+outletId+"'" + " AND COMPANY_ASSOCIATION_ID = "+currentUser.getCompany().getCompanyId());
					}

					tableData = tempSaleService.getAllTempSaleByCompanyId(reportParams);

				}else if(reportType.equals("Cost of Goods")){
					ReportParams reportParams = new ReportParams();
					reportParams.setBaseColumn("sum(Cost_of_Goods) as Cost,sum(Revenue) as Revenue,sum(Gross_Profit) as Profit,avg(Margin),Tax");
					reportParams.setCompanyId(currentUser.getCompany().getCompanyId());
					reportParams.setEndDate(endDat);
					reportParams.setGroupBy("Product");
					reportParams.setMainBaseColumn("Product");
					reportParams.setOrderBy("");
					reportParams.setPivotColumn( "CREATED_DATE");
					reportParams.setPrintColumns( reportType+","+"Revenue,Gross Profit,Margin,Tax");
					reportParams.setReportDateType(reportDateType);
					reportParams.setStartDate(startDat);
					reportParams.setTableName(tableName.toString());
					reportParams.setTallyColumn("Cost_of_Goods");
					if(completeReport){
						reportParams.setWhereClause("where CREATED_DATE BETWEEN '"+dt1.format(startDat)+"' and '"+dt1.format(endDat)+"'"+ " AND COMPANY_ASSOCIATION_ID = "+currentUser.getCompany().getCompanyId());
					}else{
						reportParams.setWhereClause("where CREATED_DATE BETWEEN '"+dt1.format(startDat)+"' and '"+dt1.format(endDat)+"'"+" AND OUTLET_ASSOCICATION_ID ='"+outletId+"'" + " AND COMPANY_ASSOCIATION_ID = "+currentUser.getCompany().getCompanyId());
					}
					tableData = tempSaleService.getAllTempSaleByCompanyId(reportParams);

				}else if(reportType.equals("Gross Profit")){
					ReportParams reportParams = new ReportParams();
					reportParams.setBaseColumn("sum(Gross_Profit) as Profit,sum(Revenue) as Revenue,sum(Cost_of_Goods) as Cost,avg(Margin),Tax");
					reportParams.setCompanyId(currentUser.getCompany().getCompanyId());
					reportParams.setEndDate(endDat);
					reportParams.setGroupBy("Product");
					reportParams.setMainBaseColumn("Product");
					reportParams.setOrderBy("");
					reportParams.setPivotColumn( "CREATED_DATE");
					reportParams.setPrintColumns( reportType+","+ "Revenue,Cost of Goods,Margin,Tax");
					reportParams.setReportDateType(reportDateType);
					reportParams.setStartDate(startDat);
					reportParams.setTableName(tableName.toString());
					reportParams.setTallyColumn("Gross_Profit");
					if(completeReport){
						reportParams.setWhereClause("where CREATED_DATE BETWEEN '"+dt1.format(startDat)+"' and '"+dt1.format(endDat)+"'"+ " AND COMPANY_ASSOCIATION_ID = "+currentUser.getCompany().getCompanyId());
					}else{
						reportParams.setWhereClause("where CREATED_DATE BETWEEN '"+dt1.format(startDat)+"' and '"+dt1.format(endDat)+"'"+" AND OUTLET_ASSOCICATION_ID ='"+outletId+"'" + " AND COMPANY_ASSOCIATION_ID = "+currentUser.getCompany().getCompanyId());
					}
					tableData = tempSaleService.getAllTempSaleByCompanyId(reportParams);

				}else if(reportType.equals("Items Sold")){
					ReportParams reportParams = new ReportParams();
					if(!isHeadOffice && configuration!=null && configuration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
						reportParams.setBaseColumn("sum(Items_Sold) as Items_Sold,Tax");
						reportParams.setPrintColumns( reportType+","+ "Tax");
					}else{
						reportParams.setBaseColumn("sum(Items_Sold),sum(Revenue) as Revenue,sum(Cost_of_Goods) as Cost,sum(Gross_Profit) as Profit,avg(Margin),Tax");
						reportParams.setPrintColumns( reportType+","+ "Cost of Goods,Gross Profit,Margin,Tax");
					}
					//reportParams.setBaseColumn("sum(Items_Sold),sum(Revenue) as Revenue,sum(Cost_of_Goods) as Cost,sum(Gross_Profit) as Profit,avg(Margin),Tax");
					reportParams.setCompanyId(currentUser.getCompany().getCompanyId());
					reportParams.setEndDate(endDat);
					reportParams.setGroupBy("Product");
					reportParams.setMainBaseColumn("Product");
					reportParams.setOrderBy("");
					reportParams.setPivotColumn( "CREATED_DATE");
					//reportParams.setPrintColumns( reportType+","+ "Revenue,Cost of Goods,Gross Profit,Margin,Tax");
					reportParams.setReportDateType(reportDateType);
					reportParams.setStartDate(startDat);
					reportParams.setTableName(tableName.toString());
					reportParams.setTallyColumn("Items_Sold");
					if(completeReport){
						reportParams.setWhereClause("where CREATED_DATE BETWEEN '"+dt1.format(startDat)+"' and '"+dt1.format(endDat)+"'"+ " AND COMPANY_ASSOCIATION_ID = "+currentUser.getCompany().getCompanyId());
					}else{
						reportParams.setWhereClause("where CREATED_DATE BETWEEN '"+dt1.format(startDat)+"' and '"+dt1.format(endDat)+"'"+" AND OUTLET_ASSOCICATION_ID ='"+outletId+"'" + " AND COMPANY_ASSOCIATION_ID = "+currentUser.getCompany().getCompanyId());
					}
					tableData = tempSaleService.getAllTempSaleByCompanyId(reportParams);

				}else if(reportType.equals("Margin")){
					ReportParams reportParams = new ReportParams();
					reportParams.setBaseColumn("avg(Margin),sum(Revenue) as Revenue,sum(Cost_of_Goods) as Cost,sum(Gross_Profit) as Profit,Tax");
					reportParams.setCompanyId(currentUser.getCompany().getCompanyId());
					reportParams.setEndDate(endDat);
					reportParams.setGroupBy("Product");
					reportParams.setMainBaseColumn("Product");
					reportParams.setOrderBy("");
					reportParams.setPivotColumn( "CREATED_DATE");
					reportParams.setPrintColumns( reportType+","+ "Revenue,Cost of Goods,Gross Profit,Tax");
					reportParams.setReportDateType(reportDateType);
					reportParams.setStartDate(startDat);
					reportParams.setTableName(tableName.toString());
					reportParams.setTallyColumn("Margin");
					if(completeReport){
						reportParams.setWhereClause("where CREATED_DATE BETWEEN '"+dt1.format(startDat)+"' and '"+dt1.format(endDat)+"'"+ " AND COMPANY_ASSOCIATION_ID = "+currentUser.getCompany().getCompanyId());
					}else{
						reportParams.setWhereClause("where CREATED_DATE BETWEEN '"+dt1.format(startDat)+"' and '"+dt1.format(endDat)+"'"+" AND OUTLET_ASSOCICATION_ID ='"+outletId+"'" + " AND COMPANY_ASSOCIATION_ID = "+currentUser.getCompany().getCompanyId());
					}
					tableData = tempSaleService.getAllTempSaleByCompanyId(reportParams);		
				}else if(reportType.equals("Revenue (tax incl)")){
					ReportParams reportParams = new ReportParams();
					if(!isHeadOffice && configuration!=null && configuration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
						reportParams.setBaseColumn("sum(Revenue_tax_incl) as Revenue_Tax_Incl,sum(Revenue) as Revenue,Tax");
						reportParams.setPrintColumns( reportType+","+ "Revenue,Tax");
						
					}else{
						reportParams.setBaseColumn("sum(Revenue_tax_incl) as Revenue_Tax_Incl,sum(Revenue) as Revenue,sum(Cost_of_Goods) as Cost,sum(Gross_Profit) as Profit,avg(Margin),Tax");
						reportParams.setPrintColumns( reportType+","+ "Revenue,Cost of Goods,Gross Profit,Margin,Tax");
						
					}
					reportParams.setCompanyId(currentUser.getCompany().getCompanyId());
					reportParams.setEndDate(endDat);
					reportParams.setGroupBy("Product");
					reportParams.setMainBaseColumn("Product");
					reportParams.setOrderBy("");
					reportParams.setPivotColumn( "CREATED_DATE");
					reportParams.setReportDateType(reportDateType);
					reportParams.setStartDate(startDat);
					reportParams.setTableName(tableName.toString());
					reportParams.setTallyColumn("Revenue_tax_incl");
					if(completeReport){
						reportParams.setWhereClause("where CREATED_DATE BETWEEN '"+dt1.format(startDat)+"' and '"+dt1.format(endDat)+"'"+ " AND COMPANY_ASSOCIATION_ID = "+currentUser.getCompany().getCompanyId());
					}else{
						reportParams.setWhereClause("where CREATED_DATE BETWEEN '"+dt1.format(startDat)+"' and '"+dt1.format(endDat)+"'"+" AND OUTLET_ASSOCICATION_ID ='"+outletId+"'" + " AND COMPANY_ASSOCIATION_ID = "+currentUser.getCompany().getCompanyId());
					}
					tableData = tempSaleService.getAllTempSaleByCompanyId(reportParams);	
				}else if(reportType.equals("First Sale")){
					ReportParams reportParams = new ReportParams();
					if(!isHeadOffice && configuration!=null && configuration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
						reportParams.setBaseColumn("min(CREATED_DATE) as First,sum(Revenue) as Revenue,Tax");
						reportParams.setPrintColumns( reportType+","+ "Revenue,Tax");
							
					}else{
						reportParams.setBaseColumn("min(CREATED_DATE) as First,sum(Revenue) as Revenue,sum(Cost_of_Goods) as Cost,sum(Gross_Profit) as Profit,avg(Margin),Tax");
						reportParams.setPrintColumns( reportType+","+ "Revenue,Cost of Goods,Gross Profit,Margin,Tax");
						
					}
					reportParams.setCompanyId(currentUser.getCompany().getCompanyId());
					reportParams.setEndDate(endDat);
					reportParams.setGroupBy("Product");
					reportParams.setMainBaseColumn("Product");
					reportParams.setOrderBy("");
					reportParams.setPivotColumn( "CREATED_DATE");
					reportParams.setReportDateType(reportDateType);
					reportParams.setStartDate(startDat);
					reportParams.setTableName(tableName.toString());
					reportParams.setTallyColumn("Revenue");
					if(completeReport){
						reportParams.setWhereClause("where CREATED_DATE BETWEEN '"+dt1.format(startDat)+"' and '"+dt1.format(endDat)+"'"+ " AND COMPANY_ASSOCIATION_ID = "+currentUser.getCompany().getCompanyId());
					}else{
						reportParams.setWhereClause("where CREATED_DATE BETWEEN '"+dt1.format(startDat)+"' and '"+dt1.format(endDat)+"'"+" AND OUTLET_ASSOCICATION_ID ='"+outletId+"'" + " AND COMPANY_ASSOCIATION_ID = "+currentUser.getCompany().getCompanyId());
					}
					tableData = tempSaleService.getAllTempSaleByCompanyId(reportParams);
				}else if(reportType.equals("Last Sale")){
					ReportParams reportParams = new ReportParams();
					if(!isHeadOffice && configuration!=null && configuration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
						reportParams.setBaseColumn("max(CREATED_DATE) as Last,sum(Revenue) as Revenue,Tax");
						reportParams.setPrintColumns(  reportType+","+ "Revenue,Tax");
								
					}else{
						reportParams.setBaseColumn("max(CREATED_DATE) as Last,sum(Revenue) as Revenue,sum(Cost_of_Goods) as Cost,sum(Gross_Profit) as Profit,avg(Margin),Tax");
						reportParams.setPrintColumns(  reportType+","+ "Revenue,Cost of Goods,Gross Profit,Margin,Tax");
						
					}
					reportParams.setCompanyId(currentUser.getCompany().getCompanyId());
					reportParams.setEndDate(endDat);
					reportParams.setGroupBy("Product");
					reportParams.setMainBaseColumn("Product");
					reportParams.setOrderBy("");
					reportParams.setPivotColumn( "CREATED_DATE");
					reportParams.setReportDateType(reportDateType);
					reportParams.setStartDate(startDat);
					reportParams.setTableName(tableName.toString());
					reportParams.setTallyColumn("Revenue");
					if(completeReport){
						reportParams.setWhereClause("where CREATED_DATE BETWEEN '"+dt1.format(startDat)+"' and '"+dt1.format(endDat)+"'"+ " AND COMPANY_ASSOCIATION_ID = "+currentUser.getCompany().getCompanyId());
					}else{
						reportParams.setWhereClause("where CREATED_DATE BETWEEN '"+dt1.format(startDat)+"' and '"+dt1.format(endDat)+"'"+" AND OUTLET_ASSOCICATION_ID ='"+outletId+"'" + " AND COMPANY_ASSOCIATION_ID = "+currentUser.getCompany().getCompanyId());
					}
					tableData = tempSaleService.getAllTempSaleByCompanyId(reportParams);

				}
				if (tableData != null) {
					ReportControllerBean reportControllerBean = new ReportControllerBean();
					reportControllerBean.setOutletBeans(outletBeans);
					reportControllerBean.setTableData(tableData);
					reportControllerBean.setHideSalesDetails(isHeadOffice);
					util.AuditTrail(request, currentUser, "SalesReportController.getSalesReportByDateRange", 
							"User "+ currentUser.getUserEmail()+" retrived sales report against date range successfully ",false);
					return new Response(reportControllerBean, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "SalesReportController.getSalesReportByDateRange", 
							" Sales report is not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SalesReportController.getSalesReportByDateRange",
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
					util.AuditTrail(request, currentUser, "CashSalesReportController.getOutlets", 
							" retrived all outlets requested by User "+currentUser.getUserEmail(),false);
					return new Response(outletBeans,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}
				else{
					util.AuditTrail(request, currentUser, "CashSalesReportController.getOutlets", 
							" outlets are not fount requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "CashSalesReportController.getOutlets",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
}
