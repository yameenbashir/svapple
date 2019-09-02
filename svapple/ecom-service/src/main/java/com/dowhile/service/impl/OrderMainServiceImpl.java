/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.OrderMain;
import com.dowhile.dao.OrderMainDAO;
import com.dowhile.service.OrderMainService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class OrderMainServiceImpl implements OrderMainService{

	private OrderMainDAO orderMainDAO;
	
	/**
	 * @return the orderMainDAO
	 */
	public OrderMainDAO getOrderMainDAO() {
		return orderMainDAO;
	}

	/**
	 * @param orderMainDAO the orderMainDAO to set
	 */
	public void setOrderMainDAO(OrderMainDAO orderMainDAO) {
		this.orderMainDAO = orderMainDAO;
	}

	@Override
	public OrderMain addOrderMain(OrderMain orderMain, int companyId) {
		// TODO Auto-generated method stub
		return getOrderMainDAO().addOrderMain(orderMain,companyId);
	}

	@Override
	public OrderMain updateOrderMain(OrderMain orderMain, int companyId) {
		// TODO Auto-generated method stub
		return getOrderMainDAO().updateOrderMain(orderMain,companyId);
	}

	@Override
	public boolean deleteOrderMain(OrderMain orderMain, int companyId) {
		// TODO Auto-generated method stub
		return getOrderMainDAO().deleteOrderMain(orderMain,companyId);
	}

	@Override
	public OrderMain getOrderMainByOrderMainId(int orderMainId, int companyId) {
		// TODO Auto-generated method stub
		return getOrderMainDAO().getOrderMainByOrderMainId(orderMainId,companyId);
	}

	@Override
	public List<OrderMain> getAllOrderMains(int companyId) {
		// TODO Auto-generated method stub
		return getOrderMainDAO().getAllOrderMains(companyId);
	}

}
