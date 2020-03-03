/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import com.dowhile.OrderDetail;
import com.dowhile.OrderMain;
import com.dowhile.dao.EcomOrderDAO;

/**
 * @author T430s
 *
 */
public class  EcomOrderDAOImpl implements  EcomOrderDAO  {

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
	public OrderMain addOrderMain(OrderMain orderMain) {
		try{
			getSessionFactory().getCurrentSession().save(orderMain);
			return orderMain;
		}
		catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public OrderMain updateOrderMain(OrderMain orderMain) {
		try{
			getSessionFactory().getCurrentSession().update(orderMain);
			return orderMain;
		}
		catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public OrderMain getOrdersByOrderId(int orderId) {
		try{
			@SuppressWarnings("unchecked")
			List<OrderMain> list = getSessionFactory().getCurrentSession()
			.createQuery("from OrderMain where ORDER_MAIN_ID=?")
			.setParameter(0, orderId).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<OrderMain> getOrdersByLikeOrderReferenceNo(String orderRefNo) {
		try{
			@SuppressWarnings("unchecked")
			List<OrderMain> list = getSessionFactory().getCurrentSession()
			.createQuery("from OrderMain where ORDER_REF_NBR=?")
			.setParameter(0, orderRefNo).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}
	@Override
	public OrderMain getOrdersByExactOrderReferenceNo(String orderRefNo) {
		try{
			@SuppressWarnings("unchecked")
			List<OrderMain> list = getSessionFactory().getCurrentSession()
			.createQuery("from OrderMain where ORDER_REF_NBR=?")
			.setParameter(0, orderRefNo).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<OrderMain> getOrdersByContactId(int contactId) {
		try{
			@SuppressWarnings("unchecked")
			List<OrderMain> list = getSessionFactory().getCurrentSession()
			.createQuery("from OrderMain where CONTACT_ASSOCIATION_ID=?")
			.setParameter(0, contactId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<OrderMain> getOrdersByCompanyId(int companyId) {
		try{
			@SuppressWarnings("unchecked")
			List<OrderMain> list = getSessionFactory().getCurrentSession()
			.createQuery("from OrderMain where COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<OrderMain> getOrdersByOutletId(int outletId) {
		try{
			@SuppressWarnings("unchecked")
			List<OrderMain> list = getSessionFactory().getCurrentSession()
			.createQuery("from OrderMain where OUTLET_ASSOCICATION_ID=?")
			.setParameter(0, outletId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public OrderDetail addOrderDetail(OrderDetail orderdetail) {
		try{
			getSessionFactory().getCurrentSession().save(orderdetail);
			return orderdetail;
		}
		catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public OrderDetail updateOrderDetail(OrderDetail orderdetail) {
		try{
			getSessionFactory().getCurrentSession().update(orderdetail);
			return orderdetail;
		}
		catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<OrderDetail> getOrdersDetailByOrderId(int orderId) {
		try{
			@SuppressWarnings("unchecked")
			List<OrderDetail> list = getSessionFactory().getCurrentSession()
			.createQuery("from OrderDetail where ORDER_MAIN_ASSOCICATION_ID=?")
			.setParameter(0, orderId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	
	
	
}
