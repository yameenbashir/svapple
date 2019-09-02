package com.dowhile.angualrspringapp.beans;

public class ContactUsBean {
	private String name;
	private String email;
	private String message;
	private String contactNumber;
	private String contactUsId;
	private String requestStatus;
	/**
	 * 
	 */
	public ContactUsBean() {
		
	}
	/**
	 * @param name
	 * @param email
	 * @param message
	 * @param contactNumber
	 * @param contactUsId
	 * @param requestStatus
	 */
	public ContactUsBean(String name, String email, String message,
			String contactNumber, String contactUsId, String requestStatus) {
		this.name = name;
		this.email = email;
		this.message = message;
		this.contactNumber = contactNumber;
		this.contactUsId = contactUsId;
		this.requestStatus = requestStatus;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the contactNumber
	 */
	public String getContactNumber() {
		return contactNumber;
	}
	/**
	 * @param contactNumber the contactNumber to set
	 */
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	/**
	 * @return the contactUsId
	 */
	public String getContactUsId() {
		return contactUsId;
	}
	/**
	 * @param contactUsId the contactUsId to set
	 */
	public void setContactUsId(String contactUsId) {
		this.contactUsId = contactUsId;
	}
	/**
	 * @return the requestStatus
	 */
	public String getRequestStatus() {
		return requestStatus;
	}
	/**
	 * @param requestStatus the requestStatus to set
	 */
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
}
