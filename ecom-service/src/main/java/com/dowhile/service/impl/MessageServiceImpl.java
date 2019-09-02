/**
 * 
 */
package com.dowhile.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Message;
import com.dowhile.dao.MessageDAO;
import com.dowhile.service.MessageService;

/**
 * @author Hafiz Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class MessageServiceImpl implements MessageService{

	private MessageDAO messageDAO;
	
	/**
	 * @return the messageDAO
	 */
	public MessageDAO getMessageDAO() {
		return messageDAO;
	}

	/**
	 * @param messageDAO the messageDAO to set
	 */
	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	@Override
	public Message addMessage(Message message) {
		// TODO Auto-generated method stub
		return getMessageDAO().addMessage(message);
	}

	@Override
	public Message updateMessage(Message message) {
		// TODO Auto-generated method stub
		return getMessageDAO().updateMessage(message);
	}

	@Override
	public boolean deleteMessage(Message message) {
		// TODO Auto-generated method stub
		return getMessageDAO().deleteMessage(message);
	}

	@Override
	public Message getMessageByCompanyId(String companyId) {
		// TODO Auto-generated method stub
		return getMessageDAO().getMessageByCompanyId(companyId);
	}

	@Override
	public Message getMessageByMaskName(String maskName) {
		// TODO Auto-generated method stub
		return getMessageDAO().getMessageByMaskName(maskName);
	}



}
