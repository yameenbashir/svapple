package com.dowhile;
// Generated Aug 17, 2017 1:48:25 PM by Hibernate Tools 3.4.0.CR1


import java.math.BigDecimal;
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
 * InvoiceMain generated by hbm2java
 */
@Entity
@Table(name="invoice_main"
    ,catalog="ecom"
)
public class InvoiceMain  implements java.io.Serializable {


     private Integer invoiceMainId;
     private Contact contact;
     private Outlet outlet;
     private OrderMain orderMain;
     private PaymentType paymentType;
     private DailyRegister dailyRegister;
     private Company company;
     private Status status;
     private String invoiceRefNbr;
     private String invoiceNotes;
     private BigDecimal invoiceDiscount;
     private BigDecimal invoiceTax;
     private String invcTypeCde;
     private Date invoiceGenerationDte;
     private Date invoiceCancelDte;
     private BigDecimal invoiceAmt;
     private BigDecimal invoiceDiscountAmt;
     private BigDecimal invoiceNetAmt;
     private BigDecimal invoiceGivenAmt;
     private BigDecimal invoiceOrignalAmt;
     private BigDecimal settledAmt;
     private boolean activeIndicator;
     private Date createdDate;
     private Date lastUpdated;
     private Integer createdBy;
     private Integer updatedBy;
     private Integer salesUser;
     private Set<InvoiceDetail> invoiceDetails = new HashSet<InvoiceDetail>(0);
     private Set<Receipt> receipts = new HashSet<Receipt>(0);

    public InvoiceMain() {
    }

	
    public InvoiceMain(PaymentType paymentType, DailyRegister dailyRegister, Company company, Status status, boolean activeIndicator, Date createdDate, Date lastUpdated) {
        this.paymentType = paymentType;
        this.dailyRegister = dailyRegister;
        this.company = company;
        this.status = status;
        this.activeIndicator = activeIndicator;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
    }
    public InvoiceMain(Contact contact, Outlet outlet, OrderMain orderMain, PaymentType paymentType, DailyRegister dailyRegister, Company company, Status status, String invoiceRefNbr, String invoiceNotes, BigDecimal invoiceDiscount, BigDecimal invoiceTax, String invcTypeCde, Date invoiceGenerationDte, Date invoiceCancelDte, BigDecimal invoiceAmt, BigDecimal invoiceDiscountAmt, BigDecimal invoiceNetAmt, BigDecimal invoiceGivenAmt, BigDecimal invoiceOrignalAmt, BigDecimal settledAmt, boolean activeIndicator, Date createdDate, Date lastUpdated, Integer createdBy, Integer updatedBy, Integer salesUser, Set<InvoiceDetail> invoiceDetails, Set<Receipt> receipts) {
       this.contact = contact;
       this.outlet = outlet;
       this.orderMain = orderMain;
       this.paymentType = paymentType;
       this.dailyRegister = dailyRegister;
       this.company = company;
       this.status = status;
       this.invoiceRefNbr = invoiceRefNbr;
       this.invoiceNotes = invoiceNotes;
       this.invoiceDiscount = invoiceDiscount;
       this.invoiceTax = invoiceTax;
       this.invcTypeCde = invcTypeCde;
       this.invoiceGenerationDte = invoiceGenerationDte;
       this.invoiceCancelDte = invoiceCancelDte;
       this.invoiceAmt = invoiceAmt;
       this.invoiceDiscountAmt = invoiceDiscountAmt;
       this.invoiceNetAmt = invoiceNetAmt;
       this.invoiceGivenAmt = invoiceGivenAmt;
       this.invoiceOrignalAmt = invoiceOrignalAmt;
       this.settledAmt = settledAmt;
       this.activeIndicator = activeIndicator;
       this.createdDate = createdDate;
       this.lastUpdated = lastUpdated;
       this.createdBy = createdBy;
       this.updatedBy = updatedBy;
       this.salesUser = salesUser;
       this.invoiceDetails = invoiceDetails;
       this.receipts = receipts;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="INVOICE_MAIN_ID", unique=true, nullable=false)
    public Integer getInvoiceMainId() {
        return this.invoiceMainId;
    }
    
    public void setInvoiceMainId(Integer invoiceMainId) {
        this.invoiceMainId = invoiceMainId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CONTACT_ASSOCIATION_ID")
    public Contact getContact() {
        return this.contact;
    }
    
    public void setContact(Contact contact) {
        this.contact = contact;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="OUTLET_ASSOCICATION_ID")
    public Outlet getOutlet() {
        return this.outlet;
    }
    
    public void setOutlet(Outlet outlet) {
        this.outlet = outlet;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ORDER_ASSOCICATION_ID")
    public OrderMain getOrderMain() {
        return this.orderMain;
    }
    
    public void setOrderMain(OrderMain orderMain) {
        this.orderMain = orderMain;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PAYMENT_TYPE_ASSOCICATION_ID", nullable=false)
    public PaymentType getPaymentType() {
        return this.paymentType;
    }
    
    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="DAILY_REGISTER_ASSOCICATION_ID", nullable=false)
    public DailyRegister getDailyRegister() {
        return this.dailyRegister;
    }
    
    public void setDailyRegister(DailyRegister dailyRegister) {
        this.dailyRegister = dailyRegister;
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
    @JoinColumn(name="STATUS_ASSOCICATION_ID", nullable=false)
    public Status getStatus() {
        return this.status;
    }
    
    public void setStatus(Status status) {
        this.status = status;
    }

    
    @Column(name="INVOICE_REF_NBR", length=100)
    public String getInvoiceRefNbr() {
        return this.invoiceRefNbr;
    }
    
    public void setInvoiceRefNbr(String invoiceRefNbr) {
        this.invoiceRefNbr = invoiceRefNbr;
    }

    
    @Column(name="INVOICE_NOTES", length=256)
    public String getInvoiceNotes() {
        return this.invoiceNotes;
    }
    
    public void setInvoiceNotes(String invoiceNotes) {
        this.invoiceNotes = invoiceNotes;
    }

    
    @Column(name="INVOICE_DISCOUNT", precision=20)
    public BigDecimal getInvoiceDiscount() {
        return this.invoiceDiscount;
    }
    
    public void setInvoiceDiscount(BigDecimal invoiceDiscount) {
        this.invoiceDiscount = invoiceDiscount;
    }

    
    @Column(name="INVOICE_TAX", precision=20)
    public BigDecimal getInvoiceTax() {
        return this.invoiceTax;
    }
    
    public void setInvoiceTax(BigDecimal invoiceTax) {
        this.invoiceTax = invoiceTax;
    }

    
    @Column(name="INVC_TYPE_CDE", length=5)
    public String getInvcTypeCde() {
        return this.invcTypeCde;
    }
    
    public void setInvcTypeCde(String invcTypeCde) {
        this.invcTypeCde = invcTypeCde;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="INVOICE_GENERATION_DTE", length=19)
    public Date getInvoiceGenerationDte() {
        return this.invoiceGenerationDte;
    }
    
    public void setInvoiceGenerationDte(Date invoiceGenerationDte) {
        this.invoiceGenerationDte = invoiceGenerationDte;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="INVOICE_CANCEL_DTE", length=19)
    public Date getInvoiceCancelDte() {
        return this.invoiceCancelDte;
    }
    
    public void setInvoiceCancelDte(Date invoiceCancelDte) {
        this.invoiceCancelDte = invoiceCancelDte;
    }

    
    @Column(name="INVOICE_AMT", precision=20)
    public BigDecimal getInvoiceAmt() {
        return this.invoiceAmt;
    }
    
    public void setInvoiceAmt(BigDecimal invoiceAmt) {
        this.invoiceAmt = invoiceAmt;
    }

    
    @Column(name="INVOICE_DISCOUNT_AMT", precision=20)
    public BigDecimal getInvoiceDiscountAmt() {
        return this.invoiceDiscountAmt;
    }
    
    public void setInvoiceDiscountAmt(BigDecimal invoiceDiscountAmt) {
        this.invoiceDiscountAmt = invoiceDiscountAmt;
    }

    
    @Column(name="INVOICE_NET_AMT", precision=20)
    public BigDecimal getInvoiceNetAmt() {
        return this.invoiceNetAmt;
    }
    
    public void setInvoiceNetAmt(BigDecimal invoiceNetAmt) {
        this.invoiceNetAmt = invoiceNetAmt;
    }

    
    @Column(name="INVOICE_GIVEN_AMT", precision=20)
    public BigDecimal getInvoiceGivenAmt() {
        return this.invoiceGivenAmt;
    }
    
    public void setInvoiceGivenAmt(BigDecimal invoiceGivenAmt) {
        this.invoiceGivenAmt = invoiceGivenAmt;
    }

    
    @Column(name="INVOICE_ORIGNAL_AMT", precision=20)
    public BigDecimal getInvoiceOrignalAmt() {
        return this.invoiceOrignalAmt;
    }
    
    public void setInvoiceOrignalAmt(BigDecimal invoiceOrignalAmt) {
        this.invoiceOrignalAmt = invoiceOrignalAmt;
    }

    
    @Column(name="SETTLED_AMT", precision=20)
    public BigDecimal getSettledAmt() {
        return this.settledAmt;
    }
    
    public void setSettledAmt(BigDecimal settledAmt) {
        this.settledAmt = settledAmt;
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

    
    @Column(name="SALES_USER")
    public Integer getSalesUser() {
        return this.salesUser;
    }
    
    public void setSalesUser(Integer salesUser) {
        this.salesUser = salesUser;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="invoiceMain")
    public Set<InvoiceDetail> getInvoiceDetails() {
        return this.invoiceDetails;
    }
    
    public void setInvoiceDetails(Set<InvoiceDetail> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="invoiceMain")
    public Set<Receipt> getReceipts() {
        return this.receipts;
    }
    
    public void setReceipts(Set<Receipt> receipts) {
        this.receipts = receipts;
    }




}

