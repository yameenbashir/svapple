use ecom;
DELIMITER $$

CREATE TRIGGER Product_Varient_after_insert
AFTER INSERT
   ON product_variant FOR EACH ROW

BEGIN


  insert into `mv_inventory_report` (`ID`, `PRODUCT_NAME`, `SKU`, `SUPPLY_PRICE_EXCL_TAX`, `MARKUP_PRCT`, `REORDER_POINT`, `REORDER_AMOUNT`, `CURRENT_INVENTORY`, `NET_PRICE`, `OUTLET_NAME`, `OUTLET_ASSOCICATION_ID`, `CREATED_DATE`, `BRAND_NAME`, `PRODUCT_TYPE`, `CONTACT_NAME`, `COMPANY_ASSOCIATION_ID`, `STOCK_VALUE`, `RETAIL_VALUE`)
 


select
   `product_variant`.`PRODUCT_VARIANT_ID` AS `PRODUCT_VARIANT_ID`, concat((
   select
      `product`.`PRODUCT_NAME` AS `PRODUCT_NAME` 
   from
      `product` 
   where
      (
         `product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`
      )
), _latin1' ', `product_variant`.`VARIANT_ATTRIBUTE_NAME`) AS `VARIANT_ATTRIBUTE_NAME`, `product_variant`.`SKU` AS `SKU`, `product_variant`.`SUPPLY_PRICE_EXCL_TAX` AS `SUPPLY_PRICE_EXCL_TAX`, `product_variant`.`MARKUP_PRCT` AS `MARKUP_PRCT`, coalesce(`product_variant`.`REORDER_POINT`, 0) AS `COALESCE(REORDER_POINT, 0)`, 
coalesce(`product_variant`.`REORDER_AMOUNT`, 0) AS `COALESCE(REORDER_AMOUNT, 0)`, `product_variant`.`CURRENT_INVENTORY` AS `CURRENT_INVENTORY`, 
      (
((`product_variant`.`SUPPLY_PRICE_EXCL_TAX` * `product_variant`.`MARKUP_PRCT`) / 100) + `product_variant`.`SUPPLY_PRICE_EXCL_TAX`
      )
      AS `NET_PRICE`, 
      (
         select
            `outlet`.`OUTLET_NAME` AS `OUTLET_NAME` 
         from
            `outlet` 
         where
            (
               `product_variant`.`OUTLET_ASSOCICATION_ID` = `outlet`.`OUTLET_ID`
            )
      )
      AS `OUTLET_NAME`, `product_variant`.`OUTLET_ASSOCICATION_ID` AS `OUTLET_ASSOCICATION_ID`, `product_variant`.`CREATED_DATE` AS `CREATED_DATE`, 
      (
         select
            `brand`.`BRAND_NAME` AS `BRAND_NAME` 
         from
            `brand` 
         where
            (
               `brand`.`BRAND_ID` = 
               (
                  select
                     `product`.`BRAND_ASSOCICATION_ID` AS `BRAND_ASSOCICATION_ID` 
                  from
                     `product` 
                  where
                     (
                        `product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`
                     )
               )
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
               `product_type`.`PRODUCT_TYPE_ID` = 
               (
                  select
                     `product`.`PRODUCT_TYPE_ASSOCICATION_ID` AS `PRODUCT_TYPE_ASSOCICATION_ID` 
                  from
                     `product` 
                  where
                     (
                        `product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`
                     )
               )
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
               `contact`.`CONTACT_ID` = 
               (
                  select
                     `product`.`CONTACT_ASSOCICATION_ID` AS `CONTACT_ASSOCICATION_ID` 
                  from
                     `product` 
                  where
                     (
                        `product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`
                     )
               )
            )
      )
      AS `CONTACT_NAME`, `product_variant`.`COMPANY_ASSOCIATION_ID` AS `COMPANY_ASSOCIATION_ID`, 
      (
         `product_variant`.`SUPPLY_PRICE_EXCL_TAX` * `product_variant`.`CURRENT_INVENTORY`
      )
      AS `STOCK_VALUE`, 
      (
(((`product_variant`.`SUPPLY_PRICE_EXCL_TAX` * `product_variant`.`MARKUP_PRCT`) / 100) + `product_variant`.`SUPPLY_PRICE_EXCL_TAX`) * `product_variant`.`CURRENT_INVENTORY`
      )
      AS `RETAIL_VALUE` 
   from
      `product_variant` 
	  inner join product on `product_variant`.`PRODUCT_ASSOCICATION_ID` = `product`.`PRODUCT_ID`
   where
      (
         `product_variant`.`ACTIVE_INDICATOR` = 1 and product_variant.PRODUCT_VARIANT_ID = New.PRODUCT_VARIANT_ID
      )
   group by
      `product_variant`.`PRODUCT_VARIANT_UUID`, `product_variant`.`OUTLET_ASSOCICATION_ID`;





END; $$

DELIMITER ;



