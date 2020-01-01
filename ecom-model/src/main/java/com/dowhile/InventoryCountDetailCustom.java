package com.dowhile;

import java.math.BigDecimal;
import java.util.Date;

public class InventoryCountDetailCustom {	
	private Integer INVENTORY_COUNT_DETAIL_ID;
	private Integer PRODUCT_VARIANT_ASSOCICATION_ID;
	private Integer INVENTORY_COUNT_ASSOCICATION_ID;
	private Integer COMPANY_ASSOCIATION_ID;
	private String product_name;
	private String VariantAttributeName;
	private Integer PRODUCT_ASSOCIATION_ID;
	private boolean IS_PRODUCT;
	private Integer EXPECTED_PROD_QTY;
	private BigDecimal SUPPLY_PRICE_EXP;
	private BigDecimal RETAIL_PRICE_EXP;
	private Integer COUNTED_PROD_QTY;
	private BigDecimal SUPPLY_PRICE_COUNTED;
	private BigDecimal RETAIL_PRICE_COUNTED;
	private Integer COUNT_DIFF;
	private BigDecimal PRICE_DIFF;
	private boolean ACTIVE_INDICATOR;
	private Date CREATED_DATE;
	private Date LAST_UPDATED;
	private Integer CREATED_BY;
	private Integer UPDATED_BY;
	private Byte audit_transfer;
	
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
	public Integer getINVENTORY_COUNT_DETAIL_ID() {
		return INVENTORY_COUNT_DETAIL_ID;
	}
	public void setINVENTORY_COUNT_DETAIL_ID(Integer iNVENTORY_COUNT_DETAIL_ID) {
		INVENTORY_COUNT_DETAIL_ID = iNVENTORY_COUNT_DETAIL_ID;
	}
	public Integer getPRODUCT_VARIANT_ASSOCICATION_ID() {
		return PRODUCT_VARIANT_ASSOCICATION_ID;
	}
	public void setPRODUCT_VARIANT_ASSOCICATION_ID(Integer pRODUCT_VARIANT_ASSOCICATION_ID) {
		PRODUCT_VARIANT_ASSOCICATION_ID = pRODUCT_VARIANT_ASSOCICATION_ID;
	}
	public Integer getINVENTORY_COUNT_ASSOCICATION_ID() {
		return INVENTORY_COUNT_ASSOCICATION_ID;
	}
	public void setINVENTORY_COUNT_ASSOCICATION_ID(Integer iNVENTORY_COUNT_ASSOCICATION_ID) {
		INVENTORY_COUNT_ASSOCICATION_ID = iNVENTORY_COUNT_ASSOCICATION_ID;
	}
	public Integer getCOMPANY_ASSOCIATION_ID() {
		return COMPANY_ASSOCIATION_ID;
	}
	public void setCOMPANY_ASSOCIATION_ID(Integer cOMPANY_ASSOCIATION_ID) {
		COMPANY_ASSOCIATION_ID = cOMPANY_ASSOCIATION_ID;
	}
	public Integer getPRODUCT_ASSOCIATION_ID() {
		return PRODUCT_ASSOCIATION_ID;
	}
	public void setPRODUCT_ASSOCIATION_ID(Integer pRODUCT_ASSOCIATION_ID) {
		PRODUCT_ASSOCIATION_ID = pRODUCT_ASSOCIATION_ID;
	}
	public boolean isIS_PRODUCT() {
		return IS_PRODUCT;
	}
	public void setIS_PRODUCT(boolean iS_PRODUCT) {
		IS_PRODUCT = iS_PRODUCT;
	}
	public Integer getEXPECTED_PROD_QTY() {
		return EXPECTED_PROD_QTY;
	}
	public void setEXPECTED_PROD_QTY(Integer eXPECTED_PROD_QTY) {
		EXPECTED_PROD_QTY = eXPECTED_PROD_QTY;
	}
	public BigDecimal getSUPPLY_PRICE_EXP() {
		return SUPPLY_PRICE_EXP;
	}
	public void setSUPPLY_PRICE_EXP(BigDecimal sUPPLY_PRICE_EXP) {
		SUPPLY_PRICE_EXP = sUPPLY_PRICE_EXP;
	}
	public BigDecimal getRETAIL_PRICE_EXP() {
		return RETAIL_PRICE_EXP;
	}
	public void setRETAIL_PRICE_EXP(BigDecimal rETAIL_PRICE_EXP) {
		RETAIL_PRICE_EXP = rETAIL_PRICE_EXP;
	}
	public Integer getCOUNTED_PROD_QTY() {
		return COUNTED_PROD_QTY;
	}
	public void setCOUNTED_PROD_QTY(Integer cOUNTED_PROD_QTY) {
		COUNTED_PROD_QTY = cOUNTED_PROD_QTY;
	}
	public BigDecimal getRETAIL_PRICE_COUNTED() {
		return RETAIL_PRICE_COUNTED;
	}
	public void setRETAIL_PRICE_COUNTED(BigDecimal rETAIL_PRICE_COUNTED) {
		RETAIL_PRICE_COUNTED = rETAIL_PRICE_COUNTED;
	}
	public Integer getCOUNT_DIFF() {
		return COUNT_DIFF;
	}
	public void setCOUNT_DIFF(Integer cOUNT_DIFF) {
		COUNT_DIFF = cOUNT_DIFF;
	}
	public boolean isACTIVE_INDICATOR() {
		return ACTIVE_INDICATOR;
	}
	public void setACTIVE_INDICATOR(boolean aCTIVE_INDICATOR) {
		ACTIVE_INDICATOR = aCTIVE_INDICATOR;
	}
	public BigDecimal getPRICE_DIFF() {
		return PRICE_DIFF;
	}
	public void setPRICE_DIFF(BigDecimal pRICE_DIFF) {
		PRICE_DIFF = pRICE_DIFF;
	}
	public Date getCREATED_DATE() {
		return CREATED_DATE;
	}
	public void setCREATED_DATE(Date cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}
	public Date getLAST_UPDATED() {
		return LAST_UPDATED;
	}
	public void setLAST_UPDATED(Date lAST_UPDATED) {
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
	public Byte getAudit_transfer() {
		return audit_transfer;
	}
	public void setAudit_transfer(Byte audit_transfer) {
		this.audit_transfer = audit_transfer;
	}
	public BigDecimal getSUPPLY_PRICE_COUNTED() {
		return SUPPLY_PRICE_COUNTED;
	}
	public void setSUPPLY_PRICE_COUNTED(BigDecimal sUPPLY_PRICE_COUNTED) {
		SUPPLY_PRICE_COUNTED = sUPPLY_PRICE_COUNTED;
	}	
	
}
