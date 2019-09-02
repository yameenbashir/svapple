package com.dowhile.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.ActivityDetail;
import com.dowhile.dao.ActivityDetailDAO;

/**
 * @author Yameen Bashir
 *
 */
public class ActivityDetailDAOImpl implements ActivityDetailDAO{

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
	public ActivityDetail addActivityDetail(ActivityDetail activityDetail, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(activityDetail);
			return activityDetail;

		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

}
