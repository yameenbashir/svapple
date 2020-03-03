/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dowhile.frontend.mapping.bean.InventoryCountDetailBean;
import com.dowhile.frontend.mapping.bean.InventoryCountTypeBean;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.ProductVariantBean;


/**
 * @author Zafar Shakeel
 *
 */
public class InventoryCountControllerBean {
	
	private InventoryCountTypeBean inventoryCountTypeBean;
	private List<OutletBean> outletBeansList ;
	private List<InventoryCountTypeBean> inventoryCountTypeBeansList;
	private List<ProductVariantBean> productBeansList;
	private List<ProductVariantBean> productVariantBeansList;
	private List<ProductVariantBean> allProductBeansList;
	private List<ProductVariantBean> allProductVariantBeansList;
	private Map<String, ProductVariantBean> productVariantMap = new HashMap<>();
	private Map<String, ProductVariantBean> productMap = new HashMap<>();
	private Map<String, ProductVariantBean> allProductVariantMap = new HashMap<>();
	private Map<String, ProductVariantBean> allProductMap = new HashMap<>();
	private List<InventoryCountDetailBean> inventoryCountDetailBeansList;
	private boolean auditTransfer;
	/**
	 * 
	 */
	public InventoryCountControllerBean() {
	}
	/**
	 * @param inventoryCountTypeBean
	 * @param outletBeansList
	 * @param inventoryCountTypeBeansList
	 * @param productBeansList
	 * @param productVariantBeansList
	 * @param allProductBeansList
	 * @param allProductVariantBeansList
	 * @param inventoryCountDetailBeansList
	 * @param autoTransfer
	 */
	public InventoryCountControllerBean(
			InventoryCountTypeBean inventoryCountTypeBean,
			List<OutletBean> outletBeansList,
			List<InventoryCountTypeBean> inventoryCountTypeBeansList,
			List<ProductVariantBean> productBeansList,
			List<ProductVariantBean> productVariantBeansList,
			List<ProductVariantBean> allProductBeansList,
			List<ProductVariantBean> allProductVariantBeansList,
			List<InventoryCountDetailBean> inventoryCountDetailBeansList,
			boolean autoTransfer) {
		this.inventoryCountTypeBean = inventoryCountTypeBean;
		this.outletBeansList = outletBeansList;
		this.inventoryCountTypeBeansList = inventoryCountTypeBeansList;
		this.productBeansList = productBeansList;
		this.productVariantBeansList = productVariantBeansList;
		this.allProductBeansList = allProductBeansList;
		this.allProductVariantBeansList = allProductVariantBeansList;
		this.inventoryCountDetailBeansList = inventoryCountDetailBeansList;
		this.auditTransfer = auditTransfer;
	}
	/**
	 * @return the inventoryCountTypeBean
	 */
	public InventoryCountTypeBean getInventoryCountTypeBean() {
		return inventoryCountTypeBean;
	}
	/**
	 * @param inventoryCountTypeBean the inventoryCountTypeBean to set
	 */
	public void setInventoryCountTypeBean(
			InventoryCountTypeBean inventoryCountTypeBean) {
		this.inventoryCountTypeBean = inventoryCountTypeBean;
	}
	/**
	 * @return the outletBeansList
	 */
	public List<OutletBean> getOutletBeansList() {
		return outletBeansList;
	}
	/**
	 * @param outletBeansList the outletBeansList to set
	 */
	public void setOutletBeansList(List<OutletBean> outletBeansList) {
		this.outletBeansList = outletBeansList;
	}
	/**
	 * @return the inventoryCountTypeBeansList
	 */
	public List<InventoryCountTypeBean> getInventoryCountTypeBeansList() {
		return inventoryCountTypeBeansList;
	}
	/**
	 * @param inventoryCountTypeBeansList the inventoryCountTypeBeansList to set
	 */
	public void setInventoryCountTypeBeansList(
			List<InventoryCountTypeBean> inventoryCountTypeBeansList) {
		this.inventoryCountTypeBeansList = inventoryCountTypeBeansList;
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
	 * @return the allProductBeansList
	 */
	public List<ProductVariantBean> getAllProductBeansList() {
		return allProductBeansList;
	}
	/**
	 * @param allProductBeansList the allProductBeansList to set
	 */
	public void setAllProductBeansList(List<ProductVariantBean> allProductBeansList) {
		this.allProductBeansList = allProductBeansList;
	}
	/**
	 * @return the allProductVariantBeansList
	 */
	public List<ProductVariantBean> getAllProductVariantBeansList() {
		return allProductVariantBeansList;
	}
	/**
	 * @param allProductVariantBeansList the allProductVariantBeansList to set
	 */
	public void setAllProductVariantBeansList(
			List<ProductVariantBean> allProductVariantBeansList) {
		this.allProductVariantBeansList = allProductVariantBeansList;
	}
	/**
	 * @return the inventoryCountDetailBeansList
	 */
	public List<InventoryCountDetailBean> getInventoryCountDetailBeansList() {
		return inventoryCountDetailBeansList;
	}
	/**
	 * @param inventoryCountDetailBeansList the inventoryCountDetailBeansList to set
	 */
	public void setInventoryCountDetailBeansList(
			List<InventoryCountDetailBean> inventoryCountDetailBeansList) {
		this.inventoryCountDetailBeansList = inventoryCountDetailBeansList;
	}
	/**
	 * @return the autoTransfer
	 */
	public boolean isAuditTransfer() {
		return auditTransfer;
	}
	/**
	 * @param autoTransfer the autoTransfer to set
	 */
	public void setAuditTransfer(boolean autoTransfer) {
		this.auditTransfer = autoTransfer;
	}
	public Map<String, ProductVariantBean> getProductVariantMap() {
		return productVariantMap;
	}
	public void setProductVariantMap(Map<String, ProductVariantBean> productVariantMap) {
		this.productVariantMap = productVariantMap;
	}
	public Map<String, ProductVariantBean> getProductMap() {
		return productMap;
	}
	public void setProductMap(Map<String, ProductVariantBean> productMap) {
		this.productMap = productMap;
	}
	public Map<String, ProductVariantBean> getAllProductVariantMap() {
		return allProductVariantMap;
	}
	public void setAllProductVariantMap(Map<String, ProductVariantBean> allProductVariantMap) {
		this.allProductVariantMap = allProductVariantMap;
	}
	public Map<String, ProductVariantBean> getAllProductMap() {
		return allProductMap;
	}
	public void setAllProductMap(Map<String, ProductVariantBean> allProductMap) {
		this.allProductMap = allProductMap;
	}
}
