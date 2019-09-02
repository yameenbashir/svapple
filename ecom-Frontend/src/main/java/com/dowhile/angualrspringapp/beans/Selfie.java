/**
 * 
 */
package com.dowhile.angualrspringapp.beans;

/**
 * @author imran.latif
 *
 */
public class Selfie {
	
	boolean webCamActive;
	String imagePath;
	String placeHolderImage;
	/**
	 * @return the webCamActive
	 */
	public final boolean isWebCamActive() {
		return webCamActive;
	}
	/**
	 * @param webCamActive the webCamActive to set
	 */
	public final void setWebCamActive(boolean webCamActive) {
		this.webCamActive = webCamActive;
	}
	/**
	 * @return the imagePath
	 */
	public final String getImagePath() {
		return imagePath;
	}
	/**
	 * @param imagePath the imagePath to set
	 */
	public final void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	/**
	 * @return the placeHolderImage
	 */
	public final String getPlaceHolderImage() {
		return placeHolderImage;
	}
	/**
	 * @param placeHolderImage the placeHolderImage to set
	 */
	public final void setPlaceHolderImage(String placeHolderImage) {
		this.placeHolderImage = placeHolderImage;
	}

}
