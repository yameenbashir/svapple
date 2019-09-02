/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Brand;
import com.dowhile.dao.BrandDAO;
import com.dowhile.service.BrandService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class BrandServiceImpl implements BrandService{

	private BrandDAO brandDAO;
	
	/**
	 * @return the brandDAO
	 */
	public BrandDAO getBrandDAO() {
		return brandDAO;
	}

	/**
	 * @param brandDAO the brandDAO to set
	 */
	public void setBrandDAO(BrandDAO brandDAO) {
		this.brandDAO = brandDAO;
	}

	@Override
	public Brand addBrand(Brand brand, int companyId) {
		// TODO Auto-generated method stub
		return getBrandDAO().addBrand(brand,companyId);
	}

	@Override
	public Brand updateBrand(Brand brand, int companyId) {
		// TODO Auto-generated method stub
		return getBrandDAO().updateBrand(brand,companyId);
	}

	@Override
	public boolean deleteBrand(Brand brand, int companyId) {
		// TODO Auto-generated method stub
		return getBrandDAO().deleteBrand(brand,companyId);
	}

	@Override
	public Brand getBrandByBrandId(int brandId, int companyId) {
		// TODO Auto-generated method stub
		return getBrandDAO().getBrandByBrandId(brandId,companyId);
	}

	@Override
	public List<Brand> getAllBrands(int companyId) {
		// TODO Auto-generated method stub
		return getBrandDAO().getAllBrands(companyId);
	}

}
