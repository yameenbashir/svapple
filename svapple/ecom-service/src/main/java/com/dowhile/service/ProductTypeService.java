/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.ProductType;

/**
 * @author Yameen Bashir
 *
 */
public interface ProductTypeService {

	ProductType addProductType(ProductType productType, int companyId);
	ProductType updateProductType(ProductType productType, int companyId);
	boolean deleteProductType(ProductType productType, int companyId);
	ProductType getProductTypeByProductTypeId(int productTypeId, int companyId);
	List<ProductType> getAllProductTypes(int companyId);
	public List<ProductType> getTopProductTypes(int companyId,int count);
}
