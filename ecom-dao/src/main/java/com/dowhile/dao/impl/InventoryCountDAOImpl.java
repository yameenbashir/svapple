package com.dowhile.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.dowhile.Company;
import com.dowhile.InventoryCount;
import com.dowhile.Product;
import com.dowhile.ProductHistory;
import com.dowhile.ProductVariant;
import com.dowhile.constant.Actions;
import com.dowhile.dao.InventoryCountDAO;
import com.dowhile.wrapper.InventoryCountWrapper;
import com.dowhile.wrapper.StockWrapper;


/**
 * @author Zafar Shakeel
 *
 */
public class InventoryCountDAOImpl implements InventoryCountDAO{
	
	
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
	public InventoryCount addInventoryCount(InventoryCount inventoryCount,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(inventoryCount);
			return inventoryCount;

		}catch(HibernateException ex){
			ex.printStackTrace();
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
			ex.printStackTrace();
		}
		return null;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateInventoryCountComplete(InventoryCountWrapper inventoryCountWrapper, Company company) {
		try {
			if(inventoryCountWrapper.getInventoryCount() != null) {
				getSessionFactory().getCurrentSession().saveOrUpdate(inventoryCountWrapper.getInventoryCount());
			}
			if(inventoryCountWrapper.getProductList().size() > 0 ) {
				for(Product product: inventoryCountWrapper.getProductList()){
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
			if(inventoryCountWrapper.getProductVariantList().size() > 0 ) {
				for(ProductVariant productVariant: inventoryCountWrapper.getProductVariantList()){
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
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return true;
	}
	
	

	@Override
	public boolean deleteInventoryCount(InventoryCount inventoryCount,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(inventoryCount);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();
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
			ex.printStackTrace();
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
			ex.printStackTrace();
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
			ex.printStackTrace();
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
			ex.printStackTrace();
		}
		return null;
	}
	

}
