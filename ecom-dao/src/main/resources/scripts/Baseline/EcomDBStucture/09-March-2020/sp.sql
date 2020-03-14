/*
SQLyog Enterprise - MySQL GUI v7.02 
MySQL - 5.0.27-community-nt : Database - ecom
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`ecom` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `ecom`;

/* Procedure structure for procedure `CreateFullInventoryCount` */

/*!50003 DROP PROCEDURE IF EXISTS  `CreateFullInventoryCount` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `CreateFullInventoryCount`(outletid int(20), inv_count_id int, userId int, cId int)
BEGIN
 
DECLARE v_finished INTEGER DEFAULT 0;
        DECLARE pvid int(20) DEFAULT 0;
	DECLARE c_invent int(20) DEFAULT 0;
        DECLARE supplyPrice decimal(20,2) DEFAULT 0.0;
        DECLARE retailPrice decimal(20,2) DEFAULT 0.0;
	DECLARE markup_prct decimal(12,5) DEFAULT 0.0;
	DECLARE V_PRICE_DIFF decimal(20,2) DEFAULT 0.0; 
	DECLARE V_COUNT_DIFF int(20) DEFAULT 0;
	DECLARE V_RETAIL_PRICE_EXP decimal(20,2) DEFAULT 0.0;
	DECLARE V_SUPPLY_PRICE_EXP decimal(20,2) DEFAULT 0.0;
	DECLARE V_EXPECTED_PROD_QTY decimal(20,2) DEFAULT 0.0;
	DECLARE V_COUNTED_PROD_QTY int(20) DEFAULT 0;
	DECLARE V_SUPPLY_PRICE_COUNTED decimal(20,2) DEFAULT 0.0;
	DECLARE V_RETAIL_PRICE_COUNTED decimal(20,2) DEFAULT 0.0;
 DEClARE outlet_products_curr CURSOR FOR 
 SELECT pv.product_variant_id, pv.CURRENT_INVENTORY, pv.SUPPLY_PRICE_EXCL_TAX, pv.MARKUP_PRCT FROM product_variant pv 
where pv.OUTLET_ASSOCICATION_ID = outletid and pv.CURRENT_INVENTORY > 0;
  DECLARE CONTINUE HANDLER 
       FOR NOT FOUND SET v_finished = 1;
 OPEN outlet_products_curr;
 
 get_pvid: LOOP
FETCH next from outlet_products_curr into pvid, c_invent, supplyPrice, markup_prct;
 
 IF v_finished  THEN 
   LEAVE get_pvid;
END IF;
set retailPrice := 0.0;
set retailPrice := (supplyPrice * markup_prct/100) + supplyPrice;
INSERT INTO INVENTORY_COUNT_DETAIL(INVENTORY_COUNT_ASSOCICATION_ID, PRODUCT_VARIANT_ASSOCICATION_ID, PRODUCT_ASSOCIATION_ID, IS_PRODUCT,
EXPECTED_PROD_QTY, SUPPLY_PRICE_EXP, RETAIL_PRICE_EXP, COUNTED_PROD_QTY, SUPPLY_PRICE_COUNTED, RETAIL_PRICE_COUNTED, COUNT_DIFF, PRICE_DIFF,
ACTIVE_INDICATOR, CREATED_DATE, LAST_UPDATED, CREATED_BY, UPDATED_BY, COMPANY_ASSOCIATION_ID, audit_transfer) VALUES
(inv_count_id,pvid, null, false, c_invent, supplyPrice * c_invent, retailPrice * c_invent, 0, 0.0, 0.0, c_invent, retailPrice, 
true, NOW(), NOW(), userId, userId, cId, 0);
END LOOP get_pvid;
 
CLOSE outlet_products_curr;
select sum(PRICE_DIFF), sum(COUNT_DIFF), sum(RETAIL_PRICE_EXP), sum(SUPPLY_PRICE_EXP), sum(EXPECTED_PROD_QTY), sum(COUNTED_PROD_QTY),
sum(SUPPLY_PRICE_COUNTED), sum(RETAIL_PRICE_COUNTED) into
V_PRICE_DIFF, V_COUNT_DIFF, V_RETAIL_PRICE_EXP, V_SUPPLY_PRICE_EXP, V_EXPECTED_PROD_QTY, V_COUNTED_PROD_QTY, V_SUPPLY_PRICE_COUNTED, 
V_RETAIL_PRICE_COUNTED from inventory_count_detail where INVENTORY_COUNT_ASSOCICATION_ID = inv_count_id;
UPDATE INVENTORY_COUNT set PRICE_DIFF = V_PRICE_DIFF,
COUNT_DIFF =  V_COUNT_DIFF,
RETAIL_PRICE_EXP = V_RETAIL_PRICE_EXP,
SUPPLY_PRICE_EXP = V_SUPPLY_PRICE_EXP,
EXPECTED_PROD_QTY = V_EXPECTED_PROD_QTY,
COUNTED_PROD_QTY = V_COUNTED_PROD_QTY,
SUPPLY_PRICE_COUNTED = V_SUPPLY_PRICE_COUNTED,
RETAIL_PRICE_COUNTED = V_RETAIL_PRICE_COUNTED,
LAST_UPDATED =  NOW(),
UPDATED_BY = userId WHERE INVENTORY_COUNT_ID = inv_count_id;
select  
icd.INVENTORY_COUNT_DETAIL_ID,  
icd.INVENTORY_COUNT_ASSOCICATION_ID,
icd.PRODUCT_VARIANT_ASSOCICATION_ID,
icd.PRODUCT_ASSOCIATION_ID, 
p.product_name,
concat(ppv.product_name, '-', pv.variant_attribute_name) VariantAttributeName,
icd.IS_PRODUCT,                       
icd.EXPECTED_PROD_QTY,                   
icd.SUPPLY_PRICE_EXP,                
icd.RETAIL_PRICE_EXP,                    
icd.COUNTED_PROD_QTY,                
icd.RETAIL_PRICE_COUNTED,
icd.SUPPLY_PRICE_COUNTED, 
icd.COUNT_DIFF,
icd.PRICE_DIFF,                    
icd.ACTIVE_INDICATOR,                 
icd.CREATED_DATE,                     
icd.LAST_UPDATED,                     
icd.CREATED_BY,                       
icd.UPDATED_BY,                       
icd.COMPANY_ASSOCIATION_ID,
icd.audit_transfer
from inventory_count_detail icd
left join product p on p.product_id = icd.PRODUCT_ASSOCIATION_ID
left join product_variant pv on pv.product_variant_id = icd.PRODUCT_VARIANT_ASSOCICATION_ID
left join product ppv on ppv.product_id = pv.product_assocication_id
where icd.INVENTORY_COUNT_ASSOCICATION_ID = inv_count_id
and icd.COMPANY_ASSOCIATION_ID = cId;
 
END */$$
DELIMITER ;

/* Procedure structure for procedure `GetAllInvoiceByOutletId` */

/*!50003 DROP PROCEDURE IF EXISTS  `GetAllInvoiceByOutletId` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `GetAllInvoiceByOutletId`(companyid int, outletid int, receiptRefNumber varchar(50), invoicestatus varchar(50),
fromdate date, todate date, customerid int)
BEGIN
 SELECT 
   invoice_main.INVOICE_MAIN_ID ,
   invoice_main.INVOICE_REF_NBR ,
   invoice_main.INVOICE_NOTES ,
   COALESCE(invoice_main.INVOICE_DISCOUNT,0) as INVOICE_DISCOUNT ,
   COALESCE(invoice_main.INVOICE_TAX,0) as INVOICE_TAX  ,
   invoice_main.INVC_TYPE_CDE  ,
   invoice_main.INVOICE_GENERATION_DTE ,
   invoice_main.INVOICE_CANCEL_DTE ,
   COALESCE(invoice_main.INVOICE_AMT,0) as INVOICE_AMT ,
   COALESCE(invoice_main.INVOICE_DISCOUNT_AMT,0) as INVOICE_DISCOUNT_AMT ,
   COALESCE(invoice_main.INVOICE_NET_AMT,0)  as INVOICE_NET_AMT,
   COALESCE(invoice_main.INVOICE_GIVEN_AMT ,0)  as INVOICE_GIVEN_AMT,
   COALESCE(invoice_main.INVOICE_ORIGNAL_AMT,0) as INVOICE_ORIGNAL_AMT ,
   COALESCE(invoice_main.SETTLED_AMT,0) as SETTLED_AMT  ,
   invoice_main.STATUS_ASSOCICATION_ID  ,
   invoice_main.ORDER_ASSOCICATION_ID  ,
   invoice_main.CONTACT_ASSOCIATION_ID  ,
   invoice_main.COMPANY_ASSOCIATION_ID  ,
   invoice_main.OUTLET_ASSOCICATION_ID  ,
   invoice_main.DAILY_REGISTER_ASSOCICATION_ID  ,
   invoice_main.PAYMENT_TYPE_ASSOCICATION_ID ,
   invoice_main.SALES_USER ,
 contact.CONTACT_ID,
 contact.CONTACT_NAME,
 contact.FIRST_NAME,
 contact.LAST_NAME,
invoice_main.CREATED_DATE,
user.FIRST_NAME as SALE_PERSON_NAME,
status.STATUS_ID,
 status.STATUS_DESC 
 FROM invoice_main 
 left join contact on CONTACT_ID = CONTACT_ASSOCIATION_ID
Left join user on USER_ID = SALES_USER
left join status on STATUS_ID = invoice_main.STATUS_ASSOCICATION_ID
where invoice_main.OUTLET_ASSOCICATION_ID  =outletid and invoice_main.COMPANY_ASSOCIATION_ID  = companyid 
 and ( ( fromdate IS NULL  ) OR   ( todate IS NULL  )  OR (cast(invoice_main.CREATED_DATE as date) BETWEEN cast(fromdate as date) and cast(todate as date)))
and ( invoicestatus IS NULL OR status.STATUS_DESC like  invoicestatus )
and ( (receiptRefNumber IS NULL) OR (invoice_main.INVOICE_REF_NBR like receiptRefNumber ))
and ( (customerid IS NULL) OR (invoice_main.CONTACT_ASSOCIATION_ID = customerid ))
order by invoice_main.INVOICE_MAIN_ID desc;
END */$$
DELIMITER ;

/* Procedure structure for procedure `GetAllInvoiceDetailByInvoiceLimit` */

/*!50003 DROP PROCEDURE IF EXISTS  `GetAllInvoiceDetailByInvoiceLimit` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `GetAllInvoiceDetailByInvoiceLimit`(companyid int , outletid int, fromInvoiceId int, toInvoiceDate int )
BEGIN
 
select 
  invcDetl.INVOICE_DETAIL_ID as invoiceDetailId, 
   invcDetl.PRODUCT_VARIENT_ASSOCIATION_ID  as productVariantId,
   invcDetl.PRODUCT_ASSOCIATION_ID   as productId,
   invcDetl.ITEM_RETAIL_PRICE  as itemRetailPrice,
   invcDetl.ITEM_SALE_PRICE   as itemSalePrice,
   invcDetl.ITEM_DISCOUNT_PRCT   as itemDiscountPrct,
   invcDetl.ITEM_TAX_AMOUNT   as itemTaxAmount,
   invcDetl.ITEM_ORIGNAL_AMT   as itemOrignalAmt,
   invcDetl.ITEM_NOTES   as itemNotes,
   invcDetl.INVOICE_MAIN_ASSOCICATION_ID  as invoiceMainId,
   invcDetl.PRODUCT_QUANTITY   as productQuantity,
   invcDetl.CREATED_DATE   as createdDate,
   invcDetl.LAST_UPDATED   as lastUpdated,
   invcDetl.CREATED_BY   as createdBy,
   invcDetl.UPDATED_BY  as updatedBy,
   invcDetl.COMPANY_ASSOCIATION_ID  as companyId,
   invcDetl.OUTLET_ASSOCICATION_ID  as outletId,
   invcDetl.ISRETURN  as isreturn,
   prod.PRODUCT_NAME as productName,
   prodVar.VARIANT_ATTRIBUTE_NAME as varientAttributeName
  
 from invoice_detail invcDetl
inner join product prod on prod.PRODUCT_ID = invcDetl.PRODUCT_ASSOCIATION_ID
left join product_variant prodVar on prodVar.PRODUCT_VARIANT_ID = invcDetl.PRODUCT_VARIENT_ASSOCIATION_ID
where invcDetl.COMPANY_ASSOCIATION_ID = companyid and invcDetl.OUTLET_ASSOCICATION_ID  = outletid
and invcDetl.INVOICE_MAIN_ASSOCICATION_ID between fromInvoiceId and toInvoiceDate;
END */$$
DELIMITER ;

/* Procedure structure for procedure `GetAllInvoiceDetailByOutletCompanyId` */

/*!50003 DROP PROCEDURE IF EXISTS  `GetAllInvoiceDetailByOutletCompanyId` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `GetAllInvoiceDetailByOutletCompanyId`(companyid int , outletid int)
BEGIN
 
select 
  invcDetl.INVOICE_DETAIL_ID as invoiceDetailId, 
   invcDetl.PRODUCT_VARIENT_ASSOCIATION_ID  as productVariantId,
   invcDetl.PRODUCT_ASSOCIATION_ID   as productId,
   invcDetl.ITEM_RETAIL_PRICE  as itemRetailPrice,
   invcDetl.ITEM_SALE_PRICE   as itemSalePrice,
   invcDetl.ITEM_DISCOUNT_PRCT   as itemDiscountPrct,
   invcDetl.ITEM_TAX_AMOUNT   as itemTaxAmount,
   invcDetl.ITEM_ORIGNAL_AMT   as itemOrignalAmt,
   invcDetl.ITEM_NOTES   as itemNotes,
   invcDetl.INVOICE_MAIN_ASSOCICATION_ID  as invoiceMainId,
   invcDetl.PRODUCT_QUANTITY   as productQuantity,
   invcDetl.CREATED_DATE   as createdDate,
   invcDetl.LAST_UPDATED   as lastUpdated,
   invcDetl.CREATED_BY   as createdBy,
   invcDetl.UPDATED_BY  as updatedBy,
   invcDetl.COMPANY_ASSOCIATION_ID  as companyId,
   invcDetl.OUTLET_ASSOCICATION_ID  as outletId,
   invcDetl.ISRETURN  as isreturn,
   prod.PRODUCT_NAME as productName,
   prodVar.VARIANT_ATTRIBUTE_NAME as varientAttributeName
  
 from invoice_detail invcDetl
inner join product prod on prod.PRODUCT_ID = invcDetl.PRODUCT_ASSOCIATION_ID
left join product_variant prodVar on prodVar.PRODUCT_VARIANT_ID = invcDetl.PRODUCT_VARIENT_ASSOCIATION_ID
where invcDetl.COMPANY_ASSOCIATION_ID = companyid and invcDetl.OUTLET_ASSOCICATION_ID  = outletid;
END */$$
DELIMITER ;

/* Procedure structure for procedure `GetAllReceiptsByCompanyOutletId` */

/*!50003 DROP PROCEDURE IF EXISTS  `GetAllReceiptsByCompanyOutletId` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `GetAllReceiptsByCompanyOutletId`(companyid int, outletid int)
BEGIN
 
select 
 `RECEIPT_ID` as receiptId, 
`RECEIPT_REF_NO` as receiptRefNo,
 `RECEIPT_DATE` as receiptDate, 
`RECEIPT_AMOUNT` as receiptAmount, 
`PAYMENT_TYPE_ASSOCICATION_ID` as paymentTypeId, 
`STATUS_ASSOCICATION_ID` as statusId,
 `RECEIPT_CANCELLATION_DATE` as receiptCancellationDate, 
`RECEIPT_UAF_AMT` as receiptUafAmt, 
`OUTLET_ASSOCIATION_ID` as outletId, 
`CONTACT_ASSOCIATION_ID` as contactId, 
`INVOICE_MAIN_ASSOCICATION_ID` as invoiceMainId, 
`ACTIVE_INDICATOR` as activeIndicator,
 `CREATED_DATE` as createdDate, 
`LAST_UPDATED` as lastUpdated, 
`CREATED_BY` as createdBy,
 `UPDATED_BY` as updatedBy, 
`DAILY_REGISTER_ASSOCICATION_ID` as dailyRegisterId,
 `COMPANY_ASSOCIATION_ID` as companyId
from receipt
where OUTLET_ASSOCIATION_ID = outletid and COMPANY_ASSOCIATION_ID = companyid;
END */$$
DELIMITER ;

/* Procedure structure for procedure `GetInventoryCountByCompanyOutlet` */

/*!50003 DROP PROCEDURE IF EXISTS  `GetInventoryCountByCompanyOutlet` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `GetInventoryCountByCompanyOutlet`(companyId int, outletId int)
BEGIN 
select product.PRODUCT_NAME , mpv.VARIANT_ATTRIBUTE_NAME , mpv.sku, sum(IFNULL(stock_order_detail.ORDER_PROD_QTY,0)) as Items_Received, sum(IFNULL(Items_Returned,0)) as Items_Returned, 
(sum(IFNULL(Items_Sell,0)) - sum(IFNULL(Items_Sell_Return,0))) as Items_Sold,
sum(IFNULL(Items_Available,0)) as Items_Available
from stock_order_detail
inner join stock_order on STOCK_ORDER_ID = stock_order_detail.STOCK_ORDER_ASSOCICATION_ID and stock_order.STATUS_ASSOCICATION_ID = 3 
and stock_order.STOCK_ORDER_TYPE_ASSOCICATION_ID =3
inner join product_variant mpv on stock_order_detail.PRODUCT_VARIANT_ASSOCICATION_ID = mpv.PRODUCT_VARIANT_ID
inner join product on PRODUCT_ID = mpv.PRODUCT_ASSOCICATION_ID
Left join 
( 
select product_variant.sku, sum(IFNULL(stock_order_detail.ORDER_PROD_QTY,0)) as Items_Returned  from stock_order_detail
inner join stock_order on STOCK_ORDER_ID = stock_order_detail.STOCK_ORDER_ASSOCICATION_ID and stock_order.STATUS_ASSOCICATION_ID = 3
and stock_order.STOCK_ORDER_TYPE_ASSOCICATION_ID =4
inner join product_variant on stock_order_detail.PRODUCT_VARIANT_ASSOCICATION_ID = product_variant.PRODUCT_VARIANT_ID
where   stock_order.COMPANY_ASSOCIATION_ID =companyId and stock_order.OUTLET_ASSOCICATION_ID = 1  and (stock_order.SOURCE_OUTLET_ASSOCICATION_ID= outletId OR outletId is null) 
group by product_variant.sku 
) returned
on mpv.sku = returned.sku
Left join 
( 
select product_variant.SKU, sum(IFNULL(invoice_detail.PRODUCT_QUANTITY,0)) as Items_Sell 
from invoice_main inner join invoice_detail
on  INVOICE_MAIN_ID = INVOICE_MAIN_ASSOCICATION_ID and invoice_main.STATUS_ASSOCICATION_ID <> 11
and invoice_detail.ITEM_SALE_PRICE > 0
inner join product_variant on invoice_detail.PRODUCT_VARIENT_ASSOCIATION_ID = product_variant.PRODUCT_VARIANT_ID
where   invoice_main.COMPANY_ASSOCIATION_ID = companyId and (invoice_main.OUTLET_ASSOCICATION_ID =outletId OR outletId is null)
group by product_variant.sku
) sold
on mpv.sku = sold.sku
Left join 
( 
select product_variant.SKU, sum(IFNULL(invoice_detail.PRODUCT_QUANTITY,0)) as Items_Sell_Return 
from invoice_main inner join invoice_detail
on  INVOICE_MAIN_ID = INVOICE_MAIN_ASSOCICATION_ID and invoice_main.STATUS_ASSOCICATION_ID <> 11
and invoice_detail.ITEM_SALE_PRICE < 0
inner join product_variant on invoice_detail.PRODUCT_VARIENT_ASSOCIATION_ID = product_variant.PRODUCT_VARIANT_ID
where   invoice_main.COMPANY_ASSOCIATION_ID = companyId and (invoice_main.OUTLET_ASSOCICATION_ID =outletId OR outletId is null)
group by product_variant.sku
) saleReturn
on mpv.sku = saleReturn.sku
Left join 
( 
select product_variant.sku,sum(IFNULL(product_variant.CURRENT_INVENTORY,0))as Items_Available
from product_variant
where   product_variant.COMPANY_ASSOCIATION_ID =companyId  and (product_variant.OUTLET_ASSOCICATION_ID = outletId OR outletId is null)
group by product_variant.sku
) available
on mpv.sku = available.sku
where  stock_order.COMPANY_ASSOCIATION_ID =companyId and (stock_order.OUTLET_ASSOCICATION_ID = outletId OR outletId is null)
group by mpv.sku;
END */$$
DELIMITER ;

/* Procedure structure for procedure `GetInventoryReportForOutlets` */

/*!50003 DROP PROCEDURE IF EXISTS  `GetInventoryReportForOutlets` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `GetInventoryReportForOutlets`(companyId int, outletId  int)
BEGIN
SELECT `PRODUCT_NAME`, `OUTLET_NAME`,`SKU`,`BRAND_NAME`,`CONTACT_NAME`,`PRODUCT_TYPE`,`CURRENT_INVENTORY`,
(`CURRENT_INVENTORY`*ROUND(`NET_PRICE`,0)) AS `STOCK_VALUE`,ROUND(`NET_PRICE`,0)AS `NET_PRICE`,`REORDER_POINT`, `REORDER_AMOUNT`
FROM mv_inventory_report rpt where rpt.OUTLET_ASSOCICATION_ID = outletId and rpt.COMPANY_ASSOCIATION_ID = companyId
order by ID;
END */$$
DELIMITER ;

/* Procedure structure for procedure `GetInventoryReportForWarehouse` */

/*!50003 DROP PROCEDURE IF EXISTS  `GetInventoryReportForWarehouse` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `GetInventoryReportForWarehouse`(companyId int, outletId  int)
BEGIN
SELECT `PRODUCT_NAME`, `OUTLET_NAME`,`SKU`,`BRAND_NAME`,`CONTACT_NAME`,`PRODUCT_TYPE`,`CURRENT_INVENTORY`,
(`CURRENT_INVENTORY`*ROUND(`SUPPLY_PRICE_EXCL_TAX`,0)) AS `STOCK_VALUE`,ROUND(`SUPPLY_PRICE_EXCL_TAX`,0)AS `NET_PRICE`,`REORDER_POINT`, `REORDER_AMOUNT`
FROM mv_inventory_report rpt where rpt.OUTLET_ASSOCICATION_ID = outletId and rpt.COMPANY_ASSOCIATION_ID = companyId
order by ID;
END */$$
DELIMITER ;

/* Procedure structure for procedure `GetLimitedInvoiceByOutletId` */

/*!50003 DROP PROCEDURE IF EXISTS  `GetLimitedInvoiceByOutletId` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `GetLimitedInvoiceByOutletId`(companyid int, outletid int,  lim int )
BEGIN
SET @companyid=companyid;
SET @outletid=outletid;
SET @lim=lim;
PREPARE STMT FROM "SELECT 
   invoice_main.INVOICE_MAIN_ID ,
   invoice_main.INVOICE_REF_NBR ,
   invoice_main.INVOICE_NOTES ,
   COALESCE(invoice_main.INVOICE_DISCOUNT,0) as INVOICE_DISCOUNT ,
   COALESCE(invoice_main.INVOICE_TAX,0) as INVOICE_TAX  ,
   invoice_main.INVC_TYPE_CDE  ,
   invoice_main.INVOICE_GENERATION_DTE ,
   invoice_main.INVOICE_CANCEL_DTE ,
   COALESCE(invoice_main.INVOICE_AMT,0) as INVOICE_AMT ,
   COALESCE(invoice_main.INVOICE_DISCOUNT_AMT,0) as INVOICE_DISCOUNT_AMT ,
   COALESCE(invoice_main.INVOICE_NET_AMT,0)  as INVOICE_NET_AMT,
   COALESCE(invoice_main.INVOICE_GIVEN_AMT ,0)  as INVOICE_GIVEN_AMT,
   COALESCE(invoice_main.INVOICE_ORIGNAL_AMT,0) as INVOICE_ORIGNAL_AMT ,
   COALESCE(invoice_main.SETTLED_AMT,0) as SETTLED_AMT  ,
   invoice_main.STATUS_ASSOCICATION_ID  ,
   invoice_main.ORDER_ASSOCICATION_ID  ,
   invoice_main.CONTACT_ASSOCIATION_ID  ,
   invoice_main.COMPANY_ASSOCIATION_ID  ,
   invoice_main.OUTLET_ASSOCICATION_ID  ,
   invoice_main.DAILY_REGISTER_ASSOCICATION_ID  ,
   invoice_main.PAYMENT_TYPE_ASSOCICATION_ID ,
   invoice_main.SALES_USER ,
 contact.CONTACT_ID,
 contact.CONTACT_NAME,
 contact.FIRST_NAME,
 contact.LAST_NAME,
invoice_main.CREATED_DATE,
user.FIRST_NAME as SALE_PERSON_NAME,
status.STATUS_ID,
 status.STATUS_DESC 
 FROM invoice_main 
 left join contact on CONTACT_ID = CONTACT_ASSOCIATION_ID
Left join user on USER_ID = SALES_USER
left join status on STATUS_ID = invoice_main.STATUS_ASSOCICATION_ID
where invoice_main.OUTLET_ASSOCICATION_ID  =? and invoice_main.COMPANY_ASSOCIATION_ID  = ?
order by invoice_main.INVOICE_MAIN_ID desc
Limit ?;";
EXECUTE STMT USING @outletid,@companyid, @lim;
DEALLOCATE PREPARE STMT;
END */$$
DELIMITER ;

/* Procedure structure for procedure `GetTodaySalesByProduct` */

/*!50003 DROP PROCEDURE IF EXISTS  `GetTodaySalesByProduct` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `GetTodaySalesByProduct`(companyId int, outletId  int, todayDate date)
BEGIN
  
SELECT 
         ( 
                SELECT `product`.`product_name` AS `product_name` 
                FROM   `product` 
                WHERE  ( 
                              `invoice_detail`.`product_association_id` = `product`.`product_id`)) AS `product`,
         ( 
                SELECT `outlet`.`outlet_name` AS `outlet_name` 
                FROM   `outlet` 
                WHERE  ( 
                              `invoice_main`.`outlet_assocication_id` = `outlet`.`outlet_id`)) AS `outlet`,
         cast(`invoice_main`.`INVOICE_GENERATION_DTE` AS date)                                           AS `Invoice_Generation_Date`,
         sum(((`invoice_detail`.`item_sale_price` * `invoice_detail`.`product_quantity`) - (COALESCE(`invoice_main`.`invoice_discount_amt`,0) /
         ( 
                SELECT count(`invoice_detail`.`invoice_main_assocication_id`) AS `count(``invoice_detail``.``invoice_main_assocication_id``)`
                FROM   `invoice_detail` 
                WHERE  ( 
                              `invoice_detail`.`invoice_main_assocication_id` = `invoice_main`.`invoice_main_id`))))) AS `revenue`,
         (sum(((`invoice_detail`.`item_sale_price` * `invoice_detail`.`product_quantity`) - (COALESCE(`invoice_main`.`invoice_discount_amt`,0) /
         ( 
                SELECT count(`invoice_detail`.`invoice_main_assocication_id`) AS `count(``invoice_detail``.``invoice_main_assocication_id``)`
                FROM   `invoice_detail` 
                WHERE  ( 
                              `invoice_detail`.`invoice_main_assocication_id` = `invoice_main`.`invoice_main_id`))))) + sum((`invoice_detail`.`item_tax_amount` * `invoice_detail`.`product_quantity`))) AS `revenue_tax_incl`,
         sum((`invoice_detail`.`item_orignal_amt` * `invoice_detail`.`product_quantity`))                                                                                                                AS `cost_of_goods`,
         (sum((`invoice_detail`.`item_sale_price` * `invoice_detail`.`product_quantity`)) - sum((`invoice_detail`.`item_orignal_amt` * `invoice_detail`.`product_quantity`)))                            AS `gross_profit`,
         COALESCE((100 - ((sum(`invoice_detail`.`item_orignal_amt`) / sum(`invoice_detail`.`item_sale_price`)) * 100)),0)                                                                                AS `margin`,
         sum(( 
         CASE 
                  WHEN ( 
                                    `invoice_detail`.`item_sale_price` < 0) THEN (`invoice_detail`.`product_quantity` * -(1)) 
                  ELSE `invoice_detail`.`product_quantity` 
         END))                                   AS `items_sold`, 
         sum(`invoice_detail`.`item_tax_amount`) AS `tax`, 
         `invoice_main`.`company_association_id` AS `company_association_id`, 
         `invoice_main`.`outlet_assocication_id` AS `outlet_assocication_id` 
FROM     (`invoice_detail` 
JOIN     `invoice_main` 
ON      (( 
                           `invoice_main`.`invoice_main_id` = `invoice_detail`.`invoice_main_assocication_id`)))
WHERE    ( 
                  `invoice_main`.`status_assocication_id` <> 11  and invoice_main.COMPANY_ASSOCIATION_ID =companyId and invoice_main.OUTLET_ASSOCICATION_ID = outletId 
and  cast(invoice_main.INVOICE_GENERATION_DTE AS date)  = todayDate
 ) 
GROUP BY `invoice_detail`.`product_association_id`;
END */$$
DELIMITER ;

/* Procedure structure for procedure `InventoryCountActions` */

/*!50003 DROP PROCEDURE IF EXISTS  `InventoryCountActions` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `InventoryCountActions`(inventoryCountId int, companyId int)
BEGIN
select  
icd.INVENTORY_COUNT_DETAIL_ID,  
icd.INVENTORY_COUNT_ASSOCICATION_ID,
icd.PRODUCT_VARIANT_ASSOCICATION_ID,
icd.PRODUCT_ASSOCIATION_ID, 
p.product_name,
concat(ppv.product_name, '-', pv.variant_attribute_name) VariantAttributeName,
icd.IS_PRODUCT,                       
icd.EXPECTED_PROD_QTY,                   
icd.SUPPLY_PRICE_EXP,                
icd.RETAIL_PRICE_EXP,                    
icd.COUNTED_PROD_QTY,                
icd.RETAIL_PRICE_COUNTED,
icd.SUPPLY_PRICE_COUNTED, 
icd.COUNT_DIFF,
icd.PRICE_DIFF,                    
icd.ACTIVE_INDICATOR,                 
icd.CREATED_DATE,                     
icd.LAST_UPDATED,                     
icd.CREATED_BY,                       
icd.UPDATED_BY,                       
icd.COMPANY_ASSOCIATION_ID,
icd.audit_transfer
from inventory_count_detail icd
left join product p on p.product_id = icd.PRODUCT_ASSOCIATION_ID
left join product_variant pv on pv.product_variant_id = icd.PRODUCT_VARIANT_ASSOCICATION_ID
left join product ppv on ppv.product_id = pv.product_assocication_id
where icd.INVENTORY_COUNT_ASSOCICATION_ID = inventoryCountId
and icd.COMPANY_ASSOCIATION_ID = companyId;	
END */$$
DELIMITER ;

/* Procedure structure for procedure `MessageCountManagement` */

/*!50003 DROP PROCEDURE IF EXISTS  `MessageCountManagement` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `MessageCountManagement`(companyid int)
    DETERMINISTIC
    SQL SECURITY INVOKER
BEGIN
	SET sql_mode = '';
	update message set MESSAGE_TEXT_LIMIT = ( select count(*) from message_detail where COMPANY_ASSOCIATION_ID = companyid and DELIVERY_ID <> 'Error : Incorrect Number') where COMPANY_ASSOCIATION_ID=companyid;
    END */$$
DELIMITER ;

/* Procedure structure for procedure `MVExecution` */

/*!50003 DROP PROCEDURE IF EXISTS  `MVExecution` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `MVExecution`(
    
)
    DETERMINISTIC
    SQL SECURITY INVOKER
BEGIN
	SET sql_mode = '';
	delete from mv_Temp_Sale;
	insert into mv_Temp_Sale (select * from Temp_Sale);
	delete from mv_credit_sale;
	insert into mv_credit_sale (select * from credit_sale);
	delete from mv_cash_sale;
	insert into mv_cash_sale (select * from cash_sale);
	delete from mv_Salereport_withsale;
	insert into mv_Salereport_withsale (select * from Salereport_withsale);
	delete from mv_SaleReport_withOutSale;
	insert into mv_SaleReport_withOutSale (select * from SaleReport_withOutSale);
	delete from mv_Sale_Details;
	insert into mv_Sale_Details (select * from Sale_Details);
	delete from mv_User_Sales_Report;
	insert into mv_User_Sales_Report (select * from User_Sales_Report);
	delete from mv_Outlet_Sales_Report;
	insert into mv_Outlet_Sales_Report (select * from Outlet_Sales_Report);
	delete from mv_Brand_Sales_Report;
	insert into mv_Brand_Sales_Report (select * from Brand_Sales_Report);
	delete from mv_tag_sales_report;
	insert into mv_tag_sales_report (select * from tag_sales_report);
	delete from mv_ProductType_Sales_Report;
	insert into mv_ProductType_Sales_Report (select * from ProductType_Sales_Report);
	delete from mv_Supplier_Sales_Report;
	insert into mv_Supplier_Sales_Report (select * from Supplier_Sales_Report);
	delete from mv_Customer_Sales_Report;
	insert into mv_Customer_Sales_Report (select * from Customer_Sales_Report);
	delete from mv_Payment_Report;
	insert into mv_Payment_Report (select * from Payment_Report);
	delete from mv_Customer_Group_Sales_Report;
	insert into mv_Customer_Group_Sales_Report (select * from Customer_Group_Sales_Report);
	END */$$
DELIMITER ;

/* Procedure structure for procedure `MVInventoryExecution` */

/*!50003 DROP PROCEDURE IF EXISTS  `MVInventoryExecution` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `MVInventoryExecution`(
    
)
    DETERMINISTIC
    SQL SECURITY INVOKER
BEGIN
	SET sql_mode = '';
	delete from mv_inventory_report;
	insert into mv_inventory_report (select * from inventory_report);
    END */$$
DELIMITER ;

/* Procedure structure for procedure `Pivot` */

/*!50003 DROP PROCEDURE IF EXISTS  `Pivot` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `Pivot`(
    IN tbl_name VARCHAR(100),       
    IN main_base_cols VARCHAR(500),      
    IN base_cols VARCHAR(500),      
    IN pivot_col VARCHAR(100),      
    IN tally_col VARCHAR(500),      
    IN where_clause VARCHAR(500),   
    IN order_by VARCHAR(500),        
    IN group_by VARCHAR(500)        
)
    DETERMINISTIC
    SQL SECURITY INVOKER
BEGIN
 
    
    SET @subq = CONCAT('SELECT DISTINCT ', pivot_col, ' AS val ',
                    ' FROM ', tbl_name, ' ', where_clause, ' ORDER BY 1');
    
    SET @cc1 = "CONCAT('SUM(IF(&p = ', &v, ', &t, 0)) AS ', &v)";
    SET @cc2 = REPLACE(@cc1, '&p', pivot_col);
    SET @cc3 = REPLACE(@cc2, '&t', tally_col);
    
    SET @qval = CONCAT("'\"', val, '\"'");
    
    SET @cc4 = REPLACE(@cc3, '&v', @qval);
    
    SET SESSION group_concat_max_len = 18446744073709551615;   
    SET @stmt = CONCAT(
            'SELECT  GROUP_CONCAT(', @cc4, ' SEPARATOR ",\n")  INTO @sums',
            ' FROM ( ', @subq, ' ) AS top');
    
   
    PREPARE _sql FROM @stmt;
    EXECUTE _sql;                      
    DEALLOCATE PREPARE _sql;
    
    SET @stmt2 = CONCAT(
            'SELECT ',
                main_base_cols, ',\n',
                @sums, ',\n',base_cols,
            '\n FROM ', tbl_name, ' ',
            where_clause,
            ' GROUP BY ', group_by , ' WITH ROLLUP',
            '\n', order_by
        );
    
    PREPARE _sql FROM @stmt2;
    EXECUTE _sql;                     
    DEALLOCATE PREPARE _sql;
    
    END */$$
DELIMITER ;

/* Procedure structure for procedure `Pivot_Summarize` */

/*!50003 DROP PROCEDURE IF EXISTS  `Pivot_Summarize` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `Pivot_Summarize`(
    IN tbl_name VARCHAR(100),       
    IN main_base_cols VARCHAR(500),      
    IN base_cols VARCHAR(500),      
    IN pivot_col VARCHAR(100),      
    IN tally_col VARCHAR(500),      
    IN where_clause VARCHAR(500),   
    IN order_by VARCHAR(500),        
    IN group_by VARCHAR(500),        
    IN summarized_criteria VARCHAR(100)        
)
    DETERMINISTIC
    SQL SECURITY INVOKER
BEGIN
 
    
	SET @selectFormat = '';
	
    IF summarized_criteria = 'monthly' THEN SET @selectFormat = '%b-%y';
    ELSEIF summarized_criteria = 'yearly' THEN SET @selectFormat = '%Y';
    ELSEIF summarized_criteria = 'weekly' THEN SET @selectFormat = '%e-%b-%y';
    END IF;
    IF summarized_criteria = 'weekly' 
	THEN SET @subq = CONCAT('SELECT DISTINCT ', 'DATE_FORMAT(DATE_ADD(',pivot_col,',','INTERVAL(1-DAYOFWEEK(',pivot_col,')) DAY),' ,"'", @selectFormat,"'",')'  , ' AS val ',
                    ' FROM ', tbl_name, ' ', where_clause, ' Group by ','DATE_FORMAT(',pivot_col,',',"'" , @selectFormat,"'" ,')', ' ORDER BY ',pivot_col);
    ELSEIF summarized_criteria = 'quaterly' 
	THEN 
	Set @quaterlySelectCriteria = CONCAT('CONCAT(', 'YEAR(',pivot_col,'),','''-''',',QUARTER(',pivot_col,')'  ,')');
	Set @quaterlyGroupbyCriteria = CONCAT('YEAR(',pivot_col,'),', 'QUARTER(',pivot_col,')' );
	SET @subq = CONCAT('SELECT DISTINCT ',@quaterlySelectCriteria  , ' AS val ',
                    ' FROM ', tbl_name, ' ', where_clause, ' Group by ',@quaterlyGroupbyCriteria, ' ORDER BY ',@quaterlyGroupbyCriteria);
    ELSE
     SET @subq = CONCAT('SELECT DISTINCT ', 'DATE_FORMAT(',pivot_col,',', "'", @selectFormat,"'",')'  , ' AS val ',
                    ' FROM ', tbl_name, ' ', where_clause, ' Group by ','DATE_FORMAT(',pivot_col,',',"'" , @selectFormat,"'" ,')', ' ORDER BY CREATED_DATE');
	 END IF;
   
    IF summarized_criteria = 'weekly' 
	THEN
		SET @updatedPivotColumn = CONCAT('DATE_FORMAT(DATE_ADD(',pivot_col,',','INTERVAL(1-DAYOFWEEK(',pivot_col,')) DAY),' ,"''", @selectFormat,"''",')');
	ELSEIF summarized_criteria = 'quaterly' 
	THEN
		SET @updatedPivotColumn = CONCAT('CONCAT(', 'YEAR(',pivot_col,'),',"''-''",',QUARTER(',pivot_col,')'  ,')');
    ELSE
	SET @updatedPivotColumn = CONCAT('DATE_FORMAT(',pivot_col,',', "''", @selectFormat,"''",')');
    END IF;
    
    SET @cc1 = "CONCAT('SUM(IF(&p = ', &v, ', &t, 0)) AS ', &v)";
   
    SET @cc2 = REPLACE(@cc1, '&p' ,@updatedPivotColumn );
    
    SET @cc3 = REPLACE(@cc2, '&t', tally_col);
    
    SET @qval = CONCAT("'\"', val, '\"'");
    
    SET @cc4 = REPLACE(@cc3, '&v', @qval);
    
    SET SESSION group_concat_max_len = 18446744073709551615;   
    SET @stmt = CONCAT(
            'SELECT  GROUP_CONCAT(', @cc4, ' SEPARATOR ",\n")  INTO @sums',
            ' FROM ( ', @subq, ' ) AS top');
   
    
    PREPARE _sql FROM @stmt;
    EXECUTE _sql;                      
    DEALLOCATE PREPARE _sql;
    
    SET @stmt2 = CONCAT(
            'SELECT  ',
                main_base_cols, ',\n',
                @sums, ',\n',base_cols,
            '\n FROM ', tbl_name, ' ',
            where_clause,
           ' GROUP BY ', group_by, ' WITH ROLLUP',
           
            '\n', order_by
        );
     
    PREPARE _sql FROM @stmt2;
    EXECUTE _sql;                     
    DEALLOCATE PREPARE _sql;
    
    END */$$
DELIMITER ;

/* Procedure structure for procedure `StockOrderActions` */

/*!50003 DROP PROCEDURE IF EXISTS  `StockOrderActions` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `StockOrderActions`(stockOrderId int, outletId int)
BEGIN
select  
sod.STOCK_ORDER_DETAIL_ID,  
sod.STOCK_ORDER_ASSOCICATION_ID,
sod.PRODUCT_VARIANT_ASSOCICATION_ID,
sod.PRODUCT_ASSOCIATION_ID, 
prod.product_name,
concat(prod.product_name, '-', pv.variant_attribute_name) VariantAttributeName,
0 as product_current_inventory,
prod.current_inventory as product_destination_current_inventory,
0 as product_variant_current_inventory,
pv.current_inventory as product_variant_destination_inventory,
sod.IS_PRODUCT,                       
sod.ORDER_PROD_QTY,                   
sod.ORDR_SUPPLY_PRICE,                
sod.RECV_PROD_QTY,                    
sod.RECV_SUPPLY_PRICE,                
sod.RETAIL_PRICE,                     
sod.ACTIVE_INDICATOR,                 
sod.CREATED_DATE,                     
sod.LAST_UPDATED,                     
sod.CREATED_BY,                       
sod.UPDATED_BY,                       
sod.COMPANY_ASSOCIATION_ID
from stock_order_detail sod
inner join stock_order so on so.STOCK_ORDER_ID = sod.STOCK_ORDER_ASSOCICATION_ID
inner join product_variant pv on pv.PRODUCT_VARIANT_ID =  sod.PRODUCT_VARIANT_ASSOCICATION_ID
inner join product prod on prod.PRODUCT_ID = pv.PRODUCT_ASSOCICATION_ID 
where STOCK_ORDER_ASSOCICATION_ID = stockOrderId and so.STOCK_ORDER_TYPE_ASSOCICATION_ID in ( 1,2,5) and so.OUTLET_ASSOCICATION_ID = outletId
union 
select  
sod.STOCK_ORDER_DETAIL_ID,  
sod.STOCK_ORDER_ASSOCICATION_ID,
sod.PRODUCT_VARIANT_ASSOCICATION_ID,
sod.PRODUCT_ASSOCIATION_ID, 
prod.product_name,
concat(prod.product_name, '-', pv.variant_attribute_name) VariantAttributeName,
source.Src_Product_Qty as product_current_inventory,
prod.current_inventory as product_destination_current_inventory,
source.Src_Product_Var_Qty as product_variant_current_inventory,
pv.current_inventory as product_variant_destination_inventory,
sod.IS_PRODUCT,                       
sod.ORDER_PROD_QTY,                   
sod.ORDR_SUPPLY_PRICE,                
sod.RECV_PROD_QTY,                    
sod.RECV_SUPPLY_PRICE,                
sod.RETAIL_PRICE,                     
sod.ACTIVE_INDICATOR,                 
sod.CREATED_DATE,                     
sod.LAST_UPDATED,                     
sod.CREATED_BY,                       
sod.UPDATED_BY,                       
sod.COMPANY_ASSOCIATION_ID
from stock_order_detail sod
inner join stock_order so on so.STOCK_ORDER_ID = sod.STOCK_ORDER_ASSOCICATION_ID
inner join product_variant pv on pv.PRODUCT_VARIANT_ID =  sod.PRODUCT_VARIANT_ASSOCICATION_ID
inner join product prod on prod.PRODUCT_ID = pv.PRODUCT_ASSOCICATION_ID 
left join (
select  coalesce(prod.CURRENT_INVENTORY,0) as Src_Product_Qty, coalesce(pv.CURRENT_INVENTORY,0) as Src_Product_Var_Qty , pv.PRODUCT_VARIANT_UUID 
from product prod
inner join product_variant pv on pv.PRODUCT_ASSOCICATION_ID =  prod.PRODUCT_ID 
inner join stock_order_detail sod on pv.PRODUCT_VARIANT_ID =  sod.PRODUCT_VARIANT_ASSOCICATION_ID 
inner join stock_order so on so.STOCK_ORDER_ID = sod.STOCK_ORDER_ASSOCICATION_ID 
and   STOCK_ORDER_ASSOCICATION_ID = stockOrderId
) source on source.PRODUCT_VARIANT_UUID = pv.PRODUCT_VARIANT_UUID
where STOCK_ORDER_ASSOCICATION_ID = stockOrderId and so.STOCK_ORDER_TYPE_ASSOCICATION_ID in( 3,4) and so.OUTLET_ASSOCICATION_ID = outletId ;
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
