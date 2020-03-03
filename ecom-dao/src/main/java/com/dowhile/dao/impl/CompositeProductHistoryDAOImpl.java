/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.CompositeProductHistory;
import com.dowhile.dao.CompositeProductHistoryDAO;

/**
 * @author Hafiz Yameen Bashir
 *
 */
public class CompositeProductHistoryDAOImpl implements CompositeProductHistoryDAO{

	private SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(CompositeProductHistoryDAOImpl.class.getName());

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
	public CompositeProductHistory addCompositeProductHistory(
			CompositeProductHistory compositeProductHistory) {
		// TODO Auto-generated method stub
		try{

			getSessionFactory().getCurrentSession().save(compositeProductHistory);

			return compositeProductHistory;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public CompositeProductHistory updateCompositeProductHistory(
			CompositeProductHistory compositeProductHistory) {
		// TODO Auto-generated method stub
		try{

			getSessionFactory().getCurrentSession().update(compositeProductHistory);

			return compositeProductHistory;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteCompositeProductHistory(
			CompositeProductHistory compositeProductHistory) {
		// TODO Auto-generated method stub
		try{

			getSessionFactory().getCurrentSession().delete(compositeProductHistory);

			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public List<CompositeProductHistory> getAllCompositeProductHistoryByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<CompositeProductHistory> list = getSessionFactory().getCurrentSession()
			.createQuery("from CompositeProductHistory where COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, companyId)
			.list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}
	
}
