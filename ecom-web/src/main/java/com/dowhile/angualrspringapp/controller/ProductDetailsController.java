package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

import com.dowhile.Brand;
import com.dowhile.CompositeProduct;
import com.dowhile.Contact;
import com.dowhile.Outlet;
import com.dowhile.Product;
import com.dowhile.ProductHistory;
import com.dowhile.ProductTag;
import com.dowhile.ProductType;
import com.dowhile.ProductVariant;
import com.dowhile.User;
import com.dowhile.VariantAttribute;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.ProductDetailsControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.CompositVariantBean;
import com.dowhile.frontend.mapping.bean.ProductBean;
import com.dowhile.frontend.mapping.bean.ProductHistoryBean;
import com.dowhile.frontend.mapping.bean.ProductTagBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.service.BrandService;
import com.dowhile.service.CompositeProductService;
import com.dowhile.service.ContactService;
import com.dowhile.service.OutletService;
import com.dowhile.service.ProductHistoryService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductTagService;
import com.dowhile.service.ProductTypeService;
import com.dowhile.service.ProductVariantService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.SalesTaxService;
import com.dowhile.service.TagService;
import com.dowhile.service.VariantAttributeService;
import com.dowhile.service.VariantAttributeValuesService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/productDetails")
public class ProductDetailsController {

	// private static Logger logger = Logger.getLogger(ProductDetailsController.class.getName());
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
	private ProductHistoryService  proudHistoryService;

	@RequestMapping("/layout")
	public String getProductDetailsControllerPartialPage(ModelMap modelMap) {
		return "productDetails/layout";
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getProductDetailsControllerData/{sessionId}/{productId}", method = RequestMethod.POST)
	public @ResponseBody Response getNewProductControllerData(@PathVariable("sessionId") String sessionId,
			@PathVariable("productId") String productId,
			HttpServletRequest request) {

		
		ProductBean productBean = null;
		List<ProductHistoryBean> productHistoryBeans = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");


			try {
				
				Response response = getProductDetailByProductIdOutletId(sessionId, productId, request);
				
				if(response.status.equals(StatusConstants.SUCCESS)){
					productBean = (ProductBean) response.data;
				}
				/*response = getProductHistoryByProductUuid(sessionId,productBean.getProductUuid(), request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					productHistoryBeans = (List<ProductHistoryBean>) response.data;
				}*/
				
				
				ProductDetailsControllerBean productDetailsControllerBean = new ProductDetailsControllerBean();
				productDetailsControllerBean.setProductBean(productBean);
				productDetailsControllerBean.setProductHistoryBeans(productHistoryBeans);
				
				util.AuditTrail(request, currentUser, "NewProductController.getAllVariantAttributes", 
						"User "+ currentUser.getUserEmail()+" retrived all Variant attributes successfully ",false);
				return new Response(productDetailsControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
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
	@RequestMapping(value = "/getProductDetailByProductId/{sessionId}/{productId}", method = RequestMethod.POST)
	public @ResponseBody Response getProductDetailByProductIdOutletId(@PathVariable("sessionId") String sessionId,
			@PathVariable("productId") String productId,
			HttpServletRequest request) {

		List<ProductVariantBean> productVariantBeanList = new ArrayList<>();
		List<CompositVariantBean> compositProductCollection = new ArrayList<>();
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
					productBean.setCompanyName(currentUser.getCompany().getCompanyName());
					productBean.setProductId(product.getProductId().toString());
					productBean.setProductName(product.getProductName());
					productBean.setProductDesc(product.getProductDesc());
					productBean.setProductHandler(product.getProductHandler());
					productBean.setProductUuid(product.getProductUuid());
					ProductType productType = productTypeService.getProductTypeByProductTypeId(product.getProductType().getProductTypeId(),currentUser.getCompany().getCompanyId());
					productBean.setProductTypeId(productType.getProductTypeId().toString());
					productBean.setProductTypeName(productType.getProductTypeName());
					Contact supplier = supplierService.getContactByID(product.getContact().getContactId(),currentUser.getCompany().getCompanyId());
					productBean.setSupplierId(supplier.getContactId().toString());
					productBean.setSupplierName(supplier.getContactName());
					Brand brand = brandService.getBrandByBrandId(product.getBrand().getBrandId(),currentUser.getCompany().getCompanyId());
					productBean.setBrandId(brand.getBrandId().toString());
					productBean.setBrandName(brand.getBrandName());
					productBean.setSalesAccountCode(product.getSalesAccountCode());
					Outlet outlet = outletService.getOuletByOutletId(product.getOutlet().getOutletId(),currentUser.getCompany().getCompanyId());
					productBean.setOutletId(outlet.getOutletId().toString());
					productBean.setOutletName(outlet.getOutletName());
					productBean.setPurchaseAccountCode(product.getPurchaseAccountCode());
					productBean.setStandardProduct(product.getStandardProduct());
					productBean.setTrackingProduct(product.getTrackingProduct());
					productBean.setVarientProducts(product.getVariantProducts());
					productBean.setMarkupPrct(product.getMarkupPrct().toString());
					productBean.setProductCanBeSold(product.getProductCanBeSold());
					productBean.setSupplyPriceExclTax(product.getSupplyPriceExclTax().toString());
					productBean.setSku(product.getSku());
					productBean.setImagePath(product.getImagePath());
					BigDecimal netPricee = (new BigDecimal(productBean.getSupplyPriceExclTax()).multiply(new BigDecimal(productBean.getMarkupPrct()).divide(new BigDecimal(100)))).add(new BigDecimal(productBean.getSupplyPriceExclTax())).setScale(5,RoundingMode.HALF_EVEN);
					BigDecimal newNetPrices =netPricee.setScale(2,RoundingMode.HALF_EVEN);
					productBean.setNetPrice(newNetPrices.toString());
					productBean.setInventoryCount(String.valueOf(productService.getCountOfInventoryByProductUuId(product.getProductUuid(),currentUser.getCompany().getCompanyId())));
					if(product.getStandardProduct().equalsIgnoreCase("true")){
						List<ProductVariant> productVariantsList = productVariantService.getVarientsByProductId(product.getProductId(),currentUser.getCompany().getCompanyId());
						if(productVariantsList!=null){
							for(ProductVariant productVariant:productVariantsList){
								ProductVariantBean productVariantBean = new ProductVariantBean();
								productVariantBean.setProductVariantId(productVariant.getProductVariantId().toString());
								productVariantBean.setProductId(productVariant.getProduct().getProductId().toString());
								productVariantBean.setProductName(product.getProductName());
								productVariantBean.setVariantAttributeName(productVariant.getVariantAttributeName());
								productVariantBean.setVariantAttributeValue1(productVariant.getVariantAttributeValue1());
								productVariantBean.setVariantAttributeValue2(productVariant.getVariantAttributeValue2());
								productVariantBean.setVariantAttributeValue3(productVariant.getVariantAttributeValue3());
								if(productVariant.getVariantAttributeByVariantAttributeAssocicationId1()!=null){
									VariantAttribute variantAttribute1 = variantAttributeService.getVariantAttributeByVariantAttributeId(productVariant.getVariantAttributeByVariantAttributeAssocicationId1().getVariantAttributeId(),currentUser.getCompany().getCompanyId());
									productVariantBean.setVariantAttributeId1(variantAttribute1.getVariantAttributeId().toString());
									productVariantBean.setVariantAttributeIdValue1(variantAttribute1.getAttributeName());
									
								}
								if(productVariant.getVariantAttributeByVariantAttributeAssocicationId2()!=null){
									VariantAttribute variantAttribute2 = variantAttributeService.getVariantAttributeByVariantAttributeId(productVariant.getVariantAttributeByVariantAttributeAssocicationId2().getVariantAttributeId(),currentUser.getCompany().getCompanyId());
									productVariantBean.setVariantAttributeId2(variantAttribute2.getVariantAttributeId().toString());
									productVariantBean.setVariantAttributeIdValue2(variantAttribute2.getAttributeName());
									
								}
								if(productVariant.getVariantAttributeByVariantAttributeAssocicationId3()!=null){
									VariantAttribute variantAttribute3 = variantAttributeService.getVariantAttributeByVariantAttributeId(productVariant.getVariantAttributeByVariantAttributeAssocicationId3().getVariantAttributeId(),currentUser.getCompany().getCompanyId());
									productVariantBean.setVariantAttributeId3(variantAttribute3.getVariantAttributeId().toString());
									productVariantBean.setVariantAttributeIdValue3(variantAttribute3.getAttributeName());
									
								}
								
								productVariantBean.setSku(productVariant.getSku());
								productVariantBean.setCurrentInventory(String.valueOf(productVariantService.getCountOfInventoryByProductVariantUuIdOutletId(productVariant.getProductVariantUuid(),currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId())));
								if(productVariantBean.getCurrentInventory()!=null){
									productVariantBean.setPrintCount(productVariantBean.getCurrentInventory());
								}else{
									productVariantBean.setPrintCount("0");
								}
								
								if(productBean.getTrackingProduct().equalsIgnoreCase("true")){
									if(productVariant.getReorderPoint()!=null){
										productVariantBean.setReorderPoint(productVariant.getReorderPoint().toString());
									}else{
										productVariantBean.setReorderPoint("");
									}
									
									if(productVariant.getReorderAmount()!=null){
										productVariantBean.setReorderAmount(productVariant.getReorderAmount().toString());
									}else{
										productVariantBean.setReorderAmount("");
									}
									
									
								}

								productVariantBean.setSupplyPriceExclTax(productVariant.getSupplyPriceExclTax().toString());
								productVariantBean.setMarkupPrct(productVariant.getMarkupPrct().toString());
								
								//BigDecimal netPricee = (new BigDecimal(productBean.getSupplyPriceExclTax()).multiply(new BigDecimal(productBean.getMarkupPrct()).divide(new BigDecimal(100)))).add(new BigDecimal(productBean.getSupplyPriceExclTax())).setScale(5,RoundingMode.HALF_EVEN);
								BigDecimal netPrice = (new BigDecimal(productVariantBean.getSupplyPriceExclTax()).multiply(new BigDecimal(productVariantBean.getMarkupPrct()).divide(new BigDecimal(100)))).add(new BigDecimal(productVariantBean.getSupplyPriceExclTax())).setScale(5,RoundingMode.HALF_EVEN);
								BigDecimal newNetPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
								//productBean.setNetPrice(newNetPrices.toString());
								
								
							
								
								
								
								//BigDecimal netPrice = (productVariant.getSupplyPriceExclTax().multiply(productVariant.getMarkupPrct().divide(new BigDecimal(100)))).add(productVariant.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
								//BigDecimal newNetPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
								productVariantBean.setNetPrice(newNetPrice.toString());
								
								productVariantBeanList.add(productVariantBean);
							}
						}
					}else{
						List<CompositeProduct> compositeProducts = compositeProductService.getAllCompositeProductsByProductIdOultetIdCompanyId(product.getProductId(),product.getOutlet().getOutletId(),currentUser.getCompany().getCompanyId());
						if(compositeProducts!=null){
							for(CompositeProduct compositeProduct :compositeProducts){
								ProductVariant productVariant = productVariantService.getProductVariantByProductVariantId(compositeProduct.getProductVariant().getProductVariantId(),currentUser.getCompany().getCompanyId());
								CompositVariantBean compositVariantBean = new CompositVariantBean();
								compositVariantBean.setCompositeProductId(compositeProduct.getCompositeProductId().toString());
								compositVariantBean.setCompositQunatity(String.valueOf(compositeProductService.getCountOfInventoryByCompositeProductUuId(compositeProduct.getCompositeProductUuid(),currentUser.getCompany().getCompanyId())));
								compositVariantBean.setProductId(productVariant.getProduct().getProductId().toString());
								compositVariantBean.setProductName(product.getProductName());
								compositVariantBean.setProductVariantId(productVariant.getProductVariantId().toString());
								compositVariantBean.setProductVariantName(productVariant.getVariantAttributeName());
								compositProductCollection.add(compositVariantBean);
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
							productTagBean.setProductTagName(tagService.getTagByTagId(productTag.getTag().getTagId(),currentUser.getCompany().getCompanyId()).getTagName());
							productTagBeanList.add(productTagBean);
						}
					}
					productBean.setProductTagBeanList(productTagBeanList);
					productBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(product.getCreatedDate()));
					
					util.AuditTrail(request, currentUser, "ProductDetailsController.getProductDetailByProductId", 
							"User "+ currentUser.getUserEmail()+" retrived  Product detail successfully ",false);
					return new Response(productBean, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "ProductDetailsController.getProductDetailByProductId", 
							" Products detail not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ProductDetailsController.getProductDetailByProductId",
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
	@RequestMapping(value = "/getProductHistoryByProductUuid/{sessionId}/{productUuid}", method = RequestMethod.POST)
	public @ResponseBody Response getProductHistoryByProductUuid(@PathVariable("sessionId") String sessionId,
			@PathVariable("productUuid") String productUuid,
			HttpServletRequest request) {

		List<ProductHistory> prodHistories = null;
		List<ProductHistoryBean> productHistoryBeans = new ArrayList<>();

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			List<Product> products = null;
			try {
				products = productService.getAllProductsByUuid(productUuid,currentUser.getCompany().getCompanyId());
				if(products!=null){
					List<Outlet> outlets = outletService.getOutlets(currentUser.getCompany().getCompanyId());
					Map outletMap = new HashMap<>();
					for(Outlet outlet:outlets){
						outletMap.put(outlet.getOutletId(), outlet);
					}
					List<User> users = resourceService.getAllUsers(currentUser.getCompany().getCompanyId());
					Map userMap = new HashMap<>();
					if(users!=null){
						for(User user:users){
							userMap.put(user.getUserId(), user);
						}
					}
					List<ProductVariant> productVariants = productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
					Map productVariantMap = new HashMap<>();
					if(productVariants!=null){
						for(ProductVariant productVariant:productVariants){
							productVariantMap.put(productVariant.getProductVariantId(), productVariant);
						}
					}
					for(Product product:products){
						prodHistories = proudHistoryService.getProductHistoryByProductId(product.getProductId(),currentUser.getCompany().getCompanyId());
						if(prodHistories!=null){
							for (ProductHistory productHistory : prodHistories) {
								ProductHistoryBean  productHistoryBean = new ProductHistoryBean();
								if(currentUser.getRole().getRoleId()==1 && currentUser.getOutlet().getIsHeadOffice()!=null && currentUser.getOutlet().getIsHeadOffice().toString()=="true"){
									productHistoryBean.setActionDate(DateTimeUtil.convertDBDateTimeToGuiFormat(productHistory.getActionDate()));
									productHistoryBean.setChangeQuantity(String.valueOf(productHistory.getChangeQuantity()));
									Outlet outlet = (Outlet)outletMap.get(productHistory.getOutlet().getOutletId());

									productHistoryBean.setOutletName(outlet.getOutletName());
									productHistoryBean.setOutletQuantity(String.valueOf(productHistory.getOutletQuantity()));
									if(productHistory.getProductVariant()!=null){
										ProductVariant productVariant = (ProductVariant)productVariantMap.get(productHistory.getProductVariant().getProductVariantId());
										if(productVariant!=null){
											productHistoryBean.setProductVariantName(productVariant.getVariantAttributeName());
											productHistoryBean.setAction(productHistory.getAction());
										}else{
											productHistoryBean.setProductVariantName("-");
											productHistoryBean.setAction("Deleted");
										}
										
									}else{
										productHistoryBean.setAction(productHistory.getAction());
									}
									productHistoryBean.setQuantity(String.valueOf(productHistory.getQuantity()));
									User user  = (User)userMap.get(productHistory.getUser().getUserId());
									productHistoryBean.setUserName(user.getFirstName()+" "+user.getLastName());
									productHistoryBeans.add(productHistoryBean);
								}else{
									if(productHistory.getOutlet().getOutletId()==currentUser.getOutlet().getOutletId()){
										productHistoryBean.setActionDate(DateTimeUtil.convertDBDateTimeToGuiFormat(productHistory.getActionDate()));
										productHistoryBean.setChangeQuantity(String.valueOf(productHistory.getChangeQuantity()));
										Outlet outlet = (Outlet)outletMap.get(productHistory.getOutlet().getOutletId());

										productHistoryBean.setOutletName(outlet.getOutletName());
										productHistoryBean.setOutletQuantity(String.valueOf(productHistory.getOutletQuantity()));
										if(productHistory.getProductVariant()!=null){
											ProductVariant productVariant = (ProductVariant)productVariantMap.get(productHistory.getProductVariant().getProductVariantId());
											if(productVariant!=null){
												productHistoryBean.setProductVariantName(productVariant.getVariantAttributeName());
												productHistoryBean.setAction(productHistory.getAction());
											}else{
												productHistoryBean.setProductVariantName("-");
												productHistoryBean.setAction("Deleted");
											}
										}
										productHistoryBean.setQuantity(String.valueOf(productHistory.getQuantity()));
										User user  = (User)userMap.get(productHistory.getUser().getUserId());
										productHistoryBean.setUserName(user.getFirstName()+" "+user.getLastName());
										productHistoryBeans.add(productHistoryBean);
									}
								}
							}
						}
					}

					util.AuditTrail(request, currentUser, "ProductDetailsController.getProductHistoryByProductUuid", 
							"User "+ currentUser.getUserEmail()+" retrived all Variant attributes successfully ",false);
					return new Response(productHistoryBeans, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "ProductDetailsController.getProductHistoryByProductUuid", 
							" Variant attributes are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ProductDetailsController.getProductHistoryByProductUuid",
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

