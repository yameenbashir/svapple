package com.dowhile.dao;

import java.util.List;

import com.dowhile.Severity;

/**
 * @author Yameen Bashir
 *
 */
public interface SeverityDAO {
	
	Severity getSeverityBySeverityId(int severityId);
	List<Severity> getAllSeverities();

}
