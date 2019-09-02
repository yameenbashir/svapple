/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.OrderDetail;
import com.dowhile.dao.OrderDetailDAO;
import com.dowhile.service.OrderDetailService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class OrderDetailServiceImpl implements OrderDetailService{

	private OrderDetailDAO orderDetailDAO;
	
	/**
	 * @return the orderDetailDAO
	 */
	public OrderDetailDAO getOrderDetailDAO() {
		return orderDetailDAO;
	}

	/**
	 * @param orderDetailDAO the orderDetailDAO to set
	 */
	public void setOrderDetailDAO(OrderDetailDAO orderDetailDAO) {
		this.orderDetailDAO = orderDetailDAO;
	}

	@Override
	public OrderDetail addOrderDetail(OrderDetail orderDetail, int companyId) {
		// TODO Auto-generated method stub
		return getOrderDetailDAO().addOrderDetail(orderDetail,companyId);
	}

	@Override
	public OrderDetail updateOrderDetail(OrderDetail orderDetail, int companyId) {
		// TODO Auto-generated method stub
		return getOrderDetailDAO().updateOrderDetail(orderDetail,companyId);
	}

	@Override
	public boolean deleteOrderDetail(OrderDetail orderDetail, int companyId) {
		// TODO Auto-generated method stub
		return getOrderDetailDAO().deleteOrderDetail(orderDetail,companyId);
	}

	@Override
	public OrderDetail getOrderDetailByOrderDetailId(int orderDetailId, int companyId) {
		// TODO Auto-generated method stub
		return getOrderDetailDAO().getOrderDetailByOrderDetailId(orderDetailId,companyId);
	}

	@Override
	public List<OrderDetail> getAllOrderDetails(int companyId) {
		// TODO Auto-generated method stub
		return getOrderDetailDAO().getAllOrderDetails(companyId);
	}

}
