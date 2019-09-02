/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.dowhile.Currency;
import com.dowhile.dao.CurrencyDAO;

/**
 * @author MHR
 *
 */
public class CurrencyDAOImpl implements CurrencyDAO {

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Currency> GetAllCurrencies() {
		try {
			List<Currency> list = getSessionFactory().getCurrentSession()
					.createQuery("From Currency WHERE ACTIVE_INDICATOR = 1")
					.list();
			if (list != null && list.size() > 0) {

				return list;
			}

		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Currency GetCurrencyById(int currencyId) {
		try{
			
			Query query= sessionFactory.getCurrentSession()
					.createQuery("from Currency where CURRENCY_ID =?")
			.setParameter(0, currencyId);
			Currency currency = (Currency) query.uniqueResult();
			if(currency!=null){

				return currency;
			}

		}catch(HibernateException ex){
			ex.printStackTrace();
		}


		return null;
	}
	

}
