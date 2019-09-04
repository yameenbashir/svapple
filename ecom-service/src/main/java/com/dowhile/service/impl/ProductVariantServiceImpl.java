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
import com.dowhile.ProductVariant;
import com.dowhile.constant.Actions;
import com.dowhile.dao.ProductVariantDAO;
import com.dowhile.service.ProductVariantService;
/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class ProductVariantServiceImpl implements ProductVariantService{

	private ProductVariantDAO productVariantDAO;
	
	/**
	 * @return the productVariantDAO
	 */
	public ProductVariantDAO getProductVariantDAO() {
		return productVariantDAO;
	}

	/**
	 * @param productVariantDAO the productVariantDAO to set
	 */
	public void setProductVariantDAO(ProductVariantDAO productVariantDAO) {
		this.productVariantDAO = productVariantDAO;
	}

	@Override
	public ProductVariant addProductVariant(ProductVariant productVariant,Actions actionName,int totalQunatity, Company company,String productUuId) {
		// TODO Auto-generated method stub
		productVariant.setProductUuid(productUuId);
		return getProductVariantDAO().addProductVariant(productVariant, actionName, totalQunatity,company);
	}

	@Override
	public void addProductVariantList(List<ProductVariant> productVariant, Company company) {
		// TODO Auto-generated method stub
		getProductVariantDAO().addProductVariantList(productVariant,company);
	}
	
	@Override
	public ProductVariant updateProductVariant(ProductVariant productVariant,Actions actionName,int totalQunatity,Company company) {
		// TODO Auto-generated method stub
		return getProductVariantDAO().updateProductVariant(productVariant, actionName, totalQunatity,company);
	}
	
	@Override
	public void updateProductVariantList(List<ProductVariant> productVariant,Company company) {
		// TODO Auto-generated method stub
		getProductVariantDAO().updateProductVariantList(productVariant, company);
	}

	@Override
	public boolean deleteProductVariant(ProductVariant productVariant,Actions actionName, Company company) {
		// TODO Auto-generated method stub
		productVariant.setCompany(company);
		return getProductVariantDAO().deleteProductVariant(productVariant, actionName,0);
	}

	@Override
	public ProductVariant getProductVariantByProductVariantId(
			int productVariantId, int companyId) {
		// TODO Auto-generated method stub
		return getProductVariantDAO().getProductVariantByProductVariantId(productVariantId,companyId);
	}

	@Override
	public List<ProductVariant> getAllProductVariants(int companyId) {
		// TODO Auto-generated method stub
		return getProductVariantDAO().getAllProductVariants(companyId);
	}

	@Override
	public List<ProductVariant> getAllProductVariantsInclInActive(int companyId) {
		// TODO Auto-generated method stub
		return getProductVariantDAO().getAllProductVariantsInclInActive(companyId);
	}
	
	@Override
	public List<ProductVariant> getVarientsByProductId(int productid, int companyId) {
		// TODO Auto-generated method stub
		return getProductVariantDAO().getVarientsByProductId(productid,companyId);
	}

	@Override
	public int getContOfVariantsByProductId(int productId, int companyId) {
		// TODO Auto-generated method stub
		return getProductVariantDAO().getContOfVariantsByProductId(productId,companyId);
	}

	@Override
	public int getCountOfInventoryByProductUuId(String productUuId, int companyId) {
		// TODO Auto-generated method stub
		return getProductVariantDAO().getCountOfInventoryByProductUuId(productUuId,companyId);
	}

	@Override
	public List<ProductVariant> getAllProductVariantsByOutletId(int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getProductVariantDAO().getAllActiveProductVariantsByOutletIdCompanyId(outletId,companyId);
	}

	@Override
	public List<ProductVariant> getAllProductVariantsByUuid(String uUid, int companyId) {
		// TODO Auto-generated method stub
		return getProductVariantDAO().getAllProductVariantsByUuid(uUid,companyId);
	}

	@Override
	public List<ProductVariant> getAllProductVariantsGroupbyUuid(int companyId) {
		// TODO Auto-generated method stub
		return getProductVariantDAO().getAllProductVariantsGroupbyUuid(companyId);
	}
	
	@Override
	public List<ProductVariant> getAllProductVariantsByOutletIdGroupbyUuid(int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getProductVariantDAO().getAllProductVariantsByOutletIdGroupbyUuid(outletId, companyId);
	}
	
	@Override
	public int getCountOfInventoryByProductVariantUuId(
			String productVariantUuId, int companyId) {
		// TODO Auto-generated method stub
		return getProductVariantDAO().getCountOfInventoryByProductVariantUuId(productVariantUuId, companyId);
	}

	@Override
	public int getCountOfInventoryByProductVariantUuIdOutletId(
			String productVariantUuId, int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getProductVariantDAO().getCountOfInventoryByProductVariantUuIdOutletId(productVariantUuId, outletId, companyId);
	}

	@Override
	public int getCountOfMAXSKUForProductVariantByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		return getProductVariantDAO().getCountOfMAXSKUForProductVariantByCompanyId(companyId);
	}
	@Override
	public Map<Integer, ProductVariant> getProductsVariantMapByProductId(
			int companyId) {
		List<ProductVariant> products = getProductVariantDAO().getAllProductVariants(companyId);
		Map<Integer, ProductVariant> map = new HashMap<Integer, ProductVariant>();
		if(products!=null){
			for(ProductVariant product : products){
				if(map.get(product.getProductVariantId())==null){
					map.put(product.getProductVariantId(), product);
				}
			}
		}
		
		return map;
	}

	@Override
	public Map<String, List<ProductVariant>> getAllProductsVariantMapByUuid(
			int companyId) {
		List<ProductVariant> products = getProductVariantDAO().getAllProductVariants(companyId);
		Map<String, List<ProductVariant>> map = new HashMap<String, List<ProductVariant>>();
		if(products!=null){
			for(ProductVariant product : products){
				List<ProductVariant> productsNew = new ArrayList<ProductVariant>();
				
				if(map.get(product.getProductVariantUuid())==null){
					productsNew.add(product);
					map.put(product.getProductVariantUuid(), productsNew);
				}else{
					productsNew = map.get(product.getProductVariantUuid());
					productsNew.add(product);
					map.put(product.getProductVariantUuid(), productsNew);
				}
			}
			
		}

		return map;
	}

	@Override
	public List<ProductVariant> getAllActiveProductVariants() {
		// TODO Auto-generated method stub
		return getProductVariantDAO().getAllActiveProductVariants();
	}

	@Override
	public Map<Integer, ProductVariant> getAllActiveProductsVariantMapByOutletIdCompanyId(
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		List<ProductVariant> products = getProductVariantDAO().getAllActiveProductVariantsByOutletIdCompanyId(outletId, companyId);
		Map<Integer, ProductVariant> map = new HashMap<Integer, ProductVariant>();
		if(products!=null){
			for(ProductVariant product : products){
				if(map.get(product.getProductVariantId())==null){
					map.put(product.getProductVariantId(), product);
				}
			}
		}
		return map;
	}
}
