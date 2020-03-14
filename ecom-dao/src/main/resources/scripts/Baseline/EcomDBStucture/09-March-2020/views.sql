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

/*Table structure for table `brand_sales_report` */

DROP TABLE IF EXISTS `brand_sales_report`;

/*!50001 DROP VIEW IF EXISTS `brand_sales_report` */;
/*!50001 DROP TABLE IF EXISTS `brand_sales_report` */;

/*!50001 CREATE TABLE `brand_sales_report` (
  `Product` varchar(500) default NULL,
  `Outlet` varchar(100) default NULL,
  `Brand` varchar(200) default NULL,
  `CREATED_DATE` date default NULL,
  `Revenue` decimal(57,6) default NULL,
  `Revenue_tax_incl` decimal(58,6) default NULL,
  `Cost_of_Goods` decimal(52,2) default NULL,
  `Gross_Profit` decimal(53,2) default NULL,
  `Margin` decimal(50,6) default NULL,
  `Items_Sold` decimal(32,0) default NULL,
  `Tax` decimal(42,2) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `cash_sale` */

DROP TABLE IF EXISTS `cash_sale`;

/*!50001 DROP VIEW IF EXISTS `cash_sale` */;
/*!50001 DROP TABLE IF EXISTS `cash_sale` */;

/*!50001 CREATE TABLE `cash_sale` (
  `product` varchar(500) default NULL,
  `outlet` varchar(100) default NULL,
  `created_date` date default NULL,
  `revenue` decimal(57,6) default NULL,
  `revenue_tax_incl` decimal(58,6) default NULL,
  `cost_of_goods` decimal(52,2) default NULL,
  `gross_profit` decimal(53,2) default NULL,
  `margin` decimal(50,6) default NULL,
  `items_sold` decimal(33,0) default NULL,
  `tax` decimal(42,2) default NULL,
  `company_association_id` int(11) NOT NULL,
  `outlet_assocication_id` int(11) default NULL,
  `PAYMENT_TYPE_ASSOCICATION_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `contacts_summmary` */

DROP TABLE IF EXISTS `contacts_summmary`;

/*!50001 DROP VIEW IF EXISTS `contacts_summmary` */;
/*!50001 DROP TABLE IF EXISTS `contacts_summmary` */;

/*!50001 CREATE TABLE `contacts_summmary` (
  `CONTACT_ID` int(11) NOT NULL default '0',
  `CONTACT_NAME` varchar(401) default NULL,
  `FIRST_NAME` varchar(200) default NULL,
  `LAST_NAME` varchar(200) default NULL,
  `COMPANY_NAME` varchar(100) default NULL,
  `BALANCE` decimal(20,5) default NULL,
  `PHONE` varchar(256) default NULL,
  `CREATED_DATE` timestamp NOT NULL default '0000-00-00 00:00:00',
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `OUTLET_ASSOCIATION_ID` int(11) NOT NULL default '1',
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CONTACT_GROUP` varchar(200) default NULL,
  `CONTACT_TYPE` varchar(250) NOT NULL default 'SUPPLIER'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `credit_sale` */

DROP TABLE IF EXISTS `credit_sale`;

/*!50001 DROP VIEW IF EXISTS `credit_sale` */;
/*!50001 DROP TABLE IF EXISTS `credit_sale` */;

/*!50001 CREATE TABLE `credit_sale` (
  `product` varchar(500) default NULL,
  `outlet` varchar(100) default NULL,
  `created_date` date default NULL,
  `revenue` decimal(57,6) default NULL,
  `revenue_tax_incl` decimal(58,6) default NULL,
  `cost_of_goods` decimal(52,2) default NULL,
  `gross_profit` decimal(53,2) default NULL,
  `margin` decimal(50,6) default NULL,
  `items_sold` decimal(33,0) default NULL,
  `tax` decimal(42,2) default NULL,
  `company_association_id` int(11) NOT NULL,
  `outlet_assocication_id` int(11) default NULL,
  `PAYMENT_TYPE_ASSOCICATION_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `customer_group_sales_report` */

DROP TABLE IF EXISTS `customer_group_sales_report`;

/*!50001 DROP VIEW IF EXISTS `customer_group_sales_report` */;
/*!50001 DROP TABLE IF EXISTS `customer_group_sales_report` */;

/*!50001 CREATE TABLE `customer_group_sales_report` (
  `Product` varchar(500) default NULL,
  `Outlet` varchar(100) default NULL,
  `Group_Name` varchar(200) default NULL,
  `CREATED_DATE` date default NULL,
  `Revenue` decimal(57,6) default NULL,
  `Revenue_tax_incl` decimal(58,6) default NULL,
  `Cost_of_Goods` decimal(52,2) default NULL,
  `Gross_Profit` decimal(53,2) default NULL,
  `Margin` decimal(50,6) default NULL,
  `Items_Sold` decimal(32,0) default NULL,
  `Tax` decimal(42,2) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `customer_sales_report` */

DROP TABLE IF EXISTS `customer_sales_report`;

/*!50001 DROP VIEW IF EXISTS `customer_sales_report` */;
/*!50001 DROP TABLE IF EXISTS `customer_sales_report` */;

/*!50001 CREATE TABLE `customer_sales_report` (
  `Product` varchar(500) default NULL,
  `Outlet` varchar(100) default NULL,
  `Customer` varchar(200) default NULL,
  `CREATED_DATE` date default NULL,
  `Revenue` decimal(57,6) default NULL,
  `Revenue_tax_incl` decimal(58,6) default NULL,
  `Cost_of_Goods` decimal(52,2) default NULL,
  `Gross_Profit` decimal(53,2) default NULL,
  `Margin` decimal(50,6) default NULL,
  `Items_Sold` decimal(32,0) default NULL,
  `Tax` decimal(42,2) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `expensereport` */

DROP TABLE IF EXISTS `expensereport`;

/*!50001 DROP VIEW IF EXISTS `expensereport` */;
/*!50001 DROP TABLE IF EXISTS `expensereport` */;

/*!50001 CREATE TABLE `expensereport` (
  `EXPENSE` varchar(200) default NULL,
  `User` longtext,
  `Outlet` varchar(100) default NULL,
  `CREATED_DATE` date default NULL,
  `CASH_AMT` decimal(42,2) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL,
  `Type` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `inventory__detail_report` */

DROP TABLE IF EXISTS `inventory__detail_report`;

/*!50001 DROP VIEW IF EXISTS `inventory__detail_report` */;
/*!50001 DROP TABLE IF EXISTS `inventory__detail_report` */;

/*!50001 CREATE TABLE `inventory__detail_report` (
  `ID` int(11) NOT NULL default '0',
  `PRODUCT_NAME` longtext,
  `SKU` varchar(500) NOT NULL default '',
  `SUPPLY_PRICE_EXCL_TAX` decimal(20,2) NOT NULL default '0.00',
  `MARKUP_PRCT` decimal(12,5) NOT NULL default '0.00000',
  `REORDER_POINT` bigint(20) default NULL,
  `REORDER_AMOUNT` decimal(20,2) default NULL,
  `CURRENT_INVENTORY` int(11) default NULL,
  `NET_PRICE` decimal(37,11) default NULL,
  `OUTLET_NAME` varchar(100) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) NOT NULL default '0',
  `CREATED_DATE` timestamp NOT NULL default '0000-00-00 00:00:00',
  `BRAND_NAME` varchar(200) default NULL,
  `PRODUCT_TYPE` varchar(200) default NULL,
  `CONTACT_NAME` varchar(200) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL default '0',
  `STOCK_VALUE` decimal(30,2) default NULL,
  `RETAIL_VALUE` decimal(47,11) default NULL,
  `TOTAL_STOCK_SENT` decimal(32,0) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `inventory_report` */

DROP TABLE IF EXISTS `inventory_report`;

/*!50001 DROP VIEW IF EXISTS `inventory_report` */;
/*!50001 DROP TABLE IF EXISTS `inventory_report` */;

/*!50001 CREATE TABLE `inventory_report` (
  `ID` int(11) NOT NULL default '0',
  `PRODUCT_NAME` longtext,
  `SKU` varchar(500) NOT NULL default '',
  `SUPPLY_PRICE_EXCL_TAX` decimal(20,2) NOT NULL default '0.00',
  `MARKUP_PRCT` decimal(12,5) NOT NULL default '0.00000',
  `REORDER_POINT` bigint(20) default NULL,
  `REORDER_AMOUNT` decimal(20,2) default NULL,
  `CURRENT_INVENTORY` int(11) default NULL,
  `NET_PRICE` decimal(37,11) default NULL,
  `OUTLET_NAME` varchar(100) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) NOT NULL default '0',
  `CREATED_DATE` timestamp NOT NULL default '0000-00-00 00:00:00',
  `BRAND_NAME` varchar(200) default NULL,
  `PRODUCT_TYPE` varchar(200) default NULL,
  `CONTACT_NAME` varchar(200) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL default '0',
  `STOCK_VALUE` decimal(30,2) default NULL,
  `RETAIL_VALUE` decimal(47,11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `outlet_sales_report` */

DROP TABLE IF EXISTS `outlet_sales_report`;

/*!50001 DROP VIEW IF EXISTS `outlet_sales_report` */;
/*!50001 DROP TABLE IF EXISTS `outlet_sales_report` */;

/*!50001 CREATE TABLE `outlet_sales_report` (
  `Product` varchar(500) default NULL,
  `Outlet` varchar(100) default NULL,
  `CREATED_DATE` date default NULL,
  `Revenue` decimal(57,6) default NULL,
  `Revenue_tax_incl` decimal(58,6) default NULL,
  `Cost_of_Goods` decimal(52,2) default NULL,
  `Gross_Profit` decimal(53,2) default NULL,
  `Margin` decimal(50,6) default NULL,
  `Items_Sold` decimal(32,0) default NULL,
  `Tax` decimal(42,2) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `payment_report` */

DROP TABLE IF EXISTS `payment_report`;

/*!50001 DROP VIEW IF EXISTS `payment_report` */;
/*!50001 DROP TABLE IF EXISTS `payment_report` */;

/*!50001 CREATE TABLE `payment_report` (
  `OUTLET_ASSOCICATION_ID` int(11) default NULL,
  `OUTLET` varchar(100) default NULL,
  `CREATED_DATE` date default NULL,
  `AMOUNT` decimal(42,2) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `price_book_detail_summary` */

DROP TABLE IF EXISTS `price_book_detail_summary`;

/*!50001 DROP VIEW IF EXISTS `price_book_detail_summary` */;
/*!50001 DROP TABLE IF EXISTS `price_book_detail_summary` */;

/*!50001 CREATE TABLE `price_book_detail_summary` (
  `PRICE_BOOK_DETAIL_ID` int(11) NOT NULL default '0',
  `PRICE_BOOK_ASSOCICATION_ID` int(11) NOT NULL,
  `PRODUCT_ASSOCICATION_ID` int(11) NOT NULL,
  `UUID` varchar(500) NOT NULL,
  `PRODUCT_NAME` varchar(500) default NULL,
  `PRODUCT_VARIENT_ASSOCICATION_ID` int(11) NOT NULL default '0',
  `VARIANT_ATTRIBUTE_NAME` varchar(200) default NULL,
  `SUPPLY_PRICE` decimal(20,2) NOT NULL,
  `MARKUP` decimal(8,5) NOT NULL,
  `DISCOUNT` decimal(8,5) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `SALES_TAX_ASSOCIATION_ID` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `product_summmary` */

DROP TABLE IF EXISTS `product_summmary`;

/*!50001 DROP VIEW IF EXISTS `product_summmary` */;
/*!50001 DROP TABLE IF EXISTS `product_summmary` */;

/*!50001 CREATE TABLE `product_summmary` (
  `ID` int(11) NOT NULL default '0',
  `PRODUCT_NAME` varchar(500) NOT NULL,
  `SKU` varchar(500) NOT NULL COMMENT 'STOCK KEEPING UNIT ( SKU)',
  `SUPPLY_PRICE_EXCL_TAX` decimal(20,2) NOT NULL,
  `REORDER_POINT` int(11) default NULL,
  `REORDER_AMOUNT` decimal(20,2) default NULL,
  `CURRENT_INVENTORY` int(11) default NULL,
  `NET_PRICE` decimal(37,11) default NULL,
  `OUTLET_NAME` varchar(100) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default '0000-00-00 00:00:00',
  `BRAND_NAME` varchar(200) default NULL,
  `PRODUCT_TYPE` varchar(200) default NULL,
  `CONTACT_NAME` varchar(200) default NULL,
  `VARIANT_COUNT` bigint(21) default NULL,
  `VARIANT_CURRENT_INVENTORY` int(11) default NULL,
  `VARIANT_SKU` varchar(500) NOT NULL COMMENT 'STOCK KEEPING UNIT ( SKU)',
  `VARIANT_SUPPLY_PRICE_EXCL_TAX` decimal(20,2) NOT NULL,
  `VARIANT_REORDER_POINT` int(11) default NULL,
  `VARIANT_REORDER_AMOUNT` decimal(20,2) default NULL,
  `VARIANT_NET_PRICE` decimal(37,11) default NULL,
  `VARIANT_COMP_COUNT` bigint(21) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `IMAGE_PATH` varchar(250) default NULL,
  `VARIANT_PRODUCTS` varchar(10) NOT NULL,
  `STANDARD_PRODUCT` varchar(10) NOT NULL,
  `IS_COMPOSITE` varchar(10) default NULL,
  `VARIANT_INVENTORY_COUNT` decimal(32,0) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `producttype_sales_report` */

DROP TABLE IF EXISTS `producttype_sales_report`;

/*!50001 DROP VIEW IF EXISTS `producttype_sales_report` */;
/*!50001 DROP TABLE IF EXISTS `producttype_sales_report` */;

/*!50001 CREATE TABLE `producttype_sales_report` (
  `Product` varchar(500) default NULL,
  `Outlet` varchar(100) default NULL,
  `ProductType` varchar(200) default NULL,
  `CREATED_DATE` date default NULL,
  `Revenue` decimal(57,6) default NULL,
  `Revenue_tax_incl` decimal(58,6) default NULL,
  `Cost_of_Goods` decimal(52,2) default NULL,
  `Gross_Profit` decimal(53,2) default NULL,
  `Margin` decimal(50,6) default NULL,
  `Items_Sold` decimal(32,0) default NULL,
  `Tax` decimal(42,2) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `register_report` */

DROP TABLE IF EXISTS `register_report`;

/*!50001 DROP VIEW IF EXISTS `register_report` */;
/*!50001 DROP TABLE IF EXISTS `register_report` */;

/*!50001 CREATE TABLE `register_report` (
  `OUTLET_ASSOCICATION_ID` int(11) default NULL,
  `OUTLET_NAME` varchar(100) default NULL,
  `CASH_AMT_ACTUAL` decimal(20,2) NOT NULL default '0.00',
  `CREDIT_CARD_AMT_ACTUAL` decimal(20,2) NOT NULL default '0.00',
  `OPENING_DATE` varbinary(49) default NULL,
  `CLOSING_DATE` varbinary(49) default NULL,
  `Open_By` longtext,
  `Close_By` longblob,
  `Status` varchar(45) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `DAILY_REGISTER_ID` int(11) NOT NULL default '0',
  `REGISTER_CLOSING_NOTES` varchar(256) default NULL,
  `REGISTER_OPENING_NOTES` varchar(256) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `sale_details` */

DROP TABLE IF EXISTS `sale_details`;

/*!50001 DROP VIEW IF EXISTS `sale_details` */;
/*!50001 DROP TABLE IF EXISTS `sale_details` */;

/*!50001 CREATE TABLE `sale_details` (
  `Product` varchar(500),
  `Variant` varchar(500) default NULL,
  `SKU` varchar(500) default NULL,
  `Outlet` varchar(100) default NULL,
  `CREATED_DATE` date default NULL,
  `Revenue` decimal(52,2) default NULL,
  `Revenue_tax_incl` decimal(53,2) default NULL,
  `Cost_of_Goods` decimal(52,2) default NULL,
  `Gross_Profit` decimal(53,2) default NULL,
  `Margin` decimal(50,6) default NULL,
  `Items_Sold` decimal(33,0) default NULL,
  `Tax` decimal(42,2) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `salereport_withoutsale` */

DROP TABLE IF EXISTS `salereport_withoutsale`;

/*!50001 DROP VIEW IF EXISTS `salereport_withoutsale` */;
/*!50001 DROP TABLE IF EXISTS `salereport_withoutsale` */;

/*!50001 CREATE TABLE `salereport_withoutsale` (
  `Product` varchar(500) default NULL,
  `Outlet` varchar(100) default NULL,
  `CREATED_DATE` date default NULL,
  `Revenue` decimal(57,6) default NULL,
  `Revenue_tax_incl` decimal(58,6) default NULL,
  `Cost_of_Goods` decimal(52,2) default NULL,
  `Gross_Profit` decimal(53,2) default NULL,
  `Margin` decimal(50,6) default NULL,
  `Items_Sold` decimal(33,0) default NULL,
  `Tax` decimal(42,2) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `salereport_withsale` */

DROP TABLE IF EXISTS `salereport_withsale`;

/*!50001 DROP VIEW IF EXISTS `salereport_withsale` */;
/*!50001 DROP TABLE IF EXISTS `salereport_withsale` */;

/*!50001 CREATE TABLE `salereport_withsale` (
  `Product` varchar(500) default NULL,
  `Outlet` varchar(100) default NULL,
  `CREATED_DATE` date default NULL,
  `ITEM_DISCOUNT_PRCT` decimal(20,2) default NULL,
  `Revenue` decimal(57,6) default NULL,
  `Revenue_tax_incl` decimal(58,6) default NULL,
  `Cost_of_Goods` decimal(52,2) default NULL,
  `Gross_Profit` decimal(53,2) default NULL,
  `Margin` decimal(50,6) default NULL,
  `Items_Sold` decimal(33,0) default NULL,
  `Tax` decimal(42,2) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `sms_report` */

DROP TABLE IF EXISTS `sms_report`;

/*!50001 DROP VIEW IF EXISTS `sms_report` */;
/*!50001 DROP TABLE IF EXISTS `sms_report` */;

/*!50001 CREATE TABLE `sms_report` (
  `MESSAGE_DETAIL_ID` int(11) NOT NULL default '0',
  `SENDER_ID` varchar(256) default NULL,
  `RECEIVER_ID` varchar(256) default NULL,
  `MESSAGE_DESCRIPTION` varchar(1000) default NULL,
  `DELIVERY_ID` mediumblob,
  `DELIVERY_STATUS` varchar(1000) default NULL,
  `MESSAGE_ASSOCIATION_ID` int(11) NOT NULL,
  `MESSAGE_BUNDLE_COUNT` int(11) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL,
  `OUTLET_NAME` varchar(100) default NULL,
  `SMS_SENT_DATE` varbinary(49) default NULL,
  `CREATED_DATE` date default NULL,
  `SENDER_NAME` longtext,
  `CUSTOMER_NAME` varchar(200) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `stock_order_v` */

DROP TABLE IF EXISTS `stock_order_v`;

/*!50001 DROP VIEW IF EXISTS `stock_order_v` */;
/*!50001 DROP TABLE IF EXISTS `stock_order_v` */;

/*!50001 CREATE TABLE `stock_order_v` (
  `STOCK_ORDER_ID` int(11) NOT NULL default '0',
  `STOCK_REF_NO` varchar(45) default NULL,
  `STATUS_ASSOCICATION_ID` int(11) NOT NULL default '0',
  `DILIVERY_DUE_DATE` varbinary(19) NOT NULL default '',
  `CONTACT_ID` int(11) NOT NULL default '0',
  `ORDER_NO` varchar(45) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) NOT NULL default '0',
  `SOURCE_OUTLET_ASSOCICATION_ID` int(11) default NULL,
  `CONTACT_INVOICE_NO` varchar(45) default NULL,
  `STOCK_ORDER_DATE` varbinary(19) default NULL,
  `REMARKS` varchar(500) default NULL,
  `ORDR_RECV_DATE` varbinary(19) default NULL,
  `RETURN_NO` varchar(45) default NULL,
  `STOCK_ORDER_TYPE_ASSOCICATION_ID` int(11) NOT NULL default '0',
  `AUTOFILL_REORDER` int(1) NOT NULL default '0',
  `RETAIL_PRICE_BILL` int(1) NOT NULL default '0',
  `ACTIVE_INDICATOR` int(1) NOT NULL default '0',
  `CREATED_DATE` varbinary(19) NOT NULL default '',
  `LAST_UPDATED` varbinary(19) NOT NULL default '',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL default '0',
  `TOTAL_ITEMS` decimal(20,2) NOT NULL default '0.00',
  `TOTAL_AMOUNT` decimal(20,2) NOT NULL default '0.00',
  `STATUS_DESC` varchar(45) default NULL,
  `STOCK_ORDER_TYPE_DESC` varchar(45) default NULL,
  `outlet` varchar(100) default NULL,
  `source` varchar(100) default NULL,
  `Supplier` varchar(200) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `supplier_sales_report` */

DROP TABLE IF EXISTS `supplier_sales_report`;

/*!50001 DROP VIEW IF EXISTS `supplier_sales_report` */;
/*!50001 DROP TABLE IF EXISTS `supplier_sales_report` */;

/*!50001 CREATE TABLE `supplier_sales_report` (
  `Product` varchar(500) default NULL,
  `Outlet` varchar(100) default NULL,
  `Supplier` varchar(200) default NULL,
  `CREATED_DATE` date default NULL,
  `Revenue` decimal(57,6) default NULL,
  `Revenue_tax_incl` decimal(58,6) default NULL,
  `Cost_of_Goods` decimal(52,2) default NULL,
  `Gross_Profit` decimal(53,2) default NULL,
  `Margin` decimal(50,6) default NULL,
  `Items_Sold` decimal(32,0) default NULL,
  `Tax` decimal(42,2) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `tag_sales_report` */

DROP TABLE IF EXISTS `tag_sales_report`;

/*!50001 DROP VIEW IF EXISTS `tag_sales_report` */;
/*!50001 DROP TABLE IF EXISTS `tag_sales_report` */;

/*!50001 CREATE TABLE `tag_sales_report` (
  `Outlet` varchar(100) default NULL,
  `Tag` varchar(200) NOT NULL,
  `CREATED_DATE` date default NULL,
  `Revenue` decimal(52,2) default NULL,
  `Revenue_tax_incl` decimal(53,2) default NULL,
  `Cost_of_Goods` decimal(52,2) default NULL,
  `Gross_Profit` decimal(53,2) default NULL,
  `Margin` decimal(50,6) default NULL,
  `Items_Sold` decimal(32,0) default NULL,
  `Tax` decimal(42,2) default NULL,
  `company_association_id` int(11) NOT NULL,
  `outlet_assocication_id` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `temp_sale` */

DROP TABLE IF EXISTS `temp_sale`;

/*!50001 DROP VIEW IF EXISTS `temp_sale` */;
/*!50001 DROP TABLE IF EXISTS `temp_sale` */;

/*!50001 CREATE TABLE `temp_sale` (
  `Product` varchar(500) default NULL,
  `Outlet` varchar(100) default NULL,
  `CREATED_DATE` date default NULL,
  `Revenue` decimal(57,6) default NULL,
  `Revenue_tax_incl` decimal(58,6) default NULL,
  `Cost_of_Goods` decimal(52,2) default NULL,
  `Gross_Profit` decimal(53,2) default NULL,
  `Margin` decimal(50,6) default NULL,
  `Items_Sold` decimal(33,0) default NULL,
  `Tax` decimal(42,2) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*Table structure for table `user_sales_report` */

DROP TABLE IF EXISTS `user_sales_report`;

/*!50001 DROP VIEW IF EXISTS `user_sales_report` */;
/*!50001 DROP TABLE IF EXISTS `user_sales_report` */;

/*!50001 CREATE TABLE `user_sales_report` (
  `Product` varchar(500) default NULL,
  `User` longtext,
  `Outlet` varchar(100) default NULL,
  `CREATED_DATE` date default NULL,
  `Revenue` decimal(57,6) default NULL,
  `Revenue_tax_incl` decimal(58,6) default NULL,
  `Cost_of_Goods` decimal(52,2) default NULL,
  `Gross_Profit` decimal(53,2) default NULL,
  `Margin` decimal(50,6) default NULL,
  `Items_Sold` decimal(33,0) default NULL,
  `Tax` decimal(42,2) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 */;

/*View structure for view brand_sales_report */

/*!50001 DROP TABLE IF EXISTS `brand_sales_report` */;
/*!50001 DROP VIEW IF EXISTS `brand_sales_report` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `brand_sales_report` AS select (select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,(select `brand`.`BRAND_NAME` AS `BRAND_NAME` from `brand` where (`brand`.`BRAND_ID` = (select `product`.`BRAND_ASSOCICATION_ID` AS `BRAND_ASSOCICATION_ID` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)))) AS `Brand`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) AS `Revenue`,(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date) */;

/*View structure for view cash_sale */

/*!50001 DROP TABLE IF EXISTS `cash_sale` */;
/*!50001 DROP VIEW IF EXISTS `cash_sale` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `cash_sale` AS select (select `product`.`PRODUCT_NAME` AS `product_name` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `product`,(select `outlet`.`OUTLET_NAME` AS `outlet_name` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `outlet`,cast(`invoice_main`.`CREATED_DATE` as date) AS `created_date`,sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``invoice_main_assocication_id``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) AS `revenue`,(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``invoice_main_assocication_id``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `cost_of_goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `gross_profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `margin`,sum((case when (`invoice_detail`.`ITEM_SALE_PRICE` < 0) then (`invoice_detail`.`PRODUCT_QUANTITY` * -(1)) else `invoice_detail`.`PRODUCT_QUANTITY` end)) AS `items_sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `company_association_id`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `outlet_assocication_id`,`receipt`.`PAYMENT_TYPE_ASSOCICATION_ID` AS `PAYMENT_TYPE_ASSOCICATION_ID` from ((`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) join `receipt` on(((`receipt`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`) and (`receipt`.`PAYMENT_TYPE_ASSOCICATION_ID` = 1)))) where (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date) */;

/*View structure for view contacts_summmary */

/*!50001 DROP TABLE IF EXISTS `contacts_summmary` */;
/*!50001 DROP VIEW IF EXISTS `contacts_summmary` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `contacts_summmary` AS select `contact`.`CONTACT_ID` AS `CONTACT_ID`,coalesce(concat(`contact`.`FIRST_NAME`,_latin1' ',coalesce(`contact`.`LAST_NAME`,_latin1'')),_latin1'-') AS `CONTACT_NAME`,coalesce(`contact`.`FIRST_NAME`,_latin1' ') AS `FIRST_NAME`,coalesce(`contact`.`LAST_NAME`,_latin1' ') AS `LAST_NAME`,coalesce(`contact`.`COMPANY_NAME`,_latin1'-') AS `COMPANY_NAME`,coalesce(`contact`.`CONTACT_BALANCE`,0) AS `BALANCE`,coalesce((select coalesce(`address`.`PHONE`,_latin1'##') AS `coalesce(``address``.``PHONE``,'##')` from `address` where ((`contact`.`CONTACT_ID` = `address`.`CONTACT_ASSOCICATION_ID`) and (`address`.`ADDRESS_TYPE` = _latin1'Physical Address')) limit 1),_latin1'-') AS `PHONE`,`contact`.`CREATED_DATE` AS `CREATED_DATE`,`contact`.`ACTIVE_INDICATOR` AS `ACTIVE_INDICATOR`,`contact`.`OUTLET_ASSOCIATION_ID` AS `OUTLET_ASSOCIATION_ID`,`contact`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`contact`.`LAST_UPDATED` AS `LAST_UPDATED`,(select `contact_group`.`CONTACT_GROUP_NAME` AS `CONTACT_GROUP_NAME` from `contact_group` where (`contact`.`CONTACT_GROUP_ASSOCIATION_ID` = `contact_group`.`CONTACT_GROUP_ID`) limit 1) AS `CONTACT_GROUP`,`contact`.`CONTACT_TYPE` AS `CONTACT_TYPE` from `contact` order by `contact`.`CREATED_DATE` desc */;

/*View structure for view credit_sale */

/*!50001 DROP TABLE IF EXISTS `credit_sale` */;
/*!50001 DROP VIEW IF EXISTS `credit_sale` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `credit_sale` AS select (select `product`.`PRODUCT_NAME` AS `product_name` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `product`,(select `outlet`.`OUTLET_NAME` AS `outlet_name` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `outlet`,cast(`invoice_main`.`CREATED_DATE` as date) AS `created_date`,sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``invoice_main_assocication_id``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) AS `revenue`,(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``invoice_main_assocication_id``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `cost_of_goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `gross_profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `margin`,sum((case when (`invoice_detail`.`ITEM_SALE_PRICE` < 0) then (`invoice_detail`.`PRODUCT_QUANTITY` * -(1)) else `invoice_detail`.`PRODUCT_QUANTITY` end)) AS `items_sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `company_association_id`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `outlet_assocication_id`,`receipt`.`PAYMENT_TYPE_ASSOCICATION_ID` AS `PAYMENT_TYPE_ASSOCICATION_ID` from ((`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) join `receipt` on(((`receipt`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`) and (`receipt`.`PAYMENT_TYPE_ASSOCICATION_ID` = 2)))) where (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date) */;

/*View structure for view customer_group_sales_report */

/*!50001 DROP TABLE IF EXISTS `customer_group_sales_report` */;
/*!50001 DROP VIEW IF EXISTS `customer_group_sales_report` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `customer_group_sales_report` AS select (select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,coalesce((select `contact_group`.`CONTACT_GROUP_NAME` AS `CONTACT_GROUP_NAME` from `contact_group` where (`contact_group`.`CONTACT_GROUP_ID` = (select `contact`.`CONTACT_GROUP_ASSOCIATION_ID` AS `CONTACT_GROUP_ASSOCIATION_ID` from `contact` where ((`contact`.`CONTACT_TYPE` like _latin1'%CUSTOMER%') and (`contact`.`CONTACT_ID` = (select `invoice_main`.`CONTACT_ASSOCIATION_ID` AS `CONTACT_ASSOCIATION_ID` from `invoice_main` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))))),_latin1'-') AS `Group_Name`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) AS `Revenue`,(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) group by coalesce((select `contact_group`.`CONTACT_GROUP_NAME` AS `CONTACT_GROUP_NAME` from `contact_group` where (`contact_group`.`CONTACT_GROUP_ID` = (select `contact`.`CONTACT_GROUP_ASSOCIATION_ID` AS `CONTACT_GROUP_ASSOCIATION_ID` from `contact` where ((`contact`.`CONTACT_TYPE` like _latin1'%CUSTOMER%') and (`contact`.`CONTACT_ID` = (select `invoice_main`.`CONTACT_ASSOCIATION_ID` AS `CONTACT_ASSOCIATION_ID` from `invoice_main` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))))),_latin1'-'),cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date) */;

/*View structure for view customer_sales_report */

/*!50001 DROP TABLE IF EXISTS `customer_sales_report` */;
/*!50001 DROP VIEW IF EXISTS `customer_sales_report` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `customer_sales_report` AS select (select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,coalesce((select `contact`.`CONTACT_NAME` AS `CONTACT_NAME` from `contact` where ((`contact`.`CONTACT_TYPE` like _latin1'%CUSTOMER%') and (`contact`.`CONTACT_ID` = (select `invoice_main`.`CONTACT_ASSOCIATION_ID` AS `CONTACT_ASSOCIATION_ID` from `invoice_main` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))),_latin1'-') AS `Customer`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) AS `Revenue`,(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) group by coalesce((select `contact`.`CONTACT_NAME` AS `CONTACT_NAME` from `contact` where ((`contact`.`CONTACT_TYPE` like _latin1'%CUSTOMER%') and (`contact`.`CONTACT_ID` = (select `invoice_main`.`CONTACT_ASSOCIATION_ID` AS `CONTACT_ASSOCIATION_ID` from `invoice_main` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))),_latin1'-'),cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date) */;

/*View structure for view expensereport */

/*!50001 DROP TABLE IF EXISTS `expensereport` */;
/*!50001 DROP VIEW IF EXISTS `expensereport` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `expensereport` AS select (select `expense_type`.`EXPENSE_TYPE_NAME` AS `EXPENSE_TYPE_NAME` from `expense_type` where (`cash_managment`.`EXPENSE_TYPE_ASSOCIATION_ID` = `expense_type`.`EXPENSE_TYPE_ID`)) AS `EXPENSE`,(select concat(`user`.`FIRST_NAME`,_latin1' ',`user`.`LAST_NAME`) AS `concat(``user``.``FIRST_NAME``,' ',``user``.``LAST_NAME``)` from `user` where (`user`.`USER_ID` = `cash_managment`.`CREATED_BY`)) AS `User`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`cash_managment`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,cast(`cash_managment`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum(`cash_managment`.`CASH_AMT`) AS `CASH_AMT`,`cash_managment`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`cash_managment`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID`,`cash_managment`.`CASH_MANAGMENT_TYPE` AS `Type` from `cash_managment` group by `cash_managment`.`EXPENSE_TYPE_ASSOCIATION_ID`,cast(`cash_managment`.`CREATED_DATE` as date),`cash_managment`.`CASH_MANAGMENT_TYPE` order by cast(`cash_managment`.`CREATED_DATE` as date) */;

/*View structure for view inventory__detail_report */

/*!50001 DROP TABLE IF EXISTS `inventory__detail_report` */;
/*!50001 DROP VIEW IF EXISTS `inventory__detail_report` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `inventory__detail_report` AS (select `product`.`PRODUCT_ID` AS `ID`,`product`.`PRODUCT_NAME` AS `PRODUCT_NAME`,`product`.`SKU` AS `SKU`,`product`.`SUPPLY_PRICE_EXCL_TAX` AS `SUPPLY_PRICE_EXCL_TAX`,`product`.`MARKUP_PRCT` AS `MARKUP_PRCT`,coalesce(`product`.`REORDER_POINT`,0) AS `REORDER_POINT`,coalesce(`product`.`REORDER_AMOUNT`,0) AS `REORDER_AMOUNT`,`product`.`CURRENT_INVENTORY` AS `CURRENT_INVENTORY`,(((`product`.`SUPPLY_PRICE_EXCL_TAX` * `product`.`MARKUP_PRCT`) / 100) + `product`.`SUPPLY_PRICE_EXCL_TAX`) AS `NET_PRICE`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`product`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `OUTLET_NAME`,`product`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID`,`product`.`CREATED_DATE` AS `CREATED_DATE`,(select `brand`.`BRAND_NAME` AS `BRAND_NAME` from `brand` where (`product`.`BRAND_ASSOCICATION_ID` = `brand`.`BRAND_ID`)) AS `BRAND_NAME`,(select `product_type`.`PRODUCT_TYPE_NAME` AS `PRODUCT_TYPE_NAME` from `product_type` where (`product`.`PRODUCT_TYPE_ASSOCICATION_ID` = `product_type`.`PRODUCT_TYPE_ID`)) AS `PRODUCT_TYPE`,(select `contact`.`CONTACT_NAME` AS `CONTACT_NAME` from `contact` where (`product`.`CONTACT_ASSOCICATION_ID` = `contact`.`CONTACT_ID`)) AS `CONTACT_NAME`,`product`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,(`product`.`SUPPLY_PRICE_EXCL_TAX` * `product`.`CURRENT_INVENTORY`) AS `STOCK_VALUE`,((((`product`.`SUPPLY_PRICE_EXCL_TAX` * `product`.`MARKUP_PRCT`) / 100) + `product`.`SUPPLY_PRICE_EXCL_TAX`) * `product`.`CURRENT_INVENTORY`) AS `RETAIL_VALUE`,coalesce((select coalesce(sum(`stock_order_detail`.`ORDER_PROD_QTY`),0) AS `coalesce(sum(``stock_order_detail``.``ORDER_PROD_QTY``),0)` from (`stock_order_detail` join `stock_order`) where ((`stock_order_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`) and (`stock_order_detail`.`STOCK_ORDER_ASSOCICATION_ID` = `stock_order`.`STOCK_ORDER_ID`))),0) AS `TOTAL_STOCK_SENT` from `product` where ((`product`.`VARIANT_PRODUCTS` = _latin1'false') and (`product`.`ACTIVE_INDICATOR` = 1)) group by `product`.`PRODUCT_UUID`) union (select `product_variant`.`PRODUCT_VARIANT_ID` AS `PRODUCT_VARIANT_ID`,concat((select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` where (`product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`)),_latin1' ',`product_variant`.`VARIANT_ATTRIBUTE_NAME`) AS `VARIANT_ATTRIBUTE_NAME`,`product_variant`.`SKU` AS `SKU`,`product_variant`.`SUPPLY_PRICE_EXCL_TAX` AS `SUPPLY_PRICE_EXCL_TAX`,`product_variant`.`MARKUP_PRCT` AS `MARKUP_PRCT`,`product_variant`.`REORDER_POINT` AS `REORDER_POINT`,`product_variant`.`REORDER_AMOUNT` AS `REORDER_AMOUNT`,`product_variant`.`CURRENT_INVENTORY` AS `CURRENT_INVENTORY`,(((`product_variant`.`SUPPLY_PRICE_EXCL_TAX` * `product_variant`.`MARKUP_PRCT`) / 100) + `product_variant`.`SUPPLY_PRICE_EXCL_TAX`) AS `NET_PRICE`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`product_variant`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `OUTLET_NAME`,`product_variant`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID`,`product_variant`.`CREATED_DATE` AS `CREATED_DATE`,(select `brand`.`BRAND_NAME` AS `BRAND_NAME` from `brand` where (`brand`.`BRAND_ID` = (select `product`.`BRAND_ASSOCICATION_ID` AS `BRAND_ASSOCICATION_ID` from `product` where (`product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`)))) AS `BRAND_NAME`,(select `product_type`.`PRODUCT_TYPE_NAME` AS `PRODUCT_TYPE_NAME` from `product_type` where (`product_type`.`PRODUCT_TYPE_ID` = (select `product`.`PRODUCT_TYPE_ASSOCICATION_ID` AS `PRODUCT_TYPE_ASSOCICATION_ID` from `product` where (`product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`)))) AS `PRODUCT_TYPE`,(select `contact`.`CONTACT_NAME` AS `CONTACT_NAME` from `contact` where (`contact`.`CONTACT_ID` = (select `product`.`CONTACT_ASSOCICATION_ID` AS `CONTACT_ASSOCICATION_ID` from `product` where (`product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`)))) AS `CONTACT_NAME`,`product_variant`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,(`product_variant`.`SUPPLY_PRICE_EXCL_TAX` * `product_variant`.`CURRENT_INVENTORY`) AS `STOCK_VALUE`,((((`product_variant`.`SUPPLY_PRICE_EXCL_TAX` * `product_variant`.`MARKUP_PRCT`) / 100) + `product_variant`.`SUPPLY_PRICE_EXCL_TAX`) * `product_variant`.`CURRENT_INVENTORY`) AS `RETAIL_VALUE`,coalesce((select coalesce(sum(`stock_order_detail`.`ORDER_PROD_QTY`),0) AS `coalesce(sum(``stock_order_detail``.``ORDER_PROD_QTY``),0)` from (`stock_order_detail` join `stock_order`) where ((`stock_order_detail`.`PRODUCT_VARIANT_ASSOCICATION_ID` = `product_variant`.`PRODUCT_VARIANT_ID`) and (`stock_order_detail`.`STOCK_ORDER_ASSOCICATION_ID` = `stock_order`.`STOCK_ORDER_ID`))),0) AS `Total_sent` from `product_variant` where (`product_variant`.`ACTIVE_INDICATOR` = 1) group by `product_variant`.`PRODUCT_VARIANT_UUID`) */;

/*View structure for view inventory_report */

/*!50001 DROP TABLE IF EXISTS `inventory_report` */;
/*!50001 DROP VIEW IF EXISTS `inventory_report` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `inventory_report` AS (select `product`.`PRODUCT_ID` AS `ID`,`product`.`PRODUCT_NAME` AS `PRODUCT_NAME`,`product`.`SKU` AS `SKU`,`product`.`SUPPLY_PRICE_EXCL_TAX` AS `SUPPLY_PRICE_EXCL_TAX`,`product`.`MARKUP_PRCT` AS `MARKUP_PRCT`,coalesce(`product`.`REORDER_POINT`,0) AS `REORDER_POINT`,coalesce(`product`.`REORDER_AMOUNT`,0) AS `REORDER_AMOUNT`,`product`.`CURRENT_INVENTORY` AS `CURRENT_INVENTORY`,(((`product`.`SUPPLY_PRICE_EXCL_TAX` * `product`.`MARKUP_PRCT`) / 100) + `product`.`SUPPLY_PRICE_EXCL_TAX`) AS `NET_PRICE`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`product`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `OUTLET_NAME`,`product`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID`,`product`.`CREATED_DATE` AS `CREATED_DATE`,(select `brand`.`BRAND_NAME` AS `BRAND_NAME` from `brand` where (`product`.`BRAND_ASSOCICATION_ID` = `brand`.`BRAND_ID`)) AS `BRAND_NAME`,(select `product_type`.`PRODUCT_TYPE_NAME` AS `PRODUCT_TYPE_NAME` from `product_type` where (`product`.`PRODUCT_TYPE_ASSOCICATION_ID` = `product_type`.`PRODUCT_TYPE_ID`)) AS `PRODUCT_TYPE`,(select `contact`.`CONTACT_NAME` AS `CONTACT_NAME` from `contact` where (`product`.`CONTACT_ASSOCICATION_ID` = `contact`.`CONTACT_ID`)) AS `CONTACT_NAME`,`product`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,(`product`.`SUPPLY_PRICE_EXCL_TAX` * `product`.`CURRENT_INVENTORY`) AS `STOCK_VALUE`,((((`product`.`SUPPLY_PRICE_EXCL_TAX` * `product`.`MARKUP_PRCT`) / 100) + `product`.`SUPPLY_PRICE_EXCL_TAX`) * `product`.`CURRENT_INVENTORY`) AS `RETAIL_VALUE` from `product` where ((`product`.`VARIANT_PRODUCTS` = _latin1'false') and (`product`.`ACTIVE_INDICATOR` = 1)) group by `product`.`PRODUCT_UUID`,`product`.`OUTLET_ASSOCICATION_ID`) union (select `product_variant`.`PRODUCT_VARIANT_ID` AS `PRODUCT_VARIANT_ID`,concat((select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` where (`product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`)),_latin1' ',`product_variant`.`VARIANT_ATTRIBUTE_NAME`) AS `VARIANT_ATTRIBUTE_NAME`,`product_variant`.`SKU` AS `SKU`,`product_variant`.`SUPPLY_PRICE_EXCL_TAX` AS `SUPPLY_PRICE_EXCL_TAX`,`product_variant`.`MARKUP_PRCT` AS `MARKUP_PRCT`,coalesce(`product_variant`.`REORDER_POINT`,0) AS `COALESCE(REORDER_POINT,0)`,coalesce(`product_variant`.`REORDER_AMOUNT`,0) AS `COALESCE(REORDER_AMOUNT,0)`,`product_variant`.`CURRENT_INVENTORY` AS `CURRENT_INVENTORY`,(((`product_variant`.`SUPPLY_PRICE_EXCL_TAX` * `product_variant`.`MARKUP_PRCT`) / 100) + `product_variant`.`SUPPLY_PRICE_EXCL_TAX`) AS `NET_PRICE`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`product_variant`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `OUTLET_NAME`,`product_variant`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID`,`product_variant`.`CREATED_DATE` AS `CREATED_DATE`,(select `brand`.`BRAND_NAME` AS `BRAND_NAME` from `brand` where (`brand`.`BRAND_ID` = (select `product`.`BRAND_ASSOCICATION_ID` AS `BRAND_ASSOCICATION_ID` from `product` where (`product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`)))) AS `BRAND_NAME`,(select `product_type`.`PRODUCT_TYPE_NAME` AS `PRODUCT_TYPE_NAME` from `product_type` where (`product_type`.`PRODUCT_TYPE_ID` = (select `product`.`PRODUCT_TYPE_ASSOCICATION_ID` AS `PRODUCT_TYPE_ASSOCICATION_ID` from `product` where (`product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`)))) AS `PRODUCT_TYPE`,(select `contact`.`CONTACT_NAME` AS `CONTACT_NAME` from `contact` where (`contact`.`CONTACT_ID` = (select `product`.`CONTACT_ASSOCICATION_ID` AS `CONTACT_ASSOCICATION_ID` from `product` where (`product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`)))) AS `CONTACT_NAME`,`product_variant`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,(`product_variant`.`SUPPLY_PRICE_EXCL_TAX` * `product_variant`.`CURRENT_INVENTORY`) AS `STOCK_VALUE`,((((`product_variant`.`SUPPLY_PRICE_EXCL_TAX` * `product_variant`.`MARKUP_PRCT`) / 100) + `product_variant`.`SUPPLY_PRICE_EXCL_TAX`) * `product_variant`.`CURRENT_INVENTORY`) AS `RETAIL_VALUE` from `product_variant` where (`product_variant`.`ACTIVE_INDICATOR` = 1) group by `product_variant`.`PRODUCT_VARIANT_UUID`,`product_variant`.`OUTLET_ASSOCICATION_ID`) */;

/*View structure for view outlet_sales_report */

/*!50001 DROP TABLE IF EXISTS `outlet_sales_report` */;
/*!50001 DROP VIEW IF EXISTS `outlet_sales_report` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `outlet_sales_report` AS select (select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) AS `Revenue`,(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date) */;

/*View structure for view payment_report */

/*!50001 DROP TABLE IF EXISTS `payment_report` */;
/*!50001 DROP VIEW IF EXISTS `payment_report` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `payment_report` AS select `invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `OUTLET`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum(`invoice_main`.`INVOICE_NET_AMT`) AS `AMOUNT`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID` from `invoice_main` where (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) group by `invoice_main`.`COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date) */;

/*View structure for view price_book_detail_summary */

/*!50001 DROP TABLE IF EXISTS `price_book_detail_summary` */;
/*!50001 DROP VIEW IF EXISTS `price_book_detail_summary` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `price_book_detail_summary` AS select `price_book_detail`.`PRICE_BOOK_DETAIL_ID` AS `PRICE_BOOK_DETAIL_ID`,`price_book_detail`.`PRICE_BOOK_ASSOCICATION_ID` AS `PRICE_BOOK_ASSOCICATION_ID`,`price_book_detail`.`PRODUCT_ASSOCICATION_ID` AS `PRODUCT_ASSOCICATION_ID`,`price_book_detail`.`UUID` AS `UUID`,(select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` where (`product`.`PRODUCT_ID` = `price_book_detail`.`PRODUCT_ASSOCICATION_ID`)) AS `PRODUCT_NAME`,ifnull(`price_book_detail`.`PRODUCT_VARIENT_ASSOCICATION_ID`,0) AS `PRODUCT_VARIENT_ASSOCICATION_ID`,coalesce((select `product_variant`.`VARIANT_ATTRIBUTE_NAME` AS `VARIANT_ATTRIBUTE_NAME` from `product_variant` where (`product_variant`.`PRODUCT_VARIANT_ID` = `price_book_detail`.`PRODUCT_VARIENT_ASSOCICATION_ID`)),_latin1'-') AS `VARIANT_ATTRIBUTE_NAME`,`price_book_detail`.`SUPPLY_PRICE` AS `SUPPLY_PRICE`,`price_book_detail`.`MARKUP` AS `MARKUP`,`price_book_detail`.`DISCOUNT` AS `DISCOUNT`,`price_book_detail`.`CREATED_BY` AS `CREATED_BY`,`price_book_detail`.`UPDATED_BY` AS `UPDATED_BY`,`price_book_detail`.`SALES_TAX_ASSOCIATION_ID` AS `SALES_TAX_ASSOCIATION_ID`,`price_book_detail`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID` from `price_book_detail` where (`price_book_detail`.`ACTIVE_INDICATOR` = _utf8'1') */;

/*View structure for view product_summmary */

/*!50001 DROP TABLE IF EXISTS `product_summmary` */;
/*!50001 DROP VIEW IF EXISTS `product_summmary` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `product_summmary` AS select `product`.`PRODUCT_ID` AS `ID`,`product`.`PRODUCT_NAME` AS `PRODUCT_NAME`,`product`.`SKU` AS `SKU`,`product`.`SUPPLY_PRICE_EXCL_TAX` AS `SUPPLY_PRICE_EXCL_TAX`,`product`.`REORDER_POINT` AS `REORDER_POINT`,`product`.`REORDER_AMOUNT` AS `REORDER_AMOUNT`,`product`.`CURRENT_INVENTORY` AS `CURRENT_INVENTORY`,(((`product`.`SUPPLY_PRICE_EXCL_TAX` * `product`.`MARKUP_PRCT`) / 100) + `product`.`SUPPLY_PRICE_EXCL_TAX`) AS `NET_PRICE`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`product`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `OUTLET_NAME`,`product`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID`,`product`.`CREATED_DATE` AS `CREATED_DATE`,(select `brand`.`BRAND_NAME` AS `BRAND_NAME` from `brand` where (`product`.`BRAND_ASSOCICATION_ID` = `brand`.`BRAND_ID`)) AS `BRAND_NAME`,(select `product_type`.`PRODUCT_TYPE_NAME` AS `PRODUCT_TYPE_NAME` from `product_type` where (`product`.`PRODUCT_TYPE_ASSOCICATION_ID` = `product_type`.`PRODUCT_TYPE_ID`)) AS `PRODUCT_TYPE`,(select `contact`.`CONTACT_NAME` AS `CONTACT_NAME` from `contact` where (`product`.`CONTACT_ASSOCICATION_ID` = `contact`.`CONTACT_ID`)) AS `CONTACT_NAME`,(select count(0) AS `count(0)` from `product_variant` where ((`product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`) and (`product_variant`.`ACTIVE_INDICATOR` = 1))) AS `VARIANT_COUNT`,`product`.`CURRENT_INVENTORY` AS `VARIANT_CURRENT_INVENTORY`,`product`.`SKU` AS `VARIANT_SKU`,`product`.`SUPPLY_PRICE_EXCL_TAX` AS `VARIANT_SUPPLY_PRICE_EXCL_TAX`,`product`.`REORDER_POINT` AS `VARIANT_REORDER_POINT`,`product`.`REORDER_AMOUNT` AS `VARIANT_REORDER_AMOUNT`,(((`product`.`SUPPLY_PRICE_EXCL_TAX` * `product`.`MARKUP_PRCT`) / 100) + `product`.`SUPPLY_PRICE_EXCL_TAX`) AS `VARIANT_NET_PRICE`,(select count(0) AS `count(0)` from `composite_product` where ((`composite_product`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`) and (`composite_product`.`ACTIVE_INDICATOR` = 1))) AS `VARIANT_COMP_COUNT`,`product`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`product`.`IMAGE_PATH` AS `IMAGE_PATH`,`product`.`VARIANT_PRODUCTS` AS `VARIANT_PRODUCTS`,`product`.`STANDARD_PRODUCT` AS `STANDARD_PRODUCT`,coalesce(`product`.`IS_COMPOSITE`,_latin1'false') AS `IS_COMPOSITE`,(select coalesce(sum(`product_variant`.`CURRENT_INVENTORY`),0) AS `coalesce(sum(``product_variant``.``CURRENT_INVENTORY``),0)` from `product_variant` where ((`product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`) and (`product_variant`.`ACTIVE_INDICATOR` = 1)) having (`product`.`CURRENT_INVENTORY` is not null)) AS `VARIANT_INVENTORY_COUNT` from `product` group by `product`.`PRODUCT_UUID`,`product`.`OUTLET_ASSOCICATION_ID` order by `product`.`CREATED_DATE` desc */;

/*View structure for view producttype_sales_report */

/*!50001 DROP TABLE IF EXISTS `producttype_sales_report` */;
/*!50001 DROP VIEW IF EXISTS `producttype_sales_report` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `producttype_sales_report` AS select (select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,(select `product_type`.`PRODUCT_TYPE_NAME` AS `PRODUCT_TYPE_NAME` from `product_type` where (`product_type`.`PRODUCT_TYPE_ID` = (select `product`.`PRODUCT_TYPE_ASSOCICATION_ID` AS `PRODUCT_TYPE_ASSOCICATION_ID` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)))) AS `ProductType`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) AS `Revenue`,(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date) */;

/*View structure for view register_report */

/*!50001 DROP TABLE IF EXISTS `register_report` */;
/*!50001 DROP VIEW IF EXISTS `register_report` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `register_report` AS select `daily_register`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`daily_register`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `OUTLET_NAME`,ifnull(`daily_register`.`CASH_AMT_ACTUAL`,0) AS `CASH_AMT_ACTUAL`,ifnull(`daily_register`.`CREDIT_CARD_AMT_ACTUAL`,0) AS `CREDIT_CARD_AMT_ACTUAL`,date_format(`daily_register`.`CREATED_DATE`,_latin1'%b %d %Y %h:%i %p') AS `OPENING_DATE`,date_format(`daily_register`.`CLOSED_DATE`,_latin1'%b %d %Y %h:%i %p') AS `CLOSING_DATE`,(select concat(`user`.`FIRST_NAME`,_latin1' ',`user`.`LAST_NAME`) AS `concat(``user``.``FIRST_NAME``,' ',``user``.``LAST_NAME``)` from `user` where (`user`.`USER_ID` = `daily_register`.`CREATED_BY`)) AS `Open_By`,coalesce((select concat(`user`.`FIRST_NAME`,_latin1' ',`user`.`LAST_NAME`) AS `concat(``user``.``FIRST_NAME``,' ',``user``.``LAST_NAME``)` from `user` where (`user`.`USER_ID` = `daily_register`.`UPDATED_BY`)),`daily_register`.`CREATED_BY`) AS `Close_By`,(select `status`.`STATUS_DESC` AS `STATUS_DESC` from `status` where (`status`.`STATUS_ID` = `daily_register`.`STATUS_ASSOCICATION_ID`)) AS `Status`,`daily_register`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`daily_register`.`DAILY_REGISTER_ID` AS `DAILY_REGISTER_ID`,coalesce(`daily_register`.`REGISTER_CLOSING_NOTES`,_latin1'-') AS `REGISTER_CLOSING_NOTES`,coalesce(`daily_register`.`REGISTER_OPENING_NOTES`,_latin1'-') AS `REGISTER_OPENING_NOTES` from `daily_register` order by cast(`daily_register`.`CREATED_DATE` as date) */;

/*View structure for view sale_details */

/*!50001 DROP TABLE IF EXISTS `sale_details` */;
/*!50001 DROP VIEW IF EXISTS `sale_details` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `sale_details` AS select `product`.`PRODUCT_NAME` AS `Product`,coalesce(`product_variant`.`VARIANT_ATTRIBUTE_NAME`,`product`.`PRODUCT_NAME`) AS `Variant`,coalesce(`product_variant`.`SKU`,`product`.`PRODUCT_NAME`) AS `SKU`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_detail`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,cast(`invoice_detail`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Revenue`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,(100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)) AS `Margin`,sum((case when (`invoice_detail`.`ITEM_SALE_PRICE` < 0) then (`invoice_detail`.`PRODUCT_QUANTITY` * -(1)) else `invoice_detail`.`PRODUCT_QUANTITY` end)) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_detail`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_detail`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from ((`invoice_detail` left join `product_variant` on((`product_variant`.`PRODUCT_VARIANT_ID` = `invoice_detail`.`PRODUCT_VARIENT_ASSOCIATION_ID`))) left join `product` on((`product`.`PRODUCT_ID` = `invoice_detail`.`PRODUCT_ASSOCIATION_ID`))) group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,`invoice_detail`.`PRODUCT_VARIENT_ASSOCIATION_ID`,cast(`invoice_detail`.`CREATED_DATE` as date) order by cast(`invoice_detail`.`CREATED_DATE` as date) */;

/*View structure for view salereport_withoutsale */

/*!50001 DROP TABLE IF EXISTS `salereport_withoutsale` */;
/*!50001 DROP VIEW IF EXISTS `salereport_withoutsale` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `salereport_withoutsale` AS select (select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) AS `Revenue`,(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum((case when (`invoice_detail`.`ITEM_SALE_PRICE` < 0) then (`invoice_detail`.`PRODUCT_QUANTITY` * -(1)) else `invoice_detail`.`PRODUCT_QUANTITY` end)) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where ((`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) and (`invoice_detail`.`ITEM_DISCOUNT_PRCT` = 0) and (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) and ((`invoice_main`.`INVOICE_DISCOUNT` = 0) or isnull(`invoice_main`.`INVOICE_DISCOUNT`))) group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date) */;

/*View structure for view salereport_withsale */

/*!50001 DROP TABLE IF EXISTS `salereport_withsale` */;
/*!50001 DROP VIEW IF EXISTS `salereport_withsale` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `salereport_withsale` AS select (select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,`invoice_detail`.`ITEM_DISCOUNT_PRCT` AS `ITEM_DISCOUNT_PRCT`,sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) AS `Revenue`,(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum((case when (`invoice_detail`.`ITEM_SALE_PRICE` < 0) then (`invoice_detail`.`PRODUCT_QUANTITY` * -(1)) else `invoice_detail`.`PRODUCT_QUANTITY` end)) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where (((`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) and (`invoice_detail`.`ITEM_DISCOUNT_PRCT` > 0)) or ((`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) and (`invoice_main`.`INVOICE_DISCOUNT` > 0))) group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date) */;

/*View structure for view sms_report */

/*!50001 DROP TABLE IF EXISTS `sms_report` */;
/*!50001 DROP VIEW IF EXISTS `sms_report` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `sms_report` AS select `message_detail`.`MESSAGE_DETAIL_ID` AS `MESSAGE_DETAIL_ID`,`message_detail`.`SENDER_ID` AS `SENDER_ID`,`message_detail`.`RECEIVER_ID` AS `RECEIVER_ID`,`message_detail`.`MESSAGE_DESCRIPTION` AS `MESSAGE_DESCRIPTION`,`message_detail`.`DELIVERY_ID` AS `DELIVERY_ID`,`message_detail`.`DELIVERY_STATUS` AS `DELIVERY_STATUS`,`message_detail`.`MESSAGE_ASSOCIATION_ID` AS `MESSAGE_ASSOCIATION_ID`,(select `message`.`MESSAGE_BUNDLE_COUNT` AS `MESSAGE_BUNDLE_COUNT` from `message` where (`message_detail`.`MESSAGE_ASSOCIATION_ID` = `message`.`MESSAGE_ID`)) AS `MESSAGE_BUNDLE_COUNT`,`message_detail`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`message_detail`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `OUTLET_NAME`,date_format(`message_detail`.`CREATED_DATE`,_utf8'%b %d %Y %h:%i %p') AS `SMS_SENT_DATE`,cast(`message_detail`.`CREATED_DATE` as date) AS `CREATED_DATE`,(select concat(`user`.`FIRST_NAME`,_latin1' ',`user`.`LAST_NAME`) AS `concat(``user``.``FIRST_NAME``,' ',``user``.``LAST_NAME``)` from `user` where (`user`.`USER_ID` = `message_detail`.`CREATED_BY`)) AS `SENDER_NAME`,coalesce((select `contact`.`FIRST_NAME` AS `FIRST_NAME` from `contact` where (`contact`.`CONTACT_ID` = `message_detail`.`RECEIVER_ID`)),_latin1'BULK') AS `CUSTOMER_NAME`,`message_detail`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID` from `message_detail` order by cast(`message_detail`.`CREATED_DATE` as date) */;

/*View structure for view stock_order_v */

/*!50001 DROP TABLE IF EXISTS `stock_order_v` */;
/*!50001 DROP VIEW IF EXISTS `stock_order_v` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `stock_order_v` AS select `s`.`STOCK_ORDER_ID` AS `STOCK_ORDER_ID`,coalesce(`s`.`STOCK_REF_NO`,_latin1'') AS `STOCK_REF_NO`,coalesce(`s`.`STATUS_ASSOCICATION_ID`,0) AS `STATUS_ASSOCICATION_ID`,coalesce(`s`.`DILIVERY_DUE_DATE`,now()) AS `DILIVERY_DUE_DATE`,coalesce(`s`.`CONTACT_ID`,0) AS `CONTACT_ID`,coalesce(`s`.`ORDER_NO`,_latin1'') AS `ORDER_NO`,coalesce(`s`.`OUTLET_ASSOCICATION_ID`,0) AS `OUTLET_ASSOCICATION_ID`,coalesce(`s`.`SOURCE_OUTLET_ASSOCICATION_ID`,0) AS `SOURCE_OUTLET_ASSOCICATION_ID`,coalesce(`s`.`CONTACT_INVOICE_NO`,_latin1'') AS `CONTACT_INVOICE_NO`,coalesce(`s`.`STOCK_ORDER_DATE`,now()) AS `STOCK_ORDER_DATE`,coalesce(`s`.`REMARKS`,_latin1'') AS `REMARKS`,coalesce(`s`.`ORDR_RECV_DATE`,now()) AS `ORDR_RECV_DATE`,coalesce(`s`.`RETURN_NO`,_latin1'') AS `RETURN_NO`,coalesce(`s`.`STOCK_ORDER_TYPE_ASSOCICATION_ID`,0) AS `STOCK_ORDER_TYPE_ASSOCICATION_ID`,coalesce(`s`.`AUTOFILL_REORDER`,0) AS `AUTOFILL_REORDER`,coalesce(`s`.`RETAIL_PRICE_BILL`,0) AS `RETAIL_PRICE_BILL`,coalesce(`s`.`ACTIVE_INDICATOR`,0) AS `ACTIVE_INDICATOR`,coalesce(`s`.`CREATED_DATE`,now()) AS `CREATED_DATE`,coalesce(`s`.`LAST_UPDATED`,now()) AS `LAST_UPDATED`,coalesce(`s`.`CREATED_BY`,0) AS `CREATED_BY`,coalesce(`s`.`UPDATED_BY`,0) AS `UPDATED_BY`,coalesce(`s`.`COMPANY_ASSOCIATION_ID`,0) AS `COMPANY_ASSOCIATION_ID`,coalesce(`s`.`TOTAL_ITEMS`,0) AS `TOTAL_ITEMS`,coalesce(`s`.`TOTAL_AMOUNT`,0) AS `TOTAL_AMOUNT`,coalesce(`st`.`STATUS_DESC`,_latin1'') AS `STATUS_DESC`,coalesce(`stype`.`STOCK_ORDER_TYPE_DESC`,_latin1'') AS `STOCK_ORDER_TYPE_DESC`,coalesce(`o1`.`OUTLET_NAME`,_latin1'') AS `outlet`,coalesce(`o2`.`OUTLET_NAME`,_latin1'') AS `source`,coalesce(`c`.`CONTACT_NAME`,_latin1'') AS `Supplier` from (((((`stock_order` `s` left join `outlet` `o1` on((`o1`.`OUTLET_ID` = `s`.`OUTLET_ASSOCICATION_ID`))) left join `outlet` `o2` on((`o2`.`OUTLET_ID` = `s`.`SOURCE_OUTLET_ASSOCICATION_ID`))) left join `contact` `c` on((`c`.`CONTACT_ID` = `s`.`CONTACT_ID`))) left join `status` `st` on((`st`.`STATUS_ID` = `s`.`STATUS_ASSOCICATION_ID`))) left join `stock_order_type` `stype` on((`stype`.`STOCK_ORDER_TYPE_ID` = `s`.`STOCK_ORDER_TYPE_ASSOCICATION_ID`))) order by `s`.`STOCK_ORDER_ID` desc */;

/*View structure for view supplier_sales_report */

/*!50001 DROP TABLE IF EXISTS `supplier_sales_report` */;
/*!50001 DROP VIEW IF EXISTS `supplier_sales_report` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `supplier_sales_report` AS select (select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,(select `contact`.`CONTACT_NAME` AS `CONTACT_NAME` from `contact` where ((`contact`.`CONTACT_TYPE` like _latin1'%SUPPLIER%') and (`contact`.`CONTACT_ID` = (select `product`.`CONTACT_ASSOCICATION_ID` AS `CONTACT_ASSOCICATION_ID` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`))))) AS `Supplier`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) AS `Revenue`,(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) group by (select `contact`.`CONTACT_NAME` AS `CONTACT_NAME` from `contact` where ((`contact`.`CONTACT_TYPE` like _latin1'%SUPPLIER%') and (`contact`.`CONTACT_ID` = (select `product`.`CONTACT_ASSOCICATION_ID` AS `CONTACT_ASSOCICATION_ID` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`))))),cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date) */;

/*View structure for view tag_sales_report */

/*!50001 DROP TABLE IF EXISTS `tag_sales_report` */;
/*!50001 DROP VIEW IF EXISTS `tag_sales_report` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `tag_sales_report` AS select `outlet`.`OUTLET_NAME` AS `Outlet`,`tag`.`TAG_NAME` AS `Tag`,cast(`invoice_detail`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Revenue`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,(100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)) AS `Margin`,sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_detail`.`COMPANY_ASSOCIATION_ID` AS `company_association_id`,`invoice_detail`.`OUTLET_ASSOCICATION_ID` AS `outlet_assocication_id` from ((((`invoice_detail` join `outlet` on((`invoice_detail`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`))) join `product` on((`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`))) join `product_tag` on((`product_tag`.`PRODUCT_TAG_UUID` = `product`.`PRODUCT_UUID`))) join `tag` on((`product_tag`.`TAG_ASSOCICATION_ID` = `tag`.`TAG_ID`))) group by cast(`invoice_detail`.`CREATED_DATE` as date),`tag`.`TAG_NAME` */;

/*View structure for view temp_sale */

/*!50001 DROP TABLE IF EXISTS `temp_sale` */;
/*!50001 DROP VIEW IF EXISTS `temp_sale` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `temp_sale` AS select (select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) AS `Revenue`,(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum((case when (`invoice_detail`.`ITEM_SALE_PRICE` < 0) then (`invoice_detail`.`PRODUCT_QUANTITY` * -(1)) else `invoice_detail`.`PRODUCT_QUANTITY` end)) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date) */;

/*View structure for view user_sales_report */

/*!50001 DROP TABLE IF EXISTS `user_sales_report` */;
/*!50001 DROP VIEW IF EXISTS `user_sales_report` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `user_sales_report` AS select (select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,(select concat(`user`.`FIRST_NAME`,_latin1' ',`user`.`LAST_NAME`) AS `concat(``user``.``FIRST_NAME``,' ',``user``.``LAST_NAME``)` from `user` where (`user`.`USER_ID` = `invoice_main`.`SALES_USER`)) AS `User`,(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) AS `Revenue`,(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,sum((case when (`invoice_detail`.`ITEM_SALE_PRICE` < 0) then (`invoice_detail`.`PRODUCT_QUANTITY` * -(1)) else `invoice_detail`.`PRODUCT_QUANTITY` end)) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where (`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) order by cast(`invoice_main`.`CREATED_DATE` as date) */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
