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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Company;
import com.dowhile.Product;
import com.dowhile.ProductVariant;
import com.dowhile.SalesTax;
import com.dowhile.User;
import com.dowhile.constant.Actions;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.LayoutDesignControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.ProductBean;
import com.dowhile.frontend.mapping.bean.ProductVaraintDetailBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.service.CompanyService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductVariantService;
import com.dowhile.service.SalesTaxService;
import com.dowhile.service.VariantAttributeService;
import com.dowhile.service.VariantAttributeValuesService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/layoutDesign")
public class LayoutDesignController {

	@Resource
	private ServiceUtil util;

	@Resource
	private CompanyService companyService;

	@Resource
	private ProductService productService;


	@Resource
	private VariantAttributeService variantAttributeService;

	@Resource
	private VariantAttributeValuesService variantAttributeValuesService;

	@Resource
	private ProductVariantService productVariantService;


	@Resource
	private SalesTaxService salesTaxService;

	@RequestMapping(value = "/getAllProducts/{sessionId}", method = RequestMethod.GET)
	public @ResponseBody
	LayoutDesignControllerBean getAllProducts(
			@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {
		LayoutDesignControllerBean designControllerBean = new LayoutDesignControllerBean();
		List<ProductBean> productsBean = new ArrayList<>();
		List<Product> products = new ArrayList<>();
		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {
				
				
				Company company = companyService.getCompanyDetailsByCompanyID(currentUser.getCompany().getCompanyId());
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
						if(product.getDisplay()!=null){
							productBean.setDisplay(product.getDisplay().toString());

						}
						BigDecimal supplyPrice = product
								.getSupplyPriceExclTax();

						BigDecimal markupPrct = product.getMarkupPrct();
						SalesTax tax = salesTaxMap.get(product.getSalesTax().getSalesTaxId());
						BigDecimal retailPriceExclusiveTax = supplyPrice.add(
								supplyPrice.multiply(markupPrct).divide(
										new BigDecimal(100))).setScale(2,
												RoundingMode.CEILING);

						BigDecimal taxAmount = retailPriceExclusiveTax
								.multiply(
										new BigDecimal(tax
												.getSalesTaxPercentage()))
												.divide(new BigDecimal(100))
												.setScale(2, RoundingMode.CEILING);

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
								.setScale(2, RoundingMode.CEILING).toString());
						productsBean.add(productBean);
			

					}
					util.AuditTrail(request, currentUser,
							"SellController.getAllProducts", "User "+ currentUser.getUserEmail()+ " retrived all products successfully ",false);
					designControllerBean.setRegisterStatus("false");

					designControllerBean.setProductsBean(productsBean);
					return designControllerBean;
				} else {
					util.AuditTrail(request, currentUser,
							"SellController.getAllProducts",
							" products are not found requested by User "
									+ currentUser.getUserEmail(), false);
					// return new Response(productsBean,
					// StatusConstants.SUCCESS,
					// LayOutPageConstants.STAY_ON_PAGE);

					return designControllerBean;
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser,
						"SellController.getAllProducts", "Error Occured "
								+ errors.toString(), true);
				// return new Response(MessageConstants.SYSTEM_BUSY,
				// StatusConstants.RECORD_NOT_FOUND,
				// LayOutPageConstants.STAY_ON_PAGE);

				return designControllerBean;

			}
		}

		else {
			// return new Response(MessageConstants.INVALID_SESSION,
			// StatusConstants.INVALID, LayOutPageConstants.LOGIN);

			return designControllerBean;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/saveLayout/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response saveLayout(@PathVariable("sessionId") String sessionId,@RequestBody List<ProductBean> beans,
			HttpServletRequest request) {

		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {
				for(ProductBean productBean: beans){
					if(productBean.getDisplay()!=null && productBean.getDisplay().equalsIgnoreCase("true")){
						Product product = productService.getProductByProductId(Integer.parseInt(productBean.getProductId()),currentUser.getCompany().getCompanyId());
						product.setDisplay(true);
						productService.updateProduct(product, Actions.INVENTORY_NOCHANGE, 0,currentUser.getCompany());
					}
					
				}
				

				util.AuditTrail(
						request,currentUser,"LayoutDesignController.saveLayout",
						"User " + currentUser.getUserEmail()
								+ " saveLayout successfully ", false);

				return new Response(MessageConstants.REQUREST_PROCESSED,
						StatusConstants.SUCCESS, LayOutPageConstants.SELL);

			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
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
}
