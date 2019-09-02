/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Role;
import com.dowhile.dao.RoleDAO;
import com.dowhile.service.RoleService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class RoleServiceImpl implements RoleService{

	private RoleDAO roleDAO;
	/**
	 * @return the roleDAO
	 */
	public RoleDAO getRoleDAO() {
		return roleDAO;
	}

	/**
	 * @param roleDAO the roleDAO to set
	 */
	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	@Override
	public Role addRole(Role role) {
		// TODO Auto-generated method stub
		return getRoleDAO().addRole(role);
	}

	@Override
	public Role updateRole(Role role) {
		// TODO Auto-generated method stub
		return getRoleDAO().updateRole(role);
	}

	@Override
	public boolean deleteRole(Role role) {
		// TODO Auto-generated method stub
		return getRoleDAO().deleteRole(role);
	}

	@Override
	public List<Role> getAllRoles() {
		// TODO Auto-generated method stub
		return getRoleDAO().getAllRoles();
	}

	@Override
	public List<Role> getAllRolesWithoutAdmin() {
		// TODO Auto-generated method stub
		return getRoleDAO().getAllRolesWithoutAdmin();
	}
	
	@Override
	public Role getRoleByRoleId(int roleId) {
		// TODO Auto-generated method stub
		return getRoleDAO().getRoleByRoleId(roleId);
	}

}
