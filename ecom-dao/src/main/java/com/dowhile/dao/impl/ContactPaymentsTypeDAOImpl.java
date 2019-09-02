/**
 * 
 */
package com.dowhile.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.dowhile.ContactPaymentsType;
import com.dowhile.dao.ContactPaymentsTypeDAO;

/**
 * @author T430s
 *
 */
public class ContactPaymentsTypeDAOImpl implements ContactPaymentsTypeDAO{

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
	public ContactPaymentsType getContactPaymentsTypeByID(int ContactPaymentsType,int companyId) {
		try{
			
			Query query= sessionFactory.getCurrentSession()
					.createQuery("from ContactPaymentsType where CONTACT_PAYMENT_TYPE_ID =?")
			.setParameter(0, ContactPaymentsType);
			ContactPaymentsType ContactPayments = (ContactPaymentsType) query.uniqueResult();
			if(ContactPayments!=null){

				return ContactPayments;
			}

		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}
	

	
	
}
