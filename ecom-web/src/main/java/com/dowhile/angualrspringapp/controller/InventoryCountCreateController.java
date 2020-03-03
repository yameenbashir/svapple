package com.dowhile.angualrspringapp.controller; 

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Configuration;
import com.dowhile.Contact;
import com.dowhile.Outlet;
import com.dowhile.InventoryCount;
import com.dowhile.InventoryCountType;
import com.dowhile.StockOrder;
import com.dowhile.User;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.InventoryCountControllerBean;
import com.dowhile.controller.bean.InventoryCountControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.InventoryCountTypeBean;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.InventoryCountBean;
import com.dowhile.frontend.mapping.bean.InventoryCountTypeBean;
import com.dowhile.frontend.mapping.bean.SupplierBean;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.ContactService;
import com.dowhile.service.OutletService;
import com.dowhile.service.StatusService;
import com.dowhile.service.InventoryCountService;
import com.dowhile.service.InventoryCountTypeService;
import com.dowhile.service.StockOrderService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * Zafar Shakeel
 */
@Controller
@RequestMapping("/inventoryCountCreate")
public class InventoryCountCreateController {
	@Resource
	private ServiceUtil util;

	@Resource
	private StockOrderService stockOrderService;

	@Resource
	private OutletService outletService;

	@Resource
	private ContactService supplierService;

	@Resource
	private InventoryCountService inventoryCountService;

	@Resource
	private StatusService statusService;

	@Resource
	private InventoryCountTypeService inventoryCountTypeService;

	@Resource
	private ConfigurationService configurationService;

	@RequestMapping("/layout")
	public String getInventoryCountCreateControllerPartialPage(ModelMap modelMap) {
		return "inventoryCount/layout";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getInventoryCountCreateControllerData/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getInventoryCountCreateControllerData(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<OutletBean> outletBeansList = null;
		List<InventoryCountTypeBean> inventoryCountTypeBeansList= null;

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
			try {

				Response response = getAllOutlets(sessionId,request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					outletBeansList = (List<OutletBean>) response.data;
				}
				response = getAllInventoryCountTypes(sessionId, request);
				if(response.status.equals(StatusConstants.SUCCESS)){
					inventoryCountTypeBeansList = (List<InventoryCountTypeBean>) response.data;
				}				
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
				InventoryCountControllerBean inventoryCountControllerBean = new InventoryCountControllerBean();
				inventoryCountControllerBean.setOutletBeansList(outletBeansList);
				inventoryCountControllerBean.setInventoryCountTypeBeansList(inventoryCountTypeBeansList);
				inventoryCountControllerBean.setAuditTransfer(autoTransfer);
				util.AuditTrail(request, currentUser, "InventoryCountCreateController.getInventoryCountControllerData", 
						"User "+ currentUser.getUserEmail()+" retrived InventoryCountCreateController successfully ",false);
				return new Response(inventoryCountControllerBean, StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);
			} catch (Exception e) {
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountCreateController.getInventoryCountControllerData",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,
						StatusConstants.RECORD_NOT_FOUND,
						LayOutPageConstants.STAY_ON_PAGE);

			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllOutlets/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getAllOutlets(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<OutletBean> outletBeansList = new ArrayList<>();
		List<Outlet> outletList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {

				outletList = outletService.getOutlets(currentUser.getCompany().getCompanyId());
				if (outletList != null) {
					for (Outlet outlet : outletList) {

						OutletBean outletBean = new OutletBean();
						outletBean.setOutletId(outlet.getOutletId().toString());
						outletBean.setOutletName(outlet.getOutletName().toString());
						if(outlet.getIsHeadOffice()!= null){
							outletBean.setIsHeadOffice(outlet.getIsHeadOffice().toString());
						}
						outletBeansList.add(outletBean);
					}

					util.AuditTrail(request, currentUser, "InventoryCountController.getAllOutlets", 
							"User "+ currentUser.getUserEmail()+" fetched all outlets successfully ",false);
					return new Response(outletBeansList,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "InventoryCountController.getAllOutlets", 
							"User "+ currentUser.getUserEmail()+" unbale to retrived all outlets ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.getAllOutlets",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getAllInventoryCountTypes/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getAllInventoryCountTypes(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {

		List<InventoryCountTypeBean> inventoryCountTypeBeansList = new ArrayList<>();
		List<InventoryCountType> inventoryCountTypeList = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try {
				inventoryCountTypeList = inventoryCountTypeService.getAllInventoryCountType();
				if (inventoryCountTypeList != null) {
					for (InventoryCountType inventoryCountType : inventoryCountTypeList) {

						InventoryCountTypeBean inventoryCountTypeBean = new InventoryCountTypeBean();
						inventoryCountTypeBean.setInventoryCountTypeId(inventoryCountType.getInventoryCountTypeId().toString());
						inventoryCountTypeBean.setInventoryCountTypeCode(inventoryCountType.getInventoryCountTypeCode());
						inventoryCountTypeBean.setInventoryCountTypeDesc(inventoryCountType.getInventoryCountTypeDesc());
						inventoryCountTypeBeansList.add(inventoryCountTypeBean);
					}

					util.AuditTrail(request, currentUser, "InventoryCountController.getAllInventoryCountTypes", 
							"User "+ currentUser.getUserEmail()+" fetched all stock order types successfully ",false);
					return new Response(inventoryCountTypeBeansList,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "InventoryCountController.getAllInventoryCountTypes", 
							"User "+ currentUser.getUserEmail()+" unbale to retrived all stock order types ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.getAllInventoryCountTypes",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getHeadOffice/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response getHeadOffice(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {
		SupplierBean supplierBean = null;
		Contact supplier = null;
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");		
			try {
				Outlet outlet = outletService.getHeadOfficeOutlet(currentUser.getCompany().getCompanyId());
				if(outlet != null){
					supplier = supplierService.getContactByContactOutletID(outlet.getOutletId(), currentUser.getCompany().getCompanyId());
					if (supplier != null) {
						supplierBean = new SupplierBean();
						supplierBean.setSupplierId(supplier.getContactId().toString());
						supplierBean.setSupplierName(supplier.getContactName().toString());
						if(supplier.getContactOutletId() != null){
							supplierBean.setOutletId(supplier.getContactOutletId().toString());
						}
						util.AuditTrail(request, currentUser, "InventoryCountController.getAllSuppliers", 
								"User "+ currentUser.getUserEmail()+" fetched all suppliers successfully ",false);
					}
					return new Response(supplierBean,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
				}else{
					util.AuditTrail(request, currentUser, "InventoryCountController.getAllSuppliers", 
							"User "+ currentUser.getUserEmail()+" unbale to retrived all suppliers ",false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}
			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.getAllSuppliers",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/addInventoryCount/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response addInventoryCount(@PathVariable("sessionId") String sessionId,
			@RequestBody InventoryCountBean inventoryCountBean, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			String stockDetails = "<p> Please Close/Complete following Stock Orders before iniating an Audit";
			List<StockOrder> stockOrderList = null;
			try {			
				DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				stockOrderList = stockOrderService.getStockOrderByOutletIdNotComp(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());
				for(StockOrder stockOrder:stockOrderList){
					String status = "";
					if(stockOrder.getStatus() != null){
						if(stockOrder.getStatus().getStatusId() == 1){
							status = "Initiated";
						}
						else if (stockOrder.getStatus().getStatusId() == 2){
							status = "In progress";
						}
					}
					stockDetails = stockDetails + "<br>" + stockOrder.getStockRefNo() + " - " + status + " - Last Updated =" + dateFormat.format(stockOrder.getLastUpdated());
				}
				stockDetails = stockDetails + "</p>";
				if(stockOrderList.size() < 1){
					if (inventoryCountBean != null) {				
						InventoryCount inventoryCount = new InventoryCount();
						inventoryCount.setActiveIndicator(true);
						inventoryCount.setCreatedBy(currentUser.getUserId());
						inventoryCount.setCreatedDate(new Date());
						inventoryCount.setLastUpdated(new Date());
						inventoryCount.setStatus(statusService.getStatusByStatusId(Integer.parseInt(inventoryCountBean.getStatusId().trim()))); 				
						inventoryCount.setInventoryCountType(inventoryCountTypeService.getInventoryCountTypeByInventoryCountTypeId(Integer.parseInt(inventoryCountBean.getInventoryCountTypeId()))); 
						inventoryCount.setInventoryCountRefNo(inventoryCountBean.getInventoryCountRefNo());
						inventoryCount.setOutlet(outletService.getOuletByOutletId(Integer.parseInt(inventoryCountBean.getOutletId()),currentUser.getCompany().getCompanyId()));					
						inventoryCount.setUpdatedBy(currentUser.getUserId());
						inventoryCount.setCompany(currentUser.getCompany());
						if(inventoryCountBean.getRemarks() != null){
							inventoryCount.setRemarks(inventoryCountBean.getRemarks());
						}	
						System.out.println(inventoryCount.getOutlet().getOutletId());
						System.out.println(inventoryCount.getStatus().getStatusId());
						inventoryCountService.addInventoryCount(inventoryCount,currentUser.getCompany().getCompanyId());
						String path = LayOutPageConstants.INVENTORY_COUNT_DETAILS;
						util.AuditTrail(request, currentUser, "InventoryCountController.addInventoryCount", 
								"User "+ currentUser.getUserEmail()+" Added InventoryCount+"+inventoryCountBean.getInventoryCountId()+" successfully ",false);
						return new Response(inventoryCount.getInventoryCountId(),StatusConstants.SUCCESS,path);
					}else{
						util.AuditTrail(request, currentUser, "InventoryCountController.addInventoryCount", "User "+ 
								currentUser.getUserEmail()+" Unable to add InventoryCount : "+inventoryCountBean.getInventoryCountRefNo(),false);
						return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
					}
				}			
				else{
					util.AuditTrail(request, currentUser, "InventoryCountController.addInventoryCount", "User "+ 
							currentUser.getUserEmail()+" Unable to add InventoryCount : "+inventoryCountBean.getInventoryCountId(),false);
					return new Response(stockDetails,StatusConstants.WARNING,LayOutPageConstants.STAY_ON_PAGE);
				}
			}
			catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.addInventoryCount",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/updateInventoryCount/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody Response updateInventoryCount(@PathVariable("sessionId") String sessionId,
			@RequestBody InventoryCountBean inventoryCountBean, HttpServletRequest request){
		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");	
			try {			
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				if (inventoryCountBean != null) {				
					InventoryCount inventoryCount = inventoryCountService.getInventoryCountByInventoryCountID(Integer.parseInt(inventoryCountBean.getInventoryCountId()),currentUser.getCompany().getCompanyId());
					inventoryCount.setActiveIndicator(true);
					inventoryCount.setCreatedBy(currentUser.getUserId());
					inventoryCount.setCreatedDate(new Date());
					inventoryCount.setLastUpdated(new Date());
					inventoryCount.setStatus(statusService.getStatusByStatusId(Integer.parseInt(inventoryCountBean.getStatusId().trim()))); 				
					inventoryCount.setInventoryCountType(inventoryCountTypeService.getInventoryCountTypeByInventoryCountTypeId(Integer.parseInt(inventoryCountBean.getInventoryCountTypeId()))); 
					inventoryCount.setInventoryCountRefNo(inventoryCountBean.getInventoryCountRefNo());
					inventoryCount.setOutlet(outletService.getOuletByOutletId(Integer.parseInt(inventoryCountBean.getOutletId()),currentUser.getCompany().getCompanyId()));					
					inventoryCount.setUpdatedBy(currentUser.getUserId());
					inventoryCount.setCompany(currentUser.getCompany());
					if(inventoryCountBean.getRemarks() != null){
						inventoryCount.setRemarks(inventoryCountBean.getRemarks());
					}
					inventoryCountService.updateInventoryCount(inventoryCount,currentUser.getCompany().getCompanyId());

					util.AuditTrail(request, currentUser, "InventoryCountController.updateInventoryCount", 
							"User "+ currentUser.getUserEmail()+" Added InventoryCount+"+inventoryCountBean.getInventoryCountId()+" successfully ",false);
					String path = LayOutPageConstants.INVENTORY_COUNT_EDIT_DETAILS;
					util.AuditTrail(request, currentUser, "InventoryCountController.addInventoryCount", "User "+ 
							currentUser.getUserEmail()+" added InventoryCount : "+inventoryCountBean.getInventoryCountId(),false);
					return new Response(inventoryCount.getInventoryCountId(),StatusConstants.SUCCESS,path);
				}else{
					util.AuditTrail(request, currentUser, "InventoryCountController.addInventoryCount", "User "+ 
							currentUser.getUserEmail()+" Unable to add InventoryCount : "+inventoryCountBean.getInventoryCountId(),false);
					return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
				}

			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "InventoryCountController.addInventoryCount",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}		
	}

}

