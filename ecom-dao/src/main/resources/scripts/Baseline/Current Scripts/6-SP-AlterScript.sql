USE `ecom`;
DELIMITER $$

DROP PROCEDURE IF EXISTS `ecom`.`Pivot`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Pivot`(
    IN tbl_name VARCHAR(100),       -- table name (or db.tbl)
    IN main_base_cols VARCHAR(500),      -- main base columns
    IN base_cols VARCHAR(500),      -- column(s) on the left, separated by commas
    IN pivot_col VARCHAR(100),      -- name of column to put across the top
    IN tally_col VARCHAR(500),      -- name of column to SUM up
    IN where_clause VARCHAR(500),   -- empty string or "WHERE ..."
    IN order_by VARCHAR(500),        -- empty string or "ORDER BY ..."; usually the base_cols
    IN group_by VARCHAR(500)        -- empty string or "ORDER BY ..."; usually the base_cols
)
    DETERMINISTIC
    SQL SECURITY INVOKER
BEGIN
 -- Find the distinct values
    -- Build the SUM()s
    SET @subq = CONCAT('SELECT DISTINCT ', pivot_col, ' AS val ',
                    ' FROM ', tbl_name, ' ', where_clause, ' ORDER BY 1');
    -- select @subq;
    SET @cc1 = "CONCAT('SUM(IF(&p = ', &v, ', &t, 0)) AS ', &v)";
    SET @cc2 = REPLACE(@cc1, '&p', pivot_col);
    SET @cc3 = REPLACE(@cc2, '&t', tally_col);
    -- select @cc2, @cc3;
    SET @qval = CONCAT("'\"', val, '\"'");
    -- select @qval;
    SET @cc4 = REPLACE(@cc3, '&v', @qval);
    -- select @cc4;
    SET SESSION group_concat_max_len = 18446744073709551615;   -- just in case
    SET @stmt = CONCAT(
            'SELECT  GROUP_CONCAT(', @cc4, ' SEPARATOR ",\n")  INTO @sums',
            ' FROM ( ', @subq, ' ) AS top');
    -- select @stmt;
   -- select @sums;
    PREPARE _sql FROM @stmt;
    EXECUTE _sql;                      -- Intermediate step: build SQL for columns
    DEALLOCATE PREPARE _sql;
    -- Construct the query and perform it
    SET @stmt2 = CONCAT(
            'SELECT ',
                main_base_cols, ',\n',
                @sums, ',\n',base_cols,
            '\n FROM ', tbl_name, ' ',
            where_clause,
            ' GROUP BY ', group_by , ' WITH ROLLUP',
            '\n', order_by
        );
    --  select @stmt2;                    -- The statement that generates the result
    PREPARE _sql FROM @stmt2;
    EXECUTE _sql;                     -- The resulting pivot table ouput
    DEALLOCATE PREPARE _sql;
    -- For debugging / tweaking, SELECT the various @variables after CALLing.
    END$$

DELIMITER ;



DELIMITER $$

DROP PROCEDURE IF EXISTS `ecom`.`Pivot_Summarize`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `Pivot_Summarize`(
    IN tbl_name VARCHAR(100),       -- table name (or db.tbl)
    IN main_base_cols VARCHAR(500),      -- main base columns
    IN base_cols VARCHAR(500),      -- column(s) on the left, separated by commas
    IN pivot_col VARCHAR(100),      -- name of column to put across the top
    IN tally_col VARCHAR(500),      -- name of column to SUM up
    IN where_clause VARCHAR(500),   -- empty string or "WHERE ..."
    IN order_by VARCHAR(500),        -- empty string or "ORDER BY ..."; usually the base_cols
    IN group_by VARCHAR(500),        -- empty string or "group BY ..."; usually the base_cols
    IN summarized_criteria VARCHAR(100)        -- summarized criteria e.g. "weekly" ,"monthly", "yearly"
)
    DETERMINISTIC
    SQL SECURITY INVOKER
BEGIN
 -- Find the distinct values
    -- Build the SUM()s
	SET @selectFormat = '';
	-- SET @groupbyFormat = '';
    IF summarized_criteria = 'monthly' THEN SET @selectFormat = '%b-%y';
    ELSEIF summarized_criteria = 'yearly' THEN SET @selectFormat = '%Y';
    ELSEIF summarized_criteria = 'weekly' THEN SET @selectFormat = '%e-%b-%y';
    END IF;
    IF summarized_criteria = 'weekly' 
	THEN SET @subq = CONCAT('SELECT DISTINCT ', 'DATE_FORMAT(DATE_ADD(',pivot_col,',','INTERVAL(1-DAYOFWEEK(',pivot_col,')) DAY),' ,"'", @selectFormat,"'",')'  , ' AS val ',
                    ' FROM ', tbl_name, ' ', where_clause, ' Group by ','DATE_FORMAT(',pivot_col,',',"'" , @selectFormat,"'" ,')', ' ORDER BY ',pivot_col);
    ELSEIF summarized_criteria = 'quaterly' 
	THEN 
	Set @quaterlySelectCriteria = CONCAT('CONCAT(', 'YEAR(',pivot_col,'),','''-''',',QUARTER(',pivot_col,')'  ,')');
	Set @quaterlyGroupbyCriteria = CONCAT('YEAR(',pivot_col,'),', 'QUARTER(',pivot_col,')' );
	SET @subq = CONCAT('SELECT DISTINCT ',@quaterlySelectCriteria  , ' AS val ',
                    ' FROM ', tbl_name, ' ', where_clause, ' Group by ',@quaterlyGroupbyCriteria, ' ORDER BY ',@quaterlyGroupbyCriteria);
    ELSE
     SET @subq = CONCAT('SELECT DISTINCT ', 'DATE_FORMAT(',pivot_col,',', "'", @selectFormat,"'",')'  , ' AS val ',
                    ' FROM ', tbl_name, ' ', where_clause, ' Group by ','DATE_FORMAT(',pivot_col,',',"'" , @selectFormat,"'" ,')', ' ORDER BY 1');
	 END IF;
   -- select @subq;
    IF summarized_criteria = 'weekly' 
	THEN
		SET @updatedPivotColumn = CONCAT('DATE_FORMAT(DATE_ADD(',pivot_col,',','INTERVAL(1-DAYOFWEEK(',pivot_col,')) DAY),' ,"''", @selectFormat,"''",')');
	ELSEIF summarized_criteria = 'quaterly' 
	THEN
		SET @updatedPivotColumn = CONCAT('CONCAT(', 'YEAR(',pivot_col,'),',"''-''",',QUARTER(',pivot_col,')'  ,')');
    ELSE
	SET @updatedPivotColumn = CONCAT('DATE_FORMAT(',pivot_col,',', "''", @selectFormat,"''",')');
    END IF;
    -- select @updatedPivotColumn;
    SET @cc1 = "CONCAT('SUM(IF(&p = ', &v, ', &t, 0)) AS ', &v)";
   -- select @cc1;
    SET @cc2 = REPLACE(@cc1, '&p' ,@updatedPivotColumn );
    -- select @cc1, @cc2;
    SET @cc3 = REPLACE(@cc2, '&t', tally_col);
    -- select @cc2, @cc3;
    SET @qval = CONCAT("'\"', val, '\"'");
    -- select @qval;
    SET @cc4 = REPLACE(@cc3, '&v', @qval);
    -- select @cc4;
    SET SESSION group_concat_max_len = 18446744073709551615;   -- just in case
    SET @stmt = CONCAT(
            'SELECT  GROUP_CONCAT(', @cc4, ' SEPARATOR ",\n")  INTO @sums',
            ' FROM ( ', @subq, ' ) AS top');
   -- select @stmt;
    -- select @sums;
    PREPARE _sql FROM @stmt;
    EXECUTE _sql;                      -- Intermediate step: build SQL for columns
    DEALLOCATE PREPARE _sql;
    -- Construct the query and perform it
    SET @stmt2 = CONCAT(
            'SELECT  ',
                main_base_cols, ',\n',
                @sums, ',\n',base_cols,
            '\n FROM ', tbl_name, ' ',
            where_clause,
           ' GROUP BY ', group_by, ' WITH ROLLUP',
           -- ' GROUP BY ', group_by, ',',@updatedPivotColumn , ' WITH ROLLUP',
            '\n', order_by
        );
   --  select @stmt2;                    -- The statement that generates the result
    PREPARE _sql FROM @stmt2;
    EXECUTE _sql;                     -- The resulting pivot table ouput
    DEALLOCATE PREPARE _sql;
    -- For debugging / tweaking, SELECT the various @variables after CALLing.
    END$$

DELIMITER ;