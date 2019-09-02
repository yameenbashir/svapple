/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.CashManagment;
import com.dowhile.dao.CashManagmentDAO;

/**
 * @author Yameen Bashir
 *
 */
public class CashManagmentDAOImpl implements CashManagmentDAO{

	private SessionFactory sessionFactory;

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
	public CashManagment addCashManagment(CashManagment cashManagment, int companyId) {
		try{
			getSessionFactory().getCurrentSession().save(cashManagment);
			return cashManagment;

		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public CashManagment updateCashManagment(CashManagment cashManagment, int companyId) {
		try{
			getSessionFactory().getCurrentSession().update(cashManagment);
			return cashManagment;

		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CashManagment> getCashManagmentDailyRegister(int companyId,
			int OutletId, int dailyRegisterid) {
		List<CashManagment> list;
		try{
			 list = getSessionFactory().getCurrentSession()
			.createQuery("from CashManagment where COMPANY_ASSOCIATION_ID=? AND OUTLET_ASSOCICATION_ID = ?"
					+ " AND DAILY_REGISTER_ASSOCICATION_ID = ?")
			.setParameter(0, companyId)
			.setParameter(1, OutletId)
			.setParameter(2, dailyRegisterid).list();
			return list;
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

}
