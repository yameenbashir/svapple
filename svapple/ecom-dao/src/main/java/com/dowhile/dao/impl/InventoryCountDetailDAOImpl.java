package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.dowhile.InventoryCountDetail;
import com.dowhile.dao.InventoryCountDetailDAO;

/**
 * @author Zafar Shakeel
 *
 */
public class InventoryCountDetailDAOImpl implements InventoryCountDetailDAO{


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
	public InventoryCountDetail addInventoryCountDetail(InventoryCountDetail inventoryCountDetail,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(inventoryCountDetail);
			return inventoryCountDetail;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public void addInventoryCountDetailsList(List<InventoryCountDetail> inventoryCountDetailsList,int companyId) {
		// TODO Auto-generated method stub
		try{
			for(InventoryCountDetail inventoryCountDetail: inventoryCountDetailsList){
				getSessionFactory().getCurrentSession().save(inventoryCountDetail);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
	}

	@Override
	public InventoryCountDetail updateInventoryCountDetail(InventoryCountDetail inventoryCountDetail,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(inventoryCountDetail);
			return inventoryCountDetail;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public void updateInventoryCountDetailsList(List<InventoryCountDetail> inventoryCountDetailsList,int companyId) {
		// TODO Auto-generated method stub
		try{
			for(InventoryCountDetail inventoryCountDetail: inventoryCountDetailsList){
				getSessionFactory().getCurrentSession().update(inventoryCountDetail);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
	}

	@Override
	public boolean deleteInventoryCountDetail(InventoryCountDetail inventoryCountDetail,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(inventoryCountDetail);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public void deleteInventoryCountDetailsList(List<InventoryCountDetail> inventoryCountDetailsList,int companyId) {
		// TODO Auto-generated method stub
		try{
			for(InventoryCountDetail inventoryCountDetail: inventoryCountDetailsList){
				getSessionFactory().getCurrentSession().delete(inventoryCountDetail);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
	}

	@Override
	public InventoryCountDetail getInventoryCountDetailByInventoryCountDetailID(int inventoryCountDetailID,int companyId) {
		// TODO Auto-generated method stub
		try{

			Query query= sessionFactory.getCurrentSession()
					.createQuery("from InventoryCountDetail where INVENTORY_COUNT_DETAIL_ID =? AND COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, inventoryCountDetailID)
					.setParameter(1, companyId);
			InventoryCountDetail inventoryCountDetail = (InventoryCountDetail) query.uniqueResult();
			if(inventoryCountDetail!=null){

				return inventoryCountDetail;
			}

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}


		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InventoryCountDetail> getInventoryCountDetailByInventoryCountId(int inventoryCountID,int companyId) {
		// TODO Auto-generated method stub
		try{
			List<InventoryCountDetail> list = getSessionFactory().getCurrentSession()
					.createQuery("From InventoryCountDetail WHERE INVENTORY_COUNT_ASSOCICATION_ID =? AND COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, inventoryCountID)
					.setParameter(1, companyId).list();
			if (list != null && list.size() > 0) {

				return list;
			}


		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}


		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InventoryCountDetail> getAllInventoryCountDetails(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<InventoryCountDetail> list = getSessionFactory().getCurrentSession()
					.createQuery("From InventoryCountDetail WHERE COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, companyId).list();
			if (list != null && list.size() > 0) {

				return list;
			}


		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}


		return null;
	}
}
