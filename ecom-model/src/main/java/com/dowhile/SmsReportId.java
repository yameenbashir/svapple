package com.dowhile;

// Generated Dec 23, 2017 11:31:14 PM by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * SmsReportId generated by hbm2java
 */
@Embeddable
public class SmsReportId implements java.io.Serializable {

	private int messageDetailId;
	private String senderId;
	private String receiverId;
	private String messageDescription;
	private String deliveryId;
	private String deliveryStatus;
	private int messageAssociationId;
	private Long messageBundleCount;
	private Integer outletAssocicationId;
	private String outletName;
	private String smsSentDate;
	private Date createdDate;
	private String senderName;
	private String customerName;
	private int companyAssociationId;

	public SmsReportId() {
	}

	public SmsReportId(int messageDetailId, int messageAssociationId,
			int companyAssociationId) {
		this.messageDetailId = messageDetailId;
		this.messageAssociationId = messageAssociationId;
		this.companyAssociationId = companyAssociationId;
	}

	public SmsReportId(int messageDetailId, String senderId, String receiverId,
			String messageDescription, String deliveryId,
			String deliveryStatus, int messageAssociationId,
			Long messageBundleCount, Integer outletAssocicationId,
			String outletName, String smsSentDate, Date createdDate,
			String senderName, String customerName, int companyAssociationId) {
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
		this.customerName = customerName;
		this.companyAssociationId = companyAssociationId;
	}

	@Column(name = "MESSAGE_DETAIL_ID", nullable = false)
	public int getMessageDetailId() {
		return this.messageDetailId;
	}

	public void setMessageDetailId(int messageDetailId) {
		this.messageDetailId = messageDetailId;
	}

	@Column(name = "SENDER_ID", length = 256)
	public String getSenderId() {
		return this.senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	@Column(name = "RECEIVER_ID", length = 256)
	public String getReceiverId() {
		return this.receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	@Column(name = "MESSAGE_DESCRIPTION", length = 1000)
	public String getMessageDescription() {
		return this.messageDescription;
	}

	public void setMessageDescription(String messageDescription) {
		this.messageDescription = messageDescription;
	}

	@Column(name = "DELIVERY_ID", length = 65535)
	public String getDeliveryId() {
		return this.deliveryId;
	}

	public void setDeliveryId(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	@Column(name = "DELIVERY_STATUS", length = 1000)
	public String getDeliveryStatus() {
		return this.deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	@Column(name = "MESSAGE_ASSOCIATION_ID", nullable = false)
	public int getMessageAssociationId() {
		return this.messageAssociationId;
	}

	public void setMessageAssociationId(int messageAssociationId) {
		this.messageAssociationId = messageAssociationId;
	}

	@Column(name = "MESSAGE_BUNDLE_COUNT")
	public Long getMessageBundleCount() {
		return this.messageBundleCount;
	}

	public void setMessageBundleCount(Long messageBundleCount) {
		this.messageBundleCount = messageBundleCount;
	}

	@Column(name = "OUTLET_ASSOCICATION_ID")
	public Integer getOutletAssocicationId() {
		return this.outletAssocicationId;
	}

	public void setOutletAssocicationId(Integer outletAssocicationId) {
		this.outletAssocicationId = outletAssocicationId;
	}

	@Column(name = "OUTLET_NAME", length = 100)
	public String getOutletName() {
		return this.outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

	@Column(name = "SMS_SENT_DATE", length = 49)
	public String getSmsSentDate() {
		return this.smsSentDate;
	}

	public void setSmsSentDate(String smsSentDate) {
		this.smsSentDate = smsSentDate;
	}

	@Column(name = "CREATED_DATE", length = 10)
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "SENDER_NAME", length = 65535)
	public String getSenderName() {
		return this.senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	@Column(name = "CUSTOMER_NAME", length = 200)
	public String getCustomerName() {
		return this.customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "COMPANY_ASSOCIATION_ID", nullable = false)
	public int getCompanyAssociationId() {
		return this.companyAssociationId;
	}

	public void setCompanyAssociationId(int companyAssociationId) {
		this.companyAssociationId = companyAssociationId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof SmsReportId))
			return false;
		SmsReportId castOther = (SmsReportId) other;

		return (this.getMessageDetailId() == castOther.getMessageDetailId())
				&& ((this.getSenderId() == castOther.getSenderId()) || (this
						.getSenderId() != null
						&& castOther.getSenderId() != null && this
						.getSenderId().equals(castOther.getSenderId())))
				&& ((this.getReceiverId() == castOther.getReceiverId()) || (this
						.getReceiverId() != null
						&& castOther.getReceiverId() != null && this
						.getReceiverId().equals(castOther.getReceiverId())))
				&& ((this.getMessageDescription() == castOther
						.getMessageDescription()) || (this
						.getMessageDescription() != null
						&& castOther.getMessageDescription() != null && this
						.getMessageDescription().equals(
								castOther.getMessageDescription())))
				&& ((this.getDeliveryId() == castOther.getDeliveryId()) || (this
						.getDeliveryId() != null
						&& castOther.getDeliveryId() != null && this
						.getDeliveryId().equals(castOther.getDeliveryId())))
				&& ((this.getDeliveryStatus() == castOther.getDeliveryStatus()) || (this
						.getDeliveryStatus() != null
						&& castOther.getDeliveryStatus() != null && this
						.getDeliveryStatus().equals(
								castOther.getDeliveryStatus())))
				&& (this.getMessageAssociationId() == castOther
						.getMessageAssociationId())
				&& ((this.getMessageBundleCount() == castOther
						.getMessageBundleCount()) || (this
						.getMessageBundleCount() != null
						&& castOther.getMessageBundleCount() != null && this
						.getMessageBundleCount().equals(
								castOther.getMessageBundleCount())))
				&& ((this.getOutletAssocicationId() == castOther
						.getOutletAssocicationId()) || (this
						.getOutletAssocicationId() != null
						&& castOther.getOutletAssocicationId() != null && this
						.getOutletAssocicationId().equals(
								castOther.getOutletAssocicationId())))
				&& ((this.getOutletName() == castOther.getOutletName()) || (this
						.getOutletName() != null
						&& castOther.getOutletName() != null && this
						.getOutletName().equals(castOther.getOutletName())))
				&& ((this.getSmsSentDate() == castOther.getSmsSentDate()) || (this
						.getSmsSentDate() != null
						&& castOther.getSmsSentDate() != null && this
						.getSmsSentDate().equals(castOther.getSmsSentDate())))
				&& ((this.getCreatedDate() == castOther.getCreatedDate()) || (this
						.getCreatedDate() != null
						&& castOther.getCreatedDate() != null && this
						.getCreatedDate().equals(castOther.getCreatedDate())))
				&& ((this.getSenderName() == castOther.getSenderName()) || (this
						.getSenderName() != null
						&& castOther.getSenderName() != null && this
						.getSenderName().equals(castOther.getSenderName())))
				&& ((this.getCustomerName() == castOther.getCustomerName()) || (this
						.getCustomerName() != null
						&& castOther.getCustomerName() != null && this
						.getCustomerName().equals(castOther.getCustomerName())))
				&& (this.getCompanyAssociationId() == castOther
						.getCompanyAssociationId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getMessageDetailId();
		result = 37 * result
				+ (getSenderId() == null ? 0 : this.getSenderId().hashCode());
		result = 37
				* result
				+ (getReceiverId() == null ? 0 : this.getReceiverId()
						.hashCode());
		result = 37
				* result
				+ (getMessageDescription() == null ? 0 : this
						.getMessageDescription().hashCode());
		result = 37
				* result
				+ (getDeliveryId() == null ? 0 : this.getDeliveryId()
						.hashCode());
		result = 37
				* result
				+ (getDeliveryStatus() == null ? 0 : this.getDeliveryStatus()
						.hashCode());
		result = 37 * result + this.getMessageAssociationId();
		result = 37
				* result
				+ (getMessageBundleCount() == null ? 0 : this
						.getMessageBundleCount().hashCode());
		result = 37
				* result
				+ (getOutletAssocicationId() == null ? 0 : this
						.getOutletAssocicationId().hashCode());
		result = 37
				* result
				+ (getOutletName() == null ? 0 : this.getOutletName()
						.hashCode());
		result = 37
				* result
				+ (getSmsSentDate() == null ? 0 : this.getSmsSentDate()
						.hashCode());
		result = 37
				* result
				+ (getCreatedDate() == null ? 0 : this.getCreatedDate()
						.hashCode());
		result = 37
				* result
				+ (getSenderName() == null ? 0 : this.getSenderName()
						.hashCode());
		result = 37
				* result
				+ (getCustomerName() == null ? 0 : this.getCustomerName()
						.hashCode());
		result = 37 * result + this.getCompanyAssociationId();
		return result;
	}

}
