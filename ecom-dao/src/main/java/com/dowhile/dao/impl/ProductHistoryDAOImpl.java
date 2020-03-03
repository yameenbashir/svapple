/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.ProductHistory;
import com.dowhile.dao.ProductHistoryDAO;

/**
 * @author Yameen Bashir
 *
 */
public class ProductHistoryDAOImpl implements ProductHistoryDAO{

	private SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(ProductHistoryDAOImpl.class.getName());

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
	public ProductHistory addProductHistory(ProductHistory productHistory,  int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(productHistory);
			return productHistory;
		}
		catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<ProductHistory> getProductHistoryByProductIdOutletId(
			int productId, int outletId ,  int companyId) {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("unchecked")
			List<ProductHistory> list = getSessionFactory()
					.getCurrentSession()
					.createQuery("from ProductHistory where PRODUCT_ASSOCICATION_ID=? AND OUTLET_ASSOCICATION_ID=? Order by ACTION_DATE Desc")
					.setParameter(0, productId)
					.setParameter(1, outletId).list();
			return list;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<ProductHistory> getProductHistoryByUuid(String uUid,  int companyId) {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("unchecked")
			List<ProductHistory> list = getSessionFactory()
					.getCurrentSession()
					.createQuery("from ProductHistory where PRODUCT_HISTORY_UUID=? AND COMPANY_ASSOCIATION_ID = ? Order by ACTION_DATE Desc")
					.setParameter(0, uUid)
					.setParameter(1, companyId).list();
			return list;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<ProductHistory> getProductHistoryByProductId(int productId ,  int companyId) {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("unchecked")
			List<ProductHistory> list = getSessionFactory()
					.getCurrentSession()
					.createQuery("from ProductHistory where PRODUCT_ASSOCICATION_ID=?  AND COMPANY_ASSOCIATION_ID = ? Order by ACTION_DATE Desc")
					.setParameter(0, productId).setParameter(1, companyId).list();
			return list;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
