/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.PaymentType;

/**
 * @author Yameen Bashir
 *
 */
public interface PaymentTypeService {
	
	PaymentType addPaymentType(PaymentType paymentType, int companyId);
	PaymentType updatePaymentType(PaymentType paymentType, int companyId);
	boolean deletePaymentType(PaymentType paymentType, int companyId);
	PaymentType getPaymentTypeByPaymentTypeId(int paymentTypeId, int companyId);
	List<PaymentType> getAllPaymentTypes(int companyId);

}
