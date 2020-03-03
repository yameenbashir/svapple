package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.util.ArrayList;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Contact;

import com.dowhile.OrderDetail;
import com.dowhile.OrderMain;

import com.dowhile.Product;
import com.dowhile.ProductVariant;

import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;

import com.dowhile.frontend.mapping.bean.OrderDetailBean;
import com.dowhile.frontend.mapping.bean.OrderMainBean;

import com.dowhile.service.AddressService;
import com.dowhile.service.CashManagmentService;
import com.dowhile.service.CompanyService;
import com.dowhile.service.ContactPaymentsService;
import com.dowhile.service.ContactPaymentsTypeService;
import com.dowhile.service.ContactService;
import com.dowhile.service.DailyRegisterService;
import com.dowhile.service.EcomOrderService;
import com.dowhile.service.OutletService;
import com.dowhile.service.PaymentTypeService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductTypeService;
import com.dowhile.service.ProductVariantService;
import com.dowhile.service.ReceiptService;
import com.dowhile.service.RegisterService;
import com.dowhile.service.SaleService;
import com.dowhile.service.SalesTaxService;
import com.dowhile.service.StatusService;
import com.dowhile.service.VariantAttributeService;
import com.dowhile.service.VariantAttributeValuesService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * hassan
 */
@Controller
@RequestMapping("/ecomOrder")
public class EcomOrderController {

	private static Logger logger = Logger.getLogger(EcomOrderController.class.getName());
	@Resource
	private ServiceUtil util;

	@Resource
	private SaleService saleService;

	@Resource
	private CompanyService companyService;

	@Resource
	private PaymentTypeService paymentTypeService;

	@Resource
	private StatusService statusService;

	@Resource
	private ContactService customerService;

	@Resource
	private ProductService productService;

	@Resource
	private ProductTypeService productTypeService;

	@Resource
	private VariantAttributeService variantAttributeService;

	@Resource
	private VariantAttributeValuesService variantAttributeValuesService;

	@Resource
	private ProductVariantService productVariantService;

	@Resource
	private OutletService outletService;

	@Resource
	private ReceiptService receiptService;

	@Resource
	private SalesTaxService salesTaxService;

	@Resource
	private DailyRegisterService dailyRegisterService;

	@Resource
	private RegisterService registerService;

	@Resource
	private ContactPaymentsService customerPaymentsService;

	@Resource
	private AddressService addressService;

	@Resource
	private EcomOrderService ecomOrderService;

	@Resource
	private CashManagmentService cashManagmentService;
	@Resource
	private ContactPaymentsTypeService contactPaymentsTypeService;

	@Resource
	private ContactService contactService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/checkout/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response checkout(@PathVariable("sessionId") String sessionId,
			@RequestBody OrderMainBean orderMainBean,
			HttpServletRequest request) {

		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {

				if(orderMainBean != null)
				{



					util.AuditTrail(request, currentUser, "EcomOrderController.checkout",
							"User " + currentUser.getUserEmail() + " Pay Cash "
									+ orderMainBean.getOrderRefNbr()
									+ " successfully ", false);

					return new Response(orderMainBean, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				}

				else
				{
					util.AuditTrail(request, currentUser, "EcomOrderController.checkout", 
							"Please contact Admin "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);

				}

			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "EcomOrderController.checkout","Error Occured " + errors.toString(), true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		} else {
			return new Response(MessageConstants.INVALID_SESSION,
					StatusConstants.INVALID, LayOutPageConstants.LOGIN);
		}
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/updatorder/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response updatorder(@PathVariable("sessionId") String sessionId,
			@RequestBody OrderMainBean orderMainBean,
			HttpServletRequest request) {

		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {

				if(orderMainBean != null)
				{



					util.AuditTrail(request, currentUser, "EcomOrderController.updatorder",
							"User " + currentUser.getUserEmail() 
							+ orderMainBean.getOrderRefNbr()
							+ " successfully ", false);

					return new Response(orderMainBean, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				}

				else
				{
					util.AuditTrail(request, currentUser, "EcomOrderController.updatorder", 
							"Please contact Admin "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);

				}

			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "EcomOrderController.updatorder","Error Occured " + errors.toString(), true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		} else {
			return new Response(MessageConstants.INVALID_SESSION,
					StatusConstants.INVALID, LayOutPageConstants.LOGIN);
		}
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getOrderByContactId/{sessionId}/{contactId}", method = RequestMethod.POST)
	public @ResponseBody Response getOrderByContactId(@PathVariable("sessionId") String sessionId,
			@PathVariable("contactId") String contactId,
			HttpServletRequest request)  {

		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {

				if(contactId != null && !contactId.equals("") && Integer.parseInt(contactId) > 0)
				{
					List<OrderMain> orderList = new ArrayList<>();



					util.AuditTrail(request, currentUser, "EcomOrderController.getOrderByContactId", 
							"User "+ currentUser.getUserId()+" retrived all Orders successfully ",false);

					return new Response(orderList, StatusConstants.SUCCESS,
							LayOutPageConstants.SELL);
				}

				else
				{
					util.AuditTrail(request, currentUser, "EcomOrderController.getOrderByContactId", 
							"No Orders found "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);

				}

			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "EcomOrderController.getOrderByContactId","Error Occured " + errors.toString(), true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		} else {
			return new Response(MessageConstants.INVALID_SESSION,
					StatusConstants.INVALID, LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getOrderByOutletId/{sessionId}/{outletId}", method = RequestMethod.POST)
	public @ResponseBody Response getOrderByOutletId(@PathVariable("sessionId") String sessionId,
			@PathVariable("outletId") String outletId,
			HttpServletRequest request)  {
		List<OrderMainBean> orderMainBeanList = null;
		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {

				if(outletId != null && !outletId.equals("") && Integer.parseInt(outletId) > 0)
				{
					orderMainBeanList = new ArrayList<OrderMainBean>(); 
					List<OrderMain> orderList = ecomOrderService.getOrdersByOutletId(Integer.parseInt(outletId));						

					for (OrderMain orderMain : orderList) {

						OrderMainBean orderBean = PopulateOrderMain(orderMain);
						if(orderBean != null )
						{

							orderMainBeanList.add(orderBean);
						}
					}

					util.AuditTrail(request, currentUser, "EcomOrderController.getOrderByOutletId", 
							"User "+ currentUser.getUserId()+" retrived all Orders successfully ",false);

					return new Response(orderMainBeanList, StatusConstants.SUCCESS,
							LayOutPageConstants.SELL);
				}

				else
				{
					util.AuditTrail(request, currentUser, "EcomOrderController.getOrderByOutletId", 
							"No Orders found "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);

				}

			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "EcomOrderController.getOrderByOutletId","Error Occured " + errors.toString(), true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		} else {
			return new Response(MessageConstants.INVALID_SESSION,
					StatusConstants.INVALID, LayOutPageConstants.LOGIN);
		}
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getOrderByOrderReferenceNumber/{sessionId}/{orderRefNumber}", method = RequestMethod.POST)
	public @ResponseBody Response getOrderByOrderReferenceNumber(@PathVariable("sessionId") String sessionId,
			@PathVariable("orderRefNumber") String orderRefNumber,
			HttpServletRequest request)  {

		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {

				if(orderRefNumber != null && !orderRefNumber.equals(""))
				{
					OrderMain order = new OrderMain();



					util.AuditTrail(request, currentUser, "EcomOrderController.getOrderByOrderReferenceNumber", 
							"User "+ currentUser.getUserId()+" retrived all Orders successfully ",false);

					return new Response(order, StatusConstants.SUCCESS,
							LayOutPageConstants.SELL);
				}

				else
				{
					util.AuditTrail(request, currentUser, "EcomOrderController.getOrderByOrderReferenceNumber", 
							"No Orders found "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);

				}

			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "EcomOrderController.getOrderByOrderReferenceNumber","Error Occured " + errors.toString(), true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		} else {
			return new Response(MessageConstants.INVALID_SESSION,
					StatusConstants.INVALID, LayOutPageConstants.LOGIN);
		}
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getOrderByOrderTrackingNumber/{sessionId}/{orderTrackingNumber}", method = RequestMethod.POST)
	public @ResponseBody Response getOrderByOrderTrackingNumber(@PathVariable("sessionId") String sessionId,
			@PathVariable("orderTrackingNumber") String orderTrackingNumber,
			HttpServletRequest request)  {

		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {

				if(orderTrackingNumber != null && !orderTrackingNumber.equals(""))
				{
					OrderMain order = new OrderMain();



					util.AuditTrail(request, currentUser, "EcomOrderController.getOrderByOrderTrackingNumber", 
							"User "+ currentUser.getUserId()+" retrived all Orders successfully ",false);

					return new Response(order, StatusConstants.SUCCESS,
							LayOutPageConstants.SELL);
				}

				else
				{
					util.AuditTrail(request, currentUser, "EcomOrderController.getOrderByOrderTrackingNumber", 
							"No Orders found "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);

				}

			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "EcomOrderController.getOrderByOrderTrackingNumber","Error Occured " + errors.toString(), true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		} else {
			return new Response(MessageConstants.INVALID_SESSION,
					StatusConstants.INVALID, LayOutPageConstants.LOGIN);
		}
	}

	private OrderMainBean PopulateOrderMain(OrderMain orderMain)

	{
		OrderMainBean orderMainBean = null;
		if(orderMain != null && orderMain.getOrderDetails() != null)
		{
			orderMainBean = new OrderMainBean();
			orderMainBean.setCompanyId(orderMain.getCompany().getCompanyId().toString());
			orderMainBean.setContactId(orderMain.getContact().getContactId().toString());
			orderMainBean.setDeliveryMethodId(orderMain.getDeliveryMethod().getDeliveryMethodId().toString());
			orderMainBean.setOrderBillAmt(orderMain.getOrderBillAmt().toString());
			orderMainBean.setOrderCancelDte(orderMain.getOrderCancelDte().toString());
			orderMainBean.setOrderDeliveryDte(orderMain.getOrderDeliveryDte().toString());
			orderMainBean.setOrderExpectedDeliveryDte(orderMain.getOrderExpectedDeliveryDte().toString());
			orderMainBean.setOrderGenerationDte(orderMain.getOrderGenerationDte().toString());
			orderMainBean.setOrderMainId(orderMain.getOrderMainId().toString());
			orderMainBean.setOrderNotes(orderMain.getOrderNotes());
			orderMainBean.setOrderRefNbr(orderMain.getOrderRefNbr());
			orderMainBean.setOrderTrackingNbr(orderMain.getOrderTrackingNbr());
			orderMainBean.setOutletId(orderMain.getOutlet().toString());
			orderMainBean.setPaymentTypeId(orderMain.getPaymentType().getPaymentTypeId().toString());
			orderMainBean.setStatusId(orderMain.getStatus().getStatusId().toString());
			orderMainBean.setTaxAmt(orderMain.getTaxAmt().toString());

			Contact contact = contactService.getContactByID(orderMain.getContact().getContactId(), orderMain.getCompany().getCompanyId()); 
			if(contact != null)
			{
				orderMainBean.setContactName(contact.getContactName());
			}
			//List<OrderDetailBean> listOrderDetBean = new ArrayList<OrderDetailBean>();
			for (OrderDetail orderDetail : orderMain.getOrderDetails()) {

				OrderDetailBean orderDetailBean = new OrderDetailBean();
				orderDetailBean.setItemNotes(orderDetail.getItemNotes());
				orderDetailBean.setItemTaxAmount(orderDetail.getItemTaxAmount().toString());
				orderDetailBean.setItemUnitPrice(orderDetail.getItemUnitPrice().toString());
				orderDetailBean.setOrderDetailId(orderDetail.getOrderDetailId().toString());
				orderDetailBean.setOrderMainId(orderDetail.getOrderMain().getOrderMainId().toString());
				orderDetailBean.setProductId(orderDetail.getProduct().getProductId().toString());
				orderDetailBean.setProductVariantId(orderDetail.getProductVariant().getProductVariantId().toString());
				orderDetailBean.setProductQuantity(String.valueOf(orderDetail.getProductQuantity()));

				Product prod =	productService.getProductByProductId(orderDetail.getProduct().getProductId(), orderMain.getCompany().getCompanyId());
				ProductVariant prodVarient = productVariantService.getProductVariantByProductVariantId(orderDetail.getProductVariant().getProductVariantAssociationId(), orderMain.getCompany().getCompanyId());
				if(prod != null)
				{
					orderDetailBean.setProductName(prod.getProductName());
				}

				if(prodVarient != null)
				{
					orderDetailBean.setProductVariantName(prodVarient.getVariantAttributeName());
				}
				orderMainBean.getOrderDetails().add(orderDetailBean);
			}

		}

		return orderMainBean;
	}
}


