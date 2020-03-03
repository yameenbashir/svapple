/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.RegisterReport;
import com.dowhile.dao.RegisterReportDAO;

/**
 * @author Yameen Bashir
 *
 */
public class RegisterReportDAOImpl implements RegisterReportDAO{

	private SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(RegisterReportDAOImpl.class.getName());

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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RegisterReport> getRegisterReportByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<RegisterReport> list = getSessionFactory().getCurrentSession()
			.createQuery("from RegisterReport where COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegisterReport> getRegisterReportByOutletIdCompanyId(
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		try{
			List<RegisterReport> list = getSessionFactory().getCurrentSession()
			.createQuery("from RegisterReport where OUTLET_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, outletId)
			.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;

	}

}
