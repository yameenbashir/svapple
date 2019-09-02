/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

/**
 * @author Yameen Bashir
 *
 */
public class MenuBean {

	private String pageName;
	private String roleId;
	private Boolean activeIndicatior;
	private String menuId;
	/**
	 * 
	 */
	public MenuBean() {
	}
	/**
	 * @param pageName
	 * @param roleId
	 * @param activeIndicatior
	 * @param menuId
	 */
	public MenuBean(String pageName, String roleId, Boolean activeIndicatior,
			String menuId) {
		this.pageName = pageName;
		this.roleId = roleId;
		this.activeIndicatior = activeIndicatior;
		this.menuId = menuId;
	}
	/**
	 * @return the pageName
	 */
	public String getPageName() {
		return pageName;
	}
	/**
	 * @param pageName the pageName to set
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
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
	 * @return the activeIndicatior
	 */
	public Boolean getActiveIndicatior() {
		return activeIndicatior;
	}
	/**
	 * @param activeIndicatior the activeIndicatior to set
	 */
	public void setActiveIndicatior(Boolean activeIndicatior) {
		this.activeIndicatior = activeIndicatior;
	}
	/**
	 * @return the menuId
	 */
	public String getMenuId() {
		return menuId;
	}
	/**
	 * @param menuId the menuId to set
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	
	
}
