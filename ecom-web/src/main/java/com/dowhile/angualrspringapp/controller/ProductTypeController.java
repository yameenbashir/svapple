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

import com.dowhile.ProductType;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.ProductTypeBean;
import com.dowhile.frontend.mapping.bean.SupplierBean;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductTypeService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * Zafar Shakeel
 */
@Controller
@RequestMapping("/productType")
public class ProductTypeController {
	
	private static Logger logger = Logger.getLogger(ProductTypeController.class.getName());
	@Resource
	private ResourceService resourceService;
	@Resource
	private ServiceUtil util;
	@Resource
	private ProductTypeService productTypeService;
	@Resource
	private ProductService productService;
	

	@RequestMapping("/layout")
	public String getProductTypeControllerPartialPage(ModelMap modelMap) {
		return "productType/layout";
	}

		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/addProductType/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response addProductType(@PathVariable("sessionId") String sessionId,
			@RequestBody ProductTypeBean productTypeBean, HttpServletRequest request) {

		List<ProductTypeBean> productTypeBeanList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{

				ProductType productType = new ProductType();
				productType.setActiveIndicator(true);
				productType.setProductTypeName(productTypeBean.getProductTypeName());
				productType.setCreatedBy(currentUser.getUserId());
				productType.setCreatedDate(new Date());
				productType.setLastUpdated(new Date());
				productType.setUpdatedBy(currentUser.getUserId());
				productType.setCompany(currentUser.getCompany());
				productTypeService.addProductType(productType,currentUser.getCompany().getCompanyId());
				
				Response response = getAllProductTypes(sessionId, request);

				if(response.status.equals(StatusConstants.SUCCESS)){
					productTypeBeanList = (List<ProductTypeBean>) response.data;
				}
				

				util.AuditTrail(request, currentUser, "ProductTypeController.addProductType", 
						"User "+ currentUser.getUserEmail()+" Added ProductType +"+productTypeBean.getProductTypeName()+" successfully ",false);
				return new Response(productTypeBeanList,StatusConstants.SUCCESS,LayOutPageConstants.PRODUCTTYPE);


			}catch(Exception e){
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ProductTypeController.addProductType",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/updateProductType/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response updateProductType(@PathVariable("sessionId") String sessionId,
			@RequestBody ProductTypeBean productTypeBean, HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{

				ProductType productType = productTypeService.getProductTypeByProductTypeId(Integer.valueOf(productTypeBean.getProductTypeId()),currentUser.getCompany().getCompanyId());
				productType.setProductTypeName(productTypeBean.getProductTypeName());
				productType.setLastUpdated(new Date());
				productType.setUpdatedBy(currentUser.getUserId());
				productTypeService.updateProductType(productType,currentUser.getCompany().getCompanyId());
				

				util.AuditTrail(request, currentUser, "ProductTypeController.updateProductType", 
						"User "+ currentUser.getUserEmail()+" Updated Product Type +"+productTypeBean.getProductTypeName()+" successfully ",false);
				return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.PRODUCTTYPE);


			}catch(Exception e){
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ProductTypeController.updateProductType",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllProductTypes/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProductTypes(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<ProductTypeBean> productTypeBeanList = new ArrayList<>();
		List<ProductType> productTypeList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");


			try {

				productTypeList = productTypeService.getAllProductTypes(currentUser.getCompany().getCompanyId());
				if (productTypeList != null) {
					for (ProductType productType : productTypeList) {

						ProductTypeBean productTypeBean = new ProductTypeBean();
						productTypeBean.setProductTypeId(productType.getProductTypeId().toString());
						productTypeBean.setProductTypeName(productType.getProductTypeName());						
						productTypeBean.setNumberOfProducts(String.valueOf(productService.getCountOfProductsByProductTypeId(productType.getProductTypeId(),currentUser.getCompany().getCompanyId())));
						productTypeBeanList.add(productTypeBean);

					}
					util.AuditTrail(request, currentUser, "ProductTypeController.getAllProductTypes", 
							"User "+ currentUser.getUserEmail()+" retrived all Product Types successfully ",false);
					return new Response(productTypeBeanList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "ProductTypeController.getAllProductTypes", 
							" Product Types are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ProductTypeController.getAllProductTypes",
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

