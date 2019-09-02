package com.dowhile.angualrspringapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * imran latif
 */
@Controller
@RequestMapping("/home")
public class HomeController {
	  @RequestMapping({"/layout"})
	  public String getAboutUsPartialPage()
	  {
	    return "home/layout";
	  }
}

