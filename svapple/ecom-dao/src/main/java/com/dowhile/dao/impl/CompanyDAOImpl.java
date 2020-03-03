/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.dowhile.Company;
import com.dowhile.dao.CompanyDAO;

/**
 * @author Yameen Bashir
 *
 */
public class CompanyDAOImpl implements CompanyDAO{
	
	
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
	public Company addCompany(Company company) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(company);
			return company;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public Company updateCompany(Company company) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(company);
			return company;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteCompany(Company company) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(company);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Company> getCompanies() {
		// TODO Auto-generated method stub

		try{
			List<Company> companies = getSessionFactory().getCurrentSession().createCriteria(Company.class).list();
			return companies;
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}
	
	@Override
	public Company getCompanyDetailsByCompanyID(int companyID) {
		// TODO Auto-generated method stub
		try{
				
			Query query= sessionFactory.getCurrentSession()
					.createQuery("from Company where COMPANY_ID =?")
			.setParameter(0, companyID);
			Company company = (Company) query.uniqueResult();
			if(company!=null){

				return company;
			}

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}


		return null;
	}
	
	

}
