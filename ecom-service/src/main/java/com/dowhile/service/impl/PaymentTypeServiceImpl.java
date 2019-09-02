/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.PaymentType;
import com.dowhile.dao.PaymentTypeDAO;
import com.dowhile.service.PaymentTypeService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class PaymentTypeServiceImpl implements PaymentTypeService{

	private PaymentTypeDAO paymentTypeDAO;
	
	/**
	 * @return the paymentTypeDAO
	 */
	public PaymentTypeDAO getPaymentTypeDAO() {
		return paymentTypeDAO;
	}

	/**
	 * @param paymentTypeDAO the paymentTypeDAO to set
	 */
	public void setPaymentTypeDAO(PaymentTypeDAO paymentTypeDAO) {
		this.paymentTypeDAO = paymentTypeDAO;
	}

	@Override
	public PaymentType addPaymentType(PaymentType paymentType, int companyId) {
		// TODO Auto-generated method stub
		return getPaymentTypeDAO().addPaymentType(paymentType,companyId);
	}

	@Override
	public PaymentType updatePaymentType(PaymentType paymentType, int companyId) {
		// TODO Auto-generated method stub
		return getPaymentTypeDAO().updatePaymentType(paymentType,companyId);
	}

	@Override
	public boolean deletePaymentType(PaymentType paymentType, int companyId) {
		// TODO Auto-generated method stub
		return getPaymentTypeDAO().deletePaymentType(paymentType,companyId);
	}

	@Override
	public PaymentType getPaymentTypeByPaymentTypeId(int paymentTypeId, int companyId) {
		// TODO Auto-generated method stub
		return getPaymentTypeDAO().getPaymentTypeByPaymentTypeId(paymentTypeId,companyId);
	}

	@Override
	public List<PaymentType> getAllPaymentTypes(int companyId) {
		// TODO Auto-generated method stub
		return getPaymentTypeDAO().getAllPaymentTypes(companyId);
	}

}
