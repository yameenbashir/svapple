/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.TicketActivity;
import com.dowhile.dao.TicketActivityDAO;
import com.dowhile.service.TicketActivityService;

/**
 * @author Yameen
 *
 */
@Transactional(readOnly = false)
public class TicketActivityServiceImpl implements TicketActivityService{

	private TicketActivityDAO ticketActivityDAO;

	/**
	 * @return the ticketActivityDAO
	 */
	public TicketActivityDAO getTicketActivityDAO() {
		return ticketActivityDAO;
	}

	/**
	 * @param ticketActivityDAO the ticketActivityDAO to set
	 */
	public void setTicketActivityDAO(TicketActivityDAO ticketActivityDAO) {
		this.ticketActivityDAO = ticketActivityDAO;
	}

	@Override
	public int addTicketActivity(TicketActivity ticketActivity) {
		// TODO Auto-generated method stub
		return getTicketActivityDAO().addTicketActivity(ticketActivity);
	}

	@Override
	public List<TicketActivity> getTicketActivitiesByCompanyIdTicketId(
			int companyId, int ticketId) {
		// TODO Auto-generated method stub
		return getTicketActivityDAO().getTicketActivitiesByCompanyIdTicketId(companyId, ticketId);
	}
}
