package com.dowhile;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

public class StockOrderDetailCustom {

	private Integer STOCK_ORDER_DETAIL_ID;
	private Integer STOCK_ORDER_ASSOCICATION_ID;
	private Integer PRODUCT_VARIANT_ASSOCICATION_ID;
	private Integer PRODUCT_ASSOCIATION_ID;
	private String product_name;
	private String VariantAttributeName;
	private BigInteger product_current_inventory;
	private Integer product_destination_current_inventory;
	private BigInteger product_variant_current_inventory;
	private Integer product_variant_destination_inventory;
	private boolean IS_PRODUCT;
	private Integer ORDER_PROD_QTY;
	private BigDecimal ORDR_SUPPLY_PRICE;
	private Integer RECV_PROD_QTY;
	private BigDecimal RECV_SUPPLY_PRICE;
	private BigDecimal RETAIL_PRICE;
	private boolean ACTIVE_INDICATOR;
	private Timestamp CREATED_DATE;
	private Timestamp LAST_UPDATED;
	private Integer CREATED_BY;
	private Integer UPDATED_BY;
	private Integer COMPANY_ASSOCIATION_ID;
	public Integer getSTOCK_ORDER_DETAIL_ID() {
		return STOCK_ORDER_DETAIL_ID;
	}
	public void setSTOCK_ORDER_DETAIL_ID(Integer sTOCK_ORDER_DETAIL_ID) {
		STOCK_ORDER_DETAIL_ID = sTOCK_ORDER_DETAIL_ID;
	}
	public Integer getSTOCK_ORDER_ASSOCICATION_ID() {
		return STOCK_ORDER_ASSOCICATION_ID;
	}
	public void setSTOCK_ORDER_ASSOCICATION_ID(Integer sTOCK_ORDER_ASSOCICATION_ID) {
		STOCK_ORDER_ASSOCICATION_ID = sTOCK_ORDER_ASSOCICATION_ID;
	}
	public Integer getPRODUCT_VARIANT_ASSOCICATION_ID() {
		return PRODUCT_VARIANT_ASSOCICATION_ID;
	}
	public void setPRODUCT_VARIANT_ASSOCICATION_ID(Integer pRODUCT_VARIANT_ASSOCICATION_ID) {
		PRODUCT_VARIANT_ASSOCICATION_ID = pRODUCT_VARIANT_ASSOCICATION_ID;
	}
	public Integer getPRODUCT_ASSOCIATION_ID() {
		return PRODUCT_ASSOCIATION_ID;
	}
	public void setPRODUCT_ASSOCIATION_ID(Integer pRODUCT_ASSOCIATION_ID) {
		PRODUCT_ASSOCIATION_ID = pRODUCT_ASSOCIATION_ID;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getVariantAttributeName() {
		return VariantAttributeName;
	}
	public void setVariantAttributeName(String variantAttributeName) {
		VariantAttributeName = variantAttributeName;
	}
	public BigInteger getProduct_current_inventory() {
		return product_current_inventory;
	}
	public void setProduct_current_inventory(BigInteger product_current_inventory) {
		this.product_current_inventory = product_current_inventory;
	}
	public Integer getProduct_destination_current_inventory() {
		return product_destination_current_inventory;
	}
	public void setProduct_destination_current_inventory(Integer product_destination_current_inventory) {
		this.product_destination_current_inventory = product_destination_current_inventory;
	}
	public BigInteger getProduct_variant_current_inventory() {
		return product_variant_current_inventory;
	}
	public void setProduct_variant_current_inventory(BigInteger product_variant_current_inventory) {
		this.product_variant_current_inventory = product_variant_current_inventory;
	}
	public Integer getProduct_variant_destination_inventory() {
		return product_variant_destination_inventory;
	}
	public void setProduct_variant_destination_inventory(Integer product_variant_destination_inventory) {
		this.product_variant_destination_inventory = product_variant_destination_inventory;
	}
	public boolean isIS_PRODUCT() {
		return IS_PRODUCT;
	}
	public void setIS_PRODUCT(boolean iS_PRODUCT) {
		IS_PRODUCT = iS_PRODUCT;
	}
	public Integer getORDER_PROD_QTY() {
		return ORDER_PROD_QTY;
	}
	public void setORDER_PROD_QTY(Integer oRDER_PROD_QTY) {
		ORDER_PROD_QTY = oRDER_PROD_QTY;
	}
	public BigDecimal getORDR_SUPPLY_PRICE() {
		return ORDR_SUPPLY_PRICE;
	}
	public void setORDR_SUPPLY_PRICE(BigDecimal oRDR_SUPPLY_PRICE) {
		ORDR_SUPPLY_PRICE = oRDR_SUPPLY_PRICE;
	}
	public Integer getRECV_PROD_QTY() {
		return RECV_PROD_QTY;
	}
	public void setRECV_PROD_QTY(Integer rECV_PROD_QTY) {
		RECV_PROD_QTY = rECV_PROD_QTY;
	}
	public BigDecimal getRECV_SUPPLY_PRICE() {
		return RECV_SUPPLY_PRICE;
	}
	public void setRECV_SUPPLY_PRICE(BigDecimal rECV_SUPPLY_PRICE) {
		RECV_SUPPLY_PRICE = rECV_SUPPLY_PRICE;
	}
	public BigDecimal getRETAIL_PRICE() {
		return RETAIL_PRICE;
	}
	public void setRETAIL_PRICE(BigDecimal rETAIL_PRICE) {
		RETAIL_PRICE = rETAIL_PRICE;
	}
	public boolean isACTIVE_INDICATOR() {
		return ACTIVE_INDICATOR;
	}
	public void setACTIVE_INDICATOR(boolean aCTIVE_INDICATOR) {
		ACTIVE_INDICATOR = aCTIVE_INDICATOR;
	}
	public Timestamp getCREATED_DATE() {
		return CREATED_DATE;
	}
	public void setCREATED_DATE(Timestamp cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}
	public Timestamp getLAST_UPDATED() {
		return LAST_UPDATED;
	}
	public void setLAST_UPDATED(Timestamp lAST_UPDATED) {
		LAST_UPDATED = lAST_UPDATED;
	}
	public Integer getCREATED_BY() {
		return CREATED_BY;
	}
	public void setCREATED_BY(Integer cREATED_BY) {
		CREATED_BY = cREATED_BY;
	}
	public Integer getUPDATED_BY() {
		return UPDATED_BY;
	}
	public void setUPDATED_BY(Integer uPDATED_BY) {
		UPDATED_BY = uPDATED_BY;
	}
	public Integer getCOMPANY_ASSOCIATION_ID() {
		return COMPANY_ASSOCIATION_ID;
	}
	public void setCOMPANY_ASSOCIATION_ID(Integer cOMPANY_ASSOCIATION_ID) {
		COMPANY_ASSOCIATION_ID = cOMPANY_ASSOCIATION_ID;
	}
	
}
