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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.dowhile.Contact;
import com.dowhile.Outlet;
import com.dowhile.Product;
import com.dowhile.ProductVariant;
import com.dowhile.StockOrder;
import com.dowhile.StockOrderType;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.POCreateandReceiveControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.frontend.mapping.bean.StockOrderBean;
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
import com.dowhile.util.SessionValidator;

/**
 * Zafar Shakeel
 */
@Controller
@RequestMapping("/poCreateandReceive")

public class POCreateandReceiveController {
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
	public String getPOCreateandReceiveControllerPartialPage(ModelMap modelMap) {
		return "poCreateandReceive/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getPOCreateandReceiveControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getPOCreateandReceiveControllerData(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<OutletBean> outletBeansList = null;
		List<StockOrderTypeBean> stockOrderTypeBeansList= null;
		List<SupplierBean> supplierBeansList = null;
		List<ProductVariantBean> productBeansList = null;
		List<ProductVariantBean> productVariantBeansList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {

				Response response = getAllOutlets(sessionId,request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					outletBeansList = (List<OutletBean>) response.data;
				}
				response = getAllStockOrderTypes(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					stockOrderTypeBeansList = (List<StockOrderTypeBean>) response.data;
				}
				response = getAllSuppliers(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					supplierBeansList = (List<SupplierBean>) response.data;
				}

				response = getAllProducts(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					productBeansList = (List<ProductVariantBean>) response.data;
				}

				response = getProductVariants(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					productVariantBeansList = (List<ProductVariantBean>) response.data;
				}
				POCreateandReceiveControllerBean POCreateandReceiveControllerBean = new POCreateandReceiveControllerBean();
				POCreateandReceiveControllerBean.setOutletBeansList(outletBeansList);
				POCreateandReceiveControllerBean.setStockOrderTypeBeansList(stockOrderTypeBeansList);
				POCreateandReceiveControllerBean.setSupplierBeansList(supplierBeansList);
				POCreateandReceiveControllerBean.setProductBeansList(productBeansList);
				POCreateandReceiveControllerBean.setProductVariantBeansList(productVariantBeansList);
				util.AuditTrail(request, currentUser, "POCreateandReceiveController.getPOCreateandReceiveControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived POCreateandReceiveControllerData successfully ",false);
				return new Response(POCreateandReceiveControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "POCreateandReceiveController.getPOCreateandReceiveControllerData",
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
	@RequestMapping(value = "/getAllOutlets/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getAllOutlets(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<OutletBean> outletBeansList = new ArrayList<>();
		List<Outlet> outletList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {

				outletList = outletService.getOutlets(currentUser.getCompany().getCompanyId());
				if (outletList != null) {
					for (Outlet outlet : outletList) {

						OutletBean outletBean = new OutletBean();
						outletBean.setOutletId(outlet.getOutletId().toString());
						outletBean.setOutletName(outlet.getOutletName().toString());
						outletBeansList.add(outletBean);
					}

					util.AuditTrail(request, currentUser, "POCreateandReceiveController.getAllOutlets", 
							"User "+ currentUser.getUserEmail()+" fetched all outlets successfully ",false);
					return new Response(outletBeansList,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "POCreateandReceiveController.getAllOutlets", 
							"User "+ currentUser.getUserEmail()+" unbale to retrived all outlets ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "POCreateandReceiveController.getAllOutlets",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllStockOrderTypes/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getAllStockOrderTypes(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<StockOrderTypeBean> stockOrderTypeBeansList = new ArrayList<>();
		List<StockOrderType> stockOrderTypeList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {
				stockOrderTypeList = stockOrderTypeService.getAllStockOrderType();
				if (stockOrderTypeList != null) {
					for (StockOrderType stockOrderType : stockOrderTypeList) {

						StockOrderTypeBean stockOrderTypeBean = new StockOrderTypeBean();
						stockOrderTypeBean.setStockOrderTypeId(stockOrderType.getStockOrderTypeId().toString());
						stockOrderTypeBean.setStockOrderTypeCode(stockOrderType.getStockOrderTypeCode());
						stockOrderTypeBean.setStockOrderTypeDesc(stockOrderType.getStockOrderTypeDesc());
						stockOrderTypeBeansList.add(stockOrderTypeBean);
					}

					util.AuditTrail(request, currentUser, "POCreateandReceiveController.getAllStockOrderTypes", 
							"User "+ currentUser.getUserEmail()+" fetched all stock order types successfully ",false);
					return new Response(stockOrderTypeBeansList,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "POCreateandReceiveController.getAllStockOrderTypes", 
							"User "+ currentUser.getUserEmail()+" unbale to retrived all stock order types ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "POCreateandReceiveController.getAllStockOrderTypes",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllSuppliers/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getAllSuppliers(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {
		List<SupplierBean> supplierBeansList = new ArrayList<>();
		List<Contact> supplierList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");


			try {

				supplierList = supplierService.getAllContacts(currentUser.getCompany().getCompanyId());
				if (supplierList != null) {
					for (Contact supplier : supplierList) {
						if(supplier.getContactType()!=null && supplier.getContactType().contains("SUPPLIER")){
							SupplierBean supplierBean = new SupplierBean();
							supplierBean.setSupplierId(supplier.getContactId().toString());
							supplierBean.setSupplierName(supplier.getContactName().toString());
							supplierBeansList.add(supplierBean);
						}

						
					}

					util.AuditTrail(request, currentUser, "POCreateandReceiveController.getAllSuppliers", 
							"User "+ currentUser.getUserEmail()+" fetched all suppliers successfully ",false);
					return new Response(supplierBeansList,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "POCreateandReceiveController.getAllSuppliers", 
							"User "+ currentUser.getUserEmail()+" unbale to retrived all suppliers ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "POCreateandReceiveController.getAllSuppliers",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addStockOrder/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response addStockOrder(@PathVariable("sessionId") String sessionId,
			@RequestBody StockOrderBean stockOrderBean, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			try {			
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				if (stockOrderBean != null) {				
					StockOrder stockOrder = new StockOrder();
					stockOrder.setActiveIndicator(true);
					stockOrder.setAutofillReorder(Boolean.valueOf(stockOrderBean.getAutofillReorder()));
					stockOrder.setCreatedBy(currentUser.getUserId());
					stockOrder.setCreatedDate(new Date());
					stockOrder.setDiliveryDueDate(dateFormat.parse(stockOrderBean.getDiliveryDueDate()));
					stockOrder.setLastUpdated(new Date());
					stockOrder.setOrderNo(stockOrderBean.getOrderNo());
					//stockOrder.setOrdrRecvDate(dateFormat.parse(stockOrderBean.getOrdrRecvDate()));
					stockOrder.setOutletByOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getOutletId()),currentUser.getCompany().getCompanyId()));
					if(stockOrderBean.getSourceOutletId() != null && stockOrderBean.getSourceOutletId() != ""){
						stockOrder.setOutletBySourceOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getSourceOutletId()),currentUser.getCompany().getCompanyId()));
					}
					//stockOrder.setRemarks(stockOrderBean.getRemarks());
					//stockOrder.setReturnNo(stockOrderBean.getReturnNo());
					stockOrder.setStatus(statusService.getStatusByStatusId(Integer.parseInt(stockOrderBean.getStatusId()))); 				
					//stockOrder.setStockOrderDate(dateFormat.parse(stockOrderBean.getStockOrderDate()));
					//stockOrder.setStockOrderId(Integer.parseInt(stockOrderBean.getStockOrderId()));
					stockOrder.setStockOrderType(stockOrderTypeService.getStockOrderTypeByStockOrderTypeId(Integer.parseInt(stockOrderBean.getStockOrderTypeId()))); 
					//stockOrder.setStockOrderType(stockOrderTypeService.getStockOrderTypeByStockOrderTypeId(1));
					stockOrder.setStockRefNo(stockOrderBean.getStockRefNo());
					if(stockOrderBean.getSupplierId() != null && stockOrderBean.getSupplierId() != ""){
						stockOrder.setContactId(Integer.parseInt(stockOrderBean.getSupplierId()));
					}
					stockOrder.setContactInvoiceNo(stockOrderBean.getSupplierInvoiceNo());
					stockOrder.setUpdatedBy(currentUser.getUserId());
					stockOrder.setCompany(currentUser.getCompany());
					stockOrderService.addStockOrder(stockOrder,currentUser.getCompany().getCompanyId());
					String path = LayOutPageConstants.PURCHASE_ORDER_RECV;
					if(stockOrder.getStockOrderType().getStockOrderTypeId() == 2){
						path = LayOutPageConstants.STOCK_RETURN_DETAILS;
					}
					else if(stockOrder.getStockOrderType().getStockOrderTypeId() == 3){
						path = LayOutPageConstants.STOCK_TRANSFER_DETAILS;
					}
					util.AuditTrail(request, currentUser, "POCreateandReceiveController.addStockOrder", 
							"User "+ currentUser.getUserEmail()+" Added StockOrder+"+stockOrderBean.getStockOrderId()+" successfully ",false);
					return new Response(stockOrder.getStockOrderId(),StatusConstants.SUCCESS,path);
				}else{
					util.AuditTrail(request, currentUser, "POCreateandReceiveController.addStockOrder", "User "+ 
							currentUser.getUserEmail()+" Unable to add StockOrder : "+stockOrderBean.getStockOrderId(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "POCreateandReceiveController.addStockOrder",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateStockOrder/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response updateStockOrder(@PathVariable("sessionId") String sessionId,
			@RequestBody StockOrderBean stockOrderBean, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			try {			
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				if (stockOrderBean != null) {				
					StockOrder stockOrder = stockOrderService.getStockOrderByStockOrderID(Integer.parseInt(stockOrderBean.getStockOrderId()),currentUser.getCompany().getCompanyId());
					stockOrder.setStockOrderId(Integer.parseInt(stockOrderBean.getStockOrderId()));
					stockOrder.setActiveIndicator(true);
					stockOrder.setAutofillReorder(Boolean.valueOf(stockOrderBean.getAutofillReorder()));
					stockOrder.setDiliveryDueDate(dateFormat.parse(stockOrderBean.getDiliveryDueDate()));
					stockOrder.setLastUpdated(new Date());
					stockOrder.setOrderNo(stockOrderBean.getOrderNo());
					stockOrder.setOutletByOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getOutletId()),currentUser.getCompany().getCompanyId()));
					if(stockOrderBean.getSourceOutletId() != null && stockOrderBean.getSourceOutletId() != ""){
						stockOrder.setOutletBySourceOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getSourceOutletId()),currentUser.getCompany().getCompanyId()));
					}
					stockOrder.setStatus(statusService.getStatusByStatusId(Integer.parseInt(stockOrderBean.getStatusId()))); 				
					stockOrder.setStockOrderType(stockOrderTypeService.getStockOrderTypeByStockOrderTypeId(Integer.parseInt(stockOrderBean.getStockOrderTypeId())));
					stockOrder.setStockRefNo(stockOrderBean.getStockRefNo());
					if(stockOrderBean.getSupplierId() != null && stockOrderBean.getSupplierId() != ""){ 
						stockOrder.setContactId(Integer.parseInt(stockOrderBean.getSupplierId()));
					}
					stockOrder.setContactInvoiceNo(stockOrderBean.getSupplierInvoiceNo());
					stockOrder.setUpdatedBy(currentUser.getUserId());
					stockOrderService.updateStockOrder(stockOrder,currentUser.getCompany().getCompanyId());

					util.AuditTrail(request, currentUser, "POCreateandReceiveController.addStockOrder", 
							"User "+ currentUser.getUserEmail()+" Added StockOrder+"+stockOrderBean.getStockOrderId()+" successfully ",false);
					String path = LayOutPageConstants.PURCHASE_ORDER_RECV;
					if(stockOrder.getStockOrderType().getStockOrderTypeId() == 2){
						path = LayOutPageConstants.STOCK_RETURN_EDIT_PRODUCTS;
					}
					else if(stockOrder.getStockOrderType().getStockOrderTypeId() == 3){
						path = LayOutPageConstants.STOCK_TRANSFER_EDIT_PRODUCTS;
					}
					if(stockOrder.getStatus().getStatusId() == 8){
						path = LayOutPageConstants.STOCKCONTROL;
					}
					util.AuditTrail(request, currentUser, "POCreateandReceiveController.addStockOrder", "User "+ 
							currentUser.getUserEmail()+" added StockOrder : "+stockOrderBean.getStockOrderId(),false);
					return new Response(stockOrder.getStockOrderId(),StatusConstants.SUCCESS,path);
				}else{
					util.AuditTrail(request, currentUser, "POCreateandReceiveController.addStockOrder", "User "+ 
							currentUser.getUserEmail()+" Unable to add StockOrder : "+stockOrderBean.getStockOrderId(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "POCreateandReceiveController.addStockOrder",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllProductVariants/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProductVariants(@PathVariable("sessionId") String sessionId, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			List<ProductVariant> productVariantList = null;
			List<Product> productList = null;
			Map productMap = new HashMap<>();
			try {			
				productVariantList = productVariantService.getAllProductVariantsGroupbyUuid(currentUser.getCompany().getCompanyId());
				productList = productService.getAllProductsByCompanyIdGroupByProductUuId(currentUser.getCompany().getCompanyId()); //Should come in one query for performance optimization
				for(Product product:productList){
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
						productVariantBean.setProductName(product.getProductName());
						//productVariantBean.setVariantAttributeName(productVariantBean.getProductName() + " - " + productVariantBean.getVariantAttributeName());
						if(product.getSupplyPriceExclTax() != null){
							productVariantBean.setSupplyPriceExclTax(String.valueOf(product.getSupplyPriceExclTax().intValue()));
						}
						productVariantBeansList.add(productVariantBean);

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
						productVariantBeansList.add(productVariantBean);
					}
					util.AuditTrail(request, currentUser, "POCreateandReceiveDetails.getAllProductVariants", "User "+ 
							currentUser.getUserEmail()+" Get Products and ProductVariants",false);
					return new Response(productVariantBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "POCreateandReceiveDetails.getAllProductVariants", "User "+ 
							currentUser.getUserEmail()+" Get Products and ProductVariants",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "POCreateandReceiveController.getAllProductVariants",
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
