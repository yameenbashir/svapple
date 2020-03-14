package com.dowhile.angualrspringapp.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Yameen Bashir
 *
 */
@Controller
@RequestMapping("/")
public class IndexController {

	// private static Logger logger = Logger.getLogger(IndexController.class.getName());
    @RequestMapping
    public String getIndexPage() {
        return "index";
    }
}
