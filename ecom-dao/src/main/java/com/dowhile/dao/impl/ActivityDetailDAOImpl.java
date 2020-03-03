package com.dowhile.dao.impl;

import org.apache.log4j.Logger;
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
	private static Logger logger = Logger.getLogger(ActivityDetailDAOImpl.class.getName());

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
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
