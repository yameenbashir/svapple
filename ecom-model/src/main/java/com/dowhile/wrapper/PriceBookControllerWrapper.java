/**
 * 
 */
package com.dowhile.wrapper;

import java.util.List;
import java.util.Map;

import com.dowhile.ContactGroup;
import com.dowhile.Outlet;
import com.dowhile.PriceBook;
import com.dowhile.PriceBookDetail;
import com.dowhile.PriceBookDetailSummary;
import com.dowhile.Product;
import com.dowhile.ProductTag;
import com.dowhile.ProductVariant;
import com.dowhile.Tag;

/**
 * @author HafizYameenBashir
 *
 */
public class PriceBookControllerWrapper {
	
	private List<Outlet> outlets;
	private List<ContactGroup> customerGroupList;
	private List<PriceBook> priceBookList;
	private List<Product> productList;
	private List<ProductVariant> productVariantList;
	private List<PriceBookDetailSummary> priceBookDetailSummaryList;
	private List<ProductTag> productTags;
	private List<Tag> tagList;
	private Map<Integer, Product> productMap;
	private Map<Integer, ProductVariant> productVariantMap;
	private Map<Integer, PriceBookDetail> priceBookDetailMap;
	/**
	 * 
	 */
	public PriceBookControllerWrapper() {
	}
	/**
	 * @param outlets
	 * @param customerGroupList
	 * @param priceBookList
	 * @param productList
	 * @param productVariantList
	 * @param priceBookDetailSummaryList
	 * @param productTags
	 * @param tagList
	 * @param productMap
	 * @param productVariantMap
	 * @param priceBookDetailMap
	 */
	public PriceBookControllerWrapper(List<Outlet> outlets, List<ContactGroup> customerGroupList,
			List<PriceBook> priceBookList, List<Product> productList, List<ProductVariant> productVariantList,
			List<PriceBookDetailSummary> priceBookDetailSummaryList, List<ProductTag> productTags, List<Tag> tagList,
			Map<Integer, Product> productMap, Map<Integer, ProductVariant> productVariantMap,
			Map<Integer, PriceBookDetail> priceBookDetailMap) {
		this.outlets = outlets;
		this.customerGroupList = customerGroupList;
		this.priceBookList = priceBookList;
		this.productList = productList;
		this.productVariantList = productVariantList;
		this.priceBookDetailSummaryList = priceBookDetailSummaryList;
		this.productTags = productTags;
		this.tagList = tagList;
		this.productMap = productMap;
		this.productVariantMap = productVariantMap;
		this.priceBookDetailMap = priceBookDetailMap;
	}
	/**
	 * @return the outlets
	 */
	public List<Outlet> getOutlets() {
		return outlets;
	}
	/**
	 * @param outlets the outlets to set
	 */
	public void setOutlets(List<Outlet> outlets) {
		this.outlets = outlets;
	}
	/**
	 * @return the customerGroupList
	 */
	public List<ContactGroup> getCustomerGroupList() {
		return customerGroupList;
	}
	/**
	 * @param customerGroupList the customerGroupList to set
	 */
	public void setCustomerGroupList(List<ContactGroup> customerGroupList) {
		this.customerGroupList = customerGroupList;
	}
	/**
	 * @return the priceBookList
	 */
	public List<PriceBook> getPriceBookList() {
		return priceBookList;
	}
	/**
	 * @param priceBookList the priceBookList to set
	 */
	public void setPriceBookList(List<PriceBook> priceBookList) {
		this.priceBookList = priceBookList;
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
	/**
	 * @return the priceBookDetailSummaryList
	 */
	public List<PriceBookDetailSummary> getPriceBookDetailSummaryList() {
		return priceBookDetailSummaryList;
	}
	/**
	 * @param priceBookDetailSummaryList the priceBookDetailSummaryList to set
	 */
	public void setPriceBookDetailSummaryList(List<PriceBookDetailSummary> priceBookDetailSummaryList) {
		this.priceBookDetailSummaryList = priceBookDetailSummaryList;
	}
	/**
	 * @return the productTags
	 */
	public List<ProductTag> getProductTags() {
		return productTags;
	}
	/**
	 * @param productTags the productTags to set
	 */
	public void setProductTags(List<ProductTag> productTags) {
		this.productTags = productTags;
	}
	/**
	 * @return the tagList
	 */
	public List<Tag> getTagList() {
		return tagList;
	}
	/**
	 * @param tagList the tagList to set
	 */
	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}
	/**
	 * @return the productMap
	 */
	public Map<Integer, Product> getProductMap() {
		return productMap;
	}
	/**
	 * @param productMap the productMap to set
	 */
	public void setProductMap(Map<Integer, Product> productMap) {
		this.productMap = productMap;
	}
	/**
	 * @return the productVariantMap
	 */
	public Map<Integer, ProductVariant> getProductVariantMap() {
		return productVariantMap;
	}
	/**
	 * @param productVariantMap the productVariantMap to set
	 */
	public void setProductVariantMap(Map<Integer, ProductVariant> productVariantMap) {
		this.productVariantMap = productVariantMap;
	}
	/**
	 * @return the priceBookDetailMap
	 */
	public Map<Integer, PriceBookDetail> getPriceBookDetailMap() {
		return priceBookDetailMap;
	}
	/**
	 * @param priceBookDetailMap the priceBookDetailMap to set
	 */
	public void setPriceBookDetailMap(Map<Integer, PriceBookDetail> priceBookDetailMap) {
		this.priceBookDetailMap = priceBookDetailMap;
	}
}
