/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.Company;
import com.dowhile.MessageDetail;
import com.dowhile.dao.MessageDetailDAO;

/**
 * @author Hafiz Yameen Bashir
 *
 */
public class MessageDetailDAOImpl implements MessageDetailDAO{

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
	public MessageDetail addMessageDetail(MessageDetail messageDetail) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(messageDetail);
			return messageDetail;

		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public MessageDetail updateMessageDetail(MessageDetail messageDetail) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(messageDetail);
			return messageDetail;

		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteMessageDetail(MessageDetail messageDetail) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(messageDetail);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public void addMessageDetailList(List<MessageDetail> messageDetails,
			Company company) {
		try {
			for(MessageDetail messageDetail: messageDetails){
				getSessionFactory().getCurrentSession().save(messageDetail);
			}			
		} 
		catch (HibernateException ex) {
			ex.printStackTrace();
		}
	}

}
