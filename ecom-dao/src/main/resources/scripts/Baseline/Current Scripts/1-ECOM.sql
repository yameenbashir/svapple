-- phpMyAdmin SQL Dump
-- version 4.0.10.12
-- http://www.phpmyadmin.net
--
-- Host: 127.6.205.130:3306
-- Generation Time: Dec 19, 2017 at 02:14 PM
-- Server version: 5.5.52
-- PHP Version: 5.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
DROP DATABASE IF EXISTS `ecom`;
--
-- Database: `ecom`
--
CREATE DATABASE IF NOT EXISTS `ecom` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
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

-- --------------------------------------------------------

--
-- Table structure for table `account_type`
--

DROP TABLE IF EXISTS `account_type`;
CREATE TABLE IF NOT EXISTS `account_type` (
  `ACCOUNT_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ACCOUNT_TYPE_NAME` varchar(256) NOT NULL,
  `MAIN_ACCOUNT_TYPE_INDICATOR` bit(1) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`ACCOUNT_TYPE_ID`),
  KEY `ACCOUNT_TYPE_CREATED_BY_FK` (`CREATED_BY`),
  KEY `ACCOUNT_TYPE_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `ACCOUNT_TYPE_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `activity_detail`
--

DROP TABLE IF EXISTS `activity_detail`;
CREATE TABLE IF NOT EXISTS `activity_detail` (
  `ACTIVITY_DETAIL_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMPLOYEE_ASSOCIATION_ID` int(11) NOT NULL,
  `EMPLOYEE_NAME` varchar(256) NOT NULL,
  `EMLOYEE_EMAIL` varchar(256) NOT NULL,
  `CREATED_BY_MANAGER_ID` int(11) NOT NULL,
  `activity_detail` varchar(256) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `IP_ADDRESS` varchar(256) NOT NULL,
  `BROWSER_NAME` varchar(256) NOT NULL,
  `BROWSER_VERSION` varchar(256) NOT NULL,
  `OPERATING_SYSTEM` varchar(256) NOT NULL,
  `DEVICE_TYPE` varchar(256) NOT NULL,
  `SESSION_ID` varchar(256) NOT NULL,
  `SEVERITY_ASSOCIATION_ID` int(11) NOT NULL,
  `OTHER_INFORMATION` blob NOT NULL,
  `IS_EXCEPTION` varchar(10) NOT NULL DEFAULT 'false',
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`ACTIVITY_DETAIL_ID`),
  KEY `ACTIVITY_DETAIL_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `ACTIVITY_DETAIL_EMPLOYEE_ASSOCIATION_ID_FK` (`EMPLOYEE_ASSOCIATION_ID`),
  KEY `ACTIVITY_DETAIL_CREATTED_BY_MANAGER_ID_FK` (`CREATED_BY_MANAGER_ID`),
  KEY `ACTIVITY_DETAIL_SEVERITY_ASSOCIATION_ID_FK` (`SEVERITY_ASSOCIATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
CREATE TABLE IF NOT EXISTS `address` (
  `ADDRESS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ADDRESS_TYPE` varchar(100) NOT NULL,
  `CONTACT_NAME` varchar(256) DEFAULT NULL,
  `FIRST_NAME` varchar(256) DEFAULT NULL,
  `LAST_NAME` varchar(256) DEFAULT NULL,
  `EMAIL` varchar(256) DEFAULT NULL,
  `PHONE` varchar(256) DEFAULT NULL,
  `FAX` varchar(45) DEFAULT NULL,
  `WEBSITE` varchar(256) DEFAULT NULL,
  `TWITTER` varchar(256) DEFAULT NULL,
  `STREET` varchar(256) DEFAULT NULL,
  `SUBURB` varchar(256) DEFAULT NULL,
  `CITY` varchar(256) DEFAULT NULL,
  `POSTAL_CODE` varchar(256) DEFAULT NULL,
  `STATE` varchar(256) DEFAULT NULL,
  `COUNTY` varchar(256) DEFAULT NULL,
  `X_COORDINATE` varchar(256) DEFAULT '0',
  `Y_COORDINATE` varchar(256) DEFAULT '0',
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `CONTACT_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `COUNTRY_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`ADDRESS_ID`),
  KEY `ADDRESS_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `ADDRESS_COUNTRY_ASSOCICATION_ID_FK` (`COUNTRY_ASSOCICATION_ID`),
  KEY `ADDRESS_CONTACT_ASSOCICATION_ID_FK` (`CONTACT_ASSOCICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `announcement`
--

DROP TABLE IF EXISTS `announcement`;
CREATE TABLE IF NOT EXISTS `announcement` (
  `ANNOUNCEMENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(256) NOT NULL,
  `ANNOUNCEMENT_DETAIL` blob NOT NULL,
  `ANNOUNCEMENT_DATE` date NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`ANNOUNCEMENT_ID`),
  KEY `ANNOUNCEMENT_CREATED_BY_FK` (`CREATED_BY`),
  KEY `ANNOUNCEMENT_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `ANNOUNCEMENT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `bank_account`
--

DROP TABLE IF EXISTS `bank_account`;
CREATE TABLE IF NOT EXISTS `bank_account` (
  `BANK_ACCOUNT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `BANK_NAME` varchar(256) NOT NULL,
  `ACCOUNT_NAME` varchar(256) NOT NULL,
  `ACCOUNT_TYPE` varchar(256) NOT NULL,
  `ACCOUNT_NUMBER` varchar(256) DEFAULT NULL,
  `CREDIT_CARD_NUMBER` varchar(256) DEFAULT NULL,
  `CURRENCY_ASSOCICATION_ID` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`BANK_ACCOUNT_ID`),
  KEY `BANK_ACCOUNT_CREATED_BY_FK` (`CREATED_BY`),
  KEY `BANK_ACCOUNT_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `BANK_ACCOUNT_CURRENCY_ASSOCICATION_ID_FK` (`CURRENCY_ASSOCICATION_ID`),
  KEY `BANK_ACCOUNT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
CREATE TABLE IF NOT EXISTS `brand` (
  `BRAND_ID` int(11) NOT NULL AUTO_INCREMENT,
  `BRAND_NAME` varchar(200) NOT NULL,
  `BRAND_DESCRIPTION` varchar(500) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`BRAND_ID`),
  KEY `BRAND_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------


-- --------------------------------------------------------


-- --------------------------------------------------------

--
-- Table structure for table `cash_managment`
--

DROP TABLE IF EXISTS `cash_managment`;
CREATE TABLE IF NOT EXISTS `cash_managment` (
  `CASH_MANAGMENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CASH_AMT` decimal(20,2) DEFAULT NULL,
  `CASH_MANAGMENT_NOTES` varchar(256) DEFAULT NULL,
  `CASH_MANAGMENT_TYPE` varchar(10) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `DAILY_REGISTER_ASSOCICATION_ID` int(11) NOT NULL,
  `EXPENSE_TYPE_ASSOCIATION_ID` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`CASH_MANAGMENT_ID`),
  KEY `CASH_MANAGMENT_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `CASH_MANAGMENT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `CASH_MANAGMENT_DAILY_REGISTER_ASSOCICATION_ID_FK` (`DAILY_REGISTER_ASSOCICATION_ID`),
  KEY `EXPENSE_TYPE_ASSOCIATION_ID_FK` (`EXPENSE_TYPE_ASSOCIATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

-- --------------------------------------------------------

--
-- Table structure for table `chart_of_account`
--

DROP TABLE IF EXISTS `chart_of_account`;
CREATE TABLE IF NOT EXISTS `chart_of_account` (
  `CHART_OF_ACCOUNT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CHART_OF_ACCOUNT_CODE` varchar(256) NOT NULL,
  `CHART_OF_ACCOUNT_NAME` varchar(256) NOT NULL,
  `ACCOUNT_TYPE_ASSOCIATION_ID` int(11) NOT NULL,
  `SALES_TAX_ASSOCIATION_ID` int(11) NOT NULL,
  `DESCRIPTION` varchar(256) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`CHART_OF_ACCOUNT_ID`),
  KEY `CHART_OF_ACCOUNT_CREATED_BY_FK` (`CREATED_BY`),
  KEY `CHART_OF_ACCOUNT_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `CHART_OF_ACCOUNT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `CHART_OF_ACCOUNT_ACCOUNT_TYPE_ASSOCIATION_ID_FK` (`ACCOUNT_TYPE_ASSOCIATION_ID`),
  KEY `CHART_OF_ACCOUNT_SALES_TAX_ASSOCIATION_ID_FK` (`SALES_TAX_ASSOCIATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
CREATE TABLE IF NOT EXISTS `company` (
  `COMPANY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMPANY_NAME` varchar(256) NOT NULL,
  `DISPLAY_PRICES` varchar(256) DEFAULT NULL,
  `SKU_GENERATION` varchar(256) DEFAULT NULL,
  `CURRENT_SEQUENCE_NUMBER` varchar(256) DEFAULT NULL,
  `LOYALTY_ENABLED` bit(1) DEFAULT NULL,
  `LOYALTY_INVOICE_AMOUNT` decimal(20,2) DEFAULT NULL,
  `LOYALTY_AMOUNT` decimal(20,2) DEFAULT NULL,
  `LOYALTY_ENABLED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LOYALTY_BONUS_AMOUNT` decimal(20,2) DEFAULT NULL,
  `LOYALTY_BONUS_ENABLED` bit(1) DEFAULT NULL,
  `LOYALTY_BONUS_ENABLED_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `USER_SWITCH_SECURITY` varchar(256) DEFAULT NULL,
  `ENABLE_STORES_CREDIT` varchar(256) NOT NULL,
  `URL` varchar(100) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) DEFAULT b'1',
  `CREATED_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `PRINTER_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `CURRENCY_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `TIME_ZONE_ASSOCICATION_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`COMPANY_ID`),
  KEY `COMPANY_CURRENCY_ASSOCICATION_ID_FK` (`CURRENCY_ASSOCICATION_ID`),
  KEY `COMPANY_PRINTER_ASSOCICATION_ID_FK` (`PRINTER_ASSOCICATION_ID`),
  KEY `COMPANY_TIME_ZONE_ASSOCICATION_ID_FK` (`TIME_ZONE_ASSOCICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `composite_product`
--

DROP TABLE IF EXISTS `composite_product`;
CREATE TABLE IF NOT EXISTS `composite_product` (
  `COMPOSITE_PRODUCT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMPOSITE_PRODUCT_UUID` varchar(500) NOT NULL,
  `PRODUCT_UUID` varchar(500) NOT NULL,
  `COMPOSITE_QUANTITY` int(11) NOT NULL,
  `PRODUCT_ASSOCICATION_ID` int(11) NOT NULL,
  `SELECTIVE_PRODUCT_ASSOCIATION_ID` int(11) NOT NULL,
  `PRODUCT_VARIANT_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`COMPOSITE_PRODUCT_ID`),
  KEY `COMPOSITE_PRODUCT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `COMPOSITE_PRODUCT_CREATED_BY_FK` (`CREATED_BY`),
  KEY `COMPOSITE_PRODUCT_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `COMPOSITE_PRODUCT_PRODUCT_ASSOCICATION_ID_FK` (`PRODUCT_ASSOCICATION_ID`),
  KEY `COMPOSITE_PRODUCT_SELECTIVE_PRODUCT_ASSOCIATION_ID_FK` (`SELECTIVE_PRODUCT_ASSOCIATION_ID`),
  KEY `COMPOSITE_PRODUCT_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `COMPOSITE_PRODUCT_PRODUCT_VARIANT_ASSOCICATION_ID_FK` (`PRODUCT_VARIANT_ASSOCICATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
CREATE TABLE IF NOT EXISTS `configuration` (
  `CONFIGURATION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PROPERTY_NAME` varchar(256) NOT NULL,
  `PROPERTY_VALUE` varchar(256) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`CONFIGURATION_ID`),
  KEY `CONFIGURATION_CREATED_BY_FK` (`CREATED_BY`),
  KEY `CONFIGURATION_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `CONFIGURATION_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
CREATE TABLE IF NOT EXISTS `contact` (
  `CONTACT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CONTACT_NAME` varchar(200) NOT NULL,
  `DOB` date DEFAULT NULL,
  `GENDER` varchar(10) DEFAULT NULL,
  `CONTACT_CODE` varchar(200) DEFAULT NULL,
  `FIRST_NAME` varchar(200) DEFAULT NULL,
  `LAST_NAME` varchar(200) DEFAULT NULL,
  `DEFAULT_MARKUP` decimal(5,2) DEFAULT NULL,
  `CONTACT_BALANCE` decimal(20,5) DEFAULT NULL,
  `DESCRIPTION` varchar(1000) DEFAULT NULL,
  `LOYALTY_ENABLED` varchar(10) DEFAULT NULL,
  `COMPANY_NAME` varchar(100) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CONTACT_OUTLET_ID` int(11) DEFAULT NULL,
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `CONTACT_GROUP_ASSOCIATION_ID` int(11) DEFAULT NULL,
  `CONTACT_TYPE` varchar(250) NOT NULL DEFAULT 'SUPPLIER',
  `OUTLET_ASSOCIATION_ID` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`CONTACT_ID`),
  KEY `CONTACT_CONTACT_GROUP_ASSOCIATION_ID_FK` (`CONTACT_GROUP_ASSOCIATION_ID`),
  KEY `CONTACT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `CONTACT_OUTLET_ASSOCIATION_ID_FK` (`OUTLET_ASSOCIATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `contacts_summmary`
--
DROP VIEW IF EXISTS `contacts_summmary`;
CREATE TABLE IF NOT EXISTS `contacts_summmary` (
`CONTACT_ID` int(11)
,`CONTACT_NAME` varchar(401)
,`FIRST_NAME` varchar(200)
,`LAST_NAME` varchar(200)
,`COMPANY_NAME` varchar(100)
,`BALANCE` decimal(20,5)
,`PHONE` varchar(256)
,`CREATED_DATE` timestamp
,`ACTIVE_INDICATOR` bit(1)
,`OUTLET_ASSOCIATION_ID` int(11)
,`COMPANY_ASSOCIATION_ID` int(11)
,`LAST_UPDATED` timestamp
,`CONTACT_GROUP` varchar(200)
,`CONTACT_TYPE` varchar(250)
);
-- --------------------------------------------------------

--
-- Table structure for table `contact_group`
--

DROP TABLE IF EXISTS `contact_group`;
CREATE TABLE IF NOT EXISTS `contact_group` (
  `CONTACT_GROUP_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CONTACT_GROUP_NAME` varchar(200) NOT NULL,
  `COUNTRY_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`CONTACT_GROUP_ID`),
  KEY `CONTACT_GROUP_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `CONTACT_GROUP_COUNTRY_ASSOCICATION_ID_FK` (`COUNTRY_ASSOCICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `contact_payments`
--

DROP TABLE IF EXISTS `contact_payments`;
CREATE TABLE IF NOT EXISTS `contact_payments` (
  `CONTACT_PAYMENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CONTACT_NAME` varchar(200) NOT NULL,
  `PAYMENT_AMOUNT` decimal(20,5) DEFAULT NULL,
  `CONTACT_BALANCE` decimal(20,5) DEFAULT NULL,
  `PAYMENT_CASH` decimal(20,5) DEFAULT NULL,
  `PAYMENT_CREDIT_CARD` decimal(20,5) DEFAULT NULL,
  `PAYMENT_OTHER_TYPE` decimal(20,5) DEFAULT NULL,
  `CONTACT_NEW_BALANCE` decimal(20,5) DEFAULT NULL,
  `DESCRIPTION` varchar(1000) DEFAULT NULL,
  `PAYMENT_REF_NUM` varchar(100) DEFAULT NULL,
  `ORDER_REF_NUM` varchar(100) DEFAULT NULL,
  `ADJUSTMENT_TYPE` varchar(100) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `CONTACT_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `CONTACT_PAYMENT_TYPE_ASSOCICATION_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`CONTACT_PAYMENT_ID`),
  KEY `CONTACT_PAYMENT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `CONTACT_PAYMENT_CONTACT_ASSOCICATION_ID_FK` (`CONTACT_ASSOCICATION_ID`),
  KEY `CONTACT_PAYMENT_TYPE_ASSOCICATION_ID_FK` (`CONTACT_PAYMENT_TYPE_ASSOCICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `contact_payments_type`
--

DROP TABLE IF EXISTS `contact_payments_type`;
CREATE TABLE IF NOT EXISTS `contact_payments_type` (
  `CONTACT_PAYMENT_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CONTACT_PAYMENT_TYPE_NAME` varchar(200) NOT NULL,
  PRIMARY KEY (`CONTACT_PAYMENT_TYPE_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `contact_us`
--

DROP TABLE IF EXISTS `contact_us`;
CREATE TABLE IF NOT EXISTS `contact_us` (
  `CONTACT_US_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(256) NOT NULL,
  `EMAIL` varchar(256) NOT NULL,
  `MESSAGE` varchar(256) DEFAULT NULL,
  `CONTACT_NUMBER` varchar(256) DEFAULT NULL,
  `REQUEST_STATUS` varchar(256) NOT NULL,
  `WEB_ACTIVITY_DETAIL_ID` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`CONTACT_US_ID`),
  KEY `CONTACT_US_WEB_ACTIVITY_DETAIL_ID_FK` (`WEB_ACTIVITY_DETAIL_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
CREATE TABLE IF NOT EXISTS `country` (
  `COUNTRY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `COUNTRY_NAME` varchar(200) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  PRIMARY KEY (`COUNTRY_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `currency`
--

DROP TABLE IF EXISTS `currency`;
CREATE TABLE IF NOT EXISTS `currency` (
  `CURRENCY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CURRENCY_NAME` varchar(256) NOT NULL,
  `CURRENCY_VALUE` varchar(256) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  PRIMARY KEY (`CURRENCY_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- --------------------------------------------------------

--
-- Table structure for table `daily_register`
--

DROP TABLE IF EXISTS `daily_register`;
CREATE TABLE IF NOT EXISTS `daily_register` (
  `DAILY_REGISTER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CREDIT_CARD_AMT_ACTUAL` decimal(20,2) DEFAULT NULL,
  `CREDIT_CARD_AMT_EXPECTED` decimal(20,2) DEFAULT NULL,
  `CREDIT_CARD_AMT_DIFFERENCE` decimal(20,2) DEFAULT NULL,
  `CASH_AMT_ACTUAL` decimal(20,2) DEFAULT NULL,
  `CASH_AMT_EXPECTED` decimal(20,2) DEFAULT NULL,
  `CASH_AMT_DIFFERENCE` decimal(20,2) DEFAULT NULL,
  `STORE_CREDIT_AMT_ACTUAL` decimal(20,2) DEFAULT NULL,
  `STORE_CREDIT_AMT_EXPECTED` decimal(20,2) DEFAULT NULL,
  `STORE_CREDIT_AMT_DIFFERENCE` decimal(20,2) DEFAULT NULL,
  `ACTUAL_AMT` decimal(20,2) DEFAULT NULL,
  `REGISTER_OPENING_NOTES` varchar(256) DEFAULT NULL,
  `REGISTER_CLOSING_NOTES` varchar(256) DEFAULT NULL,
  `REGISTER_ASSOCICATION_ID` int(11) NOT NULL,
  `STATUS_ASSOCICATION_ID` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CLOSED_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  PRIMARY KEY (`DAILY_REGISTER_ID`),
  KEY `DAILY_REGISTER_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `DAILY_REGISTER_STATUS_ASSOCICATION_ID_FK` (`STATUS_ASSOCICATION_ID`),
  KEY `DAILY_REGISTER_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `DAILY_REGISTER_REGISTER_ASSOCICATION_ID_FK` (`REGISTER_ASSOCICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `delivery_method`
--

DROP TABLE IF EXISTS `delivery_method`;
CREATE TABLE IF NOT EXISTS `delivery_method` (
  `DELIVERY_METHOD_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DELIVERY_METHOD_NAME` varchar(100) NOT NULL,
  `SHIPPING_RATE` decimal(5,2) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  PRIMARY KEY (`DELIVERY_METHOD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `expenseReport`
--
DROP VIEW IF EXISTS `expenseReport`;
CREATE TABLE IF NOT EXISTS `expenseReport` (
`EXPENSE` varchar(200)
,`User` text
,`Outlet` varchar(100)
,`CREATED_DATE` date
,`CASH_AMT` decimal(42,2)
,`COMPANY_ASSOCIATION_ID` int(11)
,`OUTLET_ASSOCICATION_ID` int(11)
,`Type` varchar(10)
);
-- --------------------------------------------------------

--
-- Table structure for table `expense_type`
--

DROP TABLE IF EXISTS `expense_type`;
CREATE TABLE IF NOT EXISTS `expense_type` (
  `EXPENSE_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EXPENSE_TYPE_NAME` varchar(200) NOT NULL,
  `COUNTRY_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`EXPENSE_TYPE_ID`),
  KEY `EXPENSE_TYPE_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `general_ledger`
--

DROP TABLE IF EXISTS `general_ledger`;
CREATE TABLE IF NOT EXISTS `general_ledger` (
  `GENERAL_LEDGER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `REFERENCE_ID` int(11) NOT NULL,
  `MODULE_ASSOCIATION_ID` int(11) NOT NULL,
  `GL_EVENT_ASSOCIATION_ID` int(11) NOT NULL,
  `GL_SUB_EVENT_ASSOCIATION_ID` int(11) NOT NULL,
  `CHART_OF_ACCOUNT_ASSOCIATION_ID` int(11) NOT NULL,
  `DEBIT_AMOUNT` decimal(21,5) DEFAULT NULL,
  `CREDIT_AMOUNT` decimal(21,5) DEFAULT NULL,
  `REMARKS` varchar(250) DEFAULT NULL,
  `BALANCE_AMOUNT` decimal(21,5) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`GENERAL_LEDGER_ID`),
  KEY `GENERAL_LEDGER_CREATED_BY_FK` (`CREATED_BY`),
  KEY `GENERAL_LEDGER_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `GENERAL_LEDGER_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `GENERAL_LEDGER_MODULE_ASSOCIATION_ID_FK` (`MODULE_ASSOCIATION_ID`),
  KEY `GENERAL_LEDGER_GL_EVENT_ASSOCIATION_ID_FK` (`GL_EVENT_ASSOCIATION_ID`),
  KEY `GENERAL_LEDGER_GL_SUB_EVENT_ASSOCIATION_ID_FK` (`GL_SUB_EVENT_ASSOCIATION_ID`),
  KEY `GENERAL_LEDGER_CHART_OF_ACCOUNT_ASSOCIATION_ID_FK` (`CHART_OF_ACCOUNT_ASSOCIATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `general_ledger_event`
--

DROP TABLE IF EXISTS `general_ledger_event`;
CREATE TABLE IF NOT EXISTS `general_ledger_event` (
  `GENERAL_LEDGER_EVENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `GENERAL_LEDGER_EVENT_CODE` varchar(256) NOT NULL,
  `GENERAL_LEDGER_EVENT_NAME` varchar(256) NOT NULL,
  `GENERAL_LEDGER_MAIN_EVENT_INDICATOR` bit(1) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`GENERAL_LEDGER_EVENT_ID`),
  KEY `GENERAL_LEDGER_EVENT_CREATED_BY_FK` (`CREATED_BY`),
  KEY `GENERAL_LEDGER_EVENT_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `GENERAL_LEDGER_EVENT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `gl_trans_config`
--

DROP TABLE IF EXISTS `gl_trans_config`;
CREATE TABLE IF NOT EXISTS `gl_trans_config` (
  `GL_TRANS_CONFIG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `GL_EVENT_ASSOCIATION_ID` int(11) NOT NULL,
  `GL_SUB_EVENT_ASSOCIATION_ID` int(11) NOT NULL,
  `CHART_OF_ACCOUNT_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTION_TYPE` varchar(10) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`GL_TRANS_CONFIG_ID`),
  KEY `GL_TRANS_CONFIG_CREATED_BY_FK` (`CREATED_BY`),
  KEY `GL_TRANS_CONFIG_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `GL_TRANS_CONFIG_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `GL_TRANS_CONFIG_GL_EVENT_ASSOCIATION_ID_FK` (`GL_EVENT_ASSOCIATION_ID`),
  KEY `GL_TRANS_CONFIG_GL_SUB_EVENT_ASSOCIATION_ID_FK` (`GL_SUB_EVENT_ASSOCIATION_ID`),
  KEY `GL_TRANS_CONFIG_CHART_OF_ACCOUNT_ASSOCIATION_ID_FK` (`CHART_OF_ACCOUNT_ASSOCIATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `inventory_count`
--

DROP TABLE IF EXISTS `inventory_count`;
CREATE TABLE IF NOT EXISTS `inventory_count` (
  `INVENTORY_COUNT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `INVENTORY_COUNT_REF_NO` varchar(45) DEFAULT NULL COMMENT 'INVENTORY COUNT REF NUMBER',
  `STATUS_ASSOCICATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) NOT NULL,
  `EXPECTED_PROD_QTY` int(11) DEFAULT NULL,
  `COUNTED_PROD_QTY` int(11) DEFAULT NULL,
  `COUNT_DIFF` int(11) DEFAULT NULL,
  `SUPPLY_PRICE_EXP` decimal(20,2) DEFAULT NULL,
  `RETAIL_PRICE_EXP` decimal(20,2) DEFAULT NULL,
  `PRICE_DIFF` decimal(20,2) DEFAULT NULL,
  `SUPPLY_PRICE_COUNTED` decimal(20,2) DEFAULT NULL,
  `RETAIL_PRICE_COUNTED` decimal(20,2) DEFAULT NULL,
  `REMARKS` varchar(500) DEFAULT NULL,
  `INVENTORY_COUNT_TYPE_ASSOCICATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`INVENTORY_COUNT_ID`),
  KEY `INVENTORY_COUNT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `INVENTORY_COUNT_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `INVENTORY_COUNT_STATUS_ASSOCICATION_ID_FK` (`STATUS_ASSOCICATION_ID`),
  KEY `INVENTORY_COUNT_INVENTORY_COUNT_TYPE_ASSOCICATION_ID_FK` (`INVENTORY_COUNT_TYPE_ASSOCICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `inventory_count_detail`
--

DROP TABLE IF EXISTS `inventory_count_detail`;
CREATE TABLE IF NOT EXISTS `inventory_count_detail` (
  `INVENTORY_COUNT_DETAIL_ID` int(11) NOT NULL AUTO_INCREMENT,
  `INVENTORY_COUNT_ASSOCICATION_ID` int(11) NOT NULL,
  `PRODUCT_VARIANT_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `PRODUCT_ASSOCIATION_ID` int(11) DEFAULT NULL,
  `IS_PRODUCT` bit(1) NOT NULL,
  `EXPECTED_PROD_QTY` int(11) DEFAULT NULL,
  `SUPPLY_PRICE_EXP` decimal(20,2) DEFAULT NULL,
  `RETAIL_PRICE_EXP` decimal(20,2) DEFAULT NULL,
  `COUNTED_PROD_QTY` int(11) DEFAULT NULL,
  `SUPPLY_PRICE_COUNTED` decimal(20,2) DEFAULT NULL,
  `RETAIL_PRICE_COUNTED` decimal(20,2) DEFAULT NULL,
  `COUNT_DIFF` int(11) DEFAULT NULL,
  `PRICE_DIFF` decimal(20,2) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`INVENTORY_COUNT_DETAIL_ID`),
  KEY `INVENTORY_COUNT_DETAIL_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `INVENTORY_COUNT_DETAIL_INVENTORY_COUNT_ASSOCICATION_ID_FK` (`INVENTORY_COUNT_ASSOCICATION_ID`),
  KEY `INVENTORY_COUNT_DETAIL_PRODUCT_VARIANT_ASSOCICATION_ID_FK` (`PRODUCT_VARIANT_ASSOCICATION_ID`),
  KEY `INVENTORY_COUNT_DETAIL_PRODUCT_ASSOCIATION_ID_FK` (`PRODUCT_ASSOCIATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `inventory_count_type`
--

DROP TABLE IF EXISTS `inventory_count_type`;
CREATE TABLE IF NOT EXISTS `inventory_count_type` (
  `INVENTORY_COUNT_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `INVENTORY_COUNT_TYPE_CODE` varchar(5) NOT NULL,
  `INVENTORY_COUNT_TYPE_DESC` varchar(45) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  PRIMARY KEY (`INVENTORY_COUNT_TYPE_ID`),
  UNIQUE KEY `INVENTORY_COUNT_TYPE_INVENTORY_COUNT_TYPE_CODE_UK` (`INVENTORY_COUNT_TYPE_CODE`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `Inventory_Report`
--
DROP VIEW IF EXISTS `Inventory_Report`;
CREATE TABLE IF NOT EXISTS `Inventory_Report` (
`ID` int(11)
,`PRODUCT_NAME` text
,`SKU` varchar(500)
,`SUPPLY_PRICE_EXCL_TAX` decimal(20,2)
,`MARKUP_PRCT` decimal(8,5)
,`REORDER_POINT` bigint(20)
,`REORDER_AMOUNT` decimal(20,2)
,`CURRENT_INVENTORY` int(11)
,`NET_PRICE` decimal(33,11)
,`OUTLET_NAME` varchar(100)
,`OUTLET_ASSOCICATION_ID` int(11)
,`CREATED_DATE` timestamp
,`BRAND_NAME` varchar(200)
,`PRODUCT_TYPE` varchar(200)
,`CONTACT_NAME` varchar(200)
,`COMPANY_ASSOCIATION_ID` int(11)
,`STOCK_VALUE` decimal(30,2)
,`RETAIL_VALUE` decimal(43,11)
);
-- --------------------------------------------------------

--
-- Table structure for table `invoice_detail`
--

DROP TABLE IF EXISTS `invoice_detail`;
CREATE TABLE IF NOT EXISTS `invoice_detail` (
  `INVOICE_DETAIL_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_VARIENT_ASSOCIATION_ID` int(11) DEFAULT NULL,
  `PRODUCT_ASSOCIATION_ID` int(11) DEFAULT NULL,
  `ITEM_RETAIL_PRICE` decimal(20,2) DEFAULT NULL,
  `ITEM_SALE_PRICE` decimal(20,2) DEFAULT NULL,
  `ITEM_DISCOUNT_PRCT` decimal(20,2) DEFAULT NULL,
  `ITEM_TAX_AMOUNT` decimal(20,2) DEFAULT NULL,
  `ITEM_ORIGNAL_AMT` decimal(20,2) DEFAULT NULL,
  `ITEM_NOTES` varchar(256) DEFAULT NULL,
  `INVOICE_MAIN_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `PRODUCT_QUANTITY` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `ISRETURN` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`INVOICE_DETAIL_ID`),
  KEY `INVOICE_DETAIL_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `INVOICE_DETAIL_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `INVOICE_DETAIL_PRODUCT_VARIENT_ASSOCIATION_ID_FK` (`PRODUCT_VARIENT_ASSOCIATION_ID`),
  KEY `INVOICE_DETAIL_INVOICE_MAIN_ASSOCICATION_ID_FK` (`INVOICE_MAIN_ASSOCICATION_ID`),
  KEY `INVOICE_DETAIL_PRODUCT_ASSOCIATION_ID_FK` (`PRODUCT_ASSOCIATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `invoice_main`
--

DROP TABLE IF EXISTS `invoice_main`;
CREATE TABLE IF NOT EXISTS `invoice_main` (
  `INVOICE_MAIN_ID` int(11) NOT NULL AUTO_INCREMENT,
  `INVOICE_REF_NBR` varchar(100) DEFAULT NULL,
  `INVOICE_NOTES` varchar(256) DEFAULT NULL,
  `INVOICE_DISCOUNT` decimal(20,2) DEFAULT NULL,
  `INVOICE_TAX` decimal(20,2) DEFAULT NULL,
  `INVC_TYPE_CDE` varchar(5) DEFAULT NULL,
  `INVOICE_GENERATION_DTE` datetime DEFAULT NULL,
  `INVOICE_CANCEL_DTE` datetime DEFAULT NULL,
  `INVOICE_AMT` decimal(20,2) DEFAULT NULL,
  `INVOICE_DISCOUNT_AMT` decimal(20,2) DEFAULT NULL,
  `INVOICE_NET_AMT` decimal(20,2) DEFAULT NULL,
  `INVOICE_GIVEN_AMT` decimal(20,2) DEFAULT NULL,
  `INVOICE_ORIGNAL_AMT` decimal(20,2) DEFAULT NULL,
  `SETTLED_AMT` decimal(20,2) DEFAULT NULL,
  `STATUS_ASSOCICATION_ID` int(11) NOT NULL,
  `ORDER_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `CONTACT_ASSOCIATION_ID` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `DAILY_REGISTER_ASSOCICATION_ID` int(11) NOT NULL,
  `PAYMENT_TYPE_ASSOCICATION_ID` int(11) NOT NULL,
  `SALES_USER` int(11) DEFAULT NULL,
  PRIMARY KEY (`INVOICE_MAIN_ID`),
  KEY `INVOICE_MAIN_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `INVOICE_MAIN_CONTACT_ASSOCIATION_ID_FK` (`CONTACT_ASSOCIATION_ID`),
  KEY `INVOICE_MAIN_DAILY_REGISTER_ASSOCICATION_ID_FK` (`DAILY_REGISTER_ASSOCICATION_ID`),
  KEY `INVOICE_MAIN_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `INVOICE_MAIN_PAYMENT_TYPE_ASSOCICATION_ID_FK` (`PAYMENT_TYPE_ASSOCICATION_ID`),
  KEY `INVOICE_MAIN_STATUS_ASSOCICATION_ID_FK` (`STATUS_ASSOCICATION_ID`),
  KEY `INVOICE_MAIN_ORDER_ASSOCICATION_ID_FK` (`ORDER_ASSOCICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `loyalty`
--

DROP TABLE IF EXISTS `loyalty`;
CREATE TABLE IF NOT EXISTS `loyalty` (
  `LOYALTY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `LOYALTY_AMOUNT` decimal(10,2) NOT NULL,
  `CONTACT_ASSOCIATION_ID` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  PRIMARY KEY (`LOYALTY_ID`),
  KEY `LOYALTY_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `LOYALTY_CONTACT_ASSOCIATION_ID_FK` (`CONTACT_ASSOCIATION_ID`),
  KEY `LOYALTY_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
CREATE TABLE IF NOT EXISTS `menu` (
  `MENU_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MENU_NAME` varchar(256) NOT NULL,
  `ROLE_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTION_TYPE` varchar(100) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`MENU_ID`),
  KEY `MENU_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `MENU_ROLE_ASSOCIATION_ID_FK` (`ROLE_ASSOCIATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
CREATE TABLE IF NOT EXISTS `message` (
  `MESSAGE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MESSAGE_BUNDLE_COUNT` int(11) NOT NULL,
  `PACKAGE_START_DATE` date DEFAULT NULL,
  `PACKAGE_END_DATE` date DEFAULT NULL,
  `PACKAGE_RENEW_DATE` date DEFAULT NULL,
  `MESSAGE_TEXT_LIMIT` int(11) NOT NULL,
  `DESCRIPTION` varchar(256) DEFAULT NULL,
  `VENDER_NAME` varchar(256) DEFAULT NULL,
  `MASK_NAME` varchar(256) DEFAULT NULL,
  `USER_ID` varchar(256) DEFAULT NULL,
  `PASSWORD` varchar(256) DEFAULT NULL,
  `NTN_NUMBER` varchar(256) DEFAULT NULL,
  `OWNER_NAME` varchar(256) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`MESSAGE_ID`),
  KEY `MESSAGE_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `MESSAGE_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `message_detail`
--

DROP TABLE IF EXISTS `message_detail`;
CREATE TABLE IF NOT EXISTS `message_detail` (
  `MESSAGE_DETAIL_ID` int(11) NOT NULL AUTO_INCREMENT,
  `SENDER_ID` varchar(256) DEFAULT NULL,
  `RECEIVER_ID` varchar(256) DEFAULT NULL,
  `MESSAGE_DESCRIPTION` varchar(1000) DEFAULT NULL,
  `DELIVERY_ID` varchar(1000) DEFAULT NULL,
  `DELIVERY_STATUS` varchar(1000) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `MESSAGE_ASSOCIATION_ID` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`MESSAGE_DETAIL_ID`),
  KEY `MESSAGE_DETAIL_MESSAGE_ASSOCIATION_ID_FK` (`MESSAGE_ASSOCIATION_ID`),
  KEY `MESSAGE_DETAIL_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `MESSAGE_DETAIL_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `module`
--

DROP TABLE IF EXISTS `module`;
CREATE TABLE IF NOT EXISTS `module` (
  `MODULE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MODULE_NAME` varchar(256) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`MODULE_ID`),
  KEY `MODULE_CREATED_BY_FK` (`CREATED_BY`),
  KEY `MODULE_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `MODULE_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
CREATE TABLE IF NOT EXISTS `notification` (
  `NOTIFICATION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NOTIFICATION_TO` int(11) NOT NULL,
  `NOTIFICATION_FROM` int(11) NOT NULL,
  `NOTIFICATION_ASSOCIATION_ID` int(11) DEFAULT NULL,
  `NOTIFICATION_SUBJECT` varchar(256) NOT NULL,
  `NOTIFICATION_DESCRIPTION` varchar(256) NOT NULL,
  `ACTION_TAKEN` varchar(256) NOT NULL,
  `MARK_AS_READ` varchar(256) NOT NULL,
  `SEVERITY_ASSOCIATION_ID` int(11) NOT NULL,
  `CREATED_DATE` date NOT NULL,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `NOTIFICATION_CREATED_TIME` time NOT NULL,
  PRIMARY KEY (`NOTIFICATION_ID`),
  KEY `NOTIFICATION_NOTIFICATION_ASSOCIATION_ID_FK` (`NOTIFICATION_ASSOCIATION_ID`),
  KEY `NOTIFICATION_NOTIFICATION_TO_FK` (`NOTIFICATION_TO`),
  KEY `NOTIFICATION_NOTIFICATION_FROM_FK` (`NOTIFICATION_FROM`),
  KEY `NOTIFICATION_SEVERITY_ASSOCIATION_ID_FK` (`SEVERITY_ASSOCIATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE IF NOT EXISTS `order_detail` (
  `ORDER_DETAIL_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ORDER_MAIN_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `PRODUCT_VARIENT_ASSOCIATION_ID` int(11) DEFAULT NULL,
  `PRODUCT_ASSOCIATION_ID` int(11) DEFAULT NULL,
  `ITEM_UNIT_PRICE` decimal(21,5) DEFAULT NULL,
  `ITEM_TAX_AMOUNT` decimal(21,5) DEFAULT NULL,
  `ITEM_NOTES` varchar(256) DEFAULT NULL,
  `PRODUCT_QUANTITY` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ORDER_DETAIL_ID`),
  KEY `ORDER_DETAIL_ORDER_MAIN_ASSOCICATION_ID_FK` (`ORDER_MAIN_ASSOCICATION_ID`),
  KEY `ORDER_DETAIL_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `ORDER_DETAIL_PRODUCT_VARIENT_ASSOCIATION_ID_FK` (`PRODUCT_VARIENT_ASSOCIATION_ID`),
  KEY `ORDER_DETAIL_PRODUCT_ASSOCIATION_ID_FK` (`PRODUCT_ASSOCIATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `order_main`
--

DROP TABLE IF EXISTS `order_main`;
CREATE TABLE IF NOT EXISTS `order_main` (
  `ORDER_MAIN_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ORDER_REF_NBR` varchar(200) DEFAULT NULL,
  `ORDER_TRACKING_NBR` varchar(200) DEFAULT NULL,
  `ORDER_NOTES` varchar(500) DEFAULT NULL,
  `ORDER_GENERATION_DTE` datetime DEFAULT NULL,
  `ORDER_CANCEL_DTE` datetime DEFAULT NULL,
  `ORDER_EXPECTED_DELIVERY_DTE` datetime DEFAULT NULL,
  `ORDER_DELIVERY_DTE` datetime DEFAULT NULL,
  `ORDER_BILL_AMT` decimal(21,5) DEFAULT NULL,
  `TAX_AMT` decimal(21,5) DEFAULT NULL,
  `STATUS_ASSOCICATION_ID` int(11) NOT NULL,
  `CONTACT_ASSOCIATION_ID` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `PAYMENT_TYPE_ASSOCICATION_ID` int(11) NOT NULL,
  `DELIVER_METHOD_ASSOCICATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`ORDER_MAIN_ID`),
  KEY `ORDER_MAIN_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `ORDER_MAIN_CONTACT_ASSOCIATION_ID_FK` (`CONTACT_ASSOCIATION_ID`),
  KEY `ORDER_MAIN_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `ORDER_MAIN_PAYMENT_TYPE_ASSOCICATION_ID_FK` (`PAYMENT_TYPE_ASSOCICATION_ID`),
  KEY `ORDER_MAIN_DELIVERY_METHOD_ID_FK` (`DELIVER_METHOD_ASSOCICATION_ID`),
  KEY `ORDER_MAIN_STATUS_ASSOCICATION_ID_FK` (`STATUS_ASSOCICATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `outlet`
--

DROP TABLE IF EXISTS `outlet`;
CREATE TABLE IF NOT EXISTS `outlet` (
  `OUTLET_ID` int(11) NOT NULL AUTO_INCREMENT,
  `OUTLET_NAME` varchar(100) DEFAULT NULL,
  `ORDER_NUMBER` varchar(100) DEFAULT NULL,
  `ORDER_NUMBER_PREFIX` varchar(100) DEFAULT NULL,
  `CONTACT_NUMBER_PREFIX` varchar(100) DEFAULT NULL,
  `CONTACT_RETURN_NUMBER` varchar(100) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `SALES_TAX_ASSOCICATION_ID` int(11) NOT NULL,
  `ADDRESS_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `TIME_ZONE_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `IS_HEAD_OFFICE` bit(1) DEFAULT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  PRIMARY KEY (`OUTLET_ID`),
  KEY `OUTLET_ADDRESS_ASSOCICATION_ID_FK` (`ADDRESS_ASSOCICATION_ID`),
  KEY `OUTLET_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `OUTLET_SALES_TAX_ASSOCICATION_ID_FK` (`SALES_TAX_ASSOCICATION_ID`),
  KEY `OUTLET_TIME_ZONE_ASSOCICATION_ID_FK` (`TIME_ZONE_ASSOCICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `outlet_sales_report`
--
DROP VIEW IF EXISTS `outlet_sales_report`;
CREATE TABLE IF NOT EXISTS `outlet_sales_report` (
`Product` varchar(500)
,`Outlet` varchar(100)
,`CREATED_DATE` date
,`Revenue` decimal(53,2)
,`Revenue_tax_incl` decimal(54,2)
,`Cost_of_Goods` decimal(52,2)
,`Gross_Profit` decimal(53,2)
,`Margin` decimal(52,6)
,`Items_Sold` decimal(32,0)
,`Tax` decimal(42,2)
,`COMPANY_ASSOCIATION_ID` int(11)
,`OUTLET_ASSOCICATION_ID` int(11)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `Outlet_Sales_Report`
--
DROP VIEW IF EXISTS `Outlet_Sales_Report`;
CREATE TABLE IF NOT EXISTS `Outlet_Sales_Report` (
`Product` varchar(500)
,`Outlet` varchar(100)
,`CREATED_DATE` date
,`Revenue` decimal(52,2)
,`Revenue_tax_incl` decimal(53,2)
,`Cost_of_Goods` decimal(52,2)
,`Gross_Profit` decimal(53,2)
,`Margin` decimal(52,6)
,`Items_Sold` decimal(32,0)
,`Tax` decimal(42,2)
,`COMPANY_ASSOCIATION_ID` int(11)
,`OUTLET_ASSOCICATION_ID` int(11)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `Payment_Report`
--
DROP VIEW IF EXISTS `Payment_Report`;
CREATE TABLE IF NOT EXISTS `Payment_Report` (
`OUTLET_ASSOCICATION_ID` int(11)
,`OUTLET` varchar(100)
,`CREATED_DATE` date
,`AMOUNT` decimal(42,2)
,`COMPANY_ASSOCIATION_ID` int(11)
);
-- --------------------------------------------------------

--
-- Table structure for table `payment_type`
--

DROP TABLE IF EXISTS `payment_type`;
CREATE TABLE IF NOT EXISTS `payment_type` (
  `PAYMENT_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PAYMENT_TYPE_VALUE` varchar(100) NOT NULL,
  `ROUND_TO` varchar(50) DEFAULT NULL,
  `GATEWAY` varchar(100) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  PRIMARY KEY (`PAYMENT_TYPE_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `price_book`
--

DROP TABLE IF EXISTS `price_book`;
CREATE TABLE IF NOT EXISTS `price_book` (
  `PRICE_BOOK_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRICE_BOOK_NAME` varchar(250) CHARACTER SET utf8 NOT NULL COMMENT 'STOCK REFERENCE NUMBER',
  `CONTACT_GROUP_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `VALID_FROM` date NOT NULL,
  `VALID_TO` date NOT NULL,
  `IS_VALID_ON_STORE` bit(1) DEFAULT NULL,
  `IS_VALID_ON_ECOM` bit(1) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `FLAT_SALE` varchar(6) NOT NULL DEFAULT 'false',
  `FLAT_DISCOUNT` decimal(8,5) NOT NULL DEFAULT '0.00000',
  PRIMARY KEY (`PRICE_BOOK_ID`),
  KEY `PRICE_BOOK_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `PRICE_BOOK_CREATED_BY_FK` (`CREATED_BY`),
  KEY `PRICE_BOOK_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `PRICE_BOOK_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `PRICE_BOOK_CONTACT_GROUP_ASSOCICATION_ID_FK` (`CONTACT_GROUP_ASSOCICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `price_book_detail`
--

DROP TABLE IF EXISTS `price_book_detail`;
CREATE TABLE IF NOT EXISTS `price_book_detail` (
  `PRICE_BOOK_DETAIL_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRICE_BOOK_ASSOCICATION_ID` int(11) NOT NULL,
  `PRODUCT_ASSOCICATION_ID` int(11) NOT NULL,
  `PRODUCT_VARIENT_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `SUPPLY_PRICE` decimal(20,2) NOT NULL,
  `MARKUP` decimal(8,5) NOT NULL,
  `DISCOUNT` decimal(8,5) NOT NULL,
  `MIN_UNITS` int(11) DEFAULT NULL,
  `MAX_UNITS` int(11) DEFAULT NULL,
  `SALES_TAX_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `UUID` varchar(500) NOT NULL,
  PRIMARY KEY (`PRICE_BOOK_DETAIL_ID`),
  KEY `PRICE_BOOK_DETAIL_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `PRICE_BOOK_DETAIL_CREATED_BY_FK` (`CREATED_BY`),
  KEY `PRICE_BOOK_DETAIL_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `PRICE_BOOK_DETAIL_PRODUCT_VARIENT_ASSOCIATION_ID_FK` (`PRODUCT_VARIENT_ASSOCICATION_ID`),
  KEY `PRICE_BOOK_DETAIL_PRODUCT_ASSOCICATION_ID_FK` (`PRODUCT_ASSOCICATION_ID`),
  KEY `PRICE_BOOK_DETAIL_PRICE_BOOK_ASSOCICATION_ID_FK` (`PRICE_BOOK_ASSOCICATION_ID`),
  KEY `PRICE_BOOK_DETAIL_SALES_TAX_ASSOCIATION_ID_FK` (`SALES_TAX_ASSOCIATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `Price_Book_Detail_Summary`
--
DROP VIEW IF EXISTS `Price_Book_Detail_Summary`;
CREATE TABLE IF NOT EXISTS `Price_Book_Detail_Summary` (
`PRICE_BOOK_DETAIL_ID` int(11)
,`PRICE_BOOK_ASSOCICATION_ID` int(11)
,`PRODUCT_ASSOCICATION_ID` int(11)
,`UUID` varchar(500)
,`PRODUCT_NAME` varchar(500)
,`PRODUCT_VARIENT_ASSOCICATION_ID` bigint(11)
,`VARIANT_ATTRIBUTE_NAME` varchar(200)
,`SUPPLY_PRICE` decimal(20,2)
,`MARKUP` decimal(8,5)
,`DISCOUNT` decimal(8,5)
,`CREATED_BY` int(11)
,`UPDATED_BY` int(11)
,`SALES_TAX_ASSOCIATION_ID` int(11)
,`COMPANY_ASSOCIATION_ID` int(11)
);
-- --------------------------------------------------------

--
-- Table structure for table `printer_format`
--

DROP TABLE IF EXISTS `printer_format`;
CREATE TABLE IF NOT EXISTS `printer_format` (
  `PRINTER_FORMAT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRINTER_FORMAT_VALUE` varchar(256) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  PRIMARY KEY (`PRINTER_FORMAT_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
CREATE TABLE IF NOT EXISTS `product` (
  `PRODUCT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_NAME` varchar(500) NOT NULL,
  `PRODUCT_UUID` varchar(500) NOT NULL,
  `PRODUCT_DESC` varchar(1000) DEFAULT NULL,
  `PRODUCT_HANDLER` varchar(45) NOT NULL COMMENT '\nA unique identifier for this product.\nThe handle is the unique identifier for this product.',
  `PRODUCT_TYPE_ASSOCICATION_ID` int(11) NOT NULL,
  `CONTACT_ASSOCICATION_ID` int(11) NOT NULL,
  `BRAND_ASSOCICATION_ID` int(11) NOT NULL,
  `SALES_ACCOUNT_CODE` varchar(45) DEFAULT NULL,
  `PURCHASE_ACCOUNT_CODE` varchar(45) DEFAULT NULL,
  `PRODUCT_CAN_BE_SOLD` varchar(10) NOT NULL,
  `STANDARD_PRODUCT` varchar(10) NOT NULL,
  `TRACKING_PRODUCT` varchar(10) NOT NULL,
  `VARIANT_PRODUCTS` varchar(10) NOT NULL,
  `CURRENT_INVENTORY` int(11) DEFAULT NULL,
  `REORDER_POINT` int(11) DEFAULT NULL,
  `REORDER_AMOUNT` decimal(20,2) DEFAULT NULL,
  `SUPPLY_PRICE_EXCL_TAX` decimal(20,2) NOT NULL,
  `MARKUP_PRCT` decimal(8,5) NOT NULL COMMENT 'MARKUP %',
  `SKU` varchar(500) NOT NULL COMMENT 'STOCK KEEPING UNIT ( SKU)',
  `OUTLET_ASSOCICATION_ID` int(11) NOT NULL,
  `SALES_TAX_ASSOCICATION_ID` int(11) NOT NULL COMMENT 'SALE TAX ID TO LINK WITH SALES TAX',
  `IMAGE_PATH` varchar(250) DEFAULT NULL,
  `DISPLAY` bit(1) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`PRODUCT_ID`),
  KEY `PRODUCT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `PRODUCT_SALES_TAX_ASSOCICATION_ID_FK` (`SALES_TAX_ASSOCICATION_ID`),
  KEY `PRODUCT_BRAND_ASSOCICATION_ID_FK` (`BRAND_ASSOCICATION_ID`),
  KEY `PRODUCT_CREATED_BY_FK` (`CREATED_BY`),
  KEY `PRODUCT_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `PRODUCT_PRODUCT_TYPE_ASSOCICATION_ID_FK` (`PRODUCT_TYPE_ASSOCICATION_ID`),
  KEY `PRODUCT_CONTACT_ASSOCICATION_ID_FK` (`CONTACT_ASSOCICATION_ID`),
  KEY `PRODUCT_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `ProductType_Sales_Report`
--
DROP VIEW IF EXISTS `ProductType_Sales_Report`;
CREATE TABLE IF NOT EXISTS `ProductType_Sales_Report` (
`Product` varchar(500)
,`Outlet` varchar(100)
,`ProductType` varchar(200)
,`CREATED_DATE` date
,`Revenue` decimal(52,2)
,`Revenue_tax_incl` decimal(53,2)
,`Cost_of_Goods` decimal(52,2)
,`Gross_Profit` decimal(53,2)
,`Margin` decimal(52,6)
,`Items_Sold` decimal(32,0)
,`Tax` decimal(42,2)
,`COMPANY_ASSOCIATION_ID` int(11)
,`OUTLET_ASSOCICATION_ID` int(11)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `producttype_sales_report`
--
DROP VIEW IF EXISTS `producttype_sales_report`;
CREATE TABLE IF NOT EXISTS `producttype_sales_report` (
`Product` varchar(500)
,`Outlet` varchar(100)
,`ProductType` varchar(200)
,`CREATED_DATE` date
,`Revenue` decimal(53,2)
,`Revenue_tax_incl` decimal(54,2)
,`Cost_of_Goods` decimal(52,2)
,`Gross_Profit` decimal(53,2)
,`Margin` decimal(52,6)
,`Items_Sold` decimal(32,0)
,`Tax` decimal(42,2)
,`COMPANY_ASSOCIATION_ID` int(11)
,`OUTLET_ASSOCICATION_ID` int(11)
);
-- --------------------------------------------------------

--
-- Table structure for table `product_history`
--

DROP TABLE IF EXISTS `product_history`;
CREATE TABLE IF NOT EXISTS `product_history` (
  `PRODUCT_HISTORY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_HISTORY_UUID` varchar(500) NOT NULL,
  `ACTION_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `PRODUCT_ASSOCICATION_ID` int(11) NOT NULL,
  `PRODUCT_VARIANT_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `COMPOSITE_PRODUCT_ASSOCIATION_ID` int(11) DEFAULT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) NOT NULL,
  `QUANTITY` int(11) NOT NULL,
  `CHANGE_QUANTITY` int(11) NOT NULL,
  `OUTLET_QUANTITY` int(11) NOT NULL,
  `ACTION` varchar(50) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`PRODUCT_HISTORY_ID`),
  KEY `PRODUCT_HISTORY_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `PRODUCT_HISTORY_CREATED_BY_FK` (`CREATED_BY`),
  KEY `PRODUCT_HISTORY_PRODUCT_ASSOCICATION_ID_FK` (`PRODUCT_ASSOCICATION_ID`),
  KEY `PRODUCT_HISTORY_COMPOSITE_PRODUCT_ASSOCIATION_ID_FK` (`COMPOSITE_PRODUCT_ASSOCIATION_ID`),
  KEY `PRODUCT_HISTORY_PRODUCT_VARIANT_ASSOCICATION_ID_FK` (`PRODUCT_VARIANT_ASSOCICATION_ID`),
  KEY `PRODUCT_HISTORY_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `product_summmary`
--
DROP VIEW IF EXISTS `product_summmary`;
CREATE TABLE IF NOT EXISTS `product_summmary` (
`ID` int(11)
,`PRODUCT_NAME` varchar(500)
,`SKU` varchar(500)
,`SUPPLY_PRICE_EXCL_TAX` decimal(20,2)
,`REORDER_POINT` int(11)
,`REORDER_AMOUNT` decimal(20,2)
,`CURRENT_INVENTORY` int(11)
,`NET_PRICE` decimal(33,11)
,`OUTLET_NAME` varchar(100)
,`OUTLET_ASSOCICATION_ID` int(11)
,`CREATED_DATE` timestamp
,`BRAND_NAME` varchar(200)
,`PRODUCT_TYPE` varchar(200)
,`CONTACT_NAME` varchar(200)
,`VARIANT_COUNT` bigint(21)
,`VARIANT_CURRENT_INVENTORY` int(11)
,`VARIANT_SKU` varchar(500)
,`VARIANT_SUPPLY_PRICE_EXCL_TAX` decimal(20,2)
,`VARIANT_REORDER_POINT` int(11)
,`VARIANT_REORDER_AMOUNT` decimal(20,2)
,`VARIANT_NET_PRICE` decimal(33,11)
,`VARIANT_COMP_COUNT` bigint(21)
,`COMPANY_ASSOCIATION_ID` int(11)
,`IMAGE_PATH` varchar(250)
,`VARIANT_PRODUCTS` varchar(10)
,`STANDARD_PRODUCT` varchar(10)
,`VARIANT_INVENTORY_COUNT` decimal(32,0)
);
-- --------------------------------------------------------

--
-- Table structure for table `product_tag`
--

DROP TABLE IF EXISTS `product_tag`;
CREATE TABLE IF NOT EXISTS `product_tag` (
  `PRODUCT_TAG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_TAG_UUID` varchar(500) NOT NULL,
  `TAG_ASSOCICATION_ID` int(11) NOT NULL,
  `PRODUCT_ASSOCICATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`PRODUCT_TAG_ID`),
  KEY `PRODUCT_TAG_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `PRODUCT_TAG_CREATED_BY_FK` (`CREATED_BY`),
  KEY `PRODUCT_TAG_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `PRODUCT_TAG_PRODUCT_ASSOCICATION_ID_FK` (`PRODUCT_ASSOCICATION_ID`),
  KEY `PRODUCT_TAG_TAG_ASSOCICATION_ID_FK` (`TAG_ASSOCICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `product_type`
--

DROP TABLE IF EXISTS `product_type`;
CREATE TABLE IF NOT EXISTS `product_type` (
  `PRODUCT_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_TYPE_NAME` varchar(200) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`PRODUCT_TYPE_ID`),
  KEY `PRODUCT_TYPE_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `product_variant`
--

DROP TABLE IF EXISTS `product_variant`;
CREATE TABLE IF NOT EXISTS `product_variant` (
  `PRODUCT_VARIANT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCT_ASSOCICATION_ID` int(11) NOT NULL,
  `VARIANT_ATTRIBUTE_NAME` varchar(200) DEFAULT NULL,
  `PRODUCT_VARIANT_UUID` varchar(500) NOT NULL,
  `PRODUCT_UUID` varchar(500) NOT NULL,
  `VARIANT_ATTRIBUTE_VALUE_1` varchar(200) DEFAULT NULL,
  `VARIANT_ATTRIBUTE_VALUE_2` varchar(200) DEFAULT NULL,
  `VARIANT_ATTRIBUTE_VALUE_3` varchar(200) DEFAULT NULL,
  `VARIANT_ATTRIBUTE_ASSOCICATION_ID_1` int(11) DEFAULT NULL,
  `VARIANT_ATTRIBUTE_ASSOCICATION_ID_2` int(11) DEFAULT NULL,
  `VARIANT_ATTRIBUTE_ASSOCICATION_ID_3` int(11) DEFAULT NULL,
  `CURRENT_INVENTORY` int(11) DEFAULT NULL,
  `REORDER_POINT` int(11) DEFAULT NULL,
  `REORDER_AMOUNT` decimal(20,2) DEFAULT NULL,
  `SUPPLY_PRICE_EXCL_TAX` decimal(20,2) NOT NULL,
  `MARKUP_PRCT` decimal(8,5) NOT NULL COMMENT 'MARKUP %',
  `SKU` varchar(500) NOT NULL COMMENT 'STOCK KEEPING UNIT ( SKU)',
  `OUTLET_ASSOCICATION_ID` int(11) NOT NULL,
  `SALES_TAX_ASSOCICATION_ID` int(11) NOT NULL COMMENT 'SALE TAX ID TO LINK WITH SALES TAX',
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `PRODUCT_VARIANT_ASSOCIATION_ID` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`PRODUCT_VARIANT_ID`),
  KEY `PRODUCT_VARIANT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `PRODUCT_VARIANT_CREATED_BY_FK` (`CREATED_BY`),
  KEY `PRODUCT_VARIANT_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `PRODUCT_VARIANT_SALES_TAX_ASSOCICATION_ID_FK` (`SALES_TAX_ASSOCICATION_ID`),
  KEY `PRODUCT_VARIANT_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `PRODUCT_VARIANT_PRODUCT_ASSOCICATION_ID_FK` (`PRODUCT_ASSOCICATION_ID`),
  KEY `PRODUCT_VARIANT_VARIANT_ATTRIBUTE_ASSOCICATION_ID_1_FK` (`VARIANT_ATTRIBUTE_ASSOCICATION_ID_1`),
  KEY `PRODUCT_VARIANT_VARIANT_ATTRIBUTE_ASSOCICATION_ID_2_FK` (`VARIANT_ATTRIBUTE_ASSOCICATION_ID_2`),
  KEY `PRODUCT_VARIANT_VARIANT_ATTRIBUTE_ASSOCICATION_ID_3_FK` (`VARIANT_ATTRIBUTE_ASSOCICATION_ID_3`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

-- --------------------------------------------------------

--
-- Table structure for table `receipt`
--

DROP TABLE IF EXISTS `receipt`;
CREATE TABLE IF NOT EXISTS `receipt` (
  `RECEIPT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `RECEIPT_REF_NO` varchar(45) DEFAULT NULL,
  `RECEIPT_DATE` datetime DEFAULT NULL,
  `RECEIPT_AMOUNT` decimal(20,2) DEFAULT NULL,
  `PAYMENT_TYPE_ASSOCICATION_ID` int(11) NOT NULL,
  `STATUS_ASSOCICATION_ID` int(11) NOT NULL,
  `RECEIPT_CANCELLATION_DATE` varchar(45) DEFAULT NULL,
  `RECEIPT_UAF_AMT` decimal(20,2) DEFAULT NULL,
  `OUTLET_ASSOCIATION_ID` int(11) DEFAULT NULL,
  `CONTACT_ASSOCIATION_ID` int(11) DEFAULT NULL,
  `INVOICE_MAIN_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `DAILY_REGISTER_ASSOCICATION_ID` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`RECEIPT_ID`),
  KEY `RECEIPT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `RECEIPT_DAILY_REGISTER_ASSOCICATION_ID_FK` (`DAILY_REGISTER_ASSOCICATION_ID`),
  KEY `RECEIPT_OUTLET_ASSOCIATION_ID_FK` (`OUTLET_ASSOCIATION_ID`),
  KEY `RECEIPT_CONTACT_ASSOCICATION_ID_FK` (`CONTACT_ASSOCIATION_ID`),
  KEY `RECEIPT_INVOICE_MAIN_ASSOCICATION_ID_FK` (`INVOICE_MAIN_ASSOCICATION_ID`),
  KEY `RECEIPT_PAYMENT_TYPE_ASSOCICATION_ID_FK` (`PAYMENT_TYPE_ASSOCICATION_ID`),
  KEY `RECEIPT_STATUS_ASSOCICATION_ID_FK` (`STATUS_ASSOCICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `register`
--

DROP TABLE IF EXISTS `register`;
CREATE TABLE IF NOT EXISTS `register` (
  `REGISTER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `REGISTER_NAME` varchar(256) NOT NULL,
  `CACHE_MANAGEMENT_ENABLE` varchar(256) NOT NULL,
  `RECEIPT_NUMBER` varchar(256) NOT NULL,
  `RECEIPT_PREFIX` varchar(256) NOT NULL,
  `RECEIPT_SUFIX` varchar(256) NOT NULL,
  `SELECT_NEXT_USER_FOR_SALE` varchar(256) NOT NULL,
  `EMAIL_RECEIPT` varchar(256) NOT NULL,
  `PRINT_RECEIPT` varchar(256) NOT NULL,
  `NOTES` varchar(256) NOT NULL,
  `PRINT_NOTES_ON_RECEIPT` varchar(256) NOT NULL,
  `SHOW_DISCOUNT_ON_RECEIPT` varchar(256) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`REGISTER_ID`),
  KEY `REGISTER_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `REGISTER_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `Register_Report`
--
DROP VIEW IF EXISTS `Register_Report`;
CREATE TABLE IF NOT EXISTS `Register_Report` (
`OUTLET_ASSOCICATION_ID` int(11)
,`OUTLET_NAME` varchar(100)
,`CASH_AMT_ACTUAL` decimal(20,2)
,`CREDIT_CARD_AMT_ACTUAL` decimal(20,2)
,`OPENING_DATE` varchar(49)
,`CLOSING_DATE` varchar(49)
,`Open_By` text
,`Close_By` text
,`Status` varchar(45)
,`COMPANY_ASSOCIATION_ID` int(11)
);
-- --------------------------------------------------------

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(256) NOT NULL,
  `ACTION_TYPE` varchar(256) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `SaleReport_withOutSale`
--
DROP VIEW IF EXISTS `SaleReport_withOutSale`;
CREATE TABLE IF NOT EXISTS `SaleReport_withOutSale` (
`Product` varchar(500)
,`Outlet` varchar(100)
,`CREATED_DATE` date
,`Revenue` decimal(52,2)
,`Revenue_tax_incl` decimal(53,2)
,`Cost_of_Goods` decimal(52,2)
,`Gross_Profit` decimal(53,2)
,`Margin` decimal(52,6)
,`Items_Sold` decimal(32,0)
,`Tax` decimal(42,2)
,`COMPANY_ASSOCIATION_ID` int(11)
,`OUTLET_ASSOCICATION_ID` int(11)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `salereport_withoutsale`
--
DROP VIEW IF EXISTS `salereport_withoutsale`;
CREATE TABLE IF NOT EXISTS `salereport_withoutsale` (
`Product` varchar(500)
,`Outlet` varchar(100)
,`CREATED_DATE` date
,`Revenue` decimal(53,2)
,`Revenue_tax_incl` decimal(54,2)
,`Cost_of_Goods` decimal(52,2)
,`Gross_Profit` decimal(53,2)
,`Margin` decimal(52,6)
,`Items_Sold` decimal(32,0)
,`Tax` decimal(42,2)
,`COMPANY_ASSOCIATION_ID` int(11)
,`OUTLET_ASSOCICATION_ID` int(11)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `salereport_withsale`
--
DROP VIEW IF EXISTS `salereport_withsale`;
CREATE TABLE IF NOT EXISTS `salereport_withsale` (
`Product` varchar(500)
,`Outlet` varchar(100)
,`CREATED_DATE` date
,`Revenue` decimal(53,2)
,`Revenue_tax_incl` decimal(54,2)
,`Cost_of_Goods` decimal(52,2)
,`Gross_Profit` decimal(53,2)
,`Margin` decimal(52,6)
,`Items_Sold` decimal(32,0)
,`Tax` decimal(42,2)
,`COMPANY_ASSOCIATION_ID` int(11)
,`OUTLET_ASSOCICATION_ID` int(11)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `SaleReport_withSale`
--
DROP VIEW IF EXISTS `SaleReport_withSale`;
CREATE TABLE IF NOT EXISTS `SaleReport_withSale` (
`Product` varchar(500)
,`Outlet` varchar(100)
,`CREATED_DATE` date
,`Revenue` decimal(52,2)
,`Revenue_tax_incl` decimal(53,2)
,`Cost_of_Goods` decimal(52,2)
,`Gross_Profit` decimal(53,2)
,`Margin` decimal(52,6)
,`Items_Sold` decimal(32,0)
,`Tax` decimal(42,2)
,`COMPANY_ASSOCIATION_ID` int(11)
,`OUTLET_ASSOCICATION_ID` int(11)
);
-- --------------------------------------------------------

--
-- Table structure for table `sales_tax`
--

DROP TABLE IF EXISTS `sales_tax`;
CREATE TABLE IF NOT EXISTS `sales_tax` (
  `SALES_TAX_ID` int(11) NOT NULL AUTO_INCREMENT,
  `SALES_TAX_NAME` varchar(100) DEFAULT NULL,
  `SALES_TAX_PERCENTAGE` double DEFAULT NULL,
  `EFFECTIVE_DATE` date NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`SALES_TAX_ID`),
  KEY `SALES_TAX_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `Sale_Details`
--
DROP VIEW IF EXISTS `Sale_Details`;
CREATE TABLE IF NOT EXISTS `Sale_Details` (
`Product` varchar(500)
,`Variant` varchar(200)
,`Outlet` varchar(100)
,`CREATED_DATE` date
,`Revenue` decimal(52,2)
,`Revenue_tax_incl` decimal(53,2)
,`Cost_of_Goods` decimal(52,2)
,`Gross_Profit` decimal(53,2)
,`Margin` decimal(52,6)
,`Items_Sold` decimal(32,0)
,`Tax` decimal(42,2)
,`COMPANY_ASSOCIATION_ID` int(11)
,`OUTLET_ASSOCICATION_ID` int(11)
);
-- --------------------------------------------------------

--
-- Table structure for table `severity`
--

DROP TABLE IF EXISTS `severity`;
CREATE TABLE IF NOT EXISTS `severity` (
  `SEVERITY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `SEVERITY_DESCRIPTION` varchar(256) NOT NULL,
  PRIMARY KEY (`SEVERITY_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `sms_report`
--
DROP VIEW IF EXISTS `sms_report`;
CREATE TABLE IF NOT EXISTS `sms_report` (
`MESSAGE_DETAIL_ID` int(11)
,`SENDER_ID` varchar(256)
,`RECEIVER_ID` varchar(256)
,`MESSAGE_DESCRIPTION` varchar(1000)
,`DELIVERY_ID` varchar(1000)
,`DELIVERY_STATUS` varchar(1000)
,`MESSAGE_ASSOCIATION_ID` int(11)
,`MESSAGE_BUNDLE_COUNT` bigint(11)
,`OUTLET_ASSOCICATION_ID` int(11)
,`OUTLET_NAME` varchar(100)
,`SMS_SENT_DATE` varchar(49)
,`CREATED_DATE` date
,`SENDER_NAME` text
,`CUSTOMER_NAME` varchar(200)
,`COMPANY_ASSOCIATION_ID` int(11)
);
-- --------------------------------------------------------

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
CREATE TABLE IF NOT EXISTS `status` (
  `STATUS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `STATUS_CODE` varchar(5) NOT NULL,
  `STATUS_DESC` varchar(45) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  PRIMARY KEY (`STATUS_ID`),
  UNIQUE KEY `STATUS_STATUS_CODE_UK` (`STATUS_CODE`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `stock_order`
--

DROP TABLE IF EXISTS `stock_order`;
CREATE TABLE IF NOT EXISTS `stock_order` (
  `STOCK_ORDER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `STOCK_REF_NO` varchar(45) DEFAULT NULL COMMENT 'STOCK REFERENCE NUMBER',
  `STATUS_ASSOCICATION_ID` int(11) NOT NULL,
  `DILIVERY_DUE_DATE` date NOT NULL,
  `CONTACT_ID` int(11) NOT NULL,
  `ORDER_NO` varchar(45) DEFAULT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) NOT NULL,
  `SOURCE_OUTLET_ASSOCICATION_ID` int(11) DEFAULT NULL COMMENT 'FOR STOCK TRANSFER INPLACE OF CONTACT',
  `CONTACT_INVOICE_NO` varchar(45) DEFAULT NULL,
  `STOCK_ORDER_DATE` datetime DEFAULT NULL,
  `REMARKS` varchar(500) DEFAULT NULL,
  `ORDR_RECV_DATE` datetime DEFAULT NULL COMMENT 'ORDER RECIEVING DATE',
  `RETURN_NO` varchar(45) DEFAULT NULL COMMENT 'USED FOR STOCK RETURN TRANSACTION',
  `STOCK_ORDER_TYPE_ASSOCICATION_ID` int(11) NOT NULL COMMENT 'USED TO SAVE ORDER TYPE CODE E.G.\n\nCONTACT ORDER\nCONTACT RETURN\nOUTLET TRANSFER',
  `AUTOFILL_REORDER` bit(1) NOT NULL COMMENT 'USED TO STORE INFO ABOUT AUTOFILL FOR REORDER',
  `RETAIL_PRICE_BILL` bit(1) NOT NULL DEFAULT b'0',
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`STOCK_ORDER_ID`),
  KEY `STOCK_ORDER_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `STOCK_ORDER_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `STOCK_ORDER_STATUS_ASSOCICATION_ID_FK` (`STATUS_ASSOCICATION_ID`),
  KEY `STOCK_ORDER_SOURCE_OUTLET_ASSOCICATION_ID_FK` (`SOURCE_OUTLET_ASSOCICATION_ID`),
  KEY `STOCK_ORDER_STOCK_ORDER_TYPE_ASSOCICATION_ID_FK` (`STOCK_ORDER_TYPE_ASSOCICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `stock_order_detail`
--

DROP TABLE IF EXISTS `stock_order_detail`;
CREATE TABLE IF NOT EXISTS `stock_order_detail` (
  `STOCK_ORDER_DETAIL_ID` int(11) NOT NULL AUTO_INCREMENT,
  `STOCK_ORDER_ASSOCICATION_ID` int(11) NOT NULL,
  `PRODUCT_VARIANT_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `PRODUCT_ASSOCIATION_ID` int(11) DEFAULT NULL,
  `IS_PRODUCT` bit(1) NOT NULL,
  `ORDER_PROD_QTY` int(11) DEFAULT NULL COMMENT 'ORDERED product QUANTITY',
  `ORDR_SUPPLY_PRICE` decimal(20,2) DEFAULT NULL COMMENT 'ORDERED SUPPLY PRICE',
  `RECV_PROD_QTY` int(11) DEFAULT NULL COMMENT 'RECIVED product QUANTITY',
  `RECV_SUPPLY_PRICE` decimal(20,2) DEFAULT NULL COMMENT 'RECIEVED SUPPLY PRICE',
  `RETAIL_PRICE` decimal(20,2) DEFAULT NULL COMMENT 'RETAIL PRICE',
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`STOCK_ORDER_DETAIL_ID`),
  KEY `STOCK_ORDER_DETAIL_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `STOCK_ORDER_DETAIL_STOCK_ORDER_ASSOCICATION_ID_FK` (`STOCK_ORDER_ASSOCICATION_ID`),
  KEY `STOCK_ORDER_DETAIL_PRODUCT_VARIANT_ASSOCICATION_ID_FK` (`PRODUCT_VARIANT_ASSOCICATION_ID`),
  KEY `STOCK_ORDER_DETAIL_PRODUCT_ASSOCIATION_ID_FK` (`PRODUCT_ASSOCIATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

-- --------------------------------------------------------

--
-- Table structure for table `stock_order_type`
--

DROP TABLE IF EXISTS `stock_order_type`;
CREATE TABLE IF NOT EXISTS `stock_order_type` (
  `STOCK_ORDER_TYPE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `STOCK_ORDER_TYPE_CODE` varchar(5) NOT NULL,
  `STOCK_ORDER_TYPE_DESC` varchar(45) NOT NULL COMMENT 'USED TO SAVE ORDER TYPE CODE E.G.\n\nCONTACT ORDER\nCONTACT RETURN\nOUTLET TRANSFER',
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  PRIMARY KEY (`STOCK_ORDER_TYPE_ID`),
  UNIQUE KEY `STOCK_ORDER_TYPE_STOCK_ORDER_TYPE_CODE_UK` (`STOCK_ORDER_TYPE_CODE`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `Supplier_Sales_Report`
--
DROP VIEW IF EXISTS `Supplier_Sales_Report`;
CREATE TABLE IF NOT EXISTS `Supplier_Sales_Report` (
`Product` varchar(500)
,`Outlet` varchar(100)
,`Supplier` varchar(200)
,`CREATED_DATE` date
,`Revenue` decimal(52,2)
,`Revenue_tax_incl` decimal(53,2)
,`Cost_of_Goods` decimal(52,2)
,`Gross_Profit` decimal(53,2)
,`Margin` decimal(52,6)
,`Items_Sold` decimal(32,0)
,`Tax` decimal(42,2)
,`COMPANY_ASSOCIATION_ID` int(11)
,`OUTLET_ASSOCICATION_ID` int(11)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `supplier_sales_report`
--
DROP VIEW IF EXISTS `supplier_sales_report`;
CREATE TABLE IF NOT EXISTS `supplier_sales_report` (
`Product` varchar(500)
,`Outlet` varchar(100)
,`Supplier` varchar(200)
,`CREATED_DATE` date
,`Revenue` decimal(53,2)
,`Revenue_tax_incl` decimal(54,2)
,`Cost_of_Goods` decimal(52,2)
,`Gross_Profit` decimal(53,2)
,`Margin` decimal(52,6)
,`Items_Sold` decimal(32,0)
,`Tax` decimal(42,2)
,`COMPANY_ASSOCIATION_ID` int(11)
,`OUTLET_ASSOCICATION_ID` int(11)
);
-- --------------------------------------------------------

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
CREATE TABLE IF NOT EXISTS `tag` (
  `TAG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TAG_NAME` varchar(200) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`TAG_ID`),
  KEY `TAG_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `TAG_CREATED_BY_FK` (`CREATED_BY`),
  KEY `TAG_UPDATED_BY_FK` (`UPDATED_BY`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `temp_sale`
--
DROP VIEW IF EXISTS `temp_sale`;
CREATE TABLE IF NOT EXISTS `temp_sale` (
`Product` varchar(500)
,`Outlet` varchar(100)
,`CREATED_DATE` date
,`Revenue` decimal(53,2)
,`Revenue_tax_incl` decimal(54,2)
,`Cost_of_Goods` decimal(52,2)
,`Gross_Profit` decimal(53,2)
,`Margin` decimal(52,6)
,`Items_Sold` decimal(32,0)
,`Tax` decimal(42,2)
,`COMPANY_ASSOCIATION_ID` int(11)
,`OUTLET_ASSOCICATION_ID` int(11)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `Temp_Sale`
--
DROP VIEW IF EXISTS `Temp_Sale`;
CREATE TABLE IF NOT EXISTS `Temp_Sale` (
`Product` varchar(500)
,`Outlet` varchar(100)
,`CREATED_DATE` date
,`Revenue` decimal(52,2)
,`Revenue_tax_incl` decimal(53,2)
,`Cost_of_Goods` decimal(52,2)
,`Gross_Profit` decimal(53,2)
,`Margin` decimal(52,6)
,`Items_Sold` decimal(32,0)
,`Tax` decimal(42,2)
,`COMPANY_ASSOCIATION_ID` int(11)
,`OUTLET_ASSOCICATION_ID` int(11)
);
-- --------------------------------------------------------

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
CREATE TABLE IF NOT EXISTS `ticket` (
  `TICKET_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(256) NOT NULL,
  `TICKET_DETAIL` blob NOT NULL,
  `SEVERITY_ASSOCIATION_ID` int(11) NOT NULL,
  `TICKET_STATUS` varchar(256) NOT NULL,
  `RE_OPEN` varchar(256) NOT NULL,
  `RE_OPEN_COUNT` int(11) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `RESOLVED_BY` int(11) DEFAULT NULL,
  `RESOLVED_DATE` timestamp NULL DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`TICKET_ID`),
  KEY `TICKET_CREATED_BY_FK` (`CREATED_BY`),
  KEY `TICKET_RESOLVED_BY_FK` (`RESOLVED_BY`),
  KEY `TICKET_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `TICKET_SEVERITY_ASSOCIATION_ID_FK` (`SEVERITY_ASSOCIATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `ticket_activity`
--

DROP TABLE IF EXISTS `ticket_activity`;
CREATE TABLE IF NOT EXISTS `ticket_activity` (
  `TICKET_ACTIVITY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TICKET_ASSOCIATION_ID` int(11) NOT NULL,
  `DESCRIPTION` varchar(256) NOT NULL,
  `USER_REPLY` varchar(256) NOT NULL,
  `OLD_STATUS` varchar(256) NOT NULL,
  `NEW_STATUS` varchar(256) NOT NULL,
  `RESOLVER_FEEDBACK` varchar(256) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`TICKET_ACTIVITY_ID`),
  KEY `TICKET_ACTIVITY_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `TICKET_ACTIVITY_TICKET_ASSOCIATION_ID_FK` (`TICKET_ASSOCIATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1;

-- --------------------------------------------------------

--
-- Table structure for table `time_zone`
--

DROP TABLE IF EXISTS `time_zone`;
CREATE TABLE IF NOT EXISTS `time_zone` (
  `TIME_ZONE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TIME_ZONE_VALUE` varchar(256) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  PRIMARY KEY (`TIME_ZONE_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USER_EMAIL` varchar(256) NOT NULL,
  `PASSWORD` varchar(50) NOT NULL,
  `ROLE_ASSOCIATION_ID` int(11) NOT NULL,
  `FIRST_NAME` varchar(256) NOT NULL,
  `LAST_NAME` varchar(256) NOT NULL,
  `CONTACT_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `IMAGE` blob,
  `LAST_LOGIN` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) DEFAULT NULL,
  `UPDATED_BY` int(11) DEFAULT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `USER_USER_EMAIL_UK` (`USER_EMAIL`),
  KEY `USER_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `USER_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `USER_ROLE_ASSOCIATION_ID_FK` (`ROLE_ASSOCIATION_ID`),
  KEY `USER_CONTACT_ASSOCICATION_ID_FK` (`CONTACT_ASSOCICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_outlets`
--

DROP TABLE IF EXISTS `user_outlets`;
CREATE TABLE IF NOT EXISTS `user_outlets` (
  `USER_OUTLETS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `USER_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `ADDRESS_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `TIME_ZONE_ASSOCICATION_ID` int(11) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  PRIMARY KEY (`USER_OUTLETS_ID`),
  KEY `USER_OUTLETS_CREATED_BY_FK` (`CREATED_BY`),
  KEY `USER_OUTLETS_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `USER_OUTLETS_USER_ASSOCIATION_ID_FK` (`USER_ASSOCIATION_ID`),
  KEY `USER_OUTLETS_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `USER_OUTLETS_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `User_Sales_Report`
--
DROP VIEW IF EXISTS `User_Sales_Report`;
CREATE TABLE IF NOT EXISTS `User_Sales_Report` (
`Product` varchar(500)
,`User` text
,`Outlet` varchar(100)
,`CREATED_DATE` date
,`Revenue` decimal(52,2)
,`Revenue_tax_incl` decimal(53,2)
,`Cost_of_Goods` decimal(52,2)
,`Gross_Profit` decimal(53,2)
,`Margin` decimal(52,6)
,`Items_Sold` decimal(32,0)
,`Tax` decimal(42,2)
,`COMPANY_ASSOCIATION_ID` int(11)
,`OUTLET_ASSOCICATION_ID` int(11)
);
-- --------------------------------------------------------

--
-- Stand-in structure for view `user_sales_report`
--
DROP VIEW IF EXISTS `user_sales_report`;
CREATE TABLE IF NOT EXISTS `user_sales_report` (
`Product` varchar(500)
,`User` text
,`Outlet` varchar(100)
,`CREATED_DATE` date
,`Revenue` decimal(53,2)
,`Revenue_tax_incl` decimal(54,2)
,`Cost_of_Goods` decimal(52,2)
,`Gross_Profit` decimal(53,2)
,`Margin` decimal(52,6)
,`Items_Sold` decimal(32,0)
,`Tax` decimal(42,2)
,`COMPANY_ASSOCIATION_ID` int(11)
,`OUTLET_ASSOCICATION_ID` int(11)
);
-- --------------------------------------------------------

--
-- Table structure for table `variant_attribute`
--

DROP TABLE IF EXISTS `variant_attribute`;
CREATE TABLE IF NOT EXISTS `variant_attribute` (
  `VARIANT_ATTRIBUTE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `VARIANT_ATTRIBUTEcol` varchar(45) DEFAULT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `VARIANT_ATTRIBUTE_ASSOCIATION_ID` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`VARIANT_ATTRIBUTE_ID`),
  UNIQUE KEY `unique_index` (`ATTRIBUTE_NAME`,`COMPANY_ASSOCIATION_ID`),
  KEY `VARIANT_ATTRIBUTE_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `VARIANT_ATTRIBUTE_CREATED_BY_FK` (`CREATED_BY`),
  KEY `VARIANT_ATTRIBUTE_UPDATED_BY_FK` (`UPDATED_BY`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `variant_attribute_values`
--

DROP TABLE IF EXISTS `variant_attribute_values`;
CREATE TABLE IF NOT EXISTS `variant_attribute_values` (
  `VARIANT_ATTRIBUTE_VALUES_ID` int(11) NOT NULL AUTO_INCREMENT,
  `VARIANT_ATTRIBUTE_ASSOCICATION_ID` int(11) NOT NULL,
  `ATTRIBUTE_VALUE` varchar(200) NOT NULL,
  `PRODUCT_VARIANT_ASSOCICATION_ID` int(11) NOT NULL,
  `PRODUCT_ASSOCIATION_ID` int(11) NOT NULL,
  `PRODUCT_UUID` varchar(500) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY (`VARIANT_ATTRIBUTE_VALUES_ID`),
  KEY `VARIANT_ATTRIBUTE_VALUES_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `VARIANT_ATTRIBUTE_VALUES_PRODUCT_VARIANT_ASSOCICATION_ID_FK` (`PRODUCT_VARIANT_ASSOCICATION_ID`),
  KEY `VARIANT_ATTRIBUTE_VALUES_CREATED_BY_FK` (`CREATED_BY`),
  KEY `VARIANT_ATTRIBUTE_VALUES_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `VARIANT_ATTRIBUTE_VALUES_PRODUCT_ASSOCIATION_ID_FK` (`PRODUCT_ASSOCIATION_ID`),
  KEY `VARIANT_ATTRIBUTE_VALUES_VARIANT_ATTRIBUTE_ASSOCICATION_ID_FK` (`VARIANT_ATTRIBUTE_ASSOCICATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `web_activity_detail`
--

DROP TABLE IF EXISTS `web_activity_detail`;
CREATE TABLE IF NOT EXISTS `web_activity_detail` (
  `WEB_ACTIVITY_DETAIL_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMPLOYEE_ASSOCIATION_ID` int(11) DEFAULT NULL,
  `EMPLOYEE_NAME` varchar(256) DEFAULT NULL,
  `EMLOYEE_EMAIL` varchar(256) DEFAULT NULL,
  `CREATED_BY_MANAGER_ID` int(11) DEFAULT NULL,
  `ACTIVITY_DETAIL` varchar(256) DEFAULT NULL,
  `CREATED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `IP_ADDRESS` varchar(256) DEFAULT NULL,
  `BROWSER_NAME` varchar(256) DEFAULT NULL,
  `BROWSER_VERSION` varchar(256) DEFAULT NULL,
  `OPERATING_SYSTEM` varchar(256) DEFAULT NULL,
  `DEVICE_TYPE` varchar(256) DEFAULT NULL,
  `SESSION_ID` varchar(256) DEFAULT NULL,
  `SEVERITY_ASSOCIATION_ID` int(11) DEFAULT NULL,
  `OTHER_INFORMATION` blob,
  `IS_EXCEPTION` varchar(10) DEFAULT 'false',
  PRIMARY KEY (`WEB_ACTIVITY_DETAIL_ID`),
  KEY `WEB_ACTIVITY_DETAIL_EMPLOYEE_ASSOCIATION_ID_FK` (`EMPLOYEE_ASSOCIATION_ID`),
  KEY `WEB_ACTIVITY_DETAIL_CREATTED_BY_MANAGER_ID_FK` (`CREATED_BY_MANAGER_ID`),
  KEY `WEB_ACTIVITY_DETAIL_SEVERITY_ASSOCIATION_ID_FK` (`SEVERITY_ASSOCIATION_ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

-- --------------------------------------------------------

--
-- Structure for view `Brand_Sales_Report`
--
DROP TABLE IF EXISTS `Brand_Sales_Report`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `Brand_Sales_Report` AS select (select `product`.`PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,(select `outlet`.`OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,(select `brand`.`BRAND_NAME` from `brand` where (`brand`.`BRAND_ID` = (select `product`.`BRAND_ASSOCICATION_ID` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)))) AS `Brand`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Revenue`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date);

-- --------------------------------------------------------

--
-- Structure for view `contacts_summmary`
--
DROP TABLE IF EXISTS `contacts_summmary`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `contacts_summmary` AS select `contact`.`CONTACT_ID` AS `CONTACT_ID`,coalesce(concat(`contact`.`FIRST_NAME`,' ',coalesce(`contact`.`LAST_NAME`,'')),'-') AS `CONTACT_NAME`,coalesce(`contact`.`FIRST_NAME`,' ') AS `FIRST_NAME`,coalesce(`contact`.`LAST_NAME`,' ') AS `LAST_NAME`,coalesce(`contact`.`COMPANY_NAME`,'-') AS `COMPANY_NAME`,coalesce(`contact`.`CONTACT_BALANCE`,0) AS `BALANCE`,coalesce((select coalesce(`address`.`PHONE`,'##') from `address` where ((`contact`.`CONTACT_ID` = `address`.`CONTACT_ASSOCICATION_ID`) and (`address`.`ADDRESS_TYPE` = 'Physical Address'))),'-') AS `PHONE`,`contact`.`CREATED_DATE` AS `CREATED_DATE`,`contact`.`ACTIVE_INDICATOR` AS `ACTIVE_INDICATOR`,`contact`.`OUTLET_ASSOCIATION_ID` AS `OUTLET_ASSOCIATION_ID`,`contact`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`contact`.`LAST_UPDATED` AS `LAST_UPDATED`,(select `contact_group`.`CONTACT_GROUP_NAME` from `contact_group` where (`contact`.`CONTACT_GROUP_ASSOCIATION_ID` = `contact_group`.`CONTACT_GROUP_ID`)) AS `CONTACT_GROUP`,`contact`.`CONTACT_TYPE` AS `CONTACT_TYPE` from `contact` order by `contact`.`CREATED_DATE` desc;

-- --------------------------------------------------------

--
-- Structure for view `customer_group_sales_report`
--
DROP TABLE IF EXISTS `customer_group_sales_report`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `customer_group_sales_report` AS select (select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,coalesce((select `contact_group`.`CONTACT_GROUP_NAME` AS `CONTACT_GROUP_NAME` from `contact_group` where (`contact_group`.`CONTACT_GROUP_ID` = (select `contact`.`CONTACT_GROUP_ASSOCIATION_ID` AS `CONTACT_GROUP_ASSOCIATION_ID` from `contact` where ((`contact`.`CONTACT_TYPE` like _latin1'%CUSTOMER%') and (`contact`.`CONTACT_ID` = (select `invoice_main`.`CONTACT_ASSOCIATION_ID` AS `CONTACT_ASSOCIATION_ID` from `invoice_main` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))))),_latin1'-') AS `Group_Name`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0))) AS `Revenue`,(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0))) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) group by coalesce((select `contact_group`.`CONTACT_GROUP_NAME` AS `CONTACT_GROUP_NAME` from `contact_group` where (`contact_group`.`CONTACT_GROUP_ID` = (select `contact`.`CONTACT_GROUP_ASSOCIATION_ID` AS `CONTACT_GROUP_ASSOCIATION_ID` from `contact` where ((`contact`.`CONTACT_TYPE` like _latin1'%CUSTOMER%') and (`contact`.`CONTACT_ID` = (select `invoice_main`.`CONTACT_ASSOCIATION_ID` AS `CONTACT_ASSOCIATION_ID` from `invoice_main` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))))),_latin1'-'),cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date);

-- --------------------------------------------------------

--
-- Structure for view `customer_sales_report`
--
DROP TABLE IF EXISTS `customer_sales_report`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `customer_sales_report` AS select (select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,coalesce((select `contact`.`CONTACT_NAME` AS `CONTACT_NAME` from `contact` where ((`contact`.`CONTACT_TYPE` like _latin1'%CUSTOMER%') and (`contact`.`CONTACT_ID` = (select `invoice_main`.`CONTACT_ASSOCIATION_ID` AS `CONTACT_ASSOCIATION_ID` from `invoice_main` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))),_latin1'-') AS `Customer`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0))) AS `Revenue`,(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0))) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) group by coalesce((select `contact`.`CONTACT_NAME` AS `CONTACT_NAME` from `contact` where ((`contact`.`CONTACT_TYPE` like _latin1'%CUSTOMER%') and (`contact`.`CONTACT_ID` = (select `invoice_main`.`CONTACT_ASSOCIATION_ID` AS `CONTACT_ASSOCIATION_ID` from `invoice_main` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))),_latin1'-'),cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date);

-- --------------------------------------------------------

--
-- Structure for view `expenseReport`
--
DROP TABLE IF EXISTS `expenseReport`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `expenseReport` AS select (select `expense_type`.`EXPENSE_TYPE_NAME` from `expense_type` where (`cash_managment`.`EXPENSE_TYPE_ASSOCIATION_ID` = `expense_type`.`EXPENSE_TYPE_ID`)) AS `EXPENSE`,(select concat(`user`.`FIRST_NAME`,' ',`user`.`LAST_NAME`) from `user` where (`user`.`USER_ID` = `cash_managment`.`CREATED_BY`)) AS `User`,(select `outlet`.`OUTLET_NAME` from `outlet` where (`cash_managment`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,cast(`cash_managment`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum(`cash_managment`.`CASH_AMT`) AS `CASH_AMT`,`cash_managment`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`cash_managment`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID`,`cash_managment`.`CASH_MANAGMENT_TYPE` AS `Type` from `cash_managment` group by `cash_managment`.`EXPENSE_TYPE_ASSOCIATION_ID`,cast(`cash_managment`.`CREATED_DATE` as date),`cash_managment`.`CASH_MANAGMENT_TYPE` order by cast(`cash_managment`.`CREATED_DATE` as date);

-- --------------------------------------------------------

--
-- Structure for view `Inventory_Report`
--
DROP TABLE IF EXISTS `Inventory_Report`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `Inventory_Report` AS (select `product`.`PRODUCT_ID` AS `ID`,`product`.`PRODUCT_NAME` AS `PRODUCT_NAME`,`product`.`SKU` AS `SKU`,`product`.`SUPPLY_PRICE_EXCL_TAX` AS `SUPPLY_PRICE_EXCL_TAX`,`product`.`MARKUP_PRCT` AS `MARKUP_PRCT`,coalesce(`product`.`REORDER_POINT`,0) AS `REORDER_POINT`,coalesce(`product`.`REORDER_AMOUNT`,0) AS `REORDER_AMOUNT`,`product`.`CURRENT_INVENTORY` AS `CURRENT_INVENTORY`,(((`product`.`SUPPLY_PRICE_EXCL_TAX` * `product`.`MARKUP_PRCT`) / 100) + `product`.`SUPPLY_PRICE_EXCL_TAX`) AS `NET_PRICE`,(select `outlet`.`OUTLET_NAME` from `outlet` where (`product`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `OUTLET_NAME`,`product`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID`,`product`.`CREATED_DATE` AS `CREATED_DATE`,(select `brand`.`BRAND_NAME` from `brand` where (`product`.`BRAND_ASSOCICATION_ID` = `brand`.`BRAND_ID`)) AS `BRAND_NAME`,(select `product_type`.`PRODUCT_TYPE_NAME` from `product_type` where (`product`.`PRODUCT_TYPE_ASSOCICATION_ID` = `product_type`.`PRODUCT_TYPE_ID`)) AS `PRODUCT_TYPE`,(select `contact`.`CONTACT_NAME` from `contact` where (`product`.`CONTACT_ASSOCICATION_ID` = `contact`.`CONTACT_ID`)) AS `CONTACT_NAME`,`product`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,(`product`.`SUPPLY_PRICE_EXCL_TAX` * `product`.`CURRENT_INVENTORY`) AS `STOCK_VALUE`,((((`product`.`SUPPLY_PRICE_EXCL_TAX` * `product`.`MARKUP_PRCT`) / 100) + `product`.`SUPPLY_PRICE_EXCL_TAX`) * `product`.`CURRENT_INVENTORY`) AS `RETAIL_VALUE` from `product` where ((`product`.`VARIANT_PRODUCTS` = 'false') and (`product`.`ACTIVE_INDICATOR` = 1)) group by `product`.`PRODUCT_UUID`,`product`.`OUTLET_ASSOCICATION_ID` order by `CREATED_DATE` desc) union (select `product_variant`.`PRODUCT_VARIANT_ID` AS `PRODUCT_VARIANT_ID`,concat((select `product`.`PRODUCT_NAME` from `product` where (`product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`)),' ',`product_variant`.`VARIANT_ATTRIBUTE_NAME`) AS `VARIANT_ATTRIBUTE_NAME`,`product_variant`.`SKU` AS `SKU`,`product_variant`.`SUPPLY_PRICE_EXCL_TAX` AS `SUPPLY_PRICE_EXCL_TAX`,`product_variant`.`MARKUP_PRCT` AS `MARKUP_PRCT`,coalesce(`product_variant`.`REORDER_POINT`,0) AS `COALESCE(REORDER_POINT,0)`,coalesce(`product_variant`.`REORDER_AMOUNT`,0) AS `COALESCE(REORDER_AMOUNT,0)`,`product_variant`.`CURRENT_INVENTORY` AS `CURRENT_INVENTORY`,(((`product_variant`.`SUPPLY_PRICE_EXCL_TAX` * `product_variant`.`MARKUP_PRCT`) / 100) + `product_variant`.`SUPPLY_PRICE_EXCL_TAX`) AS `NET_PRICE`,(select `outlet`.`OUTLET_NAME` from `outlet` where (`product_variant`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `OUTLET_NAME`,`product_variant`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID`,`product_variant`.`CREATED_DATE` AS `CREATED_DATE`,(select `brand`.`BRAND_NAME` from `brand` where (`brand`.`BRAND_ID` = (select `product`.`BRAND_ASSOCICATION_ID` from `product` where (`product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`)))) AS `BRAND_NAME`,(select `product_type`.`PRODUCT_TYPE_NAME` from `product_type` where (`product_type`.`PRODUCT_TYPE_ID` = (select `product`.`PRODUCT_TYPE_ASSOCICATION_ID` from `product` where (`product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`)))) AS `PRODUCT_TYPE`,(select `contact`.`CONTACT_NAME` from `contact` where (`contact`.`CONTACT_ID` = (select `product`.`CONTACT_ASSOCICATION_ID` from `product` where (`product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`)))) AS `CONTACT_NAME`,`product_variant`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,(`product_variant`.`SUPPLY_PRICE_EXCL_TAX` * `product_variant`.`CURRENT_INVENTORY`) AS `STOCK_VALUE`,((((`product_variant`.`SUPPLY_PRICE_EXCL_TAX` * `product_variant`.`MARKUP_PRCT`) / 100) + `product_variant`.`SUPPLY_PRICE_EXCL_TAX`) * `product_variant`.`CURRENT_INVENTORY`) AS `RETAIL_VALUE` from `product_variant` where (`product_variant`.`ACTIVE_INDICATOR` = 1) group by `product_variant`.`PRODUCT_VARIANT_UUID`,`product_variant`.`OUTLET_ASSOCICATION_ID` order by `CREATED_DATE` desc);

-- --------------------------------------------------------

--
-- Structure for view `Outlet_Sales_Report`
--
DROP TABLE IF EXISTS `Outlet_Sales_Report`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `Outlet_Sales_Report` AS select (select `product`.`PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,(select `outlet`.`OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Revenue`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date);

-- --------------------------------------------------------

--
-- Structure for view `Payment_Report`
--
DROP TABLE IF EXISTS `Payment_Report`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `Payment_Report` AS select `invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID`,(select `outlet`.`OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `OUTLET`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum(`invoice_main`.`INVOICE_NET_AMT`) AS `AMOUNT`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID` from `invoice_main` where (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) group by `invoice_main`.`COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date);

-- --------------------------------------------------------

--
-- Structure for view `Price_Book_Detail_Summary`
--
DROP TABLE IF EXISTS `Price_Book_Detail_Summary`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `Price_Book_Detail_Summary` AS select `price_book_detail`.`PRICE_BOOK_DETAIL_ID` AS `PRICE_BOOK_DETAIL_ID`,`price_book_detail`.`PRICE_BOOK_ASSOCICATION_ID` AS `PRICE_BOOK_ASSOCICATION_ID`,`price_book_detail`.`PRODUCT_ASSOCICATION_ID` AS `PRODUCT_ASSOCICATION_ID`,`price_book_detail`.`UUID` AS `UUID`,(select `product`.`PRODUCT_NAME` from `product` where (`product`.`PRODUCT_ID` = `price_book_detail`.`PRODUCT_ASSOCICATION_ID`)) AS `PRODUCT_NAME`,ifnull(`price_book_detail`.`PRODUCT_VARIENT_ASSOCICATION_ID`,0) AS `PRODUCT_VARIENT_ASSOCICATION_ID`,coalesce((select `product_variant`.`VARIANT_ATTRIBUTE_NAME` from `product_variant` where (`product_variant`.`PRODUCT_VARIANT_ID` = `price_book_detail`.`PRODUCT_VARIENT_ASSOCICATION_ID`)),'-') AS `VARIANT_ATTRIBUTE_NAME`,`price_book_detail`.`SUPPLY_PRICE` AS `SUPPLY_PRICE`,`price_book_detail`.`MARKUP` AS `MARKUP`,`price_book_detail`.`DISCOUNT` AS `DISCOUNT`,`price_book_detail`.`CREATED_BY` AS `CREATED_BY`,`price_book_detail`.`UPDATED_BY` AS `UPDATED_BY`,`price_book_detail`.`SALES_TAX_ASSOCIATION_ID` AS `SALES_TAX_ASSOCIATION_ID`,`price_book_detail`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID` from `price_book_detail` where (`price_book_detail`.`ACTIVE_INDICATOR` = '1');

-- --------------------------------------------------------


--
-- Structure for view `producttype_sales_report`
--
DROP TABLE IF EXISTS `producttype_sales_report`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `producttype_sales_report` AS select (select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,(select `product_type`.`PRODUCT_TYPE_NAME` AS `PRODUCT_TYPE_NAME` from `product_type` where (`product_type`.`PRODUCT_TYPE_ID` = (select `product`.`PRODUCT_TYPE_ASSOCICATION_ID` AS `PRODUCT_TYPE_ASSOCICATION_ID` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)))) AS `ProductType`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0))) AS `Revenue`,(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0))) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date);

-- --------------------------------------------------------

--
-- Structure for view `product_summmary`
--
DROP TABLE IF EXISTS `product_summmary`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `product_summmary` AS select `product`.`PRODUCT_ID` AS `ID`,`product`.`PRODUCT_NAME` AS `PRODUCT_NAME`,`product`.`SKU` AS `SKU`,`product`.`SUPPLY_PRICE_EXCL_TAX` AS `SUPPLY_PRICE_EXCL_TAX`,`product`.`REORDER_POINT` AS `REORDER_POINT`,`product`.`REORDER_AMOUNT` AS `REORDER_AMOUNT`,`product`.`CURRENT_INVENTORY` AS `CURRENT_INVENTORY`,(((`product`.`SUPPLY_PRICE_EXCL_TAX` * `product`.`MARKUP_PRCT`) / 100) + `product`.`SUPPLY_PRICE_EXCL_TAX`) AS `NET_PRICE`,(select `outlet`.`OUTLET_NAME` from `outlet` where (`product`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `OUTLET_NAME`,`product`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID`,`product`.`CREATED_DATE` AS `CREATED_DATE`,(select `brand`.`BRAND_NAME` from `brand` where (`product`.`BRAND_ASSOCICATION_ID` = `brand`.`BRAND_ID`)) AS `BRAND_NAME`,(select `product_type`.`PRODUCT_TYPE_NAME` from `product_type` where (`product`.`PRODUCT_TYPE_ASSOCICATION_ID` = `product_type`.`PRODUCT_TYPE_ID`)) AS `PRODUCT_TYPE`,(select `contact`.`CONTACT_NAME` from `contact` where (`product`.`CONTACT_ASSOCICATION_ID` = `contact`.`CONTACT_ID`)) AS `CONTACT_NAME`,(select count(0) from `product_variant` where ((`product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`) and (`product_variant`.`ACTIVE_INDICATOR` = 1))) AS `VARIANT_COUNT`,`product`.`CURRENT_INVENTORY` AS `VARIANT_CURRENT_INVENTORY`,`product`.`SKU` AS `VARIANT_SKU`,`product`.`SUPPLY_PRICE_EXCL_TAX` AS `VARIANT_SUPPLY_PRICE_EXCL_TAX`,`product`.`REORDER_POINT` AS `VARIANT_REORDER_POINT`,`product`.`REORDER_AMOUNT` AS `VARIANT_REORDER_AMOUNT`,(((`product`.`SUPPLY_PRICE_EXCL_TAX` * `product`.`MARKUP_PRCT`) / 100) + `product`.`SUPPLY_PRICE_EXCL_TAX`) AS `VARIANT_NET_PRICE`,(select count(0) from `composite_product` where ((`composite_product`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`) and (`composite_product`.`ACTIVE_INDICATOR` = 1))) AS `VARIANT_COMP_COUNT`,`product`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`product`.`IMAGE_PATH` AS `IMAGE_PATH`,`product`.`VARIANT_PRODUCTS` AS `VARIANT_PRODUCTS`,`product`.`STANDARD_PRODUCT` AS `STANDARD_PRODUCT`,(select coalesce(sum(`product_variant`.`CURRENT_INVENTORY`),0) from `product_variant` where ((`product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`) and (`product_variant`.`ACTIVE_INDICATOR` = 1)) having (`product`.`CURRENT_INVENTORY` is not null)) AS `VARIANT_INVENTORY_COUNT` from `product` group by `product`.`PRODUCT_UUID`,`product`.`OUTLET_ASSOCICATION_ID` order by `product`.`CREATED_DATE` desc;

-- --------------------------------------------------------

--
-- Structure for view `Register_Report`
--
DROP TABLE IF EXISTS `Register_Report`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `Register_Report` AS select `daily_register`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID`,(select `outlet`.`OUTLET_NAME` from `outlet` where (`daily_register`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `OUTLET_NAME`,ifnull(`daily_register`.`CASH_AMT_ACTUAL`,0) AS `CASH_AMT_ACTUAL`,ifnull(`daily_register`.`CREDIT_CARD_AMT_ACTUAL`,0) AS `CREDIT_CARD_AMT_ACTUAL`,date_format(`daily_register`.`CREATED_DATE`,'%b %d %Y %h:%i %p') AS `OPENING_DATE`,date_format(`daily_register`.`CLOSED_DATE`,'%b %d %Y %h:%i %p') AS `CLOSING_DATE`,(select concat(`user`.`FIRST_NAME`,' ',`user`.`LAST_NAME`) from `user` where (`user`.`USER_ID` = `daily_register`.`CREATED_BY`)) AS `Open_By`,coalesce((select concat(`user`.`FIRST_NAME`,' ',`user`.`LAST_NAME`) from `user` where (`user`.`USER_ID` = `daily_register`.`UPDATED_BY`)),`daily_register`.`CREATED_BY`) AS `Close_By`,(select `status`.`STATUS_DESC` from `status` where (`status`.`STATUS_ID` = `daily_register`.`STATUS_ASSOCICATION_ID`)) AS `Status`,`daily_register`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID` from `daily_register` order by cast(`daily_register`.`CREATED_DATE` as date);

-- --------------------------------------------------------


--
-- Structure for view `salereport_withoutsale`
--
DROP TABLE IF EXISTS `salereport_withoutsale`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `salereport_withoutsale` AS select (select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0))) AS `Revenue`,(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0))) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where ((`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) and (`invoice_detail`.`ITEM_DISCOUNT_PRCT` = 0)) group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date);

-- --------------------------------------------------------


--
-- Structure for view `SaleReport_withSale`
--
DROP TABLE IF EXISTS `SaleReport_withSale`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `SaleReport_withSale` AS select (select `product`.`PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,(select `outlet`.`OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Revenue`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where ((`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) and (`invoice_detail`.`ITEM_DISCOUNT_PRCT` > 0)) group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date);

-- --------------------------------------------------------

--
-- Structure for view `Sale_Details`
--
DROP TABLE IF EXISTS `Sale_Details`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `Sale_Details` AS select `product`.`PRODUCT_NAME` AS `Product`,`product_variant`.`VARIANT_ATTRIBUTE_NAME` AS `Variant`,(select `outlet`.`OUTLET_NAME` from `outlet` where (`invoice_detail`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,cast(`invoice_detail`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Revenue`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,(100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)) AS `Margin`,sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_detail`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_detail`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from ((`invoice_detail` left join `product_variant` on((`product_variant`.`PRODUCT_VARIANT_ID` = `invoice_detail`.`PRODUCT_VARIENT_ASSOCIATION_ID`))) left join `product` on((`product`.`PRODUCT_ID` = `invoice_detail`.`PRODUCT_ASSOCIATION_ID`))) group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,`invoice_detail`.`PRODUCT_VARIENT_ASSOCIATION_ID`,cast(`invoice_detail`.`CREATED_DATE` as date) order by cast(`invoice_detail`.`CREATED_DATE` as date);

-- --------------------------------------------------------

--
-- Structure for view `sms_report`
--
DROP TABLE IF EXISTS `sms_report`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `sms_report` AS select `message_detail`.`MESSAGE_DETAIL_ID` AS `MESSAGE_DETAIL_ID`,`message_detail`.`SENDER_ID` AS `SENDER_ID`,`message_detail`.`RECEIVER_ID` AS `RECEIVER_ID`,`message_detail`.`MESSAGE_DESCRIPTION` AS `MESSAGE_DESCRIPTION`,`message_detail`.`DELIVERY_ID` AS `DELIVERY_ID`,`message_detail`.`DELIVERY_STATUS` AS `DELIVERY_STATUS`,`message_detail`.`MESSAGE_ASSOCIATION_ID` AS `MESSAGE_ASSOCIATION_ID`,(select `message`.`MESSAGE_BUNDLE_COUNT` from `message` where (`message_detail`.`MESSAGE_ASSOCIATION_ID` = `message`.`MESSAGE_ID`)) AS `MESSAGE_BUNDLE_COUNT`,`message_detail`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID`,(select `outlet`.`OUTLET_NAME` from `outlet` where (`message_detail`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `OUTLET_NAME`,date_format(`message_detail`.`CREATED_DATE`,'%b %d %Y %h:%i %p') AS `SMS_SENT_DATE`,cast(`message_detail`.`CREATED_DATE` as date) AS `CREATED_DATE`,(select concat(`user`.`FIRST_NAME`,' ',`user`.`LAST_NAME`) from `user` where (`user`.`USER_ID` = `message_detail`.`CREATED_BY`)) AS `SENDER_NAME`,(select `contact`.`FIRST_NAME` from `contact` where (`contact`.`CONTACT_ID` = `message_detail`.`RECEIVER_ID`)) AS `CUSTOMER_NAME`,`message_detail`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID` from `message_detail` order by cast(`message_detail`.`CREATED_DATE` as date);

-- --------------------------------------------------------

--
-- Structure for view `supplier_sales_report`
--
DROP TABLE IF EXISTS `supplier_sales_report`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `supplier_sales_report` AS select (select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,(select `contact`.`CONTACT_NAME` AS `CONTACT_NAME` from `contact` where ((`contact`.`CONTACT_TYPE` like _latin1'%SUPPLIER%') and (`contact`.`CONTACT_ID` = (select `product`.`CONTACT_ASSOCICATION_ID` AS `CONTACT_ASSOCICATION_ID` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`))))) AS `Supplier`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0))) AS `Revenue`,(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0))) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) group by (select `contact`.`CONTACT_NAME` AS `CONTACT_NAME` from `contact` where ((`contact`.`CONTACT_TYPE` like _latin1'%SUPPLIER%') and (`contact`.`CONTACT_ID` = (select `product`.`CONTACT_ASSOCICATION_ID` AS `CONTACT_ASSOCICATION_ID` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`))))),cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date);

-- --------------------------------------------------------


--
-- Structure for view `Temp_Sale`
--
DROP TABLE IF EXISTS `Temp_Sale`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `Temp_Sale` AS select (select `product`.`PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,(select `outlet`.`OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Revenue`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date);

-- --------------------------------------------------------

--
-- Structure for view `user_sales_report`
--
DROP TABLE IF EXISTS `user_sales_report`;

CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `user_sales_report` AS select (select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,(select concat(`user`.`FIRST_NAME`,_latin1' ',`user`.`LAST_NAME`) AS `concat(``user``.``FIRST_NAME``,' ',``user``.``LAST_NAME``)` from `user` where (`user`.`USER_ID` = `invoice_detail`.`CREATED_BY`)) AS `User`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0))) AS `Revenue`,(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0))) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account_type`
--
ALTER TABLE `account_type`
  ADD CONSTRAINT `ACCOUNT_TYPE_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `ACCOUNT_TYPE_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `ACCOUNT_TYPE_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`);

--
-- Constraints for table `activity_detail`
--
ALTER TABLE `activity_detail`
  ADD CONSTRAINT `ACTIVITY_DETAIL_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `ACTIVITY_DETAIL_CREATTED_BY_MANAGER_ID_FK` FOREIGN KEY (`CREATED_BY_MANAGER_ID`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `ACTIVITY_DETAIL_EMPLOYEE_ASSOCIATION_ID_FK` FOREIGN KEY (`EMPLOYEE_ASSOCIATION_ID`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `ACTIVITY_DETAIL_SEVERITY_ASSOCIATION_ID_FK` FOREIGN KEY (`SEVERITY_ASSOCIATION_ID`) REFERENCES `severity` (`SEVERITY_ID`);

--
-- Constraints for table `address`
--
ALTER TABLE `address`
  ADD CONSTRAINT `ADDRESS_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `ADDRESS_CONTACT_ASSOCICATION_ID_FK` FOREIGN KEY (`CONTACT_ASSOCICATION_ID`) REFERENCES `contact` (`CONTACT_ID`),
  ADD CONSTRAINT `ADDRESS_COUNTRY_ASSOCICATION_ID_FK` FOREIGN KEY (`COUNTRY_ASSOCICATION_ID`) REFERENCES `country` (`COUNTRY_ID`);

--
-- Constraints for table `announcement`
--
ALTER TABLE `announcement`
  ADD CONSTRAINT `ANNOUNCEMENT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `ANNOUNCEMENT_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `ANNOUNCEMENT_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`);

--
-- Constraints for table `bank_account`
--
ALTER TABLE `bank_account`
  ADD CONSTRAINT `BANK_ACCOUNT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `BANK_ACCOUNT_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `BANK_ACCOUNT_CURRENCY_ASSOCICATION_ID_FK` FOREIGN KEY (`CURRENCY_ASSOCICATION_ID`) REFERENCES `currency` (`CURRENCY_ID`),
  ADD CONSTRAINT `BANK_ACCOUNT_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`);

--
-- Constraints for table `brand`
--
ALTER TABLE `brand`
  ADD CONSTRAINT `BRAND_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`);

--
-- Constraints for table `cash_managment`
--
ALTER TABLE `cash_managment`
  ADD CONSTRAINT `EXPENSE_TYPE_ASSOCIATION_ID_FK` FOREIGN KEY (`EXPENSE_TYPE_ASSOCIATION_ID`) REFERENCES `expense_type` (`EXPENSE_TYPE_ID`),
  ADD CONSTRAINT `CASH_MANAGMENT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `CASH_MANAGMENT_DAILY_REGISTER_ASSOCICATION_ID_FK` FOREIGN KEY (`DAILY_REGISTER_ASSOCICATION_ID`) REFERENCES `daily_register` (`DAILY_REGISTER_ID`),
  ADD CONSTRAINT `CASH_MANAGMENT_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`);

--
-- Constraints for table `chart_of_account`
--
ALTER TABLE `chart_of_account`
  ADD CONSTRAINT `CHART_OF_ACCOUNT_ACCOUNT_TYPE_ASSOCIATION_ID_FK` FOREIGN KEY (`ACCOUNT_TYPE_ASSOCIATION_ID`) REFERENCES `account_type` (`ACCOUNT_TYPE_ID`),
  ADD CONSTRAINT `CHART_OF_ACCOUNT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `CHART_OF_ACCOUNT_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `CHART_OF_ACCOUNT_SALES_TAX_ASSOCIATION_ID_FK` FOREIGN KEY (`SALES_TAX_ASSOCIATION_ID`) REFERENCES `sales_tax` (`SALES_TAX_ID`),
  ADD CONSTRAINT `CHART_OF_ACCOUNT_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`);

--
-- Constraints for table `company`
--
ALTER TABLE `company`
  ADD CONSTRAINT `COMPANY_CURRENCY_ASSOCICATION_ID_FK` FOREIGN KEY (`CURRENCY_ASSOCICATION_ID`) REFERENCES `currency` (`CURRENCY_ID`),
  ADD CONSTRAINT `COMPANY_PRINTER_ASSOCICATION_ID_FK` FOREIGN KEY (`PRINTER_ASSOCICATION_ID`) REFERENCES `printer_format` (`PRINTER_FORMAT_ID`),
  ADD CONSTRAINT `COMPANY_TIME_ZONE_ASSOCICATION_ID_FK` FOREIGN KEY (`TIME_ZONE_ASSOCICATION_ID`) REFERENCES `time_zone` (`TIME_ZONE_ID`);

--
-- Constraints for table `composite_product`
--
ALTER TABLE `composite_product`
  ADD CONSTRAINT `COMPOSITE_PRODUCT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `COMPOSITE_PRODUCT_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `COMPOSITE_PRODUCT_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  ADD CONSTRAINT `COMPOSITE_PRODUCT_PRODUCT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCICATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  ADD CONSTRAINT `COMPOSITE_PRODUCT_PRODUCT_VARIANT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_VARIANT_ASSOCICATION_ID`) REFERENCES `product_variant` (`PRODUCT_VARIANT_ID`),
  ADD CONSTRAINT `COMPOSITE_PRODUCT_SELECTIVE_PRODUCT_ASSOCIATION_ID_FK` FOREIGN KEY (`SELECTIVE_PRODUCT_ASSOCIATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  ADD CONSTRAINT `COMPOSITE_PRODUCT_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`);

--
-- Constraints for table `configuration`
--
ALTER TABLE `configuration`
  ADD CONSTRAINT `CONFIGURATION_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `CONFIGURATION_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `CONFIGURATION_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`);

--
-- Constraints for table `contact`
--
ALTER TABLE `contact`
  ADD CONSTRAINT `CONTACT_OUTLET_ASSOCIATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCIATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  ADD CONSTRAINT `CONTACT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `CONTACT_CONTACT_GROUP_ASSOCIATION_ID_FK` FOREIGN KEY (`CONTACT_GROUP_ASSOCIATION_ID`) REFERENCES `contact_group` (`CONTACT_GROUP_ID`);

--
-- Constraints for table `contact_group`
--
ALTER TABLE `contact_group`
  ADD CONSTRAINT `CONTACT_GROUP_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `CONTACT_GROUP_COUNTRY_ASSOCICATION_ID_FK` FOREIGN KEY (`COUNTRY_ASSOCICATION_ID`) REFERENCES `country` (`COUNTRY_ID`);

--
-- Constraints for table `contact_payments`
--
ALTER TABLE `contact_payments`
  ADD CONSTRAINT `CONTACT_PAYMENT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `CONTACT_PAYMENT_CONTACT_ASSOCICATION_ID_FK` FOREIGN KEY (`CONTACT_ASSOCICATION_ID`) REFERENCES `contact` (`CONTACT_ID`),
  ADD CONSTRAINT `CONTACT_PAYMENT_TYPE_ASSOCICATION_ID_FK` FOREIGN KEY (`CONTACT_PAYMENT_TYPE_ASSOCICATION_ID`) REFERENCES `contact_payments_type` (`CONTACT_PAYMENT_TYPE_ID`);

--
-- Constraints for table `contact_us`
--
ALTER TABLE `contact_us`
  ADD CONSTRAINT `CONTACT_US_WEB_ACTIVITY_DETAIL_ID_FK` FOREIGN KEY (`WEB_ACTIVITY_DETAIL_ID`) REFERENCES `web_activity_detail` (`WEB_ACTIVITY_DETAIL_ID`);

--
-- Constraints for table `daily_register`
--
ALTER TABLE `daily_register`
  ADD CONSTRAINT `DAILY_REGISTER_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `DAILY_REGISTER_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  ADD CONSTRAINT `DAILY_REGISTER_REGISTER_ASSOCICATION_ID_FK` FOREIGN KEY (`REGISTER_ASSOCICATION_ID`) REFERENCES `register` (`REGISTER_ID`),
  ADD CONSTRAINT `DAILY_REGISTER_STATUS_ASSOCICATION_ID_FK` FOREIGN KEY (`STATUS_ASSOCICATION_ID`) REFERENCES `status` (`STATUS_ID`);

--
-- Constraints for table `expense_type`
--
ALTER TABLE `expense_type`
  ADD CONSTRAINT `EXPENSE_TYPE_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`);

--
-- Constraints for table `general_ledger`
--
ALTER TABLE `general_ledger`
  ADD CONSTRAINT `GENERAL_LEDGER_CHART_OF_ACCOUNT_ASSOCIATION_ID_FK` FOREIGN KEY (`CHART_OF_ACCOUNT_ASSOCIATION_ID`) REFERENCES `chart_of_account` (`CHART_OF_ACCOUNT_ID`),
  ADD CONSTRAINT `GENERAL_LEDGER_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `GENERAL_LEDGER_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `GENERAL_LEDGER_GL_EVENT_ASSOCIATION_ID_FK` FOREIGN KEY (`GL_EVENT_ASSOCIATION_ID`) REFERENCES `general_ledger_event` (`GENERAL_LEDGER_EVENT_ID`),
  ADD CONSTRAINT `GENERAL_LEDGER_GL_SUB_EVENT_ASSOCIATION_ID_FK` FOREIGN KEY (`GL_SUB_EVENT_ASSOCIATION_ID`) REFERENCES `general_ledger_event` (`GENERAL_LEDGER_EVENT_ID`),
  ADD CONSTRAINT `GENERAL_LEDGER_MODULE_ASSOCIATION_ID_FK` FOREIGN KEY (`MODULE_ASSOCIATION_ID`) REFERENCES `module` (`MODULE_ID`),
  ADD CONSTRAINT `GENERAL_LEDGER_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`);

--
-- Constraints for table `general_ledger_event`
--
ALTER TABLE `general_ledger_event`
  ADD CONSTRAINT `GENERAL_LEDGER_EVENT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `GENERAL_LEDGER_EVENT_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `GENERAL_LEDGER_EVENT_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`);

--
-- Constraints for table `gl_trans_config`
--
ALTER TABLE `gl_trans_config`
  ADD CONSTRAINT `GL_TRANS_CONFIG_CHART_OF_ACCOUNT_ASSOCIATION_ID_FK` FOREIGN KEY (`CHART_OF_ACCOUNT_ASSOCIATION_ID`) REFERENCES `chart_of_account` (`CHART_OF_ACCOUNT_ID`),
  ADD CONSTRAINT `GL_TRANS_CONFIG_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `GL_TRANS_CONFIG_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `GL_TRANS_CONFIG_GL_EVENT_ASSOCIATION_ID_FK` FOREIGN KEY (`GL_EVENT_ASSOCIATION_ID`) REFERENCES `general_ledger_event` (`GENERAL_LEDGER_EVENT_ID`),
  ADD CONSTRAINT `GL_TRANS_CONFIG_GL_SUB_EVENT_ASSOCIATION_ID_FK` FOREIGN KEY (`GL_SUB_EVENT_ASSOCIATION_ID`) REFERENCES `general_ledger_event` (`GENERAL_LEDGER_EVENT_ID`),
  ADD CONSTRAINT `GL_TRANS_CONFIG_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`);

--
-- Constraints for table `inventory_count`
--
ALTER TABLE `inventory_count`
  ADD CONSTRAINT `INVENTORY_COUNT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `INVENTORY_COUNT_INVENTORY_COUNT_TYPE_ASSOCICATION_ID_FK` FOREIGN KEY (`INVENTORY_COUNT_TYPE_ASSOCICATION_ID`) REFERENCES `inventory_count_type` (`INVENTORY_COUNT_TYPE_ID`),
  ADD CONSTRAINT `INVENTORY_COUNT_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  ADD CONSTRAINT `INVENTORY_COUNT_STATUS_ASSOCICATION_ID_FK` FOREIGN KEY (`STATUS_ASSOCICATION_ID`) REFERENCES `status` (`STATUS_ID`);

--
-- Constraints for table `inventory_count_detail`
--
ALTER TABLE `inventory_count_detail`
  ADD CONSTRAINT `INVENTORY_COUNT_DETAIL_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `INVENTORY_COUNT_DETAIL_INVENTORY_COUNT_ASSOCICATION_ID_FK` FOREIGN KEY (`INVENTORY_COUNT_ASSOCICATION_ID`) REFERENCES `inventory_count` (`INVENTORY_COUNT_ID`),
  ADD CONSTRAINT `INVENTORY_COUNT_DETAIL_PRODUCT_ASSOCIATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCIATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  ADD CONSTRAINT `INVENTORY_COUNT_DETAIL_PRODUCT_VARIANT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_VARIANT_ASSOCICATION_ID`) REFERENCES `product_variant` (`PRODUCT_VARIANT_ID`);

--
-- Constraints for table `invoice_detail`
--
ALTER TABLE `invoice_detail`
  ADD CONSTRAINT `INVOICE_DETAIL_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `INVOICE_DETAIL_INVOICE_MAIN_ASSOCICATION_ID_FK` FOREIGN KEY (`INVOICE_MAIN_ASSOCICATION_ID`) REFERENCES `invoice_main` (`INVOICE_MAIN_ID`),
  ADD CONSTRAINT `INVOICE_DETAIL_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  ADD CONSTRAINT `INVOICE_DETAIL_PRODUCT_ASSOCIATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCIATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  ADD CONSTRAINT `INVOICE_DETAIL_PRODUCT_VARIENT_ASSOCIATION_ID_FK` FOREIGN KEY (`PRODUCT_VARIENT_ASSOCIATION_ID`) REFERENCES `product_variant` (`PRODUCT_VARIANT_ID`);

--
-- Constraints for table `invoice_main`
--
ALTER TABLE `invoice_main`
  ADD CONSTRAINT `INVOICE_MAIN_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `INVOICE_MAIN_CONTACT_ASSOCIATION_ID_FK` FOREIGN KEY (`CONTACT_ASSOCIATION_ID`) REFERENCES `contact` (`CONTACT_ID`),
  ADD CONSTRAINT `INVOICE_MAIN_DAILY_REGISTER_ASSOCICATION_ID_FK` FOREIGN KEY (`DAILY_REGISTER_ASSOCICATION_ID`) REFERENCES `daily_register` (`DAILY_REGISTER_ID`),
  ADD CONSTRAINT `INVOICE_MAIN_ORDER_ASSOCICATION_ID_FK` FOREIGN KEY (`ORDER_ASSOCICATION_ID`) REFERENCES `order_main` (`ORDER_MAIN_ID`),
  ADD CONSTRAINT `INVOICE_MAIN_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  ADD CONSTRAINT `INVOICE_MAIN_PAYMENT_TYPE_ASSOCICATION_ID_FK` FOREIGN KEY (`PAYMENT_TYPE_ASSOCICATION_ID`) REFERENCES `payment_type` (`PAYMENT_TYPE_ID`),
  ADD CONSTRAINT `INVOICE_MAIN_STATUS_ASSOCICATION_ID_FK` FOREIGN KEY (`STATUS_ASSOCICATION_ID`) REFERENCES `status` (`STATUS_ID`);

--
-- Constraints for table `loyalty`
--
ALTER TABLE `loyalty`
  ADD CONSTRAINT `LOYALTY_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `LOYALTY_CONTACT_ASSOCIATION_ID_FK` FOREIGN KEY (`CONTACT_ASSOCIATION_ID`) REFERENCES `contact` (`CONTACT_ID`),
  ADD CONSTRAINT `LOYALTY_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`);

--
-- Constraints for table `menu`
--
ALTER TABLE `menu`
  ADD CONSTRAINT `MENU_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `MENU_ROLE_ASSOCIATION_ID_FK` FOREIGN KEY (`ROLE_ASSOCIATION_ID`) REFERENCES `role` (`ROLE_ID`);

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `MESSAGE_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `MESSAGE_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`);

--
-- Constraints for table `message_detail`
--
ALTER TABLE `message_detail`
  ADD CONSTRAINT `MESSAGE_DETAIL_MESSAGE_ASSOCIATION_ID_FK` FOREIGN KEY (`MESSAGE_ASSOCIATION_ID`) REFERENCES `message` (`MESSAGE_ID`),
  ADD CONSTRAINT `MESSAGE_DETAIL_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `MESSAGE_DETAIL_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`);

--
-- Constraints for table `module`
--
ALTER TABLE `module`
  ADD CONSTRAINT `MODULE_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `MODULE_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `MODULE_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`);

--
-- Constraints for table `notification`
--
ALTER TABLE `notification`
  ADD CONSTRAINT `NOTIFICATION_NOTIFICATION_ASSOCIATION_ID_FK` FOREIGN KEY (`NOTIFICATION_ASSOCIATION_ID`) REFERENCES `notification` (`NOTIFICATION_ID`),
  ADD CONSTRAINT `NOTIFICATION_NOTIFICATION_FROM_FK` FOREIGN KEY (`NOTIFICATION_FROM`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `NOTIFICATION_NOTIFICATION_TO_FK` FOREIGN KEY (`NOTIFICATION_TO`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `NOTIFICATION_SEVERITY_ASSOCIATION_ID_FK` FOREIGN KEY (`SEVERITY_ASSOCIATION_ID`) REFERENCES `severity` (`SEVERITY_ID`);

--
-- Constraints for table `order_detail`
--
ALTER TABLE `order_detail`
  ADD CONSTRAINT `ORDER_DETAIL_ORDER_MAIN_ASSOCICATION_ID_FK` FOREIGN KEY (`ORDER_MAIN_ASSOCICATION_ID`) REFERENCES `order_main` (`ORDER_MAIN_ID`),
  ADD CONSTRAINT `ORDER_DETAIL_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  ADD CONSTRAINT `ORDER_DETAIL_PRODUCT_ASSOCIATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCIATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  ADD CONSTRAINT `ORDER_DETAIL_PRODUCT_VARIENT_ASSOCIATION_ID_FK` FOREIGN KEY (`PRODUCT_VARIENT_ASSOCIATION_ID`) REFERENCES `product_variant` (`PRODUCT_VARIANT_ID`);

--
-- Constraints for table `order_main`
--
ALTER TABLE `order_main`
  ADD CONSTRAINT `ORDER_MAIN_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `ORDER_MAIN_CONTACT_ASSOCIATION_ID_FK` FOREIGN KEY (`CONTACT_ASSOCIATION_ID`) REFERENCES `contact` (`CONTACT_ID`),
  ADD CONSTRAINT `ORDER_MAIN_DELIVERY_METHOD_ID_FK` FOREIGN KEY (`DELIVER_METHOD_ASSOCICATION_ID`) REFERENCES `delivery_method` (`DELIVERY_METHOD_ID`),
  ADD CONSTRAINT `ORDER_MAIN_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  ADD CONSTRAINT `ORDER_MAIN_PAYMENT_TYPE_ASSOCICATION_ID_FK` FOREIGN KEY (`PAYMENT_TYPE_ASSOCICATION_ID`) REFERENCES `payment_type` (`PAYMENT_TYPE_ID`),
  ADD CONSTRAINT `ORDER_MAIN_STATUS_ASSOCICATION_ID_FK` FOREIGN KEY (`STATUS_ASSOCICATION_ID`) REFERENCES `status` (`STATUS_ID`);

--
-- Constraints for table `outlet`
--
ALTER TABLE `outlet`
  ADD CONSTRAINT `OUTLET_ADDRESS_ASSOCICATION_ID_FK` FOREIGN KEY (`ADDRESS_ASSOCICATION_ID`) REFERENCES `address` (`ADDRESS_ID`),
  ADD CONSTRAINT `OUTLET_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `OUTLET_SALES_TAX_ASSOCICATION_ID_FK` FOREIGN KEY (`SALES_TAX_ASSOCICATION_ID`) REFERENCES `sales_tax` (`SALES_TAX_ID`),
  ADD CONSTRAINT `OUTLET_TIME_ZONE_ASSOCICATION_ID_FK` FOREIGN KEY (`TIME_ZONE_ASSOCICATION_ID`) REFERENCES `time_zone` (`TIME_ZONE_ID`);

--
-- Constraints for table `price_book`
--
ALTER TABLE `price_book`
  ADD CONSTRAINT `PRICE_BOOK_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `PRICE_BOOK_CONTACT_GROUP_ASSOCICATION_ID_FK` FOREIGN KEY (`CONTACT_GROUP_ASSOCICATION_ID`) REFERENCES `contact_group` (`CONTACT_GROUP_ID`),
  ADD CONSTRAINT `PRICE_BOOK_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `PRICE_BOOK_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  ADD CONSTRAINT `PRICE_BOOK_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`);

--
-- Constraints for table `price_book_detail`
--
ALTER TABLE `price_book_detail`
  ADD CONSTRAINT `PRICE_BOOK_DETAIL_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `PRICE_BOOK_DETAIL_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `PRICE_BOOK_DETAIL_PRICE_BOOK_ASSOCICATION_ID_FK` FOREIGN KEY (`PRICE_BOOK_ASSOCICATION_ID`) REFERENCES `price_book` (`PRICE_BOOK_ID`),
  ADD CONSTRAINT `PRICE_BOOK_DETAIL_PRODUCT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCICATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  ADD CONSTRAINT `PRICE_BOOK_DETAIL_PRODUCT_VARIENT_ASSOCIATION_ID_FK` FOREIGN KEY (`PRODUCT_VARIENT_ASSOCICATION_ID`) REFERENCES `product_variant` (`PRODUCT_VARIANT_ID`),
  ADD CONSTRAINT `PRICE_BOOK_DETAIL_SALES_TAX_ASSOCIATION_ID_FK` FOREIGN KEY (`SALES_TAX_ASSOCIATION_ID`) REFERENCES `sales_tax` (`SALES_TAX_ID`),
  ADD CONSTRAINT `PRICE_BOOK_DETAIL_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`);

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `PRODUCT_BRAND_ASSOCICATION_ID_FK` FOREIGN KEY (`BRAND_ASSOCICATION_ID`) REFERENCES `brand` (`BRAND_ID`),
  ADD CONSTRAINT `PRODUCT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `PRODUCT_CONTACT_ASSOCICATION_ID_FK` FOREIGN KEY (`CONTACT_ASSOCICATION_ID`) REFERENCES `contact` (`CONTACT_ID`),
  ADD CONSTRAINT `PRODUCT_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `PRODUCT_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  ADD CONSTRAINT `PRODUCT_PRODUCT_TYPE_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_TYPE_ASSOCICATION_ID`) REFERENCES `product_type` (`PRODUCT_TYPE_ID`),
  ADD CONSTRAINT `PRODUCT_SALES_TAX_ASSOCICATION_ID_FK` FOREIGN KEY (`SALES_TAX_ASSOCICATION_ID`) REFERENCES `sales_tax` (`SALES_TAX_ID`),
  ADD CONSTRAINT `PRODUCT_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`);

--
-- Constraints for table `product_history`
--
ALTER TABLE `product_history`
  ADD CONSTRAINT `PRODUCT_HISTORY_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `PRODUCT_HISTORY_COMPOSITE_PRODUCT_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPOSITE_PRODUCT_ASSOCIATION_ID`) REFERENCES `composite_product` (`COMPOSITE_PRODUCT_ID`),
  ADD CONSTRAINT `PRODUCT_HISTORY_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `PRODUCT_HISTORY_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  ADD CONSTRAINT `PRODUCT_HISTORY_PRODUCT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCICATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  ADD CONSTRAINT `PRODUCT_HISTORY_PRODUCT_VARIANT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_VARIANT_ASSOCICATION_ID`) REFERENCES `product_variant` (`PRODUCT_VARIANT_ID`);

--
-- Constraints for table `product_tag`
--
ALTER TABLE `product_tag`
  ADD CONSTRAINT `PRODUCT_TAG_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `PRODUCT_TAG_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `PRODUCT_TAG_PRODUCT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCICATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  ADD CONSTRAINT `PRODUCT_TAG_TAG_ASSOCICATION_ID_FK` FOREIGN KEY (`TAG_ASSOCICATION_ID`) REFERENCES `tag` (`TAG_ID`),
  ADD CONSTRAINT `PRODUCT_TAG_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`);

--
-- Constraints for table `product_type`
--
ALTER TABLE `product_type`
  ADD CONSTRAINT `PRODUCT_TYPE_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`);

--
-- Constraints for table `product_variant`
--
ALTER TABLE `product_variant`
  ADD CONSTRAINT `PRODUCT_VARIANT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `PRODUCT_VARIANT_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `PRODUCT_VARIANT_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  ADD CONSTRAINT `PRODUCT_VARIANT_PRODUCT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCICATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  ADD CONSTRAINT `PRODUCT_VARIANT_SALES_TAX_ASSOCICATION_ID_FK` FOREIGN KEY (`SALES_TAX_ASSOCICATION_ID`) REFERENCES `sales_tax` (`SALES_TAX_ID`),
  ADD CONSTRAINT `PRODUCT_VARIANT_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `PRODUCT_VARIANT_VARIANT_ATTRIBUTE_ASSOCICATION_ID_1_FK` FOREIGN KEY (`VARIANT_ATTRIBUTE_ASSOCICATION_ID_1`) REFERENCES `variant_attribute` (`VARIANT_ATTRIBUTE_ID`),
  ADD CONSTRAINT `PRODUCT_VARIANT_VARIANT_ATTRIBUTE_ASSOCICATION_ID_2_FK` FOREIGN KEY (`VARIANT_ATTRIBUTE_ASSOCICATION_ID_2`) REFERENCES `variant_attribute` (`VARIANT_ATTRIBUTE_ID`),
  ADD CONSTRAINT `PRODUCT_VARIANT_VARIANT_ATTRIBUTE_ASSOCICATION_ID_3_FK` FOREIGN KEY (`VARIANT_ATTRIBUTE_ASSOCICATION_ID_3`) REFERENCES `variant_attribute` (`VARIANT_ATTRIBUTE_ID`);

--
-- Constraints for table `receipt`
--
ALTER TABLE `receipt`
  ADD CONSTRAINT `RECEIPT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `RECEIPT_CONTACT_ASSOCICATION_ID_FK` FOREIGN KEY (`CONTACT_ASSOCIATION_ID`) REFERENCES `contact` (`CONTACT_ID`),
  ADD CONSTRAINT `RECEIPT_DAILY_REGISTER_ASSOCICATION_ID_FK` FOREIGN KEY (`DAILY_REGISTER_ASSOCICATION_ID`) REFERENCES `daily_register` (`DAILY_REGISTER_ID`),
  ADD CONSTRAINT `RECEIPT_INVOICE_MAIN_ASSOCICATION_ID_FK` FOREIGN KEY (`INVOICE_MAIN_ASSOCICATION_ID`) REFERENCES `invoice_main` (`INVOICE_MAIN_ID`),
  ADD CONSTRAINT `RECEIPT_OUTLET_ASSOCIATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCIATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  ADD CONSTRAINT `RECEIPT_PAYMENT_TYPE_ASSOCICATION_ID_FK` FOREIGN KEY (`PAYMENT_TYPE_ASSOCICATION_ID`) REFERENCES `payment_type` (`PAYMENT_TYPE_ID`),
  ADD CONSTRAINT `RECEIPT_STATUS_ASSOCICATION_ID_FK` FOREIGN KEY (`STATUS_ASSOCICATION_ID`) REFERENCES `status` (`STATUS_ID`);

--
-- Constraints for table `register`
--
ALTER TABLE `register`
  ADD CONSTRAINT `REGISTER_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `REGISTER_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`);

--
-- Constraints for table `sales_tax`
--
ALTER TABLE `sales_tax`
  ADD CONSTRAINT `SALES_TAX_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`);

--
-- Constraints for table `stock_order`
--
ALTER TABLE `stock_order`
  ADD CONSTRAINT `STOCK_ORDER_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `STOCK_ORDER_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  ADD CONSTRAINT `STOCK_ORDER_SOURCE_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`SOURCE_OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  ADD CONSTRAINT `STOCK_ORDER_STATUS_ASSOCICATION_ID_FK` FOREIGN KEY (`STATUS_ASSOCICATION_ID`) REFERENCES `status` (`STATUS_ID`),
  ADD CONSTRAINT `STOCK_ORDER_STOCK_ORDER_TYPE_ASSOCICATION_ID_FK` FOREIGN KEY (`STOCK_ORDER_TYPE_ASSOCICATION_ID`) REFERENCES `stock_order_type` (`STOCK_ORDER_TYPE_ID`);

--
-- Constraints for table `stock_order_detail`
--
ALTER TABLE `stock_order_detail`
  ADD CONSTRAINT `STOCK_ORDER_DETAIL_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `STOCK_ORDER_DETAIL_PRODUCT_ASSOCIATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCIATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  ADD CONSTRAINT `STOCK_ORDER_DETAIL_PRODUCT_VARIANT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_VARIANT_ASSOCICATION_ID`) REFERENCES `product_variant` (`PRODUCT_VARIANT_ID`),
  ADD CONSTRAINT `STOCK_ORDER_DETAIL_STOCK_ORDER_ASSOCICATION_ID_FK` FOREIGN KEY (`STOCK_ORDER_ASSOCICATION_ID`) REFERENCES `stock_order` (`STOCK_ORDER_ID`);

--
-- Constraints for table `tag`
--
ALTER TABLE `tag`
  ADD CONSTRAINT `TAG_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `TAG_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `TAG_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`);

--
-- Constraints for table `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `TICKET_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `TICKET_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `TICKET_RESOLVED_BY_FK` FOREIGN KEY (`RESOLVED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `TICKET_SEVERITY_ASSOCIATION_ID_FK` FOREIGN KEY (`SEVERITY_ASSOCIATION_ID`) REFERENCES `severity` (`SEVERITY_ID`);

--
-- Constraints for table `ticket_activity`
--
ALTER TABLE `ticket_activity`
  ADD CONSTRAINT `TICKET_ACTIVITY_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `TICKET_ACTIVITY_TICKET_ASSOCIATION_ID_FK` FOREIGN KEY (`TICKET_ASSOCIATION_ID`) REFERENCES `ticket` (`TICKET_ID`);

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `USER_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `USER_CONTACT_ASSOCICATION_ID_FK` FOREIGN KEY (`CONTACT_ASSOCICATION_ID`) REFERENCES `contact` (`CONTACT_ID`),
  ADD CONSTRAINT `USER_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  ADD CONSTRAINT `USER_ROLE_ASSOCIATION_ID_FK` FOREIGN KEY (`ROLE_ASSOCIATION_ID`) REFERENCES `role` (`ROLE_ID`);

--
-- Constraints for table `user_outlets`
--
ALTER TABLE `user_outlets`
  ADD CONSTRAINT `USER_OUTLETS_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `USER_OUTLETS_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `USER_OUTLETS_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  ADD CONSTRAINT `USER_OUTLETS_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `USER_OUTLETS_USER_ASSOCIATION_ID_FK` FOREIGN KEY (`USER_ASSOCIATION_ID`) REFERENCES `user` (`USER_ID`);

--
-- Constraints for table `variant_attribute`
--
ALTER TABLE `variant_attribute`
  ADD CONSTRAINT `VARIANT_ATTRIBUTE_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `VARIANT_ATTRIBUTE_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `VARIANT_ATTRIBUTE_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`);

--
-- Constraints for table `variant_attribute_values`
--
ALTER TABLE `variant_attribute_values`
  ADD CONSTRAINT `VARIANT_ATTRIBUTE_VALUES_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  ADD CONSTRAINT `VARIANT_ATTRIBUTE_VALUES_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `VARIANT_ATTRIBUTE_VALUES_PRODUCT_ASSOCIATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCIATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  ADD CONSTRAINT `VARIANT_ATTRIBUTE_VALUES_PRODUCT_VARIANT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_VARIANT_ASSOCICATION_ID`) REFERENCES `product_variant` (`PRODUCT_VARIANT_ID`),
  ADD CONSTRAINT `VARIANT_ATTRIBUTE_VALUES_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `VARIANT_ATTRIBUTE_VALUES_VARIANT_ATTRIBUTE_ASSOCICATION_ID_FK` FOREIGN KEY (`VARIANT_ATTRIBUTE_ASSOCICATION_ID`) REFERENCES `variant_attribute` (`VARIANT_ATTRIBUTE_ID`);

--
-- Constraints for table `web_activity_detail`
--
ALTER TABLE `web_activity_detail`
  ADD CONSTRAINT `WEB_ACTIVITY_DETAIL_CREATTED_BY_MANAGER_ID_FK` FOREIGN KEY (`CREATED_BY_MANAGER_ID`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `WEB_ACTIVITY_DETAIL_EMPLOYEE_ASSOCIATION_ID_FK` FOREIGN KEY (`EMPLOYEE_ASSOCIATION_ID`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `WEB_ACTIVITY_DETAIL_SEVERITY_ASSOCIATION_ID_FK` FOREIGN KEY (`SEVERITY_ASSOCIATION_ID`) REFERENCES `severity` (`SEVERITY_ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;


ALTER TABLE `ecom`.`message_detail` 
CHANGE COLUMN `DELIVERY_ID` `DELIVERY_ID` TEXT NULL DEFAULT NULL ;
