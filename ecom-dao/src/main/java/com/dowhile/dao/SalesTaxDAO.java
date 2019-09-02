/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.SalesTax;
import com.dowhile.TimeZone;

/**
 * @author Yameen Bashir
 *
 */
public interface SalesTaxDAO {
	
	SalesTax addSalesTax(SalesTax salesTax,int companyId);
	SalesTax updateSalesTax(SalesTax salesTax,int companyId);
	boolean deleteSalesTax(SalesTax salesTax,int companyId);
	SalesTax getSalesTaxBySalesTaxId(int salesTaxId,int companyId);
	List<SalesTax> GetAllSalesTax(int companyId);

}
