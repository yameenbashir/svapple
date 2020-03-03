package com.dowhile.angualrspringapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Company;
import com.dowhile.Configuration;
import com.dowhile.service.CompanyService;
import com.dowhile.service.ConfigurationService;

@Controller
@RequestMapping("/applicationCache")
public class ApplicationCacheController {

	private static Logger logger = Logger.getLogger(ApplicationCacheController.class.getName());
	@Resource
	private ConfigurationService configurationService;
	@Resource
	private CompanyService companyService;
	
	private static Map<Integer, Map<String, Configuration>> allCompaniesConfigurationsMap = new HashMap<>();
	private static Map<Integer, Company> allCompaniesMap = new HashMap<>();
	
	
	
	
	@RequestMapping(value = "/load", method = RequestMethod.POST)
	public @ResponseBody void load() {
		try {
			boolean forcefullyPopulate = true;
			logger.info("Starting build Cache");
			populateallCompaniesMap(forcefullyPopulate);
			populateAllCompaniesConfigurationsMap(forcefullyPopulate);
			
			logger.info("Cache built successfully");
		}catch(Exception ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
	}
	
	public Map<Integer, Company> populateallCompaniesMap(boolean forcefullyPopulate) {
		try {
			if(allCompaniesMap.isEmpty()||forcefullyPopulate) {
				List<Company> companiesList = companyService.getCompanies();
				if(companiesList!=null) {
					for(Company company:companiesList) {
						allCompaniesMap.put(company.getCompanyId(), company);
					}
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return allCompaniesMap;
	}
	
	public Map<Integer, Map<String, Configuration>> populateAllCompaniesConfigurationsMap(boolean forcefullyPopulate) {/*
		try {
			if(allCompaniesConfigurationsMap.isEmpty()||forcefullyPopulate) {
				for (Map.Entry<Integer, Company> entry : allCompaniesMap.entrySet()) {
					List<Configuration> configurationsList =configurationService.getAllConfigurationsByCompanyId(entry.getKey());
					if(configurationsList!=null) {
						Map<String, Configuration> companyConfigurationsMap = new HashMap<>();
						for(Configuration configuration:configurationsList) {
							companyConfigurationsMap.put(configuration.getPropertyName().trim(), configuration);
						}
						allCompaniesConfigurationsMap.put(entry.getKey(), companyConfigurationsMap);
					}
				}
			}
		}catch(Exception ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		
	*/
		return allCompaniesConfigurationsMap;}
	
	

	/**
	 * @return the allCompaniesConfigurationsMap
	 */
	public static Map<Integer, Map<String, Configuration>> getAllCompaniesConfigurationsMap() {
		return allCompaniesConfigurationsMap;
	}

	/**
	 * @param allCompaniesConfigurationsMap the allCompaniesConfigurationsMap to set
	 */
	public static void setAllCompaniesConfigurationsMap(
			Map<Integer, Map<String, Configuration>> allCompaniesConfigurationsMap) {
		ApplicationCacheController.allCompaniesConfigurationsMap = allCompaniesConfigurationsMap;
	}

	/**
	 * @return the allCompaniesMap
	 */
	public static Map<Integer, Company> getAllCompaniesMap() {
		return allCompaniesMap;
	}

	/**
	 * @param allCompaniesMap the allCompaniesMap to set
	 */
	public static void setAllCompaniesMap(Map<Integer, Company> allCompaniesMap) {
		ApplicationCacheController.allCompaniesMap = allCompaniesMap;
	}

	
	
	
	

	
	
	
}
