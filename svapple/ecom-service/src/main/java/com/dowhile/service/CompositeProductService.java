/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.CompositeProduct;
import com.dowhile.constant.Actions;

/**
 * @author Yameen Bashir
 *
 */
public interface CompositeProductService {

	CompositeProduct addCompositeProduct(CompositeProduct compositeProduct,Actions actionName,int totalQunatity, int companyId,String productUuId);
	CompositeProduct updateCompositeProduct(CompositeProduct compositeProduct,Actions actionName,int totalQunatity, int companyId);
	boolean deleteCompositeProduct(CompositeProduct compositeProduct,Actions actionName, int companyId);
	CompositeProduct getCompositeProductByCompositeProductId(int compositeProductId, int companyId);
	List<CompositeProduct> getAllCompositeProducts(int companyId);
	List<CompositeProduct> getAllCompositeProductsByProductIdOultetId(int productId,int outletId, int companyId);
	List<CompositeProduct> getAllCompositeProductsByUuid(String uUid, int companyId);
	int getCountOfInventoryByProductUuId(String productUuId , int companyId);
	int getCountOfInventoryByCompositeProductUuId(String compositeProductUuId , int companyId);
	
}
