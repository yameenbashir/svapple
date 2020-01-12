/**
 * 
 */
package com.dowhile.dao.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.dowhile.Company;
import com.dowhile.Product;
import com.dowhile.ProductHistory;
import com.dowhile.ProductVariant;
import com.dowhile.constant.Actions;
import com.dowhile.dao.ProductDAO;
import com.dowhile.wrapper.ProductListsWrapper;

/**
 * @author Yameen Bashir
 *
 */
public class ProductDAOImpl implements ProductDAO{

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
	public Product addProduct(Product product,Actions actionName,int totalQunatity, Company company) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(product);

			if(product.getStandardProduct().equalsIgnoreCase("true") && !product.getVariantProducts().equalsIgnoreCase("true")
					||!product.getStandardProduct().equalsIgnoreCase("true")){
				ProductHistory productHistory = new ProductHistory();
				productHistory.setAction(actionName.getActionName());
				productHistory.setActionDate(new Date());
				productHistory.setActiveIndicator(true);
				productHistory.setOutlet(product.getOutlet());
				productHistory.setOutletQuantity(product.getCurrentInventory());
				productHistory.setProduct(product);
				productHistory.setQuantity(totalQunatity);
				productHistory.setChangeQuantity(product.getCurrentInventory());
				productHistory.setUser(product.getUserByCreatedBy());
				productHistory.setProductHistoryUuid(product.getProductUuid());
				productHistory.setCompany(company);
				getSessionFactory().getCurrentSession().save(productHistory);
			}

			return product;
		}
		catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public void addProductList(List<Product> productList, Company company) {
		// TODO Auto-generated method stub
		try{
			for(Product product: productList){
				getSessionFactory().getCurrentSession().save(product);			
				if(product.getStandardProduct().equalsIgnoreCase("true") && !product.getVariantProducts().equalsIgnoreCase("true")
						||!product.getStandardProduct().equalsIgnoreCase("true")){
					ProductHistory productHistory = new ProductHistory();
					productHistory.setAction(Actions.CREATE.getActionName());
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
		catch(HibernateException ex){
			ex.printStackTrace();
		}
	}

	@Override
	public Product updateProduct(Product product,Actions actionName,int totalQunatity, Company company) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(product);
			if(product.getStandardProduct().equalsIgnoreCase("true") && !product.getVariantProducts().equalsIgnoreCase("true")
					||!product.getStandardProduct().equalsIgnoreCase("true")){
				ProductHistory productHistory = new ProductHistory();
				productHistory.setAction(actionName.getActionName());
				productHistory.setActionDate(new Date());
				productHistory.setActiveIndicator(true);
				productHistory.setOutlet(product.getOutlet());
				productHistory.setOutletQuantity(product.getCurrentInventory());
				productHistory.setProduct(product);
				productHistory.setQuantity(totalQunatity);
				productHistory.setChangeQuantity(product.getCurrentInventory());
				productHistory.setUser(product.getUserByCreatedBy());
				productHistory.setProductHistoryUuid(product.getProductUuid());
				productHistory.setCompany(company);
				getSessionFactory().getCurrentSession().save(productHistory);
			}
			return product;
		}
		catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateProductList(List<Product> productList, Company company) {
		// TODO Auto-generated method stub
		try{
			for(Product product: productList){
			getSessionFactory().getCurrentSession().update(product);
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
		catch(HibernateException ex){
			ex.printStackTrace();
		}
	}
	
	@Override
	public boolean deleteProduct(Product product,Actions actionName,  Company company) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(product);
			if(product.getStandardProduct().equalsIgnoreCase("true") && !product.getVariantProducts().equalsIgnoreCase("true")
					||!product.getStandardProduct().equalsIgnoreCase("true")){
				ProductHistory productHistory = new ProductHistory();
				productHistory.setAction(actionName.getActionName());
				productHistory.setActionDate(new Date());
				productHistory.setActiveIndicator(true);
				productHistory.setOutlet(product.getOutlet());
				productHistory.setOutletQuantity(product.getCurrentInventory());
				productHistory.setProduct(product);
				productHistory.setQuantity(0);
				productHistory.setChangeQuantity(product.getCurrentInventory());
				productHistory.setUser(product.getUserByCreatedBy());
				productHistory.setProductHistoryUuid(product.getProductUuid());
				productHistory.setCompany(company);
				getSessionFactory().getCurrentSession().save(productHistory);
			}
			return true;
		}
		catch(HibernateException ex){
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public Product getProductByProductId(int productId, int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Product> list = getSessionFactory().getCurrentSession()
			.createQuery("from Product where PRODUCT_ID = ? AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, productId)
			.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getAllProducts(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<Product> list = getSessionFactory().getCurrentSession()
					.createQuery("from Product where COMPANY_ASSOCIATION_ID = ? AND ACTIVE_INDICATOR = 1")
					.setParameter(0, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getAllProductsInclInActive(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<Product> list = getSessionFactory().getCurrentSession()
					.createQuery("from Product where COMPANY_ASSOCIATION_ID = ?")
					.setParameter(0, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public int getCountOfProductsByBrandId(int brandId , int companyId) {
		// TODO Auto-generated method stub
		try{
			int count = ((Long)getSessionFactory().getCurrentSession().createQuery("select count(*) "
					+ "from Product where COMPANY_ASSOCIATION_ID = "+companyId+" AND BRAND_ASSOCICATION_ID = "+brandId+"").uniqueResult()).intValue();
			return count;
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return 0;
	}
	@Override
	public int getCountOfProductsByProductTypeId(int productTypeId, int companyId) {
		// TODO Auto-generated method stub
		try{
			int count = ((Long)getSessionFactory().getCurrentSession().createQuery("select count(*) from Product where "
					+ "COMPANY_ASSOCIATION_ID = "+companyId+" AND PRODUCT_TYPE_ASSOCICATION_ID = "+productTypeId+"").uniqueResult()).intValue();
			return count;
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Product> getAllStandardProducts(int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Product> list = getSessionFactory().getCurrentSession()
			.createQuery("from Product where STANDARD_PRODUCT=? AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, "true")
			.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Product> getAllProductsByUuid(String uUid , int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Product> list = getSessionFactory().getCurrentSession()
			.createQuery("from Product where PRODUCT_UUID=? AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, uUid)
			.setParameter(1, companyId).list();;
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Product> getAllProductsByOutletId(int outletId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Product> list = getSessionFactory()
			.getCurrentSession()
			.createQuery(
					"from Product where OUTLET_ASSOCICATION_ID=?")
					.setParameter(0, outletId).list();
			return list;
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public int getMaxSKUCount(int outletId, int companyId) {
		Criteria criteria = getSessionFactory().getCurrentSession()
				.createCriteria(Product.class);
		criteria.add(Restrictions.eq("outlet.outletId", outletId));
		int id = 0;
		try {
			criteria.setProjection(Projections.max("productId"));
			id = (int) criteria.uniqueResult();
		} catch (Exception exception) {

		}
		return id+1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getAllProductsByCompanyIdGroupByProductUuId(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<Product> list = getSessionFactory().getCurrentSession()
					.createQuery("from Product where COMPANY_ASSOCIATION_ID = ?  AND ACTIVE_INDICATOR = 1 GROUP BY PRODUCT_UUID ")			
					.setParameter(0, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getAllProductsBySupplierIdByCompanyIdGroupByProductUuId(
			int supplierId, int companyId) {
		// TODO Auto-generated method stub
		try{
			List<Product> list = getSessionFactory().getCurrentSession()
					.createQuery("from Product where CONTACT_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID = ?  AND ACTIVE_INDICATOR = 1 GROUP BY PRODUCT_UUID ")
					.setParameter(0, supplierId)
					.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getAllProductsByOutletIdByCompanyIdGroupByProductUuId(
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		try{
			List<Product> list = getSessionFactory().getCurrentSession()
					.createQuery("from Product where OUTLET_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID = ?  AND ACTIVE_INDICATOR = 1  ")
					.setParameter(0, outletId)
					.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}


	@Override
	public int getCountOfInventoryByProductUuId(String productUuId,
			int companyId) {
		int count = 0;
		try {
			@SuppressWarnings("unchecked")
			List<Product> list = getSessionFactory()
			.getCurrentSession()
			.createQuery(
					"from Product where PRODUCT_UUID=? AND COMPANY_ASSOCIATION_ID = ?")
					.setParameter(0, productUuId)
					.setParameter(1, companyId).list();
			if(list!=null){
				for(Product product: list){
					if(product.isActiveIndicator()){
						count =count+product.getCurrentInventory();
					}

				}
			}

			return count;
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return count;
	}

	@Override
	public Product getProductByProductName(String productName, int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Product> list = getSessionFactory().getCurrentSession()
			.createQuery("from Product where PRODUCT_NAME = ? AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, productName)
			.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public int getCountOfMAXSKUForProductByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		try{
			int count = ((Long)getSessionFactory().getCurrentSession().createQuery("select count(*) from Product where "
					+ "COMPANY_ASSOCIATION_ID = "+companyId+"").uniqueResult()).intValue();
			System.out.println("Product Count: "+count);
			return count+1;
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getAllProductsByCategory(int company, int productType) {
		// TODO Auto-generated method stub
		try{
			List<Product> list = getSessionFactory().getCurrentSession()
					.createQuery("from Product where COMPANY_ASSOCIATION_ID = ? AND PRODUCT_TYPE_ASSOCICATION_ID = ? AND ACTIVE_INDICATOR = 1  ")
					.setParameter(0, company)
					.setParameter(1, productType).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getTopProductsByCategory(int company, int productType,
			int count, int pageNum) {
		try{
			List<Product> list = getSessionFactory().getCurrentSession()
					.createQuery("from Product where COMPANY_ASSOCIATION_ID = ? AND PRODUCT_TYPE_ASSOCICATION_ID = ? AND ACTIVE_INDICATOR = 1  ")
					.setParameter(0, company)
					.setParameter(1, productType).list().subList(pageNum, count);
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getAllActiveProducts() {
		try{
			List<Product> list = getSessionFactory().getCurrentSession()
					.createQuery("from Product where ACTIVE_INDICATOR = 1")
					.list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getAllActiveProductsByOutletIdCompanyId(int outletId,
			int companyId) {
		// TODO Auto-generated method stub
		try{
			List<Product> list = getSessionFactory().getCurrentSession()
					.createQuery("from Product where OUTLET_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID = ?  AND ACTIVE_INDICATOR = 1  ")
					.setParameter(0, outletId)
					.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ProductListsWrapper getAllProductsWarehouseandOutlet(int warehouseOutletId, int outletId, int companyId) {
		// TODO Auto-generated method stub
		try{
			ProductListsWrapper productListsWrapper = new ProductListsWrapper();
			long start = System.currentTimeMillis();
			List<Product> list = getSessionFactory().getCurrentSession()
					.createQuery("from Product where OUTLET_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID = ?  AND ACTIVE_INDICATOR = 1  ")
					.setParameter(0, outletId)
					.setParameter(1, companyId).list();
			if(list != null && list.size() > 0) {
				System.out.println("Outlet Product size: " + list.size());
			}else {
				System.out.println("Outlet Product size: 0");
			}			
			List<Product> list1 = getSessionFactory().getCurrentSession()
					.createQuery("from Product where OUTLET_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID = ?  AND ACTIVE_INDICATOR = 1  ")
					.setParameter(0, warehouseOutletId)
					.setParameter(1, companyId).list();
			if(list1 != null && list1.size() > 0) {
				System.out.println("Warehouse Product size: " + list1.size());
			}else {
				System.out.println("Warehouse Product size: 0");
			}
			List<ProductVariant> list2 = getSessionFactory().getCurrentSession()
					.createQuery("from ProductVariant where OUTLET_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID = ?  AND ACTIVE_INDICATOR = 1  ")
					.setParameter(0, outletId)
					.setParameter(1, companyId).list();
			if(list2 != null && list2.size() > 0) {
				System.out.println("Outlet Product Variant size: " + list2.size());
			}else {
				System.out.println("Outlet Product Variant size: 0");
			}
			List<ProductVariant> list3 = getSessionFactory().getCurrentSession()
					.createQuery("from ProductVariant where OUTLET_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID = ?  AND ACTIVE_INDICATOR = 1  ")
					.setParameter(0, warehouseOutletId)
					.setParameter(1, companyId).list();
			if(list3 != null && list3.size() > 0) {
				System.out.println("Warehouse Product Variant size: " + list3.size());
			}else {
				System.out.println("Warehouse Product Variant size: 0");
			}
			productListsWrapper.setOutletProducts(list);
			productListsWrapper.setWarehouseProducts(list1);
			productListsWrapper.setOutletProductVariants(list2);
			productListsWrapper.setWarehouseProductVariants(list3);		
			long end   = System.currentTimeMillis();
			NumberFormat formatter = new DecimalFormat("#0.00000");
			System.out.println("Execution time to get All Product + Product Variant is " + formatter.format((end - start) / 1000d) + " seconds");
			if(productListsWrapper!=null){

				return productListsWrapper;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	} 
	
	@SuppressWarnings("unchecked")
	@Override
	public ProductListsWrapper getAllProductsOutlet(int outletId, int companyId) {
		// TODO Auto-generated method stub
		try{
			ProductListsWrapper productListsWrapper = new ProductListsWrapper();
			long start = System.currentTimeMillis();
			List<Product> list = getSessionFactory().getCurrentSession()
					.createQuery("from Product where OUTLET_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID = ?  AND ACTIVE_INDICATOR = 1  ")
					.setParameter(0, outletId)
					.setParameter(1, companyId).list();
			if(list != null && list.size() > 0) {
				System.out.println("Outlet Product size: " + list.size());
			}else {
				System.out.println("Outlet Product size: 0");
			}
			List<ProductVariant> list2 = getSessionFactory().getCurrentSession()
					.createQuery("from ProductVariant where OUTLET_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID = ?  AND ACTIVE_INDICATOR = 1  ")
					.setParameter(0, outletId)
					.setParameter(1, companyId).list();
			if(list2 != null && list2.size() > 0) {
				System.out.println("Outlet Product Variant size: " + list2.size());
			}else {
				System.out.println("Outlet Product Variant size: 0");
			}			
			productListsWrapper.setOutletProducts(list);
			productListsWrapper.setOutletProductVariants(list2);		
			long end   = System.currentTimeMillis();
			NumberFormat formatter = new DecimalFormat("#0.00000");
			System.out.println("Execution time to get All Product + Product Variant is " + formatter.format((end - start) / 1000d) + " seconds");
			if(productListsWrapper!=null){

				return productListsWrapper;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
}
