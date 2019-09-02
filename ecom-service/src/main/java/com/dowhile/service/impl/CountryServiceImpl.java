/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Country;
import com.dowhile.dao.CountryDAO;
import com.dowhile.service.CountryService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class CountryServiceImpl implements CountryService{

	private CountryDAO countryDAO;
	/**
	 * @return the countryDAO
	 */
	public CountryDAO getCountryDAO() {
		return countryDAO;
	}

	/**
	 * @param countryDAO the countryDAO to set
	 */
	public void setCountryDAO(CountryDAO countryDAO) {
		this.countryDAO = countryDAO;
	}

	@Override
	public Country addCountry(Country country) {
		// TODO Auto-generated method stub
		return getCountryDAO().addCountry(country);
	}

	@Override
	public Country updateCountry(Country country) {
		// TODO Auto-generated method stub
		return getCountryDAO().updateCountry(country);
	}

	@Override
	public boolean deleteCountry(Country country) {
		// TODO Auto-generated method stub
		return getCountryDAO().deleteCountry(country);
	}

	@Override
	public Country getCountryByCountryId(int countryId) {
		// TODO Auto-generated method stub
		return getCountryDAO().getCountryByCountryId(countryId);
	}

	@Override
	public List<Country> GetAllCountry() {
		// TODO Auto-generated method stub
		return getCountryDAO().GetAllCountry();
	}
	
}
