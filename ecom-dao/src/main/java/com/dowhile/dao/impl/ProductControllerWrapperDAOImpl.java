/**
 * 
 */
package com.dowhile.dao.impl;

import org.hibernate.SessionFactory;

import com.dowhile.dao.ProductControllerWrapperDAO;
import com.dowhile.wrapper.ProductControllerWrapper;

/**
 * @author HafizYameenBashir
 *
 */
public class ProductControllerWrapperDAOImpl implements ProductControllerWrapperDAO{

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
	public ProductControllerWrapper getProductControllerWrapperDataByOutletIdCompanyId(
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		return null;
	}
}
