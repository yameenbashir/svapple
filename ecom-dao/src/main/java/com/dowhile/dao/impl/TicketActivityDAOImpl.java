/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.TicketActivity;
import com.dowhile.dao.TicketActivityDAO;

/**
 * @author Yameen
 *
 */
public class TicketActivityDAOImpl implements TicketActivityDAO{

	private SessionFactory sessionFactory;

	/**
	 * Get Hibernate Session Factory
	 * 
	 * @return SessionFactory - Hibernate Session Factory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Set Hibernate Session Factory
	 * 
	 * @param SessionFactory
	 *            - Hibernate Session Factory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public int addTicketActivity(TicketActivity ticketActivity) {
		// TODO Auto-generated method stub
		try{

			getSessionFactory().getCurrentSession().save(ticketActivity);

			return ticketActivity.getTicketActivityId();

		}catch(HibernateException ex){
			ex.printStackTrace();
		}

		return -1;
	}

	@Override
	public List<TicketActivity> getTicketActivitiesByCompanyIdTicketId(
			int companyId, int ticketId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<TicketActivity> list = getSessionFactory().getCurrentSession()
			.createQuery("from TicketActivity where TICKET_ASSOCIATION_ID=? order by CREATED_DATE DESC")
			.setParameter(0, ticketId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}
}
