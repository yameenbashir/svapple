/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.ContactGroup;
import com.dowhile.dao.ContactGroupDAO;
import com.dowhile.service.ContactGroupService;

/**
 * @author Zafar Shakeel
 *
 */
@Transactional(readOnly = false)
public class ContactGroupServiceImpl implements ContactGroupService{

	private ContactGroupDAO ContactGroupDAO;
	/**
	 * @return the ContactGroupDAO
	 */
	public ContactGroupDAO getContactGroupDAO() {
		return ContactGroupDAO;
	}

	/**
	 * @param ContactGroupDAO the ContactGroupDAO to set
	 */
	public void setContactGroupDAO(ContactGroupDAO ContactGroupDAO) {
		this.ContactGroupDAO = ContactGroupDAO;
	}

	@Override
	public ContactGroup addContactGroup(ContactGroup ContactGroup, int companyId) {
		// TODO Auto-generated method stub
		return getContactGroupDAO().addContactGroup(ContactGroup,companyId);
	}

	@Override
	public ContactGroup updateContactGroup(ContactGroup ContactGroup, int companyId) {
		// TODO Auto-generated method stub
		return getContactGroupDAO().updateContactGroup(ContactGroup,companyId);
	}

	@Override
	public boolean deleteContactGroup(ContactGroup ContactGroup, int companyId) {
		// TODO Auto-generated method stub
		return getContactGroupDAO().deleteContactGroup(ContactGroup,companyId);
	}

	@Override
	public ContactGroup getContactGroupByContactGroupId(int ContactGroupId, int companyId) {
		// TODO Auto-generated method stub
		return getContactGroupDAO().getContactGroupByContactGroupId(companyId);
	}

	@Override
	public List<ContactGroup> GetAllContactGroup(int companyId) {
		// TODO Auto-generated method stub
		return getContactGroupDAO().GetAllContactGroup(companyId);
	}
	
}
