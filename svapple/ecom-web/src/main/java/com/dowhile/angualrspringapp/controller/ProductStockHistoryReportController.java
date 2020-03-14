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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
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
import com.dowhile.StockOrder;
import com.dowhile.StockOrderDetail;
import com.dowhile.User;
import com.dowhile.beans.ReportParams;
import com.dowhile.beans.TableData;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.ProductStockHistoryReportControllerBean;
import com.dowhile.controller.bean.PurchaseOrderControllerBean;
import com.dowhile.controller.bean.PurchaseOrderEditProductsControllerBean;
import com.dowhile.controller.bean.ReportControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
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
import com.dowhile.util.ControllerUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * @author Zafar Shakeel
 *
 */
@Controller
@RequestMapping("/productStockHistoryReport")
public class ProductStockHistoryReportController {

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
	private ConfigurationService configurationService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getProductStockHistoryReportControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getProductStockHistoryReportControllerData(@PathVariable("sessionId") String sessionId, HttpServletRequest request) {
		List<ProductVariantBean> productBeansList = null;
		List<ProductVariantBean> productVariantBeansList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				Response response = getAllProducts(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					productBeansList = (List<ProductVariantBean>) response.data;
				}
				response = getProductVariants(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					productVariantBeansList = (List<ProductVariantBean>) response.data;
				}
				ProductStockHistoryReportControllerBean productStockHistoryReportControllerBean = new ProductStockHistoryReportControllerBean();
				productStockHistoryReportControllerBean.setProductBeansList(productBeansList);
				productStockHistoryReportControllerBean.setProductVariantBeansList(productVariantBeansList);
				util.AuditTrail(request, currentUser, "ProductStockHistoryReportController.getProductStockHistoryReportControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived ProductStockHistoryReportControllerData successfully",false);
				return new Response(productStockHistoryReportControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ProductStockHistoryReportController.getProductStockHistoryReportControllerData",
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
	@RequestMapping(value = "/getProductStockHistoryReportByDateRange/{sessionId}/{startDate}/{endDate}/{stockType}", method = RequestMethod.POST)
	public @ResponseBody Response getProductStockHistoryReportByDateRange(@PathVariable("sessionId") String sessionId,
			@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate,
			@PathVariable("stockType") String stockType, @RequestBody List<ProductVariantBean> productVariantBeansList, HttpServletRequest request) {
		List<StockOrderDetailBean> stockOrderDetailBeansList = null;

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				Response response = getAllDetailsByStockOrderId(sessionId, startDate, endDate, stockType, productVariantBeansList, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					stockOrderDetailBeansList = (List<StockOrderDetailBean>) response.data;
				}

				util.AuditTrail(request, currentUser, "PurchaseOrderEditProductsController.getPurchaseOrderEditProductsControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived PurchaseOrderEditProductsControllerData successfully ",false);
				return new Response(stockOrderDetailBeansList, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} 
			catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ProductStockHistoryReportController.getProductStockHistoryReportByDateRange",
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
	@RequestMapping(value = "/getAllDetailsByStockOrderId/{sessionId}/{startDate}/{endDate}/{stockType}", method = RequestMethod.POST)
	public @ResponseBody Response getAllDetailsByStockOrderId(@PathVariable("sessionId") String sessionId,
			@PathVariable("startDate") String startDate,
			@PathVariable("endDate") String endDate,
			@PathVariable("stockType") String stockType, 
			@RequestBody List<ProductVariantBean> productVariantBeansList, HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			List<StockOrderDetailBean> stockOrderDetailBeansList = new ArrayList<>();
			List<StockOrderDetail> stockOrderDetailList = null;
			List<Product> allProducts = null;
			List<ProductVariant> allProductVariants = null;
			List<StockOrder> allStockOrders = null;
			List<Outlet> allOutlets = null;
			List<Contact> allContacts = null;
			Map<Integer, Product> productsMap = new HashMap<>();
			Map<Integer, ProductVariant> productVariantsMap = new HashMap<>();
			Map<Integer, StockOrder> stockOrdersMap = new HashMap<>();
			Map<Integer, Outlet> outletsMap = new HashMap<>();
			Map<Integer, Contact> contactsMap = new HashMap<>();
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				if(productVariantBeansList.size() > 0){
					List<Integer> productVariantIdList = new ArrayList<>();
					for(int i = 0; i < productVariantBeansList.size(); i++){
						if(productVariantBeansList.get(i) != null){
							productVariantIdList.add(Integer.parseInt(productVariantBeansList.get(i).getProductVariantId()));
						}
						else{
							productVariantBeansList.remove(i);
						}
					}					
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
					if(productVariantBeansList.size() == 1){
						ProductVariantBean productVariantBean = productVariantBeansList.get(0);
						if(productVariantBean.getIsProduct().equalsIgnoreCase("true")){
							stockOrderDetailList = stockOrderDetailService.getStockOrderDetailByProductId(Integer.parseInt(productVariantBean.getProductVariantId()), dateStartDate, dateEndDate, currentUser.getCompany().getCompanyId());
						}
						else{
							stockOrderDetailList = stockOrderDetailService.getStockOrderDetailByProductVariantId(Integer.parseInt(productVariantBean.getProductVariantId()), dateStartDate, dateEndDate, currentUser.getCompany().getCompanyId());
						}
					}	
					else{
					stockOrderDetailList = stockOrderDetailService.getStockOrderDetailByProductVariantsId(productVariantIdList, dateStartDate, dateEndDate, currentUser.getCompany().getCompanyId());
					}
				}				
				int order = 1;
				if (stockOrderDetailList != null) {
					List<ProductVariant> recvProductVariantList = null;
					Map recvProductVariantMap = new HashMap<>();
					List<Product> recvProductList = null;
					Map recvProductMap = new HashMap<>();
					allProducts = productService.getAllProducts(currentUser.getCompany().getCompanyId());
					allProductVariants = productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
					allStockOrders = stockOrderService.GetAllStockOrder(currentUser.getCompany().getCompanyId());
					allOutlets = outletService.getOutlets(currentUser.getCompany().getCompanyId());
					allContacts = contactService.getAllContacts(currentUser.getCompany().getCompanyId());
					//recvProductList = productService.getAllProductsByOutletId(Integer.parseInt(stockOrderBean.getOutletId()));
					if(allProducts != null){
						for(Product product:allProducts){
							productsMap.put(product.getProductId(), product);
							/*
							if(stockOrderBean.getSourceOutletId() != null && !stockOrderBean.getSourceOutletId().equalsIgnoreCase("")){
								if(product.getOutlet().getOutletId().toString().equalsIgnoreCase(stockOrderBean.getOutletId())){
									recvProductMap.put(product.getProductUuid(), product.getCurrentInventory());
								}
							}*/
						}
					}
					//recvProductVariantList = productVariantService.getAllProductVariantsByOutletId(Integer.parseInt(stockOrderBean.getOutletId()),currentUser.getCompany().getCompanyId());
					if(allProductVariants != null){
						for(ProductVariant productVariant:allProductVariants){
							productVariantsMap.put(productVariant.getProductVariantId(), productVariant);
							/*Product product = productsMap.get(productVariant.getProduct().getProductId());
							if(stockOrderBean.getSourceOutletId() != null && !stockOrderBean.getSourceOutletId().equalsIgnoreCase("")){
								if(product.getOutlet().getOutletId().toString().equalsIgnoreCase(stockOrderBean.getOutletId())){
									recvProductVariantMap.put(productVariant.getProductVariantUuid(), productVariant.getCurrentInventory());
								}
							}*/	
						}
					}
					if(allStockOrders != null){
						for(StockOrder stockOrder:allStockOrders){
							stockOrdersMap.put(stockOrder.getStockOrderId(), stockOrder);
						}
					}
					if(allOutlets != null){
						for(Outlet outlet:allOutlets){
							outletsMap.put(outlet.getOutletId(), outlet);
						}
					}
					if(allContacts != null){
						for(Contact contact:allContacts){
							contactsMap.put(contact.getContactId(), contact);
						}
					}
					for (StockOrderDetail stockOrderDetail : stockOrderDetailList) {
						StockOrder stockOrder = stockOrdersMap.get(stockOrderDetail.getStockOrder().getStockOrderId());
						int stockOrderType = stockOrder.getStockOrderType().getStockOrderTypeId();
						if(stockOrderType == Integer.parseInt(stockType) && stockOrder.getStatus().getStatusId() == 3){
							StockOrderDetailBean stockOrderDetailBean = new StockOrderDetailBean();
							if(stockOrder.getOutletByOutletAssocicationId() != null){
								Outlet outlet = outletsMap.get(stockOrder.getOutletByOutletAssocicationId().getOutletId());
								stockOrderDetailBean.setOutletName(outlet.getOutletName());
							}
							if(stockOrder.getOutletBySourceOutletAssocicationId() != null){
								Outlet outlet = outletsMap.get(stockOrder.getOutletBySourceOutletAssocicationId().getOutletId());
								stockOrderDetailBean.setSourceOutletName(outlet.getOutletName());
							}
							if(stockOrder.getContactId() != 0){
								Contact contact = contactsMap.get(stockOrder.getContactId());
								stockOrderDetailBean.setContactName(contact.getContactName());
							}
							if(stockOrder.getStockRefNo() != null){
								stockOrderDetailBean.setStockOrderRefNo(stockOrder.getStockRefNo());
							}
							stockOrderDetailBean.setOrderProdQty(Objects.toString(stockOrderDetail.getOrderProdQty(), ""));
							stockOrderDetailBean.setOrdrSupplyPrice(Objects.toString(stockOrderDetail.getOrdrSupplyPrice(),""));
							if(stockOrderDetail.getRetailPrice()!= null){
								stockOrderDetailBean.setRetailPrice(Objects.toString(stockOrderDetail.getRetailPrice(),""));
							}
							if(!stockOrderDetail.isIsProduct()){						
								ProductVariant productVariant = productVariantsMap.get(stockOrderDetail.getProductVariant().getProductVariantId());
								if(productVariant != null){
									stockOrderDetailBean.setProductVariantId(Objects.toString(productVariant.getProductVariantId(),""));
									stockOrderDetailBean.setVariantAttributeName(productsMap.get(productVariant.getProduct().getProductId()).getProductName() + "-" + Objects.toString(productVariant.getVariantAttributeName(),""));
									stockOrderDetailBean.setProductVariantCurrInventory(Objects.toString(productVariant.getCurrentInventory(),""));
									stockOrderDetailBean.setIsProduct("false");
									if(stockOrder.getOutletBySourceOutletAssocicationId() != null){
										String recvCurrInventory = String.valueOf(recvProductVariantMap.get(productVariant.getProductVariantUuid()));
										if(recvCurrInventory != null && !recvCurrInventory.equalsIgnoreCase("null")){
											stockOrderDetailBean.setProductVariantRecvOutletInventory(recvCurrInventory);
										}
										else{
											stockOrderDetailBean.setProductVariantRecvOutletInventory("0");
										}
									}
								}
							}					
							else{
								Product product = productsMap.get(stockOrderDetail.getProduct().getProductId());
								if(product!= null){
									stockOrderDetailBean.setProductVariantId(Objects.toString(product.getProductId(),""));
									stockOrderDetailBean.setVariantAttributeName(Objects.toString(product.getProductName(),""));
									stockOrderDetailBean.setProductVariantCurrInventory(Objects.toString(product.getCurrentInventory(),""));
									stockOrderDetailBean.setIsProduct("true");
									if(stockOrder.getOutletBySourceOutletAssocicationId() != null){
										String recvCurrInventory = String.valueOf(recvProductMap.get(product.getProductUuid()));
										if(recvCurrInventory != null && !recvCurrInventory.equalsIgnoreCase("null")){
											stockOrderDetailBean.setProductVariantRecvOutletInventory(recvCurrInventory);
										}
										else{
											stockOrderDetailBean.setProductVariantRecvOutletInventory("0");
										}
									}	
								}
							}
							stockOrderDetailBean.setRecvProdQty(Objects.toString(stockOrderDetail.getRecvProdQty(),""));
							stockOrderDetailBean.setRecvSupplyPrice(Objects.toString(stockOrderDetail.getRecvSupplyPrice(),""));
							stockOrderDetailBean.setStockOrderDetailId(stockOrderDetail.getStockOrderDetailId().toString());
							stockOrderDetailBean.setOrder(Integer.toString(order));
							if(stockOrder.isRetailPriceBill() == false){
								if(stockOrderDetail.getOrderProdQty() != null &&  stockOrderDetail.getOrdrSupplyPrice() != null)
								{
									Double total = stockOrderDetail.getOrderProdQty() * stockOrderDetail.getOrdrSupplyPrice().doubleValue();
									NumberFormat formatter = new DecimalFormat("###.##");  
									String strTotal = formatter.format(total);
									stockOrderDetailBean.setTotal(strTotal);
								}
							}
							else{
								if(stockOrderDetail.getOrderProdQty() != null &&  stockOrderDetail.getRetailPrice() != null)
								{
									Double total = stockOrderDetail.getOrderProdQty() * stockOrderDetail.getRetailPrice().doubleValue();
									NumberFormat formatter = new DecimalFormat("###.##");  
									String strTotal = formatter.format(total);
									stockOrderDetailBean.setTotal(strTotal);
								}
							}
							if(stockOrderDetail.getRecvProdQty() != null && stockOrderDetail.getRecvSupplyPrice() != null){
								Double recTotal = stockOrderDetail.getRecvProdQty() * stockOrderDetail.getRecvSupplyPrice().doubleValue();
								NumberFormat formatter = new DecimalFormat("###.##");  
								String strRecTotal = formatter.format(recTotal);
								stockOrderDetailBean.setRecvTotal(strRecTotal);
							}					
							stockOrderDetailBean.setStockOrderId(String.valueOf(stockOrder.getStockOrderId()));
							if(stockOrderDetail.getCreatedDate() != null){
								stockOrderDetailBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(stockOrderDetail.getCreatedDate()));
							}
							if(stockOrder.getLastUpdated() != null){
								stockOrderDetailBean.setLastUpdated(DateTimeUtil.convertDBDateTimeToGuiFormat(stockOrder.getLastUpdated()));
							}
							order++;
							stockOrderDetailBeansList.add(stockOrderDetailBean);
						}
					}
					util.AuditTrail(request, currentUser, "PurchaseOrderEditProductsController.getAllDetailsByStockOrderId", "User "+ 
							currentUser.getUserEmail()+" Get All Stock Order Details",false);
					return new Response(stockOrderDetailBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "PurchaseOrderEditProductsController.getAllDetailsByStockOrderId", "User "+ 
							currentUser.getUserEmail()+"  Get All Stock Order Details",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderActionsController.getAllDetailsByStockOrderId",
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
				e.printStackTrace();// logger.error(e.getMessage(),e);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getProductVariants/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getProductVariants(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			List<ProductVariant> productVariantList = null;
			try {			
				productVariantList = productVariantService.getAllProductVariantsGroupbyUuid(currentUser.getCompany().getCompanyId());
				Map<Integer, Product> productsMap = new HashMap<>();
				List<Product> products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
				if(products!=null){
					for(Product product:products){
						productsMap.put(product.getProductId(), product);
					}
				}
				if(productVariantList != null){
					for(ProductVariant productVariant:productVariantList){
						ProductVariantBean productVariantBean = new ProductVariantBean();
						productVariantBean.setIsProduct("false");
						productVariantBean.setIsVariant("true");
						productVariantBean.setSku(productVariant.getSku());
						productVariantBean.setProductVariantId(productVariant.getProductVariantId().toString());
						if(productVariant.getCurrentInventory() != null){
							productVariantBean.setCurrentInventory(productVariant.getCurrentInventory().toString());
						}
						else
						{
							productVariantBean.setCurrentInventory("0");
						}
						Product product = productsMap.get(productVariant.getProduct().getProductId());
						
						productVariantBean.setProductName(product.getProductName());					
						productVariantBean.setVariantAttributeName(product.getProductName() + "-" + productVariant.getVariantAttributeName());
						productVariantBean.setProductId(product.getProductId().toString());
						if(productVariant.getSupplyPriceExclTax() != null){
							productVariantBean.setSupplyPriceExclTax(productVariant.getSupplyPriceExclTax().toString());
						}
						if(productVariant.getMarkupPrct() != null){
							/*Double retailPrice = productVariant.getSupplyPriceExclTax().doubleValue() * (productVariant.getMarkupPrct().doubleValue()/100) + productVariant.getSupplyPriceExclTax().doubleValue();
							NumberFormat formatter = new DecimalFormat("###.##");  
							String strRetailPrice = formatter.format(retailPrice);
							productVariantBean.setRetailPriceExclTax(strRetailPrice);*/
							BigDecimal netPrice = (productVariant.getSupplyPriceExclTax().multiply(productVariant.getMarkupPrct().divide(new BigDecimal(100)))).add(productVariant.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
							BigDecimal retailPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
							productVariantBean.setRetailPriceExclTax(retailPrice.toString());
						}
						productVariantBeansList.add(productVariantBean);
					}
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.getProductVariants", "User "+ 
							currentUser.getUserEmail()+" Get ProductVariants",false);
					return new Response(productVariantBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} 
				else {
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.getProductVariants", "User "+ 
							currentUser.getUserEmail()+" Get ProductVariants",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderController.getProductVariants",
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
