USE `ecom`;
DELIMITER $$

DROP PROCEDURE IF EXISTS `ecom`.`MVExecution`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `MVExecution`(
    
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
	delete from mv_inventory_report;
	insert into mv_inventory_report (select * from inventory_report);
    END$$

DELIMITER ;