/**
 * 
 */
package com.dowhile.frontend.mapping.bean;



/**
 * @author Hafiz Yamen Bashir
 *
 */
public class ProductPriceHistoryBean {
	
	private int productPriceHistoryId;
    private int outletId;
    private String outletName;
    private int userByCreatedById;
    private int userByUpdatedById;
    private int companyId;
    private String companyName;
    private int salesTaxAssociationId;
    private int productId;
    private String productUuid;
    private int productVariantId;
    private String productVariantUuid;
    private String coCode;
    private String sroNumber;
    private String hsCode;
    private String description;
    private String dollarRate;
    private String dUnitValueDeclared;
    private String dUnitValueAssessed;
    private String dTotalValueDeclared;
    private String dTotalValueAssessed;
    private String pCustomValueDeclared;
    private String pCustomValueAssessed;
    private String numberOfUnits;
    private String customDutyPrct;
    private String customDutyValue;
    private String salesTaxPrct;
    private String salesTaxValue;
    private String regularityDutyPrct;
    private String regularityDutyValue;
    private String additionalCustomDutyPrct;
    private String additionalCustomDutyValue;
    private String additionalSalesTaxPrct;
    private String additionalSalesTaxValue;
    private String excisePrct;
    private String exciseValue;
    private String totalValue;
    private String itPrct;
    private String itValue;
    private String remarks;
    private int vrNumber;
    private String supplyPriceExclTax;
    private String markupPrct;
    private boolean activeIndicator;
    private String createdDate;
    private String lastUpdated;
    private String ftaPrct;
    private String ftaValue;
    private String grNumber;
	/**
	 * 
	 */
	public ProductPriceHistoryBean() {
	}
	/**
	 * @param productPriceHistoryId
	 * @param outletId
	 * @param outletName
	 * @param userByCreatedById
	 * @param userByUpdatedById
	 * @param companyId
	 * @param companyName
	 * @param salesTaxAssociationId
	 * @param productId
	 * @param productUuid
	 * @param productVariantId
	 * @param productVariantUuid
	 * @param coCode
	 * @param sroNumber
	 * @param hsCode
	 * @param description
	 * @param dollarRate
	 * @param dUnitValueDeclared
	 * @param dUnitValueAssessed
	 * @param dTotalValueDeclared
	 * @param dTotalValueAssessed
	 * @param pCustomValueDeclared
	 * @param pCustomValueAssessed
	 * @param numberOfUnits
	 * @param customDutyPrct
	 * @param customDutyValue
	 * @param salesTaxPrct
	 * @param salesTaxValue
	 * @param regularityDutyPrct
	 * @param regularityDutyValue
	 * @param additionalCustomDutyPrct
	 * @param additionalCustomDutyValue
	 * @param additionalSalesTaxPrct
	 * @param additionalSalesTaxValue
	 * @param excisePrct
	 * @param exciseValue
	 * @param totalValue
	 * @param itPrct
	 * @param itValue
	 * @param remarks
	 * @param vrNumber
	 * @param supplyPriceExclTax
	 * @param markupPrct
	 * @param activeIndicator
	 * @param createdDate
	 * @param lastUpdated
	 * @param ftaPrct
	 * @param ftaValue
	 * @param grNumber
	 */
	public ProductPriceHistoryBean(int productPriceHistoryId, int outletId,
			String outletName, int userByCreatedById, int userByUpdatedById,
			int companyId, String companyName, int salesTaxAssociationId,
			int productId, String productUuid, int productVariantId,
			String productVariantUuid, String coCode, String sroNumber,
			String hsCode, String description, String dollarRate,
			String dUnitValueDeclared, String dUnitValueAssessed,
			String dTotalValueDeclared, String dTotalValueAssessed,
			String pCustomValueDeclared, String pCustomValueAssessed,
			String numberOfUnits, String customDutyPrct,
			String customDutyValue, String salesTaxPrct, String salesTaxValue,
			String regularityDutyPrct, String regularityDutyValue,
			String additionalCustomDutyPrct, String additionalCustomDutyValue,
			String additionalSalesTaxPrct, String additionalSalesTaxValue,
			String excisePrct, String exciseValue, String totalValue,
			String itPrct, String itValue, String remarks, int vrNumber,
			String supplyPriceExclTax, String markupPrct,
			boolean activeIndicator, String createdDate, String lastUpdated,
			String ftaPrct, String ftaValue, String grNumber) {
		this.productPriceHistoryId = productPriceHistoryId;
		this.outletId = outletId;
		this.outletName = outletName;
		this.userByCreatedById = userByCreatedById;
		this.userByUpdatedById = userByUpdatedById;
		this.companyId = companyId;
		this.companyName = companyName;
		this.salesTaxAssociationId = salesTaxAssociationId;
		this.productId = productId;
		this.productUuid = productUuid;
		this.productVariantId = productVariantId;
		this.productVariantUuid = productVariantUuid;
		this.coCode = coCode;
		this.sroNumber = sroNumber;
		this.hsCode = hsCode;
		this.description = description;
		this.dollarRate = dollarRate;
		this.dUnitValueDeclared = dUnitValueDeclared;
		this.dUnitValueAssessed = dUnitValueAssessed;
		this.dTotalValueDeclared = dTotalValueDeclared;
		this.dTotalValueAssessed = dTotalValueAssessed;
		this.pCustomValueDeclared = pCustomValueDeclared;
		this.pCustomValueAssessed = pCustomValueAssessed;
		this.numberOfUnits = numberOfUnits;
		this.customDutyPrct = customDutyPrct;
		this.customDutyValue = customDutyValue;
		this.salesTaxPrct = salesTaxPrct;
		this.salesTaxValue = salesTaxValue;
		this.regularityDutyPrct = regularityDutyPrct;
		this.regularityDutyValue = regularityDutyValue;
		this.additionalCustomDutyPrct = additionalCustomDutyPrct;
		this.additionalCustomDutyValue = additionalCustomDutyValue;
		this.additionalSalesTaxPrct = additionalSalesTaxPrct;
		this.additionalSalesTaxValue = additionalSalesTaxValue;
		this.excisePrct = excisePrct;
		this.exciseValue = exciseValue;
		this.totalValue = totalValue;
		this.itPrct = itPrct;
		this.itValue = itValue;
		this.remarks = remarks;
		this.vrNumber = vrNumber;
		this.supplyPriceExclTax = supplyPriceExclTax;
		this.markupPrct = markupPrct;
		this.activeIndicator = activeIndicator;
		this.createdDate = createdDate;
		this.lastUpdated = lastUpdated;
		this.ftaPrct = ftaPrct;
		this.ftaValue = ftaValue;
		this.grNumber = grNumber;
	}
	/**
	 * @return the productPriceHistoryId
	 */
	public int getProductPriceHistoryId() {
		return productPriceHistoryId;
	}
	/**
	 * @param productPriceHistoryId the productPriceHistoryId to set
	 */
	public void setProductPriceHistoryId(int productPriceHistoryId) {
		this.productPriceHistoryId = productPriceHistoryId;
	}
	/**
	 * @return the outletId
	 */
	public int getOutletId() {
		return outletId;
	}
	/**
	 * @param outletId the outletId to set
	 */
	public void setOutletId(int outletId) {
		this.outletId = outletId;
	}
	/**
	 * @return the outletName
	 */
	public String getOutletName() {
		return outletName;
	}
	/**
	 * @param outletName the outletName to set
	 */
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	/**
	 * @return the userByCreatedById
	 */
	public int getUserByCreatedById() {
		return userByCreatedById;
	}
	/**
	 * @param userByCreatedById the userByCreatedById to set
	 */
	public void setUserByCreatedById(int userByCreatedById) {
		this.userByCreatedById = userByCreatedById;
	}
	/**
	 * @return the userByUpdatedById
	 */
	public int getUserByUpdatedById() {
		return userByUpdatedById;
	}
	/**
	 * @param userByUpdatedById the userByUpdatedById to set
	 */
	public void setUserByUpdatedById(int userByUpdatedById) {
		this.userByUpdatedById = userByUpdatedById;
	}
	/**
	 * @return the companyId
	 */
	public int getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * @return the salesTaxAssociationId
	 */
	public int getSalesTaxAssociationId() {
		return salesTaxAssociationId;
	}
	/**
	 * @param salesTaxAssociationId the salesTaxAssociationId to set
	 */
	public void setSalesTaxAssociationId(int salesTaxAssociationId) {
		this.salesTaxAssociationId = salesTaxAssociationId;
	}
	/**
	 * @return the productId
	 */
	public int getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}
	/**
	 * @return the productUuid
	 */
	public String getProductUuid() {
		return productUuid;
	}
	/**
	 * @param productUuid the productUuid to set
	 */
	public void setProductUuid(String productUuid) {
		this.productUuid = productUuid;
	}
	/**
	 * @return the productVariantId
	 */
	public int getProductVariantId() {
		return productVariantId;
	}
	/**
	 * @param productVariantId the productVariantId to set
	 */
	public void setProductVariantId(int productVariantId) {
		this.productVariantId = productVariantId;
	}
	/**
	 * @return the productVariantUuid
	 */
	public String getProductVariantUuid() {
		return productVariantUuid;
	}
	/**
	 * @param productVariantUuid the productVariantUuid to set
	 */
	public void setProductVariantUuid(String productVariantUuid) {
		this.productVariantUuid = productVariantUuid;
	}
	/**
	 * @return the coCode
	 */
	public String getCoCode() {
		return coCode;
	}
	/**
	 * @param coCode the coCode to set
	 */
	public void setCoCode(String coCode) {
		this.coCode = coCode;
	}
	/**
	 * @return the sroNumber
	 */
	public String getSroNumber() {
		return sroNumber;
	}
	/**
	 * @param sroNumber the sroNumber to set
	 */
	public void setSroNumber(String sroNumber) {
		this.sroNumber = sroNumber;
	}
	/**
	 * @return the hsCode
	 */
	public String getHsCode() {
		return hsCode;
	}
	/**
	 * @param hsCode the hsCode to set
	 */
	public void setHsCode(String hsCode) {
		this.hsCode = hsCode;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the dollarRate
	 */
	public String getDollarRate() {
		return dollarRate;
	}
	/**
	 * @param dollarRate the dollarRate to set
	 */
	public void setDollarRate(String dollarRate) {
		this.dollarRate = dollarRate;
	}
	/**
	 * @return the dUnitValueDeclared
	 */
	public String getdUnitValueDeclared() {
		return dUnitValueDeclared;
	}
	/**
	 * @param dUnitValueDeclared the dUnitValueDeclared to set
	 */
	public void setdUnitValueDeclared(String dUnitValueDeclared) {
		this.dUnitValueDeclared = dUnitValueDeclared;
	}
	/**
	 * @return the dUnitValueAssessed
	 */
	public String getdUnitValueAssessed() {
		return dUnitValueAssessed;
	}
	/**
	 * @param dUnitValueAssessed the dUnitValueAssessed to set
	 */
	public void setdUnitValueAssessed(String dUnitValueAssessed) {
		this.dUnitValueAssessed = dUnitValueAssessed;
	}
	/**
	 * @return the dTotalValueDeclared
	 */
	public String getdTotalValueDeclared() {
		return dTotalValueDeclared;
	}
	/**
	 * @param dTotalValueDeclared the dTotalValueDeclared to set
	 */
	public void setdTotalValueDeclared(String dTotalValueDeclared) {
		this.dTotalValueDeclared = dTotalValueDeclared;
	}
	/**
	 * @return the dTotalValueAssessed
	 */
	public String getdTotalValueAssessed() {
		return dTotalValueAssessed;
	}
	/**
	 * @param dTotalValueAssessed the dTotalValueAssessed to set
	 */
	public void setdTotalValueAssessed(String dTotalValueAssessed) {
		this.dTotalValueAssessed = dTotalValueAssessed;
	}
	/**
	 * @return the pCustomValueDeclared
	 */
	public String getpCustomValueDeclared() {
		return pCustomValueDeclared;
	}
	/**
	 * @param pCustomValueDeclared the pCustomValueDeclared to set
	 */
	public void setpCustomValueDeclared(String pCustomValueDeclared) {
		this.pCustomValueDeclared = pCustomValueDeclared;
	}
	/**
	 * @return the pCustomValueAssessed
	 */
	public String getpCustomValueAssessed() {
		return pCustomValueAssessed;
	}
	/**
	 * @param pCustomValueAssessed the pCustomValueAssessed to set
	 */
	public void setpCustomValueAssessed(String pCustomValueAssessed) {
		this.pCustomValueAssessed = pCustomValueAssessed;
	}
	/**
	 * @return the numberOfUnits
	 */
	public String getNumberOfUnits() {
		return numberOfUnits;
	}
	/**
	 * @param numberOfUnits the numberOfUnits to set
	 */
	public void setNumberOfUnits(String numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}
	/**
	 * @return the customDutyPrct
	 */
	public String getCustomDutyPrct() {
		return customDutyPrct;
	}
	/**
	 * @param customDutyPrct the customDutyPrct to set
	 */
	public void setCustomDutyPrct(String customDutyPrct) {
		this.customDutyPrct = customDutyPrct;
	}
	/**
	 * @return the customDutyValue
	 */
	public String getCustomDutyValue() {
		return customDutyValue;
	}
	/**
	 * @param customDutyValue the customDutyValue to set
	 */
	public void setCustomDutyValue(String customDutyValue) {
		this.customDutyValue = customDutyValue;
	}
	/**
	 * @return the salesTaxPrct
	 */
	public String getSalesTaxPrct() {
		return salesTaxPrct;
	}
	/**
	 * @param salesTaxPrct the salesTaxPrct to set
	 */
	public void setSalesTaxPrct(String salesTaxPrct) {
		this.salesTaxPrct = salesTaxPrct;
	}
	/**
	 * @return the salesTaxValue
	 */
	public String getSalesTaxValue() {
		return salesTaxValue;
	}
	/**
	 * @param salesTaxValue the salesTaxValue to set
	 */
	public void setSalesTaxValue(String salesTaxValue) {
		this.salesTaxValue = salesTaxValue;
	}
	/**
	 * @return the regularityDutyPrct
	 */
	public String getRegularityDutyPrct() {
		return regularityDutyPrct;
	}
	/**
	 * @param regularityDutyPrct the regularityDutyPrct to set
	 */
	public void setRegularityDutyPrct(String regularityDutyPrct) {
		this.regularityDutyPrct = regularityDutyPrct;
	}
	/**
	 * @return the regularityDutyValue
	 */
	public String getRegularityDutyValue() {
		return regularityDutyValue;
	}
	/**
	 * @param regularityDutyValue the regularityDutyValue to set
	 */
	public void setRegularityDutyValue(String regularityDutyValue) {
		this.regularityDutyValue = regularityDutyValue;
	}
	/**
	 * @return the additionalCustomDutyPrct
	 */
	public String getAdditionalCustomDutyPrct() {
		return additionalCustomDutyPrct;
	}
	/**
	 * @param additionalCustomDutyPrct the additionalCustomDutyPrct to set
	 */
	public void setAdditionalCustomDutyPrct(String additionalCustomDutyPrct) {
		this.additionalCustomDutyPrct = additionalCustomDutyPrct;
	}
	/**
	 * @return the additionalCustomDutyValue
	 */
	public String getAdditionalCustomDutyValue() {
		return additionalCustomDutyValue;
	}
	/**
	 * @param additionalCustomDutyValue the additionalCustomDutyValue to set
	 */
	public void setAdditionalCustomDutyValue(String additionalCustomDutyValue) {
		this.additionalCustomDutyValue = additionalCustomDutyValue;
	}
	/**
	 * @return the additionalSalesTaxPrct
	 */
	public String getAdditionalSalesTaxPrct() {
		return additionalSalesTaxPrct;
	}
	/**
	 * @param additionalSalesTaxPrct the additionalSalesTaxPrct to set
	 */
	public void setAdditionalSalesTaxPrct(String additionalSalesTaxPrct) {
		this.additionalSalesTaxPrct = additionalSalesTaxPrct;
	}
	/**
	 * @return the additionalSalesTaxValue
	 */
	public String getAdditionalSalesTaxValue() {
		return additionalSalesTaxValue;
	}
	/**
	 * @param additionalSalesTaxValue the additionalSalesTaxValue to set
	 */
	public void setAdditionalSalesTaxValue(String additionalSalesTaxValue) {
		this.additionalSalesTaxValue = additionalSalesTaxValue;
	}
	/**
	 * @return the excisePrct
	 */
	public String getExcisePrct() {
		return excisePrct;
	}
	/**
	 * @param excisePrct the excisePrct to set
	 */
	public void setExcisePrct(String excisePrct) {
		this.excisePrct = excisePrct;
	}
	/**
	 * @return the exciseValue
	 */
	public String getExciseValue() {
		return exciseValue;
	}
	/**
	 * @param exciseValue the exciseValue to set
	 */
	public void setExciseValue(String exciseValue) {
		this.exciseValue = exciseValue;
	}
	/**
	 * @return the totalValue
	 */
	public String getTotalValue() {
		return totalValue;
	}
	/**
	 * @param totalValue the totalValue to set
	 */
	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}
	/**
	 * @return the itPrct
	 */
	public String getItPrct() {
		return itPrct;
	}
	/**
	 * @param itPrct the itPrct to set
	 */
	public void setItPrct(String itPrct) {
		this.itPrct = itPrct;
	}
	/**
	 * @return the itValue
	 */
	public String getItValue() {
		return itValue;
	}
	/**
	 * @param itValue the itValue to set
	 */
	public void setItValue(String itValue) {
		this.itValue = itValue;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the vrNumber
	 */
	public int getVrNumber() {
		return vrNumber;
	}
	/**
	 * @param vrNumber the vrNumber to set
	 */
	public void setVrNumber(int vrNumber) {
		this.vrNumber = vrNumber;
	}
	/**
	 * @return the supplyPriceExclTax
	 */
	public String getSupplyPriceExclTax() {
		return supplyPriceExclTax;
	}
	/**
	 * @param supplyPriceExclTax the supplyPriceExclTax to set
	 */
	public void setSupplyPriceExclTax(String supplyPriceExclTax) {
		this.supplyPriceExclTax = supplyPriceExclTax;
	}
	/**
	 * @return the markupPrct
	 */
	public String getMarkupPrct() {
		return markupPrct;
	}
	/**
	 * @param markupPrct the markupPrct to set
	 */
	public void setMarkupPrct(String markupPrct) {
		this.markupPrct = markupPrct;
	}
	/**
	 * @return the activeIndicator
	 */
	public boolean isActiveIndicator() {
		return activeIndicator;
	}
	/**
	 * @param activeIndicator the activeIndicator to set
	 */
	public void setActiveIndicator(boolean activeIndicator) {
		this.activeIndicator = activeIndicator;
	}
	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the lastUpdated
	 */
	public String getLastUpdated() {
		return lastUpdated;
	}
	/**
	 * @param lastUpdated the lastUpdated to set
	 */
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	/**
	 * @return the ftaPrct
	 */
	public String getFtaPrct() {
		return ftaPrct;
	}
	/**
	 * @param ftaPrct the ftaPrct to set
	 */
	public void setFtaPrct(String ftaPrct) {
		this.ftaPrct = ftaPrct;
	}
	/**
	 * @return the ftaValue
	 */
	public String getFtaValue() {
		return ftaValue;
	}
	/**
	 * @param ftaValue the ftaValue to set
	 */
	public void setFtaValue(String ftaValue) {
		this.ftaValue = ftaValue;
	}
	/**
	 * @return the grNumber
	 */
	public String getGrNumber() {
		return grNumber;
	}
	/**
	 * @param grNumber the grNumber to set
	 */
	public void setGrNumber(String grNumber) {
		this.grNumber = grNumber;
	}
}
