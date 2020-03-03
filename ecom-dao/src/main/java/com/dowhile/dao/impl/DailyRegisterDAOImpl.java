/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.DailyRegister;
import com.dowhile.dao.DailyRegisterDAO;

/**
 * @author Yameen Bashir
 *
 */
public class DailyRegisterDAOImpl implements DailyRegisterDAO{

	private SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(DailyRegisterDAOImpl.class.getName());

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
	public DailyRegister addDailyRegister(
			DailyRegister dailyRegister, int companyId) {
		try{
			getSessionFactory().getCurrentSession().save(dailyRegister);
			return dailyRegister;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public DailyRegister updateDailyRegister(
			DailyRegister dailyRegister, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(dailyRegister);
			return dailyRegister;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}


	@Override
	public DailyRegister getOpenDailyRegister(int companyId, int OutletId,int userId) {
		try{
			@SuppressWarnings("unchecked")
			List<DailyRegister> list = getSessionFactory().getCurrentSession()
			.createQuery("from DailyRegister where STATUS_ASSOCICATION_ID = 7  AND COMPANY_ASSOCIATION_ID = ? AND OUTLET_ASSOCICATION_ID = ? AND CREATED_BY = ? ")
			.setParameter(0, companyId).
			setParameter(1, OutletId).
			setParameter(2, userId).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public DailyRegister getDailyRegisterById(int dailyRegisterId, int companyId) {
		try{
			@SuppressWarnings("unchecked")
			List<DailyRegister> list = getSessionFactory().getCurrentSession()
			.createQuery("from DailyRegister where DAILY_REGISTER_ID = ? AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, dailyRegisterId)
			.setParameter(1, companyId)
			.list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public DailyRegister getLastColsedDailyRegister(int companyId, int OutletId) {
		try{
			@SuppressWarnings("unchecked")
			List<DailyRegister> list = getSessionFactory().getCurrentSession()
			.createQuery("from DailyRegister where STATUS_ASSOCICATION_ID = 8  AND COMPANY_ASSOCIATION_ID = ? AND OUTLET_ASSOCICATION_ID = ?")
			.setParameter(0, companyId).
			setParameter(1, OutletId).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
