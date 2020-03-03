package com.dowhile.angualrspringapp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Brand;
import com.dowhile.CompositeProduct;
import com.dowhile.Configuration;
import com.dowhile.Contact;
import com.dowhile.Outlet;
import com.dowhile.Product;
import com.dowhile.ProductPriceHistory;
import com.dowhile.ProductTag;
import com.dowhile.ProductType;
import com.dowhile.ProductVariant;
import com.dowhile.SalesTax;
import com.dowhile.Tag;
import com.dowhile.User;
import com.dowhile.VariantAttribute;
import com.dowhile.VariantAttributeValues;
import com.dowhile.angualrspringapp.beans.ImageData;
import com.dowhile.constant.Actions;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.NewProductControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.BrandBean;
import com.dowhile.frontend.mapping.bean.CompositVariantBean;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.ProductBean;
import com.dowhile.frontend.mapping.bean.ProductPriceHistoryBean;
import com.dowhile.frontend.mapping.bean.ProductTagBean;
import com.dowhile.frontend.mapping.bean.ProductTypeBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.frontend.mapping.bean.StockOrderBean;
import com.dowhile.frontend.mapping.bean.StockOrderDetailBean;
import com.dowhile.frontend.mapping.bean.SupplierBean;
import com.dowhile.frontend.mapping.bean.TagBean;
import com.dowhile.frontend.mapping.bean.VarientAttributeBean;
import com.dowhile.frontend.mapping.bean.VarientAttributeValueBean;
import com.dowhile.frontend.mapping.bean.VarientValueBean;
import com.dowhile.service.AddressService;
import com.dowhile.service.BrandService;
import com.dowhile.service.CompositeProductService;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.ContactService;
import com.dowhile.service.CountryService;
import com.dowhile.service.OutletService;
import com.dowhile.service.ProductControllerWrapperService;
import com.dowhile.service.ProductPriceHistoryService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductTagService;
import com.dowhile.service.ProductTypeService;
import com.dowhile.service.ProductVariantService;
import com.dowhile.service.RegisterService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.SalesTaxService;
import com.dowhile.service.StatusService;
import com.dowhile.service.StockOrderDetailService;
import com.dowhile.service.StockOrderService;
import com.dowhile.service.StockOrderTypeService;
import com.dowhile.service.TagService;
import com.dowhile.service.TimeZoneService;
import com.dowhile.service.VariantAttributeService;
import com.dowhile.service.VariantAttributeValuesService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.ConfigurationUtil;
import com.dowhile.util.ControllerUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;
import com.dowhile.wrapper.ProductControllerWrapper;

/**
 * Yameen Bashir
 */
@Controller
@RequestMapping("/newProduct")
public class NewProductController {

	private static Logger logger = Logger.getLogger(NewProductController.class.getName());
	@Resource
	private ResourceService resourceService;
	@Resource
	private ServiceUtil util;
	@Resource
	private TagService tagService;
	@Resource
	private ProductTagService productTagService;
	@Resource
	private ProductService productService;
	@Resource
	private BrandService brandService;
	@Resource
	private OutletService outletService;
	@Resource
	private ProductTypeService productTypeService;
	@Resource
	private SalesTaxService salesTaxService;
	@Resource
	private VariantAttributeService variantAttributeService;
	@Resource
	private ProductVariantService productVariantService;
	@Resource
	private CompositeProductService compositeProductService;
	@Resource
	private VariantAttributeValuesService variantAttributeValuesService;
	@Resource
	private ContactService supplierService;
	@Resource
	private ConfigurationService configurationService;
	@Resource
	private RegisterService registerService;
	@Resource
	private TimeZoneService timeZoneService;
	@Resource
	private AddressService addressService;
	@Resource
	private CountryService countryService;
	@Resource
	private StatusService statusService;
	@Resource
	private StockOrderService stockOrdeService;
	@Resource
	private StockOrderTypeService stockOrderTypeService;
	@Resource
	private ProductPriceHistoryService productPriceHistoryService;
	@Resource
	private StockOrderDetailService stockOrderDetailService;
	@Resource
	private ProductControllerWrapperService productControllerWrapperService;
	@Autowired
	private PurchaseOrderController purchaseOrderController;
	@Autowired
	PurchaseOrderDetailsController purchaseOrderDetailsController;
	@Autowired
	private ProductTagsController productTagsController;
	@Autowired
	ApplicationCacheController applicationCacheController  =  new ApplicationCacheController();

	private List<Product> products;
	@SuppressWarnings("rawtypes")
	static Map productVariantBarCodeMap = new HashMap<>();
	@SuppressWarnings("rawtypes")
	static Map variantAttributeMap = new HashMap<>();
	private ProductControllerWrapper productControllerWrapper;
	@SuppressWarnings({ "rawtypes" })
	private Map productsMap = new HashMap<>();
	@SuppressWarnings({ "rawtypes" })
	private Map productVariantMap = new HashMap<>();
	@SuppressWarnings({ "rawtypes" })
	private Map contactsMap = new HashMap<>();
	@SuppressWarnings({ "rawtypes" })
	private Map productTypeMap = new HashMap<>();
	@SuppressWarnings({ "rawtypes" })
	private Map brandsMap = new HashMap<>();
	@SuppressWarnings({ "rawtypes" })
	private Map outletsMap = new HashMap<>();
	@SuppressWarnings({ "rawtypes" })
	private Map variantAttributesMap = new HashMap<>();
	@SuppressWarnings({ "rawtypes" })
	private Map tagsMap = new HashMap<>();
	@SuppressWarnings({ "rawtypes" })
	private Map salesTaxMap = new HashMap<>();
	
	
	/**
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}
	/**
	 * @param products the products to set
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
	}



	@RequestMapping("/layout")
	public String getNewProductControllerPartialPage(ModelMap modelMap) {
		return "newProduct/layout";
	}



	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getNewProductControllerData/{sessionId}/{productId}/{outletId}", method = RequestMethod.POST)
	public @ResponseBody Response getNewProductControllerData(@PathVariable("sessionId") String sessionId,
			@PathVariable("productId") String productId,@PathVariable("outletId") String outletId,
			HttpServletRequest request) {

		List<SupplierBean> supplierBeans = null;
		List<ProductTypeBean> productTypeBeanList= null;
		List<BrandBean> brandBeanList= null;
		List<OutletBean> outletBeans= null;
		List<VarientAttributeBean> variantAttributeBeanList= null;
		List<ProductVariantBean> productVariantBeanList = null;
		ProductBean productBean = null;
		Map productBarCodeMap = new HashMap<>();
		List<TagBean> tagBeanList = null;

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
			logger.info("getNewProductControllerData request received for user: "+currentUser.getUserEmail()+" for companyId / company : "+currentUser.getCompany().getCompanyId()+" / "+currentUser.getCompany().getCompanyName()
					+ " against outletId / outlet:"+currentUser.getOutlet().getOutletId()+" / "+currentUser.getOutlet().getOutletName());
			try {
				initializeClassObjects();
				long start = System.currentTimeMillis();
				productControllerWrapper = productControllerWrapperService.getProductControllerWrapperDataByOutletIdCompanyId(currentUser.getOutlet().getOutletId(),currentUser.getCompany().getCompanyId());
				long endWrapper   = System.currentTimeMillis();
				NumberFormat formatter = new DecimalFormat("#0.00000");
				logger.info("Execution time to get productControllerWrapper is " + formatter.format((endWrapper - start) / 1000d) + " seconds");
//				products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
				products = productControllerWrapper.getProductList();
				if(products!=null){
					for(Product product:products){
						productBarCodeMap.put(product.getSku(), true);
						productsMap.put(product.getProductId(), product);
					}
				}
				populateNewProductControllersMap(request);
//				List<ProductVariant> productVariantsList = productVariantService.getAllActiveProductVariantsByOutletIdCompanyId(currentUser.getOutlet().getOutletId(),currentUser.getCompany().getCompanyId());
//				List<ProductVariant> productVariantsList = productControllerWrapper.getProductVariantList();

				Response response = getAllSuppliers(sessionId,request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					supplierBeans = (List<SupplierBean>) response.data;
				}
				
				response = getAllProductTypes(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					productTypeBeanList = (List<ProductTypeBean>) response.data;
				}
				
				response = getAllBrands(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					brandBeanList = (List<BrandBean>) response.data;
				}
				response = getOutlets(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					outletBeans = (List<OutletBean>) response.data;
				}

				response = getAllVariantAttributes(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					variantAttributeBeanList = (List<VarientAttributeBean>) response.data;
				}

				/*response = getAllProductsForDropDown(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					productVariantBeanList = (List<ProductVariantBean>) response.data;
				}*/

				if(productId!=null && !productId.equalsIgnoreCase("DEFAULT")){
					response = getProductDetailByProductIdOutletId(sessionId, productId, outletId, request);
					if(response.status.equals(StatusConstants.SUCCESS)){
						productBean = (ProductBean) response.data;
						
					}
				}
				
				response  = getAllTags(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					tagBeanList = (List<TagBean>) response.data;
				}

				NewProductControllerBean newProductControllerBean = new NewProductControllerBean();
				newProductControllerBean.setTagBeanList(tagBeanList);
				newProductControllerBean.setBrandBeanList(brandBeanList);
				newProductControllerBean.setOutletBeans(outletBeans);
				newProductControllerBean.setProductVariantBeanList(productVariantBeanList);
				newProductControllerBean.setProductTypeBeanList(productTypeBeanList);
				newProductControllerBean.setSupplierBeans(supplierBeans);
				newProductControllerBean.setVariantAttributeBeanList(variantAttributeBeanList);
				newProductControllerBean.setProductBean(productBean);
				newProductControllerBean.setProductBarCodeMap(productBarCodeMap);
				newProductControllerBean.setProductVariantBarCodeMap(productVariantBarCodeMap);
				
				
//				newProductControllerBean.setSku(productService.getCountOfMAXSKUForProductByCompanyId(currentUser.getCompany().getCompanyId()));
				newProductControllerBean.setSku(productControllerWrapper.getSku());
//				newProductControllerBean.setProductVariantSku(productVariantService.getCountOfMAXSKUForProductVariantByCompanyId(currentUser.getCompany().getCompanyId()));
				newProductControllerBean.setProductVariantSku(productControllerWrapper.getProductVariantSku());
				Configuration configuration = configurationMap.get("PRODCUT_TEMPLATE_FOR_ALL_OUTLETS");
				if(configuration==null || configuration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
					newProductControllerBean.setProductTemplateForAllOutlets(ControllersConstants.TRUE);
				}else{
					newProductControllerBean.setProductTemplateForAllOutlets(ControllersConstants.FALSE);
				}
				Configuration productTagConfiguration = configurationMap.get("SHOW_PRODCUT_TAG");
				if(productTagConfiguration!=null && productTagConfiguration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
					newProductControllerBean.setShowProductTag(ControllersConstants.TRUE);
				}else{
					newProductControllerBean.setShowProductTag(ControllersConstants.FALSE);
				}
				Configuration dutyCalculatorConfiguration = configurationMap.get("SHOW_DUTY_CALCULATOR");
				if(dutyCalculatorConfiguration!=null && dutyCalculatorConfiguration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
					newProductControllerBean.setShowDutyCalculator(ControllersConstants.TRUE);
				}else{
					newProductControllerBean.setShowDutyCalculator(ControllersConstants.FALSE);
				}
				//Map<Integer, Map<String, Configuration>> allCompaniesConfigurationsMap = applicationCacheController.populateAllCompaniesConfigurationsMap(ControllersConstants.BOOLEAN_FALSE);
				
				newProductControllerBean.setProductConfigurationBean(ConfigurationUtil.setProductConifurationsFromConfigurationMap(configurationMap));

				util.AuditTrail(request, currentUser, "NewProductController.getNewProductControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived NewProductController data successfully ",false);
				long endTimeTotal   = System.currentTimeMillis();
				logger.info("For User "+ currentUser.getUserEmail()+ "Execution time to get NewProductController.getNewProductControllerData is " + formatter.format((endTimeTotal - start) / 1000d) + " seconds");
				destroyClassObjects();
				return new Response(newProductControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NewProductController.getNewProductControllerData",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/addVariantAttribute/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response addVariantAttribute(@PathVariable("sessionId") String sessionId,
			@RequestBody VarientAttributeBean varientAttributeBean, HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				VariantAttribute variantAttribute = new VariantAttribute();
				variantAttribute.setActiveIndicator(true);
				variantAttribute.setAttributeName(varientAttributeBean.getAttributeName());
				variantAttribute.setUserByCreatedBy(currentUser);
				variantAttribute.setCreatedDate(new Date());
				variantAttribute.setLastUpdated(new Date());
				variantAttribute.setUserByUpdatedBy(currentUser);
				variantAttribute.setCompany(currentUser.getCompany());
				variantAttributeService.addVariantAttribute(variantAttribute,currentUser.getCompany().getCompanyId());

				util.AuditTrail(request, currentUser, "NewProductController.addVariantAttribute", 
						"User "+ currentUser.getUserEmail()+" Added VariantAttribute +"+varientAttributeBean.getAttributeName()+" successfully ",false);
				return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);


			}catch(Exception e){
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NewProductController.addVariantAttribute",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/addProduct/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response addProduct(@PathVariable("sessionId") String sessionId,
			@RequestBody ProductBean productBean, HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				initializeClassObjects();
				int totalQunatity = 0;
				List<OutletBean> outletsist = productBean.getOutletList();
				//To check duplicate bar code start
				Map productBarCodeMap = new HashMap<>();
				long start = System.currentTimeMillis();
				products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
				long end   = System.currentTimeMillis();
				NumberFormat formatter = new DecimalFormat("#0.00000");
				logger.info("Execution time to get products is " + formatter.format((end - start) / 1000d) + " seconds");
				if(products!=null){
					for(Product product:products){
						productBarCodeMap.put(product.getSku(), product);
					}
				}
				if(productBean.getSku()==null ||productBean.getSku().equalsIgnoreCase("")
						||productBarCodeMap.get(productBean.getSku())!=null){
					return new Response(MessageConstants.DUPLICATE_PRODCUT_BAR_CODE,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
				}
				long startVariant = System.currentTimeMillis();
				List<ProductVariant> productVariantsList = productVariantService.getAllActiveProductVariantsByOutletIdCompanyId(currentUser.getOutlet().getOutletId(),currentUser.getCompany().getCompanyId());
//				List<ProductVariant> productVariantsList = productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
				long endVariant   = System.currentTimeMillis();
//				NumberFormat formatter = new DecimalFormat("#0.00000");
				logger.info("Execution time to get products Variants is " + formatter.format((endVariant - startVariant) / 1000d) + " seconds");
				Map productVariantMap = new HashMap<>();
				if(productVariantsList!=null){
					
					for(ProductVariant productVariant:productVariantsList){
						productVariantMap.put(productVariant.getSku(), productVariant);
					}
				}
				
				if(productBean.getStandardProduct().equalsIgnoreCase("true")){
					//Standard scenario
					if(productBean.getVarientProducts().equalsIgnoreCase("true")){
						//Variants scenario
						List<VarientValueBean> varientValuesBeanCollection = productBean.getProductVariantValuesCollection();
						for(VarientValueBean varientValueBean:varientValuesBeanCollection){
							List<OutletBean> variantOutletBeanList = varientValueBean.getVarientsOutletList();
							if(variantOutletBeanList!=null){
								for(OutletBean variantOutletBean:variantOutletBeanList){
									variantOutletBean.getSku();
									if(variantOutletBean.getSku()==null ||variantOutletBean.getSku().equalsIgnoreCase("")
											||productVariantMap.get(variantOutletBean.getSku())!=null){
										return new Response(MessageConstants.DUPLICATE_PRODCUT_VARIANT_BAR_CODE,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
									}
								}
							}
							
						}
					}
				}
				long endFetching   = System.currentTimeMillis();
//				NumberFormat formatter = new DecimalFormat("#0.00000");
				logger.info("Execution time to verify duplication and fetching product and variants is " + formatter.format((endFetching - start) / 1000d) + " seconds");
				//To check duplicate bar code end
				if(outletsist!=null){

					List<Outlet> outlets = outletService.getOutlets(currentUser.getCompany().getCompanyId());
					Map outletMap = new HashMap<>();

					for(Outlet outlet:outlets){
						outletMap.put(outlet.getOutletId(), outlet);
					}
					
					UUID uuidProd = UUID.randomUUID();
					String randomUUIDProduct = uuidProd.toString();
					Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
					if(!productBean.getVarientProducts().equalsIgnoreCase("true")){
						
						Configuration configurationAutoCreateSV = configurationMap.get("AUTO_CREATE_STANDARD_VARIANT");
						if(configurationAutoCreateSV!=null && configurationAutoCreateSV.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
							productBean.setVarientProducts(ControllersConstants.TRUE);
							productBean.setProductUuid(randomUUIDProduct);
							addVariantValueBeanInProductBean(productBean,currentUser,configurationMap);
						}
					}
					

					if(productBean.getTrackingProduct().equalsIgnoreCase("true") && !productBean.getVarientProducts().equalsIgnoreCase("true")){
						totalQunatity = ControllerUtil.getTotlalQuantityForAllOutlets(outletsist);
					}
					

					Configuration configuration = configurationMap.get("PRODCUT_TEMPLATE_FOR_ALL_OUTLETS");//configurationService.getConfigurationByPropertyNameByCompanyId("PRODCUT_TEMPLATE_FOR_ALL_OUTLETS",currentUser.getCompany().getCompanyId());
					if(configuration!=null && configuration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
						for(OutletBean outletbean:outletsist){
							addProuductByOutlet(sessionId, outletbean, productBean, currentUser, outletMap, totalQunatity, outletsist,request,randomUUIDProduct);
						}
					}else{
						for(OutletBean outletbean:outletsist){
							Outlet outlet = (Outlet)outletMap.get(Integer.valueOf(outletbean.getOutletId()));
							if(outlet.getIsHeadOffice()!=null && outlet.getIsHeadOffice().toString()=="true"){
								addProuductByOutlet(sessionId, outletbean, productBean, currentUser, outletMap, totalQunatity, outletsist,request,randomUUIDProduct);
								break;
							}
						}
					}
					long endProductAdd   = System.currentTimeMillis();
//					NumberFormat formatter = new DecimalFormat("#0.00000");
					logger.info("For User "+ currentUser.getUserEmail()+" Execution time to complete add product Process is " + formatter.format((endProductAdd - start) / 1000d) + " seconds");
					destroyClassObjects();
					util.AuditTrail(request, currentUser, "NewProductController.addProduct", 
							"User "+ currentUser.getUserEmail()+"For all outlets Added Product/s +"+productBean.getProductName()+" successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.PRODUCTS);
				}else{
					util.AuditTrail(request, currentUser, "NewProductController.addProduct", 
							"User "+ currentUser.getUserEmail()+"For all outlets system unable to add Product/s +"+productBean.getProductName()+" successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.PRODUCTS);
				}
			}catch(Exception e){
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NewProductController.addProduct",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
	public void addVariantValueBeanInProductBean( ProductBean productBean,User currentUser,Map<String ,Configuration> configurationMap){
		try{
			Configuration configurationDefaultVN = configurationMap.get("DEFAULT_VARIANT_NAME");
			String variantName = "";
			if(configurationDefaultVN!=null ){
				variantName = configurationDefaultVN.getPropertyValue().toString();
			}
			List<VarientValueBean> productVariantValuesCollection = new ArrayList<>();
			List<OutletBean> varientsOutletList = new ArrayList<>();
			VarientValueBean varientValueBean = new VarientValueBean();
			varientValueBean.setVarientName(variantName);
			varientValueBean.setuUid(productBean.getProductUuid());
			
			OutletBean OutletBean  = new OutletBean();
			
			if(productBean.getOutletList().get(0).getCurrentInventory()!=null && !productBean.getOutletList().get(0).getCurrentInventory().equalsIgnoreCase("")){
				OutletBean.setCurrentInventory(productBean.getOutletList().get(0).getCurrentInventory());
			}
			OutletBean.setMarkupPrct(productBean.getMarkupPrct());
			OutletBean.setSupplyPriceExclTax(productBean.getSupplyPriceExclTax());
			OutletBean.setOutletId(String.valueOf(currentUser.getOutlet().getOutletId()));
			OutletBean.setSku(productBean.getSku());
			varientsOutletList.add(OutletBean);
			varientValueBean.setVarientsOutletList(varientsOutletList);
			productVariantValuesCollection.add(varientValueBean);
			productBean.setProductVariantValuesCollection(productVariantValuesCollection);
			VarientAttributeBean varientAttributeBean = new VarientAttributeBean();
			String attributeId = "1";
			varientAttributeBean.setVarientAttributeId(attributeId);
			List<VarientAttributeBean> productVariantAttributeCollection =  new ArrayList<>();
			productVariantAttributeCollection.add(varientAttributeBean);
			productBean.setProductVariantAttributeCollection(productVariantAttributeCollection);
			List<VarientAttributeValueBean> productVariantValuesCollectionOne = new ArrayList<>();
			VarientAttributeValueBean varientAttributeValueBean =  new VarientAttributeValueBean();
			varientAttributeValueBean.setValue(variantName);
			productVariantValuesCollectionOne.add(varientAttributeValueBean);
			productBean.setProductVariantValuesCollectionOne(productVariantValuesCollectionOne);
			
		}catch(Exception ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void addProuductByOutlet(String sessionId, OutletBean outletbean,ProductBean productBean,User currentUser,Map outletMap,int totalQunatity,List<OutletBean> outletsist,HttpServletRequest request
			,String randomUUIDProduct){
		HttpSession session =  request.getSession(false);
		Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
		Configuration configurationStockOrder = configurationMap.get("AUTO_STOCK_ORDER_NEW_PRODUCT");//configurationService.getConfigurationByPropertyNameByCompanyId("AUTO_STOCK_ORDER_NEW_PRODUCT",currentUser.getCompany().getCompanyId());
		Product product = new Product();
		product.setActiveIndicator(true);

		product.setProductUuid(randomUUIDProduct);
		Brand brand = new Brand();
		brand.setBrandId(Integer.valueOf(productBean.getBrandId()));
//		Brand brand = brandService.getBrandByBrandId(Integer.valueOf(productBean.getBrandId()),currentUser.getCompany().getCompanyId());
		product.setBrand(brand);
		product.setUserByCreatedBy(currentUser);
		product.setCreatedDate(new Date());
		product.setLastUpdated(new Date());
		product.setProductDesc(productBean.getProductDesc());
		product.setProductHandler(productBean.getProductHandler());
		product.setProductName(productBean.getProductName());
		ProductType productType =  new ProductType();
		productType.setProductTypeId(Integer.valueOf(productBean.getProductTypeId()));
//		ProductType productType = productTypeService.getProductTypeByProductTypeId(Integer.valueOf(productBean.getProductTypeId()),currentUser.getCompany().getCompanyId());
		product.setProductType(productType);
		product.setPurchaseAccountCode(productBean.getPurchaseAccountCode());
		product.setSalesAccountCode(productBean.getSalesAccountCode());
		Contact supplier = new Contact();
		supplier.setContactId(Integer.valueOf(productBean.getSupplierId()));
//		Contact supplier = supplierService.getContactByID(Integer.valueOf(productBean.getSupplierId()),currentUser.getCompany().getCompanyId());
		product.setContact(supplier);
		product.setProductCanBeSold(productBean.getProductCanBeSold());
		product.setStandardProduct(productBean.getStandardProduct());
		product.setTrackingProduct(productBean.getTrackingProduct());
		if(productBean.getProductVariantValuesCollection()==null){
			product.setVariantProducts("false");
		}else{
			product.setVariantProducts(productBean.getVarientProducts());
		}

		product.setUserByUpdatedBy(currentUser);
		product.setOutlet((Outlet)outletMap.get(Integer.valueOf(outletbean.getOutletId())));
		SalesTax salestax = new SalesTax();
		salestax.setSalesTaxId(Integer.valueOf(outletbean.getSalesTaxId()));
//		SalesTax salestax = salesTaxService.getSalesTaxBySalesTaxId(Integer.valueOf(outletbean.getSalesTaxId()),currentUser.getCompany().getCompanyId());
		product.setSalesTax(salestax);

		product.setMarkupPrct(new BigDecimal(productBean.getMarkupPrct()));
		product.setSku(productBean.getSku());
		product.setSupplyPriceExclTax(new BigDecimal(productBean.getSupplyPriceExclTax()));


		if(productBean.getCurrentInventory()==null || productBean.getCurrentInventory().equals("")){
			product.setCurrentInventory(-1);


		}
		if(productBean.getStandardProduct().equalsIgnoreCase("true") && productBean.getTrackingProduct().equalsIgnoreCase("true")&& productBean.getVarientProducts().equalsIgnoreCase("true")){
			product.setCurrentInventory(0);
			product.setReorderAmount(new BigDecimal(0));
			product.setReorderPoint(0);
		}else if(productBean.getStandardProduct().equalsIgnoreCase("true") && productBean.getTrackingProduct().equalsIgnoreCase("true")&& !productBean.getVarientProducts().equalsIgnoreCase("true")){

			if(outletbean.getCurrentInventory()!=null && !outletbean.getCurrentInventory().equalsIgnoreCase("")){
				product.setCurrentInventory(Integer.valueOf(outletbean.getCurrentInventory()));
			}else{
				product.setCurrentInventory(0);

			}
			if(outletbean.getReorderAmount()!=null && !outletbean.getReorderAmount().equalsIgnoreCase("")){
				product.setReorderAmount(new BigDecimal(outletbean.getReorderAmount()));
			}else{
				product.setReorderAmount(new BigDecimal(0));

			}
			if(outletbean.getReorderPoint()!=null && !outletbean.getReorderPoint().equalsIgnoreCase("")){
				product.setReorderPoint(Integer.valueOf(outletbean.getReorderPoint()));
			}else{
				product.setReorderPoint(0);
			}

		}else{
			product.setCurrentInventory(0);
			product.setReorderAmount(new BigDecimal(0));
			product.setReorderPoint(0);
		}

		product.setCompany(currentUser.getCompany());
		if(productBean.getImageData()!=null ){
			processImage(productBean.getImageData(), product, request);
		}	
		if(productBean.getProductVariantValuesCollectionOne()!=null){
			String attribut1 = "";
			for(VarientAttributeValueBean attributeValue : productBean.getProductVariantValuesCollectionOne()){
				VariantAttributeValues variantAttributeValues = new VariantAttributeValues();
				variantAttributeValues.setAttributeValue(attributeValue.getValue());
				if(attribut1.equalsIgnoreCase("")){
					attribut1= attributeValue.getValue();
				}else{
					attribut1 =attribut1+","+attributeValue.getValue();
				}
				
			}
			product.setAttribute1(attribut1);
		}
		if(productBean.getProductVariantValuesCollectionTwo()!=null){
			String attribut2 = "";
			for(VarientAttributeValueBean attributeValue : productBean.getProductVariantValuesCollectionTwo()){
				VariantAttributeValues variantAttributeValues = new VariantAttributeValues();
				variantAttributeValues.setAttributeValue(attributeValue.getValue());
				if(attribut2.equalsIgnoreCase("")){
					attribut2= attributeValue.getValue();
				}else{
					attribut2 =attribut2+","+attributeValue.getValue();
				}
				
			}
			product.setAttribute2(attribut2);
		}
		if(productBean.getProductVariantValuesCollectionThree()!=null){
			String attribut3 = "";
			for(VarientAttributeValueBean attributeValue : productBean.getProductVariantValuesCollectionThree()){
				VariantAttributeValues variantAttributeValues = new VariantAttributeValues();
				variantAttributeValues.setAttributeValue(attributeValue.getValue());
				if(attribut3.equalsIgnoreCase("")){
					attribut3= attributeValue.getValue();
				}else{
					attribut3 =attribut3+","+attributeValue.getValue();
				}
				
			}
			product.setAttribute3(attribut3);
		}
		Product newProduct = new Product();
		//inventory will be updated through stock order
		if(configurationStockOrder!=null && configurationStockOrder.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
			int currentInventory = 0;
			if(product.getCurrentInventory() !=null && product.getCurrentInventory()>0){
				currentInventory = product.getCurrentInventory();
				product.setCurrentInventory(0); 
			}
			newProduct =productService.addProduct(product,Actions.CREATE,totalQunatity,currentUser.getCompany());
			newProduct.setCurrentInventory(currentInventory);
		}
		else{
			newProduct =productService.addProduct(product,Actions.CREATE,totalQunatity,currentUser.getCompany());
		}
		if(newProduct!=null){
			
			
			Configuration dutyCalculatorConfiguration = configurationMap.get("SHOW_DUTY_CALCULATOR");
			if(dutyCalculatorConfiguration!=null && dutyCalculatorConfiguration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
				ProductPriceHistoryBean productPriceHistoryBean = productBean.getProductPriceHistoryBean();
				if(productPriceHistoryBean!=null){
					ProductPriceHistory productPriceHistory = getProductPriceHistoryFromProductPriceHistoryBean(productPriceHistoryBean,newProduct);
					if(productPriceHistory!=null){
						productPriceHistoryService.addProductPriceHistory(productPriceHistory);
					}
				}
			}
			
			
			List<TagBean> tagBeanList = productBean.getTagList();
			if(tagBeanList!=null){
				for(TagBean tagBean:tagBeanList){
					ProductTag productTag = new ProductTag();
					productTag.setActiveIndicator(true);
					productTag.setUserByCreatedBy(currentUser);
					productTag.setLastUpdated(new Date());
					productTag.setProduct(newProduct);
					Tag newtag = new Tag();
					newtag.setTagId(Integer.valueOf(tagBean.getTagId()));
//					Tag newtag = tagService.getTagByTagId(Integer.valueOf(tagBean.getTagId()),currentUser.getCompany().getCompanyId());
					productTag.setTag(newtag);
					productTag.setUserByUpdatedBy(currentUser);
					productTag.setCreatedDate(new Date());
					productTag.setProductTagUuid(newProduct.getProductUuid());
					productTag.setCompany(currentUser.getCompany());
					productTagService.addProductTag(productTag,currentUser.getCompany().getCompanyId());
				}
			}
			List<StockOrderDetailBean> stockOrderDetialBeansList = new ArrayList<>();
			Double grandTotal = 0.0;
			Double itemCount = 0.0;
			if(productBean.getStandardProduct().equalsIgnoreCase("true")){
				//Standard scenario
				if(productBean.getVarientProducts().equalsIgnoreCase("true")){
					//Variants scenario
					List<VarientValueBean> varientValuesBeanCollection = productBean.getProductVariantValuesCollection();
					if(varientValuesBeanCollection!=null){
						List<VariantAttribute> 	VariantAttributeList = 	variantAttributeService.getAllVariantAttributes(currentUser.getCompany().getCompanyId());
						if(VariantAttributeList!=null){
							for(VariantAttribute variantAttribute:VariantAttributeList){
								variantAttributeMap.put(variantAttribute.getVariantAttributeId(), variantAttribute);
							}	
						}
						
						for(VarientValueBean varientValueBean:varientValuesBeanCollection){
							if(configurationStockOrder!=null && configurationStockOrder.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
								int variantId = 0;
								int currInt = 0;
								if(varientValueBean.getVarientsOutletList().get(0).getCurrentInventory()!=null && !varientValueBean.getVarientsOutletList().get(0).getCurrentInventory().equalsIgnoreCase("") && 
										Integer.valueOf(varientValueBean.getVarientsOutletList().get(0).getCurrentInventory())>0){
									currInt = Integer.valueOf(varientValueBean.getVarientsOutletList().get(0).getCurrentInventory());
									varientValueBean.getVarientsOutletList().get(0).setCurrentInventory(String.valueOf(0));
								}
								variantId = addProductVariants(outletbean, productBean, currentUser, outletMap, outletsist, varientValueBean, newProduct);
								varientValueBean.getVarientsOutletList().get(0).setCurrentInventory(String.valueOf(currInt));
								if( variantId!=0 ){
									if(varientValueBean.getVarientsOutletList().get(0).getCurrentInventory()!=null && !varientValueBean.getVarientsOutletList().get(0).getCurrentInventory().equalsIgnoreCase("") && 
											Integer.valueOf(varientValueBean.getVarientsOutletList().get(0).getCurrentInventory())>0){
										StockOrderDetailBean stockOrderDetailBean = new StockOrderDetailBean();
										stockOrderDetailBean.setProductVariantId(String.valueOf(variantId));
										stockOrderDetailBean.setOrderProdQty(varientValueBean.getVarientsOutletList().get(0).getCurrentInventory());
										stockOrderDetailBean.setOrdrSupplyPrice(varientValueBean.getVarientsOutletList().get(0).getSupplyPriceExclTax());
										grandTotal = grandTotal + (Double.parseDouble(stockOrderDetailBean.getOrderProdQty()) * Double.parseDouble(stockOrderDetailBean.getOrdrSupplyPrice())); 
										itemCount = itemCount + Double.parseDouble(stockOrderDetailBean.getOrderProdQty());
										stockOrderDetailBean.setIsProduct("false");
										stockOrderDetialBeansList.add(stockOrderDetailBean);
									}
								}
							}
							else{
								addProductVariants(outletbean, productBean, currentUser, outletMap, outletsist, varientValueBean, newProduct);
							}
						}
					}
				}else{
					if(configurationStockOrder!=null && configurationStockOrder.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
						if(newProduct.getCurrentInventory()>0){
							StockOrderDetailBean stockOrderDetailBean = new StockOrderDetailBean();
							stockOrderDetailBean.setProductVariantId(newProduct.getProductId().toString());
							stockOrderDetailBean.setOrderProdQty(String.valueOf(newProduct.getCurrentInventory()));
							stockOrderDetailBean.setOrdrSupplyPrice(String.valueOf(newProduct.getSupplyPriceExclTax()));
							grandTotal = grandTotal + (Double.parseDouble(stockOrderDetailBean.getOrderProdQty()) * Double.parseDouble(stockOrderDetailBean.getOrdrSupplyPrice()));					
							itemCount = itemCount + Double.parseDouble(stockOrderDetailBean.getOrderProdQty());
							stockOrderDetailBean.setIsProduct("true");
							stockOrderDetialBeansList.add(stockOrderDetailBean);
						}
					}
				}				

				if(stockOrderDetialBeansList!=null && stockOrderDetialBeansList.size()>0){
					StockOrderBean stockOrderBean = new StockOrderBean();
					stockOrderBean.setOutlet(outletbean.getOutletId());
					stockOrderBean.setSupplierId(productBean.getSupplierId());
					long start = System.currentTimeMillis();
					AddStockOrder(sessionId, stockOrderBean, stockOrderDetialBeansList, grandTotal, itemCount, request);
					long end   = System.currentTimeMillis();
					NumberFormat formatter = new DecimalFormat("#0.00000");
					logger.info("Execution time to complete AddStockOrder is " + formatter.format((end - start) / 1000d) + " seconds");
					
				}

			}else{
				//Composite scenario
				for(OutletBean outletsForCompositeProduct:outletsist){
					List<CompositVariantBean> compositProductBeans = productBean.getCompositProductCollection();

					for(CompositVariantBean compositVariantBean:compositProductBeans){
						addCompositieProductVariants(outletbean, compositVariantBean, currentUser, outletMap, outletsForCompositeProduct, newProduct);
					}
				}



			}
		}

	}
	@SuppressWarnings("rawtypes")
	private void addCompositieProductVariants(OutletBean outletbean,CompositVariantBean compositVariantBean,User currentUser,Map outletMap,OutletBean outletsForCompositeProduct,Product newProduct ){

		if(outletsForCompositeProduct.getOutletId().equalsIgnoreCase(outletbean.getOutletId())){
			CompositeProduct compositeProduct = new CompositeProduct();
			compositeProduct.setActiveIndicator(true);
			compositeProduct.setCompositeQuantity(Integer.valueOf(compositVariantBean.getCompositQunatity()));
			if(compositVariantBean.getProductVariantId()!=null && compositVariantBean.getProductVariantId()!=""){
				ProductVariant productVariant = productVariantService.getProductVariantByProductVariantId(Integer.valueOf(compositVariantBean.getProductVariantId()),currentUser.getCompany().getCompanyId());
				compositeProduct.setProductVariant(productVariant);
			}

			compositeProduct.setCreatedDate(new Date());
			compositeProduct.setLastUpdated(new Date());
			compositeProduct.setCompositeProductUuid(compositVariantBean.getUuid());
			compositeProduct.setOutlet((Outlet)outletMap.get(Integer.valueOf(outletsForCompositeProduct.getOutletId())));
			compositeProduct.setProductByProductAssocicationId(newProduct);	
			Product selectiveProduct = productService.getProductByProductId(Integer.valueOf(compositVariantBean.getProductId()),currentUser.getCompany().getCompanyId());
			compositeProduct.setProductBySelectiveProductAssociationId(selectiveProduct);
			compositeProduct.setUserByCreatedBy(currentUser);
			compositeProduct.setUserByUpdatedBy(currentUser);
			compositeProduct.setCompany(currentUser.getCompany());
			compositeProductService.addCompositeProduct(compositeProduct,Actions.CREATE,0,currentUser.getCompany().getCompanyId(),newProduct.getProductUuid());
		}


	}

	@SuppressWarnings("rawtypes")
	private int addProductVariants(OutletBean outletbean,ProductBean productBean,User currentUser,Map outletMap,List<OutletBean> outletsist,VarientValueBean varientValueBean,Product newProduct ){

		int productVariantId = 0;
		String[] varientNamesList = varientValueBean.getVarientName().split("/");
		List<OutletBean> variantOutletBeanList = varientValueBean.getVarientsOutletList();
		if(variantOutletBeanList!=null){
			for(OutletBean variantOutletBean:variantOutletBeanList){
				if(Integer.valueOf(variantOutletBean.getOutletId())==Integer.valueOf(outletbean.getOutletId())){
					ProductVariant productVariant = new ProductVariant();
					productVariant.setActiveIndicator(true);
					productVariant.setUserByCreatedBy(currentUser);
					productVariant.setCreatedDate(new Date());
					productVariant.setLastUpdated(new Date());
					productVariant.setProductVariantUuid(varientValueBean.getuUid());
					productVariant.setMarkupPrct(new BigDecimal(variantOutletBean.getMarkupPrct()));
					productVariant.setProduct(newProduct);
					productVariant.setSku(variantOutletBean.getSku());
					productVariant.setSupplyPriceExclTax(new BigDecimal(variantOutletBean.getSupplyPriceExclTax()));
					productVariant.setUserByUpdatedBy(currentUser);
					Outlet tempOutlet = (Outlet)outletMap.get(Integer.valueOf(variantOutletBean.getOutletId()));
					productVariant.setOutlet(tempOutlet);
					SalesTax salesTax = new SalesTax();
					salesTax.setSalesTaxId(Integer.valueOf(tempOutlet.getSalesTax().getSalesTaxId()));
//					SalesTax salesTax = salesTaxService.getSalesTaxBySalesTaxId(Integer.valueOf(tempOutlet.getSalesTax().getSalesTaxId()),currentUser.getCompany().getCompanyId());
					productVariant.setSalesTax(salesTax);
					productVariant.setVariantAttributeName(varientValueBean.getVarientName());
					productVariant.setVariantAttributeValue1(varientNamesList[0]);
					if(varientNamesList.length>1){
						productVariant.setVariantAttributeValue2(varientNamesList[1]);
					}
					if(varientNamesList.length>2){
						productVariant.setVariantAttributeValue3(varientNamesList[2]);
					}
					VariantAttribute attributeOne = new VariantAttribute();
					VariantAttribute attributeTwo = new VariantAttribute();
					VariantAttribute attributeThree = new VariantAttribute();
					if(productBean.getProductVariantAttributeCollection()!=null && productBean.getProductVariantAttributeCollection().size() >= 2){
						attributeOne = (VariantAttribute)variantAttributeMap.get(Integer.parseInt(productBean.getProductVariantAttributeCollection().get(0).getVarientAttributeId()));
						//attributeOne = variantAttributeService.getVariantAttributeByVariantAttributeId(Integer.parseInt(productBean.getProductVariantAttributeCollection().get(0).getVarientAttributeId()),currentUser.getCompany().getCompanyId());
						productVariant.setVariantAttributeByVariantAttributeAssocicationId1(attributeOne);
						if(productBean.getProductVariantAttributeCollection().size()>=2){
							attributeTwo = (VariantAttribute)variantAttributeMap.get(Integer.parseInt(productBean.getProductVariantAttributeCollection().get(1).getVarientAttributeId()));
							//attributeTwo = variantAttributeService.getVariantAttributeByVariantAttributeId(Integer.parseInt(productBean.getProductVariantAttributeCollection().get(1).getVarientAttributeId()),currentUser.getCompany().getCompanyId());
							productVariant.setVariantAttributeByVariantAttributeAssocicationId2(attributeTwo);
						}
						if(productBean.getProductVariantAttributeCollection().size()>=3){
							attributeThree = (VariantAttribute)variantAttributeMap.get(Integer.parseInt(productBean.getProductVariantAttributeCollection().get(2).getVarientAttributeId()));
							//attributeThree = variantAttributeService.getVariantAttributeByVariantAttributeId(Integer.parseInt(productBean.getProductVariantAttributeCollection().get(2).getVarientAttributeId()),currentUser.getCompany().getCompanyId());
							productVariant.setVariantAttributeByVariantAttributeAssocicationId3(attributeThree);

						}
					}
					else if(productBean.getProductVariantAttributeCollection()!=null && productBean.getProductVariantAttributeCollection().size() > 1){
						attributeOne = (VariantAttribute)variantAttributeMap.get(Integer.parseInt(productBean.getProductVariantAttributeCollection().get(0).getVarientAttributeId()));
						//attributeOne = variantAttributeService.getVariantAttributeByVariantAttributeId(Integer.parseInt(productBean.getProductVariantAttributeCollection().get(0).getVarientAttributeId()),currentUser.getCompany().getCompanyId());
						productVariant.setVariantAttributeByVariantAttributeAssocicationId1(attributeOne);

						attributeTwo = (VariantAttribute)variantAttributeMap.get(Integer.parseInt(productBean.getProductVariantAttributeCollection().get(1).getVarientAttributeId()));
						//attributeTwo = variantAttributeService.getVariantAttributeByVariantAttributeId(Integer.parseInt(productBean.getProductVariantAttributeCollection().get(1).getVarientAttributeId()),currentUser.getCompany().getCompanyId());
						productVariant.setVariantAttributeByVariantAttributeAssocicationId2(attributeTwo);

					}
					else if(productBean.getProductVariantAttributeCollection()!=null && productBean.getProductVariantAttributeCollection().size() == 1){
						attributeOne = (VariantAttribute)variantAttributeMap.get(Integer.parseInt(productBean.getProductVariantAttributeCollection().get(0).getVarientAttributeId()));
						//attributeOne = variantAttributeService.getVariantAttributeByVariantAttributeId(Integer.parseInt(productBean.getProductVariantAttributeCollection().get(0).getVarientAttributeId()),currentUser.getCompany().getCompanyId());
						productVariant.setVariantAttributeByVariantAttributeAssocicationId1(attributeOne);
					}
					if(variantOutletBean.getCurrentInventory()==null || variantOutletBean.getCurrentInventory().equals("")){
						productVariant.setCurrentInventory(0);
					}
					if(productBean.getTrackingProduct().equalsIgnoreCase("true")){
						if(variantOutletBean.getCurrentInventory()==null){
							productVariant.setCurrentInventory(0);
						}else{
							productVariant.setCurrentInventory(Integer.valueOf(variantOutletBean.getCurrentInventory()));
						}
						if(variantOutletBean.getReorderAmount()==null){
							productVariant.setReorderAmount(BigDecimal.ZERO);

						}else{
							productVariant.setReorderAmount(new BigDecimal(variantOutletBean.getReorderAmount()));

						}
						if(variantOutletBean.getReorderPoint()==null){
							productVariant.setReorderPoint(0);

						}else{
							productVariant.setReorderPoint(Integer.valueOf(variantOutletBean.getReorderPoint()));
						}
					}
					productVariant.setCompany(currentUser.getCompany());
					ProductVariant variantProduct =productVariantService.addProductVariant(productVariant,Actions.CREATE,productVariant.getCurrentInventory(),currentUser.getCompany(),newProduct.getProductUuid());
					productVariantId = variantProduct.getProductVariantId();
					List<VariantAttributeValues> variantAttributeValuesList =  new ArrayList<>(); 
					if(productBean.getProductVariantValuesCollectionOne()!=null ){
						for(VarientAttributeValueBean attributeValue : productBean.getProductVariantValuesCollectionOne()){
							VariantAttributeValues variantAttributeValues = new VariantAttributeValues();
							variantAttributeValues.setActiveIndicator(true);
							variantAttributeValues.setAttributeValue(attributeValue.getValue());
							variantAttributeValues.setUserByCreatedBy(currentUser);
							variantAttributeValues.setLastUpdated(new Date());
							variantAttributeValues.setProductVariant(productVariant);
							variantAttributeValues.setUserByUpdatedBy(currentUser);
							variantAttributeValues.setVariantAttribute(attributeOne);
							variantAttributeValues.setProduct(newProduct);
							variantAttributeValues.setProductUuid(newProduct.getProductUuid());
							variantAttributeValues.setCompany(currentUser.getCompany());
							variantAttributeValuesList.add(variantAttributeValues);
							//variantAttributeValuesService.addVariantAttributeValues(variantAttributeValues,currentUser.getCompany().getCompanyId());
						}
					}
					if(productBean.getProductVariantValuesCollectionTwo()!=null ){
						for(VarientAttributeValueBean attributeValue : productBean.getProductVariantValuesCollectionTwo()){
							VariantAttributeValues variantAttributeValues = new VariantAttributeValues();
							variantAttributeValues.setActiveIndicator(true);
							variantAttributeValues.setAttributeValue(attributeValue.getValue());
							variantAttributeValues.setUserByCreatedBy(currentUser);
							variantAttributeValues.setLastUpdated(new Date());
							variantAttributeValues.setProductVariant(productVariant);
							variantAttributeValues.setUserByUpdatedBy(currentUser);
							variantAttributeValues.setVariantAttribute(attributeTwo);
							variantAttributeValues.setProduct(newProduct);
							variantAttributeValues.setProductUuid(newProduct.getProductUuid());
							variantAttributeValues.setCompany(currentUser.getCompany());
							variantAttributeValuesList.add(variantAttributeValues);
							//variantAttributeValuesService.addVariantAttributeValues(variantAttributeValues,currentUser.getCompany().getCompanyId());
						}
					}
					if(productBean.getProductVariantValuesCollectionThree()!=null ){
						for(VarientAttributeValueBean attributeValue : productBean.getProductVariantValuesCollectionThree()){
							VariantAttributeValues variantAttributeValues = new VariantAttributeValues();
							variantAttributeValues.setActiveIndicator(true);
							variantAttributeValues.setAttributeValue(attributeValue.getValue());
							variantAttributeValues.setUserByCreatedBy(currentUser);
							variantAttributeValues.setLastUpdated(new Date());
							variantAttributeValues.setProductVariant(productVariant);
							variantAttributeValues.setUserByUpdatedBy(currentUser);
							variantAttributeValues.setVariantAttribute(attributeThree);
							variantAttributeValues.setProduct(newProduct);
							variantAttributeValues.setProductUuid(newProduct.getProductUuid());
							variantAttributeValues.setCompany(currentUser.getCompany());
							variantAttributeValuesList.add(variantAttributeValues);
							//variantAttributeValuesService.addVariantAttributeValues(variantAttributeValues,currentUser.getCompany().getCompanyId());
						}
					}
					variantAttributeValuesService.addVariantAttributeValuesList(variantAttributeValuesList);
				}
			}
		}
		return productVariantId;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllVariantAttributes/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllVariantAttributes(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<VarientAttributeBean> variantAttributeBeanList = new ArrayList<>();
		List<VariantAttribute> variantAttributesList = null;

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");


			try {
				if(productControllerWrapper==null){
					variantAttributesList = variantAttributeService.getAllVariantAttributes(currentUser.getCompany().getCompanyId());
//					variantAttributesList = productControllerWrapper.getVariantAttributesList();

				}else{
//					variantAttributesList = variantAttributeService.getAllVariantAttributes(currentUser.getCompany().getCompanyId());
					variantAttributesList = productControllerWrapper.getVariantAttributesList();

				}
				if (variantAttributesList != null) {
					for (VariantAttribute variantAttribute : variantAttributesList) {

						VarientAttributeBean variantAttributeBean = new VarientAttributeBean();
						variantAttributeBean.setVarientAttributeId(variantAttribute.getVariantAttributeId().toString());
						variantAttributeBean.setAttributeName(variantAttribute.getAttributeName());
						variantAttributeBeanList.add(variantAttributeBean);
						variantAttributeBean = null;
					}
					variantAttributesList = null;
					util.AuditTrail(request, currentUser, "NewProductController.getAllVariantAttributes", 
							"User "+ currentUser.getUserEmail()+" retrived all Variant attributes successfully ",false);
					return new Response(variantAttributeBeanList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "NewProductController.getAllVariantAttributes", 
							" Variant attributes are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NewProductController.getAllVariantAttributes",
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
	@RequestMapping(value = "/getAllProductsForDropDown/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProductsForDropDown(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<ProductVariantBean> productVariantBeanList = new ArrayList<>();
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				List<ProductVariant> productVariantsList = productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
				if(productVariantsList!=null){
					Map productMap = new HashMap<>();
					for(Product product:products){
						productMap.put(product.getProductId(), product);
					}
					for(ProductVariant productVariant:productVariantsList){
						ProductVariantBean productVariantBean = new ProductVariantBean();
						productVariantBean.setProductVariantId(productVariant.getProductVariantId().toString());
						productVariantBean.setProductId(productVariant.getProduct().getProductId().toString());
						Product product = (Product)productMap.get(Integer.valueOf(productVariant.getProduct().getProductId()));
						productVariantBean.setProductName(product.getProductName());
						productVariantBean.setSku(productVariant.getSku());
						productVariantBarCodeMap.put(productVariant.getSku(), true);
						productVariantBean.setVariantAttributeName(productVariant.getVariantAttributeName());
						productVariantBeanList.add(productVariantBean);
					}

					util.AuditTrail(request, currentUser, "NewProductController.getAllProductsForDropDown", 
							"User "+ currentUser.getUserEmail()+" retrived all Products successfully ",false);
					return new Response(productVariantBeanList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "NewProductController.getAllProductsForDropDown", 
							" Products are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NewProductController.getAllProductsForDropDown",
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
	@RequestMapping(value = "/getProductDetailByProductId/{sessionId}/{productId}/{outletId}", method = RequestMethod.POST)
	public @ResponseBody Response getProductDetailByProductIdOutletId(@PathVariable("sessionId") String sessionId,
			@PathVariable("productId") String productId,@PathVariable("outletId") String outletId,
			HttpServletRequest request) {

		List<ProductVariantBean> productVariantBeanList = new ArrayList<>();
		List<CompositVariantBean> compositProductCollection = new ArrayList<>();
		List<OutletBean> outletBeanList = new ArrayList<>();
		List<ProductTagBean> productTagBeanList = new ArrayList<>();
		Product product = null;

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				product = productService.getProductByProductId(Integer.valueOf(productId),currentUser.getCompany().getCompanyId());
				if (product != null) {
					productVariantBeanList = new ArrayList<>();
					productTagBeanList = new ArrayList<>();
					ProductBean productBean = new ProductBean();
					productBean.setProductId(product.getProductId().toString());
					productBean.setProductName(product.getProductName());
					productBean.setProductDesc(product.getProductDesc());
					productBean.setProductHandler(product.getProductHandler());
					ProductType productType = (ProductType) productTypeMap.get(product.getProductType().getProductTypeId());
//					ProductType productType = productTypeService.getProductTypeByProductTypeId(product.getProductType().getProductTypeId(),currentUser.getCompany().getCompanyId());
					productBean.setProductTypeId(productType.getProductTypeId().toString());
					productBean.setProductTypeName(productType.getProductTypeName());
					Contact supplier = (Contact) contactsMap.get(product.getContact().getContactId());
//					Contact supplier = supplierService.getContactByID(product.getContact().getContactId(),currentUser.getCompany().getCompanyId());
					productBean.setSupplierId(supplier.getContactId().toString());
					productBean.setSupplierName(supplier.getContactName());
					Brand brand = (Brand) brandsMap.get(product.getBrand().getBrandId());
//					Brand brand = brandService.getBrandByBrandId(product.getBrand().getBrandId(),currentUser.getCompany().getCompanyId());
					productBean.setBrandId(brand.getBrandId().toString());
					productBean.setBrandName(brand.getBrandName());
					productBean.setSalesAccountCode(product.getSalesAccountCode());
					Outlet outlett = (Outlet) outletsMap.get(product.getOutlet().getOutletId());
//					Outlet outlett = outletService.getOuletByOutletId(product.getOutlet().getOutletId(),currentUser.getCompany().getCompanyId());
					productBean.setOutletId(outlett.getOutletId().toString());
					productBean.setOutletName(outlett.getOutletName());
					productBean.setPurchaseAccountCode(product.getPurchaseAccountCode());
					productBean.setStandardProduct(product.getStandardProduct());
					productBean.setTrackingProduct(product.getTrackingProduct());
					productBean.setVarientProducts(product.getVariantProducts());
					productBean.setMarkupPrct(product.getMarkupPrct().toString());
					productBean.setProductCanBeSold(product.getProductCanBeSold());
					productBean.setSupplyPriceExclTax(product.getSupplyPriceExclTax().toString());
					productBean.setSku(product.getSku());
					productBean.setProductUuid(product.getProductUuid());
					productBean.setImagePath(product.getImagePath());
					productBean.setCurrentInventory(product.getCurrentInventory().toString());
					productBean.setOldInventory(product.getCurrentInventory().toString());
					
					if(product.getStandardProduct().equalsIgnoreCase("true")){
//						List<Outlet> outlets = outletService.getOutlets(currentUser.getCompany().getCompanyId());
//						Map outletMap = new HashMap<>();

					/*	for(Outlet outlet:outlets){
							outletMap.put(outlet.getOutletId(), outlet);
						}*/
						List<Product> productsList = productService.getAllProductsByUuid(product.getProductUuid(),currentUser.getCompany().getCompanyId());
						if(!product.getVariantProducts().equalsIgnoreCase("true")){
							if(productsList!=null){
								for(Product productt:productsList){
									OutletBean outletBean = new OutletBean();
									Outlet tempOutlet = (Outlet)outletsMap.get(productt.getOutlet().getOutletId());
									outletBean.setOutletId(tempOutlet.getOutletId().toString());
									outletBean.setOutletName(tempOutlet.getOutletName());
									outletBean.setCurrentInventory(productt.getCurrentInventory().toString());
									outletBean.setOldInventory(productt.getCurrentInventory().toString());
									outletBean.setReorderAmount(productt.getReorderAmount().toString());
									outletBean.setReorderPoint(productt.getReorderPoint().toString());
									SalesTax salesTax = (SalesTax) salesTaxMap.get(tempOutlet.getSalesTax().getSalesTaxId());
//									SalesTax salesTax = salesTaxService.getSalesTaxBySalesTaxId(tempOutlet.getSalesTax().getSalesTaxId(), currentUser.getCompany().getCompanyId());
									outletBean.setSalesTaxId(salesTax.getSalesTaxId().toString());
									outletBean.setDefaultTax(salesTax.getSalesTaxPercentage().toString());
									outletBean.setSalesTaxName(salesTax.getSalesTaxName()+"("+salesTax.getSalesTaxPercentage().toString()+")");
									outletBeanList.add(outletBean);
								}
								productBean.setOutletBeans(outletBeanList);
							}
						}
						for(Product productt:productsList){
							List<ProductVariant> productVariantsList = productVariantService.getVarientsByProductId(productt.getProductId(),currentUser.getCompany().getCompanyId());
							if(productVariantsList!=null){

								for(ProductVariant productVariant:productVariantsList){
									if(productVariant.isActiveIndicator()){
										ProductVariantBean productVariantBean = new ProductVariantBean();
										productVariantBean.setProductVariantId(productVariant.getProductVariantId().toString());
										productVariantBean.setProductId(productVariant.getProduct().getProductId().toString());
										//productVariantBean.setProductName(product.getProductName());
										productVariantBean.setVariantAttributeName(productVariant.getVariantAttributeName());
										
										Outlet tempOutlet = (Outlet)outletsMap.get(productVariant.getOutlet().getOutletId());
										productVariantBean.setOutletId(tempOutlet.getOutletId().toString());
										productVariantBean.setOutletName(tempOutlet.getOutletName());
										productVariantBean.setSupplierCode(productt.getContact().getContactId().toString());
										productVariantBean.setSupplyPriceExclTax(productVariant.getSupplyPriceExclTax().toString());
										productVariantBean.setMarkupPrct(productVariant.getMarkupPrct().toString());
										//logger.info("variant markup: "+productVariant.getMarkupPrct().toString());
										/*BigDecimal retialPriceExclTax = (productVariant.getSupplyPriceExclTax().multiply(productVariant.getMarkupPrct().divide(new BigDecimal(100)))).add(productVariant.getSupplyPriceExclTax());
										retialPriceExclTax =retialPriceExclTax.setScale(2,RoundingMode.HALF_EVEN);
										productVariantBean.setRetailPriceExclTax(retialPriceExclTax.toString());*/
										BigDecimal retialPriceExclTax = (productVariant.getSupplyPriceExclTax().multiply(productVariant.getMarkupPrct().divide(new BigDecimal(100)))).add(productVariant.getSupplyPriceExclTax());
										//logger.info("retialPriceExclTax Before round: "+retialPriceExclTax);
										BigDecimal newNetPrice =retialPriceExclTax.setScale(2,RoundingMode.HALF_EVEN);
										//logger.info("retialPriceExclTax after round: "+newNetPrice);
										productVariantBean.setRetailPriceExclTax(newNetPrice.toString());
										productVariantBean.setSku(productVariant.getSku());
										productVariantBean.setProductVariantUuid(productVariant.getProductVariantUuid());

										productVariantBean.setVariantAttributeValue1(productVariant.getVariantAttributeValue1());
										productVariantBean.setVariantAttributeValue2(productVariant.getVariantAttributeValue2());
										productVariantBean.setVariantAttributeValue3(productVariant.getVariantAttributeValue3());
										if(productVariant.getVariantAttributeByVariantAttributeAssocicationId1()!=null){
											productVariantBean.setVariantAttributeId1(productVariant.getVariantAttributeByVariantAttributeAssocicationId1().getVariantAttributeId().toString());
										}
										if(productVariant.getVariantAttributeByVariantAttributeAssocicationId2()!=null){
											productVariantBean.setVariantAttributeId2(productVariant.getVariantAttributeByVariantAttributeAssocicationId2().getVariantAttributeId().toString());
										}
										if(productVariant.getVariantAttributeByVariantAttributeAssocicationId3()!=null){
											productVariantBean.setVariantAttributeId3(productVariant.getVariantAttributeByVariantAttributeAssocicationId3().getVariantAttributeId().toString());
										}


										if(productBean.getTrackingProduct().equalsIgnoreCase("true")){
											if(productVariant.getCurrentInventory()!=null){
												productVariantBean.setCurrentInventory(productVariant.getCurrentInventory().toString());

											}if(productVariant.getReorderPoint()!=null){
												productVariantBean.setReorderPoint(productVariant.getReorderPoint().toString());

											}if(productVariant.getReorderAmount()!=null){
												productVariantBean.setReorderAmount(productVariant.getReorderAmount().toString());

											}
										}

										productVariantBeanList.add(productVariantBean);
									}

								}
							}
						}
						if(productVariantBeanList.size()==0){
							productBean.setVarientProducts("false");
						}


					}else{
						List<CompositeProduct> compositeProducts = compositeProductService.getAllCompositeProductsByProductIdOultetIdCompanyId(product.getProductId(),Integer.valueOf(outletId),currentUser.getCompany().getCompanyId());
						if(compositeProducts!=null){
							//int id= 0;
							for(CompositeProduct compositeProduct :compositeProducts){
								if(compositeProduct.isActiveIndicator()){
									ProductVariant productVariant = productVariantService.getProductVariantByProductVariantId(compositeProduct.getProductVariant().getProductVariantId(),currentUser.getCompany().getCompanyId());
									CompositVariantBean compositVariantBean = new CompositVariantBean();
									compositVariantBean.setCompositeProductId(compositeProduct.getCompositeProductId().toString());
									compositVariantBean.setCompositQunatity(String.valueOf(compositeProduct.getCompositeQuantity()));
									compositVariantBean.setProductId(productVariant.getProduct().getProductId().toString());
									compositVariantBean.setProductName(product.getProductName());
									compositVariantBean.setProductVariantId(productVariant.getProductVariantId().toString());
									compositVariantBean.setProductVariantName(productVariant.getVariantAttributeName());
									//	compositVariantBean.setId((id++)+"");
									compositVariantBean.setUuid(compositeProduct.getCompositeProductUuid());
									compositProductCollection.add(compositVariantBean);
								}
							}
						}

					}

					productBean.setCompositProductCollection(compositProductCollection);
					productBean.setProductVariantsBeans(productVariantBeanList);

					List<ProductTag> productTags = productTagService.getAllProductTagsByProductId(product.getProductId(),currentUser.getCompany().getCompanyId());
					if(productTags!=null){
						for(ProductTag productTag:productTags){
							ProductTagBean productTagBean = new ProductTagBean();
							productTagBean.setProductTagId(productTag.getProductTagId().toString());
							productTagBean.setTagId(productTagBean.getProductTagId());
							productTagBean.setProductId(productId);
							Tag tag = (Tag) tagsMap.get(productTag.getTag().getTagId());
							productTagBean.setProductTagName(tag.getTagName());
							productTagBean.setTagName(productTagBean.getProductTagName());
							productTagBeanList.add(productTagBean);
						}
					}
					productBean.setProductTagBeanList(productTagBeanList);
					productBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(product.getCreatedDate()));

					util.AuditTrail(request, currentUser, "NewProductController.getProductDetailByProductId", 
							"User "+ currentUser.getUserEmail()+" retrived Product detail successfully ",false);
					return new Response(productBean, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "NewProductController.getProductDetailByProductId", 
							" Products detail not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NewProductController.getProductDetailByProductId",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}


	// Below method need to be clear from NewProductController


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/addSupplier/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response addSupplier(@PathVariable("sessionId") String sessionId,
			@RequestBody SupplierBean supplierBean, HttpServletRequest request) {
		List<SupplierBean> supplierBeans = new ArrayList<>();
		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {
				Contact supplier = new Contact();
				if (supplierBean != null) {

					supplier.setContactName(supplierBean.getSupplierName());
					supplier.setCompanyName(supplierBean.getCompanyName());
					supplier.setActiveIndicator(true);
					if (supplierBean.getDefaultMarkup() != null
							&& !supplierBean.getDefaultMarkup().isEmpty()) {
						supplier.setDefaultMarkup(new BigDecimal(supplierBean
								.getDefaultMarkup()));
					} else {
						supplier.setDefaultMarkup(new BigDecimal("0"));
					}
					supplier.setCompany(currentUser.getCompany());
					supplier.setContactType("SUPPLIER");
					supplier.setOutlet(currentUser.getOutlet());
					supplierService.addContact(supplier,currentUser.getCompany().getCompanyId());
					
				}

				Response response = getAllSuppliers(sessionId,request);

				if(response.status.equals(StatusConstants.SUCCESS)){
					supplierBeans = (List<SupplierBean>) response.data;
				}

				util.AuditTrail(request,currentUser,"NewProductController.addSupplier",
						"User " + currentUser.getUserEmail()+ " Added Supplier"+ supplierBean.getSupplierName()
						+ " successfully ", false);
				return new Response(supplierBeans,
						StatusConstants.SUCCESS, LayOutPageConstants.SUPPLIERS);

			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser,
						"NewProductController.addSupplier",
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllSuppliers/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getAllSuppliers(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {
		List<SupplierBean> supplierBeans = new ArrayList<>();
		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {
				List<Contact> suppliers = null;
				if(productControllerWrapper==null){
					 suppliers = supplierService.getAllContactsByCompanyIdContactType(currentUser.getCompany().getCompanyId(),"SUPPLIER");
//					List<Contact> suppliers =productControllerWrapper.getContactsList();
				}else{
//					List<Contact> suppliers = supplierService.getAllContactsByCompanyIdContactType(currentUser.getCompany().getCompanyId(),"SUPPLIER");
					suppliers =productControllerWrapper.getContactsList();
				}

				if (suppliers != null && suppliers.size() > 0) {
					for (Contact item : suppliers) {
						if(item.getContactType()!=null && item.getContactType().contains("SUPPLIER")){
							SupplierBean bean = new SupplierBean();
							bean.setSupplierName(item.getContactName());
							bean.setSupplierId(item.getContactId().toString());
							supplierBeans.add(bean);
							bean =  null;
						}
					}
					suppliers = null;
					return new Response(supplierBeans, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}

		} else {
			return new Response(MessageConstants.INVALID_SESSION,
					StatusConstants.INVALID, LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllProductTypes/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProductTypes(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<ProductTypeBean> productTypeBeanList = new ArrayList<>();
		List<ProductType> productTypeList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");


			try {
				if(productControllerWrapper==null){
					productTypeList = productTypeService.getAllProductTypes(currentUser.getCompany().getCompanyId());
//					productTypeList =productControllerWrapper.getProductTypeList();
	
				}else{
//					productTypeList = productTypeService.getAllProductTypes(currentUser.getCompany().getCompanyId());
					productTypeList =productControllerWrapper.getProductTypeList();

				}
				if (productTypeList != null) {
					for (ProductType productType : productTypeList) {

						ProductTypeBean productTypeBean = new ProductTypeBean();
						productTypeBean.setProductTypeId(productType.getProductTypeId().toString());
						productTypeBean.setProductTypeName(productType.getProductTypeName());						
						productTypeBean.setNumberOfProducts(String.valueOf(productService.getCountOfProductsByProductTypeId(productType.getProductTypeId(),currentUser.getCompany().getCompanyId())));
						productTypeBeanList.add(productTypeBean);

					}
					productTypeList = null;
					util.AuditTrail(request, currentUser, "NewProductController.getAllProductTypes", 
							"User "+ currentUser.getUserEmail()+" retrived all Product Types successfully ",false);
					return new Response(productTypeBeanList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "NewProductController.getAllProductTypes", 
							" Product Types are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NewProductController.getAllProductTypes",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/addBrand/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response addBrand(@PathVariable("sessionId") String sessionId,
			@RequestBody BrandBean brandBean, HttpServletRequest request) {

		List<BrandBean> brandBeanList= null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{

				Brand brand = new Brand();
				brand.setActiveIndicator(true);
				brand.setBrandDescription(brandBean.getBrandDescription());
				brand.setBrandName(brandBean.getBrandName());
				brand.setCreatedBy(currentUser.getUserId());
				brand.setCreatedDate(new Date());
				brand.setLastUpdated(new Date());
				brand.setUpdatedBy(currentUser.getUserId());
				brand.setCompany(currentUser.getCompany());
				brandService.addBrand(brand,0);

				Response response = getAllBrands(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					brandBeanList = (List<BrandBean>) response.data;
				}

				util.AuditTrail(request, currentUser, "NewProductController.addBrand", 
						"User "+ currentUser.getUserEmail()+" Added Brand +"+brandBean.getBrandName()+" successfully ",false);
				return new Response(brandBeanList,StatusConstants.SUCCESS,LayOutPageConstants.BRANDS);


			}catch(Exception e){
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NewProductController.addBrand",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllBrands/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllBrands(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<BrandBean> brandBeanList = new ArrayList<>();
		List<Brand> brandsList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");


			try {
				if(productControllerWrapper==null){
					brandsList = brandService.getAllBrands(currentUser.getCompany().getCompanyId());
//					brandsList = productControllerWrapper.getBrandsList();

				}else{
//					brandsList = brandService.getAllBrands(currentUser.getCompany().getCompanyId());
					brandsList = productControllerWrapper.getBrandsList();

				}
				if (brandsList != null) {
					for (Brand brand : brandsList) {

						BrandBean brandBean = new BrandBean();
						brandBean.setBrandId(brand.getBrandId().toString());
						brandBean.setBrandName(brand.getBrandName());
						brandBean.setBrandDescription(brand.getBrandDescription());
						brandBeanList.add(brandBean);
						brandBean = null;

					}
					brandsList = null;
					util.AuditTrail(request, currentUser, "NewProductController.getAllBrands", 
							"User "+ currentUser.getUserEmail()+" retrived all Brands successfully ",false);
					return new Response(brandBeanList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "NewProductController.getAllBrands", 
							" Brands are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "NewProductController.getAllBrands",
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
	@RequestMapping(value = "/getOutlets/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getOutlets(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<OutletBean> outletBeans = new ArrayList<>();
		List<Outlet> outlets = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				if(productControllerWrapper==null){
					outlets = outletService.getOutlets(currentUser.getCompany().getCompanyId());
//					outlets = productControllerWrapper.getOutlets();

				}else{
//					outlets = outletService.getOutlets(currentUser.getCompany().getCompanyId());
					outlets = productControllerWrapper.getOutlets();

				}
				if(outlets!=null){
					for(Outlet outlet:outlets){
						OutletBean outletBean = new OutletBean();
						//	List<RegisterBean> registerBeanList = new ArrayList<>();
						//	outletBean.setDefaultTax(outlet.getSelsTaxPercentage().toString());
						//outletBean.setDetails("");
						outletBean.setOutletId(outlet.getOutletId().toString());
						outletBean.setOutletName(outlet.getOutletName());
						SalesTax salesTax = salesTaxService.getSalesTaxBySalesTaxId(outlet.getSalesTax().getSalesTaxId(),currentUser.getCompany().getCompanyId());

						outletBean.setSalesTaxId(salesTax.getSalesTaxId().toString());
						outletBean.setSalesTaxName(salesTax.getSalesTaxName()+"("+salesTax.getSalesTaxPercentage().toString()+")");
						outletBean.setDefaultTax(salesTax.getSalesTaxPercentage().toString());
						outletBean.setOrderNumber(outlet.getOrderNumber());
						outletBean.setOrderNumberPrefix(outlet.getOrderNumberPrefix());
						outletBean.setSupplieNumberPrefix(outlet.getContactNumberPrefix());
						outletBean.setSupplierReturnNumber(outlet.getContactReturnNumber());
						if(outlet.getIsHeadOffice()!=null && outlet.getIsHeadOffice().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
							outletBean.setIsHeadOffice(ControllersConstants.TRUE);
						}else{
							outletBean.setIsHeadOffice(ControllersConstants.FALSE);
						}
						/*AddressBean  addressBean = new AddressBean();
						if(outlet.getAddress()!=null){
							Address address = addressService.getAddressByAddressId(outlet.getAddress().getAddressId(),currentUser.getCompany().getCompanyId());
							addressBean.setAddressId(address.getAddressId().toString());
							addressBean.setCity(address.getCity());
							addressBean.setPostalCode(address.getPostalCode());
							addressBean.setState(address.getState());
							addressBean.setEmail(address.getEmail());
							addressBean.setTwitter(address.getTwitter());
							addressBean.setStreet(address.getStreet());
							if(address.getCountry()!=null){
								Country country = countryService.getCountryByCountryId(address.getCountry().getCountryId());
								addressBean.setCountryId(country.getCountryId().toString());
								addressBean.setCountry(country.getCountryName());
							}
						}
						if(outlet.getTimeZone()!=null){
							TimeZone timeZone = timeZoneService.getTimeZoneByTimeZoneId(outlet.getTimeZone().getTimeZoneId());
							addressBean.setTimeZoneId(timeZone.getTimeZoneId().toString());
							addressBean.setTimeZone(timeZone.getTimeZoneValue());
						}
						outletBean.setAddressbean(addressBean);

						try {
							List<Register> regiserList = registerService.getRegestersByOutletId(outlet.getOutletId(),currentUser.getCompany().getCompanyId());
							if(regiserList!=null){

								if(regiserList!=null && regiserList.size()>0){
									for(Register register:regiserList){
										RegisterBean regiseBean = new RegisterBean();
										regiseBean.setCacheManagementEnable(register.getCacheManagementEnable());
										regiseBean.setEmailReceipt(register.getEmailReceipt());
										regiseBean.setNotes(register.getNotes());
										regiseBean.setOutletId(outlet.getOutletId().toString());
										regiseBean.setOutletName(outlet.getOutletName());
										regiseBean.setPrintNotesOnReceipt(register.getPrintNotesOnReceipt());
										regiseBean.setPrintReceipt(register.getPrintReceipt());
										regiseBean.setReceiptNumber(register.getReceiptNumber());
										regiseBean.setReceiptPrefix(register.getReceiptPrefix());
										regiseBean.setReceiptSufix(register.getReceiptSufix());
										regiseBean.setRegisterId(register.getRegisterId().toString());
										regiseBean.setRegisterName(register.getRegisterName());
										regiseBean.setSelectNextUserForSale(register.getSelectNextUserForSale());
										regiseBean.setShowDiscountOnReceipt(register.getShowDiscountOnReceipt());
										registerBeanList.add(regiseBean);

									}
								}

								outletBean.setRegisterBeanList(registerBeanList);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();logger.error(e.getMessage(),e);
						}*/

						outletBean.setStatus(String.valueOf(outlet.isActiveIndicator()));


						outletBeans.add(outletBean);
						outletBean = null;
					}
					outlets = null;
					return new Response(outletBeans,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}
				else{
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}


	@SuppressWarnings("unchecked")
	private Product processImage(ImageData data,Product product, HttpServletRequest request)
	{
		HttpSession session =  request.getSession(false);
		User currentUser = (User) session.getAttribute("user");
		String byteStr = data.getFile().substring(data.getFile().indexOf("base64,")+7, data.getFile().length());
		ServletContext servletContext = request.getSession().getServletContext();
		String relativeWebPath = "/resources/images/"+"Company_"+currentUser.getCompany().getCompanyId();
		Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
		Configuration configuration = configurationMap.get("DOCUMENTS_PATH");//configurationService.getConfigurationByPropertyNameByCompanyId("DOCUMENTS_PATH",currentUser.getCompany().getCompanyId());
		

		try {
			String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);

			new File(absoluteDiskPath).mkdir();
			absoluteDiskPath = absoluteDiskPath + "/"+product.getProductUuid()+".jpg";
			//byte[] fileData = Base64.decodeBase64(byteStr);
			util.AuditTrail(request, currentUser, "Upload Image to Live Server",
					"Upload Image path :"+absoluteDiskPath + " Context Path: "+servletContext.getContextPath()
					+ "servletContext.getRealPath() "+ servletContext.getRealPath(relativeWebPath),false);



			byte[] fileData = Base64.decodeBase64(byteStr);
			try (OutputStream stream = new FileOutputStream(absoluteDiskPath)) {
				stream.write(fileData);


			}
//			Configuration configuration = configurationService.getConfigurationByPropertyNameByCompanyId("DOCUMENTS_PATH",currentUser.getCompany().getCompanyId());
			util.AuditTrail(request, currentUser, "Backup of Image to Live Server",
					"Backup of Image path :"+configuration.getPropertyValue()+'/'+product.getProductUuid(),false);
			product.setImagePath("/app/resources/images/"+"Company_"+currentUser.getCompany().getCompanyId()+"/"+product.getProductUuid()+".jpg");

			String filepath = configuration.getPropertyValue() + "/Company_"+currentUser.getCompany().getCompanyId();
			new File(filepath).mkdir();
			OutputStream stream2 = new FileOutputStream( filepath+"/"+product.getProductUuid()+".jpg");
			stream2.write(fileData);
			stream2.close();

		} catch (Exception e) {
//			e.printStackTrace();logger.error(e.getMessage(),e);
			logger.info("Error of Upload Image at path:"+configuration.getPropertyValue() + "/Company_"+currentUser.getCompany().getCompanyId());
			StringWriter errors = new StringWriter();
//			e.printStackTrace(new PrintWriter(errors));
			util.AuditTrail(request, currentUser, "Error of Upload Image",
					"Error of Upload Image  :"+errors,true);

		}
		return product;
	}

	/*private void deleteProductsOnError(List<String> outletIds, List<String> sroductIds){

		for(String outletId: outletIds){
			for(String productId: sroductIds){

			}

		}
	}*/

	@SuppressWarnings("rawtypes")
	private boolean AddStockOrder(String sessionId, StockOrderBean stockOrderBean, List<StockOrderDetailBean> stockOrderDetailBeanList, Double grandTotal, Double itemCount, HttpServletRequest request)
	{
		boolean added = false;
		if(stockOrderDetailBeanList.size() > 0){				
			//Add StockOrder
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			StockOrderBean stockOrder = new StockOrderBean();
			stockOrder.setActiveIndicator("true");
			stockOrder.setAutofillReorder("true");
			stockOrder.setRetailPriceBill("false");
			stockOrder.setSupplierId(stockOrderBean.getSupplierId());
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int min = cal.get(Calendar.MINUTE);
			stockOrder.setDiliveryDueDate(dateFormat.format(new Date()));			
			stockOrder.setOrderNo("PO-"+ (month+"/"+ day+"/"+ year+ " " + hour + ":" + min));
			stockOrder.setOrdrRecvDate(dateFormat.format(new Date()));
			stockOrder.setStatusId("3"); 	// Completed		
			stockOrder.setStockOrderTypeId("1"); //Supplier Order
			stockOrder.setStockRefNo("PO-"+ (month+"/"+ day+"/"+ year+ " " + hour + ":" + min));
			stockOrder.setOutlet(stockOrderBean.getOutletId());
			//PurchaseOrderController purchaseOrderController = new PurchaseOrderController();
			Response response = purchaseOrderController.addStockOrder(sessionId, stockOrder, request);
			stockOrder.setStockOrderId(response.data.toString());
			//StockOrder Finish

			//StockOrderDetail Start
			for(StockOrderDetailBean stockOrderDetailBean : stockOrderDetailBeanList){
				stockOrderDetailBean.setStockOrderId(stockOrder.getStockOrderId());
				stockOrderDetailBean.setRecvProdQty(stockOrderDetailBean.getOrderProdQty());
				stockOrderDetailBean.setRecvSupplyPrice(stockOrderDetailBean.getOrdrSupplyPrice());
				stockOrderDetailBean.setIsDirty("true");
			}
			//PurchaseOrderDetailsController purchaseOrderDetailsController = new PurchaseOrderDetailsController();
			String total = grandTotal.toString();
			String items = itemCount.toString();
			purchaseOrderDetailsController.updateAndReceiveStockOrderDetails(sessionId, total, items, stockOrderDetailBeanList, request);
			//StockOrderDetail Finish
			added = true;
		}
		return added;
	}
	
	private ProductPriceHistory getProductPriceHistoryFromProductPriceHistoryBean(ProductPriceHistoryBean productPriceHistoryBean,Product newProduct){
		try{
			ProductPriceHistory productPriceHistory = new ProductPriceHistory();
			productPriceHistory.setActiveIndicator(true);
			productPriceHistory.setAdditionalCustomDutyPrct(new BigDecimal(productPriceHistoryBean.getAdditionalCustomDutyPrct()));
			productPriceHistory.setAdditionalCustomDutyValue(new BigDecimal(productPriceHistoryBean.getAdditionalCustomDutyValue()));
			productPriceHistory.setAdditionalSalesTaxPrct(new BigDecimal(productPriceHistoryBean.getAdditionalSalesTaxPrct()));
			productPriceHistory.setAdditionalSalesTaxValue(new BigDecimal(productPriceHistoryBean.getAdditionalSalesTaxValue()));
			productPriceHistory.setCoCode(productPriceHistoryBean.getCoCode());
			productPriceHistory.setCompany(newProduct.getCompany());
			productPriceHistory.setCreatedDate(new Date());
			productPriceHistory.setCustomDutyValue(new BigDecimal(productPriceHistoryBean.getCustomDutyValue()));
			productPriceHistory.setCustomDutyPrct(new BigDecimal(productPriceHistoryBean.getCustomDutyPrct()));
			productPriceHistory.setDescription(productPriceHistoryBean.getDescription());
			productPriceHistory.setDollarRate(new BigDecimal(productPriceHistoryBean.getDollarRate()));
			productPriceHistory.setDTotalValueAssessed(new BigDecimal(productPriceHistoryBean.getdTotalValueAssessed()));
			productPriceHistory.setDTotalValueDeclared(new BigDecimal(productPriceHistoryBean.getdTotalValueDeclared()));
			productPriceHistory.setDUnitValueAssessed(new BigDecimal(productPriceHistoryBean.getdUnitValueAssessed()));
			productPriceHistory.setDUnitValueDeclared(new BigDecimal(productPriceHistoryBean.getdUnitValueDeclared()));
			productPriceHistory.setExcisePrct(new BigDecimal(productPriceHistoryBean.getExcisePrct()));
			productPriceHistory.setExciseValue(new BigDecimal(productPriceHistoryBean.getExciseValue()));
			productPriceHistory.setFtaPrct(new BigDecimal(productPriceHistoryBean.getFtaPrct()));
			productPriceHistory.setFtaValue(new BigDecimal(productPriceHistoryBean.getFtaValue()));
			productPriceHistory.setHsCode(productPriceHistoryBean.getHsCode());
			productPriceHistory.setItPrct(new BigDecimal(productPriceHistoryBean.getItPrct()));
			productPriceHistory.setItValue(new BigDecimal(productPriceHistoryBean.getItValue()));
			productPriceHistory.setLastUpdated(newProduct.getLastUpdated());
			productPriceHistory.setMarkupPrct(newProduct.getMarkupPrct());
			productPriceHistory.setNumberOfUnits(new BigDecimal(productPriceHistoryBean.getNumberOfUnits()));
			productPriceHistory.setOutlet(newProduct.getOutlet());
			productPriceHistory.setPCustomValueAssessed(new BigDecimal(productPriceHistoryBean.getpCustomValueAssessed()));
			productPriceHistory.setPCustomValueDeclared(new BigDecimal(productPriceHistoryBean.getpCustomValueDeclared()));
			productPriceHistory.setProductId(newProduct.getProductId());
			productPriceHistory.setProductUuid(newProduct.getProductUuid());
			/*productPriceHistory.setProductVariantId(productVariantId);
			productPriceHistory.setProductVariantUuid(productVariantUuid);*/
			productPriceHistory.setRegularityDutyPrct(new BigDecimal(productPriceHistoryBean.getRegularityDutyPrct()));
			productPriceHistory.setRegularityDutyValue(new BigDecimal(productPriceHistoryBean.getRegularityDutyValue()));
			productPriceHistory.setRemarks(productPriceHistoryBean.getRemarks());
			productPriceHistory.setSalesTax(newProduct.getSalesTax());
			productPriceHistory.setSalesTaxPrct(new BigDecimal(productPriceHistoryBean.getSalesTaxPrct()));
			productPriceHistory.setSalesTaxValue(new BigDecimal(productPriceHistoryBean.getSalesTaxValue()));
			productPriceHistory.setSroNumber(productPriceHistoryBean.getSroNumber());
			productPriceHistory.setSupplyPriceExclTax(newProduct.getSupplyPriceExclTax());
			productPriceHistory.setTotalValue(new BigDecimal(productPriceHistoryBean.getTotalValue()));
			productPriceHistory.setUserByCreatedBy(newProduct.getUserByCreatedBy());
			productPriceHistory.setUserByUpdatedBy(newProduct.getUserByUpdatedBy());
			productPriceHistory.setVrNumber(productPriceHistoryBean.getVrNumber());
			productPriceHistory.setGrNumber(productPriceHistoryBean.getGrNumber());
			return productPriceHistory;
		}catch(Exception ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		
		
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllTags/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllTags(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<TagBean> tagBeanList = new ArrayList<>();
		List<Tag> tagsList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");


			try {
				if(productControllerWrapper==null){
					tagsList = tagService.getAllTags(currentUser.getCompany().getCompanyId());

				}else{
//					tagsList = tagService.getAllTags(currentUser.getCompany().getCompanyId());
					tagsList = productControllerWrapper.getTagsList();

				}
				if (tagsList != null) {
					for (Tag tag : tagsList) {

						TagBean tagBean = new TagBean();
						tagBean.setTagId(tag.getTagId().toString());
						tagBean.setTagName(tag.getTagName());
//						tagBean.setNumberOfProducts(String.valueOf(productTagService.getCountOfProductTagsByTagId(tag.getTagId(),currentUser.getCompany().getCompanyId())));
						tagBeanList.add(tagBean);
						tagBean = null;

					}
					tagsList = null;
					util.AuditTrail(request, currentUser, "ProductTagsController.getAllTags", 
							"User "+ currentUser.getUserEmail()+" retrived all Tags successfully ",false);
					return new Response(tagBeanList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "ProductTagsController.getAllTags", 
							" Tags are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ProductTagsController.getAllTags",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
	@SuppressWarnings({ "unchecked" })
	private void populateNewProductControllersMap(HttpServletRequest request){
		try{
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			logger.info("For User "+ currentUser.getUserEmail()+ " preparing product controller maps ");
			
			List<ProductVariant> productVariantsList = productControllerWrapper.getProductVariantList();
			if(productVariantsList!=null && productVariantsList.size()>0){
				for(ProductVariant productVariant:productVariantsList){
					productVariantMap.put(productVariant.getProductVariantId(), productVariant);
				}
			}
			List<Contact> suppliers =productControllerWrapper.getContactsList();
			if (suppliers != null && suppliers.size()>0) {
				for (Contact contact : suppliers) {
					if(contact.getContactType()!=null && contact.getContactType().contains("SUPPLIER")){
						contactsMap.put(contact.getContactId(), contact);
					}
				}
			}
				List<ProductType> productTypeList =productControllerWrapper.getProductTypeList();
				if (productTypeList != null) {
					for (ProductType productType : productTypeList) {
						productTypeMap.put(productType.getProductTypeId(), productType);

					}
				}
				List<Brand> brandsList = productControllerWrapper.getBrandsList();
				if (brandsList != null) {
					for (Brand brand : brandsList) {
						brandsMap.put(brand.getBrandId(), brand);

					}
				}
				List<Outlet> outlets = productControllerWrapper.getOutlets();
				if(outlets!=null){
					for(Outlet outlet:outlets){
						outletsMap.put(outlet.getOutletId(), outlet);
					}
					}
				List<VariantAttribute> variantAttributesList = productControllerWrapper.getVariantAttributesList();
				if (variantAttributesList != null) {
					for (VariantAttribute variantAttribute : variantAttributesList) {
						variantAttributesMap.put(variantAttribute.getVariantAttributeId(), variantAttribute);
					}
				}
				List<Tag> tagsList = productControllerWrapper.getTagsList();
				if (tagsList != null) {
					for (Tag tag : tagsList) {
						tagsMap.put(tag.getTagId(), tag);
					}
				}
				List<SalesTax> salesTaxList = productControllerWrapper.getSalesTaxlist();
				if(salesTaxList!=null && salesTaxList.size()>0){
					for(SalesTax salesTax:salesTaxList){
						salesTaxMap.put(salesTax.getSalesTaxId(), salesTax);
					}
				}
		}catch(Exception ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
	}
	public void initializeClassObjects(){
		logger.info("Inside method initializeClassObjects of NewProductController");
		products = null;
		productVariantBarCodeMap = new HashMap<>();
		variantAttributeMap = new HashMap<>();
		productControllerWrapper = null;
		productsMap = new HashMap<>();
		productVariantMap = new HashMap<>();
		contactsMap = new HashMap<>();
		productTypeMap = new HashMap<>();
		brandsMap = new HashMap<>();
		outletsMap = new HashMap<>();
		variantAttributesMap = new HashMap<>();
		tagsMap = new HashMap<>();
		salesTaxMap = new HashMap<>();
		salesTaxMap = new HashMap<>();
	}
	public void destroyClassObjects(){
		logger.info("Inside method destroyClassObjects of NewProductController");
		products = null;
		productVariantBarCodeMap  = null; 
		variantAttributeMap = null;
		productControllerWrapper = null;
		productsMap = null;
		productVariantMap = null;
		contactsMap = null;
		productTypeMap = null;
		brandsMap = null;
		outletsMap = null;
		variantAttributesMap = null;
		tagsMap = null;
		salesTaxMap = null;
	}

}

