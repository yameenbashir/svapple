/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.DeliveryMethod;
import com.dowhile.dao.DeliveryMethodDAO;
import com.dowhile.service.DeliveryMethodService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class DeliveryMethodServiceImpl implements DeliveryMethodService{

	private DeliveryMethodDAO deliveryMethodDAO;
	
	/**
	 * @return the deliveryMethodDAO
	 */
	public DeliveryMethodDAO getDeliveryMethodDAO() {
		return deliveryMethodDAO;
	}

	/**
	 * @param deliveryMethodDAO the deliveryMethodDAO to set
	 */
	public void setDeliveryMethodDAO(DeliveryMethodDAO deliveryMethodDAO) {
		this.deliveryMethodDAO = deliveryMethodDAO;
	}

	@Override
	public DeliveryMethod addDeliveryMethod(DeliveryMethod deliveryMethod, int companyId) {
		// TODO Auto-generated method stub
		return getDeliveryMethodDAO().addDeliveryMethod(deliveryMethod,companyId);
	}

	@Override
	public DeliveryMethod updateDeliveryMethod(DeliveryMethod deliveryMethod, int companyId) {
		// TODO Auto-generated method stub
		return getDeliveryMethodDAO().updateDeliveryMethod(deliveryMethod,companyId);
	}

	@Override
	public boolean deleteDeliveryMethod(DeliveryMethod deliveryMethod, int companyId) {
		// TODO Auto-generated method stub
		return getDeliveryMethodDAO().deleteDeliveryMethod(deliveryMethod,companyId);
	}

	@Override
	public DeliveryMethod getDeliveryMethodByDeliveryMethodId(int deliveryMethodId, int companyId) {
		// TODO Auto-generated method stub
		return getDeliveryMethodDAO().getDeliveryMethodByDeliveryMethodId(deliveryMethodId,companyId);
	}

	@Override
	public List<DeliveryMethod> getAllDeliveryMethods(int companyId) {
		// TODO Auto-generated method stub
		return getDeliveryMethodDAO().getAllDeliveryMethods(companyId);
	}

}
