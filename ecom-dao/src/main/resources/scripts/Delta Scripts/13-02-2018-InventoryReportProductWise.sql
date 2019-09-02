USE `ecom`;
SELECT @companyId := 1, @createdBy := 1;
-- -------------------Menu-------------
-- ===================================== Role ID = 1 (Admin) ======================================
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('inventoryReportProductWise',1,'Page',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,@createdBy,@createdBy,@companyId);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('inventoryReportProductWise',2,'Page',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,@createdBy,@createdBy,@companyId);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('inventoryReportProductWise',3,'Page',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,@createdBy,@createdBy,@companyId);
