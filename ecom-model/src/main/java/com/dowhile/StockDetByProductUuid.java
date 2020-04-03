package com.dowhile;

import java.math.BigDecimal;

import com.sun.jmx.snmp.Timestamp;

public class StockDetByProductUuid {

	private Integer STOCK_ORDER_DETAIL_ID;
	private Integer STOCK_ORDER_ASSOCICATION_ID;
	private Integer PRODUCT_VARIANT_ASSOCICATION_ID;
	private Integer PRODUCT_ASSOCIATION_ID;
	private String product_name;
	private String VariantAttributeName;
	private Integer product_current_inventory;
	private Integer product_destination_current_inventory;
	private Integer product_variant_current_inventory;
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
	
	private String  STOCK_REF_NO;
	private String STOCK_ORDER_TYPE_DESC;
	private String Source;
	private String Destination;
	private String PRODUCT_NAME;
	private String VARIANT_ATTRIBUTE_NAME;
	private Integer order_prod_qty;
	//private Integer RECV_PROD_QTY;
	/**
	 * @return the sTOCK_ORDER_DETAIL_ID
	 */
	public Integer getSTOCK_ORDER_DETAIL_ID() {
		return STOCK_ORDER_DETAIL_ID;
	}
	/**
	 * @param sTOCK_ORDER_DETAIL_ID the sTOCK_ORDER_DETAIL_ID to set
	 */
	public void setSTOCK_ORDER_DETAIL_ID(Integer sTOCK_ORDER_DETAIL_ID) {
		STOCK_ORDER_DETAIL_ID = sTOCK_ORDER_DETAIL_ID;
	}
	/**
	 * @return the sTOCK_ORDER_ASSOCICATION_ID
	 */
	public Integer getSTOCK_ORDER_ASSOCICATION_ID() {
		return STOCK_ORDER_ASSOCICATION_ID;
	}
	/**
	 * @param sTOCK_ORDER_ASSOCICATION_ID the sTOCK_ORDER_ASSOCICATION_ID to set
	 */
	public void setSTOCK_ORDER_ASSOCICATION_ID(Integer sTOCK_ORDER_ASSOCICATION_ID) {
		STOCK_ORDER_ASSOCICATION_ID = sTOCK_ORDER_ASSOCICATION_ID;
	}
	/**
	 * @return the pRODUCT_VARIANT_ASSOCICATION_ID
	 */
	public Integer getPRODUCT_VARIANT_ASSOCICATION_ID() {
		return PRODUCT_VARIANT_ASSOCICATION_ID;
	}
	/**
	 * @param pRODUCT_VARIANT_ASSOCICATION_ID the pRODUCT_VARIANT_ASSOCICATION_ID to set
	 */
	public void setPRODUCT_VARIANT_ASSOCICATION_ID(Integer pRODUCT_VARIANT_ASSOCICATION_ID) {
		PRODUCT_VARIANT_ASSOCICATION_ID = pRODUCT_VARIANT_ASSOCICATION_ID;
	}
	/**
	 * @return the pRODUCT_ASSOCIATION_ID
	 */
	public Integer getPRODUCT_ASSOCIATION_ID() {
		return PRODUCT_ASSOCIATION_ID;
	}
	/**
	 * @param pRODUCT_ASSOCIATION_ID the pRODUCT_ASSOCIATION_ID to set
	 */
	public void setPRODUCT_ASSOCIATION_ID(Integer pRODUCT_ASSOCIATION_ID) {
		PRODUCT_ASSOCIATION_ID = pRODUCT_ASSOCIATION_ID;
	}
	/**
	 * @return the product_name
	 */
	public String getProduct_name() {
		return product_name;
	}
	/**
	 * @param product_name the product_name to set
	 */
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	/**
	 * @return the variantAttributeName
	 */
	public String getVariantAttributeName() {
		return VariantAttributeName;
	}
	/**
	 * @param variantAttributeName the variantAttributeName to set
	 */
	public void setVariantAttributeName(String variantAttributeName) {
		VariantAttributeName = variantAttributeName;
	}
	/**
	 * @return the product_current_inventory
	 */
	public Integer getProduct_current_inventory() {
		return product_current_inventory;
	}
	/**
	 * @param product_current_inventory the product_current_inventory to set
	 */
	public void setProduct_current_inventory(Integer product_current_inventory) {
		this.product_current_inventory = product_current_inventory;
	}
	/**
	 * @return the product_destination_current_inventory
	 */
	public Integer getProduct_destination_current_inventory() {
		return product_destination_current_inventory;
	}
	/**
	 * @param product_destination_current_inventory the product_destination_current_inventory to set
	 */
	public void setProduct_destination_current_inventory(Integer product_destination_current_inventory) {
		this.product_destination_current_inventory = product_destination_current_inventory;
	}
	/**
	 * @return the product_variant_current_inventory
	 */
	public Integer getProduct_variant_current_inventory() {
		return product_variant_current_inventory;
	}
	/**
	 * @param product_variant_current_inventory the product_variant_current_inventory to set
	 */
	public void setProduct_variant_current_inventory(Integer product_variant_current_inventory) {
		this.product_variant_current_inventory = product_variant_current_inventory;
	}
	/**
	 * @return the product_variant_destination_inventory
	 */
	public Integer getProduct_variant_destination_inventory() {
		return product_variant_destination_inventory;
	}
	/**
	 * @param product_variant_destination_inventory the product_variant_destination_inventory to set
	 */
	public void setProduct_variant_destination_inventory(Integer product_variant_destination_inventory) {
		this.product_variant_destination_inventory = product_variant_destination_inventory;
	}
	/**
	 * @return the iS_PRODUCT
	 */
	public boolean isIS_PRODUCT() {
		return IS_PRODUCT;
	}
	/**
	 * @param iS_PRODUCT the iS_PRODUCT to set
	 */
	public void setIS_PRODUCT(boolean iS_PRODUCT) {
		IS_PRODUCT = iS_PRODUCT;
	}
	/**
	 * @return the oRDER_PROD_QTY
	 */
	public Integer getORDER_PROD_QTY() {
		return ORDER_PROD_QTY;
	}
	/**
	 * @param oRDER_PROD_QTY the oRDER_PROD_QTY to set
	 */
	public void setORDER_PROD_QTY(Integer oRDER_PROD_QTY) {
		ORDER_PROD_QTY = oRDER_PROD_QTY;
	}
	/**
	 * @return the oRDR_SUPPLY_PRICE
	 */
	public BigDecimal getORDR_SUPPLY_PRICE() {
		return ORDR_SUPPLY_PRICE;
	}
	/**
	 * @param oRDR_SUPPLY_PRICE the oRDR_SUPPLY_PRICE to set
	 */
	public void setORDR_SUPPLY_PRICE(BigDecimal oRDR_SUPPLY_PRICE) {
		ORDR_SUPPLY_PRICE = oRDR_SUPPLY_PRICE;
	}
	/**
	 * @return the rECV_PROD_QTY
	 */
	public Integer getRECV_PROD_QTY() {
		return RECV_PROD_QTY;
	}
	/**
	 * @param rECV_PROD_QTY the rECV_PROD_QTY to set
	 */
	public void setRECV_PROD_QTY(Integer rECV_PROD_QTY) {
		RECV_PROD_QTY = rECV_PROD_QTY;
	}
	/**
	 * @return the rECV_SUPPLY_PRICE
	 */
	public BigDecimal getRECV_SUPPLY_PRICE() {
		return RECV_SUPPLY_PRICE;
	}
	/**
	 * @param rECV_SUPPLY_PRICE the rECV_SUPPLY_PRICE to set
	 */
	public void setRECV_SUPPLY_PRICE(BigDecimal rECV_SUPPLY_PRICE) {
		RECV_SUPPLY_PRICE = rECV_SUPPLY_PRICE;
	}
	/**
	 * @return the rETAIL_PRICE
	 */
	public BigDecimal getRETAIL_PRICE() {
		return RETAIL_PRICE;
	}
	/**
	 * @param rETAIL_PRICE the rETAIL_PRICE to set
	 */
	public void setRETAIL_PRICE(BigDecimal rETAIL_PRICE) {
		RETAIL_PRICE = rETAIL_PRICE;
	}
	/**
	 * @return the aCTIVE_INDICATOR
	 */
	public boolean isACTIVE_INDICATOR() {
		return ACTIVE_INDICATOR;
	}
	/**
	 * @param aCTIVE_INDICATOR the aCTIVE_INDICATOR to set
	 */
	public void setACTIVE_INDICATOR(boolean aCTIVE_INDICATOR) {
		ACTIVE_INDICATOR = aCTIVE_INDICATOR;
	}
	/**
	 * @return the cREATED_DATE
	 */
	public Timestamp getCREATED_DATE() {
		return CREATED_DATE;
	}
	/**
	 * @param cREATED_DATE the cREATED_DATE to set
	 */
	public void setCREATED_DATE(Timestamp cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}
	/**
	 * @return the lAST_UPDATED
	 */
	public Timestamp getLAST_UPDATED() {
		return LAST_UPDATED;
	}
	/**
	 * @param lAST_UPDATED the lAST_UPDATED to set
	 */
	public void setLAST_UPDATED(Timestamp lAST_UPDATED) {
		LAST_UPDATED = lAST_UPDATED;
	}
	/**
	 * @return the cREATED_BY
	 */
	public Integer getCREATED_BY() {
		return CREATED_BY;
	}
	/**
	 * @param cREATED_BY the cREATED_BY to set
	 */
	public void setCREATED_BY(Integer cREATED_BY) {
		CREATED_BY = cREATED_BY;
	}
	/**
	 * @return the uPDATED_BY
	 */
	public Integer getUPDATED_BY() {
		return UPDATED_BY;
	}
	/**
	 * @param uPDATED_BY the uPDATED_BY to set
	 */
	public void setUPDATED_BY(Integer uPDATED_BY) {
		UPDATED_BY = uPDATED_BY;
	}
	/**
	 * @return the cOMPANY_ASSOCIATION_ID
	 */
	public Integer getCOMPANY_ASSOCIATION_ID() {
		return COMPANY_ASSOCIATION_ID;
	}
	/**
	 * @param cOMPANY_ASSOCIATION_ID the cOMPANY_ASSOCIATION_ID to set
	 */
	public void setCOMPANY_ASSOCIATION_ID(Integer cOMPANY_ASSOCIATION_ID) {
		COMPANY_ASSOCIATION_ID = cOMPANY_ASSOCIATION_ID;
	}
	/**
	 * @return the sTOCK_REF_NO
	 */
	public String getSTOCK_REF_NO() {
		return STOCK_REF_NO;
	}
	/**
	 * @param sTOCK_REF_NO the sTOCK_REF_NO to set
	 */
	public void setSTOCK_REF_NO(String sTOCK_REF_NO) {
		STOCK_REF_NO = sTOCK_REF_NO;
	}
	/**
	 * @return the sTOCK_ORDER_TYPE_DESC
	 */
	public String getSTOCK_ORDER_TYPE_DESC() {
		return STOCK_ORDER_TYPE_DESC;
	}
	/**
	 * @param sTOCK_ORDER_TYPE_DESC the sTOCK_ORDER_TYPE_DESC to set
	 */
	public void setSTOCK_ORDER_TYPE_DESC(String sTOCK_ORDER_TYPE_DESC) {
		STOCK_ORDER_TYPE_DESC = sTOCK_ORDER_TYPE_DESC;
	}
	/**
	 * @return the source
	 */
	public String getSource() {
		return Source;
	}
	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		Source = source;
	}
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return Destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		Destination = destination;
	}
	/**
	 * @return the pRODUCT_NAME
	 */
	public String getPRODUCT_NAME() {
		return PRODUCT_NAME;
	}
	/**
	 * @param pRODUCT_NAME the pRODUCT_NAME to set
	 */
	public void setPRODUCT_NAME(String pRODUCT_NAME) {
		PRODUCT_NAME = pRODUCT_NAME;
	}
	/**
	 * @return the vARIANT_ATTRIBUTE_NAME
	 */
	public String getVARIANT_ATTRIBUTE_NAME() {
		return VARIANT_ATTRIBUTE_NAME;
	}
	/**
	 * @param vARIANT_ATTRIBUTE_NAME the vARIANT_ATTRIBUTE_NAME to set
	 */
	public void setVARIANT_ATTRIBUTE_NAME(String vARIANT_ATTRIBUTE_NAME) {
		VARIANT_ATTRIBUTE_NAME = vARIANT_ATTRIBUTE_NAME;
	}
	/**
	 * @return the order_prod_qty
	 */
	public Integer getOrder_prod_qty() {
		return order_prod_qty;
	}
	/**
	 * @param order_prod_qty the order_prod_qty to set
	 */
	public void setOrder_prod_qty(Integer order_prod_qty) {
		this.order_prod_qty = order_prod_qty;
	}
	
	
	
}


