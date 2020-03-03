package com.dowhile.angualrspringapp.controller;
import java.io.PrintWriter;
import java.io.StringWriter;
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
import com.dowhile.StockOrderDetail;
import com.dowhile.StockOrderDetailCustom;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
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

/**
 * Zafar Shakeel
 */
@Controller
@RequestMapping("/purchaseOrderActions")
public class PurchaseOrderActionsController {
	
	private static Logger logger = Logger.getLogger(PurchaseOrderActionsController.class.getName());
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
				e.printStackTrace();logger.error(e.getMessage(),e);
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
	@RequestMapping(value = "/getAllDetailsByStockOrderIdforWarehouse/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getAllDetailsByStockOrderIdforWarehouse(@PathVariable("sessionId") String sessionId,
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
				e.printStackTrace();logger.error(e.getMessage(),e);
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
				e.printStackTrace();logger.error(e.getMessage(),e);
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
				e.printStackTrace();logger.error(e.getMessage(),e);
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
}	
