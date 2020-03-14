package com.dowhile.angualrspringapp.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * imran latif
 */
@Controller
@RequestMapping("/ecomProducts")
public class EcomProductsController {

	// private static Logger logger = Logger.getLogger(EcomProductsController.class.getName());

	@RequestMapping("/layout")
	public String getEcomProductsControllerPartialPage(ModelMap modelMap) {
		return "ecomProducts/layout";
	}

	
}

