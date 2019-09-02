/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.OrderDetail;

/**
 * @author Zafar Shakeel
 *
 */
public interface OrderDetailService {

	OrderDetail addOrderDetail(OrderDetail orderDetail, int companyId);
	OrderDetail updateOrderDetail(OrderDetail orderDetail, int companyId);
	boolean deleteOrderDetail(OrderDetail orderDetail, int companyId);
	OrderDetail getOrderDetailByOrderDetailId(int orderDetailId, int companyId);
	List<OrderDetail> getAllOrderDetails(int companyId);
}
