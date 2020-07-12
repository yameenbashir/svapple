use ecom;
DELIMITER $$

DROP PROCEDURE IF EXISTS `ecom`.`StockOrderActions`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `StockOrderActions`(stockOrderId int, outletId int)
BEGIN



CREATE TEMPORARY TABLE UUIDs (
select  
 pv.PRODUCT_VARIANT_UUID,so.OUTLET_ASSOCICATION_ID,so.SOURCE_OUTLET_ASSOCICATION_ID
from stock_order_detail sod
inner join stock_order so on so.STOCK_ORDER_ID = sod.STOCK_ORDER_ASSOCICATION_ID
inner join product_variant pv on pv.PRODUCT_VARIANT_ID =  sod.PRODUCT_VARIANT_ASSOCICATION_ID
inner join product prod on prod.PRODUCT_ID = pv.PRODUCT_ASSOCICATION_ID 
where STOCK_ORDER_ASSOCICATION_ID = stockOrderId );



select  
sod.STOCK_ORDER_DETAIL_ID,  
sod.STOCK_ORDER_ASSOCICATION_ID,
sod.PRODUCT_VARIANT_ASSOCICATION_ID,
sod.PRODUCT_ASSOCIATION_ID, 
prod.product_name,
concat(prod.product_name, '-', pv.variant_attribute_name) VariantAttributeName,
prod.current_inventory as product_current_inventory,
0 as product_destination_current_inventory,
pv.current_inventory as product_variant_current_inventory,
0 as product_variant_destination_inventory,
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
inner join stock_order so on so.STOCK_ORDER_ID = sod.STOCK_ORDER_ASSOCICATION_ID
inner join product_variant pv on pv.PRODUCT_VARIANT_ID =  sod.PRODUCT_VARIANT_ASSOCICATION_ID
inner join product prod on prod.PRODUCT_ID = pv.PRODUCT_ASSOCICATION_ID 
where STOCK_ORDER_ASSOCICATION_ID = stockOrderId and so.STOCK_ORDER_TYPE_ASSOCICATION_ID in (1,2) and so.OUTLET_ASSOCICATION_ID = outletId


union



select  
sod.STOCK_ORDER_DETAIL_ID,  
sod.STOCK_ORDER_ASSOCICATION_ID,
sod.PRODUCT_VARIANT_ASSOCICATION_ID,
sod.PRODUCT_ASSOCIATION_ID, 
prod.product_name,
concat(prod.product_name, '-', pv.variant_attribute_name) VariantAttributeName,
0 as product_current_inventory,
prod.current_inventory as product_destination_current_inventory,
0 as product_variant_current_inventory,
pv.current_inventory as product_variant_destination_inventory,
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
inner join stock_order so on so.STOCK_ORDER_ID = sod.STOCK_ORDER_ASSOCICATION_ID
inner join product_variant pv on pv.PRODUCT_VARIANT_ID =  sod.PRODUCT_VARIANT_ASSOCICATION_ID
inner join product prod on prod.PRODUCT_ID = pv.PRODUCT_ASSOCICATION_ID 
where STOCK_ORDER_ASSOCICATION_ID = stockOrderId and so.STOCK_ORDER_TYPE_ASSOCICATION_ID in (5) and so.OUTLET_ASSOCICATION_ID = outletId



union 



select  
sod.STOCK_ORDER_DETAIL_ID,  
sod.STOCK_ORDER_ASSOCICATION_ID,
sod.PRODUCT_VARIANT_ASSOCICATION_ID,
sod.PRODUCT_ASSOCIATION_ID, 
prod.product_name,
concat(prod.product_name, '-', pv.variant_attribute_name) VariantAttributeName,
source.Src_Product_Qty as product_current_inventory,
dest.dest_Product_Qty as product_destination_current_inventory,
source.Src_Product_Var_Qty as product_variant_current_inventory,
dest.dest_Product_Var_Qty as product_variant_destination_inventory,
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
inner join stock_order so on so.STOCK_ORDER_ID = sod.STOCK_ORDER_ASSOCICATION_ID
inner join product_variant pv on pv.PRODUCT_VARIANT_ID =  sod.PRODUCT_VARIANT_ASSOCICATION_ID
inner join product prod on prod.PRODUCT_ID = pv.PRODUCT_ASSOCICATION_ID 
left join (
select  coalesce(prod.CURRENT_INVENTORY,0) as Src_Product_Qty, coalesce(pv.CURRENT_INVENTORY,0) as Src_Product_Var_Qty , pv.PRODUCT_VARIANT_UUID 
from product prod
inner join product_variant pv on pv.PRODUCT_ASSOCICATION_ID =  prod.PRODUCT_ID 
inner join stock_order_detail sod on pv.PRODUCT_VARIANT_ID =  sod.PRODUCT_VARIANT_ASSOCICATION_ID 
inner join stock_order so on so.STOCK_ORDER_ID = sod.STOCK_ORDER_ASSOCICATION_ID 
and   STOCK_ORDER_ASSOCICATION_ID = stockOrderId
) source on source.PRODUCT_VARIANT_UUID = pv.PRODUCT_VARIANT_UUID

left join (
select  coalesce(prod.CURRENT_INVENTORY,0) as dest_Product_Qty, coalesce(pv.CURRENT_INVENTORY,0) as dest_Product_Var_Qty , pv.PRODUCT_VARIANT_UUID 
from product prod
inner join product_variant pv on pv.PRODUCT_ASSOCICATION_ID =  prod.PRODUCT_ID 
inner join UUIDs on pv.PRODUCT_VARIANT_UUID = UUIDs.PRODUCT_VARIANT_UUID
 and UUIDs.OUTLET_ASSOCICATION_ID = pv.OUTLET_ASSOCICATION_ID
) dest on dest.PRODUCT_VARIANT_UUID = pv.PRODUCT_VARIANT_UUID


where STOCK_ORDER_ASSOCICATION_ID = stockOrderId and so.STOCK_ORDER_TYPE_ASSOCICATION_ID in( 3,4) and so.OUTLET_ASSOCICATION_ID = outletId ;

DROP TEMPORARY TABLE UUIDs;
END$$
DELIMITER ;