package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.User;
import com.dowhile.dao.ResourceDAO;
/**
 * @author Yameen Bashir
 *
 */
public class ResourceDAOImpl implements ResourceDAO{
	private SessionFactory sessionFactory;private static Logger logger = Logger.getLogger(CashManagementController.class.getName());

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
	public User addUser(User user,int companyId) {
	
		try{
			getSessionFactory().getCurrentSession().save(user);
			return user;
		}
		catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		
		return null;
	}

	@Override
	public boolean IsUserExist(String email, String password,int companyId) {
		
		try{
			@SuppressWarnings("unchecked")
			List<User> list = getSessionFactory().getCurrentSession()
			.createQuery("from User where USER_EMAIL=?")
			.setParameter(0, email).list();
			if(list!=null&& list.size()>0){

				if(list.get(0).getUserEmail().equalsIgnoreCase(email)&&list.get(0).getPassword().equalsIgnoreCase(password)){
					return true;
				}
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		

		return false;

	}

	@Override
	public User getUserById(int userId,int companyId) {
		
		try{
			@SuppressWarnings("unchecked")
			List<User> list = getSessionFactory().getCurrentSession()
			.createQuery("from User where USER_ID=? AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, userId)
			.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		

		return null;
	}

	@Override
	public User getUserByEmail(String email,int companyId) {
		
		try{
			@SuppressWarnings("unchecked")
			List<User> list = getSessionFactory().getCurrentSession()
			.createQuery("from User where USER_EMAIL=? ")
			.setParameter(0, email).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllWorkersByCompanyId(int companyAssociationId) {
		// TODO Auto-generated method stub
		try{
			return getSessionFactory().getCurrentSession()
					.createQuery("from User where COMPANY_ASSOCIATION_ID=? AND ROLE_ID=? ")
					.setParameter(0, companyAssociationId)
					.setParameter(1, 4).
					list();
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllEmployeesByManagerId(int userId,int companyId) {
		// TODO Auto-generated method stub
		try{
			return getSessionFactory().getCurrentSession()
					.createQuery("from User where MANAGER_ID=? AND COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, userId)
					.setParameter(1, companyId).list();
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	
	public boolean UpdateUser(User user,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(user);
			return true;
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public boolean UpdatePassword(String email,String currentPassword, String newPassword,int companyId) {
		// TODO Auto-generated method stub
		try{
			User userForPasswordUpdate = getUserByEmail(email,companyId);
			if(userForPasswordUpdate.getPassword().equalsIgnoreCase(currentPassword)){
				userForPasswordUpdate.setPassword(newPassword);
				getSessionFactory().getCurrentSession().update(userForPasswordUpdate);
				return true;
			}
		}catch(Exception ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllManagersForSuperUser(int companyId) {
		// TODO Auto-generated method stub
		try{
			return getSessionFactory().getCurrentSession()
					.createQuery("from User where ROLE_ID=? AND COMPANY_ASSOCIATION_ID=? ")
					.setParameter(0, 3)
					.setParameter(1, companyId).list();
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public int getCountOfWorkersByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		try{
			int count = ((Long)getSessionFactory().getCurrentSession().createQuery("select count(*) from User where COMPANY_ASSOCIATION_ID = "+companyId+"AND ROLE_ID= 4").uniqueResult()).intValue();
			return count;
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllEmployeesByCompanyId(int companyAssociationId) {
		// TODO Auto-generated method stub
		try{
			return getSessionFactory().getCurrentSession()
					.createQuery("from User where COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, companyAssociationId)
					.list();
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers(int companyId) {
		// TODO Auto-generated method stub
		try{
			return getSessionFactory().getCurrentSession()
			.createQuery("from User where COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, companyId)
			.list();
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	

}
