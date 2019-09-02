/**
 * 
 */
package com.dowhile.dao;

import com.dowhile.UserOutlets;

/**
 * @author Yameen Bashir
 *
 */
public interface UserOutletsDAO {

	UserOutlets addUserOutlets(UserOutlets userOutlets);
	UserOutlets getUserDefaultOutlet( int userId,int companyId);
	UserOutlets updateUserOutlets(UserOutlets userOutlets);
	boolean deleteUserOutlets(UserOutlets userOutlets);
}
