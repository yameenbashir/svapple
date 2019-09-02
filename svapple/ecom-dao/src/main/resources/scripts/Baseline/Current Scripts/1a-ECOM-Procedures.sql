USE `ecom`;

DELIMITER $$
--
-- Procedures
--
DROP PROCEDURE IF EXISTS `GetAllInvoiceByOutletId`$$
CREATE PROCEDURE `GetAllInvoiceByOutletId`(companyid int, outletid int)
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
order by invoice_main.INVOICE_MAIN_ID desc;
END$$

DROP PROCEDURE IF EXISTS `GetAllInvoiceDetailByOutletCompanyId`$$
CREATE PROCEDURE `GetAllInvoiceDetailByOutletCompanyId`(companyid int , outletid int)
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
	
END$$

DROP PROCEDURE IF EXISTS `GetAllReceiptsByCompanyOutletId`$$
CREATE PROCEDURE `GetAllReceiptsByCompanyOutletId`(companyid int, outletid int)
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


END$$

DROP PROCEDURE IF EXISTS `Pivot`$$
CREATE PROCEDURE `Pivot`(
    IN tbl_name VARCHAR(100),       -- table name (or db.tbl)
    IN main_base_cols VARCHAR(500),      -- main base columns
    IN base_cols VARCHAR(500),      -- column(s) on the left, separated by commas
    IN pivot_col VARCHAR(100),      -- name of column to put across the top
    IN tally_col VARCHAR(500),      -- name of column to SUM up
    IN where_clause VARCHAR(500),   -- empty string or "WHERE ..."
    IN order_by VARCHAR(500),        -- empty string or "ORDER BY ..."; usually the base_cols
    IN group_by VARCHAR(500)        -- empty string or "ORDER BY ..."; usually the base_cols
)
    DETERMINISTIC
    SQL SECURITY INVOKER
BEGIN
 -- Find the distinct values
    -- Build the SUM()s
    SET @subq = CONCAT('SELECT DISTINCT ', pivot_col, ' AS val ',
                    ' FROM ', tbl_name, ' ', where_clause, ' ORDER BY 1');
    -- select @subq;
    SET @cc1 = "CONCAT('SUM(IF(&p = ', &v, ', &t, 0)) AS ', &v)";
    SET @cc2 = REPLACE(@cc1, '&p', pivot_col);
    SET @cc3 = REPLACE(@cc2, '&t', tally_col);
    -- select @cc2, @cc3;
    SET @qval = CONCAT("'\"', val, '\"'");
    -- select @qval;
    SET @cc4 = REPLACE(@cc3, '&v', @qval);
    -- select @cc4;
    SET SESSION group_concat_max_len = 10000;   -- just in case
    SET @stmt = CONCAT(
            'SELECT  GROUP_CONCAT(', @cc4, ' SEPARATOR ",\n")  INTO @sums',
            ' FROM ( ', @subq, ' ) AS top');
    -- select @stmt;
   -- select @sums;
    PREPARE _sql FROM @stmt;
    EXECUTE _sql;                      -- Intermediate step: build SQL for columns
    DEALLOCATE PREPARE _sql;
    -- Construct the query and perform it
    SET @stmt2 = CONCAT(
            'SELECT ',
                main_base_cols, ',\n',
                @sums, ',\n',base_cols,
            '\n FROM ', tbl_name, ' ',
            where_clause,
            ' GROUP BY ', group_by , ' WITH ROLLUP',
            '\n', order_by
        );
    --  select @stmt2;                    -- The statement that generates the result
    PREPARE _sql FROM @stmt2;
    EXECUTE _sql;                     -- The resulting pivot table ouput
    DEALLOCATE PREPARE _sql;
    -- For debugging / tweaking, SELECT the various @variables after CALLing.
    END$$

DROP PROCEDURE IF EXISTS `Pivot_Summarize`$$
CREATE PROCEDURE `Pivot_Summarize`(
    IN tbl_name VARCHAR(100),       -- table name (or db.tbl)
    IN main_base_cols VARCHAR(500),      -- main base columns
    IN base_cols VARCHAR(500),      -- column(s) on the left, separated by commas
    IN pivot_col VARCHAR(100),      -- name of column to put across the top
    IN tally_col VARCHAR(500),      -- name of column to SUM up
    IN where_clause VARCHAR(500),   -- empty string or "WHERE ..."
    IN order_by VARCHAR(500),        -- empty string or "ORDER BY ..."; usually the base_cols
    IN group_by VARCHAR(500),        -- empty string or "group BY ..."; usually the base_cols
    IN summarized_criteria VARCHAR(100)        -- summarized criteria e.g. "weekly" ,"monthly", "yearly"
)
    DETERMINISTIC
    SQL SECURITY INVOKER
BEGIN
 -- Find the distinct values
    -- Build the SUM()s
	SET @selectFormat = '';
	-- SET @groupbyFormat = '';
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
                    ' FROM ', tbl_name, ' ', where_clause, ' Group by ','DATE_FORMAT(',pivot_col,',',"'" , @selectFormat,"'" ,')', ' ORDER BY 1');
	 END IF;
   -- select @subq;
    IF summarized_criteria = 'weekly' 
	THEN
		SET @updatedPivotColumn = CONCAT('DATE_FORMAT(DATE_ADD(',pivot_col,',','INTERVAL(1-DAYOFWEEK(',pivot_col,')) DAY),' ,"''", @selectFormat,"''",')');
	ELSEIF summarized_criteria = 'quaterly' 
	THEN
		SET @updatedPivotColumn = CONCAT('CONCAT(', 'YEAR(',pivot_col,'),',"''-''",',QUARTER(',pivot_col,')'  ,')');
    ELSE
	SET @updatedPivotColumn = CONCAT('DATE_FORMAT(',pivot_col,',', "''", @selectFormat,"''",')');
    END IF;
    -- select @updatedPivotColumn;
    SET @cc1 = "CONCAT('SUM(IF(&p = ', &v, ', &t, 0)) AS ', &v)";
   -- select @cc1;
    SET @cc2 = REPLACE(@cc1, '&p' ,@updatedPivotColumn );
    -- select @cc1, @cc2;
    SET @cc3 = REPLACE(@cc2, '&t', tally_col);
    -- select @cc2, @cc3;
    SET @qval = CONCAT("'\"', val, '\"'");
    -- select @qval;
    SET @cc4 = REPLACE(@cc3, '&v', @qval);
    -- select @cc4;
    SET SESSION group_concat_max_len = 10000;   -- just in case
    SET @stmt = CONCAT(
            'SELECT  GROUP_CONCAT(', @cc4, ' SEPARATOR ",\n")  INTO @sums',
            ' FROM ( ', @subq, ' ) AS top');
   -- select @stmt;
    -- select @sums;
    PREPARE _sql FROM @stmt;
    EXECUTE _sql;                      -- Intermediate step: build SQL for columns
    DEALLOCATE PREPARE _sql;
    -- Construct the query and perform it
    SET @stmt2 = CONCAT(
            'SELECT  ',
                main_base_cols, ',\n',
                @sums, ',\n',base_cols,
            '\n FROM ', tbl_name, ' ',
            where_clause,
           ' GROUP BY ', group_by, ' WITH ROLLUP',
           -- ' GROUP BY ', group_by, ',',@updatedPivotColumn , ' WITH ROLLUP',
            '\n', order_by
        );
   --  select @stmt2;                    -- The statement that generates the result
    PREPARE _sql FROM @stmt2;
    EXECUTE _sql;                     -- The resulting pivot table ouput
    DEALLOCATE PREPARE _sql;
    -- For debugging / tweaking, SELECT the various @variables after CALLing.
    END$$

DELIMITER ;
DELIMITER $$

DROP PROCEDURE IF EXISTS `ecom`.`GetLimitedInvoiceByOutletId`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetLimitedInvoiceByOutletId`(companyid int, outletid int,  lim int )
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
EXECUTE STMT USING @companyid, @outletid, @lim;
DEALLOCATE PREPARE STMT;
END$$

DELIMITER ;



-- ----------------------------------


DELIMITER $$

DROP PROCEDURE IF EXISTS `ecom`.`GetAllInvoiceByOutletId`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetAllInvoiceByOutletId`(companyid int, outletid int, receiptRefNumber varchar(50), invoicestatus varchar(50),
fromdate date, todate date
)
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
order by invoice_main.INVOICE_MAIN_ID desc;
END$$

DELIMITER ;


-- --------------------

DELIMITER $$

DROP PROCEDURE IF EXISTS `ecom`.`GetAllInvoiceDetailByInvoiceLimit`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetAllInvoiceDetailByInvoiceLimit`(companyid int , outletid int, fromInvoiceId int, toInvoiceDate int )
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
	
END$$

DELIMITER ;




DELIMITER $$

DROP PROCEDURE IF EXISTS `ecom`.`GetLimitedInvoiceByOutletId`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetLimitedInvoiceByOutletId`(companyid int, outletid int,  lim int )
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
END$$

DELIMITER ;
use ecom;
DELIMITER $$

DROP VIEW IF EXISTS `ecom`.`salereport_withoutsale`$$

CREATE  SQL SECURITY DEFINER VIEW `salereport_withoutsale` AS 
select (select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` 
where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,
(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,
cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,


sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0)/
(select count(INVOICE_MAIN_ASSOCICATION_ID) from invoice_detail where invoice_detail.INVOICE_MAIN_ASSOCICATION_ID = invoice_main.INVOICE_MAIN_ID))
)) AS `Revenue`,

(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) /
(select count(INVOICE_MAIN_ASSOCICATION_ID) from invoice_detail where invoice_detail.INVOICE_MAIN_ASSOCICATION_ID = invoice_main.INVOICE_MAIN_ID))
)) +
 sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,

sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where ((`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) and (`invoice_detail`.`ITEM_DISCOUNT_PRCT` = 0) and (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) and (`invoice_main`.`INVOICE_DISCOUNT` = 0 or `invoice_main`.`INVOICE_DISCOUNT` is null )) group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date)$$

DELIMITER ;

DELIMITER $$

DROP VIEW IF EXISTS `ecom`.`salereport_withsale`$$

CREATE  SQL SECURITY DEFINER VIEW `salereport_withsale` AS 

select (select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` 
where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,
(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,

cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,


sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0)/
(select count(INVOICE_MAIN_ASSOCICATION_ID) from invoice_detail where invoice_detail.INVOICE_MAIN_ASSOCICATION_ID = invoice_main.INVOICE_MAIN_ID))
)) AS `Revenue`,

(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) /
(select count(INVOICE_MAIN_ASSOCICATION_ID) from invoice_detail where invoice_detail.INVOICE_MAIN_ASSOCICATION_ID = invoice_main.INVOICE_MAIN_ID))
)) +
 sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,
 
sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where ((`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) and (`invoice_detail`.`ITEM_DISCOUNT_PRCT` > 0) or (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) and (`invoice_main`.`INVOICE_DISCOUNT` > 0) ) group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date)$$

DELIMITER ;



DELIMITER $$

DROP PROCEDURE IF EXISTS `ecom`.`GetInventoryCountByCompanyOutlet`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetInventoryCountByCompanyOutlet`(companyId int, outletId int)
BEGIN 



-- received--

select product.PRODUCT_NAME , mpv.VARIANT_ATTRIBUTE_NAME , mpv.sku, sum(IFNULL(stock_order_detail.ORDER_PROD_QTY,0)) as Items_Received, sum(IFNULL(Items_Returned,0)) as Items_Returned, 

(sum(IFNULL(Items_Sell,0)) - sum(IFNULL(Items_Sell_Return,0))) as Items_Sold,

sum(IFNULL(Items_Available,0)) as Items_Available

from stock_order_detail
inner join stock_order on STOCK_ORDER_ID = stock_order_detail.STOCK_ORDER_ASSOCICATION_ID and stock_order.STATUS_ASSOCICATION_ID = 3 
and stock_order.STOCK_ORDER_TYPE_ASSOCICATION_ID =3
inner join product_variant mpv on stock_order_detail.PRODUCT_VARIANT_ASSOCICATION_ID = mpv.PRODUCT_VARIANT_ID
inner join product on PRODUCT_ID = mpv.PRODUCT_ASSOCICATION_ID

Left join -- returned to whare house
( 
select product_variant.sku, sum(IFNULL(stock_order_detail.ORDER_PROD_QTY,0)) as Items_Returned  from stock_order_detail
inner join stock_order on STOCK_ORDER_ID = stock_order_detail.STOCK_ORDER_ASSOCICATION_ID and stock_order.STATUS_ASSOCICATION_ID = 3
and stock_order.STOCK_ORDER_TYPE_ASSOCICATION_ID =4
inner join product_variant on stock_order_detail.PRODUCT_VARIANT_ASSOCICATION_ID = product_variant.PRODUCT_VARIANT_ID
where   stock_order.COMPANY_ASSOCIATION_ID =companyId and stock_order.OUTLET_ASSOCICATION_ID = 1  and (stock_order.SOURCE_OUTLET_ASSOCICATION_ID= outletId OR outletId is null) -- 1 is wharehouse
group by product_variant.sku 
) returned
on mpv.sku = returned.sku


Left join -- Sold
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
Left join -- Sold Return
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

Left join -- Available
( 
select product_variant.sku,sum(IFNULL(product_variant.CURRENT_INVENTORY,0))as Items_Available
from product_variant
where   product_variant.COMPANY_ASSOCIATION_ID =companyId  and (product_variant.OUTLET_ASSOCICATION_ID = outletId OR outletId is null)
group by product_variant.sku
) available
on mpv.sku = available.sku

where  stock_order.COMPANY_ASSOCIATION_ID =companyId and (stock_order.OUTLET_ASSOCICATION_ID = outletId OR outletId is null)
group by mpv.sku;

END$$

DELIMITER ;