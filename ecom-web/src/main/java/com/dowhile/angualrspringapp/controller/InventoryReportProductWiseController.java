/**
 * 
 */
package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Configuration;
import com.dowhile.InventoryReport;
import com.dowhile.Outlet;
import com.dowhile.ProductSummmary;
import com.dowhile.User;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.InventoryReportControllerBean;
import com.dowhile.controller.bean.InventoryReportProductWiseControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.InventoryReportBean;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.ProductBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.InventoryReportService;
import com.dowhile.service.OutletService;
import com.dowhile.service.ProductSummmaryService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.ControllerUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * @author Hafiz Yameen Bashir
 *
 */
@Controller
@RequestMapping("/inventoryReportProductWise")
public class InventoryReportProductWiseController {

	private static Logger logger = Logger.getLogger(InventoryReportProductWiseController.class.getName());
	@Resource
	private ServiceUtil util;
	@Resource
	private InventoryReportService inventoryReportService;
	@Resource
	private OutletService outletService;
	@Resource
	private ProductSummmaryService productSummmaryService;
	@Resource
	private ConfigurationService configurationService;
	
	private static List<OutletBean> outletBeans;

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getInventoryReportProductWiseControllerData/{sessionId}/{outletName}", method = RequestMethod.POST)
	public @ResponseBody Response getInventoryReportProductWiseControllerData(@PathVariable("sessionId") String sessionId,
			@PathVariable("outletName") String outletName,HttpServletRequest request) {

		List<ProductBean> productBeanList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				if(currentUser.getRole().getRoleId()==1 && currentUser.getOutlet().getIsHeadOffice()!=null && currentUser.getOutlet().getIsHeadOffice().toString()=="true"){
					Response response = getOutlets(sessionId, request);
					if(response.status.equals(StatusConstants.SUCCESS)){
						outletBeans = (List<OutletBean>) response.data;
					}
				}
				Response response = getAllProducts(sessionId, outletName, "false", request);
						
				if(response.status.equals(StatusConstants.SUCCESS)){
					productBeanList = (List<ProductBean>) response.data;
				}
				
				InventoryReportProductWiseControllerBean inventoryReportProductWiseControllerBean = new InventoryReportProductWiseControllerBean();
				inventoryReportProductWiseControllerBean.setProductBeanList(productBeanList);
				inventoryReportProductWiseControllerBean.setOutletBeans(outletBeans);
				
				util.AuditTrail(request, currentUser, "InventoryReportProductWiseController.getInventoryReportProductWiseControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived getInventoryReportProductWiseControllerData data successfully ",false);
				return new Response(inventoryReportProductWiseControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryReportProductWiseController.getInventoryReportProductWiseControllerData",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllProducts/{sessionId}/{outletName}/{loadAllProducts}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProducts(@PathVariable("sessionId") String sessionId,@PathVariable("outletName") String outletName,
			@PathVariable("loadAllProducts") String loadAllProducts,HttpServletRequest request) {

		List<ProductBean> productBeanList = new ArrayList<>();
		List<ProductVariantBean> productVariantBeanList = new ArrayList<>();
		List<ProductSummmary> productSummmaries =  null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				if(currentUser.getRole().getRoleId()==1 && currentUser.getOutlet().getIsHeadOffice()!=null && currentUser.getOutlet().getIsHeadOffice().toString()=="true"){
					if(outletName==null||outletName.equalsIgnoreCase("")||outletName.equalsIgnoreCase("undefined")||outletName.equalsIgnoreCase("All Outlets")){
						
						if(loadAllProducts.equalsIgnoreCase("true")){
							productSummmaries = productSummmaryService.getAllProductSummmaryByCompanyIdOutletId(currentUser.getCompany().getCompanyId(),currentUser.getOutlet().getOutletId());
						}else{
							productSummmaries = productSummmaryService.getTenNewProductSummmaryByCompanyIdOutletId(currentUser.getCompany().getCompanyId(),currentUser.getOutlet().getOutletId());
						}
						//productSummmaries = productSummmaryService.getAllProductSummmaryByCompanyIdOutletId(currentUser.getCompany().getCompanyId(),currentUser.getOutlet().getOutletId());
					}else{
						int outletId = ControllerUtil.getOutletIdByOutletName(outletName,outletBeans);
						if(outletId==0)
							outletId = currentUser.getOutlet().getOutletId();
						if(loadAllProducts.equalsIgnoreCase("true")){
							productSummmaries = productSummmaryService.getAllProductSummmaryByCompanyIdOutletId(currentUser.getCompany().getCompanyId(),outletId);
						}else{
							productSummmaries = productSummmaryService.getTenNewProductSummmaryByCompanyIdOutletId(currentUser.getCompany().getCompanyId(),outletId);
						}
//						productSummmaries = productSummmaryService.getAllProductSummmaryByCompanyIdOutletId(currentUser.getCompany().getCompanyId(),outletId);
					}
					
				}else{
					if(loadAllProducts.equalsIgnoreCase("true")){
						productSummmaries = productSummmaryService.getAllProductSummmaryByCompanyIdOutletId(currentUser.getCompany().getCompanyId(),currentUser.getOutlet().getOutletId());
					}else{
						productSummmaries = productSummmaryService.getTenNewProductSummmaryByCompanyIdOutletId(currentUser.getCompany().getCompanyId(),currentUser.getOutlet().getOutletId());
					}
//					productSummmaries = productSummmaryService.getAllProductSummmaryByCompanyIdOutletId(currentUser.getCompany().getCompanyId(),currentUser.getOutlet().getOutletId());
				}
				if (productSummmaries != null) {
					for (ProductSummmary productSummmary : productSummmaries) {
						productVariantBeanList = new ArrayList<>();
						ProductBean productBean = new ProductBean();
						productBean.setProductId(String.valueOf(productSummmary.getId().getId()));
						productBean.setProductName(productSummmary.getId().getProductName());
						productBean.setSupplierName(productSummmary.getId().getContactName());
						productBean.setBrandName(productSummmary.getId().getBrandName());
						productBean.setOutletName(productSummmary.getId().getOutletName());
						productBean.setImagePath(productSummmary.getId().getImagePath());
						productBean.setNetPrice(String.valueOf(productSummmary.getId().getNetPrice().setScale(2, RoundingMode.HALF_EVEN)));
						productBean.setVarinatsCount(String.valueOf(productSummmary.getId().getVariantCount()));
						if(productSummmary.getId().getVariantProducts().equalsIgnoreCase(ControllersConstants.TRUE) && productSummmary.getId().getStandardProduct().equalsIgnoreCase(ControllersConstants.TRUE)){
							productBean.setInventoryCount(String.valueOf(productSummmary.getId().getVariantInventoryCount()));
						}
						/*else if(!product.getStandardProduct().equalsIgnoreCase(ControllersConstants.TRUE)){
							productBean.setInventoryCount(String.valueOf(productSummmary.getId().get compositeProductService.getCountOfInventoryByProductUuId(product.getProductUuid(),currentUser.getCompany().getCompanyId())));
						}*/
						else{
							productBean.setInventoryCount(String.valueOf(productSummmary.getId().getCurrentInventory()));
						}
						
						productBean.setProductVariantsBeans(productVariantBeanList);

						/*List<ProductTag> productTags = productTagService.getAllProductTagsByProductId(product.getProductId(),currentUser.getCompany().getCompanyId());
						if(productTags!=null){
							for(ProductTag productTag:productTags){
								ProductTagBean productTagBean = new ProductTagBean();
								productTagBean.setProductTagId(productTag.getProductTagId().toString());
								productTagBean.setProductTagName(tagService.getTagByTagId(productTag.getTag().getTagId(),currentUser.getCompany().getCompanyId()).getTagName());
								productTagBeanList.add(productTagBean);
							}
						}
						productBean.setProductTagBeanList(productTagBeanList);*/
						productBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(productSummmary.getId().getCreatedDate()));
						productBeanList.add(productBean);
					}
					
					util.AuditTrail(request, currentUser, "InventoryReportProductWiseController.getAllProducts", 
							"User "+ currentUser.getUserEmail()+" retrived all Products successfully ",false);
					
					return new Response(productBeanList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "InventoryReportProductWiseController.getAllProducts", 
							" Products are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryReportProductWiseController.getAllProducts",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getOutlets/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getOutlets(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<OutletBean> outletBeans = new ArrayList<>();
		List<Outlet> outlets = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				outlets = outletService.getOutlets(currentUser.getCompany().getCompanyId());
				if(outlets!=null){
					for(Outlet outlet:outlets){
						OutletBean outletBean = new OutletBean();
						outletBean.setOutletId(outlet.getOutletId().toString());
						outletBean.setOutletName(outlet.getOutletName());
						outletBeans.add(outletBean);
					}
					return new Response(outletBeans,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}
				else{
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
	
}
