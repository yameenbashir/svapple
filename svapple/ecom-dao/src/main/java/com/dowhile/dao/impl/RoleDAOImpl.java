/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.Role;
import com.dowhile.dao.RoleDAO;

/**
 * @author Yameen Bashir
 *
 */
public class RoleDAOImpl implements RoleDAO{

	private SessionFactory sessionFactory;// private static Logger logger = Logger.getLogger(CashManagementController.class.getName());

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
	public Role addRole(Role role) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(role);
			return role;
		}
		catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public Role updateRole(Role role) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(role);
			return role;
		}
		catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteRole(Role role) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(role);
			return true;
		}
		catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAllRoles() {
		// TODO Auto-generated method stub
		try{
			List<Role> RoleList = getSessionFactory().getCurrentSession().createCriteria(Role.class).list();
			return RoleList;
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> getAllRolesWithoutAdmin() {
		// TODO Auto-generated method stub
		try{
			List<Role> list = getSessionFactory().getCurrentSession()
					.createQuery("from Role where ROLE_ID <> ?")
					.setParameter(0, 1).list();
					if(list!=null&& list.size()>0){

						return list;
					}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Role getRoleByRoleId(int roleId) {
		// TODO Auto-generated method stub
		try{
			List<Role> list = getSessionFactory().getCurrentSession()
			.createQuery("from Role where ROLE_ID=?")
			.setParameter(0, roleId).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
