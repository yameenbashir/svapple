package com.dowhile.angualrspringapp.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * imran latif
 */
@Controller
@RequestMapping("/ecommerce")
public class EcommerceController {

	
	// private static Logger logger = Logger.getLogger(EcommerceController.class.getName());
	@RequestMapping("/layout")
	public String getEcommerceControllerPartialPage(ModelMap modelMap) {
		return "ecommerce/layout";
	}

	
	
}

