package com.dowhile.controller.bean;
/**
 * @author Yameen Bashir
 *
 */
public class Response<Data,Status,LayoutPath> {

	public Data data;
	public Status status;
	public LayoutPath layOutPath;
	/**
	 * @param data
	 * @param status
	 * @param layOutPath
	 */
	public Response(Data data, Status status, LayoutPath layOutPath) {
		this.data = data;
		this.status = status;
		this.layOutPath = layOutPath;
	}
	/**
	 * @return the data
	 */
	public Data getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(Data data) {
		this.data = data;
	}
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	/**
	 * @return the layOutPath
	 */
	public LayoutPath getLayOutPath() {
		return layOutPath;
	}
	/**
	 * @param layOutPath the layOutPath to set
	 */
	public void setLayOutPath(LayoutPath layOutPath) {
		this.layOutPath = layOutPath;
	}
}
