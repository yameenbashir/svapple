package com.dowhile.angualrspringapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * imran latif
 */
@Controller
@RequestMapping("/ecomProducts")
public class EcomProductsController {

	

	@RequestMapping("/layout")
	public String getEcomProductsControllerPartialPage(ModelMap modelMap) {
		return "ecomProducts/layout";
	}

	
}

