USE `ecom`;
SELECT @companyId := 1, @createdBy := 1;
-- -------------------Menu-------------
-- ===================================== Role ID = 1 (Admin) ======================================
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('backup',1,'Page',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,@createdBy,@createdBy,@companyId);
-- For window its necessary to create folder first where you want to store backup
insert into configuration (PROPERTY_NAME,PROPERTY_VALUE,COMPANY_ASSOCIATION_ID,CREATED_BY,UPDATED_BY,CREATED_DATE,LAST_UPDATED,ACTIVE_INDICATOR) values ('BACKUP_PATH','C:/backup/backup.sql',@companyId,@createdBy,@createdBy,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1);
	