/**
 * 
 */
package com.dowhile.service;

import java.util.List;
import java.util.Map;

import com.dowhile.Configuration;

/**
 * @author Yameen
 *
 */
public interface ConfigurationService {

	Configuration addConfiguration(Configuration configuration);
	Configuration updateConfiguration(Configuration configuration);
	Configuration getConfigurationByCompanyIdConfigurationId(int companyId,int configurationId);
	Configuration getConfigurationByPropertyNameByCompanyId(String propertyName,int companyId);
	Map<String ,Configuration> getAllConfigurationsByCompanyId(int companyId);
	List<Configuration> getAllConfigurationsForCompanySetupImgagesByCompanyId(int companyId);
	boolean deleteConfiguration(Configuration configuration);
	boolean addConfigurationList(List<Configuration> configurationList);
}
