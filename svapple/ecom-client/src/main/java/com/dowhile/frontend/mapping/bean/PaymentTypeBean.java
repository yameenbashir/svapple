/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

/**
 * @author Yameen Bashir
 *
 */
public class PaymentTypeBean {
	
	private String paymentTypeId;
	private String paymentTypeValue;
	private String roundTo;
	private String gateway;
	/**
	 * 
	 */
	public PaymentTypeBean() {
	}
	/**
	 * @param paymentTypeId
	 * @param paymentTypeValue
	 * @param roundTo
	 * @param gateway
	 */
	public PaymentTypeBean(String paymentTypeId, String paymentTypeValue,
			String roundTo, String gateway) {
		this.paymentTypeId = paymentTypeId;
		this.paymentTypeValue = paymentTypeValue;
		this.roundTo = roundTo;
		this.gateway = gateway;
	}
	/**
	 * @return the paymentTypeId
	 */
	public String getPaymentTypeId() {
		return paymentTypeId;
	}
	/**
	 * @param paymentTypeId the paymentTypeId to set
	 */
	public void setPaymentTypeId(String paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}
	/**
	 * @return the paymentTypeValue
	 */
	public String getPaymentTypeValue() {
		return paymentTypeValue;
	}
	/**
	 * @param paymentTypeValue the paymentTypeValue to set
	 */
	public void setPaymentTypeValue(String paymentTypeValue) {
		this.paymentTypeValue = paymentTypeValue;
	}
	/**
	 * @return the roundTo
	 */
	public String getRoundTo() {
		return roundTo;
	}
	/**
	 * @param roundTo the roundTo to set
	 */
	public void setRoundTo(String roundTo) {
		this.roundTo = roundTo;
	}
	/**
	 * @return the gateway
	 */
	public String getGateway() {
		return gateway;
	}
	/**
	 * @param gateway the gateway to set
	 */
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

}
