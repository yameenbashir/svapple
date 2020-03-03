/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.dowhile.TimeZone;
import com.dowhile.dao.TimeZoneDAO;

/**
 * @author MHR
 *
 */
public class TimeZoneDAOImpl implements TimeZoneDAO {

	private SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(TimeZoneDAOImpl.class.getName());

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

	@SuppressWarnings("unchecked")
	@Override
	public List<TimeZone> GetAllTimeZones() {
		try {
			List<TimeZone> list = getSessionFactory().getCurrentSession()
					.createQuery("From TimeZone WHERE ACTIVE_INDICATOR = 1")
					.list();
			if (list != null && list.size() > 0) {

				return list;
			}

		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public TimeZone getTimeZoneByTimeZoneId(int timeZoneId) {
		try{
			Query query= sessionFactory.getCurrentSession()
					.createQuery("from TimeZone where TIME_ZONE_ID =?")
			.setParameter(0, timeZoneId);
			TimeZone timeZone = (TimeZone) query.uniqueResult();
			if(timeZone!=null)
			{

				return timeZone;
			}
			}catch(HibernateException ex){
				ex.printStackTrace();logger.error(ex.getMessage(),ex);
			}


			return null;
		}
}
