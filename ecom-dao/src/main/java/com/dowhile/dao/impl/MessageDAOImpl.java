/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.Message;
import com.dowhile.dao.MessageDAO;

/**
 * @author Hafiz Yameen Bashir
 *
 */
public class MessageDAOImpl implements MessageDAO{

	private SessionFactory sessionFactory;

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
	public Message addMessage(Message message) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(message);
			return message;

		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Message updateMessage(Message message) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(message);
			return message;

		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteMessage(Message message) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(message);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public Message getMessageByCompanyId(String companyId) {
		try {
			@SuppressWarnings("unchecked")
			List<Message> list = getSessionFactory().getCurrentSession()
					.createQuery("From Message WHERE COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, companyId).list();
			if (list != null && list.size() > 0) {

				return list.get(0);
			}

		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Message getMessageByMaskName(String maskName) {
		try {
			@SuppressWarnings("unchecked")
			List<Message> list = getSessionFactory().getCurrentSession()
					.createQuery("From Message WHERE MASK_NAME=?")
					.setParameter(0, maskName).list();
			if (list != null && list.size() > 0) {

				return list.get(0);
			}

		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	

}
