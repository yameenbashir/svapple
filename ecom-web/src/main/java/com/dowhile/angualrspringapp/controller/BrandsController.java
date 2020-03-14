package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Brand;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.BrandBean;
import com.dowhile.service.BrandService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/brands")
public class BrandsController {

	// private static Logger logger = Logger.getLogger(BrandsController.class.getName());
	@Resource
	private ResourceService resourceService;
	@Resource
	private ServiceUtil util;
	@Resource
	private BrandService brandService;
	@Resource
	private ProductService productService;
	
	@RequestMapping("/layout")
	public String getBrandsControllerPartialPage(ModelMap modelMap) {
		return "/brands/layout";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/addBrand/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response addBrand(@PathVariable("sessionId") String sessionId,
			@RequestBody BrandBean brandBean, HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{

				Brand brand = new Brand();
				brand.setActiveIndicator(true);
				brand.setBrandDescription(brandBean.getBrandDescription());
				brand.setBrandName(brandBean.getBrandName());
				brand.setCreatedBy(currentUser.getUserId());
				brand.setCreatedDate(new Date());
				brand.setLastUpdated(new Date());
				brand.setUpdatedBy(currentUser.getUserId());
				brand.setCompany(currentUser.getCompany());
				brandService.addBrand(brand,0);
				

				util.AuditTrail(request, currentUser, "BrandsController.addBrand", 
						"User "+ currentUser.getUserEmail()+" Added Brand +"+brandBean.getBrandName()+" successfully ",false);
				return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.BRANDS);


			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "BrandsController.addBrand",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/updateBrand/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response updateBrand(@PathVariable("sessionId") String sessionId,
			@RequestBody BrandBean brandBean, HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{

				Brand brand = brandService.getBrandByBrandId(Integer.valueOf(brandBean.getBrandId()),currentUser.getCompany().getCompanyId());
				brand.setBrandDescription(brandBean.getBrandDescription());
				brand.setBrandName(brandBean.getBrandName());
				brand.setLastUpdated(new Date());
				brand.setUpdatedBy(currentUser.getUserId());
				brandService.updateBrand(brand,0);
				

				util.AuditTrail(request, currentUser, "BrandsController.updateBrand", 
						"User "+ currentUser.getUserEmail()+" Updated Brand +"+brandBean.getBrandName()+" successfully ",false);
				return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.BRANDS);


			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "BrandsController.updateBrand",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllBrands/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllBrands(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<BrandBean> brandBeanList = new ArrayList<>();
		List<Brand> brandsList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");


			try {

				brandsList = brandService.getAllBrands(currentUser.getCompany().getCompanyId());
				if (brandsList != null) {
					for (Brand brand : brandsList) {

						BrandBean brandBean = new BrandBean();
						brandBean.setBrandId(brand.getBrandId().toString());
						brandBean.setBrandName(brand.getBrandName());
						brandBean.setBrandDescription(brand.getBrandDescription());
						brandBean.setNumberOfProducts(String.valueOf(productService.getCountOfProductsByBrandId(brand.getBrandId(),currentUser.getCompany().getCompanyId())));
						brandBeanList.add(brandBean);

					}
					util.AuditTrail(request, currentUser, "BrandsController.getAllBrands", 
							"User "+ currentUser.getUserEmail()+" retrived all Brands successfully ",false);
					return new Response(brandBeanList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "BrandsController.getAllBrands", 
							" Brands are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "BrandsController.getAllBrands",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
}

