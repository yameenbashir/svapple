/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.Company;

/**
 * @author Yameen Bashir
 *
 */
public interface CompanyDAO {
	
	Company addCompany(Company company);
	Company updateCompany(Company company);
	boolean deleteCompany(Company company);
	public List<Company> getCompanies();
	Company getCompanyDetailsByCompanyID(int companyId);

}
