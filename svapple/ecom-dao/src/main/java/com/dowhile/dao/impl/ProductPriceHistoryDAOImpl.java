/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.ProductPriceHistory;
import com.dowhile.dao.ProductPriceHistoryDAO;

/**
 * @author Hafiz Yameen Bashir
 *
 */
public class ProductPriceHistoryDAOImpl implements ProductPriceHistoryDAO{
	
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
	public ProductPriceHistory addProductPriceHistory(
			ProductPriceHistory productPriceHistory) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(productPriceHistory);
			return productPriceHistory;
		}
		catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public ProductPriceHistory updateProductPriceHistory(
			ProductPriceHistory productPriceHistory) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(productPriceHistory);
			return productPriceHistory;
		}
		catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteProductPriceHistory(
			ProductPriceHistory productPriceHistory) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(productPriceHistory);
			return true;
		}
		catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductPriceHistory> getAllProductPriceHistoryByCompanyIdOutletId(
			int companyId, int outletId) {
		// TODO Auto-generated method stub
		try{
			List<ProductPriceHistory> list = getSessionFactory().getCurrentSession()
					.createQuery("from ProductPriceHistory where COMPANY_ASSOCIATION_ID = ? AND OUTLET_ASSOCICATION_ID = ?")
					.setParameter(0, companyId)
					.setParameter(1, outletId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}
		catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductPriceHistory> getAllProductPriceHistoryByCompanyId(
			int companyId) {
		// TODO Auto-generated method stub
		try{
			List<ProductPriceHistory> list = getSessionFactory().getCurrentSession()
					.createQuery("from ProductPriceHistory where COMPANY_ASSOCIATION_ID = ?")
					.setParameter(0, companyId)
					.list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}
		catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductPriceHistory> getAllProductPriceHistoryByCompanyIdOutletIdProductId(
			int companyId, int outletId, int productId) {
		// TODO Auto-generated method stub
		try{
			List<ProductPriceHistory> list = getSessionFactory().getCurrentSession()
					.createQuery("from ProductPriceHistory where COMPANY_ASSOCIATION_ID = ? AND OUTLET_ASSOCICATION_ID = ? AND PRODUCT_ID = ?")
					.setParameter(0, companyId)
					.setParameter(1, outletId)
					.setParameter(2, productId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}
		catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
