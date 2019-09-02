/**
 * 
 */
package com.dowhile.dao;

import java.util.List;
import com.dowhile.Currency;

/**
 * @author Hassan
 *
 */
public interface CurrencyDAO {
	
	List<Currency> GetAllCurrencies();
	
	Currency GetCurrencyById(int currencyId);
	

}
