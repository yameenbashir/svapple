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
import javax.persistence.UniqueConstraint;

/**
 * InventoryCountType generated by hbm2java
 */
@Entity
@Table(name="inventory_count_type"
    ,catalog="ecom"
    , uniqueConstraints = @UniqueConstraint(columnNames="INVENTORY_COUNT_TYPE_CODE") 
)
public class InventoryCountType  implements java.io.Serializable {


     private Integer inventoryCountTypeId;
     private String inventoryCountTypeCode;
     private String inventoryCountTypeDesc;
     private boolean activeIndicator;
     private Date createdDate;
     private Date lastUpdated;
     private Integer createdBy;
     private Integer updatedBy;
     private Set<InventoryCount> inventoryCounts = new HashSet<InventoryCount>(0);

    public InventoryCountType() {
    }

	
    public InventoryCountType(String inventoryCountTypeCode, String inventoryCountTypeDesc, boolean activeIndicator, Date createdDate, Date lastUpdated) {
        this.inventoryCountTypeCode = inventoryCountTypeCode;
        this.inventoryCountTypeDesc = inventoryCountTypeDesc;
        this.activeIndicator = activeIndicator;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
    }
    public InventoryCountType(String inventoryCountTypeCode, String inventoryCountTypeDesc, boolean activeIndicator, Date createdDate, Date lastUpdated, Integer createdBy, Integer updatedBy, Set<InventoryCount> inventoryCounts) {
       this.inventoryCountTypeCode = inventoryCountTypeCode;
       this.inventoryCountTypeDesc = inventoryCountTypeDesc;
       this.activeIndicator = activeIndicator;
       this.createdDate = createdDate;
       this.lastUpdated = lastUpdated;
       this.createdBy = createdBy;
       this.updatedBy = updatedBy;
       this.inventoryCounts = inventoryCounts;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="INVENTORY_COUNT_TYPE_ID", unique=true, nullable=false)
    public Integer getInventoryCountTypeId() {
        return this.inventoryCountTypeId;
    }
    
    public void setInventoryCountTypeId(Integer inventoryCountTypeId) {
        this.inventoryCountTypeId = inventoryCountTypeId;
    }

    
    @Column(name="INVENTORY_COUNT_TYPE_CODE", unique=true, nullable=false, length=5)
    public String getInventoryCountTypeCode() {
        return this.inventoryCountTypeCode;
    }
    
    public void setInventoryCountTypeCode(String inventoryCountTypeCode) {
        this.inventoryCountTypeCode = inventoryCountTypeCode;
    }

    
    @Column(name="INVENTORY_COUNT_TYPE_DESC", nullable=false, length=45)
    public String getInventoryCountTypeDesc() {
        return this.inventoryCountTypeDesc;
    }
    
    public void setInventoryCountTypeDesc(String inventoryCountTypeDesc) {
        this.inventoryCountTypeDesc = inventoryCountTypeDesc;
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

@OneToMany(fetch=FetchType.LAZY, mappedBy="inventoryCountType")
    public Set<InventoryCount> getInventoryCounts() {
        return this.inventoryCounts;
    }
    
    public void setInventoryCounts(Set<InventoryCount> inventoryCounts) {
        this.inventoryCounts = inventoryCounts;
    }




}


