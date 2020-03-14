package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.ProductPriceHistory;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.ProductPriceHistoryBean;
import com.dowhile.service.BrandService;
import com.dowhile.service.CompositeProductService;
import com.dowhile.service.ContactService;
import com.dowhile.service.OutletService;
import com.dowhile.service.ProductPriceHistoryService;
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
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * Hafiz Yameen Bashir
 */
@Controller
@RequestMapping("/productPriceHistory")
public class ProductPriceHistoryController {

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
	private ProductPriceHistoryService productPriceHistoryService;
	@Resource
	private SaleService saleService;
	

	@RequestMapping("/layout")
	public String getProductPriceHistoryControllerPartialPage(ModelMap modelMap) {
		return "productPriceHistory/layout";
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getProductPriceHistoryByProductId/{sessionId}/{productId}", method = RequestMethod.POST)
	public @ResponseBody Response getProductPriceHistoryByProductId(@PathVariable("sessionId") String sessionId,@PathVariable("productId") String productId,
			HttpServletRequest request) {

		List<ProductPriceHistoryBean> productPriceHistoryBeanList = new ArrayList<>();
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");


			try {
				List<ProductPriceHistory>  productPriceHistoryList= productPriceHistoryService.getAllProductPriceHistoryByCompanyIdOutletIdProductId(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId(), Integer.valueOf(productId));
					if(productPriceHistoryList!=null){
						for(ProductPriceHistory productPriceHistory:productPriceHistoryList){
							ProductPriceHistoryBean productPriceHistoryBean =  new ProductPriceHistoryBean();
							productPriceHistoryBean.setAdditionalCustomDutyPrct(productPriceHistory.getAdditionalCustomDutyPrct().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setAdditionalCustomDutyValue(productPriceHistory.getAdditionalCustomDutyValue().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setAdditionalSalesTaxPrct(productPriceHistory.getAdditionalSalesTaxPrct().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setAdditionalSalesTaxValue(productPriceHistory.getAdditionalSalesTaxValue().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setCoCode(productPriceHistory.getCoCode());
							productPriceHistoryBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(productPriceHistory.getCreatedDate()));
							productPriceHistoryBean.setCustomDutyPrct(productPriceHistory.getCustomDutyPrct().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setCustomDutyValue(productPriceHistory.getCustomDutyValue().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setDescription(productPriceHistory.getDescription());
							productPriceHistoryBean.setDollarRate(productPriceHistory.getDollarRate().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setdTotalValueAssessed(productPriceHistory.getDTotalValueAssessed().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setdTotalValueDeclared(productPriceHistory.getDTotalValueDeclared().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setdUnitValueAssessed(productPriceHistory.getDUnitValueAssessed().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setdUnitValueDeclared(productPriceHistory.getDUnitValueDeclared().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setExcisePrct(productPriceHistory.getExcisePrct().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setExciseValue(productPriceHistory.getExciseValue().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setFtaPrct(productPriceHistory.getFtaPrct().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setFtaValue(productPriceHistory.getFtaValue().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setHsCode(productPriceHistory.getHsCode());
							productPriceHistoryBean.setItPrct(productPriceHistory.getItPrct().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setItValue(productPriceHistory.getItValue().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setLastUpdated(productPriceHistory.getLastUpdated().toString());
							productPriceHistoryBean.setMarkupPrct(productPriceHistory.getMarkupPrct().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setNumberOfUnits(productPriceHistory.getNumberOfUnits().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setpCustomValueAssessed(productPriceHistory.getPCustomValueAssessed().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setpCustomValueDeclared(productPriceHistory.getPCustomValueDeclared().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setProductPriceHistoryId(productPriceHistory.getProductPriceHistoryId());
							productPriceHistoryBean.setRegularityDutyPrct(productPriceHistory.getRegularityDutyPrct().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setRegularityDutyValue(productPriceHistory.getRegularityDutyValue().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setRemarks(productPriceHistory.getRemarks());
							productPriceHistoryBean.setSalesTaxPrct(productPriceHistory.getSalesTaxPrct().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setSalesTaxValue(productPriceHistory.getSalesTaxValue().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setSroNumber(productPriceHistory.getSroNumber());
							productPriceHistoryBean.setTotalValue(productPriceHistory.getTotalValue().setScale(2, RoundingMode.HALF_EVEN).toString());
							productPriceHistoryBean.setVrNumber(productPriceHistory.getVrNumber());
							productPriceHistoryBean.setGrNumber(productPriceHistory.getGrNumber());
							productPriceHistoryBeanList.add(productPriceHistoryBean);
							
						}
						util.AuditTrail(request, currentUser, "ProductPriceHistoryController.getProductPriceHistoryByProductId", 
								"User "+ currentUser.getUserEmail()+" retrived all Product Price history successfully ",false);
						return new Response(productPriceHistoryBeanList,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
					}
					util.AuditTrail(request, currentUser, "ProductPriceHistoryController.getProductPriceHistoryByProductId", 
							"User "+ currentUser.getUserEmail()+" product price history not found ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
					
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ProductPriceHistoryController.getProductPriceHistoryByProductId",
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

