/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.PaymentType;

/**
 * @author Yameen Bashir
 *
 */
public interface PaymentTypeDAO {
	
	PaymentType addPaymentType(PaymentType paymentType, int companyId);
	PaymentType updatePaymentType(PaymentType paymentType, int companyId);
	boolean deletePaymentType(PaymentType paymentType, int companyId);
	PaymentType getPaymentTypeByPaymentTypeId(int paymentTypeId, int companyId);
	List<PaymentType> getAllPaymentTypes(int companyId);

}
