/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Company;
import com.dowhile.dao.CompanyDAO;
import com.dowhile.service.CompanyService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class CompanyServiceImpl implements CompanyService{
	
	private CompanyDAO companyDAO;

	/**
	 * @return the companyDAO
	 */
	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	/**
	 * @param companyDAO the companyDAO to set
	 */
	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	@Override
	public Company addCompany(Company company) {
		// TODO Auto-generated method stub
		return getCompanyDAO().addCompany(company);
	}

	@Override
	public Company updateCompany(Company company) {
		// TODO Auto-generated method stub
		return getCompanyDAO().updateCompany(company);
	}

	@Override
	public boolean deleteCompany(Company company) {
		// TODO Auto-generated method stub
		return getCompanyDAO().deleteCompany(company);
	}

	@Override
	public List<Company> getCompanies() {
		// TODO Auto-generated method stub
		return getCompanyDAO().getCompanies();
	}
	
	@Override
	public Company getCompanyDetailsByCompanyID(int companyID) {
		// TODO Auto-generated method stub
		return getCompanyDAO().getCompanyDetailsByCompanyID(companyID);
	}

	
}
