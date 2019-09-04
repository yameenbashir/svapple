package com.dowhile.service;

import java.util.List;
import java.util.Map;

import com.dowhile.User;
/**
 * @author Yameen Bashir
 *
 */
public interface ResourceService {

	User addUser(User user,int companyId);
	boolean UpdateUser(User user,int companyId);
	boolean UpdatePassword(String email,String currentPassword,String newPassword,int companyId);
	boolean IsUserExist(String email,String password,int companyId);
	User getUserById(int userId,int companyId);
	User getUserByEmail(String email,int companyId);
	List<User> getAllWorkersByCompanyId(int companyAssociationId);
	List<User> getAllManagersForSuperUser(int companyId);
	int getCountOfWorkersByCompanyId(int companyId);
	List<User> getAllEmployeesByCompanyId(int companyAssociationId);
	List<User> getAllEmployeesByManagerId(int userId,int companyId);
	boolean updateUserLoginTime(User user,int companyId);
	List<User> getAllUsers(int companyId);
	Map<Integer,User> getAllUsersMap(int companyId);
	List<User> getAllUsersByCompanyIdOutletId(int companyId,int outletId);
	
}
