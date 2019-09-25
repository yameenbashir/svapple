USE `ecom`;
SELECT @companyId := 1, @createdBy := 1;
-- -------------------Menu-------------
-- ===================================== Role ID = 1 (Admin) ======================================
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('tagSalesReport',1,'Page',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,@createdBy,@createdBy,@companyId);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('tagSalesReport',2,'Page',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,@createdBy,@createdBy,@companyId);
insert into menu (MENU_NAME,ROLE_ASSOCIATION_ID,ACTION_TYPE,ACTIVE_INDICATOR,CREATED_DATE,LAST_UPDATED,CREATED_BY,UPDATED_BY,COMPANY_ASSOCIATION_ID)
	values ('tagSalesReport',3,'Page',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,@createdBy,@createdBy,@companyId);
	
DROP VIEW IF EXISTS tag_sales_report;
CREATE VIEW tag_sales_report AS 
SELECT 
(select OUTLET_NAME from ecom.outlet where OUTLET_ASSOCICATION_ID=OUTLET_ID) AS 'Outlet',
(select TAG_NAME from ecom.tag where TAG_ID =(select TAG_ASSOCICATION_ID from ecom.product_tag where PRODUCT_TAG_UUID = (select PRODUCT_UUID from ecom.product where PRODUCT_ASSOCIATION_ID=PRODUCT_ID))) AS 'Tag',
DATE(CREATED_DATE) AS CREATED_DATE, SUM(ITEM_SALE_PRICE * PRODUCT_QUANTITY ) AS Revenue,(SUM(ITEM_SALE_PRICE * PRODUCT_QUANTITY )+SUM(ITEM_TAX_AMOUNT * PRODUCT_QUANTITY )) AS 'Revenue_tax_incl', SUM(ITEM_ORIGNAL_AMT * PRODUCT_QUANTITY ) AS 'Cost_of_Goods', 
(SUM(ITEM_SALE_PRICE * PRODUCT_QUANTITY )-SUM(ITEM_ORIGNAL_AMT * PRODUCT_QUANTITY )) As 'Gross_Profit', 
(100-(SUM(ITEM_ORIGNAL_AMT)/SUM(ITEM_SALE_PRICE))*100) As Margin,
SUM(PRODUCT_QUANTITY) AS 'Items_Sold',
SUM(ITEM_TAX_AMOUNT) AS Tax,
COMPANY_ASSOCIATION_ID,OUTLET_ASSOCICATION_ID
FROM ecom.invoice_detail
group by PRODUCT_ASSOCIATION_ID, DATE(CREATED_DATE)
Order by  DATE(CREATED_DATE);