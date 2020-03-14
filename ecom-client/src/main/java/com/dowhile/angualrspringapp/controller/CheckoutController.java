package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Address;
import com.dowhile.Company;
import com.dowhile.Contact;
import com.dowhile.OrderDetail;
import com.dowhile.OrderMain;
import com.dowhile.Outlet;
import com.dowhile.User;
import com.dowhile.angualrspringapp.beans.Response;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.frontend.mapping.bean.AddressBean;
import com.dowhile.frontend.mapping.bean.CustomerBean;
import com.dowhile.frontend.mapping.bean.ProductBean;
import com.dowhile.frontend.mapping.bean.CheckoutBean;
import com.dowhile.service.AddressService;
import com.dowhile.service.CompanyService;
import com.dowhile.service.ContactService;
import com.dowhile.service.DeliveryMethodService;
import com.dowhile.service.OrderDetailService;
import com.dowhile.service.OrderMainService;
import com.dowhile.service.OutletService;
import com.dowhile.service.PaymentTypeService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductTypeService;
import com.dowhile.service.ProductVariantService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.SalesTaxService;
import com.dowhile.service.StatusService;
import com.dowhile.service.VariantAttributeService;
import com.dowhile.service.VariantAttributeValuesService;
import com.dowhile.service.util.ServiceUtil;

/**
 * Zafar Shakeel
 */
@Controller
@RequestMapping("/checkout")
public class CheckoutController {

	@Resource
	private ResourceService resourceService;
	@Resource
	private ServiceUtil util;
	@Resource
	private ProductTypeService productTypeService;
	@Resource
	private ProductService productService;
	@Resource
	private CompanyService companyService;

	@Resource
	private VariantAttributeService variantAttributeService;

	@Resource
	private VariantAttributeValuesService variantAttributeValuesService;

	@Resource
	private SalesTaxService salesTaxService;

	@Resource
	private ProductVariantService productVariantService;
	
	@Resource
	private OrderDetailService orderDetailService;

	@Resource
	private OrderMainService orderMainService;

	@Resource
	private DeliveryMethodService deliveryMethodService;
	
	@Resource
	private PaymentTypeService paymentTypeService;

	@Resource
	private StatusService statusService;

	@Resource
	private ContactService customerService;

	@Resource
	private AddressService addressService;
	@Resource
	private OutletService outletService;


	
	@RequestMapping("/layout")
	public String getCheckoutControllerPartialPage(ModelMap modelMap) {
		return "checkout/layout";
	}

	

	  @SuppressWarnings({ "rawtypes", "unchecked" })
			@RequestMapping(value = "/checkout", method = RequestMethod.POST)
			public @ResponseBody Response checkout(@RequestBody CheckoutBean checkoutBean,
					HttpServletRequest request) {
			  User superUser =  resourceService.getUserById(1, 1);//On live server Imran is super user and his id is 1
			  int companyId=1;
			  int outletId=1;
			  Company company = companyService.getCompanyDetailsByCompanyID(companyId);
			  CustomerBean customerBean = checkoutBean.getCustomerBean();
			  Contact customer = new Contact();
			  Outlet outlet = outletService.getOuletByOutletId(outletId, companyId);
			  try {
					if(customerBean != null){
						customer.setLoyaltyEnabled("true");
						customer.setActiveIndicator(true);
						customer.setFirstName(customerBean.getFirstName());
						customer.setOutlet(outlet);
						customer.setLastName(customerBean.getLastName());
						customer.setCreatedBy(superUser.getUserId());
						customer.setCreatedDate(new Date());
						customer.setLastUpdated(new Date());	
						customer.setCompany(company);
						customer.setContactType("CUSTOMER");
						customer = customerService.addContact(customer,company.getCompanyId());
						List<AddressBean> addressBeanList = customerBean.getAddressBeanList(); 
						if(addressBeanList.isEmpty()==false){
						for (AddressBean addressBean : addressBeanList) {
							Address address = new Address();
							address.setContact(customer);
							address.setAddressType("Physical Address");
							address.setEmail(addressBean.getEmail());
							address.setPhone(addressBean.getPhone());
							address.setStreet(addressBean.getStreet());
							address.setCity(addressBean.getCity());
							address.setPostalCode(addressBean.getPostalCode());
							address.setActiveIndicator(true); 						
							address.setCreatedBy(superUser.getUserId());
							address.setCreatedDate(new Date());
							address.setLastUpdated(new Date());	
							address.setCompany(company);
							addressService.addAddress(address,company.getCompanyId());
							}
						}
					}
					OrderMain orderMain = new OrderMain();
					orderMain.setActiveIndicator(true);
					orderMain.setCompany(company);
					orderMain.setContact(customer);
					orderMain.setCreatedBy(superUser.getUserId());
					orderMain.setCreatedDate(new Date());
					orderMain.setDeliveryMethod(deliveryMethodService.getDeliveryMethodByDeliveryMethodId(1, companyId));
					orderMain.setOrderBillAmt(new BigDecimal(checkoutBean.getBillAmount()));
					orderMain.setOrderGenerationDte(new Date());
					orderMain.setOrderNotes(checkoutBean.getNotes());
//					orderMain.setOutlet(outlet);
					orderMain.setOrderRefNbr(new Date().toString());
					orderMain.setOrderTrackingNbr(new Date().toString());
					orderMain.setPaymentType(paymentTypeService.getPaymentTypeByPaymentTypeId(1, companyId));
					orderMain.setStatus(statusService.getStatusByStatusId(7));
					orderMain.setTaxAmt(BigDecimal.ZERO);
					orderMain.setUpdatedBy(superUser.getUserId());
					orderMain.setOutlet(outlet);
					orderMain = orderMainService.addOrderMain(orderMain, companyId);
					if(checkoutBean.getProductBeans()!=null){
					
						for(ProductBean productBean:checkoutBean.getProductBeans()){
						
							OrderDetail orderDetail = new OrderDetail();
							orderDetail.setCreatedBy(superUser.getUserId());
							orderDetail.setItemTaxAmount(BigDecimal.ZERO);
							orderDetail.setItemUnitPrice(new BigDecimal(productBean.getRetailPriceInclTax()));
							orderDetail.setOrderMain(orderMain);
							orderDetail.setOutlet(outlet);
							orderDetail.setProduct(productService.getProductByProductId(Integer.parseInt(productBean.getProductId()), companyId));
							orderDetailService.addOrderDetail(orderDetail, companyId);
						}
					}
					
					util.WebAuditTrail(request, superUser, "checkout","checkout",false);
					
				} catch (Exception e) {
					e.printStackTrace();
//					// logger.error(e.getMessage(),e);
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					util.WebAuditTrail(request, superUser, "CheckoutController.checkout",
							"Error Occured " + errors.toString(),true);

					return new Response("Sorry for the inconvenience please try again at a later time.",
							StatusConstants.BUSY, LayOutPageConstants.STAY_ON_PAGE);
				}
				return new Response("Your demo request has been received, you will be received an email on provided email address.",
						StatusConstants.SUCCESS, LayOutPageConstants.STAY_ON_PAGE);
			}
		  
		 }

