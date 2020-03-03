/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.ContactGroup;
import com.dowhile.dao.ContactGroupDAO;

/**
 * @author Zafar Shakeel
 *
 */
public class ContactGroupDAOImpl implements ContactGroupDAO{

	private SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(ContactGroupDAOImpl.class.getName());

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
	public ContactGroup addContactGroup(ContactGroup ContactGroup,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(ContactGroup);
			return ContactGroup;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public ContactGroup updateContactGroup(ContactGroup ContactGroup,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(ContactGroup);
			return ContactGroup;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteContactGroup(ContactGroup ContactGroup ,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(ContactGroup);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public ContactGroup getContactGroupByContactGroupId(int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<ContactGroup> list = getSessionFactory().getCurrentSession()
			.createQuery("from ContactGroup where COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, companyId).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ContactGroup> GetAllContactGroup(int companyId) {
		try {
			List<ContactGroup> list = getSessionFactory().getCurrentSession()
					.createQuery("From ContactGroup WHERE ACTIVE_INDICATOR = 1 AND COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, companyId).list();
			if (list != null && list.size() > 0) {

				return list;
			}

		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
