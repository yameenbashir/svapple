/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Ticket;
import com.dowhile.dao.TicketDAO;
import com.dowhile.service.TicketService;

/**
 * @author Yameen
 *
 */
@Transactional(readOnly = false)
public class TicketServiceImpl implements TicketService{

	private TicketDAO ticketDAO;

	/**
	 * @return the ticketDAO
	 */
	public TicketDAO getTicketDAO() {
		return ticketDAO;
	}

	/**
	 * @param ticketDAO the ticketDAO to set
	 */
	public void setTicketDAO(TicketDAO ticketDAO) {
		this.ticketDAO = ticketDAO;
	}

	@Override
	public Ticket addTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		return getTicketDAO().addTicket(ticket);
	}

	@Override
	public List<Ticket> getAllTicketsByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		return getTicketDAO().getAllTicketsByCompanyId(companyId);
	}

	@Override
	public Ticket getTicketByTicketId(int ticketId) {
		// TODO Auto-generated method stub
		return getTicketDAO().getTicketByTicketId(ticketId);
	}

	@Override
	public Ticket updateTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		return getTicketDAO().updateTicket(ticket);
	}
}
