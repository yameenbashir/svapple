package com.dowhile.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.User;
import com.dowhile.dao.ResourceDAO;
import com.dowhile.service.ResourceService;
/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class ResourceServiceImpl implements ResourceService{

	private ResourceDAO resourceDAO;
	

	
	/**
	 * @return the resourceDAO
	 */
	public ResourceDAO getResourceDAO() {
		return resourceDAO;
	}

	/**
	 * @param resourceDAO the resourceDAO to set
	 */
	public void setResourceDAO(ResourceDAO resourceDAO) {
		this.resourceDAO = resourceDAO;
	}

	
	@Override
	public User addUser(User user,int companyId) {
		// TODO Auto-generated method stub
		return getResourceDAO().addUser(user,companyId);
		/*if(emailService.sendEmailAddUser(user.getUserEmail(), "", "123456",user.getFirstName())){
			return getResourceDAO().AddUser(user);
		}else{
			return getResourceDAO().AddUser(user);
		}*/
	}

	@Override
	public boolean IsUserExist(String email, String password,int companyId) {
		// TODO Auto-generated method stub
		return getResourceDAO().IsUserExist(email, password,companyId);
	}
	
	@Override
	public User getUserById(int userId,int companyId) {
		// TODO Auto-generated method stub
		return getResourceDAO().getUserById(userId,companyId);
	}

	@Override
	public User getUserByEmail(String email,int companyId) {
		// TODO Auto-generated method stub
		return getResourceDAO().getUserByEmail(email,companyId);
	}

	@Override
	public List<User> getAllWorkersByCompanyId(int companyAssociationId) {
		// TODO Auto-generated method stub
		return getResourceDAO().getAllWorkersByCompanyId(companyAssociationId);
	}

	@Override
	public boolean UpdateUser(User user,int companyId) {
		// TODO Auto-generated method stub
		return getResourceDAO().UpdateUser(user,companyId);
	}

	@Override
	public boolean UpdatePassword(String email, String currentPassword, String newPassword,int companyId) {
		// TODO Auto-generated method stub
		return getResourceDAO().UpdatePassword(email, currentPassword, newPassword,companyId);
	}

	@Override
	public List<User> getAllManagersForSuperUser(int companyId) {
		// TODO Auto-generated method stub
		return getResourceDAO().getAllManagersForSuperUser(companyId);
	}

	@Override
	public int getCountOfWorkersByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		return getResourceDAO().getCountOfWorkersByCompanyId(companyId);
	}

	@Override
	public List<User> getAllEmployeesByCompanyId(int companyAssociationId) {
		// TODO Auto-generated method stub
		return getResourceDAO().getAllEmployeesByCompanyId(companyAssociationId);
	}

	@Override
	public List<User> getAllEmployeesByManagerId(int userId,int companyId) {
		// TODO Auto-generated method stub
		return getResourceDAO().getAllEmployeesByManagerId(userId,companyId);
	}

	@Override
	public boolean updateUserLoginTime(User user,int companyId) {
		// TODO Auto-generated method stub
		return getResourceDAO().UpdateUser(user,companyId);
	}

	@Override
	public List<User> getAllUsers(int companyId) {
		// TODO Auto-generated method stub
		return getResourceDAO().getAllUsers(companyId);
	}

	@Override
	public Map<Integer, User> getAllUsersMap(int companyId) {
		// TODO Auto-generated method stub
		List<User>  users= getResourceDAO().getAllUsers(companyId);
		Map<Integer, User> map = new HashMap<Integer, User>();
		if(users!=null){
			for(User user : users){
				map.put(user.getUserId(), user);
				
			}
		}
		return map;
	}
	

}
