use ecom;
DELIMITER //
CREATE TRIGGER Product_after_insert
AFTER INSERT
   ON product FOR EACH ROW

BEGIN


  insert into `mv_inventory_report` (`ID`, `PRODUCT_NAME`, `SKU`, `SUPPLY_PRICE_EXCL_TAX`, `MARKUP_PRCT`, `REORDER_POINT`, `REORDER_AMOUNT`, `CURRENT_INVENTORY`, `NET_PRICE`, `OUTLET_NAME`, `OUTLET_ASSOCICATION_ID`, `CREATED_DATE`, `BRAND_NAME`, `PRODUCT_TYPE`, `CONTACT_NAME`, `COMPANY_ASSOCIATION_ID`, `STOCK_VALUE`, `RETAIL_VALUE`)
 select
      `product`.`PRODUCT_ID` AS `ID`,
      `product`.`PRODUCT_NAME` AS `PRODUCT_NAME`,
      `product`.`SKU` AS `SKU`,
      `product`.`SUPPLY_PRICE_EXCL_TAX` AS `SUPPLY_PRICE_EXCL_TAX`,
      `product`.`MARKUP_PRCT` AS `MARKUP_PRCT`,
      coalesce(`product`.`REORDER_POINT`, 0) AS `REORDER_POINT`,
      coalesce(`product`.`REORDER_AMOUNT`, 0) AS `REORDER_AMOUNT`,
      `product`.`CURRENT_INVENTORY` AS `CURRENT_INVENTORY`,
      (
((`product`.`SUPPLY_PRICE_EXCL_TAX` * `product`.`MARKUP_PRCT`) / 100) + `product`.`SUPPLY_PRICE_EXCL_TAX`
      )
      AS `NET_PRICE`,
      (
         select
            `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` 
         from
            `outlet` 
         where
            (
               `product`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`
            )
      )
      AS `OUTLET_NAME`,
      `product`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID`,
      `product`.`CREATED_DATE` AS `CREATED_DATE`,
      (
         select
            `brand`.`BRAND_NAME` AS `BRAND_NAME` 
         from
            `brand` 
         where
            (
               `product`.`BRAND_ASSOCICATION_ID` = `brand`.`BRAND_ID`
            )
      )
      AS `BRAND_NAME`,
      (
         select
            `product_type`.`PRODUCT_TYPE_NAME` AS `PRODUCT_TYPE_NAME` 
         from
            `product_type` 
         where
            (
               `product`.`PRODUCT_TYPE_ASSOCICATION_ID` = `product_type`.`PRODUCT_TYPE_ID`
            )
      )
      AS `PRODUCT_TYPE`,
      (
         select
            `contact`.`CONTACT_NAME` AS `CONTACT_NAME` 
         from
            `contact` 
         where
            (
               `product`.`CONTACT_ASSOCICATION_ID` = `contact`.`CONTACT_ID`
            )
      )
      AS `CONTACT_NAME`,
      `product`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`,
      (
         `product`.`SUPPLY_PRICE_EXCL_TAX` * `product`.`CURRENT_INVENTORY`
      )
      AS `STOCK_VALUE`,
      (
(((`product`.`SUPPLY_PRICE_EXCL_TAX` * `product`.`MARKUP_PRCT`) / 100) + `product`.`SUPPLY_PRICE_EXCL_TAX`) * `product`.`CURRENT_INVENTORY`
      )
      AS `RETAIL_VALUE` 
   from
      `product` 
   where
      (
(`product`.`VARIANT_PRODUCTS` = _latin1'false') 
         and 
         (
            `product`.`ACTIVE_INDICATOR` = 1 and product.product_id = New.product_id
         )
      )
   group by
      `product`.`PRODUCT_UUID`,
      `product`.`OUTLET_ASSOCICATION_ID`;





END; //

DELIMITER ;



