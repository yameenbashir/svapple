/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Company;
import com.dowhile.MessageDetail;
import com.dowhile.dao.MessageDetailDAO;
import com.dowhile.service.MessageDetailService;

/**
 * @author Hafiz Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class MessageDetailServiceImpl implements MessageDetailService{

	private MessageDetailDAO messageDetailDAO;
	
	/**
	 * @return the messageDetailDAO
	 */
	public MessageDetailDAO getMessageDetailDAO() {
		return messageDetailDAO;
	}

	/**
	 * @param messageDetailDAO the messageDetailDAO to set
	 */
	public void setMessageDetailDAO(MessageDetailDAO messageDetailDAO) {
		this.messageDetailDAO = messageDetailDAO;
	}

	@Override
	public MessageDetail addMessageDetail(MessageDetail messageDetail) {
		// TODO Auto-generated method stub
		return getMessageDetailDAO().addMessageDetail(messageDetail);
	}

	@Override
	public MessageDetail updateMessageDetail(MessageDetail messageDetail) {
		// TODO Auto-generated method stub
		return getMessageDetailDAO().updateMessageDetail(messageDetail);
	}

	@Override
	public boolean deleteMessageDetail(MessageDetail messageDetail) {
		// TODO Auto-generated method stub
		return getMessageDetailDAO().deleteMessageDetail(messageDetail);
	}

	@Override
	public void addMessageDetailList(List<MessageDetail> messageDetails,
			Company company) {
		getMessageDetailDAO().addMessageDetailList(messageDetails, company);
		
	}

}
