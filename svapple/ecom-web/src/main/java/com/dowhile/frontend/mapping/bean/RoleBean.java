/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

/**
 * @author Yameen Bashir
 *
 */
public class RoleBean {
	
	private String roleId;
	private String description;
	private String actionType;
	private String activeIndicator;
	private String lastUpdated;
	/**
	 * 
	 */
	public RoleBean() {
	}
	/**
	 * @param roleId
	 * @param description
	 * @param actionType
	 * @param activeIndicator
	 * @param lastUpdated
	 */
	public RoleBean(String roleId, String description, String actionType,
			String activeIndicator, String lastUpdated) {
		this.roleId = roleId;
		this.description = description;
		this.actionType = actionType;
		this.activeIndicator = activeIndicator;
		this.lastUpdated = lastUpdated;
	}
	/**
	 * @return the roleId
	 */
	public String getRoleId() {
		return roleId;
	}
	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
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
	 * @return the actionType
	 */
	public String getActionType() {
		return actionType;
	}
	/**
	 * @param actionType the actionType to set
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	/**
	 * @return the activeIndicator
	 */
	public String getActiveIndicator() {
		return activeIndicator;
	}
	/**
	 * @param activeIndicator the activeIndicator to set
	 */
	public void setActiveIndicator(String activeIndicator) {
		this.activeIndicator = activeIndicator;
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
}
