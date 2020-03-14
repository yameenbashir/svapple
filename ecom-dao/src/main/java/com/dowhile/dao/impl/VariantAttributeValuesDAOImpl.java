/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.dowhile.VariantAttributeValues;
import com.dowhile.dao.VariantAttributeValuesDAO;

/**
 * @author Yameen Bashir
 *
 */
public class VariantAttributeValuesDAOImpl implements VariantAttributeValuesDAO{

	private SessionFactory sessionFactory;
	// private static Logger logger = Logger.getLogger(VariantAttributeValuesDAOImpl.class.getName());

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
	public VariantAttributeValues addVariantAttributeValues(
			VariantAttributeValues variantAttributeValues,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(variantAttributeValues);
			return variantAttributeValues;
		}
		catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public VariantAttributeValues updateVariantAttributeValues(
			VariantAttributeValues variantAttributeValues,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(variantAttributeValues);
			return variantAttributeValues;
		}
		catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteVariantAttributeValues(
			VariantAttributeValues variantAttributeValues,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(variantAttributeValues);
			return true;
		}
		catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public VariantAttributeValues getVariantAttributeValuesByVariantAttributeValuesId(
			int variantAttributeValues,int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<VariantAttributeValues> list = getSessionFactory().getCurrentSession()
			.createQuery("from VariantAttributeValues where VARIANT_ATTRIBUTE_VALUES_ID=? AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, variantAttributeValues)
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
	public List<VariantAttributeValues> getAllVariantAttributeValues(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<VariantAttributeValues> list = getSessionFactory().getCurrentSession()
					.createQuery("from VariantAttributeValues where COMPANY_ASSOCIATION_ID=? GROUP BY PRODUCT_UUID,ATTRIBUTE_VALUE")
					.setParameter(0, companyId).list();
					if(list!=null&& list.size()>0){

						return list;
					}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<String> getAllVariantAttributeValuesAttributeAndProducttId(
			int attributeId, String productVariantId,int companyId) {
		try{
			Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(VariantAttributeValues.class);
			
			criteria.add(Restrictions.eq("variantAttribute.variantAttributeId", attributeId));
			criteria.add(Restrictions.eq("productUuid", productVariantId));
			criteria.add(Restrictions.eq("company.companyId", companyId));
			criteria.setProjection(Projections.distinct(Projections.property("attributeValue")));
			@SuppressWarnings("unchecked")
			List<String> list =  criteria.list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}
	public List<VariantAttributeValues> getAllVariantAttributeValues(
			int attributeId, String productVariantId,int companyId) {
		try{
//			Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(VariantAttributeValues.class);
//			criteria .setProjection(Projections.distinct(Projections.property("attributeValue")) );
//			criteria.add(Restrictions.eq("variantAttribute.variantAttributeId", attributeId));
//			criteria.add(Restrictions.eq("productUuid", productVariantId));
//			criteria.add(Restrictions.eq("company.companyId", companyId));
//			criteria.setProjection(Projections.distinct(Projections.property("attributeValue"))).setResultTransformer(Transformers.aliasToBean(VariantAttributeValues.class));
//			@SuppressWarnings("unchecked")
//			Criteria crit = getSessionFactory().getCurrentSession().createCriteria(VariantAttributeValues.class).setProjection(
//					
//					
//					Projections.distinct(Projections.projectionList()
//				    .add(Projections.property("attributeValue"), "attributeValue") ))
//				.setResultTransformer(Transformers.aliasToBean(VariantAttributeValues.class)); 
			
//			ProjectionList projList = Projections.projectionList();
//			projList.add(Projections.property("attributeValue"));
//			projList.add(Projections.property("productVariant.productVariantId"));
//			projList.add(Projections.property("variantAttribute.variantAttributeId"));
//			
//			
//			@SuppressWarnings("unchecked")
//			List<VariantAttributeValues> list =  criteria.list();
//			if(list!=null&& list.size()>0){
//
//				return list;
			@SuppressWarnings("unchecked")
			List<VariantAttributeValues> list = getSessionFactory().getCurrentSession()
					.createQuery("SELECT distinct attributeValue,variantAttribute.variantAttributeId from VariantAttributeValues WHERE COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, companyId).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
					if(list!=null&& list.size()>0){

						return list;
			 
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public void addVariantAttributeValuesList(
			List<VariantAttributeValues> variantAttributeValuesList) {
		// TODO Auto-generated method stub
		try{
			if(variantAttributeValuesList!=null){
				for(VariantAttributeValues variantAttributeValues:variantAttributeValuesList){
					getSessionFactory().getCurrentSession().save(variantAttributeValues);
				}
			}
			
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		
	}

}
