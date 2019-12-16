package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.dowhile.Configuration;
import com.dowhile.Contact;
import com.dowhile.Outlet;
import com.dowhile.StockOrder;
import com.dowhile.StockOrderType;
import com.dowhile.User;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.PurchaseOrderControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.StockOrderBean;
import com.dowhile.frontend.mapping.bean.StockOrderTypeBean;
import com.dowhile.frontend.mapping.bean.SupplierBean;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.ContactService;
import com.dowhile.service.OutletService;
import com.dowhile.service.StatusService;
import com.dowhile.service.StockOrderService;
import com.dowhile.service.StockOrderTypeService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;
import com.dowhile.wrapper.StockBasicDataWrapper;

/**
 * Zafar Shakeel
 */
@Controller
@RequestMapping("/purchaseOrder")
public class PurchaseOrderController {
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
	private ConfigurationService configurationService;

	private StockBasicDataWrapper stockBasicDataWrapper = new StockBasicDataWrapper();
	
	@RequestMapping("/layout")
	public String getPurchaseOrderControllerPartialPage(ModelMap modelMap) {
		return "purchaseOrder/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getPurchaseOrderControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getPurchaseOrderControllerData(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<OutletBean> outletBeansList = null;
		List<StockOrderTypeBean> stockOrderTypeBeansList= null;
		List<SupplierBean> supplierBeansList = null;

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
			try {				
				stockBasicDataWrapper = stockOrderService.GetStockBasicData(currentUser.getCompany().getCompanyId());
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
				PurchaseOrderControllerBean purchaseOrderControllerBean = new PurchaseOrderControllerBean();
				purchaseOrderControllerBean.setOutletBeansList(outletBeansList);
				purchaseOrderControllerBean.setStockOrderTypeBeansList(stockOrderTypeBeansList);
				purchaseOrderControllerBean.setSupplierBeansList(supplierBeansList);
				Configuration configuration = configurationMap.get("RETAIL_PRICE_BILL");
				if(configuration != null ){
					if(configuration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
						purchaseOrderControllerBean.setRetailPriceBill("true");
					}
					else{
						purchaseOrderControllerBean.setRetailPriceBill("false");
					}
				}
				else{
					purchaseOrderControllerBean.setRetailPriceBill("false");
				}		

				util.AuditTrail(request, currentUser, "PurchaseOrderController.getPurchaseOrderControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived PurchaseOrderControllerData successfully ",false);
				return new Response(purchaseOrderControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderController.getPurchaseOrderControllerData",
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
	@RequestMapping(value = "/stockSupplierTransferRetailPrice/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response stockSupplierTransferRetailPrice(@PathVariable("sessionId") String sessionId,
			@RequestBody StockOrderBean stockOrderBean, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
			User currentUser = (User) session.getAttribute("user");	
			String retailPriceBill =  "false";
			try {			
				
				Configuration stockSupplierTransferRetailBill = configurationMap.get("RETAIL_PRICE_BILL_STOCK_SUPPLIER_TRANSFER");
				if (stockOrderBean != null) {	
					if(stockOrderBean.getStockOrderTypeId().toString().equalsIgnoreCase("5")){
						if(stockSupplierTransferRetailBill != null ){
							if(stockSupplierTransferRetailBill.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
								retailPriceBill = stockSupplierTransferRetailBill.getPropertyValue().toString();
							}
							else{
								retailPriceBill = stockSupplierTransferRetailBill.getPropertyValue().toString();
							}
						}
						else{
							retailPriceBill = "false";
						}
					}
					util.AuditTrail(request, currentUser, "PurchaseOrderController.stockSupplierTransferRetailPrice", 
							"User "+ currentUser.getUserEmail()+" get StockSupplierTransfer retail price bill+"+stockOrderBean.getStockOrderId()+" successfully ",false);
					return new Response(retailPriceBill,StatusConstants.SUCCESS,	LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "PurchaseOrderController.stockSupplierTransferRetailPrice", "User "+ 
							currentUser.getUserEmail()+" Unable to get StockSupplierTransfer retail price bill : ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderController.stockSupplierTransferRetailPrice",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
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
				outletList = stockBasicDataWrapper.getOutletList();
				if (outletList != null) {
					for (Outlet outlet : outletList) {

						OutletBean outletBean = new OutletBean();
						outletBean.setOutletId(outlet.getOutletId().toString());
						outletBean.setOutletName(outlet.getOutletName().toString());
						if(outlet.getIsHeadOffice()!= null){
							outletBean.setIsHeadOffice(outlet.getIsHeadOffice().toString());
						}
						outletBeansList.add(outletBean);
					}

					util.AuditTrail(request, currentUser, "PurchaseOrderController.getAllOutlets", 
							"User "+ currentUser.getUserEmail()+" fetched all outlets successfully ",false);
					return new Response(outletBeansList,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "PurchaseOrderController.getAllOutlets", 
							"User "+ currentUser.getUserEmail()+" unbale to retrived all outlets ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderController.getAllOutlets",
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
				stockOrderTypeList = stockBasicDataWrapper.getStockOrderTypeList();
				if (stockOrderTypeList != null) {
					for (StockOrderType stockOrderType : stockOrderTypeList) {

						StockOrderTypeBean stockOrderTypeBean = new StockOrderTypeBean();
						stockOrderTypeBean.setStockOrderTypeId(stockOrderType.getStockOrderTypeId().toString());
						stockOrderTypeBean.setStockOrderTypeCode(stockOrderType.getStockOrderTypeCode());
						stockOrderTypeBean.setStockOrderTypeDesc(stockOrderType.getStockOrderTypeDesc());
						stockOrderTypeBeansList.add(stockOrderTypeBean);
					}

					util.AuditTrail(request, currentUser, "PurchaseOrderController.getAllStockOrderTypes", 
							"User "+ currentUser.getUserEmail()+" fetched all stock order types successfully ",false);
					return new Response(stockOrderTypeBeansList,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "PurchaseOrderController.getAllStockOrderTypes", 
							"User "+ currentUser.getUserEmail()+" unbale to retrived all stock order types ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderController.getAllStockOrderTypes",
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
				supplierList = stockBasicDataWrapper.getSupplierList();
				if (supplierList != null) {
					for (Contact supplier : supplierList) {
						if(supplier.getContactType()!=null && supplier.getContactType().contains("SUPPLIER")){
							SupplierBean supplierBean = new SupplierBean();
							supplierBean.setSupplierId(supplier.getContactId().toString());
							supplierBean.setSupplierName(supplier.getContactName().toString());
							if(supplier.getContactOutletId() != null){
								supplierBean.setOutletId(supplier.getContactOutletId().toString());
							}
							supplierBeansList.add(supplierBean);
						}
					}
					util.AuditTrail(request, currentUser, "PurchaseOrderController.getAllSuppliers", 
							"User "+ currentUser.getUserEmail()+" fetched all suppliers successfully ",false);
					return new Response(supplierBeansList,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "PurchaseOrderController.getAllSuppliers", 
							"User "+ currentUser.getUserEmail()+" unbale to retrived all suppliers ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderController.getAllSuppliers",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getHeadOffice/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getHeadOffice(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {
		SupplierBean supplierBean = null;
		Contact supplier = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");		
			try {
				Outlet outlet = outletService.getHeadOfficeOutlet(currentUser.getCompany().getCompanyId());
				if(outlet != null){
					supplier = supplierService.getContactByContactOutletID(outlet.getOutletId(), currentUser.getCompany().getCompanyId());
					if (supplier != null) {
						supplierBean = new SupplierBean();
						supplierBean.setSupplierId(supplier.getContactId().toString());
						supplierBean.setSupplierName(supplier.getContactName().toString());
						if(supplier.getContactOutletId() != null){
							supplierBean.setOutletId(supplier.getContactOutletId().toString());
						}
						util.AuditTrail(request, currentUser, "PurchaseOrderController.getAllSuppliers", 
								"User "+ currentUser.getUserEmail()+" fetched all suppliers successfully ",false);
					}
					return new Response(supplierBean,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "PurchaseOrderController.getAllSuppliers", 
							"User "+ currentUser.getUserEmail()+" unbale to retrived all suppliers ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}
			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderController.getAllSuppliers",
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
					if(stockOrderBean.getRetailPriceBill() != null){
						stockOrder.setRetailPriceBill(Boolean.valueOf(stockOrderBean.getRetailPriceBill()));
					}
					stockOrder.setCreatedBy(currentUser.getUserId());
					stockOrder.setCreatedDate(new Date());
					stockOrder.setDiliveryDueDate(dateFormat.parse(stockOrderBean.getDiliveryDueDate()));
					stockOrder.setLastUpdated(new Date());
					stockOrder.setOrderNo(stockOrderBean.getOrderNo());
					stockOrder.setTotalAmount(new BigDecimal(0));
					stockOrder.setTotalItems(new BigDecimal(0));
					//stockOrder.setOrdrRecvDate(dateFormat.parse(stockOrderBean.getOrdrRecvDate()));

					//Added by Yameen
					//In case of self process order we are setting remarks
					if(stockOrderBean.getRemarks()!=null && !stockOrderBean.getRemarks().equalsIgnoreCase("")){
						stockOrder.setRemarks(stockOrderBean.getRemarks());

					}
					//stockOrder.setReturnNo(stockOrderBean.getReturnNo());
					stockOrder.setStatus(statusService.getStatusByStatusId(Integer.parseInt(stockOrderBean.getStatusId().trim()))); 				
					//stockOrder.setStockOrderDate(dateFormat.parse(stockOrderBean.getStockOrderDate()));
					//stockOrder.setStockOrderId(Integer.parseInt(stockOrderBean.getStockOrderId()));
					stockOrder.setStockOrderType(stockOrderTypeService.getStockOrderTypeByStockOrderTypeId(Integer.parseInt(stockOrderBean.getStockOrderTypeId()))); 
					//stockOrder.setStockOrderType(stockOrderTypeService.getStockOrderTypeByStockOrderTypeId(1));
					stockOrder.setStockRefNo(stockOrderBean.getStockRefNo());
					if(stockOrderBean.getSupplierId() != null && stockOrderBean.getSupplierId() != ""){
						stockOrder.setContactId(Integer.parseInt(stockOrderBean.getSupplierId()));
					}
					stockOrder.setOutletByOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getOutletId()),currentUser.getCompany().getCompanyId()));
					if(stockOrderBean.getSourceOutletId() != null && stockOrderBean.getSourceOutletId() != ""){
						stockOrder.setOutletBySourceOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getSourceOutletId()),currentUser.getCompany().getCompanyId()));
					}
					/*Outlet outlet = outletService.getOuletByOutletId(currentUser.getOutlet().getOutletId(),currentUser.getCompany().getCompanyId());
					if(outlet.getIsHeadOffice() != null){
						if(outlet.getIsHeadOffice() != true){
							stockOrder.setOutletBySourceOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getOutletId()), currentUser.getCompany().getCompanyId()));
							stockOrder.setOutletByOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getSupplierId()), currentUser.getCompany().getCompanyId()));
						}
					}
					else{
						stockOrder.setOutletBySourceOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getOutletId()), currentUser.getCompany().getCompanyId()));
						stockOrder.setOutletByOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getSupplierId()), currentUser.getCompany().getCompanyId()));
					}*/
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
					else if(stockOrder.getStockOrderType().getStockOrderTypeId() == 5){
						path = LayOutPageConstants.STOCK_SUPPLIER_TRANSFER_DETAILS;
					}
					util.AuditTrail(request, currentUser, "PurchaseOrderController.addStockOrder", 
							"User "+ currentUser.getUserEmail()+" Added StockOrder+"+stockOrderBean.getStockOrderId()+" successfully ",false);
					return new Response(stockOrder.getStockOrderId(),StatusConstants.SUCCESS,path);
				}else{
					util.AuditTrail(request, currentUser, "PurchaseOrderController.addStockOrder", "User "+ 
							currentUser.getUserEmail()+" Unable to add StockOrder : "+stockOrderBean.getStockOrderId(),false);
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
					if(stockOrderBean.getStockOrderId() != null){
						stockOrder.setStockOrderId(Integer.parseInt(stockOrderBean.getStockOrderId()));
					}
					stockOrder.setActiveIndicator(true);
					if(stockOrderBean.getRetailPriceBill() != null){
						stockOrder.setRetailPriceBill(Boolean.valueOf(stockOrderBean.getRetailPriceBill()));
					}
					if(stockOrderBean.getAutofillReorder() != null){
						stockOrder.setAutofillReorder(Boolean.valueOf(stockOrderBean.getAutofillReorder()));
					}
					if(stockOrderBean.getDiliveryDueDate() != null){
						stockOrder.setDiliveryDueDate(dateFormat.parse(stockOrderBean.getDiliveryDueDate()));
					}
					stockOrder.setLastUpdated(new Date());
					if(stockOrderBean.getOrderNo()!= null){
						stockOrder.setOrderNo(stockOrderBean.getOrderNo());
					}
					stockOrder.setOutletByOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getOutletId()),currentUser.getCompany().getCompanyId()));
					if(stockOrderBean.getSourceOutletId() != null && stockOrderBean.getSourceOutletId() != ""){
						stockOrder.setOutletBySourceOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getSourceOutletId()),currentUser.getCompany().getCompanyId()));
					}
					/*Outlet outlet = outletService.getOuletByOutletId(currentUser.getOutlet().getOutletId(),currentUser.getCompany().getCompanyId());
					if(outlet.getIsHeadOffice() != null){
						if(outlet.getIsHeadOffice() != true){
							stockOrder.setOutletBySourceOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getOutletId()), currentUser.getCompany().getCompanyId()));
							stockOrder.setOutletByOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getSupplierId()), currentUser.getCompany().getCompanyId()));
						}
					}
					else{
						stockOrder.setOutletBySourceOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getOutletId()), currentUser.getCompany().getCompanyId()));
						stockOrder.setOutletByOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getSupplierId()), currentUser.getCompany().getCompanyId()));
					}*/					
					if(stockOrderBean.getStatusId() != null){
						stockOrder.setStatus(statusService.getStatusByStatusId(Integer.parseInt(stockOrderBean.getStatusId()))); 				
					}
					if(stockOrderBean.getStockOrderTypeId() != null){
						stockOrder.setStockOrderType(stockOrderTypeService.getStockOrderTypeByStockOrderTypeId(Integer.parseInt(stockOrderBean.getStockOrderTypeId())));
					}
					if(stockOrderBean.getStockRefNo() != null){
						stockOrder.setStockRefNo(stockOrderBean.getStockRefNo());
					}
					if(stockOrderBean.getSupplierId() != null && stockOrderBean.getSupplierId() != ""){ 
						stockOrder.setContactId(Integer.parseInt(stockOrderBean.getSupplierId()));
					}
					if(stockOrderBean.getSupplierInvoiceNo() != null){
						stockOrder.setContactInvoiceNo(stockOrderBean.getSupplierInvoiceNo());
					}
					stockOrder.setUpdatedBy(currentUser.getUserId());
			
					stockOrderService.updateStockOrder(stockOrder,currentUser.getCompany().getCompanyId());

					util.AuditTrail(request, currentUser, "PurchaseOrderController.addStockOrder", 
							"User "+ currentUser.getUserEmail()+" Added StockOrder+"+stockOrderBean.getStockOrderId()+" successfully ",false);
					String path = LayOutPageConstants.PURCHASE_ORDER_RECV;
					if(stockOrder.getStockOrderType().getStockOrderTypeId() == 2){
						path = LayOutPageConstants.STOCK_RETURN_EDIT_PRODUCTS;
					}
					else if(stockOrder.getStockOrderType().getStockOrderTypeId() == 3){
						path = LayOutPageConstants.STOCK_TRANSFER_EDIT_PRODUCTS;
					}
					else if(stockOrder.getStockOrderType().getStockOrderTypeId() == 5){
						path = LayOutPageConstants.STOCK_SUPPLIER_TRANSFER_EDIT_PRODUCTS;
					}
					if(stockOrder.getStatus().getStatusId() == 8){
						path = LayOutPageConstants.STOCKCONTROL;
					}
					util.AuditTrail(request, currentUser, "PurchaseOrderController.addStockOrder", "User "+ 
							currentUser.getUserEmail()+" added StockOrder : "+stockOrderBean.getStockOrderId(),false);
					return new Response(stockOrder.getStockOrderId(),StatusConstants.SUCCESS,path);
				}else{
					util.AuditTrail(request, currentUser, "PurchaseOrderController.addStockOrder", "User "+ 
							currentUser.getUserEmail()+" Unable to add StockOrder : "+stockOrderBean.getStockOrderId(),false);
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

}

