package com.dowhile;
// Generated Aug 17, 2017 1:48:25 PM by Hibernate Tools 3.4.0.CR1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * GlTransConfig generated by hbm2java
 */
@Entity
@Table(name="gl_trans_config"
    ,catalog="ecom"
)
public class GlTransConfig  implements java.io.Serializable {


     private Integer glTransConfigId;
     private ChartOfAccount chartOfAccount;
     private GeneralLedgerEvent generalLedgerEventByGlSubEventAssociationId;
     private User userByCreatedBy;
     private User userByUpdatedBy;
     private Company company;
     private GeneralLedgerEvent generalLedgerEventByGlEventAssociationId;
     private String actionType;
     private boolean activeIndicator;
     private Date createdDate;
     private Date lastUpdated;

    public GlTransConfig() {
    }

    public GlTransConfig(ChartOfAccount chartOfAccount, GeneralLedgerEvent generalLedgerEventByGlSubEventAssociationId, User userByCreatedBy, User userByUpdatedBy, Company company, GeneralLedgerEvent generalLedgerEventByGlEventAssociationId, String actionType, boolean activeIndicator, Date createdDate, Date lastUpdated) {
       this.chartOfAccount = chartOfAccount;
       this.generalLedgerEventByGlSubEventAssociationId = generalLedgerEventByGlSubEventAssociationId;
       this.userByCreatedBy = userByCreatedBy;
       this.userByUpdatedBy = userByUpdatedBy;
       this.company = company;
       this.generalLedgerEventByGlEventAssociationId = generalLedgerEventByGlEventAssociationId;
       this.actionType = actionType;
       this.activeIndicator = activeIndicator;
       this.createdDate = createdDate;
       this.lastUpdated = lastUpdated;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="GL_TRANS_CONFIG_ID", unique=true, nullable=false)
    public Integer getGlTransConfigId() {
        return this.glTransConfigId;
    }
    
    public void setGlTransConfigId(Integer glTransConfigId) {
        this.glTransConfigId = glTransConfigId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CHART_OF_ACCOUNT_ASSOCIATION_ID", nullable=false)
    public ChartOfAccount getChartOfAccount() {
        return this.chartOfAccount;
    }
    
    public void setChartOfAccount(ChartOfAccount chartOfAccount) {
        this.chartOfAccount = chartOfAccount;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="GL_SUB_EVENT_ASSOCIATION_ID", nullable=false)
    public GeneralLedgerEvent getGeneralLedgerEventByGlSubEventAssociationId() {
        return this.generalLedgerEventByGlSubEventAssociationId;
    }
    
    public void setGeneralLedgerEventByGlSubEventAssociationId(GeneralLedgerEvent generalLedgerEventByGlSubEventAssociationId) {
        this.generalLedgerEventByGlSubEventAssociationId = generalLedgerEventByGlSubEventAssociationId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CREATED_BY", nullable=false)
    public User getUserByCreatedBy() {
        return this.userByCreatedBy;
    }
    
    public void setUserByCreatedBy(User userByCreatedBy) {
        this.userByCreatedBy = userByCreatedBy;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="UPDATED_BY", nullable=false)
    public User getUserByUpdatedBy() {
        return this.userByUpdatedBy;
    }
    
    public void setUserByUpdatedBy(User userByUpdatedBy) {
        this.userByUpdatedBy = userByUpdatedBy;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="COMPANY_ASSOCIATION_ID", nullable=false)
    public Company getCompany() {
        return this.company;
    }
    
    public void setCompany(Company company) {
        this.company = company;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="GL_EVENT_ASSOCIATION_ID", nullable=false)
    public GeneralLedgerEvent getGeneralLedgerEventByGlEventAssociationId() {
        return this.generalLedgerEventByGlEventAssociationId;
    }
    
    public void setGeneralLedgerEventByGlEventAssociationId(GeneralLedgerEvent generalLedgerEventByGlEventAssociationId) {
        this.generalLedgerEventByGlEventAssociationId = generalLedgerEventByGlEventAssociationId;
    }

    
    @Column(name="ACTION_TYPE", nullable=false, length=10)
    public String getActionType() {
        return this.actionType;
    }
    
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    
    @Column(name="ACTIVE_INDICATOR", nullable=false)
    public boolean isActiveIndicator() {
        return this.activeIndicator;
    }
    
    public void setActiveIndicator(boolean activeIndicator) {
        this.activeIndicator = activeIndicator;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_DATE", nullable=false, length=19)
    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LAST_UPDATED", nullable=false, length=19)
    public Date getLastUpdated() {
        return this.lastUpdated;
    }
    
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }




}


