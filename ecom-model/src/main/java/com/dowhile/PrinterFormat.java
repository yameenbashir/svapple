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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PrinterFormat generated by hbm2java
 */
@Entity
@Table(name="printer_format"
    ,catalog="ecom"
)
public class PrinterFormat  implements java.io.Serializable {


     private Integer printerFormatId;
     private String printerFormatValue;
     private boolean activeIndicator;
     private Date createdDate;
     private Date lastUpdated;
     private Integer createdBy;
     private Integer updatedBy;
     private Set<Company> companies = new HashSet<Company>(0);

    public PrinterFormat() {
    }

	
    public PrinterFormat(String printerFormatValue, boolean activeIndicator, Date createdDate, Date lastUpdated) {
        this.printerFormatValue = printerFormatValue;
        this.activeIndicator = activeIndicator;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
    }
    public PrinterFormat(String printerFormatValue, boolean activeIndicator, Date createdDate, Date lastUpdated, Integer createdBy, Integer updatedBy, Set<Company> companies) {
       this.printerFormatValue = printerFormatValue;
       this.activeIndicator = activeIndicator;
       this.createdDate = createdDate;
       this.lastUpdated = lastUpdated;
       this.createdBy = createdBy;
       this.updatedBy = updatedBy;
       this.companies = companies;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="PRINTER_FORMAT_ID", unique=true, nullable=false)
    public Integer getPrinterFormatId() {
        return this.printerFormatId;
    }
    
    public void setPrinterFormatId(Integer printerFormatId) {
        this.printerFormatId = printerFormatId;
    }

    
    @Column(name="PRINTER_FORMAT_VALUE", nullable=false, length=256)
    public String getPrinterFormatValue() {
        return this.printerFormatValue;
    }
    
    public void setPrinterFormatValue(String printerFormatValue) {
        this.printerFormatValue = printerFormatValue;
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

    
    @Column(name="CREATED_BY")
    public Integer getCreatedBy() {
        return this.createdBy;
    }
    
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    
    @Column(name="UPDATED_BY")
    public Integer getUpdatedBy() {
        return this.updatedBy;
    }
    
    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="printerFormat")
    public Set<Company> getCompanies() {
        return this.companies;
    }
    
    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }




}


