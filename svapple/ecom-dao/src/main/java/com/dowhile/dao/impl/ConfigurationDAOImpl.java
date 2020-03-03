/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.Configuration;
import com.dowhile.dao.ConfigurationDAO;

/**
 * @author Yameen
 *
 */
public class ConfigurationDAOImpl implements ConfigurationDAO{

	private SessionFactory sessionFactory;private static Logger logger = Logger.getLogger(CashManagementController.class.getName());

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
	public Configuration addConfiguration(Configuration configuration) {
		// TODO Auto-generated method stub
		try{

			configuration.setLastUpdated(new Date());
			configuration.setCreatedDate(new Date());
			getSessionFactory().getCurrentSession().save(configuration);

			return configuration;

		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public Configuration updateConfiguration(Configuration configuration) {
		// TODO Auto-generated method stub
		try{
			
			configuration.setLastUpdated(new Date());
			
			getSessionFactory().getCurrentSession().update(configuration);
			return configuration;
		}catch(Exception ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public Configuration getConfigurationByCompanyIdConfigurationId(
			int companyId,int configurationId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Configuration> list = getSessionFactory().getCurrentSession()
			.createQuery("from Configuration where COMPANY_ASSOCIATION_ID=? AND CONFIGURATION_ID=?")
			.setParameter(0, companyId)
			.setParameter(1, configurationId)
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
	public List<Configuration> getAllConfigurationsByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Configuration> list = getSessionFactory().getCurrentSession()
			.createQuery("from Configuration where COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, companyId)
			.list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteConfiguration(Configuration configuration) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(configuration);
			return true;
			
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public Configuration getConfigurationByPropertyNameByCompanyId(String propertyName,int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Configuration> list = getSessionFactory().getCurrentSession()
			.createQuery("from Configuration where PROPERTY_NAME=? AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, propertyName)
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
	public List<Configuration> getAllConfigurationsForCompanySetupImgagesByCompanyId(
			int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Configuration> list = getSessionFactory().getCurrentSession()
			.createQuery("from Configuration where COMPANY_ASSOCIATION_ID=? AND PROPERTY_NAME LIKE ?")
			.setParameter(0, companyId)
			.setParameter(1, "%_IMAGE%")
			.list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
