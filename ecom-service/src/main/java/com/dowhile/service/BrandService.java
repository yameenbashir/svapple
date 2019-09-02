/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.Brand;

/**
 * @author Yameen Bashir
 *
 */
public interface BrandService {

	Brand addBrand(Brand brand, int companyId);
	Brand updateBrand(Brand brand, int companyId);
	boolean deleteBrand(Brand brand, int companyId);
	Brand getBrandByBrandId(int brandId, int companyId);
	List<Brand> getAllBrands(int companyId);
}
