package com.dowhile.angualrspringapp.controller;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
import com.dowhile.ProductVariant;
import com.dowhile.StockOrder;
import com.dowhile.StockOrderDetail;
import com.dowhile.StockOrderDetailCustom;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.frontend.mapping.bean.StockOrderBean;
import com.dowhile.frontend.mapping.bean.StockOrderDetailBean;
import com.dowhile.service.ContactService;
import com.dowhile.service.OutletService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductVariantService;
import com.dowhile.service.StatusService;
import com.dowhile.service.StockOrderDetailService;
import com.dowhile.service.StockOrderService;
import com.dowhile.service.StockOrderTypeService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;
import com.dowhile.wrapper.ProductListsWrapper;

/**
 * Zafar Shakeel
 */
@Controller
@RequestMapping("/purchaseOrderActions")
public class PurchaseOrderActionsController {
	
	// private static Logger logger = Logger.getLogger(PurchaseOrderActionsController.class.getName());
	@Resource
	private ServiceUtil util;

	@Resource
	private OutletService outletService;

	@Resource
	private ContactService supplierService;

	@Resource
	private StockOrderService stockOrderService;

	@Resource
	private StatusService statusService;

	@Resource
	private StockOrderTypeService stockOrderTypeService;

	@Resource
	private StockOrderDetailService stockOrderDetailService;	

	@Resource
	private ProductVariantService productVariantService;

	@Resource
	private ProductService productService;
	
	
	private ProductListsWrapper productListsWrapper;
	private Map productVariantMap;
	private Map productMap;
	private Map productIdsMap;
	private Map productVariantIdsMap;
	private List<Product> products; 

	@RequestMapping("/layout")
	public String getPurchaseOrderActionsControllerPartialPage(ModelMap modelMap) {
		return "purchaseOrderActions/layout";
	}

	/*	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllDetailsByStockOrderId/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllDetailsByStockOrderId(@PathVariable("sessionId") String sessionId,
			@RequestBody StockOrderBean stockOrderBean, HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			List<StockOrderDetailBean> stockOrderDetailBeansList = new ArrayList<>();
			List<StockOrderDetail> stockOrderDetailList = null;
			List<Product> allProducts = null;
			List<ProductVariant> allProductVariants = null;
			Map<Integer, Product> productsMap = new HashMap<>();
			Map<Integer, ProductVariant> productVariantsMap = new HashMap<>();
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			session.setAttribute("redirectCall", null);
			try {

				stockOrderDetailList = stockOrderDetailService.getStockOrderDetailByStockOrderId(Integer.parseInt(stockOrderBean.getStockOrderId()),currentUser.getCompany().getCompanyId());
				int order = 1;
				if (stockOrderDetailList != null) {
					List<ProductVariant> recvProductVariantList = null;
					Map recvProductVariantMap = new HashMap<>();
					List<Product> recvProductList = null;
					Map recvProductMap = new HashMap<>();
					allProducts = productService.getAllProductsInclInActive(currentUser.getCompany().getCompanyId());
					allProductVariants = productVariantService.getAllProductVariantsInclInActive(currentUser.getCompany().getCompanyId());
					//recvProductList = productService.getAllProductsByOutletId(Integer.parseInt(stockOrderBean.getOutletId()));
					if(allProducts != null){
						for(Product product:allProducts){
							productsMap.put(product.getProductId(), product);
							if(stockOrderBean.getSourceOutletId() != null && !stockOrderBean.getSourceOutletId().equalsIgnoreCase("")){
								if(product.getOutlet().getOutletId().toString().equalsIgnoreCase(stockOrderBean.getOutletId())){
									recvProductMap.put(product.getProductUuid(), product.getCurrentInventory());
								}
							}
						}
					}
					//recvProductVariantList = productVariantService.getAllProductVariantsByOutletId(Integer.parseInt(stockOrderBean.getOutletId()),currentUser.getCompany().getCompanyId());
					if(allProductVariants != null){
						for(ProductVariant productVariant:allProductVariants){
							productVariantsMap.put(productVariant.getProductVariantId(), productVariant);
							Product product = productsMap.get(productVariant.getProduct().getProductId());
							if(stockOrderBean.getSourceOutletId() != null && !stockOrderBean.getSourceOutletId().equalsIgnoreCase("")){
								if(product.getOutlet().getOutletId().toString().equalsIgnoreCase(stockOrderBean.getOutletId())){
									recvProductVariantMap.put(productVariant.getProductVariantUuid(), productVariant.getCurrentInventory());
								}
							}	
						}
					}
					for (StockOrderDetail stockOrderDetail : stockOrderDetailList) {
						StockOrderDetailBean stockOrderDetailBean = new StockOrderDetailBean();
						stockOrderDetailBean.setOrderProdQty(Objects.toString(stockOrderDetail.getOrderProdQty(), ""));
						stockOrderDetailBean.setOrdrSupplyPrice(Objects.toString(stockOrderDetail.getOrdrSupplyPrice(),""));					
						if(stockOrderDetail.getRetailPrice()!= null){
							stockOrderDetailBean.setRetailPrice(Objects.toString(stockOrderDetail.getRetailPrice(),""));
						}
						if(!stockOrderDetail.isIsProduct()){						
							ProductVariant productVariant = productVariantsMap.get(stockOrderDetail.getProductVariant().getProductVariantId());
							if(productVariant != null){
								stockOrderDetailBean.setProductVariantId(Objects.toString(productVariant.getProductVariantId(),""));
								stockOrderDetailBean.setVariantAttributeName(productsMap.get(productVariant.getProduct().getProductId()).getProductName() + "-" + Objects.toString(productVariant.getVariantAttributeName(),""));
								stockOrderDetailBean.setProductVariantCurrInventory(Objects.toString(productVariant.getCurrentInventory(),""));
								stockOrderDetailBean.setIsProduct("false");
								if(stockOrderBean.getSourceOutletId() != null && !stockOrderBean.getSourceOutletId().equalsIgnoreCase("")){
									String recvCurrInventory = String.valueOf(recvProductVariantMap.get(productVariant.getProductVariantUuid()));
									if(recvCurrInventory != null && !recvCurrInventory.equalsIgnoreCase("null")){
										stockOrderDetailBean.setProductVariantRecvOutletInventory(recvCurrInventory);
									}
									else{
										stockOrderDetailBean.setProductVariantRecvOutletInventory("0");
									}
								}
							}
						}					
						else{
							Product product = productsMap.get(stockOrderDetail.getProduct().getProductId());
							if(product!= null){
								stockOrderDetailBean.setProductVariantId(Objects.toString(product.getProductId(),""));
								stockOrderDetailBean.setVariantAttributeName(Objects.toString(product.getProductName(),""));
								stockOrderDetailBean.setProductVariantCurrInventory(Objects.toString(product.getCurrentInventory(),""));
								stockOrderDetailBean.setIsProduct("true");
								if(stockOrderBean.getSourceOutletId() != null && !stockOrderBean.getSourceOutletId().equalsIgnoreCase("")){
									String recvCurrInventory = String.valueOf(recvProductMap.get(product.getProductUuid()));
									if(recvCurrInventory != null && !recvCurrInventory.equalsIgnoreCase("null")){
										stockOrderDetailBean.setProductVariantRecvOutletInventory(recvCurrInventory);
									}
									else{
										stockOrderDetailBean.setProductVariantRecvOutletInventory("0");
									}
								}	
							}
						}
						stockOrderDetailBean.setRecvProdQty(Objects.toString(stockOrderDetail.getRecvProdQty(),""));
						stockOrderDetailBean.setRecvSupplyPrice(Objects.toString(stockOrderDetail.getRecvSupplyPrice(),""));
						stockOrderDetailBean.setStockOrderDetailId(stockOrderDetail.getStockOrderDetailId().toString());
						stockOrderDetailBean.setOrder(Integer.toString(order));
						if(stockOrderBean.getRetailPriceBill() != null && stockOrderBean.getRetailPriceBill() != ""){
							if(!stockOrderBean.getRetailPriceBill().toString().equalsIgnoreCase("true")){
								if(stockOrderDetail.getOrderProdQty() != null &&  stockOrderDetail.getOrdrSupplyPrice() != null){
									Double total = stockOrderDetail.getOrderProdQty() * stockOrderDetail.getOrdrSupplyPrice().doubleValue();
									NumberFormat formatter = new DecimalFormat("###.##");  
									String strTotal = formatter.format(total);
									stockOrderDetailBean.setTotal(strTotal);
								}
							}
							else{
								if(stockOrderDetail.getOrderProdQty() != null &&  stockOrderDetail.getRetailPrice() != null)
								{
									Double total = stockOrderDetail.getOrderProdQty() * stockOrderDetail.getRetailPrice().doubleValue();
									NumberFormat formatter = new DecimalFormat("###.##");  
									String strTotal = formatter.format(total);
									stockOrderDetailBean.setTotal(strTotal);
								}
							}					
						}
						else{
							if(stockOrderDetail.getOrderProdQty() != null &&  stockOrderDetail.getOrdrSupplyPrice() != null)
							{
								Double total = stockOrderDetail.getOrderProdQty() * stockOrderDetail.getOrdrSupplyPrice().doubleValue();
								NumberFormat formatter = new DecimalFormat("###.##");  
								String strTotal = formatter.format(total);
								stockOrderDetailBean.setTotal(strTotal);
							}
						}
						if(stockOrderDetail.getRecvProdQty() != null && stockOrderDetail.getRecvSupplyPrice() != null){//for supplier Order only
							Double recTotal = stockOrderDetail.getRecvProdQty() * stockOrderDetail.getRecvSupplyPrice().doubleValue();
							NumberFormat formatter = new DecimalFormat("###.##");  
							String strRecTotal = formatter.format(recTotal);
							stockOrderDetailBean.setRecvTotal(strRecTotal);						
						}					
						stockOrderDetailBean.setStockOrderId(stockOrderBean.getStockOrderId());
						order++;
						stockOrderDetailBeansList.add(stockOrderDetailBean);
					}
					util.AuditTrail(request, currentUser, "PurchaseOrderActionsController.getAllDetailsByStockOrderId", "User "+ 
							currentUser.getUserEmail()+" Get All Stock Order Details",false);
					return new Response(stockOrderDetailBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "PurchaseOrderActionsController.getAllDetailsByStockOrderId", "User "+ 
							currentUser.getUserEmail()+"  Get All Stock Order Details",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderActionsController.getAllDetailsByStockOrderId",
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
	@RequestMapping(value = "/getAllDetailsByStockOrderIdforWarehouse1/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllDetailsByStockOrderIdforWarehouse1(@PathVariable("sessionId") String sessionId,
			@RequestBody StockOrderBean stockOrderBean, HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			List<StockOrderDetailBean> stockOrderDetailBeansList = new ArrayList<>();
			List<StockOrderDetail> stockOrderDetailList = null;
			List<Product> allProducts = null;
			List<ProductVariant> allProductVariants = null;
			Map<Integer, Product> productsMap = new HashMap<>();
			Map<Integer, ProductVariant> productVariantsMap = new HashMap<>();
			Map<String, Product> productWarehouseMap = new HashMap<>();
			Map<String, ProductVariant> productVariantWarehouseMap = new HashMap<>();
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			session.setAttribute("redirectCall", null);
			try {

				stockOrderDetailList = stockOrderDetailService.getStockOrderDetailByStockOrderId(Integer.parseInt(stockOrderBean.getStockOrderId()),currentUser.getCompany().getCompanyId());
				int order = 1;
				if (stockOrderDetailList != null) {
					List<ProductVariant> recvProductVariantList = null;
					Map recvProductVariantMap = new HashMap<>();
					List<Product> recvProductList = null;
					Map recvProductMap = new HashMap<>();
					int warehouseId = outletService.getHeadOfficeOutlet(currentUser.getCompany().getCompanyId()).getOutletId();
					allProducts = productService.getAllProductsInclInActive(currentUser.getCompany().getCompanyId());
					allProductVariants = productVariantService.getAllProductVariantsInclInActive(currentUser.getCompany().getCompanyId());
					//recvProductList = productService.getAllProductsByOutletId(Integer.parseInt(stockOrderBean.getOutletId()));
					if(allProducts != null){
						for(Product product:allProducts){
							productsMap.put(product.getProductId(), product);
							if(stockOrderBean.getSourceOutletId() != null && !stockOrderBean.getSourceOutletId().equalsIgnoreCase("")){
								if(product.getOutlet().getOutletId().toString().equalsIgnoreCase(stockOrderBean.getOutletId())){
									recvProductMap.put(product.getProductUuid(), product.getCurrentInventory());
								}
							}
							if(product.getOutlet().getOutletId() == warehouseId){
								productWarehouseMap.put(product.getProductUuid().toString(), product);
							}
						}
					}
					//recvProductVariantList = productVariantService.getAllProductVariantsByOutletId(Integer.parseInt(stockOrderBean.getOutletId()),currentUser.getCompany().getCompanyId());
					if(allProductVariants != null){
						for(ProductVariant productVariant:allProductVariants){
							productVariantsMap.put(productVariant.getProductVariantId(), productVariant);
							Product product = productsMap.get(productVariant.getProduct().getProductId());
							if(stockOrderBean.getSourceOutletId() != null && !stockOrderBean.getSourceOutletId().equalsIgnoreCase("")){
								if(product.getOutlet().getOutletId().toString().equalsIgnoreCase(stockOrderBean.getOutletId())){
									recvProductVariantMap.put(productVariant.getProductVariantUuid(), productVariant.getCurrentInventory());
								}
							}
							if(productVariant.getOutlet().getOutletId() == warehouseId){
								productVariantWarehouseMap.put(productVariant.getProductVariantUuid().toString(), productVariant);
							}
						}
					}
					for (StockOrderDetail stockOrderDetail : stockOrderDetailList) {
						StockOrderDetailBean stockOrderDetailBean = new StockOrderDetailBean();
						stockOrderDetailBean.setOrderProdQty(Objects.toString(stockOrderDetail.getOrderProdQty(), ""));
						stockOrderDetailBean.setOrdrSupplyPrice(Objects.toString(stockOrderDetail.getOrdrSupplyPrice(),""));					
						if(stockOrderDetail.getRetailPrice()!= null){
							stockOrderDetailBean.setRetailPrice(Objects.toString(stockOrderDetail.getRetailPrice(),""));
						}
						if(!stockOrderDetail.isIsProduct()){						
							ProductVariant productVar = productVariantsMap.get(stockOrderDetail.getProductVariant().getProductVariantId());
							ProductVariant productVariant = productVariantWarehouseMap.get(productVar.getProductVariantUuid().toString());
							if(productVariant != null){
								stockOrderDetailBean.setProductVariantId(Objects.toString(productVariant.getProductVariantId(),""));
								stockOrderDetailBean.setVariantAttributeName(productsMap.get(productVariant.getProduct().getProductId()).getProductName() + "-" + Objects.toString(productVariant.getVariantAttributeName(),""));
								stockOrderDetailBean.setProductVariantCurrInventory(Objects.toString(productVariant.getCurrentInventory(),""));
								stockOrderDetailBean.setIsProduct("false");
								if(stockOrderBean.getSourceOutletId() != null && !stockOrderBean.getSourceOutletId().equalsIgnoreCase("")){
									String recvCurrInventory = String.valueOf(recvProductVariantMap.get(productVariant.getProductVariantUuid()));
									if(recvCurrInventory != null && !recvCurrInventory.equalsIgnoreCase("null")){
										stockOrderDetailBean.setProductVariantRecvOutletInventory(recvCurrInventory);
									}
									else{
										stockOrderDetailBean.setProductVariantRecvOutletInventory("0");
									}
								}
							}
						}					
						else{
							Product prod = productsMap.get(stockOrderDetail.getProduct().getProductId());
							Product product = productWarehouseMap.get(prod.getProductUuid().toString());
							if(product!= null){
								stockOrderDetailBean.setProductVariantId(Objects.toString(product.getProductId(),""));
								stockOrderDetailBean.setVariantAttributeName(Objects.toString(product.getProductName(),""));
								stockOrderDetailBean.setProductVariantCurrInventory(Objects.toString(product.getCurrentInventory(),""));
								stockOrderDetailBean.setIsProduct("true");
								if(stockOrderBean.getSourceOutletId() != null && !stockOrderBean.getSourceOutletId().equalsIgnoreCase("")){
									String recvCurrInventory = String.valueOf(recvProductMap.get(product.getProductUuid()));
									if(recvCurrInventory != null && !recvCurrInventory.equalsIgnoreCase("null")){
										stockOrderDetailBean.setProductVariantRecvOutletInventory(recvCurrInventory);
									}
									else{
										stockOrderDetailBean.setProductVariantRecvOutletInventory("0");
									}
								}	
							}
						}
						stockOrderDetailBean.setRecvProdQty(Objects.toString(stockOrderDetail.getRecvProdQty(),""));
						stockOrderDetailBean.setRecvSupplyPrice(Objects.toString(stockOrderDetail.getRecvSupplyPrice(),""));
						stockOrderDetailBean.setStockOrderDetailId(stockOrderDetail.getStockOrderDetailId().toString());
						stockOrderDetailBean.setOrder(Integer.toString(order));
						if(stockOrderBean.getRetailPriceBill() != null && stockOrderBean.getRetailPriceBill() != ""){
							if(!stockOrderBean.getRetailPriceBill().toString().equalsIgnoreCase("true")){
								if(stockOrderDetail.getOrderProdQty() != null &&  stockOrderDetail.getOrdrSupplyPrice() != null){
									Double total = stockOrderDetail.getOrderProdQty() * stockOrderDetail.getOrdrSupplyPrice().doubleValue();
									NumberFormat formatter = new DecimalFormat("###.##");  
									String strTotal = formatter.format(total);
									stockOrderDetailBean.setTotal(strTotal);
								}
							}
							else{
								if(stockOrderDetail.getOrderProdQty() != null &&  stockOrderDetail.getRetailPrice() != null)
								{
									Double total = stockOrderDetail.getOrderProdQty() * stockOrderDetail.getRetailPrice().doubleValue();
									NumberFormat formatter = new DecimalFormat("###.##");  
									String strTotal = formatter.format(total);
									stockOrderDetailBean.setTotal(strTotal);
								}
							}					
						}
						else{
							if(stockOrderDetail.getOrderProdQty() != null &&  stockOrderDetail.getOrdrSupplyPrice() != null)
							{
								Double total = stockOrderDetail.getOrderProdQty() * stockOrderDetail.getOrdrSupplyPrice().doubleValue();
								NumberFormat formatter = new DecimalFormat("###.##");  
								String strTotal = formatter.format(total);
								stockOrderDetailBean.setTotal(strTotal);
							}
						}
						if(stockOrderDetail.getRecvProdQty() != null && stockOrderDetail.getRecvSupplyPrice() != null){//for supplier Order only
							Double recTotal = stockOrderDetail.getRecvProdQty() * stockOrderDetail.getRecvSupplyPrice().doubleValue();
							NumberFormat formatter = new DecimalFormat("###.##");  
							String strRecTotal = formatter.format(recTotal);
							stockOrderDetailBean.setRecvTotal(strRecTotal);						
						}					
						stockOrderDetailBean.setStockOrderId(stockOrderBean.getStockOrderId());
						order++;
						stockOrderDetailBeansList.add(stockOrderDetailBean);
					}
					util.AuditTrail(request, currentUser, "PurchaseOrderActionsController.getAllDetailsByStockOrderId", "User "+ 
							currentUser.getUserEmail()+" Get All Stock Order Details",false);
					return new Response(stockOrderDetailBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "PurchaseOrderActionsController.getAllDetailsByStockOrderId", "User "+ 
							currentUser.getUserEmail()+"  Get All Stock Order Details",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderActionsController.getAllDetailsByStockOrderId",
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
	@RequestMapping(value = "/getAllDetailsByStockOrderId/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllDetailsByStockOrderId(@PathVariable("sessionId") String sessionId,
			@RequestBody StockOrderBean stockOrderBean, HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			List<StockOrderDetailBean> stockOrderDetailBeansList = new ArrayList<>();
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			List<StockOrderDetailCustom> stockOrderDetailCustomList = new ArrayList<>();
			try {
				stockOrderDetailCustomList = stockOrderDetailService.getStockOrderDetailCustom(Integer.parseInt(stockOrderBean.getStockOrderId()), Integer.parseInt(stockOrderBean.getOutletId()));				
				int order = 1;
				if (stockOrderDetailCustomList != null) {
					for (StockOrderDetailCustom stockOrderDetailCustom : stockOrderDetailCustomList) {
						StockOrderDetailBean stockOrderDetailBean = new StockOrderDetailBean();
						stockOrderDetailBean.setOrderProdQty(Objects.toString(stockOrderDetailCustom.getORDER_PROD_QTY(), ""));
						stockOrderDetailBean.setOrdrSupplyPrice(Objects.toString(stockOrderDetailCustom.getORDR_SUPPLY_PRICE(),""));
						if(stockOrderDetailCustom.getRETAIL_PRICE() != null){
							stockOrderDetailBean.setRetailPrice(Objects.toString(stockOrderDetailCustom.getRETAIL_PRICE(),""));
						}
						if(!stockOrderDetailCustom.isIS_PRODUCT()){						
							stockOrderDetailBean.setProductVariantId(Objects.toString(stockOrderDetailCustom.getPRODUCT_VARIANT_ASSOCICATION_ID(),""));
							stockOrderDetailBean.setVariantAttributeName(Objects.toString(stockOrderDetailCustom.getVariantAttributeName(),""));
							stockOrderDetailBean.setProductVariantCurrInventory(Objects.toString(stockOrderDetailCustom.getProduct_variant_current_inventory(),""));
							stockOrderDetailBean.setIsProduct("false");
							stockOrderDetailBean.setProductVariantRecvOutletInventory(Objects.toString(stockOrderDetailCustom.getProduct_variant_destination_inventory(),""));

						}					
						else{
							stockOrderDetailBean.setProductVariantId(Objects.toString(stockOrderDetailCustom.getPRODUCT_ASSOCIATION_ID(),""));
							stockOrderDetailBean.setVariantAttributeName(Objects.toString(stockOrderDetailCustom.getProduct_name(),""));
							stockOrderDetailBean.setProductVariantCurrInventory(Objects.toString(stockOrderDetailCustom.getProduct_current_inventory(),""));
							stockOrderDetailBean.setIsProduct("true");
							stockOrderDetailBean.setProductVariantRecvOutletInventory(Objects.toString(stockOrderDetailCustom.getProduct_destination_current_inventory(),""));

						}
						stockOrderDetailBean.setRecvProdQty(Objects.toString(stockOrderDetailCustom.getRECV_PROD_QTY(),""));
						stockOrderDetailBean.setRecvSupplyPrice(Objects.toString(stockOrderDetailCustom.getRECV_SUPPLY_PRICE(),""));
						stockOrderDetailBean.setStockOrderDetailId(Objects.toString(stockOrderDetailCustom.getSTOCK_ORDER_DETAIL_ID(),""));
						stockOrderDetailBean.setOrder(Integer.toString(order));
						if(stockOrderBean.getRetailPriceBill() != null && stockOrderBean.getRetailPriceBill() != ""){
							if(!stockOrderBean.getRetailPriceBill().toString().equalsIgnoreCase("true")){
								if(stockOrderDetailCustom.getORDER_PROD_QTY() != null &&  stockOrderDetailCustom.getORDR_SUPPLY_PRICE() != null)
								{
									Double total = stockOrderDetailCustom.getORDER_PROD_QTY() * stockOrderDetailCustom.getORDR_SUPPLY_PRICE().doubleValue();
									NumberFormat formatter = new DecimalFormat("###.##");  
									String strTotal = formatter.format(total);
									stockOrderDetailBean.setTotal(strTotal);
								}
							}
							else{
								if(stockOrderDetailCustom.getORDER_PROD_QTY() != null &&  stockOrderDetailCustom.getRETAIL_PRICE() != null)
								{
									Double total = stockOrderDetailCustom.getORDER_PROD_QTY() * stockOrderDetailCustom.getRETAIL_PRICE().doubleValue();
									NumberFormat formatter = new DecimalFormat("###.##");  
									String strTotal = formatter.format(total);
									stockOrderDetailBean.setTotal(strTotal);
								}
							}
						}
						else{
							if(stockOrderDetailCustom.getORDER_PROD_QTY() != null &&  stockOrderDetailCustom.getORDR_SUPPLY_PRICE() != null)
							{
								Double total = stockOrderDetailCustom.getORDER_PROD_QTY() * stockOrderDetailCustom.getORDR_SUPPLY_PRICE().doubleValue();
								NumberFormat formatter = new DecimalFormat("###.##");  
								String strTotal = formatter.format(total);
								stockOrderDetailBean.setTotal(strTotal);
							}
						}
						if(stockOrderDetailCustom.getRECV_PROD_QTY() != null && stockOrderDetailCustom.getRECV_SUPPLY_PRICE() != null){
							Double recTotal = stockOrderDetailCustom.getRECV_PROD_QTY() * stockOrderDetailCustom.getRECV_SUPPLY_PRICE().doubleValue();
							NumberFormat formatter = new DecimalFormat("###.##");  
							String strRecTotal = formatter.format(recTotal);
							stockOrderDetailBean.setRecvTotal(strRecTotal);
						}					
						stockOrderDetailBean.setStockOrderId(stockOrderBean.getStockOrderId());
						order++;
						stockOrderDetailBeansList.add(stockOrderDetailBean);
					}
					util.AuditTrail(request, currentUser, "PurchaseOrderActionsController.getAllDetailsByStockOrderId", "User "+ 
							currentUser.getUserEmail()+" Get All Stock Order Details",false);
					return new Response(stockOrderDetailBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "PurchaseOrderActionsController.getAllDetailsByStockOrderId", "User "+ 
							currentUser.getUserEmail()+"  Get All Stock Order Details",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderActionsController.getAllDetailsByStockOrderId",
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
	@RequestMapping(value = "/getAllDetailsByStockOrderIdforWarehouse/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllDetailsByStockOrderIdforWarehouse(@PathVariable("sessionId") String sessionId,
			@RequestBody StockOrderBean stockOrderBean, HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			List<StockOrderDetailBean> stockOrderDetailBeansList = new ArrayList<>();
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			List<StockOrderDetailCustom> stockOrderDetailCustomList = new ArrayList<>();
			Map<String, ProductVariant> recvProductVariantList = new HashMap();
			Map<String, Product> recvProductList = new HashMap();
			Map<String, ProductVariant> wareHouseProductVariantList = new HashMap();
			Map<String, Product> wareHouseProductList = new HashMap();
			try {
				StockOrder stockOrder = stockOrderService.getStockOrderByStockOrderID(Integer.parseInt(stockOrderBean.getStockOrderId()), currentUser.getCompany().getCompanyId());
				productListsWrapper = productService.getAllProductsWarehouseandOutlet(stockOrder.getOutletByOutletAssocicationId().getOutletId(), stockOrder.getOutletBySourceOutletAssocicationId().getOutletId(), currentUser.getCompany().getCompanyId());						
				Map<Integer, Product> productsMap = new HashMap<>();
				List<Product> products = productListsWrapper.getWarehouseProducts(); //productService.getAllProducts(currentUser.getCompany().getCompanyId());
				products.addAll(productListsWrapper.getOutletProducts());
				//List<StockOrderDetail> stockOrderDetailsUpdateList = new ArrayList<>();
				//List<StockOrderDetail> stockOrderDetailsDeleteList = new ArrayList<>();
				//List<StockOrderDetail> stockOrderDetailsAddList = new ArrayList<>();
				List<Product> productUpdateList = new ArrayList<>();
				List<ProductVariant> productVariantUpdateList = new ArrayList<>();
				if(products!=null){
					for(Product product:products){
						productsMap.put(product.getProductId(), product);
						if(product.getOutlet().getOutletId() == stockOrder.getOutletBySourceOutletAssocicationId().getOutletId()){
							recvProductList.put(product.getProductUuid(), product);
						}else
						{
							wareHouseProductList.put(product.getProductUuid(), product);
						}
					}
				}
				Map<Integer, ProductVariant> productVariantsMap = new HashMap<>();
				//Map<Integer, ProductVariant> 
				List<ProductVariant> productVariants = productListsWrapper.getWarehouseProductVariants(); //productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
				productVariants.addAll(productListsWrapper.getOutletProductVariants());
				if(productVariants!=null){
					for(ProductVariant productVariant:productVariants){
						productVariantsMap.put(productVariant.getProductVariantId(), productVariant);
						if(productVariant.getOutlet().getOutletId() == stockOrder.getOutletBySourceOutletAssocicationId().getOutletId()){
							recvProductVariantList.put(productVariant.getProductVariantUuid(), productVariant);
						}else
						{
							wareHouseProductVariantList.put(productVariant.getProductVariantUuid(), productVariant);
						}
					}
				}
				stockOrderDetailCustomList = stockOrderDetailService.getStockOrderDetailCustom(Integer.parseInt(stockOrderBean.getStockOrderId()), Integer.parseInt(stockOrderBean.getOutletId()));				
				int order = 1;
				if (stockOrderDetailCustomList != null) {
					for (StockOrderDetailCustom stockOrderDetailCustom : stockOrderDetailCustomList) {
						StockOrderDetailBean stockOrderDetailBean = new StockOrderDetailBean();
						stockOrderDetailBean.setOrderProdQty(Objects.toString(stockOrderDetailCustom.getORDER_PROD_QTY(), ""));
						stockOrderDetailBean.setOrdrSupplyPrice(Objects.toString(stockOrderDetailCustom.getORDR_SUPPLY_PRICE(),""));
						if(stockOrderDetailCustom.getRETAIL_PRICE() != null){
							stockOrderDetailBean.setRetailPrice(Objects.toString(stockOrderDetailCustom.getRETAIL_PRICE(),""));
						}
						if(!stockOrderDetailCustom.isIS_PRODUCT()){
							ProductVariant productVariant = productVariantsMap.get(stockOrderDetailCustom.getPRODUCT_VARIANT_ASSOCICATION_ID());
							ProductVariant productVariantw = wareHouseProductVariantList.get(productVariant.getProductVariantUuid());
							//ProductVariant productVarianto = recvProductVariantList.get(productVariant.getProductUuid());							
							stockOrderDetailBean.setProductVariantId(Objects.toString(productVariantw.getProductVariantId(),""));
							stockOrderDetailBean.setVariantAttributeName(Objects.toString(stockOrderDetailCustom.getVariantAttributeName(),""));
							stockOrderDetailBean.setProductVariantCurrInventory(Objects.toString(productVariantw.getCurrentInventory(),""));
							stockOrderDetailBean.setIsProduct("false");
							stockOrderDetailBean.setProductVariantRecvOutletInventory(Objects.toString(stockOrderDetailCustom.getProduct_variant_destination_inventory(),""));

						}					
						else{
							Product product = productsMap.get(stockOrderDetailCustom.getPRODUCT_VARIANT_ASSOCICATION_ID());
							Product productw = wareHouseProductList.get(product.getProductUuid());
							//Product producto = recvProductList.get(product.getProductUuid());
							stockOrderDetailBean.setProductVariantId(Objects.toString(productw.getProductId(),""));
							stockOrderDetailBean.setVariantAttributeName(Objects.toString(stockOrderDetailCustom.getProduct_name(),""));
							stockOrderDetailBean.setIsProduct("true");
							stockOrderDetailBean.setProductVariantRecvOutletInventory(Objects.toString(stockOrderDetailCustom.getProduct_destination_current_inventory(),""));

						}
						stockOrderDetailBean.setRecvProdQty(Objects.toString(stockOrderDetailCustom.getRECV_PROD_QTY(),""));
						stockOrderDetailBean.setRecvSupplyPrice(Objects.toString(stockOrderDetailCustom.getRECV_SUPPLY_PRICE(),""));
						stockOrderDetailBean.setStockOrderDetailId(Objects.toString(stockOrderDetailCustom.getSTOCK_ORDER_DETAIL_ID(),""));
						stockOrderDetailBean.setOrder(Integer.toString(order));
						if(stockOrderBean.getRetailPriceBill() != null && stockOrderBean.getRetailPriceBill() != ""){
							if(!stockOrderBean.getRetailPriceBill().toString().equalsIgnoreCase("true")){
								if(stockOrderDetailCustom.getORDER_PROD_QTY() != null &&  stockOrderDetailCustom.getORDR_SUPPLY_PRICE() != null)
								{
									Double total = stockOrderDetailCustom.getORDER_PROD_QTY() * stockOrderDetailCustom.getORDR_SUPPLY_PRICE().doubleValue();
									NumberFormat formatter = new DecimalFormat("###.##");  
									String strTotal = formatter.format(total);
									stockOrderDetailBean.setTotal(strTotal);
								}
							}
							else{
								if(stockOrderDetailCustom.getORDER_PROD_QTY() != null &&  stockOrderDetailCustom.getRETAIL_PRICE() != null)
								{
									Double total = stockOrderDetailCustom.getORDER_PROD_QTY() * stockOrderDetailCustom.getRETAIL_PRICE().doubleValue();
									NumberFormat formatter = new DecimalFormat("###.##");  
									String strTotal = formatter.format(total);
									stockOrderDetailBean.setTotal(strTotal);
								}
							}
						}
						else{
							if(stockOrderDetailCustom.getORDER_PROD_QTY() != null &&  stockOrderDetailCustom.getORDR_SUPPLY_PRICE() != null)
							{
								Double total = stockOrderDetailCustom.getORDER_PROD_QTY() * stockOrderDetailCustom.getORDR_SUPPLY_PRICE().doubleValue();
								NumberFormat formatter = new DecimalFormat("###.##");  
								String strTotal = formatter.format(total);
								stockOrderDetailBean.setTotal(strTotal);
							}
						}
						if(stockOrderDetailCustom.getRECV_PROD_QTY() != null && stockOrderDetailCustom.getRECV_SUPPLY_PRICE() != null){
							Double recTotal = stockOrderDetailCustom.getRECV_PROD_QTY() * stockOrderDetailCustom.getRECV_SUPPLY_PRICE().doubleValue();
							NumberFormat formatter = new DecimalFormat("###.##");  
							String strRecTotal = formatter.format(recTotal);
							stockOrderDetailBean.setRecvTotal(strRecTotal);
						}					
						stockOrderDetailBean.setStockOrderId(stockOrderBean.getStockOrderId());
						order++;
						stockOrderDetailBeansList.add(stockOrderDetailBean);
					}
					util.AuditTrail(request, currentUser, "PurchaseOrderActionsController.getAllDetailsByStockOrderId", "User "+ 
							currentUser.getUserEmail()+" Get All Stock Order Details",false);
					return new Response(stockOrderDetailBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "PurchaseOrderActionsController.getAllDetailsByStockOrderId", "User "+ 
							currentUser.getUserEmail()+"  Get All Stock Order Details",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderActionsController.getAllDetailsByStockOrderId",
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
	
/*	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllDetailsByStockOrderIdforWarehouse/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllDetailsByStockOrderIdforWarehouse(@PathVariable("sessionId") String sessionId,
			@RequestBody StockOrderBean stockOrderBean, HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			List<StockOrderDetailBean> stockOrderDetailBeansList = new ArrayList<>();
			HttpSession session = request.getSession(false);
			List<StockOrderDetailCustom> stockOrderDetailCustomList = new ArrayList<>();
			User currentUser = (User) session.getAttribute("user");
			try {
				stockOrderDetailCustomList = stockOrderDetailService.getStockOrderDetailCustom(Integer.parseInt(stockOrderBean.getStockOrderId()), Integer.parseInt(stockOrderBean.getOutletId()));				
				int order = 1;
				if (stockOrderDetailCustomList != null) {
					for (StockOrderDetailCustom stockOrderDetailCustom : stockOrderDetailCustomList) {
						StockOrderDetailBean stockOrderDetailBean = new StockOrderDetailBean();
						stockOrderDetailBean.setOrderProdQty(Objects.toString(stockOrderDetailCustom.getORDER_PROD_QTY(), ""));
						stockOrderDetailBean.setOrdrSupplyPrice(Objects.toString(stockOrderDetailCustom.getORDR_SUPPLY_PRICE(),""));
						if(stockOrderDetailCustom.getRETAIL_PRICE() != null){
							stockOrderDetailBean.setRetailPrice(Objects.toString(stockOrderDetailCustom.getRETAIL_PRICE(),""));
						}
						if(!stockOrderDetailCustom.isIS_PRODUCT()){						
							stockOrderDetailBean.setProductVariantId(Objects.toString(stockOrderDetailCustom.getPRODUCT_VARIANT_ASSOCICATION_ID(),""));
							stockOrderDetailBean.setVariantAttributeName(Objects.toString(stockOrderDetailCustom.getVariantAttributeName(),""));
							stockOrderDetailBean.setProductVariantCurrInventory(Objects.toString(stockOrderDetailCustom.getProduct_variant_current_inventory(),""));
							stockOrderDetailBean.setIsProduct("false");
							stockOrderDetailBean.setProductVariantRecvOutletInventory(Objects.toString(stockOrderDetailCustom.getProduct_variant_destination_inventory(),""));

						}					
						else{
							stockOrderDetailBean.setProductVariantId(Objects.toString(stockOrderDetailCustom.getPRODUCT_ASSOCIATION_ID(),""));
							stockOrderDetailBean.setVariantAttributeName(Objects.toString(stockOrderDetailCustom.getProduct_name(),""));
							stockOrderDetailBean.setProductVariantCurrInventory(Objects.toString(stockOrderDetailCustom.getProduct_current_inventory(),""));
							stockOrderDetailBean.setIsProduct("true");
							stockOrderDetailBean.setProductVariantRecvOutletInventory(Objects.toString(stockOrderDetailCustom.getProduct_destination_current_inventory(),""));

						}
						stockOrderDetailBean.setRecvProdQty(Objects.toString(stockOrderDetailCustom.getRECV_PROD_QTY(),""));
						stockOrderDetailBean.setRecvSupplyPrice(Objects.toString(stockOrderDetailCustom.getRECV_SUPPLY_PRICE(),""));
						stockOrderDetailBean.setStockOrderDetailId(Objects.toString(stockOrderDetailCustom.getSTOCK_ORDER_DETAIL_ID(),""));
						stockOrderDetailBean.setOrder(Integer.toString(order));
						if(stockOrderBean.getRetailPriceBill() != null && stockOrderBean.getRetailPriceBill() != ""){
							if(!stockOrderBean.getRetailPriceBill().toString().equalsIgnoreCase("true")){
								if(stockOrderDetailCustom.getORDER_PROD_QTY() != null &&  stockOrderDetailCustom.getORDR_SUPPLY_PRICE() != null)
								{
									Double total = stockOrderDetailCustom.getORDER_PROD_QTY() * stockOrderDetailCustom.getORDR_SUPPLY_PRICE().doubleValue();
									NumberFormat formatter = new DecimalFormat("###.##");  
									String strTotal = formatter.format(total);
									stockOrderDetailBean.setTotal(strTotal);
								}
							}
							else{
								if(stockOrderDetailCustom.getORDER_PROD_QTY() != null &&  stockOrderDetailCustom.getRETAIL_PRICE() != null)
								{
									Double total = stockOrderDetailCustom.getORDER_PROD_QTY() * stockOrderDetailCustom.getRETAIL_PRICE().doubleValue();
									NumberFormat formatter = new DecimalFormat("###.##");  
									String strTotal = formatter.format(total);
									stockOrderDetailBean.setTotal(strTotal);
								}
							}
						}
						else{
							if(stockOrderDetailCustom.getORDER_PROD_QTY() != null &&  stockOrderDetailCustom.getORDR_SUPPLY_PRICE() != null)
							{
								Double total = stockOrderDetailCustom.getORDER_PROD_QTY() * stockOrderDetailCustom.getORDR_SUPPLY_PRICE().doubleValue();
								NumberFormat formatter = new DecimalFormat("###.##");  
								String strTotal = formatter.format(total);
								stockOrderDetailBean.setTotal(strTotal);
							}
						}
						if(stockOrderDetailCustom.getRECV_PROD_QTY() != null && stockOrderDetailCustom.getRECV_SUPPLY_PRICE() != null){
							Double recTotal = stockOrderDetailCustom.getRECV_PROD_QTY() * stockOrderDetailCustom.getRECV_SUPPLY_PRICE().doubleValue();
							NumberFormat formatter = new DecimalFormat("###.##");  
							String strRecTotal = formatter.format(recTotal);
							stockOrderDetailBean.setRecvTotal(strRecTotal);
						}					
						stockOrderDetailBean.setStockOrderId(stockOrderBean.getStockOrderId());
						order++;
						stockOrderDetailBeansList.add(stockOrderDetailBean);
					}
					util.AuditTrail(request, currentUser, "PurchaseOrderActionsController.getAllDetailsByStockOrderId", "User "+ 
							currentUser.getUserEmail()+" Get All Stock Order Details",false);
					return new Response(stockOrderDetailBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "PurchaseOrderActionsController.getAllDetailsByStockOrderId", "User "+ 
							currentUser.getUserEmail()+"  Get All Stock Order Details",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "PurchaseOrderActionsController.getAllDetailsByStockOrderId",
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
	@RequestMapping(value = "/getAllProducts/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllProducts(@PathVariable("sessionId") String sessionId, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			//productMap = new HashMap<>();
			try {		
				/*if(session.getAttribute("redirectCall") != null && session.getAttribute("redirectCall") == "1") {
					if(session.getAttribute("productIdsMap") != null) {
						productSessionMap = (HashMap<Integer, Product>)session.getAttribute("productIdsMap");
						products = new ArrayList<Product>(productSessionMap.values());
					}
					else {
						products = productService.getAllProductsByCompanyIdGroupByProductUuId(currentUser.getCompany().getCompanyId());
					}
				}
				else {
					products = productService.getAllProductsByCompanyIdGroupByProductUuId(currentUser.getCompany().getCompanyId());
				}	*/	
				if(productListsWrapper.getOutletProducts() != null) {
					products = productListsWrapper.getOutletProducts();	
				}				
				if(products != null){
					for(Product product:products){
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
							productMap.put(product.getSku().toLowerCase(), productVariantBean);
						}
						productIdsMap.put(product.getProductId(), product);
					}
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.getAllProducts", "User "+ 
							currentUser.getUserEmail()+" Get Products and Products",false);
					return new Response(productVariantBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} 
				else {
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.getAllProducts", "User "+ 
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
				util.AuditTrail(request, currentUser, "PurchaseOrderController.getAllProducts",
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
	@RequestMapping(value = "/getProductVariants/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getProductVariants(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			List<ProductVariantBean> productVariantBeansList = new ArrayList<>();
			List<ProductVariant> productVariantList = null;
			//productVariantMap = new HashMap<>();
			try {
				/*if(session.getAttribute("redirectCall") != null && session.getAttribute("redirectCall") == "1") {
					if(session.getAttribute("productVariantIdsMap") != null) {
						productVariantSessionMap  = (HashMap<Integer, ProductVariant>)session.getAttribute("productVariantIdsMap");
						productVariantList = new ArrayList<ProductVariant>(productVariantSessionMap.values());
					}
					else {
						productVariantList = productVariantService.getAllProductVariantsGroupbyUuid(currentUser.getCompany().getCompanyId());
					}
				}
				else {
					productVariantList = productVariantService.getAllProductVariantsGroupbyUuid(currentUser.getCompany().getCompanyId());
				}*/
				if(productListsWrapper.getOutletProductVariants() != null) {
					productVariantList = productListsWrapper.getOutletProductVariants();
				}
				Map<Integer, Product> productsMap = new HashMap<>();
				/*if(products.isEmpty()) {
					if(session.getAttribute("redirectCall") != null && session.getAttribute("redirectCall") == "1") {
						if(session.getAttribute("productIdsMap") != null) {
							productsMap = (HashMap<Integer, Product>)session.getAttribute("productIdsMap");
							products = new ArrayList<Product>(productsMap.values());
						}
						else {
							products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
						}
					}
					else {
						products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
					}
				}		*/
				if(productListsWrapper.getOutletProducts() != null) {
					products = productListsWrapper.getOutletProducts();
				}
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
						productVariantMap.put(productVariant.getSku().toLowerCase(), productVariantBean);
						productVariantIdsMap.put(productVariant.getProductVariantId(), productVariant);
					}
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.getProductVariants", "User "+ 
							currentUser.getUserEmail()+" Get ProductVariants",false);
					return new Response(productVariantBeansList, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} 
				else {
					util.AuditTrail(request, currentUser, "PurchaseOrderDetails.getProductVariants", "User "+ 
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
				util.AuditTrail(request, currentUser, "PurchaseOrderController.getProductVariants",
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
	
}	
