package com.dowhile;

// Generated Dec 17, 2017 11:56:05 AM by Hibernate Tools 4.0.0

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
 * Outlet generated by hbm2java
 */
@Entity
@Table(name="outlet"
    ,catalog="ecom"
)
public class Outlet implements java.io.Serializable {


    private Integer outletId;
    private Address address;
    private Company company;
    private TimeZone timeZone;
    private SalesTax salesTax;
    private String outletName;
    private String orderNumber;
    private String orderNumberPrefix;
    private String contactNumberPrefix;
    private String contactReturnNumber;
    private boolean activeIndicator;
    private Boolean isHeadOffice;
    private Date createdDate;
    private Date lastUpdated;
    private Integer createdBy;
    private Integer updatedBy;
    private Set<StockOrder> stockOrdersForSourceOutletAssocicationId = new HashSet<StockOrder>(0);
    private Set<CompositeProductHistory> compositeProductHistories = new HashSet<CompositeProductHistory>(0);
    private Set<Loyalty> loyalties = new HashSet<Loyalty>(0);
    private Set<Product> products = new HashSet<Product>(0);
    private Set<Message> messages = new HashSet<Message>(0);
    private Set<OrderDetail> orderDetails = new HashSet<OrderDetail>(0);
    private Set<InvoiceDetail> invoiceDetails = new HashSet<InvoiceDetail>(0);
    private Set<ProductHistory> productHistories = new HashSet<ProductHistory>(0);
    private Set<UserOutlets> userOutletses = new HashSet<UserOutlets>(0);
    private Set<CompositeProduct> compositeProducts = new HashSet<CompositeProduct>(0);
    private Set<InventoryCount> inventoryCounts = new HashSet<InventoryCount>(0);
    private Set<InvoiceMain> invoiceMains = new HashSet<InvoiceMain>(0);
    private Set<ProductPriceHistory> productPriceHistories = new HashSet<ProductPriceHistory>(0);
    private Set<ProductVariant> productVariants = new HashSet<ProductVariant>(0);
    private Set<Receipt> receipts = new HashSet<Receipt>(0);
    private Set<DailyRegister> dailyRegisters = new HashSet<DailyRegister>(0);
    private Set<Notification> notificationsForOutletIdFrom = new HashSet<Notification>(0);
    private Set<Register> registers = new HashSet<Register>(0);
    private Set<MessageDetail> messageDetails = new HashSet<MessageDetail>(0);
    private Set<User> users = new HashSet<User>(0);
    private Set<PriceBook> priceBooks = new HashSet<PriceBook>(0);
    private Set<OrderMain> orderMains = new HashSet<OrderMain>(0);
    private Set<Notification> notificationsForOutletIdTo = new HashSet<Notification>(0);
    private Set<CashManagment> cashManagments = new HashSet<CashManagment>(0);
    private Set<StockOrder> stockOrdersForOutletAssocicationId = new HashSet<StockOrder>(0);

   public Outlet() {
   }

	
   public Outlet(Company company, SalesTax salesTax, boolean activeIndicator, Date createdDate, Date lastUpdated) {
       this.company = company;
       this.salesTax = salesTax;
       this.activeIndicator = activeIndicator;
       this.createdDate = createdDate;
       this.lastUpdated = lastUpdated;
   }
   public Outlet(Address address, Company company, TimeZone timeZone, SalesTax salesTax, String outletName, String orderNumber, String orderNumberPrefix, String contactNumberPrefix, String contactReturnNumber, boolean activeIndicator, Boolean isHeadOffice, Date createdDate, Date lastUpdated, Integer createdBy, Integer updatedBy, Set<StockOrder> stockOrdersForSourceOutletAssocicationId, Set<CompositeProductHistory> compositeProductHistories, Set<Loyalty> loyalties, Set<Product> products, Set<Message> messages, Set<OrderDetail> orderDetails, Set<InvoiceDetail> invoiceDetails, Set<ProductHistory> productHistories, Set<UserOutlets> userOutletses, Set<CompositeProduct> compositeProducts, Set<InventoryCount> inventoryCounts, Set<InvoiceMain> invoiceMains, Set<ProductPriceHistory> productPriceHistories, Set<ProductVariant> productVariants, Set<Receipt> receipts, Set<DailyRegister> dailyRegisters, Set<Notification> notificationsForOutletIdFrom, Set<Register> registers, Set<MessageDetail> messageDetails, Set<User> users, Set<PriceBook> priceBooks, Set<OrderMain> orderMains, Set<Notification> notificationsForOutletIdTo, Set<CashManagment> cashManagments, Set<StockOrder> stockOrdersForOutletAssocicationId) {
      this.address = address;
      this.company = company;
      this.timeZone = timeZone;
      this.salesTax = salesTax;
      this.outletName = outletName;
      this.orderNumber = orderNumber;
      this.orderNumberPrefix = orderNumberPrefix;
      this.contactNumberPrefix = contactNumberPrefix;
      this.contactReturnNumber = contactReturnNumber;
      this.activeIndicator = activeIndicator;
      this.isHeadOffice = isHeadOffice;
      this.createdDate = createdDate;
      this.lastUpdated = lastUpdated;
      this.createdBy = createdBy;
      this.updatedBy = updatedBy;
      this.stockOrdersForSourceOutletAssocicationId = stockOrdersForSourceOutletAssocicationId;
      this.compositeProductHistories = compositeProductHistories;
      this.loyalties = loyalties;
      this.products = products;
      this.messages = messages;
      this.orderDetails = orderDetails;
      this.invoiceDetails = invoiceDetails;
      this.productHistories = productHistories;
      this.userOutletses = userOutletses;
      this.compositeProducts = compositeProducts;
      this.inventoryCounts = inventoryCounts;
      this.invoiceMains = invoiceMains;
      this.productPriceHistories = productPriceHistories;
      this.productVariants = productVariants;
      this.receipts = receipts;
      this.dailyRegisters = dailyRegisters;
      this.notificationsForOutletIdFrom = notificationsForOutletIdFrom;
      this.registers = registers;
      this.messageDetails = messageDetails;
      this.users = users;
      this.priceBooks = priceBooks;
      this.orderMains = orderMains;
      this.notificationsForOutletIdTo = notificationsForOutletIdTo;
      this.cashManagments = cashManagments;
      this.stockOrdersForOutletAssocicationId = stockOrdersForOutletAssocicationId;
   }
  
    @Id @GeneratedValue(strategy=IDENTITY)

   
   @Column(name="OUTLET_ID", unique=true, nullable=false)
   public Integer getOutletId() {
       return this.outletId;
   }
   
   public void setOutletId(Integer outletId) {
       this.outletId = outletId;
   }

@ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="ADDRESS_ASSOCICATION_ID")
   public Address getAddress() {
       return this.address;
   }
   
   public void setAddress(Address address) {
       this.address = address;
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
   @JoinColumn(name="TIME_ZONE_ASSOCICATION_ID")
   public TimeZone getTimeZone() {
       return this.timeZone;
   }
   
   public void setTimeZone(TimeZone timeZone) {
       this.timeZone = timeZone;
   }

@ManyToOne(fetch=FetchType.LAZY)
   @JoinColumn(name="SALES_TAX_ASSOCICATION_ID", nullable=false)
   public SalesTax getSalesTax() {
       return this.salesTax;
   }
   
   public void setSalesTax(SalesTax salesTax) {
       this.salesTax = salesTax;
   }

   
   @Column(name="OUTLET_NAME", length=100)
   public String getOutletName() {
       return this.outletName;
   }
   
   public void setOutletName(String outletName) {
       this.outletName = outletName;
   }

   
   @Column(name="ORDER_NUMBER", length=100)
   public String getOrderNumber() {
       return this.orderNumber;
   }
   
   public void setOrderNumber(String orderNumber) {
       this.orderNumber = orderNumber;
   }

   
   @Column(name="ORDER_NUMBER_PREFIX", length=100)
   public String getOrderNumberPrefix() {
       return this.orderNumberPrefix;
   }
   
   public void setOrderNumberPrefix(String orderNumberPrefix) {
       this.orderNumberPrefix = orderNumberPrefix;
   }

   
   @Column(name="CONTACT_NUMBER_PREFIX", length=100)
   public String getContactNumberPrefix() {
       return this.contactNumberPrefix;
   }
   
   public void setContactNumberPrefix(String contactNumberPrefix) {
       this.contactNumberPrefix = contactNumberPrefix;
   }

   
   @Column(name="CONTACT_RETURN_NUMBER", length=100)
   public String getContactReturnNumber() {
       return this.contactReturnNumber;
   }
   
   public void setContactReturnNumber(String contactReturnNumber) {
       this.contactReturnNumber = contactReturnNumber;
   }

   
   @Column(name="ACTIVE_INDICATOR", nullable=false)
   public boolean isActiveIndicator() {
       return this.activeIndicator;
   }
   
   public void setActiveIndicator(boolean activeIndicator) {
       this.activeIndicator = activeIndicator;
   }

   
   @Column(name="IS_HEAD_OFFICE")
   public Boolean getIsHeadOffice() {
       return this.isHeadOffice;
   }
   
   public void setIsHeadOffice(Boolean isHeadOffice) {
       this.isHeadOffice = isHeadOffice;
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

@OneToMany(fetch=FetchType.LAZY, mappedBy="outletBySourceOutletAssocicationId")
   public Set<StockOrder> getStockOrdersForSourceOutletAssocicationId() {
       return this.stockOrdersForSourceOutletAssocicationId;
   }
   
   public void setStockOrdersForSourceOutletAssocicationId(Set<StockOrder> stockOrdersForSourceOutletAssocicationId) {
       this.stockOrdersForSourceOutletAssocicationId = stockOrdersForSourceOutletAssocicationId;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<CompositeProductHistory> getCompositeProductHistories() {
       return this.compositeProductHistories;
   }
   
   public void setCompositeProductHistories(Set<CompositeProductHistory> compositeProductHistories) {
       this.compositeProductHistories = compositeProductHistories;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<Loyalty> getLoyalties() {
       return this.loyalties;
   }
   
   public void setLoyalties(Set<Loyalty> loyalties) {
       this.loyalties = loyalties;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<Product> getProducts() {
       return this.products;
   }
   
   public void setProducts(Set<Product> products) {
       this.products = products;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<Message> getMessages() {
       return this.messages;
   }
   
   public void setMessages(Set<Message> messages) {
       this.messages = messages;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<OrderDetail> getOrderDetails() {
       return this.orderDetails;
   }
   
   public void setOrderDetails(Set<OrderDetail> orderDetails) {
       this.orderDetails = orderDetails;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<InvoiceDetail> getInvoiceDetails() {
       return this.invoiceDetails;
   }
   
   public void setInvoiceDetails(Set<InvoiceDetail> invoiceDetails) {
       this.invoiceDetails = invoiceDetails;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<ProductHistory> getProductHistories() {
       return this.productHistories;
   }
   
   public void setProductHistories(Set<ProductHistory> productHistories) {
       this.productHistories = productHistories;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<UserOutlets> getUserOutletses() {
       return this.userOutletses;
   }
   
   public void setUserOutletses(Set<UserOutlets> userOutletses) {
       this.userOutletses = userOutletses;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<CompositeProduct> getCompositeProducts() {
       return this.compositeProducts;
   }
   
   public void setCompositeProducts(Set<CompositeProduct> compositeProducts) {
       this.compositeProducts = compositeProducts;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<InventoryCount> getInventoryCounts() {
       return this.inventoryCounts;
   }
   
   public void setInventoryCounts(Set<InventoryCount> inventoryCounts) {
       this.inventoryCounts = inventoryCounts;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<InvoiceMain> getInvoiceMains() {
       return this.invoiceMains;
   }
   
   public void setInvoiceMains(Set<InvoiceMain> invoiceMains) {
       this.invoiceMains = invoiceMains;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<ProductPriceHistory> getProductPriceHistories() {
       return this.productPriceHistories;
   }
   
   public void setProductPriceHistories(Set<ProductPriceHistory> productPriceHistories) {
       this.productPriceHistories = productPriceHistories;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<ProductVariant> getProductVariants() {
       return this.productVariants;
   }
   
   public void setProductVariants(Set<ProductVariant> productVariants) {
       this.productVariants = productVariants;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<Receipt> getReceipts() {
       return this.receipts;
   }
   
   public void setReceipts(Set<Receipt> receipts) {
       this.receipts = receipts;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<DailyRegister> getDailyRegisters() {
       return this.dailyRegisters;
   }
   
   public void setDailyRegisters(Set<DailyRegister> dailyRegisters) {
       this.dailyRegisters = dailyRegisters;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outletByOutletIdFrom")
   public Set<Notification> getNotificationsForOutletIdFrom() {
       return this.notificationsForOutletIdFrom;
   }
   
   public void setNotificationsForOutletIdFrom(Set<Notification> notificationsForOutletIdFrom) {
       this.notificationsForOutletIdFrom = notificationsForOutletIdFrom;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<Register> getRegisters() {
       return this.registers;
   }
   
   public void setRegisters(Set<Register> registers) {
       this.registers = registers;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<MessageDetail> getMessageDetails() {
       return this.messageDetails;
   }
   
   public void setMessageDetails(Set<MessageDetail> messageDetails) {
       this.messageDetails = messageDetails;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<User> getUsers() {
       return this.users;
   }
   
   public void setUsers(Set<User> users) {
       this.users = users;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<PriceBook> getPriceBooks() {
       return this.priceBooks;
   }
   
   public void setPriceBooks(Set<PriceBook> priceBooks) {
       this.priceBooks = priceBooks;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<OrderMain> getOrderMains() {
       return this.orderMains;
   }
   
   public void setOrderMains(Set<OrderMain> orderMains) {
       this.orderMains = orderMains;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outletByOutletIdTo")
   public Set<Notification> getNotificationsForOutletIdTo() {
       return this.notificationsForOutletIdTo;
   }
   
   public void setNotificationsForOutletIdTo(Set<Notification> notificationsForOutletIdTo) {
       this.notificationsForOutletIdTo = notificationsForOutletIdTo;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outlet")
   public Set<CashManagment> getCashManagments() {
       return this.cashManagments;
   }
   
   public void setCashManagments(Set<CashManagment> cashManagments) {
       this.cashManagments = cashManagments;
   }

@OneToMany(fetch=FetchType.LAZY, mappedBy="outletByOutletAssocicationId")
   public Set<StockOrder> getStockOrdersForOutletAssocicationId() {
       return this.stockOrdersForOutletAssocicationId;
   }
   
   public void setStockOrdersForOutletAssocicationId(Set<StockOrder> stockOrdersForOutletAssocicationId) {
       this.stockOrdersForOutletAssocicationId = stockOrdersForOutletAssocicationId;
   }




}
