/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.ContactUs;
import com.dowhile.dao.ContactUsDAO;
import com.dowhile.service.ContactUsService;

/**
 * @author Yameen Bashir
 *
 */
@Transactional(readOnly = false)
public class ContactUsServiceImpl implements ContactUsService{

	private ContactUsDAO contactUsDAO;
	private EmailService emailService = new EmailService();
	/**
	 * @return the contactUsDAO
	 */
	public ContactUsDAO getContactUsDAO() {
		return contactUsDAO;
	}

	/**
	 * @param contactUsDAO the contactUsDAO to set
	 */
	public void setContactUsDAO(ContactUsDAO contactUsDAO) {
		this.contactUsDAO = contactUsDAO;
	}

	@Override
	public ContactUs addContactUs(ContactUs contactUs) {
		// TODO Auto-generated method stub
		
		ContactUs contactus = getContactUsDAO().addContactUs(contactUs);
		if(contactus!=null){
			emailService.sendEmailDemo(contactUs.getEmail(),contactUs.getName());
		}
		return contactus;
	}

	@Override
	public ContactUs updateContactUs(ContactUs contactUs) {
		// TODO Auto-generated method stub
		return getContactUsDAO().updateContactUs(contactUs);
	}

	@Override
	public boolean deleteContactUs(ContactUs contactUs) {
		// TODO Auto-generated method stub
		return getContactUsDAO().deleteContactUs(contactUs);
	}

	@Override
	public ContactUs getContactUsByContactUsId(int contactUsId) {
		// TODO Auto-generated method stub
		return getContactUsDAO().getContactUsByContactUsId(contactUsId);
	}

	@Override
	public List<ContactUs> getAllContactUs() {
		// TODO Auto-generated method stub
		return getContactUsDAO().getAllContactUs();
	}
}
