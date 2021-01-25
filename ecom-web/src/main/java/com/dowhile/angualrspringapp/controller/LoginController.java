package com.dowhile.angualrspringapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dowhile.Brand;
import com.dowhile.Company;
import com.dowhile.Configuration;
import com.dowhile.Contact;
import com.dowhile.ContactGroup;
import com.dowhile.Menu;
import com.dowhile.Notification;
import com.dowhile.Outlet;
import com.dowhile.Product;
import com.dowhile.ProductType;
import com.dowhile.ProductVariant;
import com.dowhile.SalesTax;
import com.dowhile.User;
import com.dowhile.UserOutlets;
import com.dowhile.VariantAttribute;
import com.dowhile.VariantAttributeValues;
import com.dowhile.constant.Actions;
import com.dowhile.constants.ControllersConstants;
import com.dowhile.constants.LayOutPageConstants;
import com.dowhile.constants.MessageConstants;
import com.dowhile.constants.StatusConstants;
import com.dowhile.controller.bean.LoginBean;
import com.dowhile.controller.bean.Response;
import com.dowhile.frontend.mapping.bean.UserBean;
import com.dowhile.service.BrandService;
import com.dowhile.service.CompanyService;
import com.dowhile.service.ConfigurationService;
import com.dowhile.service.ContactGroupService;
import com.dowhile.service.ContactService;
import com.dowhile.service.MenuService;
import com.dowhile.service.NotificationService;
import com.dowhile.service.OutletService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductTypeService;
import com.dowhile.service.ProductVariantService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.SalesTaxService;
import com.dowhile.service.TempSaleService;
import com.dowhile.service.UserOutletsService;
import com.dowhile.service.VariantAttributeService;
import com.dowhile.service.VariantAttributeValuesService;
import com.dowhile.service.util.ServiceUtil;
import com.dowhile.util.SessionValidator;

/**
 * @author Yameen Bashir
 * 
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	// private static Logger logger = Logger.getLogger(LoginController.class.getName());
	@Resource
	private ResourceService resourceService;
	@Resource
	private ServiceUtil util;
	@Resource
	private MenuService menuService;
	@Resource
	private CompanyService companyService;
	@Resource
	private UserOutletsService userOutletsService;
	@Resource
	private OutletService outletService;
	@Resource
	private ContactService supplerserService;
	@Resource
	private ProductTypeService productTypeService;
	@Resource
	private ProductService productService;
	@Resource
	private ProductVariantService productVariantService;
	@Resource
	private SalesTaxService salesTaxService;
	@Resource
	private BrandService brandService;
	@Resource
	private VariantAttributeService variantAttributeService;
	@Resource
	private ContactGroupService contactGroupService;
	@Resource
	private VariantAttributeValuesService variantAttributeValuesService;
	@Resource
	private ConfigurationService configurationService;
	@Resource
	private ContactService supplierService;
	@Resource
	private NotificationService notificationService;
	@Resource
	private TempSaleService tempSaleService;
	@Autowired
	BackupController backupController= new BackupController();
	
	@RequestMapping("/layout")
	public String getLoginControllerPartialPage(ModelMap modelMap) {
		return "login/layout";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public @ResponseBody Response doLogin(@RequestBody UserBean appuser,
			HttpServletRequest request) {
		
	//	StringUtils.encode(str);
		
		//if(code.base64decode == currentdate+companyName){pass}
			
//		tempSaleService.runDailyScript();
//		 backupController.runScheduler("session");
		HttpSession session = request.getSession(true);
		boolean isExist = false;
		User user = null;
		if (appuser.getEmail() == null
				|| appuser.getEmail().trim().length() == 0)
			return new Response(MessageConstants.INVALID_USER,
					StatusConstants.ERROR, LayOutPageConstants.STAY_ON_PAGE);
		else if (appuser.getPassword() == null
				|| appuser.getPassword().trim().length() == 0)
			return new Response(MessageConstants.INVALID_PASSWORD,
					StatusConstants.ERROR, LayOutPageConstants.STAY_ON_PAGE);
		try {
			
			isExist = resourceService.IsUserExist(appuser.getEmail(),
					appuser.getPassword(),0);
			if (isExist) {
				user = resourceService.getUserByEmail(appuser.getEmail(),0);
				user.setLastLogin(new Date());
				resourceService.updateUserLoginTime(user,0);
				user.setPassword("hide");
				Company company = companyService.getCompanyDetailsByCompanyID(user.getCompany().getCompanyId());
				user.setCompany(company);
				UserOutlets outlets = userOutletsService.getUserDefaultOutlet(user.getUserId(), company.getCompanyId());
				Outlet outlet = outletService.getOuletByOutletId(outlets.getOutlet().getOutletId(), company.getCompanyId());
				
				if(!outlet.isActiveIndicator()){
					System.out.println("Outlet status is : "+outlet.isActiveIndicator());
					return new Response(MessageConstants.OUTLET_CLOSED,
							StatusConstants.OUTLET_CLOSED,
							LayOutPageConstants.STAY_ON_PAGE);
				}
					
				user.setOutlet(outlet);
				session.setAttribute("user", user);
				session.setAttribute("impersonate", false);
				Map<String ,Configuration> configurationMap = configurationService.getAllConfigurationsByCompanyId(company.getCompanyId());
				session.setAttribute("configurationMap", configurationMap);
				//For Local Installation
				/*Configuration configruationForLocal = configurationMap.get("COMPANY_RECEIPT_IMAGE");
				if(configruationForLocal!=null){
					String companyImagePath = configruationForLocal.getPropertyValue();
					String encodeString = StringUtils.encode(companyImagePath);
					System.out.println("Encode: "+encodeString);
					String decode = StringUtils.decode(encodeString);
					System.out.println("Decode: "+decode);
					
					
				}*/
				
				
				LoginBean loginBean = new LoginBean();
				loginBean.setUserName(user.getFirstName() + " "
						+ user.getLastName());
				loginBean.setSessionId(session.getId());
				loginBean.setUserRole(String.valueOf(user.getRole().getRoleId()));
				loginBean.setOutletId(String.valueOf(outlet.getOutletId()));
				if(outlet.getIsHeadOffice() != null && String.valueOf(outlet.getIsHeadOffice()) != ""){
					loginBean.setIsHeadOffice(String.valueOf(outlet.getIsHeadOffice()));
				}
				else{
					loginBean.setIsHeadOffice(String.valueOf(false));
				}
				loginBean.setCompnayName(outlet.getOutletName());
				loginBean.setUserId(user.getUserId().toString());
				List<Menu> menuList = menuService.getMenuByRoleId(user.getRole().getRoleId(),company.getCompanyId());
				loginBean.setMapMenu(populateMenuBeanList(menuList));
				loginBean.setUserMap(populateUserBeanList(resourceService.getAllEmployeesByCompanyId(company.getCompanyId())));
				Configuration configurationCompanyLevelImageReceipt = configurationMap.get("COMPANY_LEVEL_IMAGE_RECEIPT");
				if(configurationCompanyLevelImageReceipt!=null && configurationCompanyLevelImageReceipt.getPropertyValue().equalsIgnoreCase(ControllersConstants.TRUE)) {
					Configuration configuration = configurationMap.get("COMPANY_RECEIPT_IMAGE_"+outlet.getOutletId()+"");
					if(configuration!=null){
						String companyImagePath = "/app/resources/images/"+configuration.getPropertyValue();
						loginBean.setCompanyImagePath(companyImagePath.trim());
					} 
					Configuration configurationTermsAndContitions = configurationMap.get("TERMS_AND_CONDITIONS_"+outlet.getOutletId()+"");
					if(configurationTermsAndContitions!=null){
						String termsAndContitions = configurationTermsAndContitions.getPropertyValue();
						loginBean.setTermsAndConditions(termsAndContitions);
					}
				}else {
					Configuration configuration = configurationMap.get("COMPANY_RECEIPT_IMAGE");
					if(configuration!=null){
						String companyImagePath = "/app/resources/images/"+configuration.getPropertyValue();
						loginBean.setCompanyImagePath(companyImagePath.trim());
					} 
					Configuration configurationTermsAndContitions = configurationMap.get("TERMS_AND_CONDITIONS");
					if(configurationTermsAndContitions!=null){
						String termsAndContitions = configurationTermsAndContitions.getPropertyValue();
						loginBean.setTermsAndConditions(termsAndContitions);
					}
				}
				
				
				Configuration domianConfiguration = configurationMap.get("SUB_DOMAIN_NAME");
				if(domianConfiguration!=null){
					String subDomianName = domianConfiguration.getPropertyValue();
					session.setAttribute("subDomianName", subDomianName);
				}
				//List<Notification> notificationList= notificationService.getAllUnReadNotificationsByOutletIdCompanyId(outlet.getOutletId(),company.getCompanyId());
				try{
					if (user.getRole().getRoleId()==1 && user.getOutlet().getIsHeadOffice()!=null && user.getOutlet().getIsHeadOffice().toString()=="true") {
						List<Notification> notificationList = notificationService.getAllUnReadedNotificationsByCompanyId(user.getCompany().getCompanyId());
						int count = 0;
						if(notificationList!=null && notificationList.size()>0){
							count = notificationList.size();
							loginBean.setCountNotifications(count);}
					}else if(user.getRole().getRoleId()!=1 ||user.getOutlet().getIsHeadOffice()==null || user.getOutlet().getIsHeadOffice().toString()!="true"){
						List<Notification> notificationList1= notificationService.getAllUnReadNotificationsByOutletIdCompanyId(outlet.getOutletId(),company.getCompanyId());
						int count = 0;
						if(notificationList1!=null && notificationList1.size()>0){
							count = notificationList1.size();
							loginBean.setCountNotifications(count);
						}
							}
					
				}catch(Exception e){
					e.printStackTrace();// logger.error(e.getMessage(),e);
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
				}
				
				//SynchProductMartInData(user);
//				SynchProductDataLumenFashions(user);
				if(domianConfiguration==null){
					System.out.println("domain configuration is null ");
					util.AuditTrail(request, user, "LoginController.doLogin", "domain configuration is null for user : "+
							loginBean.getUserName()+" against companyId : "+company.getCompanyId()+
							" with company Name: "+company.getCompanyName(),false);
					return new Response(MessageConstants.AUTHORIZATION_FAILED,
							StatusConstants.USER_INVALID,
							LayOutPageConstants.STAY_ON_PAGE);
				}
				System.out.println("Authorizing user for domain: "+domianConfiguration.getPropertyValue());
				if(!SessionValidator.isSessionValid(session.getId(), request)){
					System.out.println("Authorization failde for user : "+loginBean.getUserName()+" against companyId : "+company.getCompanyId()+" with company Name: "+company.getCompanyName());
					System.out.println("User associated with outlet Id: "+outlet.getOutletId()+" having outlet name: "+outlet.getOutletName());
					util.AuditTrail(request, user, "LoginController.doLogin", "Authorization failde for user : "+
					loginBean.getUserName()+" against companyId : "+company.getCompanyId()+
					" with company Name: "+company.getCompanyName(),false);
					return new Response(MessageConstants.AUTHORIZATION_FAILED,
							StatusConstants.USER_INVALID,
							LayOutPageConstants.STAY_ON_PAGE);
					
				}
				System.out.println("Authorization completed for user: "+appuser.getEmail()+" against companyId: "+company.getCompanyId()+" with company Name: "+company.getCompanyName());
				System.out.println("User associated with outlet Id: "+outlet.getOutletId()+" having outlet name: "+outlet.getOutletName());
				util.AuditTrail(request, user, "LoginController.doLogin", "User:  "+ appuser.getEmail()+" Login Successfuly ",false);
				if(user.getRole().getRoleId()==1){
					return new Response(loginBean, StatusConstants.SUCCESS,
							LayOutPageConstants.HOME);
				}else{
					return new Response(loginBean, StatusConstants.SUCCESS,
							LayOutPageConstants.SELL);
				}

			} else
				//				util.AuditTrail(request, user, "Login", "User "+ appuser.getEmail()+" Invalid! ");

				return new Response(MessageConstants.CREDENTIALS_MISMATCHED,
						StatusConstants.USER_INVALID,
						LayOutPageConstants.STAY_ON_PAGE);
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error(e.getMessage(),e);
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			util.AuditTrail(request, user, "LoginController.doLogin",
					"Error Occured " + errors.toString(),true);

			return new Response(MessageConstants.SYSTEM_BUSY,
					StatusConstants.BUSY, LayOutPageConstants.STAY_ON_PAGE);
		}
	}

	public Map<String ,Boolean> populateMenuBeanList(List<Menu> menuList){

		Map<String ,Boolean> mapMenu = new HashMap<>();

		for(Menu menu:menuList){
			mapMenu.put(menu.getMenuName(), menu.isActiveIndicator());
		}

		return mapMenu;
	}
	public Map<String ,String> populateUserBeanList(List<User> userList){

		Map<String ,String> mapUser = new HashMap<>();

		for(User user:userList){
			byte[]   bytesEncoded = Base64.encodeBase64(user.getPassword() .getBytes());
			mapUser.put(user.getUserEmail(), new String(bytesEncoded ));
		}

		return mapUser;
	}
	//INSERT INTO `ecom`.`brand` (`BRAND_NAME`, `BRAND_DESCRIPTION`, `ACTIVE_INDICATOR`, `CREATED_DATE`, `LAST_UPDATED`, `CREATED_BY`, `UPDATED_BY`, `COMPANY_ASSOCIATION_ID`) VALUES ('Kites', 'Kites', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1', '1', '1');
	//INSERT INTO `ecom`.`variant_attribute` (`ATTRIBUTE_NAME`, `ACTIVE_INDICATOR`, `CREATED_DATE`, `LAST_UPDATED`, `CREATED_BY`, `UPDATED_BY`, `VARIANT_ATTRIBUTE_ASSOCIATION_ID`, `COMPANY_ASSOCIATION_ID`) VALUES ( 'Color', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1', '1', '0', '1');
	//INSERT INTO `ecom`.`variant_attribute` (`ATTRIBUTE_NAME`, `ACTIVE_INDICATOR`, `CREATED_DATE`, `LAST_UPDATED`, `CREATED_BY`, `UPDATED_BY`, `VARIANT_ATTRIBUTE_ASSOCIATION_ID`, `COMPANY_ASSOCIATION_ID`) VALUES ( 'Size', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, '1', '1', '0', '1');

	//private static final String FILE_NAME = "/var/lib/openshift/58d95a9689f5cf06c50001ca/app-root/data/Products_Kites_2017.xlsx";
	 private static final String FILE_NAME = "C:\\mart.xlsx";//C:\
	 private static final String FILE_NAME_NEW_MART = "C:\\newmart.xlsx";//C:\

	public void SynchSupplierData(User user) {

		try {

			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(1);
			Iterator<Row> iterator = datatypeSheet.iterator();
			ContactGroup contactGroup = contactGroupService.getContactGroupByContactGroupId(1, user.getCompany().getCompanyId());
			while (iterator.hasNext()) {

				Row currentRow = iterator.next();

				String suuplierName = currentRow.getCell(1).getStringCellValue();

				Contact contact = new Contact();
				contact.setContactName(suuplierName);
				contact.setContactBalance(BigDecimal.ZERO);
				contact.setActiveIndicator(true);
				contact.setContactName(suuplierName);
				contact.setCreatedDate(new Date());
				contact.setCreatedBy(user.getUserId());
				contact.setUpdatedBy(user.getUserId());
				contact.setCompany(user.getCompany());
				contact.setContactGroup(contactGroup);
				contact.setLastUpdated(new Date());
				supplerserService.addContact(contact, user.getCompany().getCompanyId());

			}
			SynchProductTypeData(user);

		} catch (FileNotFoundException e) {
			e.printStackTrace();// logger.error(e.getMessage(),e);
		} catch (IOException e) {
			e.printStackTrace();// logger.error(e.getMessage(),e);
		}

	}
	public void SynchProductTypeData(User user) {

		try {

			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(2);
			Iterator<Row> iterator = datatypeSheet.iterator();
			while (iterator.hasNext()) {

				Row currentRow = iterator.next();

				ProductType productType = new ProductType();
				String productTypeName = currentRow.getCell(1).getStringCellValue();

				productType.setProductTypeName(productTypeName);
				productType.setActiveIndicator(true);
				productType.setCreatedDate(new Date());
				productType.setCreatedBy(user.getUserId());
				productType.setUpdatedBy(user.getUserId());
				productType.setCompany(user.getCompany());
				productType.setLastUpdated(new Date());
				productTypeService.addProductType(productType, user.getCompany().getCompanyId());
			}
			SynchProductData(user);

		} catch (FileNotFoundException e) {
			e.printStackTrace();// logger.error(e.getMessage(),e);
		} catch (IOException e) {
			e.printStackTrace();// logger.error(e.getMessage(),e);
		}

	}
	@SuppressWarnings("deprecation")
	public void SynchProductData(User user) {

		try {

			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			SalesTax salesTax = salesTaxService.getSalesTaxBySalesTaxId(1, user.getCompany().getCompanyId());
			Brand brand = brandService.getBrandByBrandId(1, user.getCompany().getCompanyId());
			VariantAttribute variantAttributeByVariantAttributeAssocicationId1 = variantAttributeService.getVariantAttributeByVariantAttributeId(1, user.getCompany().getCompanyId());
			VariantAttribute variantAttributeByVariantAttributeAssocicationId2 = variantAttributeService.getVariantAttributeByVariantAttributeId(2, user.getCompany().getCompanyId());
			int rowNum = 0;
			while (iterator.hasNext()) {
				rowNum = rowNum+1;
				System.out.println(rowNum);

				Row currentRow = iterator.next();
				String productName = "";
				if (currentRow.getCell(0).getCellTypeEnum() == CellType.STRING) {
					productName = currentRow.getCell(0).getStringCellValue();
				} else if (currentRow.getCell(0).getCellTypeEnum() == CellType.NUMERIC) {
					productName = String.valueOf(currentRow.getCell(0).getNumericCellValue());
				}

				//	                String lineItemName = currentRow.getCell(1).getStringCellValue();
				int product_code = (int) currentRow.getCell(2).getNumericCellValue();
				//	                String product_code = currentRow.getCell(2).getStringCellValue();
				//	                String Product_number = currentRow.getCell(4).getStringCellValue();
				int supplier_id = (int) currentRow.getCell(5).getNumericCellValue();
				//	                String supplier_name = currentRow.getCell(6).getStringCellValue();
				String size = currentRow.getCell(7).getStringCellValue().replaceAll("temp_", "");
				String Color = currentRow.getCell(8).getStringCellValue();
				String bar_code = currentRow.getCell(9).getStringCellValue();
				String retail_price = String.valueOf(currentRow.getCell(10).getNumericCellValue());
				int count = (int) currentRow.getCell(11).getNumericCellValue();
				Product product =  productService.getProductByProductName(productName, user.getCompany().getCompanyId());
				if(product ==null){
					Product newProduct = new Product();
					newProduct.setProductName(productName);
					newProduct.setSku(bar_code);
					ProductType productType = productTypeService.getProductTypeByProductTypeId(product_code, user.getCompany().getCompanyId());
					newProduct.setProductType(productType);
					newProduct.setProductUuid(productName);
					newProduct.setProductHandler(productName);
					Contact contact = supplerserService.getContactByID(supplier_id,user.getCompany().getCompanyId());
					newProduct.setContact(contact);
					newProduct.setBrand(brand);
					newProduct.setProductCanBeSold("true");
					newProduct.setStandardProduct("true");
					newProduct.setTrackingProduct("true");
					newProduct.setVariantProducts("true");
					newProduct.setCurrentInventory(0);
					newProduct.setReorderPoint(0);
					newProduct.setReorderAmount(BigDecimal.ZERO);
					newProduct.setSupplyPriceExclTax(new BigDecimal(retail_price));
					newProduct.setMarkupPrct(BigDecimal.ZERO);
					newProduct.setOutlet(user.getOutlet());
					newProduct.setSalesTax(salesTax);
					newProduct.setActiveIndicator(true);
					newProduct.setCreatedDate(new Date());
					newProduct.setUserByCreatedBy(user);
					newProduct.setUserByUpdatedBy(user);
					newProduct.setCompany(user.getCompany());
					newProduct.setLastUpdated(new Date());
					newProduct.setImagePath("");
					product = productService.addProduct(newProduct, Actions.CREATE, 0, user.getCompany());

					ProductVariant productVariant = new ProductVariant();
					productVariant.setProduct(product);
					productVariant.setProductVariantUuid(UUID.randomUUID().toString());
					productVariant.setVariantAttributeName(Color+"/"+size);
					productVariant.setVariantAttributeValue1(Color);
					productVariant.setVariantAttributeValue2(size);

					productVariant.setVariantAttributeByVariantAttributeAssocicationId1(variantAttributeByVariantAttributeAssocicationId1);
					productVariant.setVariantAttributeByVariantAttributeAssocicationId2(variantAttributeByVariantAttributeAssocicationId2);
					productVariant.setCurrentInventory(count);
					productVariant.setReorderPoint(0);
					productVariant.setReorderAmount(BigDecimal.ZERO);
					productVariant.setProductUuid(product.getProductUuid());
					productVariant.setSupplyPriceExclTax(new BigDecimal(retail_price));
					productVariant.setMarkupPrct(BigDecimal.ZERO);
					productVariant.setSku(bar_code);
					productVariant.setOutlet(user.getOutlet());
					productVariant.setSalesTax(salesTax);
					productVariant.setActiveIndicator(true);
					productVariant.setCreatedDate(new Date());
					productVariant.setLastUpdated(new Date());
					productVariant.setUserByCreatedBy(user);
					productVariant.setUserByUpdatedBy(user);
					productVariant.setCompany(user.getCompany());
					productVariant = productVariantService.addProductVariant(productVariant, Actions.CREATE, 0,  user.getCompany(), String.valueOf(product.getProductId()));


					VariantAttributeValues variantAttributeValues = new VariantAttributeValues();
					variantAttributeValues.setActiveIndicator(true);
					variantAttributeValues.setAttributeValue(Color);
					variantAttributeValues.setCompany(user.getCompany());
					variantAttributeValues.setCreatedDate(new Date());
					variantAttributeValues.setLastUpdated(new Date());
					variantAttributeValues.setProduct(product);
					variantAttributeValues.setProductVariant(productVariant);
					variantAttributeValues.setUserByCreatedBy(user);
					variantAttributeValues.setUserByUpdatedBy(user);
					variantAttributeValues.setProductUuid(productName);
					variantAttributeValues.setVariantAttribute(variantAttributeByVariantAttributeAssocicationId1);
					variantAttributeValuesService.addVariantAttributeValues(variantAttributeValues, user.getCompany().getCompanyId());

					VariantAttributeValues variantAttributeValues2 = new VariantAttributeValues();
					variantAttributeValues2.setActiveIndicator(true);
					variantAttributeValues2.setAttributeValue(size);
					variantAttributeValues2.setCompany(user.getCompany());
					variantAttributeValues2.setCreatedDate(new Date());
					variantAttributeValues2.setLastUpdated(new Date());
					variantAttributeValues2.setProduct(product);
					variantAttributeValues2.setProductVariant(productVariant);
					variantAttributeValues2.setUserByCreatedBy(user);
					variantAttributeValues2.setUserByUpdatedBy(user);
					variantAttributeValues2.setProductUuid(productName);
					variantAttributeValues2.setVariantAttribute(variantAttributeByVariantAttributeAssocicationId2);
					variantAttributeValuesService.addVariantAttributeValues(variantAttributeValues2, user.getCompany().getCompanyId());
				}else{
					ProductVariant productVariant = new ProductVariant();
					productVariant.setProduct(product);
					productVariant.setProductVariantUuid(UUID.randomUUID().toString());
					productVariant.setVariantAttributeName(Color+"/"+size);
					productVariant.setVariantAttributeValue1(Color);
					productVariant.setVariantAttributeValue2(size);

					productVariant.setVariantAttributeByVariantAttributeAssocicationId1(variantAttributeByVariantAttributeAssocicationId1);
					productVariant.setVariantAttributeByVariantAttributeAssocicationId2(variantAttributeByVariantAttributeAssocicationId2);
					productVariant.setCurrentInventory(count);
					productVariant.setReorderPoint(0);
					productVariant.setReorderAmount(BigDecimal.ZERO);
					productVariant.setProductUuid(product.getProductUuid());
					productVariant.setSupplyPriceExclTax(new BigDecimal(retail_price));
					productVariant.setMarkupPrct(BigDecimal.ZERO);
					productVariant.setSku(bar_code);
					productVariant.setOutlet(user.getOutlet());
					productVariant.setSalesTax(salesTax);
					productVariant.setActiveIndicator(true);
					productVariant.setCreatedDate(new Date());
					productVariant.setLastUpdated(new Date());
					productVariant.setUserByCreatedBy(user);
					productVariant.setUserByUpdatedBy(user);
					productVariant.setCompany(user.getCompany());
					productVariant = productVariantService.addProductVariant(productVariant, Actions.CREATE, 0,  user.getCompany(), String.valueOf(product.getProductId()));


					VariantAttributeValues variantAttributeValues = new VariantAttributeValues();
					variantAttributeValues.setActiveIndicator(true);
					variantAttributeValues.setAttributeValue(Color);
					variantAttributeValues.setCompany(user.getCompany());
					variantAttributeValues.setCreatedDate(new Date());
					variantAttributeValues.setLastUpdated(new Date());
					variantAttributeValues.setProduct(product);
					variantAttributeValues.setProductVariant(productVariant);
					variantAttributeValues.setUserByCreatedBy(user);
					variantAttributeValues.setUserByUpdatedBy(user);
					variantAttributeValues.setProductUuid(productName);
					variantAttributeValues.setVariantAttribute(variantAttributeByVariantAttributeAssocicationId1);
					variantAttributeValuesService.addVariantAttributeValues(variantAttributeValues, user.getCompany().getCompanyId());

					VariantAttributeValues variantAttributeValues2 = new VariantAttributeValues();
					variantAttributeValues2.setActiveIndicator(true);
					variantAttributeValues2.setAttributeValue(size);
					variantAttributeValues2.setProductUuid(productName);
					variantAttributeValues2.setCompany(user.getCompany());
					variantAttributeValues2.setCreatedDate(new Date());
					variantAttributeValues2.setLastUpdated(new Date());
					variantAttributeValues2.setProduct(product);
					variantAttributeValues2.setProductVariant(productVariant);
					variantAttributeValues2.setUserByCreatedBy(user);
					variantAttributeValues2.setUserByUpdatedBy(user);
					variantAttributeValues2.setVariantAttribute(variantAttributeByVariantAttributeAssocicationId2);
					variantAttributeValuesService.addVariantAttributeValues(variantAttributeValues2, user.getCompany().getCompanyId());
				}




				//     System.out.println(retail_price);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();// logger.error(e.getMessage(),e);
		} catch (IOException e) {
			e.printStackTrace();// logger.error(e.getMessage(),e);
		}

	}
	
	@SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
	public  void SynchProductMartInData(User user) {

		try {

			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME_NEW_MART));
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			SalesTax salesTax = salesTaxService.getSalesTaxBySalesTaxId(1, user.getCompany().getCompanyId());
			Brand brand = brandService.getBrandByBrandId(1, user.getCompany().getCompanyId());
			VariantAttribute variantAttributeByVariantAttributeAssocicationId1 = variantAttributeService.getVariantAttributeByVariantAttributeId(1, user.getCompany().getCompanyId());
			ProductType productType = new ProductType();
			productType.setProductTypeId(1);
			Contact contact = new Contact();
			contact.setContactId(2);
			int count = 0;
			int variantDefalutInventory = 10;
			String colour = ".";
			Map productMap = new HashMap<>();
			//VariantAttribute variantAttributeByVariantAttributeAssocicationId2 = variantAttributeService.getVariantAttributeByVariantAttributeId(2, user.getCompany().getCompanyId());
			int rowNum = 0;
			while (iterator.hasNext()) {
				rowNum = rowNum+1;
				System.out.println(rowNum);

				Row currentRow = iterator.next();
				String sku = currentRow.getCell(0).getStringCellValue();
				String productName = currentRow.getCell(1).getStringCellValue();
				String retailPrice = currentRow.getCell(2).getStringCellValue();
				String supplierPrice = currentRow.getCell(3).getStringCellValue();
				double retailPr = Double.parseDouble(retailPrice);
				double supplierPr = Double.parseDouble(supplierPrice);
				
				double markUp = (retailPr-supplierPr)*100/supplierPr;
				DecimalFormat numberFormat = new DecimalFormat("#.00");
				//System.out.println("Markup: "+numberFormat.format(markUp));
				BigDecimal markUpPrct = new BigDecimal(numberFormat.format(markUp)).setScale(2, RoundingMode.HALF_EVEN);
				System.out.println("Big Decimal markUpPrct: "+markUpPrct);
				//System.out.println("Row Number: "+rowNum+" sku: "+sku+" Product Name: "+productName+" supplierPrice: "+supplierPrice+" retailPrice: "+retailPrice);
				
				//result = (supplyPrice*(markUp/100)+supplyPrice).toFixed(2);
				//double newRetailPrice = (supplierPr*(markUp/100)+supplierPr);
				//System.out.println("newRetailPrice: "+newRetailPrice+" oldRetailPrice: "+retailPr);
				
				
				
				
				//Product product =  productService.getProductByProductName(productName, user.getCompany().getCompanyId());
				if(productMap.get(sku)==null){
					Product newProduct = new Product();
					newProduct.setProductName(productName);
					newProduct.setSku(sku);
					productMap.put(sku, productName);
					newProduct.setProductType(productType);
					UUID uuidProd = UUID.randomUUID();
					//String randomUUIDProduct = uuidProd.toString();
					newProduct.setProductUuid(productName);
					newProduct.setProductHandler(productName);
					
					newProduct.setContact(contact);
					newProduct.setBrand(brand);
					newProduct.setProductCanBeSold("true");
					newProduct.setStandardProduct("true");
					newProduct.setTrackingProduct("true");
					newProduct.setVariantProducts("true");
					newProduct.setCurrentInventory(0);
					newProduct.setReorderPoint(0);
					newProduct.setReorderAmount(BigDecimal.ZERO);
					newProduct.setSupplyPriceExclTax(new BigDecimal(supplierPrice));
					newProduct.setMarkupPrct(markUpPrct);
					newProduct.setOutlet(user.getOutlet());
					newProduct.setSalesTax(salesTax);
					newProduct.setActiveIndicator(true);
					newProduct.setCreatedDate(new Date());
					newProduct.setUserByCreatedBy(user);
					newProduct.setUserByUpdatedBy(user);
					newProduct.setCompany(user.getCompany());
					newProduct.setLastUpdated(new Date());
					newProduct.setImagePath("");
					newProduct.setAttribute1(colour);
					Product product = productService.addProduct(newProduct, Actions.CREATE, 0, user.getCompany());

					ProductVariant productVariant = new ProductVariant();
					productVariant.setProduct(product);
					productVariant.setProductVariantUuid(UUID.randomUUID().toString());
					productVariant.setVariantAttributeName(colour);
					productVariant.setVariantAttributeValue1(colour);
					//productVariant.setVariantAttributeValue2(size);

					productVariant.setVariantAttributeByVariantAttributeAssocicationId1(variantAttributeByVariantAttributeAssocicationId1);
					//productVariant.setVariantAttributeByVariantAttributeAssocicationId2(variantAttributeByVariantAttributeAssocicationId2);
					productVariant.setCurrentInventory(variantDefalutInventory);
					productVariant.setReorderPoint(0);
					productVariant.setReorderAmount(BigDecimal.ZERO);
					productVariant.setProductUuid(product.getProductUuid());
					productVariant.setSupplyPriceExclTax(new BigDecimal(supplierPrice));
					productVariant.setMarkupPrct(markUpPrct);
					productVariant.setSku(sku);
					productVariant.setOutlet(user.getOutlet());
					productVariant.setSalesTax(salesTax);
					productVariant.setActiveIndicator(true);
					productVariant.setCreatedDate(new Date());
					productVariant.setLastUpdated(new Date());
					productVariant.setUserByCreatedBy(user);
					productVariant.setUserByUpdatedBy(user);
					productVariant.setCompany(user.getCompany());
					productVariant = productVariantService.addProductVariant(productVariant, Actions.CREATE, 0,  user.getCompany(), String.valueOf(product.getProductId()));


					VariantAttributeValues variantAttributeValues = new VariantAttributeValues();
					variantAttributeValues.setActiveIndicator(true);
					variantAttributeValues.setAttributeValue(colour);
					variantAttributeValues.setCompany(user.getCompany());
					variantAttributeValues.setCreatedDate(new Date());
					variantAttributeValues.setLastUpdated(new Date());
					variantAttributeValues.setProduct(product);
					variantAttributeValues.setProductVariant(productVariant);
					variantAttributeValues.setUserByCreatedBy(user);
					variantAttributeValues.setUserByUpdatedBy(user);
					variantAttributeValues.setProductUuid(productName);
					variantAttributeValues.setVariantAttribute(variantAttributeByVariantAttributeAssocicationId1);
					variantAttributeValuesService.addVariantAttributeValues(variantAttributeValues, user.getCompany().getCompanyId());

					
				}else{
					System.out.println("Product Already exit");
					System.out.println("Row Number: "+rowNum+" sku: "+sku+" Product Name: "+productName+" supplierPrice: "+supplierPrice+" retailPrice: "+retailPrice);
				}




				//     System.out.println(retail_price);

			
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();// logger.error(e.getMessage(),e);
		} catch (IOException e) {
			e.printStackTrace();// logger.error(e.getMessage(),e);
		}

	}
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static void SynchProductData1() {

		try {

			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			
			//Create blank workbook
		      XSSFWorkbook workbookWrite = new XSSFWorkbook();
		    //Create a blank sheet
		      XSSFSheet spreadsheet = workbookWrite.createSheet( " Mart Info new Code ");
		      Map < String, Object[] > productInfo = new TreeMap < String, Object[] >();
		      int duplicate = 0;
		      int i =0;
		      productInfo.put( i+"", new Object[] {
		    	         "ITEM CODE", "PRODUCT NAME", "SALE PRICE","COST PRICE" });
		      Map productMap = new HashMap<>();
			
		/*	SalesTax salesTax = salesTaxService.getSalesTaxBySalesTaxId(1, user.getCompany().getCompanyId());
			Brand brand = brandService.getBrandByBrandId(1, user.getCompany().getCompanyId());
			VariantAttribute variantAttributeByVariantAttributeAssocicationId1 = variantAttributeService.getVariantAttributeByVariantAttributeId(1, user.getCompany().getCompanyId());
			VariantAttribute variantAttributeByVariantAttributeAssocicationId2 = variantAttributeService.getVariantAttributeByVariantAttributeId(2, user.getCompany().getCompanyId());*/
			int rowNum = 0;
			while (iterator.hasNext()) {
				rowNum = rowNum+1;
				System.out.println(rowNum);

				Row currentRow = iterator.next();
				String barCode = "";
				currentRow.getCell(0).setCellType(CellType.STRING);
				if (currentRow.getCell(0).getCellTypeEnum() == CellType.STRING) {
					barCode = currentRow.getCell(0).getStringCellValue();
				} else if (currentRow.getCell(0).getCellTypeEnum() == CellType.NUMERIC) {
					//currentRow.getCell(0).setCellType(currentRow.getCell(0).CELL_TYPE_STRING);
					Double doub = currentRow.getCell(0).getNumericCellValue();
					System.out.println("doub: "+Double.toString(doub));
					//System.out.println("currentRow.getCell(0).getNumericCellValue(); "+currentRow.getCell(0).getNumericCellValue());
					barCode = String.valueOf(currentRow.getCell(0).getNumericCellValue());
				}
				System.out.println("Barcode: "+barCode);
				if(barCode.equalsIgnoreCase("")||barCode.equalsIgnoreCase("LIST OF ITEM FILE SORTED BY COST PRICE DESCENDING")
						||barCode.equalsIgnoreCase("Item Code")
						||barCode.equalsIgnoreCase("MART INN")||barCode.equalsIgnoreCase("LIST OF ITEM FILE SORTED BY COST PRICE DESCENDING")){
					continue;
				}
				//	                String lineItemName = currentRow.getCell(1).getStringCellValue();
				currentRow.getCell(1).setCellType(CellType.STRING);
				String productName =  currentRow.getCell(1).getStringCellValue();
				System.out.println("ProductName: "+productName);
				
				
				
				
				//	                String product_code = currentRow.getCell(2).getStringCellValue();
				//	                String Product_number = currentRow.getCell(4).getStringCellValue();
				double retailPrice = (double) currentRow.getCell(7).getNumericCellValue();
				double supplierPrice = (double) currentRow.getCell(8).getNumericCellValue();
				if(supplierPrice==0){
					System.out.println("supplierPrice: "+supplierPrice);
					supplierPrice = retailPrice;
				}
				if(retailPrice==0){
					retailPrice = 1.00;
					supplierPrice = 1.00;
				}
				productMap.get(barCode);
				if(productMap.get(barCode)!=null){
					duplicate++;
					System.out.println("dublicate bar Code: "+barCode+" with productName: "+productName);
				}else{
					productMap.put(barCode, productName);
					productInfo.put( i+++"", new Object[] {
							barCode, productName, retailPrice+"",supplierPrice+"" });
				}
				System.out.println("Product Name: "+productName+" salePrice: "+retailPrice+" retailPrice: "+supplierPrice);
				
				//	                String supplier_name = currentRow.getCell(6).getStringCellValue();
			//	String size = currentRow.getCell(7).getStringCellValue().replaceAll("temp_", "");
				//String Color = currentRow.getCell(8).getStringCellValue();
				//String bar_code = currentRow.getCell(9).getStringCellValue();
				//String retail_price = String.valueOf(currentRow.getCell(10).getNumericCellValue());
				//int count = (int) currentRow.getCell(11).getNumericCellValue();
			




				//     System.out.println(retail_price);

			}
			System.out.println("duplicate count:"+duplicate);
			//Iterate over data and write to sheet
		      Set < String > keyid = productInfo.keySet();
			//Create row object
		      XSSFRow row;
			int rowid = 0;
		      
		      for (String key : keyid) {
		         row = spreadsheet.createRow(rowid);
		         Object [] objectArr = productInfo.get(key);
		         int cellid = 0;
		         
		         for (Object obj : objectArr){
		            Cell cell = row.createCell(cellid++);
		            cell.setCellValue((String)obj);
		         }
		         rowid++;
		      }
		      //Write the workbook in file system
		      FileOutputStream out = new FileOutputStream(
		         new File("C:\\newmart.xlsx"));
		      
		      workbookWrite.write(out);
		      out.close();
		      System.out.println("New mart written successfully");
		   	
			
		} catch (Exception e) {
			e.printStackTrace();// logger.error(e.getMessage(),e);
		}

	}
	
	@SuppressWarnings("deprecation")
	public static void SynchProductData2() {

		try {
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME_NEW_MART));
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			int rowNum = 0;
			while (iterator.hasNext()) {
				rowNum = rowNum+1;
				//System.out.println("Row Number: "+rowNum);
				Row currentRow = iterator.next();
				String barCode = "";
				currentRow.getCell(0).setCellType(CellType.STRING);
				if (currentRow.getCell(0).getCellTypeEnum() == CellType.STRING) {
					barCode = currentRow.getCell(0).getStringCellValue();
				} else if (currentRow.getCell(0).getCellTypeEnum() == CellType.NUMERIC) {
					barCode = String.valueOf(currentRow.getCell(0).getNumericCellValue());
				}
				String productName =  currentRow.getCell(1).getStringCellValue();
				String retailPrice = currentRow.getCell(2).getStringCellValue();
				String supplierPrice = currentRow.getCell(3).getStringCellValue();
				
				double retailPr = Double.parseDouble(retailPrice);
				double supplierPr = Double.parseDouble(supplierPrice);
				
				double markUp = (retailPr-supplierPr)*100/supplierPr;
				DecimalFormat numberFormat = new DecimalFormat("#.00");
				System.out.println("Markup: "+numberFormat.format(markUp));
				BigDecimal mark = new BigDecimal(numberFormat.format(markUp));
				System.out.println("Big Decimal mark: "+mark);
				System.out.println("Row Number: "+rowNum+" barCode: "+barCode+" Product Name: "+productName+" supplierPrice: "+supplierPrice+" retailPrice: "+retailPrice);
				
				//result = (supplyPrice*(markUp/100)+supplyPrice).toFixed(2);
				double newRetailPrice = (supplierPr*(markUp/100)+supplierPr);
				System.out.println("newRetailPrice: "+newRetailPrice+" oldRetailPrice: "+retailPr);
				//System.out.println("markUp: "+markUp.setScale(5, RoundingMode.HALF_EVEN));
			}
						
		} catch (Exception e) {
			e.printStackTrace();// logger.error(e.getMessage(),e);
		}

	}
	public static void updateProductsAttributes(){
		try{
			
		}catch(Exception ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
	}
	private static final String FILE_NAME_HEATMAP = "C:\\heatmap\\Heatmap_Adhoc.xlsx";
	@SuppressWarnings("deprecation")
	public static void heatmapAdhoc() {

		try {
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME_HEATMAP));
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			int rowNum = 0;
			while (iterator.hasNext()) {
				rowNum = rowNum+1;
				//System.out.println("Row Number: "+rowNum);
				Row currentRow = iterator.next();
				
				for(int i=0;i<26;i++) {
					String cell =  currentRow.getCell(i).getStringCellValue();
					System.out.println("Cell Id: "+i+" Cell Value: "+cell);
				}
				
				//System.out.println("markUp: "+markUp.setScale(5, RoundingMode.HALF_EVEN));
			}
						
		} catch (Exception e) {
			e.printStackTrace();// logger.error(e.getMessage(),e);
		}

	}
	private static final String FILE_NAME_LUMEN_FASION = "C:\\finallumen.xlsx";
	
	@SuppressWarnings("deprecation")
	public void SynchProductDataLumenFashions(User user) {

		try {
			String defaultColour = "Colour";
			String defaultSize = "Size";
			
			Map<String, Product> productMap = new HashMap<String, Product>();
			Map<Integer, ProductVariant> productVariantMap = new HashMap<Integer, ProductVariant>();
			Map<String, ProductType> productTypeMap = new HashMap<String, ProductType>();
			Map<String, Brand> brandMap = new HashMap<String, Brand>();
			
			SalesTax salesTax = new SalesTax();
			salesTax.setSalesTaxId(1);
			Brand defaultBrand = new Brand();
			defaultBrand.setBrandId(1);
			/*ProductType productType = new ProductType();
			productType.setProductTypeId(1);*/
			Contact supplier = new Contact();
			supplier.setContactName("LUMEN");
			supplier.setCompanyName("LUMEN");
			supplier.setDescription("LUMEN");
			supplier.setActiveIndicator(true);
			supplier.setDefaultMarkup(new BigDecimal("0"));
			supplier.setCompany(user.getCompany());
			supplier.setContactType("SUPPLIER");
			supplier.setOutlet(user.getOutlet());
			Contact contact = supplierService.addContact(supplier,user.getCompany().getCompanyId());
			
			
			
			VariantAttribute variantAttributeByVariantAttributeAssocicationId1 = variantAttributeService.getVariantAttributeByVariantAttributeId(1, user.getCompany().getCompanyId());
			VariantAttribute variantAttributeByVariantAttributeAssocicationId2 = variantAttributeService.getVariantAttributeByVariantAttributeId(2, user.getCompany().getCompanyId());
			
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME_LUMEN_FASION));
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			int rowNum = 0;
			while (iterator.hasNext()) {
				rowNum = rowNum+1;
				Row currentRow = iterator.next();
				String productName =  currentRow.getCell(0).getStringCellValue();
				String productTypeName =  currentRow.getCell(1).getStringCellValue();
				/*if(productTypeName.toLowerCase().trim().equalsIgnoreCase("Jacket")||
						productTypeName.equalsIgnoreCase("JERSEY")||productTypeName.toLowerCase().trim().equalsIgnoreCase("jersey")
						||productTypeName.equalsIgnoreCase("Sweat Shirt")||productTypeName.equalsIgnoreCase("SWEAT SHIRT")||productTypeName.toLowerCase().trim().equalsIgnoreCase("sweat shirt")
						||productTypeName.equalsIgnoreCase("Top")||productTypeName.toLowerCase().trim().equalsIgnoreCase("top")
						||productTypeName.equalsIgnoreCase("TracK Suit")||productTypeName.toLowerCase().trim().equalsIgnoreCase("track suit")){
					continue;
				}*/
				String brandName =  currentRow.getCell(2).getStringCellValue();
				String description =  currentRow.getCell(3).getStringCellValue();
				//String supplier =  currentRow.getCell(4).getStringCellValue();
				currentRow.getCell(5).setCellType(CellType.STRING);
				String supplierPrice = "";
				if (currentRow.getCell(5).getCellTypeEnum() == CellType.STRING) {
					supplierPrice = currentRow.getCell(5).getStringCellValue();
				} else if (currentRow.getCell(5).getCellTypeEnum() == CellType.NUMERIC) {
					supplierPrice = String.valueOf(currentRow.getCell(5).getNumericCellValue());
				}
				String retailPrice = "";
				currentRow.getCell(6).setCellType(CellType.STRING);
				if (currentRow.getCell(6).getCellTypeEnum() == CellType.STRING) {
					retailPrice = currentRow.getCell(6).getStringCellValue();
				} else if (currentRow.getCell(6).getCellTypeEnum() == CellType.NUMERIC) {
					retailPrice = String.valueOf(currentRow.getCell(6).getNumericCellValue());
				}
				
				if(supplierPrice==null || supplierPrice.equalsIgnoreCase("")){
					supplierPrice = "1.00";
					retailPrice = "1.00";
				}
			//	String rp =  currentRow.getCell(6).getStringCellValue();
				String colour =  currentRow.getCell(7).getStringCellValue();
				if(colour==null || colour.equalsIgnoreCase("")){
					colour = defaultColour;
				}
				colour = colour.replaceAll("/", " ");
								
				currentRow.getCell(8).setCellType(CellType.STRING);
				String size = "";
				if (currentRow.getCell(8).getCellTypeEnum() == CellType.STRING) {
					size = currentRow.getCell(8).getStringCellValue();
				} else if (currentRow.getCell(8).getCellTypeEnum() == CellType.NUMERIC) {
					size = String.valueOf(currentRow.getCell(8).getNumericCellValue());
				}
				if(size==null || size.equalsIgnoreCase("")){
					size = defaultSize;
				}
				size = size.replaceAll("/", " ");
				
//				String size =  currentRow.getCell(8).getStringCellValue();
				currentRow.getCell(9).setCellType(CellType.STRING);
				String quantity = "";
				if (currentRow.getCell(9).getCellTypeEnum() == CellType.STRING) {
					quantity = currentRow.getCell(9).getStringCellValue();
				} else if (currentRow.getCell(9).getCellTypeEnum() == CellType.NUMERIC) {
					quantity = String.valueOf(currentRow.getCell(9).getNumericCellValue());
				}
				
				//String quantity =  currentRow.getCell(9).getStringCellValue();
				
				
				System.out.println("Product Name: "+productName);
				
//				String retailPrice = currentRow.getCell(2).getStringCellValue();
//				String supplierPrice = currentRow.getCell(3).getStringCellValue();
				
				double retailPr = Double.parseDouble(retailPrice);
				double supplierPr = Double.parseDouble(supplierPrice);
				
				double markUp = (retailPr-supplierPr)*100/supplierPr;
				DecimalFormat numberFormat = new DecimalFormat("#.00000");
				System.out.println("Markup: "+numberFormat.format(markUp));
				BigDecimal mark = new BigDecimal(numberFormat.format(markUp));
				System.out.println("Big Decimal mark: "+mark);
				//System.out.println("Row Number: "+rowNum+" barCode: "+barCode+" Product Name: "+productName+" supplierPrice: "+supplierPrice+" retailPrice: "+retailPrice);
				
				//result = (supplyPrice*(markUp/100)+supplyPrice).toFixed(2);
				double newRetailPrice = (supplierPr*(markUp/100)+supplierPr);
				System.out.println("newRetailPrice: "+newRetailPrice+" oldRetailPrice: "+retailPr);
				//System.out.println("markUp: "+markUp.setScale(5, RoundingMode.HALF_EVEN));
				BigDecimal markUpPrct = new BigDecimal(numberFormat.format(markUp)).setScale(5, RoundingMode.HALF_EVEN);
				System.out.println("Big Decimal markUpPrct: "+markUpPrct);
				if(productMap.get(productName)==null){
					Product newProduct = new Product();
					newProduct.setProductName(productName);
					String sku = "LUMENFP"+productMap.size()+1+"";
					newProduct.setSku(sku);
//					productMap.put(sku, productName);
					if(productTypeMap.get(productTypeName)==null){
						ProductType productType = new ProductType();
						productType.setActiveIndicator(true);
						productType.setProductTypeName(productTypeName);
						productType.setCreatedBy(user.getUserId());
						productType.setCreatedDate(new Date());
						productType.setLastUpdated(new Date());
						productType.setUpdatedBy(user.getUserId());
						productType.setCompany(user.getCompany());
						productType = productTypeService.addProductType(productType,user.getCompany().getCompanyId());
						productTypeMap.put(productType.getProductTypeName(), productType);
						newProduct.setProductType(productType);
					}else{
						newProduct.setProductType((productTypeMap.get(productTypeName)));
					}
					
					UUID uuidProd = UUID.randomUUID();
					//String randomUUIDProduct = uuidProd.toString();
					newProduct.setProductUuid(productName);
					newProduct.setProductHandler(productName);
					
					newProduct.setContact(contact);
					
					if(brandName==null || brandName.equalsIgnoreCase("")){
						
						newProduct.setBrand(defaultBrand);
					}else{
						if(brandMap.get(brandName)==null){
							Brand brand = new Brand();
							brand.setActiveIndicator(true);
							brand.setBrandDescription(brandName);
							brand.setBrandName(brandName);
							brand.setCreatedBy(user.getUserId());
							brand.setCreatedDate(new Date());
							brand.setLastUpdated(new Date());
							brand.setUpdatedBy(user.getUserId());
							brand.setCompany(user.getCompany());
							brand = brandService.addBrand(brand,0);
							brandMap.put(brand.getBrandName(), brand);
							newProduct.setBrand(brand);
						}else{
							newProduct.setBrand(brandMap.get(brandName));
						}
					}
					
					newProduct.setProductCanBeSold("true");
					newProduct.setStandardProduct("true");
					newProduct.setTrackingProduct("true");
					newProduct.setVariantProducts("true");
					newProduct.setIsComposite("false");
					newProduct.setCurrentInventory(0);
					newProduct.setReorderPoint(0);
					newProduct.setReorderAmount(BigDecimal.ZERO);
					newProduct.setSupplyPriceExclTax(new BigDecimal(supplierPrice));
					newProduct.setMarkupPrct(markUpPrct);
					newProduct.setOutlet(user.getOutlet());
					newProduct.setSalesTax(salesTax);
					newProduct.setActiveIndicator(true);
					newProduct.setCreatedDate(new Date());
					newProduct.setUserByCreatedBy(user);
					newProduct.setUserByUpdatedBy(user);
					newProduct.setCompany(user.getCompany());
					newProduct.setLastUpdated(new Date());
					newProduct.setImagePath("");
					//newProduct.setAttribute1(colour);
					newProduct.setProductDesc(description);
					Product product = productService.addProduct(newProduct, Actions.CREATE, 0, user.getCompany());
					productMap.put(product.getProductName(), product);
					
					ProductVariant productVariant = new ProductVariant();
					productVariant.setProduct(product);
					productVariant.setProductVariantUuid(UUID.randomUUID().toString());
					
					productVariant.setVariantAttributeName(colour+"/"+size);
					productVariant.setVariantAttributeValue1(colour);
					productVariant.setVariantAttributeValue2(size);

					productVariant.setVariantAttributeByVariantAttributeAssocicationId1(variantAttributeByVariantAttributeAssocicationId1);
					productVariant.setVariantAttributeByVariantAttributeAssocicationId2(variantAttributeByVariantAttributeAssocicationId2);
					if(quantity==null || quantity.equalsIgnoreCase("")){
						productVariant.setCurrentInventory(0);
					}else{
						try{
							Integer currentInventory = Integer.valueOf(quantity);
							productVariant.setCurrentInventory(currentInventory);
						}catch(Exception ex){
							ex.printStackTrace();// logger.error(ex.getMessage(),ex);
							productVariant.setCurrentInventory(0);
						}
						
					}
					
					productVariant.setReorderPoint(0);
					productVariant.setReorderAmount(BigDecimal.ZERO);
					productVariant.setProductUuid(product.getProductUuid());
					productVariant.setSupplyPriceExclTax(new BigDecimal(supplierPrice));
					productVariant.setMarkupPrct(markUpPrct);
					productVariant.setSku("LUMENFPV"+productVariantMap.size()+1+"");
					productVariant.setOutlet(user.getOutlet());
					productVariant.setSalesTax(salesTax);
					productVariant.setActiveIndicator(true);
					productVariant.setCreatedDate(new Date());
					productVariant.setLastUpdated(new Date());
					productVariant.setUserByCreatedBy(user);
					productVariant.setUserByUpdatedBy(user);
					productVariant.setCompany(user.getCompany());
					productVariant = productVariantService.addProductVariant(productVariant, Actions.CREATE, 0,  user.getCompany(), String.valueOf(product.getProductId()));
					productVariantMap.put(productVariant.getProductVariantId(), productVariant);
				}else{
					Product product = productMap.get(productName);
					ProductVariant productVariant = new ProductVariant();
					productVariant.setProduct(product);
					productVariant.setProductVariantUuid(UUID.randomUUID().toString());
					
					productVariant.setVariantAttributeName(colour+"/"+size);
					productVariant.setVariantAttributeValue1(colour);
					productVariant.setVariantAttributeValue2(size);

					productVariant.setVariantAttributeByVariantAttributeAssocicationId1(variantAttributeByVariantAttributeAssocicationId1);
					productVariant.setVariantAttributeByVariantAttributeAssocicationId2(variantAttributeByVariantAttributeAssocicationId2);
					if(quantity==null || quantity.equalsIgnoreCase("")){
						productVariant.setCurrentInventory(0);
					}else{
						Integer currentInventory = Integer.valueOf(quantity);
						productVariant.setCurrentInventory(currentInventory);
					}
					
					productVariant.setReorderPoint(0);
					productVariant.setReorderAmount(BigDecimal.ZERO);
					productVariant.setProductUuid(product.getProductUuid());
					productVariant.setSupplyPriceExclTax(new BigDecimal(supplierPrice));
					productVariant.setMarkupPrct(markUpPrct);
					productVariant.setSku("LUMENFPV"+productVariantMap.size()+1+"");
					productVariant.setOutlet(user.getOutlet());
					productVariant.setSalesTax(salesTax);
					productVariant.setActiveIndicator(true);
					productVariant.setCreatedDate(new Date());
					productVariant.setLastUpdated(new Date());
					productVariant.setUserByCreatedBy(user);
					productVariant.setUserByUpdatedBy(user);
					productVariant.setCompany(user.getCompany());
					productVariant = productVariantService.addProductVariant(productVariant, Actions.CREATE, 0,  user.getCompany(), String.valueOf(product.getProductId()));
					productVariantMap.put(productVariant.getProductVariantId(), productVariant);
				}
				
			}
						
		} catch (Exception e) {
			e.printStackTrace();// logger.error(e.getMessage(),e);
		}

	}
	
	public static void main(String args[]){
		double retailPr = Double.parseDouble("2450");
		double supplierPr = Double.parseDouble("1100");
		
		double markUp = (retailPr-supplierPr)*100/supplierPr;
		DecimalFormat numberFormat = new DecimalFormat("#.00000");
		System.out.println("Markup: "+numberFormat.format(markUp));
		BigDecimal mark = new BigDecimal(numberFormat.format(markUp));
		System.out.println("Big Decimal mark: "+mark);
		//System.out.println("Row Number: "+rowNum+" barCode: "+barCode+" Product Name: "+productName+" supplierPrice: "+supplierPrice+" retailPrice: "+retailPrice);
		
		//result = (supplyPrice*(markUp/100)+supplyPrice).toFixed(2);
		double newRetailPrice = (supplierPr*(markUp/100)+supplierPr);
		System.out.println("newRetailPrice: "+newRetailPrice+" oldRetailPrice: "+retailPr);
		//System.out.println("markUp: "+markUp.setScale(5, RoundingMode.HALF_EVEN));
		BigDecimal markUpPrct = new BigDecimal(numberFormat.format(markUp)).setScale(5, RoundingMode.HALF_EVEN);
		System.out.println("Big Decimal markUpPrct: "+markUpPrct);
		/*double number = 90909090.123456789;
		DecimalFormat numberFormat = new DecimalFormat("#.00000");
		System.out.println(numberFormat.format(number));*/
		
		User user  = new User();
//		SynchProductDataLumenFashions(user);
	}


}
