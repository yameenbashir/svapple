package com.dowhile;
// Generated Aug 17, 2017 1:48:25 PM by Hibernate Tools 3.4.0.CR1


import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * CompositeProduct generated by hbm2java
 */
@Entity
@Table(name="composite_product"
    ,catalog="ecom"
)
public class CompositeProduct  implements java.io.Serializable {


    private Integer compositeProductId;
    private ProductVariant productVariant;
    private Outlet outlet;
    private User userByCreatedBy;
    private User userByUpdatedBy;
    private Product productByProductAssocicationId;
    private Company company;
    private Product productBySelectiveProductAssociationId;
    private String compositeProductUuid;
    private String productUuid;
    private int unitQuantity;
    private int compositeQuantity;
    private boolean activeIndicator;
    private Date createdDate;
    private Date lastUpdated;
    private Set<ProductHistory> productHistories = new HashSet<ProductHistory>(0);

   public CompositeProduct() {
   }

	
   public CompositeProduct(Outlet outlet, User userByCreatedBy, User userByUpdatedBy, Product productByProductAssocicationId, Company company, Product productBySelectiveProductAssociationId, String compositeProductUuid, String productUuid, int unitQuantity, int compositeQuantity, boolean activeIndicator, Date createdDate, Date lastUpdated) {
       this.outlet = outlet;
       this.userByCreatedBy = userByCreatedBy;
       this.userByUpdatedBy = userByUpdatedBy;
       this.productByProductAssocicationId = productByProductAssocicationId;
       this.company = company;
       this.productBySelectiveProductAssociationId = productBySelectiveProductAssociationId;
       this.compositeProductUuid = compositeProductUuid;
       this.productUuid = productUuid;
       this.unitQuantity = unitQuantity;
       this.compositeQuantity = compositeQuantity;
       this.activeIndicator = activeIndicator;
       this.createdDate = createdDate;
       this.lastUpdated = lastUpdated;
   }
   public CompositeProduct(ProductVariant productVariant, Outlet outlet, User userByCreatedBy, User userByUpdatedBy, Product productByProductAssocicationId, Company company, Product productBySelectiveProductAssociationId, String compositeProductUuid, String productUuid, int unitQuantity, int compositeQuantity, boolean activeIndicator, Date createdDate, Date lastUpdated, Set<ProductHistory> productHistories) {
      this.productVariant = productVariant;
      this.outlet = outlet;
      this.userByCreatedBy = userByCreatedBy;
      this.userByUpdatedBy = userByUpdatedBy;
      this.productByProductAssocicationId = productByProductAssocicationId;
      this.company = company;
      this.productBySelectiveProductAssociationId = productBySelectiveProductAssociationId;
      this.compositeProductUuid = compositeProductUuid;
      this.productUuid = productUuid;
      this.unitQuantity = unitQuantity;
      this.compositeQuantity = compositeQuantity;
      this.activeIndicator = activeIndicator;
      this.createdDate = createdDate;
      this.lastUpdated = lastUpdated;
      this.productHistories = productHistories;
   }
  
    @Id @GeneratedValue(strategy=IDENTITY)

   
   @Column(name="COMPOSITE_PRODUCT_ID", unique=true, nullable=false)
   public Integer getCompositeProductId() {
       return this.compositeProductId;
   }
   
   public void setCompositeProductId(Integer compositeProductId) {
       this.compositeProductId = compositeProductId;
   }

@ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="PRODUCT_VARIANT_ASSOCICATION_ID")
   public ProductVariant getProductVariant() {
       return this.productVariant;
   }
   
   public void setProductVariant(ProductVariant productVariant) {
       this.productVariant = productVariant;
   }

@ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="OUTLET_ASSOCICATION_ID", nullable=false)
   public Outlet getOutlet() {
       return this.outlet;
   }
   
   public void setOutlet(Outlet outlet) {
       this.outlet = outlet;
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
   @JoinColumn(name="PRODUCT_ASSOCICATION_ID", nullable=false)
   public Product getProductByProductAssocicationId() {
       return this.productByProductAssocicationId;
   }
   
   public void setProductByProductAssocicationId(Product productByProductAssocicationId) {
       this.productByProductAssocicationId = productByProductAssocicationId;
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
   @JoinColumn(name="SELECTIVE_PRODUCT_ASSOCIATION_ID", nullable=false)
   public Product getProductBySelectiveProductAssociationId() {
       return this.productBySelectiveProductAssociationId;
   }
   
   public void setProductBySelectiveProductAssociationId(Product productBySelectiveProductAssociationId) {
       this.productBySelectiveProductAssociationId = productBySelectiveProductAssociationId;
   }

   
   @Column(name="COMPOSITE_PRODUCT_UUID", nullable=false, length=500)
   public String getCompositeProductUuid() {
       return this.compositeProductUuid;
   }
   
   public void setCompositeProductUuid(String compositeProductUuid) {
       this.compositeProductUuid = compositeProductUuid;
   }

   
   @Column(name="PRODUCT_UUID", nullable=false, length=500)
   public String getProductUuid() {
       return this.productUuid;
   }
   
   public void setProductUuid(String productUuid) {
       this.productUuid = productUuid;
   }

   
   @Column(name="UNIT_QUANTITY", nullable=false)
   public int getUnitQuantity() {
       return this.unitQuantity;
   }
   
   public void setUnitQuantity(int unitQuantity) {
       this.unitQuantity = unitQuantity;
   }

   
   @Column(name="COMPOSITE_QUANTITY", nullable=false)
   public int getCompositeQuantity() {
       return this.compositeQuantity;
   }
   
   public void setCompositeQuantity(int compositeQuantity) {
       this.compositeQuantity = compositeQuantity;
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

@OneToMany(fetch=FetchType.LAZY, mappedBy="compositeProduct")
   public Set<ProductHistory> getProductHistories() {
       return this.productHistories;
   }
   
   public void setProductHistories(Set<ProductHistory> productHistories) {
       this.productHistories = productHistories;
   }




}

