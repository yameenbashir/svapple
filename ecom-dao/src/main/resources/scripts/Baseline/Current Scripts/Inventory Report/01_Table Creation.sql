use ecom;
create table `Mv_Inventory_Report` (
	`ID` double ,
	`PRODUCT_NAME` longtext ,
	`SKU` varchar (1500),
	`SUPPLY_PRICE_EXCL_TAX` Decimal (22),
	`MARKUP_PRCT` Decimal (14),
	`REORDER_POINT` double ,
	`REORDER_AMOUNT` Decimal (22),
	`CURRENT_INVENTORY` double ,
	`NET_PRICE` Decimal (39),
	`OUTLET_NAME` varchar (300),
	`OUTLET_ASSOCICATION_ID` double ,
	`CREATED_DATE` timestamp ,
	`BRAND_NAME` varchar (600),
	`PRODUCT_TYPE` varchar (600),
	`CONTACT_NAME` varchar (600),
	`COMPANY_ASSOCIATION_ID` double ,
	`STOCK_VALUE` Decimal (32),
	`RETAIL_VALUE` Decimal (49)
); 


-- insert into `mv_inventory_report` (`ID`, `PRODUCT_NAME`, `SKU`, `SUPPLY_PRICE_EXCL_TAX`, `MARKUP_PRCT`, `REORDER_POINT`, `REORDER_AMOUNT`, `CURRENT_INVENTORY`, `NET_PRICE`, `OUTLET_NAME`, `OUTLET_ASSOCICATION_ID`, `CREATED_DATE`, `BRAND_NAME`, `PRODUCT_TYPE`, `CONTACT_NAME`, `COMPANY_ASSOCIATION_ID`, `STOCK_VALUE`, `RETAIL_VALUE`)
-- select * from inventory_report;

delete from mv_inventory_report;
insert into mv_inventory_report (select * from inventory_report);