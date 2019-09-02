/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.Menu;

/**
 * @author Yameen Bashir
 *
 */
public interface MenuService {
	
	Menu addMenu(Menu menu, int companyId);
	Menu updateMenu(Menu menu, int companyId);
	boolean deleteMenu(Menu menu, int companyId);
	List<Menu> getMenuByRoleId(int roleId, int companyId);
	Menu getMenuByMenuIdCompanyId(int menuId,int companyId);
}
