/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.VariantAttribute;

/**
 * @author Yameen Bashir
 *
 */
public interface VariantAttributeDAO {

	VariantAttribute addVariantAttribute(VariantAttribute variantAttribute,int companyId);
	VariantAttribute updateVariantAttribute(VariantAttribute variantAttribute,int companyId);
	boolean deleteVariantAttribute(VariantAttribute variantAttribute,int companyId);
	VariantAttribute getVariantAttributeByVariantAttributeId(int variantAttributeId,int companyId);
	List<VariantAttribute> getAllVariantAttributes(int companyId);
}
