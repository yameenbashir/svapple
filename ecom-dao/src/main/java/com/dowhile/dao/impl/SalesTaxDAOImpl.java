/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.SalesTax;
import com.dowhile.dao.SalesTaxDAO;

/**
 * @author Yameen Bashir
 *
 */
public class SalesTaxDAOImpl implements SalesTaxDAO{
	
	private SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(SalesTaxDAOImpl.class.getName());

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
	public SalesTax addSalesTax(SalesTax salesTax,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(salesTax);
			return salesTax;
		}
		catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		
		return null;
	}

	@Override
	public SalesTax updateSalesTax(SalesTax salesTax,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(salesTax);
			return salesTax;
		}
		catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		
		return null;
	}

	@Override
	public boolean deleteSalesTax(SalesTax salesTax,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(salesTax);
			return true;
		}
		catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		
		return false;
	}

	@Override
	public SalesTax getSalesTaxBySalesTaxId(int salesTaxId,int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<SalesTax> list = getSessionFactory().getCurrentSession()
			.createQuery("from SalesTax where SALES_TAX_ID=? AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, salesTaxId)
			.setParameter(1, companyId).list();
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
	public List<SalesTax> GetAllSalesTax(int companyId) {
		try {
			List<SalesTax> list = getSessionFactory().getCurrentSession()
					.createQuery("From SalesTax WHERE ACTIVE_INDICATOR = 1 AND COMPANY_ASSOCIATION_ID=?")
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
