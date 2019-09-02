/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.Company;
import com.dowhile.MessageDetail;

/**
 * @author Hafiz Yameen Bashir
 *
 */
public interface MessageDetailDAO {

	MessageDetail addMessageDetail(MessageDetail messageDetail);
	MessageDetail updateMessageDetail(MessageDetail messageDetail);
	boolean deleteMessageDetail(MessageDetail messageDetail);
	public void addMessageDetailList(List<MessageDetail> messageDetails,Company company) ;
}
