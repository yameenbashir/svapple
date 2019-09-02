/**
 * 
 */
package com.dowhile.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.UserOutlets;
import com.dowhile.dao.UserOutletsDAO;
import com.dowhile.service.UserOutletsService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class UserOutletsServiceImpl implements UserOutletsService{

	private UserOutletsDAO userOutletsDAO;
	
	/**
	 * @return the userOutletsDAO
	 */
	public UserOutletsDAO getUserOutletsDAO() {
		return userOutletsDAO;
	}

	/**
	 * @param userOutletsDAO the userOutletsDAO to set
	 */
	public void setUserOutletsDAO(UserOutletsDAO userOutletsDAO) {
		this.userOutletsDAO = userOutletsDAO;
	}

	@Override
	public UserOutlets addUserOutlets(UserOutlets userOutlets) {
		// TODO Auto-generated method stub
		return getUserOutletsDAO().addUserOutlets(userOutlets);
	}

	@Override
	public UserOutlets updateUserOutlets(UserOutlets userOutlets) {
		// TODO Auto-generated method stub
		return getUserOutletsDAO().updateUserOutlets(userOutlets);
	}

	@Override
	public boolean deleteUserOutlets(UserOutlets userOutlets) {
		// TODO Auto-generated method stub
		return getUserOutletsDAO().deleteUserOutlets(userOutlets);
	}

	@Override
	public UserOutlets getUserDefaultOutlet( int userId,int companyId) {
		// TODO Auto-generated method stub
		return getUserOutletsDAO().getUserDefaultOutlet(userId, companyId);
	}

}
