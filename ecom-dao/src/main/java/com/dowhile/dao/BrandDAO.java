/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.Brand;

/**
 * @author Yameen Bashir
 *
 */
public interface BrandDAO {

	Brand addBrand(Brand brand, int companyId);
	Brand updateBrand(Brand brand, int companyId);
	boolean deleteBrand(Brand brand, int companyId);
	Brand getBrandByBrandId(int brandId, int companyId);
	List<Brand> getAllBrands(int companyId);
}
