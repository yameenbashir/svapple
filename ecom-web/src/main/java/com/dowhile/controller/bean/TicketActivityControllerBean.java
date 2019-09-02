/**
 * 
 */
package com.dowhile.controller.bean;

import java.util.List;

import com.dowhile.frontend.mapping.bean.TicketActivityBean;
import com.dowhile.frontend.mapping.bean.TicketBean;

/**
 * @author Yameen Bashir
 *
 */
public class TicketActivityControllerBean {
	
	private List<TicketActivityBean> ticketActivitiesList ;
	private TicketBean ticketBean;
	/**
	 * 
	 */
	public TicketActivityControllerBean() {
	}
	/**
	 * @param ticketActivitiesList
	 * @param ticketBean
	 */
	public TicketActivityControllerBean(
			List<TicketActivityBean> ticketActivitiesList, TicketBean ticketBean) {
		this.ticketActivitiesList = ticketActivitiesList;
		this.ticketBean = ticketBean;
	}
	/**
	 * @return the ticketActivitiesList
	 */
	public List<TicketActivityBean> getTicketActivitiesList() {
		return ticketActivitiesList;
	}
	/**
	 * @param ticketActivitiesList the ticketActivitiesList to set
	 */
	public void setTicketActivitiesList(
			List<TicketActivityBean> ticketActivitiesList) {
		this.ticketActivitiesList = ticketActivitiesList;
	}
	/**
	 * @return the ticketBean
	 */
	public TicketBean getTicketBean() {
		return ticketBean;
	}
	/**
	 * @param ticketBean the ticketBean to set
	 */
	public void setTicketBean(TicketBean ticketBean) {
		this.ticketBean = ticketBean;
	}
}
