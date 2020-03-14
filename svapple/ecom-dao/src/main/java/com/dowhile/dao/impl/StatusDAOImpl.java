/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.Status;
import com.dowhile.dao.StatusDAO;

/**
 * @author Zafar Shakeel
 *
 */
public class StatusDAOImpl implements StatusDAO{

	private SessionFactory sessionFactory;// private static Logger logger = Logger.getLogger(CashManagementController.class.getName());

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
	public Status addStatus(Status status) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(status);
			return status;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public Status updateStatus(Status status) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(status);
			return status;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteStatus(Status status) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(status);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public Status getStatusByStatusId(int statusId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Status> list = getSessionFactory().getCurrentSession()
			.createQuery("from Status where STATUS_ID=?")
			.setParameter(0, statusId).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Status> getAllStatus() {
		// TODO Auto-generated method stub
		try{
			List<Status> statusList = getSessionFactory().getCurrentSession().createCriteria(Status.class).list();
			return statusList;
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
