package com.dowhile.angualrspringapp.controller;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

import com.dowhile.Configuration;
import com.dowhile.InventoryCountDetail;
import com.dowhile.InventoryCountDetailCustom;
import com.dowhile.Product;
import com.dowhile.ProductVariant;
import com.dowhile.User;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.InventoryCountControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.InventoryCountBean;
import com.dowhile.frontend.mapping.bean.InventoryCountDetailBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.ContactService;
import com.dowhile.service.InventoryCountDetailService;
import com.dowhile.service.InventoryCountService;
import com.dowhile.service.InventoryCountTypeService;
import com.dowhile.service.OutletService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductVariantService;
import com.dowhile.service.StatusService;
import com.dowhile.service.StockOrderService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;
import com.dowhile.wrapper.ProductListsWrapper;
 
/**
 * Zafar Shakeel
 */
@Controller
@RequestMapping("/inventoryCountEditDetails")

public class InventoryCountEditDetailsController {
	
	// private static Logger logger = Logger.getLogger(InventoryCountEditDetailsController.class.getName());
	@Resource
	private ServiceUtil util;
	@Resource
	private OutletService outletService;

	@Resource
	private StockOrderService stockOrderService;
	
	@Resource
	private ContactService supplierService;

	@Resource
	private InventoryCountService inventoryCountService;

	@Resource
	private StatusService statusService;

	@Resource
	private InventoryCountTypeService inventoryCountTypeService;

	@Resource
	private InventoryCountDetailService inventoryCountDetailService;	

	@Resource
	private ProductVariantService productVariantService;

	@Resource
	private ProductService productService;

	@Resource
	private ConfigurationService configurationService;

	private List<Product> productList; //outlet + Warehouse Products
	private List<ProductVariant> productVariantList; //outlet + Warehouse Product Variants
	//private Map<Integer, Product> warehouseProductMap = new HashMap<>(); //Warehouse Product Map
	//private Map<Integer, Product> productMap = new HashMap<>(); //outlet Products Map
	private Map<Integer, Product> compProductMap; // Outlet + Warehouse Product
	//private Map<Integer, ProductVariantBean> productVariantMap = new HashMap<>(); //outlet Product Variant Map
	//private Map<Integer, ProductVariantBean> warehouseProductVariantMap = new HashMap<>(); //Warehouse Product Map
	private Map<String, ProductVariantBean> productBeansSKUMap;
	private Map<String, ProductVariantBean> productVariantBeansSKUMap;
	private Map<String, ProductVariantBean> warehouseProductBeansSKUMap;
	private Map<String, ProductVariantBean> warehouseProductVariantBeansSKUMap;
	Map<Integer, ProductVariantBean> productVariantMap;
	Map<Integer, ProductVariantBean> productMap;
	private int headOfficeOutletId; //= 1;
	private int outletId;
	ProductListsWrapper productListsWrapper;
	
	@RequestMapping("/layout")
	public String getInventoryCountEditDetialsControllerPartialPage(ModelMap modelMap) {
		return "inventoryCountEditDetails/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getInventoryCountEditDetailsControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getInventoryCountEditDetailsControllerData(@PathVariable("sessionId") String sessionId,
			@RequestBody InventoryCountBean inventoryCountBean, HttpServletRequest request) {
		//List<ProductVariantBean> complProductBeansList = new ArrayList<>();
		List<ProductVariantBean> complProductVariantBeansList = new ArrayList<>();
		List<ProductVariantBean> complProductBeansList = new ArrayList<>();
		//List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
		List<ProductVariantBean> outletProductBeansList = new ArrayList<>();
		List<ProductVariantBean> outletProductVariantBeansList = new ArrayList<>();
		List<ProductVariantBean> warehouseProductBeansList = new ArrayList<>();
		List<ProductVariantBean> warehouseProductVariantBeansList = new ArrayList<>();
		List<InventoryCountDetailBean> inventoryCountDetailBeansList = null;
		productVariantMap = new HashMap<>();
		productMap = new HashMap<>();
		//productListsWrapper = new ProductListsWrapper();
		//Map<Integer, ProductVariantBean> productsMap = new HashMap<>();
		
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
			headOfficeOutletId = outletService.getHeadOfficeOutlet(currentUser.getCompany().getCompanyId()).getOutletId();
			outletId = currentUser.getOutlet().getOutletId();
			try {
				initializeClassObjects();
				if(outletId != headOfficeOutletId) {
					productListsWrapper = productService.getAllProductsWarehouseandOutlet(headOfficeOutletId, currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
				}else {
					productListsWrapper = productService.getAllProductsOutlet(outletId, currentUser.getCompany().getCompanyId());
				}
				Response response = getAllProductsByOutletId(sessionId, headOfficeOutletId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					//productBeansList = (List<ProductVariantBean>) response.data;
					complProductBeansList = (List<ProductVariantBean>) response.data;
					System.out.println("outlet + warehouse ProductBeansList size: " + complProductBeansList.size());
					//int outletId = currentUser.getOutlet().getOutletId();
					/*for(ProductVariantBean product:complProductBeansList){
						if(Integer.parseInt(product.getOutletId()) == outletId) {
							productBeansList.add(product);
						}
						productsMap.put(Integer.parseInt(product.getProductVariantId()), product); //in productVariantBean is Main Id 
					}*/					
				}				
				System.out.println("outlet + warehouse Product Map size: " + compProductMap.size());
				response = getProductVariantsByOutletId(sessionId, headOfficeOutletId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					//productVariantBeansList = (List<ProductVariantBean>) response.data;
					complProductVariantBeansList = (List<ProductVariantBean>) response.data;
					//int outletId = currentUser.getOutlet().getOutletId();
					/*for(ProductVariantBean productVariant:complProductVariantBeansList){
						if(Integer.parseInt(productVariant.getOutletId()) == outletId) {
							productVariantBeansList.add(productVariant);
						}
					}*/
				}				
				Configuration configuration = configurationMap.get("AUTO_AUDIT_TRANSFER");
				boolean autoTransfer = false;
				if(configuration != null ){
					if(configuration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
						autoTransfer = true;
					}
					else{
						autoTransfer = false;
					}
				}
				else{
					autoTransfer = false;
				}
				if(outletId == headOfficeOutletId){
					autoTransfer = false;
				}
				if(autoTransfer == true){
					for(ProductVariantBean product:complProductBeansList){
						if(Integer.parseInt(product.getOutletId()) == headOfficeOutletId) {
							warehouseProductBeansList.add(product);
						}else {
							outletProductBeansList.add(product);
						}
					}
					for(ProductVariantBean productVariant:complProductVariantBeansList){
						if(Integer.parseInt(productVariant.getOutletId()) == headOfficeOutletId) {
							warehouseProductVariantBeansList.add(productVariant);
						}else {
							outletProductVariantBeansList.add(productVariant);
						}
					}
					/*response = getAllProducts(sessionId, request);
					if(response.status.equals(StatusConstants.SUCCESS)){
						allProductBeansList = (List<ProductVariantBean>) response.data;
					}
					response = getAllProductVariants(sessionId, request);
					if(response.status.equals(StatusConstants.SUCCESS)){
						allProductVariantBeansList = (List<ProductVariantBean>) response.data;
					}	*/				
				}else {
					for(ProductVariantBean product:complProductBeansList){
						outletProductBeansList.add(product);
					}
					for(ProductVariantBean productVariant:complProductVariantBeansList){
						outletProductVariantBeansList.add(productVariant);
					}
				}
				response = getAllDetailsByInventoryCountIdCustom(sessionId, inventoryCountBean, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					if(response.data != null) {
						inventoryCountDetailBeansList = (List<InventoryCountDetailBean>) response.data;
					}
				}
				InventoryCountControllerBean inventoryCountControllerBean = new InventoryCountControllerBean();
				inventoryCountControllerBean.setProductBeansList(outletProductBeansList); // outlet Products Beans
				System.out.println("outletProductBeansList size: " + outletProductBeansList.size());
				inventoryCountControllerBean.setProductVariantBeansList(outletProductVariantBeansList); //outlet Product Variant Beans
				System.out.println("outletProductVariantBeansList size: " + outletProductVariantBeansList.size());
				inventoryCountControllerBean.setInventoryCountDetailBeansList(inventoryCountDetailBeansList);
				//System.out.println("inventoryCountDetailBeansList size: " + inventoryCountDetailBeansList.size());
				if(outletId != headOfficeOutletId) {
					inventoryCountControllerBean.setAllProductBeansList(warehouseProductBeansList); // Warehouse Products Beans
					System.out.println("allProductBeansList size: " + warehouseProductBeansList.size());
					inventoryCountControllerBean.setAllProductVariantBeansList(warehouseProductVariantBeansList); //warehouse ProductVariant Beans
					System.out.println("allProductVariantBeansList size: " + warehouseProductVariantBeansList.size());
				}
				inventoryCountControllerBean.setProductVariantMap(productVariantBeansSKUMap); // outlet ProductVariants
				System.out.println("productVariantMap size: " + productVariantBeansSKUMap.size());
				inventoryCountControllerBean.setProductMap(productBeansSKUMap); //outlet Products
				System.out.println("productMap size: " + productBeansSKUMap.size());
				if(outletId != headOfficeOutletId) {
					inventoryCountControllerBean.setAllProductMap(warehouseProductBeansSKUMap); // Warehouse Products
					System.out.println("allProductMap size: " + warehouseProductBeansSKUMap.size());
					inventoryCountControllerBean.setAllProductVariantMap(warehouseProductVariantBeansSKUMap); // Warehouse ProductVariants
					System.out.println("allProductVariantMap size: " + warehouseProductVariantBeansSKUMap.size());
				}
				destroyClassObjects();
				util.AuditTrail(request, currentUser, "InventoryCountController.getInventoryCountControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived InventoryCountControllerData successfully ",false);
				return new Response(inventoryCountControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountDetailsController.getInventoryCountControllerData",
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
	@RequestMapping(value = "/getAllProductsByOutletId/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProductsByOutletId(@PathVariable("sessionId") String sessionId, int warehousOutlletId, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			productList = new  ArrayList<>();
			//productMap = new HashMap<>();
			compProductMap = new HashMap<>();
			try {			
				productList.addAll(productListsWrapper.getOutletProducts());
				System.out.println("Product size: " + productList.size());
				if(outletId != headOfficeOutletId) {
					productList.addAll(productListsWrapper.getWarehouseProducts());
					System.out.println("Product Warehosue Added size: " + productList.size());
				}
				//productList = productService.getAllProductsWarehouseandOutlet(warehousOutlletId, currentUser.getOutlet().getOutletId() ,currentUser.getCompany().getCompanyId());
				if(productList != null){
					for(Product product:productList){
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
							if(product.getOutlet().getOutletId() == currentUser.getOutlet().getOutletId()) {
								productBeansSKUMap.put(product.getSku().toLowerCase(), productVariantBean);
							}
							if(outletId != headOfficeOutletId) {
								if(product.getOutlet().getOutletId() == warehousOutlletId) {
									productVariantBean.setAuditTransfer("true");
									productVariantBean.setCurrentInventory("0");
									warehouseProductBeansSKUMap.put(product.getSku().toLowerCase(), productVariantBean);
								}
							}
							productMap.put(product.getProductId(), productVariantBean);
							productVariantBeansList.add(productVariantBean);
						}		
						compProductMap.put(product.getProductId(), product);
					}
					util.AuditTrail(request, currentUser, "InventoryCountDetails.getAllProducts", "User "+ 
							currentUser.getUserEmail()+" Get Products and Products",false);
					return new Response(productVariantBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} 
				else {
					util.AuditTrail(request, currentUser, "InventoryCountDetails.getAllProducts", "User "+ 
							currentUser.getUserEmail()+" Get Products",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.getAllProducts",
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

	/*@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllProducts/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProducts(@PathVariable("sessionId") String sessionId, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			productList = null;
			allProductMap = new HashMap<>();
			try {			 
				productList = productService.getAllProductsByOutletIdByCompanyIdGroupByProductUuId(headOfficeOutletId, currentUser.getCompany().getCompanyId());
				if(productList != null){
					for(Product product:productList){
						ProductVariantBean productVariantBean = new ProductVariantBean();
						productVariantBean.setProductId(product.getProductId().toString());
						productVariantBean.setOutletId(product.getOutlet().getOutletId().toString());
						productVariantBean.setSku(product.getSku());
						productVariantBean.setAuditTransfer("true");
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
								Double retailPrice = product.getSupplyPriceExclTax().doubleValue() * (product.getMarkupPrct().doubleValue()/100) + product.getSupplyPriceExclTax().doubleValue();
								NumberFormat formatter = new DecimalFormat("###.##");  
								String strRetailPrice = formatter.format(retailPrice);
								productVariantBean.setRetailPriceExclTax(strRetailPrice);
								BigDecimal netPrice = (product.getSupplyPriceExclTax().multiply(product.getMarkupPrct().divide(new BigDecimal(100)))).add(product.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
								BigDecimal retailPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
								productVariantBean.setRetailPriceExclTax(retailPrice.toString());
							}
							productVariantBeansList.add(productVariantBean);
							allProductMap.put(product.getSku().toLowerCase(), productVariantBean);
						}
						
					}
					util.AuditTrail(request, currentUser, "InventoryCountDetails.getAllProducts", "User "+ 
							currentUser.getUserEmail()+" Get Products and Products",false);
					return new Response(productVariantBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} 
				else {
					util.AuditTrail(request, currentUser, "InventoryCountDetails.getAllProducts", "User "+ 
							currentUser.getUserEmail()+" Get Products",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.getAllProducts",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}
		else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}	
	}*/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getProductVariantsByOutletId/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getProductVariantsByOutletId(@PathVariable("sessionId") String sessionId, int warehouseOutletId, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			//productVariantMap = new HashMap<>();
			productVariantList = new ArrayList<>();
			try {			
				//productVariantList = productVariantService.getAllProductVariantsWarehouseandOutlet(warehouseOutletId, currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
				productVariantList.addAll(productListsWrapper.getOutletProductVariants());
				System.out.println("outlet ProductVariant size: " + productVariantList.size());
				if(outletId != headOfficeOutletId) {
					productVariantList.addAll(productListsWrapper.getWarehouseProductVariants());
					System.out.println("Warehouse ProductVariant size: " + productVariantList.size());
				}
				/*Map<Integer, Product> productsMap1 = new HashMap<>();
				List<Product> products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
				if(products!=null){
					for(Product product:products){
						productsMap1.put(product.getProductId(), product);
					}
				}*/
				if(productVariantList != null){
					for(ProductVariant productVariant:productVariantList){
						ProductVariantBean productVariantBean = new ProductVariantBean();
						productVariantBean.setIsProduct("false");
						productVariantBean.setIsVariant("true");
						productVariantBean.setSku(productVariant.getSku());
						productVariantBean.setProductVariantId(productVariant.getProductVariantId().toString());
						productVariantBean.setOutletId(productVariant.getOutlet().getOutletId().toString());
						if(productVariant.getCurrentInventory() != null){
							productVariantBean.setCurrentInventory(productVariant.getCurrentInventory().toString());
						}
						else
						{
							productVariantBean.setCurrentInventory("0");
						}
						//Product product = productsMap1.get(productVariant.getProduct().getProductId());
						Product product = compProductMap.get(productVariant.getProduct().getProductId());
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
						//productVariantMap.put(productVariant.getSku().toLowerCase(), productVariantBean);
						if(productVariant.getOutlet().getOutletId() == currentUser.getOutlet().getOutletId()) {
							productVariantBeansSKUMap.put(productVariant.getSku().toLowerCase(), productVariantBean);
						}
						if(outletId != headOfficeOutletId) {
							if(productVariant.getOutlet().getOutletId() == warehouseOutletId) {
								productVariantBean.setAuditTransfer("true");
								productVariantBean.setCurrentInventory("0");
								warehouseProductVariantBeansSKUMap.put(productVariant.getSku().toLowerCase(), productVariantBean);
							}
						}
						productVariantMap.put(productVariant.getProductVariantId(), productVariantBean);
						productVariantBeansList.add(productVariantBean);
					}
					util.AuditTrail(request, currentUser, "InventoryCountDetails.getProductVariants", "User "+ 
							currentUser.getUserEmail()+" Get ProductVariants",false);
					return new Response(productVariantBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} 
				else {
					util.AuditTrail(request, currentUser, "InventoryCountDetails.getProductVariants", "User "+ 
							currentUser.getUserEmail()+" Get ProductVariants",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.getProductVariants",
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

	/*@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllProductVariants/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProductVariants(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			allProductVariantMap = new HashMap<>();
			productVariantList = null;
			try {			
				productVariantList = productVariantService.getAllProductVariantsByOutletIdGroupbyUuid(headOfficeOutletId,currentUser.getCompany().getCompanyId());
				Map<Integer, Product> productsMap = new HashMap<>();
				List<Product> products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
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
						productVariantBean.setAuditTransfer("true");
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
							Double retailPrice = productVariant.getSupplyPriceExclTax().doubleValue() * (productVariant.getMarkupPrct().doubleValue()/100) + productVariant.getSupplyPriceExclTax().doubleValue();
							NumberFormat formatter = new DecimalFormat("###.##");  
							String strRetailPrice = formatter.format(retailPrice);
							productVariantBean.setRetailPriceExclTax(strRetailPrice);
							BigDecimal netPrice = (productVariant.getSupplyPriceExclTax().multiply(productVariant.getMarkupPrct().divide(new BigDecimal(100)))).add(productVariant.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
							BigDecimal retailPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
							productVariantBean.setRetailPriceExclTax(retailPrice.toString());
						}
						productVariantBeansList.add(productVariantBean);
						allProductVariantMap.put(productVariant.getSku().toLowerCase(), productVariantBean);
					}
					util.AuditTrail(request, currentUser, "InventoryCountDetails.getProductVariants", "User "+ 
							currentUser.getUserEmail()+" Get ProductVariants",false);
					return new Response(productVariantBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} 
				else {
					util.AuditTrail(request, currentUser, "InventoryCountDetails.getProductVariants", "User "+ 
							currentUser.getUserEmail()+" Get ProductVariants",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.getProductVariants",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}
		else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}	
	}*/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllDetailsByInventoryCountId/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllDetailsByInventoryCountId(@PathVariable("sessionId") String sessionId,
			@RequestBody InventoryCountBean inventoryCountBean, HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			List<InventoryCountDetailBean> inventoryCountDetailBeansList = new ArrayList<>();
			List<InventoryCountDetail> inventoryCountDetailList = null;
			Map<Integer, Product> productsMap = new HashMap<>();
			Map<Integer, ProductVariant> productVariantsMap = new HashMap<>();
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				inventoryCountDetailList = inventoryCountDetailService.getInventoryCountDetailByInventoryCountId(Integer.parseInt(inventoryCountBean.getInventoryCountId()),currentUser.getCompany().getCompanyId());
				int order = 1;
				NumberFormat formatter = new DecimalFormat("###.##");  
				if (inventoryCountDetailList != null) {
					productList = productService.getAllProducts(currentUser.getCompany().getCompanyId());
					if(productList != null){
						for(Product product:productList){
							productsMap.put(product.getProductId(), product);
						}
					}
					productVariantList = productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
					if(productVariantList != null){
						for(ProductVariant productVariant:productVariantList){
							productVariantsMap.put(productVariant.getProductVariantId(), productVariant);
							//Product product = productsMap.get(productVariant.getProduct().getProductId());	
						}
					}
					for (InventoryCountDetail inventoryCountDetail : inventoryCountDetailList) {
						InventoryCountDetailBean inventoryCountDetailBean = new InventoryCountDetailBean();
						if(inventoryCountDetail.getCountDiff() != null){
							inventoryCountDetailBean.setCountDiff(String.valueOf(inventoryCountDetail.getCountDiff()));
						}
						if(inventoryCountDetail.getCountedProdQty() != null){
							inventoryCountDetailBean.setCountedProdQty(String.valueOf(inventoryCountDetail.getCountedProdQty()));
						}
						if(inventoryCountDetail.getCreatedDate() != null){
							inventoryCountDetailBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(inventoryCountDetail.getCreatedDate()));
						}
						if(inventoryCountDetail.getExpectedProdQty() != null){
							inventoryCountDetailBean.setExpProdQty(String.valueOf(inventoryCountDetail.getExpectedProdQty()));
						}
						if(inventoryCountDetail.getInventoryCountDetailId() != null){
							inventoryCountDetailBean.setInventoryCountDetailId(String.valueOf(inventoryCountDetail.getInventoryCountDetailId()));
						}
						if(inventoryCountDetail.getInventoryCount() != null){
							inventoryCountDetailBean.setInventoryCountId(String.valueOf(inventoryCountDetail.getInventoryCount().getInventoryCountId()));
						}
						inventoryCountDetailBean.setIsProduct(String.valueOf(inventoryCountDetail.isIsProduct()));
						if(inventoryCountDetail.getPriceDiff() != null){
							inventoryCountDetailBean.setPriceDiff(formatter.format(inventoryCountDetail.getPriceDiff()));
						}
						if(inventoryCountDetail.isIsProduct() == true){
							int productId = inventoryCountDetail.getProduct().getProductId();
							if(productId != 0){
								inventoryCountDetailBean.setProductVariantId(String.valueOf(productId));
								if(productsMap.get(productId) != null){
									inventoryCountDetailBean.setVariantAttributeName(productsMap.get(productId).getProductName());
								}
							}
						}
						else{
							int productVariantId = inventoryCountDetail.getProductVariant().getProductVariantId();
							if(productVariantId != 0){
								inventoryCountDetailBean.setProductVariantId(String.valueOf(productVariantId));
								ProductVariant productVariant= productVariantsMap.get(productVariantId);
								Product product = productsMap.get(productVariant.getProduct().getProductId());
								if(productVariant != null){
									if(product != null){
									inventoryCountDetailBean.setVariantAttributeName(product.getProductName() + "-" + productVariant.getVariantAttributeName());
									}
									else{
										inventoryCountDetailBean.setVariantAttributeName(productVariant.getVariantAttributeName());
									}
								}
							}
						}
						if(inventoryCountDetail.getRetailPriceCounted() != null){
							inventoryCountDetailBean.setRetailPriceCounted(formatter.format(inventoryCountDetail.getRetailPriceCounted()));
						}
						if(inventoryCountDetail.getRetailPriceExp() != null){
							inventoryCountDetailBean.setRetailPriceExp(formatter.format(inventoryCountDetail.getRetailPriceExp()));
						}
						if(inventoryCountDetail.getSupplyPriceCounted() != null){
							inventoryCountDetailBean.setSupplyPriceCounted(String.valueOf(inventoryCountDetail.getSupplyPriceCounted()));
						}
						if(inventoryCountDetail.getSupplyPriceExp() != null){
							inventoryCountDetailBean.setSupplyPriceExp(formatter.format(inventoryCountDetail.getSupplyPriceExp()));
						}
						if(inventoryCountDetail.getAuditTransfer() != null){
							if(inventoryCountDetail.getAuditTransfer().intValue() == 1){
								inventoryCountDetailBean.setAuditTransfer("true");	
							}
							else{
								inventoryCountDetailBean.setAuditTransfer("false");
							}

						}			
						else{
							inventoryCountDetailBean.setAuditTransfer("false");
						}
						inventoryCountDetailBean.setOrder(String.valueOf(order));
						order++;
						inventoryCountDetailBeansList.add(inventoryCountDetailBean);
					}
					util.AuditTrail(request, currentUser, "PurchaseOrderEditProductsController.getAllDetailsByInventoryCountId", "User "+ 
							currentUser.getUserEmail()+" Get All Stock Order Details",false);
					return new Response(inventoryCountDetailBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "PurchaseOrderEditProductsController.getAllDetailsByInventoryCountId", "User "+ 
							currentUser.getUserEmail()+"  Get All Stock Order Details",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderActionsController.getAllDetailsByInventoryCountId",
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
	@RequestMapping(value = "/getAllDetailsByInventoryCountIdCustom/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllDetailsByInventoryCountIdCustom(@PathVariable("sessionId") String sessionId,
			@RequestBody InventoryCountBean inventoryCountBean, HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			List<InventoryCountDetailBean> inventoryCountDetailBeansList = new ArrayList<>();
			List<InventoryCountDetailCustom> inventoryCountDetailCustomList = null;
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				inventoryCountDetailCustomList = inventoryCountDetailService.getInventoryCountDetailByInventoryCountIdCustom(Integer.parseInt(inventoryCountBean.getInventoryCountId()),currentUser.getCompany().getCompanyId());
				if(inventoryCountDetailCustomList != null) {
					int order = 1;
					for (InventoryCountDetailCustom ic : inventoryCountDetailCustomList) {
						InventoryCountDetailBean i = new InventoryCountDetailBean();
						if(ic.getAudit_transfer() == 0) {
							i.setAuditTransfer("false");
						}
						else {
							i.setAuditTransfer("true");
						}
						if(ic.isIS_PRODUCT() == true) {
							ProductVariantBean pv = productMap.get(ic.getPRODUCT_ASSOCIATION_ID());
							i.setRetailPriceExclTax(pv.getRetailPriceExclTax());
							i.setSupplyPriceExclTax(pv.getSupplyPriceExclTax());
						}else {
							ProductVariantBean pv = productVariantMap.get(ic.getPRODUCT_VARIANT_ASSOCICATION_ID());	
							i.setRetailPriceExclTax(pv.getRetailPriceExclTax());
							i.setSupplyPriceExclTax(pv.getSupplyPriceExclTax());
						}						 
						i.setCountDiff(Objects.toString(ic.getCOUNT_DIFF()));
						i.setCreatedBy(Objects.toString(ic.getCREATED_BY()));
						i.setCountedProdQty(Objects.toString(ic.getCOUNTED_PROD_QTY()));
						i.setExpProdQty(Objects.toString(ic.getEXPECTED_PROD_QTY()));
						i.setInventoryCountDetailId(Objects.toString(ic.getINVENTORY_COUNT_DETAIL_ID()));
						i.setInventoryCountId(Objects.toString(ic.getINVENTORY_COUNT_ASSOCICATION_ID()));
						if(!ic.isIS_PRODUCT()) {
							i.setIsProduct("false");
							i.setProductVariantId(Objects.toString(ic.getPRODUCT_VARIANT_ASSOCICATION_ID()));
							i.setVariantAttributeName(Objects.toString(ic.getVariantAttributeName()));
						}else {
							i.setIsProduct("true");
							i.setProductId(Objects.toString(ic.getPRODUCT_ASSOCIATION_ID()));
							i.setVariantAttributeName(Objects.toString(ic.getProduct_name()));							
						}
						i.setPriceDiff(Objects.toString(ic.getPRICE_DIFF()));
						i.setRetailPriceCounted(Objects.toString(ic.getRETAIL_PRICE_COUNTED()));
						i.setRetailPriceExp(Objects.toString(ic.getRETAIL_PRICE_EXP()));
						i.setSupplyPriceCounted(Objects.toString(ic.getSUPPLY_PRICE_COUNTED()));
						i.setSupplyPriceExp(Objects.toString(ic.getSUPPLY_PRICE_EXP()));
						i.setOrder(String.valueOf(order));
						order++;
						inventoryCountDetailBeansList.add(i);
					}
					util.AuditTrail(request, currentUser, "PurchaseOrderEditProductsController.getAllDetailsByInventoryCountId", "User "+ 
							currentUser.getUserEmail()+" Get All Stock Order Details",false);
					return new Response(inventoryCountDetailBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "PurchaseOrderEditProductsController.getAllDetailsByInventoryCountId", "User "+ 
							currentUser.getUserEmail()+"  Get All Stock Order Details",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderActionsController.getAllDetailsByInventoryCountId",
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
	
	/**
	 * @return the productList
	 */
	public List<Product> getProductList() {
		return productList;
	}

	/**
	 * @param productList the productList to set
	 */
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	/**
	 * @return the productVariantList
	 */
	public List<ProductVariant> getProductVariantList() {
		return productVariantList;
	}

	/**
	 * @param productVariantList the productVariantList to set
	 */
	public void setProductVariantList(List<ProductVariant> productVariantList) {
		this.productVariantList = productVariantList;
	}
	
	/*public Map getProductMap() {
		return productMap;
	}

	public void setProdutMap(Map produtMap) {
		this.productMap = produtMap;
	}*/
	
	public void initializeClassObjects(){
		System.out.println("Inside method initializeClassObjects of InventoryCountDetailController");
		productList = new ArrayList<>(); //outlet + Warehouse Products
		productVariantList = new ArrayList<>(); //outlet + Warehouse Product Variants
		compProductMap = new HashMap<>(); // Outlet + Warehouse Product
		productBeansSKUMap  = new HashMap<>();
		productVariantBeansSKUMap = new HashMap<>();
		warehouseProductBeansSKUMap = new HashMap<>();
		warehouseProductVariantBeansSKUMap = new HashMap<>();
		productListsWrapper = new ProductListsWrapper();
	}
	public void destroyClassObjects(){
		System.out.println("Inside method destroyClassObjects of InventoryCountDetailController");
		productList = null;; //outlet + Warehouse Products
		productVariantList = null; //outlet + Warehouse Product Variants
		compProductMap = null; // Outlet + Warehouse Product
		productBeansSKUMap  = null;
		productVariantBeansSKUMap = null;
		warehouseProductBeansSKUMap = null;
		warehouseProductVariantBeansSKUMap = null;
		productListsWrapper = null;
	}
}