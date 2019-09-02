/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

/**
 * @author Yameen Bashir
 *
 */
public class TicketBean {
	
	private String ticketId;
	private String description;
	private String ticketDetail;
	private String severityAssociationId;
	private String severityDescription;
	private String ticketStatus;
	private String createdBy;
	private String createdDate;
	/**
	 * 
	 */
	public TicketBean() {
		
	}
	/**
	 * @param ticketId
	 * @param description
	 * @param ticketDetail
	 * @param severityAssociationId
	 * @param severityDescription
	 * @param ticketStatus
	 * @param createdBy
	 * @param createdDate
	 */
	public TicketBean(String ticketId, String description, String ticketDetail,
			String severityAssociationId, String severityDescription,
			String ticketStatus, String createdBy, String createdDate) {
		this.ticketId = ticketId;
		this.description = description;
		this.ticketDetail = ticketDetail;
		this.severityAssociationId = severityAssociationId;
		this.severityDescription = severityDescription;
		this.ticketStatus = ticketStatus;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
	}
	/**
	 * @return the ticketId
	 */
	public String getTicketId() {
		return ticketId;
	}
	/**
	 * @param ticketId the ticketId to set
	 */
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
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
	 * @return the ticketDetail
	 */
	public String getTicketDetail() {
		return ticketDetail;
	}
	/**
	 * @param ticketDetail the ticketDetail to set
	 */
	public void setTicketDetail(String ticketDetail) {
		this.ticketDetail = ticketDetail;
	}
	/**
	 * @return the severityAssociationId
	 */
	public String getSeverityAssociationId() {
		return severityAssociationId;
	}
	/**
	 * @param severityAssociationId the severityAssociationId to set
	 */
	public void setSeverityAssociationId(String severityAssociationId) {
		this.severityAssociationId = severityAssociationId;
	}
	/**
	 * @return the severityDescription
	 */
	public String getSeverityDescription() {
		return severityDescription;
	}
	/**
	 * @param severityDescription the severityDescription to set
	 */
	public void setSeverityDescription(String severityDescription) {
		this.severityDescription = severityDescription;
	}
	/**
	 * @return the ticketStatus
	 */
	public String getTicketStatus() {
		return ticketStatus;
	}
	/**
	 * @param ticketStatus the ticketStatus to set
	 */
	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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
}
