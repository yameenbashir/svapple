package com.dowhile.beans;

import java.util.Date;

public class ReportParams {
	
	
	
	int companyId;
	String tableName;
	String mainBaseColumn;
	String baseColumn;
	String pivotColumn;
	String tallyColumn;
	String printColumns;
	String whereClause;
	String orderBy;
	String reportDateType;
	String groupBy;
	Date startDate;
	Date endDate;
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getMainBaseColumn() {
		return mainBaseColumn;
	}
	public void setMainBaseColumn(String mainBaseColumn) {
		this.mainBaseColumn = mainBaseColumn;
	}
	public String getBaseColumn() {
		return baseColumn;
	}
	public void setBaseColumn(String baseColumn) {
		this.baseColumn = baseColumn;
	}
	public String getPivotColumn() {
		return pivotColumn;
	}
	public void setPivotColumn(String pivotColumn) {
		this.pivotColumn = pivotColumn;
	}
	public String getTallyColumn() {
		return tallyColumn;
	}
	public void setTallyColumn(String tallyColumn) {
		this.tallyColumn = tallyColumn;
	}
	public String getPrintColumns() {
		return printColumns;
	}
	public void setPrintColumns(String printColumns) {
		this.printColumns = printColumns;
	}
	public String getWhereClause() {
		return whereClause;
	}
	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getReportDateType() {
		return reportDateType;
	}
	public void setReportDateType(String reportDateType) {
		this.reportDateType = reportDateType;
	}
	public String getGroupBy() {
		return groupBy;
	}
	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
