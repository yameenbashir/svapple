/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.DeliveryMethod;

/**
 * @author Yameen Bashir
 *
 */
public interface DeliveryMethodService {

	DeliveryMethod addDeliveryMethod(DeliveryMethod deliveryMethod, int companyId);
	DeliveryMethod updateDeliveryMethod(DeliveryMethod deliveryMethod, int companyId);
	boolean deleteDeliveryMethod(DeliveryMethod deliveryMethod, int companyId);
	DeliveryMethod getDeliveryMethodByDeliveryMethodId(int deliveryMethodId, int companyId);
	List<DeliveryMethod> getAllDeliveryMethods(int companyId);
}
