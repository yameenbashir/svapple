/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.VariantAttribute;
import com.dowhile.dao.VariantAttributeDAO;

/**
 * @author Yameen Bashir
 *
 */
public class VariantAttributeDAOImpl implements VariantAttributeDAO{

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
	public VariantAttribute addVariantAttribute(
			VariantAttribute variantAttribute,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(variantAttribute);
			return variantAttribute;
		}
		catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public VariantAttribute updateVariantAttribute(
			VariantAttribute variantAttribute,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(variantAttribute);
			return variantAttribute;
		}
		catch(HibernateException ex){
			ex.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean deleteVariantAttribute(VariantAttribute variantAttribute,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(variantAttribute);
			return true;
		}
		catch(HibernateException ex){
			ex.printStackTrace();
		}

		return false;
	}

	@Override
	public VariantAttribute getVariantAttributeByVariantAttributeId(
			int variantAttributeId,int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<VariantAttribute> list = getSessionFactory().getCurrentSession()
			.createQuery("from VariantAttribute where VARIANT_ATTRIBUTE_ID=? AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, variantAttributeId)
			.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VariantAttribute> getAllVariantAttributes(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<VariantAttribute> list = getSessionFactory().getCurrentSession()
					.createQuery("from VariantAttribute where COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, companyId).list();
					if(list!=null&& list.size()>0){

						return list;
					}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

}
