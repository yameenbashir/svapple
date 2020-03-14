package com.dowhile.angualrspringapp.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Outlet;
import com.dowhile.User;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.Organization;
import com.dowhile.frontend.mapping.bean.OrganizationGraph;
import com.dowhile.service.CompanyService;
import com.dowhile.service.OutletService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

@Controller
@RequestMapping("/orgHierarchy")

public class OrgHierarchyController {

	@Resource
	private CompanyService companyService;
	@Resource
	private ServiceUtil util;
	@Resource
	private ResourceService resourceService;
	@Resource
	private OutletService outletService;


	@RequestMapping("/layout")
	public String getOrgHierarchyControllerPartialPage(ModelMap modelMap) {
		return "orgHierarchy/layout";
	}



	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/changeHierarchyLevel/{sessionId}/{outletId}", method = RequestMethod.POST)
	public @ResponseBody
	Response changeHierarchyLevel(@PathVariable("sessionId") String sessionId,
			@PathVariable("outletId") String outletId,HttpServletRequest request) {

		if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			User currentUser = (User) session.getAttribute("user");
			System.out.println("Old Company id: "+outletId);
			Outlet outlet =  outletService.getOuletByOutletId(Integer.parseInt(outletId), currentUser.getCompany().getCompanyId());
			currentUser.setOutlet(outlet);
			System.out.println("New Company id: "+currentUser.getCompany().getCompanyId());
			session.setAttribute("user", currentUser);
			
			return new Response(MessageConstants.REQUREST_PROCESSED,StatusConstants.SUCCESS,LayOutPageConstants.ORG_HIERARCHY);
		}

		else
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);

	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/getCompanies/{sessionId}", method = RequestMethod.POST)
	public @ResponseBody
	Response getCompanies(@PathVariable("sessionId") String sessionId, HttpServletRequest request) {

	if(SessionValidator.isSessionValid(sessionId, request)){
			HttpSession session =  request.getSession(false);
			
			User currentUser = (User) session.getAttribute("user");
			try {
				List<Outlet> outlets = outletService.getOutlets(currentUser.getCompany().getCompanyId());
				OrganizationGraph organizationGraph =  new OrganizationGraph();
				organizationGraph.setName(makeTitle(currentUser.getCompany().getCompanyName(),String.valueOf(currentUser.getCompany().getCompanyId()),sessionId,"top-level"));
				organizationGraph.setTitle(currentUser.getCompany().getCompanyName());
				organizationGraph.setClassName("top-level");
				organizationGraph.setChildren(makeOrganizationList(outlets, sessionId));
				util.AuditTrail(request, currentUser, "OrgHierarchyController.getCompanies",
						"Companies found for user  " +currentUser.getFirstName(),false);
				return new Response(organizationGraph,StatusConstants.SUCCESS,LayOutPageConstants.STAY_ON_PAGE);
			}
			catch (Exception e) {
				e.printStackTrace();// logger.error(e.getMessage(),e);
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				util.AuditTrail(request, currentUser, "OrgHierarchyController.getCompanies",
						"Error Occured " + errors.toString(),true);
				return new Response(MessageConstants.SYSTEM_BUSY,StatusConstants.BUSY,LayOutPageConstants.LOGIN);
			}
		}
		else{
			return new Response(MessageConstants.INVALID_SESSION,StatusConstants.INVALID,LayOutPageConstants.LOGIN);
		}
	}

	List<Organization> makeOrganizationList(List<Outlet> outlets,String sessionId){
		List<Organization> organizationList = new ArrayList<>();
		try{
			for(Outlet outlet : outlets) {
				Organization organization = new Organization();

				organization.setName(makeTitle(outlet.getOutletName(),String.valueOf(outlet.getOutletId()),sessionId,"middle-level"));
				organization.setTitle(outlet.getOutletName());
				organization.setClassName("middle-level");
				organization.setId(String.valueOf(outlet.getOutletId()));
				organizationList.add(organization);
			}
		}catch(Exception ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return organizationList;
	}
	

	
	public String makeTitle(String companyName,String companyId,String sessionId,String companyLevel){
		
		return "<a style=\"color: white;\" id="+"company"+companyId+" href=\"\" ng-click=\"updateCompanyId("+companyId+",'"+companyLevel+"')\">"+companyName+"</a>";
	}

}