/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.PaymentType;
import com.dowhile.dao.PaymentTypeDAO;

/**
 * @author Yameen Bashir
 *
 */
public class PaymentTypeDAOImpl implements PaymentTypeDAO{

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
	public PaymentType addPaymentType(PaymentType paymentType, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(paymentType);
			return paymentType;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public PaymentType updatePaymentType(PaymentType paymentType, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(paymentType);
			return paymentType;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deletePaymentType(PaymentType paymentType, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(paymentType);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaymentType getPaymentTypeByPaymentTypeId(int paymentTypeId, int companyId) {
		// TODO Auto-generated method stub
		try{
			List<PaymentType> list = getSessionFactory().getCurrentSession()
			.createQuery("from PaymentType where PAYMENT_TYPE_ID = ?")
			.setParameter(0, paymentTypeId).list();
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
	public List<PaymentType> getAllPaymentTypes(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<PaymentType> list = getSessionFactory().getCurrentSession()
			.createQuery("from PaymentType")
			.list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
