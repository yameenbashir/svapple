/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.Company;
import com.dowhile.Product;
import com.dowhile.constant.Actions;
import com.dowhile.wrapper.ProductListsWrapper;

/**
 * @author Yameen Bashir
 *
 */
public interface ProductDAO {

	Product addProduct(Product product,Actions actionName,int totalQunatity, Company company);
	void addProductList(List<Product> product,Company company);
	Product updateProduct(Product product, Actions actionName,int totalQunatity, Company company);
	void updateProductList(List<Product> product, Company company);
	boolean deleteProduct(Product product, Actions actionName, Company company);
	Product getProductByProductId(int productId, int companyId);
	Product getProductByProductName(String productName, int companyId);
	List<Product> getAllProducts( int companyId);
	List<Product> getAllProductsInclInActive( int companyId);
	List<Product> getAllStandardProducts(int companyId);
	int getCountOfProductsByBrandId(int brandId, int companyId);
	int getCountOfProductsByProductTypeId(int productTypeId, int companyId);
	List<Product> getAllProductsByUuid(String uUid, int companyId);
	List<Product> getAllProductsByOutletId(int outletId);
	int getMaxSKUCount(int outletId, int companyId);
	List<Product> getAllProductsByCompanyIdGroupByProductUuId(int companyId);
	List<Product> getAllProductsBySupplierIdByCompanyIdGroupByProductUuId(int supplierId, int companyId);	
	List<Product> getAllProductsByOutletIdByCompanyIdGroupByProductUuId(int outletId, int companyId);
	int getCountOfInventoryByProductUuId(String productUuId , int companyId);
	int getCountOfMAXSKUForProductByCompanyId(int companyId);
	public List<Product> getAllProductsByCategory(int compant, int productType);
	public List<Product> getTopProductsByCategory(int compant, int productType,int count,int pageNum);
	List<Product> getAllActiveProducts();
	List<Product> getAllActiveProductsByOutletIdCompanyId(int outletId,int companyId);
	ProductListsWrapper getAllProductsWarehouseandOutlet(int warehouseOutletId, int outletId, int companyId);
	ProductListsWrapper getAllProductsOutlet(int outletId, int companyId);
}
