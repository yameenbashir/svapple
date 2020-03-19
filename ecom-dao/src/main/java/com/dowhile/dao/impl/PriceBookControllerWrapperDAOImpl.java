/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.ContactGroup;
import com.dowhile.Outlet;
import com.dowhile.PriceBook;
import com.dowhile.PriceBookDetail;
import com.dowhile.PriceBookDetailSummary;
import com.dowhile.Product;
import com.dowhile.ProductTag;
import com.dowhile.ProductVariant;
import com.dowhile.Tag;
import com.dowhile.dao.PriceBookControllerWrapperDAO;
import com.dowhile.wrapper.PriceBookControllerWrapper;

/**
 * @author HafizYameenBashir
 *
 */
public class PriceBookControllerWrapperDAOImpl implements PriceBookControllerWrapperDAO{

	private SessionFactory sessionFactory;
	// private static Logger logger = Logger.getLogger(PaymentTypeDAOImpl.class.getName());

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

	@SuppressWarnings("unchecked")
	@Override
	public PriceBookControllerWrapper getNewPriceBookControllerDataByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		try{
			PriceBookControllerWrapper priceBookControllerWrapper  = new PriceBookControllerWrapper();
			List<Outlet> list = getSessionFactory().getCurrentSession()
					.createQuery("from Outlet where COMPANY_ASSOCIATION_ID = ? AND ACTIVE_INDICATOR = 1")
					.setParameter(0, companyId).list();
			if(list!=null&& list.size()>0){
				priceBookControllerWrapper.setOutlets(list);
			}
			List<ContactGroup> customerGroupList = getSessionFactory().getCurrentSession()
					.createQuery("From ContactGroup WHERE COMPANY_ASSOCIATION_ID=? AND ACTIVE_INDICATOR = 1")
					.setParameter(0, companyId).list();
			if (customerGroupList != null && customerGroupList.size() > 0) {

				priceBookControllerWrapper.setCustomerGroupList(customerGroupList);
			}
			return priceBookControllerWrapper;
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PriceBookControllerWrapper getActivePriceBooksAndContactGroupByDateRangeCompanyIdContactGroupId(
			Date validFrom, Date validTo, int companyId, int contactGroupId) {
		// TODO Auto-generated method stub
		try{
			PriceBookControllerWrapper priceBookControllerWrapper  = new PriceBookControllerWrapper();
			List<PriceBook> priceBookList = getSessionFactory().getCurrentSession()
					.createQuery("from PriceBook where COMPANY_ASSOCIATION_ID = ? "
							+ "AND (VALID_FROM BETWEEN ? AND  ? OR VALID_TO BETWEEN ? AND ?) AND ACTIVE_INDICATOR = 1")
					.setParameter(0, companyId)
					.setParameter(1, validFrom)
					.setParameter(2, validTo)
					.setParameter(3, validFrom)
					.setParameter(4, validTo)
					.list();

			if(priceBookList!=null&& priceBookList.size()>0){
				priceBookControllerWrapper.setPriceBookList(priceBookList);
			}
			List<ContactGroup> customerGroupList = getSessionFactory().getCurrentSession()
					.createQuery("from ContactGroup where COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, companyId).list();
			if(customerGroupList!=null&& customerGroupList.size()>0){
				priceBookControllerWrapper.setCustomerGroupList(customerGroupList);
			}
			return priceBookControllerWrapper;
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PriceBookControllerWrapper getManagePriceBookControllerDataByCompanyIdOutletIdPriceBookId(int companyId,int outletId,int priceBookId) {
		// TODO Auto-generated method stub
		try{
			PriceBookControllerWrapper priceBookControllerWrapper  = new PriceBookControllerWrapper();
			List<Outlet> list = getSessionFactory().getCurrentSession()
					.createQuery("from Outlet where COMPANY_ASSOCIATION_ID = ? AND ACTIVE_INDICATOR = 1")
					.setParameter(0, companyId).list();
			if(list!=null&& list.size()>0){
				priceBookControllerWrapper.setOutlets(list);
			}
			List<ContactGroup> customerGroupList = getSessionFactory().getCurrentSession()
					.createQuery("From ContactGroup WHERE COMPANY_ASSOCIATION_ID=? AND ACTIVE_INDICATOR = 1")
					.setParameter(0, companyId).list();
			if (customerGroupList != null && customerGroupList.size() > 0) {

				priceBookControllerWrapper.setCustomerGroupList(customerGroupList);
			}
			List<PriceBook> priceBookList = getSessionFactory().getCurrentSession()
					.createQuery("from PriceBook where PRICE_BOOK_ID = ? AND COMPANY_ASSOCIATION_ID = ?")
					.setParameter(0, priceBookId)
					.setParameter(1, companyId).list();
			if(priceBookList!=null&& priceBookList.size()>0){
				priceBookControllerWrapper.setPriceBookList(priceBookList);
			}
			List<Product> productlist = getSessionFactory().getCurrentSession()
					.createQuery("from Product where OUTLET_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID = ?  AND ACTIVE_INDICATOR = 1  ")
					.setParameter(0, outletId)
					.setParameter(1, companyId).list();
			if(productlist!=null&& productlist.size()>0){

				priceBookControllerWrapper.setProductList(productlist);
			}

			List<ProductVariant> productVariantlist = getSessionFactory()
					.getCurrentSession()
					.createQuery(
							"from ProductVariant where OUTLET_ASSOCICATION_ID=? AND COMPANY_ASSOCIATION_ID = ?  AND ACTIVE_INDICATOR = 1 ")
					.setParameter(0, outletId)
					.setParameter(1, companyId).list();
			if(productVariantlist!=null && productVariantlist.size()>0) {
				priceBookControllerWrapper.setProductVariantList(productVariantlist);
			}
			List<PriceBookDetailSummary> priceBookDetailSummarylist = getSessionFactory().getCurrentSession()
					.createQuery("from PriceBookDetailSummary where PRICE_BOOK_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID = ?  GROUP BY UUID ORDER BY PRODUCT_NAME")
					.setParameter(0, priceBookId)
					.setParameter(1, companyId).list();
			if(priceBookDetailSummarylist!=null&& priceBookDetailSummarylist.size()>0){
				priceBookControllerWrapper.setPriceBookDetailSummaryList(priceBookDetailSummarylist);
			}
			List<ProductTag> productTaglist = getSessionFactory().getCurrentSession()
					.createQuery("from ProductTag where COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, companyId).list();
			if(productTaglist!=null&& productTaglist.size()>0){

				priceBookControllerWrapper.setProductTags(productTaglist);
			}
			List<Tag> taglist = getSessionFactory().getCurrentSession()
					.createQuery("from Tag where COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, companyId).list();
			if(taglist!=null&& taglist.size()>0){

				priceBookControllerWrapper.setTagList(taglist);
			}
			return priceBookControllerWrapper;
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PriceBookControllerWrapper manageProductsInPriceBookByCompanyIdOutletIdPriceBookId(int companyId,
			int outletId, int priceBookId) {
		// TODO Auto-generated method stub
		try{
			PriceBookControllerWrapper priceBookControllerWrapper  = new PriceBookControllerWrapper();
			List<PriceBook> priceBookList = getSessionFactory().getCurrentSession()
					.createQuery("from PriceBook where PRICE_BOOK_ID = ? AND COMPANY_ASSOCIATION_ID = ?")
					.setParameter(0, priceBookId)
					.setParameter(1, companyId).list();
			if(priceBookList!=null&& priceBookList.size()>0){
				priceBookControllerWrapper.setPriceBookList(priceBookList);
			}
			List<Product> productlist = getSessionFactory().getCurrentSession()
					.createQuery("from Product where OUTLET_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID = ?  AND ACTIVE_INDICATOR = 1  ")
					.setParameter(0, outletId)
					.setParameter(1, companyId).list();
			if(productlist!=null&& productlist.size()>0){
				priceBookControllerWrapper.setProductList(productlist);
				Map<Integer, Product> productMap = new HashMap<Integer, Product>();
				if(productlist!=null){
					for(Product product : productlist){
						if(productMap.get(product.getProductId())==null){
							productMap.put(product.getProductId(), product);
						}
					}
				}
				priceBookControllerWrapper.setProductMap(productMap);
			}
			List<ProductVariant> productVariantlist = getSessionFactory()
					.getCurrentSession()
					.createQuery(
							"from ProductVariant where OUTLET_ASSOCICATION_ID=? AND COMPANY_ASSOCIATION_ID = ?  AND ACTIVE_INDICATOR = 1 ")
					.setParameter(0, outletId)
					.setParameter(1, companyId).list();
			if(productVariantlist!=null && productVariantlist.size()>0) {
				priceBookControllerWrapper.setProductVariantList(productVariantlist);
				Map<Integer, ProductVariant> productVariantMap = new HashMap<Integer, ProductVariant>();
				if(productVariantlist!=null){
					for(ProductVariant product : productVariantlist){
						if(productVariantMap.get(product.getProductVariantId())==null){
							productVariantMap.put(product.getProductVariantId(), product);
						}
					}
				}
				priceBookControllerWrapper.setProductVariantMap(productVariantMap);
			}
			List<PriceBookDetail> priceBookDetails = getSessionFactory().getCurrentSession()
					.createQuery("from PriceBookDetail where PRICE_BOOK_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID = ? AND ACTIVE_INDICATOR = 1 ")
					.setParameter(0, priceBookId)
					.setParameter(1, companyId).list();
			if(priceBookDetails!=null&& priceBookDetails.size()>0){
				Map<Integer, PriceBookDetail> priceBookDetailMap = new HashMap<Integer, PriceBookDetail>();
				if(priceBookDetails!=null){
					for(PriceBookDetail priceBook : priceBookDetails){
						priceBookDetailMap.put(priceBook.getPriceBookDetailId(), priceBook);
					}
				}
				priceBookControllerWrapper.setPriceBookDetailMap(priceBookDetailMap);
			}
			return priceBookControllerWrapper;
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
