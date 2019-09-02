/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.ContactPayments;

/**
 * @author Zafar
 *
 */
public interface ContactPaymentsDAO {
	ContactPayments addContactPayments(ContactPayments ContactPayments,int companyId);
	ContactPayments updateContactPayments(ContactPayments ContactPayments,int companyId);
	boolean deleteContactPayments(ContactPayments ContactPayments,int companyId);
	ContactPayments getContactPaymentsByID(int ContactPaymentsID,int companyId);
	List<ContactPayments> getContactPaymentsByContactId(int ContactID,int companyId);
	List<ContactPayments> getSupplierPaymentsByContactId(int ContactID,int companyId);
	List<ContactPayments> getCustonerPaymentsByContactId(int ContactID,int companyId);
	List<ContactPayments> getAllContactPayments(int companyId);
}
