package com.dowhile.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.ContactPaymentsType;
import com.dowhile.dao.ContactPaymentsTypeDAO;
import com.dowhile.service.ContactPaymentsTypeService;
@Transactional(readOnly = false)
public class ContactPaymentsTypeServiceImpl implements
		ContactPaymentsTypeService {

	ContactPaymentsTypeDAO contactPaymentsTypeDAO;
	
	
	
	@Override
	public ContactPaymentsType getContactPaymentsTypeByID(
			int ContactPaymentsType, int companyId) {
		// TODO Auto-generated method stub
		return contactPaymentsTypeDAO.getContactPaymentsTypeByID(ContactPaymentsType, companyId);
	}



	public ContactPaymentsTypeDAO getContactPaymentsTypeDAO() {
		return contactPaymentsTypeDAO;
	}



	public void setContactPaymentsTypeDAO(
			ContactPaymentsTypeDAO contactPaymentsTypeDAO) {
		this.contactPaymentsTypeDAO = contactPaymentsTypeDAO;
	}

}
