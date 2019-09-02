/**
 * 
 */
package com.dowhile.controller.bean;


/**
 * @author Hafiz Yameen Bashir
 *
 */
public class SmsReportBean {
	
	 private String messageDetailId;
     private String senderId;
     private String receiverId;
     private String messageDescription;
     private String deliveryId;
     private String deliveryStatus;
     private String messageAssociationId;
     private String messageBundleCount;
     private Integer outletAssocicationId;
     private String outletName;
     private String smsSentDate;
     private String createdDate;
     private String senderName;
     private String companyAssociationId;
     private String customerName;
	/**
	 * 
	 */
	public SmsReportBean() {
	}
	/**
	 * @param messageDetailId
	 * @param senderId
	 * @param receiverId
	 * @param messageDescription
	 * @param deliveryId
	 * @param deliveryStatus
	 * @param messageAssociationId
	 * @param messageBundleCount
	 * @param outletAssocicationId
	 * @param outletName
	 * @param smsSentDate
	 * @param createdDate
	 * @param senderName
	 * @param companyAssociationId
	 * @param customerName
	 */
	public SmsReportBean(String messageDetailId, String senderId,
			String receiverId, String messageDescription, String deliveryId,
			String deliveryStatus, String messageAssociationId,
			String messageBundleCount, Integer outletAssocicationId,
			String outletName, String smsSentDate, String createdDate,
			String senderName, String companyAssociationId, String customerName) {
		this.messageDetailId = messageDetailId;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.messageDescription = messageDescription;
		this.deliveryId = deliveryId;
		this.deliveryStatus = deliveryStatus;
		this.messageAssociationId = messageAssociationId;
		this.messageBundleCount = messageBundleCount;
		this.outletAssocicationId = outletAssocicationId;
		this.outletName = outletName;
		this.smsSentDate = smsSentDate;
		this.createdDate = createdDate;
		this.senderName = senderName;
		this.companyAssociationId = companyAssociationId;
		this.customerName = customerName;
	}
	/**
	 * @return the messageDetailId
	 */
	public String getMessageDetailId() {
		return messageDetailId;
	}
	/**
	 * @param messageDetailId the messageDetailId to set
	 */
	public void setMessageDetailId(String messageDetailId) {
		this.messageDetailId = messageDetailId;
	}
	/**
	 * @return the senderId
	 */
	public String getSenderId() {
		return senderId;
	}
	/**
	 * @param senderId the senderId to set
	 */
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	/**
	 * @return the receiverId
	 */
	public String getReceiverId() {
		return receiverId;
	}
	/**
	 * @param receiverId the receiverId to set
	 */
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	/**
	 * @return the messageDescription
	 */
	public String getMessageDescription() {
		return messageDescription;
	}
	/**
	 * @param messageDescription the messageDescription to set
	 */
	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}
	/**
	 * @return the deliveryId
	 */
	public String getDeliveryId() {
		return deliveryId;
	}
	/**
	 * @param deliveryId the deliveryId to set
	 */
	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}
	/**
	 * @return the deliveryStatus
	 */
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	/**
	 * @param deliveryStatus the deliveryStatus to set
	 */
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	/**
	 * @return the messageAssociationId
	 */
	public String getMessageAssociationId() {
		return messageAssociationId;
	}
	/**
	 * @param messageAssociationId the messageAssociationId to set
	 */
	public void setMessageAssociationId(String messageAssociationId) {
		this.messageAssociationId = messageAssociationId;
	}
	/**
	 * @return the messageBundleCount
	 */
	public String getMessageBundleCount() {
		return messageBundleCount;
	}
	/**
	 * @param messageBundleCount the messageBundleCount to set
	 */
	public void setMessageBundleCount(String messageBundleCount) {
		this.messageBundleCount = messageBundleCount;
	}
	/**
	 * @return the outletAssocicationId
	 */
	public Integer getOutletAssocicationId() {
		return outletAssocicationId;
	}
	/**
	 * @param outletAssocicationId the outletAssocicationId to set
	 */
	public void setOutletAssocicationId(Integer outletAssocicationId) {
		this.outletAssocicationId = outletAssocicationId;
	}
	/**
	 * @return the outletName
	 */
	public String getOutletName() {
		return outletName;
	}
	/**
	 * @param outletName the outletName to set
	 */
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	/**
	 * @return the smsSentDate
	 */
	public String getSmsSentDate() {
		return smsSentDate;
	}
	/**
	 * @param smsSentDate the smsSentDate to set
	 */
	public void setSmsSentDate(String smsSentDate) {
		this.smsSentDate = smsSentDate;
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
	 * @return the senderName
	 */
	public String getSenderName() {
		return senderName;
	}
	/**
	 * @param senderName the senderName to set
	 */
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	/**
	 * @return the companyAssociationId
	 */
	public String getCompanyAssociationId() {
		return companyAssociationId;
	}
	/**
	 * @param companyAssociationId the companyAssociationId to set
	 */
	public void setCompanyAssociationId(String companyAssociationId) {
		this.companyAssociationId = companyAssociationId;
	}
	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}
