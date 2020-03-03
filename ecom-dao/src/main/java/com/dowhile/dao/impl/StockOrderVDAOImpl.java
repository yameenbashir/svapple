/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.StockOrderV;
import com.dowhile.dao.StockOrderVDAO;


/**
 * @author Zafar Shakeel
 *
 */
public class StockOrderVDAOImpl implements StockOrderVDAO{
	
	
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
	public List<StockOrderV> getAllStockOrderByOutletId(int outletID,int companyId) {
		// TODO Auto-generated method stub
		List<StockOrderV> list = new ArrayList<>(); 
		try{
			list = getSessionFactory().getCurrentSession()
					.createQuery("from StockOrderV where (OUTLET_ASSOCICATION_ID =? OR SOURCE_OUTLET_ASSOCICATION_ID = ?)AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, outletID)
			.setParameter(1, outletID)
			.setParameter(2, companyId).list();
			if (list != null && list.size() > 0) {

				return list;
			}

		}catch(HibernateException ex){
			ex.printStackTrace();
		}


		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockOrderV> getTenStockOrderByOutletId(int outletID,int companyId) {
		// TODO Auto-generated method stub
		List<StockOrderV> list = new ArrayList<>(); 
		try{
			list = getSessionFactory().getCurrentSession()
					.createQuery("from StockOrderV where (OUTLET_ASSOCICATION_ID =? OR SOURCE_OUTLET_ASSOCICATION_ID = ?)AND COMPANY_ASSOCIATION_ID=?").setMaxResults(10)
			.setParameter(0, outletID)
			.setParameter(1, outletID)
			.setParameter(2, companyId).list();
			if (list != null && list.size() > 0) {

				return list;
			}

		}catch(HibernateException ex){
			ex.printStackTrace();
		}


		return list;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StockOrderV> getAllStockOrderCompletedByOutletId(int outletID,int companyId) {
		// TODO Auto-generated method stub
		List<StockOrderV> listStockTransfer = new ArrayList<>();
		List<StockOrderV> list = new ArrayList();
		try{
			listStockTransfer = getSessionFactory().getCurrentSession()
					.createQuery("from StockOrderV where (OUTLET_ASSOCICATION_ID =? OR SOURCE_OUTLET_ASSOCICATION_ID = ?) AND STATUS_ASSOCICATION_ID = 3 AND STOCK_ORDER_TYPE_ASSOCICATION_ID = 3 AND COMPANY_ASSOCIATION_ID=? ORDER BY STOCK_ORDER_ID DESC")
			.setParameter(0, outletID)
			.setParameter(1, outletID)
			.setParameter(2, companyId).list();
			list = getSessionFactory().getCurrentSession()
					.createQuery("from StockOrderV where (OUTLET_ASSOCICATION_ID =? OR SOURCE_OUTLET_ASSOCICATION_ID = ?) AND STOCK_ORDER_TYPE_ASSOCICATION_ID <> 3 AND COMPANY_ASSOCIATION_ID=? ORDER BY STOCK_ORDER_ID DESC")
			.setParameter(0, outletID)
			.setParameter(1, outletID)
			.setParameter(2, companyId).list();
			list.addAll(listStockTransfer);
			/*Collections.sort(list, new Comparator<StockOrderV>(){
			    public int compare(StockOrderV s1, StockOrderV s2) {
			        return s1.getId().getStockOrderId().compareTo(s2.getId().getStockOrderId());
			    }
			});*/		
			if (list != null && list.size() > 0) {

				return list;
			}

		}catch(HibernateException ex){
			ex.printStackTrace();
		}


		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockOrderV> getTenStockOrderCompletedByOutletId(int outletID,int companyId) {
		// TODO Auto-generated method stub
		List<StockOrderV> listStockTransfer = new ArrayList<>();
		List<StockOrderV> list = new ArrayList();
		try{
			listStockTransfer = getSessionFactory().getCurrentSession()
					.createQuery("from StockOrderV where (OUTLET_ASSOCICATION_ID =? OR SOURCE_OUTLET_ASSOCICATION_ID = ?) AND STATUS_ASSOCICATION_ID = 3 AND STOCK_ORDER_TYPE_ASSOCICATION_ID = 3 AND COMPANY_ASSOCIATION_ID=? ORDER BY STOCK_ORDER_ID DESC").setMaxResults(10)
			.setParameter(0, outletID)
			.setParameter(1, outletID)
			.setParameter(2, companyId).list();
			list = getSessionFactory().getCurrentSession()
					.createQuery("from StockOrderV where (OUTLET_ASSOCICATION_ID =? OR SOURCE_OUTLET_ASSOCICATION_ID = ?) AND STOCK_ORDER_TYPE_ASSOCICATION_ID <> 3 AND COMPANY_ASSOCIATION_ID=? ORDER BY STOCK_ORDER_ID DESC").setMaxResults(10)
			.setParameter(0, outletID)
			.setParameter(1, outletID)
			.setParameter(2, companyId).list();
			list.addAll(listStockTransfer);
			/*Collections.sort(list, new Comparator<StockOrderV>(){
			    public int compare(StockOrderV s1, StockOrderV s2) {
			        return s1.getId().getStockOrderId().compareTo(s2.getId().getStockOrderId());
			    }
			});*/		
			if (list != null && list.size() > 0) {

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}


		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StockOrderV> getAllStockOrder(int companyId) {
		// TODO Auto-generated method stub
		List<StockOrderV> list = new ArrayList<>(); 
		try{
			list = getSessionFactory().getCurrentSession()
					.createQuery("from StockOrderV where COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, companyId).list();
			if (list != null && list.size() > 0) {

				return list;
			}

		}catch(HibernateException ex){
			ex.printStackTrace();
		}


		return list;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StockOrderV> getTenStockOrder(int companyId) {
		// TODO Auto-generated method stub
		List<StockOrderV> list = new ArrayList<>(); 
		try{
			list = getSessionFactory().getCurrentSession()
					.createQuery("from StockOrderV where COMPANY_ASSOCIATION_ID=?").setMaxResults(10)
			.setParameter(0, companyId).list();
			if (list != null && list.size() > 0) {

				return list;
			}

		}catch(HibernateException ex){
			ex.printStackTrace();
		}


		return list;
	}
	
}
