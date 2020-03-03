/**
 * 
 */
package com.dowhile.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.WebActivityDetail;
import com.dowhile.dao.WebActivityDetailDAO;

/**
 * @author Yameen Bashir
 *
 */
public class WebActivityDetailDAOImpl implements WebActivityDetailDAO{

	private SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(WebActivityDetailDAOImpl.class.getName());
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
	public WebActivityDetail addWebActivityDetail(
			WebActivityDetail webActivityDetail) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(webActivityDetail);
			return webActivityDetail;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
