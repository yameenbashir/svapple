package com.dowhile.angualrspringapp.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dowhile.service.OutletService;
import com.dowhile.service.RegisterService;

/**
 * imran latif
 */
@Controller
@RequestMapping("/roleDetails")
public class RoleDetailsController {

	
	// private static Logger logger = Logger.getLogger(RoleDetailsController.class.getName());
	@RequestMapping("/layout")
	public String getRoleDetailsControllerPartialPage(ModelMap modelMap) {
		return "roleDetails/layout";
	}

	@Resource
	private RegisterService registerService;
	@Resource
	private OutletService outletService;
	
	
}

