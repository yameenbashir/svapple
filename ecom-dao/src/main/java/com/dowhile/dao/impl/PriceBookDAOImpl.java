/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.PriceBook;
import com.dowhile.dao.PriceBookDAO;

/**
 * @author Yameen Bashir
 *
 */
public class PriceBookDAOImpl implements PriceBookDAO{

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
	public PriceBook addPriceBook(PriceBook priceBook) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(priceBook);
			return priceBook;

		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public PriceBook updatePriceBook(PriceBook priceBook) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(priceBook);
			return priceBook;

		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deletePriceBook(PriceBook priceBook) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(priceBook);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PriceBook getPriceBookByPriceBookIdCompanyId(int priceBookId,
			int companyId) {
		// TODO Auto-generated method stub
		try{
			List<PriceBook> list = getSessionFactory().getCurrentSession()
					.createQuery("from PriceBook where PRICE_BOOK_ID = ? AND COMPANY_ASSOCIATION_ID = ?")
					.setParameter(0, priceBookId)
					.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){
				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceBook> getAllActivePriceBooksByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<PriceBook> list = getSessionFactory().getCurrentSession()
					.createQuery("from PriceBook where COMPANY_ASSOCIATION_ID =? AND ACTIVE_INDICATOR = 1")
					.setParameter(0, companyId).list();
			if(list!=null&& list.size()>0){
				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<PriceBook> getAllValidPriceBooks(int companyId, int outletId,int contactgroupId) {
		try{
			 Calendar c = Calendar.getInstance();
			 c.add(Calendar.HOUR_OF_DAY, 9);
		      //  c.add(Calendar.DATE, 1); // added 1 day for date comparision
		        Date currentdate = c.getTime();
		       // if(contactgroupId == 0)
				//{
					//contactgroupId =1; //considering 0 as all groups
					
			//	}
			@SuppressWarnings("unchecked")
			
			
			List<PriceBook> list = getSessionFactory().getCurrentSession()
			.createQuery("from PriceBook where COMPANY_ASSOCIATION_ID = ? AND (OUTLET_ASSOCICATION_ID IS NULL OR OUTLET_ASSOCICATION_ID = ?)"
					//+ "AND (CONTACT_GROUP_ASSOCICATION_ID IS NULL OR CONTACT_GROUP_ASSOCICATION_ID = ?)"
					+ "AND VALID_FROM <= ? AND VALID_TO >= ? and ACTIVE_INDICATOR = 1 ")
					.setParameter(0, companyId)
					.setParameter(1, outletId)
					//.setParameter(2, contactgroupId)
					.setParameter(2, currentdate)
					.setParameter(3, currentdate)
					.list();
			if(list!=null&& list.size()>0){
				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceBook> getPriceBooksByDateRangeCompanyIdOutletIdGroupId(Date validFrom,Date validTo,int companyId,int outletId,int contactgroupId) {
		// TODO Auto-generated method stub
		try{
			List<PriceBook> list = getSessionFactory().getCurrentSession()
			.createQuery("from PriceBook where COMPANY_ASSOCIATION_ID = ? AND (OUTLET_ASSOCICATION_ID IS NULL OR OUTLET_ASSOCICATION_ID = ?)"
					+ "AND CONTACT_GROUP_ASSOCICATION_ID = ? "
					+ "AND (VALID_FROM BETWEEN ? AND  ? OR VALID_TO BETWEEN ? AND ?) AND ACTIVE_INDICATOR = 1")
					.setParameter(0, companyId)
					.setParameter(1, outletId)
					.setParameter(2, contactgroupId)
					.setParameter(3, validFrom)
					.setParameter(4, validTo)
					.setParameter(5, validFrom)
					.setParameter(6, validTo)
					.list();
			
			if(list!=null&& list.size()>0){
				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceBook> getAllInActivePriceBooksByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<PriceBook> list = getSessionFactory().getCurrentSession()
					.createQuery("from PriceBook where COMPANY_ASSOCIATION_ID =? AND ACTIVE_INDICATOR <> 1")
					.setParameter(0, companyId).list();
			if(list!=null&& list.size()>0){
				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

}
