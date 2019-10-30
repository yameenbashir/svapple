SET sql_mode = '';
USE `ecom`;
DROP TABLE IF EXISTS `mv_Temp_Sale`;
CREATE TABLE mv_Temp_Sale(

Product 				varchar(500),
Outlet                  varchar(100),
CREATED_DATE            date,
Revenue                 decimal(57,6),
Revenue_tax_incl        decimal(58,6),
Cost_of_Goods           decimal(52,2),
Gross_Profit            decimal(53,2),
Margin                  decimal(50,6),
Items_Sold              decimal(33,0),
Tax                     decimal(42,2),
COMPANY_ASSOCIATION_ID  int(11),
OUTLET_ASSOCICATION_ID  int(11)
);

insert into mv_Temp_Sale (select * from Temp_Sale);
-- delete from mv_Temp_Sale;
-- insert into mv_Temp_Sale (select * from Temp_Sale);

DROP TABLE IF EXISTS `mv_credit_sale`;
CREATE TABLE mv_credit_sale(

product                       varchar(500),      
outlet                        varchar(100),
created_date                  date,
revenue                       decimal(57,6),
revenue_tax_incl              decimal(58,6),
cost_of_goods                 decimal(52,2),
gross_profit                  decimal(53,2),
margin                        decimal(50,6),
items_sold                    decimal(33,0),
tax                           decimal(42,2),
company_association_id        int(11),
outlet_assocication_id        int(11),
PAYMENT_TYPE_ASSOCICATION_ID  int(11)

);

insert into mv_credit_sale (select * from credit_sale);
-- delete from mv_credit_sale;
-- insert into mv_credit_sale (select * from credit_sale);

DROP TABLE IF EXISTS `mv_cash_sale`;
CREATE TABLE mv_cash_sale(

product                       varchar(500),        
outlet                        varchar(100),
created_date                  date,
revenue                       decimal(57,6),
revenue_tax_incl              decimal(58,6),
cost_of_goods                 decimal(52,2),
gross_profit                  decimal(53,2),
margin                        decimal(50,6),
items_sold                    decimal(33,0),
tax                           decimal(42,2),
company_association_id        int(11),
outlet_assocication_id        int(11),
PAYMENT_TYPE_ASSOCICATION_ID  int(11)
);

insert into mv_cash_sale (select * from cash_sale);
-- delete from mv_cash_sale;
-- insert into mv_cash_sale (select * from cash_sale);
DROP TABLE IF EXISTS `mv_Salereport_withsale`;
CREATE TABLE mv_Salereport_withsale(

Product                 varchar(500),
Outlet                  varchar(100),
CREATED_DATE            date,
ITEM_DISCOUNT_PRCT      decimal(20,2),
Revenue                 decimal(57,6),
Revenue_tax_incl        decimal(58,6),
Cost_of_Goods           decimal(52,2),
Gross_Profit            decimal(53,2),
Margin                  decimal(50,6),
Items_Sold              decimal(33,0),
Tax                     decimal(42,2),
COMPANY_ASSOCIATION_ID  int(11),
OUTLET_ASSOCICATION_ID  int(11)
);

insert into mv_Salereport_withsale (select * from Salereport_withsale);
-- delete from mv_Salereport_withsale;
-- insert into mv_Salereport_withsale (select * from Salereport_withsale);
DROP TABLE IF EXISTS `mv_SaleReport_withOutSale`;
CREATE TABLE mv_SaleReport_withOutSale(

Product                 varchar(500),
Outlet                  varchar(100),
CREATED_DATE            date,
Revenue                 decimal(57,6),
Revenue_tax_incl        decimal(58,6),
Cost_of_Goods           decimal(52,2),
Gross_Profit            decimal(53,2),
Margin                  decimal(50,6),
Items_Sold              decimal(33,0),
Tax                     decimal(42,2),
COMPANY_ASSOCIATION_ID  int(11),
OUTLET_ASSOCICATION_ID  int(11)
);

insert into mv_SaleReport_withOutSale (select * from SaleReport_withOutSale);
-- delete from mv_SaleReport_withOutSale;
-- insert into mv_SaleReport_withOutSale (select * from SaleReport_withOutSale);
DROP TABLE IF EXISTS `mv_Sale_Details`;
CREATE TABLE mv_Sale_Details(

Product                 varchar(500),
Variant                 varchar(500),
SKU                     varchar(500),
Outlet                  varchar(100),
CREATED_DATE            date,
Revenue                 decimal(52,2),
Revenue_tax_incl        decimal(53,2),
Cost_of_Goods           decimal(52,2),
Gross_Profit            decimal(53,2),
Margin                  decimal(50,6),
Items_Sold              decimal(33,0),
Tax                     decimal(42,2),
COMPANY_ASSOCIATION_ID  int(11),
OUTLET_ASSOCICATION_ID  int(11)
);

insert into mv_Sale_Details (select * from Sale_Details);
-- delete from mv_Sale_Details;
-- insert into mv_Sale_Details (select * from Sale_Details);
DROP TABLE IF EXISTS `mv_User_Sales_Report`;
CREATE TABLE mv_User_Sales_Report(

Product                 varchar(500),
User                    longtext,
Outlet                  varchar(100),
CREATED_DATE            date,
Revenue                 decimal(57,6),
Revenue_tax_incl        decimal(58,6),
Cost_of_Goods           decimal(52,2),
Gross_Profit            decimal(53,2),
Margin                  decimal(50,6),
Items_Sold              decimal(33,0),
Tax                     decimal(42,2),
COMPANY_ASSOCIATION_ID  int(11),
OUTLET_ASSOCICATION_ID  int(11)
);

insert into mv_User_Sales_Report (select * from User_Sales_Report);
-- delete from mv_User_Sales_Report;
-- insert into mv_User_Sales_Report (select * from User_Sales_Report);
DROP TABLE IF EXISTS `mv_Outlet_Sales_Report`;
CREATE TABLE mv_Outlet_Sales_Report(

Product                 varchar(500),
Outlet                  varchar(100),
CREATED_DATE            date,
Revenue                 decimal(57,6),
Revenue_tax_incl        decimal(58,6),
Cost_of_Goods           decimal(52,2),
Gross_Profit            decimal(53,2),
Margin                  decimal(50,6),
Items_Sold              decimal(32,0),
Tax                     decimal(42,2),
COMPANY_ASSOCIATION_ID  int(11),
OUTLET_ASSOCICATION_ID  int(11)
);

insert into mv_Outlet_Sales_Report (select * from Outlet_Sales_Report);
-- delete from mv_Outlet_Sales_Report;
-- insert into mv_Outlet_Sales_Report (select * from Outlet_Sales_Report);
DROP TABLE IF EXISTS `mv_Brand_Sales_Report`;
CREATE TABLE mv_Brand_Sales_Report(

Product                 varchar(500),
Outlet                  varchar(100),
Brand                   varchar(200),
CREATED_DATE            date,
Revenue                 decimal(57,6),
Revenue_tax_incl        decimal(58,6),
Cost_of_Goods           decimal(52,2),
Gross_Profit            decimal(53,2),
Margin                  decimal(50,6),
Items_Sold              decimal(32,0),
Tax                     decimal(42,2),
COMPANY_ASSOCIATION_ID  int(11),
OUTLET_ASSOCICATION_ID  int(11)

);

insert into mv_Brand_Sales_Report (select * from Brand_Sales_Report);
-- delete from mv_Brand_Sales_Report;
-- insert into mv_Brand_Sales_Report (select * from Brand_Sales_Report);
DROP TABLE IF EXISTS `mv_tag_sales_report`;
CREATE TABLE mv_tag_sales_report(

Outlet                  varchar(100),
Tag                     varchar(200),
CREATED_DATE            date,
Revenue                 decimal(52,2),
Revenue_tax_incl        decimal(53,2),
Cost_of_Goods           decimal(52,2),
Gross_Profit            decimal(53,2),
Margin                  decimal(50,6),
Items_Sold              decimal(32,0),
Tax                     decimal(42,2),
company_association_id  int(11),
outlet_assocication_id  int(11)

);

insert into mv_tag_sales_report (select * from tag_sales_report);
-- delete from mv_tag_sales_report;
-- insert into mv_tag_sales_report (select * from tag_sales_report);
DROP TABLE IF EXISTS `mv_ProductType_Sales_Report`;
CREATE TABLE mv_ProductType_Sales_Report(

Product                 varchar(500),
Outlet                  varchar(100),
ProductType             varchar(200),
CREATED_DATE            date,
Revenue                 decimal(57,6),
Revenue_tax_incl        decimal(58,6),
Cost_of_Goods           decimal(52,2),
Gross_Profit            decimal(53,2),
Margin                  decimal(50,6),
Items_Sold              decimal(32,0),
Tax                     decimal(42,2),
COMPANY_ASSOCIATION_ID  int(11),
OUTLET_ASSOCICATION_ID  int(11)
);

insert into mv_ProductType_Sales_Report (select * from ProductType_Sales_Report);
-- delete from mv_ProductType_Sales_Report;
-- insert into mv_ProductType_Sales_Report (select * from ProductType_Sales_Report);
DROP TABLE IF EXISTS `mv_Supplier_Sales_Report`;
CREATE TABLE mv_Supplier_Sales_Report(

Product                 varchar(500),
Outlet                  varchar(100),
Supplier                varchar(200),
CREATED_DATE            date,
Revenue                 decimal(57,6),
Revenue_tax_incl        decimal(58,6),
Cost_of_Goods           decimal(52,2),
Gross_Profit            decimal(53,2),
Margin                  decimal(50,6),
Items_Sold              decimal(32,0),
Tax                     decimal(42,2),
COMPANY_ASSOCIATION_ID  int(11),
OUTLET_ASSOCICATION_ID  int(11)
);

insert into mv_Supplier_Sales_Report (select * from Supplier_Sales_Report);
-- delete from mv_Supplier_Sales_Report;
-- insert into mv_Supplier_Sales_Report (select * from Supplier_Sales_Report);
DROP TABLE IF EXISTS `mv_Customer_Sales_Report`;
CREATE TABLE mv_Customer_Sales_Report(

Product                 varchar(500),
Outlet                  varchar(100),
Customer                varchar(200),
CREATED_DATE            date,
Revenue                 decimal(57,6),
Revenue_tax_incl        decimal(58,6),
Cost_of_Goods           decimal(52,2),
Gross_Profit            decimal(53,2),
Margin                  decimal(50,6),
Items_Sold              decimal(32,0),
Tax                     decimal(42,2),
COMPANY_ASSOCIATION_ID  int(11),
OUTLET_ASSOCICATION_ID  int(11)
);

insert into mv_Customer_Sales_Report (select * from Customer_Sales_Report);
-- delete from mv_Customer_Sales_Report;
-- insert into mv_Customer_Sales_Report (select * from Customer_Sales_Report);
DROP TABLE IF EXISTS `mv_Payment_Report`;
CREATE TABLE mv_Payment_Report(

OUTLET_ASSOCICATION_ID  int(11),
OUTLET                  varchar(100),
CREATED_DATE            date,
AMOUNT                  decimal(42,2),
COMPANY_ASSOCIATION_ID  int(11)
);

insert into mv_Payment_Report (select * from Payment_Report);
-- delete from mv_Payment_Report;
-- insert into mv_Payment_Report (select * from Payment_Report);
DROP TABLE IF EXISTS `mv_Customer_Group_Sales_Report`;
CREATE TABLE mv_Customer_Group_Sales_Report(

Product                 varchar(500),
Outlet                  varchar(100),
Group_Name              varchar(200),
CREATED_DATE            date,
Revenue                 decimal(57,6),
Revenue_tax_incl        decimal(58,6),
Cost_of_Goods           decimal(52,2),
Gross_Profit            decimal(53,2),
Margin                  decimal(50,6),
Items_Sold              decimal(32,0),
Tax                     decimal(42,2),
COMPANY_ASSOCIATION_ID  int(11),
OUTLET_ASSOCICATION_ID  int(11)
);

insert into mv_Customer_Group_Sales_Report (select * from Customer_Group_Sales_Report);
-- delete from mv_Customer_Group_Sales_Report;
-- insert into mv_Customer_Group_Sales_Report (select * from Customer_Group_Sales_Report);