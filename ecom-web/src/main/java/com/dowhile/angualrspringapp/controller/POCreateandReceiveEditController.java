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
import java.util.Objects;

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
import com.dowhile.StockOrderDetail;
import com.dowhile.StockOrderDetailCustom;
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
import com.dowhile.util.SessionValidator;
import com.dowhile.wrapper.StockDataProductsWrapper;

/**
 * Zafar Shakeel
 */
@Controller
@RequestMapping("/poCreateandReceiveEdit")

public class POCreateandReceiveEditController {
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
	private StockDataProductsWrapper stockDataProductsWrapper = new StockDataProductsWrapper();
	private Map productVariantMap = new HashMap<>();
	private Map productMap = new HashMap<>();
	private Map productIdsMap = new HashMap<>();
	private Map productVariantIdsMap = new HashMap<>();
	private List<Product> products = new ArrayList<>();

	@RequestMapping("/layout")
	public String getPOCreateandReceiveEditControllerPartialPage(ModelMap modelMap) {
		return "poCreateandReceiveEdit/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getPOCreateandReceiveEditControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getPOCreateandReceiveEditControllerData(@PathVariable("sessionId") String sessionId,
			@RequestBody StockOrderBean stockOrderBean, HttpServletRequest request) {

		List<OutletBean> outletBeansList = null;
		List<StockOrderTypeBean> stockOrderTypeBeansList= null;
		List<SupplierBean> supplierBeansList = null;
		List<ProductVariantBean> productBeansList = null;
		List<ProductVariantBean> productVariantBeansList = null;
		List<StockOrderDetailBean> stockOrderDetailBeansList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {
				stockDataProductsWrapper = stockOrderService.GetStockWithProductsData(currentUser.getCompany().getCompanyId());

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

				response = getAllDetailsByStockOrderId(sessionId, stockOrderBean, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					stockOrderDetailBeansList = (List<StockOrderDetailBean>) response.data;
				}
				POCreateandReceiveControllerBean POCreateandReceiveControllerBean = new POCreateandReceiveControllerBean();
				POCreateandReceiveControllerBean.setOutletBeansList(outletBeansList);
				POCreateandReceiveControllerBean.setStockOrderTypeBeansList(stockOrderTypeBeansList);
				POCreateandReceiveControllerBean.setSupplierBeansList(supplierBeansList);
				POCreateandReceiveControllerBean.setProductBeansList(productBeansList);
				POCreateandReceiveControllerBean.setProductVariantBeansList(productVariantBeansList);
				POCreateandReceiveControllerBean.setStockOrderDetailBeansList(stockOrderDetailBeansList);
				POCreateandReceiveControllerBean.setProductVariantMap(productVariantMap);
				POCreateandReceiveControllerBean.setProductMap(productMap);
				util.AuditTrail(request, currentUser, "POCreateandReceiveController.getPOCreateandReceiveControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived POCreateandReceiveControllerData successfully ",false);
				return new Response(POCreateandReceiveControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();
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
				//outletList = outletService.getOutlets(currentUser.getCompany().getCompanyId());
				outletList = stockDataProductsWrapper.getOutletList();
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
				e.printStackTrace();
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
				//stockOrderTypeList = stockOrderTypeService.getAllStockOrderType();
				stockOrderTypeList = stockDataProductsWrapper.getStockOrderTypeList();
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
				e.printStackTrace();
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
				//supplierList = supplierService.getAllContacts(currentUser.getCompany().getCompanyId());
				supplierList = stockDataProductsWrapper.getSupplierList();
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
				e.printStackTrace();
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
	@RequestMapping(value = "/getAllDetailsByStockOrderId/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllDetailsByStockOrderId(@PathVariable("sessionId") String sessionId,
			@RequestBody StockOrderBean stockOrderBean, HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			List<StockOrderDetailBean> stockOrderDetailBeansList = new ArrayList<>();
			List<StockOrderDetailCustom> stockOrderDetailCustomList = new ArrayList<>();
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				stockOrderDetailCustomList = stockOrderDetailService.getStockOrderDetailCustom(Integer.parseInt(stockOrderBean.getStockOrderId()), Integer.parseInt(stockOrderBean.getOutletId()));				
			int order = 1;
			if (stockOrderDetailCustomList != null) {
				for (StockOrderDetailCustom stockOrderDetailCustom : stockOrderDetailCustomList) {
					StockOrderDetailBean stockOrderDetailBean = new StockOrderDetailBean();
					stockOrderDetailBean.setOrderProdQty(Objects.toString(stockOrderDetailCustom.getORDER_PROD_QTY(), ""));
					stockOrderDetailBean.setOrdrSupplyPrice(Objects.toString(stockOrderDetailCustom.getORDR_SUPPLY_PRICE(),""));
					if(stockOrderDetailCustom.getRETAIL_PRICE() != null){
						stockOrderDetailBean.setRetailPrice(Objects.toString(stockOrderDetailCustom.getRETAIL_PRICE(),""));
					}
					if(!stockOrderDetailCustom.isIS_PRODUCT()){						
						stockOrderDetailBean.setProductVariantId(Objects.toString(stockOrderDetailCustom.getPRODUCT_VARIANT_ASSOCICATION_ID(),""));
						stockOrderDetailBean.setVariantAttributeName(Objects.toString(stockOrderDetailCustom.getVariantAttributeName(),""));
						stockOrderDetailBean.setProductVariantCurrInventory(Objects.toString(stockOrderDetailCustom.getProduct_variant_current_inventory(),""));
						stockOrderDetailBean.setIsProduct("false");
						stockOrderDetailBean.setProductVariantRecvOutletInventory(Objects.toString(stockOrderDetailCustom.getProduct_variant_destination_inventory(),""));

					}					
					else{
						stockOrderDetailBean.setProductVariantId(Objects.toString(stockOrderDetailCustom.getPRODUCT_ASSOCIATION_ID(),""));
						stockOrderDetailBean.setVariantAttributeName(Objects.toString(stockOrderDetailCustom.getProduct_name(),""));
						stockOrderDetailBean.setProductVariantCurrInventory(Objects.toString(stockOrderDetailCustom.getProduct_current_inventory(),""));
						stockOrderDetailBean.setIsProduct("true");
						stockOrderDetailBean.setProductVariantRecvOutletInventory(Objects.toString(stockOrderDetailCustom.getProduct_destination_current_inventory(),""));

					}
					stockOrderDetailBean.setRecvProdQty(Objects.toString(stockOrderDetailCustom.getRECV_PROD_QTY(),""));
					stockOrderDetailBean.setRecvSupplyPrice(Objects.toString(stockOrderDetailCustom.getRECV_SUPPLY_PRICE(),""));
					stockOrderDetailBean.setStockOrderDetailId(Objects.toString(stockOrderDetailCustom.getSTOCK_ORDER_DETAIL_ID(),""));
					stockOrderDetailBean.setOrder(Integer.toString(order));
					if(stockOrderBean.getRetailPriceBill() != null && stockOrderBean.getRetailPriceBill() != ""){
						if(!stockOrderBean.getRetailPriceBill().toString().equalsIgnoreCase("true")){
							if(stockOrderDetailCustom.getORDER_PROD_QTY() != null &&  stockOrderDetailCustom.getORDR_SUPPLY_PRICE() != null)
							{
								Double total = stockOrderDetailCustom.getORDER_PROD_QTY() * stockOrderDetailCustom.getORDR_SUPPLY_PRICE().doubleValue();
								NumberFormat formatter = new DecimalFormat("###.##");  
								String strTotal = formatter.format(total);
								stockOrderDetailBean.setTotal(strTotal);
							}
						}
						else{
							if(stockOrderDetailCustom.getORDER_PROD_QTY() != null &&  stockOrderDetailCustom.getRETAIL_PRICE() != null)
							{
								Double total = stockOrderDetailCustom.getORDER_PROD_QTY() * stockOrderDetailCustom.getRETAIL_PRICE().doubleValue();
								NumberFormat formatter = new DecimalFormat("###.##");  
								String strTotal = formatter.format(total);
								stockOrderDetailBean.setTotal(strTotal);
							}
						}
					}
					else{
						if(stockOrderDetailCustom.getORDER_PROD_QTY() != null &&  stockOrderDetailCustom.getORDR_SUPPLY_PRICE() != null)
						{
							Double total = stockOrderDetailCustom.getORDER_PROD_QTY() * stockOrderDetailCustom.getORDR_SUPPLY_PRICE().doubleValue();
							NumberFormat formatter = new DecimalFormat("###.##");  
							String strTotal = formatter.format(total);
							stockOrderDetailBean.setTotal(strTotal);
						}
					}
					if(stockOrderDetailCustom.getRECV_PROD_QTY() != null && stockOrderDetailCustom.getRECV_SUPPLY_PRICE() != null){
						Double recTotal = stockOrderDetailCustom.getRECV_PROD_QTY() * stockOrderDetailCustom.getRECV_SUPPLY_PRICE().doubleValue();
						NumberFormat formatter = new DecimalFormat("###.##");  
						String strRecTotal = formatter.format(recTotal);
						stockOrderDetailBean.setRecvTotal(strRecTotal);
					}					
					stockOrderDetailBean.setStockOrderId(stockOrderBean.getStockOrderId());
					order++;
					stockOrderDetailBeansList.add(stockOrderDetailBean);
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
				e.printStackTrace();
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
				e.printStackTrace();
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
				e.printStackTrace();
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
	@RequestMapping(value = "/getAllProducts/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProducts(@PathVariable("sessionId") String sessionId, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			productMap = new HashMap<>();
			try {		
					products = stockDataProductsWrapper.getProductList();			
				if(products != null){
					for(Product product:products){
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
							productMap.put(product.getSku().toLowerCase(), productVariantBean);
						}
						productIdsMap.put(product.getProductId(), product);
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
			productVariantMap = new HashMap<>();
			try {
				productVariantList = stockDataProductsWrapper.getProductVariantList();
				Map<Integer, Product> productsMap = new HashMap<>();
				if(products.isEmpty()) {
					if(session.getAttribute("redirectCall") != null && session.getAttribute("redirectCall") == "1") {
						if(session.getAttribute("productIdsMap") != null) {
							productsMap = (HashMap<Integer, Product>)session.getAttribute("productIdsMap");
							products = new ArrayList<Product>(productsMap.values());
						}
						else {
							products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
						}
					}
					else {
						products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
					}
				}		
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
						productVariantMap.put(productVariant.getSku().toLowerCase(), productVariantBean);
						productVariantIdsMap.put(productVariant.getProductVariantId(), productVariant);
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

	
	public Map getProductVariantMap() {
		return productVariantMap;
	}

	public void setProductVariantMap(HashMap productVariantMap) {
		this.productVariantMap = productVariantMap;
	}
	public Map getProductMap() {
		return productMap;
	}

	public void setProductMap(Map productMap) {
		this.productMap = productMap;
	}

}
