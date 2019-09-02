package com.dowhile.controller.bean;

import java.util.Map;

/**
 * @author Yameen Bashir
 *
 */
public class LoginBean {
	
	private String userName;
	private String sessionId;
	private String userRole;
	private String userId;
	private String outletId;
	private String isHeadOffice;
	private String createdDate;
	private String compnayID;
	private String compnayName;
	private Map<String ,Boolean> mapMenu;
	private Map<String ,String> userMap;
	private String companyImagePath;
	/**
	 * 
	 */
	public LoginBean() {
		
	}
	/**
	 * @param userName
	 * @param sessionId
	 * @param userRole
	 * @param userId
	 * @param outletId
	 * @param isHeadOffice
	 * @param createdDate
	 * @param compnayID
	 * @param compnayName
	 * @param mapMenu
	 * @param userMap
	 * @param companyImagePath
	 */
	public LoginBean(String userName, String sessionId, String userRole,
			String userId, String outletId, String isHeadOffice,
			String createdDate, String compnayID, String compnayName,
			Map<String, Boolean> mapMenu, Map<String, String> userMap,
			String companyImagePath) {
		this.userName = userName;
		this.sessionId = sessionId;
		this.userRole = userRole;
		this.userId = userId;
		this.outletId = outletId;
		this.isHeadOffice = isHeadOffice;
		this.createdDate = createdDate;
		this.compnayID = compnayID;
		this.compnayName = compnayName;
		this.mapMenu = mapMenu;
		this.userMap = userMap;
		this.companyImagePath = companyImagePath;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}
	/**
	 * @param sessionId the sessionId to set
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}
	/**
	 * @param userRole the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the outletId
	 */
	public String getOutletId() {
		return outletId;
	}
	/**
	 * @param outletId the outletId to set
	 */
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
	/**
	 * @return the isHeadOffice
	 */
	public String getIsHeadOffice() {
		return isHeadOffice;
	}
	/**
	 * @param isHeadOffice the isHeadOffice to set
	 */
	public void setIsHeadOffice(String isHeadOffice) {
		this.isHeadOffice = isHeadOffice;
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
	 * @return the compnayID
	 */
	public String getCompnayID() {
		return compnayID;
	}
	/**
	 * @param compnayID the compnayID to set
	 */
	public void setCompnayID(String compnayID) {
		this.compnayID = compnayID;
	}
	/**
	 * @return the compnayName
	 */
	public String getCompnayName() {
		return compnayName;
	}
	/**
	 * @param compnayName the compnayName to set
	 */
	public void setCompnayName(String compnayName) {
		this.compnayName = compnayName;
	}
	/**
	 * @return the mapMenu
	 */
	public Map<String, Boolean> getMapMenu() {
		return mapMenu;
	}
	/**
	 * @param mapMenu the mapMenu to set
	 */
	public void setMapMenu(Map<String, Boolean> mapMenu) {
		this.mapMenu = mapMenu;
	}
	/**
	 * @return the userMap
	 */
	public Map<String, String> getUserMap() {
		return userMap;
	}
	/**
	 * @param userMap the userMap to set
	 */
	public void setUserMap(Map<String, String> userMap) {
		this.userMap = userMap;
	}
	/**
	 * @return the companyImagePath
	 */
	public String getCompanyImagePath() {
		return companyImagePath;
	}
	/**
	 * @param companyImagePath the companyImagePath to set
	 */
	public void setCompanyImagePath(String companyImagePath) {
		this.companyImagePath = companyImagePath;
	}
}
