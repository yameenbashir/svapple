/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.ContactGroup;

/**
 * @author Zafar Shakeel
 *
 */
public interface ContactGroupDAO {
	
	ContactGroup addContactGroup(ContactGroup ContactGroup, int companyId);
	ContactGroup updateContactGroup(ContactGroup ContactGroup, int companyId);
	boolean deleteContactGroup(ContactGroup ContactGroup, int companyId);
	ContactGroup getContactGroupByContactGroupId(int companyId);
	List<ContactGroup> GetAllContactGroup(int companyId);
}
