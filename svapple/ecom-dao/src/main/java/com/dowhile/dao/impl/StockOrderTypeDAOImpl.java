/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.StockOrderType;
import com.dowhile.dao.StockOrderTypeDAO;

/**
 * @author Zafar Shakeel
 *
 */
public class StockOrderTypeDAOImpl implements StockOrderTypeDAO{

	private SessionFactory sessionFactory;// private static Logger logger = Logger.getLogger(CashManagementController.class.getName());

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
	public StockOrderType addStockOrderType(StockOrderType stockOrderType) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(stockOrderType);
			return stockOrderType;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public StockOrderType updateStockOrderType(StockOrderType stockOrderType) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(stockOrderType);
			return stockOrderType;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteStockOrderType(StockOrderType stockOrderType) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(stockOrderType);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public StockOrderType getStockOrderTypeByStockOrderTypeId(int stockOrderTypeId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<StockOrderType> list = getSessionFactory().getCurrentSession()
			.createQuery("from StockOrderType where STOCK_ORDER_TYPE_ID=?")
			.setParameter(0, stockOrderTypeId).list();
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
	public List<StockOrderType> getAllStockOrderType() {
		// TODO Auto-generated method stub
		try{
			List<StockOrderType> stockOrderTypeList = getSessionFactory().getCurrentSession().createCriteria(StockOrderType.class).list();
			return stockOrderTypeList;
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
