/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.dowhile.Menu;
import com.dowhile.dao.MenuDAO;

/**
 * @author Yameen Bashir
 *
 */
public class MenuDAOImpl implements MenuDAO{

	private SessionFactory sessionFactory;
	// private static Logger logger = Logger.getLogger(MenuDAOImpl.class.getName());

    /**
     * Get Hibernate Session Factory
     * 
     * @return SessionFactory - Hibernate Session Factory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Set Hibernate Session Factory
     * 
     * @param SessionFactory
     *            - Hibernate Session Factory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
	@Override
	public Menu addMenu(Menu menu, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(menu);
			return menu;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public Menu updateMenu(Menu menu , int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(menu);
			return menu;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;

	}

	@Override
	public boolean deleteMenu(Menu menu , int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(menu);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public List<Menu> getMenuByRoleId(int roleId , int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Menu> list = getSessionFactory().getCurrentSession()
			.createQuery("from Menu where ROLE_ASSOCIATION_ID = ? AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, roleId)
			.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public Menu getMenuByMenuIdCompanyId(int menuId, int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Menu> list = getSessionFactory().getCurrentSession()
			.createQuery("from Menu where MENU_ID = ? AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, menuId)
			.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}
	
	@Override
	public List<Menu> getAllMenuByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Menu> list = getSessionFactory().getCurrentSession()
			.createQuery("from Menu where COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean addMenuList(List<Menu> menuList) {
		// TODO Auto-generated method stub
		Session session = null;
		try{
			session =  getSessionFactory().getCurrentSession();
			for(Menu menu : menuList){
				session.save(menu);
					
			}
			return true;
		
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return false;
	}

}
