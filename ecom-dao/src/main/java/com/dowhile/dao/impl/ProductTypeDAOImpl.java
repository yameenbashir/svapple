/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.dowhile.ProductType;
import com.dowhile.dao.ProductTypeDAO;

/**
 * @author Yameen Bashir
 *
 */
public class ProductTypeDAOImpl implements ProductTypeDAO{

	private SessionFactory sessionFactory;
	// private static Logger logger = Logger.getLogger(ProductTypeDAOImpl.class.getName());

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
	public ProductType addProductType(ProductType productType,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(productType);
			return productType;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public ProductType updateProductType(ProductType productType,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(productType);
			return productType;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteProductType(ProductType productType,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(productType);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public ProductType getProductTypeByProductTypeId(int productTypeId,int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<ProductType> list = getSessionFactory().getCurrentSession()
			.createQuery("from ProductType where PRODUCT_TYPE_ID=? AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, productTypeId)
			.setParameter(1, companyId).list();
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
	public List<ProductType> getAllProductTypes(int companyId) {
		// TODO Auto-generated method stub
		try{
			Criteria criteria = getSessionFactory().getCurrentSession()
					.createCriteria(ProductType.class);
			criteria.add(Restrictions.eq("company.companyId", companyId));
			List<ProductType> productTypeList = criteria.list();
			
			return productTypeList;
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductType> getTopProductTypes(int companyId, int count) {
		// TODO Auto-generated method stub
				try{
					Criteria criteria = getSessionFactory().getCurrentSession()
							.createCriteria(ProductType.class);
					criteria.add(Restrictions.eq("company.companyId", companyId)).setFirstResult(0).setMaxResults(count);
					List<ProductType> productTypeList = criteria.list();
					
					return productTypeList;
				}catch(HibernateException ex){
					ex.printStackTrace();// logger.error(ex.getMessage(),ex);
				}
				return null;
	}

}
