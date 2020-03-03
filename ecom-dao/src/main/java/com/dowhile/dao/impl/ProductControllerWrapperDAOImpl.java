/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import com.dowhile.Brand;
import com.dowhile.Contact;
import com.dowhile.Outlet;
import com.dowhile.Product;
import com.dowhile.ProductType;
import com.dowhile.ProductVariant;
import com.dowhile.SalesTax;
import com.dowhile.Tag;
import com.dowhile.VariantAttribute;
import com.dowhile.dao.ProductControllerWrapperDAO;
import com.dowhile.wrapper.ProductControllerWrapper;

/**
 * @author HafizYameenBashir
 *
 */
public class ProductControllerWrapperDAOImpl implements ProductControllerWrapperDAO{

	private SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(ProductControllerWrapperDAOImpl.class.getName());

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

	@SuppressWarnings({ "unchecked" })
	@Override
	public ProductControllerWrapper getProductControllerWrapperDataByOutletIdCompanyId(
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		try{
			ProductControllerWrapper productControllerWrapper =  new ProductControllerWrapper();
			List<Product> productList = getSessionFactory().getCurrentSession()
					.createQuery("from Product where COMPANY_ASSOCIATION_ID = ? AND ACTIVE_INDICATOR = 1")
					.setParameter(0, companyId).list();
			if(productList!=null&& productList.size()>0){
				productControllerWrapper.setProductList(productList);
				productControllerWrapper.setSku(productList.size());
				System.out.println("ProductControllerWrapper Product Count: "+productList.size()+1);
			}
			List<ProductVariant> productVariantList = getSessionFactory()
					.getCurrentSession()
					.createQuery(
							"from ProductVariant where OUTLET_ASSOCICATION_ID=? AND COMPANY_ASSOCIATION_ID = ? AND ACTIVE_INDICATOR = 1 ORDER BY PRODUCT_VARIANT_ID")
							.setParameter(0, outletId)
							.setParameter(1, companyId).list();
			if(productVariantList!=null&& productVariantList.size()>0){
				productControllerWrapper.setProductVariantList(productVariantList);
				int count = (int) getSessionFactory().getCurrentSession().createQuery("select max(productVariantId) from ProductVariant where "
						+ "COMPANY_ASSOCIATION_ID = "+companyId+"").uniqueResult();
				System.out.println("ProductControllerWrapper ProductVariant MaxCount: "+count);
				System.out.println("ProductControllerWrapper productVariantList size: "+productVariantList.size());
				productControllerWrapper.setProductVariantSku(count+1);
			}
			List<Contact> contactsList = getSessionFactory().getCurrentSession()
					.createQuery("from Contact where COMPANY_ASSOCIATION_ID=? AND CONTACT_TYPE=? ")
					.setParameter(0, companyId)
					.setParameter(1, "SUPPLIER").list();
			if(contactsList!=null&& contactsList.size()>0){
				productControllerWrapper.setContactsList(contactsList);
			}
			Criteria criteria = getSessionFactory().getCurrentSession()
					.createCriteria(ProductType.class);
			criteria.add(Restrictions.eq("company.companyId", companyId));
			List<ProductType> productTypeList = criteria.list();
			if(productTypeList!=null&& productTypeList.size()>0){
				productControllerWrapper.setProductTypeList(productTypeList);
			}
			List<Brand> brandsList = getSessionFactory().getCurrentSession()
					.createQuery("from Brand where COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, companyId).list();
			if(brandsList!=null&& brandsList.size()>0){
				productControllerWrapper.setBrandsList(brandsList);
			}
			List<Outlet> outlets = getSessionFactory().getCurrentSession()
					.createQuery("from Outlet where COMPANY_ASSOCIATION_ID = ? AND ACTIVE_INDICATOR = 1")
					.setParameter(0, companyId).list();
			if(outlets!=null&& outlets.size()>0){
				productControllerWrapper.setOutlets(outlets);
			}
			List<VariantAttribute> variantAttributesList = getSessionFactory().getCurrentSession()
					.createQuery("from VariantAttribute where COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, companyId).list();
			if(variantAttributesList!=null&& variantAttributesList.size()>0){
				productControllerWrapper.setVariantAttributesList(variantAttributesList);
			}
			List<Tag> tagsList = getSessionFactory().getCurrentSession()
					.createQuery("from Tag where COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, companyId).list();
			if(tagsList!=null&& tagsList.size()>0){
				productControllerWrapper.setTagsList(tagsList);
			}
			List<SalesTax> salesTaxlist = getSessionFactory().getCurrentSession()
					.createQuery("From SalesTax WHERE ACTIVE_INDICATOR = 1 AND COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, companyId).list();
			if (salesTaxlist != null && salesTaxlist.size() > 0) {
				productControllerWrapper.setSalesTaxlist(salesTaxlist);
			}
			return productControllerWrapper;
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ProductControllerWrapper getProductControllerWrapperDataForManageProductByOutletIdCompanyId(
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		try{
			ProductControllerWrapper productControllerWrapper =  new ProductControllerWrapper();
			List<Product> productList = getSessionFactory().getCurrentSession()
					.createQuery("from Product where COMPANY_ASSOCIATION_ID = ? AND ACTIVE_INDICATOR = 1")
					.setParameter(0, companyId).list();
			if(productList!=null&& productList.size()>0){
				productControllerWrapper.setProductList(productList);
			}
			List<ProductVariant> productVariantList = getSessionFactory()
					.getCurrentSession()
					.createQuery(
							"from ProductVariant where COMPANY_ASSOCIATION_ID = ? AND ACTIVE_INDICATOR = 1 ORDER BY PRODUCT_VARIANT_ID")
							.setParameter(0, companyId).list();
			if (productVariantList != null && productVariantList.size() > 0) {
				productControllerWrapper.setProductVariantList(productVariantList);
			}
			List<Outlet> outlets = getSessionFactory().getCurrentSession()
					.createQuery("from Outlet where COMPANY_ASSOCIATION_ID = ? AND ACTIVE_INDICATOR = 1")
					.setParameter(0, companyId).list();
			if(outlets!=null&& outlets.size()>0){
				productControllerWrapper.setOutlets(outlets);
			}
			return productControllerWrapper;
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}
}
