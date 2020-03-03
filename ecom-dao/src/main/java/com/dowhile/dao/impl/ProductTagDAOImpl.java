/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.ProductTag;
import com.dowhile.dao.ProductTagDAO;

/**
 * @author Yameen Bashir
 *
 */
public class ProductTagDAOImpl implements ProductTagDAO{

	private SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(ProductTagDAOImpl.class.getName());

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
	public ProductTag addProductTag(ProductTag productTag ,  int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(productTag);
			return productTag;
		}
		catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public ProductTag updateProductTag(ProductTag productTag ,  int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(productTag);
			return productTag;
		}
		catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteProductTag(ProductTag productTag ,  int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(productTag);
			return true;
		}
		catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public ProductTag getProductTagByProductTagIdProductIdCompanyId(int productTagId ,  int productId, int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<ProductTag> list = getSessionFactory().getCurrentSession()
			.createQuery("from ProductTag where PRODUCT_TAG_ID=? AND PRODUCT_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, productTagId)
			.setParameter(1, productId)
			.setParameter(2, companyId).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public int getCountOfProductTagsByTagId(int tagId ,  int companyId) {
		// TODO Auto-generated method stub
		try{
			int count = ((Long)getSessionFactory().getCurrentSession().createQuery("select count(*) from ProductTag "
					+ "where  COMPANY_ASSOCIATION_ID = "+companyId+" AND TAG_ASSOCICATION_ID = "+tagId+"").uniqueResult()).intValue();
			return count;
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return 0;
	}

	@Override
	public List<ProductTag> getAllProductTagsByProductId(int productId,int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<ProductTag> list = getSessionFactory().getCurrentSession()
			.createQuery("from ProductTag where PRODUCT_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, productId)
			.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<ProductTag> getAllProductTagsByCompanyId(int companyId) {
		try{
			@SuppressWarnings("unchecked")
			List<ProductTag> list = getSessionFactory().getCurrentSession()
			.createQuery("from ProductTag where COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
