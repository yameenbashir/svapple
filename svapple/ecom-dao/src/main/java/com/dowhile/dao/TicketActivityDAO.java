/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.TicketActivity;

/**
 * @author Yameen
 *
 */
public interface TicketActivityDAO {
 
	int addTicketActivity(TicketActivity ticketActivity);
	List<TicketActivity> getTicketActivitiesByCompanyIdTicketId(int companyId,int ticketId);
}
