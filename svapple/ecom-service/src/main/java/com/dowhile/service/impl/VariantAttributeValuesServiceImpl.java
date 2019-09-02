/**
 * 
 */
package com.dowhile.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.VariantAttributeValues;
import com.dowhile.dao.VariantAttributeValuesDAO;
import com.dowhile.service.VariantAttributeValuesService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class VariantAttributeValuesServiceImpl implements VariantAttributeValuesService{

	private VariantAttributeValuesDAO variantAttributeValuesDAO;
	
	/**
	 * @return the variantAttributeValuesDAO
	 */
	public VariantAttributeValuesDAO getVariantAttributeValuesDAO() {
		return variantAttributeValuesDAO;
	}

	/**
	 * @param variantAttributeValuesDAO the variantAttributeValuesDAO to set
	 */
	public void setVariantAttributeValuesDAO(
			VariantAttributeValuesDAO variantAttributeValuesDAO) {
		this.variantAttributeValuesDAO = variantAttributeValuesDAO;
	}

	@Override
	public VariantAttributeValues addVariantAttributeValues(
			VariantAttributeValues variantAttributeValues,int companyId) {
		// TODO Auto-generated method stub
		return getVariantAttributeValuesDAO().addVariantAttributeValues(variantAttributeValues,companyId);
	}

	@Override
	public VariantAttributeValues updateVariantAttributeValues(
			VariantAttributeValues variantAttributeValues,int companyId) {
		// TODO Auto-generated method stub
		return getVariantAttributeValuesDAO().updateVariantAttributeValues(variantAttributeValues,companyId);
	}

	@Override
	public boolean deleteVariantAttributeValues(
			VariantAttributeValues variantAttributeValues,int companyId) {
		// TODO Auto-generated method stub
		return getVariantAttributeValuesDAO().deleteVariantAttributeValues(variantAttributeValues,companyId);
	}

	@Override
	public VariantAttributeValues getVariantAttributeValuesByVariantAttributeValuesId(
			int variantAttributeValues,int companyId) {
		// TODO Auto-generated method stub
		return getVariantAttributeValuesDAO().getVariantAttributeValuesByVariantAttributeValuesId(variantAttributeValues,companyId);
	}

	@Override
	public List<VariantAttributeValues> getAllVariantAttributeValues(int companyId) {
		// TODO Auto-generated method stub
		return getVariantAttributeValuesDAO().getAllVariantAttributeValues(companyId);
	}

	@Override
	public List<String> getAllVariantAttributeValuesAttributeAndProducttId(
			int attributeId, String productVariantId,int companyId) {
		// TODO Auto-generated method stub
		List<String> variantAttrValues = new ArrayList<>();
		List<String> variantAttributeValues =  getVariantAttributeValuesDAO().getAllVariantAttributeValuesAttributeAndProducttId(attributeId, productVariantId,companyId);
		if(variantAttributeValues!=null){
			for(String variantAttributeValue: variantAttributeValues){
				variantAttrValues.add(variantAttributeValue);
			}
		}
		
		return variantAttrValues;
	}

	@Override
	public List<VariantAttributeValues> getAllVariantAttributeValues(
			int attributeId, String productVariantId, int companyId) {
		// TODO Auto-generated method stub
		return getVariantAttributeValuesDAO().getAllVariantAttributeValues(attributeId, productVariantId, companyId);
	}

	@Override
	public void addVariantAttributeValuesList(
			List<VariantAttributeValues> variantAttributeValuesList) {
		// TODO Auto-generated method stub
		getVariantAttributeValuesDAO().addVariantAttributeValuesList(variantAttributeValuesList);
	}

}
