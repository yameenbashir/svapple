/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.dowhile.ProductHistory;
import com.dowhile.ProductVariant;
import com.dowhile.StockOrder;
import com.dowhile.constant.Actions;
import com.dowhile.dao.StockOrderDAO;
import com.dowhile.wrapper.StockWrapper;
import com.dowhile.Company;
import com.dowhile.Product;


/**
 * @author Zafar Shakeel
 *
 */
public class StockOrderDAOImpl implements StockOrderDAO{


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
	public StockOrder addStockOrder(StockOrder stockOrder,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(stockOrder);
			return stockOrder;

		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public StockOrder updateStockOrder(StockOrder stockOrder,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(stockOrder);
			return stockOrder;

		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteStockOrder(StockOrder stockOrder,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(stockOrder);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public StockOrder getStockOrderByStockOrderID(int stockOrderID,int companyId) {
		// TODO Auto-generated method stub
		try{

			Query query= sessionFactory.getCurrentSession()
					.createQuery("from StockOrder where STOCK_ORDER_ID =? AND COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, stockOrderID)
					.setParameter(1, companyId);
			StockOrder stockOrder = (StockOrder) query.uniqueResult();
			if(stockOrder!=null){

				return stockOrder;
			}

		}catch(HibernateException ex){
			ex.printStackTrace();
		}


		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockOrder> getStockOrderBySupplierId(int supplierID,int companyId) {
		// TODO Auto-generated method stub
		List<StockOrder> list = new ArrayList<>(); 
		try{
			list = getSessionFactory().getCurrentSession()
					.createQuery("from StockOrder where CONTACT_ID =? AND COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, supplierID)
					.setParameter(1, companyId).list();
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
	public List<StockOrder> getStockOrderByOutletId(int outletID,int companyId) {
		// TODO Auto-generated method stub
		List<StockOrder> list = new ArrayList<>(); 
		try{
			list = getSessionFactory().getCurrentSession()
					.createQuery("from StockOrder where (OUTLET_ASSOCICATION_ID =? OR SOURCE_OUTLET_ASSOCICATION_ID = ?)AND COMPANY_ASSOCIATION_ID=? ORDER BY STOCK_ORDER_ID DESC")
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
	public List<StockOrder> getTenStockOrderByOutletId(int outletID,int companyId) {
		// TODO Auto-generated method stub
		List<StockOrder> list = new ArrayList<>(); 
		try{
			list = getSessionFactory().getCurrentSession()
					.createQuery("from StockOrder where (OUTLET_ASSOCICATION_ID =? OR SOURCE_OUTLET_ASSOCICATION_ID = ?)AND COMPANY_ASSOCIATION_ID=? ORDER BY STOCK_ORDER_ID DESC  LIMIT 10  ").setMaxResults(10)
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
	public List<StockOrder> getStockOrderByOutletIdNotComp(int outletID,int companyId) {
		// TODO Auto-generated method stub
		List<StockOrder> list = new ArrayList<>(); 
		try{
			list = getSessionFactory().getCurrentSession()
					.createQuery("from StockOrder where (OUTLET_ASSOCICATION_ID =? OR SOURCE_OUTLET_ASSOCICATION_ID = ?)AND COMPANY_ASSOCIATION_ID=? AND STATUS_ASSOCICATION_ID not in (3,8,10) ORDER BY STOCK_ORDER_ID DESC")
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
	public List<StockOrder> getStockOrderCompletedByOutletId(int outletID,int companyId) {
		// TODO Auto-generated method stub
		List<StockOrder> listStockTransfer = new ArrayList<>();
		List<StockOrder> list = new ArrayList();
		try{
			listStockTransfer = getSessionFactory().getCurrentSession()
					.createQuery("from StockOrder where (OUTLET_ASSOCICATION_ID =? OR SOURCE_OUTLET_ASSOCICATION_ID = ?) AND STATUS_ASSOCICATION_ID = 3 AND STOCK_ORDER_TYPE_ASSOCICATION_ID = 3 AND COMPANY_ASSOCIATION_ID=? ORDER BY STOCK_ORDER_ID DESC")
					.setParameter(0, outletID)
					.setParameter(1, outletID)
					.setParameter(2, companyId).list();
			list = getSessionFactory().getCurrentSession()
					.createQuery("from StockOrder where (OUTLET_ASSOCICATION_ID =? OR SOURCE_OUTLET_ASSOCICATION_ID = ?) AND STOCK_ORDER_TYPE_ASSOCICATION_ID <> 3 AND COMPANY_ASSOCIATION_ID=? ORDER BY STOCK_ORDER_ID DESC")
					.setParameter(0, outletID)
					.setParameter(1, outletID)
					.setParameter(2, companyId).list();
			list.addAll(listStockTransfer);
			Collections.sort(list, new Comparator<StockOrder>(){
				public int compare(StockOrder s1, StockOrder s2) {
					return s1.getStockOrderId().compareTo(s2.getStockOrderId());
				}
			});
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
	public List<StockOrder> getTenStockOrderCompletedByOutletId(int outletID,int companyId) {
		// TODO Auto-generated method stub
		List<StockOrder> listStockTransfer = new ArrayList<>();
		List<StockOrder> list = new ArrayList();
		try{
			listStockTransfer = getSessionFactory().getCurrentSession()
					.createQuery("from StockOrder where (OUTLET_ASSOCICATION_ID =? OR SOURCE_OUTLET_ASSOCICATION_ID = ?) AND STATUS_ASSOCICATION_ID = 3 AND STOCK_ORDER_TYPE_ASSOCICATION_ID = 3 AND COMPANY_ASSOCIATION_ID=? ORDER BY STOCK_ORDER_ID DESC  LIMIT 10").setMaxResults(10)
					.setParameter(0, outletID)
					.setParameter(1, outletID)
					.setParameter(2, companyId).list();
			list = getSessionFactory().getCurrentSession()
					.createQuery("from StockOrder where (OUTLET_ASSOCICATION_ID =? OR SOURCE_OUTLET_ASSOCICATION_ID = ?) AND STOCK_ORDER_TYPE_ASSOCICATION_ID <> 3 AND COMPANY_ASSOCIATION_ID=? ORDER BY STOCK_ORDER_ID DESC  LIMIT 10  ").setMaxResults(10)
					.setParameter(0, outletID)
					.setParameter(1, outletID)
					.setParameter(2, companyId).list();
			list.addAll(listStockTransfer);
			Collections.sort(list, new Comparator<StockOrder>(){
				public int compare(StockOrder s1, StockOrder s2) {
					return s1.getStockOrderId().compareTo(s2.getStockOrderId());
				}
			});
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
	public List<StockOrder> GetAllStockOrder(int companyId) {
		try {
			List<StockOrder> list = getSessionFactory().getCurrentSession()
					.createQuery("From StockOrder WHERE ACTIVE_INDICATOR = 1 AND COMPANY_ASSOCIATION_ID=? ORDER BY STOCK_ORDER_ID DESC")
					.setParameter(0, companyId).list();
			if (list != null && list.size() > 0) {

				return list;
			}

		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockOrder> GetTenStockOrder(int companyId) {
		try {
			List<StockOrder> list = getSessionFactory().getCurrentSession()
					.createQuery("From StockOrder WHERE ACTIVE_INDICATOR = 1 AND COMPANY_ASSOCIATION_ID=? ORDER BY STOCK_ORDER_ID DESC  LIMIT 10  ").setMaxResults(10)
					.setParameter(0, companyId).list();
			if (list != null && list.size() > 0) {

				return list;
			}

		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockOrder> GetAllStockTransferOrder(int companyId) {
		try {
			List<StockOrder> list = getSessionFactory().getCurrentSession()
					.createQuery("From StockOrder WHERE STOCK_ORDER_TYPE_ASSOCICATION_ID = 3 AND ACTIVE_INDICATOR = 1 AND COMPANY_ASSOCIATION_ID=? ORDER BY STOCK_ORDER_ID DESC")
					.setParameter(0, companyId).list();
			if (list != null && list.size() > 0) {

				return list;
			}

		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockOrder> GetAllStockReturntoWarehouseOrder(int companyId) {
		try {
			List<StockOrder> list = getSessionFactory().getCurrentSession()
					.createQuery("From StockOrder WHERE STOCK_ORDER_TYPE_ASSOCICATION_ID = 4 AND ACTIVE_INDICATOR = 1 AND COMPANY_ASSOCIATION_ID=? ORDER BY STOCK_ORDER_ID DESC")
					.setParameter(0, companyId).list();
			if (list != null && list.size() > 0) {

				return list;
			}

		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockOrder> GetAllStockReturnOrderForOutlet(int outletId, int companyId) {
		try {
			List<StockOrder> list = getSessionFactory().getCurrentSession()
					.createQuery("From StockOrder WHERE STOCK_ORDER_TYPE_ASSOCICATION_ID = 4 AND ACTIVE_INDICATOR = 1 AND SOURCE_OUTLET_ASSOCICATION_ID=? AND COMPANY_ASSOCIATION_ID=? ORDER BY STOCK_ORDER_ID DESC")
					.setParameter(0, outletId)
					.setParameter(1, companyId).list();
			if (list != null && list.size() > 0) {

				return list;
			}

		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean UpdateStockComplete(StockWrapper stockWrapper, Company company) {
		try {
			if(stockWrapper.getStockOrder() != null) {
				getSessionFactory().getCurrentSession().update(stockWrapper.getStockOrder());
			}
			if(stockWrapper.getProductUpdateList().size() > 0 ) {
				for(Product product: stockWrapper.getProductUpdateList()){
					getSessionFactory().getCurrentSession().saveOrUpdate(product);
					if(product.getStandardProduct().equalsIgnoreCase("true") && !product.getVariantProducts().equalsIgnoreCase("true")
							||!product.getStandardProduct().equalsIgnoreCase("true")){
						ProductHistory productHistory = new ProductHistory();
						productHistory.setAction(Actions.UPDATE.getActionName());
						productHistory.setActionDate(new Date());
						productHistory.setActiveIndicator(true);
						productHistory.setOutlet(product.getOutlet());
						productHistory.setOutletQuantity(product.getCurrentInventory());
						productHistory.setProduct(product);
						productHistory.setQuantity(product.getCurrentInventory());
						productHistory.setChangeQuantity(product.getCurrentInventory());
						productHistory.setUser(product.getUserByCreatedBy());
						productHistory.setProductHistoryUuid(product.getProductUuid());
						productHistory.setCompany(company);
						getSessionFactory().getCurrentSession().save(productHistory);
					}
				}
			}
			if(stockWrapper.getProductVariantUpdateList().size() > 0 ) {
				for(ProductVariant productVariant: stockWrapper.getProductVariantUpdateList()){
					getSessionFactory().getCurrentSession().saveOrUpdate(productVariant);
					ProductHistory productHistory = new ProductHistory();
					productHistory.setAction(Actions.UPDATE.getActionName());
					productHistory.setActionDate(new Date());
					productHistory.setActiveIndicator(true);
					productHistory.setProductVariant(productVariant);
					productHistory.setOutlet(productVariant.getOutlet());
					productHistory.setOutletQuantity(productVariant.getCurrentInventory());
					productHistory.setProduct(productVariant.getProduct());
					productHistory.setQuantity(productVariant.getCurrentInventory());
					productHistory.setChangeQuantity(productVariant.getCurrentInventory());
					productHistory.setUser(productVariant.getUserByCreatedBy());
					productHistory.setProductHistoryUuid(productVariant.getProductVariantUuid());
					productHistory.setCompany(company);
					getSessionFactory().getCurrentSession().save(productHistory);
				}
			}
			if(stockWrapper.getNotification() != null ) {
				getSessionFactory().getCurrentSession().save(stockWrapper.getNotification());
			}
			if(stockWrapper.getContact() != null) {
				getSessionFactory().getCurrentSession().update(stockWrapper.getContact());
			}

		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return true;
	}

}
