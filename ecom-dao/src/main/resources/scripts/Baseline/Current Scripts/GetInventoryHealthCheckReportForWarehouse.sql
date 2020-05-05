DELIMITER $$

DROP PROCEDURE IF EXISTS `ecom`.`GetInventoryHealthCheckReportForWarehouse`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `GetInventoryHealthCheckReportForWarehouse`(companyId int, outletId  int, afterdate date)
BEGIN
 DECLARE v_LatestAuditId  int; 
 DECLARE v_LatestAuditDate datetime;
SET v_LatestAuditId = (select max(INVENTORY_COUNT_ID) FROM inventory_count WHERE COMPANY_ASSOCIATION_ID = companyId and OUTLET_ASSOCICATION_ID =outletId and LAST_UPDATED >= afterdate);
SET v_LatestAuditDate = afterdate; -- (select LAST_UPDATED FROM inventory_count WHERE INVENTORY_COUNT_ID = v_LatestAuditId);
select 
 
prd.PRODUCT_NAME, pv.VARIANT_ATTRIBUTE_NAME, outl.OUTLET_NAME, pv.SKU, cont.CONTACT_NAME as SUPPLIER,prod_typ.PRODUCT_TYPE_NAME,
coalesce(TempLastAudit.COUNTED_PROD_QTY,0) as AUDIT_QUANTITY,
 
coalesce(StockTransfertoOutlet.Received_from_WH,0)as STOCK_TRANSFERRED,
coalesce(ReturnWH.ReturnWH_Qty,0)as STOCK_RETURN_TO_WAREHOUSE,
 coalesce(sales.Sale_Qty,0)as SALE,
 coalesce(Return_sales.Sale_Qty,0)as SALE_RETURN,
(coalesce(TempLastAudit.COUNTED_PROD_QTY,0) + coalesce(StockTransfertoOutlet.Received_from_WH,0) +coalesce(Return_sales.Sale_Qty,0)) - 
(coalesce(ReturnWH.ReturnWH_Qty,0) + coalesce(sales.Sale_Qty,0)) as EXPECTED_CURRENT_INVENTORY, 
 coalesce(pv.CURRENT_INVENTORY,0)  as SYSTEM_CURRENT_INVENTORY, 
((coalesce(TempLastAudit.COUNTED_PROD_QTY,0) + coalesce(StockTransfertoOutlet.Received_from_WH,0) +coalesce(Return_sales.Sale_Qty,0)) - 
(coalesce(ReturnWH.ReturnWH_Qty,0) + coalesce(sales.Sale_Qty,0)) -  coalesce(pv.CURRENT_INVENTORY,0)) as CONFLICTED_QUANTITY
from product_variant pv 
left join product prd on prd.PRODUCT_ID = pv.PRODUCT_ASSOCICATION_ID
inner join outlet outl on outl.OUTLET_ID = prd.OUTLET_ASSOCICATION_ID
inner join contact cont on cont.CONTACT_ID = prd.CONTACT_ASSOCICATION_ID
inner join product_type prod_typ on prod_typ.PRODUCT_TYPE_ID = prd.PRODUCT_TYPE_ASSOCICATION_ID
left join (
select 
coalesce(lastAuditDet.COUNTED_PROD_QTY,0)as COUNTED_PROD_QTY,
pv.PRODUCT_VARIANT_UUID
from inventory_count lastAudit
inner join inventory_count_detail lastAuditDet on lastAudit.INVENTORY_COUNT_ID = lastAuditDet.INVENTORY_COUNT_ASSOCICATION_ID
inner join product_variant pv on pv.PRODUCT_VARIANT_ID = lastAuditDet.PRODUCT_VARIANT_ASSOCICATION_ID
inner join product prd on prd.PRODUCT_ID = pv.PRODUCT_ASSOCICATION_ID
where  lastAudit.INVENTORY_COUNT_ID =v_LatestAuditId
) TempLastAudit on TempLastAudit.PRODUCT_VARIANT_UUID = pv.PRODUCT_VARIANT_UUID
Left join (
select (select PRODUCT_VARIANT_UUID from product_variant where PRODUCT_VARIANT_ID = sod.PRODUCT_VARIANT_ASSOCICATION_ID and OUTLET_ASSOCICATION_ID = so.SOURCE_OUTLET_ASSOCICATION_ID) as PRODUCT_VARIANT_UUID, sum(coalesce(sod.ORDER_PROD_QTY,0))  as Received_from_WH , so.OUTLET_ASSOCICATION_ID
from stock_order so
inner join stock_order_detail sod on so.STOCK_ORDER_ID = sod.STOCK_ORDER_ASSOCICATION_ID  and (so.OUTLET_ASSOCICATION_ID = outletId)
and so.ACTIVE_INDICATOR = 1 and so.STATUS_ASSOCICATION_ID = 3 and STOCK_ORDER_TYPE_ASSOCICATION_ID = 3
and so.COMPANY_ASSOCIATION_ID = companyId 
 
where so.LAST_UPDATED >= v_LatestAuditDate   and so.REMARKS is null
group by PRODUCT_VARIANT_UUID
) StockTransfertoOutlet on StockTransfertoOutlet.PRODUCT_VARIANT_UUID = pv.PRODUCT_VARIANT_UUID  
left join (
select  pv.PRODUCT_VARIANT_UUID, sum(coalesce(sod.RECV_PROD_QTY,0)) as ReturnWH_Qty
from stock_order so inner join 
stock_order_detail sod on so.STOCK_ORDER_ID = sod.STOCK_ORDER_ASSOCICATION_ID
inner join product_variant pv on pv.PRODUCT_VARIANT_ID =  sod.PRODUCT_VARIANT_ASSOCICATION_ID
inner join product prod on prod.PRODUCT_ID = pv.PRODUCT_ASSOCICATION_ID
where  (so.SOURCE_OUTLET_ASSOCICATION_ID = outletid ) and so.COMPANY_ASSOCIATION_ID = companyId 
and so.ACTIVE_INDICATOR = 1 and so.STATUS_ASSOCICATION_ID = 3 and STOCK_ORDER_TYPE_ASSOCICATION_ID = 4
 and so.LAST_UPDATED >= v_LatestAuditDate 
group by pv.PRODUCT_VARIANT_UUID
) ReturnWH on ReturnWH.PRODUCT_VARIANT_UUID = pv.PRODUCT_VARIANT_UUID
left join (
select  pv.PRODUCT_VARIANT_UUID,
sum(coalesce(det.PRODUCT_QUANTITY,0))as Sale_Qty
  from invoice_main invc
inner join invoice_detail det on invc.INVOICE_MAIN_ID =det.INVOICE_MAIN_ASSOCICATION_ID  
and invc.COMPANY_ASSOCIATION_ID = companyId and ISRETURN = false
inner join outlet on outlet.OUTLET_ID = invc.OUTLET_ASSOCICATION_ID and (outlet.OUTLET_ID = outletId )
inner join product_variant pv on pv.PRODUCT_VARIANT_ID =  det.PRODUCT_VARIENT_ASSOCIATION_ID
inner join product prod on prod.PRODUCT_ID = pv.PRODUCT_ASSOCICATION_ID  
where   invc.LAST_UPDATED >= v_LatestAuditDate 
group by pv.PRODUCT_VARIANT_UUID
) sales on sales.PRODUCT_VARIANT_UUID = pv.PRODUCT_VARIANT_UUID
left join (
select  pv.PRODUCT_VARIANT_UUID,
sum( coalesce( det.PRODUCT_QUANTITY,0))as Sale_Qty
  from invoice_main invc
inner join invoice_detail det on invc.INVOICE_MAIN_ID =det.INVOICE_MAIN_ASSOCICATION_ID  
and invc.COMPANY_ASSOCIATION_ID = companyId  and ISRETURN = true
inner join outlet on outlet.OUTLET_ID = invc.OUTLET_ASSOCICATION_ID and (outlet.OUTLET_ID = outletId )
inner join product_variant pv on pv.PRODUCT_VARIANT_ID =  det.PRODUCT_VARIENT_ASSOCIATION_ID
inner join product prod on prod.PRODUCT_ID = pv.PRODUCT_ASSOCICATION_ID 
where   invc.LAST_UPDATED >= v_LatestAuditDate 
group by pv.PRODUCT_VARIANT_UUID
) Return_sales on Return_sales.PRODUCT_VARIANT_UUID = pv.PRODUCT_VARIANT_UUID 
where pv.OUTLET_ASSOCICATION_ID = outletId ;
END$$

DELIMITER ;