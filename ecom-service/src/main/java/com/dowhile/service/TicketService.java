/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.Ticket;

/**
 * @author Yameen
 *
 */
public interface TicketService {

	Ticket addTicket(Ticket ticket);
	Ticket updateTicket(Ticket ticket);
	List<Ticket> getAllTicketsByCompanyId(int companyId);
	Ticket getTicketByTicketId(int ticketId);
}
