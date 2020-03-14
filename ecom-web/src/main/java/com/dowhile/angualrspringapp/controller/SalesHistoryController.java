package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.dowhile.Configuration;
import com.dowhile.Contact;
import com.dowhile.InvoiceDetail;
import com.dowhile.InvoiceDetailCustom;
import com.dowhile.InvoiceMain;
import com.dowhile.InvoiceMainCustom;
import com.dowhile.Product;
import com.dowhile.ProductVariant;
import com.dowhile.ReceiptCustom;
import com.dowhile.User;
import com.dowhile.angualrspringapp.beans.ProductSalesHistoryBean;
import com.dowhile.angualrspringapp.beans.SalesHistory;
import com.dowhile.angualrspringapp.beans.SalesHistoryBean;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.configuration.bean.ReceiptConfigurationBean;
import com.dowhile.frontend.mapping.bean.InvoiceDetailBean;
import com.dowhile.frontend.mapping.bean.InvoiceMainBean;
import com.dowhile.frontend.mapping.bean.ReceiptBean;
import com.dowhile.service.AddressService;
import com.dowhile.service.CompanyService;
import com.dowhile.service.ContactService;
import com.dowhile.service.DailyRegisterService;
import com.dowhile.service.OutletService;
import com.dowhile.service.PaymentTypeService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductTypeService;
import com.dowhile.service.ProductVariantService;
import com.dowhile.service.ReceiptService;
import com.dowhile.service.RegisterService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.SaleService;
import com.dowhile.service.StatusService;
import com.dowhile.service.VariantAttributeService;
import com.dowhile.service.VariantAttributeValuesService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/salesHistory")
public class SalesHistoryController {

	// private static Logger logger = Logger.getLogger(SalesHistoryController.class.getName());
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
	private ResourceService resourceService;
	@Resource
	private DailyRegisterService dailyRegisterService;

	@Resource
	private RegisterService registerService;


	@Resource
	private AddressService addressService;


	@RequestMapping("/layout")
	public String getSalesHistoryControllerPartialPage(ModelMap modelMap) {
		return "salesHistory/layout";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/processPayment/{sessionId}/{InvoiceMainId}", method = RequestMethod.POST)
	public @ResponseBody
	Response processPayment(@PathVariable("sessionId") String sessionId,@PathVariable("InvoiceMainId") String invoiceMainId, HttpServletRequest request) {
		InvoiceMainBean invoiceMainBean = new InvoiceMainBean();
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
       int itemsCount = 0;
  // 	double subtotal = 0; 
			List<InvoiceDetail> invoiceDetails = null; 
			List<InvoiceDetailBean> invoiceDetailBeans = new ArrayList<>();
			try{
				InvoiceMain invoiceMain  = saleService.getInvoiceMainByID(Integer.parseInt(invoiceMainId),currentUser.getCompany().getCompanyId());
				if(invoiceMain.getContact()!=null){

					Contact customer = customerService.getContactByID(invoiceMain.getContact().getContactId(), currentUser.getCompany().getCompanyId());

					if(customer != null)
					{
						invoiceMainBean.setCustomerId(customer.getContactId().toString());
						invoiceMainBean.setCustomername(customer.getLastName()!=null?customer.getFirstName() + " "+ customer.getLastName():customer.getFirstName());
					}
				}

				invoiceMainBean.setDailyRegesterId(invoiceMain.getDailyRegister().getDailyRegisterId().toString());
				invoiceMainBean.setInvoiceAmt(invoiceMain.getInvoiceAmt().toString());
				//invoiceMainBean.setInvoiceCancelDte(invoiceMain.getcn);

				if(invoiceMain.getInvoiceDiscount()!=null){
					invoiceMainBean.setInvoiceDiscount(invoiceMain.getInvoiceDiscount().toString());
				}
				invoiceMainBean.setInvoiceRefNbr(invoiceMain.getInvoiceRefNbr());
				invoiceMainBean.setInvcTypeCde(invoiceMain.getInvcTypeCde());
				invoiceMainBean.setInvoiceDiscountAmt(invoiceMain.getInvoiceDiscountAmt().toString());
				invoiceMainBean.setInvoiceGenerationDte(DateTimeUtil.convertDBDateTimeToGuiFormat(invoiceMain.getInvoiceGenerationDte()));
				invoiceMainBean.setInvoiceGivenAmt(invoiceMain.getInvoiceGivenAmt().toString());
				invoiceMainBean.setInvoiceMainId(invoiceMainId);
				invoiceMainBean.setInvoiceNetAmt(invoiceMain.getInvoiceNetAmt().toString());
				invoiceMainBean.setInvoiceNotes(invoiceMain.getInvoiceNotes());
				invoiceMainBean.setInvoiceRefNbr(invoiceMain.getInvoiceRefNbr());
				invoiceMainBean.setInvoiceTax(invoiceMain.getInvoiceTax().toString());
				invoiceMainBean.setOutletId(invoiceMain.getOutlet().getOutletId().toString());
				invoiceMainBean.setPaymentTypeId(invoiceMain.getPaymentType().getPaymentTypeId().toString());
				invoiceMainBean.setSettledAmt(invoiceMain.getSettledAmt().toString());
				invoiceMainBean.setStatus(invoiceMain.getStatus().getStatusId().toString());
				if(invoiceMain.getInvoiceOrignalAmt()!=null){
					invoiceMainBean.setOrignalPrice(invoiceMain.getInvoiceOrignalAmt().toString());

				}
				//					invoiceMainBean.setTransactionType(invoiceMain.get);

				if(invoiceMain!=null){
					invoiceDetails =  saleService.getAllInvoicesDeailById(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId(), invoiceMain.getInvoiceMainId());
					for(InvoiceDetail invoiceDetail : invoiceDetails){
						InvoiceDetailBean detailBean = new InvoiceDetailBean();
						//							detailBean.setActiveIndicator(invoiceDetail.);
						//							detailBean.setAmntCmptCde(amntCmptCde);
						//							detailBean.setAmntSubCmptCde(amntSubCmptCde);
						itemsCount = itemsCount + invoiceDetail.getProductQuantity();
						detailBean.setCreatedBy(invoiceDetail.getCreatedBy().toString());
						detailBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(invoiceDetail.getCreatedDate()));
						detailBean.setInvoiceDetailId(invoiceDetail.getInvoiceDetailId().toString());
						//							detailBean.setInvoiceSettleDte(invoiceDetail.get);
						detailBean.setItemDiscountPrct(invoiceDetail.getItemDiscountPrct().toString());											
						detailBean.setItemNotes(invoiceDetail.getItemNotes());
						detailBean.setItemRetailPrice(invoiceDetail.getItemRetailPrice().toString());
						detailBean.setItemSalePrice(invoiceDetail.getItemSalePrice().toString());
						detailBean.setItemTaxAmount(invoiceDetail.getItemTaxAmount().toString());
						detailBean.setLastUpdated(DateTimeUtil.convertDBDateTimeToGuiFormat(invoiceDetail.getLastUpdated()));
						detailBean.setItemNetPrice(String.valueOf((invoiceDetail.getProductQuantity() * invoiceDetail.getItemSalePrice().doubleValue())));
					//	subtotal = subtotal + (invoiceDetail.getItemSalePrice().doubleValue() * invoiceDetail.getProductQuantity()) + invoiceDetail.getItemTaxAmount().doubleValue() ;
						
						
						Product product = productService.getProductByProductId(invoiceDetail.getProduct().getProductId(),currentUser.getCompany().getCompanyId());

						detailBean.setProductName(product.getProductName());
						if(invoiceDetail.getProductVariant() != null)
						{
							ProductVariant productVarient = productVariantService.getProductVariantByProductVariantId(invoiceDetail.getProductVariant().getProductVariantId(),currentUser.getCompany().getCompanyId());

							detailBean.setProductId(productVarient.getProduct().getProductId().toString());
							detailBean.setProductVarientId(productVarient.getProductVariantId().toString());
							detailBean.setProductVarientName(productVarient.getVariantAttributeName());
							detailBean.setProductInventoryCount(productVarient.getCurrentInventory().toString());
						}

						else if(invoiceDetail.getProduct() != null)
						{

							//Product product = productService.getProductByProductId(invoiceDetail.getProduct().getProductId(),currentUser.getCompany().getCompanyId());

							detailBean.setProductId(product.getProductId().toString());
							detailBean.setProductInventoryCount(product.getCurrentInventory().toString());
						}

						detailBean.setProductQuantity(String.valueOf(invoiceDetail.getProductQuantity()));
						detailBean.setOrignalProductQuantity(String.valueOf(invoiceDetail.getProductQuantity()));

						//							detailBean.setSettledAmt(invoiceDetail.get);
						//							detailBean.setShowDetails(showDetails);
						//							detailBean.setStatus(invoiceDetail.get);
						detailBean.setUpdatedBy(invoiceDetail.getUpdatedBy().toString());
						if(invoiceDetail.getItemOrignalAmt()!=null){
							detailBean.setOrignalPrice(invoiceDetail.getItemOrignalAmt().toString());

						}
						invoiceDetailBeans.add(detailBean);

					}
				}
				invoiceMainBean.setitemsCount(String.valueOf(itemsCount));
				//invoiceMainBean.setInvoiceSubTotal(String.valueOf(subtotal));
				invoiceMainBean.setInvoiceDetails(invoiceDetailBeans);
				util.AuditTrail(request, currentUser, "SalesHistoryController.processPayment", 
						"User "+ currentUser.getUserEmail()+" Process Payment for Invoice Id: +"+invoiceMainId+" successfully ",false);
				return new Response(invoiceMainBean,StatusConstants.SUCCESS,LayOutPageConstants.SELL);


			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SalesHistoryController.processPayment",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.SELL);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}


	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getData/{sessionId}/{limit}/{startDate}/{endDate}/{applyDateRange}", method = RequestMethod.GET)
	public @ResponseBody SalesHistory getData(@PathVariable("sessionId") String sessionId,@PathVariable("limit") int limit,
			@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate,@PathVariable("applyDateRange") String applyDateRange,
			HttpServletRequest request) {
		List<InvoiceMainCustom> invoiceMains = new ArrayList<InvoiceMainCustom>();
		List<SalesHistoryBean> data = new ArrayList<SalesHistoryBean>();
		List<ReceiptCustom> receipts =  new ArrayList<ReceiptCustom>();
		List<InvoiceDetailCustom> details =  new ArrayList<InvoiceDetailCustom>();
		Address address = null;
		SalesHistory object= new SalesHistory();
		if(SessionValidator.isSessionValid(sessionId.replaceAll("\"", ""), request)){

			HttpSession session = request.getSession(false);
			Map<Integer, List<ReceiptCustom>> receiptMap =  new HashMap<>(); 
			Map<Integer, List<InvoiceDetailCustom>> invoiceDetailMap = new HashMap<>(); 
			User currentUser = (User) session.getAttribute("user");
			Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
			ReceiptConfigurationBean receiptConfigurationBean = new ReceiptConfigurationBean();
			/*System.out.println("receiptConfigurationBean.isStarndardReceipt(): "+receiptConfigurationBean.isStarndardReceipt());
			System.out.println("receiptConfigurationBean.isXpressionsReceipt(): "+receiptConfigurationBean.isXpressionsReceipt());*/
			Configuration configurationReceipt = configurationMap.get("CUSTOM_RECEIPT_NAME");
			boolean customizedReceipt = false;
			if(configurationReceipt!=null){
				if(configurationReceipt.getPropertyValue().equalsIgnoreCase("xpressions")){
					receiptConfigurationBean.setXpressionsReceipt(true);
					customizedReceipt = true;
				}
			}
			if(!customizedReceipt){
				receiptConfigurationBean.setStarndardReceipt(true);
			}
			object.setReceiptConfigurationBean(receiptConfigurationBean);
			Configuration configurationTermsAndContitions = configurationMap.get("TERMS_AND_CONDITIONS");
			if(configurationTermsAndContitions!=null){
				String termsAndContitions = configurationTermsAndContitions.getPropertyValue();
				object.setTermsAndConditions(termsAndContitions);
			}
			
			String invoiceRefNo = null;
			String status = null;
			Date fromDate = null;
			Date toDate = null;
			Integer customerId = null;
			if(limit==0 && applyDateRange!=null && applyDateRange.equalsIgnoreCase("true")){
				SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
				Date startDat =  null;
				Date endDat =  null;
				DateFormat parser = new SimpleDateFormat("MMM dd, yyyy");
				try {
					fromDate = (Date) parser.parse(startDate.trim());
					toDate = (Date) parser.parse(endDate);
					dt1.format(fromDate);
					dt1.format(toDate);
					invoiceMains  = saleService.getAllInvoicesMainById(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId(), limit,invoiceRefNo, status, fromDate,toDate,customerId);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();// logger.error(e.getMessage(),e);
				}
				
				
			}else{
				invoiceMains  = saleService.getAllInvoicesMainById(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId(), limit,invoiceRefNo, status, fromDate,toDate,customerId);
			}
			
			
			Map<Integer, Contact> customerMap = customerService.getContactsByOutletIDMap(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
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
							if(customer.getContactBalance()!=null)
								historyBean.setLaybayamount(customer.getContactBalance().toString());
						}else{
							historyBean.setCustomer("-");
							historyBean.setCustomerName("-");
							historyBean.setLaybayamount("0.00");
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
					historyBean.setHtml("<a href=\"#salesHistory\" ng-click=\"processPayment('"+invoiceMain.getINVOICE_MAIN_ID().toString()+"')\"><i class=\"fa fa-history\"></i></a>");
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
		
		if(address!=null){
			object.setCompanyAddress(address.getStreet() + ", " + address.getCity());
			object.setPhoneNumber(address.getPhone());
		}


		object.setData(data);
		return object;
	}

}


