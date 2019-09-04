package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Address;
import com.dowhile.Contact;
import com.dowhile.ContactPayments;
import com.dowhile.InvoiceDetailCustom;
import com.dowhile.InvoiceMainCustom;
import com.dowhile.ReceiptCustom;
import com.dowhile.User;
import com.dowhile.angualrspringapp.beans.ProductSalesHistoryBean;
import com.dowhile.angualrspringapp.beans.SalesHistory;
import com.dowhile.angualrspringapp.beans.SalesHistoryBean;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.CustomerDetailControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.CustomerPaymentsBean;
import com.dowhile.frontend.mapping.bean.ReceiptBean;
import com.dowhile.service.AddressService;
import com.dowhile.service.CompanyService;
import com.dowhile.service.ContactGroupService;
import com.dowhile.service.ContactPaymentsService;
import com.dowhile.service.ContactService;
import com.dowhile.service.ReceiptService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.SaleService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/customerDetails")
public class CustomerDetailsController {

	@Resource
	private CompanyService companyService;
	
	@Resource
	private ContactService contactService;
	
	@Resource
	private ContactPaymentsService contactPaymentsService;
	
	@Resource
	private ContactGroupService customerGroupService;
	
	@Resource
	private AddressService addressService;
	
	@Resource
	private ResourceService resourceService;
	@Resource
	private ReceiptService receiptService;
	@Resource
	private SaleService saleService;
	
	@Resource
	private ServiceUtil util;
	
	private Integer customerid;

	@RequestMapping("/layout")
	public String getCustomerDetailsControllerPartialPage(ModelMap modelMap) {
		return "customerDetails/layout";
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/loadCustomerDetails/{sessionId}/{customerId}", method = RequestMethod.POST)
	public @ResponseBody Response loadCustomerDetails(@PathVariable("sessionId") String sessionId,@PathVariable("customerId")
			String customerId,
			HttpServletRequest request) {

		customerid = Integer.valueOf(customerId);
		CustomerDetailControllerBean customerDetailControllerBean = new CustomerDetailControllerBean();
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			List<CustomerPaymentsBean> customerPaymentsBeans = new ArrayList<>();
			try {
				Contact contact = contactService.getContactByID(Integer.parseInt(customerId), currentUser.getCompany().getCompanyId());
				if(contact.getContactBalance()!=null){
					 DecimalFormat twoDForm = new DecimalFormat("#.##");
					 
					customerDetailControllerBean.setBalance(twoDForm.format(contact.getContactBalance()));
				}else{
					customerDetailControllerBean.setBalance("0");
				}
				if(contact.getLastName()!=null){
					customerDetailControllerBean.setContactName(contact.getFirstName()+" "+ contact.getLastName());
					
				}else{
					customerDetailControllerBean.setContactName(contact.getFirstName());
					
				}
				if(contact.getContactGroup()!=null){
					customerDetailControllerBean.setCustomerGroup(customerGroupService.getContactGroupByContactGroupId(contact.getContactGroup().getContactGroupId(),
							currentUser.getCompany().getCompanyId()).getContactGroupName());
				
				}else{
					customerDetailControllerBean.setCustomerGroup("-");
				}
				customerDetailControllerBean.setStoreCredit("0");
				customerDetailControllerBean.setTotalEarned("0");
				List<ContactPayments>  contactPayments = contactPaymentsService.getCustonerPaymentsByContactId(contact.getContactId(), currentUser.getCompany().getCompanyId());
				if(contactPayments!=null){
					double totalSpent = 0.0;
					for(ContactPayments payments : contactPayments){
						CustomerPaymentsBean customerPaymentsBean = new CustomerPaymentsBean();
						customerPaymentsBean.setDate(DateTimeUtil.convertDBDateTimeToGuiFormat(payments.getCreatedDate()));
						customerPaymentsBean.setNote(payments.getDescription());
						customerPaymentsBean.setReceipt(payments.getPaymentRefNum());
						//customerPaymentsBean.setRegister(payments.get);
						//customerPaymentsBean.setStatus(status);
						if(payments.getPaymentCash()!=null){
							customerPaymentsBean.setTotal(payments.getPaymentCash().toString());
							totalSpent = totalSpent + payments.getPaymentCash().doubleValue();
						}else{
							customerPaymentsBean.setTotal("0");
							totalSpent = totalSpent + 0;
						}
						
						
						customerPaymentsBean.setUser(resourceService.getUserById(payments.getCreatedBy(), currentUser.getCompany().getCompanyId()).getUserEmail());
						customerPaymentsBeans.add(customerPaymentsBean);
					
					}
					customerDetailControllerBean.setTotalSpent(String.valueOf(totalSpent));
				}
				customerDetailControllerBean.setCustomerPaymentsBeans(customerPaymentsBeans);
				SalesHistory salesHistory = getData(sessionId, 0, request);
				customerDetailControllerBean.setSalesHistory(salesHistory);
					util.AuditTrail(request, currentUser, "CustomerDetailsController.loadCustomerDetails", 
							"Customer id  "+ customerId+" retrived all Customer Details ",false);
					return new Response(customerDetailControllerBean, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "CustomerDetailsController.loadCustomerDetails",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
	@RequestMapping(value = "/getData/{sessionId}/{limit}", method = RequestMethod.GET)
	public @ResponseBody SalesHistory getData(@PathVariable("sessionId") String sessionId,@PathVariable("limit") int limit,
			HttpServletRequest request) {
		List<InvoiceMainCustom> invoiceMains = new ArrayList<InvoiceMainCustom>();
		List<SalesHistoryBean> data = new ArrayList<SalesHistoryBean>();
		List<ReceiptCustom> receipts =  new ArrayList<ReceiptCustom>();
		List<InvoiceDetailCustom> details =  new ArrayList<InvoiceDetailCustom>();
		Address address = null;
		if(SessionValidator.isSessionValid(sessionId.replaceAll("\"", ""), request)){

			HttpSession session = request.getSession(false);
			Map<Integer, List<ReceiptCustom>> receiptMap =  new HashMap<>(); 
			Map<Integer, List<InvoiceDetailCustom>> invoiceDetailMap = new HashMap<>(); 
			User currentUser = (User) session.getAttribute("user");
			String invoiceRefNo = null;
			String status = null;
			Date fromDate = null;
			Date toDate = null;
			
			invoiceMains  = saleService.getAllInvoicesMainById(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId(), limit,invoiceRefNo, status, fromDate,toDate,customerid);
			Map<Integer, Contact> customerMap = contactService.getContactsByOutletIDMap(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
			Map<Integer, User> userMap = resourceService.getAllUsersMap( currentUser.getCompany().getCompanyId());
			int fromInvoiceId =1;
			int toInvoiceId = 100000000;
			InvoiceMainCustom firstitem = null;
			InvoiceMainCustom lastItem = null;
			if (invoiceMains != null && invoiceMains.size() > 0) {
				lastItem = invoiceMains.get(invoiceMains.size()-1);
				if(lastItem != null)
				{
					fromInvoiceId = lastItem.getINVOICE_MAIN_ID();
				}
				firstitem = invoiceMains.get(0);
				if(firstitem != null)
				{
					toInvoiceId = firstitem.getINVOICE_MAIN_ID();
				}
		     }
			
		    invoiceDetailMap  = saleService.getAllInvoicesDetailsByInvoiceIdRange(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId(), fromInvoiceId,toInvoiceId);
			
		    if(currentUser.getOutlet().getAddress()!=null){
				address =  addressService.getAddressByAddressId(currentUser.getOutlet().getAddress().getAddressId(), currentUser.getCompany().getCompanyId());

			}
			if(invoiceMains!=null && invoiceMains.size() > 0)
			{
				receiptMap  = receiptService.getAllReceipts(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId());
			}

			if(invoiceMains!=null && currentUser!=null){
				for(InvoiceMainCustom invoiceMain : invoiceMains){
					SalesHistoryBean historyBean = new SalesHistoryBean();
					double subtotal = 0;
				//	double totalLineItemDiscount = 0;
					int itemsCount = 0;
					historyBean.setBalance(invoiceMain.getINVOICE_NET_AMT().subtract(invoiceMain.getSETTLED_AMT()).toString());
					historyBean.setCashGiven(invoiceMain.getINVOICE_GIVEN_AMT().toString());
					historyBean.setCashReturn(invoiceMain.getINVOICE_NET_AMT().subtract(invoiceMain.getINVOICE_GIVEN_AMT()).toString() );

					historyBean.setCashReturnDate(DateTimeUtil.convertDBDateTimeToGuiFormat(invoiceMain.getCREATED_DATE()));
					historyBean.setCashGivenDate(DateTimeUtil.convertDBDateTimeToGuiFormat(invoiceMain.getCREATED_DATE()));

					if(invoiceMain.getCONTACT_ID() !=null){
						Contact customer = customerMap.get(invoiceMain.getCONTACT_ID());
						if(customer != null)
						{
							historyBean.setCustomer(customer.getContactId().toString());
							historyBean.setCustomerName(customer.getLastName()!=null?customer.getFirstName() + " "+ customer.getLastName():customer.getFirstName());
						}else{
							historyBean.setCustomer("-");
							historyBean.setCustomerName("-");
						}
					}else{
						historyBean.setCustomer("-");
						historyBean.setCustomerName("-");
					}


					historyBean.setDate(DateTimeUtil.convertDBDateTimeToGuiFormat(invoiceMain.getCREATED_DATE()));
					if(invoiceMain.getINVOICE_DISCOUNT_AMT()!=null){
						historyBean.setDiscount(invoiceMain.getINVOICE_DISCOUNT_AMT().toString());
					}else{
						historyBean.setDiscount("");
					}

					if(invoiceMain.getINVOICE_NOTES()!=null){
						historyBean.setNote(invoiceMain.getINVOICE_NOTES());
					}else{
						historyBean.setNote("-");
					}
					historyBean.setInvoiceId(invoiceMain.getINVOICE_MAIN_ID().toString());
					historyBean.setReceipt(invoiceMain.getINVOICE_REF_NBR());
					historyBean.setSaleTotal(invoiceMain.getINVOICE_NET_AMT().toString());
					User customer = userMap.get(invoiceMain.getSALES_USER());
					if(customer != null)
					{
						historyBean.setSoldby(customer.getLastName()!=null?customer.getFirstName() + " "+ customer.getLastName():customer.getFirstName());
					}
					historyBean.setStatus(invoiceMain.getSTATUS_DESC());
					//historyBean.setSubtotal(invoiceMain.getINVOICE_NET_AMT().toString());
					historyBean.setTotal(invoiceMain.getINVOICE_NET_AMT().toString());
					historyBean.setTotalTax(invoiceMain.getINVOICE_TAX().toString());
					historyBean.setHtml("<button class=\"btn btn-success btn-lg\" ng-click=\"processPayment('"+invoiceMain.getINVOICE_MAIN_ID().toString()+"')\">Adjust Payment</button>");
					historyBean.setPrint("<button class=\"btn btn-success btn-lg\" ng-click=\"printReceipt('"+invoiceMain.getINVOICE_MAIN_ID().toString()+"')\">Print</button>");
					if(invoiceMain.getSTATUS_ID() == 14 || invoiceMain.getSTATUS_ID() == 13 ||
							(invoiceMain.getINVC_TYPE_CDE() !=null &&	invoiceMain.getINVC_TYPE_CDE().equalsIgnoreCase("00000"))
							){
						historyBean.setHtml("");

					}
					//List<InvoiceDetailCustom> details  = saleService.getAllInvoicesDeailById(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId(), invoiceMain.getINVOICE_MAIN_ID());

					if(invoiceDetailMap != null)
					{
						details  = invoiceDetailMap.get(invoiceMain.getINVOICE_MAIN_ID());
					}
					List<ProductSalesHistoryBean> products = new ArrayList<ProductSalesHistoryBean>();
					if(details!=null && details.size() > 0){
						for(InvoiceDetailCustom detail:details){
							ProductSalesHistoryBean product = new ProductSalesHistoryBean();
							product.setPricewithoutTax("@ PKRs"+detail.getItemSalePrice().multiply(new BigDecimal(detail.getProductQuantity()))+" + PKRs0.00 Tax (No Tax)");
							product.setPricewithTax(detail.getItemSalePrice().multiply(new BigDecimal(detail.getProductQuantity())).toString());
							product.setQuantity(String.valueOf(detail.getProductQuantity()));
							product.setProductName(detail.getProductName() +" - " + detail.getVarientAttributeName() );
							product.setItemRetailPrice(detail.getItemRetailPrice().toString());	
							product.setItemDiscountPrct(detail.getItemDiscountPrct().toString());	
							
							subtotal = subtotal + (detail.getItemSalePrice().doubleValue() * detail.getProductQuantity());
							historyBean.setSubtotal(String.valueOf(subtotal));
							
							//BigDecimal lineitemDiscountAmt = detail.getItemRetailPrice().multiply((detail.getItemDiscountPrct().divide( new BigDecimal(100)))).multiply( new BigDecimal( detail.getProductQuantity()));
							
							//totalLineItemDiscount.add(lineitemDiscountAmt);
						//	totalLineItemDiscount = totalLineItemDiscount + lineitemDiscountAmt.doubleValue();
							itemsCount = itemsCount + detail.getProductQuantity();
							products.add(product);
						}
						
						if(invoiceMain.getINVOICE_DISCOUNT_AMT() == null){
							invoiceMain.setINVOICE_DISCOUNT_AMT(new BigDecimal(0));
						}
						
						//invoiceMain.getINVOICE_DISCOUNT_AMT().add(new BigDecimal(totalLineItemDiscount));
						
						
						//historyBean.setDiscount(String.valueOf(invoiceMain.getINVOICE_DISCOUNT_AMT()));
						historyBean.setitemsCount(String.valueOf(itemsCount));
					}


					//List<Receipt> receipts =  receiptService.getAllReceiptsByInvoiceId(invoiceMain.getINVOICE_MAIN_ID(),currentUser.getCompany().getCompanyId());
					if(receiptMap != null)
					{
						receipts =  receiptMap.get(invoiceMain.getINVOICE_MAIN_ID());
					}
					List<ReceiptBean> receiptBeans = new ArrayList<>();

					if(receipts != null && receipts.size() > 0)
					{
						for(ReceiptCustom receipt:receipts){
							ReceiptBean bean = new ReceiptBean();
							bean.setActiveIndicator(String.valueOf(receipt.isActiveIndicator()));
							bean.setReceiptAmount(receipt.getReceiptAmount().toString());
							bean.setReceiptDate(DateTimeUtil.convertDBDateTimeToGuiFormat(receipt.getReceiptDate()));
							bean.setReceiptId(receipt.getReceiptId().toString());
							receiptBeans.add(bean);
						}
					}
					historyBean.setReceipts(receiptBeans);
					historyBean.setProducts(products);
					data.add(historyBean);
				}

			}
		}
		SalesHistory object= new SalesHistory();
		if(address!=null){
			object.setCompanyAddress(address.getStreet() + ", " + address.getCity());
			object.setPhoneNumber(address.getPhone());
		}


		object.setData(data);
		return object;
	}


}

