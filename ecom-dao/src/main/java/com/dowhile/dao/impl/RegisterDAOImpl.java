/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.Register;
import com.dowhile.dao.RegisterDAO;

/**
 * @author Yameen Bashir
 *
 */
public class RegisterDAOImpl implements RegisterDAO{
	
	
	private SessionFactory sessionFactory;
	// private static Logger logger = Logger.getLogger(RegisterDAOImpl.class.getName());

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
	public Register addRegister(Register register, int companyId) {
		try{
			getSessionFactory().getCurrentSession().save(register);
			return register;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public Register updateRegister(Register register, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(register);
			return register;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteRegister(Register register, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(register);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Register> getRegistersByOutletId(int outletId, int companyId,int userId) {
		// TODO Auto-generated method stub

		try{
			List<Register> list = getSessionFactory().getCurrentSession()
					.createQuery("from Register where OUTLET_ASSOCICATION_ID =? AND COMPANY_ASSOCIATION_ID = ? ")
					.setParameter(0, outletId)
					.setParameter(1, companyId)
					//.setParameter(2, userId)
					.list();
			//.setParameter(2, userId)
			//from Register where OUTLET_ASSOCICATION_ID =? AND COMPANY_ASSOCIATION_ID = ? AND CREATED_BY = ?
			if(list!=null&& list.size()>0){

				return list;
			}

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}


		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Register getRegisterByRegisterId(int registerId, int companyId) {
		// TODO Auto-generated method stub
		try{
			List<Register> list = getSessionFactory().getCurrentSession()
					.createQuery("from Register where REGISTER_ID =? AND  COMPANY_ASSOCIATION_ID = ?")
					.setParameter(0, registerId)
					.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}



}
