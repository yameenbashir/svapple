USE `ecom`;
ALTER TABLE stock_order
ADD  COLUMN TOTAL_ITEMS decimal(20,2)  Default 0.00 NOT NULL,
ADD  COLUMN  TOTAL_AMOUNT decimal(20,2)  Default 0.00 NOT NULL;

ALTER TABLE product 
ADD COLUMN attribute1 varchar(500),
ADD COLUMN attribute2 varchar(500),
ADD COLUMN attribute3 varchar(500);

use ecom;
update stock_order s set s.TOTAL_ITEMS = coalesce((select sum(ORDER_PROD_QTY) from stock_order_detail where 
STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STATUS_ASSOCICATION_ID = 1 and s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 1;

update stock_order s set s.TOTAL_AMOUNT = coalesce((select sum(ORDER_PROD_QTY * ORDR_SUPPLY_PRICE) from stock_order_detail 
where STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STATUS_ASSOCICATION_ID = 1 and s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 1;

update stock_order s set s.TOTAL_ITEMS = coalesce((select sum(ORDER_PROD_QTY) from stock_order_detail where 
STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STATUS_ASSOCICATION_ID = 2 and s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 1;

update stock_order s set s.TOTAL_AMOUNT = coalesce((select sum(ORDER_PROD_QTY * ORDR_SUPPLY_PRICE) 
from stock_order_detail where STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STATUS_ASSOCICATION_ID = 2 and s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 1;

update stock_order s set s.TOTAL_ITEMS = coalesce((select sum(ORDER_PROD_QTY) 
from stock_order_detail where STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STATUS_ASSOCICATION_ID = 8 and s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 1;

update stock_order s set s.TOTAL_AMOUNT = coalesce((select sum(ORDER_PROD_QTY * ORDR_SUPPLY_PRICE) 
from stock_order_detail where STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STATUS_ASSOCICATION_ID = 8 and s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 1;

update stock_order s set s.TOTAL_ITEMS = coalesce((select sum(RECV_PROD_QTY) from stock_order_detail 
where STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STATUS_ASSOCICATION_ID = 3 and s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 1;

update stock_order s set s.TOTAL_AMOUNT = coalesce((select sum(RECV_PROD_QTY * RECV_SUPPLY_PRICE) from stock_order_detail 
where STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STATUS_ASSOCICATION_ID = 3 and s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 1;

update stock_order s set s.TOTAL_ITEMS = coalesce((select sum(ORDER_PROD_QTY) from stock_order_detail 
where STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 2;

update stock_order s set s.TOTAL_AMOUNT = coalesce((select sum(ORDER_PROD_QTY * ORDR_SUPPLY_PRICE) from stock_order_detail 
where STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 2;

update stock_order s set s.TOTAL_ITEMS = coalesce((select sum(ORDER_PROD_QTY) from stock_order_detail 
where STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 3;

update stock_order s set s.TOTAL_AMOUNT = coalesce((select sum(ORDER_PROD_QTY * RETAIL_PRICE) from stock_order_detail 
where STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 3;

update stock_order s set s.TOTAL_ITEMS = coalesce((select sum(ORDER_PROD_QTY) from stock_order_detail 
where STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STATUS_ASSOCICATION_ID = 1 and s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 4;

update stock_order s set s.TOTAL_AMOUNT = coalesce((select sum(ORDER_PROD_QTY * RETAIL_PRICE) from stock_order_detail 
where STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STATUS_ASSOCICATION_ID = 1 and s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 4;

update stock_order s set s.TOTAL_ITEMS = coalesce((select sum(ORDER_PROD_QTY) from stock_order_detail 
where STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STATUS_ASSOCICATION_ID = 2 and s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 4;

update stock_order s set s.TOTAL_AMOUNT = coalesce((select sum(ORDER_PROD_QTY * RETAIL_PRICE) from stock_order_detail 
where STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STATUS_ASSOCICATION_ID = 2 and s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 4;

update stock_order s set s.TOTAL_ITEMS = coalesce((select sum(ORDER_PROD_QTY) from stock_order_detail 
where STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STATUS_ASSOCICATION_ID = 8 and s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 4;

update stock_order s set s.TOTAL_AMOUNT = coalesce((select sum(ORDER_PROD_QTY * RETAIL_PRICE) from stock_order_detail 
where STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STATUS_ASSOCICATION_ID = 8 and s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 4;

update stock_order s set s.TOTAL_ITEMS = coalesce((select sum(RECV_PROD_QTY) from stock_order_detail 
where STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STATUS_ASSOCICATION_ID = 3 and s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 4;

update stock_order s set s.TOTAL_AMOUNT = coalesce((select sum(RECV_PROD_QTY * RETAIL_PRICE) from stock_order_detail 
where STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STATUS_ASSOCICATION_ID = 3 and s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 4;

update stock_order s set s.TOTAL_ITEMS = coalesce((select sum(ORDER_PROD_QTY) from stock_order_detail 
where STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 5;

update stock_order s set s.TOTAL_AMOUNT = coalesce((select sum(ORDER_PROD_QTY * ORDR_SUPPLY_PRICE) from stock_order_detail 
where STOCK_ORDER_ASSOCICATION_ID = s.stock_order_id ),0)
where s.STOCK_ORDER_TYPE_ASSOCICATION_ID = 5;

insert into configuration (PROPERTY_NAME,PROPERTY_VALUE,COMPANY_ASSOCIATION_ID,CREATED_BY,UPDATED_BY,CREATED_DATE,LAST_UPDATED,ACTIVE_INDICATOR) values 
('STOCK_ORDER_COMPL_TABLE','true','1','1','1',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,1);