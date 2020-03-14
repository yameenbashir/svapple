/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.Country;
import com.dowhile.dao.CountryDAO;

/**
 * @author Yameen Bashir
 *
 */
public class CountryDAOImpl implements CountryDAO{

	private SessionFactory sessionFactory;
	// private static Logger logger = Logger.getLogger(CountryDAOImpl.class.getName());

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
	public Country addCountry(Country country) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(country);
			return country;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public Country updateCountry(Country country) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(country);
			return country;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteCountry(Country country) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(country);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public Country getCountryByCountryId(int countryId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Country> list = getSessionFactory().getCurrentSession()
			.createQuery("from Country where COUNTRY_ID=?")
			.setParameter(0, countryId).list();
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
	public List<Country> GetAllCountry() {
		try {
			List<Country> list = getSessionFactory().getCurrentSession()
					.createQuery("From Country WHERE ACTIVE_INDICATOR = 1")
					.list();
			if (list != null && list.size() > 0) {

				return list;
			}

		} catch (HibernateException ex) {
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
