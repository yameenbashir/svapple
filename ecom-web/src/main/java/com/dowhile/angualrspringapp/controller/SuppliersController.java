package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Address;
import com.dowhile.Contact;
import com.dowhile.Product;
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
@RequestMapping("/suppliers")
public class SuppliersController {

	private static Logger logger = Logger.getLogger(SuppliersController.class.getName());
	@Resource
	private ContactService supplierService;
	@Resource
	private ServiceUtil util;
	@Resource
	private AddressService addressService;

	@RequestMapping("/layout")
	public String getSuppliersControllerPartialPage(ModelMap modelMap) {
		return "suppliers/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllSuppliers/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getAllSuppliers(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {

				List<Contact> suppliers = supplierService.getAllContacts(currentUser.getCompany().getCompanyId());
				List<SupplierBean> supplierBeans = new ArrayList<>();

				if (suppliers != null && suppliers.size() > 0) {

					for (Contact item : suppliers) {
						if(item.getContactType()!=null && item.getContactType().contains("SUPPLIER")){
						SupplierBean bean = new SupplierBean();
						bean.setSupplierName(item.getContactName());
						bean.setSupplierId(item.getContactId().toString());
						bean.setDescription(item.getDescription());
						if (item.getDefaultMarkup() != null) {
							bean.setDefaultMarkup(item.getDefaultMarkup()
									.toString());
						} else {
							bean.setDefaultMarkup("0");

						}
						if (item.getContactBalance() != null) {
							NumberFormat formatter = new DecimalFormat("###.##");  
							String total = formatter.format(item.getContactBalance().doubleValue());
							bean.setSupplierBalance(total);
						} else {
							bean.setSupplierBalance("0.00");

						}
						bean.setActiveIndicator("true");
						bean.setCompanyName(item.getCompanyName());

						List<Address> supplerAddress = addressService
								.getAddressBySupplierId(item.getContactId(),currentUser.getCompany().getCompanyId());
						List<AddressBean> supplierAddressBeans = new ArrayList<>();
						if (supplerAddress != null) {
							for (Address address : supplerAddress) {

								AddressBean addressBean = new AddressBean();

								addressBean.setAddressId(address.getAddressId()
										.toString());
								addressBean.setAddressType(address
										.getAddressType());
								addressBean.setCity(address.getCity());
								addressBean.setContactName(address
										.getContactName());
								addressBean.setCounty(address.getCounty());
								addressBean.setEmail(address.getEmail());
								addressBean.setFax(address.getFax());
								addressBean
										.setFirstName(address.getFirstName());
								addressBean.setLastName(address.getLastName());
								addressBean.setPhone(address.getPhone());
								addressBean.setPostalCode(address
										.getPostalCode());
								addressBean.setState(address.getState());
								addressBean.setStreet(address.getStreet());
								addressBean.setSuburb(address.getSuburb());
								addressBean.setSupplierId(item.getContactId()
										.toString());
								addressBean.setTwitter(address.getTwitter());
								addressBean.setWebsite(address.getWebsite());

								supplierAddressBeans.add(addressBean);

							}

							bean.setAddresses(supplierAddressBeans);
						}
						List<Product> list = supplierService
								.getAllProductsByContactID(item
										.getContactId(),currentUser.getCompany().getCompanyId());

						if (list != null && list.size() > 0) {
							int productCount = list.size();
							bean.setProductCount(String.valueOf(productCount));
						}

						supplierBeans.add(bean);
					}}
					
					util.AuditTrail(request, currentUser, "SuppliersController.getAllSuppliers", 
							"User "+ currentUser.getUserEmail()+" retrived all suppliers successfully ",false);
					return new Response(supplierBeans, StatusConstants.SUCCESS,
							LayOutPageConstants.STAY_ON_PAGE);
				} else {
					util.AuditTrail(request, currentUser, "SuppliersController.getAllSuppliers", 
							"User "+ currentUser.getUserEmail()+" unable to retrived all suppliers ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,
							StatusConstants.RECORD_NOT_FOUND,
							LayOutPageConstants.STAY_ON_PAGE);
				}
			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "SuppliersController.getAllSuppliers",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);

			}

		} else {
			return new Response(MessageConstants.INVALID_SESSION,
					StatusConstants.INVALID, LayOutPageConstants.LOGIN);
		}
	}

}
