package com.dowhile.angualrspringapp.beans;

public class ImageData {
	
	private String file;
	private String user;
	private String id;
	private String fileName;
	/**
	 * 
	 */
	public ImageData() {
	}
	/**
	 * @param file
	 * @param user
	 * @param id
	 * @param fileName
	 */
	public ImageData(String file, String user, String id, String fileName) {
		this.file = file;
		this.user = user;
		this.id = id;
		this.fileName = fileName;
	}
	/**
	 * @return the file
	 */
	public String getFile() {
		return file;
	}
	/**
	 * @param file the file to set
	 */
	public void setFile(String file) {
		this.file = file;
	}
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
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
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
