package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.hibernate.mapping.Array;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Address;
import com.dowhile.Company;
import com.dowhile.Configuration;
import com.dowhile.Contact;
import com.dowhile.ContactGroup;
import com.dowhile.ContactPayments;
import com.dowhile.ContactPaymentsType;
import com.dowhile.DailyRegister;
import com.dowhile.InvoiceDetail;
import com.dowhile.InvoiceMain;
import com.dowhile.Outlet;
import com.dowhile.PaymentType;
import com.dowhile.PriceBook;
import com.dowhile.PriceBookDetail;
import com.dowhile.Product;
import com.dowhile.ProductVariant;
import com.dowhile.Receipt;
import com.dowhile.Register;
import com.dowhile.SalesTax;
import com.dowhile.User;
import com.dowhile.VariantAttributeValues;
import com.dowhile.constant.Actions;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.controller.bean.SellControllerBean;
import com.dowhile.frontend.configuration.bean.ReceiptConfigurationBean;
import com.dowhile.frontend.mapping.bean.CustomerGroupBean;
import com.dowhile.frontend.mapping.bean.InvoiceDetailBean;
import com.dowhile.frontend.mapping.bean.InvoiceMainBean;
import com.dowhile.frontend.mapping.bean.PriceBookBean;
import com.dowhile.frontend.mapping.bean.PriceBookDetailBean;
import com.dowhile.frontend.mapping.bean.ProductBean;
import com.dowhile.frontend.mapping.bean.ProductVaraintDetailBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.frontend.mapping.bean.UserBean;
import com.dowhile.service.AddressService;
import com.dowhile.service.CashManagmentService;
import com.dowhile.service.CompanyService;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.ContactGroupService;
import com.dowhile.service.ContactPaymentsService;
import com.dowhile.service.ContactPaymentsTypeService;
import com.dowhile.service.ContactService;
import com.dowhile.service.ContactsSummmaryService;
import com.dowhile.service.DailyRegisterService;
import com.dowhile.service.ExpenseTypeService;
import com.dowhile.service.OutletService;
import com.dowhile.service.PaymentTypeService;
import com.dowhile.service.PriceBookDetailService;
import com.dowhile.service.PriceBookService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductTypeService;
import com.dowhile.service.ProductVariantService;
import com.dowhile.service.ReceiptService;
import com.dowhile.service.RegisterService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.SaleService;
import com.dowhile.service.SalesTaxService;
import com.dowhile.service.StatusService;
import com.dowhile.service.VariantAttributeService;
import com.dowhile.service.VariantAttributeValuesService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/sell")
public class SellController  {

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
	private ContactGroupService contactGroupService;

	@Resource
	private ContactPaymentsService customerPaymentsService;

	@Resource
	private AddressService addressService;

	@Resource
	private ConfigurationService configurationService;

	@Resource
	private PriceBookService priceBookService;

	@Resource
	private ContactsSummmaryService contactsSummmaryService;


	@Resource
	private PriceBookDetailService priceBookDetailService;

	@Resource
	private CashManagmentService cashManagmentService;
	@Resource
	private ContactPaymentsTypeService contactPaymentsTypeService;
	@Resource
	private ResourceService resourceService;

	@Resource
	private ExpenseTypeService expenseTypeService;
	
	@RequestMapping("/layout")
	public String getSellControllerPartialPage(ModelMap modelMap) {
		return "sell/layout";
	}

	private String[] colorsData = { "#4caf50", "#C0C0C0", "#CDDC39", "#03A9F4",
			"#FA8072", "#39cccc", "#f39c12", "#ff851b", "#00a65a", "#01ff70",
			"#dd4b39", "#605ca8", "#f012be", "#777", "#001f3f", "#F5DEB3",
			"#F5DEB3", "#EE82EE", "#40E0D0", "#FF6347", "#D8BFD8", "#008080",
			"#D2B48C", "#4682B4", "#00FF7F", "#708090", "#6A5ACD", "#87CEEB",
			"#C0C0C0", "#A0522D", "#2E8B57", "#F4A460", "#FA8072", "#FF9800",
			"#03A9F4", "#CDDC39", "#39cccc", "#f39c12", "#ff851b", "#00a65a",
			"#01ff70", "#dd4b39", "#605ca8", "#f012be", "#777", "#001f3f",
			"#F5DEB3", "#F5DEB3", "#EE82EE", "#40E0D0", "#FF6347", "#D8BFD8",
			"#008080", "#D2B48C", "#4682B4", "#00FF7F", "#708090", "#6A5ACD",
			"#87CEEB", "#C0C0C0", "#A0522D", "#2E8B57", "#CDDC39", "#FA8072",
			"#FF9800", "#03A9F4", "#CDDC39", "#39cccc", "#f39c12", "#ff851b",
			"#00a65a", "#01ff70", "#dd4b39", "#605ca8", "#f012be", "#777",
			"#001f3f", "#F5DEB3", "#F5DEB3", "#EE82EE", "#40E0D0", "#FF6347",
			"#D8BFD8", "#008080", "#D2B48C", "#4682B4", "#00FF7F", "#708090",
			"#6A5ACD", "#87CEEB", "#C0C0C0", "#A0522D", "#2E8B57", "#F4A460",
			"#FA8072", "#FF9800", "#03A9F4", "#CDDC39", "#39cccc", "#f39c12",
			"#ff851b", "#00a65a", "#01ff70", "#dd4b39", "#605ca8", "#f012be",
			"#777", "#001f3f", "#F5DEB3", "#F5DEB3", "#EE82EE", "#40E0D0",
			"#FF6347", "#D8BFD8", "#008080", "#D2B48C", "#4682B4", "#00FF7F",
			"#708090", "#FF9800", "#87CEEB", "#C0C0C0", "#A0522D", "#2E8B57",
			"#F4A460", "#FA8072" };

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAllProducts/{sessionId}", method = RequestMethod.GET)
	public @ResponseBody
	SellControllerBean getAllProducts(
			@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {
		SellControllerBean sellControllerBean = new SellControllerBean();
		List<ProductBean> displayProductsBean = new ArrayList<>();
		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
			try {
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
				sellControllerBean.setReceiptConfigurationBean(receiptConfigurationBean);
				Configuration configurationTermsAndContitions = configurationMap.get("TERMS_AND_CONDITIONS");
				if(configurationTermsAndContitions!=null){
					String termsAndContitions = configurationTermsAndContitions.getPropertyValue();
					sellControllerBean.setTermsAndConditions(termsAndContitions);
				}
				DailyRegister dailyRegister = dailyRegisterService.getOpenDailyRegister(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId(),currentUser.getUserId());
				sellControllerBean.setRegisterStatus("false");
				if (dailyRegister != null) {
					sellControllerBean.setDailyRegisterId(dailyRegister
							.getDailyRegisterId().toString());
					sellControllerBean.setRegisterStatus("true");
				}
				sellControllerBean.setDisplayProductsBean(displayProductsBean);
				Configuration configurationAutoCreateSV = configurationMap.get("AUTO_CREATE_STANDARD_VARIANT");
				Configuration configurationDefaultVN = configurationMap.get("DEFAULT_VARIANT_NAME");
				if(configurationAutoCreateSV!=null && configurationAutoCreateSV.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
					sellControllerBean.setAutoCreateStandardVariant(ControllersConstants.TRUE);
					sellControllerBean.setDefaultVariantName(configurationDefaultVN.getPropertyValue().toString());
				}else{
					sellControllerBean.setAutoCreateStandardVariant(ControllersConstants.FALSE);
				}
				return sellControllerBean;
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser,
						"SellController.getAllProducts", "Error Occured "
								+ errors.toString(), true);
				return sellControllerBean;

			}
		}
		else {
			return sellControllerBean;
		}
	}

	@SuppressWarnings({ "unchecked", "null" })
	@RequestMapping(value = "/getAllProductsOnly/{sessionId}", method = RequestMethod.GET)
	public @ResponseBody
	SellControllerBean getAllProductsOnly(
			@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {
		

		SellControllerBean sellControllerBean = new SellControllerBean();
		List<ProductBean> productsBean = new ArrayList<>();
		List<Product> products = new ArrayList<>();
		List<ProductBean> displayProductsBean = new ArrayList<>();
//		List<CustomerBean> customersBeans = new ArrayList<>();
		List<CustomerGroupBean> customerGroupBeansList = new ArrayList<>();
//		byte[] bytes = null;
//		ZipData zipData = new ZipData();
		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			
			List<UserBean> usersBeans = new ArrayList<UserBean>();
			Address address = null;
			try {
				Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
				
				Company company = currentUser.getCompany();
				sellControllerBean.setCompanyName(company.getCompanyName());
				Configuration configurationImage = configurationMap.get("COMPANY_RECEIPT_IMAGE");
				if(configurationImage!=null){
					String companyImagePath = "/app/resources/images/"+configurationImage.getPropertyValue();
					sellControllerBean.setCompanyImagePath(companyImagePath);
				}
				Configuration configurationTermsAndContitions = configurationMap.get("TERMS_AND_CONDITIONS");
				if(configurationTermsAndContitions!=null){
					String termsAndContitions = configurationTermsAndContitions.getPropertyValue();
					sellControllerBean.setTermsAndConditions(termsAndContitions);
				}
				
				List<User> users =  resourceService.getAllUsersByCompanyIdOutletId(company.getCompanyId(),currentUser.getOutlet().getOutletId());
				if(users!=null){
					for(User user : users){
						UserBean userBean = new UserBean();
						userBean.setFirstName(user.getFirstName());
						userBean.setLastName(user.getLastName());
						userBean.setUserId(user.getUserId().toString());
						usersBeans.add(userBean);
					}
				}
				sellControllerBean.setUsers(usersBeans);
				Configuration configuration = configurationMap.get("RETURN_SELL_SCREEN"); 
				if(configuration!=null && configuration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
					sellControllerBean.setIsReturnEnableonSell("true");
				}
				else
				{
					sellControllerBean.setIsReturnEnableonSell("false");
				}
				
				Configuration configurationInvoiceDiscount = configurationMap.get("INVOICE_LEVEL_DISCOUNT");
				//Configuration configurationInvoiceDiscount = configurationService.getConfigurationByPropertyNameByCompanyId("INVOICE_LEVEL_DISCOUNT",currentUser.getCompany().getCompanyId());
				if(configurationInvoiceDiscount!=null && configurationInvoiceDiscount.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
					sellControllerBean.setIsInvoiceLevelDiscountEnable("true");
				}
				else
				{
					sellControllerBean.setIsInvoiceLevelDiscountEnable("false");
				}
				Configuration configurationInvoiceLineLevelDiscount = configurationMap.get("INVOICE_LINE_LEVEL_DISCOUNT");
				//Configuration configurationInvoiceLineLevelDiscount = configurationService.getConfigurationByPropertyNameByCompanyId("INVOICE_LINE_LEVEL_DISCOUNT",currentUser.getCompany().getCompanyId());
				if(configurationInvoiceLineLevelDiscount!=null && configurationInvoiceLineLevelDiscount.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
					sellControllerBean.setIsInvoiceDetailLevelDiscountEnable("true");
				}
				else
				{
					sellControllerBean.setIsInvoiceDetailLevelDiscountEnable("false");
				}
				Configuration configurationInvoiceTax = configurationMap.get("SHOW_INVOICE_LEVEL_TAX");
				//Configuration configurationInvoiceTax = configurationService.getConfigurationByPropertyNameByCompanyId("SHOW_INVOICE_LEVEL_TAX",currentUser.getCompany().getCompanyId());
				if(configurationInvoiceTax !=null && configurationInvoiceTax.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.FALSE)) {
					sellControllerBean.setIsInvoiceTaxAmountEnable("false");
				}
				/*else if(configurationInvoiceTax == null)
				{
					sellControllerBean.setIsInvoiceTaxAmountEnable("true");*/
				else 
				{
					sellControllerBean.setIsInvoiceTaxAmountEnable("true");
				}
				if(currentUser.getOutlet().getAddress()!=null){
					address =  addressService.getAddressByAddressId(currentUser.getOutlet().getAddress().getAddressId(), company.getCompanyId());

				}
				
				if(address!=null){
					sellControllerBean.setCompanyAddress(address.getStreet() + ", " + address.getCity());
					sellControllerBean.setPhoneNumber(address.getPhone());

				}
				DailyRegister dailyRegister = dailyRegisterService.getOpenDailyRegister(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId(),currentUser.getUserId());
				sellControllerBean.setRegisterStatus("false");
				if (dailyRegister != null) {
					sellControllerBean.setDailyRegisterId(dailyRegister
							.getDailyRegisterId().toString());
					sellControllerBean.setRegisterStatus("true");
				}
			sellControllerBean.setDisplayProductsBean(displayProductsBean);
			List<VariantAttributeValues> variantAttributeValues = new ArrayList<VariantAttributeValues>();
				sellControllerBean.setCompanyName(company.getCompanyName());
//				List<ContactsSummmary> customers = contactsSummmaryService.getActiveContactsSummmaryByCompanyId(currentUser.getCompany().getCompanyId());
//			//	Map<String, Address> cutomerAddressMap = addressService.getALLAddressByCustomerId(currentUser.getCompany().getCompanyId());
//				if (customers != null) {
//					for (ContactsSummmary customer : customers) {
//						if(customer.getId().getContactType()!=null && customer.getId().getContactType().contains("CUSTOMER")){
//							CustomerBean customerBean = new CustomerBean();
//							customerBean.setCustomerId(String.valueOf(customer.getId().getContactId()));
//							customerBean.setFirstName(customer.getId().getFirstName());
//							customerBean.setPhoneNumber(customer.getId().getPhone());
//							customerBean.setLastName(customer.getId().getLastName());
//							customersBeans.add(customerBean);
//						}
//					}
//					sellControllerBean.setCustomersBean(customersBeans);
//				}
				variantAttributeValues = variantAttributeValuesService.getAllVariantAttributeValues(company.getCompanyId());
				sellControllerBean.setUsers(usersBeans);
				
				if(currentUser.getOutlet().getAddress()!=null){
					address =  addressService.getAddressByAddressId(currentUser.getOutlet().getAddress().getAddressId(), company.getCompanyId());

				}
				List<ProductVariant> productsProductVariants = new ArrayList<>();
				Map<Integer, List<ProductVariant>> productVariantsMap = new HashMap<>();
				productsProductVariants = productVariantService.getAllProductVariantsByOutletId(currentUser.getOutlet().getOutletId(), company.getCompanyId());
				if(productsProductVariants!=null){
					for(ProductVariant productVariant:productsProductVariants){
						List<ProductVariant> productVariants = productVariantsMap.get(productVariant.getProduct().getProductId());
						if(productVariants!=null){
							productVariants.add(productVariant);
							productVariantsMap.put(productVariant.getProduct().getProductId(), productVariants);
						}else{
							productVariants = new ArrayList<>();
							productVariants.add(productVariant);
							productVariantsMap.put(productVariant.getProduct().getProductId(), productVariants);
						}

					}
				}
				List<SalesTax> salesTaxs = new ArrayList<>();
				Map<Integer, SalesTax> salesTaxMap = new HashMap<>();
				salesTaxs = salesTaxService.GetAllSalesTax(company.getCompanyId());
				if(salesTaxs!=null){
					for(SalesTax salesTax:salesTaxs){
						salesTaxMap.put(salesTax.getSalesTaxId(), salesTax);
					}
				}
				if(address!=null){
					sellControllerBean.setCompanyAddress(address.getStreet() + ", " + address.getCity());
					sellControllerBean.setPhoneNumber(address.getPhone());

				}
				products = productService.getAllProductsByOutletId(currentUser.getOutlet().getOutletId());
				int count = 0;
				if (products != null) {
					for (Product product : products) {
						count = count + 1;

						ProductBean productBean = new ProductBean();
						productBean.setProductUuid(product.getProductUuid());
						BigDecimal supplyPrice = product
								.getSupplyPriceExclTax();

						BigDecimal markupPrct = product.getMarkupPrct();
						SalesTax tax = salesTaxMap.get(product.getSalesTax().getSalesTaxId());
						BigDecimal retailPriceExclusiveTax = supplyPrice.add(
								supplyPrice.multiply(markupPrct).divide(
										new BigDecimal(100))).setScale(2,
												RoundingMode.HALF_EVEN);

						BigDecimal taxAmount = retailPriceExclusiveTax
								.multiply(
										new BigDecimal(tax
												.getSalesTaxPercentage()))
												.divide(new BigDecimal(100))
												.setScale(2, RoundingMode.HALF_EVEN);

						BigDecimal retailPriceInclusiveTax = retailPriceExclusiveTax
								.add(taxAmount);


						productBean.setTaxAmount(taxAmount.toString());						

						productBean
						.setRetailPriceInclTax(retailPriceInclusiveTax
								.toString());
						productBean
						.setRetailPriceExclTax(retailPriceExclusiveTax
								.toString());
						productBean.setProductName(product.getProductName());
						productBean.setSku(product.getSku());
						productBean.setProductDesc(product.getProductDesc());
						productBean.setProductHandler(product
								.getProductHandler());
						productBean.setProductId(product.getProductId()
								.toString());
						productBean.setVarientProducts(product.getVariantProducts());

						productBean.setProductId(product.getProductId().toString());
						
						if(product.getCurrentInventory() != null)
						{
							productBean.setCurrentInventory(product.getCurrentInventory().toString());
						}

						productBean.setOrignalPrice(product.getSupplyPriceExclTax()
								.setScale(2, RoundingMode.HALF_EVEN).toString());
						BigDecimal netPrice = (product.getSupplyPriceExclTax()).multiply(product.getMarkupPrct().divide(new BigDecimal(100))).add(product.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
						BigDecimal newNetPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);

						productBean.setNetPrice(newNetPrice.toString());


						List<ProductVariant> productVarients = productVariantsMap.get(product.getProductId()) ;
						List<ProductVariantBean> productVariantsBeans = new ArrayList<>();
						if(productVarients!=null){
							for (ProductVariant item : productVarients) {
								ProductVariantBean productVarientBean = new ProductVariantBean();
								if(product.getProductDesc()!=null){
									productVarientBean.setProductDesc(product.getProductDesc());
								}
								productVarientBean.setProductUuid(product.getProductUuid());
								productVarientBean.setProductVariantUuid(item.getProductVariantUuid());
								productVarientBean.setSku(item.getSku());
								productVarientBean.setVarientProducts(product.getVariantProducts());
								productVarientBean.setProductVariantId(item
										.getProductVariantId().toString());
								productVarientBean.setVariantAttributeName(item
										.getVariantAttributeName().toString());
								if (item.getCurrentInventory() != null) {
									productVarientBean.setCurrentInventory(item
											.getCurrentInventory().toString());

								}
								productVarientBean.setProductId(product.getProductId()
										.toString());
								productVarientBean.setProductName(product
										.getProductName());
								productVarientBean.setMarkupPrct(item
										.getMarkupPrct().toString());

								BigDecimal productVarientmarkupPrct = item
										.getMarkupPrct();
								SalesTax varientTax = salesTaxMap.get(product.getSalesTax().getSalesTaxId());;

								BigDecimal retailVarPriceExclusiveTax = item
										.getSupplyPriceExclTax()
										.add( item.getSupplyPriceExclTax().multiply(
												productVarientmarkupPrct).divide(
														new BigDecimal(100)))
														.setScale(2, RoundingMode.HALF_EVEN);

								BigDecimal varientTaxAmount = retailPriceExclusiveTax
										.multiply(
												new BigDecimal(varientTax
														.getSalesTaxPercentage()))
														.divide(new BigDecimal(100))
														.setScale(2, RoundingMode.HALF_EVEN);

								productVarientBean
								.setRetailPriceExclTax(retailVarPriceExclusiveTax
										.toString());
								productVarientBean.setTax(varientTaxAmount
										.toString());

								productVarientBean.setOrignalPrice(item.getSupplyPriceExclTax()
										.setScale(2, RoundingMode.HALF_EVEN).toString());

								BigDecimal netPriceVar = (item.getSupplyPriceExclTax()).multiply(item.getMarkupPrct().divide(new BigDecimal(100))).add(item.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
								BigDecimal newNetPriceVar =netPriceVar.setScale(2,RoundingMode.HALF_EVEN);

								productVarientBean.setNetPrice(newNetPriceVar.toString());


								productVariantsBeans.add(productVarientBean);
								productBean.setProductVariantsBeans(productVariantsBeans);

							}
						}

						ProductVaraintDetailBean productVaraintDetailBean = new ProductVaraintDetailBean();
						if(productVarients!=null && productVarients.size()>0){
							if (productVarients.get(0).getVariantAttributeByVariantAttributeAssocicationId1() != null) {
							if (product.getAttribute1() != null) {
								productVaraintDetailBean.setArrtibute1Values(product.getAttribute1().split(","));
								}
							}

							if (productVarients.get(0).getVariantAttributeByVariantAttributeAssocicationId2() != null) {
								if (product.getAttribute2() != null) {
									productVaraintDetailBean.setArrtibute2Values(product.getAttribute2().split(","));
									}
							}
							if (productVarients.get(0).getVariantAttributeByVariantAttributeAssocicationId3() != null) {
								if (product.getAttribute3() != null) {
									productVaraintDetailBean.setArrtibute3Values(product.getAttribute3().split(","));
									}

							}
						}
						productBean
						.setProductVaraintDetailBean(productVaraintDetailBean);

						productsBean.add(productBean);
						if (product.getDisplay() != null
								&& product.getDisplay()) {
							productBean
							.setCss("background: #f6f6f6; margin-left: 5px; border-top: 6px solid "
									+ colorsData[count%70] + ";");
							productBean.setDisplay(product.getDisplay()
									.toString());
							displayProductsBean.add(productBean);
						}

					}
					util.AuditTrail(request, currentUser,
							"SellController.getAllProducts", "User "
									+ currentUser.getUserEmail()
									+ " retrived all products successfully ",
									false);
					sellControllerBean.setProductsBean(productsBean);

					sellControllerBean.setPriceBookBean(GetallValidPricebooks(currentUser, 0));

				} else {
					util.AuditTrail(request, currentUser,
							"SellController.getAllProducts",
							" products are not found requested by User "
									+ currentUser.getUserEmail(), false);
				}
				sellControllerBean.setDisplayProductsBean(displayProductsBean);
				Response response= getAllCustomerGroups(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					customerGroupBeansList = (List<CustomerGroupBean> ) response.data;
				}
				sellControllerBean.setCustomerGroupBeansList(customerGroupBeansList);
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				GZIPOutputStream gzipOut = new GZIPOutputStream(baos);
//				ObjectOutputStream objectOut = new ObjectOutputStream(gzipOut);
//				objectOut.writeObject(sellControllerBean);
//				objectOut.close();
//				 bytes = baos.toByteArray();
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				new ObjectOutputStream( baos ).writeObject( sellControllerBean );
//				byte[] apacheBytes =  org.apache.commons.codec.binary.Base64.encodeBase64(baos.toByteArray());
//			    String fromApacheBytes = new String(apacheBytes);
//				zipData.setData(new String(bytes, "UTF-16"));
//			    zipData.setData(fromApacheBytes);
				Configuration configurationAutoCreateSV = configurationMap.get("AUTO_CREATE_STANDARD_VARIANT");
				Configuration configurationDefaultVN = configurationMap.get("DEFAULT_VARIANT_NAME");
				if(configurationAutoCreateSV!=null && configurationAutoCreateSV.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
					sellControllerBean.setAutoCreateStandardVariant(ControllersConstants.TRUE);
					sellControllerBean.setDefaultVariantName(configurationDefaultVN.getPropertyValue().toString());
				}else{
					sellControllerBean.setAutoCreateStandardVariant(ControllersConstants.FALSE);
				}
				return sellControllerBean;
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser,
						"SellController.getAllProducts", "Error Occured "
								+ errors.toString(), true);
				return sellControllerBean;

			}
		}

		else {

			return sellControllerBean;
		}
	
		
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAllProductsPrintLableOnly/{sessionId}", method = RequestMethod.GET)
	public @ResponseBody
	SellControllerBean getAllProductsPrintLableOnly(
			@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {
		

		SellControllerBean sellControllerBean = new SellControllerBean();
		List<ProductBean> productsBean = new ArrayList<>();
		List<Product> products = new ArrayList<>();
		List<ProductBean> displayProductsBean = new ArrayList<>();
		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			
			try {
				
				Company company = currentUser.getCompany();
				sellControllerBean.setCompanyName(company.getCompanyName());
				
				
			sellControllerBean.setDisplayProductsBean(displayProductsBean);
			sellControllerBean.setCompanyName(company.getCompanyName());
				
				List<ProductVariant> productsProductVariants = new ArrayList<>();
				Map<Integer, List<ProductVariant>> productVariantsMap = new HashMap<>();
				productsProductVariants = productVariantService.getAllProductVariantsByOutletId(currentUser.getOutlet().getOutletId(), company.getCompanyId());
				if(productsProductVariants!=null){
					for(ProductVariant productVariant:productsProductVariants){
						List<ProductVariant> productVariants = productVariantsMap.get(productVariant.getProduct().getProductId());
						if(productVariants!=null){
							productVariants.add(productVariant);
							productVariantsMap.put(productVariant.getProduct().getProductId(), productVariants);
						}else{
							productVariants = new ArrayList<>();
							productVariants.add(productVariant);
							productVariantsMap.put(productVariant.getProduct().getProductId(), productVariants);
						}

					}
				}
				List<SalesTax> salesTaxs = new ArrayList<>();
				Map<Integer, SalesTax> salesTaxMap = new HashMap<>();
				salesTaxs = salesTaxService.GetAllSalesTax(company.getCompanyId());
				if(salesTaxs!=null){
					for(SalesTax salesTax:salesTaxs){
						salesTaxMap.put(salesTax.getSalesTaxId(), salesTax);
					}
				}
				
				products = productService.getAllProductsByOutletId(currentUser.getOutlet().getOutletId());
				int count = 0;
				if (products != null) {
					for (Product product : products) {
						count = count + 1;

						ProductBean productBean = new ProductBean();
						productBean.setProductUuid(product.getProductUuid());
						BigDecimal supplyPrice = product
								.getSupplyPriceExclTax();

						BigDecimal markupPrct = product.getMarkupPrct();
						SalesTax tax = salesTaxMap.get(product.getSalesTax().getSalesTaxId());
						BigDecimal retailPriceExclusiveTax = supplyPrice.add(
								supplyPrice.multiply(markupPrct).divide(
										new BigDecimal(100))).setScale(2,
												RoundingMode.HALF_EVEN);

						BigDecimal taxAmount = retailPriceExclusiveTax
								.multiply(
										new BigDecimal(tax
												.getSalesTaxPercentage()))
												.divide(new BigDecimal(100))
												.setScale(2, RoundingMode.HALF_EVEN);

						BigDecimal retailPriceInclusiveTax = retailPriceExclusiveTax
								.add(taxAmount);


						productBean.setTaxAmount(taxAmount.toString());						

						productBean
						.setRetailPriceInclTax(retailPriceInclusiveTax
								.toString());
						productBean
						.setRetailPriceExclTax(retailPriceExclusiveTax
								.toString());
						productBean.setProductName(product.getProductName());
						productBean.setSku(product.getSku());
						productBean.setProductDesc(product.getProductDesc());
						productBean.setProductHandler(product
								.getProductHandler());
						productBean.setProductId(product.getProductId()
								.toString());
						productBean.setVarientProducts(product.getVariantProducts());

						productBean.setProductId(product.getProductId().toString());
						
						if(product.getCurrentInventory() != null)
						{
							productBean.setCurrentInventory(product.getCurrentInventory().toString());
						}

						productBean.setOrignalPrice(product.getSupplyPriceExclTax()
								.setScale(2, RoundingMode.HALF_EVEN).toString());
						BigDecimal netPrice = (product.getSupplyPriceExclTax()).multiply(product.getMarkupPrct().divide(new BigDecimal(100))).add(product.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
						BigDecimal newNetPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);

						productBean.setNetPrice(newNetPrice.toString());


						List<ProductVariant> productVarients = productVariantsMap.get(product.getProductId()) ;
						List<ProductVariantBean> productVariantsBeans = new ArrayList<>();
						if(productVarients!=null){
							for (ProductVariant item : productVarients) {
								ProductVariantBean productVarientBean = new ProductVariantBean();
								if(product.getProductDesc()!=null){
									productVarientBean.setProductDesc(product.getProductDesc());
								}
								productVarientBean.setProductUuid(product.getProductUuid());
								productVarientBean.setProductVariantUuid(item.getProductVariantUuid());
								productVarientBean.setSku(item.getSku());
								productVarientBean.setVarientProducts(product.getVariantProducts());
								productVarientBean.setProductVariantId(item
										.getProductVariantId().toString());
								productVarientBean.setVariantAttributeName(product.getProductName()+"/"+item
										.getVariantAttributeName().toString());
								if (item.getCurrentInventory() != null) {
									productVarientBean.setCurrentInventory(item
											.getCurrentInventory().toString());

								}
								productVarientBean.setProductId(product.getProductId()
										.toString());
								productVarientBean.setProductName(product
										.getProductName());
								productVarientBean.setMarkupPrct(item
										.getMarkupPrct().toString());

								BigDecimal productVarientmarkupPrct = item
										.getMarkupPrct();
								SalesTax varientTax = salesTaxMap.get(product.getSalesTax().getSalesTaxId());;

								BigDecimal retailVarPriceExclusiveTax = item
										.getSupplyPriceExclTax()
										.add( item.getSupplyPriceExclTax().multiply(
												productVarientmarkupPrct).divide(
														new BigDecimal(100)))
														.setScale(2, RoundingMode.HALF_EVEN);

								BigDecimal varientTaxAmount = retailPriceExclusiveTax
										.multiply(
												new BigDecimal(varientTax
														.getSalesTaxPercentage()))
														.divide(new BigDecimal(100))
														.setScale(2, RoundingMode.HALF_EVEN);

								productVarientBean
								.setRetailPriceExclTax(retailVarPriceExclusiveTax
										.toString());
								productVarientBean.setTax(varientTaxAmount
										.toString());

								productVarientBean.setOrignalPrice(item.getSupplyPriceExclTax()
										.setScale(2, RoundingMode.HALF_EVEN).toString());

								BigDecimal netPriceVar = (item.getSupplyPriceExclTax()).multiply(item.getMarkupPrct().divide(new BigDecimal(100))).add(item.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
								BigDecimal newNetPriceVar =netPriceVar.setScale(2,RoundingMode.HALF_EVEN);

								productVarientBean.setNetPrice(newNetPriceVar.toString());


								productVariantsBeans.add(productVarientBean);
								productBean.setProductVariantsBeans(productVariantsBeans);

							}
						}

						ProductVaraintDetailBean productVaraintDetailBean = new ProductVaraintDetailBean();
						if(productVarients!=null && productVarients.size()>0){
							if (productVarients.get(0).getVariantAttributeByVariantAttributeAssocicationId1() != null) {
							if (product.getAttribute1() != null) {
								productVaraintDetailBean.setArrtibute1Values(product.getAttribute1().split(","));
								}
							}

							if (productVarients.get(0).getVariantAttributeByVariantAttributeAssocicationId2() != null) {
								if (product.getAttribute2() != null) {
									productVaraintDetailBean.setArrtibute2Values(product.getAttribute2().split(","));
									}
							}
							if (productVarients.get(0).getVariantAttributeByVariantAttributeAssocicationId3() != null) {
								if (product.getAttribute3() != null) {
									productVaraintDetailBean.setArrtibute3Values(product.getAttribute3().split(","));
									}

							}
						}
						productBean
						.setProductVaraintDetailBean(productVaraintDetailBean);

						productsBean.add(productBean);
						

					}
					util.AuditTrail(request, currentUser,
							"SellController.getAllProducts", "User "
									+ currentUser.getUserEmail()
									+ " retrived all products successfully ",
									false);
					sellControllerBean.setProductsBean(productsBean);
				} else {
					util.AuditTrail(request, currentUser,
							"SellController.getAllProducts",
							" products are not found requested by User "
									+ currentUser.getUserEmail(), false);
				}
				sellControllerBean.setDisplayProductsBean(displayProductsBean);
				
				
				return sellControllerBean;
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser,
						"SellController.getAllProducts", "Error Occured "
								+ errors.toString(), true);
				return sellControllerBean;

			}
		}

		else {

			return sellControllerBean;
		}
	
		
		
	}
	
	public List<String> findUniqeVariant(List<VariantAttributeValues> VariantAttributeValues, int attributeId, String productVariantId){
		
		List<String> strings = new ArrayList<String>();
		for(VariantAttributeValues attributeValues : VariantAttributeValues){
			if(attributeValues.getVariantAttribute().getVariantAttributeId() == attributeId && attributeValues.getProductUuid().equals(productVariantId)){
				strings.add(attributeValues.getAttributeValue());
			}
		}
		Set<String> hs = new HashSet<>();
		hs.addAll(strings);
		strings.clear();
		strings.addAll(hs);
		return strings;
	}
	
	String getMonthForInt(int num) {
		String month = "wrong";
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		if (num >= 0 && num <= 11) {
			month = months[num];
		}
		return month;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/synchsales/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response synchsales(@PathVariable("sessionId") String sessionId,
			@RequestBody List<InvoiceMainBean> listInvoiceMainBean,
			HttpServletRequest request) {

		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {

				if(listInvoiceMainBean != null && listInvoiceMainBean.size() > 0)
				{
					for (InvoiceMainBean invoiceMainBean : listInvoiceMainBean) {
						if(invoiceMainBean != null)
						{
							payCash(sessionId, invoiceMainBean, request);
							invoiceMainBean.setSynchedInd("true");
						}
					}

				}

				return new Response(listInvoiceMainBean, StatusConstants.SUCCESS,
						LayOutPageConstants.SELL);
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SellController.payCash","Error Occured " + errors.toString(), true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		} else {
			return new Response(MessageConstants.INVALID_SESSION,
					StatusConstants.INVALID, LayOutPageConstants.LOGIN);
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/payCash/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response payCash(@PathVariable("sessionId") String sessionId,
			@RequestBody InvoiceMainBean invoiceMainBean,
			HttpServletRequest request) {

		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {
				int statusId = 10; // completed
				DailyRegister dailyRegister = null;
				BigDecimal currentSettlementAmount = new BigDecimal(0);

				BigDecimal previousSettlementAmount = new BigDecimal("0");

				InvoiceMain invoice = new InvoiceMain();

				if (invoiceMainBean != null) {

					if(invoiceMainBean.getSettledAmt() != null){
						currentSettlementAmount = new BigDecimal(
								invoiceMainBean.getSettledAmt());
					}

					dailyRegister = dailyRegisterService
							.getDailyRegisterById(Integer
									.parseInt(invoiceMainBean
											.getDailyRegesterId()),currentUser.getCompany().getCompanyId());

					int paymentTypeId = Integer.parseInt(invoiceMainBean
							.getPaymentTypeId());
					if (invoiceMainBean.getInvoiceMainId() == null || invoiceMainBean.getReturnvalue().equalsIgnoreCase(("returnsale"))) {

						invoice = PopulateInvoiceMain(currentUser.getCompany().getCompanyId(), paymentTypeId,
								statusId, currentUser.getOutlet().getOutletId(), invoiceMainBean,
								currentUser);
						invoice.setDailyRegister(dailyRegister);
						invoice.setCompany(currentUser.getCompany());

						if(invoiceMainBean.getSalesUser()!=null && !invoiceMainBean.getSalesUser().equals("")){
							invoice.setSalesUser(Integer.parseInt(invoiceMainBean.getSalesUser()));
						}else{
							invoice.setSalesUser(currentUser.getUserId());
						}

						invoice = saleService.addInvoiceMain(invoice,currentUser.getCompany().getCompanyId());

						invoiceMainBean.setInvoiceMainId(invoice.getInvoiceMainId().toString());

						List<InvoiceDetail> invoiceDetails = PopulateInviceDetail(invoiceMainBean, invoice, currentUser);


						invoiceDetails = saleService.addInvoiceDetails(invoiceDetails,currentUser.getCompany().getCompanyId());

						UpdateProductInventory(invoiceMainBean.getInvoiceDetails(),currentUser,invoiceMainBean.getReturnvalue(),invoice);
						for (InvoiceDetail invoiceDetail : invoiceDetails) {

							for (InvoiceDetailBean invoiceDetailBean : invoiceMainBean.getInvoiceDetails()) {


								if(invoiceDetail.getProductVariant() != null && invoiceDetail.getProductVariant().getProductVariantId().toString().equals(invoiceDetailBean.getProductVarientId()))
								{
									invoiceDetailBean.setInvoiceDetailId(invoiceDetail.getInvoiceDetailId().toString());
								}

								if(invoiceDetail.getProduct() != null)	
								{
									invoiceDetailBean.setInvoiceDetailId(invoiceDetail.getInvoiceDetailId().toString());

								}

							}
						}

						//For direct payments payments 
						if(invoice.getContact() != null && !invoiceMainBean.getReturnvalue().equalsIgnoreCase(("returnsale")))
						{

							AddCustomerPayments(invoice.getContact().getContactId(), currentSettlementAmount, currentUser.getCompany(), invoice);
						}

					} else // updating previously created invoice

					{
						invoice = saleService.getInvoiceMainByID(Integer
								.parseInt(invoiceMainBean.getInvoiceMainId()),currentUser.getCompany().getCompanyId());

						invoice.setInvoiceNotes(invoiceMainBean.getInvoiceNotes());
						invoice.setInvoiceNetAmt(new BigDecimal(invoiceMainBean.getInvoiceNetAmt()));
						invoice.setInvoiceAmt(new BigDecimal(invoiceMainBean.getInvoiceAmt()));
						invoice.setInvoiceGivenAmt(new BigDecimal(invoiceMainBean.getInvoiceGivenAmt()));
						if (invoiceMainBean.getInvoiceDiscount() != null && !invoiceMainBean.getInvoiceDiscount().equals("")) {
							invoice.setInvoiceDiscount(new BigDecimal(invoiceMainBean.getInvoiceDiscount()));
						}

						if(invoiceMainBean.getOrignalPrice()!=null && !invoiceMainBean.getOrignalPrice().equals("")){
							invoice.setInvoiceOrignalAmt(new BigDecimal(invoiceMainBean.getOrignalPrice()));

						}

						invoice.setInvoiceDiscountAmt(new BigDecimal(invoiceMainBean.getInvoiceDiscountAmt()));
						invoice.setInvoiceTax(new BigDecimal(invoiceMainBean.getInvoiceTax()));
						invoice.setInvoiceGenerationDte(Calendar.getInstance().getTime());
						invoice.setActiveIndicator(true);


						if (invoiceMainBean.getCustomerId() != null && !invoiceMainBean.getCustomerId().equals("")) {
							Contact customer = customerService.getContactByID(Integer
									.parseInt(invoiceMainBean.getCustomerId()),currentUser.getCompany().getCompanyId());
							invoice.setContact(customer);
						}

						PaymentType paymentType = paymentTypeService
								.getPaymentTypeByPaymentTypeId(paymentTypeId,currentUser.getCompany().getCompanyId());
						invoice.setPaymentType(paymentType);

						invoice.setUpdatedBy(currentUser.getUserId());
						if(invoiceMainBean.getInsertedby() != null)
						{
							invoice.setCreatedBy(Integer.parseInt(invoiceMainBean.getInsertedby()));
						}
						else
						{
							invoice.setCreatedBy(currentUser.getUserId());
						}


						previousSettlementAmount = invoice.getSettledAmt();
						invoice.setSettledAmt(currentSettlementAmount
								.add(previousSettlementAmount));



						if(invoiceMainBean.getCustomerId() != null && StringUtils.isNotEmpty(invoiceMainBean.getCustomerId()))
						{
							AddCustomerPayments(invoice.getContact().getContactId(), currentSettlementAmount, currentUser.getCompany(), invoice);

						}
						if (invoice.getStatus().getStatusId() == 9 && invoiceMainBean.getCustomerId() != null && StringUtils.isNotEmpty(invoiceMainBean.getCustomerId())) {
							statusId = invoice.getStatus().getStatusId();
							UpdateCustomerBalance(Integer.parseInt( invoiceMainBean.getCustomerId()), currentSettlementAmount,Actions.BALANCE_REMOVE, currentUser.getCompany().getCompanyId() );					
						}

						if(invoice.getStatus().getStatusId() == 9 &&   invoice.getInvoiceNetAmt().subtract(invoice.getSettledAmt()).compareTo(BigDecimal.ZERO)==0)
						{
							statusId = 13;
						}



						invoice.setStatus(statusService.getStatusByStatusId(statusId));


						List<InvoiceDetail> invoiceDetails = PopulateInviceDetail(invoiceMainBean, invoice, currentUser);


						invoiceDetails = saleService.addInvoiceDetails(invoiceDetails,currentUser.getCompany().getCompanyId());
						saleService.updateInvoiceMainByID(invoice,currentUser.getCompany().getCompanyId());
					//	UpdateProductInventory(invoiceMainBean.getInvoiceDetails(),currentUser,invoiceMainBean.getReturnvalue(),invoice);


					}

					if(!invoiceMainBean.getReturnvalue().equalsIgnoreCase(("returnsale")))
					{
						Receipt receipt = PopulateReceipt(invoiceMainBean, invoice,currentUser, statusId);
						receipt.setDailyRegister(dailyRegister);
						receipt.setCompany(currentUser.getCompany());
						receiptService.addReceipt(receipt,currentUser.getCompany().getCompanyId());
					}
					
					/*if(invoiceMainBean.getReturnvalue().equalsIgnoreCase(("returnsale")) || 
							( invoiceMainBean.getInvcTypeCde() != null  && invoiceMainBean.getInvcTypeCde().equalsIgnoreCase(("00000")) )
							)
                     {
						BigDecimal bigDecimal = new BigDecimal("-1");
						CashManagment cashManagment = new CashManagment();
						cashManagment.setActiveIndicator(true);
						//cashManagment.setCashAmt(invoice.getInvoiceNetAmt().multiply(bigDecimal));
						cashManagment.setCashAmt(invoice.getInvoiceNetAmt());
						cashManagment.setCashManagmentNotes("Cash Return");
						cashManagment.setCashManagmentType("OUT");
						cashManagment.setCompany(currentUser.getCompany());
						cashManagment.setCreatedBy(currentUser.getCreatedBy());
						cashManagment.setCreatedDate(new Date());
						cashManagment.setDailyRegister(dailyRegister);
						cashManagment.setOutlet(currentUser.getOutlet());
						ExpenseType expenseType = expenseTypeService.getExpenseTypeByExpenseTypeId(2);
						cashManagment.setExpenseType(expenseType);
						
						cashManagmentService.addCashManagment(cashManagment, currentUser.getCompany().getCompanyId());
					}*/
					invoiceMainBean.setSettledAmt(currentSettlementAmount.add(
							previousSettlementAmount).toString());
					invoiceMainBean.setStatus(invoice.getStatus().getStatusId().toString());

					util.AuditTrail(request, currentUser, "SellController.payCash",
							"User " + currentUser.getUserEmail() + " Pay Cash "
									+ invoiceMainBean.getInvoiceRefNbr()
									+ " successfully ", false);

				}


				return new Response(invoiceMainBean, StatusConstants.SUCCESS,
						LayOutPageConstants.SELL);

			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SellController.payCash","Error Occured " + errors.toString(), true);
				return new Response(MessageConstants.INVALID_REQUEST,StatusConstants.WARNING,LayOutPageConstants.SELL);
			}
		} else {
			return new Response(MessageConstants.INVALID_SESSION,
					StatusConstants.INVALID, LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/salenoncash/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response salenoncash(@PathVariable("sessionId") String sessionId,
			@RequestBody InvoiceMainBean invoiceMainBean,
			HttpServletRequest request) {
		DailyRegister dailyRegister = null;

		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			InvoiceMain alreadyLayByInvoice = null;
			try {
				// in user

				int paymentTypeId = 1;// Cash
				int statusId = 0;
				if (invoiceMainBean.getTransactionType().equals("layby")) {
					statusId = 9; // LayBy

				}

				else if (invoiceMainBean.getTransactionType().equals(
						"onaccount")) {

					statusId = 12; // OnAccount
				}
				if (invoiceMainBean != null) {

					dailyRegister = dailyRegisterService
							.getDailyRegisterById(Integer
									.parseInt(invoiceMainBean
											.getDailyRegesterId()),currentUser.getCompany().getCompanyId());
					if (invoiceMainBean.getInvoiceMainId() != null) {
						alreadyLayByInvoice = saleService
								.getInvoiceMainByID(Integer
										.parseInt(invoiceMainBean
												.getInvoiceMainId()),currentUser.getCompany().getCompanyId());
					}

					if (alreadyLayByInvoice == null) {
						InvoiceMain invoice = PopulateInvoiceMain(currentUser.getCompany().getCompanyId(),
								paymentTypeId, statusId, currentUser.getOutlet().getOutletId(),
								invoiceMainBean, currentUser);
						invoice.setDailyRegister(dailyRegister);
						if(invoiceMainBean.getOrignalPrice()!=null){
							invoice.setInvoiceOrignalAmt(new BigDecimal(invoiceMainBean.getOrignalPrice()));
						}
						invoice.setSettledAmt(new BigDecimal(0));
						invoice.setInvoiceGivenAmt(new BigDecimal(0));
						invoice.setCompany(currentUser.getCompany());
						if(invoiceMainBean.getSalesUser()!=null && !invoiceMainBean.getSalesUser().equals("")){
							invoice.setSalesUser(Integer.parseInt(invoiceMainBean.getSalesUser()));
						}else{
							invoice.setSalesUser(currentUser.getUserId());
						}
						invoice = saleService.addInvoiceMain(invoice,currentUser.getCompany().getCompanyId());
						Date date = new Date();
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						int year = cal.get(Calendar.YEAR);
						String month = getMonthForInt(cal.get(Calendar.MONTH));
						int day = cal.get(Calendar.DAY_OF_MONTH);
						SimpleDateFormat sdf = new SimpleDateFormat("K:mm a");
						String formattedTime = sdf.format(cal.getTime());

						invoiceMainBean.setInvoiceGenerationDte(month + " "+ String.valueOf(day) + "" + ", "+ String.valueOf(year) +" " + formattedTime);

						List<InvoiceDetail> invoiceDetails = PopulateInviceDetail(
								invoiceMainBean, invoice, currentUser);

						saleService.addInvoiceDetails(invoiceDetails,currentUser.getCompany().getCompanyId());

						UpdateProductInventory(invoiceMainBean
								.getInvoiceDetails(),currentUser,invoiceMainBean.getReturnvalue(),invoice);
						invoiceMainBean.setInvoiceMainId(invoice
								.getInvoiceMainId().toString() );

						if (invoiceMainBean.getCustomerId() != null && StringUtils.isNotEmpty(invoiceMainBean.getCustomerId()) ) {

							UpdateCustomerBalance(Integer.parseInt(invoiceMainBean.getCustomerId()), new BigDecimal(invoiceMainBean.getLaybyamount()),Actions.BALANCE_ADD, currentUser.getCompany().getCompanyId() );

						}

						// Receipt receipt = PopulateReceipt(invoiceMainBean,
						// invoice,
						// currentUser, statusId);
						// receiptService.addReceipt(receipt);

					}

					else // update previously created invoice
					{

						for (InvoiceDetailBean item : invoiceMainBean.getInvoiceDetails()) {

							if(item.getInvoiceDetailId() == null)
							{
								InvoiceDetail invoiceDetail = new InvoiceDetail();

								invoiceDetail.setItemDiscountPrct(new BigDecimal(item.getItemDiscountPrct()));
								invoiceDetail.setItemNotes(item.getItemNotes());
								invoiceDetail.setItemRetailPrice(new BigDecimal(item.getItemRetailPrice()));
								invoiceDetail.setItemSalePrice(new BigDecimal(item.getItemSalePrice()));
								invoiceDetail.setItemTaxAmount(new BigDecimal(item.getItemTaxAmount()));
								invoiceDetail.setProductQuantity(Integer.parseInt(item.getProductQuantity()));
								if(item.getProductVarientId() != null)
								{
									ProductVariant productVariant = productVariantService.getProductVariantByProductVariantId(Integer.parseInt( item.getProductVarientId()),currentUser.getCompany().getCompanyId());
									invoiceDetail.setProductVariant(productVariant);
								}
								if(item.getProductId() != null)
								{
									Product product = productService.getProductByProductId(Integer.parseInt( item.getProductId()),currentUser.getCompany().getCompanyId());
									invoiceDetail.setProduct(product);
								}

								invoiceDetail.setInvoiceMain(alreadyLayByInvoice);			
								invoiceDetail.setCreatedBy(currentUser.getCreatedBy());
								invoiceDetail.setUpdatedBy(currentUser.getUpdatedBy());
								invoiceDetail.setCompany(currentUser.getCompany());
								if(invoiceMainBean.getOrignalPrice()!=null){
									invoiceDetail.setItemOrignalAmt(new BigDecimal(item.getOrignalPrice()));
								}
								invoiceDetail.setOutlet(currentUser.getOutlet());
								saleService.addInvoiceDetail(invoiceDetail,currentUser.getCompany().getCompanyId());

							}

						}
						alreadyLayByInvoice.setUpdatedBy(currentUser
								.getUserId());
						alreadyLayByInvoice.setLastUpdated(Calendar
								.getInstance().getTime());
						alreadyLayByInvoice.setStatus(statusService
								.getStatusByStatusId(statusId));

						alreadyLayByInvoice.setInvoiceAmt(new BigDecimal(invoiceMainBean.getInvoiceAmt()));
						if(invoiceMainBean.getInvoiceDiscount() != null){
							alreadyLayByInvoice.setInvoiceDiscount(new BigDecimal(invoiceMainBean.getInvoiceDiscount()));}

						if(invoiceMainBean.getInvoiceDiscountAmt() != null)
						{
							alreadyLayByInvoice.setInvoiceDiscountAmt(new BigDecimal(invoiceMainBean.getInvoiceDiscountAmt()));
						}
						if(invoiceMainBean.getInvoiceGivenAmt() != null)
						{
							alreadyLayByInvoice.setInvoiceGivenAmt(new BigDecimal(invoiceMainBean.getInvoiceGivenAmt()));
						}
						if(invoiceMainBean.getInvoiceNetAmt() != null)
						{
							alreadyLayByInvoice.setInvoiceNetAmt(new BigDecimal(invoiceMainBean.getInvoiceNetAmt()));

						}
						alreadyLayByInvoice.setInvoiceNotes(invoiceMainBean.getInvoiceNotes());
						if(invoiceMainBean.getInvoiceTax() != null)
						{
							alreadyLayByInvoice.setInvoiceTax(new BigDecimal(invoiceMainBean.getInvoiceTax()));
						}



						if (alreadyLayByInvoice.getSettledAmt() == null) {
							alreadyLayByInvoice
							.setSettledAmt(new BigDecimal(0));
						}
						alreadyLayByInvoice.setSettledAmt(alreadyLayByInvoice
								.getSettledAmt().add(
										new BigDecimal(invoiceMainBean
												.getSettledAmt())));

						if (invoiceMainBean.getCustomerId() != null && StringUtils.isNotEmpty(invoiceMainBean.getCustomerId())) {
							Contact customer = customerService.getContactByID(Integer
									.parseInt(invoiceMainBean.getCustomerId()),currentUser.getCompany().getCompanyId());
							alreadyLayByInvoice.setContact(customer);
						}

						saleService.updateInvoiceMainByID(alreadyLayByInvoice,currentUser.getCompany().getCompanyId());

						if (invoiceMainBean.getCustomerId() != null && StringUtils.isNotEmpty(invoiceMainBean.getCustomerId()) && invoiceMainBean.getStatus().equals("10")) {

							UpdateCustomerBalance(Integer.parseInt(invoiceMainBean.getCustomerId()), new BigDecimal(invoiceMainBean.getLaybyamount()),Actions.BALANCE_ADD, currentUser.getCompany().getCompanyId() );

						}

						//						Receipt receipt = PopulateReceipt(invoiceMainBean,
						//								alreadyLayByInvoice, currentUser, statusId);
						//						receipt.setDailyRegister(dailyRegister);
						//						receiptService.addReceipt(receipt);
						//						invoiceMainBean.setSettledAmt(alreadyLayByInvoice
						//								.getSettledAmt().toString());
					}



					util.AuditTrail(request, currentUser,
							"SellController.salenoncash",
							"User " + currentUser.getUserEmail() + " Sale Non Cash "
									+ invoiceMainBean.getInvoiceRefNbr()
									+ " successfully ", false);
				}

				return new Response(invoiceMainBean, StatusConstants.SUCCESS,
						LayOutPageConstants.SELL);

			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SellController.payCash",
						"Error Occured " + errors.toString(), true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.ADD_RESTRICTED,
						LayOutPageConstants.STAY_ON_PAGE);
			}
		} else {
			return new Response(MessageConstants.INVALID_SESSION,
					StatusConstants.INVALID, LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/openRegister/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response openRegister(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {
				DailyRegister dailyRegister = new DailyRegister();
				dailyRegister.setActiveIndicator(true);
				dailyRegister.setCreatedDate(new Date());
				dailyRegister.setStatus(statusService.getStatusByStatusId(7));
				dailyRegister.setOutlet(currentUser.getOutlet());
				dailyRegister.setCompany(companyService
						.getCompanyDetailsByCompanyID(currentUser.getCompany().getCompanyId()));
				dailyRegister.setRegister(registerService
						.getRegestersByOutletId(currentUser.getOutlet().getOutletId(),currentUser.getCompany().getCompanyId(),currentUser.getUserId()).get(0));
				dailyRegister.setCompany(currentUser.getCompany());
				dailyRegister.setCreatedBy(currentUser.getUserId());
				dailyRegister = dailyRegisterService.addDailyRegister(dailyRegister,currentUser.getCompany().getCompanyId());

				Register register = registerService.getRegisterByRegisterId(dailyRegister.getRegister().getRegisterId(), currentUser.getCompany().getCompanyId());
				util.AuditTrail(
						request,
						currentUser,
						"dailyRegisterService.openRegister",
						"User " + currentUser.getUserEmail()
						+ " Register Open id "
						+ dailyRegister.getDailyRegisterId()
						+ " successfully ", false);

				if(register.getCacheManagementEnable().equals("No")){
					return new Response(MessageConstants.ACCESS_DENIED,
							StatusConstants.SUCCESS, LayOutPageConstants.SELL);
				}else{
					return new Response(MessageConstants.REQUREST_PROCESSED,
							StatusConstants.SUCCESS, LayOutPageConstants.SELL);
				}


			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SellController.payCash",
						"Error Occured " + errors.toString(), true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.ADD_RESTRICTED,
						LayOutPageConstants.STAY_ON_PAGE);
			}
		} else {
			return new Response(MessageConstants.INVALID_SESSION,
					StatusConstants.INVALID, LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/parkSale/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response parkSale(@PathVariable("sessionId") String sessionId,
			@RequestBody InvoiceMainBean invoiceMainBean,
			HttpServletRequest request) {

		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {
				// in user
				int paymentTypeId = 1;// Cash
				int statusId = 11; // parked
				InvoiceMain alreadyParkedInvoice = null;
				if (invoiceMainBean != null) {

					if (invoiceMainBean.getInvoiceMainId() != null) {
						alreadyParkedInvoice = saleService
								.getInvoiceMainByID(Integer
										.parseInt(invoiceMainBean
												.getInvoiceMainId()),currentUser.getCompany().getCompanyId());
					}
					if (alreadyParkedInvoice == null) {
						InvoiceMain invoice = PopulateInvoiceMain(currentUser.getCompany().getCompanyId(),
								paymentTypeId, statusId, currentUser.getOutlet().getOutletId(),
								invoiceMainBean, currentUser);
						invoice.setSettledAmt(new BigDecimal(0));
						invoice.setInvoiceGivenAmt(new BigDecimal(0));
						invoice.setDailyRegister(dailyRegisterService
								.getDailyRegisterById(Integer
										.parseInt(invoiceMainBean
												.getDailyRegesterId()),currentUser.getCompany().getCompanyId()));
						invoice.setCompany(currentUser.getCompany());
						invoice.setInvoiceOrignalAmt(new BigDecimal(invoiceMainBean.getOrignalPrice()));
						if(invoiceMainBean.getSalesUser()!=null && !invoiceMainBean.getSalesUser().equals("")){
							invoice.setSalesUser(Integer.parseInt(invoiceMainBean.getSalesUser()));
						}else{
							invoice.setSalesUser(currentUser.getUserId());
						}
						invoice = saleService.addInvoiceMain(invoice,currentUser.getCompany().getCompanyId());

						List<InvoiceDetail> invoiceDetails = PopulateInviceDetail(
								invoiceMainBean, invoice, currentUser);

						saleService.addInvoiceDetails(invoiceDetails,currentUser.getCompany().getCompanyId());

						// Receipt receipt = PopulateReceipt(invoiceMainBean,
						// invoice,currentUser);
						// receiptService.addReceipt(receipt);

					} else // update previously parked invoice
					{


						alreadyParkedInvoice.setInvoiceNotes(invoiceMainBean.getInvoiceNotes());
						alreadyParkedInvoice.setInvoiceNetAmt(new BigDecimal(invoiceMainBean.getInvoiceNetAmt()));
						alreadyParkedInvoice.setInvoiceAmt(new BigDecimal(invoiceMainBean.getInvoiceAmt()));
						alreadyParkedInvoice.setInvoiceGivenAmt(new BigDecimal(invoiceMainBean.getInvoiceGivenAmt()));
						if (invoiceMainBean.getInvoiceDiscount() != null && !invoiceMainBean.getInvoiceDiscount().equals("")) {
							alreadyParkedInvoice.setInvoiceDiscount(new BigDecimal(invoiceMainBean.getInvoiceDiscount()));
						}

						if(invoiceMainBean.getOrignalPrice()!=null && !invoiceMainBean.getOrignalPrice().equals("")){
							alreadyParkedInvoice.setInvoiceOrignalAmt(new BigDecimal(invoiceMainBean.getOrignalPrice()));

						}

						alreadyParkedInvoice.setInvoiceDiscountAmt(new BigDecimal(invoiceMainBean.getInvoiceDiscountAmt()));
						alreadyParkedInvoice.setInvoiceTax(new BigDecimal(invoiceMainBean.getInvoiceTax()));
						alreadyParkedInvoice.setInvoiceGenerationDte(Calendar.getInstance().getTime());
						alreadyParkedInvoice.setActiveIndicator(true);


						if (invoiceMainBean.getCustomerId() != null && !invoiceMainBean.getCustomerId().equals("")) {
							Contact customer = customerService.getContactByID(Integer
									.parseInt(invoiceMainBean.getCustomerId()),currentUser.getCompany().getCompanyId());
							alreadyParkedInvoice.setContact(customer);
						}



						alreadyParkedInvoice.setUpdatedBy(currentUser.getUserId());
						if(invoiceMainBean.getInsertedby() != null)
						{
							alreadyParkedInvoice.setCreatedBy(Integer.parseInt(invoiceMainBean.getInsertedby()));
						}
						else
						{
							alreadyParkedInvoice.setCreatedBy(currentUser.getUserId());
						}

						List<InvoiceDetail> invoiceDetails = PopulateInviceDetail(invoiceMainBean, alreadyParkedInvoice, currentUser);
						saleService.updateInvoiceMainByID(alreadyParkedInvoice,currentUser.getCompany().getCompanyId());

						invoiceDetails = saleService.addInvoiceDetails(invoiceDetails,currentUser.getCompany().getCompanyId());
					}
				}

				util.AuditTrail(request, currentUser, "SellController.payCash",
						"User " + currentUser.getUserEmail() + " Pay Cash "
								+ invoiceMainBean.getInvoiceRefNbr()
								+ " successfully ", false);

				return new Response(MessageConstants.PARKED_SALE,
						StatusConstants.SUCCESS, LayOutPageConstants.SELL);

			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SellController.payCash",
						"Error Occured " + errors.toString(), true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.ADD_RESTRICTED,
						LayOutPageConstants.STAY_ON_PAGE);
			}
		} else {
			return new Response(MessageConstants.INVALID_SESSION,
					StatusConstants.INVALID, LayOutPageConstants.LOGIN);
		}
	}

	private InvoiceMain PopulateInvoiceMain(int companyId, int paymentTypeId,
			int statusId, int outletId, InvoiceMainBean invoiceMain,
			User currentUser) {
		InvoiceMain invoice = new InvoiceMain();
		if(invoiceMain.getReturnvalue() != null && invoiceMain.getReturnvalue().equalsIgnoreCase("returnsale"))
		{
			statusId = 14; // return completed

		}

		invoice.setInvoiceRefNbr(invoiceMain.getInvoiceRefNbr());

		invoice.setInvoiceNotes(invoiceMain.getInvoiceNotes());
		invoice.setInvoiceNetAmt(new BigDecimal(invoiceMain.getInvoiceNetAmt()));
		invoice.setInvoiceAmt(new BigDecimal(invoiceMain.getInvoiceAmt()));
		invoice.setInvoiceGivenAmt(new BigDecimal(invoiceMain.getInvoiceGivenAmt()));
		if (invoiceMain.getInvoiceDiscount() != null && !invoiceMain.getInvoiceDiscount().equals("")) {
			invoice.setInvoiceDiscount(new BigDecimal(invoiceMain.getInvoiceDiscount()));
		}
		if (invoiceMain.getSettledAmt() != null && !invoiceMain.getSettledAmt().equals("")) {
			invoice.setSettledAmt(new BigDecimal(invoiceMain.getSettledAmt()));
		}
		if(invoiceMain.getOrignalPrice()!=null && !invoiceMain.getOrignalPrice().equals("")){

			if(invoiceMain.getReturnvalue() != null && invoiceMain.getReturnvalue().equalsIgnoreCase(("returnsale")))
			{
				invoice.setInvoiceOrignalAmt((new BigDecimal(invoiceMain.getOrignalPrice())).multiply(new BigDecimal(-1)));
			}
			else
			{
				invoice.setInvoiceOrignalAmt(new BigDecimal(invoiceMain.getOrignalPrice()));
			}

		}

		//		if(invoiceMain.getInvoiceDetails() != null && invoiceMain.getInvoiceDetails().size() > 0)
		//		{
		//
		//			for (InvoiceDetailBean item : invoiceMain.getInvoiceDetails()) {
		//				
		//				if(item.getIsreturn() == "true")
		//				{
		//					returnItemCount=returnItemCount+1;
		//				}
		//			}
		//			
		//						
		//		}

		//		if(invoiceMain.getInvoiceDetails() != null && invoiceMain.getInvoiceDetails().size() == returnItemCount)
		//		{
		//			invoice.setInvcTypeCde("00001"); // used to handle the case in which only return is received from sell screen
		//		}
		//
		//		else 
		if(invoiceMain.getInvcTypeCde() != null )
		{
			invoice.setInvcTypeCde(invoiceMain.getInvcTypeCde()); 

		}
		if(invoiceMain.getInvoiceDiscountAmt() != null)
		{
			invoice.setInvoiceDiscountAmt(new BigDecimal(invoiceMain.getInvoiceDiscountAmt()));
		}
		if(invoiceMain.getInvoiceTax() !=null)
		{
			invoice.setInvoiceTax(new BigDecimal(invoiceMain.getInvoiceTax()));
		}
		invoice.setInvoiceGenerationDte(Calendar.getInstance().getTime());
		invoice.setActiveIndicator(true);

		// invoiceMain.setCustomerId("1"); // TODO: will be binded value

		if (invoiceMain.getCustomerId() != null && !invoiceMain.getCustomerId().equals("")) {
			Contact customer = customerService.getContactByID(Integer
					.parseInt(invoiceMain.getCustomerId()),currentUser.getCompany().getCompanyId());
			invoice.setContact(customer);
		}

		Outlet outlet = outletService.getOuletByOutletId(outletId,currentUser.getCompany().getCompanyId());
		if (outlet != null) {
			invoice.setOutlet(outlet);
		}

		Company company = companyService
				.getCompanyDetailsByCompanyID(companyId);
		if (company != null) {
			invoice.setCompany(company);
		}

		invoice.setStatus(statusService.getStatusByStatusId(statusId));

		PaymentType paymentType = paymentTypeService
				.getPaymentTypeByPaymentTypeId(paymentTypeId,currentUser.getCompany().getCompanyId());
		invoice.setPaymentType(paymentType);

		invoice.setActiveIndicator(true);
		invoice.setUpdatedBy(currentUser.getUserId());
		if(invoiceMain.getInsertedby() != null)
		{
			invoice.setCreatedBy(Integer.parseInt(invoiceMain.getInsertedby()));
		}
		else
		{
			invoice.setCreatedBy(currentUser.getUserId());
		}

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		String month = getMonthForInt(cal.get(Calendar.MONTH));
		int day = cal.get(Calendar.DAY_OF_MONTH);
		SimpleDateFormat sdf = new SimpleDateFormat("K:mm a");
		String formattedTime = sdf.format(cal.getTime());

		invoiceMain.setInvoiceGenerationDte(month + " "+ String.valueOf(day) + "" + ", "+ String.valueOf(year) +" " + formattedTime);

		invoice.setInvoiceNotes(invoiceMain.getInvoiceNotes());
		return invoice;
	}

	private List<InvoiceDetail> PopulateInviceDetail(
			InvoiceMainBean invoiceMainBean, InvoiceMain invoiceMain,
			User currentUser) {
		List<InvoiceDetail> listNewitems = new ArrayList<InvoiceDetail>();
		List<InvoiceDetail> oldInvoicelineItems = null;

		if(invoiceMain != null && invoiceMain.getInvoiceMainId() != null && invoiceMain.getInvoiceMainId() > 0 ){

			oldInvoicelineItems = saleService.getAllInvoicesDeailById( invoiceMain.getOutlet().getOutletId(), invoiceMain.getCompany().getCompanyId(),invoiceMain.getInvoiceMainId());

		}

		if(oldInvoicelineItems == null || oldInvoicelineItems.isEmpty())
		{
			for (InvoiceDetailBean itemBean : invoiceMainBean.getInvoiceDetails()) {
				InvoiceDetail billItem = new InvoiceDetail();

				if(itemBean.getProductVarientId() != null)
				{
					ProductVariant varient = productVariantService
							.getProductVariantByProductVariantId(Integer
									.parseInt(itemBean.getProductVarientId()),currentUser.getCompany().getCompanyId());

					billItem.setProductVariant(varient);
				}

				if(invoiceMainBean.getReturnvalue() !=null && invoiceMainBean.getReturnvalue().equalsIgnoreCase(("returnsale")) ||itemBean.getIsreturn() == "true" )
				{
					billItem.setIsreturn(true);	
				}
				else
				{
					billItem.setIsreturn(false);	
				}

				if(itemBean.getProductId() != null)
				{
					Product product = productService
							.getProductByProductId(Integer
									.parseInt(itemBean.getProductId()),currentUser.getCompany().getCompanyId());
					billItem.setProduct(product);
				}

				billItem.setItemRetailPrice((new BigDecimal(itemBean.getItemRetailPrice())));
				if(invoiceMainBean.getReturnvalue() != null && invoiceMainBean.getReturnvalue().equalsIgnoreCase(("returnsale")))
				{
					billItem.setItemSalePrice((new BigDecimal(itemBean.getItemSalePrice())).multiply(new BigDecimal(-1)));
				}
				else
				{
					billItem.setItemSalePrice((new BigDecimal(itemBean.getItemSalePrice())));
				}
				billItem.setItemTaxAmount((new BigDecimal(itemBean.getItemTaxAmount())));

				if (itemBean.getItemDiscountPrct() != null) {
					billItem.setItemDiscountPrct(new BigDecimal(itemBean.getItemDiscountPrct()));

				}
				if(itemBean.getOrignalPrice()!=null && !itemBean.getOrignalPrice().equals("")){

					if( (invoiceMainBean.getReturnvalue() !=null && invoiceMainBean.getReturnvalue().equalsIgnoreCase(("returnsale"))) ||itemBean.getIsreturn() == "true" )
					{
						billItem.setItemOrignalAmt((new BigDecimal(itemBean.getOrignalPrice())).multiply(new BigDecimal(-1)));
					}
					else
					{
						billItem.setItemOrignalAmt(new BigDecimal(itemBean.getOrignalPrice()));
					}



				}

				billItem.setItemNotes(itemBean.getItemNotes());
				billItem.setProductQuantity(Integer.parseInt(itemBean.getProductQuantity()));
				billItem.setCreatedBy(currentUser.getUserId());
				billItem.setUpdatedBy(currentUser.getUserId());
				billItem.setInvoiceMain(invoiceMain);
				billItem.setCompany(currentUser.getCompany());
				billItem.setOutlet(currentUser.getOutlet());
				listNewitems.add(billItem);
			}
		}
		else
		{
			for (InvoiceDetail oldInvoiceDetail : oldInvoicelineItems) {

				saleService.DeleteInvoiceDetail(oldInvoiceDetail);
			}

			for (InvoiceDetailBean itemBean : invoiceMainBean.getInvoiceDetails())
			{
				InvoiceDetail billItem = new InvoiceDetail();

				if(itemBean.getProductVarientId() != null)
				{
					ProductVariant varient = productVariantService
							.getProductVariantByProductVariantId(Integer
									.parseInt(itemBean.getProductVarientId()),currentUser.getCompany().getCompanyId());

					billItem.setProductVariant(varient);
				}

				if(itemBean.getProductId() != null)
				{
					Product product = productService
							.getProductByProductId(Integer
									.parseInt(itemBean.getProductId()),currentUser.getCompany().getCompanyId());
					billItem.setProduct(product);
				}

				billItem.setItemRetailPrice((new BigDecimal(itemBean.getItemRetailPrice())));
				if( invoiceMainBean.getReturnvalue().equalsIgnoreCase(("returnsale")))
				{
					billItem.setItemSalePrice((new BigDecimal(itemBean.getItemSalePrice())).multiply(new BigDecimal(-1)));
				}
				else
				{
					billItem.setItemSalePrice((new BigDecimal(itemBean.getItemSalePrice())));
				}
				billItem.setItemTaxAmount((new BigDecimal(itemBean.getItemTaxAmount())));

				if (itemBean.getItemDiscountPrct() != null) {
					billItem.setItemDiscountPrct(new BigDecimal(itemBean.getItemDiscountPrct()));

				}
				if(itemBean.getOrignalPrice()!=null && !itemBean.getOrignalPrice().equals("")){
					billItem.setItemOrignalAmt(new BigDecimal(itemBean.getOrignalPrice()));
				}

				billItem.setItemNotes(itemBean.getItemNotes());
				billItem.setProductQuantity(Integer.parseInt(itemBean.getProductQuantity()));
				billItem.setCreatedBy(currentUser.getUserId());
				billItem.setUpdatedBy(currentUser.getUserId());
				billItem.setInvoiceMain(invoiceMain);
				billItem.setCompany(currentUser.getCompany());
				billItem.setOutlet(currentUser.getOutlet());

				listNewitems.add(billItem);


			}


		}


		return listNewitems;
	}

	private Receipt PopulateReceipt(InvoiceMainBean invoiceMainBean,
			InvoiceMain invoiceMain, User currentUser, int statusId) {
		Receipt receipt = new Receipt();
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		receipt.setReceiptRefNo("RV_" + timeStamp);
		receipt.setReceiptDate(Calendar.getInstance().getTime());
		receipt.setReceiptAmount(new BigDecimal(invoiceMainBean.getSettledAmt()));
		receipt.setInvoiceMain(invoiceMain);

		if (invoiceMain.getOutlet() != null) {
			receipt.setOutlet(invoiceMain.getOutlet());
		}

		receipt.setStatus(statusService.getStatusByStatusId(statusId));

		PaymentType paymentType = paymentTypeService
				.getPaymentTypeByPaymentTypeId(Integer.parseInt(invoiceMainBean
						.getPaymentTypeId()),currentUser.getCompany().getCompanyId());

		receipt.setPaymentType(paymentType);

		if (invoiceMain.getContact() != null) {
			receipt.setContact(invoiceMain.getContact());
		}
		receipt.setActiveIndicator(true);
		receipt.setCreatedBy(currentUser.getUserId());
		receipt.setUpdatedBy(currentUser.getUserId());
		return receipt;
	}

	private void UpdateProductInventory(
			List<InvoiceDetailBean> invoiceDetailBeans,User currentUser, String returnvalue, InvoiceMain invoice) {
		
		//if (invoice.getStatus().getStatusId() == 9)
		//{
			//returnvalue = "LayBy";
		//}
		

		if (invoiceDetailBeans != null && invoiceDetailBeans.size() > 0) {

			for (InvoiceDetailBean invoiceDetailBean : invoiceDetailBeans) {

				if(invoiceDetailBean.getProductVarientId() != null)
				{
					ProductVariant varient = productVariantService
							.getProductVariantByProductVariantId(Integer
									.parseInt(invoiceDetailBean
											.getProductVarientId()),currentUser.getCompany().getCompanyId());
					
					if (invoice.getStatus().getStatusId() == 9 && returnvalue == null) // Direct LayBy
					{
						if (varient.getCurrentInventory() != null
								&& varient.getCurrentInventory() > 0
								&& (varient.getCurrentInventory() - Integer.parseInt(invoiceDetailBean.getProductQuantity())) >= 0) {

							varient.setCurrentInventory((varient.getCurrentInventory() - Integer.parseInt(invoiceDetailBean.getProductQuantity())));
						}
					}
					else if ((invoice.getStatus().getStatusId() == 13 || invoice.getStatus().getStatusId() == 9) && returnvalue != null) // LayBy
					{
						continue;
					}
					
					else if(returnvalue!=null && !returnvalue.equalsIgnoreCase("returnsale") && (invoiceDetailBean.getIsreturn() == null || invoiceDetailBean.getIsreturn().equalsIgnoreCase("false")) )
					{

						if (varient.getCurrentInventory() != null
								&& varient.getCurrentInventory() > 0
								&& (varient.getCurrentInventory() - Integer.parseInt(invoiceDetailBean.getProductQuantity())) >= 0) {

							varient.setCurrentInventory((varient.getCurrentInventory() - Integer.parseInt(invoiceDetailBean.getProductQuantity())));
						}

					}
					

					else if(returnvalue == null || (returnvalue != null && returnvalue.equalsIgnoreCase("returnsale")) || invoiceDetailBean.getIsreturn().equalsIgnoreCase("true") )

					{
						varient.setCurrentInventory((varient.getCurrentInventory() + Integer.parseInt(invoiceDetailBean.getProductQuantity())));

					}


					productVariantService.updateProductVariant(varient,Actions.SELL,(Integer.parseInt(invoiceDetailBean.getProductQuantity())),currentUser.getCompany());

				}


				else if(invoiceDetailBean.getProductId() != null)
				{
					Product product = productService
							.getProductByProductId(Integer
									.parseInt(invoiceDetailBean
											.getProductId()),currentUser.getCompany().getCompanyId());

					if(returnvalue!=null && !returnvalue.equalsIgnoreCase("returnsale") && (invoiceDetailBean.getIsreturn() == null || invoiceDetailBean.getIsreturn().equalsIgnoreCase("false")) )
					{

						if (product.getCurrentInventory() != null
								&& product.getCurrentInventory() > 0
								&& (product.getCurrentInventory() - Integer.parseInt(invoiceDetailBean.getProductQuantity())) >= 0) {

							product.setCurrentInventory((product.getCurrentInventory() - Integer.parseInt(invoiceDetailBean.getProductQuantity())));
						}

					}

					else

					{
						product.setCurrentInventory((product.getCurrentInventory() + Integer.parseInt(invoiceDetailBean.getProductQuantity())));

					}


					productService.updateProduct(product, Actions.SELL, Integer.parseInt(invoiceDetailBean.getProductQuantity()), currentUser.getCompany());

				}
			}

		} 

	}

	private void UpdateCustomerBalance(int customerid, BigDecimal saleamout, Actions action,int companyId ) {

		if(customerid > 0 && action !=null )
		{
			//			if(saleamout.intValue() < 0)
			//			{
			//				saleamout = saleamout.multiply(new BigDecimal(-1)); //converting to +ve value in case of return
			//			}
			Contact  customer = customerService.getContactByID(customerid, companyId);	

			if(customer.getContactBalance() == null)
			{
				customer.setContactBalance(new BigDecimal(0));

			}

			if(customer != null)
			{
				if(action == Actions.BALANCE_ADD)
				{
					customer.setContactBalance(customer.getContactBalance().add(saleamout));
				}

				else if(action == Actions.BALANCE_REMOVE)
				{
					customer.setContactBalance(customer.getContactBalance().subtract(saleamout));
				}

			}

			customerService.updateContact(customer, companyId);

		}

	}



	private void AddCustomerPayments(int customerId, BigDecimal paymentAmount, Company company, InvoiceMain invoice ) {

		if(customerId > 0  && company != null && paymentAmount.intValue() > 0 && invoice != null)
		{

			invoice.setPaymentType(paymentTypeService.getPaymentTypeByPaymentTypeId(invoice.getPaymentType().getPaymentTypeId(), company.getCompanyId()));

			Contact customer = customerService.getContactByID(customerId, company.getCompanyId());
			ContactPayments payment = new ContactPayments();

			payment.setActiveIndicator(true);
			payment.setCompany(company);
			payment.setCreatedBy(invoice.getCreatedBy());
			payment.setCreatedDate(invoice.getCreatedDate());
			payment.setContact(customer);
			if(customer.getContactBalance() == null)
			{
				customer.setContactBalance(new BigDecimal(0));
			}

			if(payment.getContactNewBalance() == null)
			{
				payment.setContactNewBalance(new BigDecimal(0));
			}

			if(customer.getContactBalance().intValue() > 0)
			{
				payment.setContactNewBalance(customer.getContactBalance().subtract(paymentAmount));
			}
			else
			{
				payment.setContactNewBalance(new BigDecimal(0));
			}

			if(customer.getContactBalance().intValue() > 0)
			{
				payment.setContactBalance(customer.getContactBalance());
			}
			payment.setContactName(customer.getFirstName()  + " " + customer.getLastName());

			payment.setDescription("Payment Received From Customer");
			payment.setOrderRefNum(invoice.getInvoiceRefNbr());
			payment.setPaymentAmount(paymentAmount);
			payment.setPaymentRefNum(invoice.getInvoiceRefNbr());

			if(invoice.getPaymentType() != null && invoice.getPaymentType().getPaymentTypeValue().equalsIgnoreCase("Cash"))
			{
				payment.setPaymentCash(paymentAmount);


			}
			else if(invoice.getPaymentType() != null && invoice.getPaymentType().getPaymentTypeValue().equalsIgnoreCase("Credit Card"))
			{
				payment.setPaymentCreditCard(paymentAmount);

			}
			else if(invoice.getPaymentType() != null && invoice.getPaymentType().getPaymentTypeValue().equals("other"))
			{

				payment.setPaymentOtherType(paymentAmount);
			}
			ContactPaymentsType contactPaymentsType =  contactPaymentsTypeService.getContactPaymentsTypeByID(1, company.getCompanyId());
			payment.setContactPaymentsType(contactPaymentsType);
			customerPaymentsService.addContactPayments(payment, company.getCompanyId());
		}

	}

	private List<PriceBookBean> GetallValidPricebooks(User currentUser, int contactgroupId ) 
	{

		try {

			List<PriceBookBean> listPriceBooksBean = new ArrayList<>();
			List<PriceBook> priceBooks = new ArrayList<>();
			List<PriceBook> priceBookList = null;

			priceBookList = priceBookService.getAllValidPriceBooks(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId(), contactgroupId);
			if(priceBookList!=null && priceBookList.size()>0){
				for(PriceBook priceBook:priceBookList){
					String [] outletgroups = priceBook.getOuteletsGroup().split(",");
					for(String outletId:outletgroups){
						if(currentUser.getOutlet().getOutletId()==Integer.valueOf(outletId)){
							priceBooks.add(priceBook);
							break;
						}
					}
					
				}
			}
			
			if(priceBooks != null && priceBooks.size()>0)
			{
				Map<Integer, PriceBookDetail> priceBookDetailMap =	priceBookDetailService.getAllActivePriceBookDetailsMapByPriceBookIdCompanyId(priceBooks.get(0).getPriceBookId(), currentUser.getCompany().getCompanyId());
				//Map<Integer, List<PriceBookDetail>> priceBookDetailMap =  priceBookDetailService.getPriceBookDetailMapByPriceBookIdCompanyId(currentUser.getCompany().getCompanyId());
				for (PriceBook book : priceBooks) {
					PriceBookBean bookBean = new PriceBookBean();
					List<PriceBookDetailBean> listProceBookDetailBean = new ArrayList<>();

					bookBean.setCompanyId(book.getCompany().getCompanyId().toString());
//					if(company != null)
//					{
//						bookBean.setCompanyName(company.getCompanyName());
//					}
					bookBean.setContactGroupId(book.getContactGroup().getContactGroupId().toString());
//					ContactGroup contactGroup = contactGroupService.getContactGroupByContactGroupId(book.getContactGroup().getContactGroupId(), company.getCompanyId());
//
//					if(contactGroup != null)
//					{
//						bookBean.setContactGroupName(contactGroup.getContactGroupName());
//					}
					if(book.getOutlet() != null)
					{
						bookBean.setOutletId(book.getOutlet().getOutletId().toString());
					}

//					if(book.getOutlet() != null)
//					{
//						bookBean.setOutletName(book.getOutlet().getOutletName().toString());
//					}
					bookBean.setPriceBookId(book.getPriceBookId().toString());
					bookBean.setPriceBookName(book.getPriceBookName());
					bookBean.setValidFrom(book.getValidFrom().toString());
					bookBean.setValidTo(book.getValidTo().toString());
					bookBean.setFlatSale(book.getFlatSale());
					bookBean.setFlatDiscount(book.getFlatDiscount().toString());

					/*List<PriceBookDetail> pricebookDetailList = new ArrayList<>();
					pricebookDetailList.add(priceBookDetailMap.get(book.getPriceBookId()));
					List<PriceBookDetail> list = pricebookDetailList;*/
					if(priceBookDetailMap != null && priceBookDetailMap.size()>0)
					{
						//for (PriceBookDetail priceBookDetail : list) {
						//	book.getPriceBookDetails().add(priceBookDetail);
						//}

						//book.setPriceBookDetails(new HashSet<PriceBookDetail>(list));

						for (Map.Entry<Integer, PriceBookDetail> entry : priceBookDetailMap.entrySet()) {
							PriceBookDetail bookDetail = entry.getValue();
							PriceBookDetailBean bookDetailBean = new PriceBookDetailBean();

							bookDetailBean.setCompanyId(bookDetail.getCompany().getCompanyId().toString());
							bookDetailBean.setUuId(bookDetail.getUuid());
//							if(company != null)
//							{
//								bookDetailBean.setCompanyName(company.getCompanyName());
//							}
							bookDetailBean.setDiscount(bookDetail.getDiscount().toString());
							bookDetailBean.setMarkup(bookDetail.getMarkup().toString());
							if(bookDetail.getMaxUnits() != null)
							{
								bookDetailBean.setMaxUnits(bookDetail.getMaxUnits().toString());
							}
							if(bookDetail.getMinUnits() != null)
							{
								bookDetailBean.setMinUnits(bookDetail.getMinUnits().toString());
							}
							bookDetailBean.setPriceBookDetailId(bookDetail.getPriceBookDetailId().toString());
							bookDetailBean.setPriceBookId(bookDetail.getPriceBook().getPriceBookId().toString());
							bookDetailBean.setPriceBookName(book.getPriceBookName());
							bookDetailBean.setProductId(bookDetail.getProduct().getProductId().toString());
							//Product product = productService.getProductByProductId(bookDetail.getProduct().getProductId(), company.getCompanyId());
							//if(product != null)
							//{
							//	bookDetailBean.setProductName(product.getProductName());
							//}
							if(bookDetail.getProductVariant() != null)
							{
							bookDetailBean.setProductVariantId(bookDetail.getProductVariant().getProductVariantId().toString());
							}
//							ProductVariant prodVar = productVariantService.getProductVariantByProductVariantId(bookDetail.getProductVariant().getProductVariantId(), company.getCompanyId());
//
//							if(prodVar != null)
//							{
//
//								bookDetailBean.setProductVariantName(prodVar.getVariantAttributeName());
//							}
//							bookDetailBean.setSalesTaxId(bookDetail.getSalesTax().getSalesTaxId().toString());
//							SalesTax tax = salesTaxService.getSalesTaxBySalesTaxId(bookDetail.getSalesTax().getSalesTaxId(), company.getCompanyId());
//
//							if(tax != null)
//							{
//								bookDetailBean.setSalesTaxName(tax.getSalesTaxName());
//							}
//							bookDetailBean.setSupplyPrice(bookDetail.getSupplyPrice().toString());
							listProceBookDetailBean.add(bookDetailBean);
						}

						bookBean.setPriceBookDetailBeans(listProceBookDetailBean);
					}
					listPriceBooksBean.add(bookBean);

				}

			}



			return listPriceBooksBean;

		} catch (Exception e) {
			e.printStackTrace();
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
		
			return null;
		}
	} 
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllCustomerGroups/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllCustomerGroups(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<CustomerGroupBean> customerGroupBeansList = new ArrayList<>();
		List<ContactGroup> customerGroupList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				customerGroupList = contactGroupService.GetAllContactGroup(currentUser.getCompany().getCompanyId());
				if (customerGroupList != null) {
					for (ContactGroup customerGroup : customerGroupList) {
						CustomerGroupBean customerGroupBean = new CustomerGroupBean();
						customerGroupBean.setCustomerGroupId(customerGroup.getContactGroupId().toString());
						customerGroupBean.setCustomerGroupName(customerGroup.getContactGroupName().toString());
						customerGroupBeansList.add(customerGroupBean);
					}
					util.AuditTrail(request, currentUser, "NewCustomerController.getAllCustomerGroups", 
							"User "+ currentUser.getUserEmail()+" retrived all Customer Groups successfully ",false);
					return new Response(customerGroupBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "NewCustomerController.getAllCustomerGroups", 
							" Customer Groups are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NewCustomerController.getAllCustomerGroups",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}


}
