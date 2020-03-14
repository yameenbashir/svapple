/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.Tag;
import com.dowhile.dao.TagDAO;

/**
 * @author Yameen Bashir
 *
 */
public class TagDAOImpl implements TagDAO{

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
	public Tag addTag(Tag tag,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(tag);
			return tag;
		}
		catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public Tag updateTag(Tag tag,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(tag);
			return tag;
		}
		catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteTag(Tag tag,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(tag);
			return true;
		}
		catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public Tag getTagByTagId(int tagId,int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Tag> list = getSessionFactory().getCurrentSession()
			.createQuery("from Tag where TAG_ID=? AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, tagId)
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
	public List<Tag> getAllTags(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<Tag> list = getSessionFactory().getCurrentSession()
					.createQuery("from Tag where COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, companyId).list();
					if(list!=null&& list.size()>0){

						return list;
					}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
