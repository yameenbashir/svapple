/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.OrderMain;
import com.dowhile.dao.OrderMainDAO;

/**
 * @author Yameen Bashir
 *
 */
public class OrderMainDAOImpl implements OrderMainDAO{

	private SessionFactory sessionFactory;
	// private static Logger logger = Logger.getLogger(OrderMainDAOImpl.class.getName());

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
	public OrderMain addOrderMain(OrderMain orderMain, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(orderMain);
			return orderMain;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public OrderMain updateOrderMain(OrderMain orderMain, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(orderMain);
			return orderMain;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteOrderMain(OrderMain orderMain, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(orderMain);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public OrderMain getOrderMainByOrderMainId(int orderMainId, int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<OrderMain> list = getSessionFactory().getCurrentSession()
			.createQuery("from OrderMain where ORDER_MAIN_ID = ? and COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, orderMainId).
			setParameter(1, companyId).list();
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
	public List<OrderMain> getAllOrderMains(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<OrderMain> list = getSessionFactory().getCurrentSession()
			.createQuery("from OrderMain where COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
