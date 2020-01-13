use ecom;
DELIMITER $$

DROP PROCEDURE IF EXISTS `ecom`.`InventoryCountActions`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `InventoryCountActions`(inventoryCountId int, companyId int)
BEGIN
select  
icd.INVENTORY_COUNT_DETAIL_ID,  
icd.INVENTORY_COUNT_ASSOCICATION_ID,
icd.PRODUCT_VARIANT_ASSOCICATION_ID,
icd.PRODUCT_ASSOCIATION_ID, 
p.product_name,
concat(ppv.product_name, '-', pv.variant_attribute_name) VariantAttributeName,
icd.IS_PRODUCT,                       
icd.EXPECTED_PROD_QTY,                   
icd.SUPPLY_PRICE_EXP,                
icd.RETAIL_PRICE_EXP,                    
icd.COUNTED_PROD_QTY,                
icd.RETAIL_PRICE_COUNTED,
icd.SUPPLY_PRICE_COUNTED, 
icd.COUNT_DIFF,
icd.PRICE_DIFF,                    
icd.ACTIVE_INDICATOR,                 
icd.CREATED_DATE,                     
icd.LAST_UPDATED,                     
icd.CREATED_BY,                       
icd.UPDATED_BY,                       
icd.COMPANY_ASSOCIATION_ID,
icd.audit_transfer
from inventory_count_detail icd
left join product p on p.product_id = icd.PRODUCT_ASSOCIATION_ID
left join product_variant pv on pv.product_variant_id = icd.PRODUCT_VARIANT_ASSOCICATION_ID
left join product ppv on ppv.product_id = pv.product_assocication_id
where icd.INVENTORY_COUNT_ASSOCICATION_ID = inventoryCountId
and icd.COMPANY_ASSOCIATION_ID = companyId;	
END$$

DELIMITER ;