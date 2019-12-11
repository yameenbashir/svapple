/**
 * 
 */
package com.dowhile.service;

import java.util.List;
import java.util.Map;

import com.dowhile.Product;
import com.dowhile.Contact;

/**
 * @author T430s
 *
 */
public interface ContactService {
	Contact addContact(Contact Contact,int companyId);
	Contact updateContact(Contact Contact,int companyId);
	boolean deleteContact(Contact Contact,int companyId);
	Contact getContactByID(int ContactID,int companyId);
	Contact getContactByContactOutletID(int outletID,int companyId);
	int getOutletId(int ContactID,int companyId);
	List<Contact> getAllContacts(int companyId);
	List<Product> getAllProductsByContactID(int ContactID,int companyId);
	List<Contact> getContactsByOutletID(int outletID, int companyId);
	Map<Integer,Contact> getContactsByOutletIDMap(int outletID, int companyId);
	List<Contact> getcustomerByOutletID(int outletID, int companyId);
	boolean activeInactiveAllContactsByCompanyIdOutletIdUserIdContactTypeIsActive(int companyId,int outletId,int userId,String contactType,boolean isActive);
	List<Contact> getAllContactsByCompanyIdContactType(int companyId,String contactType);
}
