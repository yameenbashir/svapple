/**
 * 
 */
package com.dowhile.frontend.mapping.bean;


/**
 * @author Hafiz Yameen Bashir
 *
 */
public class RegisterReportBean {
	
	private String dailyRegisterId;
	private String outletAssocicationId;
    private String outletName;
    private String cashAmtActual;
    private String creditCardAmtActual;
    private String openingDate;
    private String closingDate;
    private String openBy;
    private String closeBy;
    private String status;
    private String companyAssociationId;
    private String registerClosingNotes;
    private String registerOpeningNotes;
	/**
	 * 
	 */
	public RegisterReportBean() {
	}
	/**
	 * @param dailyRegisterId
	 * @param outletAssocicationId
	 * @param outletName
	 * @param cashAmtActual
	 * @param creditCardAmtActual
	 * @param openingDate
	 * @param closingDate
	 * @param openBy
	 * @param closeBy
	 * @param status
	 * @param companyAssociationId
	 * @param registerClosingNotes
	 * @param registerOpeningNotes
	 */
	public RegisterReportBean(String dailyRegisterId,
			String outletAssocicationId, String outletName,
			String cashAmtActual, String creditCardAmtActual,
			String openingDate, String closingDate, String openBy,
			String closeBy, String status, String companyAssociationId,
			String registerClosingNotes, String registerOpeningNotes) {
		this.dailyRegisterId = dailyRegisterId;
		this.outletAssocicationId = outletAssocicationId;
		this.outletName = outletName;
		this.cashAmtActual = cashAmtActual;
		this.creditCardAmtActual = creditCardAmtActual;
		this.openingDate = openingDate;
		this.closingDate = closingDate;
		this.openBy = openBy;
		this.closeBy = closeBy;
		this.status = status;
		this.companyAssociationId = companyAssociationId;
		this.registerClosingNotes = registerClosingNotes;
		this.registerOpeningNotes = registerOpeningNotes;
	}
	/**
	 * @return the dailyRegisterId
	 */
	public String getDailyRegisterId() {
		return dailyRegisterId;
	}
	/**
	 * @param dailyRegisterId the dailyRegisterId to set
	 */
	public void setDailyRegisterId(String dailyRegisterId) {
		this.dailyRegisterId = dailyRegisterId;
	}
	/**
	 * @return the outletAssocicationId
	 */
	public String getOutletAssocicationId() {
		return outletAssocicationId;
	}
	/**
	 * @param outletAssocicationId the outletAssocicationId to set
	 */
	public void setOutletAssocicationId(String outletAssocicationId) {
		this.outletAssocicationId = outletAssocicationId;
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
	 * @return the cashAmtActual
	 */
	public String getCashAmtActual() {
		return cashAmtActual;
	}
	/**
	 * @param cashAmtActual the cashAmtActual to set
	 */
	public void setCashAmtActual(String cashAmtActual) {
		this.cashAmtActual = cashAmtActual;
	}
	/**
	 * @return the creditCardAmtActual
	 */
	public String getCreditCardAmtActual() {
		return creditCardAmtActual;
	}
	/**
	 * @param creditCardAmtActual the creditCardAmtActual to set
	 */
	public void setCreditCardAmtActual(String creditCardAmtActual) {
		this.creditCardAmtActual = creditCardAmtActual;
	}
	/**
	 * @return the openingDate
	 */
	public String getOpeningDate() {
		return openingDate;
	}
	/**
	 * @param openingDate the openingDate to set
	 */
	public void setOpeningDate(String openingDate) {
		this.openingDate = openingDate;
	}
	/**
	 * @return the closingDate
	 */
	public String getClosingDate() {
		return closingDate;
	}
	/**
	 * @param closingDate the closingDate to set
	 */
	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}
	/**
	 * @return the openBy
	 */
	public String getOpenBy() {
		return openBy;
	}
	/**
	 * @param openBy the openBy to set
	 */
	public void setOpenBy(String openBy) {
		this.openBy = openBy;
	}
	/**
	 * @return the closeBy
	 */
	public String getCloseBy() {
		return closeBy;
	}
	/**
	 * @param closeBy the closeBy to set
	 */
	public void setCloseBy(String closeBy) {
		this.closeBy = closeBy;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the companyAssociationId
	 */
	public String getCompanyAssociationId() {
		return companyAssociationId;
	}
	/**
	 * @param companyAssociationId the companyAssociationId to set
	 */
	public void setCompanyAssociationId(String companyAssociationId) {
		this.companyAssociationId = companyAssociationId;
	}
	/**
	 * @return the registerClosingNotes
	 */
	public String getRegisterClosingNotes() {
		return registerClosingNotes;
	}
	/**
	 * @param registerClosingNotes the registerClosingNotes to set
	 */
	public void setRegisterClosingNotes(String registerClosingNotes) {
		this.registerClosingNotes = registerClosingNotes;
	}
	/**
	 * @return the registerOpeningNotes
	 */
	public String getRegisterOpeningNotes() {
		return registerOpeningNotes;
	}
	/**
	 * @param registerOpeningNotes the registerOpeningNotes to set
	 */
	public void setRegisterOpeningNotes(String registerOpeningNotes) {
		this.registerOpeningNotes = registerOpeningNotes;
	}
}
