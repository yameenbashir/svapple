package com.dowhile.angualrspringapp.controller;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.dowhile.InventoryCount;
import com.dowhile.InventoryCountDetail;
import com.dowhile.Outlet;
import com.dowhile.Product;
import com.dowhile.ProductVariant;
import com.dowhile.User;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.InventoryCountControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.InventoryCountBean;
import com.dowhile.frontend.mapping.bean.InventoryCountDetailBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.frontend.mapping.bean.StockOrderBean;
import com.dowhile.frontend.mapping.bean.StockOrderDetailBean;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.ContactService;
import com.dowhile.service.InventoryCountDetailService;
import com.dowhile.service.InventoryCountService;
import com.dowhile.service.InventoryCountTypeService;
import com.dowhile.service.OutletService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductVariantService;
import com.dowhile.service.StatusService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * Zafar Shakeel
 */
@Controller
@RequestMapping("/inventoryCountDetails")

public class InventoryCountDetailsController {
	@Resource
	private ServiceUtil util;
	@Resource
	private OutletService outletService;

	@Resource
	private ContactService supplierService;

	@Resource
	private InventoryCountService inventoryCountService;

	@Resource
	private StatusService statusService;

	@Resource
	private InventoryCountTypeService inventoryCountTypeService;

	@Resource
	private InventoryCountDetailService inventoryCountDetailService;	

	@Resource
	private ProductVariantService productVariantService;

	@Resource
	private ProductService productService;

	@Resource
	private ConfigurationService configurationService;

	@Autowired
	private PurchaseOrderController purchaseOrderController;
	@Autowired
	private PurchaseOrderDetailsController purchaseOrderDetailsController;

	private List<Product> productList;
	private List<ProductVariant> productVariantList;

	@RequestMapping("/layout")
	public String getInventoryCountDetialsControllerPartialPage(ModelMap modelMap) {
		return "inventoryCountDetails/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getInventoryCountDetailsControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getInventoryCountDetailsControllerData(@PathVariable("sessionId") String sessionId,
			@RequestBody InventoryCountBean inventoryCountBean, HttpServletRequest request) {
		List<ProductVariantBean> productBeansList = null;
		List<ProductVariantBean> productVariantBeansList = null;
		List<ProductVariantBean> allProductBeansList = null;
		List<ProductVariantBean> allProductVariantBeansList = null;
		List<InventoryCountDetailBean> inventoryCountDetailBeansList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
			try {
				Response response = getAllProductsByOutletId(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					productBeansList = (List<ProductVariantBean>) response.data;
				}

				response = getProductVariantsByOutletId(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					productVariantBeansList = (List<ProductVariantBean>) response.data;
				}				
				Configuration configuration = configurationMap.get("AUTO_AUDIT_TRANSFER");
				boolean autoTransfer = false;
				if(configuration != null ){
					if(configuration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
						autoTransfer = true;
					}
					else{
						autoTransfer = false;
					}
				}
				else{
					autoTransfer = false;
				}
				int headOfficeOutletId = outletService.getHeadOfficeOutlet(currentUser.getCompany().getCompanyId()).getOutletId();
				int outletId = currentUser.getOutlet().getOutletId();
				if(outletId == headOfficeOutletId){
					autoTransfer = false;
				}
				if(autoTransfer == true){
					response = getAllProducts(sessionId, request);
					if(response.status.equals(StatusConstants.SUCCESS)){
						allProductBeansList = (List<ProductVariantBean>) response.data;
					}
					response = getAllProductVariants(sessionId, request);
					if(response.status.equals(StatusConstants.SUCCESS)){
						allProductVariantBeansList = (List<ProductVariantBean>) response.data;
					}					
				}
				response = getAllDetailsByInventoryCountId(sessionId, inventoryCountBean, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					inventoryCountDetailBeansList = (List<InventoryCountDetailBean>) response.data;
				}
				InventoryCountControllerBean inventoryCountControllerBean = new InventoryCountControllerBean();
				inventoryCountControllerBean.setProductBeansList(productBeansList);
				inventoryCountControllerBean.setProductVariantBeansList(productVariantBeansList);
				inventoryCountControllerBean.setInventoryCountDetailBeansList(inventoryCountDetailBeansList);
				inventoryCountControllerBean.setAllProductBeansList(allProductBeansList);
				inventoryCountControllerBean.setAllProductVariantBeansList(allProductVariantBeansList);
				util.AuditTrail(request, currentUser, "InventoryCountController.getInventoryCountControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived InventoryCountControllerData successfully ",false);
				return new Response(inventoryCountControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountDetailsController.getInventoryCountControllerData",
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
	@RequestMapping(value = "/updateInventoryCountDetail/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response updateInventoryCountDetail(@PathVariable("sessionId") String sessionId,
			@RequestBody InventoryCountBean inventoryCountBean, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<InventoryCountDetailBean> inventoryCountDetailBeansList = inventoryCountBean.getInventoryCountDetailBeansList(); 
			try {			
				if (inventoryCountDetailBeansList.size() > 0) {	
					InventoryCount inventoryCount = inventoryCountService.getInventoryCountByInventoryCountID(Integer.parseInt(inventoryCountDetailBeansList.get(0).getInventoryCountId()),currentUser.getCompany().getCompanyId());
					Map<Integer, Product> productsMap = new HashMap<>();
					List<Product> products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
					List<InventoryCountDetail> inventoryCountDetailsUpdateList = new ArrayList<>();
					List<InventoryCountDetail> inventoryCountDetailsDeleteList = new ArrayList<>();
					List<InventoryCountDetail> inventoryCountDetailsAddList = new ArrayList<>();
					if(products!=null){
						for(Product product:products){
							productsMap.put(product.getProductId(), product);
						}
					}
					Map<Integer, ProductVariant> productVariantsMap = new HashMap<>();
					List<ProductVariant> productVariants = productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
					if(productVariants!=null){
						for(ProductVariant productVariant:productVariants){
							productVariantsMap.put(productVariant.getProductVariantId(), productVariant);
						}
					}
					//Inventory Count Details Map Region
					List<InventoryCountDetail> inventoryCountDetails = new ArrayList<>();
					Map<Integer, List<InventoryCountDetail>> inventoryCountDetailsMap = new HashMap<>();
					Map<Integer, InventoryCountDetail> inventoryCountDetailsByDetailIDMap = new HashMap<>();
					inventoryCountDetails = inventoryCountDetailService.getAllInventoryCountDetails(currentUser.getCompany().getCompanyId());
					if(inventoryCountDetails!=null){
						for(InventoryCountDetail inventoryCountDetail:inventoryCountDetails){
							List<InventoryCountDetail> addedinventoryCountDetails = inventoryCountDetailsMap.get(inventoryCountDetail.getInventoryCount().getInventoryCountId());
							if(addedinventoryCountDetails!=null){
								addedinventoryCountDetails.add(inventoryCountDetail);
								inventoryCountDetailsMap.put(inventoryCountDetail.getInventoryCount().getInventoryCountId(), addedinventoryCountDetails);
							}else{
								addedinventoryCountDetails = new ArrayList<>();
								addedinventoryCountDetails.add(inventoryCountDetail);
								inventoryCountDetailsMap.put(inventoryCountDetail.getInventoryCount().getInventoryCountId(), addedinventoryCountDetails);
							}
							inventoryCountDetailsByDetailIDMap.put(inventoryCountDetail.getInventoryCountDetailId(), inventoryCountDetail);
						}
					}
					//End Region
					List<InventoryCountDetail> preInventoryCountDetailList = inventoryCountDetailsMap.get(Integer.parseInt(inventoryCountDetailBeansList.get(0).getInventoryCountId()));
					for(InventoryCountDetailBean inventoryCountDetailBean : inventoryCountDetailBeansList)
					{
						if(inventoryCountDetailBean.getInventoryCountDetailId() != null && !inventoryCountDetailBean.getInventoryCountDetailId().equalsIgnoreCase("")){
							InventoryCountDetail inventoryCountDetail = inventoryCountDetailsByDetailIDMap.get(Integer.parseInt(inventoryCountDetailBean.getInventoryCountDetailId()));
							if(preInventoryCountDetailList != null){
								int i = 0;
								int index = -1;
								for (InventoryCountDetail preInventoryCountDetail : preInventoryCountDetailList){
									int inventoryCountDetailId = inventoryCountDetail.getInventoryCountDetailId();
									int preInventoryCountDetailId = preInventoryCountDetail.getInventoryCountDetailId();
									if(inventoryCountDetailId == preInventoryCountDetailId)
									{
										index = i;
										break;
									}
									i++;
								}
								if(index != -1){
									preInventoryCountDetailList.remove(index);
								}
							}
							if(inventoryCountDetailBean.getCountedProdQty() != null && !inventoryCountDetailBean.getCountedProdQty().equalsIgnoreCase("")){
								inventoryCountDetail.setCountedProdQty(Integer.parseInt(inventoryCountDetailBean.getCountedProdQty()));
							}
							if(inventoryCountDetailBean.getExpProdQty() != null && !inventoryCountDetailBean.getExpProdQty().equalsIgnoreCase("")){
								inventoryCountDetail.setExpectedProdQty(Integer.parseInt(inventoryCountDetailBean.getExpProdQty()));
							}
							if(inventoryCountDetailBean.getInventoryCountId() != null && !inventoryCountDetailBean.getInventoryCountId().equalsIgnoreCase("")){
								inventoryCountDetail.setInventoryCount(inventoryCount);
							}
							if(inventoryCountDetailBean.getIsProduct() != null && !inventoryCountDetailBean.getIsProduct().equalsIgnoreCase("")){
								if(!inventoryCountDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
									inventoryCountDetail.setProductVariant(productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())));
									inventoryCountDetail.setIsProduct(false);
								}
								else{
									inventoryCountDetail.setProduct(productsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())));
									inventoryCountDetail.setIsProduct(true);
								}	
							}
							else{
								inventoryCountDetail.setProductVariant(productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())));
								inventoryCountDetail.setIsProduct(false);
							}
							if(inventoryCountDetailBean.getRetailPriceCounted() != null && !inventoryCountDetailBean.getRetailPriceCounted().equalsIgnoreCase("")){
								inventoryCountDetail.setRetailPriceCounted(new BigDecimal(inventoryCountDetailBean.getRetailPriceCounted()));
							}
							if(inventoryCountDetailBean.getRetailPriceExp() != null && !inventoryCountDetailBean.getRetailPriceExp().equalsIgnoreCase("")){
								inventoryCountDetail.setRetailPriceExp(new BigDecimal(inventoryCountDetailBean.getRetailPriceExp()));
							}
							if(inventoryCountDetailBean.getSupplyPriceCounted() != null && !inventoryCountDetailBean.getSupplyPriceCounted().equalsIgnoreCase("")){
								inventoryCountDetail.setSupplyPriceCounted(new BigDecimal(inventoryCountDetailBean.getSupplyPriceCounted()));
							}
							if(inventoryCountDetailBean.getSupplyPriceExp() != null && !inventoryCountDetailBean.getSupplyPriceExp().equalsIgnoreCase("")){
								inventoryCountDetail.setSupplyPriceExp(new BigDecimal(inventoryCountDetailBean.getSupplyPriceExp()));
							}
							if(inventoryCountDetailBean.getCountDiff() != null && !inventoryCountDetailBean.getCountDiff().equalsIgnoreCase("")){
								inventoryCountDetail.setCountDiff(Integer.parseInt(inventoryCountDetailBean.getCountDiff()));
							}
							if(inventoryCountDetailBean.getPriceDiff() != null && !inventoryCountDetailBean.getPriceDiff().equalsIgnoreCase("")){
								inventoryCountDetail.setPriceDiff(new BigDecimal(inventoryCountDetailBean.getPriceDiff()));
							}
							if(inventoryCountDetailBean.getAuditTransfer() != null && !inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("")){
								if(inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("true")){
									Byte i = 1;
									inventoryCountDetail.setAuditTransfer(i);
								}else{
									Byte i = 0;
									inventoryCountDetail.setAuditTransfer(i);
								}
							}else{
								Byte i = 0;
								inventoryCountDetail.setAuditTransfer(i);
							}
							inventoryCountDetail.setInventoryCount(inventoryCount);
							inventoryCountDetail.setActiveIndicator(true);				
							inventoryCountDetail.setLastUpdated(new Date());
							inventoryCountDetail.setUpdatedBy(currentUser.getUserId());
							inventoryCountDetailsUpdateList.add(inventoryCountDetail);
						}

						else
						{
							InventoryCountDetail inventoryCountDetail = new InventoryCountDetail();
							//inventoryCountDetail.setInventoryCountDetailId(Integer.parseInt(inventoryCountDetailBean.getInventoryCountDetailId()));
							if(inventoryCountDetailBean.getCountedProdQty() != null && !inventoryCountDetailBean.getCountedProdQty().equalsIgnoreCase("")){
								inventoryCountDetail.setCountedProdQty(Integer.parseInt(inventoryCountDetailBean.getCountedProdQty()));
							}
							if(inventoryCountDetailBean.getExpProdQty() != null && !inventoryCountDetailBean.getExpProdQty().equalsIgnoreCase("")){
								inventoryCountDetail.setExpectedProdQty(Integer.parseInt(inventoryCountDetailBean.getExpProdQty()));
							}
							if(inventoryCountDetailBean.getInventoryCountId() != null && !inventoryCountDetailBean.getInventoryCountId().equalsIgnoreCase("")){
								inventoryCountDetail.setInventoryCount(inventoryCount);
							}
							if(inventoryCountDetailBean.getIsProduct() != null && !inventoryCountDetailBean.getIsProduct().equalsIgnoreCase("")){
								if(!inventoryCountDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
									inventoryCountDetail.setProductVariant(productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())));
									inventoryCountDetail.setIsProduct(false);
								}
								else{
									inventoryCountDetail.setProduct(productsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())));
									inventoryCountDetail.setIsProduct(true);
								}	
							}
							else{
								inventoryCountDetail.setProductVariant(productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())));
								inventoryCountDetail.setIsProduct(false);
							}
							if(inventoryCountDetailBean.getRetailPriceCounted() != null && !inventoryCountDetailBean.getRetailPriceCounted().equalsIgnoreCase("")){
								inventoryCountDetail.setRetailPriceCounted(new BigDecimal(inventoryCountDetailBean.getRetailPriceCounted()));
							}
							if(inventoryCountDetailBean.getRetailPriceExp() != null && !inventoryCountDetailBean.getRetailPriceExp().equalsIgnoreCase("")){
								inventoryCountDetail.setRetailPriceExp(new BigDecimal(inventoryCountDetailBean.getRetailPriceExp()));
							}
							if(inventoryCountDetailBean.getSupplyPriceCounted() != null && !inventoryCountDetailBean.getSupplyPriceCounted().equalsIgnoreCase("")){
								inventoryCountDetail.setSupplyPriceCounted(new BigDecimal(inventoryCountDetailBean.getSupplyPriceCounted()));
							}
							if(inventoryCountDetailBean.getSupplyPriceExp() != null && !inventoryCountDetailBean.getSupplyPriceExp().equalsIgnoreCase("")){
								inventoryCountDetail.setSupplyPriceExp(new BigDecimal(inventoryCountDetailBean.getSupplyPriceExp()));
							}
							if(inventoryCountDetailBean.getCountDiff() != null && !inventoryCountDetailBean.getCountDiff().equalsIgnoreCase("")){
								inventoryCountDetail.setCountDiff(Integer.parseInt(inventoryCountDetailBean.getCountDiff()));
							}
							if(inventoryCountDetailBean.getPriceDiff() != null && !inventoryCountDetailBean.getPriceDiff().equalsIgnoreCase("")){
								inventoryCountDetail.setPriceDiff(new BigDecimal(inventoryCountDetailBean.getPriceDiff()));
							}
							if(inventoryCountDetailBean.getAuditTransfer() != null && !inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("")){
								if(inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("true")){
									Byte i = 1;
									inventoryCountDetail.setAuditTransfer(i);
								}else{
									Byte i = 0;
									inventoryCountDetail.setAuditTransfer(i);
								}
							}else{
								Byte i = 0;
								inventoryCountDetail.setAuditTransfer(i);
							}
							inventoryCountDetail.setInventoryCount(inventoryCount);
							inventoryCountDetail.setActiveIndicator(true);			
							inventoryCountDetail.setCreatedDate(new Date());				
							inventoryCountDetail.setLastUpdated(new Date());
							inventoryCountDetail.setCreatedBy(currentUser.getUserId());
							inventoryCountDetail.setUpdatedBy(currentUser.getUserId());
							inventoryCountDetail.setCompany(currentUser.getCompany());
							inventoryCountDetailsAddList.add(inventoryCountDetail);

						}
					}			
					if(preInventoryCountDetailList != null){
						if(preInventoryCountDetailList.size() > 0)
						{
							for(InventoryCountDetail inventoryCountDetail : preInventoryCountDetailList)
							{
								inventoryCountDetailsDeleteList.add(inventoryCountDetail);
							}
						}	
					}
					if(inventoryCountDetailsUpdateList.size() > 0){
						inventoryCountDetailService.updateInventoryCountDetailsList(inventoryCountDetailsUpdateList, currentUser.getCompany().getCompanyId());
					}
					if(inventoryCountDetailsAddList.size() > 0){
						inventoryCountDetailService.addInventoryCountDetailsList(inventoryCountDetailsAddList, currentUser.getCompany().getCompanyId());
					}
					if(inventoryCountDetailsDeleteList.size() > 0){
						inventoryCountDetailService.deleteInventoryCountDetailsList(inventoryCountDetailsDeleteList, currentUser.getCompany().getCompanyId());
					}
					inventoryCount.setStatus(statusService.getStatusByStatusId(2));  //in Progress status
					inventoryCount.setPriceDiff(new BigDecimal(inventoryCountBean.getPriceDiff()));
					inventoryCount.setCountDiff(Integer.parseInt(inventoryCountBean.getCountDiff()));
					inventoryCount.setRetailPriceExp(new BigDecimal(inventoryCountBean.getRetailPriceExp()));
					inventoryCount.setRetailPriceCounted(new BigDecimal(inventoryCountBean.getRetailPriceCounted()));
					inventoryCount.setSupplyPriceExp(new BigDecimal(inventoryCountBean.getSupplyPriceExp()));
					inventoryCount.setSupplyPriceCounted(new BigDecimal(inventoryCountBean.getSupplyPriceCounted()));
					inventoryCount.setExpectedProdQty(Integer.parseInt(inventoryCountBean.getItemCountExp()));
					inventoryCount.setCountedProdQty(Integer.parseInt(inventoryCountBean.getItemCountCounted()));
					inventoryCount.setLastUpdated(new Date());
					inventoryCount.setUpdatedBy(currentUser.getUserId());				
					inventoryCountService.updateInventoryCount(inventoryCount,currentUser.getCompany().getCompanyId());					
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.STOCKCONTROL);
				}else{

					util.AuditTrail(request, currentUser, "InventoryCountDetails.addInventoryCountDetail", "User "+ 
							currentUser.getUserEmail()+" Unable to add InventoryCountDetail+"+ inventoryCountDetailBeansList.get(0).getInventoryCountDetailId(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.addInventoryCount",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateQtyAndUpdateInventoryCountDetail/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response updateQtyAndUpdateInventoryCountDetail(@PathVariable("sessionId") String sessionId,
			@RequestBody InventoryCountBean inventoryCountBean, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			Double grandTotal = 0.0;
			List<InventoryCountDetailBean> inventoryCountDetailBeansList = inventoryCountBean.getInventoryCountDetailBeansList(); 
			List<StockOrderDetailBean> stockOrderDetialBeansList = new ArrayList<>();
			List<Product> productUpdateList = new ArrayList<>();
			List<ProductVariant> productVariantUpdateList = new ArrayList<>();
			try {			
				if (inventoryCountDetailBeansList.size() > 0) {	
					InventoryCount inventoryCount = inventoryCountService.getInventoryCountByInventoryCountID(Integer.parseInt(inventoryCountDetailBeansList.get(0).getInventoryCountId()),currentUser.getCompany().getCompanyId());
					Map<Integer, Product> productsMap = new HashMap<>();
					List<Product> products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
					Outlet sourceOutlet = outletService.getHeadOfficeOutlet(currentUser.getCompany().getCompanyId());
					List<InventoryCountDetail> inventoryCountDetailsUpdateList = new ArrayList<>();
					List<InventoryCountDetail> inventoryCountDetailsDeleteList = new ArrayList<>();
					List<InventoryCountDetail> inventoryCountDetailsAddList = new ArrayList<>();
					Map<String, Product> warehouseProducts = new HashMap<>();
					Map<String, ProductVariant> warehouseProductVariants = new HashMap<>();
					if(products!=null){
						for(Product product:products){
							productsMap.put(product.getProductId(), product);
							if(product.getOutlet().getOutletId() == sourceOutlet.getOutletId()){
								warehouseProducts.put(product.getProductUuid(), product);
							}
						}
					}
					Map<Integer, ProductVariant> productVariantsMap = new HashMap<>();
					List<ProductVariant> productVariants = productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
					if(productVariants!=null){
						for(ProductVariant productVariant:productVariants){
							productVariantsMap.put(productVariant.getProductVariantId(), productVariant);
							if(productVariant.getOutlet().getOutletId() == sourceOutlet.getOutletId()){
								warehouseProductVariants.put(productVariant.getProductVariantUuid(), productVariant);
							}
						}
					}
					//Inventory Count Details Map Region
					List<InventoryCountDetail> inventoryCountDetails = new ArrayList<>();
					Map<Integer, List<InventoryCountDetail>> inventoryCountDetailsMap = new HashMap<>();
					Map<Integer, InventoryCountDetail> inventoryCountDetailsByDetailIDMap = new HashMap<>();
					inventoryCountDetails = inventoryCountDetailService.getAllInventoryCountDetails(currentUser.getCompany().getCompanyId());
					if(inventoryCountDetails!=null){
						for(InventoryCountDetail inventoryCountDetail:inventoryCountDetails){
							List<InventoryCountDetail> addedinventoryCountDetails = inventoryCountDetailsMap.get(inventoryCountDetail.getInventoryCount().getInventoryCountId());
							if(addedinventoryCountDetails!=null){
								addedinventoryCountDetails.add(inventoryCountDetail);
								inventoryCountDetailsMap.put(inventoryCountDetail.getInventoryCount().getInventoryCountId(), addedinventoryCountDetails);
							}else{
								addedinventoryCountDetails = new ArrayList<>();
								addedinventoryCountDetails.add(inventoryCountDetail);
								inventoryCountDetailsMap.put(inventoryCountDetail.getInventoryCount().getInventoryCountId(), addedinventoryCountDetails);
							}
							inventoryCountDetailsByDetailIDMap.put(inventoryCountDetail.getInventoryCountDetailId(), inventoryCountDetail);
						}
					}
					//End Region
					List<InventoryCountDetail> preInventoryCountDetailList = inventoryCountDetailsMap.get(Integer.parseInt(inventoryCountDetailBeansList.get(0).getInventoryCountId()));
					for(InventoryCountDetailBean inventoryCountDetailBean : inventoryCountDetailBeansList)
					{
						if(inventoryCountDetailBean.getInventoryCountDetailId() != null && !inventoryCountDetailBean.getInventoryCountDetailId().equalsIgnoreCase("")){
							InventoryCountDetail inventoryCountDetail = inventoryCountDetailsByDetailIDMap.get(Integer.parseInt(inventoryCountDetailBean.getInventoryCountDetailId()));
							if(preInventoryCountDetailList != null){
								int i = 0;
								int index = -1;
								for (InventoryCountDetail preInventoryCountDetail : preInventoryCountDetailList){
									int inventoryCountDetailId = inventoryCountDetail.getInventoryCountDetailId();
									int preInventoryCountDetailId = preInventoryCountDetail.getInventoryCountDetailId();
									if(inventoryCountDetailId == preInventoryCountDetailId)
									{
										index = i;
										break;
									}
									i++;
								}
								if(index != -1){
									preInventoryCountDetailList.remove(index);
								}
							}
							if(inventoryCountDetailBean.getCountedProdQty() != null && !inventoryCountDetailBean.getCountedProdQty().equalsIgnoreCase("")){
								inventoryCountDetail.setCountedProdQty(Integer.parseInt(inventoryCountDetailBean.getCountedProdQty()));
							}
							if(inventoryCountDetailBean.getExpProdQty() != null && !inventoryCountDetailBean.getExpProdQty().equalsIgnoreCase("")){
								inventoryCountDetail.setExpectedProdQty(Integer.parseInt(inventoryCountDetailBean.getExpProdQty()));
							}
							if(inventoryCountDetailBean.getInventoryCountId() != null && !inventoryCountDetailBean.getInventoryCountId().equalsIgnoreCase("")){
								inventoryCountDetail.setInventoryCount(inventoryCount);
							}
							if(inventoryCountDetailBean.getIsProduct() != null && !inventoryCountDetailBean.getIsProduct().equalsIgnoreCase("")){
								if(!inventoryCountDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
									inventoryCountDetail.setProductVariant(productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())));
									inventoryCountDetail.setIsProduct(false);
									if(inventoryCountDetailBean.getAuditTransfer() != null && !inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("")){
										if(!inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("true")){
											ProductVariant productVariant  = productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
											productVariant.setCurrentInventory(inventoryCountDetail.getCountedProdQty());
											productVariantUpdateList.add(productVariant);
										}
									}else{
										ProductVariant productVariant  = productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
										productVariant.setCurrentInventory(inventoryCountDetail.getExpectedProdQty());
										productVariantUpdateList.add(productVariant);
									}
								}
								else{
									inventoryCountDetail.setProduct(productsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())));
									inventoryCountDetail.setIsProduct(true);
									if(inventoryCountDetailBean.getAuditTransfer() != null && !inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("")){
										if(!inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("true")){
											Product product  = productsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
											product.setCurrentInventory(inventoryCountDetail.getCountedProdQty());
											productUpdateList.add(product);
										}
									}else{
										Product product  = productsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
										product.setCurrentInventory(inventoryCountDetail.getExpectedProdQty());
										productUpdateList.add(product);
									}
								}	
							}
							else{
								inventoryCountDetail.setProductVariant(productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())));
								inventoryCountDetail.setIsProduct(false);
								if(inventoryCountDetailBean.getAuditTransfer() != null && !inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("")){
									if(!inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("true")){
										ProductVariant productVariant  = productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
										productVariant.setCurrentInventory(inventoryCountDetail.getCountedProdQty());
										productVariantUpdateList.add(productVariant);
									}
								}else{
									ProductVariant productVariant  = productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
									productVariant.setCurrentInventory(inventoryCountDetail.getExpectedProdQty());
									productVariantUpdateList.add(productVariant);
								}
							}
							if(inventoryCountDetailBean.getRetailPriceCounted() != null && !inventoryCountDetailBean.getRetailPriceCounted().equalsIgnoreCase("")){
								inventoryCountDetail.setRetailPriceCounted(new BigDecimal(inventoryCountDetailBean.getRetailPriceCounted()));
							}
							if(inventoryCountDetailBean.getRetailPriceExp() != null && !inventoryCountDetailBean.getRetailPriceExp().equalsIgnoreCase("")){
								inventoryCountDetail.setRetailPriceExp(new BigDecimal(inventoryCountDetailBean.getRetailPriceExp()));
							}
							if(inventoryCountDetailBean.getSupplyPriceCounted() != null && !inventoryCountDetailBean.getSupplyPriceCounted().equalsIgnoreCase("")){
								inventoryCountDetail.setSupplyPriceCounted(new BigDecimal(inventoryCountDetailBean.getSupplyPriceCounted()));
							}
							if(inventoryCountDetailBean.getSupplyPriceExp() != null && !inventoryCountDetailBean.getSupplyPriceExp().equalsIgnoreCase("")){
								inventoryCountDetail.setSupplyPriceExp(new BigDecimal(inventoryCountDetailBean.getSupplyPriceExp()));
							}
							if(inventoryCountDetailBean.getCountDiff() != null && !inventoryCountDetailBean.getCountDiff().equalsIgnoreCase("")){
								inventoryCountDetail.setCountDiff(Integer.parseInt(inventoryCountDetailBean.getCountDiff()));
							}
							if(inventoryCountDetailBean.getPriceDiff() != null && !inventoryCountDetailBean.getPriceDiff().equalsIgnoreCase("")){
								inventoryCountDetail.setPriceDiff(new BigDecimal(inventoryCountDetailBean.getPriceDiff()));
							}
							if(inventoryCountDetailBean.getAuditTransfer() != null && !inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("")){
								if(inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("true")){
									Byte i = 1;
									inventoryCountDetail.setAuditTransfer(i);
								}else{
									Byte i = 0;
									inventoryCountDetail.setAuditTransfer(i);
								}
							}else{
								Byte i = 0;
								inventoryCountDetail.setAuditTransfer(i);
							}
							inventoryCountDetail.setInventoryCount(inventoryCount);
							inventoryCountDetail.setActiveIndicator(true);				
							inventoryCountDetail.setLastUpdated(new Date());
							inventoryCountDetail.setUpdatedBy(currentUser.getUserId());
							inventoryCountDetailsUpdateList.add(inventoryCountDetail);
							if(inventoryCountDetailBean.getAuditTransfer() != null && !inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("")){
								if(inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("true")){
									StockOrderDetailBean stockOrderDetailBean = new StockOrderDetailBean();
									//stockOrderDetailBean.setProductVariantId(String.valueOf(variantId));	
									if(inventoryCountDetailBean.getIsProduct() != null && !inventoryCountDetailBean.getIsProduct().equalsIgnoreCase("")){
										if(!inventoryCountDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
											ProductVariant productVariant = productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
											if(productVariant.getOutlet().getOutletId() == sourceOutlet.getOutletId()){
												stockOrderDetailBean.setProductVariantId(String.valueOf(productVariant.getProductVariantId()));
											}
											else{
												ProductVariant pv = warehouseProductVariants.get(productVariant.getProductVariantUuid());
												if(pv != null){
													stockOrderDetailBean.setProductVariantId(String.valueOf(pv.getProductVariantId()));
												}
												else{
													stockOrderDetailBean.setProductVariantId(String.valueOf(productVariant.getProductVariantId()));
												}
											}											
											stockOrderDetailBean.setIsProduct("false");
											BigDecimal netPrice = (productVariant.getSupplyPriceExclTax().multiply(productVariant.getMarkupPrct().divide(new BigDecimal(100)))).add(productVariant.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
											BigDecimal retailPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
											stockOrderDetailBean.setOrdrSupplyPrice(retailPrice.toString());
											stockOrderDetailBean.setRetailPrice(retailPrice.toString());
										}
									}
									else{
										Product product = productsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
										//stockOrderDetailBean.setProductVariantId(String.valueOf(productsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())).getProductId()));
										if(product.getOutlet().getOutletId() == sourceOutlet.getOutletId()){
											stockOrderDetailBean.setProductVariantId(String.valueOf(product.getProductId()));
										}
										else{
											Product pv = warehouseProducts.get(product.getProductUuid());
											if(pv != null){
												stockOrderDetailBean.setProductVariantId(String.valueOf(pv.getProductId()));
											}
											else{
												stockOrderDetailBean.setProductVariantId(String.valueOf(product.getProductId()));
											}
										}
										
										stockOrderDetailBean.setIsProduct("true");
										BigDecimal netPrice = (product.getSupplyPriceExclTax().multiply(product.getMarkupPrct().divide(new BigDecimal(100)))).add(product.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
										BigDecimal retailPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
										stockOrderDetailBean.setOrdrSupplyPrice(retailPrice.toString());
										stockOrderDetailBean.setRetailPrice(retailPrice.toString());
									}	
									stockOrderDetailBean.setOrderProdQty(String.valueOf(inventoryCountDetail.getCountedProdQty() - inventoryCountDetail.getExpectedProdQty()));
									grandTotal = grandTotal + (Double.parseDouble(stockOrderDetailBean.getOrderProdQty()) * Double.parseDouble(stockOrderDetailBean.getOrdrSupplyPrice()));
									stockOrderDetialBeansList.add(stockOrderDetailBean);
								}
							}
						}
						else
						{
							InventoryCountDetail inventoryCountDetail = new InventoryCountDetail();
							//inventoryCountDetail.setInventoryCountDetailId(Integer.parseInt(inventoryCountDetailBean.getInventoryCountDetailId()));
							if(inventoryCountDetailBean.getCountedProdQty() != null && !inventoryCountDetailBean.getCountedProdQty().equalsIgnoreCase("")){
								inventoryCountDetail.setCountedProdQty(Integer.parseInt(inventoryCountDetailBean.getCountedProdQty()));
							}
							if(inventoryCountDetailBean.getExpProdQty() != null && !inventoryCountDetailBean.getExpProdQty().equalsIgnoreCase("")){
								inventoryCountDetail.setExpectedProdQty(Integer.parseInt(inventoryCountDetailBean.getExpProdQty()));
							}
							if(inventoryCountDetailBean.getInventoryCountId() != null && !inventoryCountDetailBean.getInventoryCountId().equalsIgnoreCase("")){
								inventoryCountDetail.setInventoryCount(inventoryCount);
							}
							if(inventoryCountDetailBean.getIsProduct() != null && !inventoryCountDetailBean.getIsProduct().equalsIgnoreCase("")){
								if(!inventoryCountDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
									inventoryCountDetail.setProductVariant(productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())));
									inventoryCountDetail.setIsProduct(false);
									if(inventoryCountDetailBean.getAuditTransfer() != null && !inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("")){
										if(!inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("true")){
											ProductVariant productVariant  = productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
											productVariant.setCurrentInventory(inventoryCountDetail.getCountedProdQty());
											productVariantUpdateList.add(productVariant);
										}
									}else{
										ProductVariant productVariant  = productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
										productVariant.setCurrentInventory(inventoryCountDetail.getExpectedProdQty());
										productVariantUpdateList.add(productVariant);
									}
								}
								else{
									inventoryCountDetail.setProduct(productsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())));
									inventoryCountDetail.setIsProduct(true);
									if(inventoryCountDetailBean.getAuditTransfer() != null && !inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("")){
										if(!inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("true")){
											Product product  = productsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
											product.setCurrentInventory(inventoryCountDetail.getCountedProdQty());
											productUpdateList.add(product);
										}
									}else{
										Product product  = productsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
										product.setCurrentInventory(inventoryCountDetail.getExpectedProdQty());
										productUpdateList.add(product);
									}
								}	
							}
							else{
								inventoryCountDetail.setProductVariant(productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())));
								inventoryCountDetail.setIsProduct(false);
								if(inventoryCountDetailBean.getAuditTransfer() != null && !inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("")){
									if(!inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("true")){
										ProductVariant productVariant  = productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
										productVariant.setCurrentInventory(inventoryCountDetail.getCountedProdQty());
										productVariantUpdateList.add(productVariant);
									}
								}else{
									ProductVariant productVariant  = productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
									productVariant.setCurrentInventory(inventoryCountDetail.getExpectedProdQty());
									productVariantUpdateList.add(productVariant);
								}
							}
							if(inventoryCountDetailBean.getRetailPriceCounted() != null && !inventoryCountDetailBean.getRetailPriceCounted().equalsIgnoreCase("")){
								inventoryCountDetail.setRetailPriceCounted(new BigDecimal(inventoryCountDetailBean.getRetailPriceCounted()));
							}
							if(inventoryCountDetailBean.getRetailPriceExp() != null && !inventoryCountDetailBean.getRetailPriceExp().equalsIgnoreCase("")){
								inventoryCountDetail.setRetailPriceExp(new BigDecimal(inventoryCountDetailBean.getRetailPriceExp()));
							}
							if(inventoryCountDetailBean.getSupplyPriceCounted() != null && !inventoryCountDetailBean.getSupplyPriceCounted().equalsIgnoreCase("")){
								inventoryCountDetail.setSupplyPriceCounted(new BigDecimal(inventoryCountDetailBean.getSupplyPriceCounted()));
							}
							if(inventoryCountDetailBean.getSupplyPriceExp() != null && !inventoryCountDetailBean.getSupplyPriceExp().equalsIgnoreCase("")){
								inventoryCountDetail.setSupplyPriceExp(new BigDecimal(inventoryCountDetailBean.getSupplyPriceExp()));
							}
							if(inventoryCountDetailBean.getCountDiff() != null && !inventoryCountDetailBean.getCountDiff().equalsIgnoreCase("")){
								inventoryCountDetail.setCountDiff(Integer.parseInt(inventoryCountDetailBean.getCountDiff()));
							}
							if(inventoryCountDetailBean.getPriceDiff() != null && !inventoryCountDetailBean.getPriceDiff().equalsIgnoreCase("")){
								inventoryCountDetail.setPriceDiff(new BigDecimal(inventoryCountDetailBean.getPriceDiff()));
							}
							if(inventoryCountDetailBean.getAuditTransfer() != null && !inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("")){
								if(inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("true")){
									Byte i = 1;
									inventoryCountDetail.setAuditTransfer(i);
								}else{
									Byte i = 0;
									inventoryCountDetail.setAuditTransfer(i);
								}
							}else{
								Byte i = 0;
								inventoryCountDetail.setAuditTransfer(i);
							}
							inventoryCountDetail.setInventoryCount(inventoryCount);
							inventoryCountDetail.setActiveIndicator(true);			
							inventoryCountDetail.setCreatedDate(new Date());				
							inventoryCountDetail.setLastUpdated(new Date());
							inventoryCountDetail.setCreatedBy(currentUser.getUserId());
							inventoryCountDetail.setUpdatedBy(currentUser.getUserId());
							inventoryCountDetail.setCompany(currentUser.getCompany());
							inventoryCountDetailsAddList.add(inventoryCountDetail);
							if(inventoryCountDetailBean.getAuditTransfer() != null && !inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("")){
								if(inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("true")){
									StockOrderDetailBean stockOrderDetailBean = new StockOrderDetailBean();
									//stockOrderDetailBean.setProductVariantId(String.valueOf(variantId));	
									if(inventoryCountDetailBean.getIsProduct() != null && !inventoryCountDetailBean.getIsProduct().equalsIgnoreCase("")){
										if(!inventoryCountDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
											ProductVariant productVariant = productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
											if(productVariant.getOutlet().getOutletId() == sourceOutlet.getOutletId()){
												stockOrderDetailBean.setProductVariantId(String.valueOf(productVariant.getProductVariantId()));
											}
											else{
												ProductVariant pv = warehouseProductVariants.get(productVariant.getProductVariantUuid());
												if(pv != null){
													stockOrderDetailBean.setProductVariantId(String.valueOf(pv.getProductVariantId()));
												}
												else{
													stockOrderDetailBean.setProductVariantId(String.valueOf(productVariant.getProductVariantId()));
												}
											}	
											stockOrderDetailBean.setIsProduct("false");
											BigDecimal netPrice = (productVariant.getSupplyPriceExclTax().multiply(productVariant.getMarkupPrct().divide(new BigDecimal(100)))).add(productVariant.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
											BigDecimal retailPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
											stockOrderDetailBean.setOrdrSupplyPrice(retailPrice.toString());
											stockOrderDetailBean.setRetailPrice(retailPrice.toString());
										}
										else{
											Product product = productsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
											//stockOrderDetailBean.setProductVariantId(String.valueOf(productsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())).getProductId()));
											if(product.getOutlet().getOutletId() == sourceOutlet.getOutletId()){
												stockOrderDetailBean.setProductVariantId(String.valueOf(product.getProductId()));
											}
											else{
												Product pv = warehouseProducts.get(product.getProductUuid());
												if(pv != null){
													stockOrderDetailBean.setProductVariantId(String.valueOf(pv.getProductId()));
												}
												else{
													stockOrderDetailBean.setProductVariantId(String.valueOf(product.getProductId()));
												}
											}	
											stockOrderDetailBean.setIsProduct("true");
											BigDecimal netPrice = (product.getSupplyPriceExclTax().multiply(product.getMarkupPrct().divide(new BigDecimal(100)))).add(product.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
											BigDecimal retailPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
											stockOrderDetailBean.setOrdrSupplyPrice(retailPrice.toString());
											stockOrderDetailBean.setRetailPrice(retailPrice.toString());
										}	
									}
									stockOrderDetailBean.setOrderProdQty(String.valueOf(inventoryCountDetail.getCountedProdQty() - inventoryCountDetail.getExpectedProdQty()));								
									grandTotal = grandTotal + (Double.parseDouble(stockOrderDetailBean.getOrderProdQty()) * Double.parseDouble(stockOrderDetailBean.getOrdrSupplyPrice()));
									stockOrderDetialBeansList.add(stockOrderDetailBean);
								}
							}
						}		
					}
					if(preInventoryCountDetailList != null){
						if(preInventoryCountDetailList.size() > 0)
						{
							for(InventoryCountDetail inventoryCountDetail : preInventoryCountDetailList)
							{
								inventoryCountDetailsDeleteList.add(inventoryCountDetail);
							}
						}	
					}
					if(inventoryCountDetailsUpdateList.size() > 0){
						inventoryCountDetailService.updateInventoryCountDetailsList(inventoryCountDetailsUpdateList, currentUser.getCompany().getCompanyId());
					}
					if(inventoryCountDetailsAddList.size() > 0){
						inventoryCountDetailService.addInventoryCountDetailsList(inventoryCountDetailsAddList, currentUser.getCompany().getCompanyId());
					}
					if(inventoryCountDetailsDeleteList.size() > 0){
						inventoryCountDetailService.deleteInventoryCountDetailsList(inventoryCountDetailsDeleteList, currentUser.getCompany().getCompanyId());
					}
					if(productUpdateList.size()>0){
						productService.updateProductList(productUpdateList, currentUser.getCompany());
					}
					if(productVariantUpdateList.size()>0){
						productVariantService.updateProductVariantList(productVariantUpdateList, currentUser.getCompany());
					}
					inventoryCount.setStatus(statusService.getStatusByStatusId(3));  //Complete status
					inventoryCount.setPriceDiff(new BigDecimal(inventoryCountBean.getPriceDiff()));
					inventoryCount.setCountDiff(Integer.parseInt(inventoryCountBean.getCountDiff()));
					inventoryCount.setRetailPriceExp(new BigDecimal(inventoryCountBean.getRetailPriceExp()));
					inventoryCount.setRetailPriceCounted(new BigDecimal(inventoryCountBean.getRetailPriceCounted()));
					inventoryCount.setSupplyPriceExp(new BigDecimal(inventoryCountBean.getSupplyPriceExp()));
					inventoryCount.setSupplyPriceCounted(new BigDecimal(inventoryCountBean.getSupplyPriceCounted()));
					inventoryCount.setExpectedProdQty(Integer.parseInt(inventoryCountBean.getItemCountExp()));
					inventoryCount.setCountedProdQty(Integer.parseInt(inventoryCountBean.getItemCountCounted()));
					inventoryCount.setLastUpdated(new Date());
					inventoryCount.setUpdatedBy(currentUser.getUserId());				
					inventoryCountService.updateInventoryCount(inventoryCount,currentUser.getCompany().getCompanyId());
					if(stockOrderDetialBeansList!=null && stockOrderDetialBeansList.size()>0){
						StockOrderBean stockOrderBean = new StockOrderBean();												
						stockOrderBean.setSourceOutletId(String.valueOf(sourceOutlet.getOutletId()));
						stockOrderBean.setOutlet(String.valueOf(inventoryCount.getOutlet().getOutletId()));
						AddStockOrder(sessionId, stockOrderBean, stockOrderDetialBeansList, grandTotal, request);
					}
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.INVENTORY_COUNT);
				}else{

					util.AuditTrail(request, currentUser, "InventoryCountDetails.addInventoryCountDetail", "User "+ 
							currentUser.getUserEmail()+" Unable to add and update InventoryCountDetail+"+ inventoryCountDetailBeansList.get(0).getInventoryCountDetailId(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.addInventoryCount",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}		
	}

	@SuppressWarnings("rawtypes")
	private boolean AddStockOrder(String sessionId, StockOrderBean stockOrderBean, List<StockOrderDetailBean> stockOrderDetailBeanList, Double grandTotal, HttpServletRequest request)
	{
		boolean added = false;
		if(stockOrderDetailBeanList.size() > 0){				
			//Add StockOrder
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			StockOrderBean stockOrder = new StockOrderBean();
			stockOrder.setActiveIndicator("true");
			stockOrder.setAutofillReorder("true");
			stockOrder.setRetailPriceBill("false");
			stockOrder.setSupplierId(stockOrderBean.getSupplierId());
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int min = cal.get(Calendar.MINUTE);
			stockOrder.setDiliveryDueDate(dateFormat.format(new Date()));			
			stockOrder.setOrderNo("ST-"+ (month+"/"+ day+"/"+ year+ " " + hour + ":" + min));
			stockOrder.setOrdrRecvDate(dateFormat.format(new Date()));
			stockOrder.setStatusId("3"); 	// Completed		
			stockOrder.setStockOrderTypeId("3"); //Supplier Order
			stockOrder.setStockRefNo("ST-"+ (month+"/"+ day+"/"+ year+ " " + hour + ":" + min));
			stockOrder.setOutlet(stockOrderBean.getOutletId());
			stockOrder.setSourceOutletId(stockOrderBean.getSourceOutletId());
			stockOrder.setRemarks("Auto Stock Transfer on Audit");
			//PurchaseOrderController purchaseOrderController = new PurchaseOrderController();
			Response response = purchaseOrderController.addStockOrder(sessionId, stockOrder, request);
			stockOrder.setStockOrderId(response.data.toString());
			//StockOrder Finish

			//StockOrderDetail Start
			for(StockOrderDetailBean stockOrderDetailBean : stockOrderDetailBeanList){
				stockOrderDetailBean.setStockOrderId(stockOrder.getStockOrderId());
			}
			//PurchaseOrderDetailsController purchaseOrderDetailsController = new PurchaseOrderDetailsController();
			String total = grandTotal.toString();
			purchaseOrderDetailsController.updateAndTransferStockOrderDetails(sessionId, total, stockOrderDetailBeanList, request);
			//StockOrderDetail Finish
			added = true;
		}
		return added;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/deleteInventoryCountDetail/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response deleteInventoryCountDetail(@PathVariable("sessionId") String sessionId,
			@RequestBody InventoryCountDetailBean inventoryCountDetailBean, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			try {			
				InventoryCountDetail inventoryCountDetail = new InventoryCountDetail();
				inventoryCountDetail.setInventoryCountDetailId(Integer.parseInt(inventoryCountDetailBean.getInventoryCountDetailId()));
				inventoryCountDetailService.deleteInventoryCountDetail(inventoryCountDetail,currentUser.getCompany().getCompanyId());			
				util.AuditTrail(request, currentUser, "InventoryCountDetails.deleteInventoryCountDetail", "User "+ 
						currentUser.getUserEmail()+" InventoryCountDetail Deleted+"+ inventoryCountDetailBean.getInventoryCountDetailId(),false);
				return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.deleteInventoryCountDetail",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllProductsByOutletId/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProductsByOutletId(@PathVariable("sessionId") String sessionId, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			productList = null;
			try {			
				productList = productService.getAllProductsByOutletIdByCompanyIdGroupByProductUuId(currentUser.getOutlet().getOutletId() ,currentUser.getCompany().getCompanyId());
				if(productList != null){
					for(Product product:productList){
						ProductVariantBean productVariantBean = new ProductVariantBean();
						productVariantBean.setProductId(product.getProductId().toString());
						productVariantBean.setOutletId(product.getOutlet().getOutletId().toString());
						productVariantBean.setSku(product.getSku());
						if (product.getVariantProducts().equalsIgnoreCase("true")){
							productVariantBean.setIsProduct("false");
						}
						else
						{
							productVariantBean.setIsProduct("true");
							productVariantBean.setIsVariant("false");
							productVariantBean.setProductVariantId(product.getProductId().toString());					
							productVariantBean.setVariantAttributeName(product.getProductName());
							if(product.getCurrentInventory() != null){
								productVariantBean.setCurrentInventory(product.getCurrentInventory().toString());
							}
							else
							{
								productVariantBean.setCurrentInventory("0");
							}
							productVariantBean.setProductName(product.getProductName());
							if(product.getSupplyPriceExclTax() != null){
								productVariantBean.setSupplyPriceExclTax(product.getSupplyPriceExclTax().toString());
							}
							if(product.getMarkupPrct() != null){
								/*Double retailPrice = product.getSupplyPriceExclTax().doubleValue() * (product.getMarkupPrct().doubleValue()/100) + product.getSupplyPriceExclTax().doubleValue();
								NumberFormat formatter = new DecimalFormat("###.##");  
								String strRetailPrice = formatter.format(retailPrice);
								productVariantBean.setRetailPriceExclTax(strRetailPrice);*/
								BigDecimal netPrice = (product.getSupplyPriceExclTax().multiply(product.getMarkupPrct().divide(new BigDecimal(100)))).add(product.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
								BigDecimal retailPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
								productVariantBean.setRetailPriceExclTax(retailPrice.toString());
							}
							productVariantBeansList.add(productVariantBean);
						}						
					}
					util.AuditTrail(request, currentUser, "InventoryCountDetails.getAllProducts", "User "+ 
							currentUser.getUserEmail()+" Get Products and Products",false);
					return new Response(productVariantBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} 
				else {
					util.AuditTrail(request, currentUser, "InventoryCountDetails.getAllProducts", "User "+ 
							currentUser.getUserEmail()+" Get Products",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.getAllProducts",
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
	@RequestMapping(value = "/getAllProducts/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProducts(@PathVariable("sessionId") String sessionId, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			productList = null;
			try {			
				productList = productService.getAllProducts(currentUser.getCompany().getCompanyId());
				if(productList != null){
					for(Product product:productList){
						ProductVariantBean productVariantBean = new ProductVariantBean();
						productVariantBean.setProductId(product.getProductId().toString());
						productVariantBean.setOutletId(product.getOutlet().getOutletId().toString());
						productVariantBean.setSku(product.getSku());
						productVariantBean.setAuditTransfer("true");
						if (product.getVariantProducts().equalsIgnoreCase("true")){
							productVariantBean.setIsProduct("false");
						}
						else
						{
							productVariantBean.setIsProduct("true");
							productVariantBean.setIsVariant("false");
							productVariantBean.setProductVariantId(product.getProductId().toString());					
							productVariantBean.setVariantAttributeName(product.getProductName());
							if(product.getCurrentInventory() != null){
								productVariantBean.setCurrentInventory(product.getCurrentInventory().toString());
							}
							else
							{
								productVariantBean.setCurrentInventory("0");
							}
							productVariantBean.setProductName(product.getProductName());
							if(product.getSupplyPriceExclTax() != null){
								productVariantBean.setSupplyPriceExclTax(product.getSupplyPriceExclTax().toString());
							}
							if(product.getMarkupPrct() != null){
								/*Double retailPrice = product.getSupplyPriceExclTax().doubleValue() * (product.getMarkupPrct().doubleValue()/100) + product.getSupplyPriceExclTax().doubleValue();
								NumberFormat formatter = new DecimalFormat("###.##");  
								String strRetailPrice = formatter.format(retailPrice);
								productVariantBean.setRetailPriceExclTax(strRetailPrice);*/
								BigDecimal netPrice = (product.getSupplyPriceExclTax().multiply(product.getMarkupPrct().divide(new BigDecimal(100)))).add(product.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
								BigDecimal retailPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
								productVariantBean.setRetailPriceExclTax(retailPrice.toString());
							}
							productVariantBeansList.add(productVariantBean);
						}
						
					}
					util.AuditTrail(request, currentUser, "InventoryCountDetails.getAllProducts", "User "+ 
							currentUser.getUserEmail()+" Get Products and Products",false);
					return new Response(productVariantBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} 
				else {
					util.AuditTrail(request, currentUser, "InventoryCountDetails.getAllProducts", "User "+ 
							currentUser.getUserEmail()+" Get Products",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.getAllProducts",
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
	@RequestMapping(value = "/getProductVariantsByOutletId/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getProductVariantsByOutletId(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			productVariantList = null;
			try {			
				productVariantList = productVariantService.getAllProductVariantsByOutletIdGroupbyUuid(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
				Map<Integer, Product> productsMap = new HashMap<>();
				List<Product> products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
				if(products!=null){
					for(Product product:products){
						productsMap.put(product.getProductId(), product);
					}
				}
				if(productVariantList != null){
					for(ProductVariant productVariant:productVariantList){
						ProductVariantBean productVariantBean = new ProductVariantBean();
						productVariantBean.setIsProduct("false");
						productVariantBean.setIsVariant("true");
						productVariantBean.setSku(productVariant.getSku());
						productVariantBean.setProductVariantId(productVariant.getProductVariantId().toString());
						if(productVariant.getCurrentInventory() != null){
							productVariantBean.setCurrentInventory(productVariant.getCurrentInventory().toString());
						}
						else
						{
							productVariantBean.setCurrentInventory("0");
						}
						Product product = productsMap.get(productVariant.getProduct().getProductId());
						productVariantBean.setProductName(product.getProductName());					
						productVariantBean.setVariantAttributeName(product.getProductName() + "-" + productVariant.getVariantAttributeName());
						productVariantBean.setProductId(product.getProductId().toString());
						if(productVariant.getSupplyPriceExclTax() != null){
							productVariantBean.setSupplyPriceExclTax(productVariant.getSupplyPriceExclTax().toString());
						}
						if(productVariant.getMarkupPrct() != null){
							/*Double retailPrice = productVariant.getSupplyPriceExclTax().doubleValue() * (productVariant.getMarkupPrct().doubleValue()/100) + productVariant.getSupplyPriceExclTax().doubleValue();
							NumberFormat formatter = new DecimalFormat("###.##");  
							String strRetailPrice = formatter.format(retailPrice);
							productVariantBean.setRetailPriceExclTax(strRetailPrice);*/
							BigDecimal netPrice = (productVariant.getSupplyPriceExclTax().multiply(productVariant.getMarkupPrct().divide(new BigDecimal(100)))).add(productVariant.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
							BigDecimal retailPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
							productVariantBean.setRetailPriceExclTax(retailPrice.toString());
						}
						productVariantBeansList.add(productVariantBean);
					}
					util.AuditTrail(request, currentUser, "InventoryCountDetails.getProductVariants", "User "+ 
							currentUser.getUserEmail()+" Get ProductVariants",false);
					return new Response(productVariantBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} 
				else {
					util.AuditTrail(request, currentUser, "InventoryCountDetails.getProductVariants", "User "+ 
							currentUser.getUserEmail()+" Get ProductVariants",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.getProductVariants",
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
	@RequestMapping(value = "/getAllProductVariants/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProductVariants(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			productVariantList = null;
			try {			
				productVariantList = productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
				Map<Integer, Product> productsMap = new HashMap<>();
				List<Product> products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
				if(products!=null){
					for(Product product:products){
						productsMap.put(product.getProductId(), product);
					}
				}
				if(productVariantList != null){
					for(ProductVariant productVariant:productVariantList){
						ProductVariantBean productVariantBean = new ProductVariantBean();
						productVariantBean.setIsProduct("false");
						productVariantBean.setIsVariant("true");
						productVariantBean.setSku(productVariant.getSku());
						productVariantBean.setAuditTransfer("true");
						productVariantBean.setProductVariantId(productVariant.getProductVariantId().toString());
						if(productVariant.getCurrentInventory() != null){
							productVariantBean.setCurrentInventory(productVariant.getCurrentInventory().toString());
						}
						else
						{
							productVariantBean.setCurrentInventory("0");
						}
						Product product = productsMap.get(productVariant.getProduct().getProductId());
						productVariantBean.setProductName(product.getProductName());					
						productVariantBean.setVariantAttributeName(product.getProductName() + "-" + productVariant.getVariantAttributeName());
						productVariantBean.setProductId(product.getProductId().toString());
						if(productVariant.getSupplyPriceExclTax() != null){
							productVariantBean.setSupplyPriceExclTax(productVariant.getSupplyPriceExclTax().toString());
						}
						if(productVariant.getMarkupPrct() != null){
							/*Double retailPrice = productVariant.getSupplyPriceExclTax().doubleValue() * (productVariant.getMarkupPrct().doubleValue()/100) + productVariant.getSupplyPriceExclTax().doubleValue();
							NumberFormat formatter = new DecimalFormat("###.##");  
							String strRetailPrice = formatter.format(retailPrice);
							productVariantBean.setRetailPriceExclTax(strRetailPrice);*/
							BigDecimal netPrice = (productVariant.getSupplyPriceExclTax().multiply(productVariant.getMarkupPrct().divide(new BigDecimal(100)))).add(productVariant.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
							BigDecimal retailPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
							productVariantBean.setRetailPriceExclTax(retailPrice.toString());
						}
						productVariantBeansList.add(productVariantBean);
					}
					util.AuditTrail(request, currentUser, "InventoryCountDetails.getProductVariants", "User "+ 
							currentUser.getUserEmail()+" Get ProductVariants",false);
					return new Response(productVariantBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} 
				else {
					util.AuditTrail(request, currentUser, "InventoryCountDetails.getProductVariants", "User "+ 
							currentUser.getUserEmail()+" Get ProductVariants",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.getProductVariants",
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
	@RequestMapping(value = "/getAllDetailsByInventoryCountId/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllDetailsByInventoryCountId(@PathVariable("sessionId") String sessionId,
			@RequestBody InventoryCountBean inventoryCountBean, HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			List<InventoryCountDetailBean> inventoryCountDetailBeansList = new ArrayList<>();
			List<InventoryCountDetail> inventoryCountDetailList = null;
			Map<Integer, Product> productsMap = new HashMap<>();
			Map<Integer, ProductVariant> productVariantsMap = new HashMap<>();
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				inventoryCountDetailList = inventoryCountDetailService.getInventoryCountDetailByInventoryCountId(Integer.parseInt(inventoryCountBean.getInventoryCountId()),currentUser.getCompany().getCompanyId());
				int order = 1;
				NumberFormat formatter = new DecimalFormat("###.##");  
				if (inventoryCountDetailList != null) {
					productList = productService.getAllProducts(currentUser.getCompany().getCompanyId());
					if(productList != null){
						for(Product product:productList){
							productsMap.put(product.getProductId(), product);
						}
					}
					productVariantList = productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
					if(productVariantList != null){
						for(ProductVariant productVariant:productVariantList){
							productVariantsMap.put(productVariant.getProductVariantId(), productVariant);
							//Product product = productsMap.get(productVariant.getProduct().getProductId());	
						}
					}
					for (InventoryCountDetail inventoryCountDetail : inventoryCountDetailList) {
						InventoryCountDetailBean inventoryCountDetailBean = new InventoryCountDetailBean();
						if(inventoryCountDetail.getCountDiff() != null){
							inventoryCountDetailBean.setCountDiff(String.valueOf(inventoryCountDetail.getCountDiff()));
						}
						if(inventoryCountDetail.getCountedProdQty() != null){
							inventoryCountDetailBean.setCountedProdQty(String.valueOf(inventoryCountDetail.getCountedProdQty()));
						}
						if(inventoryCountDetail.getCreatedDate() != null){
							inventoryCountDetailBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(inventoryCountDetail.getCreatedDate()));
						}
						if(inventoryCountDetail.getExpectedProdQty() != null){
							inventoryCountDetailBean.setExpProdQty(String.valueOf(inventoryCountDetail.getExpectedProdQty()));
						}
						if(inventoryCountDetail.getInventoryCountDetailId() != null){
							inventoryCountDetailBean.setInventoryCountDetailId(String.valueOf(inventoryCountDetail.getInventoryCountDetailId()));
						}
						if(inventoryCountDetail.getInventoryCount() != null){
							inventoryCountDetailBean.setInventoryCountId(String.valueOf(inventoryCountDetail.getInventoryCount().getInventoryCountId()));
						}
						inventoryCountDetailBean.setIsProduct(String.valueOf(inventoryCountDetail.isIsProduct()));
						if(inventoryCountDetail.getPriceDiff() != null){
							inventoryCountDetailBean.setPriceDiff(formatter.format(inventoryCountDetail.getPriceDiff()));
						}
						if(inventoryCountDetail.isIsProduct() == true){
							int productId = inventoryCountDetail.getProduct().getProductId();
							if(productId != 0){
								inventoryCountDetailBean.setProductVariantId(String.valueOf(productId));
								if(productsMap.get(productId) != null){
									inventoryCountDetailBean.setVariantAttributeName(productsMap.get(productId).getProductName());
								}
							}
						}
						else{
							int productVariantId = inventoryCountDetail.getProductVariant().getProductVariantId();
							if(productVariantId != 0){
								inventoryCountDetailBean.setProductVariantId(String.valueOf(productVariantId));
								ProductVariant productVariant= productVariantsMap.get(productVariantId);
								Product product = productsMap.get(productVariant.getProduct().getProductId());
								if(productVariant != null){
									if(product != null){
										inventoryCountDetailBean.setVariantAttributeName(product.getProductName() + "-" + productVariant.getVariantAttributeName());
									}
									else{
										inventoryCountDetailBean.setVariantAttributeName(productVariant.getVariantAttributeName());
									}
								}
							}
						}

						if(inventoryCountDetail.getRetailPriceCounted() != null){
							inventoryCountDetailBean.setRetailPriceCounted(formatter.format(inventoryCountDetail.getRetailPriceCounted()));
						}
						if(inventoryCountDetail.getRetailPriceExp() != null){
							inventoryCountDetailBean.setRetailPriceExp(formatter.format(inventoryCountDetail.getRetailPriceExp()));
						}
						if(inventoryCountDetail.getSupplyPriceCounted() != null){
							inventoryCountDetailBean.setSupplyPriceCounted(String.valueOf(inventoryCountDetail.getSupplyPriceCounted()));
						}
						if(inventoryCountDetail.getSupplyPriceExp() != null){
							inventoryCountDetailBean.setSupplyPriceExp(formatter.format(inventoryCountDetail.getSupplyPriceExp()));
						}
						if(inventoryCountDetail.getAuditTransfer() != null){
							if(inventoryCountDetail.getAuditTransfer().intValue() == 1){
								inventoryCountDetailBean.setAuditTransfer("true");	
							}
							else{
								inventoryCountDetailBean.setAuditTransfer("false");
							}

						}			
						else{
							inventoryCountDetailBean.setAuditTransfer("false");
						}
						inventoryCountDetailBean.setOrder(String.valueOf(order));
						order++;
						inventoryCountDetailBeansList.add(inventoryCountDetailBean);
					}
					util.AuditTrail(request, currentUser, "PurchaseOrderEditProductsController.getAllDetailsByInventoryCountId", "User "+ 
							currentUser.getUserEmail()+" Get All Stock Order Details",false);
					return new Response(inventoryCountDetailBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "PurchaseOrderEditProductsController.getAllDetailsByInventoryCountId", "User "+ 
							currentUser.getUserEmail()+"  Get All Stock Order Details",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderActionsController.getAllDetailsByInventoryCountId",
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

	/**
	 * @return the productList
	 */
	public List<Product> getProductList() {
		return productList;
	}

	/**
	 * @param productList the productList to set
	 */
	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	/**
	 * @return the productVariantList
	 */
	public List<ProductVariant> getProductVariantList() {
		return productVariantList;
	}

	/**
	 * @param productVariantList the productVariantList to set
	 */
	public void setProductVariantList(List<ProductVariant> productVariantList) {
		this.productVariantList = productVariantList;
	}

}
