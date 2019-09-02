/**
 * 
 */
package com.dowhile.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Configuration;
import com.dowhile.dao.ConfigurationDAO;
import com.dowhile.service.ConfigurationService;

/**
 * @author Yameen
 *
 */
@Transactional(readOnly = false)
public class ConfigurationServiceImpl implements ConfigurationService{

	private ConfigurationDAO configurationDAO;

	/**
	 * @return the configurationDAO
	 */
	public ConfigurationDAO getConfigurationDAO() {
		return configurationDAO;
	}

	/**
	 * @param configurationDAO the configurationDAO to set
	 */
	public void setConfigurationDAO(ConfigurationDAO configurationDAO) {
		this.configurationDAO = configurationDAO;
	}

	@Override
	public Configuration addConfiguration(Configuration configuration) {
		// TODO Auto-generated method stub
		return getConfigurationDAO().addConfiguration(configuration);
	}

	@Override
	public Configuration updateConfiguration(Configuration configuration) {
		// TODO Auto-generated method stub
		return getConfigurationDAO().updateConfiguration(configuration);
	}

	@Override
	public Configuration getConfigurationByCompanyIdConfigurationId(
			int companyId, int configurationId) {
		// TODO Auto-generated method stub
		return getConfigurationDAO().getConfigurationByCompanyIdConfigurationId(companyId, configurationId);
	}

	@Override
	public Map<String ,Configuration> getAllConfigurationsByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		 Map<String ,Configuration> configurationMap = new HashMap<>(); 
		List<Configuration> configurationList =  getConfigurationDAO().getAllConfigurationsByCompanyId(companyId);
		if(configurationList!=null && configurationList.size()>0){
			for(Configuration configuration:configurationList){
				configurationMap.put(configuration.getPropertyName(), configuration);
			}
			
		}
		return configurationMap;
	}

	@Override
	public boolean deleteConfiguration(Configuration configuration) {
		// TODO Auto-generated method stub
		return getConfigurationDAO().deleteConfiguration(configuration);
	}

	@Override
	public Configuration getConfigurationByPropertyNameByCompanyId(String propertyName,int companyId) {
		// TODO Auto-generated method stub
		return getConfigurationDAO().getConfigurationByPropertyNameByCompanyId(propertyName,companyId);
	}

	@Override
	public List<Configuration> getAllConfigurationsForCompanySetupImgagesByCompanyId(
			int companyId) {
		// TODO Auto-generated method stub
		return getConfigurationDAO().getAllConfigurationsForCompanySetupImgagesByCompanyId(companyId);
	}
}
