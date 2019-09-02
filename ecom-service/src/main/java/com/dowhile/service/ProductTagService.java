/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.ProductTag;

/**
 * @author Yamen Bashir
 *
 */
public interface ProductTagService {

	ProductTag addProductTag(ProductTag productTag, int companyId);
	ProductTag updateProductTag(ProductTag productTag, int companyId);
	boolean deleteProductTag(ProductTag productTag, int companyId);
	ProductTag getProductTagByProductTagIdProductIdCompanyId(int productTagId, int productId, int companyId);
    int getCountOfProductTagsByTagId(int tagId, int companyId);
    List<ProductTag> getAllProductTagsByProductId(int productId, int companyId);
    List<ProductTag> getAllProductTagsByCompanyId(int companyId);
}
