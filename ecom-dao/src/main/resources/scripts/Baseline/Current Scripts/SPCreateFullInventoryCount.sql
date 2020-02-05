use ecom;
DELIMITER $$

DROP PROCEDURE IF EXISTS `ecom`.`CreateFullInventoryCount`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `CreateFullInventoryCount`(outletid int(20), inv_count_id int, userId int, cId int)
BEGIN
 
DECLARE v_finished INTEGER DEFAULT 0;
        DECLARE pvid int(20) DEFAULT 0;
	DECLARE c_invent int(20) DEFAULT 0;
        DECLARE supplyPrice decimal(20,2) DEFAULT 0.0;
        DECLARE retailPrice decimal(20,2) DEFAULT 0.0;
	DECLARE markup_prct decimal(12,5) DEFAULT 0.0;
	DECLARE V_PRICE_DIFF decimal(20,2) DEFAULT 0.0; 
	DECLARE V_COUNT_DIFF int(20) DEFAULT 0;
	DECLARE V_RETAIL_PRICE_EXP decimal(20,2) DEFAULT 0.0;
	DECLARE V_SUPPLY_PRICE_EXP decimal(20,2) DEFAULT 0.0;
	DECLARE V_EXPECTED_PROD_QTY decimal(20,2) DEFAULT 0.0;
	DECLARE V_COUNTED_PROD_QTY int(20) DEFAULT 0;
	DECLARE V_SUPPLY_PRICE_COUNTED decimal(20,2) DEFAULT 0.0;
	DECLARE V_RETAIL_PRICE_COUNTED decimal(20,2) DEFAULT 0.0;
 DEClARE outlet_products_curr CURSOR FOR 
 SELECT pv.product_variant_id, pv.CURRENT_INVENTORY, pv.SUPPLY_PRICE_EXCL_TAX, pv.MARKUP_PRCT FROM product_variant pv 
where pv.OUTLET_ASSOCICATION_ID = outletid and pv.CURRENT_INVENTORY > 0;
-- declare NOT FOUND handler
  DECLARE CONTINUE HANDLER 
       FOR NOT FOUND SET v_finished = 1;
 OPEN outlet_products_curr;
 
 get_pvid: LOOP
FETCH next from outlet_products_curr into pvid, c_invent, supplyPrice, markup_prct;
 -- FETCH outlet_products_curr INTO pvid;
 IF v_finished  THEN 
   LEAVE get_pvid;
END IF;
set retailPrice := 0.0;
set retailPrice := (supplyPrice * markup_prct/100) + supplyPrice;
-- select pvid, c_invent, retailPrice;
INSERT INTO INVENTORY_COUNT_DETAIL(INVENTORY_COUNT_ASSOCICATION_ID, PRODUCT_VARIANT_ASSOCICATION_ID, PRODUCT_ASSOCIATION_ID, IS_PRODUCT,
EXPECTED_PROD_QTY, SUPPLY_PRICE_EXP, RETAIL_PRICE_EXP, COUNTED_PROD_QTY, SUPPLY_PRICE_COUNTED, RETAIL_PRICE_COUNTED, COUNT_DIFF, PRICE_DIFF,
ACTIVE_INDICATOR, CREATED_DATE, LAST_UPDATED, CREATED_BY, UPDATED_BY, COMPANY_ASSOCIATION_ID, audit_transfer) VALUES
(inv_count_id,pvid, null, false, c_invent, supplyPrice * c_invent, retailPrice * c_invent, 0, 0.0, 0.0, c_invent, retailPrice, 
true, NOW(), NOW(), userId, userId, cId, 0);
END LOOP get_pvid;
 
CLOSE outlet_products_curr;
select sum(PRICE_DIFF), sum(COUNT_DIFF), sum(RETAIL_PRICE_EXP), sum(SUPPLY_PRICE_EXP), sum(EXPECTED_PROD_QTY), sum(COUNTED_PROD_QTY),
sum(SUPPLY_PRICE_COUNTED), sum(RETAIL_PRICE_COUNTED) into
V_PRICE_DIFF, V_COUNT_DIFF, V_RETAIL_PRICE_EXP, V_SUPPLY_PRICE_EXP, V_EXPECTED_PROD_QTY, V_COUNTED_PROD_QTY, V_SUPPLY_PRICE_COUNTED, 
V_RETAIL_PRICE_COUNTED from inventory_count_detail where INVENTORY_COUNT_ASSOCICATION_ID = inv_count_id;
UPDATE INVENTORY_COUNT set PRICE_DIFF = V_PRICE_DIFF,
COUNT_DIFF =  V_COUNT_DIFF,
RETAIL_PRICE_EXP = V_RETAIL_PRICE_EXP,
SUPPLY_PRICE_EXP = V_SUPPLY_PRICE_EXP,
EXPECTED_PROD_QTY = V_EXPECTED_PROD_QTY,
COUNTED_PROD_QTY = V_COUNTED_PROD_QTY,
SUPPLY_PRICE_COUNTED = V_SUPPLY_PRICE_COUNTED,
RETAIL_PRICE_COUNTED = V_RETAIL_PRICE_COUNTED,
LAST_UPDATED =  NOW(),
UPDATED_BY = userId WHERE INVENTORY_COUNT_ID = inv_count_id;
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
where icd.INVENTORY_COUNT_ASSOCICATION_ID = inv_count_id
and icd.COMPANY_ASSOCIATION_ID = cId;
 
END$$

DELIMITER ;