/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.dowhile.ContactPayments;
import com.dowhile.dao.ContactPaymentsDAO;

/**
 * @author T430s
 *
 */
public class ContactPaymentsDAOImpl implements ContactPaymentsDAO{

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
	public ContactPayments addContactPayments(ContactPayments ContactPayments,int companyId) {
		try{
		Session session =	getSessionFactory().getCurrentSession();
		
		session.save(ContactPayments);
		
			return ContactPayments;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public ContactPayments updateContactPayments(ContactPayments ContactPayments,int companyId) {
		try{
			getSessionFactory().getCurrentSession().update(ContactPayments);
			return ContactPayments;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteContactPayments(ContactPayments ContactPayments,int companyId) {
		try{
			getSessionFactory().getCurrentSession().delete(ContactPayments);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public ContactPayments getContactPaymentsByID(int ContactPaymentsID,int companyId) {
		try{
			
			Query query= sessionFactory.getCurrentSession()
					.createQuery("from ContactPayments where CONTACT_PAYMENT_ID =? AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, ContactPaymentsID)
			.setParameter(1, companyId);
			ContactPayments ContactPayments = (ContactPayments) query.uniqueResult();
			if(ContactPayments!=null){

				return ContactPayments;
			}

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}

		return null;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<ContactPayments> getContactPaymentsByContactId(int ContactID,int companyId) {
		// TODO Auto-generated method stub
		List<ContactPayments> list = new ArrayList<>(); 
		try{
			list = getSessionFactory().getCurrentSession()
					.createQuery("from ContactPayments where CONTACT_ASSOCICATION_ID =? AND COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, ContactID)
					.setParameter(1, companyId).list();
			if (list != null && list.size() > 0) {

				return list;
			}

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}


		return list;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public List<ContactPayments> getAllContactPayments(int companyId) {
		try{
			Criteria criteria = getSessionFactory().getCurrentSession()
					.createCriteria(ContactPayments.class);
			criteria.add(Restrictions.eq("company.companyId", companyId));
			List<ContactPayments> ContactPayments = criteria.list();
			return ContactPayments;
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContactPayments> getSupplierPaymentsByContactId(int ContactID,
			int companyId) {
		// TODO Auto-generated method stub
				List<ContactPayments> list = new ArrayList<>(); 
				try{
					list = getSessionFactory().getCurrentSession()
							.createQuery("from ContactPayments where CONTACT_ASSOCICATION_ID =? AND COMPANY_ASSOCIATION_ID=? AND CONTACT_PAYMENT_TYPE_ASSOCICATION_ID=2")
							.setParameter(0, ContactID)
							.setParameter(1, companyId).list();
					if (list != null && list.size() > 0) {

						return list;
					}

				}catch(HibernateException ex){
					ex.printStackTrace();// logger.error(ex.getMessage(),ex);
				}


				return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContactPayments> getCustonerPaymentsByContactId(int ContactID,
			int companyId) {
		// TODO Auto-generated method stub
		List<ContactPayments> list = new ArrayList<>(); 
		try{
			list = getSessionFactory().getCurrentSession()
					.createQuery("from ContactPayments where CONTACT_ASSOCICATION_ID =? AND COMPANY_ASSOCIATION_ID=? AND CONTACT_PAYMENT_TYPE_ASSOCICATION_ID=1")
					.setParameter(0, ContactID)
					.setParameter(1, companyId).list();
			if (list != null && list.size() > 0) {

				return list;
			}

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}


		return list;
	}

	
}
