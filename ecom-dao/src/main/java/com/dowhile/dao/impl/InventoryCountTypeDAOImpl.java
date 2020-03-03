package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.InventoryCountType;
import com.dowhile.dao.InventoryCountTypeDAO;

/**
 * @author Zafar Shakeel
 *
 */
public class InventoryCountTypeDAOImpl implements InventoryCountTypeDAO{

	private SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(InventoryCountTypeDAOImpl.class.getName());

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
	public InventoryCountType addInventoryCountType(InventoryCountType inventoryCountType) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(inventoryCountType);
			return inventoryCountType;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public InventoryCountType updateInventoryCountType(InventoryCountType inventoryCountType) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(inventoryCountType);
			return inventoryCountType;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteInventoryCountType(InventoryCountType inventoryCountType) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(inventoryCountType);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public InventoryCountType getInventoryCountTypeByInventoryCountTypeId(int inventoryCountTypeId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<InventoryCountType> list = getSessionFactory().getCurrentSession()
			.createQuery("from InventoryCountType where INVENTORY_COUNT_TYPE_ID=?")
			.setParameter(0, inventoryCountTypeId).list();
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
	public List<InventoryCountType> getAllInventoryCountType() {
		// TODO Auto-generated method stub
		try{
			List<InventoryCountType> inventoryCountTypeList = getSessionFactory().getCurrentSession().createCriteria(InventoryCountType.class).list();
			return inventoryCountTypeList;
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
