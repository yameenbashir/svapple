package com.dowhile.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.dowhile.InventoryCount;
import com.dowhile.dao.InventoryCountDAO;


/**
 * @author Zafar Shakeel
 *
 */
public class InventoryCountDAOImpl implements InventoryCountDAO{
	
	
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
	public InventoryCount addInventoryCount(InventoryCount inventoryCount,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(inventoryCount);
			return inventoryCount;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public InventoryCount updateInventoryCount(InventoryCount inventoryCount,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(inventoryCount);
			return inventoryCount;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteInventoryCount(InventoryCount inventoryCount,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(inventoryCount);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return false;
	}
	
	@Override
	public InventoryCount getInventoryCountByInventoryCountID(int inventoryCountID,int companyId) {
		// TODO Auto-generated method stub
		try{
				
			Query query= sessionFactory.getCurrentSession()
					.createQuery("from InventoryCount where INVENTORY_COUNT_ID =? AND COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, inventoryCountID)
					.setParameter(1, companyId);
			InventoryCount inventoryCount = (InventoryCount) query.uniqueResult();
			if(inventoryCount!=null){

				return inventoryCount;
			}

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}


		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InventoryCount> getInventoryCountBySupplierId(int supplierID,int companyId) {
		// TODO Auto-generated method stub
		List<InventoryCount> list = new ArrayList<>(); 
		try{
			list = getSessionFactory().getCurrentSession()
					.createQuery("from InventoryCount where CONTACT_ID =? AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, supplierID)
			.setParameter(1, companyId).list();
			if (list != null && list.size() > 0) {

				return list;
			}

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}


		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InventoryCount> getInventoryCountByOutletId(int outletID,int companyId) {
		// TODO Auto-generated method stub
		List<InventoryCount> list = new ArrayList<>(); 
		try{
			list = getSessionFactory().getCurrentSession()
					.createQuery("from InventoryCount where OUTLET_ASSOCICATION_ID =? AND COMPANY_ASSOCIATION_ID=? ORDER BY INVENTORY_COUNT_ID DESC")
			.setParameter(0, outletID)
			.setParameter(1, companyId).list();
			if (list != null && list.size() > 0) {

				return list;
			}

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}


		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InventoryCount> GetAllInventoryCount(int companyId) {
		try {
			List<InventoryCount> list = getSessionFactory().getCurrentSession()
					.createQuery("From InventoryCount WHERE ACTIVE_INDICATOR = 1 AND COMPANY_ASSOCIATION_ID=? ORDER BY INVENTORY_COUNT_ID DESC")
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
