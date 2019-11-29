/**
 * 
 */
package com.dowhile.dao;

import com.dowhile.Message;

/**
 * @author Hafiz Yameen Bashir
 *
 */
public interface MessageDAO {

	Message addMessage(Message message);
	Message updateMessage(Message message);
	boolean deleteMessage(Message message);
	Message getMessageByCompanyId(String companyId);
	Message getMessageByMaskName(String maskName);
	boolean updateMessageTextLimtByCompanyId(int companyId);
}
