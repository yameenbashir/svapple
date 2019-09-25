package com.dowhile;
// Generated Aug 17, 2017 1:48:25 PM by Hibernate Tools 3.4.0.CR1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ChartOfAccount generated by hbm2java
 */
@Entity
@Table(name="chart_of_account"
    ,catalog="ecom"
)
public class ChartOfAccount  implements java.io.Serializable {


     private Integer chartOfAccountId;
     private SalesTax salesTax;
     private AccountType accountType;
     private User userByCreatedBy;
     private User userByUpdatedBy;
     private Company company;
     private String chartOfAccountCode;
     private String chartOfAccountName;
     private String description;
     private boolean activeIndicator;
     private Date createdDate;
     private Date lastUpdated;
     private Set<GeneralLedger> generalLedgers = new HashSet<GeneralLedger>(0);
     private Set<GlTransConfig> glTransConfigs = new HashSet<GlTransConfig>(0);

    public ChartOfAccount() {
    }

	
    public ChartOfAccount(SalesTax salesTax, AccountType accountType, User userByCreatedBy, User userByUpdatedBy, Company company, String chartOfAccountCode, String chartOfAccountName, boolean activeIndicator, Date createdDate, Date lastUpdated) {
        this.salesTax = salesTax;
        this.accountType = accountType;
        this.userByCreatedBy = userByCreatedBy;
        this.userByUpdatedBy = userByUpdatedBy;
        this.company = company;
        this.chartOfAccountCode = chartOfAccountCode;
        this.chartOfAccountName = chartOfAccountName;
        this.activeIndicator = activeIndicator;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
    }
    public ChartOfAccount(SalesTax salesTax, AccountType accountType, User userByCreatedBy, User userByUpdatedBy, Company company, String chartOfAccountCode, String chartOfAccountName, String description, boolean activeIndicator, Date createdDate, Date lastUpdated, Set<GeneralLedger> generalLedgers, Set<GlTransConfig> glTransConfigs) {
       this.salesTax = salesTax;
       this.accountType = accountType;
       this.userByCreatedBy = userByCreatedBy;
       this.userByUpdatedBy = userByUpdatedBy;
       this.company = company;
       this.chartOfAccountCode = chartOfAccountCode;
       this.chartOfAccountName = chartOfAccountName;
       this.description = description;
       this.activeIndicator = activeIndicator;
       this.createdDate = createdDate;
       this.lastUpdated = lastUpdated;
       this.generalLedgers = generalLedgers;
       this.glTransConfigs = glTransConfigs;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="CHART_OF_ACCOUNT_ID", unique=true, nullable=false)
    public Integer getChartOfAccountId() {
        return this.chartOfAccountId;
    }
    
    public void setChartOfAccountId(Integer chartOfAccountId) {
        this.chartOfAccountId = chartOfAccountId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SALES_TAX_ASSOCIATION_ID", nullable=false)
    public SalesTax getSalesTax() {
        return this.salesTax;
    }
    
    public void setSalesTax(SalesTax salesTax) {
        this.salesTax = salesTax;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ACCOUNT_TYPE_ASSOCIATION_ID", nullable=false)
    public AccountType getAccountType() {
        return this.accountType;
    }
    
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
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

    
    @Column(name="CHART_OF_ACCOUNT_CODE", nullable=false, length=256)
    public String getChartOfAccountCode() {
        return this.chartOfAccountCode;
    }
    
    public void setChartOfAccountCode(String chartOfAccountCode) {
        this.chartOfAccountCode = chartOfAccountCode;
    }

    
    @Column(name="CHART_OF_ACCOUNT_NAME", nullable=false, length=256)
    public String getChartOfAccountName() {
        return this.chartOfAccountName;
    }
    
    public void setChartOfAccountName(String chartOfAccountName) {
        this.chartOfAccountName = chartOfAccountName;
    }

    
    @Column(name="DESCRIPTION", length=256)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
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

@OneToMany(fetch=FetchType.LAZY, mappedBy="chartOfAccount")
    public Set<GeneralLedger> getGeneralLedgers() {
        return this.generalLedgers;
    }
    
    public void setGeneralLedgers(Set<GeneralLedger> generalLedgers) {
        this.generalLedgers = generalLedgers;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="chartOfAccount")
    public Set<GlTransConfig> getGlTransConfigs() {
        return this.glTransConfigs;
    }
    
    public void setGlTransConfigs(Set<GlTransConfig> glTransConfigs) {
        this.glTransConfigs = glTransConfigs;
    }




}

