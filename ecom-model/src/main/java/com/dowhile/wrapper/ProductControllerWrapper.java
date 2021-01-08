/**
 * 
 */
package com.dowhile.wrapper;

import java.util.HashMap;
import java.util.List;

import com.dowhile.Brand;
import com.dowhile.Contact;
import com.dowhile.Outlet;
import com.dowhile.Product;
import com.dowhile.ProductHistory;
import com.dowhile.ProductType;
import com.dowhile.ProductVariant;
import com.dowhile.SalesTax;
import com.dowhile.Tag;
import com.dowhile.User;
import com.dowhile.VariantAttribute;

/**
 * @author HafizYameenBashir
 *
 */
public class ProductControllerWrapper {
	
	private List<Product> productList;
	private List<ProductVariant> productVariantList;
	private List<Contact> contactsList;
	private List<ProductType> productTypeList;
	private List<Brand> brandsList;
	private List<Outlet> outlets;
	private List<VariantAttribute> variantAttributesList;
	private List<Tag> tagsList;
	private List<User> usersList;
	private int sku;
	private int productVariantSku;
	private List<SalesTax> salesTaxlist;
	private HashMap<Integer,List<ProductHistory>> productHistoryMap;
	/**
	 * 
	 */
	public ProductControllerWrapper() {
	}
	/**
	 * @param productList
	 * @param productVariantList
	 * @param contactsList
	 * @param productTypeList
	 * @param brandsList
	 * @param outlets
	 * @param variantAttributesList
	 * @param tagsList
	 * @param usersList
	 * @param sku
	 * @param productVariantSku
	 * @param salesTaxlist
	 * @param productHistoryMap
	 */
	public ProductControllerWrapper(List<Product> productList, List<ProductVariant> productVariantList,
			List<Contact> contactsList, List<ProductType> productTypeList, List<Brand> brandsList, List<Outlet> outlets,
			List<VariantAttribute> variantAttributesList, List<Tag> tagsList, List<User> usersList, int sku,
			int productVariantSku, List<SalesTax> salesTaxlist,
			HashMap<Integer, List<ProductHistory>> productHistoryMap) {
		this.productList = productList;
		this.productVariantList = productVariantList;
		this.contactsList = contactsList;
		this.productTypeList = productTypeList;
		this.brandsList = brandsList;
		this.outlets = outlets;
		this.variantAttributesList = variantAttributesList;
		this.tagsList = tagsList;
		this.usersList = usersList;
		this.sku = sku;
		this.productVariantSku = productVariantSku;
		this.salesTaxlist = salesTaxlist;
		this.productHistoryMap = productHistoryMap;
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
	 * @return the contactsList
	 */
	public List<Contact> getContactsList() {
		return contactsList;
	}
	/**
	 * @param contactsList the contactsList to set
	 */
	public void setContactsList(List<Contact> contactsList) {
		this.contactsList = contactsList;
	}
	/**
	 * @return the productTypeList
	 */
	public List<ProductType> getProductTypeList() {
		return productTypeList;
	}
	/**
	 * @param productTypeList the productTypeList to set
	 */
	public void setProductTypeList(List<ProductType> productTypeList) {
		this.productTypeList = productTypeList;
	}
	/**
	 * @return the brandsList
	 */
	public List<Brand> getBrandsList() {
		return brandsList;
	}
	/**
	 * @param brandsList the brandsList to set
	 */
	public void setBrandsList(List<Brand> brandsList) {
		this.brandsList = brandsList;
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
	 * @return the variantAttributesList
	 */
	public List<VariantAttribute> getVariantAttributesList() {
		return variantAttributesList;
	}
	/**
	 * @param variantAttributesList the variantAttributesList to set
	 */
	public void setVariantAttributesList(List<VariantAttribute> variantAttributesList) {
		this.variantAttributesList = variantAttributesList;
	}
	/**
	 * @return the tagsList
	 */
	public List<Tag> getTagsList() {
		return tagsList;
	}
	/**
	 * @param tagsList the tagsList to set
	 */
	public void setTagsList(List<Tag> tagsList) {
		this.tagsList = tagsList;
	}
	/**
	 * @return the usersList
	 */
	public List<User> getUsersList() {
		return usersList;
	}
	/**
	 * @param usersList the usersList to set
	 */
	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
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
	 * @return the salesTaxlist
	 */
	public List<SalesTax> getSalesTaxlist() {
		return salesTaxlist;
	}
	/**
	 * @param salesTaxlist the salesTaxlist to set
	 */
	public void setSalesTaxlist(List<SalesTax> salesTaxlist) {
		this.salesTaxlist = salesTaxlist;
	}
	/**
	 * @return the productHistoryMap
	 */
	public HashMap<Integer, List<ProductHistory>> getProductHistoryMap() {
		return productHistoryMap;
	}
	/**
	 * @param productHistoryMap the productHistoryMap to set
	 */
	public void setProductHistoryMap(HashMap<Integer, List<ProductHistory>> productHistoryMap) {
		this.productHistoryMap = productHistoryMap;
	}
}
