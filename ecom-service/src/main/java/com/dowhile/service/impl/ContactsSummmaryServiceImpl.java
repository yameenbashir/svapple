/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.ContactsSummmary;
import com.dowhile.dao.ContactsSummmaryDAO;
import com.dowhile.service.ContactsSummmaryService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class ContactsSummmaryServiceImpl implements ContactsSummmaryService{

	private ContactsSummmaryDAO contactsSummmaryDAO;
	/**
	 * @return the ContactsSummmaryDAO
	 */
	public ContactsSummmaryDAO getContactsSummmaryDAO() {
		return contactsSummmaryDAO;
	}
	/**
	 * @param ContactsSummmaryDAO the ContactsSummmaryDAO to set
	 */
	public void setContactsSummmaryDAO(ContactsSummmaryDAO ContactsSummmaryDAO) {
		this.contactsSummmaryDAO = ContactsSummmaryDAO;
	}
	@Override
	public List<ContactsSummmary> getAllContactsSummmaryByCompanyOutletId(int companyId, int outletId) {
		// TODO Auto-generated method stub
		return getContactsSummmaryDAO().getAllContactsSummmaryByCompanyOutletId(companyId,outletId);
	}
	@Override
	public List<ContactsSummmary> getAllContactsSummmaryByCompanyId(
			int companyId) {
		// TODO Auto-generated method stub
		return getContactsSummmaryDAO().getAllContactsSummmaryByCompanyId(companyId);
	}
	@Override
	public List<ContactsSummmary> getActiveContactsSummmaryByCompanyId(
			int companyId) {
		// TODO Auto-generated method stub
				return getContactsSummmaryDAO().getActiveContactsSummmaryByCompanyId(companyId);
	}
	@Override
	public List<ContactsSummmary> getActiveContactsSummmaryByCompanyOutletId(
			int companyId, int outletId) {
		// TODO Auto-generated method stub
		return getContactsSummmaryDAO().getActiveContactsSummmaryByCompanyOutletId(companyId, outletId);
	}
	@Override
	public List<ContactsSummmary> getAllContactsSummmaryByCompanyIdOutletIdConctactType(
			int companyId, int outletId, String contactType) {
		// TODO Auto-generated method stub
		return getContactsSummmaryDAO().getAllContactsSummmaryByCompanyIdOutletIdConctactType(companyId, outletId, contactType);
	}
	@Override
	public List<ContactsSummmary> getTenNewContactsSummmaryByCompanyIdOutletIdConctactType(
			int companyId, int outletId, String contactType) {
		// TODO Auto-generated method stub
		return getContactsSummmaryDAO().getTenNewContactsSummmaryByCompanyIdOutletIdConctactType(companyId, outletId, contactType);
	}

}
