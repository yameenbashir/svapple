/**
 * 
 */
package com.dowhile.service;

import com.dowhile.Message;

/**
 * @author Hafiz Yameen Bashir
 *
 */
public interface MessageService {

	Message addMessage(Message message);
	Message getMessageByCompanyId(String companyId);
	Message getMessageByMaskName(String maskName);
	Message updateMessage(Message message);
	boolean deleteMessage(Message message);
	boolean updateMessageTextLimtByCompanyId(int companyId);
	
}
