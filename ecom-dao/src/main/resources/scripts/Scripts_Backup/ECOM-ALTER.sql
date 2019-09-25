USE `ecom`;
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('userSalesReport',1,'Page',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('userSalesReport',2,'Page',false,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('userSalesReport',3,'Page',false,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('inventoryReport',1,'Page',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('inventoryReport',2,'Page',false,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('inventoryReport',3,'Page',false,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('outletSalesReport',1,'Page',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('outletSalesReport',2,'Page',false,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('outletSalesReport',3,'Page',false,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('brandSalesReport',1,'Page',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('brandSalesReport',2,'Page',false,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('brandSalesReport',3,'Page',false,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('productTypeSalesReport',1,'Page',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('productTypeSalesReport',2,'Page',false,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('productTypeSalesReport',3,'Page',false,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
alter table contact
ADD CONTACT_TYPE VARCHAR(250) NOT NULL DEFAULT 'SUPPLIER';
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('supplierSalesReport',1,'Page',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('supplierSalesReport',2,'Page',false,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('supplierSalesReport',3,'Page',false,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('customerSalesReport',1,'Page',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('customerSalesReport',2,'Page',false,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('customerSalesReport',3,'Page',false,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('customerGroupSalesReport',1,'Page',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('customerGroupSalesReport',2,'Page',false,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('customerGroupSalesReport',3,'Page',false,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('paymentReport',1,'Page',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('paymentReport',2,'Page',false,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('paymentReport',3,'Page',false,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('registerReport',1,'Page',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('registerReport',2,'Page',false,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('registerReport',3,'Page',false,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1,1,1);