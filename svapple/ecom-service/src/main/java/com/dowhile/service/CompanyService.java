/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.Company;
import com.dowhile.Outlet;

/**
 * @author Yameen Bashir
 *
 */
public interface CompanyService {

	Company addCompany(Company company);
	Company updateCompany(Company company);
	boolean deleteCompany(Company company);
	public List<Company> getCompanies();
	Company getCompanyDetailsByCompanyID(int companyID);
}
