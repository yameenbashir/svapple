package com.dowhile.angualrspringapp.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.UserBean;
import com.dowhile.service.CompanyService;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.MenuService;
import com.dowhile.service.OutletService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.UserOutletsService;
import com.dowhile.service.util.ServiceUtil;

/**
 * @author Yameen Bashir
 * 
 */
@Controller
@RequestMapping("/heartbeat")
public class HeartBeatController {

	private static Logger logger = Logger.getLogger(HeartBeatController.class.getName());
	@Resource
	private ResourceService resourceService;
	@Resource
	private ServiceUtil util;
	@Resource
	private MenuService menuService;
	@Resource
	private CompanyService companyService;
	@Resource
	private UserOutletsService userOutletsService;
	@Resource
	private OutletService outletService;
	
	@Resource
	private ConfigurationService configurationService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public @ResponseBody Response doLogin(
			HttpServletRequest request) {
		
		String url = request.getRequestURL().toString();
		logger.info("url :"+url);
        // Getting servlet request query string.
        String queryString = request.getQueryString();
        logger.info("queryString :"+queryString);
        // Getting request information without the hostname.
        String uri = request.getRequestURI();
        logger.info("uri :"+uri);
		
        // Below we extract information about the request object path
        // information.
        String scheme = request.getScheme();
        logger.info("scheme :"+scheme);
        String serverName = request.getServerName();
        logger.info("serverName :"+serverName);
        int portNumber = request.getServerPort();
        logger.info("portNumber :"+portNumber);
        String contextPath = request.getContextPath();
        logger.info("contextPath :"+contextPath);
        String servletPath = request.getServletPath();
        logger.info("servletPath :"+servletPath);
        String pathInfo = request.getPathInfo();
        logger.info("pathInfo :"+pathInfo);
        logger.info("Subdomain :"+request.getServerName().split("\\.")[0]);
        
        
      	return new Response(MessageConstants.INVALID_USER,
					StatusConstants.BUSY, LayOutPageConstants.STAY_ON_PAGE);
		
	}


}
