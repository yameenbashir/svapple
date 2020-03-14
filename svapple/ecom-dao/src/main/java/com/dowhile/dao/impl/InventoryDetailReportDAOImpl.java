/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.InventoryDetailReport;
import com.dowhile.InventoryReport;
import com.dowhile.dao.InventoryDetailReportDAO;

/**
 * @author Hafiz Bashir
 *
 */
public class InventoryDetailReportDAOImpl implements InventoryDetailReportDAO{

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
	public List<InventoryDetailReport> getInventoryDetailReportByCompanyId(
			int companyId) {
		// TODO Auto-generated method stub
				try{
					List<InventoryDetailReport> list = getSessionFactory().getCurrentSession()
					.createQuery("from InventoryDetailReport where COMPANY_ASSOCIATION_ID = ?")
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
	public List<InventoryDetailReport> getInventoryDetailReportByOutletIdCompanyId(
			int outletId, int companyId) {
		try{
			List<InventoryDetailReport> list = getSessionFactory().getCurrentSession()
			.createQuery("from InventoryDetailReport where OUTLET_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID = ?")
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
