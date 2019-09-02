/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.ContactPayments;
import com.dowhile.dao.ContactPaymentsDAO;
import com.dowhile.service.ContactPaymentsService;

/**
 * @author T430s
 *
 */

@Transactional(readOnly = false)
public class ContactPaymentsServiceImpl implements ContactPaymentsService {

	private ContactPaymentsDAO  ContactPaymentsDAO;
	
	@Override
	public ContactPayments addContactPayments(ContactPayments ContactPayments,int companyId) {
		return getContactPaymentsDAO().addContactPayments(ContactPayments,companyId);
	}

	@Override
	public ContactPayments updateContactPayments(ContactPayments ContactPayments,int companyId) {
		return getContactPaymentsDAO().updateContactPayments(ContactPayments,companyId);
	}

	@Override
	public boolean deleteContactPayments(ContactPayments ContactPayments,int companyId) {
		return getContactPaymentsDAO().deleteContactPayments(ContactPayments,companyId);
	}

	@Override
	public ContactPayments getContactPaymentsByID(int ContactPaymentsID,int companyId) {
		return getContactPaymentsDAO().getContactPaymentsByID(ContactPaymentsID,companyId);
	}

	@Override
	public List<ContactPayments> getAllContactPayments(int companyId) {
		return getContactPaymentsDAO().getAllContactPayments(companyId);
	}

	@Override
	public List<ContactPayments> getAllContactPaymentsByContactId(int ContactId, int companyId) {
		return getContactPaymentsDAO().getContactPaymentsByContactId(ContactId, companyId);
	}


	
	/**
	 * @return the ContactPaymentsDAO
	 */
	public ContactPaymentsDAO getContactPaymentsDAO() {
		return ContactPaymentsDAO;
	}

	/**
	 * @param ContactPaymentsDAO the ContactPaymentsDAO to set
	 */
	public void setContactPaymentsDAO(ContactPaymentsDAO ContactPaymentsDAO) {
		this.ContactPaymentsDAO = ContactPaymentsDAO;
	}

	@Override
	public List<ContactPayments> getSupplierPaymentsByContactId(int ContactID,
			int companyId) {
		// TODO Auto-generated method stub
		return getContactPaymentsDAO().getSupplierPaymentsByContactId(ContactID, companyId);
	}

	@Override
	public List<ContactPayments> getCustonerPaymentsByContactId(int ContactID,
			int companyId) {
		// TODO Auto-generated method stub
		return getContactPaymentsDAO().getCustonerPaymentsByContactId(ContactID, companyId);
	}

}
