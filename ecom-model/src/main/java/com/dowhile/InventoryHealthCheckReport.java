package com.dowhile;

import java.math.BigDecimal;
import java.math.BigInteger;

public class InventoryHealthCheckReport {
	private String PRODUCT_NAME;
	private String VARIANT_ATTRIBUTE_NAME;
	private String OUTLET_NAME;
	private String SKU;
	private String SUPPLIER;
	private String PRODUCT_TYPE_NAME;
	private BigInteger AUDIT_QUANTITY;
	private BigDecimal STOCK_TRANSFERRED;
	private BigDecimal STOCK_RETURN_TO_WAREHOUSE;
	private BigDecimal SALE;
	private BigDecimal SALE_RETURN;
	private BigDecimal EXPECTED_CURRENT_INVENTORY;
	private BigInteger SYSTEM_CURRENT_INVENTORY;
	private BigDecimal CONFLICTED_QUANTITY;
	
	public InventoryHealthCheckReport() {
		
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
	 * @return the oUTLET_NAME
	 */
	public String getOUTLET_NAME() {
		return OUTLET_NAME;
	}
	/**
	 * @param oUTLET_NAME the oUTLET_NAME to set
	 */
	public void setOUTLET_NAME(String oUTLET_NAME) {
		OUTLET_NAME = oUTLET_NAME;
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
	 * @return the sKU
	 */
	public String getSKU() {
		return SKU;
	}
	/**
	 * @param sKU the sKU to set
	 */
	public void setSKU(String sKU) {
		SKU = sKU;
	}
	/**
	 * @return the sUPPLIER
	 */
	public String getSUPPLIER() {
		return SUPPLIER;
	}
	/**
	 * @param sUPPLIER the sUPPLIER to set
	 */
	public void setSUPPLIER(String sUPPLIER) {
		SUPPLIER = sUPPLIER;
	}
	/**
	 * @return the pRODUCT_TYPE_NAME
	 */
	public String getPRODUCT_TYPE_NAME() {
		return PRODUCT_TYPE_NAME;
	}
	/**
	 * @param pRODUCT_TYPE_NAME the pRODUCT_TYPE_NAME to set
	 */
	public void setPRODUCT_TYPE_NAME(String pRODUCT_TYPE_NAME) {
		PRODUCT_TYPE_NAME = pRODUCT_TYPE_NAME;
	}
	/**
	 * @return the aUDIT_QUANTITY
	 */
	public BigInteger getAUDIT_QUANTITY() {
		return AUDIT_QUANTITY;
	}
	/**
	 * @param aUDIT_QUANTITY the aUDIT_QUANTITY to set
	 */
	public void setAUDIT_QUANTITY(BigInteger aUDIT_QUANTITY) {
		AUDIT_QUANTITY = aUDIT_QUANTITY;
	}
	
	/**
	 * @return the sTOCK_RETURN_TO_WAREHOUSE
	 */
	public BigDecimal getSTOCK_RETURN_TO_WAREHOUSE() {
		return STOCK_RETURN_TO_WAREHOUSE;
	}
	/**
	 * @param sTOCK_RETURN_TO_WAREHOUSE the sTOCK_RETURN_TO_WAREHOUSE to set
	 */
	public void setSTOCK_RETURN_TO_WAREHOUSE(BigDecimal sTOCK_RETURN_TO_WAREHOUSE) {
		STOCK_RETURN_TO_WAREHOUSE = sTOCK_RETURN_TO_WAREHOUSE;
	}
	/**
	 * @return the sALE
	 */
	public BigDecimal getSALE() {
		return SALE;
	}
	/**
	 * @param sALE the sALE to set
	 */
	public void setSALE(BigDecimal sALE) {
		SALE = sALE;
	}
	/**
	 * @return the sALE_RETURN
	 */
	public BigDecimal getSALE_RETURN() {
		return SALE_RETURN;
	}
	/**
	 * @param sALE_RETURN the sALE_RETURN to set
	 */
	public void setSALE_RETURN(BigDecimal sALE_RETURN) {
		SALE_RETURN = sALE_RETURN;
	}
	/**
	 * @return the eXPECTED_CURRENT_INVENTORY
	 */
	public BigDecimal getEXPECTED_CURRENT_INVENTORY() {
		return EXPECTED_CURRENT_INVENTORY;
	}
	/**
	 * @param eXPECTED_CURRENT_INVENTORY the eXPECTED_CURRENT_INVENTORY to set
	 */
	public void setEXPECTED_CURRENT_INVENTORY(BigDecimal eXPECTED_CURRENT_INVENTORY) {
		EXPECTED_CURRENT_INVENTORY = eXPECTED_CURRENT_INVENTORY;
	}
	/**
	 * @return the sYSTEM_CURRENT_INVENTORY
	 */
	public BigInteger getSYSTEM_CURRENT_INVENTORY() {
		return SYSTEM_CURRENT_INVENTORY;
	}
	/**
	 * @param sYSTEM_CURRENT_INVENTORY the sYSTEM_CURRENT_INVENTORY to set
	 */
	public void setSYSTEM_CURRENT_INVENTORY(BigInteger sYSTEM_CURRENT_INVENTORY) {
		SYSTEM_CURRENT_INVENTORY = sYSTEM_CURRENT_INVENTORY;
	}
	/**
	 * @return the cONFLICTED_QUANTITY
	 */
	public BigDecimal getCONFLICTED_QUANTITY() {
		return CONFLICTED_QUANTITY;
	}
	/**
	 * @param cONFLICTED_QUANTITY the cONFLICTED_QUANTITY to set
	 */
	public void setCONFLICTED_QUANTITY(BigDecimal cONFLICTED_QUANTITY) {
		CONFLICTED_QUANTITY = cONFLICTED_QUANTITY;
	}
	/**
	 * @return the sTOCK_TRANSFERRED
	 */
	public BigDecimal getSTOCK_TRANSFERRED() {
		return STOCK_TRANSFERRED;
	}
	/**
	 * @param sTOCK_TRANSFERRED the sTOCK_TRANSFERRED to set
	 */
	public void setSTOCK_TRANSFERRED(BigDecimal sTOCK_TRANSFERRED) {
		STOCK_TRANSFERRED = sTOCK_TRANSFERRED;
	}
}
