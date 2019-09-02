package com.dowhile.dao;

import java.util.List;

import com.dowhile.User;

/**
 * @author Yameen Bashir
 *
 */
public interface ResourceDAO {

	User addUser(User user,int companyId);
	boolean UpdateUser(User user,int companyId);
	boolean UpdatePassword(String email,String currentPassword,String newPassword,int companyId);
	boolean IsUserExist(String email,String password,int companyId);
	User getUserById(int userId,int companyId);
	User getUserByEmail(String email,int companyId);
	List<User> getAllWorkersByCompanyId(int companyAssociationId);
	List<User> getAllEmployeesByCompanyId(int companyAssociationId);
	List<User> getAllManagersForSuperUser(int companyId);
	int getCountOfWorkersByCompanyId(int companyId);
	List<User> getAllEmployeesByManagerId(int userId,int companyId);
	List<User> getAllUsers(int companyId);
	
	
}
