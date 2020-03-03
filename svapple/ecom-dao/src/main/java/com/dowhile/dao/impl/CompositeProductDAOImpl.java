/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.CompositeProduct;
import com.dowhile.ProductHistory;
import com.dowhile.ProductVariant;
import com.dowhile.constant.Actions;
import com.dowhile.dao.CompositeProductDAO;

/**
 * @author Yameen Bashir
 *
 */
public class CompositeProductDAOImpl implements CompositeProductDAO{

	
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
	public CompositeProduct addCompositeProduct(
			CompositeProduct compositeProduct,Actions actionName,int totalQunatity, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(compositeProduct);
			ProductHistory productHistory = new ProductHistory();
			productHistory.setAction(actionName.getActionName());
			productHistory.setActionDate(new Date());
			productHistory.setActiveIndicator(true);
			productHistory.setOutlet(compositeProduct.getOutlet());
			productHistory.setOutletQuantity(compositeProduct.getCompositeQuantity());
			productHistory.setProduct(compositeProduct.getProductByProductAssocicationId());
			productHistory.setQuantity(totalQunatity);
			productHistory.setChangeQuantity(compositeProduct.getCompositeQuantity());
			productHistory.setUser(compositeProduct.getUserByCreatedBy());
			productHistory.setProductHistoryUuid(compositeProduct.getCompositeProductUuid());
			productHistory.setCompany(compositeProduct.getCompany());
			getSessionFactory().getCurrentSession().save(productHistory);
			return compositeProduct;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public CompositeProduct updateCompositeProduct(
			CompositeProduct compositeProduct,Actions actionName,int totalQunatity, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(compositeProduct);
			ProductHistory productHistory = new ProductHistory();
			productHistory.setAction(actionName.getActionName());
			productHistory.setActionDate(new Date());
			productHistory.setActiveIndicator(true);
			productHistory.setOutlet(compositeProduct.getOutlet());
			productHistory.setOutletQuantity(compositeProduct.getCompositeQuantity());
			productHistory.setProduct(compositeProduct.getProductByProductAssocicationId());
			productHistory.setQuantity(totalQunatity);
			productHistory.setChangeQuantity(compositeProduct.getCompositeQuantity());
			productHistory.setUser(compositeProduct.getUserByCreatedBy());
			productHistory.setProductHistoryUuid(compositeProduct.getCompositeProductUuid());
			productHistory.setCompany(compositeProduct.getCompany());
			getSessionFactory().getCurrentSession().save(productHistory);
			return compositeProduct;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteCompositeProduct(CompositeProduct compositeProduct,Actions actionName, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(compositeProduct);
			ProductHistory productHistory = new ProductHistory();
			productHistory.setAction(actionName.getActionName());
			productHistory.setActionDate(new Date());
			productHistory.setActiveIndicator(true);
			productHistory.setOutlet(compositeProduct.getOutlet());
			productHistory.setOutletQuantity(compositeProduct.getCompositeQuantity());
			productHistory.setProduct(compositeProduct.getProductByProductAssocicationId());
			productHistory.setQuantity(0);
			productHistory.setChangeQuantity(compositeProduct.getCompositeQuantity());
			productHistory.setUser(compositeProduct.getUserByCreatedBy());
			productHistory.setProductHistoryUuid(compositeProduct.getCompositeProductUuid());
			productHistory.setCompany(compositeProduct.getCompany());
			getSessionFactory().getCurrentSession().save(productHistory);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public CompositeProduct getCompositeProductByCompositeProductId(
			int compositeProductId, int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<CompositeProduct> list = getSessionFactory().getCurrentSession()
			.createQuery("from CompositeProduct where COMPOSITE_PRODUCT_ID = ? AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, compositeProductId).
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
	public List<CompositeProduct> getAllCompositeProducts(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<CompositeProduct> list = getSessionFactory().getCurrentSession()
			.createQuery("from CompositeProduct where COMPANY_ASSOCIATION_ID = ?")
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
	public List<CompositeProduct> getAllCompositeProductsByProductIdOultetId(
			int productId,int outletId,int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<CompositeProduct> list = getSessionFactory().getCurrentSession()
			.createQuery("from CompositeProduct where PRODUCT_ASSOCICATION_ID = ? AND OUTLET_ASSOCICATION_ID = ?"
					+ " AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, productId)
			.setParameter(1, outletId).
			 setParameter(2, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<CompositeProduct> getAllCompositeProductsByUuid(String uUid,int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<CompositeProduct> list = getSessionFactory().getCurrentSession()
			.createQuery("from CompositeProduct where COMPOSITE_PRODUCT_UUID=? AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, uUid).
			 setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}
	
	@Override
	public int getCountOfInventoryByProductUuId(String productUuId , int companyId) {
		// TODO Auto-generated method stub
	
		int count = 0;
		try {
			@SuppressWarnings("unchecked")
			List<CompositeProduct> list = getSessionFactory()
					.getCurrentSession()
					.createQuery(
							"from CompositeProduct where PRODUCT_UUID=? AND COMPANY_ASSOCIATION_ID = ?")
					.setParameter(0, productUuId)
					.setParameter(1, companyId).list();
			if(list!=null){
				for(CompositeProduct compositeProduct: list){
					if(compositeProduct.isActiveIndicator()){
						count =count+compositeProduct.getCompositeQuantity();
					}
					
				}
			}
			
			return count;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return count;
	}

	@Override
	public int getCountOfInventoryByCompositeProductUuId(
			String compositeProductUuId, int companyId) {
		// TODO Auto-generated method stub
		int count = 0;
		try {
			@SuppressWarnings("unchecked")
			List<CompositeProduct> list = getSessionFactory()
					.getCurrentSession()
					.createQuery(
							"from CompositeProduct where COMPOSITE_PRODUCT_UUID=? AND COMPANY_ASSOCIATION_ID = ?")
					.setParameter(0, compositeProductUuId)
					.setParameter(1, companyId).list();
			if(list!=null){
				for(CompositeProduct compositeProduct: list){
					if(compositeProduct.isActiveIndicator()){
						count =count+compositeProduct.getCompositeQuantity();
					}
					
				}
			}
			
			return count;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return count;
	}

}
