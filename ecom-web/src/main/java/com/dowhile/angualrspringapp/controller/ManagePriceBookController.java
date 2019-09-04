package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Configuration;
import com.dowhile.ContactGroup;
import com.dowhile.Outlet;
import com.dowhile.PriceBook;
import com.dowhile.PriceBookDetail;
import com.dowhile.PriceBookDetailSummary;
import com.dowhile.Product;
import com.dowhile.ProductTag;
import com.dowhile.ProductVariant;
import com.dowhile.Tag;
import com.dowhile.User;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.NewPriceBookControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.CustomerGroupBean;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.PriceBookBean;
import com.dowhile.frontend.mapping.bean.ProductTagBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.frontend.mapping.bean.TagBean;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.ContactGroupService;
import com.dowhile.service.OutletService;
import com.dowhile.service.PriceBookDetailService;
import com.dowhile.service.PriceBookDetailSummaryService;
import com.dowhile.service.PriceBookService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductTagService;
import com.dowhile.service.ProductVariantService;
import com.dowhile.service.SalesTaxService;
import com.dowhile.service.TagService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * Yameen Bashir
 */
@Controller
@RequestMapping("/managePriceBook")
public class ManagePriceBookController {

	@Resource
	private ContactGroupService customerGroupService;
	@Resource
	private ServiceUtil util;
	@Resource
	private OutletService outletService;
	@Resource
	private PriceBookService priceBookService;
	@Resource
	private ProductVariantService productVariantService;
	@Resource
	private ProductService productService;
	@Resource
	private SalesTaxService salesTaxService;
	@Resource
	private PriceBookDetailService priceBookDetailService;
	@Resource
	private PriceBookDetailSummaryService priceBookDetailSummaryService;
	@Resource
	private ProductTagService productTagService;
	@Resource
	private TagService tagService;
	@Autowired
	ProductTagsController ProductTagsController ;
	@Resource
	private ConfigurationService configurationService;

	@SuppressWarnings("rawtypes")
	private Map outletMap = new HashMap<>();
	@SuppressWarnings("rawtypes")
	private Map contactGroupMap = new HashMap<>();
	@SuppressWarnings("rawtypes")
	private Map productMap = new HashMap<>();
	@SuppressWarnings("rawtypes")
	private Map tagMap = new HashMap<>();

	@RequestMapping("/layout")
	public String getManagePriceBookControllerPartialPage(ModelMap modelMap) {
		return "managePriceBook/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getManagePriceBookControllerData/{sessionId}/{priceBookId}", method = RequestMethod.POST)
	public @ResponseBody Response getManagePriceBookControllerData(@PathVariable("sessionId") String sessionId,
			@PathVariable("priceBookId") String priceBookId,HttpServletRequest request) {

		List<OutletBean> outletBeans = null;
		List<CustomerGroupBean> customerGroupBeansList = null;
		PriceBookBean priceBookBean = null;
		List<ProductVariantBean> productBeansList = null;
		List<ProductVariantBean> productVariantBeansList = null;
		List<ProductVariantBean> priceBookProductVariantBeansList = null;
		List<ProductTagBean> productTagBeanList = null;
		List<TagBean> tagBeanList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {

				Response response = getOutletsForDropDown(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					outletBeans = (List<OutletBean>) response.data;
				}
				response = getAllCustomerGroups(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					customerGroupBeansList = (List<CustomerGroupBean>) response.data;
				}
				response = getPriceBookByPriceBookId(sessionId, priceBookId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					priceBookBean = (PriceBookBean) response.data;
				}
				response = getAllProducts(sessionId, priceBookBean.getOutletId(), request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					productBeansList = (List<ProductVariantBean>) response.data;
				}
				response = getProductVariants(sessionId,priceBookBean.getOutletId(), request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					productVariantBeansList = (List<ProductVariantBean>) response.data;
				}
				response = getPriceBookDetailByPriceBookId(sessionId, priceBookId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					priceBookProductVariantBeansList = (List<ProductVariantBean>) response.data;
				}
				
				response = getAllProductTags(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					 productTagBeanList = (List<ProductTagBean>) response.data;
				}
				
				response = ProductTagsController.getAllTags(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					tagBeanList = (List<TagBean>) response.data;
				}

				NewPriceBookControllerBean newPriceBookControllerBean = new NewPriceBookControllerBean();
				newPriceBookControllerBean.setCustomerGroupBeansList(customerGroupBeansList);
				newPriceBookControllerBean.setOutletBeans(outletBeans);
				newPriceBookControllerBean.setPriceBookBean(priceBookBean);
				newPriceBookControllerBean.setProductBeansList(productBeansList);
				newPriceBookControllerBean.setProductVariantBeansList(productVariantBeansList);
				newPriceBookControllerBean.setPriceBookProductVariantBeansList(priceBookProductVariantBeansList);
				newPriceBookControllerBean.setProductTagBeanList(productTagBeanList);
				newPriceBookControllerBean.setTagBeanList(tagBeanList);
				
				Configuration productTagConfiguration = configurationService.getConfigurationByPropertyNameByCompanyId("SHOW_PRODCUT_TAG",currentUser.getCompany().getCompanyId());
				if(productTagConfiguration!=null && productTagConfiguration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
					newPriceBookControllerBean.setShowProductTag(ControllersConstants.TRUE);
				}else{
					newPriceBookControllerBean.setShowProductTag(ControllersConstants.FALSE);
				}

				util.AuditTrail(request, currentUser, "ManagePriceBookController.getManagePriceBookControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived ManagePriceBookControllerData successfully ",false);
				return new Response(newPriceBookControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManagePriceBookController.getManagePriceBookControllerData",
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
	@RequestMapping(value = "/getOutletsForDropDown/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getOutletsForDropDown(@PathVariable("sessionId") String sessionId,
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
						outletMap.put(outlet.getOutletId(), outlet);
					}
					for(Outlet outlet:outlets){
						OutletBean outletBean = new OutletBean();
						outletBean.setOutletId(outlet.getOutletId().toString());
						outletBean.setOutletName(outlet.getOutletName());

						outletBeans.add(outletBean);
					}
					//For All outlet case
					OutletBean outletBean = new OutletBean();
					outletBean.setOutletId("-1");
					outletBean.setOutletName(ControllersConstants.ALL_OUTLETS);

					outletBeans.add(outletBean);
					util.AuditTrail(request, currentUser, "ManagePriceBookController.getOutletsForDropDown", 
							"User "+ currentUser.getUserEmail()+" retrived all outlets successfully ",false);
					return new Response(outletBeans,StatusConstants.SUCCESS,LayOutPageConstants.NEW_PRICEBOOK);
				}else{
					util.AuditTrail(request, currentUser, "ManagePriceBookController.getOutletsForDropDown", "User "+ 
							currentUser.getUserEmail()+" Unable to retrived all outlets",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManagePriceBookController.getOutletsForDropDown",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllProductTags/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getAllProductTags(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			List<ProductTagBean> productTagBeanList = new ArrayList<>();
			try {
				List<ProductTag> productTags = productTagService.getAllProductTagsByCompanyId(currentUser.getCompany().getCompanyId());
				if(productTags!=null){
					List<Tag> tagList = tagService.getAllTags(currentUser.getCompany().getCompanyId());
					for(Tag tag:tagList){
						tagMap.put(tag.getTagId(), tag);
					}
					for(ProductTag productTag:productTags){
						ProductTagBean productTagBean = new ProductTagBean();
						productTagBean.setProductTagId(productTag.getProductTagId().toString());
						productTagBean.setTagId(productTag.getTag().getTagId()+"");
						productTagBean.setProductId(productTag.getProduct().getProductId().toString());
						Tag tAg = (Tag) tagMap.get(productTag.getTag().getTagId());
						productTagBean.setProductTagName(tAg.getTagName());
						productTagBean.setTagName(productTagBean.getProductTagName());
						productTagBean.setProductUuid(productTag.getProductTagUuid());
						productTagBeanList.add(productTagBean);
					}
					util.AuditTrail(request, currentUser, "ManagePriceBookController.getAllProductTags", 
							"User "+ currentUser.getUserEmail()+" retrived all product tags successfully ",false);
					return new Response(productTagBeanList,StatusConstants.SUCCESS,LayOutPageConstants.NEW_PRICEBOOK);
				}else{
					util.AuditTrail(request, currentUser, "ManagePriceBookController.getAllProductTags", "User "+ 
							currentUser.getUserEmail()+" Unable to retrived all product tags",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}
			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManagePriceBookController.getAllProductTags",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllCustomerGroups/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllCustomerGroups(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<CustomerGroupBean> customerGroupBeansList = new ArrayList<>();
		List<ContactGroup> customerGroupList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				customerGroupList = customerGroupService.GetAllContactGroup(currentUser.getCompany().getCompanyId());
				if (customerGroupList != null) {
					for(ContactGroup contactGroup:customerGroupList){
						contactGroupMap.put(contactGroup.getContactGroupId(), contactGroup);
					}
					for (ContactGroup customerGroup : customerGroupList) {
						CustomerGroupBean customerGroupBean = new CustomerGroupBean();
						customerGroupBean.setCustomerGroupId(customerGroup.getContactGroupId().toString());
						customerGroupBean.setCustomerGroupName(customerGroup.getContactGroupName().toString());
						customerGroupBeansList.add(customerGroupBean);
					}
					util.AuditTrail(request, currentUser, "ManagePriceBookController.getAllCustomerGroups", 
							"User "+ currentUser.getUserEmail()+" retrived all Customer Groups successfully ",false);
					return new Response(customerGroupBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "ManagePriceBookController.getAllCustomerGroups", 
							" Customer Groups are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManagePriceBookController.getAllCustomerGroups",
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
	@RequestMapping(value = "/getPriceBookByPriceBookId/{sessionId}/{priceBookId}", method = RequestMethod.POST)
	public @ResponseBody Response getPriceBookByPriceBookId(@PathVariable("sessionId") String sessionId,
			@PathVariable("priceBookId") String priceBookId,HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				List<OutletBean> outletBeans =  new ArrayList<>();

				PriceBook priceBook = priceBookService.getPriceBookByPriceBookIdCompanyId(Integer.valueOf(priceBookId), currentUser.getCompany().getCompanyId());
				if(priceBook!=null){
					PriceBookBean priceBookBean = new PriceBookBean();
					if(priceBook.getOutlet()!=null){
						Outlet outlet = (Outlet)outletMap.get(priceBook.getOutlet().getOutletId());
						priceBookBean.setOutletId(outlet.getOutletId().toString());
						priceBookBean.setOutletName(outlet.getOutletName());
					}else{
						priceBookBean.setOutletName(ControllersConstants.ALL_OUTLETS);
						priceBookBean.setOutletId("-1");
					}
					ContactGroup contactGroup = (ContactGroup)contactGroupMap.get(priceBook.getContactGroup().getContactGroupId());
					priceBookBean.setContactGroupId(contactGroup.getContactGroupId().toString());
					priceBookBean.setContactGroupName(contactGroup.getContactGroupName());
					priceBookBean.setIsValidOnStore(priceBook.getIsValidOnStore()==true?ControllersConstants.TRUE:ControllersConstants.FALSE);
					priceBookBean.setPriceBookId(priceBook.getPriceBookId().toString());
					priceBookBean.setPriceBookName(priceBook.getPriceBookName());
					priceBookBean.setValidFrom(priceBook.getValidFrom().toString());
					priceBookBean.setValidTo(priceBook.getValidTo().toString());
					priceBookBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(priceBook.getCreatedDate()));
					priceBookBean.setFlatSale(priceBook.getFlatSale());
					priceBookBean.setFlatDiscount(priceBook.getFlatDiscount().toString());
					priceBookBean.setActive(priceBook.isActiveIndicator()==true?ControllersConstants.TRUE:ControllersConstants.FALSE);
					priceBookBean.setOuteletsGroup(priceBook.getOuteletsGroup());
					
					String [] outletGroup = priceBook.getOuteletsGroup().split(",");
					for(String outletId:outletGroup){
						Outlet outlet = (Outlet) outletMap.get(Integer.valueOf(outletId));
						OutletBean outletBean = new OutletBean();
						outletBean.setOutletId(outlet.getOutletId().toString());
						outletBean.setOutletName(outlet.getOutletName());

						outletBeans.add(outletBean);
					}
					priceBookBean.setOutletBeans(outletBeans);
					
					util.AuditTrail(request, currentUser, "ManagePriceBookController.getPriceBookByPriceBookId", 
							"User "+ currentUser.getUserEmail()+" retrive PriceBook successfully ",false);
					return new Response(priceBookBean, StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "ManagePriceBookController.getPriceBookByPriceBookId", 
							"PriceBook not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}

			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManagePriceBookController.getPriceBookByPriceBookId",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getPriceBookDetailByPriceBookId/{sessionId}/{priceBookId}", method = RequestMethod.POST)
	public @ResponseBody Response getPriceBookDetailByPriceBookId(@PathVariable("sessionId") String sessionId,
			@PathVariable("priceBookId") String priceBookId,HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
				List<PriceBookDetailSummary> priceBookDetailSummaryList = priceBookDetailSummaryService.getPriceBookDetailSummaryByPriceBookIdCompanyIdGroupByUuId(Integer.valueOf(priceBookId), currentUser.getCompany().getCompanyId());
				if(priceBookDetailSummaryList!=null){
					for(PriceBookDetailSummary priceBookDetailSummary:priceBookDetailSummaryList){
						ProductVariantBean productVariantBean = new ProductVariantBean();
						productVariantBean.setDiscount(priceBookDetailSummary.getId().getDiscount().toString());
						productVariantBean.setMarkupPrct(priceBookDetailSummary.getId().getMarkup().toString());
						productVariantBean.setProductId(String.valueOf(priceBookDetailSummary.getId().getProductAssocicationId()));
						if(priceBookDetailSummary.getId().getProductVarientAssocicationId()==0){
							productVariantBean.setVariantAttributeName(priceBookDetailSummary.getId().getProductName());
						}else{
							productVariantBean.setProductVariantId(String.valueOf(priceBookDetailSummary.getId().getProductVarientAssocicationId()));
							productVariantBean.setVariantAttributeName(priceBookDetailSummary.getId().getProductName() + "-" + priceBookDetailSummary.getId().getVariantAttributeName());
						}
						productVariantBean.setSupplyPriceExclTax(priceBookDetailSummary.getId().getSupplyPrice().toString());
						productVariantBean.setPriceBookDetailId(String.valueOf(priceBookDetailSummary.getId().getPriceBookDetailId()));
						productVariantBean.setPriceBookId(String.valueOf(priceBookDetailSummary.getId().getPriceBookAssocicationId()));
						productVariantBean.setProductVariantUuid(priceBookDetailSummary.getId().getUuid());
						productVariantBean.setIndivisualUpdate(false);
						productVariantBean.setAllUpdate(false);
						productVariantBeansList.add(productVariantBean);
					}

					util.AuditTrail(request, currentUser, "ManagePriceBookController.getPriceBookDetailByPriceBookId", 
							"User "+ currentUser.getUserEmail()+" retrive PriceBook detail successfully ",false);
					return new Response(productVariantBeansList, StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "ManagePriceBookController.getPriceBookDetailByPriceBookId", 
							"PriceBook detail not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}

			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManagePriceBookController.getPriceBookDetailByPriceBookId",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getProductVariants/{sessionId}/{outletId}", method = RequestMethod.POST)
	public @ResponseBody Response getProductVariants(@PathVariable("sessionId") String sessionId,
			@PathVariable("outletId") String outletId,
			HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			List<ProductVariant> productVariantList = null;
			try {	
				//New rule implemented whatever the case we only bring warehouse product variants for pricebook
				productVariantList = productVariantService.getAllProductVariantsGroupbyUuid(currentUser.getCompany().getCompanyId());
				/*if(outletId.equalsIgnoreCase("-1")||outletId.equalsIgnoreCase("")||outletId.isEmpty()){
					productVariantList = productVariantService.getAllProductVariantsGroupbyUuid(currentUser.getCompany().getCompanyId());
				}else{
					productVariantList = productVariantService.getAllProductVariantsByOutletIdGroupbyUuid(Integer.valueOf(outletId),currentUser.getCompany().getCompanyId());
				}*/
				
				if(productVariantList != null){
					for(ProductVariant productVariant:productVariantList){
						ProductVariantBean productVariantBean = new ProductVariantBean();
						productVariantBean.setIsProduct("false");
						productVariantBean.setProductVariantId(productVariant.getProductVariantId().toString());
						Product product = (Product)productMap.get(productVariant.getProduct().getProductId());
						productVariantBean.setProductName(product.getProductName());					
						productVariantBean.setVariantAttributeName(product.getProductName() + "-" + productVariant.getVariantAttributeName());
						productVariantBean.setProductId(product.getProductId().toString());
						if(product.getSupplyPriceExclTax() != null){
							productVariantBean.setSupplyPriceExclTax(productVariant.getSupplyPriceExclTax().toString());
							productVariantBean.setMarkupPrct(productVariant.getMarkupPrct().toString());

						}
						productVariantBean.setProductVariantUuid(productVariant.getProductVariantUuid());
						productVariantBean.setProductUuid(product.getProductUuid());
						productVariantBean.setIndivisualUpdate(false);
						productVariantBean.setAllUpdate(false);
						productVariantBeansList.add(productVariantBean);
					}
					util.AuditTrail(request, currentUser, "ManagePriceBookController.getProductVariants", "User "+ 
							currentUser.getUserEmail()+" Get ProductVariants",false);
					return new Response(productVariantBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} 
				else {
					util.AuditTrail(request, currentUser, "ManagePriceBookController.getProductVariants", "User "+ 
							currentUser.getUserEmail()+" Get ProductVariants",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManagePriceBookController.getProductVariants",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}
		else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}	
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllProducts/{sessionId}/{outletId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProducts(@PathVariable("sessionId") String sessionId,
			@PathVariable("outletId") String outletId,HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			List<Product> productList = null;
			try {	
				//New rule implemented so whatever the case we only bring warehouse products for pricebook
				productList = productService.getAllProductsByCompanyIdGroupByProductUuId(currentUser.getCompany().getCompanyId());
				/*if(outletId.equalsIgnoreCase("-1")||outletId.equalsIgnoreCase("")||outletId.isEmpty()){
					productList = productService.getAllProductsByCompanyIdGroupByProductUuId(currentUser.getCompany().getCompanyId());
				}else{
					productList = productService.getAllProductsByOutletId(Integer.valueOf(outletId));
				}*/
				
				if(productList != null){
					for(Product product:productList){
						productMap.put(product.getProductId(), product);
					}
					for(Product product:productList){
						ProductVariantBean productVariantBean = new ProductVariantBean();
						productVariantBean.setProductId(product.getProductId().toString());
						productVariantBean.setOutletId(product.getOutlet().getOutletId().toString());
						if (product.getVariantProducts().equalsIgnoreCase("true")){
							productVariantBean.setIsProduct("false");
						}
						else
						{
							productVariantBean.setIsProduct("true");
						}
						productVariantBean.setProductVariantId(product.getProductId().toString());					
						productVariantBean.setVariantAttributeName(product.getProductName());

						productVariantBean.setProductName(product.getProductName());
						if(product.getSupplyPriceExclTax() != null){
							productVariantBean.setSupplyPriceExclTax(product.getSupplyPriceExclTax().toString());
							productVariantBean.setMarkupPrct(product.getMarkupPrct().toString());
						}
						productVariantBean.setProductUuid(product.getProductUuid());
						productVariantBean.setProductVariantUuid(product.getProductUuid());
						productVariantBean.setIndivisualUpdate(false);
						productVariantBean.setAllUpdate(false);
						productVariantBeansList.add(productVariantBean);
					}
					util.AuditTrail(request, currentUser, "ManagePriceBookController.getAllProducts", "User "+ 
							currentUser.getUserEmail()+" Get Products and Products",false);
					return new Response(productVariantBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} 
				else {
					util.AuditTrail(request, currentUser, "ManagePriceBookController.getAllProducts", "User "+ 
							currentUser.getUserEmail()+" Get Products",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManagePriceBookController.getAllProducts",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}
		else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}	
	}

	/*@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/manageProductsInPriceBook/{sessionId}/{priceBookId}", method = RequestMethod.POST)
	public @ResponseBody Response manageProductsInPriceBook(@PathVariable("sessionId") String sessionId,
			@PathVariable("priceBookId") String priceBookId,
			@RequestBody List<ProductVariantBean> productVariantBeansList,HttpServletRequest request) {

		List<PriceBookDetail> bookDetailsNewAdd = new ArrayList<>();
		List<PriceBookDetail> priceBookDetailsUpdateList = new ArrayList<>();
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			Map<Integer, Product> productMap = productService.getProductsMapByProductId(currentUser.getCompany().getCompanyId());
			Map<String, List<Product>> productUUidMap = productService.getAllProductsMapByUuid(currentUser.getCompany().getCompanyId());
			Map<Integer, ProductVariant> productVariantMap = productVariantService.getProductsVariantMapByProductId(currentUser.getCompany().getCompanyId());
			Map<String, List<ProductVariant>> productVariantUUidMap = productVariantService.getAllProductsVariantMapByUuid(currentUser.getCompany().getCompanyId());
			Map<Integer, PriceBookDetail> priceMap =	priceBookDetailService.getPriceBookDetailMapByPriceBookDetailIdCompanyId(currentUser.getCompany().getCompanyId());
			
			try {
				PriceBook priceBook = priceBookService.getPriceBookByPriceBookIdCompanyId(Integer.valueOf(priceBookId), currentUser.getCompany().getCompanyId());
				List<PriceBookDetailSummary> priceBookDetailSummaryList = priceBookDetailSummaryService.getPriceBookDetailSummaryByPriceBookIdCompanyId(Integer.valueOf(priceBookId), currentUser.getCompany().getCompanyId());
				if(priceBook!=null){
					if(productVariantBeansList!=null){
						for(ProductVariantBean productVariantBean:productVariantBeansList){
							if(verifyProductOrVariantExistInPriceBookDetail(priceBookDetailSummaryList,Integer.valueOf(productVariantBean.getProductId()),
									productVariantBean.getProductVariantId()!=null?Integer.valueOf(productVariantBean.getProductVariantId()):0)){
								PriceBookDetail priceBookDetail = priceMap.get(Integer.valueOf(productVariantBean.getPriceBookDetailId()));
								
								priceBookDetail.setLastUpdated(new Date());
								priceBookDetail.setActiveIndicator(true);
								if(productVariantBean.getDiscount()!=null && !productVariantBean.getDiscount().equalsIgnoreCase("")){
									priceBookDetail.setDiscount(new BigDecimal(productVariantBean.getDiscount()));
								}else{
									priceBookDetail.setDiscount(new BigDecimal(0));
								}
								if(productVariantBean.getMarkupPrct()!=null && !productVariantBean.getMarkupPrct().equalsIgnoreCase("")){
									priceBookDetail.setMarkup(new BigDecimal(productVariantBean.getMarkupPrct()));
								}else{
									priceBookDetail.setMarkup(new BigDecimal(0));
								}
								priceBookDetail.setPriceBook(priceBook);
								priceBookDetail.setUserByUpdatedBy(currentUser);
								if(priceBook.getOutlet()!=null){
									priceBookDetailsUpdateList.add(priceBookDetail);
								}
								
								//All Outlet Case
								if(priceBook.getOutlet()==null){
									Product product = productMap.get(Integer.valueOf(productVariantBean.getProductId()));
									if(product.getVariantProducts().equalsIgnoreCase("true")){
										List<ProductVariant> productVariants = productVariantUUidMap.get(productVariantBean.getProductVariantUuid());
										for(ProductVariant productVariantlocal:productVariants){
											
											if(!verifyProductOrVariantExistInPriceBookDetail(priceBookDetailSummaryList,productVariantlocal.getProduct().getProductId(),
													productVariantlocal.getProductVariantId())){
												PriceBookDetail priceBookDetaillocal = new PriceBookDetail();
												priceBookDetaillocal.setActiveIndicator(true);
												priceBookDetaillocal.setCompany(currentUser.getCompany());
												priceBookDetaillocal.setLastUpdated(new Date());
												if(productVariantBean.getDiscount()!=null && !productVariantBean.getDiscount().equalsIgnoreCase("")){
													priceBookDetaillocal.setDiscount(new BigDecimal(productVariantBean.getDiscount()));
												}else{
													priceBookDetaillocal.setDiscount(new BigDecimal(0));
												}
												if(productVariantBean.getMarkupPrct()!=null && !productVariantBean.getMarkupPrct().equalsIgnoreCase("")){
													priceBookDetaillocal.setMarkup(new BigDecimal(productVariantBean.getMarkupPrct()));
												}else{
													priceBookDetaillocal.setMarkup(new BigDecimal(0));
												}
												priceBookDetaillocal.setPriceBook(priceBook);
												priceBookDetaillocal.setUserByUpdatedBy(currentUser);
												priceBookDetaillocal.setCreatedDate(new Date());
												priceBookDetaillocal.setUserByCreatedBy(currentUser);
												priceBookDetaillocal.setProduct(productMap.get(productVariantlocal.getProduct().getProductId()));
												priceBookDetaillocal.setProductVariant(productVariantlocal);
												priceBookDetaillocal.setSalesTax(productVariantlocal.getSalesTax());
												priceBookDetaillocal.setSupplyPrice(productVariantlocal.getSupplyPriceExclTax());
												priceBookDetaillocal.setUuid(productVariantlocal.getProductVariantUuid());
												//priceBookDetailService.addPriceBookDetail(priceBookDetail);
												bookDetailsNewAdd.add(priceBookDetaillocal);
											}else{
												PriceBookDetail priceBookDetaillocal = getPriceBookDetailByProductVariantIdPriceBookId(priceMap, productVariantlocal.getProductVariantId(), priceBook.getPriceBookId()); 
												if(priceBookDetaillocal!=null){
													priceBookDetaillocal.setActiveIndicator(true);
													//priceBookDetaillocal.setCompany(currentUser.getCompany());
													priceBookDetaillocal.setLastUpdated(new Date());
													if(productVariantBean.getDiscount()!=null && !productVariantBean.getDiscount().equalsIgnoreCase("")){
														priceBookDetaillocal.setDiscount(new BigDecimal(productVariantBean.getDiscount()));
													}else{
														priceBookDetaillocal.setDiscount(new BigDecimal(0));
													}
													if(productVariantBean.getMarkupPrct()!=null && !productVariantBean.getMarkupPrct().equalsIgnoreCase("")){
														priceBookDetaillocal.setMarkup(new BigDecimal(productVariantBean.getMarkupPrct()));
													}else{
														priceBookDetaillocal.setMarkup(new BigDecimal(0));
													}
													priceBookDetaillocal.setPriceBook(priceBook);
													priceBookDetaillocal.setUserByUpdatedBy(currentUser);
													//priceBookDetaillocal.setCreatedDate(new Date());
													//priceBookDetaillocal.setUserByCreatedBy(currentUser);
													//priceBookDetaillocal.setProduct(productMap.get(productVariantlocal.getProduct().getProductId()));
													//priceBookDetaillocal.setProductVariant(productVariantlocal);
													//priceBookDetaillocal.setSalesTax(productVariantlocal.getSalesTax());
													//priceBookDetaillocal.setSupplyPrice(productVariantlocal.getSupplyPriceExclTax());
													//priceBookDetaillocal.setUuid(productVariantlocal.getProductVariantUuid());
													//priceBookDetaillocal.setPriceBookDetailId(priceBookDetail.getPriceBookDetailId());
													priceBookDetailsUpdateList.add(priceBookDetaillocal);
												}
											}
											//To make sure variant does not exit in price book detail because we are updating variants on the basis of uuid
											// and retrieving on the basis of group by uuid
										}
									}else{
										List<Product> products = productUUidMap.get(product.getProductUuid());
										for(Product productt:products){
											PriceBookDetail priceBookDetaillocal = new PriceBookDetail();
											priceBookDetaillocal.setActiveIndicator(true);
											priceBookDetaillocal.setCompany(currentUser.getCompany());
											priceBookDetaillocal.setLastUpdated(new Date());
											if(productVariantBean.getDiscount()!=null && !productVariantBean.getDiscount().equalsIgnoreCase("")){
												priceBookDetaillocal.setDiscount(new BigDecimal(productVariantBean.getDiscount()));
											}else{
												priceBookDetaillocal.setDiscount(new BigDecimal(0));
											}
											if(productVariantBean.getMarkupPrct()!=null && !productVariantBean.getMarkupPrct().equalsIgnoreCase("")){
												priceBookDetaillocal.setMarkup(new BigDecimal(productVariantBean.getMarkupPrct()));
											}else{
												priceBookDetaillocal.setMarkup(new BigDecimal(0));
											}
											priceBookDetaillocal.setPriceBook(priceBook);
											priceBookDetaillocal.setUserByUpdatedBy(currentUser);
											priceBookDetaillocal.setCreatedDate(new Date());
											//	Product productlocal =  productMap.get(Integer.valueOf(productVariantBean.getProductId()));
											priceBookDetaillocal.setProduct(productt);
											priceBookDetaillocal.setUserByCreatedBy(currentUser);
											priceBookDetaillocal.setSalesTax(productt.getSalesTax());
											priceBookDetaillocal.setSupplyPrice(productt.getSupplyPriceExclTax());
											priceBookDetaillocal.setUuid(productt.getProductUuid());
											//To make sure products does not exit in price book detail because we are updating products on the basis of uuid
											// and retrieving on the basis of group by uuid
											if(!verifyProductOrVariantExistInPriceBookDetail(priceBookDetailSummaryList,productt.getProductId(),
													0)){
												//priceBookDetailService.addPriceBookDetail(priceBookDetail);
												bookDetailsNewAdd.add(priceBookDetaillocal);
											}
										}
									}
								}else{
									//priceBookDetailService.addPriceBookDetail(priceBookDetail);
									bookDetailsNewAdd.add(priceBookDetail);
								}
								//All Outlet Case end here
								//	priceBookDetailService.updatePriceBookDetail(priceBookDetail);
							}else{
								PriceBookDetail priceBookDetail = new PriceBookDetail();
								priceBookDetail.setActiveIndicator(true);
								priceBookDetail.setCompany(currentUser.getCompany());
								priceBookDetail.setLastUpdated(new Date());
								if(productVariantBean.getDiscount()!=null && !productVariantBean.getDiscount().equalsIgnoreCase("")){
									priceBookDetail.setDiscount(new BigDecimal(productVariantBean.getDiscount()));
								}else{
									priceBookDetail.setDiscount(new BigDecimal(0));
								}
								if(productVariantBean.getMarkupPrct()!=null && !productVariantBean.getMarkupPrct().equalsIgnoreCase("")){
									priceBookDetail.setMarkup(new BigDecimal(productVariantBean.getMarkupPrct()));
								}else{
									priceBookDetail.setMarkup(new BigDecimal(0));
								}
								priceBookDetail.setPriceBook(priceBook);
								priceBookDetail.setUserByUpdatedBy(currentUser);
								priceBookDetail.setCreatedDate(new Date());
								
								Product product =  productMap.get(Integer.valueOf(productVariantBean.getProductId()));
								priceBookDetail.setProduct(product);
								if(product.getVariantProducts().equalsIgnoreCase("true")){
									ProductVariant productVariant = productVariantMap.get(Integer.valueOf(productVariantBean.getProductVariantId()));
									priceBookDetail.setProductVariant(productVariant);
									priceBookDetail.setSalesTax(productVariant.getSalesTax());
									priceBookDetail.setSupplyPrice(productVariant.getSupplyPriceExclTax());
									priceBookDetail.setUuid(productVariant.getProductVariantUuid());
								}else{
									priceBookDetail.setSalesTax(product.getSalesTax());
									priceBookDetail.setSupplyPrice(product.getSupplyPriceExclTax());
									priceBookDetail.setUuid(product.getProductUuid());
								}
								priceBookDetail.setUserByCreatedBy(currentUser);
								//All outlet case
								if(priceBook.getOutlet()==null){
									if(product.getVariantProducts().equalsIgnoreCase("true")){
										List<ProductVariant> productVariants = productVariantUUidMap.get(priceBookDetail.getProductVariant().getProductVariantUuid());
										for(ProductVariant productVariantlocal:productVariants){
											PriceBookDetail priceBookDetaillocal = new PriceBookDetail();
											priceBookDetaillocal.setActiveIndicator(true);
											priceBookDetaillocal.setCompany(currentUser.getCompany());
											priceBookDetaillocal.setLastUpdated(new Date());
											if(productVariantBean.getDiscount()!=null && !productVariantBean.getDiscount().equalsIgnoreCase("")){
												priceBookDetaillocal.setDiscount(new BigDecimal(productVariantBean.getDiscount()));
											}else{
												priceBookDetaillocal.setDiscount(new BigDecimal(0));
											}
											if(productVariantBean.getMarkupPrct()!=null && !productVariantBean.getMarkupPrct().equalsIgnoreCase("")){
												priceBookDetaillocal.setMarkup(new BigDecimal(productVariantBean.getMarkupPrct()));
											}else{
												priceBookDetaillocal.setMarkup(new BigDecimal(0));
											}
											priceBookDetaillocal.setPriceBook(priceBook);
											priceBookDetaillocal.setUserByUpdatedBy(currentUser);
											priceBookDetaillocal.setCreatedDate(new Date());
											priceBookDetaillocal.setUserByCreatedBy(currentUser);
											priceBookDetaillocal.setProduct(productVariantlocal.getProduct());
											priceBookDetaillocal.setProductVariant(productVariantlocal);
											priceBookDetaillocal.setSalesTax(productVariantlocal.getSalesTax());
											priceBookDetaillocal.setSupplyPrice(productVariantlocal.getSupplyPriceExclTax());
											priceBookDetaillocal.setUuid(productVariantlocal.getProductVariantUuid());
											//To make sure variant does not exit in price book detail because we are updating variants on the basis of uuid
											// and retrieving on the basis of group by uuid
											if(!verifyProductOrVariantExistInPriceBookDetail(priceBookDetailSummaryList,productVariantlocal.getProduct().getProductId(),
													productVariantlocal.getProductVariantId())){
												//priceBookDetailService.addPriceBookDetail(priceBookDetail);
												bookDetailsNewAdd.add(priceBookDetaillocal);
											}
										}
									}else{
										List<Product> products = productUUidMap.get(product.getProductUuid());
										for(Product productt:products){
											PriceBookDetail priceBookDetaillocal = new PriceBookDetail();
											priceBookDetaillocal.setActiveIndicator(true);
											priceBookDetaillocal.setCompany(currentUser.getCompany());
											priceBookDetaillocal.setLastUpdated(new Date());
											if(productVariantBean.getDiscount()!=null && !productVariantBean.getDiscount().equalsIgnoreCase("")){
												priceBookDetaillocal.setDiscount(new BigDecimal(productVariantBean.getDiscount()));
											}else{
												priceBookDetaillocal.setDiscount(new BigDecimal(0));
											}
											if(productVariantBean.getMarkupPrct()!=null && !productVariantBean.getMarkupPrct().equalsIgnoreCase("")){
												priceBookDetaillocal.setMarkup(new BigDecimal(productVariantBean.getMarkupPrct()));
											}else{
												priceBookDetaillocal.setMarkup(new BigDecimal(0));
											}
											priceBookDetaillocal.setPriceBook(priceBook);
											priceBookDetaillocal.setUserByUpdatedBy(currentUser);
											priceBookDetaillocal.setCreatedDate(new Date());
											//	Product productlocal =  productMap.get(Integer.valueOf(productVariantBean.getProductId()));
											priceBookDetaillocal.setProduct(productt);
											priceBookDetaillocal.setUserByCreatedBy(currentUser);
											priceBookDetaillocal.setSalesTax(productt.getSalesTax());
											priceBookDetaillocal.setSupplyPrice(productt.getSupplyPriceExclTax());
											priceBookDetaillocal.setUuid(productt.getProductUuid());
											//To make sure products does not exit in price book detail because we are updating products on the basis of uuid
											// and retrieving on the basis of group by uuid
											if(!verifyProductOrVariantExistInPriceBookDetail(priceBookDetailSummaryList,productt.getProductId(),
													0)){
												//priceBookDetailService.addPriceBookDetail(priceBookDetail);
												bookDetailsNewAdd.add(priceBookDetaillocal);
											}
										}
									}
								}else{
									//priceBookDetailService.addPriceBookDetail(priceBookDetail);
									bookDetailsNewAdd.add(priceBookDetail);
								}
							}
						}
					}
					priceBookDetailService.addPriceBookDetail(bookDetailsNewAdd);
					priceBookDetailService.updatePriceBookDetailList(priceBookDetailsUpdateList);
					util.AuditTrail(request, currentUser, "ManagePriceBookController.manageProductsInPriceBook", 
							"User "+ currentUser.getUserEmail()+" added products in pricebook successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED, StatusConstants.SUCCESS,LayOutPageConstants.PRICEBOOK);
				}else{
					util.AuditTrail(request, currentUser, "ManagePriceBookController.manageProductsInPriceBook", 
							"PriceBook not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}

			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManagePriceBookController.manageProductsInPriceBook",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}*/
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/manageProductsInPriceBook/{sessionId}/{priceBookId}", method = RequestMethod.POST)
	public @ResponseBody Response manageProductsInPriceBook(@PathVariable("sessionId") String sessionId,
			@PathVariable("priceBookId") String priceBookId,
			@RequestBody List<ProductVariantBean> productVariantBeansList,HttpServletRequest request) {

		List<PriceBookDetail> bookDetailsNewAdd = new ArrayList<>();
		List<PriceBookDetail> priceBookDetailsUpdateList = new ArrayList<>();
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			Map<Integer, Product> productMap = productService.getAllActiveProductsMapByOutletIdCompanyId(currentUser.getOutlet().getOutletId(),currentUser.getCompany().getCompanyId());
			Map<Integer, ProductVariant> productVariantMap = productVariantService.getAllActiveProductsVariantMapByOutletIdCompanyId(currentUser.getOutlet().getOutletId(),currentUser.getCompany().getCompanyId());
			Map<Integer, PriceBookDetail> priceBookDetailMap =	priceBookDetailService.getAllActivePriceBookDetailsMapByPriceBookIdCompanyId(Integer.valueOf(priceBookId), currentUser.getCompany().getCompanyId());
			int skippedUpdatesCount = 0;
			try {
				PriceBook priceBook = priceBookService.getPriceBookByPriceBookIdCompanyId(Integer.valueOf(priceBookId), currentUser.getCompany().getCompanyId());
				if(priceBook!=null && productVariantBeansList!=null){
					for(ProductVariantBean productVariantBean:productVariantBeansList){
						 if(productVariantBean.getPriceBookDetailId()==null||productVariantBean.getPriceBookDetailId().equalsIgnoreCase("")){
							//New entries in pricebookDetail
							PriceBookDetail priceBookDetail = new PriceBookDetail();
							priceBookDetail.setActiveIndicator(true);
							priceBookDetail.setCompany(currentUser.getCompany());
							priceBookDetail.setLastUpdated(new Date());
							if(productVariantBean.getDiscount()!=null && !productVariantBean.getDiscount().equalsIgnoreCase("")){
								priceBookDetail.setDiscount(new BigDecimal(productVariantBean.getDiscount()));
							}else{
								priceBookDetail.setDiscount(new BigDecimal(0));
							}
							if(productVariantBean.getMarkupPrct()!=null && !productVariantBean.getMarkupPrct().equalsIgnoreCase("")){
								priceBookDetail.setMarkup(new BigDecimal(productVariantBean.getMarkupPrct()));
							}else{
								priceBookDetail.setMarkup(new BigDecimal(0));
							}
							priceBookDetail.setPriceBook(priceBook);
							priceBookDetail.setUserByUpdatedBy(currentUser);
							priceBookDetail.setCreatedDate(new Date());
							
							Product product =  productMap.get(Integer.valueOf(productVariantBean.getProductId()));
							priceBookDetail.setProduct(product);
							if(product.getVariantProducts().equalsIgnoreCase("true")){
								ProductVariant productVariant = productVariantMap.get(Integer.valueOf(productVariantBean.getProductVariantId()));
								priceBookDetail.setProductVariant(productVariant);
								priceBookDetail.setSalesTax(productVariant.getSalesTax());
								priceBookDetail.setSupplyPrice(productVariant.getSupplyPriceExclTax());
								priceBookDetail.setUuid(productVariant.getProductVariantUuid());
							}else{
								priceBookDetail.setSalesTax(product.getSalesTax());
								priceBookDetail.setSupplyPrice(product.getSupplyPriceExclTax());
								priceBookDetail.setUuid(product.getProductUuid());
							}
							priceBookDetail.setUserByCreatedBy(currentUser);
							bookDetailsNewAdd.add(priceBookDetail);
						}else if(productVariantBean.getPriceBookDetailId()!=null && !productVariantBean.getPriceBookDetailId().equalsIgnoreCase("")){
							//update entries in pricebookDetail
							//We only update those entries in pricebookDetail whose isIndivisualUpdate flag is true rest will be skipped
							if(productVariantBean.isIndivisualUpdate()){
								PriceBookDetail priceBookDetail = priceBookDetailMap.get(Integer.valueOf(productVariantBean.getPriceBookDetailId()));
								
								priceBookDetail.setLastUpdated(new Date());
								priceBookDetail.setActiveIndicator(true);
								if(productVariantBean.getDiscount()!=null && !productVariantBean.getDiscount().equalsIgnoreCase("")){
									priceBookDetail.setDiscount(new BigDecimal(productVariantBean.getDiscount()));
								}else{
									priceBookDetail.setDiscount(new BigDecimal(0));
								}
								if(productVariantBean.getMarkupPrct()!=null && !productVariantBean.getMarkupPrct().equalsIgnoreCase("")){
									priceBookDetail.setMarkup(new BigDecimal(productVariantBean.getMarkupPrct()));
								}else{
									priceBookDetail.setMarkup(new BigDecimal(0));
								}
								priceBookDetail.setPriceBook(priceBook);
								priceBookDetail.setUserByUpdatedBy(currentUser);
								priceBookDetailsUpdateList.add(priceBookDetail);
							}else{
								skippedUpdatesCount++;
							}
						}else{
							System.out.println("====Case missed==== productVariantBean.getPriceBookDetailId():  "+productVariantBean.getPriceBookDetailId());
						}
					}
					System.out.println("skippedUpdatesCount: "+skippedUpdatesCount+" while productVariantBeansList size is: "+productVariantBeansList.size());
					priceBookDetailService.addPriceBookDetail(bookDetailsNewAdd);
					priceBookDetailService.updatePriceBookDetailList(priceBookDetailsUpdateList);
					util.AuditTrail(request, currentUser, "ManagePriceBookController.manageProductsInPriceBook", 
							"User "+ currentUser.getUserEmail()+" added products in pricebook successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED, StatusConstants.SUCCESS,LayOutPageConstants.PRICEBOOK);
				}else{
					util.AuditTrail(request, currentUser, "ManagePriceBookController.manageProductsInPriceBook", 
							"PriceBook not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}

			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManagePriceBookController.manageProductsInPriceBook",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updatePriceBook/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response updatePriceBook(@PathVariable("sessionId") String sessionId,
			@RequestBody PriceBookBean priceBookBean,HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				String outletGroups = "";
				List<OutletBean> outletBeans = priceBookBean.getOutletBeans();
				Map<String,OutletBean> outletgroupsIdsMap = new HashMap<>();
				for(OutletBean outletBean:outletBeans){
					if(outletGroups.equalsIgnoreCase("")){
						outletGroups = outletBean.getOutletId();
						outletgroupsIdsMap.put(outletBean.getOutletId(), outletBean);
					}else{
						outletgroupsIdsMap.put(outletBean.getOutletId(), outletBean);
						outletGroups = outletGroups+","+outletBean.getOutletId();
					}
				}
				
				List<PriceBook> priceBookList = priceBookService.getActivePriceBooksByDateRangeCompanyId(DateTimeUtil.convertGuiDateFormatYYYYMMDDToDBDateFormat(priceBookBean.getValidFrom()), DateTimeUtil.convertGuiDateFormatYYYYMMDDToDBDateFormat(priceBookBean.getValidTo()), currentUser.getCompany().getCompanyId());
				boolean duplicateOutletsExist = false;
				if(priceBookList!=null && priceBookList.size()>0){
					for(PriceBook priceBook:priceBookList){
						if(priceBook.getPriceBookId()==Integer.valueOf(priceBookBean.getPriceBookId())){
							continue;
						}
						String [] outletgroups = priceBook.getOuteletsGroup().split(",");
						String  outletgroupsName = "";
						for(String outletId:outletgroups){
							if(outletgroupsIdsMap.get(outletId)!=null){
								duplicateOutletsExist = true;
								outletgroupsName = outletgroupsName+","+outletgroupsIdsMap.get(outletId).getOutletName();
							}
						}
						if(duplicateOutletsExist){
							util.AuditTrail(request, currentUser, "ManagePriceBookController.updatePriceBook", 
									"User "+ currentUser.getUserEmail()+MessageConstants.PRICEBOOK_ALREADY_EXIST+outletgroupsName,false);
							return new Response(MessageConstants.PRICEBOOK_ALREADY_EXIST+outletgroupsName, StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
						}
					}
				}

				/*int outletId = !priceBookBean.getOutletId().equalsIgnoreCase("-1") && !priceBookBean.getOutletId().equalsIgnoreCase("") ?Integer.valueOf(priceBookBean.getOutletId()): currentUser.getOutlet().getOutletId();
				List<PriceBook> priceBookList= priceBookService.getPriceBooksByDateRangeCompanyIdOutletIdGroupId(DateTimeUtil.convertGuiDateFormatYYYYMMDDToDBDateFormat(priceBookBean.getValidFrom()),
						DateTimeUtil.convertGuiDateFormatYYYYMMDDToDBDateFormat(priceBookBean.getValidTo()), currentUser.getCompany().getCompanyId(), outletId, Integer.valueOf(priceBookBean.getContactGroupId()));
				if(priceBookList!=null && priceBookList.size()>0){
					if(priceBookList.size()>1|| priceBookList.get(0).getPriceBookId()!=Integer.valueOf(priceBookBean.getPriceBookId())){
						util.AuditTrail(request, currentUser, "ManagePriceBookController.updatePriceBook", 
								"User "+ currentUser.getUserEmail()+" unable to update price book because pricebook already exist with date range. ",false);
						return new Response(MessageConstants.PRICEBOOK_ALREADY_EXIST, StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
					}
				}*/
				PriceBook priceBook = priceBookService.getPriceBookByPriceBookIdCompanyId(Integer.valueOf(priceBookBean.getPriceBookId()), currentUser.getCompany().getCompanyId());
				if(priceBook!=null){
					ContactGroup contactGroup = customerGroupService.getContactGroupByContactGroupId(Integer.valueOf(priceBookBean.getContactGroupId()), currentUser.getCompany().getCompanyId());
					priceBook.setContactGroup(contactGroup);
					if(priceBookBean.getIsValidOnStore().equalsIgnoreCase(ControllersConstants.TRUE)){
						priceBook.setIsValidOnStore(true);
						priceBook.setIsValidOnEcom(false);
					}else{
						priceBook.setIsValidOnStore(false);
						priceBook.setIsValidOnEcom(true);
					}
					priceBook.setLastUpdated(new Date());
					if(priceBookBean.getOutletId()!=null && !priceBookBean.getOutletId().equalsIgnoreCase("-1")){
						Outlet outlet = outletService.getOuletByOutletId(Integer.valueOf(priceBookBean.getOutletId()), currentUser.getCompany().getCompanyId());
						priceBook.setOutlet(outlet);
					}
					priceBook.setPriceBookName(priceBookBean.getPriceBookName());
					priceBook.setUserByUpdatedBy(currentUser);
					priceBook.setValidFrom(DateTimeUtil.convertGuiDateFormatYYYYMMDDToDBDateFormat(priceBookBean.getValidFrom()));
					priceBook.setValidTo(DateTimeUtil.convertGuiDateFormatYYYYMMDDToDBDateFormat(priceBookBean.getValidTo()));
					if(priceBookBean.getFlatDiscount()!=null && !priceBookBean.getFlatDiscount().equalsIgnoreCase("")){
						priceBook.setFlatDiscount(new BigDecimal(priceBookBean.getFlatDiscount()));
					}else{
						priceBook.setFlatDiscount(new BigDecimal(0));
					}
					if(priceBookBean.getFlatSale().equalsIgnoreCase("true")){
						priceBook.setFlatSale("true");
					}else{
						priceBook.setFlatSale("false");
					}
					if(priceBookBean.getActive().equalsIgnoreCase(ControllersConstants.TRUE)){
						priceBook.setActiveIndicator(true);
					}else{
						priceBook.setActiveIndicator(false);
					}
					priceBookService.updatePriceBook(priceBook);

					util.AuditTrail(request, currentUser, "ManagePriceBookController.updatePriceBook", 
							"User "+ currentUser.getUserEmail()+" updated PriceBook successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED, StatusConstants.SUCCESS,LayOutPageConstants.PRICEBOOK);
				}else{
					util.AuditTrail(request, currentUser, "ManagePriceBookController.updatePriceBook", 
							"User "+ currentUser.getUserEmail()+" unable to  update PriceBook ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND, StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManagePriceBookController.updatePriceBook",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/deleteProductOrVariantInPriceBookDetail/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response deleteProductOrVariantInPriceBookDetail(@PathVariable("sessionId") String sessionId,
			@RequestBody ProductVariantBean productVariantBean,
			HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				PriceBookDetail priceBookDetail = priceBookDetailService.getPriceBookDetailByPriceBookDetailIdCompanyId(Integer.valueOf(productVariantBean.getPriceBookDetailId()), currentUser.getCompany().getCompanyId());
				priceBookDetail.setActiveIndicator(false);
				priceBookDetail.setLastUpdated(new Date());
				priceBookDetail.setUserByUpdatedBy(currentUser);
				priceBookDetailService.deletePriceBookDetail(priceBookDetail);

				util.AuditTrail(request, currentUser, "ManageProductController.deleteProductOrVariantInPriceBookDetail", 
						"User "+ currentUser.getUserEmail()+" deleted product or variant in pricebook detail  successfully ",false);
				return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.PRODUCTS);
			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManageProductController.deleteProductOrVariantInPriceBookDetail",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	public boolean verifyProductOrVariantExistInPriceBookDetail(List<PriceBookDetailSummary>priceBookDetailSummaryList,int productId,int productVariantId){
		if(priceBookDetailSummaryList!=null && priceBookDetailSummaryList.size()>0){
			for(PriceBookDetailSummary priceBookDetailSummary:priceBookDetailSummaryList){
				try{
					if(productVariantId==0){
						if(priceBookDetailSummary.getId().getProductAssocicationId()==productId){
							return true;
						}/*else{
						return false;
					}*/
					}else{
						if(priceBookDetailSummary.getId().getProductVarientAssocicationId()==productVariantId){
							return true;
						}/*else{
						return false;
					}*/
					}
				}catch(Exception exception){
					exception.printStackTrace();
				}
			}
			return false;
		}else{
			return false;
		}
	}
	
	public PriceBookDetail getPriceBookDetailByProductVariantIdPriceBookId(Map<Integer, PriceBookDetail> priceMap,int productVariantId,int priceBookId){
		for (Map.Entry<Integer, PriceBookDetail> entry : priceMap.entrySet())
		{
			PriceBookDetail priceBookDetail= entry.getValue();
			if(priceBookDetail.getPriceBook().getPriceBookId()==priceBookId && priceBookDetail.getProductVariant().getProductVariantId()==productVariantId){
				return priceBookDetail;
			}
		}
		return null;
	}
}

