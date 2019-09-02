/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.Country;

/**
 * @author Yameen Bashir
 *
 */
public interface CountryService {

	Country addCountry(Country country);
	Country updateCountry(Country country);
	boolean deleteCountry(Country country);
	Country getCountryByCountryId(int countryId);
	List<Country> GetAllCountry();
}
