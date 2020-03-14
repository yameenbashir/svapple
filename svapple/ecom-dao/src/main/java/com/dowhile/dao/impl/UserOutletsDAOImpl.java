/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.UserOutlets;
import com.dowhile.dao.UserOutletsDAO;

/**
 * @author Yameen Bashir
 *
 */
public class UserOutletsDAOImpl implements UserOutletsDAO{

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
	public UserOutlets addUserOutlets(UserOutlets userOutlets) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(userOutlets);
			return userOutlets;
		}
		catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public UserOutlets updateUserOutlets(UserOutlets userOutlets) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(userOutlets);
			return userOutlets;
		}
		catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteUserOutlets(UserOutlets userOutlets) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(userOutlets);
			return true;
		}
		catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public UserOutlets getUserDefaultOutlet( int userId,int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<UserOutlets> list = getSessionFactory().getCurrentSession()
			.createQuery("from UserOutlets where USER_ASSOCIATION_ID=? AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, userId)
			.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
