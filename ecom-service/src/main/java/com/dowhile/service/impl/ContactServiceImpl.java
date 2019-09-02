/**
 * 
 */
package com.dowhile.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Contact;
import com.dowhile.Product;
import com.dowhile.dao.ContactDAO;
import com.dowhile.dao.ContactGroupDAO;
import com.dowhile.service.ContactService;

/**
 * @author T430s
 *
 */

@Transactional(readOnly = false)
public class ContactServiceImpl implements ContactService {

	private ContactDAO  ContactDAO;
	private ContactGroupDAO  contactGroupDAO;
	
	@Override
	public Contact addContact(Contact Contact,int companyId) {
		if(Contact.getFirstName()==null || Contact.getFirstName().equals("")){
			if(Contact.getContactName()==null){
				Contact.setFirstName("Customer");	
			}else{
				Contact.setFirstName(Contact.getContactName());
			}
			
		}
		if(Contact.getContactName()==null){
			Contact.setContactName(Contact.getFirstName());
		}
		if(Contact.getContactGroup()==null){
			Contact.setContactGroup(contactGroupDAO.getContactGroupByContactGroupId(companyId));
		}
		return getContactDAO().addContact(Contact,companyId);
	}

	@Override
	public Contact updateContact(Contact Contact,int companyId) {
		return getContactDAO().updateContact(Contact,companyId);
	}

	@Override
	public boolean deleteContact(Contact Contact,int companyId) {
		return getContactDAO().deleteContact(Contact,companyId);
	}

	@Override
	public Contact getContactByID(int ContactID,int companyId) {
		return getContactDAO().getContactByID(ContactID,companyId);
	}

	@Override
	public Contact getContactByContactOutletID(int outletID,int companyId) {
		return getContactDAO().getContactByContactOutletID(outletID,companyId);
	}

	
	@Override
	public int getOutletId(int ContactID,int companyId) {
		return getContactDAO().getOutletId(ContactID,companyId);
	}

	
	@Override
	public List<Contact> getAllContacts(int companyId) {
		return getContactDAO().getAllContacts(companyId);
	}

	@Override
	public List<Product> getAllProductsByContactID(int ContactID,int companyId) {
		return getContactDAO().getAllProductsByContactID(ContactID,companyId);
	}

	/**
	 * @return the ContactDAO
	 */
	public ContactDAO getContactDAO() {
		return ContactDAO;
	}

	/**
	 * @param ContactDAO the ContactDAO to set
	 */
	public void setContactDAO(ContactDAO ContactDAO) {
		this.ContactDAO = ContactDAO;
	}

	public ContactGroupDAO getContactGroupDAO() {
		return contactGroupDAO;
	}

	public void setContactGroupDAO(ContactGroupDAO contactGroupDAO) {
		this.contactGroupDAO = contactGroupDAO;
	}

	@Override
	public List<Contact> getContactsByOutletID(int outletID, int companyId) {
		// TODO Auto-generated method stub
		return getContactDAO().getContactsByOutletID(outletID, companyId);
	}

	@Override
	public List<Contact> getcustomerByOutletID(int outletID, int companyId) {
		// TODO Auto-generated method stub
		return getContactDAO().getcustomerByOutletID(outletID, companyId);
	}

	@Override
	public Map<Integer, Contact> getContactsByOutletIDMap(int outletID,
			int companyId) {
		// TODO Auto-generated method stub
		 List<Contact> contacts =  getContactDAO().getContactsByOutletID(outletID, companyId);
		 Map<Integer, Contact> map = new HashMap<Integer, Contact>();
		 if(contacts!=null){
			 for(Contact contact : contacts){
				 map.put(contact.getContactId(), contact);
			 }
		 }
		return map;
	}

	@Override
	public boolean activeInactiveAllContactsByCompanyIdOutletIdUserIdContactTypeIsActive(
			int companyId, int outletId, int userId, String contactType,
			boolean isActive) {
		// TODO Auto-generated method stub
		return getContactDAO().activeInactiveAllContactsByCompanyIdOutletIdUserIdContactTypeIsActive(companyId, outletId, userId, contactType, isActive);
	}

}
