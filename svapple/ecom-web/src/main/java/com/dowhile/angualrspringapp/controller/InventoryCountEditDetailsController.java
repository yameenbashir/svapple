package com.dowhile.angualrspringapp.controller;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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
import com.dowhile.Product;
import com.dowhile.ProductVariant;
import com.dowhile.Status;
import com.dowhile.InventoryCount;
import com.dowhile.InventoryCountDetail;
import com.dowhile.InventoryCountType;
import com.dowhile.InventoryCountDetail;
import com.dowhile.User;
import com.dowhile.constant.Actions;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.POCreateandReceiveControllerBean;
import com.dowhile.controller.bean.InventoryCountControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.frontend.mapping.bean.InventoryCountBean;
import com.dowhile.frontend.mapping.bean.InventoryCountDetailBean;
import com.dowhile.frontend.mapping.bean.InventoryCountTypeBean;
import com.dowhile.frontend.mapping.bean.InventoryCountBean;
import com.dowhile.frontend.mapping.bean.InventoryCountDetailBean;
import com.dowhile.frontend.mapping.bean.InventoryCountTypeBean;
import com.dowhile.frontend.mapping.bean.SupplierBean;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.ContactService;
import com.dowhile.service.OutletService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductVariantService;
import com.dowhile.service.StatusService;
import com.dowhile.service.InventoryCountDetailService;
import com.dowhile.service.InventoryCountService;
import com.dowhile.service.InventoryCountTypeService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * Zafar Shakeel
 */
@Controller
@RequestMapping("/inventoryCountEditDetails")

public class InventoryCountEditDetailsController {
	@Resource
	private ServiceUtil util;
	@Resource
	private OutletService outletService;

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

	private List<Product> productList;
	private List<ProductVariant> productVariantList;

	@RequestMapping("/layout")
	public String getInventoryCountEditDetialsControllerPartialPage(ModelMap modelMap) {
		return "inventoryCountEditDetails/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getInventoryCountEditDetailsControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getInventoryCountEditDetailsControllerData(@PathVariable("sessionId") String sessionId,
			@RequestBody InventoryCountBean inventoryCountBean, HttpServletRequest request) {

		List<OutletBean> outletBeansList = null;
		List<InventoryCountTypeBean> inventoryCountTypeBeansList= null;
		List<ProductVariantBean> productBeansList = null;
		List<ProductVariantBean> productVariantBeansList = null;
		List<ProductVariantBean> allProductBeansList = null;
		List<ProductVariantBean> allProductVariantBeansList = null;
		List<InventoryCountDetailBean> inventoryCountDetailBeansList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
			try {
				Response response = getAllProductsByOutletId(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					productBeansList = (List<ProductVariantBean>) response.data;
				}				
				response = getProductVariantsByOutletId(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					productVariantBeansList = (List<ProductVariantBean>) response.data;
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
				int headOfficeOutletId = outletService.getHeadOfficeOutlet(currentUser.getCompany().getCompanyId()).getOutletId();
				int outletId = currentUser.getOutlet().getOutletId();
				if(outletId == headOfficeOutletId){
					autoTransfer = false;
				}
				if(autoTransfer == true){
					response = getAllProducts(sessionId, request);
					if(response.status.equals(StatusConstants.SUCCESS)){
						allProductBeansList = (List<ProductVariantBean>) response.data;
					}
					response = getAllProductVariants(sessionId, request);
					if(response.status.equals(StatusConstants.SUCCESS)){
						allProductVariantBeansList = (List<ProductVariantBean>) response.data;
					}					
				}
				response = getAllDetailsByInventoryCountId(sessionId, inventoryCountBean, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					inventoryCountDetailBeansList = (List<InventoryCountDetailBean>) response.data;
				}
				InventoryCountControllerBean inventoryCountControllerBean = new InventoryCountControllerBean();
				inventoryCountControllerBean.setOutletBeansList(outletBeansList);
				inventoryCountControllerBean.setInventoryCountTypeBeansList(inventoryCountTypeBeansList);
				inventoryCountControllerBean.setProductBeansList(productBeansList);
				inventoryCountControllerBean.setProductVariantBeansList(productVariantBeansList);
				inventoryCountControllerBean.setAllProductBeansList(allProductBeansList);
				inventoryCountControllerBean.setAllProductVariantBeansList(allProductVariantBeansList);
				inventoryCountControllerBean.setInventoryCountDetailBeansList(inventoryCountDetailBeansList);
				util.AuditTrail(request, currentUser, "InventoryCountEditDetailsController.getInventoryCountEditDetailsControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived getInventoryCountEditDetailsControllerData successfully ",false);
				return new Response(inventoryCountControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountEditDetailsController.getInventoryCountEditDetailsControllerData",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}

	public @ResponseBody Response getAllProductsByOutletId(@PathVariable("sessionId") String sessionId, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			productList = null;
			try {			
				productList = productService.getAllProductsByOutletIdByCompanyIdGroupByProductUuId(currentUser.getOutlet().getOutletId() ,currentUser.getCompany().getCompanyId());
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
							productVariantBeansList.add(productVariantBean);
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
				e.printStackTrace();logger.error(e.getMessage(),e);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllProducts/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProducts(@PathVariable("sessionId") String sessionId, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			productList = null;
			try {			
				productList = productService.getAllProducts(currentUser.getCompany().getCompanyId());
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
								/*Double retailPrice = product.getSupplyPriceExclTax().doubleValue() * (product.getMarkupPrct().doubleValue()/100) + product.getSupplyPriceExclTax().doubleValue();
								NumberFormat formatter = new DecimalFormat("###.##");  
								String strRetailPrice = formatter.format(retailPrice);
								productVariantBean.setRetailPriceExclTax(strRetailPrice);*/
								BigDecimal netPrice = (product.getSupplyPriceExclTax().multiply(product.getMarkupPrct().divide(new BigDecimal(100)))).add(product.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
								BigDecimal retailPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
								productVariantBean.setRetailPriceExclTax(retailPrice.toString());
							}
							productVariantBeansList.add(productVariantBean);
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
				e.printStackTrace();logger.error(e.getMessage(),e);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getProductVariantsByOutletId/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getProductVariantsByOutletId(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			productVariantList = null;
			try {			
				productVariantList = productVariantService.getAllProductVariantsByOutletIdGroupbyUuid(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
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
							/*Double retailPrice = productVariant.getSupplyPriceExclTax().doubleValue() * (productVariant.getMarkupPrct().doubleValue()/100) + productVariant.getSupplyPriceExclTax().doubleValue();
							NumberFormat formatter = new DecimalFormat("###.##");  
							String strRetailPrice = formatter.format(retailPrice);
							productVariantBean.setRetailPriceExclTax(strRetailPrice);*/
							BigDecimal netPrice = (productVariant.getSupplyPriceExclTax().multiply(productVariant.getMarkupPrct().divide(new BigDecimal(100)))).add(productVariant.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
							BigDecimal retailPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
							productVariantBean.setRetailPriceExclTax(retailPrice.toString());
						}
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
				e.printStackTrace();logger.error(e.getMessage(),e);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllProductVariants/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProductVariants(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			productVariantList = null;
			try {			
				productVariantList = productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
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
							/*Double retailPrice = productVariant.getSupplyPriceExclTax().doubleValue() * (productVariant.getMarkupPrct().doubleValue()/100) + productVariant.getSupplyPriceExclTax().doubleValue();
							NumberFormat formatter = new DecimalFormat("###.##");  
							String strRetailPrice = formatter.format(retailPrice);
							productVariantBean.setRetailPriceExclTax(strRetailPrice);*/
							BigDecimal netPrice = (productVariant.getSupplyPriceExclTax().multiply(productVariant.getMarkupPrct().divide(new BigDecimal(100)))).add(productVariant.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
							BigDecimal retailPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
							productVariantBean.setRetailPriceExclTax(retailPrice.toString());
						}
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
				e.printStackTrace();logger.error(e.getMessage(),e);
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
				e.printStackTrace();logger.error(e.getMessage(),e);
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

}