package com.dowhile;
// Generated Nov 26, 2017 3:16:44 PM by Hibernate Tools 3.4.0.CR1


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
 * Message generated by hbm2java
 */
@Entity
@Table(name="message"
    ,catalog="ecom"
)
public class Message  implements java.io.Serializable {


     private Integer messageId;
     private Company company;
     private Outlet outlet;
     private int messageBundleCount;
     private Date packageStartDate;
     private Date packageEndDate;
     private Date packageRenewDate;
     private int messageTextLimit;
     private String description;
     private String venderName;
     private String maskName;
     private String userId;
     private String password;
     private String ntnNumber;
     private String ownerName;
     private boolean activeIndicator;
     private Date createdDate;
     private Date lastUpdated;
     private Integer createdBy;
     private Integer updatedBy;
     private Set<MessageDetail> messageDetails = new HashSet<MessageDetail>(0);

    public Message() {
    }

	
    public Message(Company company, int messageBundleCount, int messageTextLimit, boolean activeIndicator, Date createdDate, Date lastUpdated) {
        this.company = company;
        this.messageBundleCount = messageBundleCount;
        this.messageTextLimit = messageTextLimit;
        this.activeIndicator = activeIndicator;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
    }
    public Message(Company company, Outlet outlet, int messageBundleCount, Date packageStartDate, Date packageEndDate, Date packageRenewDate, int messageTextLimit, String description, String venderName, String maskName, String userId, String password, String ntnNumber, String ownerName, boolean activeIndicator, Date createdDate, Date lastUpdated, Integer createdBy, Integer updatedBy, Set<MessageDetail> messageDetails) {
       this.company = company;
       this.outlet = outlet;
       this.messageBundleCount = messageBundleCount;
       this.packageStartDate = packageStartDate;
       this.packageEndDate = packageEndDate;
       this.packageRenewDate = packageRenewDate;
       this.messageTextLimit = messageTextLimit;
       this.description = description;
       this.venderName = venderName;
       this.maskName = maskName;
       this.userId = userId;
       this.password = password;
       this.ntnNumber = ntnNumber;
       this.ownerName = ownerName;
       this.activeIndicator = activeIndicator;
       this.createdDate = createdDate;
       this.lastUpdated = lastUpdated;
       this.createdBy = createdBy;
       this.updatedBy = updatedBy;
       this.messageDetails = messageDetails;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="MESSAGE_ID", unique=true, nullable=false)
    public Integer getMessageId() {
        return this.messageId;
    }
    
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
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
    @JoinColumn(name="OUTLET_ASSOCICATION_ID")
    public Outlet getOutlet() {
        return this.outlet;
    }
    
    public void setOutlet(Outlet outlet) {
        this.outlet = outlet;
    }

    
    @Column(name="MESSAGE_BUNDLE_COUNT", nullable=false)
    public int getMessageBundleCount() {
        return this.messageBundleCount;
    }
    
    public void setMessageBundleCount(int messageBundleCount) {
        this.messageBundleCount = messageBundleCount;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="PACKAGE_START_DATE", length=10)
    public Date getPackageStartDate() {
        return this.packageStartDate;
    }
    
    public void setPackageStartDate(Date packageStartDate) {
        this.packageStartDate = packageStartDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="PACKAGE_END_DATE", length=10)
    public Date getPackageEndDate() {
        return this.packageEndDate;
    }
    
    public void setPackageEndDate(Date packageEndDate) {
        this.packageEndDate = packageEndDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name="PACKAGE_RENEW_DATE", length=10)
    public Date getPackageRenewDate() {
        return this.packageRenewDate;
    }
    
    public void setPackageRenewDate(Date packageRenewDate) {
        this.packageRenewDate = packageRenewDate;
    }

    
    @Column(name="MESSAGE_TEXT_LIMIT", nullable=false)
    public int getMessageTextLimit() {
        return this.messageTextLimit;
    }
    
    public void setMessageTextLimit(int messageTextLimit) {
        this.messageTextLimit = messageTextLimit;
    }

    
    @Column(name="DESCRIPTION", length=256)
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    
    @Column(name="VENDER_NAME", length=256)
    public String getVenderName() {
        return this.venderName;
    }
    
    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }

    
    @Column(name="MASK_NAME", length=256)
    public String getMaskName() {
        return this.maskName;
    }
    
    public void setMaskName(String maskName) {
        this.maskName = maskName;
    }

    
    @Column(name="USER_ID", length=256)
    public String getUserId() {
        return this.userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }

    
    @Column(name="PASSWORD", length=256)
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    
    @Column(name="NTN_NUMBER", length=256)
    public String getNtnNumber() {
        return this.ntnNumber;
    }
    
    public void setNtnNumber(String ntnNumber) {
        this.ntnNumber = ntnNumber;
    }

    
    @Column(name="OWNER_NAME", length=256)
    public String getOwnerName() {
        return this.ownerName;
    }
    
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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

@OneToMany(fetch=FetchType.LAZY, mappedBy="message")
    public Set<MessageDetail> getMessageDetails() {
        return this.messageDetails;
    }
    
    public void setMessageDetails(Set<MessageDetail> messageDetails) {
        this.messageDetails = messageDetails;
    }




}

