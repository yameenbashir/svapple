/**
 * 
 */
package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Configuration;
import com.dowhile.Contact;
import com.dowhile.Outlet;
import com.dowhile.Product;
import com.dowhile.ProductVariant;
import com.dowhile.StockDetByProductUuid;
import com.dowhile.StockOrder;
import com.dowhile.StockOrderDetail;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.StockDetByProductUuidControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.frontend.mapping.bean.StockDetByProductUuidBean;
import com.dowhile.frontend.mapping.bean.StockOrderBean;
import com.dowhile.frontend.mapping.bean.StockOrderDetailBean;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.ContactService;
import com.dowhile.service.OutletService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductVariantService;
import com.dowhile.service.StockOrderDetailService;
import com.dowhile.service.StockOrderService;
import com.dowhile.service.TempSaleService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * @author asif
 *
 */
@Controller
@RequestMapping("/stockDetByProductUuid")
public class StockDetByProductUuidController {
	
	//private static Logger logger = Logger.getLogger(StockDetByProductUuidController.class.getName());  
	
	
	@Resource
	private ServiceUtil util;
	@Resource
	private TempSaleService tempSaleService;
	@Resource
	private OutletService outletService;

	@Resource
	private StockOrderDetailService stockOrderDetailService;

	@Resource
	private StockOrderService stockOrderService;

	@Resource
	private ProductService productService;

	@Resource
	private ProductVariantService productVariantService;
	
	@Resource
	private ContactService contactService;

	private Configuration configuration =null;

	private static List<OutletBean> outletBeans;

	@Resource
	private ConfigurationService configurationService ;
	
	/*@RequestMapping("/layout")
	public String getStockDetByProductUuidControllerPartialPage(ModelMap modelMap) {
		return "stockDetByProductUuid/layout";
	}*/
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getStockDetByProductUuidControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getStockDetByProductUuidControllerData(@PathVariable("sessionId") String sessionId, HttpServletRequest request) {
		List<ProductVariantBean> productBeansList = null;
		//List<ProductVariantBean> productVariantBeansList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				Response response = getAllProducts(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					productBeansList = (List<ProductVariantBean>) response.data;
				}
				/*response = getProductVariants(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					productVariantBeansList = (List<ProductVariantBean>) response.data;
				}*/
			StockDetByProductUuidControllerBean stockDetByProductUuidControllerBean = new StockDetByProductUuidControllerBean();
			stockDetByProductUuidControllerBean.setProductBeansList(productBeansList);
				
				util.AuditTrail(request, currentUser, "ProductStockHistoryReportController.getStockDetByProductUuidControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived getStockDetByProductUuidControllerData successfully",false);
				return new Response(stockDetByProductUuidControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "StockDetByProductUuidController.getStockDetByProductUuidControllerData",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@RequestMapping(value = "/getStockOrderDetailByProductUUID/{sessionId}/{startDate}/{endDate}/{stockType}", method = RequestMethod.POST)
	public @ResponseBody Response getStockOrderDetailByProductUUID(
			@PathVariable("sessionId") String sessionId,
			@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate,
			@PathVariable("stockType") String stockType, 
			@RequestBody List<ProductVariantBean> productVariantBeansList, HttpServletRequest request) {
			//@PathVariable("productUuid") String productUuid,  HttpServletRequest request) {
			List<StockDetByProductUuidBean> stockDetByProductUuidBeanList = new ArrayList<>();
			List<StockDetByProductUuid> stockDetByProductList= null;
			

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			List<Product> products = null;
			try {
				
				Date dateStartDate = new Date();
				Date dateEndDate = new Date();
				DateFormat parser = new SimpleDateFormat("MMM dd, yyyy");
				if(startDate.equalsIgnoreCase("undefined") || endDate.equalsIgnoreCase("undefined")
						|| startDate.equals("") || endDate.equals("") ){
					Date today = new Date();
					Calendar calendar =  Calendar.getInstance();
					calendar.setTime(today);
					calendar.add(Calendar.DAY_OF_MONTH, -7);
					dateStartDate = today;
					dateEndDate  = calendar.getTime();
				}else{
					dateStartDate = (Date) parser.parse(startDate.trim());
					dateEndDate = (Date) parser.parse(endDate);
				}

				Map<Integer, Outlet> outletMap = outletService.getAllOutletsMapByCompanyId(currentUser.getCompany().getCompanyId());
				
				//products = productService.getAllProductsByUuid(productUuid,currentUser.getCompany().getCompanyId());
				int stockOrdeType = Integer.parseInt(stockType);
					int status = 3;
					if(productVariantBeansList.size() == 1){
						ProductVariantBean productVariantBean = productVariantBeansList.get(0);
						 String selectedProductUuid = productVariantBean.getProductUuid();
						 System.out.println(productVariantBean.getProductUuid());
						if(productVariantBean.getIsProduct().equalsIgnoreCase("true")){
							stockDetByProductList = stockOrderDetailService.getStockOrderDetailByProductUUID(currentUser.getCompany().getCompanyId(), status ,stockOrdeType ,selectedProductUuid, dateStartDate, dateEndDate);
						}
						
					}
					/*if(products!=null) {
						stockDetByProductList = stockOrderDetailService.getStockOrderDetailByProductUUID(currentUser.getCompany().getCompanyId(), status ,stockOrdeType ,productUuid, dateStartDate, dateEndDate);
					}*/else {
						
						util.AuditTrail(request, currentUser, "StockDetByProductUuidController.getStockOrderDetailByProductUUID", 
								" StockOrders are not found requested by User "+currentUser.getUserEmail(),false);
						return new Response(MessageConstants.RECORD_NOT_FOUND,
								StatusConstants.RECORD_NOT_FOUND,
								LayOutPageConstants.STAY_ON_PAGE);
					}
					
					

					if(stockDetByProductList!=null) {
						for(StockDetByProductUuid stockOrderList :stockDetByProductList) {
							StockDetByProductUuidBean stockDetByProductUuidBean = new StockDetByProductUuidBean();
							BeanUtils.copyProperties(stockOrderList, stockDetByProductUuidBean);
							stockDetByProductUuidBean.setStockOrderRefNo(stockOrderList.getSTOCK_REF_NO());
							stockDetByProductUuidBean.setStockOrderTypeDesc(stockOrderList.getSTOCK_ORDER_TYPE_DESC());
							stockDetByProductUuidBean.setSourceOutletName(stockOrderList.getSource());
							stockDetByProductUuidBean.setOutletName(stockOrderList.getDestination());
							stockDetByProductUuidBean.setProductName(stockOrderList.getPRODUCT_NAME());
							stockDetByProductUuidBean.setVariantAttributeName(stockOrderList.getVARIANT_ATTRIBUTE_NAME());
							stockDetByProductUuidBean.setOrderProdQty(Objects.toString(stockOrderList.getOrder_prod_qty()));
							stockDetByProductUuidBean.setRecvProdQty(Objects.toString(stockOrderList.getRECV_PROD_QTY()));
							
							
							
							stockDetByProductUuidBeanList.add(stockDetByProductUuidBean);
							
						}
						
						util.AuditTrail(request, currentUser, "StockDetByProductUuidController.getStockOrderDetailByProductUUID", 
								"User "+ currentUser.getUserEmail()+" retrived all StockOrders successfully ",false);
						return new Response(stockDetByProductUuidBeanList, StatusConstants.SUCCESS,
								LayOutPageConstants.STAY_ON_PAGE);	
					}else {
						util.AuditTrail(request, currentUser, "StockDetByProductUuidController.getStockOrderDetailByProductUUID", 
								" StockOrders are not found requested by User "+currentUser.getUserEmail(),false);
						return new Response(MessageConstants.RECORD_NOT_FOUND,
								StatusConstants.RECORD_NOT_FOUND,
								LayOutPageConstants.STAY_ON_PAGE);
					}

			} 
			catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "StockDetByProductUuidController.getProductStockHistoryNewByProductId",
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
	@RequestMapping(value = "/getAllProducts/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProducts(@PathVariable("sessionId") String sessionId, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			List<Product> productList = null;
			try {			
				productList = productService.getAllProductsByCompanyIdGroupByProductUuId(currentUser.getCompany().getCompanyId());
				if(productList != null){
					for(Product product:productList){
						ProductVariantBean productVariantBean = new ProductVariantBean();
						productVariantBean.setProductId(product.getProductId().toString());
						productVariantBean.setProductName(product.getProductName());
						productVariantBean.setProductUuid(product.getProductUuid());
						productVariantBean.setOutletId(product.getOutlet().getOutletId().toString());
						productVariantBean.setSku(product.getSku());
						if (product.getVariantProducts().equalsIgnoreCase("true")){
							productVariantBean.setIsProduct("false");
						}
						else
						{
							productVariantBean.setIsProduct("true");
						}
						productVariantBean.setIsVariant("false");
						productVariantBean.setProductVariantId(product.getProductId().toString());					
						productVariantBean.setVariantAttributeName(product.getProductName());
						if(product.getCurrentInventory() != null){
							productVariantBean.setCurrentInventory(product.getCurrentInventory().toString());
						}
						else
						{
							productVariantBean.setCurrentInventory("0");
						}
						productVariantBean.setProductName(product.getProductName());
						if(product.getSupplyPriceExclTax() != null){
							productVariantBean.setSupplyPriceExclTax(product.getSupplyPriceExclTax().toString());
						}
						if(product.getMarkupPrct() != null){
							/*Double retailPrice = product.getSupplyPriceExclTax().doubleValue() * (product.getMarkupPrct().doubleValue()/100) + product.getSupplyPriceExclTax().doubleValue();
							NumberFormat formatter = new DecimalFormat("###.##");  
							String strRetailPrice = formatter.format(retailPrice);
							productVariantBean.setRetailPriceExclTax(strRetailPrice);*/
							BigDecimal netPrice = (product.getSupplyPriceExclTax().multiply(product.getMarkupPrct().divide(new BigDecimal(100)))).add(product.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
							BigDecimal retailPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
							productVariantBean.setRetailPriceExclTax(retailPrice.toString());
						}
						productVariantBeansList.add(productVariantBean);
					}
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.getAllProducts", "User "+ 
							currentUser.getUserEmail()+" Get Products and Products",false);
					return new Response(productVariantBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} 
				else {
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.getAllProducts", "User "+ 
							currentUser.getUserEmail()+" Get Products",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch (Exception e) {
				e.printStackTrace();/*logger.error(e.getMessage(),e);*/
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderController.getAllProducts",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}
		else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}	
	}
	
}

