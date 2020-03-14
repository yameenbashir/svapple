/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.DeliveryMethod;
import com.dowhile.dao.DeliveryMethodDAO;

/**
 * @author Zafar Shakeel
 *
 */
public class DeliveryMethodDAOImpl implements DeliveryMethodDAO{

	private SessionFactory sessionFactory;
	// private static Logger logger = Logger.getLogger(DeliveryMethodDAOImpl.class.getName());

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
	public DeliveryMethod addDeliveryMethod(DeliveryMethod deliveryMethod, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(deliveryMethod);
			return deliveryMethod;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public DeliveryMethod updateDeliveryMethod(DeliveryMethod deliveryMethod, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(deliveryMethod);
			return deliveryMethod;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteDeliveryMethod(DeliveryMethod deliveryMethod, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(deliveryMethod);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public DeliveryMethod getDeliveryMethodByDeliveryMethodId(int deliveryMethodId, int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<DeliveryMethod> list = getSessionFactory().getCurrentSession()
			.createQuery("from DeliveryMethod where DELIVERY_METHOD_ID = ?")
			.setParameter(0, deliveryMethodId).list();
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
	public List<DeliveryMethod> getAllDeliveryMethods(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<DeliveryMethod> list = getSessionFactory().getCurrentSession()
			.createQuery("from DeliveryMethod").list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
