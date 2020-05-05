/**
 * 
 */
package com.dowhile.dao.impl;
 
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

import com.dowhile.Product;
import com.dowhile.ProductVariant;
import com.dowhile.StockDetByProductUuid;
import com.dowhile.StockOrderDetail;
import com.dowhile.StockOrderDetailCustom;
import com.dowhile.dao.StockOrderDetailDAO;

/**
 * @author Zafar Shakeel
 *
 */
public class StockOrderDetailDAOImpl implements StockOrderDetailDAO{


	private SessionFactory sessionFactory;
	// private static Logger logger = Logger.getLogger(StockOrderDetailDAOImpl.class.getName());

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
	public StockOrderDetail addStockOrderDetail(StockOrderDetail stockOrderDetail,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(stockOrderDetail);
			return stockOrderDetail;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public void addStockOrderDetailsList(List<StockOrderDetail> stockOrderDetailsList,int companyId) {
		// TODO Auto-generated method stub
		try{
			for(StockOrderDetail stockOrderDetail: stockOrderDetailsList){
				getSessionFactory().getCurrentSession().save(stockOrderDetail);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
	}

	@Override
	public StockOrderDetail updateStockOrderDetail(StockOrderDetail stockOrderDetail,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(stockOrderDetail);
			return stockOrderDetail;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public void updateStockOrderDetailsList(List<StockOrderDetail> stockOrderDetailsList,int companyId) {
		// TODO Auto-generated method stub
		try{
			for(StockOrderDetail stockOrderDetail: stockOrderDetailsList){
				getSessionFactory().getCurrentSession().update(stockOrderDetail);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
	}
	
	@Override
	public void saveorUpdateStockOrderDetailsList(List<StockOrderDetail> stockOrderDetailsList,int companyId) {
		// TODO Auto-generated method stub
		try{
			for(StockOrderDetail stockOrderDetail: stockOrderDetailsList){
				getSessionFactory().getCurrentSession().saveOrUpdate(stockOrderDetail);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
	}

	@Override
	public boolean deleteStockOrderDetail(StockOrderDetail stockOrderDetail,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(stockOrderDetail);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public void deleteStockOrderDetailsList(List<StockOrderDetail> stockOrderDetailsList,int companyId) {
		// TODO Auto-generated method stub
		try{
			for(StockOrderDetail stockOrderDetail: stockOrderDetailsList){
				getSessionFactory().getCurrentSession().delete(stockOrderDetail);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
	}

	@Override
	public StockOrderDetail getStockOrderDetailByStockOrderDetailID(int stockOrderDetailID,int companyId) {
		// TODO Auto-generated method stub
		try{

			Query query= sessionFactory.getCurrentSession()
					.createQuery("from StockOrderDetail where STOCK_ORDER_DETAIL_ID =? AND COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, stockOrderDetailID)
					.setParameter(1, companyId);
			StockOrderDetail stockOrderDetail = (StockOrderDetail) query.uniqueResult();
			if(stockOrderDetail!=null){

				return stockOrderDetail;
			}

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}


		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockOrderDetail> getStockOrderDetailByStockOrderId(int stockOrderID,int companyId) {
		// TODO Auto-generated method stub
		try{
			List<StockOrderDetail> list = getSessionFactory().getCurrentSession()
					.createQuery("From StockOrderDetail WHERE STOCK_ORDER_ASSOCICATION_ID =? AND COMPANY_ASSOCIATION_ID=? ORDER BY PRODUCT_VARIANT_ASSOCICATION_ID asc")
					.setParameter(0, stockOrderID)
					.setParameter(1, companyId).list();
			if (list != null && list.size() > 0) {

				return list;
			}


		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}


		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockOrderDetail> getAllStockOrderDetails(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<StockOrderDetail> list = getSessionFactory().getCurrentSession()
					.createQuery("From StockOrderDetail WHERE COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, companyId).list();
			if (list != null && list.size() > 0) {

				return list;
			}


		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}


		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockOrderDetail> getStockOrderDetailByProductId(int productId, Date  startDate, Date endDate, int companyId) {
		// TODO Auto-generated method stub
		try{
			List<StockOrderDetail> list = getSessionFactory().getCurrentSession()
					.createQuery("From StockOrderDetail WHERE PRODUCT_ASSOCIATION_ID =? AND LAST_UPDATED BETWEEN ? AND ? AND COMPANY_ASSOCIATION_ID=? ORDER BY STOCK_ORDER_ASSOCICATION_ID DESC")
					.setParameter(0, productId)
					.setParameter(1, startDate)
					.setParameter(2, endDate)
					.setParameter(3, companyId).list();
			Query query = getSessionFactory().getCurrentSession().createQuery("From Product WHERE PRODUCT_UUID IN (SELECT productUuid FROM Product WHERE PRODUCT_ID IN (" + productId + ")) AND PRODUCT_ID NOT IN (" + productId + ")");
			List<Product> products = query.list();
			if(products.size() > 0){
				String strProducts = new String();
				strProducts = "'" +  String.valueOf(products.get(0).getProductId()) + "'";
				products.remove(0);
				for(Product product:products){
					strProducts = strProducts + ", " + "'" + String.valueOf(product.getProductId()) + "'";
				}
				query  = getSessionFactory().getCurrentSession().createQuery("From StockOrderDetail WHERE PRODUCT_ASSOCIATION_ID IN (" + strProducts + ") AND LAST_UPDATED BETWEEN ? AND ? AND COMPANY_ASSOCIATION_ID= ? ORDER BY STOCK_ORDER_ASSOCICATION_ID DESC")
						//.setParameter(0, strIds)
						.setParameter(0, startDate)
						.setParameter(1, endDate)
						.setParameter(2, companyId); 
				List<StockOrderDetail> listStockReturnToWarehouse = query.list();
				list.addAll(listStockReturnToWarehouse);
			}
			if (list != null && list.size() > 0) {

				return list;
			}


		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}


		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockOrderDetail> getStockOrderDetailByProductVariantId(int productVariantId, Date  startDate, Date endDate, int companyId) {
		// TODO Auto-generated method stub
		try{
			List<StockOrderDetail> list = getSessionFactory().getCurrentSession()
					.createQuery("From StockOrderDetail WHERE PRODUCT_VARIANT_ASSOCICATION_ID =? AND LAST_UPDATED BETWEEN ? AND ? AND COMPANY_ASSOCIATION_ID=? ORDER BY STOCK_ORDER_ASSOCICATION_ID DESC")
					.setParameter(0, productVariantId)
					.setParameter(1, startDate)
					.setParameter(2, endDate)
					.setParameter(3, companyId).list();
			Query query = getSessionFactory().getCurrentSession().createQuery("From ProductVariant WHERE PRODUCT_VARIANT_UUID IN (SELECT productVariantUuid FROM ProductVariant WHERE PRODUCT_VARIANT_ID IN (" + productVariantId + ")) AND PRODUCT_VARIANT_ID NOT IN (" + productVariantId + ")");
			List<ProductVariant> productVariants = query.list();
			if(productVariants.size() > 0){
				String strVariants = new String();
				strVariants = "'" +  String.valueOf(productVariants.get(0).getProductVariantId()) + "'";
				productVariants.remove(0);
				for(ProductVariant productVariant:productVariants){
					strVariants = strVariants + ", " + "'" + String.valueOf(productVariant.getProductVariantId()) + "'";
				}
				query  = getSessionFactory().getCurrentSession().createQuery("From StockOrderDetail WHERE PRODUCT_VARIANT_ASSOCICATION_ID IN (" + strVariants + ") AND LAST_UPDATED BETWEEN ? AND ? AND COMPANY_ASSOCIATION_ID= ? ORDER BY STOCK_ORDER_ASSOCICATION_ID DESC")
						//.setParameter(0, strIds)
						.setParameter(0, startDate)
						.setParameter(1, endDate)
						.setParameter(2, companyId); 
				List<StockOrderDetail> listStockReturnToWarehouse = query.list();
				list.addAll(listStockReturnToWarehouse);
			}
			if (list != null && list.size() > 0) {

				return list;
			}


		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}


		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StockOrderDetail> getStockOrderDetailByProductVariantsId(List<Integer> productVariantsId, Date  startDate, Date endDate, int companyId) {
		// TODO Auto-generated method stub
		try{
			String strIds = new String();
			strIds = "'" +  String.valueOf(productVariantsId.get(0)) + "'";
			productVariantsId.remove(0);
			for(Integer id:productVariantsId){
				strIds = strIds + ", " + "'" + String.valueOf(id) + "'";
			}
			Query query = getSessionFactory().getCurrentSession().createQuery("From StockOrderDetail WHERE PRODUCT_VARIANT_ASSOCICATION_ID IN (" + strIds + ") AND LAST_UPDATED BETWEEN ? AND ? AND COMPANY_ASSOCIATION_ID= ? ORDER BY STOCK_ORDER_ASSOCICATION_ID DESC")
					//.setParameter(0, strIds)
					.setParameter(0, startDate)
					.setParameter(1, endDate)
					.setParameter(2, companyId);
			List<StockOrderDetail> list = query.list();	
			query = getSessionFactory().getCurrentSession().createQuery("From ProductVariant WHERE PRODUCT_VARIANT_UUID IN (SELECT productVariantUuid FROM ProductVariant WHERE PRODUCT_VARIANT_ID IN (" + strIds + ")) AND PRODUCT_VARIANT_ID NOT IN (" + strIds + ")");
			List<ProductVariant> productVariants = query.list();
			if(productVariants.size() > 0){
				String strVariants = new String();
				strVariants = "'" +  String.valueOf(productVariants.get(0).getProductVariantId()) + "'";
				productVariants.remove(0);
				for(ProductVariant productVariant:productVariants){
					strVariants = strVariants + ", " + "'" + String.valueOf(productVariant.getProductVariantId()) + "'";
				}
				query  = getSessionFactory().getCurrentSession().createQuery("From StockOrderDetail WHERE PRODUCT_VARIANT_ASSOCICATION_ID IN (" + strVariants + ") AND LAST_UPDATED BETWEEN ? AND ? AND COMPANY_ASSOCIATION_ID= ? ORDER BY STOCK_ORDER_ASSOCICATION_ID DESC")
						//.setParameter(0, strIds)
						.setParameter(0, startDate)
						.setParameter(1, endDate)
						.setParameter(2, companyId); 
				List<StockOrderDetail> listStockReturnToWarehouse = query.list();
				list.addAll(listStockReturnToWarehouse);
			}
			if (list != null && list.size() > 0) {

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}


		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
public List<StockOrderDetailCustom> getStockOrderDetailCustom(int stockOrderId, int outletId) {
		
		List<StockOrderDetailCustom> list = null;
		try{
		 
			list= getSessionFactory().getCurrentSession()
			.createSQLQuery("CALL StockOrderActions(?,?)" )
			.setParameter(0, stockOrderId)
			.setParameter(1, outletId)
			.setResultTransformer(Transformers.aliasToBean(StockOrderDetailCustom.class))
			.list();
			
		 if(list!=null&& list.size()>0){

				return list;
			}			
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StockDetByProductUuid> getStockOrderDetailByProductUUID(int companyId, int status, int stockOrdeType,
			String productUuid, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		List<StockDetByProductUuid> list = null;
		try {
				list = getSessionFactory().getCurrentSession()
						.createSQLQuery("call GetStockDetByProductUUID(?,?,?,?,?,?)")
						.setParameter(0, companyId)
						.setParameter(1, status)
						.setParameter(2, stockOrdeType)
						.setParameter(3, productUuid)
						.setParameter(4, startDate)
						.setParameter(5, endDate)
						.setResultTransformer(Transformers.aliasToBean(StockDetByProductUuid.class))
						.list();
				if(list!=null && list.size()>0) {
					return list;
				}
			
			
		}catch(HibernateException ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
