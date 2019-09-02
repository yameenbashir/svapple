package com.dowhile;

import java.math.BigDecimal;
import java.sql.Timestamp;

// Generated Jun 8, 2017 2:41:51 AM by Hibernate Tools 3.4.0.CR1




public class InvoiceMainCustom  implements java.io.Serializable {

	public String getINVOICE_REF_NBR() {
		return INVOICE_REF_NBR;
	}

	public void setINVOICE_REF_NBR(String iNVOICE_REF_NBR) {
		INVOICE_REF_NBR = iNVOICE_REF_NBR;
	}

	public String getINVOICE_NOTES() {
		return INVOICE_NOTES;
	}

	public void setINVOICE_NOTES(String iNVOICE_NOTES) {
		INVOICE_NOTES = iNVOICE_NOTES;
	}

	public BigDecimal getINVOICE_DISCOUNT() {
		return INVOICE_DISCOUNT;
	}

	public void setINVOICE_DISCOUNT(BigDecimal iNVOICE_DISCOUNT) {
		INVOICE_DISCOUNT = iNVOICE_DISCOUNT;
	}

	public BigDecimal getINVOICE_TAX() {
		return INVOICE_TAX;
	}

	public void setINVOICE_TAX(BigDecimal iNVOICE_TAX) {
		INVOICE_TAX = iNVOICE_TAX;
	}

	public String getINVC_TYPE_CDE() {
		return INVC_TYPE_CDE;
	}

	public void setINVC_TYPE_CDE(String iNVC_TYPE_CDE) {
		INVC_TYPE_CDE = iNVC_TYPE_CDE;
	}

	public Timestamp getINVOICE_GENERATION_DTE() {
		return INVOICE_GENERATION_DTE;
	}

	public void setINVOICE_GENERATION_DTE(Timestamp iNVOICE_GENERATION_DTE) {
		INVOICE_GENERATION_DTE = iNVOICE_GENERATION_DTE;
	}

	public Timestamp getINVOICE_CANCEL_DTE() {
		return INVOICE_CANCEL_DTE;
	}

	public void setINVOICE_CANCEL_DTE(Timestamp iNVOICE_CANCEL_DTE) {
		INVOICE_CANCEL_DTE = iNVOICE_CANCEL_DTE;
	}

	public BigDecimal getINVOICE_AMT() {
		return INVOICE_AMT;
	}

	public void setINVOICE_AMT(BigDecimal iNVOICE_AMT) {
		INVOICE_AMT = iNVOICE_AMT;
	}

	public BigDecimal getINVOICE_DISCOUNT_AMT() {
		return INVOICE_DISCOUNT_AMT;
	}

	public void setINVOICE_DISCOUNT_AMT(BigDecimal iNVOICE_DISCOUNT_AMT) {
		INVOICE_DISCOUNT_AMT = iNVOICE_DISCOUNT_AMT;
	}

	public BigDecimal getINVOICE_NET_AMT() {
		return INVOICE_NET_AMT;
	}

	public void setINVOICE_NET_AMT(BigDecimal iNVOICE_NET_AMT) {
		INVOICE_NET_AMT = iNVOICE_NET_AMT;
	}

	public BigDecimal getINVOICE_GIVEN_AMT() {
		return INVOICE_GIVEN_AMT;
	}

	public void setINVOICE_GIVEN_AMT(BigDecimal iNVOICE_GIVEN_AMT) {
		INVOICE_GIVEN_AMT = iNVOICE_GIVEN_AMT;
	}

	public BigDecimal getINVOICE_ORIGNAL_AMT() {
		return INVOICE_ORIGNAL_AMT;
	}

	public void setINVOICE_ORIGNAL_AMT(BigDecimal iNVOICE_ORIGNAL_AMT) {
		INVOICE_ORIGNAL_AMT = iNVOICE_ORIGNAL_AMT;
	}

	public BigDecimal getSETTLED_AMT() {
		return SETTLED_AMT;
	}

	public void setSETTLED_AMT(BigDecimal sETTLED_AMT) {
		SETTLED_AMT = sETTLED_AMT;
	}

	public Integer getSTATUS_ASSOCICATION_ID() {
		return STATUS_ASSOCICATION_ID;
	}

	public void setSTATUS_ASSOCICATION_ID(Integer sTATUS_ASSOCICATION_ID) {
		STATUS_ASSOCICATION_ID = sTATUS_ASSOCICATION_ID;
	}

	public Integer getORDER_ASSOCICATION_ID() {
		return ORDER_ASSOCICATION_ID;
	}

	public void setORDER_ASSOCICATION_ID(Integer oRDER_ASSOCICATION_ID) {
		ORDER_ASSOCICATION_ID = oRDER_ASSOCICATION_ID;
	}

	public Integer getCONTACT_ASSOCIATION_ID() {
		return CONTACT_ASSOCIATION_ID;
	}

	public void setCONTACT_ASSOCIATION_ID(Integer cONTACT_ASSOCIATION_ID) {
		CONTACT_ASSOCIATION_ID = cONTACT_ASSOCIATION_ID;
	}

	public Integer getCOMPANY_ASSOCIATION_ID() {
		return COMPANY_ASSOCIATION_ID;
	}

	public void setCOMPANY_ASSOCIATION_ID(Integer cOMPANY_ASSOCIATION_ID) {
		COMPANY_ASSOCIATION_ID = cOMPANY_ASSOCIATION_ID;
	}

	public Integer getOUTLET_ASSOCICATION_ID() {
		return OUTLET_ASSOCICATION_ID;
	}

	public void setOUTLET_ASSOCICATION_ID(Integer oUTLET_ASSOCICATION_ID) {
		OUTLET_ASSOCICATION_ID = oUTLET_ASSOCICATION_ID;
	}

	public Integer getDAILY_REGISTER_ASSOCICATION_ID() {
		return DAILY_REGISTER_ASSOCICATION_ID;
	}

	public void setDAILY_REGISTER_ASSOCICATION_ID(
			Integer dAILY_REGISTER_ASSOCICATION_ID) {
		DAILY_REGISTER_ASSOCICATION_ID = dAILY_REGISTER_ASSOCICATION_ID;
	}

	public Integer getPAYMENT_TYPE_ASSOCICATION_ID() {
		return PAYMENT_TYPE_ASSOCICATION_ID;
	}

	public void setPAYMENT_TYPE_ASSOCICATION_ID(Integer pAYMENT_TYPE_ASSOCICATION_ID) {
		PAYMENT_TYPE_ASSOCICATION_ID = pAYMENT_TYPE_ASSOCICATION_ID;
	}

	public Integer getSALES_USER() {
		return SALES_USER;
	}

	public void setSALES_USER(Integer sALES_USER) {
		SALES_USER = sALES_USER;
	}

	public Integer getCONTACT_ID() {
		return CONTACT_ID;
	}

	public void setCONTACT_ID(Integer cONTACT_ID) {
		CONTACT_ID = cONTACT_ID;
	}

	public String getCONTACT_NAME() {
		return CONTACT_NAME;
	}

	public void setCONTACT_NAME(String cONTACT_NAME) {
		CONTACT_NAME = cONTACT_NAME;
	}

	public String getFIRST_NAME() {
		return FIRST_NAME;
	}

	public void setFIRST_NAME(String fIRST_NAME) {
		FIRST_NAME = fIRST_NAME;
	}

	public String getLAST_NAME() {
		return LAST_NAME;
	}

	public void setLAST_NAME(String lAST_NAME) {
		LAST_NAME = lAST_NAME;
	}

	private Integer INVOICE_MAIN_ID;
	private String INVOICE_REF_NBR;
	private String INVOICE_NOTES;
	private BigDecimal INVOICE_DISCOUNT;
	private BigDecimal INVOICE_TAX;
	private String INVC_TYPE_CDE;
	private Timestamp INVOICE_GENERATION_DTE;
	private Timestamp INVOICE_CANCEL_DTE;
	private BigDecimal INVOICE_AMT;
	private BigDecimal INVOICE_DISCOUNT_AMT;
	private BigDecimal INVOICE_NET_AMT;
	private BigDecimal INVOICE_GIVEN_AMT;
	private BigDecimal INVOICE_ORIGNAL_AMT;
	private BigDecimal SETTLED_AMT;
	private Integer STATUS_ASSOCICATION_ID;
	private Integer ORDER_ASSOCICATION_ID;
	private Integer CONTACT_ASSOCIATION_ID;
	private Integer COMPANY_ASSOCIATION_ID;
	private Integer OUTLET_ASSOCICATION_ID;
	private Integer DAILY_REGISTER_ASSOCICATION_ID;
	private Integer PAYMENT_TYPE_ASSOCICATION_ID;
	private Integer SALES_USER;
	private Integer CONTACT_ID;
	private String CONTACT_NAME;
	private String FIRST_NAME;
	private String LAST_NAME;
	private Timestamp CREATED_DATE;
	private String SALE_PERSON_NAME;
	private String STATUS_DESC;
	private Integer STATUS_ID;
	public Integer getINVOICE_MAIN_ID() {
		return INVOICE_MAIN_ID;
	}

	public void setINVOICE_MAIN_ID(Integer iNVOICE_MAIN_ID) {
		INVOICE_MAIN_ID = iNVOICE_MAIN_ID;
	}

	public Timestamp getCREATED_DATE() {
		return CREATED_DATE;
	}

	public void setCREATED_DATE(Timestamp cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	public String getSALE_PERSON_NAME() {
		return SALE_PERSON_NAME;
	}

	public void setSALE_PERSON_NAME(String sALE_PERSON_NAME) {
		SALE_PERSON_NAME = sALE_PERSON_NAME;
	}

	public String getSTATUS_DESC() {
		return STATUS_DESC;
	}

	public void setSTATUS_DESC(String sTATUS_DESC) {
		STATUS_DESC = sTATUS_DESC;
	}

	public Integer getSTATUS_ID() {
		return STATUS_ID;
	}

	public void setSTATUS_ID(Integer sTATUS_ID) {
		STATUS_ID = sTATUS_ID;
	}
    
   
}


