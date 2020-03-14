package com.dowhile.angualrspringapp.controller; 

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.dowhile.Configuration;
import com.dowhile.Country;
import com.dowhile.InventoryCount;
import com.dowhile.InventoryCountDetail;
import com.dowhile.InventoryCountType;
import com.dowhile.Outlet;
import com.dowhile.Status;
import com.dowhile.User;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.InventoryCountBean;
import com.dowhile.service.AddressService;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.ContactService;
import com.dowhile.service.CountryService;
import com.dowhile.service.InventoryCountDetailService;
import com.dowhile.service.InventoryCountService;
import com.dowhile.service.InventoryCountTypeService;
import com.dowhile.service.OutletService;
import com.dowhile.service.StatusService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * Zafar Shakeel
 */
@Controller
@RequestMapping("/inventoryCount")
public class InventoryCountController {

	// private static Logger logger = Logger.getLogger(InventoryCountController.class.getName());
	@Resource
	private OutletService outletService;
	@Resource
	private StatusService statusService;
	@Resource
	private AddressService addressService;
	@Resource
	private InventoryCountTypeService inventoryCountTypeService;	
	@Resource
	private InventoryCountService inventoryCountService;	
	@Resource
	private CountryService countryService;	
	@Resource
	private ContactService supplierService;
	@Resource
	private ServiceUtil util;
	@Resource
	private InventoryCountDetailService inventoryCountDetailService;
	@Resource
	private ConfigurationService configurationService;

	@RequestMapping("/layout")
	public String getInventoryCountControllerPartialPage(ModelMap modelMap) {
		return "inventoryCount/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllInventoryCounts/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getAllInventoryCounts(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<InventoryCountBean> inventoryCountBeansList = new ArrayList<>();
		List<InventoryCount> inventoryCountList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Map<Integer, Outlet> outletsMap = new HashMap<>();
			List<Outlet> outlets = outletService.getOutlets(currentUser.getCompany().getCompanyId());
			if(outlets!=null){
				for(Outlet outlet:outlets){
					outletsMap.put(outlet.getOutletId(), outlet);
				}
			}
			Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
			Map<Integer, InventoryCountType> inventoryCountTypesMap = new HashMap<>();
			List<InventoryCountType> inventoryCountTypes = inventoryCountTypeService.getAllInventoryCountType();
			if(inventoryCountTypes!=null){
				for(InventoryCountType inventoryCountType:inventoryCountTypes){
					inventoryCountTypesMap.put(inventoryCountType.getInventoryCountTypeId(), inventoryCountType);
				}
			}
			Map<Integer, Status> statusMap = new HashMap<>();
			List<Status> statuses = statusService.getAllStatus();
			if(statusMap!=null){
				for(Status status:statuses){
					statusMap.put(status.getStatusId(), status);
				}
			}
			/*Map<Integer, Contact> suppliersMap = new HashMap<>();
			List<Contact> suppliers = supplierService.getAllContacts(currentUser.getCompany().getCompanyId());
			if(suppliersMap!=null){
				for(Contact contact:suppliers){
					if(contact.getContactType()!=null && contact.getContactType().contains("SUPPLIER")){
						suppliersMap.put(contact.getContactId(), contact);
					}
				}
			} */
			Map<Integer, Country> countryMap = new HashMap<>();
			List<Country> countrys = countryService.GetAllCountry();
			if(countryMap!=null){
				for(Country country:countrys){					
					countryMap.put(country.getCountryId(), country);
				}
			}
			/*Map<Integer, Address> addressMap = new HashMap<>();
			List<Address> addresses = addressService.getAllAddress(currentUser.getCompany().getCompanyId());
			if(addressMap!=null){
				for(Address address:addresses){					
					addressMap.put(address.getAddressId(), address);
				}
			}*/
			Configuration configuration = configurationMap.get("AUTO_AUDIT_TRANSFER");
			boolean autoTransfer = false;
			if(configuration != null ){
				if(configuration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
					autoTransfer = true;
				}
				else{
					autoTransfer = false;
				}
			}
			else{
				autoTransfer = false;
			}
			try {
				Outlet userOutlet = outletsMap.get(currentUser.getOutlet().getOutletId());
				if(userOutlet.getIsHeadOffice()!=null && userOutlet.getIsHeadOffice() == true){
					inventoryCountList = inventoryCountService.GetAllInventoryCount(currentUser.getCompany().getCompanyId());
				}
				else{					
					inventoryCountList = inventoryCountService.getInventoryCountByOutletId(userOutlet.getOutletId(), currentUser.getCompany().getCompanyId());
				}
				if (inventoryCountList != null) {
					for (InventoryCount inventoryCount : inventoryCountList) {
						InventoryCountBean inventoryCountBean = new InventoryCountBean();
						inventoryCountBean.setAuditTransfer(String.valueOf(autoTransfer));
						int inventoryCountId = 0;
						if(inventoryCount.getCreatedDate() != null){
							inventoryCountBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(inventoryCount.getCreatedDate()));
						}
						if(inventoryCount.getLastUpdated() != null){
							inventoryCountBean.setLastUpdated(DateTimeUtil.convertDBDateTimeToGuiFormat(inventoryCount.getLastUpdated()));
						}
						if(inventoryCount.getCreatedBy() != null){
							inventoryCountBean.setCreatedBy(String.valueOf(inventoryCount.getCreatedBy()));
						}
						if(inventoryCount.getInventoryCountRefNo() != null){
							inventoryCountBean.setInventoryCountRefNo(inventoryCount.getInventoryCountRefNo());
						}
						if(inventoryCount.getInventoryCountId() != null){
							inventoryCountId = inventoryCount.getInventoryCountId();
							inventoryCountBean.setInventoryCountId(String.valueOf(inventoryCountId));
						}
						Outlet outlet = outletsMap.get(inventoryCount.getOutlet().getOutletId());
						if(outlet != null){
							inventoryCountBean.setOutletId(outlet.getOutletId().toString());
							inventoryCountBean.setOutletName(outlet.getOutletName());
							/*if(outlet.getAddress() != null){
								Address address = addressMap.get(outlet.getAddress().getAddressId());					
								if(address.getStreet() != null){
									inventoryCountBean.setOutletAddress(address.getStreet());
								}
								if(address.getCity()!= null){
									inventoryCountBean.setOutletAddress(inventoryCountBean.getOutletAddress() + " " + address.getCity());		
								}
								if(address.getPostalCode() != null){
									inventoryCountBean.setOutletAddress(inventoryCountBean.getOutletAddress() + " " + address.getPostalCode());
								}
								if(address.getState() != null){
									inventoryCountBean.setOutletAddress(inventoryCountBean.getOutletAddress() + " " + address.getState());
								}
								if(address.getCountry() != null){
									Country country = countryMap.get(address.getCountry().getCountryId());
									inventoryCountBean.setOutletAddress(inventoryCountBean.getOutletAddress() + " " + country.getCountryName());							
								}
							}*/
						}
						if(inventoryCount.getRemarks() != null){
							inventoryCountBean.setRemarks(inventoryCount.getRemarks());
						}
						Status status = null;
						if(inventoryCount.getStatus() != null){
							status = new Status();
							status = statusMap.get(inventoryCount.getStatus().getStatusId());
						}
						if(status != null){
							inventoryCountBean.setStatusId(status.getStatusId().toString());
							inventoryCountBean.setStatus(status.getStatusDesc());
						}
						InventoryCountType inventoryCountType = inventoryCountTypesMap.get(inventoryCount.getInventoryCountType().getInventoryCountTypeId());
						if(inventoryCountType != null){
							inventoryCountBean.setInventoryCountTypeId(inventoryCountType.getInventoryCountTypeId().toString());
							inventoryCountBean.setInventoryCountTypeDesc(inventoryCountType.getInventoryCountTypeDesc().toString());
						}						
						NumberFormat formatter = new DecimalFormat("###.##");  
						if(inventoryCount.getSupplyPriceExp() != null){
							inventoryCountBean.setSupplyPriceExp(formatter.format(inventoryCount.getSupplyPriceExp()));
						}
						if(inventoryCount.getRetailPriceExp() != null){
							inventoryCountBean.setRetailPriceExp(formatter.format(inventoryCount.getRetailPriceExp()));
						}
						if(inventoryCount.getSupplyPriceCounted() != null){
							inventoryCountBean.setSupplyPriceCounted(formatter.format(inventoryCount.getSupplyPriceCounted()));
						}
						if(inventoryCount.getRetailPriceCounted() != null){
							inventoryCountBean.setRetailPriceCounted(formatter.format(inventoryCount.getRetailPriceCounted()));
						}
						if(inventoryCount.getCountDiff() != null){
							inventoryCountBean.setCountDiff(String.valueOf(inventoryCount.getCountDiff()));
						}
						if(inventoryCount.getPriceDiff() != null){
							inventoryCountBean.setPriceDiff(formatter.format(inventoryCount.getPriceDiff()));
						}
						if(inventoryCount.getExpectedProdQty() != null){
							inventoryCountBean.setItemCountExp(String.valueOf(inventoryCount.getExpectedProdQty()));
						}
						if(inventoryCount.getCountedProdQty() != null){
							inventoryCountBean.setItemCountCounted(String.valueOf(inventoryCount.getCountedProdQty()));
						}
						inventoryCountBeansList.add(inventoryCountBean);
					}

					util.AuditTrail(request, currentUser, "InventoryCountController.getAllInventoryCounts", 
							"User "+ currentUser.getUserEmail()+" fetched all Inventory Counts successfully ",false);
					return new Response(inventoryCountBeansList,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "InventoryCountController.getAllInventoryCounts", 
							"User "+ currentUser.getUserEmail()+" unbale to retrive Inventory Counts ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.getAllInventoryCounts",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
}


