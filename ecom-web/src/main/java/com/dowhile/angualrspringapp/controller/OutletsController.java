package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
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
import com.dowhile.Country;
import com.dowhile.Outlet;
import com.dowhile.Register;
import com.dowhile.SalesTax;
import com.dowhile.TimeZone;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.AddressBean;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.RegisterBean;
import com.dowhile.service.AddressService;
import com.dowhile.service.CountryService;
import com.dowhile.service.OutletService;
import com.dowhile.service.RegisterService;
import com.dowhile.service.SalesTaxService;
import com.dowhile.service.TimeZoneService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/outlets")
public class OutletsController {

	private static Logger logger = Logger.getLogger(OutletsController.class.getName());
	@Resource
	private RegisterService registerService;
	@Resource
	private OutletService outletService;
	@Resource
	private SalesTaxService salesTaxService;
	@Resource
	private TimeZoneService timeZoneService;
	@Resource
	private AddressService addressService;
	@Resource
	private CountryService countryService;
	@Resource
	private ServiceUtil util;

	@RequestMapping("/layout")
	public String getOutletsControllerPartialPage(ModelMap modelMap) {
		return "outlets/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getOutlets/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getOutlets(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<OutletBean> outletBeans = new ArrayList<>();
		List<Outlet> outlets = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				if(currentUser.getRole().getRoleId()==1){
					outlets = outletService.getOutlets(currentUser.getCompany().getCompanyId());
				}else{
					outlets = new ArrayList<>();
					Outlet outlet = outletService.getOuletByOutletId(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId()) ;
					outlets.add(outlet);
				}
				
				
				if(outlets!=null){
					for(Outlet outlet:outlets){
						OutletBean outletBean = new OutletBean();
						List<RegisterBean> registerBeanList = new ArrayList<>();
						//	outletBean.setDefaultTax(outlet.getSelsTaxPercentage().toString());
						//outletBean.setDetails("");
						outletBean.setOutletId(outlet.getOutletId().toString());
						outletBean.setOutletName(outlet.getOutletName());
						SalesTax salesTax = salesTaxService.getSalesTaxBySalesTaxId(outlet.getSalesTax().getSalesTaxId(),currentUser.getCompany().getCompanyId());
						outletBean.setSalesTaxId(salesTax.getSalesTaxId().toString());
						outletBean.setSalesTaxName(salesTax.getSalesTaxName()+"("+salesTax.getSalesTaxPercentage().toString()+")");
						outletBean.setDefaultTax(salesTax.getSalesTaxPercentage().toString());
						outletBean.setOrderNumber(outlet.getOrderNumber());
						outletBean.setOrderNumberPrefix(outlet.getOrderNumberPrefix());
						outletBean.setSupplieNumberPrefix(outlet.getContactNumberPrefix());
						outletBean.setSupplierReturnNumber(outlet.getContactReturnNumber());
						if(outlet.getIsHeadOffice() != null){
							outletBean.setIsHeadOffice(outlet.getIsHeadOffice().toString());
						}
						else{
							outletBean.setIsHeadOffice("false");
						}
						AddressBean  addressBean = new AddressBean();
						if(outlet.getAddress()!=null){
							Address address = addressService.getAddressByAddressId(outlet.getAddress().getAddressId(),currentUser.getCompany().getCompanyId());
							addressBean.setAddressId(address.getAddressId().toString());
							addressBean.setCity(address.getCity());
							addressBean.setPostalCode(address.getPostalCode());
							addressBean.setState(address.getState());
							addressBean.setEmail(address.getEmail());
							addressBean.setTwitter(address.getTwitter());
							addressBean.setStreet(address.getStreet());
							if(address.getCountry()!=null){
							Country country = countryService.getCountryByCountryId(address.getCountry().getCountryId());
							addressBean.setCountryId(country.getCountryId().toString());
							addressBean.setCountry(country.getCountryName());
							}
						}
						if(outlet.getTimeZone()!=null){
							TimeZone timeZone = timeZoneService.getTimeZoneByTimeZoneId(outlet.getTimeZone().getTimeZoneId());
							addressBean.setTimeZoneId(timeZone.getTimeZoneId().toString());
							addressBean.setTimeZone(timeZone.getTimeZoneValue());
						}
						outletBean.setAddressbean(addressBean);
						
						try {
							if(registerService.getRegestersByOutletId(outlet.getOutletId(),currentUser.getCompany().getCompanyId(),currentUser.getUserId())!=null){
								List<Register> regiserList = registerService.getRegestersByOutletId(outlet.getOutletId(),currentUser.getCompany().getCompanyId(),currentUser.getUserId());
								if(regiserList!=null && regiserList.size()>0){
									for(Register register:regiserList){
										RegisterBean regiseBean = new RegisterBean();
										regiseBean.setCacheManagementEnable(register.getCacheManagementEnable());
										regiseBean.setEmailReceipt(register.getEmailReceipt());
										regiseBean.setNotes(register.getNotes());
										regiseBean.setOutletId(outlet.getOutletId().toString());
										regiseBean.setOutletName(outlet.getOutletName());
										regiseBean.setPrintNotesOnReceipt(register.getPrintNotesOnReceipt());
										regiseBean.setPrintReceipt(register.getPrintReceipt());
										regiseBean.setReceiptNumber(register.getReceiptNumber());
										regiseBean.setReceiptPrefix(register.getReceiptPrefix());
										regiseBean.setReceiptSufix(register.getReceiptSufix());
										regiseBean.setRegisterId(register.getRegisterId().toString());
										regiseBean.setRegisterName(register.getRegisterName());
										regiseBean.setSelectNextUserForSale(register.getSelectNextUserForSale());
										regiseBean.setShowDiscountOnReceipt(register.getShowDiscountOnReceipt());
										registerBeanList.add(regiseBean);
										
									}
								}
								
								outletBean.setRegisterBeanList(registerBeanList);
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();logger.error(e.getMessage(),e);
						}

						outletBean.setStatus(String.valueOf(outlet.isActiveIndicator()));


						outletBeans.add(outletBean);
					}
					util.AuditTrail(request, currentUser, "OutletsController.getOutlets", 
							"User "+ currentUser.getUserEmail()+" retrived all outlets successfully ",false);
					return new Response(outletBeans,StatusConstants.SUCCESS,LayOutPageConstants.OUTLETS);
				}else{
					util.AuditTrail(request, currentUser, "OutletsController.getOutlets", "User "+ 
							currentUser.getUserEmail()+" Unable to retrived all outlets",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}
				
			}catch(Exception e){
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "OutletsController.getOutlets",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}
}

