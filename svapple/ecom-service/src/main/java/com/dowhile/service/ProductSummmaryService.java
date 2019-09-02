/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.ProductSummmary;

/**
 * @author Yameen Bashir
 *
 */
public interface ProductSummmaryService {

	List<ProductSummmary> getAllProductSummmaryByCompanyIdOutletId(int companyId, int outletId);
	List<ProductSummmary> getAllProductSummmaryByCompanyId(int companyId);
	List<ProductSummmary> getTenNewProductSummmaryByCompanyIdOutletId(int companyId, int outletId);
}
