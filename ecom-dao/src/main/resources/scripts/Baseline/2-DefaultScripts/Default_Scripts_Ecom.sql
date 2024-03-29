
-- AUTHOR : Team(DOWHILE)
-- CREATION DATE : 31-DEC-2016
-- UPDATION DATE : 18-FEB-2017
-- First we query on db and find the last compnayID, userId,outletId,registerId,salesTaxId and contactGroupId and assing these values to below variables thats it.
-- no need updatedBy on default script we will use createdBy for both
-- In case of new server set all ***Ids to 1
SELECT @companyId := 1, @createdBy := 1, @companyName := 'Meeras' , @defaultUserEmail:='admin@meeras.com', @outletId:=1 , @RegisterId:=1 , @saleTaxId:=1, @contactGroupId:=1;

-- ----------Company----------------------

INSERT INTO company (COMPANY_ID,COMPANY_NAME, DISPLAY_PRICES, SKU_GENERATION, CURRENT_SEQUENCE_NUMBER, LOYALTY_ENABLED, LOYALTY_INVOICE_AMOUNT, LOYALTY_AMOUNT, LOYALTY_ENABLED_DATE, USER_SWITCH_SECURITY, ACTIVE_INDICATOR, CREATED_DATE, LAST_UPDATED, CREATED_BY, UPDATED_BY, PRINTER_ASSOCICATION_ID, CURRENCY_ASSOCICATION_ID, TIME_ZONE_ASSOCICATION_ID,LOYALTY_BONUS_ENABLED_DATE,LOYALTY_BONUS_AMOUNT,LOYALTY_BONUS_ENABLED,ENABLE_STORES_CREDIT)
VALUES (@companyId,@companyName, 'Tax Inclusive', 'Generate By Name', '10000', 0, 5000, 50, CURRENT_TIMESTAMP, 'Never require a password when switching between users', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, @createdBy, @createdBy, 1, 1, 1,CURRENT_TIMESTAMP,0.00,1,'false');
-- ---------------user--------------

insert into user (USER_EMAIL,PASSWORD,ROLE_ASSOCIATION_ID,FIRST_NAME,LAST_NAME,CONTACT_ASSOCICATION_ID,OUTLET_ASSOCICATION_ID,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,LAST_LOGIN,COMPANY_ASSOCIATION_ID)
	values (@defaultUserEmail,'123','1','Admin','Admin',null,null,1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,@createdBy, @createdBy,CURRENT_TIMESTAMP,@companyId);
-- -------------------Sales Tax-------------

INSERT INTO sales_tax (SALES_TAX_ID,SALES_TAX_NAME,SALES_TAX_PERCENTAGE,EFFECTIVE_DATE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
values (@saleTaxId,'No Tax',0,CURRENT_DATE,1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,@createdBy,@createdBy,@companyId);
-- -------------------address-----------------------------------

INSERT INTO address ( ADDRESS_TYPE, CONTACT_NAME, FIRST_NAME, LAST_NAME, EMAIL, PHONE, FAX, WEBSITE, TWITTER, STREET, SUBURB, CITY, POSTAL_CODE, STATE, COUNTY, X_COORDINATE, Y_COORDINATE, ACTIVE_INDICATOR, CREATED_DATE, LAST_UPDATED, CREATED_BY, UPDATED_BY, COMPANY_ASSOCIATION_ID, CONTACT_ASSOCICATION_ID, COUNTRY_ASSOCICATION_ID)
VALUES ( "Physical Address", "Admin", "", "", "adminInfo@shopvitals.com", "", "23424", "www.shopvitals.com", "twiter", "", "Suburb", "Lahore", "234234", "Punjab", "Pakistan", 0, 0, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, @createdBy,@createdBy,@companyId, NULL, 1);
-- -------------------customer_group-----------------------------------

INSERT INTO contact_group (CONTACT_GROUP_ID, CONTACT_GROUP_NAME, COUNTRY_ASSOCICATION_ID, ACTIVE_INDICATOR, 
CREATED_DATE, LAST_UPDATED, CREATED_BY, UPDATED_BY,COMPANY_ASSOCIATION_ID)
VALUES (@contactGroupId,'All Contact', 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, @createdBy,@createdBy,@companyId);
-- ---------------------------------outlet---------------------------------

INSERT INTO outlet (OUTLET_ID,OUTLET_NAME, ORDER_NUMBER, ORDER_NUMBER_PREFIX, CONTACT_NUMBER_PREFIX, CONTACT_RETURN_NUMBER, COMPANY_ASSOCIATION_ID, SALES_TAX_ASSOCICATION_ID, ADDRESS_ASSOCICATION_ID, TIME_ZONE_ASSOCICATION_ID, ACTIVE_INDICATOR, CREATED_DATE, LAST_UPDATED, CREATED_BY, UPDATED_BY,IS_HEAD_OFFICE)
VALUES (@outletId, 'Main Outlet', '100', 'Main Outlet', 'temp', '4', @companyId, @saleTaxId, null, 1, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, @createdBy,@createdBy,1);
-- ------------------------------Main Register --------------------------

INSERT INTO `ecom`.`register` (`REGISTER_NAME`, `CACHE_MANAGEMENT_ENABLE`, `RECEIPT_NUMBER`, `RECEIPT_PREFIX`, `RECEIPT_SUFIX`, `SELECT_NEXT_USER_FOR_SALE`, `EMAIL_RECEIPT`, `PRINT_RECEIPT`, `NOTES`, `PRINT_NOTES_ON_RECEIPT`, `SHOW_DISCOUNT_ON_RECEIPT`, `ACTIVE_INDICATOR`, `CREATED_DATE`, `LAST_UPDATED`, `CREATED_BY`, `UPDATED_BY`, `OUTLET_ASSOCICATION_ID`, `COMPANY_ASSOCIATION_ID`) VALUES ('Main Register', 'Yes', '1', '1', '1', 'No', 'No', 'Yes', 'On Save/Layby/Account/Return', 'No', 'Yes', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, @createdBy,@createdBy,@outletId,@companyId);
-- ----------------------------------User_Outlet-------------------------

INSERT INTO user_outlets (`COMPANY_ASSOCIATION_ID`, `USER_ASSOCIATION_ID`, `OUTLET_ASSOCICATION_ID`, `ACTIVE_INDICATOR`, `CREATED_DATE`, `LAST_UPDATED`, `CREATED_BY`, `UPDATED_BY`) VALUES ( @companyId, @createdBy, @outletId, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, @createdBy, @createdBy);
-- ----------------------------User Outlet Update------------------------

UPDATE `ecom`.`user` SET `OUTLET_ASSOCICATION_ID`=@outletId WHERE `USER_ID`=@createdBy;
-- -------------------------Contacts--------------------------------------

INSERT INTO `ecom`.`contact` (`CONTACT_NAME`, `CONTACT_BALANCE`, `ACTIVE_INDICATOR`, `CREATED_DATE`, `LAST_UPDATED`, `CONTACT_OUTLET_ID`, `CREATED_BY`, `COMPANY_ASSOCIATION_ID`,CONTACT_GROUP_ASSOCIATION_ID) VALUES ('Main Outlet', '0.00000', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, @outletId, @createdBy, @companyId,@contactGroupId);
-- ------------------------ Kites Data -------------------------------------

INSERT INTO `ecom`.`brand` (`BRAND_NAME`, `BRAND_DESCRIPTION`, `ACTIVE_INDICATOR`, `CREATED_DATE`, `LAST_UPDATED`, `CREATED_BY`, `UPDATED_BY`, `COMPANY_ASSOCIATION_ID`) VALUES (@companyName, @companyName, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, @createdBy, @createdBy, @companyId);
-- INSERT INTO `ecom`.`variant_attribute` (`ATTRIBUTE_NAME`, `ACTIVE_INDICATOR`, `CREATED_DATE`, `LAST_UPDATED`, `CREATED_BY`, `UPDATED_BY`, `VARIANT_ATTRIBUTE_ASSOCIATION_ID`, `COMPANY_ASSOCIATION_ID`) VALUES ( 'Color', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, @createdBy, @createdBy, '0', @companyId);
-- INSERT INTO `ecom`.`variant_attribute` (`ATTRIBUTE_NAME`, `ACTIVE_INDICATOR`, `CREATED_DATE`, `LAST_UPDATED`, `CREATED_BY`, `UPDATED_BY`, `VARIANT_ATTRIBUTE_ASSOCIATION_ID`, `COMPANY_ASSOCIATION_ID`) VALUES ( 'Size', TRUE, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, @createdBy, @createdBy, '0', @companyId);
-- ----------------------- ECOM USER -----------------------------------------

-- insert into user (USER_EMAIL,PASSWORD,ROLE_ASSOCIATION_ID,FIRST_NAME,LAST_NAME,CONTACT_ASSOCICATION_ID,OUTLET_ASSOCICATION_ID,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,LAST_LOGIN,COMPANY_ASSOCIATION_ID)
--	values ('ecom_admin@shopvitals.com','ecom_admin','4','Ecom','Admin',null,null,1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,@createdBy,@createdBy,CURRENT_TIMESTAMP,@companyId);

