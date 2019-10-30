package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
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

import com.dowhile.Brand;
import com.dowhile.Configuration;
import com.dowhile.User;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.BrandBean;
import com.dowhile.service.ResourceService;
import com.dowhile.service.TempSaleService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.DBUtil;
import com.dowhile.util.SessionValidator;

/**
 * imran latif
 */
@Controller
@RequestMapping("/backup")
public class BackupController {

	@Resource
	private ResourceService resourceService;
	@Resource
	private ServiceUtil util;
	@Resource
	private TempSaleService tempSaleService;
	
	@RequestMapping("/layout")
	public String getBackupControllerPartialPage(ModelMap modelMap) {
		return "/backup/layout";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/takeBakup/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response takeBakup(@PathVariable("sessionId") String sessionId,
			 HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
				Configuration bakcupPathConfiguration = configurationMap.get("BACKUP_PATH");
				if(bakcupPathConfiguration==null){
					util.AuditTrail(request, currentUser, "BackupController.takeBakup", 
							"User "+ currentUser.getUserEmail()+" unbale to find backup path ",false);
					return new Response(MessageConstants.PATH_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.BACKUP);
				}else{
					String backupPath = bakcupPathConfiguration.getPropertyValue().toString();
					DBUtil.Backupdbtosql(backupPath);
				}
				
				util.AuditTrail(request, currentUser, "BackupController.takeBakup", 
						"User "+ currentUser.getUserEmail()+" taked backup successfully ",false);
				return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.BACKUP);
			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "BackupController.takeBakup",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/reStoreBackup/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response reStoreBackup(@PathVariable("sessionId") String sessionId,
			 HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");

			try{
				Map<String ,Configuration> configurationMap = (Map<String, Configuration>) session.getAttribute("configurationMap");
				Configuration bakcupPathConfiguration = configurationMap.get("BACKUP_PATH");
				if(bakcupPathConfiguration==null){
					util.AuditTrail(request, currentUser, "BackupController.reStoreBackup", 
							"User "+ currentUser.getUserEmail()+" unbale to find restore backup path ",false);
					return new Response(MessageConstants.PATH_NOT_FOUND,StatusConstants.RECORD_NOT_FOUND,LayOutPageConstants.BACKUP);
				}else{
					String backupPath = bakcupPathConfiguration.getPropertyValue().toString();
					DBUtil.dbRestore(backupPath);
				}
				
				util.AuditTrail(request, currentUser, "BackupController.reStoreBackup", 
						"User "+ currentUser.getUserEmail()+" restored backup successfully ",false);
				return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.BACKUP);
			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "BackupController.reStoreBackup",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/runScheduler/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response runScheduler(@PathVariable("sessionId") String sessionId) {

		if(!sessionId.equalsIgnoreCase("")){
			

			try{
				System.out.println("Running schedule");
				tempSaleService.runDailyScript();
				System.out.println("schedule run succesfully");
				return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.BACKUP);
			}catch(Exception e){
				e.printStackTrace();
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.ADD_RESTRICTED,LayOutPageConstants.STAY_ON_PAGE);
			}
		}else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	
}

