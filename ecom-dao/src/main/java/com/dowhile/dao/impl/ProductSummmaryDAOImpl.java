/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.ProductSummmary;
import com.dowhile.dao.ProductSummmaryDAO;

/**
 * @author Yameen Bashir
 *
 */
public class ProductSummmaryDAOImpl implements ProductSummmaryDAO{

	private SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(ProductSummmaryDAOImpl.class.getName());

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
	public List<ProductSummmary> getAllProductSummmaryByCompanyIdOutletId(int companyId, int outletId) {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("unchecked")
			List<ProductSummmary> list = getSessionFactory()
					.getCurrentSession()
					.createQuery("from ProductSummmary WHERE COMPANY_ASSOCIATION_ID = ? AND OUTLET_ASSOCICATION_ID = ?")
					.setParameter(0, companyId)
					.setParameter(1, outletId)
					.list();
			return list;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<ProductSummmary> getTenNewProductSummmaryByCompanyIdOutletId(
			int companyId, int outletId) {
		try {
			@SuppressWarnings("unchecked")
			List<ProductSummmary> list = getSessionFactory()
					.getCurrentSession()
					//.createQuery("from ProductSummmary WHERE OUTLET_ASSOCICATION_ID = ? ORDER BY id DESC LIMIT 10")
					.createQuery("FROM ProductSummmary WHERE COMPANY_ASSOCIATION_ID = ? AND OUTLET_ASSOCICATION_ID = ? ORDER BY id DESC LIMIT 10 ").setMaxResults(10)
					.setParameter(0, companyId)
					.setParameter(1, outletId)
					.list();
			return list;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<ProductSummmary> getAllProductSummmaryByCompanyId(int companyId) {
		try {
			@SuppressWarnings("unchecked")
			List<ProductSummmary> list = getSessionFactory()
					.getCurrentSession()
					.createQuery("from ProductSummmary WHERE COMPANY_ASSOCIATION_ID = ?")
					.setParameter(0, companyId)
					.list();
			return list;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
