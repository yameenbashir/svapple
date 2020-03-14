/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.ExpenseType;
import com.dowhile.dao.ExpenseTypeDAO;

/**
 * @author Zafar Shakeel
 *
 */
public class ExpenseTypeDAOImpl implements ExpenseTypeDAO{

	private SessionFactory sessionFactory;// private static Logger logger = Logger.getLogger(CashManagementController.class.getName());

    /**
     * Get Hibernate Session Factory
     * 
     * @return SessionFactory - Hibernate Session Factory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Set Hibernate Session Factory
     * 
     * @param SessionFactory
     *            - Hibernate Session Factory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	@Override
	public ExpenseType addExpenseType(ExpenseType ExpenseType,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(ExpenseType);
			return ExpenseType;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public ExpenseType updateExpenseType(ExpenseType ExpenseType,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(ExpenseType);
			return ExpenseType;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteExpenseType(ExpenseType ExpenseType ,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(ExpenseType);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public ExpenseType getExpenseTypeByExpenseTypeId(int ExpenseTypeId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<ExpenseType> list = getSessionFactory().getCurrentSession()
			.createQuery("from ExpenseType where EXPENSE_TYPE_ID=?")
			.setParameter(0, ExpenseTypeId).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpenseType> GetAllExpenseType() {
		try {
			List<ExpenseType> list = getSessionFactory().getCurrentSession()
					.createQuery("From ExpenseType WHERE ACTIVE_INDICATOR = 1")
					.list();
			if (list != null && list.size() > 0) {

				return list;
			}

		} catch (HibernateException ex) {
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
