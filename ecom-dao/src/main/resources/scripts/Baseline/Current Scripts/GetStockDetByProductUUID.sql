drop PROCEDURE IF EXISTS GetStockDetByProductUUID;

DELIMITER $$
 
CREATE PROCEDURE GetStockDetByProductUUID(
    
   IN  companyId INT,IN status_id int,IN stockOrderType int, IN productuuid varchar(50), IN startDate timestamp , IN endDate timestamp 
	
)
BEGIN

select  STOCK_REF_NO, sot.STOCK_ORDER_TYPE_DESC,
CASE
    WHEN so.SOURCE_OUTLET_ASSOCICATION_ID is null THEN cont.CONTACT_NAME
    ELSE olsrc.OUTLET_NAME
END

 as Source, olde.OUTLET_NAME as Destination  , prod.PRODUCT_NAME, pv.VARIANT_ATTRIBUTE_NAME, sod.order_prod_qty, sod.RECV_PROD_QTY

  from stock_order so
inner join stock_order_detail sod on so.STOCK_ORDER_ID = sod.STOCK_ORDER_ASSOCICATION_ID 
and so.ACTIVE_INDICATOR = 1 
and so.COMPANY_ASSOCIATION_ID = companyId and ((so.STATUS_ASSOCICATION_ID = status_id) or (status_id is null))
inner join stock_order_type sot on sot.STOCK_ORDER_TYPE_ID = so.STOCK_ORDER_TYPE_ASSOCICATION_ID
inner join product_variant pv on pv.PRODUCT_VARIANT_ID =  sod.PRODUCT_VARIANT_ASSOCICATION_ID
inner join product prod on prod.PRODUCT_ID = pv.PRODUCT_ASSOCICATION_ID
left join outlet olsrc on ((olsrc.OUTLET_ID = so.SOURCE_OUTLET_ASSOCICATION_ID))
inner join outlet olde on olde.OUTLET_ID = so.OUTLET_ASSOCICATION_ID
Left join contact cont on cont.CONTACT_ID = so.CONTACT_ID

where ((so.STOCK_ORDER_TYPE_ASSOCICATION_ID = stockOrderType) or (stockOrderType is null))

and ((productuuid is null)or (prod.PRODUCT_UUID = productuuid ));

END$$
 
DELIMITER ;