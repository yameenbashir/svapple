package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Address;
import com.dowhile.Contact;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.AddressBean;
import com.dowhile.frontend.mapping.bean.SupplierBean;
import com.dowhile.service.AddressService;
import com.dowhile.service.ContactService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/newSupplier")
public class NewSupplierController {

	// private static Logger logger = Logger.getLogger(NewSupplierController.class.getName());
	@Resource
	private ContactService supplierService;

	@Resource
	private AddressService addressService;

	@Resource
	private ServiceUtil util;

	@RequestMapping("/layout")
	public String getNewSupplierControllerPartialPage(ModelMap modelMap) {
		return "newSupplier/layout";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/addSupplier/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response addSupplier(@PathVariable("sessionId") String sessionId,
			@RequestBody SupplierBean supplierBean, HttpServletRequest request) {

		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {
				Contact supplier = new Contact();
				if (supplierBean != null) {

					supplier.setContactName(supplierBean.getSupplierName());
					supplier.setCompanyName(supplierBean.getCompanyName());
					supplier.setDescription(supplierBean.getDescription());
					supplier.setActiveIndicator(true);
					if (supplierBean.getDefaultMarkup() != null
							&& !supplierBean.getDefaultMarkup().isEmpty()) {
						supplier.setDefaultMarkup(new BigDecimal(supplierBean
								.getDefaultMarkup()));
					} else {
						supplier.setDefaultMarkup(new BigDecimal("0"));
					}
					supplier.setCompany(currentUser.getCompany());
					supplier.setContactType("SUPPLIER");
					supplier.setOutlet(currentUser.getOutlet());
					supplierService.addContact(supplier,currentUser.getCompany().getCompanyId());
				}

				if (supplierBean != null && supplierBean.getAddresses() != null
						&& supplierBean.getAddresses().size() > 0) {
					for (AddressBean addressBean : supplierBean.getAddresses()) {

						Address address = new Address();
						address.setAddressType(addressBean.getAddressType());
						address.setCity(addressBean.getCity());
						address.setContactName(addressBean.getContactName());
						address.setCounty(addressBean.getCounty());
						address.setEmail(addressBean.getEmail());
						address.setFax(addressBean.getFax());

						address.setActiveIndicator(true);
						address.setFirstName(addressBean.getFirstName());
						address.setLastName(addressBean.getLastName());
						address.setPhone(addressBean.getPhone());
						address.setState(addressBean.getState());
						address.setPostalCode(addressBean.getPostalCode());
						address.setState(addressBean.getState());
						address.setCity(addressBean.getCity());
						address.setSuburb(addressBean.getSuburb());
						address.setStreet(addressBean.getStreet());
						address.setTwitter(addressBean.getTwitter());
						address.setWebsite(addressBean.getWebsite());

						address.setContact(supplier);
						address.setCompany(currentUser.getCompany());
						addressService.addAddress(address,currentUser.getCompany().getCompanyId());

					}

				}

				util.AuditTrail(
						request,
						currentUser,
						"OutletController.addOutlet",
						"User " + currentUser.getUserEmail()
								+ " Added Supplier+"
								+ supplierBean.getSupplierName()
								+ " successfully ", false);
				return new Response(MessageConstants.REQUREST_PROCESSED,
						StatusConstants.SUCCESS, LayOutPageConstants.SUPPLIERS);

			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser,
						"OutletController.addOutlet",
						"Error Occured " + errors.toString(), true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.ADD_RESTRICTED,
						LayOutPageConstants.STAY_ON_PAGE);
			}
		} else {
			return new Response(MessageConstants.INVALID_SESSION,
					StatusConstants.INVALID, LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/updateSupplier/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response updateSupplier(@PathVariable("sessionId") String sessionId,
			@RequestBody SupplierBean supplierBean, HttpServletRequest request) {

		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {

				if (supplierBean != null) {
					Contact supplier = supplierService.getContactByID(Integer
							.parseInt(supplierBean.getSupplierId()),currentUser.getCompany().getCompanyId());
					supplier.setContactName(supplierBean.getSupplierName());
					supplier.setCompanyName(supplierBean.getCompanyName());
					supplier.setDescription(supplierBean.getDescription());

					if (supplierBean.getDefaultMarkup() != null
							&& !supplierBean.getDefaultMarkup().isEmpty()) {
						supplier.setDefaultMarkup(new BigDecimal(supplierBean
								.getDefaultMarkup()));
					} else {
						supplier.setDefaultMarkup(new BigDecimal("0"));
					}
					supplierService.updateContact(supplier,currentUser.getCompany().getCompanyId());

					if (supplierBean.getAddresses() != null
							&& supplierBean.getAddresses().size() > 0) {
						for (AddressBean addressBean : supplierBean
								.getAddresses()) {

							if (addressBean.getAddressId() == null
									|| addressBean.getAddressId().isEmpty()) {
								Address address = new Address();
								address.setAddressType(addressBean
										.getAddressType());
								address.setCity(addressBean.getCity());
								address.setContactName(addressBean
										.getContactName());
								address.setCounty(addressBean.getCounty());
								address.setEmail(addressBean.getEmail());
								address.setFax(addressBean.getFax());

								address.setActiveIndicator(true);
								address.setFirstName(addressBean.getFirstName());
								address.setLastName(addressBean.getLastName());
								address.setPhone(addressBean.getPhone());
								address.setState(addressBean.getState());
								address.setPostalCode(addressBean
										.getPostalCode());
								address.setState(addressBean.getState());
								address.setCity(addressBean.getCity());
								address.setSuburb(addressBean.getSuburb());
								address.setStreet(addressBean.getStreet());
								address.setTwitter(addressBean.getTwitter());
								address.setWebsite(addressBean.getWebsite());

								address.setCompany(currentUser.getCompany());
								address.setContact(supplier);
								addressService.addAddress(address,currentUser.getCompany().getCompanyId());
							}

							else {

								Address address = addressService
										.getAddressByAddressId(Integer
												.parseInt(addressBean
														.getAddressId()),currentUser.getCompany().getCompanyId());

								if (address != null) {
									address.setAddressType(addressBean
											.getAddressType());
									address.setCity(addressBean.getCity());
									address.setContactName(addressBean
											.getContactName());
									address.setCounty(addressBean.getCounty());
									address.setEmail(addressBean.getEmail());
									address.setFax(addressBean.getFax());

									address.setActiveIndicator(true);
									address.setFirstName(addressBean
											.getFirstName());
									address.setLastName(addressBean
											.getLastName());
									address.setPhone(addressBean.getPhone());
									address.setState(addressBean.getState());
									address.setPostalCode(addressBean
											.getPostalCode());
									address.setState(addressBean.getState());
									address.setCity(addressBean.getCity());
									address.setSuburb(addressBean.getSuburb());
									address.setStreet(addressBean.getStreet());
									address.setTwitter(addressBean.getTwitter());
									address.setWebsite(addressBean.getWebsite());
									addressService.updateAddress(address,currentUser.getCompany().getCompanyId());
								}

							}
						}
					}

				}

				util.AuditTrail(
						request,
						currentUser,
						"OutletController.addOutlet",
						"User " + currentUser.getUserEmail()
								+ " Added Supplier+"
								+ supplierBean.getSupplierName()
								+ " successfully ", false);
				return new Response(MessageConstants.REQUREST_PROCESSED,
						StatusConstants.SUCCESS, LayOutPageConstants.SUPPLIERS);

			} catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser,
						"OutletController.addOutlet",
						"Error Occured " + errors.toString(), true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.ADD_RESTRICTED,
						LayOutPageConstants.STAY_ON_PAGE);
			}
		} else {
			return new Response(MessageConstants.INVALID_SESSION,
					StatusConstants.INVALID, LayOutPageConstants.LOGIN);
		}
	}
}
