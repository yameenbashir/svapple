package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Address;
import com.dowhile.Company;
import com.dowhile.Configuration;
import com.dowhile.Contact;
import com.dowhile.Country;
import com.dowhile.DeliveryMethod;
import com.dowhile.OrderDetail;
import com.dowhile.OrderMain;
import com.dowhile.Outlet;
import com.dowhile.PaymentType;
import com.dowhile.Status;
import com.dowhile.StockOrder;
import com.dowhile.StockOrderDetail;
import com.dowhile.StockOrderType;
import com.dowhile.User;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.OrderDetailBean;
import com.dowhile.frontend.mapping.bean.OrderMainBean;
import com.dowhile.frontend.mapping.bean.StockOrderBean;
import com.dowhile.service.AddressService;
import com.dowhile.service.CompanyService;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.ContactService;
import com.dowhile.service.CountryService;
import com.dowhile.service.DeliveryMethodService;
import com.dowhile.service.OrderDetailService;
import com.dowhile.service.OrderMainService;
import com.dowhile.service.OutletService;
import com.dowhile.service.PaymentTypeService;
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
@RequestMapping("/orders")
public class OrdersController {

	private static Logger logger = Logger.getLogger(OrdersController.class.getName());
	@Resource
	private StockOrderService stockOrderService;

	@Resource
	private OutletService outletService;

	@Resource
	private StatusService statusService;

	@Resource
	private AddressService addressService;

	@Resource
	private StockOrderTypeService stockOrderTypeService;

	@Resource
	private CountryService countryService;

	@Resource
	private PaymentTypeService paymentTypeService;

	@Resource
	private DeliveryMethodService deliveryMethodService;
	@Resource
	private ContactService supplierService;
	@Resource
	private OrderMainService orderMainService;
	@Resource
	private OrderDetailService orderDetailService;
	@Resource
	private CompanyService companyService;
	@Resource
	private ServiceUtil util;
	@Resource
	private StockOrderDetailService stockOrderDetailService;
	@Resource
	private ConfigurationService configurationService;


	@RequestMapping("/layout")
	public String getOrdersControllerPartialPage(ModelMap modelMap) {
		return "orders/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllOrders/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody 
	Response getAllOrders(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {
		List<OrderMainBean> orderMainBeansList = new ArrayList<>();
		List<OrderMain> orderMainList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			Map<Integer, Outlet> outletsMap = new HashMap<>();
			List<Outlet> outlets = outletService.getOutlets(currentUser.getCompany().getCompanyId());
			if(outlets!=null){
				for(Outlet outlet:outlets){
					outletsMap.put(outlet.getOutletId(), outlet);
				}
			}
			Map<Integer, PaymentType> paymentTypesMap = new HashMap<>();
			List<PaymentType> paymentTypes = paymentTypeService.getAllPaymentTypes(currentUser.getCompany().getCompanyId());
			if(paymentTypes!=null){
				for(PaymentType paymentType:paymentTypes){
					paymentTypesMap.put(paymentType.getPaymentTypeId(), paymentType);
				}
			}
			Map<Integer, Status> statusMap = new HashMap<>();
			List<Status> statuses = statusService.getAllStatus();
			if(statusMap!=null){
				for(Status status:statuses){
					statusMap.put(status.getStatusId(), status);
				}
			}
			/*Map<Integer, Contact> suppliersMap = new HashMap<>();
			List<Contact> suppliers = supplierService.getAllContacts(currentUser.getCompany().getCompanyId());
			if(suppliersMap!=null){
				for(Contact contact:suppliers){
					if(contact.getContactType()!=null && contact.getContactType().contains("SUPPLIER")){
						suppliersMap.put(contact.getContactId(), contact);
					}
				}
			}*/
			Map<Integer, DeliveryMethod> deliveryMethodMap = new HashMap<>();
			List<DeliveryMethod> deliveryMethods = deliveryMethodService.getAllDeliveryMethods(currentUser.getCompany().getCompanyId());
			if(deliveryMethodMap!=null){
				for(DeliveryMethod deliveryMethod:deliveryMethods){
					deliveryMethodMap.put(deliveryMethod.getDeliveryMethodId(), deliveryMethod);
				}
			}
			Map<Integer, Address> addressMap = new HashMap<>();
			List<Address> addresses = addressService.getAllAddress(currentUser.getCompany().getCompanyId());
			if(addressMap!=null){
				for(Address address:addresses){					
					addressMap.put(address.getAddressId(), address);
				}
			}
			Map<Integer, Company> companyMap = new HashMap<>();
			List<Company> companys = companyService.getCompanies();
			if(companyMap!=null){
				for(Company company:companys){					
					companyMap.put(company.getCompanyId(), company);
				}
			}
			//Order Detail Map Region
			List<OrderDetail> orderDetails = new ArrayList<>();
			Map<Integer, List<OrderDetail>> orderDetailsMap = new HashMap<>();
			orderDetails = orderDetailService.getAllOrderDetails(currentUser.getCompany().getCompanyId());
			if(orderDetails!=null){
				for(OrderDetail orderDetail:orderDetails){
					List<OrderDetail> addedOrderDetails = orderDetailsMap.get(orderDetail.getOrderMain().getOrderMainId());
					if(addedOrderDetails!=null){
						addedOrderDetails.add(orderDetail);
						orderDetailsMap.put(orderDetail.getOrderMain().getOrderMainId(), addedOrderDetails);
					}else{
						addedOrderDetails = new ArrayList<>();
						addedOrderDetails.add(orderDetail);
						orderDetailsMap.put(orderDetail.getOrderMain().getOrderMainId(), addedOrderDetails);
					}

				}
			}
			//End Region
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			try {
				orderMainList = orderMainService.getAllOrderMains(currentUser.getCompany().getCompanyId());
				if (orderMainList != null) {
					for (OrderMain orderMain : orderMainList) {
						OrderMainBean orderMainBean = new OrderMainBean();	
						if(orderMain.getOrderMainId() != null){
							orderMainBean.setOrderMainId(String.valueOf(orderMain.getOrderMainId()));
						}
						if(orderMain.getOrderRefNbr() != null && orderMain.getOrderRefNbr() != ""){
							orderMainBean.setOrderRefNbr(orderMain.getOrderRefNbr());
						}
						if(orderMain.getOrderTrackingNbr() != null && orderMain.getOrderTrackingNbr() != ""){
							orderMainBean.setOrderTrackingNbr(orderMain.getOrderTrackingNbr());
						}
						if(orderMain.getOrderNotes() != null && orderMain.getOrderNotes() != ""){
							orderMainBean.setOrderNotes(orderMain.getOrderNotes());
						}
						if(orderMain.getOrderGenerationDte() != null){
							orderMainBean.setOrderGenerationDte(simpleDateFormat.format(orderMain.getOrderGenerationDte()));
						}
						if(orderMain.getOrderCancelDte() != null){
							orderMainBean.setOrderCancelDte(simpleDateFormat.format(orderMain.getOrderCancelDte()));
						}
						if(orderMain.getOrderExpectedDeliveryDte() != null){
							orderMainBean.setOrderExpectedDeliveryDte(simpleDateFormat.format(orderMain.getOrderExpectedDeliveryDte()));
						}
						if(orderMain.getOrderDeliveryDte() != null){
							orderMainBean.setOrderDeliveryDte(simpleDateFormat.format(orderMain.getOrderDeliveryDte()));
						}
						if(orderMain.getOrderBillAmt() != null){
							orderMainBean.setOrderBillAmt(String.valueOf(orderMain.getOrderBillAmt()));
						}
						else{
							orderMainBean.setOrderBillAmt(String.valueOf(0));
						}
						if(orderMain.getTaxAmt() != null){
							orderMainBean.setTaxAmt(String.valueOf(orderMain.getTaxAmt()));
						}
						else{
							orderMainBean.setTaxAmt(String.valueOf(0));
						}
						if(orderMain.getStatus() != null){
							orderMainBean.setStatusId(String.valueOf(orderMain.getStatus().getStatusId()));
							orderMainBean.setStatus(statusMap.get(orderMain.getStatus().getStatusId()).getStatusDesc());
						}
						/*if(orderMain.getContact() != null){
							orderMainBean.setContactId(String.valueOf(orderMain.getContact().getContactId()));
							orderMainBean.setContactName(suppliersMap.get(orderMain.getContact().getContactId()).getContactName());
						}*/
						if(orderMain.getCompany() != null){
							orderMainBean.setCompanyId(String.valueOf(orderMain.getCompany().getCompanyId()));
							orderMainBean.setCompanyName(companyMap.get(orderMain.getCompany().getCompanyId()).getCompanyName());
						}
						if(orderMain.getOutlet() != null){
							orderMainBean.setOutletId(String.valueOf(orderMain.getOutlet().getOutletId()));
							orderMainBean.setOutletName(outletsMap.get(orderMain.getOutlet().getOutletId()).getOutletName());
						}
						if(orderMain.getCreatedDate() != null){
							orderMainBean.setCreatedDate(simpleDateFormat.format(orderMain.getCreatedDate()).toString());
						}
						if(orderMain.getLastUpdated() != null){
							orderMainBean.setLastUpdated(simpleDateFormat.format(orderMain.getLastUpdated()));
						}
						if(orderMain.getCreatedBy() != null){
							orderMainBean.setCreatedBy(String.valueOf(orderMain.getCreatedBy()));
						}
						if(orderMain.getUpdatedBy() != null){
							orderMainBean.setUpdatedBy(String.valueOf(orderMain.getUpdatedBy()));
						}
						if(orderMain.getPaymentType() != null){
							orderMainBean.setPaymentTypeId(String.valueOf(orderMain.getPaymentType().getPaymentTypeId()));
							orderMainBean.setPaymentTypeName(paymentTypesMap.get(orderMain.getPaymentType().getPaymentTypeId()).getPaymentTypeValue());
						}
						if(orderMain.getDeliveryMethod() != null){
							orderMainBean.setDeliveryMethodId(String.valueOf(orderMain.getDeliveryMethod().getDeliveryMethodId()));
							orderMainBean.setDeliveryMethodName(deliveryMethodMap.get(orderMain.getDeliveryMethod().getDeliveryMethodId()).getDeliveryMethodName());
						}
						List<OrderDetail> orderDetailList = orderDetailsMap.get(orderMain.getOrderMainId());
						int itemCount = 0;
						Double totalCost = 0.0;
						//OrderDetailBean orderDetailBean = new OrderDetailBean();
						if(orderDetailList != null){
							for(OrderDetail orderDetail : orderDetailList){
								itemCount = itemCount + orderDetail.getProductQuantity();
								if(orderDetail.getItemUnitPrice() == null){
									orderDetail.setItemUnitPrice(BigDecimal.ZERO);
								}
								if(orderDetail.getItemTaxAmount() == null){
									orderDetail.setItemTaxAmount(BigDecimal.ZERO);
								}
								totalCost = totalCost + ((orderDetail.getItemUnitPrice().doubleValue() + orderDetail.getItemTaxAmount().doubleValue()) * orderDetail.getProductQuantity());									
								/*orderDetailBean.setOrderDetailId(String.valueOf(orderDetail.getOrderDetailId()));
								orderDetailBean.setOrderMainId(String.valueOf(orderDetail.getOrderMain().getOrderMainId()));
								orderDetailBean.setProductVariantId(String.valueOf(orderDetail.getProductVariant().getProductVariantId()));
								orderDetailBean.setProductId(String.valueOf(orderDetail.getProduct().getProductId()));*/
								
							}							
						}		
						orderMainBeansList.add(orderMainBean);
					}
					util.AuditTrail(request, currentUser, "OrderMainController.getAllOrders", 
							"User "+ currentUser.getUserEmail()+" fetched all orders successfully ",false);
					return new Response(orderMainBeansList,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "OrderMainController.getAllOrders", 
							"User "+ currentUser.getUserEmail()+" unbale to retrive orders ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "OrderMainController.getAllOrders",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

}

