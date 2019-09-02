/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Currency;
import com.dowhile.dao.CurrencyDAO;
import com.dowhile.service.CurrencyService;

/**
 * @author Hassan Rasheed
 *
 */
@Transactional(readOnly = false)
public class CurrencyServiceImpl implements CurrencyService{
	
	private CurrencyDAO currencyDAO;

	
	/**
	 * @return the currencyDAO
	 */
	public CurrencyDAO getCurrencyDAO() {
		return currencyDAO;
	}

	/**
	 * @param currencyDAO the currencyDAO to set
	 */
	public void setCurrencyDAO(CurrencyDAO currencyDAO) {
		this.currencyDAO = currencyDAO;
	}

	@Override
	public List<Currency> GetAllCurrencies() {
		return getCurrencyDAO().GetAllCurrencies();
	}

	@Override
	public Currency GetCurrencyById(int currencyId) {
		return getCurrencyDAO().GetCurrencyById(currencyId);
	}

	




	
	
}
