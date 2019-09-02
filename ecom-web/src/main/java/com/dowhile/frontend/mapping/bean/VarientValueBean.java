/**
 * 
 */
package com.dowhile.frontend.mapping.bean;

import java.util.List;

/**
 * @author Yameen Bashir
 *
 */
public class VarientValueBean {
	
	private String varientName;
	private String varientId;
	private String uUid;
	private List<OutletBean> varientsOutletList;
	/**
	 * 
	 */
	public VarientValueBean() {
	}
	/**
	 * @param varientName
	 * @param varientId
	 * @param uUid
	 * @param varientsOutletList
	 */
	public VarientValueBean(String varientName, String varientId, String uUid,
			List<OutletBean> varientsOutletList) {
		this.varientName = varientName;
		this.varientId = varientId;
		this.uUid = uUid;
		this.varientsOutletList = varientsOutletList;
	}
	/**
	 * @return the varientName
	 */
	public String getVarientName() {
		return varientName;
	}
	/**
	 * @param varientName the varientName to set
	 */
	public void setVarientName(String varientName) {
		this.varientName = varientName;
	}
	/**
	 * @return the varientId
	 */
	public String getVarientId() {
		return varientId;
	}
	/**
	 * @param varientId the varientId to set
	 */
	public void setVarientId(String varientId) {
		this.varientId = varientId;
	}
	/**
	 * @return the uUid
	 */
	public String getuUid() {
		return uUid;
	}
	/**
	 * @param uUid the uUid to set
	 */
	public void setuUid(String uUid) {
		this.uUid = uUid;
	}
	/**
	 * @return the varientsOutletList
	 */
	public List<OutletBean> getVarientsOutletList() {
		return varientsOutletList;
	}
	/**
	 * @param varientsOutletList the varientsOutletList to set
	 */
	public void setVarientsOutletList(List<OutletBean> varientsOutletList) {
		this.varientsOutletList = varientsOutletList;
	}
}
