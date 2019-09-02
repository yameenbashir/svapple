/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.angualrspringapp.beans.Select;
import com.dowhile.frontend.mapping.bean.TicketBean;

/**
 * @author Yameen Bashir
 *
 */
public class SupportControllerBean {

	private List<TicketBean> ticketsList ;
	private List<Select> severityList ;
	/**
	 * 
	 */
	public SupportControllerBean() {
	}
	/**
	 * @param ticketsList
	 * @param severityList
	 */
	public SupportControllerBean(List<TicketBean> ticketsList,
			List<Select> severityList) {
		this.ticketsList = ticketsList;
		this.severityList = severityList;
	}
	/**
	 * @return the ticketsList
	 */
	public List<TicketBean> getTicketsList() {
		return ticketsList;
	}
	/**
	 * @param ticketsList the ticketsList to set
	 */
	public void setTicketsList(List<TicketBean> ticketsList) {
		this.ticketsList = ticketsList;
	}
	/**
	 * @return the severityList
	 */
	public List<Select> getSeverityList() {
		return severityList;
	}
	/**
	 * @param severityList the severityList to set
	 */
	public void setSeverityList(List<Select> severityList) {
		this.severityList = severityList;
	}
}
