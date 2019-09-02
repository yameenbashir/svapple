/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.frontend.mapping.bean.CustomerGroupBean;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.PriceBookBean;
import com.dowhile.frontend.mapping.bean.ProductTagBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;
import com.dowhile.frontend.mapping.bean.TagBean;

/**
 * @author Yameen Bashir
 *
 */
public class NewPriceBookControllerBean {
	
	private List<OutletBean> outletBeans;
	private List<CustomerGroupBean> customerGroupBeansList;
	private PriceBookBean priceBookBean;
	private List<ProductVariantBean> productBeansList;
	private List<ProductVariantBean> productVariantBeansList;
	private List<ProductVariantBean> priceBookProductVariantBeansList;
	private List<ProductTagBean> productTagBeanList;
	private List<TagBean> tagBeanList;
	private String showProductTag;
	/**
	 * 
	 */
	public NewPriceBookControllerBean() {
	}
	/**
	 * @param outletBeans
	 * @param customerGroupBeansList
	 * @param priceBookBean
	 * @param productBeansList
	 * @param productVariantBeansList
	 * @param priceBookProductVariantBeansList
	 * @param productTagBeanList
	 * @param tagBeanList
	 * @param showProductTag
	 */
	public NewPriceBookControllerBean(List<OutletBean> outletBeans,
			List<CustomerGroupBean> customerGroupBeansList,
			PriceBookBean priceBookBean,
			List<ProductVariantBean> productBeansList,
			List<ProductVariantBean> productVariantBeansList,
			List<ProductVariantBean> priceBookProductVariantBeansList,
			List<ProductTagBean> productTagBeanList, List<TagBean> tagBeanList,
			String showProductTag) {
		this.outletBeans = outletBeans;
		this.customerGroupBeansList = customerGroupBeansList;
		this.priceBookBean = priceBookBean;
		this.productBeansList = productBeansList;
		this.productVariantBeansList = productVariantBeansList;
		this.priceBookProductVariantBeansList = priceBookProductVariantBeansList;
		this.productTagBeanList = productTagBeanList;
		this.tagBeanList = tagBeanList;
		this.showProductTag = showProductTag;
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
	 * @return the customerGroupBeansList
	 */
	public List<CustomerGroupBean> getCustomerGroupBeansList() {
		return customerGroupBeansList;
	}
	/**
	 * @param customerGroupBeansList the customerGroupBeansList to set
	 */
	public void setCustomerGroupBeansList(
			List<CustomerGroupBean> customerGroupBeansList) {
		this.customerGroupBeansList = customerGroupBeansList;
	}
	/**
	 * @return the priceBookBean
	 */
	public PriceBookBean getPriceBookBean() {
		return priceBookBean;
	}
	/**
	 * @param priceBookBean the priceBookBean to set
	 */
	public void setPriceBookBean(PriceBookBean priceBookBean) {
		this.priceBookBean = priceBookBean;
	}
	/**
	 * @return the productBeansList
	 */
	public List<ProductVariantBean> getProductBeansList() {
		return productBeansList;
	}
	/**
	 * @param productBeansList the productBeansList to set
	 */
	public void setProductBeansList(List<ProductVariantBean> productBeansList) {
		this.productBeansList = productBeansList;
	}
	/**
	 * @return the productVariantBeansList
	 */
	public List<ProductVariantBean> getProductVariantBeansList() {
		return productVariantBeansList;
	}
	/**
	 * @param productVariantBeansList the productVariantBeansList to set
	 */
	public void setProductVariantBeansList(
			List<ProductVariantBean> productVariantBeansList) {
		this.productVariantBeansList = productVariantBeansList;
	}
	/**
	 * @return the priceBookProductVariantBeansList
	 */
	public List<ProductVariantBean> getPriceBookProductVariantBeansList() {
		return priceBookProductVariantBeansList;
	}
	/**
	 * @param priceBookProductVariantBeansList the priceBookProductVariantBeansList to set
	 */
	public void setPriceBookProductVariantBeansList(
			List<ProductVariantBean> priceBookProductVariantBeansList) {
		this.priceBookProductVariantBeansList = priceBookProductVariantBeansList;
	}
	/**
	 * @return the productTagBeanList
	 */
	public List<ProductTagBean> getProductTagBeanList() {
		return productTagBeanList;
	}
	/**
	 * @param productTagBeanList the productTagBeanList to set
	 */
	public void setProductTagBeanList(List<ProductTagBean> productTagBeanList) {
		this.productTagBeanList = productTagBeanList;
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
}
