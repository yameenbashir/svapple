/**
 * 
 */
package com.dowhile.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Company;
import com.dowhile.Product;
import com.dowhile.constant.Actions;
import com.dowhile.dao.ProductDAO;
import com.dowhile.service.ProductService;
import com.dowhile.wrapper.ProductListsWrapper;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class ProductServiceImpl implements ProductService{

	private ProductDAO productDAO;
	
	/**
	 * @return the productDAO
	 */
	public ProductDAO getProductDAO() {
		return productDAO;
	}

	/**
	 * @param productDAO the productDAO to set
	 */
	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}

	@Override
	public Product addProduct(Product product,Actions actionName,int totalQunatity, Company company) {
		// TODO Auto-generated method stub
		return getProductDAO().addProduct(product,actionName,totalQunatity,company);
	}

	@Override
	public void addProductList(List<Product> product, Company company) {
		// TODO Auto-generated method stub
		getProductDAO().addProductList(product,company);
	}
	
	@Override
	public Product updateProduct(Product product,Actions actionName,int totalQunatity, Company company) {
		// TODO Auto-generated method stub
		return getProductDAO().updateProduct(product,actionName,totalQunatity,company);
	}

	@Override
	public void updateProductList(List<Product> product, Company company) {
		// TODO Auto-generated method stub
		getProductDAO().updateProductList(product,company);
	}
	
	@Override
	public boolean deleteProduct(Product product,Actions actionName, Company company) {
		// TODO Auto-generated method stub
		return getProductDAO().deleteProduct(product,actionName,company);
	}

	@Override
	public Product getProductByProductId(int productId, int companyId) {
		// TODO Auto-generated method stub
		return getProductDAO().getProductByProductId(productId,companyId);
	}

	@Override
	public List<Product> getAllProducts(int companyId) {
		// TODO Auto-generated method stub
		return getProductDAO().getAllProducts(companyId);
	}

	@Override
	public List<Product> getAllProductsInclInActive(int companyId) {
		// TODO Auto-generated method stub
		return getProductDAO().getAllProductsInclInActive(companyId);
	}
	
	@Override
	public int getCountOfProductsByBrandId(int brandId, int companyId) {
		// TODO Auto-generated method stub
		return getProductDAO().getCountOfProductsByBrandId(brandId,companyId);
	}

	@Override
	public int getCountOfProductsByProductTypeId(int productTypeId, int companyId) {
		// TODO Auto-generated method stub
		return getProductDAO().getCountOfProductsByProductTypeId(productTypeId,companyId);
	}

	@Override
	public List<Product> getAllStandardProducts( int companyId) {
		// TODO Auto-generated method stub
		return getProductDAO().getAllStandardProducts(companyId);
	}

	@Override
	public List<Product> getAllProductsByUuid(String uUid, int companyId) {
		// TODO Auto-generated method stub
		return getProductDAO().getAllProductsByUuid(uUid,companyId);
	}

	@Override
	public List<Product> getAllProductsByOutletId(int outletId) {
		// TODO Auto-generated method stub
		return getProductDAO().getAllProductsByOutletId(outletId);
	}
	
	@Override
	public List<Product> getAllProductsByCategory(int compant, int productType) {
		// TODO Auto-generated method stub
		return getProductDAO().getAllProductsByCategory(compant, productType);
	}

	@Override
	public int getMaxSKUCount(int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getProductDAO().getMaxSKUCount(outletId, companyId);
	}

	@Override
	public List<Product> getAllProductsByCompanyIdGroupByProductUuId(int companyId) {
		// TODO Auto-generated method stub
		return getProductDAO().getAllProductsByCompanyIdGroupByProductUuId(companyId);
	}

	
	@Override
	public List<Product> getAllProductsBySupplierIdByCompanyIdGroupByProductUuId(
			int supplierId, int companyId) {
		// TODO Auto-generated method stub
		return getProductDAO().getAllProductsBySupplierIdByCompanyIdGroupByProductUuId(supplierId, companyId);
	}

	@Override
	public List<Product> getAllProductsByOutletIdByCompanyIdGroupByProductUuId(
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getProductDAO().getAllProductsByOutletIdByCompanyIdGroupByProductUuId(outletId, companyId);
	}
	
	@Override
	public int getCountOfInventoryByProductUuId(String productUuId,
			int companyId) {
		// TODO Auto-generated method stub
		return getProductDAO().getCountOfInventoryByProductUuId(productUuId, companyId);
	}

	@Override
	public Product getProductByProductName(String productName, int companyId) {
		// TODO Auto-generated method stub
		return getProductDAO().getProductByProductName(productName, companyId);
	}

	@Override
	public int getCountOfMAXSKUForProductByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		return getProductDAO().getCountOfMAXSKUForProductByCompanyId(companyId);
	}

	@Override
	public Map<Integer, Product> getProductsMapByProductId(
			int companyId) {
		List<Product> products = getProductDAO().getAllProducts(companyId);
		Map<Integer, Product> map = new HashMap<Integer, Product>();
		if(products!=null){
			for(Product product : products){
				if(map.get(product.getProductId())==null){
					map.put(product.getProductId(), product);
				}
			}
		}
		
		return map;
	}

	@Override
	public Map<String, List<Product>> getAllProductsMapByUuid(
			int companyId) {
		List<Product> products = getProductDAO().getAllProducts(companyId);
		Map<String, List<Product>> map = new HashMap<String, List<Product>>();
		if(products!=null){
			for(Product product : products){
				List<Product> productsNew = new ArrayList<Product>();
				
				if(map.get(product.getProductUuid())==null){
					productsNew.add(product);
					map.put(product.getProductUuid(), productsNew);
				}else{
					productsNew = map.get(product.getProductUuid());
					productsNew.add(product);
					map.put(product.getProductUuid(), productsNew);
				}
			}
		}
		
		return map;
	}

	@Override
	public List<Product> getTopProductsByCategory(int compant, int productType,
			int count, int pageNum) {
		// TODO Auto-generated method stub
		return getProductDAO().getTopProductsByCategory(compant, productType, count, pageNum);
	}

	@Override
	public List<Product> getAllActiveProducts() {
		// TODO Auto-generated method stub
		return getProductDAO().getAllActiveProducts();
	}

	@Override
	public Map<Integer, Product> getAllActiveProductsMapByOutletIdCompanyId(
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		List<Product> products = getProductDAO().getAllActiveProductsByOutletIdCompanyId(outletId, companyId);
		Map<Integer, Product> map = new HashMap<Integer, Product>();
		if(products!=null){
			for(Product product : products){
				if(map.get(product.getProductId())==null){
					map.put(product.getProductId(), product);
				}
			}
		}
		return map;
	}

	@Override
	public List<Product> getAllActiveProductsByOutletIdCompanyId(int outletId,
			int companyId) {
		// TODO Auto-generated method stub
		return getProductDAO().getAllActiveProductsByOutletIdCompanyId(outletId, companyId);
	}
	
	@Override
	public ProductListsWrapper getAllProductsWarehouseandOutlet(int warehouseOutletId, int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getProductDAO().getAllProductsWarehouseandOutlet(warehouseOutletId, outletId, companyId);
	}
}
