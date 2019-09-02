/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.ContactGroup;

/**
 * @author Zafar Shakeel
 *
 */
public interface ContactGroupService {

	ContactGroup addContactGroup(ContactGroup ContactGroup, int companyId);
	ContactGroup updateContactGroup(ContactGroup ContactGroup, int companyId);
	boolean deleteContactGroup(ContactGroup ContactGroup, int companyId);
	ContactGroup getContactGroupByContactGroupId(int ContactGroupId, int companyId);
	List<ContactGroup> GetAllContactGroup(int companyId);
}
