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
	private String termsAndConditions;
	private int countNotifications;
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
	 * @param termsAndConditions
	 */
	public LoginBean(String userName, String sessionId, String userRole,
			String userId, String outletId, String isHeadOffice,
			String createdDate, String compnayID, String compnayName,
			Map<String, Boolean> mapMenu, Map<String, String> userMap,
			String companyImagePath, String termsAndConditions,int countNotifications) {
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
		this.termsAndConditions = termsAndConditions;
		this.countNotifications = countNotifications;
	}
	public int getCountNotifications() {
		return countNotifications;
	}
	public void setCountNotifications(int countNotifications) {
		this.countNotifications = countNotifications;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOutletId() {
		return outletId;
	}
	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}
	public String getIsHeadOffice() {
		return isHeadOffice;
	}
	public void setIsHeadOffice(String isHeadOffice) {
		this.isHeadOffice = isHeadOffice;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCompnayID() {
		return compnayID;
	}
	public void setCompnayID(String compnayID) {
		this.compnayID = compnayID;
	}
	public String getCompnayName() {
		return compnayName;
	}
	public void setCompnayName(String compnayName) {
		this.compnayName = compnayName;
	}
	public Map<String, Boolean> getMapMenu() {
		return mapMenu;
	}
	public void setMapMenu(Map<String, Boolean> mapMenu) {
		this.mapMenu = mapMenu;
	}
	public Map<String, String> getUserMap() {
		return userMap;
	}
	public void setUserMap(Map<String, String> userMap) {
		this.userMap = userMap;
	}
	public String getCompanyImagePath() {
		return companyImagePath;
	}
	public void setCompanyImagePath(String companyImagePath) {
		this.companyImagePath = companyImagePath;
	}
	public String getTermsAndConditions() {
		return termsAndConditions;
	}
	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}
}
