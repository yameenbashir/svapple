/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Status;
import com.dowhile.dao.StatusDAO;
import com.dowhile.service.StatusService;

/**
 * @author Zafar Shakeel
 *
 */
@Transactional(readOnly = false)
public class StatusServiceImpl implements StatusService{


	private StatusDAO statusDAO;
	
	/**
	 * @return the StatusDAO
	 */
	public StatusDAO getStatusDAO() {
		return statusDAO;
	}

	/**
	 * @param statusDAO the StatusDAO to set
	 */
	public void setStatusDAO(StatusDAO statusDAO) {
		this.statusDAO = statusDAO;
	}

	@Override
	public Status addStatus(Status status) {
		// TODO Auto-generated method stub
		return getStatusDAO().addStatus(status);
	}

	@Override
	public Status updateStatus(Status status) {
		// TODO Auto-generated method stub
		return getStatusDAO().updateStatus(status);
	}

	@Override
	public boolean deleteStatus(Status status) {
		// TODO Auto-generated method stub
		return getStatusDAO().deleteStatus(status);
	}

	@Override
	public Status getStatusByStatusId(int statusId) {
		// TODO Auto-generated method stub
		return getStatusDAO().getStatusByStatusId(statusId);
	}

	@Override
	public List<Status> getAllStatus() {
		// TODO Auto-generated method stub
		return getStatusDAO().getAllStatus();
	}

}
