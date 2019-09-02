package com.dowhile.service;

import java.util.List;

import com.dowhile.Severity;
/**
 * @author Yameen Bashir
 *
 */
public interface SeverityService {
	Severity getSeverityBySeverityId(int severityId);
	List<Severity> getAllSeverities();

}
