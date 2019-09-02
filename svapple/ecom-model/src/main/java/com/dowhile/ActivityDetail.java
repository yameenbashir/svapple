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
 * ActivityDetail generated by hbm2java
 */
@Entity
@Table(name="activity_detail"
    ,catalog="ecom"
)
public class ActivityDetail  implements java.io.Serializable {


     private Integer activityDetailId;
     private User userByEmployeeAssociationId;
     private Company company;
     private User userByCreatedByManagerId;
     private Severity severity;
     private String employeeName;
     private String emloyeeEmail;
     private String activityDetail;
     private Date createdDate;
     private String ipAddress;
     private String browserName;
     private String browserVersion;
     private String operatingSystem;
     private String deviceType;
     private String sessionId;
     private byte[] otherInformation;
     private String isException;

    public ActivityDetail() {
    }

    public ActivityDetail(User userByEmployeeAssociationId, Company company, User userByCreatedByManagerId, Severity severity, String employeeName, String emloyeeEmail, String activityDetail, Date createdDate, String ipAddress, String browserName, String browserVersion, String operatingSystem, String deviceType, String sessionId, byte[] otherInformation, String isException) {
       this.userByEmployeeAssociationId = userByEmployeeAssociationId;
       this.company = company;
       this.userByCreatedByManagerId = userByCreatedByManagerId;
       this.severity = severity;
       this.employeeName = employeeName;
       this.emloyeeEmail = emloyeeEmail;
       this.activityDetail = activityDetail;
       this.createdDate = createdDate;
       this.ipAddress = ipAddress;
       this.browserName = browserName;
       this.browserVersion = browserVersion;
       this.operatingSystem = operatingSystem;
       this.deviceType = deviceType;
       this.sessionId = sessionId;
       this.otherInformation = otherInformation;
       this.isException = isException;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="ACTIVITY_DETAIL_ID", unique=true, nullable=false)
    public Integer getActivityDetailId() {
        return this.activityDetailId;
    }
    
    public void setActivityDetailId(Integer activityDetailId) {
        this.activityDetailId = activityDetailId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="EMPLOYEE_ASSOCIATION_ID", nullable=false)
    public User getUserByEmployeeAssociationId() {
        return this.userByEmployeeAssociationId;
    }
    
    public void setUserByEmployeeAssociationId(User userByEmployeeAssociationId) {
        this.userByEmployeeAssociationId = userByEmployeeAssociationId;
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
    @JoinColumn(name="CREATED_BY_MANAGER_ID", nullable=false)
    public User getUserByCreatedByManagerId() {
        return this.userByCreatedByManagerId;
    }
    
    public void setUserByCreatedByManagerId(User userByCreatedByManagerId) {
        this.userByCreatedByManagerId = userByCreatedByManagerId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="SEVERITY_ASSOCIATION_ID", nullable=false)
    public Severity getSeverity() {
        return this.severity;
    }
    
    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    
    @Column(name="EMPLOYEE_NAME", nullable=false, length=256)
    public String getEmployeeName() {
        return this.employeeName;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    
    @Column(name="EMLOYEE_EMAIL", nullable=false, length=256)
    public String getEmloyeeEmail() {
        return this.emloyeeEmail;
    }
    
    public void setEmloyeeEmail(String emloyeeEmail) {
        this.emloyeeEmail = emloyeeEmail;
    }

    
    @Column(name="activity_detail", nullable=false, length=256)
    public String getActivityDetail() {
        return this.activityDetail;
    }
    
    public void setActivityDetail(String activityDetail) {
        this.activityDetail = activityDetail;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_DATE", nullable=false, length=19)
    public Date getCreatedDate() {
        return this.createdDate;
    }
    
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    
    @Column(name="IP_ADDRESS", nullable=false, length=256)
    public String getIpAddress() {
        return this.ipAddress;
    }
    
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    
    @Column(name="BROWSER_NAME", nullable=false, length=256)
    public String getBrowserName() {
        return this.browserName;
    }
    
    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    
    @Column(name="BROWSER_VERSION", nullable=false, length=256)
    public String getBrowserVersion() {
        return this.browserVersion;
    }
    
    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    
    @Column(name="OPERATING_SYSTEM", nullable=false, length=256)
    public String getOperatingSystem() {
        return this.operatingSystem;
    }
    
    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    
    @Column(name="DEVICE_TYPE", nullable=false, length=256)
    public String getDeviceType() {
        return this.deviceType;
    }
    
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    
    @Column(name="SESSION_ID", nullable=false, length=256)
    public String getSessionId() {
        return this.sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    
    @Column(name="OTHER_INFORMATION", nullable=false)
    public byte[] getOtherInformation() {
        return this.otherInformation;
    }
    
    public void setOtherInformation(byte[] otherInformation) {
        this.otherInformation = otherInformation;
    }

    
    @Column(name="IS_EXCEPTION", nullable=false, length=10)
    public String getIsException() {
        return this.isException;
    }
    
    public void setIsException(String isException) {
        this.isException = isException;
    }




}


