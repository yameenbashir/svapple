/**
 * 
 */
package com.dowhile.dao;
import java.util.List;

import com.dowhile.Configuration;

/**
 * @author Yameen
 *
 */
public interface ConfigurationDAO {

	Configuration addConfiguration(Configuration configuration);
	Configuration updateConfiguration(Configuration configuration);
	Configuration getConfigurationByCompanyIdConfigurationId(int companyId,int configurationId);
	Configuration getConfigurationByPropertyNameByCompanyId(String propertyName,int companyId);
	List<Configuration> getAllConfigurationsByCompanyId(int companyId);
	List<Configuration> getAllConfigurationsForCompanySetupImgagesByCompanyId(int companyId);
	boolean deleteConfiguration(Configuration configuration);
	boolean addConfigurationList(List<Configuration> configurationList);
}
