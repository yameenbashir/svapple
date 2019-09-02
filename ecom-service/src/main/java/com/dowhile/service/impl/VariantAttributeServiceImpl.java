/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.VariantAttribute;
import com.dowhile.dao.VariantAttributeDAO;
import com.dowhile.service.VariantAttributeService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class VariantAttributeServiceImpl implements VariantAttributeService{

	private VariantAttributeDAO variantAttributeDAO;
	
	/**
	 * @return the variantAttributeDAO
	 */
	public VariantAttributeDAO getVariantAttributeDAO() {
		return variantAttributeDAO;
	}

	/**
	 * @param variantAttributeDAO the variantAttributeDAO to set
	 */
	public void setVariantAttributeDAO(VariantAttributeDAO variantAttributeDAO) {
		this.variantAttributeDAO = variantAttributeDAO;
	}

	@Override
	public VariantAttribute addVariantAttribute(
			VariantAttribute variantAttribute,int companyId) {
		// TODO Auto-generated method stub
		return getVariantAttributeDAO().addVariantAttribute(variantAttribute,companyId);
	}

	@Override
	public VariantAttribute updateVariantAttribute(
			VariantAttribute variantAttribute,int companyId) {
		// TODO Auto-generated method stub
		return getVariantAttributeDAO().updateVariantAttribute(variantAttribute,companyId);
	}

	@Override
	public boolean deleteVariantAttribute(VariantAttribute variantAttribute,int companyId) {
		// TODO Auto-generated method stub
		return getVariantAttributeDAO().deleteVariantAttribute(variantAttribute,companyId);
	}

	@Override
	public VariantAttribute getVariantAttributeByVariantAttributeId(
			int variantAttributeId,int companyId) {
		// TODO Auto-generated method stub
		return getVariantAttributeDAO().getVariantAttributeByVariantAttributeId(variantAttributeId,companyId);
	}

	@Override
	public List<VariantAttribute> getAllVariantAttributes(int companyId) {
		// TODO Auto-generated method stub
		return getVariantAttributeDAO().getAllVariantAttributes(companyId);
	}

}
