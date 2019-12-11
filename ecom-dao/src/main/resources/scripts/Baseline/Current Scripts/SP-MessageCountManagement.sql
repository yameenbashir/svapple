USE `ecom`;
DELIMITER $$

DROP PROCEDURE IF EXISTS `ecom`.`MessageCountManagement`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `MessageCountManagement`(companyid int)
    DETERMINISTIC
    SQL SECURITY INVOKER
BEGIN
	SET sql_mode = '';
	update message set MESSAGE_TEXT_LIMIT = ( select count(*) from message_detail where COMPANY_ASSOCIATION_ID = companyid and DELIVERY_ID <> 'Error : Incorrect Number') where COMPANY_ASSOCIATION_ID=companyid;
    END$$

DELIMITER ;