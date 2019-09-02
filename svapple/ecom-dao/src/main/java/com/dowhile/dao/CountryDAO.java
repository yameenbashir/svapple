/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.Country;

/**
 * @author Yameen Bashir
 *
 */
public interface CountryDAO {
	
	Country addCountry(Country country);
	Country updateCountry(Country country);
	boolean deleteCountry(Country country);
	Country getCountryByCountryId(int countryId);
	List<Country> GetAllCountry();
}
