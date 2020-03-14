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

import com.dowhile.Product;
import com.dowhile.ProductTag;
import com.dowhile.Tag;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.ProductTagBean;
import com.dowhile.frontend.mapping.bean.SupplierBean;
import com.dowhile.frontend.mapping.bean.TagBean;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductTagService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.TagService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/productTags")
public class ProductTagsController {

	// private static Logger logger = Logger.getLogger(ProductTagsController.class.getName());
	@Resource
	private ResourceService resourceService;
	@Resource
	private ServiceUtil util;
	@Resource
	private TagService tagService;
	@Resource
	private ProductTagService productTagService;
	@Resource
	private ProductService productService;

	@RequestMapping("/layout")
	public String getProductTagsControllerPartialPage(ModelMap modelMap) {
		return "productTags/layout";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/ValidateSession/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response ValidateSession(@PathVariable("sessionId") String sessionId,HttpServletRequest request) {
		boolean isValidSession = false;
		if (SessionValidator.isSessionValid(sessionId, request)){
			isValidSession = true;
		}
		if(isValidSession)
			return new Response(MessageConstants.VALID_SESSION,StatusConstants.SUCCESS,LayOutPageConstants.PRODUCT_TAGS);
		else
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/addTag/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response addTag(@PathVariable("sessionId") String sessionId,
			@RequestBody TagBean tagBean, HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			
			try{
				List<TagBean> tagBeanList = new ArrayList<>();
				Tag tag = new Tag();
				tag.setActiveIndicator(true);
				tag.setUserByCreatedBy(currentUser);
				tag.setCreatedDate(new Date());
				tag.setLastUpdated(new Date());
				tag.setTagName(tagBean.getTagName());
				tag.setUserByUpdatedBy(currentUser);
				tag.setCompany(currentUser.getCompany());
				tagService.addTag(tag,currentUser.getCompany().getCompanyId());
				
				Response response = getAllTags(sessionId, request);

				if(response.status.equals(StatusConstants.SUCCESS)){
					tagBeanList = (List<TagBean>) response.data;
				}

				util.AuditTrail(request, currentUser, "ProductTagsController.addTag", 
						"User "+ currentUser.getUserEmail()+" Added Tag +"+tagBean.getTagName()+" successfully ",false);
				return new Response(tagBeanList,StatusConstants.SUCCESS,LayOutPageConstants.PRODUCT_TAGS);


			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ProductTagsController.addTag",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/updateTag/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response updateTag(@PathVariable("sessionId") String sessionId,
			@RequestBody TagBean tagBean, HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{

				Tag tag = tagService.getTagByTagId(Integer.valueOf(tagBean.getTagId()),currentUser.getCompany().getCompanyId());
				tag.setLastUpdated(new Date());
				tag.setTagName(tagBean.getTagName());
				tag.setUserByUpdatedBy(currentUser);
				tagService.updateTag(tag,currentUser.getCompany().getCompanyId());

				util.AuditTrail(request, currentUser, "ProductTagsController.updateTag", 
						"User "+ currentUser.getUserEmail()+" Updated Tag +"+tagBean.getTagName()+" successfully ",false);
				return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.PRODUCT_TAGS);


			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ProductTagsController.updateTag",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/addProductTag/{sessionId}/{productId}", method = RequestMethod.POST)
	public @ResponseBody
	Response addProductTag(@PathVariable("sessionId") String sessionId,@PathVariable("productId") String productId,
			@RequestBody List<ProductTagBean> productTagBeanList, HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				if(productTagBeanList!=null){
					Product product = productService.getProductByProductId(Integer.valueOf(productId), currentUser.getCompany().getCompanyId());
					for(ProductTagBean productTagBean:productTagBeanList){
						//we are setting productId as -1 if its not already added in productTag table
						if(productTagBean.getProductId().equalsIgnoreCase("-1")){
							ProductTag productTag = new ProductTag();
							productTag.setActiveIndicator(true);
							productTag.setUserByCreatedBy(currentUser);
							productTag.setLastUpdated(new Date());
							productTag.setProduct(product);
							Tag newtag = tagService.getTagByTagId(Integer.valueOf(productTagBean.getTagId()),currentUser.getCompany().getCompanyId());
							productTag.setTag(newtag);
							productTag.setUserByUpdatedBy(currentUser);
							productTag.setCreatedDate(new Date());
							productTag.setProductTagUuid(product.getProductUuid());
							productTag.setCompany(currentUser.getCompany());
							productTagService.addProductTag(productTag,currentUser.getCompany().getCompanyId());
						}
					}
					util.AuditTrail(request, currentUser, "ProductTagsController.addProductTag", 
							"User "+ currentUser.getUserEmail()+" added product Tag/s  successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "ProductTagsController.addProductTag", 
							"User "+ currentUser.getUserEmail()+" Unable to add product Tag/s because list is empty  ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}
			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ProductTagsController.updateTag",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/deleteProductTag/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response deleteProductTag(@PathVariable("sessionId") String sessionId,
			@RequestBody ProductTagBean productTagBean, HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				ProductTag productTag = productTagService.getProductTagByProductTagIdProductIdCompanyId(
						Integer.valueOf(productTagBean.getProductTagId()), Integer.valueOf(productTagBean.getProductId()), currentUser.getCompany().getCompanyId());
				if(productTag!=null){
					productTagService.deleteProductTag(productTag, currentUser.getCompany().getCompanyId());
					util.AuditTrail(request, currentUser, "ProductTagsController.deleteProductTag", 
							"User "+ currentUser.getUserEmail()+" deleted product Tag +"+productTagBean.getTagName()+" successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "ProductTagsController.deleteProductTag", 
							"User "+ currentUser.getUserEmail()+" unable to delete product Tag +"+productTagBean.getTagName()+" successfully ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ERROR,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ProductTagsController.deleteProductTag",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllTags/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllTags(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<TagBean> tagBeanList = new ArrayList<>();
		List<Tag> tagsList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");


			try {

				tagsList = tagService.getAllTags(currentUser.getCompany().getCompanyId());
				if (tagsList != null) {
					for (Tag tag : tagsList) {

						TagBean tagBean = new TagBean();
						tagBean.setTagId(tag.getTagId().toString());
						tagBean.setTagName(tag.getTagName());
						tagBean.setNumberOfProducts(String.valueOf(productTagService.getCountOfProductTagsByTagId(tag.getTagId(),currentUser.getCompany().getCompanyId())));
						tagBeanList.add(tagBean);

					}
					util.AuditTrail(request, currentUser, "ProductTagsController.getAllTags", 
							"User "+ currentUser.getUserEmail()+" retrived all Tags successfully ",false);
					return new Response(tagBeanList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "ProductTagsController.getAllTags", 
							" Tags are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ProductTagsController.getAllTags",
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

