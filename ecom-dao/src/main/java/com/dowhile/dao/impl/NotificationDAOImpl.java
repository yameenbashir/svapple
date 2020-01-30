/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.dowhile.Contact;
import com.dowhile.Notification;
import com.dowhile.StockOrder;
import com.dowhile.dao.NotificationDAO;

/**
 * @author Hafiz Bashir
 *
 */
public class NotificationDAOImpl implements NotificationDAO{

	private static final Object companyId = null;
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

	@Override
	public Notification getAllNotificationByID(int notificationId,int outletId) {
		// TODO Auto-generated method stubtr
		try{
			@SuppressWarnings("unchecked")
			
			Query query= sessionFactory.getCurrentSession()
			.createQuery("from Notification where NOTIFICATION_ID =? AND OUTLET_ID_TO =?")
	.setParameter(0, notificationId)
	.setParameter(1, outletId);
			Notification notification = (Notification) query.uniqueResult();
	if(notification!=null){

		return notification;
	}
			
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
		
	}

	@Override
	public boolean markAllAsReadByOutletIdCompanyId(int outletId,
			int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession()
			.createSQLQuery("update notification set MARK_AS_READ =:markAsRead"
					+ " where OUTLET_ID_TO =:outletId "
					+ "AND COMPANY_ASSOCIATION_ID =:companyId")
			
			.setParameter("markAsRead", true)
			.setParameter("outletId", outletId)
			.setParameter("companyId", companyId).executeUpdate();
			
			return true;
			
		}catch(HibernateException ex){
			ex.printStackTrace();
			
		}
			return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Notification> getAllUnReadNotificationsByOutletIdCompanyId(
			int outletId,int companyId) {
		try{
				
				List<Notification> list= sessionFactory.getCurrentSession()
				.createQuery("from Notification where OUTLET_ID_TO = ? AND MARK_AS_READ = ? AND COMPANY_ASSOCIATION_ID = ?")
		.setParameter(0, outletId)
		.setParameter(1, false)
		.setParameter(2, companyId).list();
		if(list!=null && list.size()>0){

			return list;
		}
				
			}catch(HibernateException ex){
				ex.printStackTrace();
			}
			return null;
	}
	


	@Override
	@SuppressWarnings("unchecked")
	public List<Notification> getAllReadedNotificationsByOutletIdCompanyId(int outletId, int companyId) {
		// TODO Auto-generated method stub
try{
			
			List<Notification> list= sessionFactory.getCurrentSession()
			.createQuery("from Notification where OUTLET_ID_TO = ? AND MARK_AS_READ = ? AND COMPANY_ASSOCIATION_ID = ?")
	.setParameter(0, outletId)
	.setParameter(1, true)
	.setParameter(2, companyId).list();
	if(list!=null && list.size()>0){

		return list;
	}
			
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Notification> getTenReadedNotificationsByOutletIdCompanyId(int outletId, int companyId) {
		// TODO Auto-generated method stub
		List<Notification> list = new ArrayList<>();
try{
			
			 list= sessionFactory.getCurrentSession()
			.createQuery("from Notification where OUTLET_ID_TO = ? AND MARK_AS_READ = ? AND COMPANY_ASSOCIATION_ID = ?  ORDER BY NOTIFICATION_ID DESC  LIMIT 10").setMaxResults(10)
	.setParameter(0, outletId)
	.setParameter(1, true)
	.setParameter(2, companyId).list();
	if(list!=null && list.size()>0){

		return list;
	}
			
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Notification> getAllReadedNotificationsByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		try{
			
			List<Notification> list= sessionFactory.getCurrentSession()
			.createQuery("from Notification where COMPANY_ASSOCIATION_ID = ? AND MARK_AS_READ = ?")
			.setParameter(0, companyId)
			.setParameter(1, true).list();
			if(list!=null && list.size()>0){

				return list;
			}
			
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Notification> getTenReadedNotificationsByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		List<Notification> list = new ArrayList<>();
		try{
			
			 list= sessionFactory.getCurrentSession()
			.createQuery("from Notification where COMPANY_ASSOCIATION_ID = ? AND MARK_AS_READ = ?  ORDER BY NOTIFICATION_ID DESC  LIMIT 10").setMaxResults(10)
			.setParameter(0, companyId)
			.setParameter(1, true).list();
			if(list!=null && list.size()>0){

				return list;
			}
			
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Notification> getAllUnReadedNotificationsByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<Notification> list = getSessionFactory().getCurrentSession()
			.createQuery("from Notification where COMPANY_ASSOCIATION_ID = ? AND MARK_AS_READ = ?" )
			.setParameter(0, companyId)
			.setParameter(1, false).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	
		
	}

