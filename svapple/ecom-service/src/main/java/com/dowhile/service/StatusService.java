/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.Status;

/**
 * @author Zafar Shakeel
 *
 */
public interface StatusService {

	Status addStatus(Status status);
	Status updateStatus(Status status);
	boolean deleteStatus(Status status);
	Status getStatusByStatusId(int statusId);
	List<Status> getAllStatus();
}
