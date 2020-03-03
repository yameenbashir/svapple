/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.ContactsSummmary;
import com.dowhile.dao.ContactsSummmaryDAO;

/**
 * @author Yameen Bashir
 *
 */
public class ContactsSummmaryDAOImpl implements ContactsSummmaryDAO{

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
	public List<ContactsSummmary> getAllContactsSummmaryByCompanyOutletId(int companyId, int outletId) {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("unchecked")
			List<ContactsSummmary> list = getSessionFactory()
					.getCurrentSession()
					.createQuery("from ContactsSummmary WHERE OUTLET_ASSOCIATION_ID = ? AND COMPANY_ASSOCIATION_ID = ?")
					.setParameter(0, outletId)
					.setParameter(1, companyId)
					.list();
			return list;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<ContactsSummmary> getAllContactsSummmaryByCompanyId(
			int companyId) {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("unchecked")
			List<ContactsSummmary> list = getSessionFactory()
					.getCurrentSession()
					.createQuery("from ContactsSummmary WHERE COMPANY_ASSOCIATION_ID = ?")
					.setParameter(0, companyId)
					
					.list();
			return list;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<ContactsSummmary> getActiveContactsSummmaryByCompanyId(
			int companyId) {
		// TODO Auto-generated method stub
				try {
					@SuppressWarnings("unchecked")
					List<ContactsSummmary> list = getSessionFactory()
							.getCurrentSession()
							.createQuery("from ContactsSummmary WHERE COMPANY_ASSOCIATION_ID = ? AND ACTIVE_INDICATOR = 1")
							.setParameter(0, companyId)
							
							.list();
					return list;
				} catch (HibernateException ex) {
					ex.printStackTrace();logger.error(ex.getMessage(),ex);
				}
				return null;
	}

	@Override
	public List<ContactsSummmary> getActiveContactsSummmaryByCompanyOutletId(
			int companyId, int outletId) {
		try {
			@SuppressWarnings("unchecked")
			List<ContactsSummmary> list = getSessionFactory()
					.getCurrentSession()
					.createQuery("from ContactsSummmary WHERE OUTLET_ASSOCIATION_ID = ? AND COMPANY_ASSOCIATION_ID = ? AND ACTIVE_INDICATOR = 1")
					.setParameter(0, outletId)
					.setParameter(1, companyId)
					.list();
			return list;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContactsSummmary> getAllContactsSummmaryByCompanyIdOutletIdConctactType(
			int companyId, int outletId, String contactType) {
		try {
			
			List<ContactsSummmary> list = getSessionFactory()
					.getCurrentSession()
					.createQuery("from ContactsSummmary WHERE COMPANY_ASSOCIATION_ID = ? AND OUTLET_ASSOCIATION_ID = ? AND CONTACT_TYPE = ?")
					.setParameter(0, companyId)
					.setParameter(1, outletId)
					.setParameter(2, contactType)
					.list();
			return list;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContactsSummmary> getTenNewContactsSummmaryByCompanyIdOutletIdConctactType(
			int companyId, int outletId, String contactType) {
try {
			
			List<ContactsSummmary> list = getSessionFactory()
					.getCurrentSession()
					.createQuery("from ContactsSummmary WHERE COMPANY_ASSOCIATION_ID = ? AND OUTLET_ASSOCIATION_ID = ? AND CONTACT_TYPE = ? ORDER BY CONTACT_ID DESC LIMIT 10 ").setMaxResults(10)
					.setParameter(0, companyId)
					.setParameter(1, outletId)
					.setParameter(2, contactType)
					.list();
			return list;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
