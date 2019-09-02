/**
 * 
 */
package com.dowhile.frontend.mapping.bean;


/**
 * @author Hafiz Bashir
 *
 */
public class NotificationBean {
	
	private Integer notificationId;
    private int outletByOutletIdTo;
    private String outletByOutletIdToName;
    private int companyId;
    private int outletByOutletIdFrom;
    private String outletByOutletIdFromName;
    private String subject;
    private String description;
    private boolean markAsRead;
    private boolean activeIndicator;
    private String createdDate;
    private String lastUpdated;
    private Integer createdBy;
    private Integer updatedBy;
	/**
	 * 
	 */
	public NotificationBean() {
	}
	/**
	 * @param notificationId
	 * @param outletByOutletIdTo
	 * @param outletByOutletIdToName
	 * @param companyId
	 * @param outletByOutletIdFrom
	 * @param outletByOutletIdFromName
	 * @param subject
	 * @param description
	 * @param markAsRead
	 * @param activeIndicator
	 * @param createdDate
	 * @param lastUpdated
	 * @param createdBy
	 * @param updatedBy
	 */
	public NotificationBean(Integer notificationId, int outletByOutletIdTo,
			String outletByOutletIdToName, int companyId,
			int outletByOutletIdFrom, String outletByOutletIdFromName,
			String subject, String description, boolean markAsRead,
			boolean activeIndicator, String createdDate, String lastUpdated,
			Integer createdBy, Integer updatedBy) {
		this.notificationId = notificationId;
		this.outletByOutletIdTo = outletByOutletIdTo;
		this.outletByOutletIdToName = outletByOutletIdToName;
		this.companyId = companyId;
		this.outletByOutletIdFrom = outletByOutletIdFrom;
		this.outletByOutletIdFromName = outletByOutletIdFromName;
		this.subject = subject;
		this.description = description;
		this.markAsRead = markAsRead;
		this.activeIndicator = activeIndicator;
		this.createdDate = createdDate;
		this.lastUpdated = lastUpdated;
		this.createdBy = createdBy;
		this.updatedBy = updatedBy;
	}
	/**
	 * @return the notificationId
	 */
	public Integer getNotificationId() {
		return notificationId;
	}
	/**
	 * @param notificationId the notificationId to set
	 */
	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}
	/**
	 * @return the outletByOutletIdTo
	 */
	public int getOutletByOutletIdTo() {
		return outletByOutletIdTo;
	}
	/**
	 * @param outletByOutletIdTo the outletByOutletIdTo to set
	 */
	public void setOutletByOutletIdTo(int outletByOutletIdTo) {
		this.outletByOutletIdTo = outletByOutletIdTo;
	}
	/**
	 * @return the outletByOutletIdToName
	 */
	public String getOutletByOutletIdToName() {
		return outletByOutletIdToName;
	}
	/**
	 * @param outletByOutletIdToName the outletByOutletIdToName to set
	 */
	public void setOutletByOutletIdToName(String outletByOutletIdToName) {
		this.outletByOutletIdToName = outletByOutletIdToName;
	}
	/**
	 * @return the companyId
	 */
	public int getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the outletByOutletIdFrom
	 */
	public int getOutletByOutletIdFrom() {
		return outletByOutletIdFrom;
	}
	/**
	 * @param outletByOutletIdFrom the outletByOutletIdFrom to set
	 */
	public void setOutletByOutletIdFrom(int outletByOutletIdFrom) {
		this.outletByOutletIdFrom = outletByOutletIdFrom;
	}
	/**
	 * @return the outletByOutletIdFromName
	 */
	public String getOutletByOutletIdFromName() {
		return outletByOutletIdFromName;
	}
	/**
	 * @param outletByOutletIdFromName the outletByOutletIdFromName to set
	 */
	public void setOutletByOutletIdFromName(String outletByOutletIdFromName) {
		this.outletByOutletIdFromName = outletByOutletIdFromName;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the markAsRead
	 */
	public boolean isMarkAsRead() {
		return markAsRead;
	}
	/**
	 * @param markAsRead the markAsRead to set
	 */
	public void setMarkAsRead(boolean markAsRead) {
		this.markAsRead = markAsRead;
	}
	/**
	 * @return the activeIndicator
	 */
	public boolean isActiveIndicator() {
		return activeIndicator;
	}
	/**
	 * @param activeIndicator the activeIndicator to set
	 */
	public void setActiveIndicator(boolean activeIndicator) {
		this.activeIndicator = activeIndicator;
	}
	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the lastUpdated
	 */
	public String getLastUpdated() {
		return lastUpdated;
	}
	/**
	 * @param lastUpdated the lastUpdated to set
	 */
	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the updatedBy
	 */
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	/**
	 * @param updatedBy the updatedBy to set
	 */
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
}
