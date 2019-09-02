/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

/**
 * @author Yameen Bashir
 *
 */
public class TicketActivityBean {
	
	private String ticketActivityId;
	private String ticketAssociationId;
	private String description;
	private String userReply;
	private String resolverFeedBack;
	private String createdDate;
	private String lastUpdated;
	private String createdTime;
	private String updatedTime; 
	private String oldStatus;
	private String newStatus;
	
	/**
	 * 
	 */
	public TicketActivityBean() {
	}

	/**
	 * @param ticketActivityId
	 * @param ticketAssociationId
	 * @param description
	 * @param userReply
	 * @param resolverFeedBack
	 * @param createdDate
	 * @param lastUpdated
	 * @param createdTime
	 * @param updatedTime
	 * @param oldStatus
	 * @param newStatus
	 */
	public TicketActivityBean(String ticketActivityId,
			String ticketAssociationId, String description, String userReply,
			String resolverFeedBack, String createdDate, String lastUpdated,
			String createdTime, String updatedTime, String oldStatus,
			String newStatus) {
		this.ticketActivityId = ticketActivityId;
		this.ticketAssociationId = ticketAssociationId;
		this.description = description;
		this.userReply = userReply;
		this.resolverFeedBack = resolverFeedBack;
		this.createdDate = createdDate;
		this.lastUpdated = lastUpdated;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.oldStatus = oldStatus;
		this.newStatus = newStatus;
	}

	/**
	 * @return the ticketActivityId
	 */
	public String getTicketActivityId() {
		return ticketActivityId;
	}

	/**
	 * @param ticketActivityId the ticketActivityId to set
	 */
	public void setTicketActivityId(String ticketActivityId) {
		this.ticketActivityId = ticketActivityId;
	}

	/**
	 * @return the ticketAssociationId
	 */
	public String getTicketAssociationId() {
		return ticketAssociationId;
	}

	/**
	 * @param ticketAssociationId the ticketAssociationId to set
	 */
	public void setTicketAssociationId(String ticketAssociationId) {
		this.ticketAssociationId = ticketAssociationId;
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
	 * @return the userReply
	 */
	public String getUserReply() {
		return userReply;
	}

	/**
	 * @param userReply the userReply to set
	 */
	public void setUserReply(String userReply) {
		this.userReply = userReply;
	}

	/**
	 * @return the resolverFeedBack
	 */
	public String getResolverFeedBack() {
		return resolverFeedBack;
	}

	/**
	 * @param resolverFeedBack the resolverFeedBack to set
	 */
	public void setResolverFeedBack(String resolverFeedBack) {
		this.resolverFeedBack = resolverFeedBack;
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
	 * @return the createdTime
	 */
	public String getCreatedTime() {
		return createdTime;
	}

	/**
	 * @param createdTime the createdTime to set
	 */
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	/**
	 * @return the updatedTime
	 */
	public String getUpdatedTime() {
		return updatedTime;
	}

	/**
	 * @param updatedTime the updatedTime to set
	 */
	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}

	/**
	 * @return the oldStatus
	 */
	public String getOldStatus() {
		return oldStatus;
	}

	/**
	 * @param oldStatus the oldStatus to set
	 */
	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	/**
	 * @return the newStatus
	 */
	public String getNewStatus() {
		return newStatus;
	}

	/**
	 * @param newStatus the newStatus to set
	 */
	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}
}
