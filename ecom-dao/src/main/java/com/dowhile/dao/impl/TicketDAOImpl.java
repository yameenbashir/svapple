/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.Ticket;
import com.dowhile.dao.TicketDAO;

/**
 * @author Yameen
 *
 */
public class TicketDAOImpl implements TicketDAO{

	private SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(TicketDAOImpl.class.getName());

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
	public Ticket addTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		try{

			getSessionFactory().getCurrentSession().save(ticket);

			return ticket;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}

		return null;
	}

	@Override
	public List<Ticket> getAllTicketsByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Ticket> list = getSessionFactory().getCurrentSession()
			.createQuery("from Ticket where COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public Ticket getTicketByTicketId(int ticketId) {
		// TODO Auto-generated method stub
		
		try{
			@SuppressWarnings("unchecked")
			List<Ticket> list = getSessionFactory().getCurrentSession()
			.createQuery("from Ticket where TICKET_ID=?")
			.setParameter(0, ticketId).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public Ticket updateTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		try{

			getSessionFactory().getCurrentSession().update(ticket);

			return ticket;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}

		return null;
	}
}
