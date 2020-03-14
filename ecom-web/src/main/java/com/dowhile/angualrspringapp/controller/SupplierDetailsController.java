package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Address;
import com.dowhile.Contact;
import com.dowhile.ContactPayments;
import com.dowhile.ContactPaymentsType;
import com.dowhile.Outlet;
import com.dowhile.Product;
import com.dowhile.Status;
import com.dowhile.StockOrder;
import com.dowhile.StockOrderDetail;
import com.dowhile.StockOrderType;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.controller.bean.SupplierDetailsControllerBean;
import com.dowhile.frontend.mapping.bean.AddressBean;
import com.dowhile.frontend.mapping.bean.StockOrderBean;
import com.dowhile.frontend.mapping.bean.SupplierBean;
import com.dowhile.frontend.mapping.bean.SupplierPaymentsBean;
import com.dowhile.service.AddressService;
import com.dowhile.service.ContactPaymentsService;
import com.dowhile.service.ContactPaymentsTypeService;
import com.dowhile.service.ContactService;
import com.dowhile.service.OutletService;
import com.dowhile.service.ResourceService;
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
@RequestMapping("/supplierDetails")
public class SupplierDetailsController {
	
	// private static Logger logger = Logger.getLogger(SupplierDetailsController.class.getName());
	@Resource
	private ContactService supplierService;
	@Resource
	private AddressService addressService;
	@Resource
	private StockOrderService stockOrderService;
	@Resource
	private OutletService outletService;
	@Resource
	private StatusService statusService;
	@Resource
	private StockOrderTypeService stockOrderTypeService;
	@Resource
	private StockOrderDetailService stockOrderDetailService;	
	@Resource
	private ContactPaymentsService supplierPaymentsService;
	@Resource
	private ResourceService resourceService;
	@Resource
	private ServiceUtil util;
	@Resource
	private ContactPaymentsTypeService contactPaymentsTypeService;
	@RequestMapping("/layout")
	public String getNewSupplierControllerPartialPage(ModelMap modelMap) {
		return "supplierDetails/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getSupplierDetailsControllerData/{sessionId}/{supplierId}", method = RequestMethod.POST)
	public @ResponseBody Response getSupplierDetailsControllerData(@PathVariable("sessionId") String sessionId,
			@PathVariable("supplierId") String supplierId,HttpServletRequest request) {

		SupplierBean supplierBean = null;
		List<StockOrderBean> stockOrderBeansList = null;
		List<SupplierPaymentsBean> supplierPaymentsBeanList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				Response response = getSupplier(sessionId, supplierId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					supplierBean = (SupplierBean) response.data;
				}
				response = getAllStockOrdersBySupplierId(sessionId, supplierId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					stockOrderBeansList = (List<StockOrderBean>) response.data;
				}
				response = getSupplierPayments(sessionId, supplierId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					supplierPaymentsBeanList = (List<SupplierPaymentsBean>) response.data;
				}
				SupplierDetailsControllerBean supplierDetailsControllerBean = new SupplierDetailsControllerBean();
				supplierDetailsControllerBean.setStockOrderBeansList(stockOrderBeansList);
				supplierDetailsControllerBean.setSupplierBean(supplierBean);
				supplierDetailsControllerBean.setSupplierPaymentsBeanList(supplierPaymentsBeanList);

				util.AuditTrail(request, currentUser, "SupplierDetailsController.getSupplierDetailsControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived SupplierDetailsControllerData successfully ",false);
				return new Response(supplierDetailsControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SupplierDetailsController.getSupplierDetailsControllerData",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getOutletDetailsControllerData/{sessionId}/{outletId}", method = RequestMethod.POST)
	public @ResponseBody Response getOutletDetailsControllerData(@PathVariable("sessionId") String sessionId,
			@PathVariable("outletId") String outletId,HttpServletRequest request) {

		SupplierBean supplierBean = null;
		List<StockOrderBean> stockOrderBeansList = null;
		List<SupplierPaymentsBean> supplierPaymentsBeanList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				Response response = getSupplierbyOutletId(sessionId, outletId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					supplierBean = (SupplierBean) response.data;
				}
				response = getAllStockOrdersByOutletId(sessionId, outletId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					stockOrderBeansList = (List<StockOrderBean>) response.data;
				}
				if(supplierBean != null){
					response = getSupplierPayments(sessionId, supplierBean.getSupplierId(), request);
					if(response.status.equals(StatusConstants.SUCCESS)){
						supplierPaymentsBeanList = (List<SupplierPaymentsBean>) response.data;
					}
				}
				SupplierDetailsControllerBean supplierDetailsControllerBean = new SupplierDetailsControllerBean();
				supplierDetailsControllerBean.setStockOrderBeansList(stockOrderBeansList);
				supplierDetailsControllerBean.setSupplierBean(supplierBean);
				supplierDetailsControllerBean.setSupplierPaymentsBeanList(supplierPaymentsBeanList);

				util.AuditTrail(request, currentUser, "SupplierDetailsController.getSupplierDetailsControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived SupplierDetailsControllerData successfully ",false);
				return new Response(supplierDetailsControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SupplierDetailsController.getSupplierDetailsControllerData",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getSupplierbyOutletId/{sessionId}/{supplierId}", method = RequestMethod.POST)
	public @ResponseBody Response getSupplierbyOutletId(@PathVariable("sessionId") String sessionId,
			@PathVariable("supplierId") String supplierId, HttpServletRequest request) {

		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			Outlet outlet = null;
			try {

				Contact supplier = supplierService.getContactByContactOutletID(Integer.parseInt(supplierId), currentUser.getCompany().getCompanyId());
				SupplierBean supplierBean = new SupplierBean();

				if (supplier != null ) {				
					supplierBean.setSupplierName(supplier.getContactName());
					supplierBean.setSupplierId(supplier.getContactId().toString());
					supplierBean.setDescription(supplier.getDescription());
					if(supplier.getContactOutletId() != null){
						supplierBean.setOutletId(supplier.getContactOutletId().toString());
						outlet = outletService.getOuletByOutletId(supplier.getContactOutletId(), currentUser.getCompany().getCompanyId());	
					}
					if(supplier.getContactBalance() == null){
						supplierBean.setSupplierBalance("0.00");
					}
					else{
						NumberFormat formatter = new DecimalFormat("###.##");  
						String total = formatter.format(supplier.getContactBalance().doubleValue());
						supplierBean.setSupplierBalance(total);

					}
					if (supplier.getDefaultMarkup() != null) {
						supplierBean.setDefaultMarkup(supplier.getDefaultMarkup()
								.toString());
					} else {
						supplierBean.setDefaultMarkup("0");

					}
					supplierBean.setActiveIndicator("true");
					supplierBean.setCompanyName(supplier.getCompanyName());
					if(outlet.getAddress() != null){						
						Address supplerAddress = addressService
								.getAddressByAddressId(outlet.getAddress().getAddressId(),currentUser.getCompany().getCompanyId());						
						if (supplerAddress != null) {
							List<AddressBean> supplierAddressBeans = new ArrayList<>();
							AddressBean addressBean = new AddressBean();
							addressBean.setAddressId(supplerAddress.getAddressId()
									.toString());
							addressBean.setAddressType(supplerAddress
									.getAddressType());
							addressBean.setCity(supplerAddress.getCity());
							addressBean.setContactName(supplerAddress
									.getContactName());
							addressBean.setCounty(supplerAddress.getCounty());
							addressBean.setEmail(supplerAddress.getEmail());
							addressBean.setFax(supplerAddress.getFax());
							addressBean
							.setFirstName(supplerAddress.getFirstName());
							addressBean.setLastName(supplerAddress.getLastName());
							addressBean.setPhone(supplerAddress.getPhone());
							addressBean.setPostalCode(supplerAddress
									.getPostalCode());
							addressBean.setState(supplerAddress.getState());
							addressBean.setStreet(supplerAddress.getStreet());
							addressBean.setSuburb(supplerAddress.getSuburb());
							addressBean.setSupplierId(supplier.getContactId()
									.toString());
							addressBean.setTwitter(supplerAddress.getTwitter());
							addressBean.setWebsite(supplerAddress.getWebsite());
							supplierAddressBeans.add(addressBean);
							supplierBean.setAddresses(supplierAddressBeans);
						}
					}
					List<Product> list = supplierService
							.getAllProductsByContactID(supplier
									.getContactId(),currentUser.getCompany().getCompanyId());

					if (list != null && list.size() > 0) {
						int productCount = list.size();
						supplierBean.setProductCount(String.valueOf(productCount));
					}

					util.AuditTrail(request, currentUser, "SupplierDetailsController.getSupplier", 
							"User "+ currentUser.getUserEmail()+" retrived supplier successfully ",false);
					return new Response(supplierBean, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "SupplierDetailsController.getSupplier", 
							"User "+ currentUser.getUserEmail()+" unable to retrive supplier ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SupplierDetailsController.getSupplier",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);

			}

		} else {
			return new Response(MessageConstants.INVALID_SESSION,
					StatusConstants.INVALID, LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllStockOrdersByOutletId/{sessionId}/{outletId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllStockOrdersByOutletId(@PathVariable("sessionId") String sessionId,
			@PathVariable("outletId") String outletId, HttpServletRequest request) {

		List<StockOrderBean> stockOrderBeansList = new ArrayList<>();
		List<StockOrder> stockOrderList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			Contact supplierOutlet = supplierService.getContactByContactOutletID(Integer.parseInt(outletId), currentUser.getCompany().getCompanyId());
			//int outletId = supplierOutlet.getContactOutletId();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			try {
				stockOrderList = stockOrderService.getStockOrderByOutletId(Integer.parseInt(outletId), currentUser.getCompany().getCompanyId());
				if (stockOrderList != null) {
					for (StockOrder stockOrder : stockOrderList) {
						StockOrderBean stockOrderBean = new StockOrderBean();					
						stockOrderBean.setAutofillReorder(Boolean.toString(stockOrder.isAutofillReorder()));
						if(stockOrder.getCreatedDate() != null){
							stockOrderBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(stockOrder.getCreatedDate()).toString());
						}
						if(stockOrder.getCreatedBy() != null){
							stockOrderBean.setCreatedBy(String.valueOf(stockOrder.getCreatedBy()));
						}
						if(stockOrder.getDiliveryDueDate() != null){
							stockOrderBean.setDiliveryDueDate(simpleDateFormat.format(stockOrder.getDiliveryDueDate()).toString());
						}
						stockOrderBean.setOrderNo(stockOrder.getOrderNo());
						if(stockOrder.getOrdrRecvDate() != null){
							stockOrderBean.setOrdrRecvDate(simpleDateFormat.format(stockOrder.getOrdrRecvDate()).toString());
						}
						Outlet outlet = outletService.getOuletByOutletId(stockOrder.getOutletByOutletAssocicationId().getOutletId(),currentUser.getCompany().getCompanyId());
						if(outlet != null){
							stockOrderBean.setOutlet(outlet.getOutletId().toString());
							stockOrderBean.setOutletName(outlet.getOutletName());
						}
						if(stockOrder.getOutletBySourceOutletAssocicationId() != null)
						{
							Outlet outletSource = outletService.getOuletByOutletId(stockOrder.getOutletBySourceOutletAssocicationId().getOutletId(),currentUser.getCompany().getCompanyId());
							if(outletSource != null){
								stockOrderBean.setSourceOutletId(outletSource.getOutletId().toString());
								stockOrderBean.setSourceOutletName(outletSource.getOutletName());
							}
						}
						stockOrderBean.setRemarks(stockOrder.getRemarks());
						stockOrderBean.setReturnNo(stockOrder.getReturnNo());
						Status status = statusService.getStatusByStatusId(stockOrder.getStatus().getStatusId());
						if(status != null){
							stockOrderBean.setStatusId(status.getStatusId().toString());
							stockOrderBean.setStatus(status.getStatusDesc());
						}
						if(stockOrder.getStockOrderDate() != null){
							stockOrderBean.setStockOrderDate(simpleDateFormat.format(stockOrder.getStockOrderDate()).toString());
						}
						StockOrderType stockOrderType = stockOrderTypeService.getStockOrderTypeByStockOrderTypeId(stockOrder.getStockOrderType().getStockOrderTypeId());
						if(stockOrderType != null){
							stockOrderBean.setStockOrderTypeId(stockOrderType.getStockOrderTypeId().toString());
							stockOrderBean.setStockOrderTypeDesc(stockOrderType.getStockOrderTypeDesc().toString());
						}					
						stockOrderBean.setStockRefNo(stockOrder.getStockRefNo());
						if(stockOrderBean.getSourceOutletId() == null){
							Contact supplier = supplierService.getContactByID(stockOrder.getContactId(),currentUser.getCompany().getCompanyId());
							if(supplier != null){
								stockOrderBean.setSupplierId(supplier.getContactId().toString());
								stockOrderBean.setSupplierName(supplier.getContactName());
							}
						}
						else{
							stockOrderBean.setSupplierName(stockOrderBean.getSourceOutletName());
						}
						stockOrderBean.setSupplierInvoiceNo(stockOrder.getContactInvoiceNo());
						int stockOrderId = stockOrder.getStockOrderId();
						List<StockOrderDetail> stockOrderDetailList = stockOrderDetailService.getStockOrderDetailByStockOrderId(stockOrderId,currentUser.getCompany().getCompanyId());
						int itemCount = 0;
						Double totalCost = 0.00;
						if(stockOrderDetailList != null){
							for(StockOrderDetail stockOrderDetail : stockOrderDetailList){
								if(stockOrderDetail.getRecvProdQty()!= null){
									itemCount = itemCount + stockOrderDetail.getRecvProdQty();
								}
								else{
									itemCount = itemCount + stockOrderDetail.getOrderProdQty();
								}
								if(stockOrder.getStockOrderType().getStockOrderTypeId() == 1){ //Supplier Order
									if(stockOrderDetail.getRecvSupplyPrice() != null && stockOrderDetail.getRecvProdQty()!= null){
										totalCost = totalCost + (stockOrderDetail.getRecvSupplyPrice().doubleValue() * stockOrderDetail.getRecvProdQty());
									}
									else{
										totalCost = totalCost + (stockOrderDetail.getOrdrSupplyPrice().doubleValue() * stockOrderDetail.getOrderProdQty());
									}
								}
								else
								{
									if(stockOrder.isRetailPriceBill()){
										totalCost = totalCost + (stockOrderDetail.getRetailPrice().doubleValue() * stockOrderDetail.getOrderProdQty());
									}
									else{
										totalCost = totalCost + (stockOrderDetail.getOrdrSupplyPrice().doubleValue() * stockOrderDetail.getOrderProdQty());
									}
								}
							}
						}
						stockOrderBean.setStockOrderId(Integer.toString(stockOrderId));
						stockOrderBean.setItemCount(Integer.toString(itemCount));
						NumberFormat formatter = new DecimalFormat("###.##");  
						String total = formatter.format(totalCost);
						stockOrderBean.setTotalCost(total);
						stockOrderBeansList.add(stockOrderBean);
					}

					util.AuditTrail(request, currentUser, "StockControlController.getAllStockOrders", 
							"User "+ currentUser.getUserEmail()+" fetched all stock orders successfully ",false);
					return new Response(stockOrderBeansList,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "StockControlController.getAllStockOrders", 
							"User "+ currentUser.getUserEmail()+" unbale to retrived stock orders ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "StockControlController.getAllStockOrders",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getSupplier/{sessionId}/{supplierId}", method = RequestMethod.POST)
	public @ResponseBody Response getSupplier(@PathVariable("sessionId") String sessionId,
			@PathVariable("supplierId") String supplierId, HttpServletRequest request) {

		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {

				Contact supplier = supplierService.getContactByID(Integer.parseInt(supplierId), currentUser.getCompany().getCompanyId());
				SupplierBean supplierBean = new SupplierBean();

				if (supplier != null ) {				
					supplierBean.setSupplierName(supplier.getContactName());
					supplierBean.setSupplierId(supplier.getContactId().toString());
					supplierBean.setDescription(supplier.getDescription());
					if(supplier.getContactBalance() == null){
						supplierBean.setSupplierBalance("0.00");
					}
					else{
						NumberFormat formatter = new DecimalFormat("###.##");  
						String total = formatter.format(supplier.getContactBalance().doubleValue());
						supplierBean.setSupplierBalance(total);

					}
					if (supplier.getDefaultMarkup() != null) {
						supplierBean.setDefaultMarkup(supplier.getDefaultMarkup()
								.toString());
					} else {
						supplierBean.setDefaultMarkup("0");

					}
					supplierBean.setActiveIndicator("true");
					supplierBean.setCompanyName(supplier.getCompanyName());

					List<Address> supplerAddress = addressService
							.getAddressBySupplierId(supplier.getContactId(),currentUser.getCompany().getCompanyId());
					List<AddressBean> supplierAddressBeans = new ArrayList<>();
					if (supplerAddress != null) {
						for (Address address : supplerAddress) {

							AddressBean addressBean = new AddressBean();

							addressBean.setAddressId(address.getAddressId()
									.toString());
							addressBean.setAddressType(address
									.getAddressType());
							addressBean.setCity(address.getCity());
							addressBean.setContactName(address
									.getContactName());
							addressBean.setCounty(address.getCounty());
							addressBean.setEmail(address.getEmail());
							addressBean.setFax(address.getFax());
							addressBean
							.setFirstName(address.getFirstName());
							addressBean.setLastName(address.getLastName());
							addressBean.setPhone(address.getPhone());
							addressBean.setPostalCode(address
									.getPostalCode());
							addressBean.setState(address.getState());
							addressBean.setStreet(address.getStreet());
							addressBean.setSuburb(address.getSuburb());
							addressBean.setSupplierId(supplier.getContactId()
									.toString());
							addressBean.setTwitter(address.getTwitter());
							addressBean.setWebsite(address.getWebsite());

							supplierAddressBeans.add(addressBean);

						}

						supplierBean.setAddresses(supplierAddressBeans);
					}
					List<Product> list = supplierService
							.getAllProductsByContactID(supplier
									.getContactId(),currentUser.getCompany().getCompanyId());

					if (list != null && list.size() > 0) {
						int productCount = list.size();
						supplierBean.setProductCount(String.valueOf(productCount));
					}

					util.AuditTrail(request, currentUser, "SupplierDetailsController.getSupplier", 
							"User "+ currentUser.getUserEmail()+" retrived supplier successfully ",false);
					return new Response(supplierBean, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "SupplierDetailsController.getSupplier", 
							"User "+ currentUser.getUserEmail()+" unable to retrive supplier ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SupplierDetailsController.getSupplier",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);

			}

		} else {
			return new Response(MessageConstants.INVALID_SESSION,
					StatusConstants.INVALID, LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllStockOrdersBySupplierId/{sessionId}/{supplierId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllStockOrdersBySupplierId(@PathVariable("sessionId") String sessionId,
			@PathVariable("supplierId") String supplierId, HttpServletRequest request) {

		List<StockOrderBean> stockOrderBeansList = new ArrayList<>();
		List<StockOrder> stockOrderList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			try {
				stockOrderList = stockOrderService.getStockOrderBySupplierId(Integer.parseInt(supplierId), currentUser.getCompany().getCompanyId());
				if (stockOrderList != null) {
					for (StockOrder stockOrder : stockOrderList) {
						StockOrderBean stockOrderBean = new StockOrderBean();					
						stockOrderBean.setAutofillReorder(Boolean.toString(stockOrder.isAutofillReorder()));
						if(stockOrder.getCreatedDate() != null){
							stockOrderBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(stockOrder.getCreatedDate()).toString());
						}
						if(stockOrder.getCreatedBy() != null){
							stockOrderBean.setCreatedBy(String.valueOf(stockOrder.getCreatedBy()));
						}
						if(stockOrder.getDiliveryDueDate() != null){
							stockOrderBean.setDiliveryDueDate(simpleDateFormat.format(stockOrder.getDiliveryDueDate()).toString());
						}
						stockOrderBean.setOrderNo(stockOrder.getOrderNo());
						if(stockOrder.getOrdrRecvDate() != null){
							stockOrderBean.setOrdrRecvDate(simpleDateFormat.format(stockOrder.getOrdrRecvDate()).toString());
						}
						Outlet outlet = outletService.getOuletByOutletId(stockOrder.getOutletByOutletAssocicationId().getOutletId(),currentUser.getCompany().getCompanyId());
						if(outlet != null){
							stockOrderBean.setOutlet(outlet.getOutletId().toString());
							stockOrderBean.setOutletName(outlet.getOutletName());
						}
						if(stockOrder.getOutletBySourceOutletAssocicationId() != null)
						{
							Outlet outletSource = outletService.getOuletByOutletId(stockOrder.getOutletBySourceOutletAssocicationId().getOutletId(),currentUser.getCompany().getCompanyId());
							if(outletSource != null){
								stockOrderBean.setSourceOutletId(outletSource.getOutletId().toString());
								stockOrderBean.setSourceOutletName(outletSource.getOutletName());
							}
						}
						stockOrderBean.setRemarks(stockOrder.getRemarks());
						stockOrderBean.setReturnNo(stockOrder.getReturnNo());
						Status status = statusService.getStatusByStatusId(stockOrder.getStatus().getStatusId());
						if(status != null){
							stockOrderBean.setStatusId(status.getStatusId().toString());
							stockOrderBean.setStatus(status.getStatusDesc());
						}
						if(stockOrder.getStockOrderDate() != null){
							stockOrderBean.setStockOrderDate(simpleDateFormat.format(stockOrder.getStockOrderDate()).toString());
						}
						StockOrderType stockOrderType = stockOrderTypeService.getStockOrderTypeByStockOrderTypeId(stockOrder.getStockOrderType().getStockOrderTypeId());
						if(stockOrderType != null){
							stockOrderBean.setStockOrderTypeId(stockOrderType.getStockOrderTypeId().toString());
							stockOrderBean.setStockOrderTypeDesc(stockOrderType.getStockOrderTypeDesc().toString());
						}					
						stockOrderBean.setStockRefNo(stockOrder.getStockRefNo());
						if(stockOrderBean.getSourceOutletId() == null){
							Contact supplier = supplierService.getContactByID(stockOrder.getContactId(),currentUser.getCompany().getCompanyId());
							if(supplier != null){
								stockOrderBean.setSupplierId(supplier.getContactId().toString());
								stockOrderBean.setSupplierName(supplier.getContactName());
							}
						}
						else{
							stockOrderBean.setSupplierName(stockOrderBean.getSourceOutletName());
						}
						stockOrderBean.setSupplierInvoiceNo(stockOrder.getContactInvoiceNo());
						int stockOrderId = stockOrder.getStockOrderId();
						List<StockOrderDetail> stockOrderDetailList = stockOrderDetailService.getStockOrderDetailByStockOrderId(stockOrderId,currentUser.getCompany().getCompanyId());
						int itemCount = 0;
						Double totalCost = 0.0;
						if(stockOrderDetailList != null){
							for(StockOrderDetail stockOrderDetail : stockOrderDetailList){
								if(stockOrderDetail.getRecvProdQty()!= null){
									itemCount = itemCount + stockOrderDetail.getRecvProdQty();
								}
								else{
									itemCount = itemCount + stockOrderDetail.getOrderProdQty();
								}
								if(stockOrder.getStockOrderType().getStockOrderTypeId() == 1){ //Supplier Order
									if(stockOrderDetail.getRecvSupplyPrice() != null && stockOrderDetail.getRecvProdQty()!= null){
										totalCost = totalCost + (stockOrderDetail.getRecvSupplyPrice().doubleValue() * stockOrderDetail.getRecvProdQty());
									}
									else{
										totalCost = totalCost + (stockOrderDetail.getOrdrSupplyPrice().doubleValue() * stockOrderDetail.getOrderProdQty());
									}
								}
								else
								{
									if(stockOrder.isRetailPriceBill()){
										totalCost = totalCost + (stockOrderDetail.getRetailPrice().doubleValue() * stockOrderDetail.getOrderProdQty());
									}
									else{
										totalCost = totalCost + (stockOrderDetail.getOrdrSupplyPrice().doubleValue() * stockOrderDetail.getOrderProdQty());
									}
								}
							}
						}
						stockOrderBean.setStockOrderId(Integer.toString(stockOrderId));
						stockOrderBean.setItemCount(Integer.toString(itemCount));
						NumberFormat formatter = new DecimalFormat("###.##");  
						String total = formatter.format(totalCost);
						stockOrderBean.setTotalCost(total);
						stockOrderBeansList.add(stockOrderBean);
					}

					util.AuditTrail(request, currentUser, "StockControlController.getAllStockOrders", 
							"User "+ currentUser.getUserEmail()+" fetched all stock orders successfully ",false);
					return new Response(stockOrderBeansList,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "StockControlController.getAllStockOrders", 
							"User "+ currentUser.getUserEmail()+" unbale to retrived stock orders ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "StockControlController.getAllStockOrders",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getSupplierPayments/{sessionId}/{supplierId}", method = RequestMethod.POST)
	public @ResponseBody Response getSupplierPayments(@PathVariable("sessionId") String sessionId,
			@PathVariable("supplierId") String supplierId, HttpServletRequest request) {

		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			NumberFormat formatter = new DecimalFormat("###.##"); 
			try {
				List<ContactPayments> supplierPaymentsList = supplierPaymentsService.getSupplierPaymentsByContactId(Integer.parseInt(supplierId), currentUser.getCompany().getCompanyId());
				List<SupplierPaymentsBean> supplierPaymentsBeanList = new ArrayList();
				if (supplierPaymentsList != null) {
					for (ContactPayments supplierPayment : supplierPaymentsList) {
						SupplierPaymentsBean supplierPaymentBean = new SupplierPaymentsBean();					
						supplierPaymentBean.setAdjustmentType(supplierPayment.getAdjustmentType());
						//supplierPaymentBean.setCreatedBy(createdBy);
						if(supplierPayment.getCreatedDate() != null){
							supplierPaymentBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(supplierPayment.getCreatedDate()).toString());
						}
						supplierPaymentBean.setDescription(supplierPayment.getDescription());
						if(supplierPayment.getOrderRefNum()!=null){
							supplierPaymentBean.setOrderRefNum(stockOrderService.getStockOrderByStockOrderID(Integer.parseInt(supplierPayment.getOrderRefNum()), currentUser.getCompany().getCompanyId()).getStockRefNo());
						}else{
							supplierPaymentBean.setOrderRefNum("-");
						}
						if(supplierPayment.getPaymentAmount() != null){
							String total = formatter.format(supplierPayment.getPaymentAmount().doubleValue());
							supplierPaymentBean.setPaymentAmount(total);
						}
						if(supplierPayment.getPaymentCash() != null){
							String total = formatter.format(supplierPayment.getPaymentCash().doubleValue());
							supplierPaymentBean.setPaymentCash(total);
						}
						if(supplierPayment.getPaymentCreditCard() != null){
							String total = formatter.format(supplierPayment.getPaymentCreditCard().doubleValue());
							supplierPaymentBean.setPaymentCreditCard(total);
						}
						if(supplierPayment.getPaymentOtherType() != null){
							String total = formatter.format(supplierPayment.getPaymentOtherType().doubleValue());
							supplierPaymentBean.setPaymentOtherType(total);
						}

						if(supplierPayment.getPaymentRefNum() != null){
							supplierPaymentBean.setPaymentRefNum(supplierPayment.getPaymentRefNum());
						}
						if(supplierPayment.getContact() != null){
							supplierPaymentBean.setSupplierName(supplierService.getContactByID(supplierPayment.getContact().getContactId(), currentUser.getCompany().getCompanyId()).getContactName());
						}
						if(supplierPayment.getContactBalance() != null){
							String total = formatter.format(supplierPayment.getContactBalance().doubleValue());
							supplierPaymentBean.setSupplierBalance(total);
						}
						if(supplierPayment.getContactNewBalance() != null){ 
							String total = formatter.format(supplierPayment.getContactNewBalance().doubleValue());
							supplierPaymentBean.setSupplierNewBalance(total);
						}
						Double totalPaid = Double.parseDouble(supplierPaymentBean.getSupplierBalance()) - Double.parseDouble(supplierPaymentBean.getSupplierNewBalance());						 
						if(totalPaid<0){
							totalPaid = totalPaid * -1;
						}
						String strTotalPaid = formatter.format(totalPaid);
						supplierPaymentBean.setTotalPaid(strTotalPaid);

						if(supplierPayment.getContactPaymentId() != null){
							supplierPaymentBean.setSupplierPaymentId(String.valueOf(supplierPayment.getContactPaymentId()));
						}
						if(supplierPayment.getCreatedBy() != null){
							User user = resourceService.getUserById(supplierPayment.getCreatedBy(), currentUser.getCompany().getCompanyId());
							if(user.getFirstName() != null){
								supplierPaymentBean.setUserName(user.getFirstName());
							}
							if(user.getLastName() != null){
								supplierPaymentBean.setUserName(supplierPaymentBean.getUserName() + " " +  user.getLastName());
							}
						}
						supplierPaymentsBeanList.add(supplierPaymentBean);
					}

					util.AuditTrail(request, currentUser, "SupplierDetailsController.getSupplierPayments", 
							"User "+ currentUser.getUserEmail()+" retrived SupplierPayments successfully ",false);
					return new Response(supplierPaymentsBeanList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "SupplierDetailsController.getSupplierPayments", 
							"User "+ currentUser.getUserEmail()+" unable to retrive SupplierPayments ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SupplierDetailsController.getSupplierPayments",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);

			}

		} else {
			return new Response(MessageConstants.INVALID_SESSION,
					StatusConstants.INVALID, LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addSupplierPayment/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response addSupplierPayment(@PathVariable("sessionId") String sessionId,
			@RequestBody SupplierPaymentsBean supplierPaymentsBean, HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			Outlet outlet = outletService.getOuletByOutletId(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
			if(outlet.getIsHeadOffice()!=null && outlet.getIsHeadOffice().toString()=="true"){
				if(currentUser.getRole().getRoleId() == 1 || currentUser.getRole().getRoleId() == 2){ 
					try {			
						if (supplierPaymentsBean != null) {				
							ContactPayments supplierPayments = new ContactPayments();
							supplierPayments.setActiveIndicator(true);
							supplierPayments.setAdjustmentType(supplierPaymentsBean.getAdjustmentType());
							supplierPayments.setCompany(currentUser.getCompany());
							supplierPayments.setDescription(supplierPaymentsBean.getDescription());
							supplierPayments.setOrderRefNum(supplierPaymentsBean.getOrderRefNum());
							supplierPayments.setPaymentAmount(new BigDecimal(supplierPaymentsBean.getPaymentAmount()));
							supplierPayments.setPaymentCash(new BigDecimal(supplierPaymentsBean.getPaymentCash()));
							supplierPayments.setPaymentCreditCard(new BigDecimal(supplierPaymentsBean.getPaymentCreditCard()));
							supplierPayments.setPaymentOtherType(new BigDecimal(supplierPaymentsBean.getPaymentOtherType()));
							supplierPayments.setPaymentRefNum(supplierPaymentsBean.getPaymentRefNum());
							supplierPayments.setContactBalance(new BigDecimal(supplierPaymentsBean.getSupplierBalance()));
							supplierPayments.setContactNewBalance(new BigDecimal(supplierPaymentsBean.getSupplierNewBalance()));
							Contact supplier = supplierService.getContactByID(Integer.parseInt(supplierPaymentsBean.getSupplier()),currentUser.getCompany().getCompanyId());
							supplierPayments.setContact(supplier);
							supplierPayments.setContactName(supplier.getContactName());
							supplierPayments.setCreatedBy(currentUser.getUserId());
							supplierPayments.setCreatedDate(new Date());
							supplierPayments.setLastUpdated(new Date());
							supplierPayments.setUpdatedBy(currentUser.getUserId());
							ContactPaymentsType contactPaymentsType =  contactPaymentsTypeService.getContactPaymentsTypeByID(2, currentUser.getCompany().getCompanyId());
							supplierPayments.setContactPaymentsType(contactPaymentsType);
							supplierPaymentsService.addContactPayments(supplierPayments,currentUser.getCompany().getCompanyId());					
							util.AuditTrail(request, currentUser, "SupplierDetailsController.addSupplierPayments", 
									"User "+ currentUser.getUserEmail()+" Added SupplierPayments+"+supplierPaymentsBean.getSupplierPaymentId()+" successfully ",false);

							supplier.setContactBalance(new BigDecimal(supplierPaymentsBean.getSupplierNewBalance()));
							supplier.setLastUpdated(new Date());
							supplier.setUpdatedBy(currentUser.getUserId());
							supplierService.updateContact(supplier,currentUser.getCompany().getCompanyId());
							util.AuditTrail(request, currentUser, "SupplierDetailsController.addSupplierPayments", 
									"User "+ currentUser.getUserEmail()+" Updated Supplier+"+supplier.getContactId()+" successfully ",false);
							return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
						}else{
							util.AuditTrail(request, currentUser, "SupplierDetailsController.addSupplierPayments", "User "+ 
									currentUser.getUserEmail()+" Unable to add SupplierPayments : "+supplierPaymentsBean.getSupplierPaymentId(),false);
							return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
						}

					}catch(Exception e){
						e.printStackTrace();// logger.error(e.getMessage(),e);
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						util.AuditTrail(request, currentUser, "SupplierDetailsController.addSupplierPayments",
								"Error Occured " + errors.toString(),true);
						return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
					}
				}
				else{
					return new Response(MessageConstants.ACCESS_DENIED,StatusConstants.INVALID,LayOutPageConstants.LOGIN);	
				}
			}
			else{
				return new Response(MessageConstants.ACCESS_DENIED,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
			}
		}
		else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}		
	}
}