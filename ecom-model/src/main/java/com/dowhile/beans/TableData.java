package com.dowhile.beans;

import java.util.List;



public class TableData {
	List<Row> rows;
	List<String> columns;
	Row footer;
	/**
	 * @return the rows
	 */
	
	/**
	 * @return the columns
	 */
	public List<String> getColumns() {
		return columns;
	}
	/**
	 * @param columns the columns to set
	 */
	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	/**
	 * @return the rows
	 */
	public List<Row> getRows() {
		return rows;
	}
	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<Row> rows) {
		this.rows = rows;
	}
	public Row getFooter() {
		return footer;
	}
	public void setFooter(Row footer) {
		this.footer = footer;
	}

}
