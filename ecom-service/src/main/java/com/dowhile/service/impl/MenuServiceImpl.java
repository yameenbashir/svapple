/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Menu;
import com.dowhile.dao.MenuDAO;
import com.dowhile.service.MenuService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class MenuServiceImpl implements MenuService{

	private MenuDAO menuDAO;
	
	/**
	 * @return the menuDAO
	 */
	public MenuDAO getMenuDAO() {
		return menuDAO;
	}

	/**
	 * @param menuDAO the menuDAO to set
	 */
	public void setMenuDAO(MenuDAO menuDAO) {
		this.menuDAO = menuDAO;
	}

	@Override
	public Menu addMenu(Menu menu, int companyId) {
		// TODO Auto-generated method stub
		return getMenuDAO().addMenu(menu,companyId);
	}

	@Override
	public Menu updateMenu(Menu menu, int companyId) {
		// TODO Auto-generated method stub
		return getMenuDAO().updateMenu(menu,companyId);
	}

	@Override
	public boolean deleteMenu(Menu menu, int companyId) {
		// TODO Auto-generated method stub
		return getMenuDAO().deleteMenu(menu,companyId);
	}

	@Override
	public List<Menu> getMenuByRoleId(int roleId, int companyId) {
		// TODO Auto-generated method stub
		return getMenuDAO().getMenuByRoleId(roleId,companyId);
	}

	@Override
	public Menu getMenuByMenuIdCompanyId(int menuId, int companyId) {
		// TODO Auto-generated method stub
		return getMenuDAO().getMenuByMenuIdCompanyId(menuId, companyId);
	}

	@Override
	public List<Menu> getAllMenuByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		return getMenuDAO().getAllMenuByCompanyId(companyId);
	}

	@Override
	public boolean addMenuList(List<Menu> menuList) {
		// TODO Auto-generated method stub
		return getMenuDAO().addMenuList(menuList);
	}

}
