/**
 * 
 */
package com.dowhile.util;

import java.util.Map;

import com.dowhile.Configuration;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.frontend.configuration.bean.ProductConfigurationBean;

/**
 * @author Hafiz Bashir
 *
 */
public class ConfigurationUtil {

	public static ProductConfigurationBean setProductConifurationsFromConfigurationMap(Map<String, Configuration> congigurationMap){
		
		
		ProductConfigurationBean productConfigurationBean = new ProductConfigurationBean();
		try{
			if(congigurationMap.get("PRODUCT_MARKUP").getPropertyValue()!=null && congigurationMap.get("PRODUCT_MARKUP").getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.FALSE)){
				productConfigurationBean.setMarkupPrct(false);
			}else{
				productConfigurationBean.setMarkupPrct(true);
			}
			if(congigurationMap.get("PRODUCT_PRODUCT_CODE").getPropertyValue()!=null && congigurationMap.get("PRODUCT_PRODUCT_CODE").getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.FALSE)){
				productConfigurationBean.setProductCode(false);
			}else{
				productConfigurationBean.setProductCode(true);
			}
			if(congigurationMap.get("PRODUCT_PRODUCT_DESC").getPropertyValue()!=null && congigurationMap.get("PRODUCT_PRODUCT_DESC").getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.FALSE)){
				productConfigurationBean.setProductDesc(false);
			}else{
				productConfigurationBean.setProductDesc(true);
			}
			if(congigurationMap.get("PRODUCT_PURCHASE_ACCOUNT_CODE").getPropertyValue()!=null && congigurationMap.get("PRODUCT_PURCHASE_ACCOUNT_CODE").getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.FALSE)){
				productConfigurationBean.setPurchaseAccountCode(false);
			}else{
				productConfigurationBean.setPurchaseAccountCode(true);
			}
			if(congigurationMap.get("PRODUCT_REORDER_AMOUNT").getPropertyValue()!=null && congigurationMap.get("PRODUCT_REORDER_AMOUNT").getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.FALSE)){
				productConfigurationBean.setReOrderAmount(false);
			}else{
				productConfigurationBean.setReOrderAmount(true);
			}
			if(congigurationMap.get("PRODUCT_REORDER_POINT").getPropertyValue()!=null && congigurationMap.get("PRODUCT_REORDER_POINT").getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.FALSE)){
				productConfigurationBean.setReOrderPoint(false);
			}else{
				productConfigurationBean.setReOrderPoint(true);
			}
			if(congigurationMap.get("PRODUCT_RETAIL_PRICE_EXCLUSIVE_TAX").getPropertyValue()!=null && congigurationMap.get("PRODUCT_RETAIL_PRICE_EXCLUSIVE_TAX").getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.FALSE)){
				productConfigurationBean.setRetailPriceExclusiveTax(false);
			}else{
				productConfigurationBean.setRetailPriceExclusiveTax(true);
			}
			if(congigurationMap.get("PRODUCT_SALES_ACCOUNT_CODE").getPropertyValue()!=null && congigurationMap.get("PRODUCT_SALES_ACCOUNT_CODE").getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.FALSE)){
				productConfigurationBean.setSalesAccountCode(false);
			}else{
				productConfigurationBean.setSalesAccountCode(true);
			}
			if(congigurationMap.get("PRODUCT_PRODUCT_SKU").getPropertyValue()!=null && congigurationMap.get("PRODUCT_PRODUCT_SKU").getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.FALSE)){
				productConfigurationBean.setSku(false);
			}else{
				productConfigurationBean.setSku(true);
			}
			if(congigurationMap.get("SUPPLIER_ID").getPropertyValue()!=null && congigurationMap.get("SUPPLIER_ID").getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.FALSE)){
				productConfigurationBean.setSupplierId(false);
			}else{
				productConfigurationBean.setSupplierId(true);
			}
			if(congigurationMap.get("PRODUCT_SUPPLY_PRICE_EXCLUSIVE_TAX").getPropertyValue()!=null && congigurationMap.get("PRODUCT_SUPPLY_PRICE_EXCLUSIVE_TAX").getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.FALSE)){
				productConfigurationBean.setSupplyPriceExclusiveTax(false);
			}else{
				productConfigurationBean.setSupplyPriceExclusiveTax(true);
			}
			if(congigurationMap.get("PRODUCT_TRACKING_INVENTORY").getPropertyValue()!=null && congigurationMap.get("PRODUCT_TRACKING_INVENTORY").getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.FALSE)){
				productConfigurationBean.setTrackingInventory(false);
			}else{
				productConfigurationBean.setTrackingInventory(true);
			}
			if(congigurationMap.get("PRODUCT_VARIANT_PRODUCTS").getPropertyValue()!=null && congigurationMap.get("PRODUCT_VARIANT_PRODUCTS").getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.FALSE)){
				productConfigurationBean.setVariantProducts(false);
			}else{
				productConfigurationBean.setVariantProducts(true);
			}
			if(congigurationMap.get("PRODUCT_SHOW_PRODCUT_TAG").getPropertyValue()!=null && congigurationMap.get("PRODUCT_SHOW_PRODCUT_TAG").getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.FALSE)){
				productConfigurationBean.setProductTag(false);
			}else{
				productConfigurationBean.setProductTag(true);
			}
			if(congigurationMap.get("PRODUCT_PRODUCT_IMAGE").getPropertyValue()!=null && congigurationMap.get("PRODUCT_PRODUCT_IMAGE").getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.FALSE)){
				productConfigurationBean.setProductImage(false);
			}else{
				productConfigurationBean.setProductImage(true);
			}
			if(congigurationMap.get("PRODUCT_CURRENT_INVENTORY").getPropertyValue()!=null && congigurationMap.get("PRODUCT_CURRENT_INVENTORY").getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.FALSE)){
				productConfigurationBean.setCurrentInventory(false);
			}else{
				productConfigurationBean.setCurrentInventory(true);
			}
			
			//productConfigurationBean.setMarkupPrct(congigurationMap.get("MARKUP").getPropertyValue()!=null && congigurationMap.get("MARKUP").getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE) ?true:false);
			//productConfigurationBean.setProductCode(congigurationMap.get("PRODUCT_SKU").getPropertyValue()!=null?Boolean.getBoolean(congigurationMap.get("PRODUCT_SKU").getPropertyValue()):true);
			//productConfigurationBean.setProductDesc(congigurationMap.get("PRODUCT_DESC").getPropertyValue()!=null?Boolean.getBoolean(congigurationMap.get("PRODUCT_DESC").getPropertyValue()):true);
			//it never be hide
			productConfigurationBean.setProductName(true);
			//productConfigurationBean.setPurchaseAccountCode(congigurationMap.get("PURCHASE_ACCOUNT_CODE").getPropertyValue()!=null?Boolean.getBoolean(congigurationMap.get("PURCHASE_ACCOUNT_CODE").getPropertyValue()):true);
			//productConfigurationBean.setReOrderAmount(congigurationMap.get("REORDER_AMOUNT").getPropertyValue()!=null?Boolean.getBoolean(congigurationMap.get("REORDER_AMOUNT").getPropertyValue()):true);
			//productConfigurationBean.setReOrderPoint(congigurationMap.get("REORDER_POINT").getPropertyValue()!=null?Boolean.getBoolean(congigurationMap.get("REORDER_POINT").getPropertyValue()):true);
			//productConfigurationBean.setRetailPriceExclusiveTax(congigurationMap.get("RETAIL_PRICE_EXCLUSIVE_TAX").getPropertyValue()!=null?Boolean.getBoolean(congigurationMap.get("RETAIL_PRICE_EXCLUSIVE_TAX").getPropertyValue()):true);
			//productConfigurationBean.setSalesAccountCode(congigurationMap.get("SALES_ACCOUNT_CODE").getPropertyValue()!=null?Boolean.getBoolean(congigurationMap.get("SALES_ACCOUNT_CODE").getPropertyValue()):true);
			//productConfigurationBean.setSku(congigurationMap.get("PRODUCT_SKU").getPropertyValue()!=null?Boolean.getBoolean(congigurationMap.get("PRODUCT_SKU").getPropertyValue()):true);
			//productConfigurationBean.setSupplierId(congigurationMap.get("SUPPLIER_ID").getPropertyValue()!=null?Boolean.getBoolean(congigurationMap.get("SUPPLIER_ID").getPropertyValue()):true);
			//productConfigurationBean.setSupplyPriceExclusiveTax(congigurationMap.get("SUPPLY_PRICE_EXCLUSIVE_TAX").getPropertyValue()!=null?Boolean.getBoolean(congigurationMap.get("SUPPLY_PRICE_EXCLUSIVE_TAX").getPropertyValue()):true);
			//productConfigurationBean.setTrackingInventory(congigurationMap.get("TRACKING_INVENTORY").getPropertyValue()!=null?Boolean.getBoolean(congigurationMap.get("TRACKING_INVENTORY").getPropertyValue()):true);
			//productConfigurationBean.setVariantProducts(congigurationMap.get("VARIANT_PRODUCTS").getPropertyValue()!=null?Boolean.getBoolean(congigurationMap.get("VARIANT_PRODUCTS").getPropertyValue()):true);
			
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			productConfigurationBean.setMarkupPrct(true);
			productConfigurationBean.setProductCode(true);
			productConfigurationBean.setProductDesc(true);
			productConfigurationBean.setProductName(true);
			productConfigurationBean.setPurchaseAccountCode(true);
			productConfigurationBean.setReOrderAmount(true);
			productConfigurationBean.setReOrderPoint(true); 
			productConfigurationBean.setRetailPriceExclusiveTax(true);
			productConfigurationBean.setSalesAccountCode(true);
			productConfigurationBean.setSku(true);
			productConfigurationBean.setSupplierId(true);
			productConfigurationBean.setSupplyPriceExclusiveTax(true);
			productConfigurationBean.setTrackingInventory(true);
			productConfigurationBean.setVariantProducts(true);
			productConfigurationBean.setProductTag(true);
			productConfigurationBean.setProductImage(true);
			productConfigurationBean.setCurrentInventory(true);
			
		}
		
		return productConfigurationBean;
	}
}
