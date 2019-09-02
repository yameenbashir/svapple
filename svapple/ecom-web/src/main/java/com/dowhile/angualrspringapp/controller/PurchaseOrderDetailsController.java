package com.dowhile.angualrspringapp.controller;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import com.dowhile.Contact;
import com.dowhile.Outlet;
import com.dowhile.Product;
import com.dowhile.ProductVariant;
import com.dowhile.Status;
import com.dowhile.StockOrder;
import com.dowhile.StockOrderDetail;
import com.dowhile.StockOrderType;
import com.dowhile.User;
import com.dowhile.constant.Actions;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.POCreateandReceiveControllerBean;
import com.dowhile.controller.bean.PurchaseOrderControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.frontend.mapping.bean.StockOrderBean;
import com.dowhile.frontend.mapping.bean.StockOrderDetailBean;
import com.dowhile.frontend.mapping.bean.StockOrderTypeBean;
import com.dowhile.frontend.mapping.bean.SupplierBean;
import com.dowhile.service.ContactService;
import com.dowhile.service.OutletService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductVariantService;
import com.dowhile.service.StatusService;
import com.dowhile.service.StockOrderDetailService;
import com.dowhile.service.StockOrderService;
import com.dowhile.service.StockOrderTypeService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * Zafar Shakeel
 */
@Controller
@RequestMapping("/purchaseOrderDetails")

public class PurchaseOrderDetailsController {
	@Resource
	private ServiceUtil util;
	@Resource
	private OutletService outletService;

	@Resource
	private ContactService supplierService;

	@Resource
	private StockOrderService stockOrderService;

	@Resource
	private StatusService statusService;

	@Resource
	private StockOrderTypeService stockOrderTypeService;

	@Resource
	private StockOrderDetailService stockOrderDetailService;	

	@Resource
	private ProductVariantService productVariantService;

	@Resource
	private ProductService productService;

	@RequestMapping("/layout")
	public String getPurchaseOrderDetialsControllerPartialPage(ModelMap modelMap) {
		return "purchaseOrderDetails/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getPurchaseOrderDetailsControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getPurchaseOrderDetailsControllerData(@PathVariable("sessionId") String sessionId,
			@RequestBody StockOrderBean stockOrderBean, HttpServletRequest request) {
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
				PurchaseOrderControllerBean purchaseOrderControllerBean = new PurchaseOrderControllerBean();
				purchaseOrderControllerBean.setProductBeansList(productBeansList);
				purchaseOrderControllerBean.setProductVariantBeansList(productVariantBeansList);
				util.AuditTrail(request, currentUser, "PurchaseOrderController.getPurchaseOrderControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived PurchaseOrderControllerData successfully ",false);
				return new Response(purchaseOrderControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderDetailsController.getPurchaseOrderControllerData",
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
	@RequestMapping(value = "/getPurchaseOrderTransferControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getPurchaseOrderTransferControllerData(@PathVariable("sessionId") String sessionId,
			@RequestBody StockOrderBean stockOrderBean, HttpServletRequest request) {
		List<ProductVariantBean> productBeansList = null;
		List<ProductVariantBean> productVariantBeansList = null;
		List<StockOrderBean> stockTransferOrderBeansList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {
				Response response = getAllProductsforTransfer(sessionId, stockOrderBean, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					productBeansList = (List<ProductVariantBean>) response.data;
				}

				response = getProductVariantsforTransfer(sessionId, stockOrderBean, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					productVariantBeansList = (List<ProductVariantBean>) response.data;
				}
				response = getAllStockTransferOrders(sessionId,stockOrderBean, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					stockTransferOrderBeansList = (List<StockOrderBean>) response.data;
				}
				PurchaseOrderControllerBean purchaseOrderControllerBean = new PurchaseOrderControllerBean();
				purchaseOrderControllerBean.setProductBeansList(productBeansList);
				purchaseOrderControllerBean.setProductVariantBeansList(productVariantBeansList);
				purchaseOrderControllerBean.setStockTransferOrderBeansList(stockTransferOrderBeansList);
				util.AuditTrail(request, currentUser, "PurchaseOrderController.getPurchaseOrderControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived PurchaseOrderControllerData successfully ",false);
				return new Response(purchaseOrderControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderDetailsController.getPurchaseOrderControllerData",
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
	@RequestMapping(value = "/getPurchaseOrderReturnsControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getPurchaseOrderReturnsControllerData(@PathVariable("sessionId") String sessionId,
			@RequestBody StockOrderBean stockOrderBean, HttpServletRequest request) {
		List<ProductVariantBean> productBeansList = null;
		List<ProductVariantBean> productVariantBeansList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {
				Response response = getAllProductsByOutletId(sessionId, stockOrderBean.getOutletId(), request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					productBeansList = (List<ProductVariantBean>) response.data;
				}

				response = getProductVariantsByOutletId(sessionId, stockOrderBean.getOutletId(), request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					productVariantBeansList = (List<ProductVariantBean>) response.data;
				}
				PurchaseOrderControllerBean purchaseOrderControllerBean = new PurchaseOrderControllerBean();
				purchaseOrderControllerBean.setProductBeansList(productBeansList);
				purchaseOrderControllerBean.setProductVariantBeansList(productVariantBeansList);
				util.AuditTrail(request, currentUser, "PurchaseOrderController.getPurchaseOrderControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived PurchaseOrderControllerData successfully ",false);
				return new Response(purchaseOrderControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderDetailsController.getPurchaseOrderControllerData",
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
	@RequestMapping(value = "/addStockOrderDetail/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response addStockOrder(@PathVariable("sessionId") String sessionId,
			@RequestBody List<StockOrderDetailBean> stockOrderDetailBeansList, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			try {			
				if (stockOrderDetailBeansList.size() > 0) {	
					StockOrder stockOrder = stockOrderService.getStockOrderByStockOrderID(Integer.parseInt(stockOrderDetailBeansList.get(0).getStockOrderId()),currentUser.getCompany().getCompanyId());
					Map<Integer, Product> productsMap = new HashMap<>();
					List<Product> products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
					List<StockOrderDetail> stockOrderDetailsAddList = new ArrayList<>();
					if(products!=null){
						for(Product product:products){
							productsMap.put(product.getProductId(), product);
						}
					}
					Map<Integer, ProductVariant> productVariantsMap = new HashMap<>();
					List<ProductVariant> productVariants = productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
					if(productVariants!=null){
						for(ProductVariant productVariant:productVariants){
							productVariantsMap.put(productVariant.getProductVariantId(), productVariant);
						}
					}
					//Stock Order Map Region
					List<StockOrderDetail> stockOrderDetails = new ArrayList<>();
					Map<Integer, List<StockOrderDetail>> stockOrderDetailsMap = new HashMap<>();
					Map<Integer, StockOrderDetail> stockOrderDetailsByDetailIDMap = new HashMap<>();
					stockOrderDetails = stockOrderDetailService.getAllStockOrderDetails(currentUser.getCompany().getCompanyId());
					if(stockOrderDetails!=null){
						for(StockOrderDetail stockOrderDetail:stockOrderDetails){
							List<StockOrderDetail> addedstockOrderDetails = stockOrderDetailsMap.get(stockOrderDetail.getStockOrder().getStockOrderId());
							if(addedstockOrderDetails!=null){
								addedstockOrderDetails.add(stockOrderDetail);
								stockOrderDetailsMap.put(stockOrderDetail.getStockOrder().getStockOrderId(), addedstockOrderDetails);
							}else{
								addedstockOrderDetails = new ArrayList<>();
								addedstockOrderDetails.add(stockOrderDetail);
								stockOrderDetailsMap.put(stockOrderDetail.getStockOrder().getStockOrderId(), addedstockOrderDetails);
							}
							stockOrderDetailsByDetailIDMap.put(stockOrderDetail.getStockOrderDetailId(), stockOrderDetail);
						}
					}
					//End Region
					for(StockOrderDetailBean stockOrderDetailBean : stockOrderDetailBeansList)
					{
						StockOrderDetail stockOrderDetail = new StockOrderDetail();
						stockOrderDetail.setOrderProdQty(Integer.parseInt(stockOrderDetailBean.getOrderProdQty()));
						stockOrderDetail.setOrdrSupplyPrice(new BigDecimal(stockOrderDetailBean.getOrdrSupplyPrice()));
						if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
							stockOrderDetail.setProductVariant(productVariantsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId())));
							stockOrderDetail.setIsProduct(false);
						}
						else{
							stockOrderDetail.setProduct(productsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId())));
							stockOrderDetail.setIsProduct(true);
						}					
						if(stockOrderDetailBean.getRecvProdQty() != null && !stockOrderDetailBean.getRecvProdQty().equalsIgnoreCase("")){
							stockOrderDetail.setRecvProdQty(Integer.parseInt(stockOrderDetailBean.getRecvProdQty()));
						}
						if(stockOrderDetailBean.getRecvSupplyPrice() != null && !stockOrderDetailBean.getRecvSupplyPrice().equalsIgnoreCase("")){
							stockOrderDetail.setRecvSupplyPrice(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
						}
						if(stockOrderDetailBean.getRetailPrice() != null && !stockOrderDetailBean.getRetailPrice().equalsIgnoreCase("")){
							stockOrderDetail.setRetailPrice(new BigDecimal(stockOrderDetailBean.getRetailPrice()));
						}
						stockOrderDetail.setStockOrder(stockOrder);
						stockOrderDetail.setActiveIndicator(true);			
						stockOrderDetail.setCreatedDate(new Date());				
						stockOrderDetail.setLastUpdated(new Date());
						stockOrderDetail.setCreatedBy(currentUser.getUserId());
						stockOrderDetail.setUpdatedBy(currentUser.getUserId());
						stockOrderDetail.setCompany(currentUser.getCompany());
						stockOrderDetailsAddList.add(stockOrderDetail);
						/*stockOrderDetailService.addStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());
						util.AuditTrail(request, currentUser, "PurchaseOrderDetails.addStockOrderDetail", 
								"User "+ currentUser.getUserEmail()+" Added StockOrderDetail+"+stockOrderDetailBean.getStockOrderDetailId()+" successfully ",false);*/
					}
					if(stockOrderDetailsAddList.size() > 0){
						stockOrderDetailService.addStockOrderDetailsList(stockOrderDetailsAddList, currentUser.getCompany().getCompanyId());
					}
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.STOCKCONTROL);
				}else{
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.addStockOrderDetail", "User "+ 
							currentUser.getUserEmail()+" Unable to add StockOrderDetail+"+ stockOrderDetailBeansList.get(0).getStockOrderDetailId(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderController.addStockOrder",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateStockOrderDetail/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response updateStockOrderDetail(@PathVariable("sessionId") String sessionId,
			@RequestBody List<StockOrderDetailBean> stockOrderDetailBeansList, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			try {			
				if (stockOrderDetailBeansList.size() > 0) {	
					StockOrder stockOrder = stockOrderService.getStockOrderByStockOrderID(Integer.parseInt(stockOrderDetailBeansList.get(0).getStockOrderId()),currentUser.getCompany().getCompanyId());
					Map<Integer, Product> productsMap = new HashMap<>();
					List<Product> products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
					List<StockOrderDetail> stockOrderDetailsUpdateList = new ArrayList<>();
					List<StockOrderDetail> stockOrderDetailsDeleteList = new ArrayList<>();
					List<StockOrderDetail> stockOrderDetailsAddList = new ArrayList<>();
					if(products!=null){
						for(Product product:products){
							productsMap.put(product.getProductId(), product);
						}
					}
					Map<Integer, ProductVariant> productVariantsMap = new HashMap<>();
					List<ProductVariant> productVariants = productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
					if(productVariants!=null){
						for(ProductVariant productVariant:productVariants){
							productVariantsMap.put(productVariant.getProductVariantId(), productVariant);
						}
					}
					//Stock Order Map Region
					List<StockOrderDetail> stockOrderDetails = new ArrayList<>();
					Map<Integer, List<StockOrderDetail>> stockOrderDetailsMap = new HashMap<>();
					Map<Integer, StockOrderDetail> stockOrderDetailsByDetailIDMap = new HashMap<>();
					stockOrderDetails = stockOrderDetailService.getAllStockOrderDetails(currentUser.getCompany().getCompanyId());
					if(stockOrderDetails!=null){
						for(StockOrderDetail stockOrderDetail:stockOrderDetails){
							List<StockOrderDetail> addedstockOrderDetails = stockOrderDetailsMap.get(stockOrderDetail.getStockOrder().getStockOrderId());
							if(addedstockOrderDetails!=null){
								addedstockOrderDetails.add(stockOrderDetail);
								stockOrderDetailsMap.put(stockOrderDetail.getStockOrder().getStockOrderId(), addedstockOrderDetails);
							}else{
								addedstockOrderDetails = new ArrayList<>();
								addedstockOrderDetails.add(stockOrderDetail);
								stockOrderDetailsMap.put(stockOrderDetail.getStockOrder().getStockOrderId(), addedstockOrderDetails);
							}
							stockOrderDetailsByDetailIDMap.put(stockOrderDetail.getStockOrderDetailId(), stockOrderDetail);
						}
					}
					//End Region
					List<StockOrderDetail> preStockOrderDetailList = stockOrderDetailsMap.get(Integer.parseInt(stockOrderDetailBeansList.get(0).getStockOrderId()));
					for(StockOrderDetailBean stockOrderDetailBean : stockOrderDetailBeansList)
					{
						if(stockOrderDetailBean.getStockOrderDetailId() != null && !stockOrderDetailBean.getStockOrderDetailId().equalsIgnoreCase("")){
							StockOrderDetail stockOrderDetail = stockOrderDetailsByDetailIDMap.get(Integer.parseInt(stockOrderDetailBean.getStockOrderDetailId()));
							if(preStockOrderDetailList != null){
								int i = 0;
								int index = -1;
								for (StockOrderDetail preStockOrderDetail : preStockOrderDetailList){
									int stockOrderDetailId = stockOrderDetail.getStockOrderDetailId();
									int preStockOrderDetailId = preStockOrderDetail.getStockOrderDetailId();
									if(stockOrderDetailId == preStockOrderDetailId)
									{
										index = i;
										break;
									}
									i++;
								}
								if(index != -1){
									preStockOrderDetailList.remove(index);
								}
							}
							stockOrderDetail.setOrderProdQty(Integer.parseInt(stockOrderDetailBean.getOrderProdQty()));
							stockOrderDetail.setOrdrSupplyPrice(new BigDecimal(stockOrderDetailBean.getOrdrSupplyPrice()));
							if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
								stockOrderDetail.setProductVariant(productVariantsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId())));
								stockOrderDetail.setIsProduct(false);
							}
							else{
								stockOrderDetail.setProduct(productsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId())));
								stockOrderDetail.setIsProduct(true);
							}
							if(stockOrderDetailBean.getRecvProdQty() != null && !stockOrderDetailBean.getRecvProdQty().equalsIgnoreCase("")){
								stockOrderDetail.setRecvProdQty(Integer.parseInt(stockOrderDetailBean.getRecvProdQty()));
							}
							if(stockOrderDetailBean.getRecvSupplyPrice() != null && !stockOrderDetailBean.getRecvSupplyPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRecvSupplyPrice(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
							}
							if(stockOrderDetailBean.getRetailPrice() != null && !stockOrderDetailBean.getRetailPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRetailPrice(new BigDecimal(stockOrderDetailBean.getRetailPrice()));
							}
							stockOrderDetail.setStockOrder(stockOrder);
							stockOrderDetail.setActiveIndicator(true);				
							stockOrderDetail.setLastUpdated(new Date());
							stockOrderDetail.setUpdatedBy(currentUser.getUserId());
							stockOrderDetailsUpdateList.add(stockOrderDetail);
							//stockOrderDetailService.updateStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());
							//util.AuditTrail(request, currentUser, "PurchaseOrderDetails.updateStockOrderDetail", 
							//	"User "+ currentUser.getUserEmail()+" Update StockOrderDetail+"+stockOrderDetailBean.getStockOrderDetailId()+" successfully ",false);
						}
						else
						{
							StockOrderDetail stockOrderDetail = new StockOrderDetail();
							//stockOrderDetail.setStockOrderDetailId(Integer.parseInt(stockOrderDetailBean.getStockOrderDetailId()));
							stockOrderDetail.setOrderProdQty(Integer.parseInt(stockOrderDetailBean.getOrderProdQty()));
							stockOrderDetail.setOrdrSupplyPrice(new BigDecimal(stockOrderDetailBean.getOrdrSupplyPrice()));
							if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
								stockOrderDetail.setProductVariant(productVariantsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId())));
								stockOrderDetail.setIsProduct(false);
							}
							else{
								stockOrderDetail.setProduct(productsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId())));
								stockOrderDetail.setIsProduct(true);	
							}
							if(stockOrderDetailBean.getRecvProdQty() != null && !stockOrderDetailBean.getRecvProdQty().equalsIgnoreCase("")){
								stockOrderDetail.setRecvProdQty(Integer.parseInt(stockOrderDetailBean.getRecvProdQty()));
							}
							if(stockOrderDetailBean.getRecvSupplyPrice() != null && !stockOrderDetailBean.getRecvSupplyPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRecvSupplyPrice(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
							}
							if(stockOrderDetailBean.getRetailPrice() != null && !stockOrderDetailBean.getRetailPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRetailPrice(new BigDecimal(stockOrderDetailBean.getRetailPrice()));
							}
							stockOrderDetail.setStockOrder(stockOrder);
							stockOrderDetail.setActiveIndicator(true);			
							stockOrderDetail.setCreatedDate(new Date());				
							stockOrderDetail.setLastUpdated(new Date());
							stockOrderDetail.setCreatedBy(currentUser.getUserId());
							stockOrderDetail.setUpdatedBy(currentUser.getUserId());
							stockOrderDetail.setCompany(currentUser.getCompany());
							stockOrderDetailsAddList.add(stockOrderDetail);
							/*stockOrderDetailService.addStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());
							util.AuditTrail(request, currentUser, "PurchaseOrderDetails.addStockOrderDetail", 
									"User "+ currentUser.getUserEmail()+" Added StockOrderDetail+"+stockOrderDetailBean.getStockOrderDetailId()+" successfully ",false);*/
						}
					}			
					if(preStockOrderDetailList != null){
						if(preStockOrderDetailList.size() > 0)
						{
							for(StockOrderDetail stockOrderDetail : preStockOrderDetailList)
							{
								stockOrderDetailsDeleteList.add(stockOrderDetail);
							}
						}	
					}
					if(stockOrderDetailsUpdateList.size() > 0){
						stockOrderDetailService.updateStockOrderDetailsList(stockOrderDetailsUpdateList, currentUser.getCompany().getCompanyId());
					}
					if(stockOrderDetailsAddList.size() > 0){
						stockOrderDetailService.addStockOrderDetailsList(stockOrderDetailsAddList, currentUser.getCompany().getCompanyId());
					}
					if(stockOrderDetailsDeleteList.size() > 0){
						stockOrderDetailService.deleteStockOrderDetailsList(stockOrderDetailsDeleteList, currentUser.getCompany().getCompanyId());
					}
					stockOrder.setStatus(statusService.getStatusByStatusId(2));  //in Progress status
					stockOrder.setOrdrRecvDate(new Date());
					stockOrder.setLastUpdated(new Date());
					stockOrder.setUpdatedBy(currentUser.getUserId());				
					stockOrderService.updateStockOrder(stockOrder,currentUser.getCompany().getCompanyId());
					String layOutPath = LayOutPageConstants.STOCKCONTROL;
					if(stockOrder.getStockOrderType().getStockOrderTypeId() == 1){
						layOutPath = LayOutPageConstants.PO_Create_RECV_EDIT;
					}
					if(stockOrder.getStockOrderType().getStockOrderTypeId() == 2){
						layOutPath = LayOutPageConstants.STOCK_RETURN_EDIT_PRODUCTS;
					}
					if(stockOrder.getStockOrderType().getStockOrderTypeId() == 3){
						layOutPath = LayOutPageConstants.STOCK_TRANSFER_EDIT_PRODUCTS;
					}
					if(stockOrder.getStockOrderType().getStockOrderTypeId() == 4){
						layOutPath = LayOutPageConstants.STOCK_RETURN_TO_WAREHOUSE_EDIT_PRODUCTS;
					}
					if(stockOrder.getStockOrderType().getStockOrderTypeId() == 5){
						layOutPath = LayOutPageConstants.STOCK_SUPPLIER_TRANSFER_EDIT_PRODUCTS;
					}
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,layOutPath);
				}else{

					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.addStockOrderDetail", "User "+ 
							currentUser.getUserEmail()+" Unable to add StockOrderDetail+"+ stockOrderDetailBeansList.get(0).getStockOrderDetailId(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderController.addStockOrder",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateAndReceiveStockOrderDetails/{sessionId}/{grandTotal}", method = RequestMethod.POST)
	public @ResponseBody Response updateAndReceiveStockOrderDetails(@PathVariable("sessionId") String sessionId,
			@PathVariable("grandTotal") String grandTotal,
			@RequestBody List<StockOrderDetailBean> stockOrderDetailBeansList, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariant> outletProductVariantList = new ArrayList<>();
			List<Product> outletProductList = new ArrayList<>();
			List<StockOrderDetail> stockOrderDetailsUpdateList = new ArrayList<>();
			List<StockOrderDetail> stockOrderDetailsDeleteList = new ArrayList<>();
			List<StockOrderDetail> stockOrderDetailsAddList = new ArrayList<>();
			List<Product> productUpdateList = new ArrayList<>();
			List<ProductVariant> productVariantUpdateList = new ArrayList<>();
			try {			
				if (stockOrderDetailBeansList.size() > 0) {	
					StockOrder stockOrder = stockOrderService.getStockOrderByStockOrderID(Integer.parseInt(stockOrderDetailBeansList.get(0).getStockOrderId()),currentUser.getCompany().getCompanyId());
					//List<StockOrderDetail> preStockOrderDetailList = stockOrderDetailService.getStockOrderDetailByStockOrderId(Integer.parseInt(stockOrderDetailBeansList.get(0).getStockOrderId()),currentUser.getCompany().getCompanyId());
					//outletProductVariantList = productVariantService.getAllProductVariantsByOutletId(stockOrder.getOutletByOutletAssocicationId().getOutletId(),currentUser.getCompany().getCompanyId());
					//outletProductList = productService.getAllProductsByOutletId(stockOrder.getOutletByOutletAssocicationId().getOutletId());
					Contact contact = supplierService.getContactByID(stockOrder.getContactId(),currentUser.getCompany().getCompanyId());
					Map<Integer, Product> productsMap = new HashMap<>();
					List<Product> products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
					if(products!=null){
						for(Product product:products){
							productsMap.put(product.getProductId(), product);
							if(product.getOutlet().getOutletId() == stockOrder.getOutletByOutletAssocicationId().getOutletId()){
								outletProductList.add(product);
							}
						}
					}
					Map<Integer, ProductVariant> productVariantsMap = new HashMap<>();
					List<ProductVariant> productVariants = productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
					if(productVariants!=null){
						for(ProductVariant productVariant:productVariants){
							productVariantsMap.put(productVariant.getProductVariantId(), productVariant);
							if(productVariant.getOutlet().getOutletId() == stockOrder.getOutletByOutletAssocicationId().getOutletId()){
								outletProductVariantList.add(productVariant);
							}
						}
					}
					//Stock Order Map Region
					List<StockOrderDetail> stockOrderDetails = new ArrayList<>();
					Map<Integer, List<StockOrderDetail>> stockOrderDetailsMap = new HashMap<>();
					Map<Integer, StockOrderDetail> stockOrderDetailsByDetailIDMap = new HashMap<>();
					stockOrderDetails = stockOrderDetailService.getAllStockOrderDetails(currentUser.getCompany().getCompanyId());
					if(stockOrderDetails!=null){
						for(StockOrderDetail stockOrderDetail:stockOrderDetails){
							List<StockOrderDetail> addedstockOrderDetails = stockOrderDetailsMap.get(stockOrderDetail.getStockOrder().getStockOrderId());
							if(addedstockOrderDetails!=null){
								addedstockOrderDetails.add(stockOrderDetail);
								stockOrderDetailsMap.put(stockOrderDetail.getStockOrder().getStockOrderId(), addedstockOrderDetails);
							}else{
								addedstockOrderDetails = new ArrayList<>();
								addedstockOrderDetails.add(stockOrderDetail);
								stockOrderDetailsMap.put(stockOrderDetail.getStockOrder().getStockOrderId(), addedstockOrderDetails);
							}
							stockOrderDetailsByDetailIDMap.put(stockOrderDetail.getStockOrderDetailId(), stockOrderDetail);
						}
					}
					//End Region
					List<StockOrderDetail> preStockOrderDetailList = stockOrderDetailsMap.get(Integer.parseInt(stockOrderDetailBeansList.get(0).getStockOrderId()));
					for(StockOrderDetailBean stockOrderDetailBean : stockOrderDetailBeansList)
					{
						if(stockOrderDetailBean.getStockOrderDetailId() != null && !stockOrderDetailBean.getStockOrderDetailId().equalsIgnoreCase("")){
							StockOrderDetail stockOrderDetail = stockOrderDetailsByDetailIDMap.get(Integer.parseInt(stockOrderDetailBean.getStockOrderDetailId()));
							if(preStockOrderDetailList != null){
								int i = 0;
								int index = -1;
								for (StockOrderDetail preStockOrderDetail : preStockOrderDetailList){
									int stockOrderDetailId = stockOrderDetail.getStockOrderDetailId();
									int preStockOrderDetailId = preStockOrderDetail.getStockOrderDetailId();
									if(stockOrderDetailId == preStockOrderDetailId)
									{
										index = i;
										break;
									}
									i++;
								}
								if(index != -1){
									preStockOrderDetailList.remove(index);
								}
							}
							stockOrderDetail.setOrderProdQty(Integer.parseInt(stockOrderDetailBean.getOrderProdQty()));
							stockOrderDetail.setOrdrSupplyPrice(new BigDecimal(stockOrderDetailBean.getOrdrSupplyPrice()));
							if(stockOrderDetailBean.getRecvProdQty() != null && !stockOrderDetailBean.getRecvProdQty().equalsIgnoreCase("")){
								stockOrderDetail.setRecvProdQty(Integer.parseInt(stockOrderDetailBean.getRecvProdQty()));
							}
							if(stockOrderDetailBean.getRecvSupplyPrice() != null && !stockOrderDetailBean.getRecvSupplyPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRecvSupplyPrice(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
							}
							if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
								stockOrderDetail.setIsProduct(false);
								ProductVariant productVariant = productVariantsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
								//check that variant is present in outlet or not
								boolean found = false;
								for(ProductVariant outletProductVariant : outletProductVariantList)
								{
									//									UUID u1 = UUID.fromString(outletProductVariant.getProductVariantUuid());
									//									UUID u2 = UUID.fromString(productVariant.getProductVariantUuid());
									if(outletProductVariant.getProductVariantUuid().equals(productVariant.getProductVariantUuid())){
										int preQuantity = outletProductVariant.getCurrentInventory();
										outletProductVariant.setCurrentInventory(preQuantity + stockOrderDetail.getRecvProdQty());
										productVariant.setSupplyPriceExclTax(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
										productVariant.setLastUpdated(new Date());
										productVariant.setUserByUpdatedBy(currentUser);
										productVariant.setLastUpdated(new Date());
										productVariant.setUserByUpdatedBy(currentUser);
										//productVariantService.updateProductVariant(outletProductVariant, Actions.UPDATE, outletProductVariant.getCurrentInventory(),currentUser.getCompany());
										productVariantUpdateList.add(outletProductVariant);
										Product parentProduct = productsMap.get(productVariant.getProduct().getProductId());
										parentProduct.setContact(contact);
										//productService.updateProduct(parentProduct, Actions.UPDATE, parentProduct.getCurrentInventory(),currentUser.getCompany());
										productUpdateList.add(parentProduct);
										stockOrderDetail.setProductVariant(outletProductVariant);
										found = true;
										break;
									}
								}
								if(!found)
								{	
									Product parentProduct = productsMap.get(productVariant.getProduct().getProductId());
									boolean isPresent = false;
									Product newProduct = parentProduct;
									for(Product outletProduct: outletProductList){
										//										UUID u1 = UUID.fromString(outletProduct.getProductUuid());
										//										UUID u2 = UUID.fromString(parentProduct.getProductUuid());
										if(outletProduct.getProductUuid().equals(parentProduct.getProductUuid())){
											isPresent = true;
											newProduct = outletProduct;
											break;
										}
									}
									if(!isPresent)
									{
										newProduct.setOutlet(stockOrder.getOutletByOutletAssocicationId());
										newProduct.setCurrentInventory(stockOrderDetail.getRecvProdQty());
										newProduct.setCreatedDate(new Date());				
										newProduct.setLastUpdated(new Date());
										newProduct.setUserByCreatedBy(currentUser);
										newProduct.setUserByUpdatedBy(currentUser);
										newProduct.setCompany(currentUser.getCompany());
										newProduct.setContact(contact);
										newProduct = productService.addProduct(newProduct, Actions.CREATE, stockOrderDetail.getRecvProdQty(),currentUser.getCompany());
										outletProductList.add(newProduct);
									}
									ProductVariant newProductVariant = productVariant; 
									newProductVariant.setOutlet(stockOrder.getOutletByOutletAssocicationId());
									newProductVariant.setCurrentInventory(stockOrderDetail.getRecvProdQty());
									newProductVariant.setSupplyPriceExclTax(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
									newProductVariant.setProduct(newProduct);
									newProductVariant.setCreatedDate(new Date());				
									newProductVariant.setLastUpdated(new Date());
									newProductVariant.setUserByCreatedBy(currentUser);
									newProductVariant.setUserByUpdatedBy(currentUser);
									newProductVariant.setCompany(currentUser.getCompany());
									newProductVariant = productVariantService.addProductVariant(newProductVariant, Actions.CREATE, stockOrderDetail.getRecvProdQty(),currentUser.getCompany(),newProduct.getProductUuid());	
									outletProductVariantList.add(newProductVariant);
									stockOrderDetail.setProductVariant(newProductVariant);
								}
							}
							else{
								Product product = productsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
								stockOrderDetail.setIsProduct(true);
								boolean found = false;
								for(Product outletProduct : outletProductList){
									//									UUID u1 = UUID.fromString(outletProduct.getProductUuid());
									//									UUID u2 = UUID.fromString(product.getProductUuid());
									if(outletProduct.getProductUuid().equals(product.getProductUuid())){
										int preQuantity = outletProduct.getCurrentInventory();
										outletProduct.setCurrentInventory(preQuantity + stockOrderDetail.getRecvProdQty());	
										outletProduct.setSupplyPriceExclTax(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));	
										outletProduct.setLastUpdated(new Date());
										outletProduct.setUserByUpdatedBy(currentUser);
										outletProduct.setContact(contact);
										productUpdateList.add(outletProduct);
										//productService.updateProduct(outletProduct, Actions.UPDATE, outletProduct.getCurrentInventory(),currentUser.getCompany());
										stockOrderDetail.setProduct(outletProduct);
										found = true;
										break;
									}
								}
								if(!found){
									Product newProduct = product; 
									newProduct.setOutlet(stockOrder.getOutletByOutletAssocicationId());
									newProduct.setCurrentInventory(stockOrderDetail.getRecvProdQty());
									newProduct.setSupplyPriceExclTax(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
									newProduct.setCreatedDate(new Date());				
									newProduct.setLastUpdated(new Date());
									newProduct.setUserByCreatedBy(currentUser);
									newProduct.setUserByUpdatedBy(currentUser);
									newProduct.setCompany(currentUser.getCompany());
									newProduct.setContact(contact);
									newProduct = productService.addProduct(newProduct, Actions.CREATE, stockOrderDetail.getRecvProdQty(),currentUser.getCompany());
									outletProductList.add(newProduct);
									stockOrderDetail.setProduct(newProduct);
								}
							}

							stockOrderDetail.setStockOrder(stockOrder);
							stockOrderDetail.setActiveIndicator(true);					
							stockOrderDetail.setLastUpdated(new Date());
							stockOrderDetail.setUpdatedBy(currentUser.getUserId());
							stockOrderDetailsUpdateList.add(stockOrderDetail);
							/*stockOrderDetailService.updateStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());
							util.AuditTrail(request, currentUser, "PurchaseOrderDetails.updateStockOrderDetail", 
									"User "+ currentUser.getUserEmail()+" Update StockOrderDetail+"+stockOrderDetailBean.getStockOrderDetailId()+" successfully ",false);*/
						}
						else
						{
							StockOrderDetail stockOrderDetail = new StockOrderDetail();
							//stockOrderDetail.setStockOrderDetailId(Integer.parseInt(stockOrderDetailBean.getStockOrderDetailId()));
							stockOrderDetail.setOrderProdQty(Integer.parseInt(stockOrderDetailBean.getOrderProdQty()));
							stockOrderDetail.setOrdrSupplyPrice(new BigDecimal(stockOrderDetailBean.getOrdrSupplyPrice()));
							if(stockOrderDetailBean.getRecvProdQty() != null && !stockOrderDetailBean.getRecvProdQty().equalsIgnoreCase("")){
								stockOrderDetail.setRecvProdQty(Integer.parseInt(stockOrderDetailBean.getRecvProdQty()));
							}
							if(stockOrderDetailBean.getRecvSupplyPrice() != null && !stockOrderDetailBean.getRecvSupplyPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRecvSupplyPrice(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
							}							
							if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
								ProductVariant productVariant = productVariantsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
								boolean found = false;
								for(ProductVariant outletProductVariant : outletProductVariantList)
								{
									//									UUID u1 = UUID.fromString(outletProductVariant.getProductVariantUuid());
									//									UUID u2 = UUID.fromString(productVariant.getProductVariantUuid());
									if(outletProductVariant.getProductVariantUuid().equals(productVariant.getProductVariantUuid())){
										int preQuantity = outletProductVariant.getCurrentInventory();
										outletProductVariant.setCurrentInventory(preQuantity + stockOrderDetail.getRecvProdQty());
										productVariant.setSupplyPriceExclTax(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
										productVariant.setLastUpdated(new Date());
										productVariant.setUserByUpdatedBy(currentUser);
										productVariant.setLastUpdated(new Date());
										productVariant.setUserByUpdatedBy(currentUser);										
										//productVariantService.updateProductVariant(outletProductVariant, Actions.UPDATE, outletProductVariant.getCurrentInventory(),currentUser.getCompany());
										productVariantUpdateList.add(outletProductVariant);
										Product parentProduct = productsMap.get(productVariant.getProduct().getProductId());
										parentProduct.setContact(contact);
										//productService.updateProduct(parentProduct, Actions.UPDATE, parentProduct.getCurrentInventory(),currentUser.getCompany());
										productUpdateList.add(parentProduct);
										stockOrderDetail.setProductVariant(outletProductVariant);
										found = true;
										break;
									}
								}
								if(!found)
								{	
									Product parentProduct = productsMap.get(productVariant.getProduct().getProductId());
									boolean isPresent = false;
									Product newProduct = parentProduct;
									for(Product outletProduct: outletProductList){
										//										UUID u1 = UUID.fromString(outletProduct.getProductUuid());
										//										UUID u2 = UUID.fromString(parentProduct.getProductUuid());
										if(outletProduct.getProductUuid().equals(parentProduct.getProductUuid())){
											isPresent = true;
											newProduct = outletProduct;
											break;
										}
									}
									if(!isPresent)
									{
										newProduct.setOutlet(stockOrder.getOutletByOutletAssocicationId());
										newProduct.setCurrentInventory(stockOrderDetail.getRecvProdQty());
										newProduct.setCreatedDate(new Date());				
										newProduct.setLastUpdated(new Date());
										newProduct.setUserByCreatedBy(currentUser);
										newProduct.setUserByUpdatedBy(currentUser);
										newProduct.setCompany(currentUser.getCompany());
										newProduct.setContact(contact);
										newProduct = productService.addProduct(newProduct, Actions.CREATE, stockOrderDetail.getRecvProdQty(),currentUser.getCompany());
										outletProductList.add(newProduct);
									}
									ProductVariant newProductVariant = productVariant; 
									newProductVariant.setOutlet(stockOrder.getOutletByOutletAssocicationId());
									newProductVariant.setCurrentInventory(stockOrderDetail.getRecvProdQty());
									newProductVariant.setSupplyPriceExclTax(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
									newProductVariant.setProduct(newProduct);
									newProductVariant.setCreatedDate(new Date());				
									newProductVariant.setLastUpdated(new Date());
									newProductVariant.setUserByCreatedBy(currentUser);
									newProductVariant.setUserByUpdatedBy(currentUser);
									newProductVariant.setCompany(currentUser.getCompany());
									newProductVariant = productVariantService.addProductVariant(newProductVariant, Actions.CREATE, stockOrderDetail.getRecvProdQty(),currentUser.getCompany(),newProduct.getProductUuid());	
									outletProductVariantList.add(newProductVariant);
									stockOrderDetail.setProductVariant(newProductVariant);
								}
							}
							else{
								Product product = productsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
								stockOrderDetail.setIsProduct(true);
								boolean found = false;
								for(Product outletProduct : outletProductList){
									//									UUID u1 = UUID.fromString(outletProduct.getProductUuid());
									//									UUID u2 = UUID.fromString(product.getProductUuid());
									if(outletProduct.getProductUuid().equals(product.getProductUuid())){
										int preQuantity = outletProduct.getCurrentInventory();
										outletProduct.setCurrentInventory(preQuantity + stockOrderDetail.getRecvProdQty());	
										outletProduct.setSupplyPriceExclTax(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));	
										outletProduct.setLastUpdated(new Date());
										outletProduct.setUserByUpdatedBy(currentUser);
										outletProduct.setContact(contact);
										//productService.updateProduct(outletProduct, Actions.UPDATE, outletProduct.getCurrentInventory(),currentUser.getCompany());
										productUpdateList.add(outletProduct);
										stockOrderDetail.setProduct(outletProduct);
										found = true;
										break;
									}
								}
								if(!found){
									Product newProduct = product; 
									newProduct.setOutlet(stockOrder.getOutletByOutletAssocicationId());
									newProduct.setCurrentInventory(stockOrderDetail.getRecvProdQty());
									newProduct.setSupplyPriceExclTax(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
									newProduct.setCreatedDate(new Date());				
									newProduct.setLastUpdated(new Date());
									newProduct.setUserByCreatedBy(currentUser);
									newProduct.setUserByUpdatedBy(currentUser);
									newProduct.setCompany(currentUser.getCompany());
									newProduct.setContact(contact);
									newProduct = productService.addProduct(newProduct, Actions.CREATE, stockOrderDetail.getRecvProdQty(),currentUser.getCompany());
									outletProductList.add(newProduct);
									stockOrderDetail.setProduct(newProduct);
								}
							}												
							stockOrderDetail.setStockOrder(stockOrder);
							stockOrderDetail.setActiveIndicator(true);			
							stockOrderDetail.setCreatedDate(new Date());				
							stockOrderDetail.setLastUpdated(new Date());
							stockOrderDetail.setCreatedBy(currentUser.getUserId());
							stockOrderDetail.setUpdatedBy(currentUser.getUserId());
							stockOrderDetail.setCompany(currentUser.getCompany());
							stockOrderDetailsAddList.add(stockOrderDetail);
							/*stockOrderDetailService.addStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());
							util.AuditTrail(request, currentUser, "PurchaseOrderDetails.addStockOrderDetail", 
									"User "+ currentUser.getUserEmail()+" Added StockOrderDetail+"+stockOrderDetailBean.getStockOrderDetailId()+" successfully ",false);*/
						}
					}
					if(preStockOrderDetailList != null){
						if(preStockOrderDetailList.size() > 0)
						{
							for(StockOrderDetail stockOrderDetail : preStockOrderDetailList)
							{
								stockOrderDetailsDeleteList.add(stockOrderDetail);
								//stockOrderDetailService.deleteStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());
							}
						}	
					}			

					if(stockOrderDetailsUpdateList.size() > 0){
						stockOrderDetailService.updateStockOrderDetailsList(stockOrderDetailsUpdateList, currentUser.getCompany().getCompanyId());
					}
					if(stockOrderDetailsAddList.size() > 0){
						stockOrderDetailService.addStockOrderDetailsList(stockOrderDetailsAddList, currentUser.getCompany().getCompanyId());
					}
					if(stockOrderDetailsDeleteList.size() > 0){
						stockOrderDetailService.deleteStockOrderDetailsList(stockOrderDetailsDeleteList, currentUser.getCompany().getCompanyId());
					}
					if(productUpdateList.size()>0){
						productService.updateProductList(productUpdateList, currentUser.getCompany());
					}
					if(productVariantUpdateList.size()>0){
						productVariantService.updateProductVariantList(productVariantUpdateList, currentUser.getCompany());
					}
					stockOrder.setStatus(statusService.getStatusByStatusId(3));  //completed status
					stockOrder.setOrdrRecvDate(new Date());
					stockOrder.setLastUpdated(new Date());
					stockOrder.setUpdatedBy(currentUser.getUserId());				
					stockOrderService.updateStockOrder(stockOrder,currentUser.getCompany().getCompanyId());
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.updateandRecStockOrderDetail", 
							"User "+ currentUser.getUserEmail()+"Updated Supplier+"+stockOrder.getStockOrderId()+" successfully ",false);
					Contact supplier = supplierService.getContactByID(stockOrder.getContactId(), currentUser.getCompany().getCompanyId());
					if(supplier.getContactBalance() == null){
						supplier.setContactBalance(BigDecimal.ZERO);
					}
					supplier.setContactBalance(supplier.getContactBalance().add(new BigDecimal(grandTotal)));
					supplier.setLastUpdated(new Date());
					supplier.setUpdatedBy(currentUser.getUserId());
					supplierService.updateContact(supplier, currentUser.getCompany().getCompanyId());
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.updateandRecStockOrderDetail", 
							"User "+ currentUser.getUserEmail()+"Updated Supplier+"+supplier.getContactId()+" successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.STOCKCONTROL);
				}else{
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.addStockOrderDetail", "User "+ 
							currentUser.getUserEmail()+" Unable to add StockOrderDetail+"+ stockOrderDetailBeansList.get(0).getStockOrderDetailId(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderController.addStockOrder",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateAndReturnStockOrderDetails/{sessionId}/{grandTotal}", method = RequestMethod.POST)
	public @ResponseBody Response updateAndReturnStockOrderDetails(@PathVariable("sessionId") String sessionId,
			@PathVariable("grandTotal") String grandTotal,
			@RequestBody List<StockOrderDetailBean> stockOrderDetailBeansList, HttpServletRequest request){

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			try {			
				if (stockOrderDetailBeansList.size() > 0) {	
					Map<Integer, Product> productsMap = new HashMap<>();
					List<Product> products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
					List<StockOrderDetail> stockOrderDetailsUpdateList = new ArrayList<>();
					List<StockOrderDetail> stockOrderDetailsDeleteList = new ArrayList<>();
					List<StockOrderDetail> stockOrderDetailsAddList = new ArrayList<>();
					List<Product> productUpdateList = new ArrayList<>();
					List<ProductVariant> productVariantUpdateList = new ArrayList<>();
					if(products!=null){
						for(Product product:products){
							productsMap.put(product.getProductId(), product);
						}
					}
					Map<Integer, ProductVariant> productVariantsMap = new HashMap<>();
					List<ProductVariant> productVariants = productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
					if(productVariants!=null){
						for(ProductVariant productVariant:productVariants){
							productVariantsMap.put(productVariant.getProductVariantId(), productVariant);
						}
					}
					//Stock Order Map Region
					List<StockOrderDetail> stockOrderDetails = new ArrayList<>();
					Map<Integer, List<StockOrderDetail>> stockOrderDetailsMap = new HashMap<>();
					Map<Integer, StockOrderDetail> stockOrderDetailsByDetailIDMap = new HashMap<>();
					stockOrderDetails = stockOrderDetailService.getAllStockOrderDetails(currentUser.getCompany().getCompanyId());
					if(stockOrderDetails!=null){
						for(StockOrderDetail stockOrderDetail:stockOrderDetails){
							List<StockOrderDetail> addedstockOrderDetails = stockOrderDetailsMap.get(stockOrderDetail.getStockOrder().getStockOrderId());
							if(addedstockOrderDetails!=null){
								addedstockOrderDetails.add(stockOrderDetail);
								stockOrderDetailsMap.put(stockOrderDetail.getStockOrder().getStockOrderId(), addedstockOrderDetails);
							}else{
								addedstockOrderDetails = new ArrayList<>();
								addedstockOrderDetails.add(stockOrderDetail);
								stockOrderDetailsMap.put(stockOrderDetail.getStockOrder().getStockOrderId(), addedstockOrderDetails);
							}
							stockOrderDetailsByDetailIDMap.put(stockOrderDetail.getStockOrderDetailId(), stockOrderDetail);
						}
					}
					//End Region
					StockOrder stockOrder = stockOrderService.getStockOrderByStockOrderID(Integer.parseInt(stockOrderDetailBeansList.get(0).getStockOrderId()),currentUser.getCompany().getCompanyId());
					List<StockOrderDetail> preStockOrderDetailList = stockOrderDetailsMap.get(Integer.parseInt(stockOrderDetailBeansList.get(0).getStockOrderId()));
					for(StockOrderDetailBean stockOrderDetailBean : stockOrderDetailBeansList)
					{
						if(stockOrderDetailBean.getStockOrderDetailId() != null && !stockOrderDetailBean.getStockOrderDetailId().equalsIgnoreCase("")){
							StockOrderDetail stockOrderDetail = stockOrderDetailsByDetailIDMap.get(Integer.parseInt(stockOrderDetailBean.getStockOrderDetailId()));
							if(preStockOrderDetailList != null){
								int i = 0;
								int index = -1;
								for (StockOrderDetail preStockOrderDetail : preStockOrderDetailList){
									int stockOrderDetailId = stockOrderDetail.getStockOrderDetailId();
									int preStockOrderDetailId = preStockOrderDetail.getStockOrderDetailId();
									if(stockOrderDetailId == preStockOrderDetailId)
									{
										index = i;
										break;
									}
									i++;
								}
								if(index != -1){
									preStockOrderDetailList.remove(index);
								}
							}
							stockOrderDetail.setOrderProdQty(Integer.parseInt(stockOrderDetailBean.getOrderProdQty()));
							stockOrderDetail.setOrdrSupplyPrice(new BigDecimal(stockOrderDetailBean.getOrdrSupplyPrice()));
							if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
								ProductVariant productVariant = productVariantsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
								stockOrderDetail.setProductVariant(productVariant);
								stockOrderDetail.setIsProduct(false);
								int preQuantity = productVariant.getCurrentInventory();
								productVariant.setCurrentInventory(preQuantity - stockOrderDetail.getOrderProdQty());
								productVariant.setLastUpdated(new Date());
								productVariant.setUserByUpdatedBy(currentUser);
								//productVariantService.updateProductVariant(productVariant, Actions.UPDATE, productVariant.getCurrentInventory(),currentUser.getCompany());
								productVariantUpdateList.add(productVariant);
							}
							else{
								Product product = productsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
								stockOrderDetail.setProduct(product);
								stockOrderDetail.setIsProduct(true);
								int preQuantity = product.getCurrentInventory();
								product.setCurrentInventory(preQuantity - stockOrderDetail.getOrderProdQty());
								product.setLastUpdated(new Date());
								product.setUserByUpdatedBy(currentUser);
								//productService.updateProduct(product, Actions.INVENTORY_SUBTRACT, product.getCurrentInventory(),currentUser.getCompany());
								productUpdateList.add(product);
							}
							if(stockOrderDetailBean.getRecvProdQty() != null && !stockOrderDetailBean.getRecvProdQty().equalsIgnoreCase("")){
								stockOrderDetail.setRecvProdQty(Integer.parseInt(stockOrderDetailBean.getRecvProdQty()));
							}
							if(stockOrderDetailBean.getRecvSupplyPrice() != null && !stockOrderDetailBean.getRecvSupplyPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRecvSupplyPrice(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
							}
							stockOrderDetail.setStockOrder(stockOrder);
							stockOrderDetail.setActiveIndicator(true);					
							stockOrderDetail.setLastUpdated(new Date());
							stockOrderDetail.setUpdatedBy(currentUser.getUserId());
							stockOrderDetailsUpdateList.add(stockOrderDetail);
							/*stockOrderDetailService.updateStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());
							util.AuditTrail(request, currentUser, "PurchaseOrderDetails.updateStockOrderDetail", 
									"User "+ currentUser.getUserEmail()+" Update StockOrderDetail+"+stockOrderDetailBean.getStockOrderDetailId()+" successfully ",false);*/
						}
						else
						{
							StockOrderDetail stockOrderDetail = new StockOrderDetail();
							//stockOrderDetail.setStockOrderDetailId(Integer.parseInt(stockOrderDetailBean.getStockOrderDetailId()));
							stockOrderDetail.setOrderProdQty(Integer.parseInt(stockOrderDetailBean.getOrderProdQty()));
							stockOrderDetail.setOrdrSupplyPrice(new BigDecimal(stockOrderDetailBean.getOrdrSupplyPrice()));
							if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
								ProductVariant productVariant = productVariantsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));								
								stockOrderDetail.setProductVariant(productVariant);
								stockOrderDetail.setIsProduct(false);
								int preQuantity = productVariant.getCurrentInventory();
								productVariant.setCurrentInventory(preQuantity - stockOrderDetail.getOrderProdQty());
								productVariant.setLastUpdated(new Date());
								productVariant.setUserByUpdatedBy(currentUser);
								//productVariantService.updateProductVariant(productVariant, Actions.UPDATE, productVariant.getCurrentInventory(),currentUser.getCompany());
								productVariantUpdateList.add(productVariant);
							}
							else{
								Product product = productsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
								stockOrderDetail.setProduct(product);
								stockOrderDetail.setIsProduct(true);
								int preQuantity = product.getCurrentInventory();
								product.setCurrentInventory(preQuantity - stockOrderDetail.getOrderProdQty());
								product.setLastUpdated(new Date());
								product.setUserByUpdatedBy(currentUser);
								//productService.updateProduct(product, Actions.UPDATE, product.getCurrentInventory(),currentUser.getCompany());
								productUpdateList.add(product);
							}
							if(stockOrderDetailBean.getRecvProdQty() != null && !stockOrderDetailBean.getRecvProdQty().equalsIgnoreCase("")){
								stockOrderDetail.setRecvProdQty(Integer.parseInt(stockOrderDetailBean.getRecvProdQty()));
							}
							if(stockOrderDetailBean.getRecvSupplyPrice() != null && !stockOrderDetailBean.getRecvSupplyPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRecvSupplyPrice(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
							}					
							stockOrderDetail.setStockOrder(stockOrder);
							stockOrderDetail.setActiveIndicator(true);			
							stockOrderDetail.setCreatedDate(new Date());				
							stockOrderDetail.setLastUpdated(new Date());
							stockOrderDetail.setCreatedBy(currentUser.getUserId());
							stockOrderDetail.setUpdatedBy(currentUser.getUserId());
							stockOrderDetail.setCompany(currentUser.getCompany());
							stockOrderDetailsAddList.add(stockOrderDetail);
							/*stockOrderDetailService.addStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());
							util.AuditTrail(request, currentUser, "PurchaseOrderDetails.addStockOrderDetail", 
									"User "+ currentUser.getUserEmail()+" Added StockOrderDetail+"+stockOrderDetailBean.getStockOrderDetailId()+" successfully ",false);*/
						}
					}
					if(preStockOrderDetailList != null){
						if(preStockOrderDetailList.size() > 0)
						{
							for(StockOrderDetail stockOrderDetail : preStockOrderDetailList)
							{
								stockOrderDetailsDeleteList.add(stockOrderDetail);
								//stockOrderDetailService.deleteStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());
							}
						}	
					}
					if(stockOrderDetailsUpdateList.size() > 0){
						stockOrderDetailService.updateStockOrderDetailsList(stockOrderDetailsUpdateList, currentUser.getCompany().getCompanyId());
					}
					if(stockOrderDetailsAddList.size() > 0){
						stockOrderDetailService.addStockOrderDetailsList(stockOrderDetailsAddList, currentUser.getCompany().getCompanyId());
					}
					if(stockOrderDetailsDeleteList.size() > 0){
						stockOrderDetailService.deleteStockOrderDetailsList(stockOrderDetailsDeleteList, currentUser.getCompany().getCompanyId());
					}
					if(productUpdateList.size()>0){
						productService.updateProductList(productUpdateList, currentUser.getCompany());
					}
					if(productVariantUpdateList.size()>0){
						productVariantService.updateProductVariantList(productVariantUpdateList, currentUser.getCompany());
					}
					//StockOrder stockOrder = stockOrderService.getStockOrderByStockOrderID(Integer.parseInt(stockOrderDetailBeansList.get(0).getStockOrderId()),currentUser.getCompany().getCompanyId());				
					stockOrder.setStatus(statusService.getStatusByStatusId(3)); //completed status
					stockOrder.setOrdrRecvDate(new Date());
					stockOrder.setLastUpdated(new Date());
					stockOrder.setUpdatedBy(currentUser.getUserId());
					stockOrderService.updateStockOrder(stockOrder,currentUser.getCompany().getCompanyId());
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.updateandRecStockOrderDetail", 
							"User "+ currentUser.getUserEmail()+"Updated Supplier+"+stockOrder.getStockOrderId()+" successfully ",false);
					Contact supplier = supplierService.getContactByID(stockOrder.getContactId(), currentUser.getCompany().getCompanyId());
					if(supplier.getContactBalance() == null){
						supplier.setContactBalance(BigDecimal.ZERO);
					}
					supplier.setContactBalance(supplier.getContactBalance().subtract(new BigDecimal(grandTotal)));
					supplier.setLastUpdated(new Date());
					supplier.setUpdatedBy(currentUser.getUserId());
					supplierService.updateContact(supplier, currentUser.getCompany().getCompanyId());
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.updateandRecStockOrderDetail", 
							"User "+ currentUser.getUserEmail()+"Updated Supplier+"+supplier.getContactId()+" successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.STOCKCONTROL);
				}else{
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.addStockOrderDetail", "User "+ 
							currentUser.getUserEmail()+" Unable to add StockOrderDetail+"+ stockOrderDetailBeansList.get(0).getStockOrderDetailId(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderController.addStockOrder",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateAndTransferStockOrderDetails/{sessionId}/{grandTotal}", method = RequestMethod.POST)
	public @ResponseBody Response updateAndTransferStockOrderDetails(@PathVariable("sessionId") String sessionId,
			@PathVariable("grandTotal") String grandTotal,
			@RequestBody List<StockOrderDetailBean> stockOrderDetailBeansList, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			try {			
				if (stockOrderDetailBeansList.size() > 0) {	
					Map<String, ProductVariant> recvProductVariantList = new HashMap();
					Map<String, Product> recvProductList = new HashMap();
					StockOrder stockOrder = stockOrderService.getStockOrderByStockOrderID(Integer.parseInt(stockOrderDetailBeansList.get(0).getStockOrderId()),currentUser.getCompany().getCompanyId());
					Map<Integer, Product> productsMap = new HashMap<>();
					List<Product> products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
					List<StockOrderDetail> stockOrderDetailsUpdateList = new ArrayList<>();
					List<StockOrderDetail> stockOrderDetailsDeleteList = new ArrayList<>();
					List<StockOrderDetail> stockOrderDetailsAddList = new ArrayList<>();
					List<Product> productUpdateList = new ArrayList<>();
					List<ProductVariant> productVariantUpdateList = new ArrayList<>();
					if(products!=null){
						for(Product product:products){
							productsMap.put(product.getProductId(), product);
							if(product.getOutlet().getOutletId() == stockOrder.getOutletByOutletAssocicationId().getOutletId()){
								recvProductList.put(product.getProductUuid(), product);
							}
						}
					}
					Map<Integer, ProductVariant> productVariantsMap = new HashMap<>();
					//Map<Integer, ProductVariant> 
					List<ProductVariant> productVariants = productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
					if(productVariants!=null){
						for(ProductVariant productVariant:productVariants){
							productVariantsMap.put(productVariant.getProductVariantId(), productVariant);
							if(productVariant.getOutlet().getOutletId() == stockOrder.getOutletByOutletAssocicationId().getOutletId()){
								recvProductVariantList.put(productVariant.getProductVariantUuid(), productVariant);
							}
						}
					}
					//Stock Order Map Region
					List<StockOrderDetail> stockOrderDetails = new ArrayList<>();
					Map<Integer, List<StockOrderDetail>> stockOrderDetailsMap = new HashMap<>();
					Map<Integer, StockOrderDetail> stockOrderDetailsByDetailIDMap = new HashMap<>();
					stockOrderDetails = stockOrderDetailService.getAllStockOrderDetails(currentUser.getCompany().getCompanyId());
					if(stockOrderDetails!=null){
						for(StockOrderDetail stockOrderDetail:stockOrderDetails){
							List<StockOrderDetail> addedstockOrderDetails = stockOrderDetailsMap.get(stockOrderDetail.getStockOrder().getStockOrderId());
							if(addedstockOrderDetails!=null){
								addedstockOrderDetails.add(stockOrderDetail);
								stockOrderDetailsMap.put(stockOrderDetail.getStockOrder().getStockOrderId(), addedstockOrderDetails);
							}else{
								addedstockOrderDetails = new ArrayList<>();
								addedstockOrderDetails.add(stockOrderDetail);
								stockOrderDetailsMap.put(stockOrderDetail.getStockOrder().getStockOrderId(), addedstockOrderDetails);
							}
							stockOrderDetailsByDetailIDMap.put(stockOrderDetail.getStockOrderDetailId(), stockOrderDetail);
						}
					}
					//End Region
					List<StockOrderDetail> preStockOrderDetailList = stockOrderDetailsMap.get(Integer.parseInt(stockOrderDetailBeansList.get(0).getStockOrderId()));

					for(StockOrderDetailBean stockOrderDetailBean : stockOrderDetailBeansList)
					{
						if(stockOrderDetailBean.getStockOrderDetailId() != null && !stockOrderDetailBean.getStockOrderDetailId().equalsIgnoreCase("")){
							StockOrderDetail stockOrderDetail = stockOrderDetailsByDetailIDMap.get(Integer.parseInt(stockOrderDetailBean.getStockOrderDetailId()));
							if(preStockOrderDetailList != null){
								int i = 0;
								int index = -1;
								for (StockOrderDetail preStockOrderDetail : preStockOrderDetailList){
									int stockOrderDetailId = stockOrderDetail.getStockOrderDetailId();
									int preStockOrderDetailId = preStockOrderDetail.getStockOrderDetailId();
									if(stockOrderDetailId == preStockOrderDetailId)
									{
										index = i;
										break;
									}
									i++;
								}
								if(index != -1){
									preStockOrderDetailList.remove(index);
								}
							}
							stockOrderDetail.setOrderProdQty(Integer.parseInt(stockOrderDetailBean.getOrderProdQty()));
							stockOrderDetail.setOrdrSupplyPrice(new BigDecimal(stockOrderDetailBean.getOrdrSupplyPrice()));
							ProductVariant productVariant = new ProductVariant();
							Product product = new Product();
							if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
								productVariant = productVariantsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
								stockOrderDetail.setIsProduct(false);
								//stockOrderDetail.setProductVariant(productVariant);
								int preQuantity = productVariant.getCurrentInventory();
								productVariant.setCurrentInventory(preQuantity - stockOrderDetail.getOrderProdQty());						
								productVariant.setLastUpdated(new Date());
								productVariant.setUserByUpdatedBy(currentUser);
								//productVariantService.updateProductVariant(productVariant, Actions.UPDATE, productVariant.getCurrentInventory(),currentUser.getCompany());
								productVariantUpdateList.add(productVariant);
							}
							else{
								product = productsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
								//stockOrderDetail.setProduct(product);
								stockOrderDetail.setIsProduct(true);
								int preQuantity = product.getCurrentInventory();
								product.setCurrentInventory(preQuantity - stockOrderDetail.getOrderProdQty());						
								product.setLastUpdated(new Date());
								product.setUserByUpdatedBy(currentUser);
								//productService.updateProduct(product, Actions.INVENTORY_TRANSFER, product.getCurrentInventory(),currentUser.getCompany());
								productUpdateList.add(product);
							}
							if(stockOrderDetailBean.getRecvProdQty() != null && !stockOrderDetailBean.getRecvProdQty().equalsIgnoreCase("")){
								stockOrderDetail.setRecvProdQty(Integer.parseInt(stockOrderDetailBean.getRecvProdQty()));
							}
							if(stockOrderDetailBean.getRecvSupplyPrice() != null && !stockOrderDetailBean.getRecvSupplyPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRecvSupplyPrice(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
							}
							if(stockOrderDetailBean.getRetailPrice() != null && !stockOrderDetailBean.getRetailPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRetailPrice(new BigDecimal(stockOrderDetailBean.getRetailPrice()));
							}
							boolean found = false;
							//if(stockOrder.getOutletBySourceOutletAssocicationId() != null){
							//recvProductVariantList = productVariantService.getAllProductVariantsByOutletId(stockOrder.getOutletByOutletAssocicationId().getOutletId(),currentUser.getCompany().getCompanyId());
							//recvProductList = productService.getAllProductsByOutletId(stockOrder.getOutletByOutletAssocicationId().getOutletId());
							//}
							if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
								//for(ProductVariant recProductVariant : recvProductVariantList)
								//{
								//									UUID u1 = UUID.fromString(recProductVariant.getProductVariantUuid());
								//									UUID u2 = UUID.fromString(productVariant.getProductVariantUuid());
								ProductVariant recProductVariant = recvProductVariantList.get(productVariant.getProductVariantUuid());
								if(recProductVariant != null){
									int recvPreQuantity = recProductVariant.getCurrentInventory();
									recProductVariant.setCurrentInventory(recvPreQuantity + stockOrderDetail.getOrderProdQty());
									recProductVariant.setSupplyPriceExclTax(productVariant.getSupplyPriceExclTax());
									recProductVariant.setMarkupPrct(productVariant.getMarkupPrct());
									//productVariantService.updateProductVariant(recProductVariant, Actions.UPDATE, recProductVariant.getCurrentInventory(),currentUser.getCompany());
									productVariantUpdateList.add(recProductVariant);
									Product sourceProduct = productsMap.get(productVariant.getProduct().getProductId());
									Product recProduct = productsMap.get(recProductVariant.getProduct().getProductId());
									//recProduct.setContact(supplierService.getContactByID(sourceProduct.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
									recProduct.setSupplyPriceExclTax(sourceProduct.getSupplyPriceExclTax());
									recProduct.setMarkupPrct(sourceProduct.getMarkupPrct());
									//productService.updateProduct(recProduct, Actions.UPDATE, recvPreQuantity + stockOrderDetail.getOrderProdQty(), currentUser.getCompany());
									productUpdateList.add(recProduct);
									found = true;
									//break;
								}
								//}
								if(!found)
								{	
									Product parentProduct = productsMap.get(productVariant.getProduct().getProductId());
									boolean isPresent = false;
									Product newProduct = new Product();
									//for(Product recProduct: recvProductList){
									//										UUID u1 = UUID.fromString(recProduct.getProductUuid());
									//										UUID u2 = UUID.fromString(parentProduct.getProductUuid());
									Product recProduct = recvProductList.get(parentProduct.getProductUuid());
									if(recProduct != null){
										isPresent = true;
										if(!productUpdateList.contains(recProduct)){
											//recProduct.setContact(supplierService.getContactByID(parentProduct.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
											recProduct.setSupplyPriceExclTax(parentProduct.getSupplyPriceExclTax());
											recProduct.setMarkupPrct(parentProduct.getMarkupPrct());
											//productService.updateProduct(recProduct, Actions.UPDATE, recProduct.getCurrentInventory() + stockOrderDetail.getOrderProdQty(), currentUser.getCompany());
											productUpdateList.add(recProduct);
										}
										//break;
									}
									//}
									if(!isPresent)
									{
										newProduct.setOutlet(stockOrder.getOutletByOutletAssocicationId());
										newProduct.setCurrentInventory(stockOrderDetail.getOrderProdQty());
										newProduct.setCreatedDate(new Date());				
										newProduct.setLastUpdated(new Date());
										newProduct.setUserByCreatedBy(currentUser);
										newProduct.setUserByUpdatedBy(currentUser);
										newProduct.setCompany(currentUser.getCompany());
										newProduct.setActiveIndicator(parentProduct.isActiveIndicator());
										if(parentProduct.getBrand() != null){
											newProduct.setBrand(parentProduct.getBrand());
										}
										if(parentProduct.getContact() != null){
											newProduct.setContact(parentProduct.getContact());
										}
										if(parentProduct.getDisplay() != null){
											newProduct.setDisplay(parentProduct.getDisplay());
										}
										if(parentProduct.getImagePath() != null){
											newProduct.setImagePath(parentProduct.getImagePath());
										}
										if(parentProduct.getMarkupPrct() != null){
											newProduct.setMarkupPrct(parentProduct.getMarkupPrct());
										}
										if(parentProduct.getProductCanBeSold() != null){
											newProduct.setProductCanBeSold(parentProduct.getProductCanBeSold());
										}
										if(parentProduct.getProductDesc() != null){
											newProduct.setProductDesc(parentProduct.getProductDesc());
										}
										if(parentProduct.getProductHandler() != null){
											newProduct.setProductHandler(parentProduct.getProductHandler());
										}
										if(parentProduct.getProductName() != null){
											newProduct.setProductName(parentProduct.getProductName());
										}
										if(parentProduct.getProductTags() != null){
											newProduct.setProductTags(parentProduct.getProductTags());
										}
										if(parentProduct.getProductType() != null){
											newProduct.setProductType(parentProduct.getProductType());
										}
										if(parentProduct.getProductUuid() != null){
											newProduct.setProductUuid(parentProduct.getProductUuid());
										}
										if(parentProduct.getPurchaseAccountCode() != null){
											newProduct.setPurchaseAccountCode(parentProduct.getPurchaseAccountCode());
										}
										if(parentProduct.getReorderAmount() != null){
											newProduct.setReorderAmount(parentProduct.getReorderAmount());
										}
										if(parentProduct.getReorderPoint() != null){
											newProduct.setReorderPoint(parentProduct.getReorderPoint());
										}
										if(parentProduct.getSalesAccountCode() != null){
											newProduct.setSalesAccountCode(parentProduct.getSalesAccountCode());
										}
										if(parentProduct.getSalesTax() != null){
											newProduct.setSalesTax(parentProduct.getSalesTax());
										}
										if(parentProduct.getSku() != null){
											newProduct.setSku(parentProduct.getSku());
										}
										if(parentProduct.getStandardProduct() != null){
											newProduct.setStandardProduct(parentProduct.getStandardProduct());
										}
										if(parentProduct.getSupplyPriceExclTax() != null){
											newProduct.setSupplyPriceExclTax(parentProduct.getSupplyPriceExclTax());
										}
										if(parentProduct.getTrackingProduct() != null){
											newProduct.setTrackingProduct(parentProduct.getTrackingProduct());
										}
										if(parentProduct.getVariantProducts() != null){
											newProduct.setVariantProducts(parentProduct.getVariantProducts());
										}
										newProduct = productService.addProduct(newProduct, Actions.CREATE, stockOrderDetail.getOrderProdQty(),currentUser.getCompany());
										recvProductList.put(newProduct.getProductUuid(), newProduct);
										productsMap.put(newProduct.getProductId(), newProduct);
									}
									ProductVariant recvProductVariant = new ProductVariant(); 
									recvProductVariant.setOutlet(stockOrder.getOutletByOutletAssocicationId());
									recvProductVariant.setCurrentInventory(stockOrderDetail.getOrderProdQty());
									if(newProduct.getProductId() != null){
										recvProductVariant.setProduct(newProduct);
									}
									else{
										recvProductVariant.setProduct(recProduct);
									}
									recvProductVariant.setCreatedDate(new Date());				
									recvProductVariant.setLastUpdated(new Date());
									recvProductVariant.setUserByCreatedBy(currentUser);
									recvProductVariant.setUserByUpdatedBy(currentUser);
									recvProductVariant.setCompany(currentUser.getCompany());
									recvProductVariant.setActiveIndicator(productVariant.isActiveIndicator());
									if(productVariant.getMarkupPrct() != null){
										recvProductVariant.setMarkupPrct(productVariant.getMarkupPrct());
									}
									if(productVariant.getProductUuid() != null){
										recvProductVariant.setProductUuid(productVariant.getProductUuid());
									}
									if(productVariant.getReorderAmount() != null){
										recvProductVariant.setReorderAmount(productVariant.getReorderAmount());
									}
									if(productVariant.getReorderPoint() != null){
										recvProductVariant.setReorderPoint(productVariant.getReorderPoint());
									}
									if(productVariant.getSalesTax() != null){
										recvProductVariant.setSalesTax(productVariant.getSalesTax());
									}
									if(productVariant.getSku() != null){
										recvProductVariant.setSku(productVariant.getSku());
									}
									if(productVariant.getSupplyPriceExclTax() != null){
										recvProductVariant.setSupplyPriceExclTax(productVariant.getSupplyPriceExclTax());
									}
									if(productVariant.getVariantAttributeByVariantAttributeAssocicationId1() != null){
										recvProductVariant.setVariantAttributeByVariantAttributeAssocicationId1(productVariant.getVariantAttributeByVariantAttributeAssocicationId1());
									}
									if(productVariant.getVariantAttributeByVariantAttributeAssocicationId2() != null){
										recvProductVariant.setVariantAttributeByVariantAttributeAssocicationId2(productVariant.getVariantAttributeByVariantAttributeAssocicationId2());
									}
									if(productVariant.getVariantAttributeByVariantAttributeAssocicationId3() != null){
										recvProductVariant.setVariantAttributeByVariantAttributeAssocicationId3(productVariant.getVariantAttributeByVariantAttributeAssocicationId3());
									}
									if(productVariant.getVariantAttributeName() != null){
										recvProductVariant.setVariantAttributeName(productVariant.getVariantAttributeName());
									}
									if(productVariant.getVariantAttributeValue1() != null){
										recvProductVariant.setVariantAttributeValue1(productVariant.getVariantAttributeValue1());
									}
									if(productVariant.getVariantAttributeValue2() != null){
										recvProductVariant.setVariantAttributeValue2(productVariant.getVariantAttributeValue2());
									}
									if(productVariant.getVariantAttributeValue3() != null){
										recvProductVariant.setVariantAttributeValue3(productVariant.getVariantAttributeValue3());
									}
									//									if(productVariant.getVariantAttributeValueses() != null){
									//										recvProductVariant.setVariantAttributeValueses(productVariant.getVariantAttributeValueses());
									//									}
									if(productVariant.getProductVariantUuid() != null){
										recvProductVariant.setProductVariantUuid(productVariant.getProductVariantUuid());
									}
									recvProductVariant = productVariantService.addProductVariant(recvProductVariant, Actions.CREATE, stockOrderDetail.getOrderProdQty(),currentUser.getCompany(),recvProductVariant.getProductUuid());
									recvProductVariantList.put(recvProductVariant.getProductVariantUuid(), recvProductVariant);
									productVariantsMap.put(recvProductVariant.getProductVariantId(), recvProductVariant);
								}									
							}
							else{
								//for(Product recProduct : recvProductList){
								//									UUID u1 = UUID.fromString(recProduct.getProductUuid());
								//									UUID u2 = UUID.fromString(product.getProductUuid());
								Product recProduct = recvProductList.get(product.getProductUuid());
								if(recProduct != null){
									int recvPreQuantity = recProduct.getCurrentInventory();
									recProduct.setCurrentInventory(recvPreQuantity + stockOrderDetail.getOrderProdQty());		
									recProduct.setLastUpdated(new Date());
									recProduct.setUserByUpdatedBy(currentUser);
									//recProduct.setContact(supplierService.getContactByID(product.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
									recProduct.setSupplyPriceExclTax(product.getSupplyPriceExclTax());
									recProduct.setMarkupPrct(product.getMarkupPrct());
									//productService.updateProduct(recProduct, Actions.UPDATE, recProduct.getCurrentInventory(),currentUser.getCompany());
									productUpdateList.add(recProduct);
									found = true;
									//break;
								}
								//}
								if(!found){
									Product recvProduct = product; 
									recvProduct.setOutlet(stockOrder.getOutletByOutletAssocicationId());
									recvProduct.setCurrentInventory(stockOrderDetail.getOrderProdQty());
									recvProduct.setCreatedDate(new Date());				
									recvProduct.setLastUpdated(new Date());
									recvProduct.setUserByCreatedBy(currentUser);
									recvProduct.setUserByUpdatedBy(currentUser);
									recvProduct.setCompany(currentUser.getCompany());
									recvProduct = productService.addProduct(recvProduct, Actions.CREATE, stockOrderDetail.getOrderProdQty(),currentUser.getCompany());
									recvProductList.put(recvProduct.getProductUuid(), recvProduct);
									productsMap.put(recvProduct.getProductId(), recProduct);
								}
							}
							stockOrderDetail.setStockOrder(stockOrder);
							stockOrderDetail.setActiveIndicator(true);					
							stockOrderDetail.setLastUpdated(new Date());
							stockOrderDetail.setUpdatedBy(currentUser.getUserId());
							stockOrderDetailsUpdateList.add(stockOrderDetail);
							/*stockOrderDetailService.updateStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());
							util.AuditTrail(request, currentUser, "PurchaseOrderDetails.updateStockOrderDetail", 
									"User "+ currentUser.getUserEmail()+" Update StockOrderDetail+"+stockOrderDetailBean.getStockOrderDetailId()+" successfully ",false);*/
						}
						else
						{
							StockOrderDetail stockOrderDetail = new StockOrderDetail();
							//stockOrderDetail.setStockOrderDetailId(Integer.parseInt(stockOrderDetailBean.getStockOrderDetailId()));
							stockOrderDetail.setOrderProdQty(Integer.parseInt(stockOrderDetailBean.getOrderProdQty()));
							stockOrderDetail.setOrdrSupplyPrice(new BigDecimal(stockOrderDetailBean.getOrdrSupplyPrice()));
							ProductVariant productVariant = new ProductVariant();
							Product product = new Product();
							if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
								productVariant = productVariantsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
								stockOrderDetail.setProductVariant(productVariant);
								stockOrderDetail.setIsProduct(false);
								int preQuantity = productVariant.getCurrentInventory();
								productVariant.setCurrentInventory(preQuantity - stockOrderDetail.getOrderProdQty());						
								productVariant.setLastUpdated(new Date());
								productVariant.setUserByUpdatedBy(currentUser);
								//productVariantService.updateProductVariant(productVariant, Actions.UPDATE, productVariant.getCurrentInventory(),currentUser.getCompany());
								productVariantUpdateList.add(productVariant);
							}
							else{
								product = productsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
								stockOrderDetail.setProduct(product);
								stockOrderDetail.setIsProduct(true);
								int preQuantity = product.getCurrentInventory();
								product.setCurrentInventory(preQuantity - stockOrderDetail.getOrderProdQty());						
								product.setLastUpdated(new Date());
								product.setUserByUpdatedBy(currentUser);
								//productService.updateProduct(product, Actions.UPDATE, product.getCurrentInventory(),currentUser.getCompany());
								productUpdateList.add(product);
							}
							if(stockOrderDetailBean.getRecvProdQty() != null && !stockOrderDetailBean.getRecvProdQty().equalsIgnoreCase("")){
								stockOrderDetail.setRecvProdQty(Integer.parseInt(stockOrderDetailBean.getRecvProdQty()));
							}
							if(stockOrderDetailBean.getRecvSupplyPrice() != null && !stockOrderDetailBean.getRecvSupplyPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRecvSupplyPrice(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
							}
							if(stockOrderDetailBean.getRetailPrice() != null && !stockOrderDetailBean.getRetailPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRetailPrice(new BigDecimal(stockOrderDetailBean.getRetailPrice()));
							}
							boolean found = false;
							/*if(stockOrder.getOutletBySourceOutletAssocicationId() != null){
								recvProductVariantList = productVariantService.getAllProductVariantsByOutletId(stockOrder.getOutletByOutletAssocicationId().getOutletId(),currentUser.getCompany().getCompanyId());
								recvProductList = productService.getAllProductsByOutletId(stockOrder.getOutletByOutletAssocicationId().getOutletId());
							}*/
							if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
								//for(ProductVariant recProductVariant : recvProductVariantList)
								//{
								// UUID u1 = UUID.fromString(recProductVariant.getProductVariantUuid());
								// UUID u2 = UUID.fromString(productVariant.getProductVariantUuid());
								ProductVariant recProductVariant = recvProductVariantList.get(productVariant.getProductVariantUuid());
								if(recProductVariant != null){
									int recvPreQuantity = recProductVariant.getCurrentInventory();
									recProductVariant.setSupplyPriceExclTax(productVariant.getSupplyPriceExclTax());
									recProductVariant.setMarkupPrct(productVariant.getMarkupPrct());
									recProductVariant.setCurrentInventory(recvPreQuantity + stockOrderDetail.getOrderProdQty());
									//productVariantService.updateProductVariant(recProductVariant, Actions.UPDATE, recProductVariant.getCurrentInventory(),currentUser.getCompany());
									productVariantUpdateList.add(recProductVariant);
									Product sourceProduct = productsMap.get(productVariant.getProduct().getProductId());
									Product recProduct = productsMap.get(recProductVariant.getProduct().getProductId());
									//recProduct.setContact(supplierService.getContactByID(sourceProduct.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
									recProduct.setSupplyPriceExclTax(sourceProduct.getSupplyPriceExclTax());
									recProduct.setMarkupPrct(sourceProduct.getMarkupPrct());
									//productService.updateProduct(recProduct, Actions.UPDATE, recvPreQuantity + stockOrderDetail.getOrderProdQty(), currentUser.getCompany());
									productUpdateList.add(recProduct);
									found = true;
									//break;
								}
								//}
								if(!found)
								{	
									Product parentProduct = productsMap.get(productVariant.getProduct().getProductId());
									boolean isPresent = false;
									Product newProduct = new Product();
									//for(Product recProduct: recvProductList){
									//										UUID u1 = UUID.fromString(recProduct.getProductUuid());
									//										UUID u2 = UUID.fromString(parentProduct.getProductUuid());
									Product recProduct = recvProductList.get(parentProduct.getProductUuid());
									if(recProduct != null){
										isPresent = true;
										//newProduct = recProduct;
										if(!productUpdateList.contains(recProduct)){
											recProduct.setContact(supplierService.getContactByID(parentProduct.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
											recProduct.setSupplyPriceExclTax(parentProduct.getSupplyPriceExclTax());
											recProduct.setMarkupPrct(parentProduct.getMarkupPrct());
											//productService.updateProduct(recProduct, Actions.UPDATE, recProduct.getCurrentInventory() + stockOrderDetail.getOrderProdQty(), currentUser.getCompany());
											productUpdateList.add(recProduct);
										}
										//break;
									}
									//}
									if(!isPresent)
									{
										newProduct.setOutlet(stockOrder.getOutletByOutletAssocicationId());
										newProduct.setCurrentInventory(stockOrderDetail.getOrderProdQty());
										newProduct.setCreatedDate(new Date());				
										newProduct.setLastUpdated(new Date());
										newProduct.setUserByCreatedBy(currentUser);
										newProduct.setUserByUpdatedBy(currentUser);
										newProduct.setCompany(currentUser.getCompany());
										newProduct.setActiveIndicator(parentProduct.isActiveIndicator());
										if(parentProduct.getBrand() != null){
											newProduct.setBrand(parentProduct.getBrand());
										}
										if(parentProduct.getContact() != null){
											newProduct.setContact(parentProduct.getContact());
										}
										if(parentProduct.getDisplay() != null){
											newProduct.setDisplay(parentProduct.getDisplay());
										}
										if(parentProduct.getImagePath() != null){
											newProduct.setImagePath(parentProduct.getImagePath());
										}
										if(parentProduct.getMarkupPrct() != null){
											newProduct.setMarkupPrct(parentProduct.getMarkupPrct());
										}
										if(parentProduct.getProductCanBeSold() != null){
											newProduct.setProductCanBeSold(parentProduct.getProductCanBeSold());
										}
										if(parentProduct.getProductDesc() != null){
											newProduct.setProductDesc(parentProduct.getProductDesc());
										}
										if(parentProduct.getProductHandler() != null){
											newProduct.setProductHandler(parentProduct.getProductHandler());
										}
										if(parentProduct.getProductName() != null){
											newProduct.setProductName(parentProduct.getProductName());
										}
										if(parentProduct.getProductTags() != null){
											newProduct.setProductTags(parentProduct.getProductTags());
										}
										if(parentProduct.getProductType() != null){
											newProduct.setProductType(parentProduct.getProductType());
										}
										if(parentProduct.getProductUuid() != null){
											newProduct.setProductUuid(parentProduct.getProductUuid());
										}
										if(parentProduct.getPurchaseAccountCode() != null){
											newProduct.setPurchaseAccountCode(parentProduct.getPurchaseAccountCode());
										}
										if(parentProduct.getReorderAmount() != null){
											newProduct.setReorderAmount(parentProduct.getReorderAmount());
										}
										if(parentProduct.getReorderPoint() != null){
											newProduct.setReorderPoint(parentProduct.getReorderPoint());
										}
										if(parentProduct.getSalesAccountCode() != null){
											newProduct.setSalesAccountCode(parentProduct.getSalesAccountCode());
										}
										if(parentProduct.getSalesTax() != null){
											newProduct.setSalesTax(parentProduct.getSalesTax());
										}
										if(parentProduct.getSku() != null){
											newProduct.setSku(parentProduct.getSku());
										}
										if(parentProduct.getStandardProduct() != null){
											newProduct.setStandardProduct(parentProduct.getStandardProduct());
										}
										if(parentProduct.getSupplyPriceExclTax() != null){
											newProduct.setSupplyPriceExclTax(parentProduct.getSupplyPriceExclTax());
										}
										if(parentProduct.getTrackingProduct() != null){
											newProduct.setTrackingProduct(parentProduct.getTrackingProduct());
										}
										if(parentProduct.getVariantProducts() != null){
											newProduct.setVariantProducts(parentProduct.getVariantProducts());
										}
										newProduct = productService.addProduct(newProduct, Actions.CREATE, stockOrderDetail.getOrderProdQty(),currentUser.getCompany());
										recvProductList.put(newProduct.getProductUuid(), newProduct);
										productsMap.put(newProduct.getProductId(), newProduct);
									}							
									ProductVariant recvProductVariant = new ProductVariant(); 
									recvProductVariant.setOutlet(stockOrder.getOutletByOutletAssocicationId());
									recvProductVariant.setCurrentInventory(stockOrderDetail.getOrderProdQty());
									if(newProduct.getProductId() != null){
										recvProductVariant.setProduct(newProduct);
									}
									else{
										recvProductVariant.setProduct(recProduct);
									}
									recvProductVariant.setCreatedDate(new Date());				
									recvProductVariant.setLastUpdated(new Date());
									recvProductVariant.setUserByCreatedBy(currentUser);
									recvProductVariant.setUserByUpdatedBy(currentUser);
									recvProductVariant.setCompany(currentUser.getCompany());
									recvProductVariant.setActiveIndicator(productVariant.isActiveIndicator());
									if(productVariant.getMarkupPrct() != null){
										recvProductVariant.setMarkupPrct(productVariant.getMarkupPrct());
									}
									if(productVariant.getProductUuid() != null){
										recvProductVariant.setProductUuid(productVariant.getProductUuid());
									}
									if(productVariant.getReorderAmount() != null){
										recvProductVariant.setReorderAmount(productVariant.getReorderAmount());
									}
									if(productVariant.getReorderPoint() != null){
										recvProductVariant.setReorderPoint(productVariant.getReorderPoint());
									}
									if(productVariant.getSalesTax() != null){
										recvProductVariant.setSalesTax(productVariant.getSalesTax());
									}
									if(productVariant.getSku() != null){
										recvProductVariant.setSku(productVariant.getSku());
									}
									if(productVariant.getSupplyPriceExclTax() != null){
										recvProductVariant.setSupplyPriceExclTax(productVariant.getSupplyPriceExclTax());
									}
									if(productVariant.getVariantAttributeByVariantAttributeAssocicationId1() != null){
										recvProductVariant.setVariantAttributeByVariantAttributeAssocicationId1(productVariant.getVariantAttributeByVariantAttributeAssocicationId1());
									}
									if(productVariant.getVariantAttributeByVariantAttributeAssocicationId2() != null){
										recvProductVariant.setVariantAttributeByVariantAttributeAssocicationId2(productVariant.getVariantAttributeByVariantAttributeAssocicationId2());
									}
									if(productVariant.getVariantAttributeByVariantAttributeAssocicationId3() != null){
										recvProductVariant.setVariantAttributeByVariantAttributeAssocicationId3(productVariant.getVariantAttributeByVariantAttributeAssocicationId3());
									}
									if(productVariant.getVariantAttributeName() != null){
										recvProductVariant.setVariantAttributeName(productVariant.getVariantAttributeName());
									}
									if(productVariant.getVariantAttributeValue1() != null){
										recvProductVariant.setVariantAttributeValue1(productVariant.getVariantAttributeValue1());
									}
									if(productVariant.getVariantAttributeValue2() != null){
										recvProductVariant.setVariantAttributeValue2(productVariant.getVariantAttributeValue2());
									}
									if(productVariant.getVariantAttributeValue3() != null){
										recvProductVariant.setVariantAttributeValue3(productVariant.getVariantAttributeValue3());
									}
									if(productVariant.getProductVariantUuid() != null){
										recvProductVariant.setProductVariantUuid(productVariant.getProductVariantUuid());
									}
									//									if(productVariant.getVariantAttributeValueses() != null){
									//										recvProductVariant.setVariantAttributeValueses(productVariant.getVariantAttributeValueses());
									//									}
									System.out.println(recvProductVariant.getProductUuid());
									recvProductVariant = productVariantService.addProductVariant(recvProductVariant, Actions.CREATE, stockOrderDetail.getOrderProdQty(),currentUser.getCompany(),recvProductVariant.getProductUuid());
									recvProductVariantList.put(recvProductVariant.getProductVariantUuid(), recvProductVariant);
									productVariantsMap.put(recvProductVariant.getProductVariantId(), recvProductVariant);
								}									
							}
							else{
								//for(Product recProduct : recvProductList){
								//									UUID u1 = UUID.fromString(recProduct.getProductUuid());
								//									UUID u2 = UUID.fromString(product.getProductUuid());
								Product recProduct = recvProductList.get(product.getProductUuid());
								if(recProduct!= null){
									int recvPreQuantity = recProduct.getCurrentInventory();
									recProduct.setCurrentInventory(recvPreQuantity + stockOrderDetail.getOrderProdQty());				
									recProduct.setLastUpdated(new Date());
									recProduct.setUserByUpdatedBy(currentUser);
									//recProduct.setContact(supplierService.getContactByID(product.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
									recProduct.setSupplyPriceExclTax(product.getSupplyPriceExclTax());
									recProduct.setMarkupPrct(product.getMarkupPrct());
									//productService.updateProduct(recProduct, Actions.UPDATE, recProduct.getCurrentInventory(),currentUser.getCompany());
									productUpdateList.add(recProduct);
									found = true;
									//break;
								}
								//}
								if(!found){
									Product recvProduct = new Product();
									recvProduct.setOutlet(stockOrder.getOutletByOutletAssocicationId());
									recvProduct.setCurrentInventory(stockOrderDetail.getOrderProdQty());
									recvProduct.setCreatedDate(new Date());				
									recvProduct.setLastUpdated(new Date());
									recvProduct.setUserByCreatedBy(currentUser);
									recvProduct.setUserByUpdatedBy(currentUser);
									recvProduct.setCompany(currentUser.getCompany());
									recvProduct.setActiveIndicator(product.isActiveIndicator());
									if(product.getBrand() != null){
										recvProduct.setBrand(product.getBrand());
									}
									if(product.getContact() != null){
										recvProduct.setContact(product.getContact());
									}
									if(product.getDisplay() != null){
										recvProduct.setDisplay(product.getDisplay());
									}
									if(product.getImagePath() != null){
										recvProduct.setImagePath(product.getImagePath());
									}
									if(product.getMarkupPrct() != null){
										recvProduct.setMarkupPrct(product.getMarkupPrct());
									}
									if(product.getProductCanBeSold() != null){
										recvProduct.setProductCanBeSold(product.getProductCanBeSold());
									}
									if(product.getProductDesc() != null){
										recvProduct.setProductDesc(product.getProductDesc());
									}
									if(product.getProductHandler() != null){
										recvProduct.setProductHandler(product.getProductHandler());
									}
									if(product.getProductName() != null){
										recvProduct.setProductName(product.getProductName());
									}
									if(product.getProductTags() != null){
										recvProduct.setProductTags(product.getProductTags());
									}
									if(product.getProductType() != null){
										recvProduct.setProductType(product.getProductType());
									}
									if(product.getProductUuid() != null){
										recvProduct.setProductUuid(product.getProductUuid());
									}
									if(product.getPurchaseAccountCode() != null){
										recvProduct.setPurchaseAccountCode(product.getPurchaseAccountCode());
									}
									if(product.getReorderAmount() != null){
										recvProduct.setReorderAmount(product.getReorderAmount());
									}
									if(product.getReorderPoint() != null){
										recvProduct.setReorderPoint(product.getReorderPoint());
									}
									if(product.getSalesAccountCode() != null){
										recvProduct.setSalesAccountCode(product.getSalesAccountCode());
									}
									if(product.getSalesTax() != null){
										recvProduct.setSalesTax(product.getSalesTax());
									}
									if(product.getSku() != null){
										recvProduct.setSku(product.getSku());
									}
									if(product.getStandardProduct() != null){
										recvProduct.setStandardProduct(product.getStandardProduct());
									}
									if(product.getSupplyPriceExclTax() != null){
										recvProduct.setSupplyPriceExclTax(product.getSupplyPriceExclTax());
									}
									if(product.getTrackingProduct() != null){
										recvProduct.setTrackingProduct(product.getTrackingProduct());
									}
									if(product.getVariantProducts() != null){
										recvProduct.setVariantProducts(product.getVariantProducts());
									}
									recvProduct = productService.addProduct(recvProduct, Actions.CREATE, stockOrderDetail.getOrderProdQty(),currentUser.getCompany());
									recvProductList.put(recvProduct.getProductUuid(), recvProduct);
									productsMap.put(recvProduct.getProductId(), recvProduct);
								}
							}
							stockOrderDetail.setStockOrder(stockOrder);
							stockOrderDetail.setActiveIndicator(true);			
							stockOrderDetail.setCreatedDate(new Date());				
							stockOrderDetail.setLastUpdated(new Date());
							stockOrderDetail.setCreatedBy(currentUser.getUserId());
							stockOrderDetail.setUpdatedBy(currentUser.getUserId());
							stockOrderDetail.setCompany(currentUser.getCompany());							
							stockOrderDetailsAddList.add(stockOrderDetail);
							/*stockOrderDetailService.addStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());
							util.AuditTrail(request, currentUser, "PurchaseOrderDetails.addStockOrderDetail", 
									"User "+ currentUser.getUserEmail()+" Added StockOrderDetail+"+stockOrderDetailBean.getStockOrderDetailId()+" successfully ",false);*/
						}
					}
					if(preStockOrderDetailList != null){
						if(preStockOrderDetailList.size() > 0)
						{
							for(StockOrderDetail stockOrderDetail : preStockOrderDetailList)
							{
								stockOrderDetailsDeleteList.add(stockOrderDetail);
								//stockOrderDetailService.deleteStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());
							}
						}	
					}								
					if(stockOrderDetailsUpdateList.size() > 0){
						stockOrderDetailService.updateStockOrderDetailsList(stockOrderDetailsUpdateList, currentUser.getCompany().getCompanyId());
					}
					if(stockOrderDetailsAddList.size() > 0){
						stockOrderDetailService.addStockOrderDetailsList(stockOrderDetailsAddList, currentUser.getCompany().getCompanyId());
					}
					if(stockOrderDetailsDeleteList.size() > 0){
						stockOrderDetailService.deleteStockOrderDetailsList(stockOrderDetailsDeleteList, currentUser.getCompany().getCompanyId());
					}
					if(productUpdateList.size()>0){
						productService.updateProductList(productUpdateList, currentUser.getCompany());
					}
					if(productVariantUpdateList.size()>0){
						productVariantService.updateProductVariantList(productVariantUpdateList, currentUser.getCompany());
					}
					stockOrder.setStatus(statusService.getStatusByStatusId(3)); //completed status
					stockOrder.setOrdrRecvDate(new Date());
					stockOrder.setLastUpdated(new Date());
					stockOrder.setUpdatedBy(currentUser.getUserId());
					stockOrderService.updateStockOrder(stockOrder,currentUser.getCompany().getCompanyId());

					Contact supplier = supplierService.getContactByContactOutletID(stockOrder.getOutletByOutletAssocicationId().getOutletId(), currentUser.getCompany().getCompanyId());
					if(supplier.getContactBalance() == null){
						supplier.setContactBalance(BigDecimal.ZERO);
					}
					supplier.setContactBalance(supplier.getContactBalance().subtract(new BigDecimal(grandTotal)));
					supplier.setLastUpdated(new Date());
					supplier.setUpdatedBy(currentUser.getUserId());
					supplierService.updateContact(supplier, currentUser.getCompany().getCompanyId());
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.updateandRecStockOrderDetail", 
							"User "+ currentUser.getUserEmail()+"Updated Supplier+"+supplier.getContactId()+" successfully ",false);



					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.STOCKCONTROL);
				}else{
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.addStockOrderDetail", "User "+ 
							currentUser.getUserEmail()+" Unable to add StockOrderDetail+"+ stockOrderDetailBeansList.get(0).getStockOrderDetailId(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderController.addStockOrder",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}		
	}

	/*@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateAndReturntoHeadOffice/{sessionId}/{grandTotal}", method = RequestMethod.POST)
	public @ResponseBody Response updateAndReturntoHeadOffice(@PathVariable("sessionId") String sessionId,
			@PathVariable("grandTotal") String grandTotal,
			@RequestBody List<StockOrderDetailBean> stockOrderDetailBeansList, HttpServletRequest request){		
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			try {			
				if (stockOrderDetailBeansList.size() > 0) {					
					List<StockOrderDetail> preStockOrderDetailList = new ArrayList<>();
					List<ProductVariant> recvProductVariantList = new ArrayList<>();
					List<Product> recvProductList = new ArrayList<>();
					StockOrder stockOrder = stockOrderService.getStockOrderByStockOrderID(Integer.parseInt(stockOrderDetailBeansList.get(0).getStockOrderId()),currentUser.getCompany().getCompanyId());
					Map<Integer, Product> productsMap = new HashMap<>();
					List<Product> products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
					List<StockOrderDetail> stockOrderDetailsUpdateList = new ArrayList<>();
					List<StockOrderDetail> stockOrderDetailsDeleteList = new ArrayList<>();
					List<StockOrderDetail> stockOrderDetailsAddList = new ArrayList<>();
					List<Product> productUpdateList = new ArrayList<>();
					List<ProductVariant> productVariantUpdateList = new ArrayList<>();
					if(products!=null){
						for(Product product:products){
							productsMap.put(product.getProductId(), product);
							if(product.getOutlet().getOutletId() == stockOrder.getOutletByOutletAssocicationId().getOutletId()){
								recvProductList.add(product);
							}
						}
					}
					Map<Integer, ProductVariant> productVariantsMap = new HashMap<>();
					List<ProductVariant> productVariants = productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
					if(productVariants!=null){
						for(ProductVariant productVariant:productVariants){
							productVariantsMap.put(productVariant.getProductVariantId(), productVariant);
							if(productVariant.getOutlet().getOutletId() == stockOrder.getOutletByOutletAssocicationId().getOutletId()){
								recvProductVariantList.add(productVariant);
							}
						}
					}
					//Stock Order Map Region
					List<StockOrderDetail> stockOrderDetails = new ArrayList<>();
					Map<Integer, List<StockOrderDetail>> stockOrderDetailsMap = new HashMap<>();
					Map<Integer, StockOrderDetail> stockOrderDetailsByDetailIDMap = new HashMap<>();
					stockOrderDetails = stockOrderDetailService.getAllStockOrderDetails(currentUser.getCompany().getCompanyId());
					if(stockOrderDetails!=null){
						for(StockOrderDetail stockOrderDetail:stockOrderDetails){
							List<StockOrderDetail> addedstockOrderDetails = stockOrderDetailsMap.get(stockOrderDetail.getStockOrder().getStockOrderId());
							if(addedstockOrderDetails!=null){
								addedstockOrderDetails.add(stockOrderDetail);
								stockOrderDetailsMap.put(stockOrderDetail.getStockOrder().getStockOrderId(), addedstockOrderDetails);
							}else{
								addedstockOrderDetails = new ArrayList<>();
								addedstockOrderDetails.add(stockOrderDetail);
								stockOrderDetailsMap.put(stockOrderDetail.getStockOrder().getStockOrderId(), addedstockOrderDetails);
							}
							stockOrderDetailsByDetailIDMap.put(stockOrderDetail.getStockOrderDetailId(), stockOrderDetail);
						}
					}
					//End Region
					preStockOrderDetailList = stockOrderDetailsMap.get(Integer.parseInt(stockOrderDetailBeansList.get(0).getStockOrderId()));
					for(StockOrderDetailBean stockOrderDetailBean : stockOrderDetailBeansList)
					{
						if(stockOrderDetailBean.getStockOrderDetailId() != null && !stockOrderDetailBean.getStockOrderDetailId().equalsIgnoreCase("")){
							StockOrderDetail stockOrderDetail = stockOrderDetailsByDetailIDMap.get(Integer.parseInt(stockOrderDetailBean.getStockOrderDetailId()));
							if(preStockOrderDetailList != null){
								int i = 0;
								int index = -1;
								for (StockOrderDetail preStockOrderDetail : preStockOrderDetailList){
									int stockOrderDetailId = stockOrderDetail.getStockOrderDetailId();
									int preStockOrderDetailId = preStockOrderDetail.getStockOrderDetailId();
									if(stockOrderDetailId == preStockOrderDetailId)
									{
										index = i;
										break;
									}
									i++;
								}
								if(index != -1){
									preStockOrderDetailList.remove(index);
								}
							}
							stockOrderDetail.setOrderProdQty(Integer.parseInt(stockOrderDetailBean.getOrderProdQty()));
							stockOrderDetail.setOrdrSupplyPrice(new BigDecimal(stockOrderDetailBean.getOrdrSupplyPrice()));
							if(stockOrderDetailBean.getRecvProdQty() != null && !stockOrderDetailBean.getRecvProdQty().equalsIgnoreCase("")){
								stockOrderDetail.setRecvProdQty(Integer.parseInt(stockOrderDetailBean.getRecvProdQty()));
							}else{
								stockOrderDetail.setRecvProdQty(0);
							}		
							ProductVariant productVariant = new ProductVariant();
							Product product = new Product();
							if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
								productVariant = productVariantsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
								stockOrderDetail.setIsProduct(false);
								//stockOrderDetail.setProductVariant(productVariant);
								int preQuantity = productVariant.getCurrentInventory();
								productVariant.setCurrentInventory(preQuantity - stockOrderDetail.getRecvProdQty());						
								productVariant.setLastUpdated(new Date());
								productVariant.setUserByUpdatedBy(currentUser);
								//productVariantService.updateProductVariant(productVariant, Actions.UPDATE, productVariant.getCurrentInventory(),currentUser.getCompany());
								productVariantUpdateList.add(productVariant);
							}
							else{
								product = productsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
								//stockOrderDetail.setProduct(product);
								stockOrderDetail.setIsProduct(true);
								int preQuantity = product.getCurrentInventory();
								product.setCurrentInventory(preQuantity - stockOrderDetail.getRecvProdQty());						
								product.setLastUpdated(new Date());
								product.setUserByUpdatedBy(currentUser);
								//productService.updateProduct(product, Actions.INVENTORY_TRANSFER, product.getCurrentInventory(),currentUser.getCompany());
								productUpdateList.add(product);
							}
							if(stockOrderDetailBean.getRecvProdQty() != null && !stockOrderDetailBean.getRecvProdQty().equalsIgnoreCase("")){
								stockOrderDetail.setRecvProdQty(Integer.parseInt(stockOrderDetailBean.getRecvProdQty()));
							}
							if(stockOrderDetailBean.getRecvSupplyPrice() != null && !stockOrderDetailBean.getRecvSupplyPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRecvSupplyPrice(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
							}
							if(stockOrderDetailBean.getRetailPrice() != null && !stockOrderDetailBean.getRetailPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRetailPrice(new BigDecimal(stockOrderDetailBean.getRetailPrice()));
							}
							boolean found = false;
							if(stockOrder.getOutletBySourceOutletAssocicationId() != null){
								recvProductVariantList = productVariantService.getAllProductVariantsByOutletId(stockOrder.getOutletByOutletAssocicationId().getOutletId(),currentUser.getCompany().getCompanyId());
								recvProductList = productService.getAllProductsByOutletId(stockOrder.getOutletByOutletAssocicationId().getOutletId());
							}
							if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
								for(ProductVariant recProductVariant : recvProductVariantList)
								{
									//									UUID u1 = UUID.fromString(recProductVariant.getProductVariantUuid());
									//									UUID u2 = UUID.fromString(productVariant.getProductVariantUuid());
									if(recProductVariant.getProductVariantUuid().equals(productVariant.getProductVariantUuid())){
										int recvPreQuantity = recProductVariant.getCurrentInventory();
										recProductVariant.setCurrentInventory(recvPreQuantity + stockOrderDetail.getRecvProdQty());
										recProductVariant.setSupplyPriceExclTax(productVariant.getSupplyPriceExclTax());
										recProductVariant.setMarkupPrct(productVariant.getMarkupPrct());
										//productVariantService.updateProductVariant(recProductVariant, Actions.UPDATE, recProductVariant.getCurrentInventory(),currentUser.getCompany());
										productVariantUpdateList.add(recProductVariant);
										Product sourceProduct = productsMap.get(productVariant.getProduct().getProductId());
										Product recProduct = productsMap.get(recProductVariant.getProduct().getProductId());
										//recProduct.setContact(supplierService.getContactByID(sourceProduct.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
										recProduct.setSupplyPriceExclTax(sourceProduct.getSupplyPriceExclTax());
										recProduct.setMarkupPrct(sourceProduct.getMarkupPrct());
										//productService.updateProduct(recProduct, Actions.UPDATE, recvPreQuantity + stockOrderDetail.getOrderProdQty(), currentUser.getCompany());
										productUpdateList.add(recProduct);
										found = true;
										break;
									}
								}
								if(!found)
								{	
									Product parentProduct = productsMap.get(productVariant.getProduct().getProductId());
									boolean isPresent = false;
									Product newProduct = parentProduct;
									for(Product recProduct: recvProductList){
										//										UUID u1 = UUID.fromString(recProduct.getProductUuid());
										//										UUID u2 = UUID.fromString(parentProduct.getProductUuid());
										if(recProduct.getProductUuid().equals(parentProduct.getProductUuid())){
											isPresent = true;
											newProduct = recProduct;
											//recProduct.setContact(supplierService.getContactByID(parentProduct.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
											recProduct.setSupplyPriceExclTax(parentProduct.getSupplyPriceExclTax());
											recProduct.setMarkupPrct(parentProduct.getMarkupPrct());
											//productService.updateProduct(recProduct, Actions.UPDATE, recProduct.getCurrentInventory() + stockOrderDetail.getOrderProdQty(), currentUser.getCompany());
											productUpdateList.add(recProduct);
											break;
										}
									}
									if(!isPresent)
									{
										newProduct.setOutlet(stockOrder.getOutletByOutletAssocicationId());
										newProduct.setCurrentInventory(stockOrderDetail.getRecvProdQty());
										newProduct.setCreatedDate(new Date());				
										newProduct.setLastUpdated(new Date());
										newProduct.setUserByCreatedBy(currentUser);
										newProduct.setUserByUpdatedBy(currentUser);
										newProduct.setCompany(currentUser.getCompany());
										newProduct = productService.addProduct(newProduct, Actions.CREATE, stockOrderDetail.getOrderProdQty(),currentUser.getCompany());
										recvProductList.add(newProduct);
									}
									ProductVariant recProductVariant = productVariant; 
									recProductVariant.setOutlet(stockOrder.getOutletByOutletAssocicationId());
									recProductVariant.setCurrentInventory(stockOrderDetail.getRecvProdQty());
									recProductVariant.setProduct(newProduct);
									recProductVariant.setCreatedDate(new Date());				
									recProductVariant.setLastUpdated(new Date());
									recProductVariant.setUserByCreatedBy(currentUser);
									recProductVariant.setUserByUpdatedBy(currentUser);
									recProductVariant.setCompany(currentUser.getCompany());
									recProductVariant = productVariantService.addProductVariant(recProductVariant, Actions.CREATE, stockOrderDetail.getOrderProdQty(),currentUser.getCompany(),newProduct.getProductUuid());
									recvProductVariantList.add(recProductVariant);
								}									
							}
							else{
								for(Product recProduct : recvProductList){
									//									UUID u1 = UUID.fromString(recProduct.getProductUuid());
									//									UUID u2 = UUID.fromString(product.getProductUuid());
									if(recProduct.getProductUuid().equals(product.getProductUuid())){
										int recvPreQuantity = recProduct.getCurrentInventory();
										recProduct.setCurrentInventory(recvPreQuantity + stockOrderDetail.getRecvProdQty());		
										recProduct.setLastUpdated(new Date());
										recProduct.setUserByUpdatedBy(currentUser);
										//recProduct.setContact(supplierService.getContactByID(product.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
										recProduct.setSupplyPriceExclTax(product.getSupplyPriceExclTax());
										recProduct.setMarkupPrct(product.getMarkupPrct());
										//productService.updateProduct(recProduct, Actions.UPDATE, recProduct.getCurrentInventory(),currentUser.getCompany());
										productUpdateList.add(recProduct);
										found = true;
										break;
									}
								}
								if(!found){
									Product recProduct = product; 
									recProduct.setOutlet(stockOrder.getOutletByOutletAssocicationId());
									recProduct.setCurrentInventory(stockOrderDetail.getRecvProdQty());
									recProduct.setCreatedDate(new Date());				
									recProduct.setLastUpdated(new Date());
									recProduct.setUserByCreatedBy(currentUser);
									recProduct.setUserByUpdatedBy(currentUser);
									recProduct.setCompany(currentUser.getCompany());
									recProduct = productService.addProduct(recProduct, Actions.CREATE, stockOrderDetail.getOrderProdQty(),currentUser.getCompany());
									recvProductList.add(recProduct);
								}
							}
							stockOrderDetail.setStockOrder(stockOrder);
							stockOrderDetail.setActiveIndicator(true);					
							stockOrderDetail.setLastUpdated(new Date());
							stockOrderDetail.setUpdatedBy(currentUser.getUserId());
							stockOrderDetailsUpdateList.add(stockOrderDetail);
							stockOrderDetailService.updateStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());
							util.AuditTrail(request, currentUser, "PurchaseOrderDetails.updateStockOrderDetail", 
									"User "+ currentUser.getUserEmail()+" Update StockOrderDetail+"+stockOrderDetailBean.getStockOrderDetailId()+" successfully ",false);
						}
						else
						{
							StockOrderDetail stockOrderDetail = new StockOrderDetail();
							//stockOrderDetail.setStockOrderDetailId(Integer.parseInt(stockOrderDetailBean.getStockOrderDetailId()));
							stockOrderDetail.setOrderProdQty(Integer.parseInt(stockOrderDetailBean.getOrderProdQty()));
							if(stockOrderDetailBean.getRecvProdQty() != null && !stockOrderDetailBean.getRecvProdQty().equalsIgnoreCase("")){
								stockOrderDetail.setRecvProdQty(Integer.parseInt(stockOrderDetailBean.getRecvProdQty()));
							}else{
								stockOrderDetail.setRecvProdQty(0);
							}							
							stockOrderDetail.setOrdrSupplyPrice(new BigDecimal(stockOrderDetailBean.getOrdrSupplyPrice()));
							ProductVariant productVariant = new ProductVariant();
							Product product = new Product();
							if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
								productVariant = productVariantsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
								stockOrderDetail.setProductVariant(productVariant);
								stockOrderDetail.setIsProduct(false);
								int preQuantity = productVariant.getCurrentInventory();
								productVariant.setCurrentInventory(preQuantity - stockOrderDetail.getRecvProdQty());						
								productVariant.setLastUpdated(new Date());
								productVariant.setUserByUpdatedBy(currentUser);
								//productVariantService.updateProductVariant(productVariant, Actions.UPDATE, productVariant.getCurrentInventory(),currentUser.getCompany());
								productVariantUpdateList.add(productVariant);
							}
							else{
								product = productsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
								stockOrderDetail.setProduct(product);
								stockOrderDetail.setIsProduct(true);
								int preQuantity = product.getCurrentInventory();
								product.setCurrentInventory(preQuantity - stockOrderDetail.getRecvProdQty());						
								product.setLastUpdated(new Date());
								product.setUserByUpdatedBy(currentUser);
								//productService.updateProduct(product, Actions.UPDATE, product.getCurrentInventory(),currentUser.getCompany());
								productUpdateList.add(product);
							}
							if(stockOrderDetailBean.getRecvProdQty() != null && !stockOrderDetailBean.getRecvProdQty().equalsIgnoreCase("")){
								stockOrderDetail.setRecvProdQty(Integer.parseInt(stockOrderDetailBean.getRecvProdQty()));
							}
							if(stockOrderDetailBean.getRecvSupplyPrice() != null && !stockOrderDetailBean.getRecvSupplyPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRecvSupplyPrice(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
							}
							if(stockOrderDetailBean.getRetailPrice() != null && !stockOrderDetailBean.getRetailPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRetailPrice(new BigDecimal(stockOrderDetailBean.getRetailPrice()));
							}
							boolean found = false;
							if(stockOrder.getOutletBySourceOutletAssocicationId() != null){
								recvProductVariantList = productVariantService.getAllProductVariantsByOutletId(stockOrder.getOutletByOutletAssocicationId().getOutletId(),currentUser.getCompany().getCompanyId());
								recvProductList = productService.getAllProductsByOutletId(stockOrder.getOutletByOutletAssocicationId().getOutletId());
							}
							if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
								for(ProductVariant recProductVariant : recvProductVariantList)
								{
									//									UUID u1 = UUID.fromString(recProductVariant.getProductVariantUuid());
									//									UUID u2 = UUID.fromString(productVariant.getProductVariantUuid());
									if(recProductVariant.getProductVariantUuid().equals(productVariant.getProductVariantUuid())){
										int recvPreQuantity = recProductVariant.getCurrentInventory();
										recProductVariant.setSupplyPriceExclTax(productVariant.getSupplyPriceExclTax());
										recProductVariant.setMarkupPrct(productVariant.getMarkupPrct());
										recProductVariant.setCurrentInventory(recvPreQuantity + stockOrderDetail.getRecvProdQty());
										//productVariantService.updateProductVariant(recProductVariant, Actions.UPDATE, recProductVariant.getCurrentInventory(),currentUser.getCompany());
										productVariantUpdateList.add(recProductVariant);
										Product sourceProduct = productsMap.get(productVariant.getProduct().getProductId());
										Product recProduct = productsMap.get(recProductVariant.getProduct().getProductId());
										//recProduct.setContact(supplierService.getContactByID(sourceProduct.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
										recProduct.setSupplyPriceExclTax(sourceProduct.getSupplyPriceExclTax());
										recProduct.setMarkupPrct(sourceProduct.getMarkupPrct());
										//productService.updateProduct(recProduct, Actions.UPDATE, recvPreQuantity + stockOrderDetail.getOrderProdQty(), currentUser.getCompany());
										productUpdateList.add(recProduct);
										found = true;
										break;
									}
								}
								if(!found)
								{	
									Product parentProduct = productsMap.get(productVariant.getProduct().getProductId());
									boolean isPresent = false;
									Product newProduct = parentProduct;
									for(Product recProduct: recvProductList){
										//										UUID u1 = UUID.fromString(recProduct.getProductUuid());
										//										UUID u2 = UUID.fromString(parentProduct.getProductUuid());
										if(recProduct.getProductUuid().equals(parentProduct.getProductUuid())){
											isPresent = true;
											newProduct = recProduct;
											//recProduct.setContact(supplierService.getContactByID(parentProduct.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
											recProduct.setSupplyPriceExclTax(parentProduct.getSupplyPriceExclTax());
											recProduct.setMarkupPrct(parentProduct.getMarkupPrct());
											//productService.updateProduct(recProduct, Actions.UPDATE, recProduct.getCurrentInventory() + stockOrderDetail.getOrderProdQty(), currentUser.getCompany());
											productUpdateList.add(recProduct);
											break;
										}
									}
									if(!isPresent)
									{
										newProduct.setOutlet(stockOrder.getOutletByOutletAssocicationId());
										newProduct.setCurrentInventory(stockOrderDetail.getRecvProdQty());
										newProduct.setCreatedDate(new Date());				
										newProduct.setLastUpdated(new Date());
										newProduct.setUserByCreatedBy(currentUser);
										newProduct.setUserByUpdatedBy(currentUser);
										newProduct.setCompany(currentUser.getCompany());
										newProduct = productService.addProduct(newProduct, Actions.CREATE, stockOrderDetail.getOrderProdQty(),currentUser.getCompany());
										recvProductList.add(newProduct);
									}
									ProductVariant recProductVariant = productVariant; 
									recProductVariant.setOutlet(stockOrder.getOutletByOutletAssocicationId());
									recProductVariant.setCurrentInventory(stockOrderDetail.getRecvProdQty());
									recProductVariant.setProduct(newProduct);
									recProductVariant.setCreatedDate(new Date());				
									recProductVariant.setLastUpdated(new Date());
									recProductVariant.setUserByCreatedBy(currentUser);
									recProductVariant.setUserByUpdatedBy(currentUser);
									recProductVariant.setCompany(currentUser.getCompany());
									recProductVariant = productVariantService.addProductVariant(recProductVariant, Actions.CREATE, stockOrderDetail.getOrderProdQty(),currentUser.getCompany(),newProduct.getProductUuid());
									recvProductVariantList.add(recProductVariant);
								}									
							}
							else{
								for(Product recProduct : recvProductList){
									//									UUID u1 = UUID.fromString(recProduct.getProductUuid());
									//									UUID u2 = UUID.fromString(product.getProductUuid());
									if(recProduct.getProductUuid().equals(product.getProductUuid())){
										int recvPreQuantity = recProduct.getCurrentInventory();
										recProduct.setCurrentInventory(recvPreQuantity + stockOrderDetail.getRecvProdQty());				
										recProduct.setLastUpdated(new Date());
										recProduct.setUserByUpdatedBy(currentUser);
										//recProduct.setContact(supplierService.getContactByID(product.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
										recProduct.setSupplyPriceExclTax(product.getSupplyPriceExclTax());
										recProduct.setMarkupPrct(product.getMarkupPrct());
										//productService.updateProduct(recProduct, Actions.UPDATE, recProduct.getCurrentInventory(),currentUser.getCompany());
										productUpdateList.add(recProduct);
										found = true;
										break;
									}
								}
								if(!found){
									Product recProduct = product; 
									recProduct.setOutlet(stockOrder.getOutletByOutletAssocicationId());
									recProduct.setCurrentInventory(stockOrderDetail.getRecvProdQty());
									recProduct.setCreatedDate(new Date());				
									recProduct.setLastUpdated(new Date());
									recProduct.setUserByCreatedBy(currentUser);
									recProduct.setUserByUpdatedBy(currentUser);
									recProduct.setCompany(currentUser.getCompany());
									recProduct = productService.addProduct(recProduct, Actions.CREATE, stockOrderDetail.getOrderProdQty(),currentUser.getCompany());
									recvProductList.add(recProduct);
								}
							}
							stockOrderDetail.setStockOrder(stockOrder);
							stockOrderDetail.setActiveIndicator(true);			
							stockOrderDetail.setCreatedDate(new Date());				
							stockOrderDetail.setLastUpdated(new Date());
							stockOrderDetail.setCreatedBy(currentUser.getUserId());
							stockOrderDetail.setUpdatedBy(currentUser.getUserId());
							stockOrderDetail.setCompany(currentUser.getCompany());							
							stockOrderDetailsAddList.add(stockOrderDetail);
							stockOrderDetailService.addStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());
							util.AuditTrail(request, currentUser, "PurchaseOrderDetails.addStockOrderDetail", 
									"User "+ currentUser.getUserEmail()+" Added StockOrderDetail+"+stockOrderDetailBean.getStockOrderDetailId()+" successfully ",false);
						}
					}
					if(preStockOrderDetailList != null){
						if(preStockOrderDetailList.size() > 0)
						{
							for(StockOrderDetail stockOrderDetail : preStockOrderDetailList)
							{
								stockOrderDetailsDeleteList.add(stockOrderDetail);
								//stockOrderDetailService.deleteStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());
							}
						}	
					}								
					if(stockOrderDetailsUpdateList.size() > 0){
						stockOrderDetailService.updateStockOrderDetailsList(stockOrderDetailsUpdateList, currentUser.getCompany().getCompanyId());
					}
					if(stockOrderDetailsAddList.size() > 0){
						stockOrderDetailService.addStockOrderDetailsList(stockOrderDetailsAddList, currentUser.getCompany().getCompanyId());
					}
					if(stockOrderDetailsDeleteList.size() > 0){
						stockOrderDetailService.deleteStockOrderDetailsList(stockOrderDetailsDeleteList, currentUser.getCompany().getCompanyId());
					}
					if(productUpdateList.size()>0){
						productService.updateProductList(productUpdateList, currentUser.getCompany());
					}
					if(productVariantUpdateList.size()>0){
						productVariantService.updateProductVariantList(productVariantUpdateList, currentUser.getCompany());
					}
					stockOrder.setStatus(statusService.getStatusByStatusId(3)); //completed status
					stockOrder.setOrdrRecvDate(new Date());
					stockOrder.setLastUpdated(new Date());
					stockOrder.setUpdatedBy(currentUser.getUserId());
					stockOrderService.updateStockOrder(stockOrder,currentUser.getCompany().getCompanyId());

					Contact supplier = supplierService.getContactByContactOutletID(stockOrder.getOutletBySourceOutletAssocicationId().getOutletId(), currentUser.getCompany().getCompanyId());
					if(supplier.getContactBalance() == null){
						supplier.setContactBalance(BigDecimal.ZERO);
					}
					supplier.setContactBalance(supplier.getContactBalance().add(new BigDecimal(grandTotal)));
					supplier.setLastUpdated(new Date());
					supplier.setUpdatedBy(currentUser.getUserId());
					supplierService.updateContact(supplier, currentUser.getCompany().getCompanyId());
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.updateandRecStockOrderDetail", 
							"User "+ currentUser.getUserEmail()+"Updated Supplier+"+supplier.getContactId()+" successfully ",false);


					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.STOCKCONTROL);

				}else{
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.addStockOrderDetail", "User "+ 
							currentUser.getUserEmail()+" Unable to add StockOrderDetail+"+ stockOrderDetailBeansList.get(0).getStockOrderDetailId(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderController.addStockOrder",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}		
	}*/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateAndReturntoHeadOffice/{sessionId}/{grandTotal}", method = RequestMethod.POST)
	public @ResponseBody Response updateAndReturntoHeadOffice(@PathVariable("sessionId") String sessionId,
			@PathVariable("grandTotal") String grandTotal,
			@RequestBody List<StockOrderDetailBean> stockOrderDetailBeansList, HttpServletRequest request){		
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			try {			
				if (stockOrderDetailBeansList.size() > 0) {					
					
					Map<String, ProductVariant> recvProductVariantList = new HashMap();
					Map<String, Product> recvProductList = new HashMap();
					StockOrder stockOrder = stockOrderService.getStockOrderByStockOrderID(Integer.parseInt(stockOrderDetailBeansList.get(0).getStockOrderId()),currentUser.getCompany().getCompanyId());
					Map<Integer, Product> productsMap = new HashMap<>();
					List<Product> products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
					List<StockOrderDetail> stockOrderDetailsUpdateList = new ArrayList<>();
					List<StockOrderDetail> stockOrderDetailsDeleteList = new ArrayList<>();
					List<StockOrderDetail> stockOrderDetailsAddList = new ArrayList<>();
					List<Product> productUpdateList = new ArrayList<>();
					List<ProductVariant> productVariantUpdateList = new ArrayList<>();
					if(products!=null){
						for(Product product:products){
							if(product.getOutlet().getOutletId() == stockOrder.getOutletBySourceOutletAssocicationId().getOutletId()){
								productsMap.put(product.getProductId(), product);
							}
							if(product.getOutlet().getOutletId() == stockOrder.getOutletByOutletAssocicationId().getOutletId()){
								recvProductList.put(product.getProductUuid(), product);
							}
						}
					}
					Map<Integer, ProductVariant> productVariantsMap = new HashMap<>();
					//Map<Integer, ProductVariant> 
					List<ProductVariant> productVariants = productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
					if(productVariants!=null){
						for(ProductVariant productVariant:productVariants){
						if(productVariant.getOutlet().getOutletId() == stockOrder.getOutletBySourceOutletAssocicationId().getOutletId()){
							productVariantsMap.put(productVariant.getProductVariantId(), productVariant);
						}
							if(productVariant.getOutlet().getOutletId() == stockOrder.getOutletByOutletAssocicationId().getOutletId()){
								recvProductVariantList.put(productVariant.getProductVariantUuid(), productVariant);
							}
						}
					}
					//Stock Order Map Region
					List<StockOrderDetail> stockOrderDetails = new ArrayList<>();
					Map<Integer, List<StockOrderDetail>> stockOrderDetailsMap = new HashMap<>();
					Map<Integer, StockOrderDetail> stockOrderDetailsByDetailIDMap = new HashMap<>();
					stockOrderDetails = stockOrderDetailService.getAllStockOrderDetails(currentUser.getCompany().getCompanyId());
					if(stockOrderDetails!=null){
						for(StockOrderDetail stockOrderDetail:stockOrderDetails){
							List<StockOrderDetail> addedstockOrderDetails = stockOrderDetailsMap.get(stockOrderDetail.getStockOrder().getStockOrderId());
							if(addedstockOrderDetails!=null){
								addedstockOrderDetails.add(stockOrderDetail);
								stockOrderDetailsMap.put(stockOrderDetail.getStockOrder().getStockOrderId(), addedstockOrderDetails);
							}else{
								addedstockOrderDetails = new ArrayList<>();
								addedstockOrderDetails.add(stockOrderDetail);
								stockOrderDetailsMap.put(stockOrderDetail.getStockOrder().getStockOrderId(), addedstockOrderDetails);
							}
							stockOrderDetailsByDetailIDMap.put(stockOrderDetail.getStockOrderDetailId(), stockOrderDetail);
						}
					}
					//End Region
					List<StockOrderDetail> preStockOrderDetailList = stockOrderDetailsMap.get(Integer.parseInt(stockOrderDetailBeansList.get(0).getStockOrderId()));

					for(StockOrderDetailBean stockOrderDetailBean : stockOrderDetailBeansList)
					{
						if(stockOrderDetailBean.getStockOrderDetailId() != null && !stockOrderDetailBean.getStockOrderDetailId().equalsIgnoreCase("")){
							StockOrderDetail stockOrderDetail = stockOrderDetailsByDetailIDMap.get(Integer.parseInt(stockOrderDetailBean.getStockOrderDetailId()));
							if(preStockOrderDetailList != null){
								int i = 0;
								int index = -1;
								for (StockOrderDetail preStockOrderDetail : preStockOrderDetailList){
									int stockOrderDetailId = stockOrderDetail.getStockOrderDetailId();
									int preStockOrderDetailId = preStockOrderDetail.getStockOrderDetailId();
									if(stockOrderDetailId == preStockOrderDetailId)
									{
										index = i;
										break;
									}
									i++;
								}
								if(index != -1){
									preStockOrderDetailList.remove(index);
								}
							}
							stockOrderDetail.setOrderProdQty(Integer.parseInt(stockOrderDetailBean.getOrderProdQty()));
							stockOrderDetail.setOrdrSupplyPrice(new BigDecimal(stockOrderDetailBean.getOrdrSupplyPrice()));
							if(stockOrderDetailBean.getRecvProdQty() != null && !stockOrderDetailBean.getRecvProdQty().equalsIgnoreCase("")){
								stockOrderDetail.setRecvProdQty(Integer.parseInt(stockOrderDetailBean.getRecvProdQty()));
							}else{
								stockOrderDetail.setRecvProdQty(0);
							}		
							ProductVariant productVariant = new ProductVariant();
							Product product = new Product();
							if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
								productVariant = productVariantsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
								stockOrderDetail.setIsProduct(false);
								//stockOrderDetail.setProductVariant(productVariant);
								int preQuantity = productVariant.getCurrentInventory();
								productVariant.setCurrentInventory(preQuantity - stockOrderDetail.getRecvProdQty());						
								productVariant.setLastUpdated(new Date());
								productVariant.setUserByUpdatedBy(currentUser);
								//productVariantService.updateProductVariant(productVariant, Actions.UPDATE, productVariant.getCurrentInventory(),currentUser.getCompany());
								productVariantUpdateList.add(productVariant);
							}
							else{
								product = productsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
								//stockOrderDetail.setProduct(product);
								stockOrderDetail.setIsProduct(true);
								int preQuantity = product.getCurrentInventory();
								product.setCurrentInventory(preQuantity - stockOrderDetail.getRecvProdQty());						
								product.setLastUpdated(new Date());
								product.setUserByUpdatedBy(currentUser);
								//productService.updateProduct(product, Actions.INVENTORY_TRANSFER, product.getCurrentInventory(),currentUser.getCompany());
								productUpdateList.add(product);
							}
							if(stockOrderDetailBean.getRecvProdQty() != null && !stockOrderDetailBean.getRecvProdQty().equalsIgnoreCase("")){
								stockOrderDetail.setRecvProdQty(Integer.parseInt(stockOrderDetailBean.getRecvProdQty()));
							}
							if(stockOrderDetailBean.getRecvSupplyPrice() != null && !stockOrderDetailBean.getRecvSupplyPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRecvSupplyPrice(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
							}
							if(stockOrderDetailBean.getRetailPrice() != null && !stockOrderDetailBean.getRetailPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRetailPrice(new BigDecimal(stockOrderDetailBean.getRetailPrice()));
							}
							boolean found = false;
							//if(stockOrder.getOutletBySourceOutletAssocicationId() != null){
							//recvProductVariantList = productVariantService.getAllProductVariantsByOutletId(stockOrder.getOutletByOutletAssocicationId().getOutletId(),currentUser.getCompany().getCompanyId());
							//recvProductList = productService.getAllProductsByOutletId(stockOrder.getOutletByOutletAssocicationId().getOutletId());
							//}
							if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
								//for(ProductVariant recProductVariant : recvProductVariantList)
								//{
								//									UUID u1 = UUID.fromString(recProductVariant.getProductVariantUuid());
								//									UUID u2 = UUID.fromString(productVariant.getProductVariantUuid());
								ProductVariant recProductVariant = recvProductVariantList.get(productVariant.getProductVariantUuid());
									if(recProductVariant != null){
										
										int recvPreQuantity = recProductVariant.getCurrentInventory();
										recProductVariant.setCurrentInventory(recvPreQuantity + stockOrderDetail.getRecvProdQty());
										//recProductVariant.setSupplyPriceExclTax(productVariant.getSupplyPriceExclTax());
										//recProductVariant.setMarkupPrct(productVariant.getMarkupPrct());
										//productVariantService.updateProductVariant(recProductVariant, Actions.UPDATE, recProductVariant.getCurrentInventory(),currentUser.getCompany());
										productVariantUpdateList.add(recProductVariant);
										Product sourceProduct = productsMap.get(productVariant.getProduct().getProductId());
										//Product recProduct = productsMap.get(recProductVariant.getProduct().getProductId());
										//recProduct.setContact(supplierService.getContactByID(sourceProduct.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
										//recProduct.setSupplyPriceExclTax(sourceProduct.getSupplyPriceExclTax());
										//recProduct.setMarkupPrct(sourceProduct.getMarkupPrct());
										//productService.updateProduct(recProduct, Actions.UPDATE, recvPreQuantity + stockOrderDetail.getOrderProdQty(), currentUser.getCompany());
										//productUpdateList.add(recProduct);
										found = true;
										//break;
									}
								//}
								if(!found)
								{	
									Product parentProduct = productsMap.get(productVariant.getProduct().getProductId());
									boolean isPresent = false;
									Product newProduct = new Product();
									//for(Product recProduct: recvProductList){
									//										UUID u1 = UUID.fromString(recProduct.getProductUuid());
									//										UUID u2 = UUID.fromString(parentProduct.getProductUuid());
									Product recProduct = recvProductList.get(parentProduct.getProductUuid());
									if(recProduct != null){
										isPresent = true;
										if(!productUpdateList.contains(recProduct)){
										
											//recProduct.setContact(supplierService.getContactByID(parentProduct.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
											//recProduct.setSupplyPriceExclTax(parentProduct.getSupplyPriceExclTax());
											//recProduct.setMarkupPrct(parentProduct.getMarkupPrct());
											//productService.updateProduct(recProduct, Actions.UPDATE, recProduct.getCurrentInventory() + stockOrderDetail.getOrderProdQty(), currentUser.getCompany());
											//productUpdateList.add(recProduct);
											//break;
										}
									}
									if(!isPresent)
									{
										newProduct.setOutlet(stockOrder.getOutletByOutletAssocicationId());
										newProduct.setCurrentInventory(stockOrderDetail.getRecvProdQty());
										newProduct.setCreatedDate(new Date());				
										newProduct.setLastUpdated(new Date());
										newProduct.setUserByCreatedBy(currentUser);
										newProduct.setUserByUpdatedBy(currentUser);
										newProduct.setCompany(currentUser.getCompany());
										newProduct.setActiveIndicator(parentProduct.isActiveIndicator());
										if(parentProduct.getBrand() != null){
											newProduct.setBrand(parentProduct.getBrand());
										}
										if(parentProduct.getContact() != null){
											newProduct.setContact(parentProduct.getContact());
										}
										if(parentProduct.getDisplay() != null){
											newProduct.setDisplay(parentProduct.getDisplay());
										}
										if(parentProduct.getImagePath() != null){
											newProduct.setImagePath(parentProduct.getImagePath());
										}
										if(parentProduct.getMarkupPrct() != null){
											newProduct.setMarkupPrct(parentProduct.getMarkupPrct());
										}
										if(parentProduct.getProductCanBeSold() != null){
											newProduct.setProductCanBeSold(parentProduct.getProductCanBeSold());
										}
										if(parentProduct.getProductDesc() != null){
											newProduct.setProductDesc(parentProduct.getProductDesc());
										}
										if(parentProduct.getProductHandler() != null){
											newProduct.setProductHandler(parentProduct.getProductHandler());
										}
										if(parentProduct.getProductName() != null){
											newProduct.setProductName(parentProduct.getProductName());
										}
										if(parentProduct.getProductTags() != null){
											newProduct.setProductTags(parentProduct.getProductTags());
										}
										if(parentProduct.getProductType() != null){
											newProduct.setProductType(parentProduct.getProductType());
										}
										if(parentProduct.getProductUuid() != null){
											newProduct.setProductUuid(parentProduct.getProductUuid());
										}
										if(parentProduct.getPurchaseAccountCode() != null){
											newProduct.setPurchaseAccountCode(parentProduct.getPurchaseAccountCode());
										}
										if(parentProduct.getReorderAmount() != null){
											newProduct.setReorderAmount(parentProduct.getReorderAmount());
										}
										if(parentProduct.getReorderPoint() != null){
											newProduct.setReorderPoint(parentProduct.getReorderPoint());
										}
										if(parentProduct.getSalesAccountCode() != null){
											newProduct.setSalesAccountCode(parentProduct.getSalesAccountCode());
										}
										if(parentProduct.getSalesTax() != null){
											newProduct.setSalesTax(parentProduct.getSalesTax());
										}
										if(parentProduct.getSku() != null){
											newProduct.setSku(parentProduct.getSku());
										}
										if(parentProduct.getStandardProduct() != null){
											newProduct.setStandardProduct(parentProduct.getStandardProduct());
										}
										if(parentProduct.getSupplyPriceExclTax() != null){
											newProduct.setSupplyPriceExclTax(parentProduct.getSupplyPriceExclTax());
										}
										if(parentProduct.getTrackingProduct() != null){
											newProduct.setTrackingProduct(parentProduct.getTrackingProduct());
										}
										if(parentProduct.getVariantProducts() != null){
											newProduct.setVariantProducts(parentProduct.getVariantProducts());
										}
										newProduct = productService.addProduct(newProduct, Actions.CREATE, stockOrderDetail.getOrderProdQty(),currentUser.getCompany());
										recvProductList.put(newProduct.getProductUuid(), newProduct);
										productsMap.put(newProduct.getProductId(), newProduct);
									}
									ProductVariant recvProductVariant = new ProductVariant();
									recvProductVariant.setOutlet(stockOrder.getOutletByOutletAssocicationId());
									recvProductVariant.setCurrentInventory(stockOrderDetail.getRecvProdQty());
									if(newProduct.getProductId() != null){
										recvProductVariant.setProduct(newProduct);
									}
									else{
										recvProductVariant.setProduct(recProduct);
									}
									recvProductVariant.setCreatedDate(new Date());				
									recvProductVariant.setLastUpdated(new Date());
									recvProductVariant.setUserByCreatedBy(currentUser);
									recvProductVariant.setUserByUpdatedBy(currentUser);
									recvProductVariant.setCompany(currentUser.getCompany());
									recvProductVariant.setActiveIndicator(productVariant.isActiveIndicator());
									if(productVariant.getMarkupPrct() != null){
										recvProductVariant.setMarkupPrct(productVariant.getMarkupPrct());
									}
									if(productVariant.getProductUuid() != null){
										recvProductVariant.setProductUuid(productVariant.getProductUuid());
									}
									if(productVariant.getReorderAmount() != null){
										recvProductVariant.setReorderAmount(productVariant.getReorderAmount());
									}
									if(productVariant.getReorderPoint() != null){
										recvProductVariant.setReorderPoint(productVariant.getReorderPoint());
									}
									if(productVariant.getSalesTax() != null){
										recvProductVariant.setSalesTax(productVariant.getSalesTax());
									}
									if(productVariant.getSku() != null){
										recvProductVariant.setSku(productVariant.getSku());
									}
									if(productVariant.getSupplyPriceExclTax() != null){
										recvProductVariant.setSupplyPriceExclTax(productVariant.getSupplyPriceExclTax());
									}
									if(productVariant.getVariantAttributeByVariantAttributeAssocicationId1() != null){
										recvProductVariant.setVariantAttributeByVariantAttributeAssocicationId1(productVariant.getVariantAttributeByVariantAttributeAssocicationId1());
									}
									if(productVariant.getVariantAttributeByVariantAttributeAssocicationId2() != null){
										recvProductVariant.setVariantAttributeByVariantAttributeAssocicationId2(productVariant.getVariantAttributeByVariantAttributeAssocicationId2());
									}
									if(productVariant.getVariantAttributeByVariantAttributeAssocicationId3() != null){
										recvProductVariant.setVariantAttributeByVariantAttributeAssocicationId3(productVariant.getVariantAttributeByVariantAttributeAssocicationId3());
									}
									if(productVariant.getVariantAttributeName() != null){
										recvProductVariant.setVariantAttributeName(productVariant.getVariantAttributeName());
									}
									if(productVariant.getVariantAttributeValue1() != null){
										recvProductVariant.setVariantAttributeValue1(productVariant.getVariantAttributeValue1());
									}
									if(productVariant.getVariantAttributeValue2() != null){
										recvProductVariant.setVariantAttributeValue2(productVariant.getVariantAttributeValue2());
									}
									if(productVariant.getVariantAttributeValue3() != null){
										recvProductVariant.setVariantAttributeValue3(productVariant.getVariantAttributeValue3());
									}
									//									if(productVariant.getVariantAttributeValueses() != null){
									//										recvProductVariant.setVariantAttributeValueses(productVariant.getVariantAttributeValueses());
									//									}
									if(productVariant.getProductVariantUuid() != null){
										recvProductVariant.setProductVariantUuid(productVariant.getProductVariantUuid());
									}
									recvProductVariant = productVariantService.addProductVariant(recvProductVariant, Actions.CREATE, stockOrderDetail.getOrderProdQty(),currentUser.getCompany(),recvProductVariant.getProductUuid());
									recvProductVariantList.put(recvProductVariant.getProductVariantUuid(), recvProductVariant);
									productVariantsMap.put(recvProductVariant.getProductVariantId(), recvProductVariant);
								}									
							}
							else{
								//for(Product recProduct : recvProductList){
								//									UUID u1 = UUID.fromString(recProduct.getProductUuid());
								//									UUID u2 = UUID.fromString(product.getProductUuid());
								Product recProduct = recvProductList.get(product.getProductUuid());
								if(recProduct != null){
									int recvPreQuantity = recProduct.getCurrentInventory();
									recProduct.setCurrentInventory(recvPreQuantity + stockOrderDetail.getRecvProdQty());		
									recProduct.setLastUpdated(new Date());
									recProduct.setUserByUpdatedBy(currentUser);
									//recProduct.setContact(supplierService.getContactByID(product.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
									recProduct.setSupplyPriceExclTax(product.getSupplyPriceExclTax());
									recProduct.setMarkupPrct(product.getMarkupPrct());
									//productService.updateProduct(recProduct, Actions.UPDATE, recProduct.getCurrentInventory(),currentUser.getCompany());
									productUpdateList.add(recProduct);
									found = true;
									//break;
								}
								//}
								if(!found){
									Product recvProduct = product; 
									recvProduct.setOutlet(stockOrder.getOutletByOutletAssocicationId());
									recvProduct.setCurrentInventory(stockOrderDetail.getRecvProdQty());
									recvProduct.setCreatedDate(new Date());				
									recvProduct.setLastUpdated(new Date());
									recvProduct.setUserByCreatedBy(currentUser);
									recvProduct.setUserByUpdatedBy(currentUser);
									recvProduct.setCompany(currentUser.getCompany());
									recvProduct = productService.addProduct(recvProduct, Actions.CREATE, stockOrderDetail.getOrderProdQty(),currentUser.getCompany());
									recvProductList.put(recvProduct.getProductUuid(), recvProduct);
									productsMap.put(recvProduct.getProductId(), recProduct);
								}
							}
							stockOrderDetail.setStockOrder(stockOrder);
							stockOrderDetail.setActiveIndicator(true);					
							stockOrderDetail.setLastUpdated(new Date());
							stockOrderDetail.setUpdatedBy(currentUser.getUserId());
							stockOrderDetailsUpdateList.add(stockOrderDetail);
							/*stockOrderDetailService.updateStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());
							util.AuditTrail(request, currentUser, "PurchaseOrderDetails.updateStockOrderDetail", 
									"User "+ currentUser.getUserEmail()+" Update StockOrderDetail+"+stockOrderDetailBean.getStockOrderDetailId()+" successfully ",false);*/
						}
						else
						{
							StockOrderDetail stockOrderDetail = new StockOrderDetail();
							//stockOrderDetail.setStockOrderDetailId(Integer.parseInt(stockOrderDetailBean.getStockOrderDetailId()));
							stockOrderDetail.setOrderProdQty(Integer.parseInt(stockOrderDetailBean.getOrderProdQty()));
							if(stockOrderDetailBean.getRecvProdQty() != null && !stockOrderDetailBean.getRecvProdQty().equalsIgnoreCase("")){
								stockOrderDetail.setRecvProdQty(Integer.parseInt(stockOrderDetailBean.getRecvProdQty()));
							}else{
								stockOrderDetail.setRecvProdQty(0);
							}							
							stockOrderDetail.setOrdrSupplyPrice(new BigDecimal(stockOrderDetailBean.getOrdrSupplyPrice()));
							ProductVariant productVariant = new ProductVariant();
							Product product = new Product();
							if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
								productVariant = productVariantsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
								stockOrderDetail.setProductVariant(productVariant);
								stockOrderDetail.setIsProduct(false);
								int preQuantity = productVariant.getCurrentInventory();
								productVariant.setCurrentInventory(preQuantity - stockOrderDetail.getRecvProdQty());						
								productVariant.setLastUpdated(new Date());
								productVariant.setUserByUpdatedBy(currentUser);
								//productVariantService.updateProductVariant(productVariant, Actions.UPDATE, productVariant.getCurrentInventory(),currentUser.getCompany());
								productVariantUpdateList.add(productVariant);
							}
							else{
								product = productsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
								stockOrderDetail.setProduct(product);
								stockOrderDetail.setIsProduct(true);
								int preQuantity = product.getCurrentInventory();
								product.setCurrentInventory(preQuantity - stockOrderDetail.getRecvProdQty());						
								product.setLastUpdated(new Date());
								product.setUserByUpdatedBy(currentUser);
								//productService.updateProduct(product, Actions.UPDATE, product.getCurrentInventory(),currentUser.getCompany());
								productUpdateList.add(product);
							}
							if(stockOrderDetailBean.getRecvProdQty() != null && !stockOrderDetailBean.getRecvProdQty().equalsIgnoreCase("")){
								stockOrderDetail.setRecvProdQty(Integer.parseInt(stockOrderDetailBean.getRecvProdQty()));
							}
							if(stockOrderDetailBean.getRecvSupplyPrice() != null && !stockOrderDetailBean.getRecvSupplyPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRecvSupplyPrice(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
							}
							if(stockOrderDetailBean.getRetailPrice() != null && !stockOrderDetailBean.getRetailPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRetailPrice(new BigDecimal(stockOrderDetailBean.getRetailPrice()));
							}
							boolean found = false;
							/*if(stockOrder.getOutletBySourceOutletAssocicationId() != null){
								recvProductVariantList = productVariantService.getAllProductVariantsByOutletId(stockOrder.getOutletByOutletAssocicationId().getOutletId(),currentUser.getCompany().getCompanyId());
								recvProductList = productService.getAllProductsByOutletId(stockOrder.getOutletByOutletAssocicationId().getOutletId());
							}*/
							if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
								//for(ProductVariant recProductVariant : recvProductVariantList)
								//{
								// UUID u1 = UUID.fromString(recProductVariant.getProductVariantUuid());
								// UUID u2 = UUID.fromString(productVariant.getProductVariantUuid());
								ProductVariant recProductVariant = recvProductVariantList.get(productVariant.getProductVariantUuid());
								if(recProductVariant != null){
									int recvPreQuantity = recProductVariant.getCurrentInventory();
									recProductVariant.setSupplyPriceExclTax(productVariant.getSupplyPriceExclTax());
									recProductVariant.setMarkupPrct(productVariant.getMarkupPrct());
									recProductVariant.setCurrentInventory(recvPreQuantity + stockOrderDetail.getRecvProdQty());
									//productVariantService.updateProductVariant(recProductVariant, Actions.UPDATE, recProductVariant.getCurrentInventory(),currentUser.getCompany());
									productVariantUpdateList.add(recProductVariant);
									Product sourceProduct = productsMap.get(productVariant.getProduct().getProductId());
									//Product recProduct = productsMap.get(recProductVariant.getProduct().getProductId());
									//recProduct.setContact(supplierService.getContactByID(sourceProduct.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
									//recProduct.setSupplyPriceExclTax(sourceProduct.getSupplyPriceExclTax());
									//recProduct.setMarkupPrct(sourceProduct.getMarkupPrct());
									//productService.updateProduct(recProduct, Actions.UPDATE, recvPreQuantity + stockOrderDetail.getOrderProdQty(), currentUser.getCompany());
									//productUpdateList.add(recProduct);
									found = true;
									//break;
								}
								//}
								if(!found)
								{	
									Product parentProduct = productsMap.get(productVariant.getProduct().getProductId());
									boolean isPresent = false;
									Product newProduct = new Product();
									//for(Product recProduct: recvProductList){
									//										UUID u1 = UUID.fromString(recProduct.getProductUuid());
									//										UUID u2 = UUID.fromString(parentProduct.getProductUuid());
									Product recProduct = recvProductList.get(parentProduct.getProductUuid());
									if(recProduct != null){
										isPresent = true;
										//newProduct = recProduct;
										if(!productUpdateList.contains(recProduct)){
											recProduct.setContact(supplierService.getContactByID(parentProduct.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
											recProduct.setSupplyPriceExclTax(parentProduct.getSupplyPriceExclTax());
											recProduct.setMarkupPrct(parentProduct.getMarkupPrct());
											//productService.updateProduct(recProduct, Actions.UPDATE, recProduct.getCurrentInventory() + stockOrderDetail.getRecvProdQty(), currentUser.getCompany());
											productUpdateList.add(recProduct);
										}
										//break;
									}
									//}
									if(!isPresent)
									{
										newProduct.setOutlet(stockOrder.getOutletByOutletAssocicationId());
										newProduct.setCurrentInventory(stockOrderDetail.getRecvProdQty());
										newProduct.setCreatedDate(new Date());				
										newProduct.setLastUpdated(new Date());
										newProduct.setUserByCreatedBy(currentUser);
										newProduct.setUserByUpdatedBy(currentUser);
										newProduct.setCompany(currentUser.getCompany());
										newProduct.setActiveIndicator(parentProduct.isActiveIndicator());
										if(parentProduct.getBrand() != null){
											newProduct.setBrand(parentProduct.getBrand());
										}
										if(parentProduct.getContact() != null){
											newProduct.setContact(parentProduct.getContact());
										}
										if(parentProduct.getDisplay() != null){
											newProduct.setDisplay(parentProduct.getDisplay());
										}
										if(parentProduct.getImagePath() != null){
											newProduct.setImagePath(parentProduct.getImagePath());
										}
										if(parentProduct.getMarkupPrct() != null){
											newProduct.setMarkupPrct(parentProduct.getMarkupPrct());
										}
										if(parentProduct.getProductCanBeSold() != null){
											newProduct.setProductCanBeSold(parentProduct.getProductCanBeSold());
										}
										if(parentProduct.getProductDesc() != null){
											newProduct.setProductDesc(parentProduct.getProductDesc());
										}
										if(parentProduct.getProductHandler() != null){
											newProduct.setProductHandler(parentProduct.getProductHandler());
										}
										if(parentProduct.getProductName() != null){
											newProduct.setProductName(parentProduct.getProductName());
										}
										if(parentProduct.getProductTags() != null){
											newProduct.setProductTags(parentProduct.getProductTags());
										}
										if(parentProduct.getProductType() != null){
											newProduct.setProductType(parentProduct.getProductType());
										}
										if(parentProduct.getProductUuid() != null){
											newProduct.setProductUuid(parentProduct.getProductUuid());
										}
										if(parentProduct.getPurchaseAccountCode() != null){
											newProduct.setPurchaseAccountCode(parentProduct.getPurchaseAccountCode());
										}
										if(parentProduct.getReorderAmount() != null){
											newProduct.setReorderAmount(parentProduct.getReorderAmount());
										}
										if(parentProduct.getReorderPoint() != null){
											newProduct.setReorderPoint(parentProduct.getReorderPoint());
										}
										if(parentProduct.getSalesAccountCode() != null){
											newProduct.setSalesAccountCode(parentProduct.getSalesAccountCode());
										}
										if(parentProduct.getSalesTax() != null){
											newProduct.setSalesTax(parentProduct.getSalesTax());
										}
										if(parentProduct.getSku() != null){
											newProduct.setSku(parentProduct.getSku());
										}
										if(parentProduct.getStandardProduct() != null){
											newProduct.setStandardProduct(parentProduct.getStandardProduct());
										}
										if(parentProduct.getSupplyPriceExclTax() != null){
											newProduct.setSupplyPriceExclTax(parentProduct.getSupplyPriceExclTax());
										}
										if(parentProduct.getTrackingProduct() != null){
											newProduct.setTrackingProduct(parentProduct.getTrackingProduct());
										}
										if(parentProduct.getVariantProducts() != null){
											newProduct.setVariantProducts(parentProduct.getVariantProducts());
										}
										newProduct = productService.addProduct(newProduct, Actions.CREATE, stockOrderDetail.getOrderProdQty(),currentUser.getCompany());
										recvProductList.put(newProduct.getProductUuid(), newProduct);
										productsMap.put(newProduct.getProductId(), newProduct);
									}							
									ProductVariant recvProductVariant = new ProductVariant(); 
									recvProductVariant.setOutlet(stockOrder.getOutletByOutletAssocicationId());
									recvProductVariant.setCurrentInventory(stockOrderDetail.getOrderProdQty());
									if(newProduct.getProductId() != null){
										recvProductVariant.setProduct(newProduct);
									}
									else{
										recvProductVariant.setProduct(recProduct);
									}
									recvProductVariant.setCreatedDate(new Date());				
									recvProductVariant.setLastUpdated(new Date());
									recvProductVariant.setUserByCreatedBy(currentUser);
									recvProductVariant.setUserByUpdatedBy(currentUser);
									recvProductVariant.setCompany(currentUser.getCompany());
									recvProductVariant.setActiveIndicator(productVariant.isActiveIndicator());
									if(productVariant.getMarkupPrct() != null){
										recvProductVariant.setMarkupPrct(productVariant.getMarkupPrct());
									}
									if(productVariant.getProductUuid() != null){
										recvProductVariant.setProductUuid(productVariant.getProductUuid());
									}
									if(productVariant.getReorderAmount() != null){
										recvProductVariant.setReorderAmount(productVariant.getReorderAmount());
									}
									if(productVariant.getReorderPoint() != null){
										recvProductVariant.setReorderPoint(productVariant.getReorderPoint());
									}
									if(productVariant.getSalesTax() != null){
										recvProductVariant.setSalesTax(productVariant.getSalesTax());
									}
									if(productVariant.getSku() != null){
										recvProductVariant.setSku(productVariant.getSku());
									}
									if(productVariant.getSupplyPriceExclTax() != null){
										recvProductVariant.setSupplyPriceExclTax(productVariant.getSupplyPriceExclTax());
									}
									if(productVariant.getVariantAttributeByVariantAttributeAssocicationId1() != null){
										recvProductVariant.setVariantAttributeByVariantAttributeAssocicationId1(productVariant.getVariantAttributeByVariantAttributeAssocicationId1());
									}
									if(productVariant.getVariantAttributeByVariantAttributeAssocicationId2() != null){
										recvProductVariant.setVariantAttributeByVariantAttributeAssocicationId2(productVariant.getVariantAttributeByVariantAttributeAssocicationId2());
									}
									if(productVariant.getVariantAttributeByVariantAttributeAssocicationId3() != null){
										recvProductVariant.setVariantAttributeByVariantAttributeAssocicationId3(productVariant.getVariantAttributeByVariantAttributeAssocicationId3());
									}
									if(productVariant.getVariantAttributeName() != null){
										recvProductVariant.setVariantAttributeName(productVariant.getVariantAttributeName());
									}
									if(productVariant.getVariantAttributeValue1() != null){
										recvProductVariant.setVariantAttributeValue1(productVariant.getVariantAttributeValue1());
									}
									if(productVariant.getVariantAttributeValue2() != null){
										recvProductVariant.setVariantAttributeValue2(productVariant.getVariantAttributeValue2());
									}
									if(productVariant.getVariantAttributeValue3() != null){
										recvProductVariant.setVariantAttributeValue3(productVariant.getVariantAttributeValue3());
									}
									if(productVariant.getProductVariantUuid() != null){
										recvProductVariant.setProductVariantUuid(productVariant.getProductVariantUuid());
									}
									//									if(productVariant.getVariantAttributeValueses() != null){
									//										recvProductVariant.setVariantAttributeValueses(productVariant.getVariantAttributeValueses());
									//									}
									System.out.println(recvProductVariant.getProductUuid());
									recvProductVariant = productVariantService.addProductVariant(recvProductVariant, Actions.CREATE, stockOrderDetail.getOrderProdQty(),currentUser.getCompany(),recvProductVariant.getProductUuid());
									recvProductVariantList.put(recvProductVariant.getProductVariantUuid(), recvProductVariant);
									productVariantsMap.put(recvProductVariant.getProductVariantId(), recvProductVariant);
								}									
							}
							else{
								//for(Product recProduct : recvProductList){
								//									UUID u1 = UUID.fromString(recProduct.getProductUuid());
								//									UUID u2 = UUID.fromString(product.getProductUuid());
								Product recProduct = recvProductList.get(product.getProductUuid());
								if(recProduct!= null){
									int recvPreQuantity = recProduct.getCurrentInventory();
									recProduct.setCurrentInventory(recvPreQuantity + stockOrderDetail.getRecvProdQty());				
									recProduct.setLastUpdated(new Date());
									recProduct.setUserByUpdatedBy(currentUser);
									//recProduct.setContact(supplierService.getContactByID(product.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
									recProduct.setSupplyPriceExclTax(product.getSupplyPriceExclTax());
									recProduct.setMarkupPrct(product.getMarkupPrct());
									//productService.updateProduct(recProduct, Actions.UPDATE, recProduct.getCurrentInventory(),currentUser.getCompany());
									productUpdateList.add(recProduct);
									found = true;
									//break;
								}
								//}
								if(!found){
									Product recvProduct = new Product();
									recvProduct.setOutlet(stockOrder.getOutletByOutletAssocicationId());
									recvProduct.setCurrentInventory(stockOrderDetail.getRecvProdQty());
									recvProduct.setCreatedDate(new Date());				
									recvProduct.setLastUpdated(new Date());
									recvProduct.setUserByCreatedBy(currentUser);
									recvProduct.setUserByUpdatedBy(currentUser);
									recvProduct.setCompany(currentUser.getCompany());
									recvProduct.setActiveIndicator(product.isActiveIndicator());
									if(product.getBrand() != null){
										recvProduct.setBrand(product.getBrand());
									}
									if(product.getContact() != null){
										recvProduct.setContact(product.getContact());
									}
									if(product.getDisplay() != null){
										recvProduct.setDisplay(product.getDisplay());
									}
									if(product.getImagePath() != null){
										recvProduct.setImagePath(product.getImagePath());
									}
									if(product.getMarkupPrct() != null){
										recvProduct.setMarkupPrct(product.getMarkupPrct());
									}
									if(product.getProductCanBeSold() != null){
										recvProduct.setProductCanBeSold(product.getProductCanBeSold());
									}
									if(product.getProductDesc() != null){
										recvProduct.setProductDesc(product.getProductDesc());
									}
									if(product.getProductHandler() != null){
										recvProduct.setProductHandler(product.getProductHandler());
									}
									if(product.getProductName() != null){
										recvProduct.setProductName(product.getProductName());
									}
									if(product.getProductTags() != null){
										recvProduct.setProductTags(product.getProductTags());
									}
									if(product.getProductType() != null){
										recvProduct.setProductType(product.getProductType());
									}
									if(product.getProductUuid() != null){
										recvProduct.setProductUuid(product.getProductUuid());
									}
									if(product.getPurchaseAccountCode() != null){
										recvProduct.setPurchaseAccountCode(product.getPurchaseAccountCode());
									}
									if(product.getReorderAmount() != null){
										recvProduct.setReorderAmount(product.getReorderAmount());
									}
									if(product.getReorderPoint() != null){
										recvProduct.setReorderPoint(product.getReorderPoint());
									}
									if(product.getSalesAccountCode() != null){
										recvProduct.setSalesAccountCode(product.getSalesAccountCode());
									}
									if(product.getSalesTax() != null){
										recvProduct.setSalesTax(product.getSalesTax());
									}
									if(product.getSku() != null){
										recvProduct.setSku(product.getSku());
									}
									if(product.getStandardProduct() != null){
										recvProduct.setStandardProduct(product.getStandardProduct());
									}
									if(product.getSupplyPriceExclTax() != null){
										recvProduct.setSupplyPriceExclTax(product.getSupplyPriceExclTax());
									}
									if(product.getTrackingProduct() != null){
										recvProduct.setTrackingProduct(product.getTrackingProduct());
									}
									if(product.getVariantProducts() != null){
										recvProduct.setVariantProducts(product.getVariantProducts());
									}
									recvProduct = productService.addProduct(recvProduct, Actions.CREATE, stockOrderDetail.getOrderProdQty(),currentUser.getCompany());
									recvProductList.put(recvProduct.getProductUuid(), recvProduct);
									productsMap.put(recvProduct.getProductId(), recvProduct);
								}
							}
							stockOrderDetail.setStockOrder(stockOrder);
							stockOrderDetail.setActiveIndicator(true);			
							stockOrderDetail.setCreatedDate(new Date());				
							stockOrderDetail.setLastUpdated(new Date());
							stockOrderDetail.setCreatedBy(currentUser.getUserId());
							stockOrderDetail.setUpdatedBy(currentUser.getUserId());
							stockOrderDetail.setCompany(currentUser.getCompany());							
							stockOrderDetailsAddList.add(stockOrderDetail);
							/*stockOrderDetailService.addStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());
							util.AuditTrail(request, currentUser, "PurchaseOrderDetails.addStockOrderDetail", 
									"User "+ currentUser.getUserEmail()+" Added StockOrderDetail+"+stockOrderDetailBean.getStockOrderDetailId()+" successfully ",false);*/
						}
					}
					if(preStockOrderDetailList != null){
						if(preStockOrderDetailList.size() > 0)
						{
							for(StockOrderDetail stockOrderDetail : preStockOrderDetailList)
							{
								stockOrderDetailsDeleteList.add(stockOrderDetail);
								//stockOrderDetailService.deleteStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());
							}
						}	
					}								
					if(stockOrderDetailsUpdateList.size() > 0){
						stockOrderDetailService.updateStockOrderDetailsList(stockOrderDetailsUpdateList, currentUser.getCompany().getCompanyId());
					}
					if(stockOrderDetailsAddList.size() > 0){
						stockOrderDetailService.addStockOrderDetailsList(stockOrderDetailsAddList, currentUser.getCompany().getCompanyId());
					}
					if(stockOrderDetailsDeleteList.size() > 0){
						stockOrderDetailService.deleteStockOrderDetailsList(stockOrderDetailsDeleteList, currentUser.getCompany().getCompanyId());
					}
					if(productUpdateList.size()>0){
						productService.updateProductList(productUpdateList, currentUser.getCompany());
					}
					if(productVariantUpdateList.size()>0){
						productVariantService.updateProductVariantList(productVariantUpdateList, currentUser.getCompany());
					}
					stockOrder.setStatus(statusService.getStatusByStatusId(3)); //completed status
					stockOrder.setOrdrRecvDate(new Date());
					stockOrder.setLastUpdated(new Date());
					stockOrder.setUpdatedBy(currentUser.getUserId());
					stockOrderService.updateStockOrder(stockOrder,currentUser.getCompany().getCompanyId());

					Contact supplier = supplierService.getContactByContactOutletID(stockOrder.getOutletBySourceOutletAssocicationId().getOutletId(), currentUser.getCompany().getCompanyId());
					if(supplier.getContactBalance() == null){
						supplier.setContactBalance(BigDecimal.ZERO);
					}
					supplier.setContactBalance(supplier.getContactBalance().add(new BigDecimal(grandTotal)));
					supplier.setLastUpdated(new Date());
					supplier.setUpdatedBy(currentUser.getUserId());
					supplierService.updateContact(supplier, currentUser.getCompany().getCompanyId());
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.updateandRecStockOrderDetail", 
							"User "+ currentUser.getUserEmail()+"Updated Supplier+"+supplier.getContactId()+" successfully ",false);


					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.STOCKCONTROL);

				}else{
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.addStockOrderDetail", "User "+ 
							currentUser.getUserEmail()+" Unable to add StockOrderDetail+"+ stockOrderDetailBeansList.get(0).getStockOrderDetailId(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderController.addStockOrder",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}		
	}

	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/deleteStockOrderDetail/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response deleteStockOrderDetail(@PathVariable("sessionId") String sessionId,
			@RequestBody StockOrderDetailBean stockOrderDetailBean, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			try {			
				StockOrderDetail stockOrderDetail = new StockOrderDetail();
				stockOrderDetail.setStockOrderDetailId(Integer.parseInt(stockOrderDetailBean.getStockOrderDetailId()));
				stockOrderDetailService.deleteStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());			
				util.AuditTrail(request, currentUser, "PurchaseOrderDetails.deleteStockOrderDetail", "User "+ 
						currentUser.getUserEmail()+" StockOrderDetail Deleted+"+ stockOrderDetailBean.getStockOrderDetailId(),false);
				return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderController.deleteStockOrderDetail",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllStockTransferOrders/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody	Response getAllStockTransferOrders(@PathVariable("sessionId") String sessionId,
			@RequestBody StockOrderBean stockOrdBean, HttpServletRequest request) {

		List<StockOrderBean> stockOrderBeansList = new ArrayList<>();
		List<StockOrder> stockOrderList = null;
		List<StockOrder> stockReturnList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			try {

				stockOrderList = stockOrderService.GetAllStockTransferOrder(currentUser.getCompany().getCompanyId());
				stockReturnList = stockOrderService.GetAllStockReturntoWarehouseOrder(currentUser.getCompany().getCompanyId());
				if(stockReturnList != null){
					for(StockOrder stockOrder: stockReturnList){
						stockOrderList.add(stockOrder);
					}
				}
				if (stockOrderList != null) {
					for (StockOrder stockOrder : stockOrderList) {
						StockOrderBean stockOrderBean = new StockOrderBean();											
						stockOrderBean.setStockRefNo(stockOrder.getStockRefNo());
						int stockOrderId = stockOrder.getStockOrderId();						
						stockOrderBean.setStockOrderId(Integer.toString(stockOrderId));
						stockOrderBean.setStockOrderTypeId(Integer.toString(stockOrder.getStockOrderType().getStockOrderTypeId()));
						stockOrderBeansList.add(stockOrderBean);
					}

					util.AuditTrail(request, currentUser, "PurchaseOrderDetailsController.getAllStockTransferOrders", 
							"User "+ currentUser.getUserEmail()+" fetched all stock transfer orders successfully ",false);
					return new Response(stockOrderBeansList,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "PurchaseOrderDetailsController.getAllStockTransferOrders", 
							"User "+ currentUser.getUserEmail()+" unbale to retrived stock orders ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}


			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderDetailsController.getAllStockTransferOrders",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllProductVariants/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProductVariants(@PathVariable("sessionId") String sessionId,
			@RequestBody StockOrderBean stockOrderBean, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			List<ProductVariant> productVariantList = null;
			List<ProductVariant> recvProductVariantList = null;
			List<Product> productListSource = null;
			List<Product> productListDest = null;
			Map recvProductVariantMap = new HashMap<>();
			Map recvProductMap = new HashMap<>();
			Map productMap = new HashMap<>();
			int outletId = Integer.parseInt(stockOrderBean.getOutletId());
			if(stockOrderBean.getSourceOutletId() != null && !stockOrderBean.getSourceOutletId().equalsIgnoreCase(""))
			{
				outletId = Integer.parseInt(stockOrderBean.getSourceOutletId());
			}
			try {


				productVariantList = productVariantService.getAllProductVariantsByOutletId(outletId,currentUser.getCompany().getCompanyId());
				productListSource = productService.getAllProductsByOutletId(outletId); //Should come in one query for performance optimization
				productListDest = productService.getAllProductsByOutletId(Integer.parseInt(stockOrderBean.getOutletId())); //Should come in one query for performance optimization
				if(productListDest!=null && stockOrderBean.getSourceOutletId() != null && !stockOrderBean.getSourceOutletId().equalsIgnoreCase("")){ //Stock Transfer Case
					for(Product product:productListDest){
						recvProductMap.put(product.getProductUuid(), product.getCurrentInventory());
					}	
				}
				for(Product product:productListSource){
					productMap.put(product.getProductId(), product.getProductName());
					List<ProductVariant> productVariants = productVariantService.getVarientsByProductId(product.getProductId(), currentUser.getCompany().getCompanyId());
					if(productVariants==null || productVariants.size()==0){
						ProductVariantBean productVariantBean = new ProductVariantBean();
						productVariantBean.setIsProduct("true");
						productVariantBean.setProductVariantId(product.getProductId().toString());					
						productVariantBean.setVariantAttributeName(product.getProductName());
						if(product.getCurrentInventory() != null){
							productVariantBean.setCurrentInventory(product.getCurrentInventory().toString());
						}
						else
						{
							productVariantBean.setCurrentInventory("0");
						}
						productVariantBean.setProductName((String)productMap.get(product.getProductId()));
						//productVariantBean.setVariantAttributeName(productVariantBean.getProductName() + " - " + productVariantBean.getVariantAttributeName());
						if(product.getSupplyPriceExclTax() != null){
							productVariantBean.setSupplyPriceExclTax(String.valueOf(product.getSupplyPriceExclTax().intValue()));
						}
						if(stockOrderBean.getSourceOutletId() != null && !stockOrderBean.getSourceOutletId().equalsIgnoreCase("")){
							String recvCurrInventory = String.valueOf(recvProductMap.get(product.getProductUuid()));
							if(!recvCurrInventory.equalsIgnoreCase("null")){
								productVariantBean.setRecCurrentInventory(recvCurrInventory);
							}
							else{
								productVariantBean.setRecCurrentInventory("0");
							}
						}						
						productVariantBeansList.add(productVariantBean);

					}

				}
				if(stockOrderBean.getSourceOutletId() != null && !stockOrderBean.getSourceOutletId().equalsIgnoreCase("")){ //Stock Transfer Case
					recvProductVariantList = productVariantService.getAllProductVariantsByOutletId(Integer.parseInt(stockOrderBean.getOutletId()),currentUser.getCompany().getCompanyId());
					for(ProductVariant productVariant:recvProductVariantList){
						recvProductVariantMap.put(productVariant.getProductVariantUuid(), productVariant.getCurrentInventory());
					}	
				}
				if (productVariantList != null) {
					for (ProductVariant productVariant : productVariantList) {

						ProductVariantBean productVariantBean = new ProductVariantBean();
						productVariantBean.setIsProduct("false");
						productVariantBean.setProductVariantId(productVariant.getProductVariantId().toString());					
						productVariantBean.setVariantAttributeName(productVariant.getVariantAttributeName());
						if(productVariant.getCurrentInventory() != null){
							productVariantBean.setCurrentInventory(productVariant.getCurrentInventory().toString());
						}
						else
						{
							productVariantBean.setCurrentInventory("0");
						}
						productVariantBean.setProductName((String)productMap.get(productVariant.getProduct().getProductId()));
						productVariantBean.setVariantAttributeName(productVariantBean.getProductName() + " - " + productVariantBean.getVariantAttributeName());
						if(productVariant.getSupplyPriceExclTax() != null){
							productVariantBean.setSupplyPriceExclTax(String.valueOf(productVariant.getSupplyPriceExclTax().intValue()));
						}
						if(stockOrderBean.getSourceOutletId() != null && !stockOrderBean.getSourceOutletId().equalsIgnoreCase("")){
							String recvCurrInventory = String.valueOf(recvProductVariantMap.get(productVariant.getProductVariantUuid()));
							if(!recvCurrInventory.equalsIgnoreCase("null")){
								productVariantBean.setRecCurrentInventory(recvCurrInventory);
							}
							else{
								productVariantBean.setRecCurrentInventory("0");
							}
						}
						productVariantBeansList.add(productVariantBean);
					}
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.getAllProductVariants", "User "+ 
							currentUser.getUserEmail()+" Get Products and ProductVariants",false);
					return new Response(productVariantBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.getAllProductVariants", "User "+ 
							currentUser.getUserEmail()+" Get Products and ProductVariants",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderController.getAllProductVariants",
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
	@RequestMapping(value = "/getAllProductsByOutletId/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProductsByOutletId(@PathVariable("sessionId") String sessionId, 
			@RequestBody String outletId, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			List<Product> productList = null;
			try {			
				productList = productService.getAllProductsByOutletIdByCompanyIdGroupByProductUuId(Integer.parseInt(outletId), currentUser.getCompany().getCompanyId());
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
				e.printStackTrace();
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
	@RequestMapping(value = "/getProductVariantsByOutletId/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getProductVariantsByOutletId(@PathVariable("sessionId") String sessionId,
			@RequestBody String outletId, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			List<ProductVariant> productVariantList = null;
			try {			
				productVariantList = productVariantService.getAllProductVariantsByOutletIdGroupbyUuid(Integer.parseInt(outletId), currentUser.getCompany().getCompanyId());
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
				e.printStackTrace();
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllProductsforTransfer/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProductsforTransfer(@PathVariable("sessionId") String sessionId, 
			@RequestBody StockOrderBean stockOrderBean, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			List<Product> productList = null;
			List<Product> recvProductList = null;
			Map recvProductMap = new HashMap<>();
			try {			
				productList = productService.getAllProductsByOutletIdByCompanyIdGroupByProductUuId(Integer.parseInt(stockOrderBean.getSourceOutletId()), currentUser.getCompany().getCompanyId());
				if(stockOrderBean.getSourceOutletId() != null && !stockOrderBean.getSourceOutletId().equalsIgnoreCase("")){ //Stock Transfer Case
					recvProductList = productService.getAllProductsByOutletId(Integer.parseInt(stockOrderBean.getOutletId()));
					for(Product product:recvProductList){
						recvProductMap.put(product.getProductUuid(), product.getCurrentInventory());
					}	
				}

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
							if(stockOrderBean.getSourceOutletId() != null && !stockOrderBean.getSourceOutletId().equalsIgnoreCase("")){
								String recvCurrInventory = String.valueOf(recvProductMap.get(product.getProductUuid()));
								if(!recvCurrInventory.equalsIgnoreCase("null")){
									productVariantBean.setRecCurrentInventory(recvCurrInventory);
								}
								else{
									productVariantBean.setRecCurrentInventory("0");
								}
							}
							productVariantBeansList.add(productVariantBean);
						}
						
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
				e.printStackTrace();
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
	@RequestMapping(value = "/getProductVariantsforTransfer/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getProductVariantsforTransfer(@PathVariable("sessionId") String sessionId,
			@RequestBody StockOrderBean stockOrderBean, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			List<ProductVariant> productVariantList = null;
			List<ProductVariant> recvProductVariantList = null;
			Map recvProductVariantMap = new HashMap<>();
			try {			
				productVariantList = productVariantService.getAllProductVariantsByOutletIdGroupbyUuid(Integer.parseInt(stockOrderBean.getSourceOutletId()), currentUser.getCompany().getCompanyId());
				Map<Integer, Product> productsMap = new HashMap<>();
				List<Product> products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
				if(products!=null){
					for(Product product:products){
						productsMap.put(product.getProductId(), product);
					}
				}
				if(stockOrderBean.getSourceOutletId() != null && !stockOrderBean.getSourceOutletId().equalsIgnoreCase("")){ //Stock Transfer Case
					recvProductVariantList = productVariantService.getAllProductVariantsByOutletId(Integer.parseInt(stockOrderBean.getOutletId()),currentUser.getCompany().getCompanyId());
					for(ProductVariant productVariant:recvProductVariantList){
						recvProductVariantMap.put(productVariant.getProductVariantUuid(), productVariant.getCurrentInventory());
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
						if(stockOrderBean.getSourceOutletId() != null && !stockOrderBean.getSourceOutletId().equalsIgnoreCase("")){
							String recvCurrInventory = String.valueOf(recvProductVariantMap.get(productVariant.getProductVariantUuid()));
							if(!recvCurrInventory.equalsIgnoreCase("null")){
								productVariantBean.setRecCurrentInventory(recvCurrInventory);
							}
							else{
								productVariantBean.setRecCurrentInventory("0");
							}
						}
						if(stockOrderBean.getSourceOutletId() != null && !stockOrderBean.getSourceOutletId().equalsIgnoreCase("")){
							String recvCurrInventory = String.valueOf(recvProductVariantMap.get(productVariant.getProductVariantUuid()));
							if(!recvCurrInventory.equalsIgnoreCase("null")){
								productVariantBean.setRecCurrentInventory(recvCurrInventory);
							}
							else{
								productVariantBean.setRecCurrentInventory("0");
							}
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
