package com.dowhile.angualrspringapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * imran latif
 */
@Controller
@RequestMapping("/ecommerce")
public class EcommerceController {

	

	@RequestMapping("/layout")
	public String getEcommerceControllerPartialPage(ModelMap modelMap) {
		return "ecommerce/layout";
	}

	
	
}

