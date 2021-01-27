package com.dowhile.angualrspringapp.controller; 
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Configuration;
import com.dowhile.Contact;
import com.dowhile.InventoryCount;
import com.dowhile.InventoryCountDetail;
import com.dowhile.InventoryCountDetailCustom;
import com.dowhile.Notification;
import com.dowhile.Outlet;
import com.dowhile.Product;
import com.dowhile.ProductVariant;
import com.dowhile.Status;
import com.dowhile.StockOrder;
import com.dowhile.StockOrderDetail;
import com.dowhile.User;
import com.dowhile.constant.Actions;
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
import com.dowhile.service.StockOrderService;
import com.dowhile.service.StockOrderTypeService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;
import com.dowhile.wrapper.InventoryCountWrapper;
import com.dowhile.wrapper.ProductListsWrapper;
import com.dowhile.wrapper.StockDataProductsWrapper;
import com.dowhile.wrapper.StockWrapper;
import com.dowhile.service.StockOrderDetailService;

/**
 * Zafar Shakeel
 */
@Controller
@RequestMapping("/inventoryCountDetails")

public class InventoryCountDetailsController {
	
	// private static Logger logger = Logger.getLogger(InventoryCountDetailsController.class.getName());
	@Resource
	private ServiceUtil util;
	@Resource
	private OutletService outletService;

	@Resource
	private StockOrderService stockOrderService;

	@Resource
	private ContactService supplierService;

	@Resource
	private InventoryCountService inventoryCountService;

	@Resource
	private StatusService statusService;

	@Resource
	private StockOrderTypeService stockOrderTypeService;
	
	@Resource
	private InventoryCountTypeService inventoryCountTypeService;

	@Resource
	private InventoryCountDetailService inventoryCountDetailService;	

	@Resource
	private ProductVariantService productVariantService;

	@Resource
	private StockOrderDetailService stockOrderDetailService;
	
	@Resource
	private ProductService productService;

	@Resource
	private ConfigurationService configurationService;

	/*@Autowired
	private PurchaseOrderController purchaseOrderController;
	@Autowired
	private PurchaseOrderDetailsController purchaseOrderDetailsController;*/

	private List<Product> productList; //outlet + Warehouse Products
	private List<ProductVariant> productVariantList; //outlet + Warehouse Product Variants
	//private Map<Integer, Product> warehouseProductMap = new HashMap<>(); //Warehouse Product Map
	//private Map<Integer, Product> productMap = new HashMap<>(); //outlet Products Map
	private Map<Integer, Product> compProductMap; // Outlet + Warehouse Product
	//private Map<Integer, ProductVariantBean> productVariantMap = new HashMap<>(); //outlet Product Variant Map
	//private Map<Integer, ProductVariantBean> warehouseProductVariantMap = new HashMap<>(); //Warehouse Product Map
	private Map<String, ProductVariantBean> productBeansSKUMap;
	private Map<String, ProductVariantBean> productVariantBeansSKUMap;
	private Map<String, ProductVariantBean> warehouseProductBeansSKUMap;
	private Map<String, ProductVariantBean> warehouseProductVariantBeansSKUMap;
	private int headOfficeOutletId; //= 1;
	private int outletId;
	ProductListsWrapper productListsWrapper;

	@RequestMapping("/layout")
	public String getInventoryCountDetialsControllerPartialPage(ModelMap modelMap) {
		return "inventoryCountDetails/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getInventoryCountDetailsControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getInventoryCountDetailsControllerData(@PathVariable("sessionId") String sessionId,
			@RequestBody InventoryCountBean inventoryCountBean, HttpServletRequest request) {
		//List<ProductVariantBean> complProductBeansList = new ArrayList<>();
		List<ProductVariantBean> complProductVariantBeansList = new ArrayList<>();
		List<ProductVariantBean> complProductBeansList = new ArrayList<>();
		//List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
		List<ProductVariantBean> outletProductBeansList = new ArrayList<>();
		List<ProductVariantBean> outletProductVariantBeansList = new ArrayList<>();
		List<ProductVariantBean> warehouseProductBeansList = new ArrayList<>();
		List<ProductVariantBean> warehouseProductVariantBeansList = new ArrayList<>();
		List<InventoryCountDetailBean> inventoryCountDetailBeansList = null;
		initializeClassObjects();
		//Map<Integer, ProductVariantBean> productsMap = new HashMap<>();
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
			headOfficeOutletId = outletService.getHeadOfficeOutlet(currentUser.getCompany().getCompanyId()).getOutletId();
			outletId = currentUser.getOutlet().getOutletId();
			try {
				if(outletId != headOfficeOutletId) {
					productListsWrapper = productService.getAllProductsWarehouseandOutlet(headOfficeOutletId, currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
				}else {
					productListsWrapper = productService.getAllProductsOutlet(outletId, currentUser.getCompany().getCompanyId());
				}
				Response response = getAllProductsByOutletId(sessionId, headOfficeOutletId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					//productBeansList = (List<ProductVariantBean>) response.data;
					complProductBeansList = (List<ProductVariantBean>) response.data;
					System.out.println("outlet + warehouse ProductBeansList size: " + complProductBeansList.size());
					//int outletId = currentUser.getOutlet().getOutletId();
					/*for(ProductVariantBean product:complProductBeansList){
						if(Integer.parseInt(product.getOutletId()) == outletId) {
							productBeansList.add(product);
						}
						productsMap.put(Integer.parseInt(product.getProductVariantId()), product); //in productVariantBean is Main Id 
					}*/					
				}				
				System.out.println("outlet + warehouse Product Map size: " + compProductMap.size());
				response = getProductVariantsByOutletId(sessionId, headOfficeOutletId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					//productVariantBeansList = (List<ProductVariantBean>) response.data;
					complProductVariantBeansList = (List<ProductVariantBean>) response.data;
					//int outletId = currentUser.getOutlet().getOutletId();
					/*for(ProductVariantBean productVariant:complProductVariantBeansList){
						if(Integer.parseInt(productVariant.getOutletId()) == outletId) {
							productVariantBeansList.add(productVariant);
						}
					}*/
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
				if(outletId == headOfficeOutletId){
					autoTransfer = false;
				}
				if(autoTransfer == true){
					for(ProductVariantBean product:complProductBeansList){
						if(Integer.parseInt(product.getOutletId()) == headOfficeOutletId) {
							warehouseProductBeansList.add(product);
						}else {
							outletProductBeansList.add(product);
						}
					}
					for(ProductVariantBean productVariant:complProductVariantBeansList){
						if(Integer.parseInt(productVariant.getOutletId()) == headOfficeOutletId) {
							warehouseProductVariantBeansList.add(productVariant);
						}else {
							outletProductVariantBeansList.add(productVariant);
						}
					}
					/*response = getAllProducts(sessionId, request);
					if(response.status.equals(StatusConstants.SUCCESS)){
						allProductBeansList = (List<ProductVariantBean>) response.data;
					}
					response = getAllProductVariants(sessionId, request);
					if(response.status.equals(StatusConstants.SUCCESS)){
						allProductVariantBeansList = (List<ProductVariantBean>) response.data;
					}	*/				
				}else {
					for(ProductVariantBean product:complProductBeansList){
						outletProductBeansList.add(product);
					}
					for(ProductVariantBean productVariant:complProductVariantBeansList){
						outletProductVariantBeansList.add(productVariant);
					}
				}
				response = getAllDetailsByInventoryCountIdCustom(sessionId, inventoryCountBean, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					if(response.data != null) {
						inventoryCountDetailBeansList = (List<InventoryCountDetailBean>) response.data;
					}
				}
				InventoryCountControllerBean inventoryCountControllerBean = new InventoryCountControllerBean();
				inventoryCountControllerBean.setProductBeansList(outletProductBeansList); // outlet Products Beans
				System.out.println("outletProductBeansList size: " + outletProductBeansList.size());
				inventoryCountControllerBean.setProductVariantBeansList(outletProductVariantBeansList); //outlet Product Variant Beans
				System.out.println("outletProductVariantBeansList size: " + outletProductVariantBeansList.size());
				inventoryCountControllerBean.setInventoryCountDetailBeansList(inventoryCountDetailBeansList);
				//System.out.println("inventoryCountDetailBeansList size: " + inventoryCountDetailBeansList.size());
				if(outletId != headOfficeOutletId) {
					inventoryCountControllerBean.setAllProductBeansList(warehouseProductBeansList); // Warehouse Products Beans
					System.out.println("allProductBeansList size: " + warehouseProductBeansList.size());
					inventoryCountControllerBean.setAllProductVariantBeansList(warehouseProductVariantBeansList); //warehouse ProductVariant Beans
					System.out.println("allProductVariantBeansList size: " + warehouseProductVariantBeansList.size());
				}
				inventoryCountControllerBean.setProductVariantMap(productVariantBeansSKUMap); // outlet ProductVariants
				System.out.println("productVariantMap size: " + productVariantBeansSKUMap.size());
				inventoryCountControllerBean.setProductMap(productBeansSKUMap); //outlet Products
				System.out.println("productMap size: " + productBeansSKUMap.size());
				if(outletId != headOfficeOutletId) {
					inventoryCountControllerBean.setAllProductMap(warehouseProductBeansSKUMap); // Warehouse Products
					System.out.println("allProductMap size: " + warehouseProductBeansSKUMap.size());
					inventoryCountControllerBean.setAllProductVariantMap(warehouseProductVariantBeansSKUMap); // Warehouse ProductVariants
					System.out.println("allProductVariantMap size: " + warehouseProductVariantBeansSKUMap.size());
				}
				destroyClassObjects();
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
	@RequestMapping(value = "/addFullInventoryCount/{sessionId}", method = RequestMethod.POST)	
	public @ResponseBody Response addFullInventoryCount(@PathVariable("sessionId") String sessionId,
			@RequestBody InventoryCountBean inventoryCountBean, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			//List<InventoryCountDetailBean> inventoryCountDetailBeansList = inventoryCountBean.getInventoryCountDetailBeansList(); 
			List<InventoryCountDetailBean> inventoryCountDetailBeansList = new ArrayList<>();
			List<InventoryCountDetailCustom> inventoryCountDetailCustomList;
			String stockDetails = "<p> Please Close/Complete following Stock Orders before iniating an Audit";
			List<StockOrder> stockOrderList = null;
			boolean  impersonate= (boolean) session.getAttribute("impersonate");
			if(impersonate == false) {
			try {			
				DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				stockOrderList = stockOrderService.getStockOrderByOutletIdNotComp(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
				for(StockOrder stockOrder:stockOrderList){
					String status = "";
					if(stockOrder.getStatus() != null){
						if(stockOrder.getStatus().getStatusId() == 1){
							status = "Initiated";
						}
						else if (stockOrder.getStatus().getStatusId() == 2){
							status = "In progress";
						}
					}
					stockDetails = stockDetails + "<br>" + stockOrder.getStockRefNo() + " - " + status + " - Last Updated =" + dateFormat.format(stockOrder.getLastUpdated());
				}
				stockDetails = stockDetails + "</p>";
				if(stockOrderList.size() < 1){
					inventoryCountDetailCustomList = inventoryCountDetailService.createFullInventoryCount(currentUser.getOutlet().getOutletId(), Integer.parseInt(inventoryCountBean.getInventoryCountId()), currentUser.getUserId(), currentUser.getCompany().getCompanyId());				
					for (InventoryCountDetailCustom ic : inventoryCountDetailCustomList) {
						InventoryCountDetailBean i = new InventoryCountDetailBean();
						if(ic.getAudit_transfer() == 0) {
							i.setAuditTransfer("false");
						}
						else {
							i.setAuditTransfer("true");
						}
						i.setCountDiff(Objects.toString(ic.getCOUNT_DIFF()));
						i.setCreatedBy(Objects.toString(ic.getCREATED_BY()));
						i.setCountedProdQty(Objects.toString(ic.getCOUNTED_PROD_QTY()));
						i.setExpProdQty(Objects.toString(ic.getEXPECTED_PROD_QTY()));
						i.setInventoryCountDetailId(Objects.toString(ic.getINVENTORY_COUNT_DETAIL_ID()));
						i.setInventoryCountId(Objects.toString(ic.getINVENTORY_COUNT_ASSOCICATION_ID()));
						if(!ic.isIS_PRODUCT()) {
							i.setIsProduct("false");
							i.setProductVariantId(Objects.toString(ic.getPRODUCT_VARIANT_ASSOCICATION_ID()));
							i.setVariantAttributeName(Objects.toString(ic.getVariantAttributeName()));
						}else {
							i.setIsProduct("true");
							i.setProductId(Objects.toString(ic.getPRODUCT_ASSOCIATION_ID()));
							i.setVariantAttributeName(Objects.toString(ic.getProduct_name()));							
						}
						i.setPriceDiff(Objects.toString(ic.getPRICE_DIFF()));
						i.setRetailPriceCounted(Objects.toString(ic.getRETAIL_PRICE_COUNTED()));
						i.setRetailPriceExp(Objects.toString(ic.getRETAIL_PRICE_EXP()));
						i.setSupplyPriceCounted(Objects.toString(ic.getSUPPLY_PRICE_COUNTED()));
						i.setSupplyPriceExp(Objects.toString(ic.getSUPPLY_PRICE_EXP()));
						inventoryCountDetailBeansList.add(i);						
					}
					inventoryCountBean.setInventoryCountDetailBeansList(inventoryCountDetailBeansList);
					return new Response(inventoryCountBean,StatusConstants.SUCCESS,LayOutPageConstants.INVENTORY_COUNT_EDIT_DETAILS);

				}
				else{
					util.AuditTrail(request, currentUser, "InventoryCountController.addInventoryCount", "User "+ 
							currentUser.getUserEmail()+" Unable to add InventoryCount : "+inventoryCountBean.getInventoryCountId(),false);
					return new Response(stockDetails,StatusConstants.WARNING,LayOutPageConstants.STAY_ON_PAGE);
				}				
			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.addInventoryCount",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
			}
			else {
				return new Response(MessageConstants.IMPERSONATE_USER_NOT_ALLOWED,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
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
			boolean  impersonate= (boolean) session.getAttribute("impersonate");
			if(impersonate == false) {
			List<InventoryCountDetailBean> inventoryCountDetailBeansList = inventoryCountBean.getInventoryCountDetailBeansList(); 
			List<InventoryCountDetail> inventoryCountDetailsAddList = new ArrayList<>();
			String stockDetails = "<p> Please Close/Complete following Stock Orders before iniating an Audit";
			List<StockOrder> stockOrderList = null;
			try {			
				DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				stockOrderList = stockOrderService.getStockOrderByOutletIdNotComp(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
				for(StockOrder stockOrder:stockOrderList){
					String status = "";
					if(stockOrder.getStatus() != null){
						if(stockOrder.getStatus().getStatusId() == 1){
							status = "Initiated";
						}
						else if (stockOrder.getStatus().getStatusId() == 2){
							status = "In progress";
						}
					}
					stockDetails = stockDetails + "<br>" + stockOrder.getStockRefNo() + " - " + status + " - Last Updated =" + dateFormat.format(stockOrder.getLastUpdated());
				}
				stockDetails = stockDetails + "</p>";
				if(stockOrderList.size() < 1){
					if (inventoryCountDetailBeansList.size() > 0) {	
						InventoryCount inventoryCount = inventoryCountService.getInventoryCountByInventoryCountID(Integer.parseInt(inventoryCountDetailBeansList.get(0).getInventoryCountId()),currentUser.getCompany().getCompanyId());
						/*Map<Integer, Product> productsMap = new HashMap<>();
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
						List<InventoryCountDetail> preInventoryCountDetailList = inventoryCountDetailsMap.get(Integer.parseInt(inventoryCountDetailBeansList.get(0).getInventoryCountId()));*/
						for(InventoryCountDetailBean inventoryCountDetailBean : inventoryCountDetailBeansList)
						{
							if(inventoryCountDetailBean.getIsDirty() != null && !inventoryCountDetailBean.getIsDirty().equalsIgnoreCase("")){
								if(inventoryCountDetailBean.getIsDirty().toString().equalsIgnoreCase("true")){
									/*if(inventoryCountDetailBean.getInventoryCountDetailId() != null && !inventoryCountDetailBean.getInventoryCountDetailId().equalsIgnoreCase("")){
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
							{*/
									InventoryCountDetail inventoryCountDetail = new InventoryCountDetail();
									//inventoryCountDetail.setInventoryCountDetailId(Integer.parseInt(inventoryCountDetailBean.getInventoryCountDetailId()));
									if(inventoryCountDetailBean.getInventoryCountDetailId() != null && !inventoryCountDetailBean.getInventoryCountDetailId().equalsIgnoreCase("")){
										inventoryCountDetail.setInventoryCountDetailId(Integer.parseInt(inventoryCountDetailBean.getInventoryCountDetailId()));
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
											//inventoryCountDetail.setProductVariant(productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())));
											//inventoryCountDetail.setIsProduct(false);
											ProductVariant productVariant = new ProductVariant();
											productVariant.setProductVariantId(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
											inventoryCountDetail.setProductVariant(productVariant);
											inventoryCountDetail.setIsProduct(false);
										}
										else{
											//inventoryCountDetail.setProduct(productsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())));
											//inventoryCountDetail.setIsProduct(true);
											Product product = new Product();
											product.setProductId(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
											inventoryCountDetail.setProduct(product);
											inventoryCountDetail.setIsProduct(true);
										}	
									}
									else{
										//inventoryCountDetail.setProductVariant(productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())));
										//inventoryCountDetail.setIsProduct(false);
										ProductVariant productVariant = new ProductVariant();
										productVariant.setProductVariantId(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
										inventoryCountDetail.setProductVariant(productVariant);
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
						}			
						/*if(preInventoryCountDetailList != null){
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
						}*/
						if(inventoryCountDetailsAddList.size() > 0){
							inventoryCountDetailService.addorUpdateInventoryCountDetailsList(inventoryCountDetailsAddList, currentUser.getCompany().getCompanyId());
						}
						for(int i=0; i < inventoryCountDetailsAddList.size(); i++) {
							inventoryCountDetailBeansList.get(i).setInventoryCountDetailId(String.valueOf(inventoryCountDetailsAddList.get(i).getInventoryCountDetailId()));
						}
						/*if(inventoryCountDetailsDeleteList.size() > 0){
							inventoryCountDetailService.deleteInventoryCountDetailsList(inventoryCountDetailsDeleteList, currentUser.getCompany().getCompanyId());
						}*/
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
						inventoryCountBean.setInventoryCountDetailBeansList(inventoryCountDetailBeansList);
						return new Response(inventoryCountBean,StatusConstants.SUCCESS,LayOutPageConstants.INVENTORY_COUNT_EDIT_DETAILS);
					}else{

						util.AuditTrail(request, currentUser, "InventoryCountDetails.addInventoryCountDetail", "User "+ 
								currentUser.getUserEmail()+" Unable to add InventoryCountDetail+"+ inventoryCountDetailBeansList.get(0).getInventoryCountDetailId(),false);
						return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
					}
				}
				else{
					util.AuditTrail(request, currentUser, "InventoryCountController.addInventoryCount", "User "+ 
							currentUser.getUserEmail()+" Unable to add InventoryCount : "+inventoryCountBean.getInventoryCountId(),false);
					return new Response(stockDetails,StatusConstants.WARNING,LayOutPageConstants.STAY_ON_PAGE);
				}				
			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.addInventoryCount",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}
		else {
			return new Response(MessageConstants.IMPERSONATE_USER_NOT_ALLOWED,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
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
			boolean  impersonate= (boolean) session.getAttribute("impersonate");
			if(impersonate == false) {
			Double grandTotal = 0.0;
			Double itemCount = 0.0;
			List<InventoryCountDetailBean> inventoryCountDetailBeansList = inventoryCountBean.getInventoryCountDetailBeansList(); 
			List<StockOrderDetailBean> stockOrderDetialBeansList = new ArrayList<>();
			List<Product> productUpdateList = new ArrayList<>();
			List<ProductVariant> productVariantUpdateList = new ArrayList<>();
			String stockDetails = "<p> Please Close/Complete following Stock Orders before iniating an Audit";
			List<StockOrder> stockOrderList = null;
			try {
				DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				stockOrderList = stockOrderService.getStockOrderByOutletIdNotComp(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
				for(StockOrder stockOrder:stockOrderList){
					String status = "";
					if(stockOrder.getStatus() != null){
						if(stockOrder.getStatus().getStatusId() == 1){
							status = "Initiated";
						}
						else if (stockOrder.getStatus().getStatusId() == 2){
							status = "In progress";
						}
					}
					stockDetails = stockDetails + "<br>" + stockOrder.getStockRefNo() + " - " + status + " - Last Updated =" + dateFormat.format(stockOrder.getLastUpdated());
				}
				stockDetails = stockDetails + "</p>";
				if(stockOrderList.size() < 1){
					if (inventoryCountDetailBeansList.size() > 0) {				
						initializeClassObjects();
						updateInventoryCountDetail(sessionId,inventoryCountBean, request);
						InventoryCount inventoryCount = inventoryCountService.getInventoryCountByInventoryCountID(Integer.parseInt(inventoryCountDetailBeansList.get(0).getInventoryCountId()),currentUser.getCompany().getCompanyId());
						Map<Integer, Product> productsMap = new HashMap<>();
						int sourceOutletId = outletService.getHeadOfficeOutlet(currentUser.getCompany().getCompanyId()).getOutletId();
						int outletId = currentUser.getOutlet().getOutletId();
						//List<Product> products = productService.getAllProductsWarehouseandOutlet(sourceOutlet.getOutletId(), currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());	
						ProductListsWrapper productListsWrapper = productService.getAllProductsWarehouseandOutlet(sourceOutletId, outletId, currentUser.getCompany().getCompanyId());
						List<Product> products = new ArrayList<>();
						products.addAll(productListsWrapper.getWarehouseProducts());
						products.addAll(productListsWrapper.getOutletProducts());
						//List<InventoryCountDetail> inventoryCountDetailsUpdateList = new ArrayList<>();
						//List<InventoryCountDetail> inventoryCountDetailsDeleteList = new ArrayList<>();
						//List<InventoryCountDetail> inventoryCountDetailsAddList = new ArrayList<>();
						Map<String, Product> warehouseProductsMap = new HashMap<>();
						if(products!=null){
							for(Product product:products){
								productsMap.put(product.getProductId(), product);
								if(product.getOutlet().getOutletId() == sourceOutletId){
									warehouseProductsMap.put(product.getProductUuid(), product);	
								}
							}
						}
						Map<Integer, ProductVariant> productVariantsMap = new HashMap<>();
						Map<String, ProductVariant> warehouseProductVariantsMap = new HashMap<>();
						//List<ProductVariant> productVariants = productVariantService.getAllProductVariantsWarehouseandOutlet(sourceOutlet.getOutletId(), currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
						List<ProductVariant> productVariants = new ArrayList<>(); 
						productVariants.addAll(productListsWrapper.getWarehouseProductVariants());
						productVariants.addAll(productListsWrapper.getOutletProductVariants());
						if(productVariants!=null){
							for(ProductVariant productVariant:productVariants){
								productVariantsMap.put(productVariant.getProductVariantId(), productVariant);
								if(productVariant.getOutlet().getOutletId() == sourceOutletId){
									warehouseProductVariantsMap.put(productVariant.getProductVariantUuid(), productVariant);
								}
							}
						}
						//Inventory Count Details Map Region
						/*List<InventoryCountDetail> inventoryCountDetails = new ArrayList<>();
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
						List<InventoryCountDetail> preInventoryCountDetailList = inventoryCountDetailsMap.get(Integer.parseInt(inventoryCountDetailBeansList.get(0).getInventoryCountId()));*/
						for(InventoryCountDetailBean inventoryCountDetailBean : inventoryCountDetailBeansList)
						{
							if(inventoryCountDetailBean.getInventoryCountDetailId() != null && !inventoryCountDetailBean.getInventoryCountDetailId().equalsIgnoreCase("")){
								//InventoryCountDetail inventoryCountDetail = inventoryCountDetailsByDetailIDMap.get(Integer.parseInt(inventoryCountDetailBean.getInventoryCountDetailId()));
								/*if(preInventoryCountDetailList != null){
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
								}*/
								if(inventoryCountDetailBean.getIsProduct() != null && !inventoryCountDetailBean.getIsProduct().equalsIgnoreCase("")){
									if(!inventoryCountDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
										//inventoryCountDetail.setProductVariant(productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())));
										//inventoryCountDetail.setIsProduct(false);
										//										if(inventoryCountDetailBean.getAuditTransfer() != null && !inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("")){
										//											if(!inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("true")){
										ProductVariant productVariant;
										if(productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())) != null) {
											productVariant = productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
											if(!inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("true")){
												productVariant.setCurrentInventory(Integer.parseInt(inventoryCountDetailBean.getCountedProdQty()));
												productVariantUpdateList.add(productVariant);
											}else {
												if(productVariant.getOutlet().getOutletId()==outletId){
													productVariant.setCurrentInventory(Integer.parseInt(inventoryCountDetailBean.getExpProdQty()));
													productVariantUpdateList.add(productVariant);
												}
											}
										}										
										/*}
										}else{
											ProductVariant productVariant  = productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
											productVariant.setCurrentInventory(Integer.parseInt(inventoryCountDetailBean.getExpProdQty()));
											productVariantUpdateList.add(productVariant);
										}*/
									}
									else{
										//inventoryCountDetail.setProduct(productsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())));
										//inventoryCountDetail.setIsProduct(true);
										//if(inventoryCountDetailBean.getAuditTransfer() != null && !inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("")){
										//if(!inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("true")){
										Product product;
										if(productsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())) != null){
											product = productsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
											if(product.getOutlet().getOutletId() == outletId) {
												if(!inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("true")){
													product.setCurrentInventory(Integer.parseInt(inventoryCountDetailBean.getCountedProdQty()));
													productUpdateList.add(product);
												}else {
													if(product.getOutlet().getOutletId()==outletId){
														product.setCurrentInventory(Integer.parseInt(inventoryCountDetailBean.getExpProdQty()));
														productUpdateList.add(product);
													}

												}
											}
										}
										/*}
										}else{
											Product product  = productsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
											product.setCurrentInventory(Integer.parseInt(inventoryCountDetailBean.getExpProdQty()));
											productUpdateList.add(product);
										}*/
									}	
								}
								else{
									//inventoryCountDetail.setProductVariant(productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())));
									//inventoryCountDetail.setIsProduct(false);
									//if(inventoryCountDetailBean.getAuditTransfer() != null && !inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("")){
									//if(!inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("true")){
									ProductVariant productVariant;
									if(productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())) != null) {
										productVariant = productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
										if(productVariant.getOutlet().getOutletId() == outletId) {
											if(!inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("true")){
												productVariant.setCurrentInventory(Integer.parseInt(inventoryCountDetailBean.getCountedProdQty()));
												productVariantUpdateList.add(productVariant);
											}else {
												if(productVariant.getOutlet().getOutletId()==outletId){
													productVariant.setCurrentInventory(Integer.parseInt(inventoryCountDetailBean.getExpProdQty()));
													productVariantUpdateList.add(productVariant);
												}

											}
										}
									}
									/*}
									}else{
										ProductVariant productVariant  = productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
										productVariant.setCurrentInventory(Integer.parseInt(inventoryCountDetailBean.getExpProdQty()));
										productVariantUpdateList.add(productVariant);
									}*/
								}
								/*if(inventoryCountDetailBean.getRetailPriceCounted() != null && !inventoryCountDetailBean.getRetailPriceCounted().equalsIgnoreCase("")){
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
								inventoryCountDetailsUpdateList.add(inventoryCountDetail);*/
								if(inventoryCountDetailBean.getAuditTransfer() != null && !inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("")){
									if(inventoryCountDetailBean.getAuditTransfer().equalsIgnoreCase("true")){
										StockOrderDetailBean stockOrderDetailBean = new StockOrderDetailBean();
										//stockOrderDetailBean.setProductVariantId(String.valueOf(variantId));	
										if(inventoryCountDetailBean.getIsProduct() != null && !inventoryCountDetailBean.getIsProduct().equalsIgnoreCase("")){
											if(!inventoryCountDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
												ProductVariant productVariant = productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
												if(productVariant.getOutlet().getOutletId() == sourceOutletId){
													stockOrderDetailBean.setProductVariantId(String.valueOf(productVariant.getProductVariantId()));
												}
												else{
													ProductVariant pv = warehouseProductVariantsMap.get(productVariant.getProductVariantUuid());
													if(pv != null){
														stockOrderDetailBean.setProductVariantId(String.valueOf(pv.getProductVariantId()));
													}
													/*else{
															stockOrderDetailBean.setProductVariantId(String.valueOf(productVariant.getProductVariantId()));
														}*/
												}											
												stockOrderDetailBean.setIsProduct("false");
												BigDecimal netPrice = (productVariant.getSupplyPriceExclTax().multiply(productVariant.getMarkupPrct().divide(new BigDecimal(100)))).add(productVariant.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
												BigDecimal retailPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
												stockOrderDetailBean.setOrdrSupplyPrice(retailPrice.toString());
												stockOrderDetailBean.setRetailPrice(retailPrice.toString());
											}
											else {
												Product product = productsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
												//stockOrderDetailBean.setProductVariantId(String.valueOf(productsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId())).getProductId()));
												if(product.getOutlet().getOutletId() == sourceOutletId){
													stockOrderDetailBean.setProductVariantId(String.valueOf(product.getProductId()));
												}
												else{
													Product pv = warehouseProductsMap.get(product.getProductUuid());
													if(pv != null){
														stockOrderDetailBean.setProductVariantId(String.valueOf(pv.getProductId()));
													}
												}
												stockOrderDetailBean.setIsProduct("true");
												BigDecimal netPrice = (product.getSupplyPriceExclTax().multiply(product.getMarkupPrct().divide(new BigDecimal(100)))).add(product.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
												BigDecimal retailPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
												stockOrderDetailBean.setOrdrSupplyPrice(retailPrice.toString());
												stockOrderDetailBean.setRetailPrice(retailPrice.toString());
											}
										}
										/*else{
											ProductVariant productVariant = productVariantsMap.get(Integer.parseInt(inventoryCountDetailBean.getProductVariantId()));
											if(productVariant.getOutlet().getOutletId() == sourceOutletId){
												stockOrderDetailBean.setProductVariantId(String.valueOf(productVariant.getProductVariantId()));
											}
											else{
												ProductVariant pv = warehouseProductVariantsMap.get(productVariant.getProductVariantUuid());
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
										}*/	
										stockOrderDetailBean.setOrderProdQty(inventoryCountDetailBean.getCountDiff());
										grandTotal = grandTotal + (Double.parseDouble(stockOrderDetailBean.getOrderProdQty()) * Double.parseDouble(stockOrderDetailBean.getOrdrSupplyPrice()));
										itemCount = itemCount + Double.parseDouble(stockOrderDetailBean.getOrderProdQty());
										stockOrderDetailBean.setIsDirty("true");
										stockOrderDetialBeansList.add(stockOrderDetailBean);
									}
								}
							}
							/*else
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
										itemCount = itemCount + Double.parseDouble(stockOrderDetailBean.getOrderProdQty());
										stockOrderDetialBeansList.add(stockOrderDetailBean);
									}
								}
							}	*/	
						}
						/*if(preInventoryCountDetailList != null){
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
						}*/
						InventoryCountWrapper inventoryCountWrapper = new InventoryCountWrapper();
						inventoryCountWrapper.setProductList(productUpdateList);
						//if(productUpdateList.size()>0){
						//productService.updateProductList(productUpdateList, currentUser.getCompany());
						//}
						inventoryCountWrapper.setProductVariantList(productVariantUpdateList);
						//if(productVariantUpdateList.size()>0){
						//productVariantService.updateProductVariantList(productVariantUpdateList, currentUser.getCompany());
						//}
						Status status = new Status();
						status.setStatusId(3); //Complete status
						inventoryCount.setStatus(status);  
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
						inventoryCountWrapper.setInventoryCount(inventoryCount);
						inventoryCountService.updateInventoryCountComplete(inventoryCountWrapper ,currentUser.getCompany());
						if(stockOrderDetialBeansList!=null && stockOrderDetialBeansList.size()>0){
							StockOrderBean stockOrderBean = new StockOrderBean();												
							stockOrderBean.setSourceOutletId(String.valueOf(sourceOutletId));
							stockOrderBean.setOutlet(String.valueOf(inventoryCount.getOutlet().getOutletId()));
							AddStockOrder(sessionId, stockOrderBean, stockOrderDetialBeansList, grandTotal, itemCount, request);
						}
						initializeClassObjects();
						return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.INVENTORY_COUNT);
					}else{
						util.AuditTrail(request, currentUser, "InventoryCountDetails.addandUpdateInventoryCountDetail", "User "+ 
								currentUser.getUserEmail()+" Unable to add and update InventoryCountDetail+"+ inventoryCountDetailBeansList.get(0).getInventoryCountDetailId(),false);
						return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
					}
				}
				else{
					util.AuditTrail(request, currentUser, "InventoryCountController.addandUpdateInventoryCount", "User "+ 
							currentUser.getUserEmail()+" Unable to add InventoryCount : "+inventoryCountBean.getInventoryCountId(),false);
					return new Response(stockDetails,StatusConstants.WARNING,LayOutPageConstants.STAY_ON_PAGE);
				}
			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.addandUpdateInventoryCount",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}
		else {
			return new Response(MessageConstants.IMPERSONATE_USER_NOT_ALLOWED,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
		}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}		
	}


	@SuppressWarnings("rawtypes")
	private boolean AddStockOrder(String sessionId, StockOrderBean stockOrderBean, List<StockOrderDetailBean> stockOrderDetailBeanList, Double grandTotal, Double itemCount, HttpServletRequest request)
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
			Response response = addStockOrder(sessionId, stockOrder, request);
			stockOrder.setStockOrderId(response.data.toString());
			//StockOrder Finish

			//StockOrderDetail Start
			for(StockOrderDetailBean stockOrderDetailBean : stockOrderDetailBeanList){
				stockOrderDetailBean.setStockOrderId(stockOrder.getStockOrderId());
			}
			//PurchaseOrderDetailsController purchaseOrderDetailsController = new PurchaseOrderDetailsController();
			String total = grandTotal.toString();
			String items = itemCount.toString();
			updateAndAutoTransferStockOrderDetails(sessionId, total, items, stockOrderDetailBeanList, request);
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
			boolean  impersonate= (boolean) session.getAttribute("impersonate");
			if(impersonate == false) {
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
		}
		else {
			return new Response(MessageConstants.IMPERSONATE_USER_NOT_ALLOWED,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
		}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllProductsByOutletId/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProductsByOutletId(@PathVariable("sessionId") String sessionId, int warehousOutlletId, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			//productList = new  ArrayList<>();
			//productMap = new HashMap<>();
			//compProductMap = new HashMap<>();
			try {			
				productList.addAll(productListsWrapper.getOutletProducts());
				System.out.println("Product size: " + productList.size());
				if(outletId != headOfficeOutletId) {
					productList.addAll(productListsWrapper.getWarehouseProducts());
					System.out.println("Product Warehosue Added size: " + productList.size());
				}
				//productList = productService.getAllProductsWarehouseandOutlet(warehousOutlletId, currentUser.getOutlet().getOutletId() ,currentUser.getCompany().getCompanyId());
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
							if(product.getOutlet().getOutletId() == currentUser.getOutlet().getOutletId()) {
								productBeansSKUMap.put(product.getSku().toLowerCase(), productVariantBean);
							}
							if(outletId != headOfficeOutletId) {
								if(product.getOutlet().getOutletId() == warehousOutlletId) {
									productVariantBean.setAuditTransfer("true");
									productVariantBean.setCurrentInventory("0");
									warehouseProductBeansSKUMap.put(product.getSku().toLowerCase(), productVariantBean);
								}
							}
							productVariantBeansList.add(productVariantBean);
						}		
						compProductMap.put(product.getProductId(), product);
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

	/*@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllProducts/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProducts(@PathVariable("sessionId") String sessionId, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			productList = null;
			warehouseProductMap = new HashMap<>();
			try {			 
				productList = productService.getAllProductsByOutletIdByCompanyIdGroupByProductUuId(headOfficeOutletId, currentUser.getCompany().getCompanyId());
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
								Double retailPrice = product.getSupplyPriceExclTax().doubleValue() * (product.getMarkupPrct().doubleValue()/100) + product.getSupplyPriceExclTax().doubleValue();
								NumberFormat formatter = new DecimalFormat("###.##");  
								String strRetailPrice = formatter.format(retailPrice);
								productVariantBean.setRetailPriceExclTax(strRetailPrice);
								BigDecimal netPrice = (product.getSupplyPriceExclTax().multiply(product.getMarkupPrct().divide(new BigDecimal(100)))).add(product.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
								BigDecimal retailPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
								productVariantBean.setRetailPriceExclTax(retailPrice.toString());
							}
							productVariantBeansList.add(productVariantBean);
							warehouseProductMap.put(product.getSku().toLowerCase(), productVariantBean);
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
	}*/

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getProductVariantsByOutletId/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getProductVariantsByOutletId(@PathVariable("sessionId") String sessionId, int warehouseOutletId, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			//productVariantMap = new HashMap<>();
			//productVariantList = new ArrayList<>();
			try {			
				//productVariantList = productVariantService.getAllProductVariantsWarehouseandOutlet(warehouseOutletId, currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
				productVariantList.addAll(productListsWrapper.getOutletProductVariants());
				System.out.println("outlet ProductVariant size: " + productVariantList.size());
				if(outletId != headOfficeOutletId) {
					productVariantList.addAll(productListsWrapper.getWarehouseProductVariants());
					System.out.println("Warehouse ProductVariant size: " + productVariantList.size());
				}
				/*Map<Integer, Product> productsMap1 = new HashMap<>();
				List<Product> products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
				if(products!=null){
					for(Product product:products){
						productsMap1.put(product.getProductId(), product);
					}
				}*/
				if(productVariantList != null){
					for(ProductVariant productVariant:productVariantList){
						ProductVariantBean productVariantBean = new ProductVariantBean();
						productVariantBean.setIsProduct("false");
						productVariantBean.setIsVariant("true");
						productVariantBean.setSku(productVariant.getSku());
						productVariantBean.setProductVariantId(productVariant.getProductVariantId().toString());
						productVariantBean.setOutletId(productVariant.getOutlet().getOutletId().toString());
						if(productVariant.getCurrentInventory() != null){
							productVariantBean.setCurrentInventory(productVariant.getCurrentInventory().toString());
						}
						else
						{
							productVariantBean.setCurrentInventory("0");
						}
						//Product product = productsMap1.get(productVariant.getProduct().getProductId());
						Product product = compProductMap.get(productVariant.getProduct().getProductId());
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
						//productVariantMap.put(productVariant.getSku().toLowerCase(), productVariantBean);
						if(productVariant.getOutlet().getOutletId() == currentUser.getOutlet().getOutletId()) {
							productVariantBeansSKUMap.put(productVariant.getSku().toLowerCase(), productVariantBean);
						}
						if(outletId != headOfficeOutletId) {
							if(productVariant.getOutlet().getOutletId() == warehouseOutletId) {
								productVariantBean.setAuditTransfer("true");
								productVariantBean.setCurrentInventory("0");
								warehouseProductVariantBeansSKUMap.put(productVariant.getSku().toLowerCase(), productVariantBean);
							}
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


	/*@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllProductVariants/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProductVariants(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			allProductVariantMap = new HashMap<>();
			productVariantList = null;
			try {			
				productVariantList = productVariantService.getAllProductVariantsByOutletIdGroupbyUuid(headOfficeOutletId,currentUser.getCompany().getCompanyId());
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
							Double retailPrice = productVariant.getSupplyPriceExclTax().doubleValue() * (productVariant.getMarkupPrct().doubleValue()/100) + productVariant.getSupplyPriceExclTax().doubleValue();
							NumberFormat formatter = new DecimalFormat("###.##");  
							String strRetailPrice = formatter.format(retailPrice);
							productVariantBean.setRetailPriceExclTax(strRetailPrice);
							BigDecimal netPrice = (productVariant.getSupplyPriceExclTax().multiply(productVariant.getMarkupPrct().divide(new BigDecimal(100)))).add(productVariant.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
							BigDecimal retailPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);
							productVariantBean.setRetailPriceExclTax(retailPrice.toString());
						}
						productVariantBeansList.add(productVariantBean);
						allProductVariantMap.put(productVariant.getSku().toLowerCase(), productVariantBean);
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
	}*/

	/*@SuppressWarnings({ "unchecked", "rawtypes" })
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

	}*/

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllDetailsByInventoryCountIdCustom/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllDetailsByInventoryCountIdCustom(@PathVariable("sessionId") String sessionId,
			@RequestBody InventoryCountBean inventoryCountBean, HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			List<InventoryCountDetailBean> inventoryCountDetailBeansList = new ArrayList<>();
			List<InventoryCountDetailCustom> inventoryCountDetailCustomList = null;
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				inventoryCountDetailCustomList = inventoryCountDetailService.getInventoryCountDetailByInventoryCountIdCustom(Integer.parseInt(inventoryCountBean.getInventoryCountId()),currentUser.getCompany().getCompanyId());
				if(inventoryCountDetailCustomList != null) {
					int order = 1;
					for (InventoryCountDetailCustom ic : inventoryCountDetailCustomList) {
						InventoryCountDetailBean i = new InventoryCountDetailBean();
						if(ic.getAudit_transfer() == 0) {
							i.setAuditTransfer("false");
						}
						else {
							i.setAuditTransfer("true");
						}
						i.setCountDiff(Objects.toString(ic.getCOUNT_DIFF()));
						i.setCreatedBy(Objects.toString(ic.getCREATED_BY()));
						i.setCountedProdQty(Objects.toString(ic.getCOUNTED_PROD_QTY()));
						i.setExpProdQty(Objects.toString(ic.getEXPECTED_PROD_QTY()));
						i.setInventoryCountDetailId(Objects.toString(ic.getINVENTORY_COUNT_DETAIL_ID()));
						i.setInventoryCountId(Objects.toString(ic.getINVENTORY_COUNT_ASSOCICATION_ID()));
						if(!ic.isIS_PRODUCT()) {
							i.setIsProduct("false");
							i.setProductVariantId(Objects.toString(ic.getPRODUCT_VARIANT_ASSOCICATION_ID()));
							i.setVariantAttributeName(Objects.toString(ic.getVariantAttributeName()));
						}else {
							i.setIsProduct("true");
							i.setProductId(Objects.toString(ic.getPRODUCT_ASSOCIATION_ID()));
							i.setVariantAttributeName(Objects.toString(ic.getProduct_name()));							
						}
						i.setPriceDiff(Objects.toString(ic.getPRICE_DIFF()));
						i.setRetailPriceCounted(Objects.toString(ic.getRETAIL_PRICE_COUNTED()));
						i.setRetailPriceExp(Objects.toString(ic.getRETAIL_PRICE_EXP()));
						i.setSupplyPriceCounted(Objects.toString(ic.getSUPPLY_PRICE_COUNTED()));
						i.setSupplyPriceExp(Objects.toString(ic.getSUPPLY_PRICE_EXP()));
						i.setOrder(String.valueOf(order));
						order++;
						inventoryCountDetailBeansList.add(i);
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

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addStockOrder/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response addStockOrder(@PathVariable("sessionId") String sessionId,
			@RequestBody StockOrderBean stockOrderBean, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			try {			
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				if (stockOrderBean != null) {				
					StockOrder stockOrder = new StockOrder();
					stockOrder.setActiveIndicator(true);
					stockOrder.setAutofillReorder(Boolean.valueOf(stockOrderBean.getAutofillReorder()));
					if(stockOrderBean.getRetailPriceBill() != null){
						stockOrder.setRetailPriceBill(Boolean.valueOf(stockOrderBean.getRetailPriceBill()));
					}
					stockOrder.setCreatedBy(currentUser.getUserId());
					stockOrder.setCreatedDate(new Date());
					stockOrder.setDiliveryDueDate(dateFormat.parse(stockOrderBean.getDiliveryDueDate()));
					stockOrder.setLastUpdated(new Date());
					stockOrder.setOrderNo(stockOrderBean.getOrderNo());
					stockOrder.setTotalAmount(new BigDecimal(0));
					stockOrder.setTotalItems(new BigDecimal(0));
					//stockOrder.setOrdrRecvDate(dateFormat.parse(stockOrderBean.getOrdrRecvDate()));

					//Added by Yameen
					//In case of self process order we are setting remarks
					if(stockOrderBean.getRemarks()!=null && !stockOrderBean.getRemarks().equalsIgnoreCase("")){
						stockOrder.setRemarks(stockOrderBean.getRemarks());

					}
					//stockOrder.setReturnNo(stockOrderBean.getReturnNo());
					stockOrder.setStatus(statusService.getStatusByStatusId(Integer.parseInt(stockOrderBean.getStatusId().trim()))); 				
					//stockOrder.setStockOrderDate(dateFormat.parse(stockOrderBean.getStockOrderDate()));
					//stockOrder.setStockOrderId(Integer.parseInt(stockOrderBean.getStockOrderId()));
					stockOrder.setStockOrderType(stockOrderTypeService.getStockOrderTypeByStockOrderTypeId(Integer.parseInt(stockOrderBean.getStockOrderTypeId()))); 
					//stockOrder.setStockOrderType(stockOrderTypeService.getStockOrderTypeByStockOrderTypeId(1));
					stockOrder.setStockRefNo(stockOrderBean.getStockRefNo());
					if(stockOrderBean.getSupplierId() != null && stockOrderBean.getSupplierId() != ""){
						stockOrder.setContactId(Integer.parseInt(stockOrderBean.getSupplierId()));
					}
					stockOrder.setOutletByOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getOutletId()),currentUser.getCompany().getCompanyId()));
					if(stockOrderBean.getSourceOutletId() != null && stockOrderBean.getSourceOutletId() != ""){
						stockOrder.setOutletBySourceOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getSourceOutletId()),currentUser.getCompany().getCompanyId()));
					}
					/*Outlet outlet = outletService.getOuletByOutletId(currentUser.getOutlet().getOutletId(),currentUser.getCompany().getCompanyId());
					if(outlet.getIsHeadOffice() != null){
						if(outlet.getIsHeadOffice() != true){
							stockOrder.setOutletBySourceOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getOutletId()), currentUser.getCompany().getCompanyId()));
							stockOrder.setOutletByOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getSupplierId()), currentUser.getCompany().getCompanyId()));
						}
					}
					else{
						stockOrder.setOutletBySourceOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getOutletId()), currentUser.getCompany().getCompanyId()));
						stockOrder.setOutletByOutletAssocicationId(outletService.getOuletByOutletId(Integer.parseInt(stockOrderBean.getSupplierId()), currentUser.getCompany().getCompanyId()));
					}*/
					stockOrder.setContactInvoiceNo(stockOrderBean.getSupplierInvoiceNo());
					stockOrder.setUpdatedBy(currentUser.getUserId());
					stockOrder.setCompany(currentUser.getCompany());
					stockOrderService.addStockOrder(stockOrder,currentUser.getCompany().getCompanyId());
					String path = LayOutPageConstants.PURCHASE_ORDER_RECV;
					if(stockOrder.getStockOrderType().getStockOrderTypeId() == 2){
						path = LayOutPageConstants.STOCK_RETURN_DETAILS;
					}
					else if(stockOrder.getStockOrderType().getStockOrderTypeId() == 3){
						path = LayOutPageConstants.STOCK_TRANSFER_DETAILS;
					}
					else if(stockOrder.getStockOrderType().getStockOrderTypeId() == 5){
						path = LayOutPageConstants.STOCK_SUPPLIER_TRANSFER_DETAILS;
					}
					util.AuditTrail(request, currentUser, "PurchaseOrderController.addStockOrder", 
							"User "+ currentUser.getUserEmail()+" Added StockOrder+"+stockOrderBean.getStockOrderId()+" successfully ",false);
					return new Response(stockOrder.getStockOrderId(),StatusConstants.SUCCESS,path);
				}else{
					util.AuditTrail(request, currentUser, "PurchaseOrderController.addStockOrder", "User "+ 
							currentUser.getUserEmail()+" Unable to add StockOrder : "+stockOrderBean.getStockOrderId(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.addStockOrder",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateAndAutoTransferStockOrderDetails/{sessionId}/{grandTotal}/{itemCount}", method = RequestMethod.POST)
	public @ResponseBody Response updateAndAutoTransferStockOrderDetails(@PathVariable("sessionId") String sessionId,
			@PathVariable("grandTotal") String grandTotal,
			@PathVariable("itemCount") String itemCount,
			@RequestBody List<StockOrderDetailBean> stockOrderDetailBeansList,
			HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			try {	
				initializeClassObjects();		
				if (stockOrderDetailBeansList.size() > 0) {	
					updateStockOrderDetail(sessionId, grandTotal, itemCount, stockOrderDetailBeansList, request);
					Map<String, ProductVariant> recvProductVariantList = new HashMap();
					Map<String, Product> recvProductList = new HashMap();
					StockOrder stockOrder = stockOrderService.getStockOrderByStockOrderID(Integer.parseInt(stockOrderDetailBeansList.get(0).getStockOrderId()),currentUser.getCompany().getCompanyId());
					Map<Integer, Product> productsMap = new HashMap<>();
					//List<Product> products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
					//List<StockOrderDetail> stockOrderDetailsUpdateList = new ArrayList<>();
					//List<StockOrderDetail> stockOrderDetailsDeleteList = new ArrayList<>();
					//List<StockOrderDetail> stockOrderDetailsAddList = new ArrayList<>();
					List<Product> productUpdateList = new ArrayList<>();
					List<ProductVariant> productVariantUpdateList = new ArrayList<>();
					List<Product> products = new ArrayList<>();
					List<ProductVariant> productVariants = new ArrayList<>();
					productListsWrapper = productService.getAllProductsWarehouseandOutlet(stockOrder.getOutletBySourceOutletAssocicationId().getOutletId(), stockOrder.getOutletByOutletAssocicationId().getOutletId(), currentUser.getCompany().getCompanyId());
					products.addAll(productListsWrapper.getWarehouseProducts());
					products.addAll(productListsWrapper.getOutletProducts());
					productVariants.addAll(productListsWrapper.getWarehouseProductVariants());
					productVariants.addAll(productListsWrapper.getOutletProductVariants());
					if(products!=null){
						for(Product product:products){
							productsMap.put(product.getProductId(), product);
							if(product.getOutlet().getOutletId() == stockOrder.getOutletByOutletAssocicationId().getOutletId()){
								recvProductList.put(product.getProductUuid(), product);
							}
						}
					}
					Map<Integer, ProductVariant> productVariantsMap = new HashMap<>();
					//Map<Integer, ProductVariant> 
					//List<ProductVariant> productVariants = productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
					if(productVariants!=null){
						for(ProductVariant productVariant:productVariants){
							productVariantsMap.put(productVariant.getProductVariantId(), productVariant);
							if(productVariant.getOutlet().getOutletId() == stockOrder.getOutletByOutletAssocicationId().getOutletId()){
								recvProductVariantList.put(productVariant.getProductVariantUuid(), productVariant);
							}
						}
					}
					//Stock Order Map Region
					//List<StockOrderDetail> stockOrderDetails = new ArrayList<>();
					//Map<Integer, List<StockOrderDetail>> stockOrderDetailsMap = new HashMap<>();
					//Map<Integer, StockOrderDetail> stockOrderDetailsByDetailIDMap = new HashMap<>();
					//stockOrderDetails = stockOrderDetailService.getAllStockOrderDetails(currentUser.getCompany().getCompanyId());
					//stockOrderDetails = stockOrderDetailService.getStockOrderDetailByStockOrderId(stockOrder.getStockOrderId(), currentUser.getCompany().getCompanyId());
					/*if(stockOrderDetails!=null){
							for(StockOrderDetail stockOrderDetail:stockOrderDetails){
								List<StockOrderDetail> addedstockOrderDetails = stockOrderDetailsMap.get(stockOrderDetail.getStockOrder().getStockOrderId());
								if(addedstockOrderDetails!=null){
									addedstockOrderDetails.add(stockOrderDetail);
									stockOrderDetailsMap.put(stockOrderDetail.getStockOrder().getStockOrderId(), addedstockOrderDetails);
								}else{
									addedstockOrderDetails = new ArrayList<>();
									addedstockOrderDetails.add(stockOrderDetail);
									stockOrderDetailsMap.put(stockOrderDetail.getStockOrder().getStockOrderId(), addedstockOrderDetails);
								}
								stockOrderDetailsByDetailIDMap.put(stockOrderDetail.getStockOrderDetailId(), stockOrderDetail);
							}
						}*/
					//End Region
					//List<StockOrderDetail> preStockOrderDetailList = stockOrderDetailsMap.get(Integer.parseInt(stockOrderDetailBeansList.get(0).getStockOrderId()));

					for(StockOrderDetailBean stockOrderDetail : stockOrderDetailBeansList)
					{
						//if(stockOrderDetailBean.getStockOrderDetailId() != null && !stockOrderDetailBean.getStockOrderDetailId().equalsIgnoreCase("")){
						//StockOrderDetail stockOrderDetail = stockOrderDetailsByDetailIDMap.get(Integer.parseInt(stockOrderDetailBean.getStockOrderDetailId()));
						/*if(preStockOrderDetailList != null){
									int i = 0;
									int index = -1;
									for (StockOrderDetail preStockOrderDetail : preStockOrderDetailList){
										int stockOrderDetailId = stockOrderDetail.getStockOrderDetailId();
										int preStockOrderDetailId = preStockOrderDetail.getStockOrderDetailId();
										if(stockOrderDetailId == preStockOrderDetailId)
										{ 
											System.out.println("stockOrderId = " + stockOrderDetailId);
											index = i;
											break;
										}
										i++;
									}
									if(index != -1){
										preStockOrderDetailList.remove(index);
									}
									else {
										System.out.println("Not matched= "+stockOrderDetailBean.getStockOrderDetailId());
									}
								}*/
						//stockOrderDetail.setOrderProdQty(Integer.parseInt(stockOrderDetailBean.getOrderProdQty()));
						//stockOrderDetail.setOrdrSupplyPrice(new BigDecimal(stockOrderDetailBean.getOrdrSupplyPrice()));
						ProductVariant productVariant = new ProductVariant();
						Product product = new Product();
						if(!stockOrderDetail.getIsProduct().equalsIgnoreCase("true")){
							productVariant = productVariantsMap.get(Integer.parseInt(stockOrderDetail.getProductVariantId()));
							//stockOrderDetail.setIsProduct(false);
							//stockOrderDetail.setProductVariant(productVariant);
							int preQuantity = productVariant.getCurrentInventory();
							productVariant.setCurrentInventory(preQuantity - Integer.parseInt(stockOrderDetail.getOrderProdQty()));						
							productVariant.setLastUpdated(new Date());
							productVariant.setUserByUpdatedBy(currentUser);
							//productVariantService.updateProductVariant(productVariant, Actions.UPDATE, productVariant.getCurrentInventory(),currentUser.getCompany());
							productVariantUpdateList.add(productVariant);
						}
						else{
							product = productsMap.get(Integer.parseInt(stockOrderDetail.getProductVariantId()));
							//stockOrderDetail.setProduct(product);
							//stockOrderDetail.setIsProduct(true);
							int preQuantity = product.getCurrentInventory();
							product.setCurrentInventory(preQuantity - Integer.parseInt(stockOrderDetail.getOrderProdQty()));						
							product.setLastUpdated(new Date());
							product.setUserByUpdatedBy(currentUser);
							//productService.updateProduct(product, Actions.INVENTORY_TRANSFER, product.getCurrentInventory(),currentUser.getCompany());
							productUpdateList.add(product);
						}
						/*if(stockOrderDetailBean.getRecvProdQty() != null && !stockOrderDetailBean.getRecvProdQty().equalsIgnoreCase("")){
									stockOrderDetail.setRecvProdQty(Integer.parseInt(stockOrderDetailBean.getRecvProdQty()));
								}
								if(stockOrderDetailBean.getRecvSupplyPrice() != null && !stockOrderDetailBean.getRecvSupplyPrice().equalsIgnoreCase("")){
									stockOrderDetail.setRecvSupplyPrice(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
								}
								if(stockOrderDetailBean.getRetailPrice() != null && !stockOrderDetailBean.getRetailPrice().equalsIgnoreCase("")){
									stockOrderDetail.setRetailPrice(new BigDecimal(stockOrderDetailBean.getRetailPrice()));
								}*/
						boolean found = false;
						//if(stockOrder.getOutletBySourceOutletAssocicationId() != null){
						//recvProductVariantList = productVariantService.getAllProductVariantsByOutletId(stockOrder.getOutletByOutletAssocicationId().getOutletId(),currentUser.getCompany().getCompanyId());
						//recvProductList = productService.getAllProductsByOutletId(stockOrder.getOutletByOutletAssocicationId().getOutletId());
						//}
						if(!stockOrderDetail.getIsProduct().equalsIgnoreCase("true")){
							//for(ProductVariant recProductVariant : recvProductVariantList)
							//{
							//									UUID u1 = UUID.fromString(recProductVariant.getProductVariantUuid());
							//									UUID u2 = UUID.fromString(productVariant.getProductVariantUuid());
							ProductVariant recProductVariant = recvProductVariantList.get(productVariant.getProductVariantUuid());
							if(recProductVariant != null){
								int recvPreQuantity = recProductVariant.getCurrentInventory();
								recProductVariant.setCurrentInventory(recvPreQuantity + Integer.parseInt(stockOrderDetail.getOrderProdQty()));
								recProductVariant.setSupplyPriceExclTax(productVariant.getSupplyPriceExclTax());
								recProductVariant.setMarkupPrct(productVariant.getMarkupPrct());
								//productVariantService.updateProductVariant(recProductVariant, Actions.UPDATE, recProductVariant.getCurrentInventory(),currentUser.getCompany());
								productVariantUpdateList.add(recProductVariant);
								Product sourceProduct = productsMap.get(productVariant.getProduct().getProductId());
								Product recProduct = productsMap.get(recProductVariant.getProduct().getProductId());
								//recProduct.setContact(supplierService.getContactByID(sourceProduct.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
								recProduct.setSupplyPriceExclTax(sourceProduct.getSupplyPriceExclTax());
								recProduct.setMarkupPrct(sourceProduct.getMarkupPrct());
								//productService.updateProduct(recProduct, Actions.UPDATE, recvPreQuantity + stockOrderDetail.getOrderProdQty(), currentUser.getCompany());
								productUpdateList.add(recProduct);
								found = true;
								//break;
							}
							//}
							if(!found)
							{	
								Product parentProduct = productsMap.get(productVariant.getProduct().getProductId());
								boolean isPresent = false;
								Product newProduct = new Product();
								//for(Product recProduct: recvProductList){
								//										UUID u1 = UUID.fromString(recProduct.getProductUuid());
								//										UUID u2 = UUID.fromString(parentProduct.getProductUuid());
								Product recProduct = recvProductList.get(parentProduct.getProductUuid());
								if(recProduct != null){
									isPresent = true;
									if(!productUpdateList.contains(recProduct)){
										//recProduct.setContact(supplierService.getContactByID(parentProduct.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
										recProduct.setSupplyPriceExclTax(parentProduct.getSupplyPriceExclTax());
										recProduct.setMarkupPrct(parentProduct.getMarkupPrct());
										//productService.updateProduct(recProduct, Actions.UPDATE, recProduct.getCurrentInventory() + stockOrderDetail.getOrderProdQty(), currentUser.getCompany());
										productUpdateList.add(recProduct);
									}
									//break;
								}
								//}
								if(!isPresent)
								{
									newProduct.setOutlet(stockOrder.getOutletByOutletAssocicationId());
									newProduct.setCurrentInventory(Integer.parseInt(stockOrderDetail.getOrderProdQty()));
									newProduct.setCreatedDate(new Date());				
									newProduct.setLastUpdated(new Date());
									newProduct.setUserByCreatedBy(currentUser);
									newProduct.setUserByUpdatedBy(currentUser);
									newProduct.setCompany(currentUser.getCompany());
									newProduct.setActiveIndicator(parentProduct.isActiveIndicator());
									newProduct.setAttribute1(parentProduct.getAttribute1());
									newProduct.setAttribute2(parentProduct.getAttribute2());
									newProduct.setAttribute3(parentProduct.getAttribute3());
									if(parentProduct.getBrand() != null){
										newProduct.setBrand(parentProduct.getBrand());
									}
									if(parentProduct.getContact() != null){
										newProduct.setContact(parentProduct.getContact());
									}
									if(parentProduct.getDisplay() != null){
										newProduct.setDisplay(parentProduct.getDisplay());
									}
									if(parentProduct.getImagePath() != null){
										newProduct.setImagePath(parentProduct.getImagePath());
									}
									if(parentProduct.getMarkupPrct() != null){
										newProduct.setMarkupPrct(parentProduct.getMarkupPrct());
									}
									if(parentProduct.getProductCanBeSold() != null){
										newProduct.setProductCanBeSold(parentProduct.getProductCanBeSold());
									}
									if(parentProduct.getProductDesc() != null){
										newProduct.setProductDesc(parentProduct.getProductDesc());
									}
									if(parentProduct.getProductHandler() != null){
										newProduct.setProductHandler(parentProduct.getProductHandler());
									}
									if(parentProduct.getProductName() != null){
										newProduct.setProductName(parentProduct.getProductName());
									}
									if(parentProduct.getProductTags() != null){
										newProduct.setProductTags(parentProduct.getProductTags());
									}
									if(parentProduct.getProductType() != null){
										newProduct.setProductType(parentProduct.getProductType());
									}
									if(parentProduct.getProductUuid() != null){
										newProduct.setProductUuid(parentProduct.getProductUuid());
									}
									if(parentProduct.getPurchaseAccountCode() != null){
										newProduct.setPurchaseAccountCode(parentProduct.getPurchaseAccountCode());
									}
									if(parentProduct.getReorderAmount() != null){
										newProduct.setReorderAmount(parentProduct.getReorderAmount());
									}
									if(parentProduct.getReorderPoint() != null){
										newProduct.setReorderPoint(parentProduct.getReorderPoint());
									}
									if(parentProduct.getSalesAccountCode() != null){
										newProduct.setSalesAccountCode(parentProduct.getSalesAccountCode());
									}
									if(parentProduct.getSalesTax() != null){
										newProduct.setSalesTax(parentProduct.getSalesTax());
									}
									if(parentProduct.getSku() != null){
										newProduct.setSku(parentProduct.getSku());
									}
									if(parentProduct.getStandardProduct() != null){
										newProduct.setStandardProduct(parentProduct.getStandardProduct());
									}
									if(parentProduct.getSupplyPriceExclTax() != null){
										newProduct.setSupplyPriceExclTax(parentProduct.getSupplyPriceExclTax());
									}
									if(parentProduct.getTrackingProduct() != null){
										newProduct.setTrackingProduct(parentProduct.getTrackingProduct());
									}
									if(parentProduct.getVariantProducts() != null){
										newProduct.setVariantProducts(parentProduct.getVariantProducts());
									}

									newProduct = productService.addProduct(newProduct, Actions.CREATE, Integer.parseInt(stockOrderDetail.getOrderProdQty()),currentUser.getCompany());
									//productUpdateList.add(newProduct);
									recvProductList.put(newProduct.getProductUuid(), newProduct);
									productsMap.put(newProduct.getProductId(), newProduct);
								}
								ProductVariant recvProductVariant = new ProductVariant(); 
								recvProductVariant.setOutlet(stockOrder.getOutletByOutletAssocicationId());
								recvProductVariant.setCurrentInventory(Integer.parseInt(stockOrderDetail.getOrderProdQty()));
								if(newProduct.getProductId() != null){
									recvProductVariant.setProduct(newProduct);
								}
								else{
									recvProductVariant.setProduct(recProduct);
								}
								recvProductVariant.setCreatedDate(new Date());				
								recvProductVariant.setLastUpdated(new Date());
								recvProductVariant.setUserByCreatedBy(currentUser);
								recvProductVariant.setUserByUpdatedBy(currentUser);
								recvProductVariant.setCompany(currentUser.getCompany());
								recvProductVariant.setActiveIndicator(productVariant.isActiveIndicator());
								if(productVariant.getMarkupPrct() != null){
									recvProductVariant.setMarkupPrct(productVariant.getMarkupPrct());
								}
								if(productVariant.getProductUuid() != null){
									recvProductVariant.setProductUuid(productVariant.getProductUuid());
								}
								if(productVariant.getReorderAmount() != null){
									recvProductVariant.setReorderAmount(productVariant.getReorderAmount());
								}
								if(productVariant.getReorderPoint() != null){
									recvProductVariant.setReorderPoint(productVariant.getReorderPoint());
								}
								if(productVariant.getSalesTax() != null){
									recvProductVariant.setSalesTax(productVariant.getSalesTax());
								}
								if(productVariant.getSku() != null){
									recvProductVariant.setSku(productVariant.getSku());
								}
								if(productVariant.getSupplyPriceExclTax() != null){
									recvProductVariant.setSupplyPriceExclTax(productVariant.getSupplyPriceExclTax());
								}
								if(productVariant.getVariantAttributeByVariantAttributeAssocicationId1() != null){
									recvProductVariant.setVariantAttributeByVariantAttributeAssocicationId1(productVariant.getVariantAttributeByVariantAttributeAssocicationId1());
								}
								if(productVariant.getVariantAttributeByVariantAttributeAssocicationId2() != null){
									recvProductVariant.setVariantAttributeByVariantAttributeAssocicationId2(productVariant.getVariantAttributeByVariantAttributeAssocicationId2());
								}
								if(productVariant.getVariantAttributeByVariantAttributeAssocicationId3() != null){
									recvProductVariant.setVariantAttributeByVariantAttributeAssocicationId3(productVariant.getVariantAttributeByVariantAttributeAssocicationId3());
								}
								if(productVariant.getVariantAttributeName() != null){
									recvProductVariant.setVariantAttributeName(productVariant.getVariantAttributeName());
								}
								if(productVariant.getVariantAttributeValue1() != null){
									recvProductVariant.setVariantAttributeValue1(productVariant.getVariantAttributeValue1());
								}
								if(productVariant.getVariantAttributeValue2() != null){
									recvProductVariant.setVariantAttributeValue2(productVariant.getVariantAttributeValue2());
								}
								if(productVariant.getVariantAttributeValue3() != null){
									recvProductVariant.setVariantAttributeValue3(productVariant.getVariantAttributeValue3());
								}
								//									if(productVariant.getVariantAttributeValueses() != null){
								//										recvProductVariant.setVariantAttributeValueses(productVariant.getVariantAttributeValueses());
								//									}
								if(productVariant.getProductVariantUuid() != null){
									recvProductVariant.setProductVariantUuid(productVariant.getProductVariantUuid());
								}
								//recvProductVariant = productVariantService.addProductVariant(recvProductVariant, Actions.CREATE, stockOrderDetail.getOrderProdQty(),currentUser.getCompany(),recvProductVariant.getProductUuid());
								productVariantUpdateList.add(recvProductVariant);
								recvProductVariantList.put(recvProductVariant.getProductVariantUuid(), recvProductVariant);
								productVariantsMap.put(recvProductVariant.getProductVariantId(), recvProductVariant);
							}									
						}
						else{
							//for(Product recProduct : recvProductList){
							//									UUID u1 = UUID.fromString(recProduct.getProductUuid());
							//									UUID u2 = UUID.fromString(product.getProductUuid());
							Product recProduct = recvProductList.get(product.getProductUuid());
							if(recProduct != null){
								int recvPreQuantity = recProduct.getCurrentInventory();
								recProduct.setCurrentInventory(recvPreQuantity + Integer.parseInt(stockOrderDetail.getOrderProdQty()));		
								recProduct.setLastUpdated(new Date());
								recProduct.setUserByUpdatedBy(currentUser);
								//recProduct.setContact(supplierService.getContactByID(product.getContact().getContactId(), currentUser.getCompany().getCompanyId()));
								recProduct.setSupplyPriceExclTax(product.getSupplyPriceExclTax());
								recProduct.setMarkupPrct(product.getMarkupPrct());
								//productService.updateProduct(recProduct, Actions.UPDATE, recProduct.getCurrentInventory(),currentUser.getCompany());
								productUpdateList.add(recProduct);
								found = true;
								//break;
							}
							//}
							if(!found){
								Product recvProduct = product; 
								recvProduct.setOutlet(stockOrder.getOutletByOutletAssocicationId());
								recvProduct.setCurrentInventory(Integer.parseInt(stockOrderDetail.getOrderProdQty()));
								recvProduct.setCreatedDate(new Date());				
								recvProduct.setLastUpdated(new Date());
								recvProduct.setUserByCreatedBy(currentUser);
								recvProduct.setUserByUpdatedBy(currentUser);
								recvProduct.setCompany(currentUser.getCompany());
								recvProduct.setAttribute1(product.getAttribute1());
								recvProduct.setAttribute2(product.getAttribute2());
								recvProduct.setAttribute3(product.getAttribute3());
								//recvProduct = productService.addProduct(recvProduct, Actions.CREATE, stockOrderDetail.getOrderProdQty(),currentUser.getCompany());
								productUpdateList.add(recvProduct);
								recvProductList.put(recvProduct.getProductUuid(), recvProduct);
								productsMap.put(recvProduct.getProductId(), recProduct);
							}
						}
						
					}
					
					StockWrapper stockWrapper = new StockWrapper(); 
					
					stockWrapper.setProductUpdateList(productUpdateList);
					
					stockWrapper.setProductVariantUpdateList(productVariantUpdateList);
					
					Status status = new Status();
					status.setStatusId(3);
					stockOrder.setStatus(status);
					stockOrder.setTotalAmount(new BigDecimal(grandTotal));
					stockOrder.setTotalItems(new BigDecimal(itemCount));
					stockOrder.setOrdrRecvDate(new Date());
					stockOrder.setLastUpdated(new Date());
					stockOrder.setUpdatedBy(currentUser.getUserId());
					
					stockWrapper.setStockOrder(stockOrder);	
					try { 
						if(stockOrder!=null){
							Notification notification = new Notification();

							Outlet outletByOutletIdFrom = new Outlet();
							outletByOutletIdFrom.setOutletId(stockOrder.getOutletBySourceOutletAssocicationId().getOutletId());

							//System.out.println("hello " +outletByOutletIdFrom);

							notification.setOutletByOutletIdFrom(outletByOutletIdFrom);

							Outlet outletByOutletIdTo = new Outlet();
							outletByOutletIdTo.setOutletId(stockOrder.getOutletByOutletAssocicationId().getOutletId());

							//System.out.println(outletByOutletIdTo);

							notification.setOutletByOutletIdTo(outletByOutletIdTo);
							notification.setDescription("Stock of worth: "+grandTotal+" has been transferd having total item count: "+itemCount);
							notification.setCompany(stockOrder.getCompany());
							notification.setActiveIndicator(true);
							notification.setCreatedDate(new Date());
							notification.setCreatedBy(currentUser.getUserId());
							notification.setLastUpdated(new Date());
							notification.setUpdatedBy(currentUser.getUserId());
							notification.setSubject("transfer stock");
							//notificationService.addNotification(notification);
							stockWrapper.setNotification(notification);							
						}


					} catch (Exception e) {
						e.printStackTrace();// logger.error(e.getMessage(),e);
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
					}
					Contact supplier = supplierService.getContactByContactOutletID(stockOrder.getOutletByOutletAssocicationId().getOutletId(), currentUser.getCompany().getCompanyId());
					if(supplier.getContactBalance() == null){
						supplier.setContactBalance(BigDecimal.ZERO);
					}
					supplier.setContactBalance(supplier.getContactBalance().subtract(new BigDecimal(grandTotal)));
					supplier.setLastUpdated(new Date());
					supplier.setUpdatedBy(currentUser.getUserId());
					//supplierService.updateContact(supplier, currentUser.getCompany().getCompanyId());
					stockWrapper.setContact(supplier);
					stockOrderService.UpdateStockComplete(stockWrapper, currentUser.getCompany());
					destroyClassObjects();
					util.AuditTrail(request, currentUser, "InventoryCountDetails.updateandRecStockOrderDetail", 
							"User "+ currentUser.getUserEmail()+"Updated Supplier+"+supplier.getContactId()+" successfully ",false);



					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.STOCKCONTROL);
				}else{
					util.AuditTrail(request, currentUser, "InventoryCountDetails.addStockOrderDetail", "User "+ 
							currentUser.getUserEmail()+" Unable to add StockOrderDetail+"+ stockOrderDetailBeansList.get(0).getStockOrderDetailId(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountDetails.addStockOrder",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}		
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateStockOrderDetail/{sessionId}/{itemCountTotal}/{itemCount}", method = RequestMethod.POST)
	public @ResponseBody Response updateStockOrderDetail(@PathVariable("sessionId") String sessionId,
			@PathVariable("itemCountTotal") String itemCountTotal,
			@PathVariable("itemCount") String itemCount,
			@RequestBody List<StockOrderDetailBean> stockOrderDetailBeansList, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			try {			
				if (stockOrderDetailBeansList.size() > 0) {	
					StockOrder stockOrder = stockOrderService.getStockOrderByStockOrderID(Integer.parseInt(stockOrderDetailBeansList.get(0).getStockOrderId()),currentUser.getCompany().getCompanyId());
					//List<StockOrderDetail> stockOrderDetailsUpdateList = new ArrayList<>();
					//List<StockOrderDetail> stockOrderDetailsDeleteList = new ArrayList<>();
					List<StockOrderDetail> stockOrderDetailsAddList = new ArrayList<>();
					/*Map<Integer, Product> productsMap = new HashMap<>();
					if(session.getAttribute("productIdsMap") != null) {
						productsMap = (Map<Integer, Product>)session.getAttribute("productIdsMap");
					}
					else {
						List<Product> products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
						if(products!=null){
							for(Product product:products){
								productsMap.put(product.getProductId(), product);
							}
						}
					}	
					Map<Integer, ProductVariant> productVariantsMap = new HashMap<>();
					if(session.getAttribute("productVariantIdsMap") != null) {
						productVariantsMap = (Map<Integer, ProductVariant>)session.getAttribute("productVariantIdsMap");
					}
					else {
						List<ProductVariant> productVariants = productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
						if(productVariants!=null){
							for(ProductVariant productVariant:productVariants){
								productVariantsMap.put(productVariant.getProductVariantId(), productVariant);
							}
						}
					}*/
					//Stock Order Map Region
					/*List<StockOrderDetail> stockOrderDetails = new ArrayList<>();
					Map<Integer, List<StockOrderDetail>> stockOrderDetailsMap = new HashMap<>();
					Map<Integer, StockOrderDetail> stockOrderDetailsByDetailIDMap = new HashMap<>();
					stockOrderDetails = stockOrderDetailService.getAllStockOrderDetails(currentUser.getCompany().getCompanyId());
					if(stockOrderDetails!=null){
						for(StockOrderDetail stockOrderDetail:stockOrderDetails){
							List<StockOrderDetail> addedstockOrderDetails = stockOrderDetailsMap.get(stockOrderDetail.getStockOrder().getStockOrderId());
							if(addedstockOrderDetails!=null){
								addedstockOrderDetails.add(stockOrderDetail);
								stockOrderDetailsMap.put(stockOrderDetail.getStockOrder().getStockOrderId(), addedstockOrderDetails);
							}else{
								addedstockOrderDetails = new ArrayList<>();
								addedstockOrderDetails.add(stockOrderDetail);
								stockOrderDetailsMap.put(stockOrderDetail.getStockOrder().getStockOrderId(), addedstockOrderDetails);
							}
							stockOrderDetailsByDetailIDMap.put(stockOrderDetail.getStockOrderDetailId(), stockOrderDetail);
						}
					}*/
					//End Region
					//List<StockOrderDetail> preStockOrderDetailList = stockOrderDetailsMap.get(Integer.parseInt(stockOrderDetailBeansList.get(0).getStockOrderId()));
					for(StockOrderDetailBean stockOrderDetailBean : stockOrderDetailBeansList)
					{
						if(stockOrderDetailBean.getIsDirty() != null && !stockOrderDetailBean.getIsDirty().equalsIgnoreCase("")){
							if(stockOrderDetailBean.getIsDirty().toString().equalsIgnoreCase("true")){
								/*if(stockOrderDetailBean.getStockOrderDetailId() != null && !stockOrderDetailBean.getStockOrderDetailId().equalsIgnoreCase("")){
							//StockOrderDetail stockOrderDetail = stockOrderDetailsByDetailIDMap.get(Integer.parseInt(stockOrderDetailBean.getStockOrderDetailId()));
							if(preStockOrderDetailList != null){
								int i = 0;
								int index = -1;
								for (StockOrderDetail preStockOrderDetail : preStockOrderDetailList){
									int stockOrderDetailId = stockOrderDetail.getStockOrderDetailId();
									int preStockOrderDetailId = preStockOrderDetail.getStockOrderDetailId();
									if(stockOrderDetailId == preStockOrderDetailId)
									{
										index = i;
										break;
									}
									i++;
								}
								if(index != -1){
									preStockOrderDetailList.remove(index);
								}
							}
							stockOrderDetail.setOrderProdQty(Integer.parseInt(stockOrderDetailBean.getOrderProdQty()));
							stockOrderDetail.setOrdrSupplyPrice(new BigDecimal(stockOrderDetailBean.getOrdrSupplyPrice()));
							if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
								stockOrderDetail.setProductVariant(productVariantsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId())));
								stockOrderDetail.setIsProduct(false);
							}
							else{
								stockOrderDetail.setProduct(productsMap.get(Integer.parseInt(stockOrderDetailBean.getProductVariantId())));
								stockOrderDetail.setIsProduct(true);
							}
							if(stockOrderDetailBean.getRecvProdQty() != null && !stockOrderDetailBean.getRecvProdQty().equalsIgnoreCase("")){
								stockOrderDetail.setRecvProdQty(Integer.parseInt(stockOrderDetailBean.getRecvProdQty()));
							}
							if(stockOrderDetailBean.getRecvSupplyPrice() != null && !stockOrderDetailBean.getRecvSupplyPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRecvSupplyPrice(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
							}
							if(stockOrderDetailBean.getRetailPrice() != null && !stockOrderDetailBean.getRetailPrice().equalsIgnoreCase("")){
								stockOrderDetail.setRetailPrice(new BigDecimal(stockOrderDetailBean.getRetailPrice()));
							}
							stockOrderDetail.setStockOrder(stockOrder);
							stockOrderDetail.setActiveIndicator(true);				
							stockOrderDetail.setLastUpdated(new Date());
							stockOrderDetail.setUpdatedBy(currentUser.getUserId());
							stockOrderDetailsUpdateList.add(stockOrderDetail);
							//stockOrderDetailService.updateStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());
							//util.AuditTrail(request, currentUser, "PurchaseOrderDetails.updateStockOrderDetail", 
							//	"User "+ currentUser.getUserEmail()+" Update StockOrderDetail+"+stockOrderDetailBean.getStockOrderDetailId()+" successfully ",false);
						}
						else
						{*/
								StockOrderDetail stockOrderDetail = new StockOrderDetail();
								if(stockOrderDetailBean.getStockOrderDetailId() != null && !stockOrderDetailBean.getStockOrderDetailId().equalsIgnoreCase("")){
									stockOrderDetail.setStockOrderDetailId(Integer.parseInt(stockOrderDetailBean.getStockOrderDetailId()));
								}
								stockOrderDetail.setOrderProdQty(Integer.parseInt(stockOrderDetailBean.getOrderProdQty()));
								stockOrderDetail.setOrdrSupplyPrice(new BigDecimal(stockOrderDetailBean.getOrdrSupplyPrice()));
								if(!stockOrderDetailBean.getIsProduct().toString().equalsIgnoreCase("true")){
									ProductVariant productVariant = new ProductVariant();
									productVariant.setProductVariantId(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
									stockOrderDetail.setProductVariant(productVariant);
									stockOrderDetail.setIsProduct(false);
								}
								else{
									Product product = new Product();
									product.setProductId(Integer.parseInt(stockOrderDetailBean.getProductVariantId()));
									stockOrderDetail.setProduct(product);
									stockOrderDetail.setIsProduct(true);	
								}
								if(stockOrderDetailBean.getRecvProdQty() != null && !stockOrderDetailBean.getRecvProdQty().equalsIgnoreCase("")){
									stockOrderDetail.setRecvProdQty(Integer.parseInt(stockOrderDetailBean.getRecvProdQty()));
								}
								if(stockOrderDetailBean.getRecvSupplyPrice() != null && !stockOrderDetailBean.getRecvSupplyPrice().equalsIgnoreCase("")){
									stockOrderDetail.setRecvSupplyPrice(new BigDecimal(stockOrderDetailBean.getRecvSupplyPrice()));
								}
								if(stockOrderDetailBean.getRetailPrice() != null && !stockOrderDetailBean.getRetailPrice().equalsIgnoreCase("")){
									stockOrderDetail.setRetailPrice(new BigDecimal(stockOrderDetailBean.getRetailPrice()));
								}
								stockOrderDetail.setStockOrder(stockOrder);
								stockOrderDetail.setActiveIndicator(true);			
								stockOrderDetail.setCreatedDate(new Date());				
								stockOrderDetail.setLastUpdated(new Date());
								stockOrderDetail.setCreatedBy(currentUser.getUserId());
								stockOrderDetail.setUpdatedBy(currentUser.getUserId());
								stockOrderDetail.setCompany(currentUser.getCompany());
								stockOrderDetailsAddList.add(stockOrderDetail);
								/*stockOrderDetailService.addStockOrderDetail(stockOrderDetail,currentUser.getCompany().getCompanyId());
							util.AuditTrail(request, currentUser, "PurchaseOrderDetails.addStockOrderDetail", 
									"User "+ currentUser.getUserEmail()+" Added StockOrderDetail+"+stockOrderDetailBean.getStockOrderDetailId()+" successfully ",false);*/
							}
						}
					}
					//}			
					/*if(preStockOrderDetailList != null){
						if(preStockOrderDetailList.size() > 0)
						{
							for(StockOrderDetail stockOrderDetail : preStockOrderDetailList)
							{
								stockOrderDetailsDeleteList.add(stockOrderDetail);
							}
						}	
					}*/
					/*if(stockOrderDetailsUpdateList.size() > 0){
						stockOrderDetailService.updateStockOrderDetailsList(stockOrderDetailsUpdateList, currentUser.getCompany().getCompanyId());
					}*/
					if(stockOrderDetailsAddList.size() > 0){
						stockOrderDetailService.saveorUpdateStockOrderDetailsList(stockOrderDetailsAddList, currentUser.getCompany().getCompanyId());
					}
					for(int i=0; i < stockOrderDetailsAddList.size(); i++) {
						stockOrderDetailBeansList.get(i).setStockOrderDetailId(String.valueOf(stockOrderDetailsAddList.get(i).getStockOrderDetailId()));
					}					
					/*if(stockOrderDetailsDeleteList.size() > 0){
						stockOrderDetailService.deleteStockOrderDetailsList(stockOrderDetailsDeleteList, currentUser.getCompany().getCompanyId());
					}*/
					stockOrder.setStatus(statusService.getStatusByStatusId(2));  //in Progress status
					stockOrder.setOrdrRecvDate(new Date());
					stockOrder.setLastUpdated(new Date());
					stockOrder.setTotalAmount(new BigDecimal(itemCountTotal));
					stockOrder.setTotalItems(new BigDecimal(itemCount));					
					stockOrder.setUpdatedBy(currentUser.getUserId());				
					stockOrderService.updateStockOrder(stockOrder,currentUser.getCompany().getCompanyId());
					String layOutPath = LayOutPageConstants.STOCKCONTROL;
					if(stockOrder.getStockOrderType().getStockOrderTypeId() == 1){
						layOutPath = LayOutPageConstants.PO_Create_RECV_EDIT;
					}
					if(stockOrder.getStockOrderType().getStockOrderTypeId() == 2){
						layOutPath = LayOutPageConstants.STOCK_RETURN_EDIT_PRODUCTS;
					}
					if(stockOrder.getStockOrderType().getStockOrderTypeId() == 3){
						layOutPath = LayOutPageConstants.STOCK_TRANSFER_EDIT_PRODUCTS;
					}
					if(stockOrder.getStockOrderType().getStockOrderTypeId() == 4){
						layOutPath = LayOutPageConstants.STOCK_RETURN_TO_WAREHOUSE_EDIT_PRODUCTS;
					}
					if(stockOrder.getStockOrderType().getStockOrderTypeId() == 5){
						layOutPath = LayOutPageConstants.STOCK_SUPPLIER_TRANSFER_EDIT_PRODUCTS;
					}
					return new Response(stockOrderDetailBeansList,StatusConstants.SUCCESS,layOutPath);
				}else{

					util.AuditTrail(request, currentUser, "InventoryCountDetails.addStockOrderDetail", "User "+ 
							currentUser.getUserEmail()+" Unable to add StockOrderDetail+"+ stockOrderDetailBeansList.get(0).getStockOrderDetailId(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountDetails.addStockOrder",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
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

	/*public Map getProductVariantMap() {
		return productVariantMap;
	}

	public void setProductVariantMap(Map productVariantMap) {
		this.productVariantMap = productVariantMap;
	}*/

	/*public Map<Integer, Product> getProductMap() {
		return productMap;
	}

	public void setProdutMap(Map<Integer, Product> produtMap) {
		this.productMap = produtMap;
	}*/

	public void initializeClassObjects(){
		System.out.println("Inside method initializeClassObjects of InventoryCountDetailController");
		/*purchaseOrderController = new PurchaseOrderController();
		purchaseOrderDetailsController = new PurchaseOrderDetailsController();*/
		productList = new ArrayList<>(); //outlet + Warehouse Products
		productVariantList = new ArrayList<>(); //outlet + Warehouse Product Variants
		compProductMap = new HashMap<>(); // Outlet + Warehouse Product
		productBeansSKUMap  = new HashMap<>();
		productVariantBeansSKUMap = new HashMap<>();
		warehouseProductBeansSKUMap = new HashMap<>();
		warehouseProductVariantBeansSKUMap = new HashMap<>();
		productListsWrapper = new ProductListsWrapper();
	}
	public void destroyClassObjects(){
		System.out.println("Inside method destroyClassObjects of InventoryCountDetailController");
		/*9*/
		productList = null;; //outlet + Warehouse Products
		productVariantList = null; //outlet + Warehouse Product Variants
		compProductMap = null; // Outlet + Warehouse Product
		productBeansSKUMap  = null;
		productVariantBeansSKUMap = null;
		warehouseProductBeansSKUMap = null;
		warehouseProductVariantBeansSKUMap = null;
		productListsWrapper = null;
	}
	
	
	
}
