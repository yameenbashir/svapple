package com.dowhile.angualrspringapp.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dowhile.service.CompanyService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductTypeService;
import com.dowhile.service.ProductVariantService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.SalesTaxService;
import com.dowhile.service.VariantAttributeService;
import com.dowhile.service.VariantAttributeValuesService;
import com.dowhile.service.util.ServiceUtil;

/**
 * Zafar Shakeel
 */
@Controller
@RequestMapping("/cart")
public class CartController {

	@Resource
	private ResourceService resourceService;
	@Resource
	private ServiceUtil util;
	@Resource
	private ProductTypeService productTypeService;
	@Resource
	private ProductService productService;
	@Resource
	private CompanyService companyService;

	@Resource
	private VariantAttributeService variantAttributeService;

	@Resource
	private VariantAttributeValuesService variantAttributeValuesService;

	@Resource
	private SalesTaxService salesTaxService;

	@Resource
	private ProductVariantService productVariantService;
	

	@RequestMapping("/layout")
	public String getCartControllerPartialPage(ModelMap modelMap) {
		return "cart/layout";
	}

	

}

