/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.dowhile.Contact;
import com.dowhile.Product;
import com.dowhile.dao.ContactDAO;

/**
 * @author T430s
 *
 */
public class ContactDAOImpl implements ContactDAO{

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
	public Contact addContact(Contact Contact,int companyId) {
		try{
		Session session =	getSessionFactory().getCurrentSession();
		
		session.save(Contact);
		
			return Contact;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public Contact updateContact(Contact Contact,int companyId) {
		try{
			getSessionFactory().getCurrentSession().update(Contact);
			return Contact;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteContact(Contact Contact,int companyId) {
		try{
			getSessionFactory().getCurrentSession().delete(Contact);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public Contact getContactByID(int ContactID,int companyId) {
		try{
			
			Query query= sessionFactory.getCurrentSession()
					.createQuery("from Contact where CONTACT_ID =? AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, ContactID)
			.setParameter(1, companyId);
			Contact Contact = (Contact) query.uniqueResult();
			if(Contact!=null){

				return Contact;
			}

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}


		return null;
	}

	@Override
	public Contact getContactByContactOutletID(int OutletID,int companyId) {
		try{
			
			Query query= sessionFactory.getCurrentSession()
					.createQuery("from Contact where CONTACT_OUTLET_ID =? AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, OutletID)
			.setParameter(1, companyId);
			Contact Contact = (Contact) query.uniqueResult();
			if(Contact!=null){

				return Contact;
			}

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}


		return null;
	}

	
	@Override
	public int getOutletId(int ContactID,int companyId) {
		try{
			
			Query query= sessionFactory.getCurrentSession()
					.createQuery("from Contact where CONTACT_ID =? AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, ContactID)
			.setParameter(1, companyId);
			Contact Contact = (Contact) query.uniqueResult();
			if(Contact!=null){

				return Contact.getContactOutletId();
			}

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return -1;
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Contact> getAllContacts(int companyId) {
		try{
			Criteria criteria = getSessionFactory().getCurrentSession()
					.createCriteria(Contact.class);
			criteria.add(Restrictions.eq("company.companyId", companyId));
			criteria.add(Restrictions.eq("contactOutletId", null));
			List<Contact> Contact = criteria.list();
			return Contact;
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<Product> getAllProductsByContactID(int ContactID,int companyId) {
		try{
			@SuppressWarnings("unchecked")
			List<Product> list = getSessionFactory().getCurrentSession()
			.createQuery("from Product where CONTACT_ASSOCICATION_ID=? AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, ContactID)
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
	public List<Contact> getContactsByOutletID(int outletID, int companyId) {
		try{
			@SuppressWarnings("unchecked")
			List<Contact> list = getSessionFactory().getCurrentSession()
			.createQuery("from Contact where OUTLET_ASSOCIATION_ID=? AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, outletID)
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
	public List<Contact> getcustomerByOutletID(int outletID, int companyId) {
		try{
			@SuppressWarnings("unchecked")
			List<Contact> list = getSessionFactory().getCurrentSession()
			.createQuery("from Contact where OUTLET_ASSOCIATION_ID=? AND COMPANY_ASSOCIATION_ID=? AND CONTACT_TYPE='CUSTOMER'")
			.setParameter(0, outletID)
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
	public boolean activeInactiveAllContactsByCompanyIdOutletIdUserIdContactTypeIsActive(
			int companyId,int outletId, int userId, String contactType,boolean isActive) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession()
			.createSQLQuery("update Contact set ACTIVE_INDICATOR=:isActive , UPDATED_BY =:updatedBy , LAST_UPDATED =:lastUpdated"
					+ " where COMPANY_ASSOCIATION_ID =:companyId "
					+ "AND OUTLET_ASSOCIATION_ID =:outletId"
					+ " AND CONTACT_TYPE =:contactType")
					.setParameter("isActive", isActive)
					.setParameter("updatedBy", userId)
					.setParameter("lastUpdated", new Date())
					.setParameter("companyId", companyId)
					.setParameter("outletId", outletId)
					.setParameter("contactType", contactType)
					.executeUpdate();
			return true;
			
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return false;
	}

}
