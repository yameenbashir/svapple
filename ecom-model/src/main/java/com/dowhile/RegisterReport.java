package com.dowhile;
// Generated Aug 17, 2017 1:48:25 PM by Hibernate Tools 3.4.0.CR1


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * RegisterReport generated by hbm2java
 */
@Entity
@Table(name="Register_Report"
    ,catalog="ecom"
)
public class RegisterReport  implements java.io.Serializable {


    private RegisterReportId id;

   public RegisterReport() {
   }

   public RegisterReport(RegisterReportId id) {
      this.id = id;
   }
  
    @EmbeddedId

   
   @AttributeOverrides( {
       @AttributeOverride(name="outletAssocicationId", column=@Column(name="OUTLET_ASSOCICATION_ID") ), 
       @AttributeOverride(name="outletName", column=@Column(name="OUTLET_NAME", length=100) ), 
       @AttributeOverride(name="cashAmtActual", column=@Column(name="CASH_AMT_ACTUAL", nullable=false, precision=20) ), 
       @AttributeOverride(name="creditCardAmtActual", column=@Column(name="CREDIT_CARD_AMT_ACTUAL", nullable=false, precision=20) ), 
       @AttributeOverride(name="openingDate", column=@Column(name="OPENING_DATE") ), 
       @AttributeOverride(name="closingDate", column=@Column(name="CLOSING_DATE") ), 
       @AttributeOverride(name="openBy", column=@Column(name="Open_By") ), 
       @AttributeOverride(name="closeBy", column=@Column(name="Close_By") ), 
       @AttributeOverride(name="status", column=@Column(name="Status", length=45) ), 
       @AttributeOverride(name="companyAssociationId", column=@Column(name="COMPANY_ASSOCIATION_ID", nullable=false) ), 
       @AttributeOverride(name="dailyRegisterId", column=@Column(name="DAILY_REGISTER_ID", nullable=false) ), 
       @AttributeOverride(name="registerClosingNotes", column=@Column(name="REGISTER_CLOSING_NOTES", length=256) ), 
       @AttributeOverride(name="registerOpeningNotes", column=@Column(name="REGISTER_OPENING_NOTES", length=256) ) } )
   public RegisterReportId getId() {
       return this.id;
   }
   
   public void setId(RegisterReportId id) {
       this.id = id;
   }




}


