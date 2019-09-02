/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.OrderMain;

/**
 * @author Zafar Shakeel
 *
 */
public interface OrderMainService {

	OrderMain addOrderMain(OrderMain orderMain, int companyId);
	OrderMain updateOrderMain(OrderMain orderMain, int companyId);
	boolean deleteOrderMain(OrderMain orderMain, int companyId);
	OrderMain getOrderMainByOrderMainId(int orderMainId, int companyId);
	List<OrderMain> getAllOrderMains(int companyId);
}
