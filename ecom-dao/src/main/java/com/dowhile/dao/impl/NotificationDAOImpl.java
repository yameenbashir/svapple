/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.Notification;
import com.dowhile.dao.NotificationDAO;

/**
 * @author Hafiz Bashir
 *
 */
public class NotificationDAOImpl implements NotificationDAO{

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
	public Notification addNotification(Notification notification) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(notification);
			return notification;

		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Notification updateNotification(Notification notification) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(notification);
			return notification;

		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteNotification(Notification notification) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(notification);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Notification> getAllNotificationsByoutletIdToCompanyId(
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Notification> list = getSessionFactory().getCurrentSession()
			.createQuery("from Notification where OUTLET_ID_TO = ? AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, outletId)
			.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Notification> getAllNotificationsByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Notification> list = getSessionFactory().getCurrentSession()
			.createQuery("from Notification where COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Notification> getAllNotificationsByoutletIdFromCompanyId(
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Notification> list = getSessionFactory().getCurrentSession()
			.createQuery("from Notification where OUTLET_ID_FROM = ? AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, outletId)
			.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

}
