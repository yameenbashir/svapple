package com.dowhile.angualrspringapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.dowhile.service.OutletService;
import com.dowhile.service.ProductService;
import com.dowhile.service.ProductTypeService;
import com.dowhile.service.ProductVariantService;
import com.dowhile.service.ResourceService;
import com.dowhile.service.SalesTaxService;
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
	@RequestMapping("/layout")
	public String getLoginControllerPartialPage(ModelMap modelMap) {
		return "login/layout";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/doLogin", method = RequestMethod.POST)
	public @ResponseBody Response doLogin(@RequestBody UserBean appuser,
			HttpServletRequest request) {
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
				Map<String ,Configuration> configurationMap = configurationService.getAllConfigurationsByCompanyId(company.getCompanyId());
				session.setAttribute("configurationMap", configurationMap);
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
				loginBean.setUserId(user.getUserId().toString());
				List<Menu> menuList = menuService.getMenuByRoleId(user.getRole().getRoleId(),company.getCompanyId());
				loginBean.setMapMenu(populateMenuBeanList(menuList));
				loginBean.setUserMap(populateUserBeanList(resourceService.getAllEmployeesByCompanyId(company.getCompanyId())));
				Configuration configuration = configurationMap.get("COMPANY_RECEIPT_IMAGE");
				if(configuration!=null){
					String companyImagePath = "/app/resources/images/"+configuration.getPropertyValue();
					loginBean.setCompanyImagePath(companyImagePath.trim());
				}
				Configuration domianConfiguration = configurationMap.get("SUB_DOMAIN_NAME");
				if(domianConfiguration!=null){
					String subDomianName = domianConfiguration.getPropertyValue();
					session.setAttribute("subDomianName", subDomianName);
				}
				
				//				SynchSupplierData(user);
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
					util.AuditTrail(request, user, "LoginController.doLogin", "Authorization failde for user : "+
					loginBean.getUserName()+" against companyId : "+company.getCompanyId()+
					" with company Name: "+company.getCompanyName(),false);
					return new Response(MessageConstants.AUTHORIZATION_FAILED,
							StatusConstants.USER_INVALID,
							LayOutPageConstants.STAY_ON_PAGE);
					
				}
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
			e.printStackTrace();// logger.error(e.getMessage(),e);
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

	private static final String FILE_NAME = "/var/lib/openshift/58d95a9689f5cf06c50001ca/app-root/data/Products_Kites_2017.xlsx";
	// private static final String FILE_NAME = "C:\\Users\\IBM_ADMIN\\Desktop\\Products_Kites_2017.xlsx";

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

}
