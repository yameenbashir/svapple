package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import com.dowhile.OrderDetail;
import com.dowhile.OrderMain;
import com.dowhile.dao.EcomOrderDAO;
import com.dowhile.service.EcomOrderService;
/**
 * @author hassan
 *
 */
@Transactional(readOnly = false)
public class EcomOrderServiceImpl implements EcomOrderService{
	
	private EcomOrderDAO ecomOrderDAO;

	public EcomOrderDAO getEcomOrderDAO() {
		return ecomOrderDAO;
	}

	public void setEcomOrderDAO(EcomOrderDAO ecomOrderDAO) {
		this.ecomOrderDAO = ecomOrderDAO;
	}

	@Override
	public OrderMain addOrderMain(OrderMain orderMain) {
		return ecomOrderDAO.addOrderMain(orderMain) ;
	}

	@Override
	public OrderMain updateOrderMain(OrderMain orderMain) {
		return ecomOrderDAO.updateOrderMain(orderMain) ;
	}

	@Override
	public OrderMain getOrdersByOrderId(int orderId) {
		return ecomOrderDAO.getOrdersByOrderId(orderId) ;
	}

	@Override
	public List<OrderMain> getOrdersByLikeOrderReferenceNo(String orderRefNo) {
		return ecomOrderDAO.getOrdersByLikeOrderReferenceNo(orderRefNo) ;
	}

	@Override
	public OrderMain getOrdersByExactOrderReferenceNo(String orderRefNo) {
		return ecomOrderDAO.getOrdersByExactOrderReferenceNo(orderRefNo) ;
	}

	@Override
	public List<OrderMain> getOrdersByContactId(int contactId) {
		return ecomOrderDAO.getOrdersByContactId(contactId) ;
	}

	@Override
	public List<OrderMain> getOrdersByCompanyId(int companyId) {
		return ecomOrderDAO.getOrdersByCompanyId(companyId) ;
	}

	@Override
	public List<OrderMain> getOrdersByOutletId(int outletId) {
		return ecomOrderDAO.getOrdersByOutletId(outletId) ;
	}

	@Override
	public OrderDetail addOrderDetail(OrderDetail orderdetail) {
		return ecomOrderDAO.addOrderDetail(orderdetail) ;
	}

	@Override
	public OrderDetail updateOrderDetail(OrderDetail orderdetail) {
		return ecomOrderDAO.updateOrderDetail(orderdetail) ;
	}

	@Override
	public List<OrderDetail> getOrdersDetailByOrderId(int orderId) {
		return ecomOrderDAO.getOrdersDetailByOrderId(orderId) ;
	}

	
	
}
