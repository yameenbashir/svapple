/**
 * 
 */
package com.dowhile.service;

import com.dowhile.UserOutlets;

/**
 * @author Yameen Bashir
 *
 */
public interface UserOutletsService {

	UserOutlets addUserOutlets(UserOutlets userOutlets);
	UserOutlets updateUserOutlets(UserOutlets userOutlets);
	boolean deleteUserOutlets(UserOutlets userOutlets);
	UserOutlets getUserDefaultOutlet( int userId,int companyId);
}
