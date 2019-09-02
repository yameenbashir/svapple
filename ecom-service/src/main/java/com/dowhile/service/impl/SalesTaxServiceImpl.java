/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.SalesTax;
import com.dowhile.TimeZone;
import com.dowhile.dao.SalesTaxDAO;
import com.dowhile.service.SalesTaxService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class SalesTaxServiceImpl implements SalesTaxService{

	private SalesTaxDAO salesTaxDAO;
	
	/**
	 * @return the salesTaxDAO
	 */
	public SalesTaxDAO getSalesTaxDAO() {
		return salesTaxDAO;
	}

	/**
	 * @param salesTaxDAO the salesTaxDAO to set
	 */
	public void setSalesTaxDAO(SalesTaxDAO salesTaxDAO) {
		this.salesTaxDAO = salesTaxDAO;
	}

	@Override
	public SalesTax addSalesTax(SalesTax salesTax,int companyId) {
		// TODO Auto-generated method stub
		return getSalesTaxDAO().addSalesTax(salesTax,companyId);
	}

	@Override
	public SalesTax updateSalesTax(SalesTax salesTax,int companyId) {
		// TODO Auto-generated method stub
		return getSalesTaxDAO().updateSalesTax(salesTax,companyId);
	}

	@Override
	public boolean deleteSalesTax(SalesTax salesTax,int companyId) {
		// TODO Auto-generated method stub
		return getSalesTaxDAO().deleteSalesTax(salesTax,companyId);
	}

	@Override
	public SalesTax getSalesTaxBySalesTaxId(int salesTaxId,int companyId) {
		// TODO Auto-generated method stub
		return getSalesTaxDAO().getSalesTaxBySalesTaxId(salesTaxId,companyId);
	}

	@Override
	public List<SalesTax> GetAllSalesTax(int companyId) {
		return getSalesTaxDAO().GetAllSalesTax(companyId);
	}


}

