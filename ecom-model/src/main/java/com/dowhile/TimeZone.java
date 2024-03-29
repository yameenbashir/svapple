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
 * TimeZone generated by hbm2java
 */
@Entity
@Table(name="time_zone"
    ,catalog="ecom"
)
public class TimeZone  implements java.io.Serializable {


     private Integer timeZoneId;
     private String timeZoneValue;
     private boolean activeIndicator;
     private Date createdDate;
     private Date lastUpdated;
     private Integer createdBy;
     private Integer updatedBy;
     private Set<Outlet> outlets = new HashSet<Outlet>(0);
     private Set<Company> companies = new HashSet<Company>(0);

    public TimeZone() {
    }

	
    public TimeZone(String timeZoneValue, boolean activeIndicator, Date createdDate, Date lastUpdated) {
        this.timeZoneValue = timeZoneValue;
        this.activeIndicator = activeIndicator;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
    }
    public TimeZone(String timeZoneValue, boolean activeIndicator, Date createdDate, Date lastUpdated, Integer createdBy, Integer updatedBy, Set<Outlet> outlets, Set<Company> companies) {
       this.timeZoneValue = timeZoneValue;
       this.activeIndicator = activeIndicator;
       this.createdDate = createdDate;
       this.lastUpdated = lastUpdated;
       this.createdBy = createdBy;
       this.updatedBy = updatedBy;
       this.outlets = outlets;
       this.companies = companies;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="TIME_ZONE_ID", unique=true, nullable=false)
    public Integer getTimeZoneId() {
        return this.timeZoneId;
    }
    
    public void setTimeZoneId(Integer timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    
    @Column(name="TIME_ZONE_VALUE", nullable=false, length=256)
    public String getTimeZoneValue() {
        return this.timeZoneValue;
    }
    
    public void setTimeZoneValue(String timeZoneValue) {
        this.timeZoneValue = timeZoneValue;
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

@OneToMany(fetch=FetchType.LAZY, mappedBy="timeZone")
    public Set<Outlet> getOutlets() {
        return this.outlets;
    }
    
    public void setOutlets(Set<Outlet> outlets) {
        this.outlets = outlets;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="timeZone")
    public Set<Company> getCompanies() {
        return this.companies;
    }
    
    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }




}


