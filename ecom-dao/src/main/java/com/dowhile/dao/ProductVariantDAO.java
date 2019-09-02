/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.Company;
import com.dowhile.ProductVariant;
import com.dowhile.constant.Actions;

/**
 * @author Yameen Bashir
 *
 */
public interface ProductVariantDAO {

	ProductVariant addProductVariant(ProductVariant productVariant,Actions actionName,int totalQunatity, Company company);
	void addProductVariantList(List<ProductVariant> productVariant, Company company);
	ProductVariant updateProductVariant(ProductVariant productVariant,Actions actionName,int totalQunatity,Company company);
	void updateProductVariantList(List<ProductVariant> productVariant,Company company);
	boolean deleteProductVariant(ProductVariant productVariant,Actions actionName, int companyId);
	ProductVariant getProductVariantByProductVariantId(int productVariantId, int companyId);
	List<ProductVariant> getAllProductVariants(int companyId);
	List<ProductVariant> getAllProductVariantsInclInActive(int companyId);
	List<ProductVariant> getVarientsByProductId(int productid, int companyId);
	int getContOfVariantsByProductId(int productId, int companyId);
	int getCountOfInventoryByProductUuId(String productUuId, int companyId);
	List<ProductVariant> getAllProductVariantsByOutletId(int outletId, int companyId);
	List<ProductVariant> getAllProductVariantsByUuid(String uUid, int companyId);
	List<ProductVariant> getAllProductVariantsGroupbyUuid(int companyId);
	List<ProductVariant> getAllProductVariantsByOutletIdGroupbyUuid(int outletId, int companyId);	
	int getCountOfInventoryByProductVariantUuId(String productVariantUuId , int companyId);
	int getCountOfInventoryByProductVariantUuIdOutletId(String productVariantUuId ,int outletId, int companyId);
	int getCountOfMAXSKUForProductVariantByCompanyId(int companyId);
	
	
}
