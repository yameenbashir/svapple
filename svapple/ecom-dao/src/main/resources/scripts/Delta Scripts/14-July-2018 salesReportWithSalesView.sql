USE `ecom`;
DELIMITER $$

DROP VIEW IF EXISTS `ecom`.`salereport_withsale`$$

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `salereport_withsale` 
AS select (select `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` from `product` where (`invoice_detail`.`PRODUCT_ASSOCIATION_ID` = `product`.`PRODUCT_ID`)) AS `Product`,
(select `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` from `outlet` where (`invoice_main`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`)) AS `Outlet`,
cast(`invoice_main`.`CREATED_DATE` as date) AS `CREATED_DATE`,ITEM_DISCOUNT_PRCT,
sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) AS `Revenue`,
(sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0) / (select count(`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`) AS `count(``invoice_detail``.``INVOICE_MAIN_ASSOCICATION_ID``)` from `invoice_detail` where (`invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID` = `invoice_main`.`INVOICE_MAIN_ID`))))) + sum((`invoice_detail`.`ITEM_TAX_AMOUNT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Revenue_tax_incl`,
sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`)) AS `Cost_of_Goods`,(sum((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`)) - sum((`invoice_detail`.`ITEM_ORIGNAL_AMT` * `invoice_detail`.`PRODUCT_QUANTITY`))) AS `Gross_Profit`,coalesce((100 - ((sum(`invoice_detail`.`ITEM_ORIGNAL_AMT`) / sum(`invoice_detail`.`ITEM_SALE_PRICE`)) * 100)),0) AS `Margin`,
sum(`invoice_detail`.`PRODUCT_QUANTITY`) AS `Items_Sold`,sum(`invoice_detail`.`ITEM_TAX_AMOUNT`) AS `Tax`,`invoice_main`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,`invoice_main`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID` from (`invoice_detail` join `invoice_main` on((`invoice_main`.`INVOICE_MAIN_ID` = `invoice_detail`.`INVOICE_MAIN_ASSOCICATION_ID`))) where (((`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) 
and (`invoice_detail`.`ITEM_DISCOUNT_PRCT` > 0)) or ((`invoice_main`.`STATUS_ASSOCICATION_ID` <> 11) 
and (`invoice_main`.`INVOICE_DISCOUNT` > 0))) 
group by `invoice_detail`.`PRODUCT_ASSOCIATION_ID`,cast(`invoice_main`.`CREATED_DATE` as date) 
order by cast(`invoice_main`.`CREATED_DATE` as date)$$

DELIMITER ;