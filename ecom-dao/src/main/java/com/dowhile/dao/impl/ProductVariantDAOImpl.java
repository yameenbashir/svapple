/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.Company;
import com.dowhile.ProductHistory;
import com.dowhile.ProductVariant;
import com.dowhile.constant.Actions;
import com.dowhile.dao.ProductVariantDAO;

/**
 * @author Yameen Bashir
 * 
 */
public class ProductVariantDAOImpl implements ProductVariantDAO {

	private SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(ProductVariantDAOImpl.class.getName());

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
	public ProductVariant addProductVariant(ProductVariant productVariant,Actions actionName,
			int totalQunatity ,Company company) {
		// TODO Auto-generated method stub
		try {
			getSessionFactory().getCurrentSession().save(productVariant);
			ProductHistory productHistory = new ProductHistory();
			productHistory.setAction(actionName.getActionName());
			productHistory.setActionDate(new Date());
			productHistory.setActiveIndicator(true);
			productHistory.setProductVariant(productVariant);
			productHistory.setOutlet(productVariant.getOutlet());
			productHistory.setOutletQuantity(productVariant.getCurrentInventory());
			productHistory.setProduct(productVariant.getProduct());
			productHistory.setQuantity(productVariant.getCurrentInventory());
			productHistory.setChangeQuantity(totalQunatity);
			productHistory.setUser(productVariant.getUserByCreatedBy());
			productHistory.setProductHistoryUuid(productVariant.getProductVariantUuid());

			productHistory.setCompany(company);
			getSessionFactory().getCurrentSession().save(productHistory);
			return productVariant;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public void addProductVariantList(List<ProductVariant> productVariantList,Company company) {
		// TODO Auto-generated method stub
		try {
			for(ProductVariant productVariant: productVariantList){
				getSessionFactory().getCurrentSession().save(productVariant);
				ProductHistory productHistory = new ProductHistory();
				productHistory.setAction(Actions.CREATE.getActionName());
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
		catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
	}


	@Override
	public ProductVariant updateProductVariant(ProductVariant productVariant,Actions actionName,
			int totalQunatity,Company company) {
		// TODO Auto-generated method stub
		try {
			getSessionFactory().getCurrentSession().update(productVariant);
			ProductHistory productHistory = new ProductHistory();
			productHistory.setAction(actionName.getActionName());
			productHistory.setActionDate(new Date());
			productHistory.setActiveIndicator(true);
			productHistory.setProductVariant(productVariant);
			productHistory.setOutlet(productVariant.getOutlet());
			productHistory.setOutletQuantity(productVariant.getCurrentInventory());
			productHistory.setProduct(productVariant.getProduct());
			productHistory.setQuantity(productVariant.getCurrentInventory());
			productHistory.setChangeQuantity(totalQunatity);
			productHistory.setUser(productVariant.getUserByCreatedBy());
			productHistory.setProductHistoryUuid(productVariant.getProductVariantUuid());
			productHistory.setCompany(company);
			getSessionFactory().getCurrentSession().save(productHistory);
			return productVariant;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public void updateProductVariantList(List<ProductVariant> productVariantList,Company company) {
		// TODO Auto-generated method stub
		try {
			for(ProductVariant productVariant: productVariantList){
				getSessionFactory().getCurrentSession().update(productVariant);
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
		catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
	}

	@Override
	public boolean deleteProductVariant(ProductVariant productVariant,Actions actionName,int companyId) {
		// TODO Auto-generated method stub
		try {
			getSessionFactory().getCurrentSession().delete(productVariant);
			ProductHistory productHistory = new ProductHistory();
			productHistory.setAction(actionName.getActionName());
			productHistory.setActionDate(new Date());
			productHistory.setActiveIndicator(true);
			productHistory.setOutlet(productVariant.getOutlet());
			productHistory.setOutletQuantity(productVariant.getCurrentInventory());
			productHistory.setProduct(productVariant.getProduct());
			productHistory.setQuantity(productVariant.getCurrentInventory());
			productHistory.setChangeQuantity(productVariant.getCurrentInventory());
			productHistory.setUser(productVariant.getUserByCreatedBy());
			productHistory.setProductHistoryUuid(productVariant.getProductVariantUuid());
			getSessionFactory().getCurrentSession().save(productHistory);
			return true;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public ProductVariant getProductVariantByProductVariantId(
			int productVariantId,int companyId) {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("unchecked")
			List<ProductVariant> list = getSessionFactory()
			.getCurrentSession()
			.createQuery(
					"from ProductVariant where PRODUCT_VARIANT_ID=? AND COMPANY_ASSOCIATION_ID = ?")
					.setParameter(0, productVariantId)
					.setParameter(1, companyId).list();
			if (list != null && list.size() > 0) {

				return list.get(0);
			}
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductVariant> getAllProductVariants(int companyId) {
		// TODO Auto-generated method stub
		try {
			List<ProductVariant> list = getSessionFactory()
					.getCurrentSession()
					.createQuery(
							"from ProductVariant where COMPANY_ASSOCIATION_ID = ? AND ACTIVE_INDICATOR = 1 ORDER BY PRODUCT_VARIANT_ID")
							.setParameter(0, companyId).list();
			if (list != null && list.size() > 0) {

				return list;
			}
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductVariant> getAllProductVariantsInclInActive(int companyId) {
		// TODO Auto-generated method stub
		try {
			List<ProductVariant> list = getSessionFactory()
					.getCurrentSession()
					.createQuery(
							"from ProductVariant where COMPANY_ASSOCIATION_ID = ? ORDER BY PRODUCT_VARIANT_ID")
							.setParameter(0, companyId).list();
			if (list != null && list.size() > 0) {

				return list;
			}
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<ProductVariant> getVarientsByProductId(int productid , int companyId) {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("unchecked")
			List<ProductVariant> list = getSessionFactory()
			.getCurrentSession()
			.createQuery(
					"from ProductVariant where PRODUCT_ASSOCICATION_ID=? AND COMPANY_ASSOCIATION_ID = ? AND ACTIVE_INDICATOR = 1 ORDER BY PRODUCT_VARIANT_ID")
					.setParameter(0, productid)
					.setParameter(1, companyId).list();
			return list;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public int getContOfVariantsByProductId(int productId, int companyId) {
		// TODO Auto-generated method stub
		try{
			int count = ((Long)getSessionFactory().getCurrentSession().createQuery("SELECT COUNT(*) FROM ProductVariant WHERE "
					+ "  COMPANY_ASSOCIATION_ID = "+companyId+" AND PRODUCT_ASSOCICATION_ID = "+productId+"").uniqueResult()).intValue();
			return count;
		}catch(Exception ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return 0;
	}

	@Override
	public int getCountOfInventoryByProductUuId(String productUuId , int companyId) {
		// TODO Auto-generated method stub

		int count = 0;
		try {
			@SuppressWarnings("unchecked")
			List<ProductVariant> list = getSessionFactory()
			.getCurrentSession()
			.createQuery(
					"from ProductVariant where PRODUCT_UUID=? AND COMPANY_ASSOCIATION_ID = ?")
					.setParameter(0, productUuId)
					.setParameter(1, companyId).list();
			if(list!=null){
				for(ProductVariant productVariant: list){
					if(productVariant.isActiveIndicator()){
						count =count+productVariant.getCurrentInventory();
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
	public List<ProductVariant> getAllActiveProductVariantsByOutletIdCompanyId(int OutletId , int companyId) {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("unchecked")
			List<ProductVariant> list = getSessionFactory()
			.getCurrentSession()
			.createQuery(
					"from ProductVariant where OUTLET_ASSOCICATION_ID=? AND COMPANY_ASSOCIATION_ID = ? AND ACTIVE_INDICATOR = 1 ORDER BY PRODUCT_VARIANT_ID")
					.setParameter(0, OutletId)
					.setParameter(1, companyId).list();
			return list;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<ProductVariant> getAllProductVariantsByUuid(String uUid , int companyId) {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("unchecked")
			List<ProductVariant> list = getSessionFactory()
			.getCurrentSession()
			.createQuery(
					"from ProductVariant where PRODUCT_VARIANT_UUID=? AND COMPANY_ASSOCIATION_ID = ? ORDER BY PRODUCT_VARIANT_ID")
					.setParameter(0, uUid)
					.setParameter(1, companyId).list();
			return list;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<ProductVariant> getAllProductVariantsGroupbyUuid(int companyId) {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("unchecked")
			List<ProductVariant> list = getSessionFactory()
			.getCurrentSession()
			.createQuery(
					"from ProductVariant where COMPANY_ASSOCIATION_ID = ?  AND ACTIVE_INDICATOR = 1 GROUP BY PRODUCT_VARIANT_UUID ORDER BY PRODUCT_VARIANT_ID")
					.setParameter(0, companyId).list();
			return list;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}


	@Override
	public List<ProductVariant> getAllProductVariantsByOutletIdGroupbyUuid(int OutletId , int companyId) {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("unchecked")
			List<ProductVariant> list = getSessionFactory()
			.getCurrentSession()
			.createQuery(
					"from ProductVariant where OUTLET_ASSOCICATION_ID=? AND COMPANY_ASSOCIATION_ID = ?  AND ACTIVE_INDICATOR = 1 ORDER BY PRODUCT_VARIANT_ID")
					.setParameter(0, OutletId)
					.setParameter(1, companyId).list();
			return list;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}



	@Override
	public int getCountOfInventoryByProductVariantUuId(
			String productVariantUuId, int companyId) {
		int count = 0;
		try {
			@SuppressWarnings("unchecked")
			List<ProductVariant> list = getSessionFactory()
			.getCurrentSession()
			.createQuery(
					"from ProductVariant where PRODUCT_VARIANT_UUID=? AND COMPANY_ASSOCIATION_ID = ? AND ACTIVE_INDICATOR = 1")
					.setParameter(0, productVariantUuId)
					.setParameter(1, companyId).list();
			if(list!=null){
				for(ProductVariant productVariant: list){
					if(productVariant.isActiveIndicator()){
						count =count+productVariant.getCurrentInventory();
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
	public int getCountOfInventoryByProductVariantUuIdOutletId(
			String productVariantUuId, int outletId, int companyId) {
		int count = 0;
		try {
			@SuppressWarnings("unchecked")
			List<ProductVariant> list = getSessionFactory()
			.getCurrentSession()
			.createQuery(
					"from ProductVariant where PRODUCT_VARIANT_UUID=? AND OUTLET_ASSOCICATION_ID=? AND COMPANY_ASSOCIATION_ID = ? AND ACTIVE_INDICATOR = 1")
					.setParameter(0, productVariantUuId)
					.setParameter(1, outletId)
					.setParameter(2, companyId).list();
			if(list!=null){
				for(ProductVariant productVariant: list){
					if(productVariant.isActiveIndicator()){
						count =count+productVariant.getCurrentInventory();
					}

				}
			}

			return count;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return count;
	}

	/*@Override
	public int getCountOfMAXSKUForProductVariantByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		try{
			int count = ((Long)getSessionFactory().getCurrentSession().createQuery("select count(*) from ProductVariant where "
					+ "COMPANY_ASSOCIATION_ID = "+companyId+"").uniqueResult()).intValue();
			return count+1;
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return 0;
	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	public int getCountOfMAXSKUForProductVariantByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		try{
			
			int count = (int) getSessionFactory().getCurrentSession().createQuery("select max(productVariantId) from ProductVariant where "
					+ "COMPANY_ASSOCIATION_ID = "+companyId+"").uniqueResult();
			System.out.println("ProductVariant MaxCount: "+count);
			return count+1;
			
			
			
			
			/*List<ProductVariant> list = getSessionFactory()
					.getCurrentSession()
					.createQuery(
//							"FROM ProductVariant WHERE COMPANY_ASSOCIATION_ID=?  ORDER BY PRODUCT_VARIANT_ID DESC LIMIT 1")
							"FROM ProductVariant WHERE COMPANY_ASSOCIATION_ID=?")
							.setParameter(0, companyId).list();
					if(list!=null && list.size()>0){
						System.out.println("Product Variant Id: "+list.get(0).getProductVariantId());
						return list.get(0).getProductVariantId()+1;
					}
					return 1;*/
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}catch(Exception ex){
			
		}
		return 0;
	}

	@Override
	public List<ProductVariant> getAllActiveProductVariants() {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("unchecked")
			List<ProductVariant> list = getSessionFactory()
			.getCurrentSession()
			.createQuery(
					"from ProductVariant where ACTIVE_INDICATOR = 1")
					.list();
			return list;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}
	
	@Override
	public List<ProductVariant> getAllProductVariantsWarehouseandOutlet(int warehouseOutletId, int outletId, int companyId) {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("unchecked")
			List<ProductVariant> list = getSessionFactory()
			.getCurrentSession()
			.createQuery(
					"from ProductVariant where OUTLET_ASSOCICATION_ID in (?,?) AND COMPANY_ASSOCIATION_ID = ?")
					.setParameter(0, warehouseOutletId)
					.setParameter(1, outletId)
					.setParameter(2, companyId).list();
			return list;
		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
