/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.ContactUs;

/**
 * @author Yameen Bashir
 *
 */
public interface ContactUsService {

	ContactUs addContactUs(ContactUs contactUs);
	ContactUs updateContactUs(ContactUs contactUs);
	boolean deleteContactUs(ContactUs contactUs);
	ContactUs getContactUsByContactUsId(int contactUsId);
	List<ContactUs> getAllContactUs();
}
