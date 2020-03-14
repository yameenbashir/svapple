/**
 * 
 */
package com.dowhile.angualrspringapp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Brand;
import com.dowhile.CompositeProduct;
import com.dowhile.Configuration;
import com.dowhile.Contact;
import com.dowhile.Outlet;
import com.dowhile.Product;
import com.dowhile.ProductPriceHistory;
import com.dowhile.ProductType;
import com.dowhile.ProductVariant;
import com.dowhile.SalesTax;
import com.dowhile.StockOrder;
import com.dowhile.StockOrderDetail;
import com.dowhile.User;
import com.dowhile.VariantAttribute;
import com.dowhile.VariantAttributeValues;
import com.dowhile.angualrspringapp.beans.ImageData;
import com.dowhile.constant.Actions;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.CompositVariantBean;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.ProductBean;
import com.dowhile.frontend.mapping.bean.ProductPriceHistoryBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.frontend.mapping.bean.StockOrderBean;
import com.dowhile.frontend.mapping.bean.StockOrderDetailBean;
import com.dowhile.frontend.mapping.bean.VarientAttributeValueBean;
import com.dowhile.frontend.mapping.bean.VarientValueBean;
import com.dowhile.service.BrandService;
import com.dowhile.service.CompositeProductService;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.ContactService;
import com.dowhile.service.OutletService;
import com.dowhile.service.ProductPriceHistoryService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductTagService;
import com.dowhile.service.ProductTypeService;
import com.dowhile.service.ProductVariantService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.SalesTaxService;
import com.dowhile.service.StockOrderDetailService;
import com.dowhile.service.TagService;
import com.dowhile.service.VariantAttributeService;
import com.dowhile.service.VariantAttributeValuesService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * @author Yameen Bashir
 *
 */
@Controller
@RequestMapping("/manageCompositeProduct")
public class ManageCompositeProductController {

	// private static Logger logger = Logger.getLogger(ManageCompositeProductController.class.getName());
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
	@Resource
	private BrandService brandService;
	@Resource
	private OutletService outletService;
	@Resource
	private ProductTypeService productTypeService;
	@Resource
	private SalesTaxService salesTaxService;
	@Resource
	private VariantAttributeService variantAttributeService;
	@Resource
	private ProductVariantService productVariantService;
	@Resource
	private CompositeProductService compositeProductService;
	@Resource
	private VariantAttributeValuesService variantAttributeValuesService;
	@Resource
	private ContactService supplierService;
	@Resource
	private ConfigurationService configurationService;
	@Resource
	private ProductPriceHistoryService productPriceHistoryService;
	@Resource
	private StockOrderDetailService stockOrderDetailService;
	//Added by Zafar for auto StockOrder
	@Autowired
	private PurchaseOrderController purchaseOrderController;
	@Autowired
	PurchaseOrderDetailsController purchaseOrderDetailsController;
	private Configuration configurationStockOrder;
	private Configuration configurationReturnStock;
	private List<StockOrderDetailBean> stockOrderDetialBeansList;
	private Double grandTotal;
	private Double itemCount;
	private List<StockOrderDetailBean> stockReturnDetialBeansList;
	private Double grandTotalReturn;
	private Double itemCountReturn;
	private int headOfficeId;
	//finish

	@RequestMapping("/layout")
	public String getManageProductControllerPartialPage(ModelMap modelMap) {
		return "manageProduct/layout";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/updateCompositeProduct/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response updateCompositeProduct(@PathVariable("sessionId") String sessionId,
			@RequestBody ProductBean productBean, HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			List<Product> productList = null;
			try{
				//variants should only be added 1 time for warehouse
				boolean variantsAddedForWarehouse = false;
				//added by Zafar for autoStockOrder
				stockOrderDetialBeansList = new ArrayList<>();
				grandTotal = 0.0;
				itemCount = 0.0;
				stockReturnDetialBeansList = new ArrayList<>();
				grandTotalReturn = 0.0;
				itemCountReturn = 0.0;
				headOfficeId = 0;			
				configurationStockOrder = configurationService.getConfigurationByPropertyNameByCompanyId("AUTO_STOCK_ORDER_NEW_PRODUCT",currentUser.getCompany().getCompanyId());
				configurationReturnStock = configurationService.getConfigurationByPropertyNameByCompanyId("AUTO_STOCK_RETURN_MANAGE_PRODUCT",currentUser.getCompany().getCompanyId());
				//Finish 
				
				//To check duplicate bar code start
				Map productBarCodeMap = new HashMap<>();
				Map productMap = new HashMap<>();
				List<Product>products = productService.getAllProducts(currentUser.getCompany().getCompanyId());
				if(products!=null){
					for(Product product:products){
						productBarCodeMap.put(product.getSku(), product);
						productMap.put(product.getProductId(), product);
					}
				}
				Product duplicateProduct = (Product) productBarCodeMap.get(productBean.getSku());
				if(productBean.getSku()==null ||productBean.getSku().equalsIgnoreCase("")
						||(
								duplicateProduct.getSku()!=null && duplicateProduct.getProductId()!=Integer.valueOf(productBean.getProductId())
								&& !duplicateProduct.getProductUuid().equalsIgnoreCase(productBean.getProductUuid()))){
					return new Response(MessageConstants.DUPLICATE_PRODCUT_BAR_CODE,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
				}
				
				List<ProductVariant> productVariantsList = productVariantService.getAllProductVariants(currentUser.getCompany().getCompanyId());
				Map productVariantMap = new HashMap<>();
				Map productvariantMap = new HashMap<>();
				if(productVariantsList!=null){
					
					for(ProductVariant productVariant:productVariantsList){
						productVariantMap.put(productVariant.getSku(), productVariant);
						productvariantMap.put(productVariant.getProductVariantId(), productVariant);
					}
				}
				
				if(productBean.getStandardProduct().equalsIgnoreCase("true")){
					//Standard scenario
					if(productBean.getVarientProducts().equalsIgnoreCase("true")){
						//Variants scenario
						List<VarientValueBean> varientValuesBeanCollection = productBean.getProductVariantValuesCollection();
						for(VarientValueBean varientValueBean:varientValuesBeanCollection){
							List<OutletBean> variantOutletBeanList = varientValueBean.getVarientsOutletList();
							if(variantOutletBeanList!=null){
								for(OutletBean variantOutletBean:variantOutletBeanList){
									variantOutletBean.getSku();
									if(variantOutletBean.getSku()==null ||variantOutletBean.getSku().equalsIgnoreCase("")
											||productVariantMap.get(variantOutletBean.getSku())!=null){
										return new Response(MessageConstants.DUPLICATE_PRODCUT_VARIANT_BAR_CODE,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
									}
								}
							}
							
						}
					}
				}
				
				//To check duplicate bar code end
				
				int totalQunatity = 0;
				boolean isCcreateSelfProcessOrder = false;
				productList = productService.getAllProductsByUuid(productBean.getProductUuid(),currentUser.getCompany().getCompanyId());
				if(productList!=null){
					List<Outlet> outlets = outletService.getOutlets(currentUser.getCompany().getCompanyId());
					Map outletMap = new HashMap<>();
					for(Outlet outlet:outlets){
						outletMap.put(outlet.getOutletId(), outlet);
						if(outlet.getIsHeadOffice()!=null && outlet.getIsHeadOffice().toString()=="true"){
							headOfficeId = outlet.getOutletId();
						}
					}
					//finish
					for(Product product:productList){
						Brand brand = brandService.getBrandByBrandId(Integer.valueOf(productBean.getBrandId()),currentUser.getCompany().getCompanyId());
						product.setBrand(brand);
						product.setUserByUpdatedBy(currentUser);
						product.setLastUpdated(new Date());
						product.setProductDesc(productBean.getProductDesc());
						product.setProductHandler(productBean.getProductHandler());
						product.setProductName(productBean.getProductName());
						ProductType productType = productTypeService.getProductTypeByProductTypeId(Integer.valueOf(productBean.getProductTypeId()),currentUser.getCompany().getCompanyId());
						product.setProductType(productType);
						product.setPurchaseAccountCode(productBean.getPurchaseAccountCode());
						product.setSalesAccountCode(productBean.getSalesAccountCode());
						Contact supplier = supplierService.getContactByID(Integer.valueOf(productBean.getSupplierId()),currentUser.getCompany().getCompanyId());
						product.setContact(supplier);
						product.setProductCanBeSold(productBean.getProductCanBeSold());
						product.setStandardProduct(productBean.getStandardProduct());
						product.setTrackingProduct(productBean.getTrackingProduct());
						product.setVariantProducts(productBean.getVarientProducts());
						//Will enable in future, when we have drop down against each outlet on product add and edit page
						//Currently there is no business activity depends on product all activities are performed on outlet
						//Case need to handle in case of standard/composite product with no variants and no composite product respectively.
						/*product.setOutlet((Outlet)outletMap.get(Integer.valueOf(outletbean.getOutletId())));
						SalesTax salestax = salesTaxService.getSalesTaxBySalesTaxId(Integer.valueOf(outletbean.getSalesTaxId()));
						product.setSalesTax(salestax);*/
						product.setMarkupPrct(new BigDecimal(productBean.getMarkupPrct()));
						product.setSku(productBean.getSku());
						product.setSupplyPriceExclTax(new BigDecimal(productBean.getSupplyPriceExclTax()));
						totalQunatity = 0;
						if(productBean.getTrackingProduct().equalsIgnoreCase("true") && productBean.getStandardProduct().equalsIgnoreCase("true")
								&&	!productBean.getVarientProducts().equalsIgnoreCase("true")){
							List<OutletBean> outletBeans = productBean.getOutletList();
							if(outletBeans!=null){
								for(OutletBean outletBean:outletBeans){
									//Inventory only update for warehouse other than warehouse inventory only be updated through stock control
									if(outletBean.getOutletId().equalsIgnoreCase(product.getOutlet().getOutletId().toString()) && product.getOutlet().getOutletId()==headOfficeId){
										if(outletBean.getCurrentInventory()!=null && !outletBean.getCurrentInventory().equalsIgnoreCase("")){
											totalQunatity = Integer.valueOf(outletBean.getCurrentInventory())+totalQunatity;
											product.setCurrentInventory(Integer.valueOf(outletBean.getCurrentInventory()));
											productBean.setCurrentInventory(outletBean.getCurrentInventory()); //added by Zafar
										}else{
											product.setCurrentInventory(0);
											productBean.setCurrentInventory("0"); //Added by Zafar
										}
										//Added by Zafar
										if(outletBean.getOldInventory()!=null && !outletBean.getOldInventory().equalsIgnoreCase("")){
											productBean.setOldInventory(outletBean.getOldInventory()); //added by Zafar
										}else{
											productBean.setOldInventory("0");
										}
										//Finish
										if(outletBean.getReorderAmount()!=null && !outletBean.getReorderAmount().equalsIgnoreCase("")){
											product.setReorderAmount(new BigDecimal(outletBean.getReorderAmount()));
										}else{
											product.setReorderAmount(new BigDecimal(0));
										}
										if(outletBean.getReorderPoint()!=null && !outletBean.getReorderPoint().equalsIgnoreCase("")){
											product.setReorderPoint(Integer.valueOf(outletBean.getReorderPoint()));
										}else{
											product.setReorderPoint(0);
										}
									}
								}
							}
						}
						if(productBean.getImageData()!=null){
							processImage(productBean.getImageData(), product, request);
						}
						
						if(productBean.getProductVariantValuesCollectionOne()!=null){
							String attribut1 = "";
							for(VarientAttributeValueBean attributeValue : productBean.getProductVariantValuesCollectionOne()){
								VariantAttributeValues variantAttributeValues = new VariantAttributeValues();
								variantAttributeValues.setAttributeValue(attributeValue.getValue());
								if(attribut1.equalsIgnoreCase("")){
									attribut1= attributeValue.getValue();
								}else{
									attribut1 =attribut1+","+attributeValue.getValue();
								}
								
							}
							product.setAttribute1(attribut1);
						}
						if(productBean.getProductVariantValuesCollectionTwo()!=null){
							String attribut2 = "";
							for(VarientAttributeValueBean attributeValue : productBean.getProductVariantValuesCollectionTwo()){
								VariantAttributeValues variantAttributeValues = new VariantAttributeValues();
								variantAttributeValues.setAttributeValue(attributeValue.getValue());
								if(attribut2.equalsIgnoreCase("")){
									attribut2= attributeValue.getValue();
								}else{
									attribut2 =attribut2+","+attributeValue.getValue();
								}
								
							}
							product.setAttribute2(attribut2);
						}
						if(productBean.getProductVariantValuesCollectionThree()!=null){
							String attribut3 = "";
							for(VarientAttributeValueBean attributeValue : productBean.getProductVariantValuesCollectionThree()){
								VariantAttributeValues variantAttributeValues = new VariantAttributeValues();
								variantAttributeValues.setAttributeValue(attributeValue.getValue());
								if(attribut3.equalsIgnoreCase("")){
									attribut3= attributeValue.getValue();
								}else{
									attribut3 =attribut3+","+attributeValue.getValue();
								}
								
							}
							product.setAttribute3(attribut3);
						}
						
						if(productBean.getVarientProducts().equalsIgnoreCase("false")){
							if(productBean.getCurrentInventory() !=null){
								if(productBean.getOldInventory() != null){
									if(Integer.parseInt(productBean.getCurrentInventory()) > Integer.parseInt(productBean.getOldInventory())){
										if(configurationStockOrder!=null && configurationStockOrder.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
											//Stock order should be populate only in case of warehouse
											if(product.getOutlet().getOutletId()==headOfficeId){
												StockOrderDetailBean stockOrderDetailBean = new StockOrderDetailBean();
												int quantity = Integer.parseInt(productBean.getCurrentInventory()) - Integer.parseInt(productBean.getOldInventory());
												stockOrderDetailBean.setProductVariantId(productBean.getProductId().toString());
												stockOrderDetailBean.setOrderProdQty(String.valueOf(quantity));
												stockOrderDetailBean.setOrdrSupplyPrice(String.valueOf(productBean.getSupplyPriceExclTax()));
												grandTotal = grandTotal + (Double.parseDouble(stockOrderDetailBean.getOrderProdQty()) * Double.parseDouble(stockOrderDetailBean.getOrdrSupplyPrice()));					
												itemCount = itemCount + Double.parseDouble(stockOrderDetailBean.getOrderProdQty());
												stockOrderDetailBean.setIsProduct("true");
												stockOrderDetialBeansList.add(stockOrderDetailBean);
												product.setCurrentInventory(Integer.parseInt(productBean.getOldInventory())); //quantity will be added by StockOrder
												productService.updateProduct(product,Actions.UPDATE,totalQunatity,currentUser.getCompany());
												product.setCurrentInventory(Integer.parseInt(productBean.getCurrentInventory()));
											}else{
												productService.updateProduct(product,Actions.UPDATE,totalQunatity,currentUser.getCompany());
											}
										}
										else{
											productService.updateProduct(product,Actions.UPDATE,totalQunatity,currentUser.getCompany());
										}
									}
									else if(Integer.parseInt(productBean.getCurrentInventory()) < Integer.parseInt(productBean.getOldInventory())){
										if(configurationReturnStock!=null && configurationReturnStock.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
											//Stock order should be populate only in case of warehouse
											if(product.getOutlet().getOutletId()==headOfficeId){
												StockOrderDetailBean stockOrderDetailBean = new StockOrderDetailBean();
												int quantity = Integer.parseInt(productBean.getOldInventory()) - Integer.parseInt(productBean.getCurrentInventory());
												stockOrderDetailBean.setProductVariantId(productBean.getProductId().toString());
												stockOrderDetailBean.setOrderProdQty(String.valueOf(quantity));
												stockOrderDetailBean.setOrdrSupplyPrice(String.valueOf(productBean.getSupplyPriceExclTax()));
												grandTotalReturn = grandTotalReturn + (Double.parseDouble(stockOrderDetailBean.getOrderProdQty()) * Double.parseDouble(stockOrderDetailBean.getOrdrSupplyPrice()));					
												itemCountReturn = itemCountReturn + Double.parseDouble(stockOrderDetailBean.getOrderProdQty());
												stockOrderDetailBean.setIsProduct("true");
												stockReturnDetialBeansList.add(stockOrderDetailBean);
												product.setCurrentInventory(Integer.parseInt(productBean.getOldInventory())); //quantity will be subtracted by StockReturn
												productService.updateProduct(product,Actions.UPDATE,totalQunatity,currentUser.getCompany());
												product.setCurrentInventory(Integer.parseInt(productBean.getCurrentInventory()));
											}else{
												productService.updateProduct(product,Actions.UPDATE,totalQunatity,currentUser.getCompany());
											}
										}
										else{
											productService.updateProduct(product,Actions.UPDATE,totalQunatity,currentUser.getCompany());
										}											
									}
									else{
										productService.updateProduct(product,Actions.UPDATE,totalQunatity,currentUser.getCompany());
									}
								}
							}
						}
						else{
							productService.updateProduct(product,Actions.UPDATE,totalQunatity,currentUser.getCompany());
						}
						if(!productBean.getStandardProduct().equalsIgnoreCase("true")){/*
							List<CompositVariantBean> compositeVariantBeans = productBean.getCompositProductCollection();
							for(CompositVariantBean compositVariantBean:compositeVariantBeans){
								if(compositVariantBean.getCompositeProductId()!=null && !compositVariantBean.getCompositeProductId().equalsIgnoreCase("")){
									List<CompositeProduct> compositeProductList = compositeProductService.getAllCompositeProductsByUuid(compositVariantBean.getUuid(),currentUser.getCompany().getCompanyId());
									for(CompositeProduct compositeProduct:compositeProductList){
										compositeProduct.setCompositeQuantity(Integer.valueOf(compositVariantBean.getCompositQunatity()));
										compositeProduct.setUserByUpdatedBy(currentUser);
										compositeProduct.setLastUpdated(new Date());
										compositeProductService.updateCompositeProduct(compositeProduct,Actions.UPDATE,0,currentUser.getCompany().getCompanyId());
									}
								}else{
									CompositeProduct compositeProduct = new CompositeProduct();
									compositeProduct.setActiveIndicator(true);
									compositeProduct.setCompositeQuantity(Integer.valueOf(compositVariantBean.getCompositQunatity()));
									if(compositVariantBean.getProductVariantId()!=null && compositVariantBean.getProductVariantId()!=""){
										ProductVariant productVariant = productVariantService.getProductVariantByProductVariantId(Integer.valueOf(compositVariantBean.getProductVariantId()),currentUser.getCompany().getCompanyId());
										compositeProduct.setProductVariant(productVariant);
									}
									compositeProduct.setCreatedDate(new Date());
									compositeProduct.setLastUpdated(new Date());
									compositeProduct.setOutlet(product.getOutlet());
									compositeProduct.setProductByProductAssocicationId(product);	
									Product selectiveProduct = productService.getProductByProductId(Integer.valueOf(compositVariantBean.getProductId()),currentUser.getCompany().getCompanyId());
									compositeProduct.setProductBySelectiveProductAssociationId(selectiveProduct);
									compositeProduct.setUserByCreatedBy(currentUser);
									compositeProduct.setUserByUpdatedBy(currentUser);
									compositeProduct.setCompositeProductUuid(compositVariantBean.getUuid());
									compositeProduct.setCompany(currentUser.getCompany());
									compositeProductService.addCompositeProduct(compositeProduct,Actions.CREATE,0,currentUser.getCompany().getCompanyId(),product.getProductUuid());
								}
							}
						*/}
						if(productBean.getVarientProducts().equalsIgnoreCase("true")){
							//Variants scenario
							/*//Check if variants exists then remove all variant attribute values  
							List<ProductVariantBean> productVariantBeans = productBean.getProductVariantsBeans();
							if(productVariantBeans!=null){
								List<Integer> productVariantIdList = new ArrayList<>();
								for(ProductVariantBean productVariantBean:productVariantBeans){
									productVariantIdList.add(Integer.valueOf(productVariantBean.getProductVariantId()));
								}
							}*/
							
							List<VarientValueBean> varientValuesBeanCollection = productBean.getProductVariantValuesCollection();
							if(varientValuesBeanCollection!=null){
								List<OutletBean> outletsist = productBean.getOutletList();
								
								if(outletsist!=null){
									Configuration configuration = configurationService.getConfigurationByPropertyNameByCompanyId("PRODCUT_TEMPLATE_FOR_ALL_OUTLETS",currentUser.getCompany().getCompanyId());
									if(configuration==null || configuration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
										for(OutletBean outletBean:outletsist){
											for(VarientValueBean varientValueBean:varientValuesBeanCollection){
												addProductVariants(outletBean, productBean, currentUser, outletMap, outletsist, varientValueBean, product);
											}
										}
									}else{
										for(OutletBean outletbean:outletsist){
											Outlet outlet = (Outlet)outletMap.get(Integer.valueOf(outletbean.getOutletId()));
											if(outlet.getIsHeadOffice()!=null && outlet.getIsHeadOffice().toString()=="true" && !variantsAddedForWarehouse){
												variantsAddedForWarehouse = true;
												for(VarientValueBean varientValueBean:varientValuesBeanCollection){
													if(configurationStockOrder!=null && configurationStockOrder.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
														int variantId = 0;
														int currInt = 0;
														if(varientValueBean.getVarientsOutletList().get(0).getCurrentInventory()!=null && !varientValueBean.getVarientsOutletList().get(0).getCurrentInventory().equalsIgnoreCase("") && 
																Integer.valueOf(varientValueBean.getVarientsOutletList().get(0).getCurrentInventory())>0){
															currInt = Integer.valueOf(varientValueBean.getVarientsOutletList().get(0).getCurrentInventory());
															varientValueBean.getVarientsOutletList().get(0).setCurrentInventory(String.valueOf(0));
															isCcreateSelfProcessOrder = true;
														}
														variantId = addProductVariants(outletbean, productBean, currentUser, outletMap, outletsist, varientValueBean, product);
														varientValueBean.getVarientsOutletList().get(0).setCurrentInventory(String.valueOf(currInt));
														if( variantId!=0 ){
															if(varientValueBean.getVarientsOutletList().get(0).getCurrentInventory()!=null && !varientValueBean.getVarientsOutletList().get(0).getCurrentInventory().equalsIgnoreCase("") && 
																	Integer.valueOf(varientValueBean.getVarientsOutletList().get(0).getCurrentInventory())>0){
																StockOrderDetailBean stockOrderDetailBean = new StockOrderDetailBean();
																stockOrderDetailBean.setProductVariantId(String.valueOf(variantId));
																stockOrderDetailBean.setOrderProdQty(varientValueBean.getVarientsOutletList().get(0).getCurrentInventory());
																stockOrderDetailBean.setOrdrSupplyPrice(varientValueBean.getVarientsOutletList().get(0).getSupplyPriceExclTax());
																grandTotal = grandTotal + (Double.parseDouble(stockOrderDetailBean.getOrderProdQty()) * Double.parseDouble(stockOrderDetailBean.getOrdrSupplyPrice())); 
																itemCount = itemCount + Double.parseDouble(stockOrderDetailBean.getOrderProdQty());
																stockOrderDetailBean.setIsProduct("false");
																stockOrderDetialBeansList.add(stockOrderDetailBean);
															}
														}
													}
													else{
														if(varientValueBean.getVarientsOutletList().get(0).getCurrentInventory()!=null && !varientValueBean.getVarientsOutletList().get(0).getCurrentInventory().equalsIgnoreCase("") && 
																Integer.valueOf(varientValueBean.getVarientsOutletList().get(0).getCurrentInventory())>0){
															isCcreateSelfProcessOrder = true;
														}
														addProductVariants(outletbean, productBean, currentUser, outletMap, outletsist, varientValueBean, product);
													}
												}
												break;
											}
										}
									}
								}
							}
						}
						Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
						Configuration dutyCalculatorConfiguration = configurationMap.get("SHOW_DUTY_CALCULATOR");
						if(dutyCalculatorConfiguration!=null && dutyCalculatorConfiguration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
							ProductPriceHistoryBean productPriceHistoryBean = productBean.getProductPriceHistoryBean();
							if(productPriceHistoryBean!=null){
								ProductPriceHistory productPriceHistory = getProductPriceHistoryFromProductPriceHistoryBean(productPriceHistoryBean,product);
								if(productPriceHistory!=null){
									productPriceHistoryService.addProductPriceHistory(productPriceHistory);
								}
							}
						}
					}
					if(productBean.getStandardProduct().equalsIgnoreCase("true") && productBean.getVarientProducts().equalsIgnoreCase("true")){
						List<ProductVariantBean> productVariantBeans = productBean.getProductVariantsBeans();
						if(productVariantBeans!=null){
							int productVariantToltalCount = 0;
							for(ProductVariantBean productVariantBean:productVariantBeans){
								ProductVariant productVariant = productVariantService.getProductVariantByProductVariantId(Integer.valueOf(productVariantBean.getProductVariantId()),currentUser.getCompany().getCompanyId());
								productVariantBean.setOldInventory(String.valueOf(productVariant.getCurrentInventory()));
								productVariant.setMarkupPrct(new BigDecimal(productVariantBean.getMarkupPrct()));
								productVariant.setSku(productVariantBean.getSku());
								productVariant.setUserByUpdatedBy(currentUser);
								productVariant.setSupplyPriceExclTax(new BigDecimal(productVariantBean.getSupplyPriceExclTax()));
								productVariant.setLastUpdated(new Date());
								if(productBean.getTrackingProduct().equalsIgnoreCase("true") && productVariant.getOutlet().getOutletId()==headOfficeId){
									if(productVariantBean.getCurrentInventory()!=null){
										productVariantToltalCount = Integer.valueOf(productVariantBean.getCurrentInventory());
										productVariant.setCurrentInventory(productVariantToltalCount);
									}
									if(productVariantBean.getReorderAmount()!=null){
										productVariant.setReorderAmount(new BigDecimal(productVariantBean.getReorderAmount()));
									}
									if(productVariantBean.getReorderPoint()!=null){
										productVariant.setReorderPoint(Integer.valueOf(productVariantBean.getReorderPoint()));
									}
								}
								if(productVariantBean.getCurrentInventory() !=null){
									if(productVariantBean.getOldInventory() != null){
										if(Integer.parseInt(productVariantBean.getCurrentInventory()) > Integer.parseInt(productVariantBean.getOldInventory())){
											if(configurationStockOrder!=null && configurationStockOrder.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
												//Stock order should be populate only in case of warehouse
												if(productVariant.getOutlet().getOutletId()==headOfficeId){
													StockOrderDetailBean stockOrderDetailBean = new StockOrderDetailBean();
													int quantity = Integer.parseInt(productVariantBean.getCurrentInventory()) - Integer.parseInt(productVariantBean.getOldInventory());
													stockOrderDetailBean.setProductVariantId(productVariantBean.getProductVariantId().toString());
													stockOrderDetailBean.setOrderProdQty(String.valueOf(quantity));
													stockOrderDetailBean.setOrdrSupplyPrice(String.valueOf(productVariantBean.getSupplyPriceExclTax()));
													grandTotal = grandTotal + (Double.parseDouble(stockOrderDetailBean.getOrderProdQty()) * Double.parseDouble(stockOrderDetailBean.getOrdrSupplyPrice()));					
													itemCount = itemCount + Double.parseDouble(stockOrderDetailBean.getOrderProdQty());
													stockOrderDetailBean.setIsProduct("false");
													stockOrderDetialBeansList.add(stockOrderDetailBean);
													productVariant.setCurrentInventory(Integer.parseInt(productVariantBean.getOldInventory())); //quantity will be added by StockOrder
													productVariantService.updateProductVariant(productVariant,Actions.UPDATE,totalQunatity,currentUser.getCompany());
													productVariantToltalCount = 0;
													productVariant.setCurrentInventory(Integer.parseInt(productVariantBean.getCurrentInventory()));
												}else{
													productVariantService.updateProductVariant(productVariant,Actions.UPDATE,totalQunatity,currentUser.getCompany());
												}
											}
											else{
												productVariantService.updateProductVariant(productVariant,Actions.UPDATE,totalQunatity,currentUser.getCompany());
												productVariantToltalCount = 0;
											}
										}
										else if(Integer.parseInt(productVariantBean.getCurrentInventory()) < Integer.parseInt(productVariantBean.getOldInventory())){
											if(configurationReturnStock!=null && configurationReturnStock.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
												//Stock order should be populate only in case of warehouse
												if(productVariant.getOutlet().getOutletId()==headOfficeId){
													StockOrderDetailBean stockOrderDetailBean = new StockOrderDetailBean();
													int quantity = Integer.parseInt(productVariantBean.getOldInventory()) - Integer.parseInt(productVariantBean.getCurrentInventory());
													stockOrderDetailBean.setProductVariantId(productVariantBean.getProductVariantId().toString());
													stockOrderDetailBean.setOrderProdQty(String.valueOf(quantity));
													stockOrderDetailBean.setOrdrSupplyPrice(String.valueOf(productVariantBean.getSupplyPriceExclTax()));
													grandTotalReturn = grandTotalReturn + (Double.parseDouble(stockOrderDetailBean.getOrderProdQty()) * Double.parseDouble(stockOrderDetailBean.getOrdrSupplyPrice()));					
													itemCountReturn = itemCountReturn + Double.parseDouble(stockOrderDetailBean.getOrderProdQty());
													stockOrderDetailBean.setIsProduct("false");
													stockReturnDetialBeansList.add(stockOrderDetailBean);
													productVariant.setCurrentInventory(Integer.parseInt(productVariantBean.getOldInventory())); //quantity will be subtracted by StockReturn
													productVariantService.updateProductVariant(productVariant,Actions.UPDATE,totalQunatity,currentUser.getCompany());
													productVariantToltalCount = 0;
													productVariant.setCurrentInventory(Integer.parseInt(productVariantBean.getCurrentInventory()));
												}else{
													productVariantService.updateProductVariant(productVariant,Actions.UPDATE,totalQunatity,currentUser.getCompany());
												}
											}
											else{
												productVariantService.updateProductVariant(productVariant,Actions.UPDATE,totalQunatity,currentUser.getCompany());
												productVariantToltalCount = 0;
											}											
										}
										else{
											productVariantService.updateProductVariant(productVariant,Actions.UPDATE,productVariantToltalCount,currentUser.getCompany());
											productVariantToltalCount = 0;
										}
									}
								}else{
									//product has variants but does not have inventory so adjusting markup,supply price only
									productVariantService.updateProductVariant(productVariant,Actions.UPDATE,productVariantToltalCount,currentUser.getCompany());
								}
							}
						}
					}
					if(stockOrderDetialBeansList!=null && stockOrderDetialBeansList.size()>0){
						StockOrderBean stockOrderBean = new StockOrderBean();
						stockOrderBean.setOutlet(String.valueOf(headOfficeId));
						stockOrderBean.setSupplierId(productBean.getSupplierId());
						AddStockOrder(sessionId, stockOrderBean, stockOrderDetialBeansList, grandTotal, itemCount, request);
					}
					if(stockReturnDetialBeansList!=null && stockReturnDetialBeansList.size()>0){
						StockOrderBean stockOrderBean = new StockOrderBean();
						stockOrderBean.setOutlet(String.valueOf(headOfficeId));
						stockOrderBean.setSupplierId(productBean.getSupplierId());
						AddStockReturn(sessionId, stockOrderBean, stockReturnDetialBeansList, grandTotalReturn, itemCountReturn, request);
					}
					if(productList.size()==1){
						if(productList.get(0)!=null && productBean.getIsComposite()!=null && productBean.getIsComposite().equalsIgnoreCase("true")){
							Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
							Configuration consumeCompositeQunatityConfiguration = configurationMap.get("CONSUME_COMPOSITE_QUNATITY");
							boolean isConsumeCompositeQunatity = false;
							if(consumeCompositeQunatityConfiguration!=null && consumeCompositeQunatityConfiguration.getPropertyValue().equalsIgnoreCase(ControllersConstants.TRUE)){
								isConsumeCompositeQunatity = true;
							}
							if(productList.get(0).getCurrentInventory()>0){
								boolean isCreated = createCompositeProductAndCompositeProductHistory(productList.get(0),productBean,currentUser,productMap , productvariantMap);
								if(isCreated && isConsumeCompositeQunatity){
									createSelfProcessOrder(productList.get(0),productBean,currentUser,productMap , productvariantMap,sessionId,request);
								}
							}else if(productBean.getStandardProduct().equalsIgnoreCase("true") && productBean.getVarientProducts().equalsIgnoreCase("true") && isCcreateSelfProcessOrder){
								boolean isCreated = createCompositeProductAndCompositeProductHistory(productList.get(0),productBean,currentUser,productMap , productvariantMap);
								if(isCreated && isConsumeCompositeQunatity){
									createSelfProcessOrder(productList.get(0),productBean,currentUser,productMap , productvariantMap,sessionId,request);
								}
							}
						}
					}
					
					util.AuditTrail(request, currentUser, "ManageProductController.updateProduct", 
							"User "+ currentUser.getUserEmail()+"Product updated +"+productBean.getProductName()+" successfully ",false);
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.PRODUCTS);
				}else{
					util.AuditTrail(request, currentUser, "ManageProductController.updateProduct", 
							"System unable to update Product, requested By +"+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.MANAGE_PRODUCT);
				}
			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManageProductController.updateProduct",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/removeCompositeProduct/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response removeCompositeProduct(@PathVariable("sessionId") String sessionId,
			@RequestBody ProductVariantBean productVariantBean, HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			Product product = null;
			ProductVariant productVariant = null;
		try{
			Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
			Configuration consumeCompositeQunatityConfiguration = configurationMap.get("CONSUME_COMPOSITE_QUNATITY");
			boolean isConsumeCompositeQunatity = false;
			if(consumeCompositeQunatityConfiguration!=null && consumeCompositeQunatityConfiguration.getPropertyValue().equalsIgnoreCase(ControllersConstants.TRUE)){
				isConsumeCompositeQunatity = true;
			}
			if(isConsumeCompositeQunatity){
				if(productVariantBean.getIsProduct().equalsIgnoreCase("true")){
					 product = productService.getProductByProductId(Integer.valueOf(productVariantBean.getProductVariantId()), currentUser.getCompany().getCompanyId());
					product.setCurrentInventory(product.getCurrentInventory()+Integer.valueOf(productVariantBean.getCompositeQunatityConsumed()));
					productService.updateProduct(product, Actions.INVENTORY_ADD, product.getCurrentInventory(), currentUser.getCompany());
				}else{
					 productVariant = productVariantService.getProductVariantByProductVariantId(Integer.valueOf(productVariantBean.getProductVariantId()), currentUser.getCompany().getCompanyId());
					productVariant.setCurrentInventory(productVariant.getCurrentInventory()+Integer.valueOf(productVariantBean.getCompositeQunatityConsumed()));
					productVariantService.updateProductVariant(productVariant, Actions.INVENTORY_ADD, productVariant.getCurrentInventory(), currentUser.getCompany());
				}
			}
			
			
			CompositeProduct compositeProduct = compositeProductService.getCompositeProductByCompositeProductId(productVariantBean.getCompositeProductId(), currentUser.getCompany().getCompanyId());
			boolean isDeleted = compositeProductService.deleteCompositeProduct(compositeProduct, Actions.DELETE, currentUser.getCompany().getCompanyId());
			if(isConsumeCompositeQunatity && isDeleted){
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				StockOrderBean stockOrderBean = new StockOrderBean();
				stockOrderBean.setActiveIndicator("true");
				stockOrderBean.setAutofillReorder("true");
				stockOrderBean.setRetailPriceBill("false");
				stockOrderBean.setSupplierId(productVariantBean.getContactId()+"");
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH) + 1;
				int day = cal.get(Calendar.DAY_OF_MONTH);
				int hour = cal.get(Calendar.HOUR_OF_DAY);
				int min = cal.get(Calendar.MINUTE);
				stockOrderBean.setDiliveryDueDate(dateFormat.format(new Date()));			
				stockOrderBean.setOrderNo("SPOA-"+ (month+"/"+ day+"/"+ year+ " " + hour + ":" + min));
				stockOrderBean.setOrdrRecvDate(dateFormat.format(new Date()));
				stockOrderBean.setStatusId("3"); 	// Completed		
				stockOrderBean.setStockOrderTypeId("6"); //Self Process Order Add
				stockOrderBean.setStockRefNo("SPOA-"+ (month+"/"+ day+"/"+ year+ " " + hour + ":" + min));
				stockOrderBean.setOutlet(currentUser.getOutlet().getOutletId()+"");
				stockOrderBean.setRemarks("Self Process Order Created for Composite Product having id: "+productVariantBean.getProductId()+" with Product Name: "+productVariantBean.getVariantAttributeName());
				Response response = purchaseOrderController.addStockOrder(sessionId, stockOrderBean, request);
				stockOrderBean.setStockOrderId(response.data.toString());
				List<StockOrderDetail> stockOrderDetailsAddList = new ArrayList<>();

				StockOrderDetail stockOrderDetail = new StockOrderDetail();
				stockOrderDetail.setActiveIndicator(true);
				stockOrderDetail.setCompany(currentUser.getCompany());
				stockOrderDetail.setCreatedBy(currentUser.getUserId());
				if(productVariantBean.getIsProduct().equalsIgnoreCase("true")){
					stockOrderDetail.setIsProduct(true);
					stockOrderDetail.setProduct(product);
					//product.setCurrentInventory(product.getCurrentInventory());
					
				}else{
					stockOrderDetail.setIsProduct(false);
					stockOrderDetail.setProductVariant(productVariant);
					//productVariant.setCurrentInventory(productVariant.getCurrentInventory());
				}
				stockOrderDetail.setOrderProdQty(Integer.valueOf(productVariantBean.getCompositeQunatityConsumed()));
				stockOrderDetail.setOrdrSupplyPrice(new BigDecimal(productVariantBean.getSupplyPriceExclTax()));
				stockOrderDetail.setRecvProdQty(Integer.valueOf(productVariantBean.getCompositeQunatityConsumed()));
				stockOrderDetail.setRecvSupplyPrice(new BigDecimal(productVariantBean.getSupplyPriceExclTax()));
				stockOrderDetail.setRetailPrice(new BigDecimal(productVariantBean.getRetailPrice()));
				StockOrder stockOrder = new StockOrder();
				stockOrder.setStockOrderId(Integer.valueOf(stockOrderBean.getStockOrderId()));
				stockOrderDetail.setStockOrder(stockOrder);
				stockOrderDetailsAddList.add(stockOrderDetail);
				stockOrderDetailService.addStockOrderDetailsList(stockOrderDetailsAddList, currentUser.getCompany().getCompanyId());
			}
			
		
			
				util.AuditTrail(request, currentUser, "ManageCompositeProductController.removeCompositeProduct", 
						"User "+ currentUser.getUserEmail()+"Product updated +"+productVariantBean.getVariantAttributeName()+" successfully ",false);
				return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.PRODUCTS);
				
				
			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManageProductController.updateProduct",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}


	@SuppressWarnings("rawtypes")
	private int addProductVariants(OutletBean outletbean,ProductBean productBean,User currentUser,Map outletMap,List<OutletBean> outletsist,VarientValueBean varientValueBean,Product newProduct ){
		int productVariantId = 0;
		String[] varientNamesList = varientValueBean.getVarientName().split("/");
		List<OutletBean> variantOutletBeanList = varientValueBean.getVarientsOutletList();
		if(variantOutletBeanList!=null){
			for(OutletBean variantOutletBean:variantOutletBeanList){
				if(Integer.valueOf(variantOutletBean.getOutletId())==Integer.valueOf(outletbean.getOutletId())){
					ProductVariant productVariant = new ProductVariant();
					productVariant.setActiveIndicator(true);
					productVariant.setUserByCreatedBy(currentUser);
					productVariant.setCreatedDate(new Date());
					productVariant.setLastUpdated(new Date());
					productVariant.setProductVariantUuid(varientValueBean.getuUid());
					productVariant.setMarkupPrct(new BigDecimal(variantOutletBean.getMarkupPrct()));
					productVariant.setProduct(newProduct);
					productVariant.setSku(variantOutletBean.getSku());
					productVariant.setSupplyPriceExclTax(new BigDecimal(variantOutletBean.getSupplyPriceExclTax()));
					productVariant.setUserByUpdatedBy(currentUser);
					Outlet tempOutlet = (Outlet)outletMap.get(Integer.valueOf(variantOutletBean.getOutletId()));
					productVariant.setOutlet(tempOutlet);
					SalesTax salesTax = salesTaxService.getSalesTaxBySalesTaxId(Integer.valueOf(tempOutlet.getSalesTax().getSalesTaxId()),currentUser.getCompany().getCompanyId());
					productVariant.setSalesTax(salesTax);
					productVariant.setVariantAttributeName(varientValueBean.getVarientName());
					productVariant.setVariantAttributeValue1(varientNamesList[0]);
					if(varientNamesList.length>1){
						productVariant.setVariantAttributeValue2(varientNamesList[1]);
					}
					if(varientNamesList.length>2){
						productVariant.setVariantAttributeValue3(varientNamesList[2]);
					}
					VariantAttribute attributeOne = new VariantAttribute();
					VariantAttribute attributeTwo = new VariantAttribute();
					VariantAttribute attributeThree = new VariantAttribute();
					if(productBean.getProductVariantAttributeCollection()!=null && productBean.getProductVariantAttributeCollection().size() >= 2){
						attributeOne = variantAttributeService.getVariantAttributeByVariantAttributeId(Integer.parseInt(productBean.getProductVariantAttributeCollection().get(0).getVarientAttributeId()),currentUser.getCompany().getCompanyId());
						productVariant.setVariantAttributeByVariantAttributeAssocicationId1(attributeOne);
						if(productBean.getProductVariantAttributeCollection().size()>=2){
							attributeTwo = variantAttributeService.getVariantAttributeByVariantAttributeId(Integer.parseInt(productBean.getProductVariantAttributeCollection().get(1).getVarientAttributeId()),currentUser.getCompany().getCompanyId());
							productVariant.setVariantAttributeByVariantAttributeAssocicationId2(attributeTwo);
						}
						if(productBean.getProductVariantAttributeCollection().size()>=3){
							attributeThree = variantAttributeService.getVariantAttributeByVariantAttributeId(Integer.parseInt(productBean.getProductVariantAttributeCollection().get(2).getVarientAttributeId()),currentUser.getCompany().getCompanyId());
							productVariant.setVariantAttributeByVariantAttributeAssocicationId3(attributeThree);
						}
					}
					else if(productBean.getProductVariantAttributeCollection()!=null && productBean.getProductVariantAttributeCollection().size() > 1){
						attributeOne = variantAttributeService.getVariantAttributeByVariantAttributeId(Integer.parseInt(productBean.getProductVariantAttributeCollection().get(0).getVarientAttributeId()),currentUser.getCompany().getCompanyId());
						productVariant.setVariantAttributeByVariantAttributeAssocicationId1(attributeOne);

						attributeTwo = variantAttributeService.getVariantAttributeByVariantAttributeId(Integer.parseInt(productBean.getProductVariantAttributeCollection().get(1).getVarientAttributeId()),currentUser.getCompany().getCompanyId());
						productVariant.setVariantAttributeByVariantAttributeAssocicationId2(attributeTwo);
					}
					else if(productBean.getProductVariantAttributeCollection()!=null && productBean.getProductVariantAttributeCollection().size() == 1){
						attributeOne = variantAttributeService.getVariantAttributeByVariantAttributeId(Integer.parseInt(productBean.getProductVariantAttributeCollection().get(0).getVarientAttributeId()),currentUser.getCompany().getCompanyId());
						productVariant.setVariantAttributeByVariantAttributeAssocicationId1(attributeOne);
					}
					if(variantOutletBean.getCurrentInventory()==null || variantOutletBean.getCurrentInventory().equals("")){
						productVariant.setCurrentInventory(0);
					}
					if(productBean.getTrackingProduct().equalsIgnoreCase("true")){
						if(variantOutletBean.getCurrentInventory()==null){
							productVariant.setCurrentInventory(0);
						}else{
							productVariant.setCurrentInventory(Integer.valueOf(variantOutletBean.getCurrentInventory()));
						}
						if(variantOutletBean.getReorderAmount()==null){
							productVariant.setReorderAmount(BigDecimal.ZERO);
						}else{
							productVariant.setReorderAmount(new BigDecimal(variantOutletBean.getReorderAmount()));
						}
						if(variantOutletBean.getReorderPoint()==null){
							productVariant.setReorderPoint(0);
						}else{
							productVariant.setReorderPoint(Integer.valueOf(variantOutletBean.getReorderPoint()));
						}
					}
					productVariant.setCompany(currentUser.getCompany());
					ProductVariant variantProduct = productVariantService.addProductVariant(productVariant,Actions.CREATE,productVariant.getCurrentInventory(),currentUser.getCompany(),newProduct.getProductUuid());
					productVariantId = variantProduct.getProductVariantId();
					if(productBean.getProductVariantValuesCollectionOne()!=null ){
						for(VarientAttributeValueBean attributeValue : productBean.getProductVariantValuesCollectionOne()){
							VariantAttributeValues variantAttributeValues = new VariantAttributeValues();
							variantAttributeValues.setActiveIndicator(true);
							variantAttributeValues.setAttributeValue(attributeValue.getValue());
							variantAttributeValues.setUserByCreatedBy(currentUser);
							variantAttributeValues.setLastUpdated(new Date());
							variantAttributeValues.setProductVariant(productVariant);
							variantAttributeValues.setUserByUpdatedBy(currentUser);
							variantAttributeValues.setVariantAttribute(attributeOne);
							variantAttributeValues.setProduct(newProduct);
							variantAttributeValues.setProductUuid(newProduct.getProductUuid());
							variantAttributeValues.setCompany(currentUser.getCompany());
							variantAttributeValuesService.addVariantAttributeValues(variantAttributeValues,currentUser.getCompany().getCompanyId());
						}
					}
					if(productBean.getProductVariantValuesCollectionTwo()!=null ){
						for(VarientAttributeValueBean attributeValue : productBean.getProductVariantValuesCollectionTwo()){
							VariantAttributeValues variantAttributeValues = new VariantAttributeValues();
							variantAttributeValues.setActiveIndicator(true);
							variantAttributeValues.setAttributeValue(attributeValue.getValue());
							variantAttributeValues.setUserByCreatedBy(currentUser);
							variantAttributeValues.setLastUpdated(new Date());
							variantAttributeValues.setProductVariant(productVariant);
							variantAttributeValues.setUserByUpdatedBy(currentUser);
							variantAttributeValues.setVariantAttribute(attributeTwo);
							variantAttributeValues.setProduct(newProduct);
							variantAttributeValues.setProductUuid(newProduct.getProductUuid());
							variantAttributeValues.setCompany(currentUser.getCompany());
							variantAttributeValuesService.addVariantAttributeValues(variantAttributeValues,currentUser.getCompany().getCompanyId());
						}
					}
					if(productBean.getProductVariantValuesCollectionThree()!=null ){
						for(VarientAttributeValueBean attributeValue : productBean.getProductVariantValuesCollectionThree()){
							VariantAttributeValues variantAttributeValues = new VariantAttributeValues();
							variantAttributeValues.setActiveIndicator(true);
							variantAttributeValues.setAttributeValue(attributeValue.getValue());
							variantAttributeValues.setUserByCreatedBy(currentUser);
							variantAttributeValues.setLastUpdated(new Date());
							variantAttributeValues.setProductVariant(productVariant);
							variantAttributeValues.setUserByUpdatedBy(currentUser);
							variantAttributeValues.setVariantAttribute(attributeThree);
							variantAttributeValues.setProduct(newProduct);
							variantAttributeValues.setProductUuid(newProduct.getProductUuid());
							variantAttributeValues.setCompany(currentUser.getCompany());
							variantAttributeValuesService.addVariantAttributeValues(variantAttributeValues,currentUser.getCompany().getCompanyId());
						}
					}
				}
			}
		}
		return productVariantId;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/markInActiveProductVariant/{sessionId}/{productVariantId}", method = RequestMethod.POST)
	public @ResponseBody
	Response markInActiveProductVariant(@PathVariable("sessionId") String sessionId,@PathVariable("productVariantId") String productVariantId,
			HttpServletRequest request) {
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			stockReturnDetialBeansList = new ArrayList<>();
			grandTotalReturn = 0.0;
			itemCountReturn = 0.0;
			configurationReturnStock = configurationService.getConfigurationByPropertyNameByCompanyId("AUTO_STOCK_RETURN_MANAGE_PRODUCT",currentUser.getCompany().getCompanyId());

			try{
				ProductVariant productVariant = productVariantService.getProductVariantByProductVariantId(Integer.valueOf(productVariantId),currentUser.getCompany().getCompanyId());
				productVariant.setActiveIndicator(false);
				productVariant.setLastUpdated(new Date());
				productVariant.setUserByUpdatedBy(currentUser);
				productVariantService.updateProductVariant(productVariant,Actions.DELETE,0,currentUser.getCompany());
				Product product = productService.getProductByProductId(productVariant.getProduct().getProductId(), currentUser.getCompany().getCompanyId());
				if(configurationReturnStock!=null && configurationReturnStock.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
					StockOrderDetailBean stockOrderDetailBean = new StockOrderDetailBean();
					int quantity = productVariant.getCurrentInventory();
					if(quantity > 0){
						stockOrderDetailBean.setProductVariantId(productVariant.getProductVariantId().toString());
						stockOrderDetailBean.setOrderProdQty(String.valueOf(quantity));
						stockOrderDetailBean.setOrdrSupplyPrice(String.valueOf(productVariant.getSupplyPriceExclTax()));
						grandTotalReturn = grandTotalReturn + (Double.parseDouble(stockOrderDetailBean.getOrderProdQty()) * Double.parseDouble(stockOrderDetailBean.getOrdrSupplyPrice()));					
						itemCountReturn = itemCountReturn + Double.parseDouble(stockOrderDetailBean.getOrderProdQty());
						stockOrderDetailBean.setIsProduct("false");
						stockReturnDetialBeansList.add(stockOrderDetailBean);
					}
				}
				if(stockReturnDetialBeansList!=null && stockReturnDetialBeansList.size()>0){
					StockOrderBean stockOrderBean = new StockOrderBean();
					stockOrderBean.setOutlet(String.valueOf(productVariant.getOutlet().getOutletId()));
					stockOrderBean.setSupplierId(String.valueOf(product.getContact().getContactId()));
					AddStockReturn(sessionId, stockOrderBean, stockReturnDetialBeansList, grandTotalReturn, itemCountReturn, request);
					stockReturnDetialBeansList = new ArrayList<>();
					grandTotalReturn = 0.0;
					itemCountReturn = 0.0;
				}

				util.AuditTrail(request, currentUser, "ManageProductController.markInActiveProductVariant", 
						"User "+ currentUser.getUserEmail()+" Updated product variant  successfully ",false);
				return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.PRODUCTS);


			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManageProductController.markInActiveProductVariant",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/markInActiveCompositeProductVariant/{sessionId}/{compositeProductUuid}", method = RequestMethod.POST)
	public @ResponseBody
	Response markInActiveCompositeProductVariant(@PathVariable("sessionId") String sessionId,@PathVariable("compositeProductUuid") String compositeProductUuid,
			HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{

				List<CompositeProduct> compositeProductList = compositeProductService.getAllCompositeProductsByUuid(compositeProductUuid,currentUser.getCompany().getCompanyId());
				for(CompositeProduct compositeProduct:compositeProductList){
					compositeProduct.setActiveIndicator(false);
					compositeProduct.setUserByUpdatedBy(currentUser);
					compositeProduct.setLastUpdated(new Date());
					compositeProductService.updateCompositeProduct(compositeProduct,Actions.DELETE,0,currentUser.getCompany().getCompanyId());
				}



				util.AuditTrail(request, currentUser, "ManageProductController.markInActiveCompositeProductVariant", 
						"User "+ currentUser.getUserEmail()+" Updated composite product variant  successfully ",false);
				return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.PRODUCTS);


			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "ManageProductController.markInActiveCompositeProductVariant",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	private Product processImage(ImageData data,Product product, HttpServletRequest request)
	{
		HttpSession session =  request.getSession(false);
		User currentUser = (User) session.getAttribute("user");
		String byteStr = data.getFile().substring(data.getFile().indexOf("base64,")+7, data.getFile().length());
		ServletContext servletContext = request.getSession().getServletContext();
		String relativeWebPath = "/resources/images/"+"Company_"+currentUser.getCompany().getCompanyId();

		try {
			String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);

			new File(absoluteDiskPath).mkdir();
			absoluteDiskPath = absoluteDiskPath + "/"+product.getProductUuid()+".jpg";
			//byte[] fileData = Base64.decodeBase64(byteStr);
			util.AuditTrail(request, currentUser, "Upload Image to Live Server",
					"Upload Image path :"+absoluteDiskPath + " Context Path: "+servletContext.getContextPath()
					+ "servletContext.getRealPath() "+ servletContext.getRealPath(relativeWebPath),false);



			byte[] fileData = Base64.decodeBase64(byteStr);
			try (OutputStream stream = new FileOutputStream(absoluteDiskPath)) {
				stream.write(fileData);


			}
			Configuration configuration = configurationService.getConfigurationByPropertyNameByCompanyId("DOCUMENTS_PATH",currentUser.getCompany().getCompanyId());
			util.AuditTrail(request, currentUser, "Backup of Image to Live Server",
					"Backup of Image path :"+configuration.getPropertyValue()+'/'+product.getProductUuid(),false);
			product.setImagePath("/app/resources/images/"+"Company_"+currentUser.getCompany().getCompanyId()+"/"+product.getProductUuid()+".jpg");

			String filepath = configuration.getPropertyValue() + "/Company_"+currentUser.getCompany().getCompanyId();
			new File(filepath).mkdir();
			OutputStream stream2 = new FileOutputStream( filepath+"/"+product.getProductUuid()+".jpg");
			stream2.write(fileData);
			stream2.close();

		} catch (Exception e) {
			e.printStackTrace();// logger.error(e.getMessage(),e);
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			util.AuditTrail(request, currentUser, "Error of Upload Image",
					"Error of Upload Image  :"+errors,true);

		}
		return product;
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
			stockOrder.setOrderNo("PO-"+ (month+"/"+ day+"/"+ year+ " " + hour + ":" + min));
			stockOrder.setOrdrRecvDate(dateFormat.format(new Date()));
			stockOrder.setStatusId("3"); 	// Completed		
			stockOrder.setStockOrderTypeId("1"); //Supplier Order
			stockOrder.setStockRefNo("PO-"+ (month+"/"+ day+"/"+ year+ " " + hour + ":" + min));
			stockOrder.setOutlet(stockOrderBean.getOutletId());
			//PurchaseOrderController purchaseOrderController = new PurchaseOrderController();
			Response response = purchaseOrderController.addStockOrder(sessionId, stockOrder, request);
			stockOrder.setStockOrderId(response.data.toString());
			//StockOrder Finish

			//StockOrderDetail Start
			for(StockOrderDetailBean stockOrderDetailBean : stockOrderDetailBeanList){
				stockOrderDetailBean.setStockOrderId(stockOrder.getStockOrderId());
				stockOrderDetailBean.setRecvProdQty(stockOrderDetailBean.getOrderProdQty());
				stockOrderDetailBean.setRecvSupplyPrice(stockOrderDetailBean.getOrdrSupplyPrice());
			}
			//PurchaseOrderDetailsController purchaseOrderDetailsController = new PurchaseOrderDetailsController();
			String total = grandTotal.toString();
			String items = itemCount.toString();
			purchaseOrderDetailsController.updateAndReceiveStockOrderDetails(sessionId, total, items, stockOrderDetailBeanList, request);
			//StockOrderDetail Finish
			added = true;
		}
		return added;
	}


	@SuppressWarnings("rawtypes")
	private boolean AddStockReturn(String sessionId, StockOrderBean stockOrderBean, List<StockOrderDetailBean> stockOrderDetailBeanList, Double grandTotal, Double itemCount, HttpServletRequest request)
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
			stockOrder.setOrderNo("SR-"+ (month+"/"+ day+"/"+ year+ " " + hour + ":" + min));
			stockOrder.setOrdrRecvDate(dateFormat.format(new Date()));
			stockOrder.setStatusId("3"); 	// Completed		
			stockOrder.setStockOrderTypeId("2"); //Stock Return
			stockOrder.setStockRefNo("SR-"+ (month+"/"+ day+"/"+ year+ " " + hour + ":" + min));
			stockOrder.setOutlet(stockOrderBean.getOutletId());
			stockOrder.setRetailPriceBill("false");
			//PurchaseOrderController purchaseOrderController = new PurchaseOrderController();
			Response response = purchaseOrderController.addStockOrder(sessionId, stockOrder, request);
			stockOrder.setStockOrderId(response.data.toString());
			//StockOrder Finish

			//StockOrderDetail Start
			for(StockOrderDetailBean stockOrderDetailBean : stockOrderDetailBeanList){
				stockOrderDetailBean.setStockOrderId(stockOrder.getStockOrderId());
				//stockOrderDetailBean.setRecvProdQty(stockOrderDetailBean.getOrderProdQty());
				//stockOrderDetailBean.setRecvSupplyPrice(stockOrderDetailBean.getOrdrSupplyPrice());
			}
			//PurchaseOrderDetailsController purchaseOrderDetailsController = new PurchaseOrderDetailsController();
			String total = grandTotal.toString();
			String items = itemCount.toString();
			purchaseOrderDetailsController.updateAndReturnStockOrderDetails(sessionId, total, items, stockOrderDetailBeanList, request);
			//StockOrderDetail Finish
			added = true;
		}
		return added;
	}
	
	private ProductPriceHistory getProductPriceHistoryFromProductPriceHistoryBean(ProductPriceHistoryBean productPriceHistoryBean,Product newProduct){
		try{
			ProductPriceHistory productPriceHistory = new ProductPriceHistory();
			productPriceHistory.setActiveIndicator(true);
			productPriceHistory.setAdditionalCustomDutyPrct(new BigDecimal(productPriceHistoryBean.getAdditionalCustomDutyPrct()));
			productPriceHistory.setAdditionalCustomDutyValue(new BigDecimal(productPriceHistoryBean.getAdditionalCustomDutyValue()));
			productPriceHistory.setAdditionalSalesTaxPrct(new BigDecimal(productPriceHistoryBean.getAdditionalSalesTaxPrct()));
			productPriceHistory.setAdditionalSalesTaxValue(new BigDecimal(productPriceHistoryBean.getAdditionalSalesTaxValue()));
			productPriceHistory.setCoCode(productPriceHistoryBean.getCoCode());
			productPriceHistory.setCompany(newProduct.getCompany());
			productPriceHistory.setCreatedDate(new Date());
			productPriceHistory.setCustomDutyValue(new BigDecimal(productPriceHistoryBean.getCustomDutyValue()));
			productPriceHistory.setCustomDutyPrct(new BigDecimal(productPriceHistoryBean.getCustomDutyPrct()));
			productPriceHistory.setDescription(productPriceHistoryBean.getDescription());
			productPriceHistory.setDollarRate(new BigDecimal(productPriceHistoryBean.getDollarRate()));
			productPriceHistory.setDTotalValueAssessed(new BigDecimal(productPriceHistoryBean.getdTotalValueAssessed()));
			productPriceHistory.setDTotalValueDeclared(new BigDecimal(productPriceHistoryBean.getdTotalValueDeclared()));
			productPriceHistory.setDUnitValueAssessed(new BigDecimal(productPriceHistoryBean.getdUnitValueAssessed()));
			productPriceHistory.setDUnitValueDeclared(new BigDecimal(productPriceHistoryBean.getdUnitValueDeclared()));
			productPriceHistory.setExcisePrct(new BigDecimal(productPriceHistoryBean.getExcisePrct()));
			productPriceHistory.setExciseValue(new BigDecimal(productPriceHistoryBean.getExciseValue()));
			productPriceHistory.setFtaPrct(new BigDecimal(productPriceHistoryBean.getFtaPrct()));
			productPriceHistory.setFtaValue(new BigDecimal(productPriceHistoryBean.getFtaValue()));
			productPriceHistory.setHsCode(productPriceHistoryBean.getHsCode());
			productPriceHistory.setItPrct(new BigDecimal(productPriceHistoryBean.getItPrct()));
			productPriceHistory.setItValue(new BigDecimal(productPriceHistoryBean.getItValue()));
			productPriceHistory.setLastUpdated(newProduct.getLastUpdated());
			productPriceHistory.setMarkupPrct(newProduct.getMarkupPrct());
			productPriceHistory.setNumberOfUnits(new BigDecimal(productPriceHistoryBean.getNumberOfUnits()));
			productPriceHistory.setOutlet(newProduct.getOutlet());
			productPriceHistory.setPCustomValueAssessed(new BigDecimal(productPriceHistoryBean.getpCustomValueAssessed()));
			productPriceHistory.setPCustomValueDeclared(new BigDecimal(productPriceHistoryBean.getpCustomValueDeclared()));
			productPriceHistory.setProductId(newProduct.getProductId());
			productPriceHistory.setProductUuid(newProduct.getProductUuid());
			/*productPriceHistory.setProductVariantId(productVariantId);
			productPriceHistory.setProductVariantUuid(productVariantUuid);*/
			productPriceHistory.setRegularityDutyPrct(new BigDecimal(productPriceHistoryBean.getRegularityDutyPrct()));
			productPriceHistory.setRegularityDutyValue(new BigDecimal(productPriceHistoryBean.getRegularityDutyValue()));
			productPriceHistory.setRemarks(productPriceHistoryBean.getRemarks());
			productPriceHistory.setSalesTax(newProduct.getSalesTax());
			productPriceHistory.setSalesTaxPrct(new BigDecimal(productPriceHistoryBean.getSalesTaxPrct()));
			productPriceHistory.setSalesTaxValue(new BigDecimal(productPriceHistoryBean.getSalesTaxValue()));
			productPriceHistory.setSroNumber(productPriceHistoryBean.getSroNumber());
			productPriceHistory.setSupplyPriceExclTax(newProduct.getSupplyPriceExclTax());
			productPriceHistory.setTotalValue(new BigDecimal(productPriceHistoryBean.getTotalValue()));
			productPriceHistory.setUserByCreatedBy(newProduct.getUserByCreatedBy());
			productPriceHistory.setUserByUpdatedBy(newProduct.getUserByUpdatedBy());
			productPriceHistory.setVrNumber(productPriceHistoryBean.getVrNumber());
			return productPriceHistory;
		}catch(Exception ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		
		
		return null;
	}
	
	public boolean createCompositeProductAndCompositeProductHistory(Product newProduct,ProductBean productBean,User currentUser,Map<Integer , Product> productMap ,Map<Integer,ProductVariant> productVariantMap){
		try{
			List<CompositeProduct> compositeProductList =  new ArrayList<>();
			List<ProductVariantBean> productList = productBean.getProductList();
			if(productList!=null && productList.size()>0){
				for(ProductVariantBean productVariantBean:productList){
					CompositeProduct compositeProduct = new CompositeProduct();
					compositeProduct.setActiveIndicator(true);
					compositeProduct.setUnitQuantity(Integer.valueOf(productVariantBean.getUniteQunatity()));
					if(productVariantBean.getCompositeQunatityConsumed()!=null && !productVariantBean.getCompositeQunatityConsumed().equalsIgnoreCase("")){
						compositeProduct.setCompositeQuantity(Integer.valueOf(productVariantBean.getCompositeQunatityConsumed()));
					}else{
						compositeProduct.setCompositeQuantity(0);
					}
					if(productVariantBean.getIsProduct().equalsIgnoreCase("false")){
						ProductVariant productVariant = productVariantMap.get(Integer.valueOf(productVariantBean.getProductVariantId()));
						compositeProduct.setProductVariant(productVariant);
					}
					Product selectiveProductId = productMap.get(Integer.valueOf(productVariantBean.getProductId()));
					compositeProduct.setProductBySelectiveProductAssociationId(selectiveProductId);
					
					compositeProduct.setCreatedDate(new Date());
					compositeProduct.setLastUpdated(new Date());
					compositeProduct.setCompositeProductUuid(newProduct.getProductUuid());
					compositeProduct.setProductUuid(selectiveProductId.getProductUuid());
					compositeProduct.setOutlet(newProduct.getOutlet());
					compositeProduct.setProductByProductAssocicationId(newProduct);	
					compositeProduct.setUserByCreatedBy(currentUser);
					compositeProduct.setUserByUpdatedBy(currentUser);
					compositeProduct.setCompany(currentUser.getCompany());
					compositeProductList.add(compositeProduct);
				}
			}
			
			compositeProductService.addCompositeProductList(compositeProductList);
			return true;
			
		}catch(Exception ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
			return false;
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void createSelfProcessOrder(Product newProduct,ProductBean productBean,User currentUser,Map<Integer , Product> productMap ,Map<Integer,ProductVariant> productVariantMap,
			String sessionId,HttpServletRequest request){
		try{
			List<ProductVariantBean> productList = productBean.getProductList();
			//Add Self StockOrder
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			StockOrderBean stockOrderBean = new StockOrderBean();
			stockOrderBean.setActiveIndicator("true");
			stockOrderBean.setAutofillReorder("true");
			stockOrderBean.setRetailPriceBill("false");
			stockOrderBean.setSupplierId(newProduct.getContact().getContactId()+"");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int min = cal.get(Calendar.MINUTE);
			stockOrderBean.setDiliveryDueDate(dateFormat.format(new Date()));			
			stockOrderBean.setOrderNo("SPOR-"+ (month+"/"+ day+"/"+ year+ " " + hour + ":" + min));
			stockOrderBean.setOrdrRecvDate(dateFormat.format(new Date()));
			stockOrderBean.setStatusId("3"); 	// Completed		
			stockOrderBean.setStockOrderTypeId("7"); //Self Process Order Remove
			stockOrderBean.setStockRefNo("SPOR-"+ (month+"/"+ day+"/"+ year+ " " + hour + ":" + min));
			stockOrderBean.setOutlet(newProduct.getOutlet().getOutletId()+"");
			stockOrderBean.setRemarks("Self Process Order Created for Composite Product having id: "+newProduct.getProductId()+" with Product Name: "+newProduct.getProductName());
			Response response = purchaseOrderController.addStockOrder(sessionId, stockOrderBean, request);
			stockOrderBean.setStockOrderId(response.data.toString());
			//Add Self StockOrder Details
			List<StockOrderDetail> stockOrderDetailsAddList = new ArrayList<>();
			List<Product> updateProductList =  new ArrayList<>();
			List<ProductVariant> updateProductVariantList = new ArrayList<>();
			if(productList!=null && productList.size()>0){
				for(ProductVariantBean productVariantBean:productList){
					StockOrderDetail stockOrderDetail = new StockOrderDetail();
					stockOrderDetail.setActiveIndicator(true);
					stockOrderDetail.setCompany(newProduct.getCompany());
					stockOrderDetail.setCreatedBy(currentUser.getUserId());
					if(productVariantBean.getIsProduct().equalsIgnoreCase("true")){
						stockOrderDetail.setIsProduct(true);
						Product product = productMap.get(Integer.valueOf(productVariantBean.getProductVariantId()));
						stockOrderDetail.setProduct(product);
						product.setCurrentInventory(product.getCurrentInventory()-Integer.valueOf(productVariantBean.getCompositeQunatityConsumed()));
						updateProductList.add(product);
						
					}else{
						stockOrderDetail.setIsProduct(false);
						ProductVariant productVariant = productVariantMap.get(Integer.valueOf(productVariantBean.getProductVariantId()));
						stockOrderDetail.setProductVariant(productVariant);
						productVariant.setCurrentInventory(productVariant.getCurrentInventory()-Integer.valueOf(productVariantBean.getCompositeQunatityConsumed()));
						updateProductVariantList.add(productVariant);
					}
					stockOrderDetail.setOrderProdQty(Integer.valueOf(productVariantBean.getCompositeQunatityConsumed()));
					stockOrderDetail.setOrdrSupplyPrice(new BigDecimal(productVariantBean.getSupplyPriceExclTax()));
					stockOrderDetail.setRecvProdQty(Integer.valueOf(productVariantBean.getCompositeQunatityConsumed()));
					stockOrderDetail.setRecvSupplyPrice(new BigDecimal(productVariantBean.getSupplyPriceExclTax()));
					stockOrderDetail.setRetailPrice(new BigDecimal(productVariantBean.getRetailPrice()));
					StockOrder stockOrder = new StockOrder();
					stockOrder.setStockOrderId(Integer.valueOf(stockOrderBean.getStockOrderId()));
					stockOrderDetail.setStockOrder(stockOrder);
					stockOrderDetailsAddList.add(stockOrderDetail);
				}
				stockOrderDetailService.addStockOrderDetailsList(stockOrderDetailsAddList, currentUser.getCompany().getCompanyId());
				productService.updateProductList(updateProductList, newProduct.getCompany());
				productVariantService.updateProductVariantList(updateProductVariantList, newProduct.getCompany());
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void createSelfProcessOrderRemove(Product newProduct,ProductBean productBean,User currentUser,Map<Integer , Product> productMap ,Map<Integer,ProductVariant> productVariantMap,
			String sessionId,HttpServletRequest request){
		try{
			List<ProductVariantBean> productList = productBean.getProductList();
			//Add Self StockOrder
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			StockOrderBean stockOrderBean = new StockOrderBean();
			stockOrderBean.setActiveIndicator("true");
			stockOrderBean.setAutofillReorder("true");
			stockOrderBean.setRetailPriceBill("false");
			stockOrderBean.setSupplierId(newProduct.getContact().getContactId()+"");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			int min = cal.get(Calendar.MINUTE);
			stockOrderBean.setDiliveryDueDate(dateFormat.format(new Date()));			
			stockOrderBean.setOrderNo("SPOA-"+ (month+"/"+ day+"/"+ year+ " " + hour + ":" + min));
			stockOrderBean.setOrdrRecvDate(dateFormat.format(new Date()));
			stockOrderBean.setStatusId("3"); 	// Completed		
			stockOrderBean.setStockOrderTypeId("6"); //Self Process Order Add
			stockOrderBean.setStockRefNo("SPOA-"+ (month+"/"+ day+"/"+ year+ " " + hour + ":" + min));
			stockOrderBean.setOutlet(newProduct.getOutlet().getOutletId()+"");
			stockOrderBean.setRemarks("Self Process Order Created for Composite Product having id: "+newProduct.getProductId()+" with Product Name: "+newProduct.getProductName());
			Response response = purchaseOrderController.addStockOrder(sessionId, stockOrderBean, request);
			stockOrderBean.setStockOrderId(response.data.toString());
			//Add Self StockOrder Details
			List<StockOrderDetail> stockOrderDetailsAddList = new ArrayList<>();
			List<Product> updateProductList =  new ArrayList<>();
			List<ProductVariant> updateProductVariantList = new ArrayList<>();
			if(productList!=null && productList.size()>0){
				for(ProductVariantBean productVariantBean:productList){
					StockOrderDetail stockOrderDetail = new StockOrderDetail();
					stockOrderDetail.setActiveIndicator(true);
					stockOrderDetail.setCompany(newProduct.getCompany());
					stockOrderDetail.setCreatedBy(currentUser.getUserId());
					if(productVariantBean.getIsProduct().equalsIgnoreCase("true")){
						stockOrderDetail.setIsProduct(true);
						Product product = productMap.get(Integer.valueOf(productVariantBean.getProductVariantId()));
						stockOrderDetail.setProduct(product);
						product.setCurrentInventory(product.getCurrentInventory()-Integer.valueOf(productVariantBean.getCompositeQunatityConsumed()));
						updateProductList.add(product);
						
					}else{
						stockOrderDetail.setIsProduct(false);
						ProductVariant productVariant = productVariantMap.get(Integer.valueOf(productVariantBean.getProductVariantId()));
						stockOrderDetail.setProductVariant(productVariant);
						productVariant.setCurrentInventory(productVariant.getCurrentInventory()-Integer.valueOf(productVariantBean.getCompositeQunatityConsumed()));
						updateProductVariantList.add(productVariant);
					}
					stockOrderDetail.setOrderProdQty(Integer.valueOf(productVariantBean.getCompositeQunatityConsumed()));
					stockOrderDetail.setOrdrSupplyPrice(new BigDecimal(productVariantBean.getSupplyPriceExclTax()));
					stockOrderDetail.setRecvProdQty(Integer.valueOf(productVariantBean.getCompositeQunatityConsumed()));
					stockOrderDetail.setRecvSupplyPrice(new BigDecimal(productVariantBean.getSupplyPriceExclTax()));
					stockOrderDetail.setRetailPrice(new BigDecimal(productVariantBean.getRetailPrice()));
					StockOrder stockOrder = new StockOrder();
					stockOrder.setStockOrderId(Integer.valueOf(stockOrderBean.getStockOrderId()));
					stockOrderDetail.setStockOrder(stockOrder);
					stockOrderDetailsAddList.add(stockOrderDetail);
				}
				stockOrderDetailService.addStockOrderDetailsList(stockOrderDetailsAddList, currentUser.getCompany().getCompanyId());
				productService.updateProductList(updateProductList, newProduct.getCompany());
				productVariantService.updateProductVariantList(updateProductVariantList, newProduct.getCompany());
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
	}


}
