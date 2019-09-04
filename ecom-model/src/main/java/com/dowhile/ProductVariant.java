package com.dowhile;
// Generated Aug 17, 2017 1:48:25 PM by Hibernate Tools 3.4.0.CR1


import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
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
 * ProductVariant generated by hbm2java
 */
@Entity
@Table(name="product_variant"
    ,catalog="ecom"
)
public class ProductVariant  implements java.io.Serializable {


    private Integer productVariantId;
    private Outlet outlet;
    private VariantAttribute variantAttributeByVariantAttributeAssocicationId1;
    private User userByCreatedBy;
    private User userByUpdatedBy;
    private Product product;
    private Company company;
    private VariantAttribute variantAttributeByVariantAttributeAssocicationId3;
    private VariantAttribute variantAttributeByVariantAttributeAssocicationId2;
    private SalesTax salesTax;
    private String variantAttributeName;
    private String productVariantUuid;
    private String productUuid;
    private String variantAttributeValue1;
    private String variantAttributeValue2;
    private String variantAttributeValue3;
    private Integer currentInventory;
    private Integer reorderPoint;
    private BigDecimal reorderAmount;
    private BigDecimal supplyPriceExclTax;
    private BigDecimal markupPrct;
    private String sku;
    private boolean activeIndicator;
    private Date createdDate;
    private Date lastUpdated;
    private int productVariantAssociationId;
    private Set<InventoryCountDetail> inventoryCountDetails = new HashSet<InventoryCountDetail>(0);
    private Set<PriceBookDetail> priceBookDetails = new HashSet<PriceBookDetail>(0);
    private Set<InvoiceDetail> invoiceDetails = new HashSet<InvoiceDetail>(0);
    private Set<CompositeProduct> compositeProducts = new HashSet<CompositeProduct>(0);
    private Set<ProductHistory> productHistories = new HashSet<ProductHistory>(0);
    private Set<VariantAttributeValues> variantAttributeValueses = new HashSet<VariantAttributeValues>(0);
    private Set<CompositeProductHistory> compositeProductHistories = new HashSet<CompositeProductHistory>(0);
    private Set<OrderDetail> orderDetails = new HashSet<OrderDetail>(0);
    private Set<StockOrderDetail> stockOrderDetails = new HashSet<StockOrderDetail>(0);

   public ProductVariant() {
   }

	
   public ProductVariant(Outlet outlet, User userByCreatedBy, User userByUpdatedBy, Product product, Company company, SalesTax salesTax, String productVariantUuid, String productUuid, BigDecimal supplyPriceExclTax, BigDecimal markupPrct, String sku, boolean activeIndicator, Date createdDate, Date lastUpdated, int productVariantAssociationId) {
       this.outlet = outlet;
       this.userByCreatedBy = userByCreatedBy;
       this.userByUpdatedBy = userByUpdatedBy;
       this.product = product;
       this.company = company;
       this.salesTax = salesTax;
       this.productVariantUuid = productVariantUuid;
       this.productUuid = productUuid;
       this.supplyPriceExclTax = supplyPriceExclTax;
       this.markupPrct = markupPrct;
       this.sku = sku;
       this.activeIndicator = activeIndicator;
       this.createdDate = createdDate;
       this.lastUpdated = lastUpdated;
       this.productVariantAssociationId = productVariantAssociationId;
   }
   public ProductVariant(Outlet outlet, VariantAttribute variantAttributeByVariantAttributeAssocicationId1, User userByCreatedBy, User userByUpdatedBy, Product product, Company company, VariantAttribute variantAttributeByVariantAttributeAssocicationId3, VariantAttribute variantAttributeByVariantAttributeAssocicationId2, SalesTax salesTax, String variantAttributeName, String productVariantUuid, String productUuid, String variantAttributeValue1, String variantAttributeValue2, String variantAttributeValue3, Integer currentInventory, Integer reorderPoint, BigDecimal reorderAmount, BigDecimal supplyPriceExclTax, BigDecimal markupPrct, String sku, boolean activeIndicator, Date createdDate, Date lastUpdated, int productVariantAssociationId, Set<InventoryCountDetail> inventoryCountDetails, Set<PriceBookDetail> priceBookDetails, Set<InvoiceDetail> invoiceDetails, Set<CompositeProduct> compositeProducts, Set<ProductHistory> productHistories, Set<VariantAttributeValues> variantAttributeValueses, Set<CompositeProductHistory> compositeProductHistories, Set<OrderDetail> orderDetails, Set<StockOrderDetail> stockOrderDetails) {
      this.outlet = outlet;
      this.variantAttributeByVariantAttributeAssocicationId1 = variantAttributeByVariantAttributeAssocicationId1;
      this.userByCreatedBy = userByCreatedBy;
      this.userByUpdatedBy = userByUpdatedBy;
      this.product = product;
      this.company = company;
      this.variantAttributeByVariantAttributeAssocicationId3 = variantAttributeByVariantAttributeAssocicationId3;
      this.variantAttributeByVariantAttributeAssocicationId2 = variantAttributeByVariantAttributeAssocicationId2;
      this.salesTax = salesTax;
      this.variantAttributeName = variantAttributeName;
      this.productVariantUuid = productVariantUuid;
      this.productUuid = productUuid;
      this.variantAttributeValue1 = variantAttributeValue1;
      this.variantAttributeValue2 = variantAttributeValue2;
      this.variantAttributeValue3 = variantAttributeValue3;
      this.currentInventory = currentInventory;
      this.reorderPoint = reorderPoint;
      this.reorderAmount = reorderAmount;
      this.supplyPriceExclTax = supplyPriceExclTax;
      this.markupPrct = markupPrct;
      this.sku = sku;
      this.activeIndicator = activeIndicator;
      this.createdDate = createdDate;
      this.lastUpdated = lastUpdated;
      this.productVariantAssociationId = productVariantAssociationId;
      this.inventoryCountDetails = inventoryCountDetails;
      this.priceBookDetails = priceBookDetails;
      this.invoiceDetails = invoiceDetails;
      this.compositeProducts = compositeProducts;
      this.productHistories = productHistories;
      this.variantAttributeValueses = variantAttributeValueses;
      this.compositeProductHistories = compositeProductHistories;
      this.orderDetails = orderDetails;
      this.stockOrderDetails = stockOrderDetails;
   }
  
    @Id @GeneratedValue(strategy=IDENTITY)

   
   @Column(name="PRODUCT_VARIANT_ID", unique=true, nullable=false)
   public Integer getProductVariantId() {
       return this.productVariantId;
   }
   
   public void setProductVariantId(Integer productVariantId) {
       this.productVariantId = productVariantId;
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
   @JoinColumn(name="VARIANT_ATTRIBUTE_ASSOCICATION_ID_1")
   public VariantAttribute getVariantAttributeByVariantAttributeAssocicationId1() {
       return this.variantAttributeByVariantAttributeAssocicationId1;
   }
   
   public void setVariantAttributeByVariantAttributeAssocicationId1(VariantAttribute variantAttributeByVariantAttributeAssocicationId1) {
       this.variantAttributeByVariantAttributeAssocicationId1 = variantAttributeByVariantAttributeAssocicationId1;
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
   public Product getProduct() {
       return this.product;
   }
   
   public void setProduct(Product product) {
       this.product = product;
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
   @JoinColumn(name="VARIANT_ATTRIBUTE_ASSOCICATION_ID_3")
   public VariantAttribute getVariantAttributeByVariantAttributeAssocicationId3() {
       return this.variantAttributeByVariantAttributeAssocicationId3;
   }
   
   public void setVariantAttributeByVariantAttributeAssocicationId3(VariantAttribute variantAttributeByVariantAttributeAssocicationId3) {
       this.variantAttributeByVariantAttributeAssocicationId3 = variantAttributeByVariantAttributeAssocicationId3;
   }

@ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="VARIANT_ATTRIBUTE_ASSOCICATION_ID_2")
   public VariantAttribute getVariantAttributeByVariantAttributeAssocicationId2() {
       return this.variantAttributeByVariantAttributeAssocicationId2;
   }
   
   public void setVariantAttributeByVariantAttributeAssocicationId2(VariantAttribute variantAttributeByVariantAttributeAssocicationId2) {
       this.variantAttributeByVariantAttributeAssocicationId2 = variantAttributeByVariantAttributeAssocicationId2;
   }

@ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="SALES_TAX_ASSOCICATION_ID", nullable=false)
   public SalesTax getSalesTax() {
       return this.salesTax;
   }
   
   public void setSalesTax(SalesTax salesTax) {
       this.salesTax = salesTax;
   }

   
   @Column(name="VARIANT_ATTRIBUTE_NAME", length=200)
   public String getVariantAttributeName() {
       return this.variantAttributeName;
   }
   
   public void setVariantAttributeName(String variantAttributeName) {
       this.variantAttributeName = variantAttributeName;
   }

   
   @Column(name="PRODUCT_VARIANT_UUID", nullable=false, length=500)
   public String getProductVariantUuid() {
       return this.productVariantUuid;
   }
   
   public void setProductVariantUuid(String productVariantUuid) {
       this.productVariantUuid = productVariantUuid;
   }

   
   @Column(name="PRODUCT_UUID", nullable=false, length=500)
   public String getProductUuid() {
       return this.productUuid;
   }
   
   public void setProductUuid(String productUuid) {
       this.productUuid = productUuid;
   }

   
   @Column(name="VARIANT_ATTRIBUTE_VALUE_1", length=200)
   public String getVariantAttributeValue1() {
       return this.variantAttributeValue1;
   }
   
   public void setVariantAttributeValue1(String variantAttributeValue1) {
       this.variantAttributeValue1 = variantAttributeValue1;
   }

   
   @Column(name="VARIANT_ATTRIBUTE_VALUE_2", length=200)
   public String getVariantAttributeValue2() {
       return this.variantAttributeValue2;
   }
   
   public void setVariantAttributeValue2(String variantAttributeValue2) {
       this.variantAttributeValue2 = variantAttributeValue2;
   }

   
   @Column(name="VARIANT_ATTRIBUTE_VALUE_3", length=200)
   public String getVariantAttributeValue3() {
       return this.variantAttributeValue3;
   }
   
   public void setVariantAttributeValue3(String variantAttributeValue3) {
       this.variantAttributeValue3 = variantAttributeValue3;
   }

   
   @Column(name="CURRENT_INVENTORY")
   public Integer getCurrentInventory() {
       return this.currentInventory;
   }
   
   public void setCurrentInventory(Integer currentInventory) {
       this.currentInventory = currentInventory;
   }

   
   @Column(name="REORDER_POINT")
   public Integer getReorderPoint() {
       return this.reorderPoint;
   }
   
   public void setReorderPoint(Integer reorderPoint) {
       this.reorderPoint = reorderPoint;
   }

   
   @Column(name="REORDER_AMOUNT", precision=20)
   public BigDecimal getReorderAmount() {
       return this.reorderAmount;
   }
   
   public void setReorderAmount(BigDecimal reorderAmount) {
       this.reorderAmount = reorderAmount;
   }

   
   @Column(name="SUPPLY_PRICE_EXCL_TAX", nullable=false, precision=20)
   public BigDecimal getSupplyPriceExclTax() {
       return this.supplyPriceExclTax;
   }
   
   public void setSupplyPriceExclTax(BigDecimal supplyPriceExclTax) {
       this.supplyPriceExclTax = supplyPriceExclTax;
   }

   
   @Column(name="MARKUP_PRCT", nullable=false, precision=12, scale=5)
   public BigDecimal getMarkupPrct() {
       return this.markupPrct;
   }
   
   public void setMarkupPrct(BigDecimal markupPrct) {
       this.markupPrct = markupPrct;
   }

   
   @Column(name="SKU", nullable=false, length=500)
   public String getSku() {
       return this.sku;
   }
   
   public void setSku(String sku) {
       this.sku = sku;
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

   
   @Column(name="PRODUCT_VARIANT_ASSOCIATION_ID", nullable=false)
   public int getProductVariantAssociationId() {
       return this.productVariantAssociationId;
   }
   
   public void setProductVariantAssociationId(int productVariantAssociationId) {
       this.productVariantAssociationId = productVariantAssociationId;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="productVariant")
   public Set<InventoryCountDetail> getInventoryCountDetails() {
       return this.inventoryCountDetails;
   }
   
   public void setInventoryCountDetails(Set<InventoryCountDetail> inventoryCountDetails) {
       this.inventoryCountDetails = inventoryCountDetails;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="productVariant")
   public Set<PriceBookDetail> getPriceBookDetails() {
       return this.priceBookDetails;
   }
   
   public void setPriceBookDetails(Set<PriceBookDetail> priceBookDetails) {
       this.priceBookDetails = priceBookDetails;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="productVariant")
   public Set<InvoiceDetail> getInvoiceDetails() {
       return this.invoiceDetails;
   }
   
   public void setInvoiceDetails(Set<InvoiceDetail> invoiceDetails) {
       this.invoiceDetails = invoiceDetails;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="productVariant")
   public Set<CompositeProduct> getCompositeProducts() {
       return this.compositeProducts;
   }
   
   public void setCompositeProducts(Set<CompositeProduct> compositeProducts) {
       this.compositeProducts = compositeProducts;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="productVariant")
   public Set<ProductHistory> getProductHistories() {
       return this.productHistories;
   }
   
   public void setProductHistories(Set<ProductHistory> productHistories) {
       this.productHistories = productHistories;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="productVariant")
   public Set<VariantAttributeValues> getVariantAttributeValueses() {
       return this.variantAttributeValueses;
   }
   
   public void setVariantAttributeValueses(Set<VariantAttributeValues> variantAttributeValueses) {
       this.variantAttributeValueses = variantAttributeValueses;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="productVariant")
   public Set<CompositeProductHistory> getCompositeProductHistories() {
       return this.compositeProductHistories;
   }
   
   public void setCompositeProductHistories(Set<CompositeProductHistory> compositeProductHistories) {
       this.compositeProductHistories = compositeProductHistories;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="productVariant")
   public Set<OrderDetail> getOrderDetails() {
       return this.orderDetails;
   }
   
   public void setOrderDetails(Set<OrderDetail> orderDetails) {
       this.orderDetails = orderDetails;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="productVariant")
   public Set<StockOrderDetail> getStockOrderDetails() {
       return this.stockOrderDetails;
   }
   
   public void setStockOrderDetails(Set<StockOrderDetail> stockOrderDetails) {
       this.stockOrderDetails = stockOrderDetails;
   }




}


