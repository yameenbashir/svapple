package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Severity;
import com.dowhile.dao.SeverityDAO;
import com.dowhile.service.SeverityService;
/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class SeverityServiceImpl implements SeverityService{

	private SeverityDAO severityDAO;

	/**
	 * @return the severityDAO
	 */
	public SeverityDAO getSeverityDAO() {
		return severityDAO;
	}

	/**
	 * @param severityDAO the severityDAO to set
	 */
	public void setSeverityDAO(SeverityDAO severityDAO) {
		this.severityDAO = severityDAO;
	}

	@Override
	public Severity getSeverityBySeverityId(int severityId) {
		// TODO Auto-generated method stub
		return getSeverityDAO().getSeverityBySeverityId(severityId);
	}

	@Override
	public List<Severity> getAllSeverities() {
		// TODO Auto-generated method stub
		return getSeverityDAO().getAllSeverities();
	}
}
