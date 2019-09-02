/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.VariantAttributeValues;

/**
 * @author Yameen Bashir
 *
 */
public interface VariantAttributeValuesService {

	void addVariantAttributeValuesList(List<VariantAttributeValues> variantAttributeValuesList);
	VariantAttributeValues addVariantAttributeValues(VariantAttributeValues variantAttributeValues,int companyId);
	VariantAttributeValues updateVariantAttributeValues(VariantAttributeValues variantAttributeValues,int companyId);
	boolean deleteVariantAttributeValues(VariantAttributeValues variantAttributeValues,int companyId);
	VariantAttributeValues getVariantAttributeValuesByVariantAttributeValuesId(int variantAttributeValues,int companyId);
	List<VariantAttributeValues> getAllVariantAttributeValues(int companyId);
	List<String> getAllVariantAttributeValuesAttributeAndProducttId(int attributeId, String productVariantId,int companyId);
	List<VariantAttributeValues> getAllVariantAttributeValues(int attributeId, String productVariantId,int companyId);
}
