/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.OrderDetail;
import com.dowhile.OrderMain;

/**
 * @author hassan
 *
 */
public interface EcomOrderDAO {
	
	public OrderMain addOrderMain(OrderMain orderMain);
	public OrderMain updateOrderMain(OrderMain orderMain);
	public OrderMain getOrdersByOrderId(int orderId);
	public List<OrderMain> getOrdersByLikeOrderReferenceNo(String orderRefNo);
	public OrderMain getOrdersByExactOrderReferenceNo(String orderRefNo);
	public List<OrderMain> getOrdersByContactId(int contactId);
	public List<OrderMain> getOrdersByCompanyId(int companyId);
	public List<OrderMain> getOrdersByOutletId(int outletId);

	OrderDetail addOrderDetail(OrderDetail orderdetail);
	OrderDetail updateOrderDetail(OrderDetail orderdetail);
	public List<OrderDetail> getOrdersDetailByOrderId(int orderId);
	
	
}
