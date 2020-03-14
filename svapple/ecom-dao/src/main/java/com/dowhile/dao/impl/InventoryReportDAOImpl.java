/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.InventoryReport;
import com.dowhile.dao.InventoryReportDAO;

/**
 * @author Yameen Bashir
 *
 */
public class InventoryReportDAOImpl implements InventoryReportDAO{

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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InventoryReport> getInventoryReportByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<InventoryReport> list = getSessionFactory().getCurrentSession()
			.createQuery("from InventoryReport where COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, companyId).list();
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
	public List<InventoryReport> getInventoryReportByOutletIdCompanyId(
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		try{
			List<InventoryReport> list = getSessionFactory().getCurrentSession()
			.createQuery("from InventoryReport where OUTLET_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, outletId)
			.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
