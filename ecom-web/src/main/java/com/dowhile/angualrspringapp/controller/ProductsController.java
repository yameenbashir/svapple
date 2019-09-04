package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.dowhile.Configuration;
import com.dowhile.ProductSummmary;
import com.dowhile.User;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.HomeControllerBean;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.ProductsControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.ProductBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.service.BrandService;
import com.dowhile.service.CompositeProductService;
import com.dowhile.service.ContactService;
import com.dowhile.service.OutletService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductSummmaryService;
import com.dowhile.service.ProductTagService;
import com.dowhile.service.ProductTypeService;
import com.dowhile.service.ProductVariantService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.SaleService;
import com.dowhile.service.SalesTaxService;
import com.dowhile.service.TagService;
import com.dowhile.service.VariantAttributeService;
import com.dowhile.service.VariantAttributeValuesService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.ControllerUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * Hafiz Yameen Bashir
 */
@Controller
@RequestMapping("/products")
public class ProductsController {

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
	private ProductSummmaryService productSummmaryService;
	
	@Resource
	private SaleService saleService;
	

	@RequestMapping("/layout")
	public String getProductsControllerPartialPage(ModelMap modelMap) {
		return "products/layout";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllProducts/{sessionId}/{loadAllProducts}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProducts(@PathVariable("sessionId") String sessionId,@PathVariable("loadAllProducts") String loadAllProducts,
			HttpServletRequest request) {

		List<ProductBean> productBeanList = new ArrayList<>();
		List<ProductVariantBean> productVariantBeanList = new ArrayList<>();
		List<ProductSummmary> productSummmaries =  null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {
				Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
				if(loadAllProducts.equalsIgnoreCase("true")){
					productSummmaries = productSummmaryService.getAllProductSummmaryByCompanyIdOutletId(currentUser.getCompany().getCompanyId(),currentUser.getOutlet().getOutletId());
				}else{
					productSummmaries = productSummmaryService.getTenNewProductSummmaryByCompanyIdOutletId(currentUser.getCompany().getCompanyId(),currentUser.getOutlet().getOutletId());
				}
				
				if (productSummmaries != null) {
					for (ProductSummmary productSummmary : productSummmaries) {
						productVariantBeanList = new ArrayList<>();
						ProductBean productBean = new ProductBean();
						productBean.setProductId(String.valueOf(productSummmary.getId().getId()));
						productBean.setProductName(productSummmary.getId().getProductName());
						productBean.setSupplierName(productSummmary.getId().getContactName());
						productBean.setBrandName(productSummmary.getId().getBrandName());
						productBean.setOutletName(productSummmary.getId().getOutletName());
						productBean.setImagePath(productSummmary.getId().getImagePath());
						productBean.setNetPrice(String.valueOf(productSummmary.getId().getNetPrice().setScale(2, RoundingMode.HALF_EVEN)));
						productBean.setVarinatsCount(String.valueOf(productSummmary.getId().getVariantCount()));
						productBean.setIsComposite(productSummmary.getId().getIsComposite());
						
						if(productSummmary.getId().getVariantProducts().equalsIgnoreCase(ControllersConstants.TRUE) && productSummmary.getId().getStandardProduct().equalsIgnoreCase(ControllersConstants.TRUE)){
							productBean.setInventoryCount(String.valueOf(productSummmary.getId().getVariantInventoryCount()));
						}
						/*else if(!product.getStandardProduct().equalsIgnoreCase(ControllersConstants.TRUE)){
							productBean.setInventoryCount(String.valueOf(productSummmary.getId().get compositeProductService.getCountOfInventoryByProductUuId(product.getProductUuid(),currentUser.getCompany().getCompanyId())));
						}*/
						else{
							productBean.setInventoryCount(String.valueOf(productSummmary.getId().getCurrentInventory()));
						}
						
						productBean.setProductVariantsBeans(productVariantBeanList);

						/*List<ProductTag> productTags = productTagService.getAllProductTagsByProductId(product.getProductId(),currentUser.getCompany().getCompanyId());
						if(productTags!=null){
							for(ProductTag productTag:productTags){
								ProductTagBean productTagBean = new ProductTagBean();
								productTagBean.setProductTagId(productTag.getProductTagId().toString());
								productTagBean.setProductTagName(tagService.getTagByTagId(productTag.getTag().getTagId(),currentUser.getCompany().getCompanyId()).getTagName());
								productTagBeanList.add(productTagBean);
							}
						}
						productBean.setProductTagBeanList(productTagBeanList);*/
						productBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(productSummmary.getId().getCreatedDate()));
						productBeanList.add(productBean);
					}
					ProductsControllerBean productsControllerBean = new ProductsControllerBean();
					productsControllerBean.setProductBeanList(productBeanList);
					Configuration dutyCalculatorConfiguration = configurationMap.get("SHOW_DUTY_CALCULATOR");
					if(dutyCalculatorConfiguration!=null && dutyCalculatorConfiguration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
						productsControllerBean.setShowProductPriceHistory(ControllersConstants.TRUE);
					}else{
						productsControllerBean.setShowProductPriceHistory(ControllersConstants.FALSE);
					}
					util.AuditTrail(request, currentUser, "ProductsController.getAllProducts", 
							"User "+ currentUser.getUserEmail()+" retrived all Products successfully ",false);
					return new Response(productsControllerBean, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "ProductsController.getAllProducts", 
							" Products are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ProductsController.getAllProducts",
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
	@RequestMapping(value = "/getProductDashBoard/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getDashBoard(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {
		HomeControllerBean controllerBean = new HomeControllerBean();
		List graphData = null;
		Date startDate = null , endDate = null;
		Calendar calendar = Calendar.getInstance();
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			controllerBean.setOutletName(currentUser.getOutlet().getOutletName());

			try {

				calendar.setTime(new Date());
				calendar.add(Calendar.DAY_OF_MONTH, -6);
				startDate = calendar.getTime();
				endDate = new Date();

				Calendar customerCountCalendar = (Calendar) calendar.clone();

				graphData = saleService.getProductsCount(currentUser
						.getOutlet().getOutletId(), currentUser.getCompany()
						.getCompanyId(), startDate, endDate);
				ControllerUtil.getDefaultData(graphData, customerCountCalendar,
						controllerBean, currentUser, request, "customerCount");

				//
				util.AuditTrail(request, currentUser,
						"ProductsController.getDashBoard",
						"User " + currentUser.getUserEmail()
								+ " dashboard loaded successfully ", false);
				return new Response(controllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);

			}
			catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ProductsController.getDashBoard",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
}

