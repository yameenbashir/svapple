/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.ExpenseType;
import com.dowhile.dao.ExpenseTypeDAO;
import com.dowhile.service.ExpenseTypeService;

/**
 * @author Zafar Shakeel
 *
 */
@Transactional(readOnly = false)
public class ExpenseTypeServiceImpl implements ExpenseTypeService{

	private ExpenseTypeDAO expenseTypeDAO;
	/**
	 * @return the ExpenseTypeDAO
	 */
	public ExpenseTypeDAO getExpenseTypeDAO() {
		return expenseTypeDAO;
	}

	/**
	 * @param ExpenseTypeDAO the ExpenseTypeDAO to set
	 */
	public void setExpenseTypeDAO(ExpenseTypeDAO ExpenseTypeDAO) {
		this.expenseTypeDAO = ExpenseTypeDAO;
	}

	@Override
	public ExpenseType addExpenseType(ExpenseType ExpenseType, int companyId) {
		// TODO Auto-generated method stub
		return getExpenseTypeDAO().addExpenseType(ExpenseType,companyId);
	}

	@Override
	public ExpenseType updateExpenseType(ExpenseType ExpenseType, int companyId) {
		// TODO Auto-generated method stub
		return getExpenseTypeDAO().updateExpenseType(ExpenseType,companyId);
	}

	@Override
	public boolean deleteExpenseType(ExpenseType ExpenseType, int companyId) {
		// TODO Auto-generated method stub
		return getExpenseTypeDAO().deleteExpenseType(ExpenseType,companyId);
	}

	@Override
	public ExpenseType getExpenseTypeByExpenseTypeId(int ExpenseTypeId) {
		// TODO Auto-generated method stub
		return getExpenseTypeDAO().getExpenseTypeByExpenseTypeId(ExpenseTypeId);
	}

	@Override
	public List<ExpenseType> GetAllExpenseType() {
		// TODO Auto-generated method stub
		return getExpenseTypeDAO().GetAllExpenseType();
	}
	
}
