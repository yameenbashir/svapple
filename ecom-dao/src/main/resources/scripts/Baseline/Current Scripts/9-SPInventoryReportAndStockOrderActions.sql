use ecom;
DELIMITER $$

DROP PROCEDURE IF EXISTS `ecom`.`MVInventoryExecution`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `MVInventoryExecution`(
    
)
    DETERMINISTIC
    SQL SECURITY INVOKER
BEGIN
	SET sql_mode = '';
	delete from mv_inventory_report;
	insert into mv_inventory_report (select * from inventory_report);
    END$$

DELIMITER ;

DELIMITER $$
DROP PROCEDURE IF EXISTS `ecom`.`StockOrderActions`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `StockOrderActions`(stockOrderId int, outletId int)
BEGIN
select  
sod.STOCK_ORDER_DETAIL_ID,  
sod.STOCK_ORDER_ASSOCICATION_ID,
sod.PRODUCT_VARIANT_ASSOCICATION_ID,
sod.PRODUCT_ASSOCIATION_ID, 
p.product_name,
concat(ppv.product_name, '-', pv.variant_attribute_name) VariantAttributeName,
p.current_inventory product_current_inventory,
pt.current_inventory product_destination_current_inventory,
pv.current_inventory product_variant_current_inventory,
pvt.current_inventory product_variant_destination_inventory,
sod.IS_PRODUCT,                       
sod.ORDER_PROD_QTY,                   
sod.ORDR_SUPPLY_PRICE,                
sod.RECV_PROD_QTY,                    
sod.RECV_SUPPLY_PRICE,                
sod.RETAIL_PRICE,                     
sod.ACTIVE_INDICATOR,                 
sod.CREATED_DATE,                     
sod.LAST_UPDATED,                     
sod.CREATED_BY,                       
sod.UPDATED_BY,                       
sod.COMPANY_ASSOCIATION_ID
from stock_order_detail sod
left join product p on p.product_id = sod.PRODUCT_ASSOCIATION_ID
left join product_variant pv on pv.product_variant_id = sod.PRODUCT_VARIANT_ASSOCICATION_ID
left join product ppv on ppv.product_id = pv.product_assocication_id
left join product_variant pvt on pvt.product_variant_uuid = pv.product_variant_uuid and pvt.outlet_assocication_id = outletId
left join product pt on pt.product_uuid = p.product_uuid and pt.outlet_assocication_id = outletId
where STOCK_ORDER_ASSOCICATION_ID = stockOrderId;	
END$$

DELIMITER ;