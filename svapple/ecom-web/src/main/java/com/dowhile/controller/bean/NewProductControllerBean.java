/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;
import java.util.Map;

import com.dowhile.frontend.configuration.bean.ProductConfigurationBean;
import com.dowhile.frontend.mapping.bean.BrandBean;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.ProductBean;
import com.dowhile.frontend.mapping.bean.ProductTypeBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.frontend.mapping.bean.SupplierBean;
import com.dowhile.frontend.mapping.bean.TagBean;
import com.dowhile.frontend.mapping.bean.VarientAttributeBean;

/**
 * @author Yameen Bashir
 *
 */
public class NewProductControllerBean {
	
	private List<SupplierBean> supplierBeans;
	private List<ProductTypeBean> productTypeBeanList;
	private List<BrandBean> brandBeanList;
	private List<OutletBean> outletBeans;
	private List<VarientAttributeBean> variantAttributeBeanList;
	private List<ProductBean> productBeanList;
	private ProductBean productBean;
	private List<ProductVariantBean> productVariantBeanList;
	private int sku;
	private int productVariantSku;
	private String productTemplateForAllOutlets;
	private Map<String ,Boolean> productBarCodeMap;
	private Map<String ,Boolean> productVariantBarCodeMap;
	List<TagBean> tagBeanList;
	private String showProductTag;
	private ProductConfigurationBean productConfigurationBean;
	private String showDutyCalculator;
	
	/**
	 * 
	 */
	public NewProductControllerBean() {
	}

	/**
	 * @param supplierBeans
	 * @param productTypeBeanList
	 * @param brandBeanList
	 * @param outletBeans
	 * @param variantAttributeBeanList
	 * @param productBeanList
	 * @param productBean
	 * @param productVariantBeanList
	 * @param sku
	 * @param productVariantSku
	 * @param productTemplateForAllOutlets
	 * @param productBarCodeMap
	 * @param productVariantBarCodeMap
	 * @param tagBeanList
	 * @param showProductTag
	 * @param productConfigurationBean
	 * @param showDutyCalculator
	 */
	public NewProductControllerBean(List<SupplierBean> supplierBeans,
			List<ProductTypeBean> productTypeBeanList,
			List<BrandBean> brandBeanList, List<OutletBean> outletBeans,
			List<VarientAttributeBean> variantAttributeBeanList,
			List<ProductBean> productBeanList, ProductBean productBean,
			List<ProductVariantBean> productVariantBeanList, int sku,
			int productVariantSku, String productTemplateForAllOutlets,
			Map<String, Boolean> productBarCodeMap,
			Map<String, Boolean> productVariantBarCodeMap,
			List<TagBean> tagBeanList, String showProductTag,
			ProductConfigurationBean productConfigurationBean,
			String showDutyCalculator) {
		this.supplierBeans = supplierBeans;
		this.productTypeBeanList = productTypeBeanList;
		this.brandBeanList = brandBeanList;
		this.outletBeans = outletBeans;
		this.variantAttributeBeanList = variantAttributeBeanList;
		this.productBeanList = productBeanList;
		this.productBean = productBean;
		this.productVariantBeanList = productVariantBeanList;
		this.sku = sku;
		this.productVariantSku = productVariantSku;
		this.productTemplateForAllOutlets = productTemplateForAllOutlets;
		this.productBarCodeMap = productBarCodeMap;
		this.productVariantBarCodeMap = productVariantBarCodeMap;
		this.tagBeanList = tagBeanList;
		this.showProductTag = showProductTag;
		this.productConfigurationBean = productConfigurationBean;
		this.showDutyCalculator = showDutyCalculator;
	}

	/**
	 * @return the supplierBeans
	 */
	public List<SupplierBean> getSupplierBeans() {
		return supplierBeans;
	}

	/**
	 * @param supplierBeans the supplierBeans to set
	 */
	public void setSupplierBeans(List<SupplierBean> supplierBeans) {
		this.supplierBeans = supplierBeans;
	}

	/**
	 * @return the productTypeBeanList
	 */
	public List<ProductTypeBean> getProductTypeBeanList() {
		return productTypeBeanList;
	}

	/**
	 * @param productTypeBeanList the productTypeBeanList to set
	 */
	public void setProductTypeBeanList(List<ProductTypeBean> productTypeBeanList) {
		this.productTypeBeanList = productTypeBeanList;
	}

	/**
	 * @return the brandBeanList
	 */
	public List<BrandBean> getBrandBeanList() {
		return brandBeanList;
	}

	/**
	 * @param brandBeanList the brandBeanList to set
	 */
	public void setBrandBeanList(List<BrandBean> brandBeanList) {
		this.brandBeanList = brandBeanList;
	}

	/**
	 * @return the outletBeans
	 */
	public List<OutletBean> getOutletBeans() {
		return outletBeans;
	}

	/**
	 * @param outletBeans the outletBeans to set
	 */
	public void setOutletBeans(List<OutletBean> outletBeans) {
		this.outletBeans = outletBeans;
	}

	/**
	 * @return the variantAttributeBeanList
	 */
	public List<VarientAttributeBean> getVariantAttributeBeanList() {
		return variantAttributeBeanList;
	}

	/**
	 * @param variantAttributeBeanList the variantAttributeBeanList to set
	 */
	public void setVariantAttributeBeanList(
			List<VarientAttributeBean> variantAttributeBeanList) {
		this.variantAttributeBeanList = variantAttributeBeanList;
	}

	/**
	 * @return the productBeanList
	 */
	public List<ProductBean> getProductBeanList() {
		return productBeanList;
	}

	/**
	 * @param productBeanList the productBeanList to set
	 */
	public void setProductBeanList(List<ProductBean> productBeanList) {
		this.productBeanList = productBeanList;
	}

	/**
	 * @return the productBean
	 */
	public ProductBean getProductBean() {
		return productBean;
	}

	/**
	 * @param productBean the productBean to set
	 */
	public void setProductBean(ProductBean productBean) {
		this.productBean = productBean;
	}

	/**
	 * @return the productVariantBeanList
	 */
	public List<ProductVariantBean> getProductVariantBeanList() {
		return productVariantBeanList;
	}

	/**
	 * @param productVariantBeanList the productVariantBeanList to set
	 */
	public void setProductVariantBeanList(
			List<ProductVariantBean> productVariantBeanList) {
		this.productVariantBeanList = productVariantBeanList;
	}

	/**
	 * @return the sku
	 */
	public int getSku() {
		return sku;
	}

	/**
	 * @param sku the sku to set
	 */
	public void setSku(int sku) {
		this.sku = sku;
	}

	/**
	 * @return the productVariantSku
	 */
	public int getProductVariantSku() {
		return productVariantSku;
	}

	/**
	 * @param productVariantSku the productVariantSku to set
	 */
	public void setProductVariantSku(int productVariantSku) {
		this.productVariantSku = productVariantSku;
	}

	/**
	 * @return the productTemplateForAllOutlets
	 */
	public String getProductTemplateForAllOutlets() {
		return productTemplateForAllOutlets;
	}

	/**
	 * @param productTemplateForAllOutlets the productTemplateForAllOutlets to set
	 */
	public void setProductTemplateForAllOutlets(String productTemplateForAllOutlets) {
		this.productTemplateForAllOutlets = productTemplateForAllOutlets;
	}

	/**
	 * @return the productBarCodeMap
	 */
	public Map<String, Boolean> getProductBarCodeMap() {
		return productBarCodeMap;
	}

	/**
	 * @param productBarCodeMap the productBarCodeMap to set
	 */
	public void setProductBarCodeMap(Map<String, Boolean> productBarCodeMap) {
		this.productBarCodeMap = productBarCodeMap;
	}

	/**
	 * @return the productVariantBarCodeMap
	 */
	public Map<String, Boolean> getProductVariantBarCodeMap() {
		return productVariantBarCodeMap;
	}

	/**
	 * @param productVariantBarCodeMap the productVariantBarCodeMap to set
	 */
	public void setProductVariantBarCodeMap(
			Map<String, Boolean> productVariantBarCodeMap) {
		this.productVariantBarCodeMap = productVariantBarCodeMap;
	}

	/**
	 * @return the tagBeanList
	 */
	public List<TagBean> getTagBeanList() {
		return tagBeanList;
	}

	/**
	 * @param tagBeanList the tagBeanList to set
	 */
	public void setTagBeanList(List<TagBean> tagBeanList) {
		this.tagBeanList = tagBeanList;
	}

	/**
	 * @return the showProductTag
	 */
	public String getShowProductTag() {
		return showProductTag;
	}

	/**
	 * @param showProductTag the showProductTag to set
	 */
	public void setShowProductTag(String showProductTag) {
		this.showProductTag = showProductTag;
	}

	/**
	 * @return the productConfigurationBean
	 */
	public ProductConfigurationBean getProductConfigurationBean() {
		return productConfigurationBean;
	}

	/**
	 * @param productConfigurationBean the productConfigurationBean to set
	 */
	public void setProductConfigurationBean(
			ProductConfigurationBean productConfigurationBean) {
		this.productConfigurationBean = productConfigurationBean;
	}

	/**
	 * @return the showDutyCalculator
	 */
	public String getShowDutyCalculator() {
		return showDutyCalculator;
	}

	/**
	 * @param showDutyCalculator the showDutyCalculator to set
	 */
	public void setShowDutyCalculator(String showDutyCalculator) {
		this.showDutyCalculator = showDutyCalculator;
	}
}
