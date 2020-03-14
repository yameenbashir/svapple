/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.Address;
import com.dowhile.dao.AddressDAO;

/**
 * @author Yameen Bashir
 *
 */
public class AddressDAOImpl implements AddressDAO{

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
	public Address addAddress(Address address, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(address);
			return address;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public Address updateAddress(Address address, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(address);
			return address;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public boolean deleteAddress(Address address, int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(address);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@Override
	public Address getAddressByAddressId(int addressId, int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<Address> list = getSessionFactory().getCurrentSession()
			.createQuery("from Address where ADDRESS_ID = ? AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, addressId).
			setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<Address> getAddressByCompanyId(int companyId) {
		{
			// TODO Auto-generated method stub
			try{
				@SuppressWarnings("unchecked")
				List<Address> list = getSessionFactory().getCurrentSession()
				.createQuery("from Address where COMPANY_ASSOCIATION_ID=?")
				.setParameter(0, companyId).list();
				if(list!=null&& list.size()>0){

					return list;
				}
			}catch(HibernateException ex){
				ex.printStackTrace();// logger.error(ex.getMessage(),ex);
			}
			return null;
		}
	}

	@Override
	public List<Address> getAddressBySupplierId(int supplierId, int companyId) {
		{
			// TODO Auto-generated method stub
			try{
				@SuppressWarnings("unchecked")
				List<Address> list = getSessionFactory().getCurrentSession()
				.createQuery("from Address where CONTACT_ASSOCICATION_ID=? AND COMPANY_ASSOCIATION_ID=?")
				.setParameter(0, supplierId)
				.setParameter(1, companyId).list();
				if(list!=null&& list.size()>0){

					return list;
				}
			}catch(HibernateException ex){
				ex.printStackTrace();// logger.error(ex.getMessage(),ex);
			}
			return null;
		}
	}
	@Override
	public List<Address> getAddressByCustomerId(int customerId, int companyId) {
		{
			// TODO Auto-generated method stub
			try{
				@SuppressWarnings("unchecked")
				List<Address> list = getSessionFactory().getCurrentSession()
				.createQuery("from Address where CONTACT_ASSOCICATION_ID=? AND COMPANY_ASSOCIATION_ID=? ORDER BY PHONE")
				.setParameter(0, customerId).
				setParameter(1, companyId).list();
				if(list!=null&& list.size()>0){

					return list;
				}
			}catch(HibernateException ex){
				ex.printStackTrace();// logger.error(ex.getMessage(),ex);
			}
			return null;
		}
	}

	@Override
	public List<Address> getAllAddress(int companyId) {
		{
			// TODO Auto-generated method stub
			try{
				@SuppressWarnings("unchecked")
				List<Address> list = getSessionFactory().getCurrentSession()
				.createQuery("from Address where COMPANY_ASSOCIATION_ID=?")
				.setParameter(0, companyId).list();
				if(list!=null&& list.size()>0){

					return list;
				}
			}catch(HibernateException ex){
				ex.printStackTrace();// logger.error(ex.getMessage(),ex);
			}
			return null;
		}
	}





}
