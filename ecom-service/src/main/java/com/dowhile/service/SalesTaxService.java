/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.SalesTax;

/**
 * @author Yameen Bashir
 *
 */
public interface SalesTaxService {

	SalesTax addSalesTax(SalesTax salesTax,int companyId);
	SalesTax updateSalesTax(SalesTax salesTax,int companyId);
	boolean deleteSalesTax(SalesTax salesTax,int companyId);
	SalesTax getSalesTaxBySalesTaxId(int salesTaxId,int companyId);
	List<SalesTax> GetAllSalesTax(int companyId);
}
