/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.SmsReport;
import com.dowhile.dao.SmsReportDAO;

/**
 * @author Hafiz Yameen Bashir
 *
 */
public class SmsReportDAOImpl implements SmsReportDAO{

	private SessionFactory sessionFactory;private static Logger logger = Logger.getLogger(CashManagementController.class.getName());

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
	public List<SmsReport> getAllSmsByCompanyIdOutletId(int companyId,
			int outletId) {
		try {
			@SuppressWarnings("unchecked")
			List<SmsReport> list = getSessionFactory()
					.getCurrentSession()
					.createQuery("from SmsReport WHERE COMPANY_ASSOCIATION_ID = ? AND OUTLET_ASSOCICATION_ID = ?")
					.setParameter(0, companyId)
					.setParameter(1, outletId)
					.list();
			return list;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
