/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.Company;
import com.dowhile.MessageDetail;

/**
 * @author Hafiz Yameen Bashir
 *
 */
public interface MessageDetailService {

	MessageDetail addMessageDetail(MessageDetail messageDetail);
	MessageDetail updateMessageDetail(MessageDetail messageDetail);
	boolean deleteMessageDetail(MessageDetail messageDetail);
    void addMessageDetailList(List<MessageDetail> messageDetails,Company company) ;
}
