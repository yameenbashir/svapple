package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.dowhile.CashManagment;
import com.dowhile.DailyRegister;
import com.dowhile.Outlet;
import com.dowhile.Receipt;
import com.dowhile.Register;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.RegisterCloseControllerBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.CashManagmenBean;
import com.dowhile.frontend.mapping.bean.DailyRegisterBean;
import com.dowhile.service.CashManagmentService;
import com.dowhile.service.DailyRegisterService;
import com.dowhile.service.OutletService;
import com.dowhile.service.ReceiptService;
import com.dowhile.service.RegisterService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.SaleService;
import com.dowhile.service.StatusService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DateTimeUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/registerClose")
public class RegisterCloseController {

	@Resource
	private SaleService saleService;

	@Resource
	private ServiceUtil util;

	@Resource
	private DailyRegisterService dailyRegisterService;

	@Resource
	private CashManagmentService cashManagmentService;

	@Resource
	private ReceiptService receiptService;

	@Resource
	private OutletService outletService;

	@Resource
	private RegisterService registerService;

	@Resource
	private ResourceService resourceService;

	@Resource
	private StatusService statusService;

	@RequestMapping("/layout")
	public String getRegisterCloseControllerPartialPage(ModelMap modelMap) {
		return "registerClose/layout";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/loadRegister/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response loadRegister(@PathVariable("sessionId") String sessionId,
			HttpServletRequest request) {
		RegisterCloseControllerBean controllerBean = new RegisterCloseControllerBean();
		DailyRegister dailyRegister = null;

		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			DailyRegisterBean dailyRegisterBean = new DailyRegisterBean();
			List<CashManagmenBean> cashManagmenBeans = new ArrayList<CashManagmenBean>();
			try {
				dailyRegister= dailyRegisterService.getOpenDailyRegister(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId());
				double cashAmtExpected = 0;
				double cashAmtReceived = 0;
				double creditAmtExpected = 0; 
				controllerBean.setOpenDayilyRegisterExist(true);
				if(dailyRegister==null){
					dailyRegister= dailyRegisterService.getLastColsedDailyRegister(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId());
					controllerBean.setLastDayilyRegisterExist(true);
					controllerBean.setOpenDayilyRegisterExist(false);

				}
				if(dailyRegister!=null){
					Register register = registerService.getRegisterByRegisterId(dailyRegister.getRegister().getRegisterId() , currentUser.getCompany().getCompanyId());
					Outlet outlet = outletService.getOuletByOutletId(register.getOutlet().getOutletId(),currentUser.getCompany().getCompanyId());
					controllerBean.setOutletName(outlet.getOutletName());

					/*List<Receipt> receipts = receiptService.getAllReceiptsByDailyRegister(dailyRegister.getDailyRegisterId(), currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());

						if(receipts!=null){
							for(Receipt receipt:receipts){
								if(receipt.getPaymentType().getPaymentTypeId()==1){
									cashAmtExpected = cashAmtExpected + receipt.getReceiptAmount().doubleValue();
								}else{
									creditAmtExpected = creditAmtExpected + receipt.getReceiptAmount().doubleValue();
								}

							}
						}
						dailyRegisterBean.setPaymentReceived(String.valueOf(cashAmtExpected));*/

					cashAmtReceived = saleService.getTodaysRevenue(currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());

					dailyRegisterBean.setPaymentReceived(String.valueOf(cashAmtReceived));

					List<CashManagment> cashManagments = cashManagmentService.getCashManagmentDailyRegister(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId(), dailyRegister.getDailyRegisterId());
					cashAmtExpected = cashAmtReceived;
					if(cashManagments!=null){
						for(CashManagment managment: cashManagments){
							CashManagmenBean managmentControllerBean = new CashManagmenBean();
							if(managment.getCashManagmentType().equalsIgnoreCase("IN")){
								managmentControllerBean.setCashAmount(managment.getCashAmt().toString());
								cashAmtExpected = cashAmtExpected + managment.getCashAmt().doubleValue();
							}else{
								managmentControllerBean.setCashAmount(managment.getCashAmt().toString());
								cashAmtExpected = cashAmtExpected + managment.getCashAmt().doubleValue();
							}

							managmentControllerBean.setTime(DateTimeUtil.convertDBDateTimeToGuiFormat(managment.getCreatedDate()));
							managmentControllerBean.setCreatedBy(resourceService.getUserById(managment.getCreatedBy(),currentUser.getCompany().getCompanyId()).getFirstName());
							managmentControllerBean.setNotes(managment.getCashManagmentNotes());
							cashManagmenBeans.add(managmentControllerBean);
						}

					}
					if(dailyRegister.getCashAmtActual()!=null){
						dailyRegisterBean.setCashAmtActual(dailyRegister.getCashAmtActual().toString());
					}
					if(dailyRegister.getCreditCardAmtActual()!=null){
						dailyRegisterBean.setCreditCardAmtActual(dailyRegister.getCreditCardAmtActual().toString());
					}
					dailyRegisterBean.setCashAmtExpected(String.valueOf(cashAmtExpected));
					dailyRegisterBean.setCreditCardAmtExpected(String.valueOf(creditAmtExpected));
					dailyRegisterBean.setStoreCreditAmtExpected("0");
					dailyRegisterBean.setRegisterName(register.getRegisterName());
					dailyRegisterBean.setOutletName(outlet.getOutletName());
					dailyRegisterBean.setRegisterId(register.getRegisterId().toString());
					dailyRegisterBean.setDailyRegisterId(dailyRegister.getDailyRegisterId().toString());
					dailyRegisterBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(dailyRegister.getCreatedDate()));
					controllerBean.setDailyRegisterBean(dailyRegisterBean);
					controllerBean.setCashManagmenBeans(cashManagmenBeans);
					util.AuditTrail(request, currentUser, "RegisterCloseController.loadRegister",
							"User " + currentUser.getUserEmail() + " Load Daily Register Id "
									+ dailyRegister.getDailyRegisterId()
									+ " successfully ", false);

					return new Response(controllerBean,
							StatusConstants.SUCCESS, LayOutPageConstants.SELL);
				}
				return new Response(MessageConstants.RECORD_NOT_FOUND,
						StatusConstants.ERROR, LayOutPageConstants.HOME);

			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "RegisterCloseController.loadRegister",
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
	@RequestMapping(value = "/closeRegister/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response closeRegister(@PathVariable("sessionId") String sessionId, @RequestBody DailyRegisterBean dailyRegisterBean,
			HttpServletRequest request) {
		DailyRegister dailyRegister = null;

		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			try {
				dailyRegister = dailyRegisterService.getDailyRegisterById(Integer.parseInt(dailyRegisterBean.getDailyRegisterId()),currentUser.getCompany().getCompanyId());
				dailyRegister.setCashAmtActual(new BigDecimal(dailyRegisterBean.getCashAmtActual()));
				if(dailyRegisterBean.getCashAmtActual()!=null && !dailyRegisterBean.getCashAmtActual().equals("")){
					dailyRegister.setCashAmtActual(new BigDecimal(dailyRegisterBean.getCashAmtActual()));

				}
				if(dailyRegisterBean.getCreditCardAmtActual()!=null && !dailyRegisterBean.getCreditCardAmtActual().equals("")){
					dailyRegister.setCreditCardAmtActual(new BigDecimal(dailyRegisterBean.getCreditCardAmtActual()));

				}

				dailyRegister.setStatus(statusService.getStatusByStatusId(8));
				dailyRegister.setRegisterClosingNotes(dailyRegisterBean.getRegisterClosingNotes());
				dailyRegister.setClosedDate(new Date());
				dailyRegister.setUpdatedBy(currentUser.getUserId());
				dailyRegisterService.updateDailyRegister(dailyRegister,currentUser.getCompany().getCompanyId());
				util.AuditTrail(request, currentUser, "RegisterCloseController.closeRegister",
						"Daily Regester id " + dailyRegisterBean.getDailyRegisterId(), true);
				return new Response(MessageConstants.REQUREST_PROCESSED,
						StatusConstants.SUCCESS,
						LayOutPageConstants.STAY_ON_PAGE);


			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "RegisterCloseController.closeRegister",
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
	@RequestMapping(value = "/loadRegisterDetail/{sessionId}/{registerId}", method = RequestMethod.POST)
	public @ResponseBody
	Response loadRegisterDetail(@PathVariable("sessionId") String sessionId,@PathVariable("registerId") String registerId,
			HttpServletRequest request) {
		RegisterCloseControllerBean controllerBean = new RegisterCloseControllerBean();
		DailyRegister dailyRegister = null;

		if (SessionValidator.isSessionValid(sessionId, request)) {
			HttpSession session = request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			DailyRegisterBean dailyRegisterBean = new DailyRegisterBean();
			List<CashManagmenBean> cashManagmenBeans = new ArrayList<CashManagmenBean>();
			try {
				dailyRegister= dailyRegisterService.getDailyRegisterById(Integer.valueOf(registerId),currentUser.getCompany().getCompanyId());
				double cashAmtExpected = 0; 
				double creditAmtExpected = 0; 
				controllerBean.setOpenDayilyRegisterExist(true);
				if(dailyRegister==null){
					dailyRegister= dailyRegisterService.getLastColsedDailyRegister(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId());
					controllerBean.setLastDayilyRegisterExist(true);
					controllerBean.setOpenDayilyRegisterExist(false);

				}
				if(dailyRegister!=null){
					Register register = registerService.getRegisterByRegisterId(dailyRegister.getRegister().getRegisterId() , currentUser.getCompany().getCompanyId());
					Outlet outlet = outletService.getOuletByOutletId(register.getOutlet().getOutletId(),currentUser.getCompany().getCompanyId());
					controllerBean.setOutletName(outlet.getOutletName());
					List<Receipt> receipts = receiptService.getAllReceiptsByDailyRegister(dailyRegister.getDailyRegisterId(), currentUser.getOutlet().getOutletId(), currentUser.getCompany().getCompanyId());

					if(receipts!=null){
						for(Receipt receipt:receipts){
							if(receipt.getPaymentType().getPaymentTypeId()==1){
								cashAmtExpected = cashAmtExpected + receipt.getReceiptAmount().doubleValue();
							}else{
								creditAmtExpected = creditAmtExpected + receipt.getReceiptAmount().doubleValue();
							}

						}
					}
					dailyRegisterBean.setPaymentReceived(String.valueOf(cashAmtExpected));

					List<CashManagment> cashManagments = cashManagmentService.getCashManagmentDailyRegister(currentUser.getCompany().getCompanyId(), currentUser.getOutlet().getOutletId(), dailyRegister.getDailyRegisterId());
					if(cashManagments!=null){
						for(CashManagment managment: cashManagments){
							CashManagmenBean managmentControllerBean = new CashManagmenBean();
							if(managment.getCashManagmentType().equalsIgnoreCase("IN")){
								managmentControllerBean.setCashAmount(managment.getCashAmt().toString());
								cashAmtExpected = cashAmtExpected + managment.getCashAmt().doubleValue();
							}else{
								managmentControllerBean.setCashAmount(managment.getCashAmt().toString());
								cashAmtExpected = cashAmtExpected + managment.getCashAmt().doubleValue();
							}

							managmentControllerBean.setTime(DateTimeUtil.convertDBDateTimeToGuiFormat(managment.getCreatedDate()));
							managmentControllerBean.setCreatedBy(resourceService.getUserById(managment.getCreatedBy(),currentUser.getCompany().getCompanyId()).getFirstName());
							managmentControllerBean.setNotes(managment.getCashManagmentNotes());
							cashManagmenBeans.add(managmentControllerBean);
						}

					}
					if(dailyRegister.getCashAmtActual()!=null){
						dailyRegisterBean.setCashAmtActual(dailyRegister.getCashAmtActual().toString());
					}
					if(dailyRegister.getCreditCardAmtActual()!=null){
						dailyRegisterBean.setCreditCardAmtActual(dailyRegister.getCreditCardAmtActual().toString());
					}
					dailyRegisterBean.setCashAmtExpected(String.valueOf(cashAmtExpected));
					dailyRegisterBean.setCreditCardAmtExpected(String.valueOf(creditAmtExpected));
					dailyRegisterBean.setStoreCreditAmtExpected("0");
					dailyRegisterBean.setRegisterName(register.getRegisterName());
					dailyRegisterBean.setOutletName(outlet.getOutletName());
					dailyRegisterBean.setRegisterId(register.getRegisterId().toString());
					dailyRegisterBean.setDailyRegisterId(dailyRegister.getDailyRegisterId().toString());
					dailyRegisterBean.setCreatedDate(DateTimeUtil.convertDBDateTimeToGuiFormat(dailyRegister.getCreatedDate()));
					dailyRegisterBean.setRegisterClosingNotes(dailyRegister.getRegisterClosingNotes());
					dailyRegisterBean.setRegisterOpeningNotes(dailyRegister.getRegisterOpeningNotes());
					controllerBean.setDailyRegisterBean(dailyRegisterBean);
					controllerBean.setCashManagmenBeans(cashManagmenBeans);
					util.AuditTrail(request, currentUser, "RegisterCloseController.loadRegisterDetail",
							"User " + currentUser.getUserEmail() + " Loaded Daily Register detail by Register Id "
									+ dailyRegister.getDailyRegisterId()
									+ " successfully ", false);

					return new Response(controllerBean,
							StatusConstants.SUCCESS, LayOutPageConstants.SELL);
				}
				return new Response(MessageConstants.RECORD_NOT_FOUND,
						StatusConstants.ERROR, LayOutPageConstants.HOME);

			} catch (Exception e) {
				e.printStackTrace();logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "RegisterCloseController.loadRegisterDetail",
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

