use ecom;
SET @contactId := 33;
SET @outletId := 6;

SET SQL_SAFE_UPDATES = 0;
update contact set CONTACT_BALANCE = 0 where  CONTACT_ID = @contactId;

DELETE  from stock_order_detail where PRODUCT_VARIANT_ASSOCICATION_ID in (

select PRODUCT_VARIANT_ID from product_variant where OUTLET_ASSOCICATION_ID  = @outletId
);
delete from stock_order_detail where STOCK_ORDER_ASSOCICATION_ID in (

select STOCK_ORDER_ID from stock_order where OUTLET_ASSOCICATION_ID  = @outletId
);
delete from stock_order where OUTLET_ASSOCICATION_ID  = @outletId OR SOURCE_OUTLET_ASSOCICATION_ID  = @outletId;


delete from product_history where OUTLET_ASSOCICATION_ID  = @outletId;

delete from product_history where OUTLET_ASSOCICATION_ID  = @outletId;

delete from inventory_count_detail where INVENTORY_COUNT_ASSOCICATION_ID  in (
select INVENTORY_COUNT_ID  from inventory_count where OUTLET_ASSOCICATION_ID = @outletId
);

delete from price_book_detail  where PRODUCT_ASSOCICATION_ID  in(
 select PRODUCT_ID from product where OUTLET_ASSOCICATION_ID  = @outletId
);

delete from product_variant where PRODUCT_ASSOCICATION_ID in
(select PRODUCT_ID from product where OUTLET_ASSOCICATION_ID  = @outletId);


delete from product where OUTLET_ASSOCICATION_ID  = @outletId;


SET SQL_SAFE_UPDATES = 1;