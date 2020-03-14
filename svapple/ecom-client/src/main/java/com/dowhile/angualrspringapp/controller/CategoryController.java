package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Product;
import com.dowhile.ProductType;
import com.dowhile.ProductVariant;
import com.dowhile.SalesTax;
import com.dowhile.VariantAttributeValues;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.HomeControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.ProductBean;
import com.dowhile.frontend.mapping.bean.ProductTypeBean;
import com.dowhile.frontend.mapping.bean.ProductVaraintDetailBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.service.CompanyService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductTypeService;
import com.dowhile.service.ProductVariantService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.SalesTaxService;
import com.dowhile.service.VariantAttributeService;
import com.dowhile.service.VariantAttributeValuesService;
import com.dowhile.service.util.ServiceUtil;

/**
 * Zafar Shakeel
 */
@Controller
@RequestMapping("/category")
public class CategoryController {
	  private String[] colorsData = { "#4caf50", "#C0C0C0", "#CDDC39", "#03A9F4",
				"#FA8072", "#39cccc", "#f39c12", "#ff851b", "#00a65a", "#01ff70",
				"#dd4b39", "#605ca8", "#f012be", "#777", "#001f3f", "#F5DEB3",
				"#F5DEB3", "#EE82EE", "#40E0D0", "#FF6347", "#D8BFD8", "#008080",
				"#D2B48C", "#4682B4", "#00FF7F", "#708090", "#6A5ACD", "#87CEEB",
				"#C0C0C0", "#A0522D", "#2E8B57", "#F4A460", "#FA8072", "#FF9800",
				"#03A9F4", "#CDDC39", "#39cccc", "#f39c12", "#ff851b", "#00a65a",
				"#01ff70", "#dd4b39", "#605ca8", "#f012be", "#777", "#001f3f",
				"#F5DEB3", "#F5DEB3", "#EE82EE", "#40E0D0", "#FF6347", "#D8BFD8",
				"#008080", "#D2B48C", "#4682B4", "#00FF7F", "#708090", "#6A5ACD",
				"#87CEEB", "#C0C0C0", "#A0522D", "#2E8B57", "#CDDC39", "#FA8072",
				"#FF9800", "#03A9F4", "#CDDC39", "#39cccc", "#f39c12", "#ff851b",
				"#00a65a", "#01ff70", "#dd4b39", "#605ca8", "#f012be", "#777",
				"#001f3f", "#F5DEB3", "#F5DEB3", "#EE82EE", "#40E0D0", "#FF6347",
				"#D8BFD8", "#008080", "#D2B48C", "#4682B4", "#00FF7F", "#708090",
				"#6A5ACD", "#87CEEB", "#C0C0C0", "#A0522D", "#2E8B57", "#F4A460",
				"#FA8072", "#FF9800", "#03A9F4", "#CDDC39", "#39cccc", "#f39c12",
				"#ff851b", "#00a65a", "#01ff70", "#dd4b39", "#605ca8", "#f012be",
				"#777", "#001f3f", "#F5DEB3", "#F5DEB3", "#EE82EE", "#40E0D0",
				"#FF6347", "#D8BFD8", "#008080", "#D2B48C", "#4682B4", "#00FF7F",
				"#708090", "#FF9800", "#87CEEB", "#C0C0C0", "#A0522D", "#2E8B57",
				"#F4A460", "#FA8072" };
	@Resource
	private ResourceService resourceService;
	@Resource
	private ServiceUtil util;
	@Resource
	private ProductTypeService productTypeService;
	@Resource
	private ProductService productService;
	@Resource
	private CompanyService companyService;

	@Resource
	private VariantAttributeService variantAttributeService;

	@Resource
	private VariantAttributeValuesService variantAttributeValuesService;

	@Resource
	private SalesTaxService salesTaxService;

	@Resource
	private ProductVariantService productVariantService;
	

	@RequestMapping("/layout")
	public String getCategoryControllerPartialPage(ModelMap modelMap) {
		return "category/layout";
	}


	@RequestMapping(value = "/getAllProducts/{category}/{categoryId}", method = RequestMethod.POST)
	public @ResponseBody
	List<ProductBean>  getAllProducts(HttpServletRequest request,@PathVariable("category") String category,@PathVariable("categoryId") int categoryId) {
		

		List<ProductBean> productsBean = new ArrayList<>();
		List<Product> products = new ArrayList<>();
		List<ProductBean> displayProductsBean = new ArrayList<>();
//		List<CustomerBean> customersBeans = new ArrayList<>();
//		byte[] bytes = null;
//		ZipData zipData = new ZipData();
		int companyId =1;
		int outlet =1;
		try {
				List<VariantAttributeValues> variantAttributeValues = new ArrayList<VariantAttributeValues>();

				variantAttributeValues = variantAttributeValuesService.getAllVariantAttributeValues(companyId);
				
				List<ProductVariant> productsProductVariants = new ArrayList<>();
				Map<Integer, List<ProductVariant>> productVariantsMap = new HashMap<>();
				productsProductVariants = productVariantService.getAllProductVariantsByOutletId(outlet, companyId);
				if(productsProductVariants!=null){
					for(ProductVariant productVariant:productsProductVariants){
						List<ProductVariant> productVariants = productVariantsMap.get(productVariant.getProduct().getProductId());
						if(productVariants!=null){
							productVariants.add(productVariant);
							productVariantsMap.put(productVariant.getProduct().getProductId(), productVariants);
						}else{
							productVariants = new ArrayList<>();
							productVariants.add(productVariant);
							productVariantsMap.put(productVariant.getProduct().getProductId(), productVariants);
						}

					}
				}
				List<SalesTax> salesTaxs = new ArrayList<>();
				Map<Integer, SalesTax> salesTaxMap = new HashMap<>();
				salesTaxs = salesTaxService.GetAllSalesTax(companyId);
				if(salesTaxs!=null){
					for(SalesTax salesTax:salesTaxs){
						salesTaxMap.put(salesTax.getSalesTaxId(), salesTax);
					}
				}
				products = productService.getTopProductsByCategory(companyId, categoryId,6,0);
				int count = 0;
				if (products != null) {
					for (Product product : products) {
						count = count + 1;

						ProductBean productBean = new ProductBean();
						productBean.setCategory(category);
						BigDecimal supplyPrice = product
								.getSupplyPriceExclTax();

						BigDecimal markupPrct = product.getMarkupPrct();
						SalesTax tax = salesTaxMap.get(product.getSalesTax().getSalesTaxId());
						BigDecimal retailPriceExclusiveTax = supplyPrice.add(
								supplyPrice.multiply(markupPrct).divide(
										new BigDecimal(100))).setScale(2,
												RoundingMode.HALF_EVEN);

						BigDecimal taxAmount = retailPriceExclusiveTax
								.multiply(
										new BigDecimal(tax
												.getSalesTaxPercentage()))
												.divide(new BigDecimal(100))
												.setScale(2, RoundingMode.HALF_EVEN);

						BigDecimal retailPriceInclusiveTax = retailPriceExclusiveTax
								.add(taxAmount);


						productBean.setTaxAmount(taxAmount.toString());						

						productBean
						.setRetailPriceInclTax(retailPriceInclusiveTax
								.toString());
						productBean
						.setRetailPriceExclTax(retailPriceExclusiveTax
								.toString());
						productBean.setProductName(product.getProductName());
						productBean.setSku(product.getSku());
						productBean.setProductDesc(product.getProductDesc());
						productBean.setProductHandler(product
								.getProductHandler());
						productBean.setProductId(product.getProductId()
								.toString());
						productBean.setVarientProducts(product.getVariantProducts());

						productBean.setProductId(product.getProductId().toString());
						if(product.getCurrentInventory() != null)
						{
							productBean.setCurrentInventory(product.getCurrentInventory().toString());
						}

						productBean.setOrignalPrice(product.getSupplyPriceExclTax()
								.setScale(2, RoundingMode.HALF_EVEN).toString());
						BigDecimal netPrice = (product.getSupplyPriceExclTax()).multiply(product.getMarkupPrct().divide(new BigDecimal(100))).add(product.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
						BigDecimal newNetPrice =netPrice.setScale(2,RoundingMode.HALF_EVEN);

						productBean.setNetPrice(newNetPrice.toString());


						List<ProductVariant> productVarients = productVariantsMap.get(product.getProductId()) ;
						List<ProductVariantBean> productVariantsBeans = new ArrayList<>();
						if(productVarients!=null){
							for (ProductVariant item : productVarients) {
								ProductVariantBean productVarientBean = new ProductVariantBean();
								productVarientBean.setSku(item.getSku());
								productVarientBean.setVarientProducts(product.getVariantProducts());
								productVarientBean.setProductVariantId(item
										.getProductVariantId().toString());
								productVarientBean.setVariantAttributeName(item
										.getVariantAttributeName().toString());
								if (item.getCurrentInventory() != null) {
									productVarientBean.setCurrentInventory(item
											.getCurrentInventory().toString());

								}
								productVarientBean.setProductId(product.getProductId()
										.toString());
								productVarientBean.setProductName(product
										.getProductName());
								productVarientBean.setMarkupPrct(item
										.getMarkupPrct().toString());

								BigDecimal productVarientmarkupPrct = item
										.getMarkupPrct();
								SalesTax varientTax = salesTaxMap.get(product.getSalesTax().getSalesTaxId());;

								BigDecimal retailVarPriceExclusiveTax = item
										.getSupplyPriceExclTax()
										.add( item.getSupplyPriceExclTax().multiply(
												productVarientmarkupPrct).divide(
														new BigDecimal(100)))
														.setScale(2, RoundingMode.HALF_EVEN);

								BigDecimal varientTaxAmount = retailPriceExclusiveTax
										.multiply(
												new BigDecimal(varientTax
														.getSalesTaxPercentage()))
														.divide(new BigDecimal(100))
														.setScale(2, RoundingMode.HALF_EVEN);

								productVarientBean
								.setRetailPriceExclTax(retailVarPriceExclusiveTax
										.toString());
								productVarientBean.setTax(varientTaxAmount
										.toString());

								productVarientBean.setOrignalPrice(item.getSupplyPriceExclTax()
										.setScale(2, RoundingMode.HALF_EVEN).toString());

								BigDecimal netPriceVar = (item.getSupplyPriceExclTax()).multiply(item.getMarkupPrct().divide(new BigDecimal(100))).add(item.getSupplyPriceExclTax()).setScale(5,RoundingMode.HALF_EVEN);
								BigDecimal newNetPriceVar =netPriceVar.setScale(2,RoundingMode.HALF_EVEN);

								productVarientBean.setNetPrice(newNetPriceVar.toString());


								productVariantsBeans.add(productVarientBean);
								productBean.setProductVariantsBeans(productVariantsBeans);

							}
						}

						ProductVaraintDetailBean productVaraintDetailBean = new ProductVaraintDetailBean();
						if(productVarients!=null && productVarients.size()>0){
							if (productVarients.get(0).getVariantAttributeByVariantAttributeAssocicationId1() != null) {
								if( productVarients.get(productVarients.size()-1).getVariantAttributeByVariantAttributeAssocicationId1()!=null){
									productVaraintDetailBean.setArrtibute1Values(findUniqeVariant(variantAttributeValues, productVarients.get(productVarients.size()-1).getVariantAttributeByVariantAttributeAssocicationId1().getVariantAttributeId(), product.getProductUuid()));
								}
							}

							if (productVarients.get(0).getVariantAttributeByVariantAttributeAssocicationId2() != null) {
								if(productVarients.get(productVarients.size()-1).getVariantAttributeByVariantAttributeAssocicationId2()!=null){
									productVaraintDetailBean.setArrtibute2Values(findUniqeVariant(variantAttributeValues, productVarients.get(productVarients.size()-1).getVariantAttributeByVariantAttributeAssocicationId2().getVariantAttributeId(), product.getProductUuid()));
									
								}
							}
							if (productVarients.get(0).getVariantAttributeByVariantAttributeAssocicationId3() != null) {
								if( productVarients.get(productVarients.size()-1).getVariantAttributeByVariantAttributeAssocicationId3()!=null){
								productVaraintDetailBean.setArrtibute3Values(findUniqeVariant(variantAttributeValues, productVarients.get(productVarients.size()-1).getVariantAttributeByVariantAttributeAssocicationId3().getVariantAttributeId(), product.getProductUuid()));
								}

							}
						}
						productBean
						.setProductVaraintDetailBean(productVaraintDetailBean);

						productsBean.add(productBean);
						if (product.getDisplay() != null
								&& product.getDisplay()) {
							productBean
							.setCss("background: #f6f6f6; margin-left: 5px; border-top: 6px solid "
									+ colorsData[count%70] + ";");
							productBean.setDisplay(product.getDisplay()
									.toString());
							displayProductsBean.add(productBean);
						}

					}
//					util.AuditTrail(request, currentUser,
//							"SellController.getAllProducts", "User "
//									+ currentUser.getUserEmail()
//									+ " retrived all products successfully ",
//									false);
					
//					sellControllerBean.setPriceBookBean(GetallValidPricebooks(currentUser, 0));

				} else {
//					util.AuditTrail(request, currentUser,
//							"SellController.getAllProducts",
//							" products are not found requested by User "
//									+ currentUser.getUserEmail(), false);
				}
	//			sellControllerBean.setDisplayProductsBean(displayProductsBean);
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				GZIPOutputStream gzipOut = new GZIPOutputStream(baos);
//				ObjectOutputStream objectOut = new ObjectOutputStream(gzipOut);
//				objectOut.writeObject(sellControllerBean);
//				objectOut.close();
//				 bytes = baos.toByteArray();
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				new ObjectOutputStream( baos ).writeObject( sellControllerBean );
//				byte[] apacheBytes =  org.apache.commons.codec.binary.Base64.encodeBase64(baos.toByteArray());
//			    String fromApacheBytes = new String(apacheBytes);
//				zipData.setData(new String(bytes, "UTF-16"));
//			    zipData.setData(fromApacheBytes);
				
				return productsBean;
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
//				util.AuditTrail(request, currentUser,
//						"SellController.getAllProducts", "Error Occured "
//								+ errors.toString(), true);
				return productsBean;

			}

		
	
		
		
	}
	public List<String> findUniqeVariant(List<VariantAttributeValues> VariantAttributeValues, int attributeId, String productVariantId){
		
		List<String> strings = new ArrayList<String>();
		for(VariantAttributeValues attributeValues : VariantAttributeValues){
			if(attributeValues.getVariantAttribute().getVariantAttributeId() == attributeId && attributeValues.getProductUuid().equals(productVariantId)){
				strings.add(attributeValues.getAttributeValue());
			}
		}
		Set<String> hs = new HashSet<>();
		hs.addAll(strings);
		strings.clear();
		strings.addAll(hs);
		return strings;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getHomeData", method = RequestMethod.POST)
	public @ResponseBody Response getHomeData(HttpServletRequest request) {

		List<ProductTypeBean> productTypeBeanList = new ArrayList<>();
		List<ProductType> productTypeList = null;
		int companyId = 1;
		HomeControllerBean homeControllerBean = new HomeControllerBean();
		try {

				productTypeList = productTypeService.getAllProductTypes(companyId);
				if (productTypeList != null) {
					for (ProductType productType : productTypeList) {

						ProductTypeBean productTypeBean = new ProductTypeBean();
						productTypeBean.setProductTypeId(productType.getProductTypeId().toString());
						productTypeBean.setProductTypeName(productType.getProductTypeName());						
						productTypeBean.setNumberOfProducts(String.valueOf(productService.getCountOfProductsByProductTypeId(productType.getProductTypeId(),companyId)));
						productTypeBeanList.add(productTypeBean);

					}
					homeControllerBean.setProductTypeBeanList(productTypeBeanList);
//					util.AuditTrail(request, currentUser, "ProductTypeController.getAllProductTypes", 
//							"User "+ currentUser.getUserEmail()+" retrived all Product Types successfully ",false);
					return new Response(homeControllerBean, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
//					util.AuditTrail(request, currentUser, "ProductTypeController.getAllProductTypes", 
//							" Product Types are not found requested by User "+currentUser.getUserEmail(),false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
//				util.AuditTrail(request, currentUser, "ProductTypeController.getAllProductTypes",
//						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		

	}

}

