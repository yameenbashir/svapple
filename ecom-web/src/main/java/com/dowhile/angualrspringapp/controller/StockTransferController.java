package com.dowhile.angualrspringapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * imran latif
 */
@Controller
@RequestMapping("/stockTransfer")
public class StockTransferController {

	

	@RequestMapping("/layout")
	public String getStockTransferControllerPartialPage(ModelMap modelMap) {
		return "stockTransfer/layout";
	}

	
}

