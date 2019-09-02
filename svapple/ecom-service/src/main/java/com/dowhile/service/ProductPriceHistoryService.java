/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.ProductPriceHistory;

/**
 * @author Hafiz Yameen Bashir
 *
 */
public interface ProductPriceHistoryService {

	ProductPriceHistory addProductPriceHistory(ProductPriceHistory productPriceHistory);
	ProductPriceHistory updateProductPriceHistory(ProductPriceHistory productPriceHistory);
	boolean deleteProductPriceHistory(ProductPriceHistory productPriceHistory);
	List<ProductPriceHistory> getAllProductPriceHistoryByCompanyIdOutletId(int companyId,int outletId);
	List<ProductPriceHistory> getAllProductPriceHistoryByCompanyId(int companyId);
	List<ProductPriceHistory> getAllProductPriceHistoryByCompanyIdOutletIdProductId(int companyId,int outletId,int productId);
}
