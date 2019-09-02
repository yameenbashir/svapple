package com.dowhile.angualrspringapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * imran latif
 */
@Controller
@RequestMapping("/stockReturn")
public class StockReturnController {

	

	@RequestMapping("/layout")
	public String getStockReturnControllerPartialPage(ModelMap modelMap) {
		return "stockReturn/layout";
	}

	
}

