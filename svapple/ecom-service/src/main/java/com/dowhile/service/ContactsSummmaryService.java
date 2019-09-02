/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.ContactsSummmary;

/**
 * @author Yameen Bashir
 *
 */
public interface ContactsSummmaryService {

	List<ContactsSummmary> getAllContactsSummmaryByCompanyOutletId(int companyId, int outletId);
	List<ContactsSummmary> getActiveContactsSummmaryByCompanyOutletId(int companyId, int outletId);
	List<ContactsSummmary> getAllContactsSummmaryByCompanyId(int companyId);
	List<ContactsSummmary> getActiveContactsSummmaryByCompanyId(int companyId);
	List<ContactsSummmary> getAllContactsSummmaryByCompanyIdOutletIdConctactType(int companyId, int outletId,String contactType);
	List<ContactsSummmary> getTenNewContactsSummmaryByCompanyIdOutletIdConctactType(int companyId, int outletId,String contactType);
	
}
