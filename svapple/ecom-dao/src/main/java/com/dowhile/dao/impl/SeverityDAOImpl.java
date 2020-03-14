package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.Severity;
import com.dowhile.dao.SeverityDAO;
/**
 * @author Yameen Bashir
 *
 */
public class SeverityDAOImpl implements SeverityDAO{

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
	public Severity getSeverityBySeverityId(int severityId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Severity> list = getSessionFactory().getCurrentSession()
			.createQuery("from Severity where SEVERITY_ID=?")
			.setParameter(0, severityId).list();
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
	public List<Severity> getAllSeverities() {
		// TODO Auto-generated method stub
		try{
			List<Severity> severityList = getSessionFactory().getCurrentSession().createCriteria(Severity.class).list();
			return severityList;
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}
}
