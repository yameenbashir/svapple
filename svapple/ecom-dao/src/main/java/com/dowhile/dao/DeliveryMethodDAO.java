/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.DeliveryMethod;

/**
 * @author Zafar Shakeel
 *
 */
public interface DeliveryMethodDAO {

	DeliveryMethod addDeliveryMethod(DeliveryMethod deliveryMethod, int companyId);
	DeliveryMethod updateDeliveryMethod(DeliveryMethod deliveryMethod, int companyId);
	boolean deleteDeliveryMethod(DeliveryMethod deliveryMethod, int companyId);
	DeliveryMethod getDeliveryMethodByDeliveryMethodId(int deliveryMethodId, int companyId);
	List<DeliveryMethod> getAllDeliveryMethods(int companyId);
}
