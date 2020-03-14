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

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Address;
import com.dowhile.Configuration;
import com.dowhile.Contact;
import com.dowhile.Country;
import com.dowhile.Outlet;
import com.dowhile.Product;
import com.dowhile.ProductVariant;
import com.dowhile.Status;
import com.dowhile.StockOrder;
import com.dowhile.StockOrderDetail;
import com.dowhile.StockOrderType;
import com.dowhile.User;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.controller.bean.StockControlControllerBean;
import com.dowhile.frontend.mapping.bean.StockOrderBean;
import com.dowhile.service.AddressService;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.ContactService;
import com.dowhile.service.CountryService;
import com.dowhile.service.OutletService;
import com.dowhile.service.StatusService;
import com.dowhile.service.StockOrderDetailService;
import com.dowhile.service.StockOrderService;
import com.dowhile.service.StockOrderTypeService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * Zafar Shakeel
 */
@Controller
@RequestMapping("/stockControl")
public class StockControlController {
	@Resource
	private StockOrderService stockOrderService;

	@Resource
	private OutletService outletService;

	@Resource
	private StatusService statusService;

	@Resource
	private AddressService addressService;

	@Resource
	private StockOrderTypeService stockOrderTypeService;

	@Resource
	private CountryService countryService;
	
	@Resource
	private ContactService supplierService;
	@Resource
	private ServiceUtil util;
	@Resource
	private StockOrderDetailService stockOrderDetailService;
	@Resource
	private ConfigurationService configurationService;

	@RequestMapping("/layout")
	public String getStockControlControllerPartialPage(ModelMap modelMap) {
		return "stockControl/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllStockOrders/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getAllStockOrders(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<StockOrderBean> stockOrderBeansList = new ArrayList<>();
		List<StockOrder> stockOrderList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
			Map<Integer, Outlet> outletsMap = new HashMap<>();
			List<Outlet> outlets = outletService.getOutlets(currentUser.getCompany().getCompanyId());
			if(outlets!=null){
				for(Outlet outlet:outlets){
					outletsMap.put(outlet.getOutletId(), outlet);
				}
			}
			Map<Integer, StockOrderType> stockOrderTypesMap = new HashMap<>();
			List<StockOrderType> stockOrderTypes = stockOrderTypeService.getAllStockOrderType();
			if(stockOrderTypes!=null){
				for(StockOrderType stockOrderType:stockOrderTypes){
					stockOrderTypesMap.put(stockOrderType.getStockOrderTypeId(), stockOrderType);
				}
			}
			Map<Integer, Status> statusMap = new HashMap<>();
			List<Status> statuses = statusService.getAllStatus();
			if(statusMap!=null){
				for(Status status:statuses){
					statusMap.put(status.getStatusId(), status);
				}
			}
			Map<Integer, Contact> suppliersMap = new HashMap<>();
			List<Contact> suppliers = supplierService.getAllContacts(currentUser.getCompany().getCompanyId());
			if(suppliersMap!=null){
				for(Contact contact:suppliers){
					if(contact.getContactType()!=null && contact.getContactType().contains("SUPPLIER")){
						suppliersMap.put(contact.getContactId(), contact);
					}
				}
			}
			Map<Integer, Country> countryMap = new HashMap<>();
			List<Country> countrys = countryService.GetAllCountry();
			if(countryMap!=null){
				for(Country country:countrys){					
						countryMap.put(country.getCountryId(), country);
				}
			}
			Map<Integer, Address> addressMap = new HashMap<>();
			List<Address> addresses = addressService.getAllAddress(currentUser.getCompany().getCompanyId());
			if(addressMap!=null){
				for(Address address:addresses){					
						addressMap.put(address.getAddressId(), address);
				}
			}
			//Stock Order Map Region
			List<StockOrderDetail> stockOrderDetails = new ArrayList<>();
			Map<Integer, List<StockOrderDetail>> stockOrderDetailsMap = new HashMap<>();
			stockOrderDetails = stockOrderDetailService.getAllStockOrderDetails(currentUser.getCompany().getCompanyId());
			if(stockOrderDetails!=null){
				for(StockOrderDetail stockOrderDetail:stockOrderDetails){
					List<StockOrderDetail> addedstockOrderDetails = stockOrderDetailsMap.get(stockOrderDetail.getStockOrder().getStockOrderId());
					if(addedstockOrderDetails!=null){
						addedstockOrderDetails.add(stockOrderDetail);
						stockOrderDetailsMap.put(stockOrderDetail.getStockOrder().getStockOrderId(), addedstockOrderDetails);
					}else{
						addedstockOrderDetails = new ArrayList<>();
						addedstockOrderDetails.add(stockOrderDetail);
						stockOrderDetailsMap.put(stockOrderDetail.getStockOrder().getStockOrderId(), addedstockOrderDetails);
					}

				}
			}
			//End Region
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			try {
				Outlet userOutlet = outletsMap.get(currentUser.getOutlet().getOutletId());
				if(userOutlet.getIsHeadOffice()!=null && userOutlet.getIsHeadOffice() == true){
					stockOrderList = stockOrderService.GetAllStockOrder(currentUser.getCompany().getCompanyId());
				}
				else{
					if(configurationService.getConfigurationByPropertyNameByCompanyId("TRANSFER_STOCK_OUTLET_ACCESS",currentUser.getCompany().getCompanyId()) != null ){
						Configuration configuration = configurationService.getConfigurationByPropertyNameByCompanyId("TRANSFER_STOCK_OUTLET_ACCESS",currentUser.getCompany().getCompanyId());
						if(configuration.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
							stockOrderList = stockOrderService.getStockOrderByOutletId(userOutlet.getOutletId(), currentUser.getCompany().getCompanyId());
						}
						else{
							stockOrderList = stockOrderService.getStockOrderCompletedByOutletId(userOutlet.getOutletId(), currentUser.getCompany().getCompanyId());
						}
					}
					else{
						stockOrderList = stockOrderService.getStockOrderByOutletId(userOutlet.getOutletId(), currentUser.getCompany().getCompanyId());
					}
				}
				if (stockOrderList != null) {
					for (StockOrder stockOrder : stockOrderList) {
						StockOrderBean stockOrderBean = new StockOrderBean();					
						stockOrderBean.setAutofillReorder(Boolean.toString(stockOrder.isAutofillReorder()));
						if(stockOrder.getCreatedDate() != null){
							stockOrderBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(stockOrder.getCreatedDate()).toString());
						}
						if(stockOrder.getCreatedBy() != null){
							stockOrderBean.setCreatedBy(String.valueOf(stockOrder.getCreatedBy()));
						}
						if(stockOrder.getDiliveryDueDate() != null){
							stockOrderBean.setDiliveryDueDate(simpleDateFormat.format(stockOrder.getDiliveryDueDate()).toString());
						}
						stockOrderBean.setOrderNo(stockOrder.getOrderNo());
						if(stockOrder.getOrdrRecvDate() != null){
							stockOrderBean.setOrdrRecvDate(simpleDateFormat.format(stockOrder.getOrdrRecvDate()).toString());
						}
						Outlet outlet = outletsMap.get(stockOrder.getOutletByOutletAssocicationId().getOutletId());
						if(outlet != null){
							stockOrderBean.setOutlet(outlet.getOutletId().toString());
							stockOrderBean.setOutletName(outlet.getOutletName());
							if(outlet.getAddress() != null){
								Address address = addressMap.get(outlet.getAddress().getAddressId());					
								if(address.getStreet() != null){
									stockOrderBean.setOutletAddress(address.getStreet());
								}
								if(address.getCity()!= null){
									stockOrderBean.setOutletAddress(stockOrderBean.getOutletAddress() + " " + address.getCity());		
								}
								if(address.getPostalCode() != null){
									stockOrderBean.setOutletAddress(stockOrderBean.getOutletAddress() + " " + address.getPostalCode());
								}
								if(address.getState() != null){
									stockOrderBean.setOutletAddress(stockOrderBean.getOutletAddress() + " " + address.getState());
								}
								if(address.getCountry() != null){
									Country country = countryMap.get(address.getCountry().getCountryId());
									stockOrderBean.setOutletAddress(stockOrderBean.getOutletAddress() + " " + country.getCountryName());							
								}
							}
						}
						if(stockOrder.getOutletBySourceOutletAssocicationId() != null)
						{
							Outlet outletSource = outletsMap.get(stockOrder.getOutletBySourceOutletAssocicationId().getOutletId());
							if(outletSource != null){
								stockOrderBean.setSourceOutletId(outletSource.getOutletId().toString());
								stockOrderBean.setSourceOutletName(outletSource.getOutletName());
								if(outletSource.getAddress() != null){								
									Address address = addressMap.get(outletSource.getAddress().getAddressId());
									if(address.getStreet() != null){
										stockOrderBean.setSourceOutletAddress(address.getStreet());
									}
									if(address.getCity()!= null){
										stockOrderBean.setSourceOutletAddress(stockOrderBean.getSourceOutletAddress() + " " + address.getCity());		
									}
									if(address.getPostalCode() != null){
										stockOrderBean.setSourceOutletAddress(stockOrderBean.getSourceOutletAddress() + " " + address.getPostalCode());
									}
									if(address.getState() != null){
										stockOrderBean.setSourceOutletAddress(stockOrderBean.getSourceOutletAddress() + " " + address.getState());
									}
									if(address.getCountry() != null){
										Country country = countryMap.get(address.getCountry().getCountryId());
										stockOrderBean.setOutletAddress(stockOrderBean.getOutletAddress() + " " + country.getCountryName());							
									}
								}
							}
						}
						if(stockOrder.isRetailPriceBill()){
							stockOrderBean.setRetailPriceBill(String.valueOf(stockOrder.isRetailPriceBill()));
						}
						stockOrderBean.setRemarks(stockOrder.getRemarks());
						stockOrderBean.setReturnNo(stockOrder.getReturnNo());
						Status status = statusMap.get(stockOrder.getStatus().getStatusId());
						if(status != null){
							stockOrderBean.setStatusId(status.getStatusId().toString());
							stockOrderBean.setStatus(status.getStatusDesc());
						}
						if(stockOrder.getStockOrderDate() != null){
							stockOrderBean.setStockOrderDate(simpleDateFormat.format(stockOrder.getStockOrderDate()).toString());
						}
						StockOrderType stockOrderType = stockOrderTypesMap.get(stockOrder.getStockOrderType().getStockOrderTypeId());
						if(stockOrderType != null){
							stockOrderBean.setStockOrderTypeId(stockOrderType.getStockOrderTypeId().toString());
							stockOrderBean.setStockOrderTypeDesc(stockOrderType.getStockOrderTypeDesc().toString());
						}					
						stockOrderBean.setStockRefNo(stockOrder.getStockRefNo());
						if(stockOrderBean.getSourceOutletId() == null){
							Contact supplier = suppliersMap.get(stockOrder.getContactId());
							if(supplier != null){
								stockOrderBean.setSupplierId(supplier.getContactId().toString());
								stockOrderBean.setSupplierName(supplier.getContactName());
							}
						}
						else{
							stockOrderBean.setSupplierName(stockOrderBean.getSourceOutletName());
						}
						stockOrderBean.setSupplierInvoiceNo(stockOrder.getContactInvoiceNo());
						int stockOrderId = stockOrder.getStockOrderId();
						List<StockOrderDetail> stockOrderDetailList = stockOrderDetailsMap.get(stockOrderId);
						int itemCount = 0;
						Double totalCost = 0.0;
						if(stockOrderDetailList != null){
							for(StockOrderDetail stockOrderDetail : stockOrderDetailList){
								if(stockOrderDetail.getRecvProdQty()!= null){
									itemCount = itemCount + stockOrderDetail.getRecvProdQty();
								}
								else{
									itemCount = itemCount + stockOrderDetail.getOrderProdQty();
								}
								if(stockOrder.getStockOrderType().getStockOrderTypeId() == 1){ //Supplier Order
									if(stockOrderDetail.getRecvSupplyPrice() != null && stockOrderDetail.getRecvProdQty()!= null){
										totalCost = totalCost + (stockOrderDetail.getRecvSupplyPrice().doubleValue() * stockOrderDetail.getRecvProdQty());
									}
									else{
										totalCost = totalCost + (stockOrderDetail.getOrdrSupplyPrice().doubleValue() * stockOrderDetail.getOrderProdQty());
									}
								}
								else
								{
									if(stockOrder.isRetailPriceBill()){
										totalCost = totalCost + (stockOrderDetail.getRetailPrice().doubleValue() * stockOrderDetail.getOrderProdQty());
									}
									else{
										totalCost = totalCost + (stockOrderDetail.getOrdrSupplyPrice().doubleValue() * stockOrderDetail.getOrderProdQty());
									}
								}
							}
						}
						stockOrderBean.setStockOrderId(Integer.toString(stockOrderId));
						stockOrderBean.setItemCount(Integer.toString(itemCount));
						NumberFormat formatter = new DecimalFormat("###.##");  
						String total = formatter.format(totalCost);
						stockOrderBean.setTotalCost(total);
						stockOrderBeansList.add(stockOrderBean);
					}
					
					
					
					StockControlControllerBean stockControlControllerBean =  new StockControlControllerBean();
					stockControlControllerBean.setStockOrderBeansList(stockOrderBeansList);
					Configuration configurationStockTransferToSupplier = configurationMap.get("STOCK_TRANSFER_TO_SUPPLIER");
					if(configurationStockTransferToSupplier!=null && configurationStockTransferToSupplier.getPropertyValue().toString().equalsIgnoreCase(ControllersConstants.TRUE)){
						stockControlControllerBean.setStockTransferToSupplier(true);
					}else{
						stockControlControllerBean.setStockTransferToSupplier(false);
					}

					util.AuditTrail(request, currentUser, "StockControlController.getAllStockOrders", 
							"User "+ currentUser.getUserEmail()+" fetched all stock orders successfully ",false);
					return new Response(stockControlControllerBean,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "StockControlController.getAllStockOrders", 
							"User "+ currentUser.getUserEmail()+" unbale to retrived stock orders ",false);
					return new Response(MessageConstants.RECORD_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "StockControlController.getAllStockOrders",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
}


