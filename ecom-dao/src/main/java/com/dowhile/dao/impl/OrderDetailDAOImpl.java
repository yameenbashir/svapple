/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.OrderDetail;
import com.dowhile.dao.OrderDetailDAO;

/**
 * @author Zafar Shakeel
 *
 */
public class OrderDetailDAOImpl implements OrderDetailDAO{

	private SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(OrderDetailDAOImpl.class.getName());

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
	public OrderDetail addOrderDetail(OrderDetail orderDetail, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(orderDetail);
			return orderDetail;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public OrderDetail updateOrderDetail(OrderDetail orderDetail, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(orderDetail);
			return orderDetail;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteOrderDetail(OrderDetail orderDetail, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(orderDetail);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public OrderDetail getOrderDetailByOrderDetailId(int orderDetailId, int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<OrderDetail> list = getSessionFactory().getCurrentSession()
			.createQuery("from OrderDetail where ORDER_MAIN_ID = ? and COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, orderDetailId).
			setParameter(1, companyId).list();
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
	public List<OrderDetail> getAllOrderDetails(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<OrderDetail> list = getSessionFactory().getCurrentSession()
			.createQuery("from OrderDetail").list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
