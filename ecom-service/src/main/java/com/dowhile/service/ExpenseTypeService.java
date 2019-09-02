/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.ExpenseType;

/**
 * @author Zafar Shakeel
 *
 */
public interface ExpenseTypeService {

	ExpenseType addExpenseType(ExpenseType ExpenseType, int companyId);
	ExpenseType updateExpenseType(ExpenseType ExpenseType, int companyId);
	boolean deleteExpenseType(ExpenseType ExpenseType, int companyId);
	ExpenseType getExpenseTypeByExpenseTypeId(int ExpenseTypeId);
	List<ExpenseType> GetAllExpenseType();
}
