/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.Currency;
import com.dowhile.Register;

/**
 * @author Hassan 
 *
 */
public interface CurrencyService {

	public List<Currency> GetAllCurrencies();
	public Currency GetCurrencyById(int currencyId);

}
