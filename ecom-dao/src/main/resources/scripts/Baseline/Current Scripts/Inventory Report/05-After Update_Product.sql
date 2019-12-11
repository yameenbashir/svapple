use ecom;

DELIMITER $$
 
CREATE TRIGGER after_product_update
AFTER UPDATE
ON product FOR EACH ROW
BEGIN
   
            update `mv_inventory_report`  set CURRENT_INVENTORY = new.CURRENT_INVENTORY,NET_PRICE =(((`New`.`SUPPLY_PRICE_EXCL_TAX` * `New`.`MARKUP_PRCT`) / 100) + `New`.`SUPPLY_PRICE_EXCL_TAX`) ,
STOCK_VALUE =(`New`.`SUPPLY_PRICE_EXCL_TAX` * `New`.`CURRENT_INVENTORY`),  SUPPLY_PRICE_EXCL_TAX = `New`.`SUPPLY_PRICE_EXCL_TAX`, MARKUP_PRCT = New.MARKUP_PRCT,

 RETAIL_VALUE = ((((`New`.`SUPPLY_PRICE_EXCL_TAX` * `New`.`MARKUP_PRCT`) / 100) + `New`.`SUPPLY_PRICE_EXCL_TAX`) * `New`.`CURRENT_INVENTORY`),
 PRODUCT_NAME = New.PRODUCT_NAME

   where id = new.product_id and (`New`.`VARIANT_PRODUCTS` = _latin1'false'); 
    
END$$
 
DELIMITER ;