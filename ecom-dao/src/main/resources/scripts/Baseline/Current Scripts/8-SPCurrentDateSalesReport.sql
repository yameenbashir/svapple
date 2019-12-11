use ecom;
DROP PROCEDURE IF EXISTS GetTodaySalesByProduct;
DELIMITER $$
CREATE PROCEDURE GetTodaySalesByProduct(companyId int, outletId  int, todayDate date)
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



END $$
DELIMITER ;