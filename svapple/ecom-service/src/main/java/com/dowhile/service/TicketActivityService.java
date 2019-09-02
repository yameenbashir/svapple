/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.TicketActivity;

/**
 * @author Yameen
 *
 */
public interface TicketActivityService {

	int addTicketActivity(TicketActivity ticketActivity);
	List<TicketActivity> getTicketActivitiesByCompanyIdTicketId(int companyId,int ticketId);
}
