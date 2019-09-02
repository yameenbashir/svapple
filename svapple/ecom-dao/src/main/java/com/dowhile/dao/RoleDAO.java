/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.Role;

/**
 * @author Yameen Bashir
 *
 */
public interface RoleDAO {

	Role addRole(Role role);
	Role updateRole(Role role);
	boolean deleteRole(Role role);
	List<Role> getAllRoles();
	List<Role> getAllRolesWithoutAdmin();
	Role getRoleByRoleId(int roleId);
}
