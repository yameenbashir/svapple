/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

/**
 * @author Yameen Bashir
 *
 */
public class VarientAttributeBean {
	
	private String varientAttributeId;
	private String attributeName;
	private String id;
	private String value;
	/**
	 * 
	 */
	public VarientAttributeBean() {
	}
	/**
	 * @param varientAttributeId
	 * @param attributeName
	 * @param id
	 * @param value
	 */
	public VarientAttributeBean(String varientAttributeId,
			String attributeName, String id, String value) {
		this.varientAttributeId = varientAttributeId;
		this.attributeName = attributeName;
		this.id = id;
		this.value = value;
	}
	/**
	 * @return the varientAttributeId
	 */
	public String getVarientAttributeId() {
		return varientAttributeId;
	}
	/**
	 * @param varientAttributeId the varientAttributeId to set
	 */
	public void setVarientAttributeId(String varientAttributeId) {
		this.varientAttributeId = varientAttributeId;
	}
	/**
	 * @return the attributeName
	 */
	public String getAttributeName() {
		return attributeName;
	}
	/**
	 * @param attributeName the attributeName to set
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
