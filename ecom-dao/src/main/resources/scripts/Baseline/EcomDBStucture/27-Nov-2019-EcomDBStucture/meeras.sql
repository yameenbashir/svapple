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

/*Table structure for table `account_type` */

DROP TABLE IF EXISTS `account_type`;

CREATE TABLE `account_type` (
  `ACCOUNT_TYPE_ID` int(11) NOT NULL auto_increment,
  `ACCOUNT_TYPE_NAME` varchar(256) NOT NULL,
  `MAIN_ACCOUNT_TYPE_INDICATOR` bit(1) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`ACCOUNT_TYPE_ID`),
  KEY `ACCOUNT_TYPE_CREATED_BY_FK` (`CREATED_BY`),
  KEY `ACCOUNT_TYPE_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `ACCOUNT_TYPE_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  CONSTRAINT `ACCOUNT_TYPE_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `ACCOUNT_TYPE_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `ACCOUNT_TYPE_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `account_type` */

/*Table structure for table `activity_detail` */

DROP TABLE IF EXISTS `activity_detail`;

CREATE TABLE `activity_detail` (
  `ACTIVITY_DETAIL_ID` int(11) NOT NULL auto_increment,
  `EMPLOYEE_ASSOCIATION_ID` int(11) NOT NULL,
  `EMPLOYEE_NAME` varchar(256) NOT NULL,
  `EMLOYEE_EMAIL` varchar(256) NOT NULL,
  `CREATED_BY_MANAGER_ID` int(11) NOT NULL,
  `activity_detail` varchar(256) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `IP_ADDRESS` varchar(256) NOT NULL,
  `BROWSER_NAME` varchar(256) NOT NULL,
  `BROWSER_VERSION` varchar(256) NOT NULL,
  `OPERATING_SYSTEM` varchar(256) NOT NULL,
  `DEVICE_TYPE` varchar(256) NOT NULL,
  `SESSION_ID` varchar(256) NOT NULL,
  `SEVERITY_ASSOCIATION_ID` int(11) NOT NULL,
  `OTHER_INFORMATION` blob NOT NULL,
  `IS_EXCEPTION` varchar(10) NOT NULL default 'false',
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`ACTIVITY_DETAIL_ID`),
  KEY `ACTIVITY_DETAIL_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `ACTIVITY_DETAIL_EMPLOYEE_ASSOCIATION_ID_FK` (`EMPLOYEE_ASSOCIATION_ID`),
  KEY `ACTIVITY_DETAIL_CREATTED_BY_MANAGER_ID_FK` (`CREATED_BY_MANAGER_ID`),
  KEY `ACTIVITY_DETAIL_SEVERITY_ASSOCIATION_ID_FK` (`SEVERITY_ASSOCIATION_ID`),
  CONSTRAINT `ACTIVITY_DETAIL_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `ACTIVITY_DETAIL_CREATTED_BY_MANAGER_ID_FK` FOREIGN KEY (`CREATED_BY_MANAGER_ID`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `ACTIVITY_DETAIL_EMPLOYEE_ASSOCIATION_ID_FK` FOREIGN KEY (`EMPLOYEE_ASSOCIATION_ID`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `ACTIVITY_DETAIL_SEVERITY_ASSOCIATION_ID_FK` FOREIGN KEY (`SEVERITY_ASSOCIATION_ID`) REFERENCES `severity` (`SEVERITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `activity_detail` */

insert  into `activity_detail`(`ACTIVITY_DETAIL_ID`,`EMPLOYEE_ASSOCIATION_ID`,`EMPLOYEE_NAME`,`EMLOYEE_EMAIL`,`CREATED_BY_MANAGER_ID`,`activity_detail`,`CREATED_DATE`,`IP_ADDRESS`,`BROWSER_NAME`,`BROWSER_VERSION`,`OPERATING_SYSTEM`,`DEVICE_TYPE`,`SESSION_ID`,`SEVERITY_ASSOCIATION_ID`,`OTHER_INFORMATION`,`IS_EXCEPTION`,`COMPANY_ASSOCIATION_ID`) values (1,1,'Admin','admin@meeras.com',1,'LoginController.doLogin','2019-11-28 14:39:20','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User:  admin@meeras.com Login Successfuly ','false',1),(2,1,'Admin','admin@meeras.com',1,'HomeController.getDashBoard','2019-11-28 14:39:20','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com dashboard loaded successfully ','false',1),(3,1,'Admin','admin@meeras.com',1,'OutletsController.getOutlets','2019-11-28 14:39:54','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived all outlets successfully ','false',1),(4,1,'Admin','admin@meeras.com',1,'ManageOutletController.getAllTimeZones','2019-11-28 14:39:55','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived all time zones successfully ','false',1),(5,1,'Admin','admin@meeras.com',1,'ManageOutletController.getAllCountry','2019-11-28 14:39:55','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived all countries successfully ','false',1),(6,1,'Admin','admin@meeras.com',1,'ManageOutletController.getAllSalesTax','2019-11-28 14:39:56','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived all sales tax successfully ','false',1),(7,1,'Admin','admin@meeras.com',1,'ManageOutletController.getOutletByOutletId','2019-11-28 14:39:56','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived Outlet+Main Outlet successfully ','false',1),(8,1,'Admin','admin@meeras.com',1,'ManageOutletController.getManageOutletControllerData','2019-11-28 14:39:56','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived ManageOutletControllerData successfully ','false',1),(9,1,'Admin','admin@meeras.com',1,'ManageOutletController.updateOutlet','2019-11-28 14:41:08','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com Updated Outlet+Main Outlet successfully ','false',1),(10,1,'Admin','admin@meeras.com',1,'OutletsController.getOutlets','2019-11-28 14:41:09','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived all outlets successfully ','false',1),(11,1,'Admin','admin@meeras.com',1,'ManageRegisterController.getRegisterByRegisterId','2019-11-28 14:41:15','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com Updated Register+Main Register successfully ','false',1),(12,1,'Admin','admin@meeras.com',1,'OutletsController.getOutlets','2019-11-28 14:41:17','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived all outlets successfully ','false',1),(13,1,'Admin','admin@meeras.com',1,'UsersController.getAllUsers','2019-11-28 14:41:19','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com fetched all users successfully ','false',1),(14,1,'Admin','admin@meeras.com',1,'PaymentTypeController.getAllPaymentTypes','2019-11-28 14:41:23','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived all paymentTypes successfully ','false',1),(15,1,'Admin','admin@meeras.com',1,'OutletsController.getOutlets','2019-11-28 14:41:24','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived all outlets successfully ','false',1),(16,1,'Admin','admin@meeras.com',1,'StoreController.getAllTimeZones','2019-11-28 14:41:26','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived all time zones successfully ','false',1),(17,1,'Admin','admin@meeras.com',1,'StoreController.getAllCurrencies','2019-11-28 14:41:26','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived all currencies successfully ','false',1),(18,1,'Admin','admin@meeras.com',1,'StoreController.getAllPrinterFormat','2019-11-28 14:41:26','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived all printer format successfully ','false',1),(19,1,'Admin','admin@meeras.com',1,'StoreController.getCompanyDetailsByCompanyID','2019-11-28 14:41:26','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived company detail successfully ','false',1),(20,1,'Admin','admin@meeras.com',1,'StoreController.getStoreControllerData','2019-11-28 14:41:26','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived StoreControllerData successfully ','false',1),(21,1,'Admin','admin@meeras.com',1,'LoginController.doLogin','2019-11-28 14:41:52','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User:  admin@meeras.com Login Successfuly ','false',1),(22,1,'Admin','admin@meeras.com',1,'HomeController.getDashBoard','2019-11-28 14:41:52','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com dashboard loaded successfully ','false',1),(23,1,'Admin','admin@meeras.com',1,'UsersController.getAllUsers','2019-11-28 14:41:55','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com fetched all users successfully ','false',1),(24,1,'Admin','admin@meeras.com',1,'NewUserController.getAllRoles','2019-11-28 14:42:00','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived all roles successfully ','false',1),(25,1,'Admin','admin@meeras.com',1,'NewUserController.getOutletsForDropDown','2019-11-28 14:42:00','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived all outlets successfully ','false',1),(26,1,'Admin','admin@meeras.com',1,'NewUserController.getNewUserControllerData','2019-11-28 14:42:00','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived NewUserControllerData successfully ','false',1),(27,1,'Admin','admin@meeras.com',1,'NewUserController.addUser','2019-11-28 14:42:24','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com Added User+Manager successfully ','false',1),(28,1,'Admin','admin@meeras.com',1,'UsersController.getAllUsers','2019-11-28 14:42:26','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com fetched all users successfully ','false',1),(29,1,'Admin','admin@meeras.com',1,'NewUserController.getAllRoles','2019-11-28 14:42:35','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived all roles successfully ','false',1),(30,1,'Admin','admin@meeras.com',1,'NewUserController.getOutletsForDropDown','2019-11-28 14:42:35','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived all outlets successfully ','false',1),(31,1,'Admin','admin@meeras.com',1,'NewUserController.getNewUserControllerData','2019-11-28 14:42:35','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived NewUserControllerData successfully ','false',1),(32,1,'Admin','admin@meeras.com',1,'NewUserController.addUser','2019-11-28 14:43:08','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com Added User+Cashier successfully ','false',1),(33,1,'Admin','admin@meeras.com',1,'UsersController.getAllUsers','2019-11-28 14:43:10','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com fetched all users successfully ','false',1),(34,2,'Manager','manager@meeras.com',2,'LoginController.doLogin','2019-11-28 14:44:03','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User:  manager@meeras.com Login Successfuly ','false',1),(35,2,'Manager','manager@meeras.com',2,'NewCustomerController.getAllCustomerGroups','2019-11-28 14:44:03','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User manager@meeras.com retrived all Customer Groups successfully ','false',1),(36,2,'Manager','manager@meeras.com',2,'SellController.getAllProducts','2019-11-28 14:44:04','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User manager@meeras.com retrived all products successfully ','false',1),(37,2,'Manager','manager@meeras.com',2,'NewCustomerController.getAllCustomerGroups','2019-11-28 14:44:04','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User manager@meeras.com retrived all Customer Groups successfully ','false',1),(38,2,'Manager','manager@meeras.com',2,'CustomersController.getAllCustomersOrderBYPhone','2019-11-28 14:44:04','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User manager@meeras.com retrived all Customers successfully ','false',1),(39,3,'Cashier','cashier@meeras.com',3,'LoginController.doLogin','2019-11-28 14:44:28','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User:  cashier@meeras.com Login Successfuly ','false',1),(40,3,'Cashier','cashier@meeras.com',3,'NewCustomerController.getAllCustomerGroups','2019-11-28 14:44:28','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User cashier@meeras.com retrived all Customer Groups successfully ','false',1),(41,3,'Cashier','cashier@meeras.com',3,'SellController.getAllProducts','2019-11-28 14:44:28','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User cashier@meeras.com retrived all products successfully ','false',1),(42,3,'Cashier','cashier@meeras.com',3,'NewCustomerController.getAllCustomerGroups','2019-11-28 14:44:28','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User cashier@meeras.com retrived all Customer Groups successfully ','false',1),(43,3,'Cashier','cashier@meeras.com',3,'CustomersController.getAllCustomersOrderBYPhone','2019-11-28 14:44:28','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User cashier@meeras.com retrived all Customers successfully ','false',1),(44,1,'Admin','admin@meeras.com',1,'LoginController.doLogin','2019-11-28 14:44:39','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User:  admin@meeras.com Login Successfuly ','false',1),(45,1,'Admin','admin@meeras.com',1,'HomeController.getDashBoard','2019-11-28 14:44:39','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com dashboard loaded successfully ','false',1),(46,1,'Admin','admin@meeras.com',1,'UsersController.getAllUsers','2019-11-28 14:44:42','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com fetched all users successfully ','false',1),(47,1,'Admin','admin@meeras.com',1,'UtilitiesController.getAllRoles','2019-11-28 14:44:42','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived all roles successfully ','false',1),(48,1,'Admin','admin@meeras.com',1,'ManageRoleController.getPageRightsListByRoleId','2019-11-28 14:44:45','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived menu list against role id 2 successfully ','false',1),(49,1,'Admin','admin@meeras.com',1,'ManageRoleController.updatePageRightsByRoleId','2019-11-28 14:44:54','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com page rights updated successfully ','false',1),(50,1,'Admin','admin@meeras.com',1,'ManageRoleController.getPageRightsListByRoleId','2019-11-28 14:44:57','0:0:0:0:0:0:0:1','Chrome','78.0.3904.108','Windows 10','Computer','F92BA28C38DDDB0A9E2E266754465EB5',1,'User admin@meeras.com retrived menu list against role id 2 successfully ','false',1);

/*Table structure for table `address` */

DROP TABLE IF EXISTS `address`;

CREATE TABLE `address` (
  `ADDRESS_ID` int(11) NOT NULL auto_increment,
  `ADDRESS_TYPE` varchar(100) NOT NULL,
  `CONTACT_NAME` varchar(256) default NULL,
  `FIRST_NAME` varchar(256) default NULL,
  `LAST_NAME` varchar(256) default NULL,
  `EMAIL` varchar(256) default NULL,
  `PHONE` varchar(256) default NULL,
  `FAX` varchar(45) default NULL,
  `WEBSITE` varchar(256) default NULL,
  `TWITTER` varchar(256) default NULL,
  `STREET` varchar(256) default NULL,
  `SUBURB` varchar(256) default NULL,
  `CITY` varchar(256) default NULL,
  `POSTAL_CODE` varchar(256) default NULL,
  `STATE` varchar(256) default NULL,
  `COUNTY` varchar(256) default NULL,
  `X_COORDINATE` varchar(256) default '0',
  `Y_COORDINATE` varchar(256) default '0',
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `CONTACT_ASSOCICATION_ID` int(11) default NULL,
  `COUNTRY_ASSOCICATION_ID` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`ADDRESS_ID`),
  KEY `ADDRESS_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `ADDRESS_COUNTRY_ASSOCICATION_ID_FK` (`COUNTRY_ASSOCICATION_ID`),
  KEY `ADDRESS_CONTACT_ASSOCICATION_ID_FK` (`CONTACT_ASSOCICATION_ID`),
  CONSTRAINT `ADDRESS_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `ADDRESS_CONTACT_ASSOCICATION_ID_FK` FOREIGN KEY (`CONTACT_ASSOCICATION_ID`) REFERENCES `contact` (`CONTACT_ID`),
  CONSTRAINT `ADDRESS_COUNTRY_ASSOCICATION_ID_FK` FOREIGN KEY (`COUNTRY_ASSOCICATION_ID`) REFERENCES `country` (`COUNTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `address` */

insert  into `address`(`ADDRESS_ID`,`ADDRESS_TYPE`,`CONTACT_NAME`,`FIRST_NAME`,`LAST_NAME`,`EMAIL`,`PHONE`,`FAX`,`WEBSITE`,`TWITTER`,`STREET`,`SUBURB`,`CITY`,`POSTAL_CODE`,`STATE`,`COUNTY`,`X_COORDINATE`,`Y_COORDINATE`,`ACTIVE_INDICATOR`,`CREATED_DATE`,`LAST_UPDATED`,`CREATED_BY`,`UPDATED_BY`,`CONTACT_ASSOCICATION_ID`,`COUNTRY_ASSOCICATION_ID`,`COMPANY_ASSOCIATION_ID`) values (1,'Physical Address','Admin','','','adminInfo@shopvitals.com','','23424','www.shopvitals.com','twiter','','Suburb','Lahore','234234','Punjab','Pakistan','0','0','','2019-11-28 14:36:18','2019-11-28 14:36:18',1,1,NULL,1,1),(2,'OUTLET',NULL,NULL,NULL,'customercare@meeras.pk','0340-4888-999',NULL,NULL,NULL,'LG-2 Jinnah Plaza Near J. Opp. Focus Link-Road Model Town',NULL,'Lahore','54000','Punjab',NULL,NULL,NULL,'','2019-11-28 14:41:08','2019-11-28 14:41:08',1,1,NULL,1,1);

/*Table structure for table `announcement` */

DROP TABLE IF EXISTS `announcement`;

CREATE TABLE `announcement` (
  `ANNOUNCEMENT_ID` int(11) NOT NULL auto_increment,
  `DESCRIPTION` varchar(256) NOT NULL,
  `ANNOUNCEMENT_DETAIL` blob NOT NULL,
  `ANNOUNCEMENT_DATE` date NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`ANNOUNCEMENT_ID`),
  KEY `ANNOUNCEMENT_CREATED_BY_FK` (`CREATED_BY`),
  KEY `ANNOUNCEMENT_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `ANNOUNCEMENT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  CONSTRAINT `ANNOUNCEMENT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `ANNOUNCEMENT_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `ANNOUNCEMENT_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `announcement` */

/*Table structure for table `bank_account` */

DROP TABLE IF EXISTS `bank_account`;

CREATE TABLE `bank_account` (
  `BANK_ACCOUNT_ID` int(11) NOT NULL auto_increment,
  `BANK_NAME` varchar(256) NOT NULL,
  `ACCOUNT_NAME` varchar(256) NOT NULL,
  `ACCOUNT_TYPE` varchar(256) NOT NULL,
  `ACCOUNT_NUMBER` varchar(256) default NULL,
  `CREDIT_CARD_NUMBER` varchar(256) default NULL,
  `CURRENCY_ASSOCICATION_ID` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`BANK_ACCOUNT_ID`),
  KEY `BANK_ACCOUNT_CREATED_BY_FK` (`CREATED_BY`),
  KEY `BANK_ACCOUNT_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `BANK_ACCOUNT_CURRENCY_ASSOCICATION_ID_FK` (`CURRENCY_ASSOCICATION_ID`),
  KEY `BANK_ACCOUNT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  CONSTRAINT `BANK_ACCOUNT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `BANK_ACCOUNT_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `BANK_ACCOUNT_CURRENCY_ASSOCICATION_ID_FK` FOREIGN KEY (`CURRENCY_ASSOCICATION_ID`) REFERENCES `currency` (`CURRENCY_ID`),
  CONSTRAINT `BANK_ACCOUNT_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `bank_account` */

/*Table structure for table `brand` */

DROP TABLE IF EXISTS `brand`;

CREATE TABLE `brand` (
  `BRAND_ID` int(11) NOT NULL auto_increment,
  `BRAND_NAME` varchar(200) NOT NULL,
  `BRAND_DESCRIPTION` varchar(500) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`BRAND_ID`),
  KEY `BRAND_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  CONSTRAINT `BRAND_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `brand` */

insert  into `brand`(`BRAND_ID`,`BRAND_NAME`,`BRAND_DESCRIPTION`,`ACTIVE_INDICATOR`,`CREATED_DATE`,`LAST_UPDATED`,`CREATED_BY`,`UPDATED_BY`,`COMPANY_ASSOCIATION_ID`) values (1,'Meeras','Meeras','','2019-11-28 14:36:18','2019-11-28 14:36:18',1,1,1);

/*Table structure for table `cash_managment` */

DROP TABLE IF EXISTS `cash_managment`;

CREATE TABLE `cash_managment` (
  `CASH_MANAGMENT_ID` int(11) NOT NULL auto_increment,
  `CASH_AMT` decimal(20,2) default NULL,
  `CASH_MANAGMENT_NOTES` varchar(256) default NULL,
  `CASH_MANAGMENT_TYPE` varchar(10) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `DAILY_REGISTER_ASSOCICATION_ID` int(11) NOT NULL,
  `EXPENSE_TYPE_ASSOCIATION_ID` int(11) NOT NULL default '1',
  PRIMARY KEY  (`CASH_MANAGMENT_ID`),
  KEY `CASH_MANAGMENT_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `CASH_MANAGMENT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `CASH_MANAGMENT_DAILY_REGISTER_ASSOCICATION_ID_FK` (`DAILY_REGISTER_ASSOCICATION_ID`),
  KEY `EXPENSE_TYPE_ASSOCIATION_ID_FK` (`EXPENSE_TYPE_ASSOCIATION_ID`),
  CONSTRAINT `CASH_MANAGMENT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `CASH_MANAGMENT_DAILY_REGISTER_ASSOCICATION_ID_FK` FOREIGN KEY (`DAILY_REGISTER_ASSOCICATION_ID`) REFERENCES `daily_register` (`DAILY_REGISTER_ID`),
  CONSTRAINT `CASH_MANAGMENT_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  CONSTRAINT `EXPENSE_TYPE_ASSOCIATION_ID_FK` FOREIGN KEY (`EXPENSE_TYPE_ASSOCIATION_ID`) REFERENCES `expense_type` (`EXPENSE_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `cash_managment` */

/*Table structure for table `chart_of_account` */

DROP TABLE IF EXISTS `chart_of_account`;

CREATE TABLE `chart_of_account` (
  `CHART_OF_ACCOUNT_ID` int(11) NOT NULL auto_increment,
  `CHART_OF_ACCOUNT_CODE` varchar(256) NOT NULL,
  `CHART_OF_ACCOUNT_NAME` varchar(256) NOT NULL,
  `ACCOUNT_TYPE_ASSOCIATION_ID` int(11) NOT NULL,
  `SALES_TAX_ASSOCIATION_ID` int(11) NOT NULL,
  `DESCRIPTION` varchar(256) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`CHART_OF_ACCOUNT_ID`),
  KEY `CHART_OF_ACCOUNT_CREATED_BY_FK` (`CREATED_BY`),
  KEY `CHART_OF_ACCOUNT_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `CHART_OF_ACCOUNT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `CHART_OF_ACCOUNT_ACCOUNT_TYPE_ASSOCIATION_ID_FK` (`ACCOUNT_TYPE_ASSOCIATION_ID`),
  KEY `CHART_OF_ACCOUNT_SALES_TAX_ASSOCIATION_ID_FK` (`SALES_TAX_ASSOCIATION_ID`),
  CONSTRAINT `CHART_OF_ACCOUNT_ACCOUNT_TYPE_ASSOCIATION_ID_FK` FOREIGN KEY (`ACCOUNT_TYPE_ASSOCIATION_ID`) REFERENCES `account_type` (`ACCOUNT_TYPE_ID`),
  CONSTRAINT `CHART_OF_ACCOUNT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `CHART_OF_ACCOUNT_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `CHART_OF_ACCOUNT_SALES_TAX_ASSOCIATION_ID_FK` FOREIGN KEY (`SALES_TAX_ASSOCIATION_ID`) REFERENCES `sales_tax` (`SALES_TAX_ID`),
  CONSTRAINT `CHART_OF_ACCOUNT_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `chart_of_account` */

/*Table structure for table `company` */

DROP TABLE IF EXISTS `company`;

CREATE TABLE `company` (
  `COMPANY_ID` int(11) NOT NULL auto_increment,
  `COMPANY_NAME` varchar(256) NOT NULL,
  `DISPLAY_PRICES` varchar(256) default NULL,
  `SKU_GENERATION` varchar(256) default NULL,
  `CURRENT_SEQUENCE_NUMBER` varchar(256) default NULL,
  `LOYALTY_ENABLED` bit(1) default NULL,
  `LOYALTY_INVOICE_AMOUNT` decimal(20,2) default NULL,
  `LOYALTY_AMOUNT` decimal(20,2) default NULL,
  `LOYALTY_ENABLED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LOYALTY_BONUS_AMOUNT` decimal(20,2) default NULL,
  `LOYALTY_BONUS_ENABLED` bit(1) default NULL,
  `LOYALTY_BONUS_ENABLED_DATE` timestamp NOT NULL default '0000-00-00 00:00:00',
  `USER_SWITCH_SECURITY` varchar(256) default NULL,
  `ENABLE_STORES_CREDIT` varchar(256) NOT NULL,
  `URL` varchar(100) default NULL,
  `ACTIVE_INDICATOR` bit(1) default '',
  `CREATED_DATE` timestamp NOT NULL default '0000-00-00 00:00:00',
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `PRINTER_ASSOCICATION_ID` int(11) default NULL,
  `CURRENCY_ASSOCICATION_ID` int(11) default NULL,
  `TIME_ZONE_ASSOCICATION_ID` int(11) default NULL,
  PRIMARY KEY  (`COMPANY_ID`),
  KEY `COMPANY_CURRENCY_ASSOCICATION_ID_FK` (`CURRENCY_ASSOCICATION_ID`),
  KEY `COMPANY_PRINTER_ASSOCICATION_ID_FK` (`PRINTER_ASSOCICATION_ID`),
  KEY `COMPANY_TIME_ZONE_ASSOCICATION_ID_FK` (`TIME_ZONE_ASSOCICATION_ID`),
  CONSTRAINT `COMPANY_CURRENCY_ASSOCICATION_ID_FK` FOREIGN KEY (`CURRENCY_ASSOCICATION_ID`) REFERENCES `currency` (`CURRENCY_ID`),
  CONSTRAINT `COMPANY_PRINTER_ASSOCICATION_ID_FK` FOREIGN KEY (`PRINTER_ASSOCICATION_ID`) REFERENCES `printer_format` (`PRINTER_FORMAT_ID`),
  CONSTRAINT `COMPANY_TIME_ZONE_ASSOCICATION_ID_FK` FOREIGN KEY (`TIME_ZONE_ASSOCICATION_ID`) REFERENCES `time_zone` (`TIME_ZONE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `company` */

insert  into `company`(`COMPANY_ID`,`COMPANY_NAME`,`DISPLAY_PRICES`,`SKU_GENERATION`,`CURRENT_SEQUENCE_NUMBER`,`LOYALTY_ENABLED`,`LOYALTY_INVOICE_AMOUNT`,`LOYALTY_AMOUNT`,`LOYALTY_ENABLED_DATE`,`LOYALTY_BONUS_AMOUNT`,`LOYALTY_BONUS_ENABLED`,`LOYALTY_BONUS_ENABLED_DATE`,`USER_SWITCH_SECURITY`,`ENABLE_STORES_CREDIT`,`URL`,`ACTIVE_INDICATOR`,`CREATED_DATE`,`LAST_UPDATED`,`CREATED_BY`,`UPDATED_BY`,`PRINTER_ASSOCICATION_ID`,`CURRENCY_ASSOCICATION_ID`,`TIME_ZONE_ASSOCICATION_ID`) values (1,'Meeras','Tax Inclusive','Generate By Name','10000','\0','5000.00','50.00','2019-11-28 14:36:18','0.00','','2019-11-28 14:36:18','Never require a password when switching between users','false',NULL,'','2019-11-28 14:36:18','2019-11-28 14:36:18',1,1,1,1,1);

/*Table structure for table `composite_product` */

DROP TABLE IF EXISTS `composite_product`;

CREATE TABLE `composite_product` (
  `COMPOSITE_PRODUCT_ID` int(11) NOT NULL auto_increment,
  `COMPOSITE_PRODUCT_UUID` varchar(500) NOT NULL,
  `PRODUCT_UUID` varchar(500) NOT NULL,
  `UNIT_QUANTITY` int(11) NOT NULL,
  `COMPOSITE_QUANTITY` int(11) NOT NULL,
  `PRODUCT_ASSOCICATION_ID` int(11) NOT NULL,
  `SELECTIVE_PRODUCT_ASSOCIATION_ID` int(11) NOT NULL,
  `PRODUCT_VARIANT_ASSOCICATION_ID` int(11) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`COMPOSITE_PRODUCT_ID`),
  KEY `COMPOSITE_PRODUCT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `COMPOSITE_PRODUCT_CREATED_BY_FK` (`CREATED_BY`),
  KEY `COMPOSITE_PRODUCT_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `COMPOSITE_PRODUCT_PRODUCT_ASSOCICATION_ID_FK` (`PRODUCT_ASSOCICATION_ID`),
  KEY `COMPOSITE_PRODUCT_SELECTIVE_PRODUCT_ASSOCIATION_ID_FK` (`SELECTIVE_PRODUCT_ASSOCIATION_ID`),
  KEY `COMPOSITE_PRODUCT_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `COMPOSITE_PRODUCT_PRODUCT_VARIANT_ASSOCICATION_ID_FK` (`PRODUCT_VARIANT_ASSOCICATION_ID`),
  CONSTRAINT `COMPOSITE_PRODUCT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `COMPOSITE_PRODUCT_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `COMPOSITE_PRODUCT_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  CONSTRAINT `COMPOSITE_PRODUCT_PRODUCT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCICATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  CONSTRAINT `COMPOSITE_PRODUCT_PRODUCT_VARIANT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_VARIANT_ASSOCICATION_ID`) REFERENCES `product_variant` (`PRODUCT_VARIANT_ID`),
  CONSTRAINT `COMPOSITE_PRODUCT_SELECTIVE_PRODUCT_ASSOCIATION_ID_FK` FOREIGN KEY (`SELECTIVE_PRODUCT_ASSOCIATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  CONSTRAINT `COMPOSITE_PRODUCT_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `composite_product` */

/*Table structure for table `composite_product_history` */

DROP TABLE IF EXISTS `composite_product_history`;

CREATE TABLE `composite_product_history` (
  `COMPOSITE_PRODUCT_HISTORY_ID` int(11) NOT NULL auto_increment,
  `COMPOSITE_PRODUCT_ID` int(11) NOT NULL,
  `COMPOSITE_PRODUCT_UUID` varchar(500) NOT NULL,
  `PRODUCT_UUID` varchar(500) NOT NULL,
  `UNIT_QUANTITY` int(11) NOT NULL,
  `COMPOSITE_QUANTITY` int(11) NOT NULL,
  `PRODUCT_ASSOCICATION_ID` int(11) NOT NULL,
  `SELECTIVE_PRODUCT_ASSOCIATION_ID` int(11) NOT NULL,
  `PRODUCT_VARIANT_ASSOCICATION_ID` int(11) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTION_TYPE` varchar(100) NOT NULL,
  PRIMARY KEY  (`COMPOSITE_PRODUCT_HISTORY_ID`),
  KEY `COMPOSITE_PRODUCT_HISTORY_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `COMPOSITE_PRODUCT_HISTORY_CREATED_BY_FK` (`CREATED_BY`),
  KEY `COMPOSITE_PRODUCT_HISTORY_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `COMPOSITE_PRODUCT_HISTORY_PRODUCT_ASSOCICATION_ID_FK` (`PRODUCT_ASSOCICATION_ID`),
  KEY `COMPOSITE_PRODUCT_HISTORY_SELECTIVE_PRODUCT_ASSOCIATION_ID_FK` (`SELECTIVE_PRODUCT_ASSOCIATION_ID`),
  KEY `COMPOSITE_PRODUCT_HISTORY_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `COMPOSITE_PRODUCT_HISTORY_PRODUCT_VARIANT_ASSOCICATION_ID_FK` (`PRODUCT_VARIANT_ASSOCICATION_ID`),
  CONSTRAINT `COMPOSITE_PRODUCT_HISTORY_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `COMPOSITE_PRODUCT_HISTORY_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `COMPOSITE_PRODUCT_HISTORY_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  CONSTRAINT `COMPOSITE_PRODUCT_HISTORY_PRODUCT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCICATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  CONSTRAINT `COMPOSITE_PRODUCT_HISTORY_PRODUCT_VARIANT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_VARIANT_ASSOCICATION_ID`) REFERENCES `product_variant` (`PRODUCT_VARIANT_ID`),
  CONSTRAINT `COMPOSITE_PRODUCT_HISTORY_SELECTIVE_PRODUCT_ASSOCIATION_ID_FK` FOREIGN KEY (`SELECTIVE_PRODUCT_ASSOCIATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  CONSTRAINT `COMPOSITE_PRODUCT_HISTORY_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `composite_product_history` */

/*Table structure for table `configuration` */

DROP TABLE IF EXISTS `configuration`;

CREATE TABLE `configuration` (
  `CONFIGURATION_ID` int(11) NOT NULL auto_increment,
  `PROPERTY_NAME` varchar(256) NOT NULL,
  `PROPERTY_VALUE` varchar(1000) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`CONFIGURATION_ID`),
  KEY `CONFIGURATION_CREATED_BY_FK` (`CREATED_BY`),
  KEY `CONFIGURATION_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `CONFIGURATION_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  CONSTRAINT `CONFIGURATION_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `CONFIGURATION_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `CONFIGURATION_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `configuration` */

insert  into `configuration`(`CONFIGURATION_ID`,`PROPERTY_NAME`,`PROPERTY_VALUE`,`COMPANY_ASSOCIATION_ID`,`ACTIVE_INDICATOR`,`CREATED_BY`,`UPDATED_BY`,`CREATED_DATE`,`LAST_UPDATED`) values (1,'PRODCUT_TEMPLATE_FOR_ALL_OUTLETS','false',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(2,'RETAIL_PRICE_BILL','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(3,'TRANSFER_STOCK_OUTLET_ACCESS','false',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(4,'AUTO_STOCK_ORDER_NEW_PRODUCT','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(5,'AUTO_STOCK_RETURN_MANAGE_PRODUCT','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(6,'RETURN_SELL_SCREEN','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(7,'HIDE_ORIGNAL_PRICE_INFO_REPORTS','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(8,'DOCUMENTS_PATH','C:/images',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(9,'SALE_SMS','Thanks for shopping on our brand!',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(10,'SMS_ENABLED','false',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(11,'SHOW_PRODCUT_TAG','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(12,'TERMS_AND_CONDITIONS','<div>\nThank You For Your Shopping.<br> No Return & Non Refundable.<br>\nNo Exchange Of Style <br>\nNo Exchange Of Size In Case Of Any Damage Or Worn <br>\nExchange Of Size Within 15days Of Purchase Along With Receipt And Tag(Based On Availability) <br>\n</div>',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(13,'COMPANY_RECEIPT_IMAGE','brand_meeras.jpg',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(14,'SUB_DOMAIN_NAME','localhost',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(15,'STOCK_ORDER_COMPL_TABLE','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(16,'LOCAL_INSTANCE','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(17,'WAREHOSE_ADMIN_RESTRICTION','false',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(18,'PRODUCT_MARKUP','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(19,'PRODUCT_PRODUCT_SKU','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(20,'PRODUCT_PRODUCT_DESC','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(21,'PRODUCT_PURCHASE_ACCOUNT_CODE','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(22,'PRODUCT_REORDER_AMOUNT','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(23,'PRODUCT_REORDER_POINT','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(24,'PRODUCT_RETAIL_PRICE_EXCLUSIVE_TAX','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(25,'PRODUCT_SALES_ACCOUNT_CODE','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(26,'PRODUCT_PRODUCT_CODE','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(27,'SUPPLIER_ID','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(28,'PRODUCT_SUPPLY_PRICE_EXCLUSIVE_TAX','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(29,'PRODUCT_TRACKING_INVENTORY','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(30,'PRODUCT_VARIANT_PRODUCTS','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(31,'PRODUCT_SHOW_PRODCUT_TAG','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(32,'PRODUCT_PRODUCT_IMAGE','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20'),(33,'PRODUCT_CURRENT_INVENTORY','true',1,'',1,1,'2019-11-28 14:37:20','2019-11-28 14:37:20');

/*Table structure for table `contact` */

DROP TABLE IF EXISTS `contact`;

CREATE TABLE `contact` (
  `CONTACT_ID` int(11) NOT NULL auto_increment,
  `CONTACT_NAME` varchar(200) NOT NULL,
  `DOB` date default NULL,
  `GENDER` varchar(10) default NULL,
  `CONTACT_CODE` varchar(200) default NULL,
  `FIRST_NAME` varchar(200) default NULL,
  `LAST_NAME` varchar(200) default NULL,
  `DEFAULT_MARKUP` decimal(5,2) default NULL,
  `CONTACT_BALANCE` decimal(20,5) default NULL,
  `DESCRIPTION` varchar(1000) default NULL,
  `LOYALTY_ENABLED` varchar(10) default NULL,
  `COMPANY_NAME` varchar(100) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CONTACT_OUTLET_ID` int(11) default NULL,
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `CONTACT_GROUP_ASSOCIATION_ID` int(11) default NULL,
  `CONTACT_TYPE` varchar(250) NOT NULL default 'SUPPLIER',
  `OUTLET_ASSOCIATION_ID` int(11) NOT NULL default '1',
  PRIMARY KEY  (`CONTACT_ID`),
  KEY `CONTACT_CONTACT_GROUP_ASSOCIATION_ID_FK` (`CONTACT_GROUP_ASSOCIATION_ID`),
  KEY `CONTACT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  CONSTRAINT `CONTACT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `CONTACT_CONTACT_GROUP_ASSOCIATION_ID_FK` FOREIGN KEY (`CONTACT_GROUP_ASSOCIATION_ID`) REFERENCES `contact_group` (`CONTACT_GROUP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `contact` */

insert  into `contact`(`CONTACT_ID`,`CONTACT_NAME`,`DOB`,`GENDER`,`CONTACT_CODE`,`FIRST_NAME`,`LAST_NAME`,`DEFAULT_MARKUP`,`CONTACT_BALANCE`,`DESCRIPTION`,`LOYALTY_ENABLED`,`COMPANY_NAME`,`ACTIVE_INDICATOR`,`CREATED_DATE`,`LAST_UPDATED`,`CONTACT_OUTLET_ID`,`CREATED_BY`,`UPDATED_BY`,`COMPANY_ASSOCIATION_ID`,`CONTACT_GROUP_ASSOCIATION_ID`,`CONTACT_TYPE`,`OUTLET_ASSOCIATION_ID`) values (1,'Main Outlet',NULL,NULL,NULL,NULL,NULL,NULL,'0.00000',NULL,NULL,NULL,'','2019-11-28 14:36:18','2019-11-28 14:36:18',1,1,NULL,1,1,'SUPPLIER',1);

/*Table structure for table `contact_group` */

DROP TABLE IF EXISTS `contact_group`;

CREATE TABLE `contact_group` (
  `CONTACT_GROUP_ID` int(11) NOT NULL auto_increment,
  `CONTACT_GROUP_NAME` varchar(200) NOT NULL,
  `COUNTRY_ASSOCICATION_ID` int(11) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`CONTACT_GROUP_ID`),
  KEY `CONTACT_GROUP_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `CONTACT_GROUP_COUNTRY_ASSOCICATION_ID_FK` (`COUNTRY_ASSOCICATION_ID`),
  CONSTRAINT `CONTACT_GROUP_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `CONTACT_GROUP_COUNTRY_ASSOCICATION_ID_FK` FOREIGN KEY (`COUNTRY_ASSOCICATION_ID`) REFERENCES `country` (`COUNTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `contact_group` */

insert  into `contact_group`(`CONTACT_GROUP_ID`,`CONTACT_GROUP_NAME`,`COUNTRY_ASSOCICATION_ID`,`ACTIVE_INDICATOR`,`CREATED_DATE`,`LAST_UPDATED`,`CREATED_BY`,`UPDATED_BY`,`COMPANY_ASSOCIATION_ID`) values (1,'All Contact',1,'','2019-11-28 14:36:18','2019-11-28 14:36:18',1,1,1);

/*Table structure for table `contact_payments` */

DROP TABLE IF EXISTS `contact_payments`;

CREATE TABLE `contact_payments` (
  `CONTACT_PAYMENT_ID` int(11) NOT NULL auto_increment,
  `CONTACT_NAME` varchar(200) NOT NULL,
  `PAYMENT_AMOUNT` decimal(20,5) default NULL,
  `CONTACT_BALANCE` decimal(20,5) default NULL,
  `PAYMENT_CASH` decimal(20,5) default NULL,
  `PAYMENT_CREDIT_CARD` decimal(20,5) default NULL,
  `PAYMENT_OTHER_TYPE` decimal(20,5) default NULL,
  `CONTACT_NEW_BALANCE` decimal(20,5) default NULL,
  `DESCRIPTION` varchar(1000) default NULL,
  `PAYMENT_REF_NUM` varchar(100) default NULL,
  `ORDER_REF_NUM` varchar(100) default NULL,
  `ADJUSTMENT_TYPE` varchar(100) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `CONTACT_ASSOCICATION_ID` int(11) default NULL,
  `CONTACT_PAYMENT_TYPE_ASSOCICATION_ID` int(11) default NULL,
  PRIMARY KEY  (`CONTACT_PAYMENT_ID`),
  KEY `CONTACT_PAYMENT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `CONTACT_PAYMENT_CONTACT_ASSOCICATION_ID_FK` (`CONTACT_ASSOCICATION_ID`),
  KEY `CONTACT_PAYMENT_TYPE_ASSOCICATION_ID_FK` (`CONTACT_PAYMENT_TYPE_ASSOCICATION_ID`),
  CONSTRAINT `CONTACT_PAYMENT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `CONTACT_PAYMENT_CONTACT_ASSOCICATION_ID_FK` FOREIGN KEY (`CONTACT_ASSOCICATION_ID`) REFERENCES `contact` (`CONTACT_ID`),
  CONSTRAINT `CONTACT_PAYMENT_TYPE_ASSOCICATION_ID_FK` FOREIGN KEY (`CONTACT_PAYMENT_TYPE_ASSOCICATION_ID`) REFERENCES `contact_payments_type` (`CONTACT_PAYMENT_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `contact_payments` */

/*Table structure for table `contact_payments_type` */

DROP TABLE IF EXISTS `contact_payments_type`;

CREATE TABLE `contact_payments_type` (
  `CONTACT_PAYMENT_TYPE_ID` int(11) NOT NULL auto_increment,
  `CONTACT_PAYMENT_TYPE_NAME` varchar(200) NOT NULL,
  PRIMARY KEY  (`CONTACT_PAYMENT_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `contact_payments_type` */

insert  into `contact_payments_type`(`CONTACT_PAYMENT_TYPE_ID`,`CONTACT_PAYMENT_TYPE_NAME`) values (1,'CUSTOMER'),(2,'SUPPLIER'),(3,'EMPLOYEE'),(4,'OTHERS'),(5,'OUTLET');

/*Table structure for table `contact_us` */

DROP TABLE IF EXISTS `contact_us`;

CREATE TABLE `contact_us` (
  `CONTACT_US_ID` int(11) NOT NULL auto_increment,
  `NAME` varchar(256) NOT NULL,
  `EMAIL` varchar(256) NOT NULL,
  `MESSAGE` varchar(256) default NULL,
  `CONTACT_NUMBER` varchar(256) default NULL,
  `REQUEST_STATUS` varchar(256) NOT NULL,
  `WEB_ACTIVITY_DETAIL_ID` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`CONTACT_US_ID`),
  KEY `CONTACT_US_WEB_ACTIVITY_DETAIL_ID_FK` (`WEB_ACTIVITY_DETAIL_ID`),
  CONSTRAINT `CONTACT_US_WEB_ACTIVITY_DETAIL_ID_FK` FOREIGN KEY (`WEB_ACTIVITY_DETAIL_ID`) REFERENCES `web_activity_detail` (`WEB_ACTIVITY_DETAIL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `contact_us` */

/*Table structure for table `country` */

DROP TABLE IF EXISTS `country`;

CREATE TABLE `country` (
  `COUNTRY_ID` int(11) NOT NULL auto_increment,
  `COUNTRY_NAME` varchar(200) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  PRIMARY KEY  (`COUNTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `country` */

insert  into `country`(`COUNTRY_ID`,`COUNTRY_NAME`,`ACTIVE_INDICATOR`,`CREATED_DATE`,`LAST_UPDATED`,`CREATED_BY`,`UPDATED_BY`) values (1,'Pakistan','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1);

/*Table structure for table `currency` */

DROP TABLE IF EXISTS `currency`;

CREATE TABLE `currency` (
  `CURRENCY_ID` int(11) NOT NULL auto_increment,
  `CURRENCY_NAME` varchar(256) NOT NULL,
  `CURRENCY_VALUE` varchar(256) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  PRIMARY KEY  (`CURRENCY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `currency` */

insert  into `currency`(`CURRENCY_ID`,`CURRENCY_NAME`,`CURRENCY_VALUE`,`ACTIVE_INDICATOR`,`CREATED_DATE`,`LAST_UPDATED`,`CREATED_BY`,`UPDATED_BY`) values (1,'Rupees','PKR','','2019-11-28 14:35:55','2019-11-28 14:35:55',1,1),(2,'Dollar','USD','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1);

/*Table structure for table `daily_register` */

DROP TABLE IF EXISTS `daily_register`;

CREATE TABLE `daily_register` (
  `DAILY_REGISTER_ID` int(11) NOT NULL auto_increment,
  `CREDIT_CARD_AMT_ACTUAL` decimal(20,2) default NULL,
  `CREDIT_CARD_AMT_EXPECTED` decimal(20,2) default NULL,
  `CREDIT_CARD_AMT_DIFFERENCE` decimal(20,2) default NULL,
  `CASH_AMT_ACTUAL` decimal(20,2) default NULL,
  `CASH_AMT_EXPECTED` decimal(20,2) default NULL,
  `CASH_AMT_DIFFERENCE` decimal(20,2) default NULL,
  `STORE_CREDIT_AMT_ACTUAL` decimal(20,2) default NULL,
  `STORE_CREDIT_AMT_EXPECTED` decimal(20,2) default NULL,
  `STORE_CREDIT_AMT_DIFFERENCE` decimal(20,2) default NULL,
  `ACTUAL_AMT` decimal(20,2) default NULL,
  `REGISTER_OPENING_NOTES` varchar(256) default NULL,
  `REGISTER_CLOSING_NOTES` varchar(256) default NULL,
  `REGISTER_ASSOCICATION_ID` int(11) NOT NULL,
  `STATUS_ASSOCICATION_ID` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `CLOSED_DATE` timestamp NOT NULL default '0000-00-00 00:00:00',
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  PRIMARY KEY  (`DAILY_REGISTER_ID`),
  KEY `DAILY_REGISTER_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `DAILY_REGISTER_STATUS_ASSOCICATION_ID_FK` (`STATUS_ASSOCICATION_ID`),
  KEY `DAILY_REGISTER_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `DAILY_REGISTER_REGISTER_ASSOCICATION_ID_FK` (`REGISTER_ASSOCICATION_ID`),
  CONSTRAINT `DAILY_REGISTER_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `DAILY_REGISTER_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  CONSTRAINT `DAILY_REGISTER_REGISTER_ASSOCICATION_ID_FK` FOREIGN KEY (`REGISTER_ASSOCICATION_ID`) REFERENCES `register` (`REGISTER_ID`),
  CONSTRAINT `DAILY_REGISTER_STATUS_ASSOCICATION_ID_FK` FOREIGN KEY (`STATUS_ASSOCICATION_ID`) REFERENCES `status` (`STATUS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `daily_register` */

/*Table structure for table `delivery_method` */

DROP TABLE IF EXISTS `delivery_method`;

CREATE TABLE `delivery_method` (
  `DELIVERY_METHOD_ID` int(11) NOT NULL auto_increment,
  `DELIVERY_METHOD_NAME` varchar(100) NOT NULL,
  `SHIPPING_RATE` decimal(5,2) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  PRIMARY KEY  (`DELIVERY_METHOD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `delivery_method` */

/*Table structure for table `expense_type` */

DROP TABLE IF EXISTS `expense_type`;

CREATE TABLE `expense_type` (
  `EXPENSE_TYPE_ID` int(11) NOT NULL auto_increment,
  `EXPENSE_TYPE_NAME` varchar(200) NOT NULL,
  `COUNTRY_ASSOCICATION_ID` int(11) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`EXPENSE_TYPE_ID`),
  KEY `EXPENSE_TYPE_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  CONSTRAINT `EXPENSE_TYPE_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `expense_type` */

insert  into `expense_type`(`EXPENSE_TYPE_ID`,`EXPENSE_TYPE_NAME`,`COUNTRY_ASSOCICATION_ID`,`ACTIVE_INDICATOR`,`CREATED_DATE`,`LAST_UPDATED`,`CREATED_BY`,`UPDATED_BY`,`COMPANY_ASSOCIATION_ID`) values (1,'DAILY EXPENSE',1,'','2019-11-28 14:36:18','2019-11-28 14:36:18',1,1,1),(2,'SALES RETURN',1,'','2019-11-28 14:36:18','2019-11-28 14:36:18',1,1,1),(3,'MONTHLY EXPENSE',1,'','2019-11-28 14:36:18','2019-11-28 14:36:18',1,1,1),(4,'UTILITY BILLS',1,'','2019-11-28 14:36:18','2019-11-28 14:36:18',1,1,1),(5,'MEAL EXPENSE',1,'','2019-11-28 14:36:18','2019-11-28 14:36:18',1,1,1),(6,'RENT',1,'','2019-11-28 14:36:18','2019-11-28 14:36:18',1,1,1),(7,'SALARY',1,'','2019-11-28 14:36:18','2019-11-28 14:36:18',1,1,1),(8,'PAYMENTS',1,'','2019-11-28 14:36:18','2019-11-28 14:36:18',1,1,1),(9,'LOANS',1,'','2019-11-28 14:36:18','2019-11-28 14:36:18',1,1,1);

/*Table structure for table `general_ledger` */

DROP TABLE IF EXISTS `general_ledger`;

CREATE TABLE `general_ledger` (
  `GENERAL_LEDGER_ID` int(11) NOT NULL auto_increment,
  `REFERENCE_ID` int(11) NOT NULL,
  `MODULE_ASSOCIATION_ID` int(11) NOT NULL,
  `GL_EVENT_ASSOCIATION_ID` int(11) NOT NULL,
  `GL_SUB_EVENT_ASSOCIATION_ID` int(11) NOT NULL,
  `CHART_OF_ACCOUNT_ASSOCIATION_ID` int(11) NOT NULL,
  `DEBIT_AMOUNT` decimal(21,5) default NULL,
  `CREDIT_AMOUNT` decimal(21,5) default NULL,
  `REMARKS` varchar(250) default NULL,
  `BALANCE_AMOUNT` decimal(21,5) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`GENERAL_LEDGER_ID`),
  KEY `GENERAL_LEDGER_CREATED_BY_FK` (`CREATED_BY`),
  KEY `GENERAL_LEDGER_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `GENERAL_LEDGER_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `GENERAL_LEDGER_MODULE_ASSOCIATION_ID_FK` (`MODULE_ASSOCIATION_ID`),
  KEY `GENERAL_LEDGER_GL_EVENT_ASSOCIATION_ID_FK` (`GL_EVENT_ASSOCIATION_ID`),
  KEY `GENERAL_LEDGER_GL_SUB_EVENT_ASSOCIATION_ID_FK` (`GL_SUB_EVENT_ASSOCIATION_ID`),
  KEY `GENERAL_LEDGER_CHART_OF_ACCOUNT_ASSOCIATION_ID_FK` (`CHART_OF_ACCOUNT_ASSOCIATION_ID`),
  CONSTRAINT `GENERAL_LEDGER_CHART_OF_ACCOUNT_ASSOCIATION_ID_FK` FOREIGN KEY (`CHART_OF_ACCOUNT_ASSOCIATION_ID`) REFERENCES `chart_of_account` (`CHART_OF_ACCOUNT_ID`),
  CONSTRAINT `GENERAL_LEDGER_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `GENERAL_LEDGER_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `GENERAL_LEDGER_GL_EVENT_ASSOCIATION_ID_FK` FOREIGN KEY (`GL_EVENT_ASSOCIATION_ID`) REFERENCES `general_ledger_event` (`GENERAL_LEDGER_EVENT_ID`),
  CONSTRAINT `GENERAL_LEDGER_GL_SUB_EVENT_ASSOCIATION_ID_FK` FOREIGN KEY (`GL_SUB_EVENT_ASSOCIATION_ID`) REFERENCES `general_ledger_event` (`GENERAL_LEDGER_EVENT_ID`),
  CONSTRAINT `GENERAL_LEDGER_MODULE_ASSOCIATION_ID_FK` FOREIGN KEY (`MODULE_ASSOCIATION_ID`) REFERENCES `module` (`MODULE_ID`),
  CONSTRAINT `GENERAL_LEDGER_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `general_ledger` */

/*Table structure for table `general_ledger_event` */

DROP TABLE IF EXISTS `general_ledger_event`;

CREATE TABLE `general_ledger_event` (
  `GENERAL_LEDGER_EVENT_ID` int(11) NOT NULL auto_increment,
  `GENERAL_LEDGER_EVENT_CODE` varchar(256) NOT NULL,
  `GENERAL_LEDGER_EVENT_NAME` varchar(256) NOT NULL,
  `GENERAL_LEDGER_MAIN_EVENT_INDICATOR` bit(1) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`GENERAL_LEDGER_EVENT_ID`),
  KEY `GENERAL_LEDGER_EVENT_CREATED_BY_FK` (`CREATED_BY`),
  KEY `GENERAL_LEDGER_EVENT_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `GENERAL_LEDGER_EVENT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  CONSTRAINT `GENERAL_LEDGER_EVENT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `GENERAL_LEDGER_EVENT_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `GENERAL_LEDGER_EVENT_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `general_ledger_event` */

/*Table structure for table `gl_trans_config` */

DROP TABLE IF EXISTS `gl_trans_config`;

CREATE TABLE `gl_trans_config` (
  `GL_TRANS_CONFIG_ID` int(11) NOT NULL auto_increment,
  `GL_EVENT_ASSOCIATION_ID` int(11) NOT NULL,
  `GL_SUB_EVENT_ASSOCIATION_ID` int(11) NOT NULL,
  `CHART_OF_ACCOUNT_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTION_TYPE` varchar(10) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`GL_TRANS_CONFIG_ID`),
  KEY `GL_TRANS_CONFIG_CREATED_BY_FK` (`CREATED_BY`),
  KEY `GL_TRANS_CONFIG_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `GL_TRANS_CONFIG_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `GL_TRANS_CONFIG_GL_EVENT_ASSOCIATION_ID_FK` (`GL_EVENT_ASSOCIATION_ID`),
  KEY `GL_TRANS_CONFIG_GL_SUB_EVENT_ASSOCIATION_ID_FK` (`GL_SUB_EVENT_ASSOCIATION_ID`),
  KEY `GL_TRANS_CONFIG_CHART_OF_ACCOUNT_ASSOCIATION_ID_FK` (`CHART_OF_ACCOUNT_ASSOCIATION_ID`),
  CONSTRAINT `GL_TRANS_CONFIG_CHART_OF_ACCOUNT_ASSOCIATION_ID_FK` FOREIGN KEY (`CHART_OF_ACCOUNT_ASSOCIATION_ID`) REFERENCES `chart_of_account` (`CHART_OF_ACCOUNT_ID`),
  CONSTRAINT `GL_TRANS_CONFIG_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `GL_TRANS_CONFIG_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `GL_TRANS_CONFIG_GL_EVENT_ASSOCIATION_ID_FK` FOREIGN KEY (`GL_EVENT_ASSOCIATION_ID`) REFERENCES `general_ledger_event` (`GENERAL_LEDGER_EVENT_ID`),
  CONSTRAINT `GL_TRANS_CONFIG_GL_SUB_EVENT_ASSOCIATION_ID_FK` FOREIGN KEY (`GL_SUB_EVENT_ASSOCIATION_ID`) REFERENCES `general_ledger_event` (`GENERAL_LEDGER_EVENT_ID`),
  CONSTRAINT `GL_TRANS_CONFIG_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `gl_trans_config` */

/*Table structure for table `inventory_count` */

DROP TABLE IF EXISTS `inventory_count`;

CREATE TABLE `inventory_count` (
  `INVENTORY_COUNT_ID` int(11) NOT NULL auto_increment,
  `INVENTORY_COUNT_REF_NO` varchar(45) default NULL COMMENT 'INVENTORY COUNT REF NUMBER',
  `STATUS_ASSOCICATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) NOT NULL,
  `EXPECTED_PROD_QTY` int(11) default NULL,
  `COUNTED_PROD_QTY` int(11) default NULL,
  `COUNT_DIFF` int(11) default NULL,
  `SUPPLY_PRICE_EXP` decimal(20,2) default NULL,
  `RETAIL_PRICE_EXP` decimal(20,2) default NULL,
  `PRICE_DIFF` decimal(20,2) default NULL,
  `SUPPLY_PRICE_COUNTED` decimal(20,2) default NULL,
  `RETAIL_PRICE_COUNTED` decimal(20,2) default NULL,
  `REMARKS` varchar(500) default NULL,
  `INVENTORY_COUNT_TYPE_ASSOCICATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`INVENTORY_COUNT_ID`),
  KEY `INVENTORY_COUNT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `INVENTORY_COUNT_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `INVENTORY_COUNT_STATUS_ASSOCICATION_ID_FK` (`STATUS_ASSOCICATION_ID`),
  KEY `INVENTORY_COUNT_INVENTORY_COUNT_TYPE_ASSOCICATION_ID_FK` (`INVENTORY_COUNT_TYPE_ASSOCICATION_ID`),
  CONSTRAINT `INVENTORY_COUNT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `INVENTORY_COUNT_INVENTORY_COUNT_TYPE_ASSOCICATION_ID_FK` FOREIGN KEY (`INVENTORY_COUNT_TYPE_ASSOCICATION_ID`) REFERENCES `inventory_count_type` (`INVENTORY_COUNT_TYPE_ID`),
  CONSTRAINT `INVENTORY_COUNT_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  CONSTRAINT `INVENTORY_COUNT_STATUS_ASSOCICATION_ID_FK` FOREIGN KEY (`STATUS_ASSOCICATION_ID`) REFERENCES `status` (`STATUS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `inventory_count` */

/*Table structure for table `inventory_count_detail` */

DROP TABLE IF EXISTS `inventory_count_detail`;

CREATE TABLE `inventory_count_detail` (
  `INVENTORY_COUNT_DETAIL_ID` int(11) NOT NULL auto_increment,
  `INVENTORY_COUNT_ASSOCICATION_ID` int(11) NOT NULL,
  `PRODUCT_VARIANT_ASSOCICATION_ID` int(11) default NULL,
  `PRODUCT_ASSOCIATION_ID` int(11) default NULL,
  `IS_PRODUCT` bit(1) NOT NULL,
  `EXPECTED_PROD_QTY` int(11) default NULL,
  `SUPPLY_PRICE_EXP` decimal(20,2) default NULL,
  `RETAIL_PRICE_EXP` decimal(20,2) default NULL,
  `COUNTED_PROD_QTY` int(11) default NULL,
  `SUPPLY_PRICE_COUNTED` decimal(20,2) default NULL,
  `RETAIL_PRICE_COUNTED` decimal(20,2) default NULL,
  `COUNT_DIFF` int(11) default NULL,
  `PRICE_DIFF` decimal(20,2) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `audit_transfer` tinyint(4) default '0',
  PRIMARY KEY  (`INVENTORY_COUNT_DETAIL_ID`),
  KEY `INVENTORY_COUNT_DETAIL_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `INVENTORY_COUNT_DETAIL_INVENTORY_COUNT_ASSOCICATION_ID_FK` (`INVENTORY_COUNT_ASSOCICATION_ID`),
  KEY `INVENTORY_COUNT_DETAIL_PRODUCT_VARIANT_ASSOCICATION_ID_FK` (`PRODUCT_VARIANT_ASSOCICATION_ID`),
  KEY `INVENTORY_COUNT_DETAIL_PRODUCT_ASSOCIATION_ID_FK` (`PRODUCT_ASSOCIATION_ID`),
  CONSTRAINT `INVENTORY_COUNT_DETAIL_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `INVENTORY_COUNT_DETAIL_INVENTORY_COUNT_ASSOCICATION_ID_FK` FOREIGN KEY (`INVENTORY_COUNT_ASSOCICATION_ID`) REFERENCES `inventory_count` (`INVENTORY_COUNT_ID`),
  CONSTRAINT `INVENTORY_COUNT_DETAIL_PRODUCT_ASSOCIATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCIATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  CONSTRAINT `INVENTORY_COUNT_DETAIL_PRODUCT_VARIANT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_VARIANT_ASSOCICATION_ID`) REFERENCES `product_variant` (`PRODUCT_VARIANT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `inventory_count_detail` */

/*Table structure for table `inventory_count_type` */

DROP TABLE IF EXISTS `inventory_count_type`;

CREATE TABLE `inventory_count_type` (
  `INVENTORY_COUNT_TYPE_ID` int(11) NOT NULL auto_increment,
  `INVENTORY_COUNT_TYPE_CODE` varchar(5) NOT NULL,
  `INVENTORY_COUNT_TYPE_DESC` varchar(45) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  PRIMARY KEY  (`INVENTORY_COUNT_TYPE_ID`),
  UNIQUE KEY `INVENTORY_COUNT_TYPE_INVENTORY_COUNT_TYPE_CODE_UK` (`INVENTORY_COUNT_TYPE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `inventory_count_type` */

/*Table structure for table `invoice_detail` */

DROP TABLE IF EXISTS `invoice_detail`;

CREATE TABLE `invoice_detail` (
  `INVOICE_DETAIL_ID` int(11) NOT NULL auto_increment,
  `PRODUCT_VARIENT_ASSOCIATION_ID` int(11) default NULL,
  `PRODUCT_ASSOCIATION_ID` int(11) default NULL,
  `ITEM_RETAIL_PRICE` decimal(20,2) default NULL,
  `ITEM_SALE_PRICE` decimal(20,2) default NULL,
  `ITEM_DISCOUNT_PRCT` decimal(20,2) default NULL,
  `ITEM_TAX_AMOUNT` decimal(20,2) default NULL,
  `ITEM_ORIGNAL_AMT` decimal(20,2) default NULL,
  `ITEM_NOTES` varchar(256) default NULL,
  `INVOICE_MAIN_ASSOCICATION_ID` int(11) default NULL,
  `PRODUCT_QUANTITY` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL,
  `ISRETURN` bit(1) NOT NULL default '\0',
  PRIMARY KEY  (`INVOICE_DETAIL_ID`),
  KEY `INVOICE_DETAIL_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `INVOICE_DETAIL_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `INVOICE_DETAIL_PRODUCT_VARIENT_ASSOCIATION_ID_FK` (`PRODUCT_VARIENT_ASSOCIATION_ID`),
  KEY `INVOICE_DETAIL_INVOICE_MAIN_ASSOCICATION_ID_FK` (`INVOICE_MAIN_ASSOCICATION_ID`),
  KEY `INVOICE_DETAIL_PRODUCT_ASSOCIATION_ID_FK` (`PRODUCT_ASSOCIATION_ID`),
  CONSTRAINT `INVOICE_DETAIL_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `INVOICE_DETAIL_INVOICE_MAIN_ASSOCICATION_ID_FK` FOREIGN KEY (`INVOICE_MAIN_ASSOCICATION_ID`) REFERENCES `invoice_main` (`INVOICE_MAIN_ID`),
  CONSTRAINT `INVOICE_DETAIL_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  CONSTRAINT `INVOICE_DETAIL_PRODUCT_ASSOCIATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCIATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  CONSTRAINT `INVOICE_DETAIL_PRODUCT_VARIENT_ASSOCIATION_ID_FK` FOREIGN KEY (`PRODUCT_VARIENT_ASSOCIATION_ID`) REFERENCES `product_variant` (`PRODUCT_VARIANT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `invoice_detail` */

/*Table structure for table `invoice_main` */

DROP TABLE IF EXISTS `invoice_main`;

CREATE TABLE `invoice_main` (
  `INVOICE_MAIN_ID` int(11) NOT NULL auto_increment,
  `INVOICE_REF_NBR` varchar(100) default NULL,
  `INVOICE_NOTES` varchar(256) default NULL,
  `INVOICE_DISCOUNT` decimal(20,2) default NULL,
  `INVOICE_TAX` decimal(20,2) default NULL,
  `INVC_TYPE_CDE` varchar(5) default NULL,
  `INVOICE_GENERATION_DTE` datetime default NULL,
  `INVOICE_CANCEL_DTE` datetime default NULL,
  `INVOICE_AMT` decimal(20,2) default NULL,
  `INVOICE_DISCOUNT_AMT` decimal(20,2) default NULL,
  `INVOICE_NET_AMT` decimal(20,2) default NULL,
  `INVOICE_GIVEN_AMT` decimal(20,2) default NULL,
  `INVOICE_ORIGNAL_AMT` decimal(20,2) default NULL,
  `SETTLED_AMT` decimal(20,2) default NULL,
  `STATUS_ASSOCICATION_ID` int(11) NOT NULL,
  `ORDER_ASSOCICATION_ID` int(11) default NULL,
  `CONTACT_ASSOCIATION_ID` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `DAILY_REGISTER_ASSOCICATION_ID` int(11) NOT NULL,
  `PAYMENT_TYPE_ASSOCICATION_ID` int(11) NOT NULL,
  `SALES_USER` int(11) default NULL,
  PRIMARY KEY  (`INVOICE_MAIN_ID`),
  KEY `INVOICE_MAIN_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `INVOICE_MAIN_CONTACT_ASSOCIATION_ID_FK` (`CONTACT_ASSOCIATION_ID`),
  KEY `INVOICE_MAIN_DAILY_REGISTER_ASSOCICATION_ID_FK` (`DAILY_REGISTER_ASSOCICATION_ID`),
  KEY `INVOICE_MAIN_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `INVOICE_MAIN_PAYMENT_TYPE_ASSOCICATION_ID_FK` (`PAYMENT_TYPE_ASSOCICATION_ID`),
  KEY `INVOICE_MAIN_STATUS_ASSOCICATION_ID_FK` (`STATUS_ASSOCICATION_ID`),
  KEY `INVOICE_MAIN_ORDER_ASSOCICATION_ID_FK` (`ORDER_ASSOCICATION_ID`),
  CONSTRAINT `INVOICE_MAIN_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `INVOICE_MAIN_CONTACT_ASSOCIATION_ID_FK` FOREIGN KEY (`CONTACT_ASSOCIATION_ID`) REFERENCES `contact` (`CONTACT_ID`),
  CONSTRAINT `INVOICE_MAIN_DAILY_REGISTER_ASSOCICATION_ID_FK` FOREIGN KEY (`DAILY_REGISTER_ASSOCICATION_ID`) REFERENCES `daily_register` (`DAILY_REGISTER_ID`),
  CONSTRAINT `INVOICE_MAIN_ORDER_ASSOCICATION_ID_FK` FOREIGN KEY (`ORDER_ASSOCICATION_ID`) REFERENCES `order_main` (`ORDER_MAIN_ID`),
  CONSTRAINT `INVOICE_MAIN_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  CONSTRAINT `INVOICE_MAIN_PAYMENT_TYPE_ASSOCICATION_ID_FK` FOREIGN KEY (`PAYMENT_TYPE_ASSOCICATION_ID`) REFERENCES `payment_type` (`PAYMENT_TYPE_ID`),
  CONSTRAINT `INVOICE_MAIN_STATUS_ASSOCICATION_ID_FK` FOREIGN KEY (`STATUS_ASSOCICATION_ID`) REFERENCES `status` (`STATUS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `invoice_main` */

/*Table structure for table `loyalty` */

DROP TABLE IF EXISTS `loyalty`;

CREATE TABLE `loyalty` (
  `LOYALTY_ID` int(11) NOT NULL auto_increment,
  `LOYALTY_AMOUNT` decimal(10,2) NOT NULL,
  `CONTACT_ASSOCIATION_ID` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  PRIMARY KEY  (`LOYALTY_ID`),
  KEY `LOYALTY_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `LOYALTY_CONTACT_ASSOCIATION_ID_FK` (`CONTACT_ASSOCIATION_ID`),
  KEY `LOYALTY_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  CONSTRAINT `LOYALTY_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `LOYALTY_CONTACT_ASSOCIATION_ID_FK` FOREIGN KEY (`CONTACT_ASSOCIATION_ID`) REFERENCES `contact` (`CONTACT_ID`),
  CONSTRAINT `LOYALTY_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `loyalty` */

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `MENU_ID` int(11) NOT NULL auto_increment,
  `MENU_NAME` varchar(256) NOT NULL,
  `ROLE_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTION_TYPE` varchar(100) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`MENU_ID`),
  KEY `MENU_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `MENU_ROLE_ASSOCIATION_ID_FK` (`ROLE_ASSOCIATION_ID`),
  CONSTRAINT `MENU_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `MENU_ROLE_ASSOCIATION_ID_FK` FOREIGN KEY (`ROLE_ASSOCIATION_ID`) REFERENCES `role` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `menu` */

insert  into `menu`(`MENU_ID`,`MENU_NAME`,`ROLE_ASSOCIATION_ID`,`ACTION_TYPE`,`ACTIVE_INDICATOR`,`CREATED_DATE`,`LAST_UPDATED`,`CREATED_BY`,`UPDATED_BY`,`COMPANY_ASSOCIATION_ID`) values (1,'brands',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(2,'status',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(3,'cashManagement',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(5,'customerDetails',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(6,'customerGroup',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(7,'customers',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(8,'ecomProducts',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(9,'home',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(10,'layoutDesign',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(11,'loyalty',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(12,'manageCustomer',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(13,'manageOutlet',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(14,'manageProduct',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(15,'manageRegister',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(16,'newCustomer',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(17,'newPriceBook',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(18,'newProduct',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(19,'newSupplier',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(20,'newUser',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(21,'orders',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(23,'outlets',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(24,'paymentType',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(25,'poCreateandReceive',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(26,'poCreateandReceiveEdit',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(27,'priceBook',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(28,'productDetails',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(29,'products',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(30,'productTags',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(31,'productType',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(32,'purchaseOrder',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(33,'purchaseOrderActions',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(34,'purchaseOrderDetails',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(35,'purchaseOrderEditDetails',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(36,'purchaseOrderEditProducts',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(37,'purchaseOrderReceive',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(38,'register',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(39,'registerClose',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(40,'roleDetails',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(41,'roles',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(42,'salesHistory',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(43,'salesLedger',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(44,'salesTax',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(45,'sell',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(46,'stockControl',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(47,'stockReturn',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(48,'stockReturnActions',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(49,'stockReturnDetails',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(50,'stockReturnEditDetails',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(51,'stockReturnEditProducts',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(52,'stockTransfer',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(53,'stockTransferActions',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(54,'stockTransferDetails',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(55,'stockTransferEditDetails',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(56,'stockTransferEditProducts',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(57,'store',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(58,'storeCredit',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(59,'suppliers',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(60,'users',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(61,'manageRole',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(62,'support',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(63,'ticketActivity',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(64,'manageUser',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(65,'printLabels',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(66,'supplierDetails',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(67,'salesReport',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(68,'companySetupImages',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(69,'outletPaymentDetails',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(70,'orgHierarchy',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(71,'managePriceBook',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(72,'brands',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(73,'status',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(74,'cashManagement',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(76,'customerDetails',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(77,'customerGroup',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(78,'customers',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(79,'ecomProducts',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(80,'home',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(81,'layoutDesign',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(82,'loyalty',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(83,'manageCustomer',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(84,'manageOutlet',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(85,'manageProduct',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(86,'manageRegister',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(87,'newCustomer',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(88,'newPriceBook',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(89,'newProduct',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(90,'newSupplier',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(91,'newUser',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(92,'orders',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(94,'outlets',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(95,'paymentType',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(96,'poCreateandReceive',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(97,'poCreateandReceiveEdit',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(98,'priceBook',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(99,'productDetails',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(100,'products',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(101,'productTags',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(102,'productType',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(103,'purchaseOrder',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(104,'purchaseOrderActions',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(105,'purchaseOrderDetails',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(106,'purchaseOrderEditDetails',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(107,'purchaseOrderEditProducts',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(108,'purchaseOrderReceive',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(109,'register',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(110,'registerClose',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(111,'roleDetails',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(112,'roles',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(113,'salesHistory',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(114,'salesLedger',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(115,'salesTax',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(116,'sell',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(117,'stockControl',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(118,'stockReturn',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(119,'stockReturnActions',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(120,'stockReturnDetails',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(121,'stockReturnEditDetails',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(122,'stockReturnEditProducts',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(123,'stockTransfer',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(124,'stockTransferActions',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(125,'stockTransferDetails',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:53',1,1,1),(126,'stockTransferEditDetails',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(127,'stockTransferEditProducts',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(128,'store',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(129,'storeCredit',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(130,'suppliers',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(131,'users',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(132,'manageRole',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(133,'support',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(134,'ticketActivity',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(135,'manageUser',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(136,'printLabels',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(137,'supplierDetails',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(138,'salesReport',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(139,'companySetupImages',2,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(140,'managePriceBook',2,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(141,'brands',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(142,'status',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(143,'cashManagement',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(145,'customerDetails',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(146,'customerGroup',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(147,'customers',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(148,'ecomProducts',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(149,'home',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(150,'layoutDesign',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(151,'loyalty',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(152,'manageCustomer',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(153,'manageOutlet',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(154,'manageProduct',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(155,'manageRegister',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(156,'newCustomer',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(157,'newPriceBook',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(158,'newProduct',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(159,'newSupplier',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(160,'newUser',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(161,'orders',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(163,'outlets',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(164,'paymentType',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(165,'poCreateandReceive',3,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(166,'poCreateandReceiveEdit',3,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(167,'priceBook',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(168,'productDetails',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(169,'products',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(170,'productTags',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(171,'productType',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(172,'purchaseOrder',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(173,'purchaseOrderActions',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(174,'purchaseOrderDetails',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(175,'purchaseOrderEditDetails',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(176,'purchaseOrderEditProducts',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(177,'purchaseOrderReceive',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(178,'register',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(179,'registerClose',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(180,'roleDetails',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(181,'roles',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(182,'salesHistory',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(183,'salesLedger',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(184,'salesTax',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(185,'sell',3,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(186,'stockControl',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(187,'stockReturn',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(188,'stockReturnActions',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(189,'stockReturnDetails',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(190,'stockReturnEditDetails',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(191,'stockReturnEditProducts',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(192,'stockTransfer',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(193,'stockTransferActions',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(194,'stockTransferDetails',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(195,'stockTransferEditDetails',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(196,'stockTransferEditProducts',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(197,'store',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(198,'storeCredit',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(199,'suppliers',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(200,'users',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(201,'manageRole',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(202,'support',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(203,'ticketActivity',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(204,'manageUser',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(205,'printLabels',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(206,'supplierDetails',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(207,'salesReport',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(208,'companySetupImages',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(209,'managePriceBook',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(210,'userSalesReport',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(211,'userSalesReport',2,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(212,'userSalesReport',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(213,'inventoryReport',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(214,'inventoryReport',2,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(215,'inventoryReport',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(216,'outletSalesReport',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(217,'outletSalesReport',2,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(218,'outletSalesReport',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(219,'brandSalesReport',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(220,'brandSalesReport',2,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(221,'brandSalesReport',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(222,'productTypeSalesReport',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(223,'productTypeSalesReport',2,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(224,'productTypeSalesReport',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(225,'supplierSalesReport',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(226,'supplierSalesReport',2,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(227,'supplierSalesReport',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(228,'customerSalesReport',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(229,'customerSalesReport',2,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(230,'customerSalesReport',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(231,'customerGroupSalesReport',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(232,'customerGroupSalesReport',2,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(233,'customerGroupSalesReport',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(234,'paymentReport',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(235,'paymentReport',2,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(236,'paymentReport',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(237,'registerReport',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(238,'registerReport',2,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(239,'registerReport',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(240,'outletPaymentDetails',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(241,'outletPaymentDetails',2,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(242,'stockReturntoWarehouse',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(243,'stockReturntoWarehouseActions',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(244,'stockReturntoWarehouseDetails',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(245,'stockReturntoWarehouseEditDetails',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(246,'stockReturntoWarehouseEditProducts',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(247,'stockReturntoWarehouse',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(248,'stockReturntoWarehouseActions',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(249,'stockReturntoWarehouseDetails',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(250,'stockReturntoWarehouseEditDetails',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(251,'stockReturntoWarehouseEditProducts',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(252,'stockReturntoWarehouse',3,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(253,'stockReturntoWarehouseActions',3,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(254,'stockReturntoWarehouseDetails',3,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(255,'stockReturntoWarehouseEditDetails',3,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(256,'stockReturntoWarehouseEditProducts',3,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(257,'expenseReport',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(258,'expenseReport',2,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(259,'expenseReport',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(260,'salesReportWithSale',1,'Page','','2019-11-28 00:00:00','2019-11-28 00:00:00',1,1,1),(261,'salesReportWithSale',2,'Page','','2019-11-28 00:00:00','2019-11-28 14:44:54',1,1,1),(262,'salesReportWithSale',3,'Page','','2019-11-28 00:00:00','2019-11-28 00:00:00',1,1,1),(263,'salesReportWithOutSale',1,'Page','','2019-11-28 00:00:00','2019-11-28 00:00:00',1,1,1),(264,'salesReportWithOutSale',2,'Page','','2019-11-28 00:00:00','2019-11-28 14:44:54',1,1,1),(265,'salesReportWithOutSale',3,'Page','','2019-11-28 00:00:00','2019-11-28 00:00:00',1,1,1),(266,'productStockHistoryReport',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(267,'productStockHistoryReport',2,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(268,'productStockHistoryReport',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(269,'inventoryCount',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(270,'inventoryCountCreate',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(271,'inventoryCountDetails',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(272,'inventoryCountActions',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(273,'inventoryCountEditDetails',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(274,'inventoryCount',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(275,'inventoryCountCreate',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(276,'inventoryCountDetails',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(277,'inventoryCountActions',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(278,'inventoryCountEditDetails',2,'Page','','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(279,'inventoryCount',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(280,'inventoryCountCreate',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(281,'inventoryCountDetails',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(282,'inventoryCountActions',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(283,'inventoryCountEditDetails',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(284,'productStockHistoryReport',1,'Page','','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(285,'productStockHistoryReport',2,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:44:54',1,1,1),(286,'productStockHistoryReport',3,'Page','\0','2019-11-28 14:37:54','2019-11-28 14:37:54',1,1,1),(287,'salesDetailReport',1,'Page','','2019-11-28 00:00:00','2019-11-28 00:00:00',1,1,1),(288,'salesDetailReport',2,'Page','','2019-11-28 00:00:00','2019-11-28 14:44:54',1,1,1),(289,'salesDetailReport',3,'Page','','2019-11-28 00:00:00','2019-11-28 00:00:00',1,1,1),(290,'registerDetail',1,'Page','','2019-11-28 00:00:00','2019-11-28 00:00:00',1,1,1),(291,'registerDetail',2,'Page','','2019-11-28 00:00:00','2019-11-28 00:00:00',1,1,1),(292,'registerDetail',3,'Page','','2019-11-28 00:00:00','2019-11-28 00:00:00',1,1,1);

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `MESSAGE_ID` int(11) NOT NULL auto_increment,
  `MESSAGE_BUNDLE_COUNT` int(11) NOT NULL,
  `PACKAGE_START_DATE` date default NULL,
  `PACKAGE_END_DATE` date default NULL,
  `PACKAGE_RENEW_DATE` date default NULL,
  `MESSAGE_TEXT_LIMIT` int(11) NOT NULL,
  `DESCRIPTION` varchar(256) default NULL,
  `VENDER_NAME` varchar(256) default NULL,
  `MASK_NAME` varchar(256) default NULL,
  `USER_ID` varchar(256) default NULL,
  `PASSWORD` varchar(256) default NULL,
  `NTN_NUMBER` varchar(256) default NULL,
  `OWNER_NAME` varchar(256) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL,
  PRIMARY KEY  (`MESSAGE_ID`),
  KEY `MESSAGE_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `MESSAGE_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  CONSTRAINT `MESSAGE_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `MESSAGE_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `message` */

/*Table structure for table `message_detail` */

DROP TABLE IF EXISTS `message_detail`;

CREATE TABLE `message_detail` (
  `MESSAGE_DETAIL_ID` int(11) NOT NULL auto_increment,
  `SENDER_ID` varchar(256) default NULL,
  `RECEIVER_ID` varchar(256) default NULL,
  `MESSAGE_DESCRIPTION` varchar(1000) default NULL,
  `DELIVERY_ID` mediumblob,
  `DELIVERY_STATUS` varchar(1000) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `MESSAGE_ASSOCIATION_ID` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL,
  PRIMARY KEY  (`MESSAGE_DETAIL_ID`),
  KEY `MESSAGE_DETAIL_MESSAGE_ASSOCIATION_ID_FK` (`MESSAGE_ASSOCIATION_ID`),
  KEY `MESSAGE_DETAIL_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `MESSAGE_DETAIL_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  CONSTRAINT `MESSAGE_DETAIL_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `MESSAGE_DETAIL_MESSAGE_ASSOCIATION_ID_FK` FOREIGN KEY (`MESSAGE_ASSOCIATION_ID`) REFERENCES `message` (`MESSAGE_ID`),
  CONSTRAINT `MESSAGE_DETAIL_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `message_detail` */

/*Table structure for table `module` */

DROP TABLE IF EXISTS `module`;

CREATE TABLE `module` (
  `MODULE_ID` int(11) NOT NULL auto_increment,
  `MODULE_NAME` varchar(256) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`MODULE_ID`),
  KEY `MODULE_CREATED_BY_FK` (`CREATED_BY`),
  KEY `MODULE_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `MODULE_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  CONSTRAINT `MODULE_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `MODULE_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `MODULE_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `module` */

/*Table structure for table `mv_brand_sales_report` */

DROP TABLE IF EXISTS `mv_brand_sales_report`;

CREATE TABLE `mv_brand_sales_report` (
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
  `COMPANY_ASSOCIATION_ID` int(11) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `mv_brand_sales_report` */

/*Table structure for table `mv_cash_sale` */

DROP TABLE IF EXISTS `mv_cash_sale`;

CREATE TABLE `mv_cash_sale` (
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
  `company_association_id` int(11) default NULL,
  `outlet_assocication_id` int(11) default NULL,
  `PAYMENT_TYPE_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `mv_cash_sale` */

/*Table structure for table `mv_credit_sale` */

DROP TABLE IF EXISTS `mv_credit_sale`;

CREATE TABLE `mv_credit_sale` (
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
  `company_association_id` int(11) default NULL,
  `outlet_assocication_id` int(11) default NULL,
  `PAYMENT_TYPE_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `mv_credit_sale` */

/*Table structure for table `mv_customer_group_sales_report` */

DROP TABLE IF EXISTS `mv_customer_group_sales_report`;

CREATE TABLE `mv_customer_group_sales_report` (
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
  `COMPANY_ASSOCIATION_ID` int(11) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `mv_customer_group_sales_report` */

/*Table structure for table `mv_customer_sales_report` */

DROP TABLE IF EXISTS `mv_customer_sales_report`;

CREATE TABLE `mv_customer_sales_report` (
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
  `COMPANY_ASSOCIATION_ID` int(11) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `mv_customer_sales_report` */

/*Table structure for table `mv_outlet_sales_report` */

DROP TABLE IF EXISTS `mv_outlet_sales_report`;

CREATE TABLE `mv_outlet_sales_report` (
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
  `COMPANY_ASSOCIATION_ID` int(11) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `mv_outlet_sales_report` */

/*Table structure for table `mv_payment_report` */

DROP TABLE IF EXISTS `mv_payment_report`;

CREATE TABLE `mv_payment_report` (
  `OUTLET_ASSOCICATION_ID` int(11) default NULL,
  `OUTLET` varchar(100) default NULL,
  `CREATED_DATE` date default NULL,
  `AMOUNT` decimal(42,2) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `mv_payment_report` */

/*Table structure for table `mv_producttype_sales_report` */

DROP TABLE IF EXISTS `mv_producttype_sales_report`;

CREATE TABLE `mv_producttype_sales_report` (
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
  `COMPANY_ASSOCIATION_ID` int(11) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `mv_producttype_sales_report` */

/*Table structure for table `mv_sale_details` */

DROP TABLE IF EXISTS `mv_sale_details`;

CREATE TABLE `mv_sale_details` (
  `Product` varchar(500) default NULL,
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
  `COMPANY_ASSOCIATION_ID` int(11) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `mv_sale_details` */

/*Table structure for table `mv_salereport_withoutsale` */

DROP TABLE IF EXISTS `mv_salereport_withoutsale`;

CREATE TABLE `mv_salereport_withoutsale` (
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
  `COMPANY_ASSOCIATION_ID` int(11) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `mv_salereport_withoutsale` */

/*Table structure for table `mv_salereport_withsale` */

DROP TABLE IF EXISTS `mv_salereport_withsale`;

CREATE TABLE `mv_salereport_withsale` (
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
  `COMPANY_ASSOCIATION_ID` int(11) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `mv_salereport_withsale` */

/*Table structure for table `mv_supplier_sales_report` */

DROP TABLE IF EXISTS `mv_supplier_sales_report`;

CREATE TABLE `mv_supplier_sales_report` (
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
  `COMPANY_ASSOCIATION_ID` int(11) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `mv_supplier_sales_report` */

/*Table structure for table `mv_tag_sales_report` */

DROP TABLE IF EXISTS `mv_tag_sales_report`;

CREATE TABLE `mv_tag_sales_report` (
  `Outlet` varchar(100) default NULL,
  `Tag` varchar(200) default NULL,
  `CREATED_DATE` date default NULL,
  `Revenue` decimal(52,2) default NULL,
  `Revenue_tax_incl` decimal(53,2) default NULL,
  `Cost_of_Goods` decimal(52,2) default NULL,
  `Gross_Profit` decimal(53,2) default NULL,
  `Margin` decimal(50,6) default NULL,
  `Items_Sold` decimal(32,0) default NULL,
  `Tax` decimal(42,2) default NULL,
  `company_association_id` int(11) default NULL,
  `outlet_assocication_id` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `mv_tag_sales_report` */

/*Table structure for table `mv_temp_sale` */

DROP TABLE IF EXISTS `mv_temp_sale`;

CREATE TABLE `mv_temp_sale` (
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
  `COMPANY_ASSOCIATION_ID` int(11) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `mv_temp_sale` */

/*Table structure for table `mv_user_sales_report` */

DROP TABLE IF EXISTS `mv_user_sales_report`;

CREATE TABLE `mv_user_sales_report` (
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
  `COMPANY_ASSOCIATION_ID` int(11) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `mv_user_sales_report` */

/*Table structure for table `notification` */

DROP TABLE IF EXISTS `notification`;

CREATE TABLE `notification` (
  `NOTIFICATION_ID` int(11) NOT NULL auto_increment,
  `OUTLET_ID_FROM` int(11) NOT NULL,
  `OUTLET_ID_TO` int(11) NOT NULL,
  `SUBJECT` varchar(256) NOT NULL,
  `DESCRIPTION` varchar(1000) NOT NULL,
  `MARK_AS_READ` bit(1) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  PRIMARY KEY  (`NOTIFICATION_ID`),
  KEY `NOTIFICATION_OUTLET_ID_TO_FK` (`OUTLET_ID_TO`),
  KEY `NOTIFICATION_OUTLET_ID_FROM_FK` (`OUTLET_ID_FROM`),
  KEY `NOTIFICATION_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  CONSTRAINT `NOTIFICATION_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `NOTIFICATION_OUTLET_ID_FROM_FK` FOREIGN KEY (`OUTLET_ID_FROM`) REFERENCES `outlet` (`OUTLET_ID`),
  CONSTRAINT `NOTIFICATION_OUTLET_ID_TO_FK` FOREIGN KEY (`OUTLET_ID_TO`) REFERENCES `outlet` (`OUTLET_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `notification` */

/*Table structure for table `order_detail` */

DROP TABLE IF EXISTS `order_detail`;

CREATE TABLE `order_detail` (
  `ORDER_DETAIL_ID` int(11) NOT NULL auto_increment,
  `ORDER_MAIN_ASSOCICATION_ID` int(11) default NULL,
  `PRODUCT_VARIENT_ASSOCIATION_ID` int(11) default NULL,
  `PRODUCT_ASSOCIATION_ID` int(11) default NULL,
  `ITEM_UNIT_PRICE` decimal(21,5) default NULL,
  `ITEM_TAX_AMOUNT` decimal(21,5) default NULL,
  `ITEM_NOTES` varchar(256) default NULL,
  `PRODUCT_QUANTITY` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL,
  PRIMARY KEY  (`ORDER_DETAIL_ID`),
  KEY `ORDER_DETAIL_ORDER_MAIN_ASSOCICATION_ID_FK` (`ORDER_MAIN_ASSOCICATION_ID`),
  KEY `ORDER_DETAIL_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `ORDER_DETAIL_PRODUCT_VARIENT_ASSOCIATION_ID_FK` (`PRODUCT_VARIENT_ASSOCIATION_ID`),
  KEY `ORDER_DETAIL_PRODUCT_ASSOCIATION_ID_FK` (`PRODUCT_ASSOCIATION_ID`),
  CONSTRAINT `ORDER_DETAIL_ORDER_MAIN_ASSOCICATION_ID_FK` FOREIGN KEY (`ORDER_MAIN_ASSOCICATION_ID`) REFERENCES `order_main` (`ORDER_MAIN_ID`),
  CONSTRAINT `ORDER_DETAIL_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  CONSTRAINT `ORDER_DETAIL_PRODUCT_ASSOCIATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCIATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  CONSTRAINT `ORDER_DETAIL_PRODUCT_VARIENT_ASSOCIATION_ID_FK` FOREIGN KEY (`PRODUCT_VARIENT_ASSOCIATION_ID`) REFERENCES `product_variant` (`PRODUCT_VARIANT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `order_detail` */

/*Table structure for table `order_main` */

DROP TABLE IF EXISTS `order_main`;

CREATE TABLE `order_main` (
  `ORDER_MAIN_ID` int(11) NOT NULL auto_increment,
  `ORDER_REF_NBR` varchar(200) default NULL,
  `ORDER_TRACKING_NBR` varchar(200) default NULL,
  `ORDER_NOTES` varchar(500) default NULL,
  `ORDER_GENERATION_DTE` datetime default NULL,
  `ORDER_CANCEL_DTE` datetime default NULL,
  `ORDER_EXPECTED_DELIVERY_DTE` datetime default NULL,
  `ORDER_DELIVERY_DTE` datetime default NULL,
  `ORDER_BILL_AMT` decimal(21,5) default NULL,
  `TAX_AMT` decimal(21,5) default NULL,
  `STATUS_ASSOCICATION_ID` int(11) NOT NULL,
  `CONTACT_ASSOCIATION_ID` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `PAYMENT_TYPE_ASSOCICATION_ID` int(11) NOT NULL,
  `DELIVER_METHOD_ASSOCICATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`ORDER_MAIN_ID`),
  KEY `ORDER_MAIN_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `ORDER_MAIN_CONTACT_ASSOCIATION_ID_FK` (`CONTACT_ASSOCIATION_ID`),
  KEY `ORDER_MAIN_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `ORDER_MAIN_PAYMENT_TYPE_ASSOCICATION_ID_FK` (`PAYMENT_TYPE_ASSOCICATION_ID`),
  KEY `ORDER_MAIN_DELIVERY_METHOD_ID_FK` (`DELIVER_METHOD_ASSOCICATION_ID`),
  KEY `ORDER_MAIN_STATUS_ASSOCICATION_ID_FK` (`STATUS_ASSOCICATION_ID`),
  CONSTRAINT `ORDER_MAIN_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `ORDER_MAIN_CONTACT_ASSOCIATION_ID_FK` FOREIGN KEY (`CONTACT_ASSOCIATION_ID`) REFERENCES `contact` (`CONTACT_ID`),
  CONSTRAINT `ORDER_MAIN_DELIVERY_METHOD_ID_FK` FOREIGN KEY (`DELIVER_METHOD_ASSOCICATION_ID`) REFERENCES `delivery_method` (`DELIVERY_METHOD_ID`),
  CONSTRAINT `ORDER_MAIN_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  CONSTRAINT `ORDER_MAIN_PAYMENT_TYPE_ASSOCICATION_ID_FK` FOREIGN KEY (`PAYMENT_TYPE_ASSOCICATION_ID`) REFERENCES `payment_type` (`PAYMENT_TYPE_ID`),
  CONSTRAINT `ORDER_MAIN_STATUS_ASSOCICATION_ID_FK` FOREIGN KEY (`STATUS_ASSOCICATION_ID`) REFERENCES `status` (`STATUS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `order_main` */

/*Table structure for table `outlet` */

DROP TABLE IF EXISTS `outlet`;

CREATE TABLE `outlet` (
  `OUTLET_ID` int(11) NOT NULL auto_increment,
  `OUTLET_NAME` varchar(100) default NULL,
  `ORDER_NUMBER` varchar(100) default NULL,
  `ORDER_NUMBER_PREFIX` varchar(100) default NULL,
  `CONTACT_NUMBER_PREFIX` varchar(100) default NULL,
  `CONTACT_RETURN_NUMBER` varchar(100) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `SALES_TAX_ASSOCICATION_ID` int(11) NOT NULL,
  `ADDRESS_ASSOCICATION_ID` int(11) default NULL,
  `TIME_ZONE_ASSOCICATION_ID` int(11) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `IS_HEAD_OFFICE` bit(1) default NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  PRIMARY KEY  (`OUTLET_ID`),
  KEY `OUTLET_ADDRESS_ASSOCICATION_ID_FK` (`ADDRESS_ASSOCICATION_ID`),
  KEY `OUTLET_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `OUTLET_SALES_TAX_ASSOCICATION_ID_FK` (`SALES_TAX_ASSOCICATION_ID`),
  KEY `OUTLET_TIME_ZONE_ASSOCICATION_ID_FK` (`TIME_ZONE_ASSOCICATION_ID`),
  CONSTRAINT `OUTLET_ADDRESS_ASSOCICATION_ID_FK` FOREIGN KEY (`ADDRESS_ASSOCICATION_ID`) REFERENCES `address` (`ADDRESS_ID`),
  CONSTRAINT `OUTLET_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `OUTLET_SALES_TAX_ASSOCICATION_ID_FK` FOREIGN KEY (`SALES_TAX_ASSOCICATION_ID`) REFERENCES `sales_tax` (`SALES_TAX_ID`),
  CONSTRAINT `OUTLET_TIME_ZONE_ASSOCICATION_ID_FK` FOREIGN KEY (`TIME_ZONE_ASSOCICATION_ID`) REFERENCES `time_zone` (`TIME_ZONE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `outlet` */

insert  into `outlet`(`OUTLET_ID`,`OUTLET_NAME`,`ORDER_NUMBER`,`ORDER_NUMBER_PREFIX`,`CONTACT_NUMBER_PREFIX`,`CONTACT_RETURN_NUMBER`,`COMPANY_ASSOCIATION_ID`,`SALES_TAX_ASSOCICATION_ID`,`ADDRESS_ASSOCICATION_ID`,`TIME_ZONE_ASSOCICATION_ID`,`ACTIVE_INDICATOR`,`IS_HEAD_OFFICE`,`CREATED_DATE`,`LAST_UPDATED`,`CREATED_BY`,`UPDATED_BY`) values (1,'Main Outlet','100','Main Outlet','temp','4',1,1,2,1,'','','2019-11-28 14:36:18','2019-11-28 14:41:08',1,1);

/*Table structure for table `payment_type` */

DROP TABLE IF EXISTS `payment_type`;

CREATE TABLE `payment_type` (
  `PAYMENT_TYPE_ID` int(11) NOT NULL auto_increment,
  `PAYMENT_TYPE_VALUE` varchar(100) NOT NULL,
  `ROUND_TO` varchar(50) default NULL,
  `GATEWAY` varchar(100) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  PRIMARY KEY  (`PAYMENT_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `payment_type` */

insert  into `payment_type`(`PAYMENT_TYPE_ID`,`PAYMENT_TYPE_VALUE`,`ROUND_TO`,`GATEWAY`,`ACTIVE_INDICATOR`,`CREATED_DATE`,`LAST_UPDATED`,`CREATED_BY`,`UPDATED_BY`) values (1,'Cash',NULL,NULL,'','2019-11-28 14:35:56','2019-11-28 14:35:56',NULL,NULL),(2,'Credit Card',NULL,NULL,'','2019-11-28 14:35:56','2019-11-28 14:35:56',NULL,NULL);

/*Table structure for table `price_book` */

DROP TABLE IF EXISTS `price_book`;

CREATE TABLE `price_book` (
  `PRICE_BOOK_ID` int(11) NOT NULL auto_increment,
  `PRICE_BOOK_NAME` varchar(250) character set utf8 NOT NULL COMMENT 'STOCK REFERENCE NUMBER',
  `CONTACT_GROUP_ASSOCICATION_ID` int(11) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL,
  `VALID_FROM` date NOT NULL,
  `VALID_TO` date NOT NULL,
  `IS_VALID_ON_STORE` bit(1) default NULL,
  `IS_VALID_ON_ECOM` bit(1) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `FLAT_SALE` varchar(6) NOT NULL default 'false',
  `FLAT_DISCOUNT` decimal(8,5) NOT NULL default '0.00000',
  `OUTELETS_GROUP` varchar(200) default NULL,
  PRIMARY KEY  (`PRICE_BOOK_ID`),
  KEY `PRICE_BOOK_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `PRICE_BOOK_CREATED_BY_FK` (`CREATED_BY`),
  KEY `PRICE_BOOK_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `PRICE_BOOK_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `PRICE_BOOK_CONTACT_GROUP_ASSOCICATION_ID_FK` (`CONTACT_GROUP_ASSOCICATION_ID`),
  CONSTRAINT `PRICE_BOOK_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `PRICE_BOOK_CONTACT_GROUP_ASSOCICATION_ID_FK` FOREIGN KEY (`CONTACT_GROUP_ASSOCICATION_ID`) REFERENCES `contact_group` (`CONTACT_GROUP_ID`),
  CONSTRAINT `PRICE_BOOK_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `PRICE_BOOK_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  CONSTRAINT `PRICE_BOOK_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `price_book` */

/*Table structure for table `price_book_detail` */

DROP TABLE IF EXISTS `price_book_detail`;

CREATE TABLE `price_book_detail` (
  `PRICE_BOOK_DETAIL_ID` int(11) NOT NULL auto_increment,
  `PRICE_BOOK_ASSOCICATION_ID` int(11) NOT NULL,
  `PRODUCT_ASSOCICATION_ID` int(11) NOT NULL,
  `PRODUCT_VARIENT_ASSOCICATION_ID` int(11) default NULL,
  `SUPPLY_PRICE` decimal(20,2) NOT NULL,
  `MARKUP` decimal(8,5) NOT NULL,
  `DISCOUNT` decimal(8,5) NOT NULL,
  `MIN_UNITS` int(11) default NULL,
  `MAX_UNITS` int(11) default NULL,
  `SALES_TAX_ASSOCIATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `UUID` varchar(500) NOT NULL,
  PRIMARY KEY  (`PRICE_BOOK_DETAIL_ID`),
  KEY `PRICE_BOOK_DETAIL_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `PRICE_BOOK_DETAIL_CREATED_BY_FK` (`CREATED_BY`),
  KEY `PRICE_BOOK_DETAIL_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `PRICE_BOOK_DETAIL_PRODUCT_VARIENT_ASSOCIATION_ID_FK` (`PRODUCT_VARIENT_ASSOCICATION_ID`),
  KEY `PRICE_BOOK_DETAIL_PRODUCT_ASSOCICATION_ID_FK` (`PRODUCT_ASSOCICATION_ID`),
  KEY `PRICE_BOOK_DETAIL_PRICE_BOOK_ASSOCICATION_ID_FK` (`PRICE_BOOK_ASSOCICATION_ID`),
  KEY `PRICE_BOOK_DETAIL_SALES_TAX_ASSOCIATION_ID_FK` (`SALES_TAX_ASSOCIATION_ID`),
  CONSTRAINT `PRICE_BOOK_DETAIL_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `PRICE_BOOK_DETAIL_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `PRICE_BOOK_DETAIL_PRICE_BOOK_ASSOCICATION_ID_FK` FOREIGN KEY (`PRICE_BOOK_ASSOCICATION_ID`) REFERENCES `price_book` (`PRICE_BOOK_ID`),
  CONSTRAINT `PRICE_BOOK_DETAIL_PRODUCT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCICATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  CONSTRAINT `PRICE_BOOK_DETAIL_PRODUCT_VARIENT_ASSOCIATION_ID_FK` FOREIGN KEY (`PRODUCT_VARIENT_ASSOCICATION_ID`) REFERENCES `product_variant` (`PRODUCT_VARIANT_ID`),
  CONSTRAINT `PRICE_BOOK_DETAIL_SALES_TAX_ASSOCIATION_ID_FK` FOREIGN KEY (`SALES_TAX_ASSOCIATION_ID`) REFERENCES `sales_tax` (`SALES_TAX_ID`),
  CONSTRAINT `PRICE_BOOK_DETAIL_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `price_book_detail` */

/*Table structure for table `printer_format` */

DROP TABLE IF EXISTS `printer_format`;

CREATE TABLE `printer_format` (
  `PRINTER_FORMAT_ID` int(11) NOT NULL auto_increment,
  `PRINTER_FORMAT_VALUE` varchar(256) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  PRIMARY KEY  (`PRINTER_FORMAT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `printer_format` */

insert  into `printer_format`(`PRINTER_FORMAT_ID`,`PRINTER_FORMAT_VALUE`,`ACTIVE_INDICATOR`,`CREATED_DATE`,`LAST_UPDATED`,`CREATED_BY`,`UPDATED_BY`) values (1,'Continuous Feed','','2019-11-28 14:35:55','2019-11-28 14:35:55',1,1),(2,'Continuous Feed-Wide','','2019-11-28 14:35:55','2019-11-28 14:35:55',1,1);

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `PRODUCT_ID` int(11) NOT NULL auto_increment,
  `PRODUCT_NAME` varchar(500) NOT NULL,
  `PRODUCT_UUID` varchar(500) NOT NULL,
  `PRODUCT_DESC` varchar(1000) default NULL,
  `PRODUCT_HANDLER` varchar(45) NOT NULL COMMENT '\nA unique identifier for this product.\nThe handle is the unique identifier for this product.',
  `PRODUCT_TYPE_ASSOCICATION_ID` int(11) NOT NULL,
  `CONTACT_ASSOCICATION_ID` int(11) NOT NULL,
  `BRAND_ASSOCICATION_ID` int(11) NOT NULL,
  `SALES_ACCOUNT_CODE` varchar(45) default NULL,
  `PURCHASE_ACCOUNT_CODE` varchar(45) default NULL,
  `PRODUCT_CAN_BE_SOLD` varchar(10) NOT NULL,
  `STANDARD_PRODUCT` varchar(10) NOT NULL,
  `TRACKING_PRODUCT` varchar(10) NOT NULL,
  `VARIANT_PRODUCTS` varchar(10) NOT NULL,
  `CURRENT_INVENTORY` int(11) default NULL,
  `REORDER_POINT` int(11) default NULL,
  `REORDER_AMOUNT` decimal(20,2) default NULL,
  `SUPPLY_PRICE_EXCL_TAX` decimal(20,2) NOT NULL,
  `MARKUP_PRCT` decimal(12,5) NOT NULL,
  `SKU` varchar(500) NOT NULL COMMENT 'STOCK KEEPING UNIT ( SKU)',
  `OUTLET_ASSOCICATION_ID` int(11) NOT NULL,
  `SALES_TAX_ASSOCICATION_ID` int(11) NOT NULL COMMENT 'SALE TAX ID TO LINK WITH SALES TAX',
  `IMAGE_PATH` varchar(250) default NULL,
  `DISPLAY` bit(1) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `attribute1` varchar(500) default NULL,
  `attribute2` varchar(500) default NULL,
  `attribute3` varchar(500) default NULL,
  `IS_COMPOSITE` varchar(10) default NULL,
  PRIMARY KEY  (`PRODUCT_ID`),
  KEY `PRODUCT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `PRODUCT_SALES_TAX_ASSOCICATION_ID_FK` (`SALES_TAX_ASSOCICATION_ID`),
  KEY `PRODUCT_BRAND_ASSOCICATION_ID_FK` (`BRAND_ASSOCICATION_ID`),
  KEY `PRODUCT_CREATED_BY_FK` (`CREATED_BY`),
  KEY `PRODUCT_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `PRODUCT_PRODUCT_TYPE_ASSOCICATION_ID_FK` (`PRODUCT_TYPE_ASSOCICATION_ID`),
  KEY `PRODUCT_CONTACT_ASSOCICATION_ID_FK` (`CONTACT_ASSOCICATION_ID`),
  KEY `PRODUCT_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  CONSTRAINT `PRODUCT_BRAND_ASSOCICATION_ID_FK` FOREIGN KEY (`BRAND_ASSOCICATION_ID`) REFERENCES `brand` (`BRAND_ID`),
  CONSTRAINT `PRODUCT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `PRODUCT_CONTACT_ASSOCICATION_ID_FK` FOREIGN KEY (`CONTACT_ASSOCICATION_ID`) REFERENCES `contact` (`CONTACT_ID`),
  CONSTRAINT `PRODUCT_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `PRODUCT_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  CONSTRAINT `PRODUCT_PRODUCT_TYPE_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_TYPE_ASSOCICATION_ID`) REFERENCES `product_type` (`PRODUCT_TYPE_ID`),
  CONSTRAINT `PRODUCT_SALES_TAX_ASSOCICATION_ID_FK` FOREIGN KEY (`SALES_TAX_ASSOCICATION_ID`) REFERENCES `sales_tax` (`SALES_TAX_ID`),
  CONSTRAINT `PRODUCT_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `product` */

/*Table structure for table `product_history` */

DROP TABLE IF EXISTS `product_history`;

CREATE TABLE `product_history` (
  `PRODUCT_HISTORY_ID` int(11) NOT NULL auto_increment,
  `PRODUCT_HISTORY_UUID` varchar(500) NOT NULL,
  `ACTION_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `PRODUCT_ASSOCICATION_ID` int(11) NOT NULL,
  `PRODUCT_VARIANT_ASSOCICATION_ID` int(11) default NULL,
  `COMPOSITE_PRODUCT_ASSOCIATION_ID` int(11) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) NOT NULL,
  `QUANTITY` int(11) NOT NULL,
  `CHANGE_QUANTITY` int(11) NOT NULL,
  `OUTLET_QUANTITY` int(11) NOT NULL,
  `ACTION` varchar(50) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`PRODUCT_HISTORY_ID`),
  KEY `PRODUCT_HISTORY_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `PRODUCT_HISTORY_CREATED_BY_FK` (`CREATED_BY`),
  KEY `PRODUCT_HISTORY_PRODUCT_ASSOCICATION_ID_FK` (`PRODUCT_ASSOCICATION_ID`),
  KEY `PRODUCT_HISTORY_COMPOSITE_PRODUCT_ASSOCIATION_ID_FK` (`COMPOSITE_PRODUCT_ASSOCIATION_ID`),
  KEY `PRODUCT_HISTORY_PRODUCT_VARIANT_ASSOCICATION_ID_FK` (`PRODUCT_VARIANT_ASSOCICATION_ID`),
  KEY `PRODUCT_HISTORY_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  CONSTRAINT `PRODUCT_HISTORY_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `PRODUCT_HISTORY_COMPOSITE_PRODUCT_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPOSITE_PRODUCT_ASSOCIATION_ID`) REFERENCES `composite_product` (`COMPOSITE_PRODUCT_ID`),
  CONSTRAINT `PRODUCT_HISTORY_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `PRODUCT_HISTORY_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  CONSTRAINT `PRODUCT_HISTORY_PRODUCT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCICATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  CONSTRAINT `PRODUCT_HISTORY_PRODUCT_VARIANT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_VARIANT_ASSOCICATION_ID`) REFERENCES `product_variant` (`PRODUCT_VARIANT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `product_history` */

/*Table structure for table `product_price_history` */

DROP TABLE IF EXISTS `product_price_history`;

CREATE TABLE `product_price_history` (
  `PRODUCT_PRICE_HISTORY_ID` int(11) NOT NULL auto_increment,
  `PRODUCT_ID` int(11) NOT NULL,
  `PRODUCT_UUID` varchar(500) NOT NULL,
  `PRODUCT_VARIANT_ID` int(11) default NULL,
  `PRODUCT_VARIANT_UUID` varchar(500) default NULL,
  `CO_CODE` varchar(250) default NULL,
  `SRO_NUMBER` varchar(250) default NULL,
  `HS_CODE` varchar(250) default NULL,
  `DESCRIPTION` varchar(1000) default NULL,
  `DOLLAR_RATE` decimal(8,5) default NULL,
  `D_UNIT_VALUE_DECLARED` decimal(15,5) default NULL,
  `D_UNIT_VALUE_ASSESSED` decimal(15,5) default NULL,
  `D_TOTAL_VALUE_DECLARED` decimal(20,5) default NULL,
  `D_TOTAL_VALUE_ASSESSED` decimal(20,5) default NULL,
  `P_CUSTOM_VALUE_DECLARED` decimal(30,5) default NULL,
  `P_CUSTOM_VALUE_ASSESSED` decimal(30,5) default NULL,
  `NUMBER_OF_UNITS` decimal(15,5) default NULL,
  `CUSTOM_DUTY_PRCT` decimal(15,5) default NULL,
  `CUSTOM_DUTY_VALUE` decimal(15,5) default NULL,
  `SALES_TAX_PRCT` decimal(15,5) default NULL,
  `SALES_TAX_VALUE` decimal(15,5) default NULL,
  `REGULARITY_DUTY_PRCT` decimal(15,5) default NULL,
  `REGULARITY_DUTY_VALUE` decimal(15,5) default NULL,
  `ADDITIONAL_CUSTOM_DUTY_PRCT` decimal(15,5) default NULL,
  `ADDITIONAL_CUSTOM_DUTY_VALUE` decimal(15,5) default NULL,
  `ADDITIONAL_SALES_TAX_PRCT` decimal(15,5) default NULL,
  `ADDITIONAL_SALES_TAX_VALUE` decimal(15,5) default NULL,
  `EXCISE_PRCT` decimal(15,5) default NULL,
  `EXCISE_VALUE` decimal(15,5) default NULL,
  `TOTAL_VALUE` decimal(30,5) default NULL,
  `IT_PRCT` decimal(15,5) default NULL,
  `IT_VALUE` decimal(15,5) default NULL,
  `FTA_PRCT` decimal(15,5) default NULL,
  `FTA_VALUE` decimal(15,5) default NULL,
  `REMARKS` varchar(1000) default NULL,
  `VR_NUMBER` int(11) default NULL,
  `SUPPLY_PRICE_EXCL_TAX` decimal(20,2) NOT NULL,
  `MARKUP_PRCT` decimal(8,5) NOT NULL COMMENT 'MARKUP %',
  `OUTLET_ASSOCICATION_ID` int(11) NOT NULL,
  `SALES_TAX_ASSOCICATION_ID` int(11) NOT NULL COMMENT 'SALE TAX ID TO LINK WITH SALES TAX',
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `GR_NUMBER` varchar(500) default NULL,
  PRIMARY KEY  (`PRODUCT_PRICE_HISTORY_ID`),
  KEY `PRODUCT_PRICE_HISTORY_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `PRODUCT_PRICE_HISTORY_SALES_TAX_ASSOCICATION_ID_FK` (`SALES_TAX_ASSOCICATION_ID`),
  KEY `PRODUCT_PRICE_HISTORY_CREATED_BY_FK` (`CREATED_BY`),
  KEY `PRODUCT_PRICE_HISTORY_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `PRODUCT_PRICE_HISTORY_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  CONSTRAINT `PRODUCT_PRICE_HISTORY_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `PRODUCT_PRICE_HISTORY_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `PRODUCT_PRICE_HISTORY_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  CONSTRAINT `PRODUCT_PRICE_HISTORY_SALES_TAX_ASSOCICATION_ID_FK` FOREIGN KEY (`SALES_TAX_ASSOCICATION_ID`) REFERENCES `sales_tax` (`SALES_TAX_ID`),
  CONSTRAINT `PRODUCT_PRICE_HISTORY_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `product_price_history` */

/*Table structure for table `product_tag` */

DROP TABLE IF EXISTS `product_tag`;

CREATE TABLE `product_tag` (
  `PRODUCT_TAG_ID` int(11) NOT NULL auto_increment,
  `PRODUCT_TAG_UUID` varchar(500) NOT NULL,
  `TAG_ASSOCICATION_ID` int(11) NOT NULL,
  `PRODUCT_ASSOCICATION_ID` int(11) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`PRODUCT_TAG_ID`),
  KEY `PRODUCT_TAG_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `PRODUCT_TAG_CREATED_BY_FK` (`CREATED_BY`),
  KEY `PRODUCT_TAG_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `PRODUCT_TAG_PRODUCT_ASSOCICATION_ID_FK` (`PRODUCT_ASSOCICATION_ID`),
  KEY `PRODUCT_TAG_TAG_ASSOCICATION_ID_FK` (`TAG_ASSOCICATION_ID`),
  CONSTRAINT `PRODUCT_TAG_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `PRODUCT_TAG_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `PRODUCT_TAG_PRODUCT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCICATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  CONSTRAINT `PRODUCT_TAG_TAG_ASSOCICATION_ID_FK` FOREIGN KEY (`TAG_ASSOCICATION_ID`) REFERENCES `tag` (`TAG_ID`),
  CONSTRAINT `PRODUCT_TAG_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `product_tag` */

/*Table structure for table `product_type` */

DROP TABLE IF EXISTS `product_type`;

CREATE TABLE `product_type` (
  `PRODUCT_TYPE_ID` int(11) NOT NULL auto_increment,
  `PRODUCT_TYPE_NAME` varchar(200) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`PRODUCT_TYPE_ID`),
  KEY `PRODUCT_TYPE_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  CONSTRAINT `PRODUCT_TYPE_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `product_type` */

/*Table structure for table `product_variant` */

DROP TABLE IF EXISTS `product_variant`;

CREATE TABLE `product_variant` (
  `PRODUCT_VARIANT_ID` int(11) NOT NULL auto_increment,
  `PRODUCT_ASSOCICATION_ID` int(11) NOT NULL,
  `VARIANT_ATTRIBUTE_NAME` varchar(200) default NULL,
  `PRODUCT_VARIANT_UUID` varchar(500) NOT NULL,
  `PRODUCT_UUID` varchar(500) NOT NULL,
  `VARIANT_ATTRIBUTE_VALUE_1` varchar(200) default NULL,
  `VARIANT_ATTRIBUTE_VALUE_2` varchar(200) default NULL,
  `VARIANT_ATTRIBUTE_VALUE_3` varchar(200) default NULL,
  `VARIANT_ATTRIBUTE_ASSOCICATION_ID_1` int(11) default NULL,
  `VARIANT_ATTRIBUTE_ASSOCICATION_ID_2` int(11) default NULL,
  `VARIANT_ATTRIBUTE_ASSOCICATION_ID_3` int(11) default NULL,
  `CURRENT_INVENTORY` int(11) default NULL,
  `REORDER_POINT` int(11) default NULL,
  `REORDER_AMOUNT` decimal(20,2) default NULL,
  `SUPPLY_PRICE_EXCL_TAX` decimal(20,2) NOT NULL,
  `MARKUP_PRCT` decimal(12,5) NOT NULL,
  `SKU` varchar(500) NOT NULL COMMENT 'STOCK KEEPING UNIT ( SKU)',
  `OUTLET_ASSOCICATION_ID` int(11) NOT NULL,
  `SALES_TAX_ASSOCICATION_ID` int(11) NOT NULL COMMENT 'SALE TAX ID TO LINK WITH SALES TAX',
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `PRODUCT_VARIANT_ASSOCIATION_ID` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`PRODUCT_VARIANT_ID`),
  KEY `PRODUCT_VARIANT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `PRODUCT_VARIANT_CREATED_BY_FK` (`CREATED_BY`),
  KEY `PRODUCT_VARIANT_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `PRODUCT_VARIANT_SALES_TAX_ASSOCICATION_ID_FK` (`SALES_TAX_ASSOCICATION_ID`),
  KEY `PRODUCT_VARIANT_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `PRODUCT_VARIANT_PRODUCT_ASSOCICATION_ID_FK` (`PRODUCT_ASSOCICATION_ID`),
  KEY `PRODUCT_VARIANT_VARIANT_ATTRIBUTE_ASSOCICATION_ID_1_FK` (`VARIANT_ATTRIBUTE_ASSOCICATION_ID_1`),
  KEY `PRODUCT_VARIANT_VARIANT_ATTRIBUTE_ASSOCICATION_ID_2_FK` (`VARIANT_ATTRIBUTE_ASSOCICATION_ID_2`),
  KEY `PRODUCT_VARIANT_VARIANT_ATTRIBUTE_ASSOCICATION_ID_3_FK` (`VARIANT_ATTRIBUTE_ASSOCICATION_ID_3`),
  CONSTRAINT `PRODUCT_VARIANT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `PRODUCT_VARIANT_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `PRODUCT_VARIANT_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  CONSTRAINT `PRODUCT_VARIANT_PRODUCT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCICATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  CONSTRAINT `PRODUCT_VARIANT_SALES_TAX_ASSOCICATION_ID_FK` FOREIGN KEY (`SALES_TAX_ASSOCICATION_ID`) REFERENCES `sales_tax` (`SALES_TAX_ID`),
  CONSTRAINT `PRODUCT_VARIANT_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `PRODUCT_VARIANT_VARIANT_ATTRIBUTE_ASSOCICATION_ID_1_FK` FOREIGN KEY (`VARIANT_ATTRIBUTE_ASSOCICATION_ID_1`) REFERENCES `variant_attribute` (`VARIANT_ATTRIBUTE_ID`),
  CONSTRAINT `PRODUCT_VARIANT_VARIANT_ATTRIBUTE_ASSOCICATION_ID_2_FK` FOREIGN KEY (`VARIANT_ATTRIBUTE_ASSOCICATION_ID_2`) REFERENCES `variant_attribute` (`VARIANT_ATTRIBUTE_ID`),
  CONSTRAINT `PRODUCT_VARIANT_VARIANT_ATTRIBUTE_ASSOCICATION_ID_3_FK` FOREIGN KEY (`VARIANT_ATTRIBUTE_ASSOCICATION_ID_3`) REFERENCES `variant_attribute` (`VARIANT_ATTRIBUTE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `product_variant` */

/*Table structure for table `receipt` */

DROP TABLE IF EXISTS `receipt`;

CREATE TABLE `receipt` (
  `RECEIPT_ID` int(11) NOT NULL auto_increment,
  `RECEIPT_REF_NO` varchar(45) default NULL,
  `RECEIPT_DATE` datetime default NULL,
  `RECEIPT_AMOUNT` decimal(20,2) default NULL,
  `PAYMENT_TYPE_ASSOCICATION_ID` int(11) NOT NULL,
  `STATUS_ASSOCICATION_ID` int(11) NOT NULL,
  `RECEIPT_CANCELLATION_DATE` varchar(45) default NULL,
  `RECEIPT_UAF_AMT` decimal(20,2) default NULL,
  `OUTLET_ASSOCIATION_ID` int(11) default NULL,
  `CONTACT_ASSOCIATION_ID` int(11) default NULL,
  `INVOICE_MAIN_ASSOCICATION_ID` int(11) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `DAILY_REGISTER_ASSOCICATION_ID` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`RECEIPT_ID`),
  KEY `RECEIPT_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `RECEIPT_DAILY_REGISTER_ASSOCICATION_ID_FK` (`DAILY_REGISTER_ASSOCICATION_ID`),
  KEY `RECEIPT_OUTLET_ASSOCIATION_ID_FK` (`OUTLET_ASSOCIATION_ID`),
  KEY `RECEIPT_CONTACT_ASSOCICATION_ID_FK` (`CONTACT_ASSOCIATION_ID`),
  KEY `RECEIPT_INVOICE_MAIN_ASSOCICATION_ID_FK` (`INVOICE_MAIN_ASSOCICATION_ID`),
  KEY `RECEIPT_PAYMENT_TYPE_ASSOCICATION_ID_FK` (`PAYMENT_TYPE_ASSOCICATION_ID`),
  KEY `RECEIPT_STATUS_ASSOCICATION_ID_FK` (`STATUS_ASSOCICATION_ID`),
  CONSTRAINT `RECEIPT_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `RECEIPT_CONTACT_ASSOCICATION_ID_FK` FOREIGN KEY (`CONTACT_ASSOCIATION_ID`) REFERENCES `contact` (`CONTACT_ID`),
  CONSTRAINT `RECEIPT_DAILY_REGISTER_ASSOCICATION_ID_FK` FOREIGN KEY (`DAILY_REGISTER_ASSOCICATION_ID`) REFERENCES `daily_register` (`DAILY_REGISTER_ID`),
  CONSTRAINT `RECEIPT_INVOICE_MAIN_ASSOCICATION_ID_FK` FOREIGN KEY (`INVOICE_MAIN_ASSOCICATION_ID`) REFERENCES `invoice_main` (`INVOICE_MAIN_ID`),
  CONSTRAINT `RECEIPT_OUTLET_ASSOCIATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCIATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  CONSTRAINT `RECEIPT_PAYMENT_TYPE_ASSOCICATION_ID_FK` FOREIGN KEY (`PAYMENT_TYPE_ASSOCICATION_ID`) REFERENCES `payment_type` (`PAYMENT_TYPE_ID`),
  CONSTRAINT `RECEIPT_STATUS_ASSOCICATION_ID_FK` FOREIGN KEY (`STATUS_ASSOCICATION_ID`) REFERENCES `status` (`STATUS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `receipt` */

/*Table structure for table `register` */

DROP TABLE IF EXISTS `register`;

CREATE TABLE `register` (
  `REGISTER_ID` int(11) NOT NULL auto_increment,
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
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`REGISTER_ID`),
  KEY `REGISTER_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `REGISTER_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  CONSTRAINT `REGISTER_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `REGISTER_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `register` */

insert  into `register`(`REGISTER_ID`,`REGISTER_NAME`,`CACHE_MANAGEMENT_ENABLE`,`RECEIPT_NUMBER`,`RECEIPT_PREFIX`,`RECEIPT_SUFIX`,`SELECT_NEXT_USER_FOR_SALE`,`EMAIL_RECEIPT`,`PRINT_RECEIPT`,`NOTES`,`PRINT_NOTES_ON_RECEIPT`,`SHOW_DISCOUNT_ON_RECEIPT`,`ACTIVE_INDICATOR`,`CREATED_DATE`,`LAST_UPDATED`,`CREATED_BY`,`UPDATED_BY`,`OUTLET_ASSOCICATION_ID`,`COMPANY_ASSOCIATION_ID`) values (1,'Main Register','Yes','1','1','1','No','No','Yes','On Save/Layby/Account/Return','No','Yes','','2019-11-28 14:36:18','2019-11-28 14:36:18',1,1,1,1);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `ROLE_ID` int(11) NOT NULL auto_increment,
  `DESCRIPTION` varchar(256) NOT NULL,
  `ACTION_TYPE` varchar(256) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `role` */

insert  into `role`(`ROLE_ID`,`DESCRIPTION`,`ACTION_TYPE`,`ACTIVE_INDICATOR`,`CREATED_DATE`,`LAST_UPDATED`) values (1,'Administrator','Admin','','2019-11-28 14:35:56','2019-11-28 14:35:56'),(2,'Manager','Manager','','2019-11-28 14:35:56','2019-11-28 14:35:56'),(3,'Cashier','Cashier','','2019-11-28 14:35:56','2019-11-28 14:35:56'),(4,'Ecom Administrator','Ecom Admin','','2019-11-28 14:35:56','2019-11-28 14:35:56'),(5,'Ecom Customer','Ecom Customer','','2019-11-28 14:35:56','2019-11-28 14:35:56');

/*Table structure for table `sales_tax` */

DROP TABLE IF EXISTS `sales_tax`;

CREATE TABLE `sales_tax` (
  `SALES_TAX_ID` int(11) NOT NULL auto_increment,
  `SALES_TAX_NAME` varchar(100) default NULL,
  `SALES_TAX_PERCENTAGE` double default NULL,
  `EFFECTIVE_DATE` date NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`SALES_TAX_ID`),
  KEY `SALES_TAX_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  CONSTRAINT `SALES_TAX_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `sales_tax` */

insert  into `sales_tax`(`SALES_TAX_ID`,`SALES_TAX_NAME`,`SALES_TAX_PERCENTAGE`,`EFFECTIVE_DATE`,`ACTIVE_INDICATOR`,`CREATED_DATE`,`LAST_UPDATED`,`CREATED_BY`,`UPDATED_BY`,`COMPANY_ASSOCIATION_ID`) values (1,'No Tax',0,'2019-11-28','','2019-11-28 14:36:18','2019-11-28 14:36:18',1,1,1);

/*Table structure for table `severity` */

DROP TABLE IF EXISTS `severity`;

CREATE TABLE `severity` (
  `SEVERITY_ID` int(11) NOT NULL auto_increment,
  `SEVERITY_DESCRIPTION` varchar(256) NOT NULL,
  PRIMARY KEY  (`SEVERITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `severity` */

insert  into `severity`(`SEVERITY_ID`,`SEVERITY_DESCRIPTION`) values (1,'HIGH'),(2,'MEDIUM'),(3,'LOW'),(4,'NORMAL');

/*Table structure for table `status` */

DROP TABLE IF EXISTS `status`;

CREATE TABLE `status` (
  `STATUS_ID` int(11) NOT NULL auto_increment,
  `STATUS_CODE` varchar(5) NOT NULL,
  `STATUS_DESC` varchar(45) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  PRIMARY KEY  (`STATUS_ID`),
  UNIQUE KEY `STATUS_STATUS_CODE_UK` (`STATUS_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `status` */

insert  into `status`(`STATUS_ID`,`STATUS_CODE`,`STATUS_DESC`,`ACTIVE_INDICATOR`,`CREATED_DATE`,`LAST_UPDATED`,`CREATED_BY`,`UPDATED_BY`) values (1,'Init','Initiated','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1),(2,'Inp','In Progress','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1),(3,'Comp','Completed','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1),(4,'Rtn','Returned','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1),(5,'Sett','Settled','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1),(6,'New','New','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1),(7,'Open','Open','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1),(8,'Close','Closed','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1),(9,'LayBy','LayBy','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1),(10,'Compl','Completed','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1),(11,'Park','Parked','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1),(12,'onAcc','OnAccount','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1),(13,'LyCom','Lay By Completed','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1),(14,'reCmp','Returned Completed','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1);

/*Table structure for table `stock_order` */

DROP TABLE IF EXISTS `stock_order`;

CREATE TABLE `stock_order` (
  `STOCK_ORDER_ID` int(11) NOT NULL auto_increment,
  `STOCK_REF_NO` varchar(45) default NULL COMMENT 'STOCK REFERENCE NUMBER',
  `STATUS_ASSOCICATION_ID` int(11) NOT NULL,
  `DILIVERY_DUE_DATE` date NOT NULL,
  `CONTACT_ID` int(11) NOT NULL,
  `ORDER_NO` varchar(45) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) NOT NULL,
  `SOURCE_OUTLET_ASSOCICATION_ID` int(11) default NULL COMMENT 'FOR STOCK TRANSFER INPLACE OF CONTACT',
  `CONTACT_INVOICE_NO` varchar(45) default NULL,
  `STOCK_ORDER_DATE` datetime default NULL,
  `REMARKS` varchar(500) default NULL,
  `ORDR_RECV_DATE` datetime default NULL COMMENT 'ORDER RECIEVING DATE',
  `RETURN_NO` varchar(45) default NULL COMMENT 'USED FOR STOCK RETURN TRANSACTION',
  `STOCK_ORDER_TYPE_ASSOCICATION_ID` int(11) NOT NULL COMMENT 'USED TO SAVE ORDER TYPE CODE E.G.\n\nCONTACT ORDER\nCONTACT RETURN\nOUTLET TRANSFER',
  `AUTOFILL_REORDER` bit(1) NOT NULL COMMENT 'USED TO STORE INFO ABOUT AUTOFILL FOR REORDER',
  `RETAIL_PRICE_BILL` bit(1) NOT NULL default '\0',
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `TOTAL_ITEMS` decimal(20,2) NOT NULL default '0.00',
  `TOTAL_AMOUNT` decimal(20,2) NOT NULL default '0.00',
  PRIMARY KEY  (`STOCK_ORDER_ID`),
  KEY `STOCK_ORDER_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `STOCK_ORDER_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `STOCK_ORDER_STATUS_ASSOCICATION_ID_FK` (`STATUS_ASSOCICATION_ID`),
  KEY `STOCK_ORDER_SOURCE_OUTLET_ASSOCICATION_ID_FK` (`SOURCE_OUTLET_ASSOCICATION_ID`),
  KEY `STOCK_ORDER_STOCK_ORDER_TYPE_ASSOCICATION_ID_FK` (`STOCK_ORDER_TYPE_ASSOCICATION_ID`),
  CONSTRAINT `STOCK_ORDER_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `STOCK_ORDER_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  CONSTRAINT `STOCK_ORDER_SOURCE_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`SOURCE_OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  CONSTRAINT `STOCK_ORDER_STATUS_ASSOCICATION_ID_FK` FOREIGN KEY (`STATUS_ASSOCICATION_ID`) REFERENCES `status` (`STATUS_ID`),
  CONSTRAINT `STOCK_ORDER_STOCK_ORDER_TYPE_ASSOCICATION_ID_FK` FOREIGN KEY (`STOCK_ORDER_TYPE_ASSOCICATION_ID`) REFERENCES `stock_order_type` (`STOCK_ORDER_TYPE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `stock_order` */

/*Table structure for table `stock_order_detail` */

DROP TABLE IF EXISTS `stock_order_detail`;

CREATE TABLE `stock_order_detail` (
  `STOCK_ORDER_DETAIL_ID` int(11) NOT NULL auto_increment,
  `STOCK_ORDER_ASSOCICATION_ID` int(11) NOT NULL,
  `PRODUCT_VARIANT_ASSOCICATION_ID` int(11) default NULL,
  `PRODUCT_ASSOCIATION_ID` int(11) default NULL,
  `IS_PRODUCT` bit(1) NOT NULL,
  `ORDER_PROD_QTY` int(11) default NULL COMMENT 'ORDERED product QUANTITY',
  `ORDR_SUPPLY_PRICE` decimal(20,2) default NULL COMMENT 'ORDERED SUPPLY PRICE',
  `RECV_PROD_QTY` int(11) default NULL COMMENT 'RECIVED product QUANTITY',
  `RECV_SUPPLY_PRICE` decimal(20,2) default NULL COMMENT 'RECIEVED SUPPLY PRICE',
  `RETAIL_PRICE` decimal(20,2) default NULL COMMENT 'RETAIL PRICE',
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`STOCK_ORDER_DETAIL_ID`),
  KEY `STOCK_ORDER_DETAIL_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `STOCK_ORDER_DETAIL_STOCK_ORDER_ASSOCICATION_ID_FK` (`STOCK_ORDER_ASSOCICATION_ID`),
  KEY `STOCK_ORDER_DETAIL_PRODUCT_VARIANT_ASSOCICATION_ID_FK` (`PRODUCT_VARIANT_ASSOCICATION_ID`),
  KEY `STOCK_ORDER_DETAIL_PRODUCT_ASSOCIATION_ID_FK` (`PRODUCT_ASSOCIATION_ID`),
  CONSTRAINT `STOCK_ORDER_DETAIL_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `STOCK_ORDER_DETAIL_PRODUCT_ASSOCIATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCIATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  CONSTRAINT `STOCK_ORDER_DETAIL_PRODUCT_VARIANT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_VARIANT_ASSOCICATION_ID`) REFERENCES `product_variant` (`PRODUCT_VARIANT_ID`),
  CONSTRAINT `STOCK_ORDER_DETAIL_STOCK_ORDER_ASSOCICATION_ID_FK` FOREIGN KEY (`STOCK_ORDER_ASSOCICATION_ID`) REFERENCES `stock_order` (`STOCK_ORDER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `stock_order_detail` */

/*Table structure for table `stock_order_type` */

DROP TABLE IF EXISTS `stock_order_type`;

CREATE TABLE `stock_order_type` (
  `STOCK_ORDER_TYPE_ID` int(11) NOT NULL auto_increment,
  `STOCK_ORDER_TYPE_CODE` varchar(5) NOT NULL,
  `STOCK_ORDER_TYPE_DESC` varchar(45) NOT NULL COMMENT 'USED TO SAVE ORDER TYPE CODE E.G.\n\nCONTACT ORDER\nCONTACT RETURN\nOUTLET TRANSFER',
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  PRIMARY KEY  (`STOCK_ORDER_TYPE_ID`),
  UNIQUE KEY `STOCK_ORDER_TYPE_STOCK_ORDER_TYPE_CODE_UK` (`STOCK_ORDER_TYPE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `stock_order_type` */

insert  into `stock_order_type`(`STOCK_ORDER_TYPE_ID`,`STOCK_ORDER_TYPE_CODE`,`STOCK_ORDER_TYPE_DESC`,`ACTIVE_INDICATOR`,`CREATED_DATE`,`LAST_UPDATED`,`CREATED_BY`,`UPDATED_BY`) values (1,'SO','Supplier Order','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1),(2,'SR','Supplier Return','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1),(3,'OT','Outlet Transfer','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1),(4,'SRW','Return to Warehouse','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1);

/*Table structure for table `tag` */

DROP TABLE IF EXISTS `tag`;

CREATE TABLE `tag` (
  `TAG_ID` int(11) NOT NULL auto_increment,
  `TAG_NAME` varchar(200) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`TAG_ID`),
  KEY `TAG_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `TAG_CREATED_BY_FK` (`CREATED_BY`),
  KEY `TAG_UPDATED_BY_FK` (`UPDATED_BY`),
  CONSTRAINT `TAG_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `TAG_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `TAG_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tag` */

/*Table structure for table `ticket` */

DROP TABLE IF EXISTS `ticket`;

CREATE TABLE `ticket` (
  `TICKET_ID` int(11) NOT NULL auto_increment,
  `DESCRIPTION` varchar(256) NOT NULL,
  `TICKET_DETAIL` blob NOT NULL,
  `SEVERITY_ASSOCIATION_ID` int(11) NOT NULL,
  `TICKET_STATUS` varchar(256) NOT NULL,
  `RE_OPEN` varchar(256) NOT NULL,
  `RE_OPEN_COUNT` int(11) NOT NULL,
  `CREATED_BY` int(11) NOT NULL,
  `RESOLVED_BY` int(11) default NULL,
  `RESOLVED_DATE` timestamp NULL default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default '0000-00-00 00:00:00',
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  PRIMARY KEY  (`TICKET_ID`),
  KEY `TICKET_CREATED_BY_FK` (`CREATED_BY`),
  KEY `TICKET_RESOLVED_BY_FK` (`RESOLVED_BY`),
  KEY `TICKET_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `TICKET_SEVERITY_ASSOCIATION_ID_FK` (`SEVERITY_ASSOCIATION_ID`),
  CONSTRAINT `TICKET_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `TICKET_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `TICKET_RESOLVED_BY_FK` FOREIGN KEY (`RESOLVED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `TICKET_SEVERITY_ASSOCIATION_ID_FK` FOREIGN KEY (`SEVERITY_ASSOCIATION_ID`) REFERENCES `severity` (`SEVERITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ticket` */

/*Table structure for table `ticket_activity` */

DROP TABLE IF EXISTS `ticket_activity`;

CREATE TABLE `ticket_activity` (
  `TICKET_ACTIVITY_ID` int(11) NOT NULL auto_increment,
  `TICKET_ASSOCIATION_ID` int(11) NOT NULL,
  `DESCRIPTION` varchar(256) NOT NULL,
  `USER_REPLY` varchar(256) NOT NULL,
  `OLD_STATUS` varchar(256) NOT NULL,
  `NEW_STATUS` varchar(256) NOT NULL,
  `RESOLVER_FEEDBACK` varchar(256) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`TICKET_ACTIVITY_ID`),
  KEY `TICKET_ACTIVITY_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `TICKET_ACTIVITY_TICKET_ASSOCIATION_ID_FK` (`TICKET_ASSOCIATION_ID`),
  CONSTRAINT `TICKET_ACTIVITY_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `TICKET_ACTIVITY_TICKET_ASSOCIATION_ID_FK` FOREIGN KEY (`TICKET_ASSOCIATION_ID`) REFERENCES `ticket` (`TICKET_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ticket_activity` */

/*Table structure for table `time_zone` */

DROP TABLE IF EXISTS `time_zone`;

CREATE TABLE `time_zone` (
  `TIME_ZONE_ID` int(11) NOT NULL auto_increment,
  `TIME_ZONE_VALUE` varchar(256) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  PRIMARY KEY  (`TIME_ZONE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `time_zone` */

insert  into `time_zone`(`TIME_ZONE_ID`,`TIME_ZONE_VALUE`,`ACTIVE_INDICATOR`,`CREATED_DATE`,`LAST_UPDATED`,`CREATED_BY`,`UPDATED_BY`) values (1,'(GMT+05:00)Asia/Karachi','','2019-11-28 14:35:56','2019-11-28 14:35:56',1,1);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `USER_ID` int(11) NOT NULL auto_increment,
  `USER_EMAIL` varchar(256) NOT NULL,
  `PASSWORD` varchar(50) NOT NULL,
  `ROLE_ASSOCIATION_ID` int(11) NOT NULL,
  `FIRST_NAME` varchar(256) NOT NULL,
  `LAST_NAME` varchar(256) NOT NULL,
  `CONTACT_ASSOCICATION_ID` int(11) default NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL,
  `IMAGE` blob,
  `LAST_LOGIN` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default '0000-00-00 00:00:00',
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) default NULL,
  `UPDATED_BY` int(11) default NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`USER_ID`),
  UNIQUE KEY `USER_USER_EMAIL_UK` (`USER_EMAIL`),
  KEY `USER_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `USER_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  KEY `USER_ROLE_ASSOCIATION_ID_FK` (`ROLE_ASSOCIATION_ID`),
  KEY `USER_CONTACT_ASSOCICATION_ID_FK` (`CONTACT_ASSOCICATION_ID`),
  CONSTRAINT `USER_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `USER_CONTACT_ASSOCICATION_ID_FK` FOREIGN KEY (`CONTACT_ASSOCICATION_ID`) REFERENCES `contact` (`CONTACT_ID`),
  CONSTRAINT `USER_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  CONSTRAINT `USER_ROLE_ASSOCIATION_ID_FK` FOREIGN KEY (`ROLE_ASSOCIATION_ID`) REFERENCES `role` (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `user` */

insert  into `user`(`USER_ID`,`USER_EMAIL`,`PASSWORD`,`ROLE_ASSOCIATION_ID`,`FIRST_NAME`,`LAST_NAME`,`CONTACT_ASSOCICATION_ID`,`OUTLET_ASSOCICATION_ID`,`IMAGE`,`LAST_LOGIN`,`ACTIVE_INDICATOR`,`CREATED_DATE`,`LAST_UPDATED`,`CREATED_BY`,`UPDATED_BY`,`COMPANY_ASSOCIATION_ID`) values (1,'admin@meeras.com','123',1,'Admin','Admin',NULL,1,NULL,'2019-11-28 14:44:39','','2019-11-28 14:36:18','2019-11-28 14:36:18',1,1,1),(2,'manager@meeras.com','12345',2,'Manager','Manager',NULL,1,NULL,'2019-11-28 14:44:03','','2019-11-28 14:42:24','2019-11-28 14:42:24',1,1,1),(3,'cashier@meeras.com','12345',3,'Cashier','Cashier',NULL,1,NULL,'2019-11-28 14:44:28','','2019-11-28 14:43:08','2019-11-28 14:43:08',1,1,1);

/*Table structure for table `user_outlets` */

DROP TABLE IF EXISTS `user_outlets`;

CREATE TABLE `user_outlets` (
  `USER_OUTLETS_ID` int(11) NOT NULL auto_increment,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  `USER_ASSOCIATION_ID` int(11) NOT NULL,
  `OUTLET_ASSOCICATION_ID` int(11) default NULL,
  `ADDRESS_ASSOCICATION_ID` int(11) default NULL,
  `TIME_ZONE_ASSOCICATION_ID` int(11) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  PRIMARY KEY  (`USER_OUTLETS_ID`),
  KEY `USER_OUTLETS_CREATED_BY_FK` (`CREATED_BY`),
  KEY `USER_OUTLETS_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `USER_OUTLETS_USER_ASSOCIATION_ID_FK` (`USER_ASSOCIATION_ID`),
  KEY `USER_OUTLETS_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `USER_OUTLETS_OUTLET_ASSOCICATION_ID_FK` (`OUTLET_ASSOCICATION_ID`),
  CONSTRAINT `USER_OUTLETS_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `USER_OUTLETS_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `USER_OUTLETS_OUTLET_ASSOCICATION_ID_FK` FOREIGN KEY (`OUTLET_ASSOCICATION_ID`) REFERENCES `outlet` (`OUTLET_ID`),
  CONSTRAINT `USER_OUTLETS_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `USER_OUTLETS_USER_ASSOCIATION_ID_FK` FOREIGN KEY (`USER_ASSOCIATION_ID`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `user_outlets` */

insert  into `user_outlets`(`USER_OUTLETS_ID`,`COMPANY_ASSOCIATION_ID`,`USER_ASSOCIATION_ID`,`OUTLET_ASSOCICATION_ID`,`ADDRESS_ASSOCICATION_ID`,`TIME_ZONE_ASSOCICATION_ID`,`ACTIVE_INDICATOR`,`CREATED_DATE`,`LAST_UPDATED`,`CREATED_BY`,`UPDATED_BY`) values (1,1,1,1,NULL,NULL,'','2019-11-28 14:36:18','2019-11-28 14:36:18',1,1),(2,1,2,1,NULL,NULL,'','2019-11-28 14:42:24','2019-11-28 14:42:24',1,1),(3,1,3,1,NULL,NULL,'','2019-11-28 14:43:08','2019-11-28 14:43:08',1,1);

/*Table structure for table `variant_attribute` */

DROP TABLE IF EXISTS `variant_attribute`;

CREATE TABLE `variant_attribute` (
  `VARIANT_ATTRIBUTE_ID` int(11) NOT NULL auto_increment,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `VARIANT_ATTRIBUTEcol` varchar(45) default NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `VARIANT_ATTRIBUTE_ASSOCIATION_ID` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`VARIANT_ATTRIBUTE_ID`),
  UNIQUE KEY `unique_index` (`ATTRIBUTE_NAME`,`COMPANY_ASSOCIATION_ID`),
  KEY `VARIANT_ATTRIBUTE_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `VARIANT_ATTRIBUTE_CREATED_BY_FK` (`CREATED_BY`),
  KEY `VARIANT_ATTRIBUTE_UPDATED_BY_FK` (`UPDATED_BY`),
  CONSTRAINT `VARIANT_ATTRIBUTE_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `VARIANT_ATTRIBUTE_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `VARIANT_ATTRIBUTE_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `variant_attribute` */

/*Table structure for table `variant_attribute_values` */

DROP TABLE IF EXISTS `variant_attribute_values`;

CREATE TABLE `variant_attribute_values` (
  `VARIANT_ATTRIBUTE_VALUES_ID` int(11) NOT NULL auto_increment,
  `VARIANT_ATTRIBUTE_ASSOCICATION_ID` int(11) NOT NULL,
  `ATTRIBUTE_VALUE` varchar(200) NOT NULL,
  `PRODUCT_VARIANT_ASSOCICATION_ID` int(11) NOT NULL,
  `PRODUCT_ASSOCIATION_ID` int(11) NOT NULL,
  `PRODUCT_UUID` varchar(500) NOT NULL,
  `ACTIVE_INDICATOR` bit(1) NOT NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `LAST_UPDATED` timestamp NOT NULL default '0000-00-00 00:00:00',
  `CREATED_BY` int(11) NOT NULL,
  `UPDATED_BY` int(11) NOT NULL,
  `COMPANY_ASSOCIATION_ID` int(11) NOT NULL,
  PRIMARY KEY  (`VARIANT_ATTRIBUTE_VALUES_ID`),
  KEY `VARIANT_ATTRIBUTE_VALUES_COMPANY_ASSOCIATION_ID_FK` (`COMPANY_ASSOCIATION_ID`),
  KEY `VARIANT_ATTRIBUTE_VALUES_PRODUCT_VARIANT_ASSOCICATION_ID_FK` (`PRODUCT_VARIANT_ASSOCICATION_ID`),
  KEY `VARIANT_ATTRIBUTE_VALUES_CREATED_BY_FK` (`CREATED_BY`),
  KEY `VARIANT_ATTRIBUTE_VALUES_UPDATED_BY_FK` (`UPDATED_BY`),
  KEY `VARIANT_ATTRIBUTE_VALUES_PRODUCT_ASSOCIATION_ID_FK` (`PRODUCT_ASSOCIATION_ID`),
  KEY `VARIANT_ATTRIBUTE_VALUES_VARIANT_ATTRIBUTE_ASSOCICATION_ID_FK` (`VARIANT_ATTRIBUTE_ASSOCICATION_ID`),
  CONSTRAINT `VARIANT_ATTRIBUTE_VALUES_COMPANY_ASSOCIATION_ID_FK` FOREIGN KEY (`COMPANY_ASSOCIATION_ID`) REFERENCES `company` (`COMPANY_ID`),
  CONSTRAINT `VARIANT_ATTRIBUTE_VALUES_CREATED_BY_FK` FOREIGN KEY (`CREATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `VARIANT_ATTRIBUTE_VALUES_PRODUCT_ASSOCIATION_ID_FK` FOREIGN KEY (`PRODUCT_ASSOCIATION_ID`) REFERENCES `product` (`PRODUCT_ID`),
  CONSTRAINT `VARIANT_ATTRIBUTE_VALUES_PRODUCT_VARIANT_ASSOCICATION_ID_FK` FOREIGN KEY (`PRODUCT_VARIANT_ASSOCICATION_ID`) REFERENCES `product_variant` (`PRODUCT_VARIANT_ID`),
  CONSTRAINT `VARIANT_ATTRIBUTE_VALUES_UPDATED_BY_FK` FOREIGN KEY (`UPDATED_BY`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `VARIANT_ATTRIBUTE_VALUES_VARIANT_ATTRIBUTE_ASSOCICATION_ID_FK` FOREIGN KEY (`VARIANT_ATTRIBUTE_ASSOCICATION_ID`) REFERENCES `variant_attribute` (`VARIANT_ATTRIBUTE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `variant_attribute_values` */

/*Table structure for table `web_activity_detail` */

DROP TABLE IF EXISTS `web_activity_detail`;

CREATE TABLE `web_activity_detail` (
  `WEB_ACTIVITY_DETAIL_ID` int(11) NOT NULL auto_increment,
  `EMPLOYEE_ASSOCIATION_ID` int(11) default NULL,
  `EMPLOYEE_NAME` varchar(256) default NULL,
  `EMLOYEE_EMAIL` varchar(256) default NULL,
  `CREATED_BY_MANAGER_ID` int(11) default NULL,
  `ACTIVITY_DETAIL` varchar(256) default NULL,
  `CREATED_DATE` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `IP_ADDRESS` varchar(256) default NULL,
  `BROWSER_NAME` varchar(256) default NULL,
  `BROWSER_VERSION` varchar(256) default NULL,
  `OPERATING_SYSTEM` varchar(256) default NULL,
  `DEVICE_TYPE` varchar(256) default NULL,
  `SESSION_ID` varchar(256) default NULL,
  `SEVERITY_ASSOCIATION_ID` int(11) default NULL,
  `OTHER_INFORMATION` blob,
  `IS_EXCEPTION` varchar(10) default 'false',
  PRIMARY KEY  (`WEB_ACTIVITY_DETAIL_ID`),
  KEY `WEB_ACTIVITY_DETAIL_EMPLOYEE_ASSOCIATION_ID_FK` (`EMPLOYEE_ASSOCIATION_ID`),
  KEY `WEB_ACTIVITY_DETAIL_CREATTED_BY_MANAGER_ID_FK` (`CREATED_BY_MANAGER_ID`),
  KEY `WEB_ACTIVITY_DETAIL_SEVERITY_ASSOCIATION_ID_FK` (`SEVERITY_ASSOCIATION_ID`),
  CONSTRAINT `WEB_ACTIVITY_DETAIL_CREATTED_BY_MANAGER_ID_FK` FOREIGN KEY (`CREATED_BY_MANAGER_ID`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `WEB_ACTIVITY_DETAIL_EMPLOYEE_ASSOCIATION_ID_FK` FOREIGN KEY (`EMPLOYEE_ASSOCIATION_ID`) REFERENCES `user` (`USER_ID`),
  CONSTRAINT `WEB_ACTIVITY_DETAIL_SEVERITY_ASSOCIATION_ID_FK` FOREIGN KEY (`SEVERITY_ASSOCIATION_ID`) REFERENCES `severity` (`SEVERITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `web_activity_detail` */

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
