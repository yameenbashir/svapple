/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.ContactUs;
import com.dowhile.dao.ContactUsDAO;

/**
 * @author Yameen Bashir
 *
 */
public class ContactUsDAOImpl implements ContactUsDAO{

	private SessionFactory sessionFactory;private static Logger logger = Logger.getLogger(CashManagementController.class.getName());

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
	public ContactUs addContactUs(ContactUs contactUs) {
		// TODO Auto-generated method stub
		try{

			contactUs.setLastUpdated(new Date());
			contactUs.setCreatedDate(new Date());
			getSessionFactory().getCurrentSession().save(contactUs);

			return contactUs;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public ContactUs updateContactUs(ContactUs contactUs) {
		// TODO Auto-generated method stub
		try{

			contactUs.setLastUpdated(new Date());

			getSessionFactory().getCurrentSession().update(contactUs);
			return contactUs;
		}catch(Exception ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteContactUs(ContactUs contactUs) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(contactUs);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public ContactUs getContactUsByContactUsId(	int contactUsId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<ContactUs> list = getSessionFactory().getCurrentSession()
			.createQuery("from ContactUs where CONTACT_US_ID=?")
			.setParameter(0, contactUsId)
			.list();
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
	public List<ContactUs> getAllContactUs() {
		// TODO Auto-generated method stub
		try{
			List<ContactUs> contactUsList = getSessionFactory().getCurrentSession().createCriteria(ContactUs.class).list();
			return contactUsList;
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}
}
