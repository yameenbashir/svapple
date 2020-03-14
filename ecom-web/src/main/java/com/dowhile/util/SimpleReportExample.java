/*package com.dowhile.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

public class SimpleReportExample {

  public static void main(String[] args) {
	Connection connection = null;
	try {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/ecom","root", "123456");
	} catch (SQLException e) {
		e.printStackTrace();// logger.error(e.getMessage(),e);
		return;
	} catch (ClassNotFoundException e) {
		e.printStackTrace();// logger.error(e.getMessage(),e);
		return;
	}

	JasperReportBuilder report = DynamicReports.report();//a new report
	report
	  .columns(
	      Columns.column("User Id", "USER_ID", DataTypes.integerType()),
	      Columns.column("Email", "USER_EMAIL", DataTypes.stringType()),
	      Columns.column("First Name", "FIRST_NAME", DataTypes.stringType()),
	      Columns.column("Created Date", "CREATED_DATE", DataTypes.dateType()))
	  .title(//title of the report
	      Components.text("Users Detail Report")
		  .setHorizontalAlignment(HorizontalAlignment.LEFT))
		  .pageFooter(Components.pageXofY())//show page number on the page footer
		  .setDataSource("SELECT USER_ID, USER_EMAIL, FIRST_NAME, CREATED_DATE FROM user", 
                                  connection);

	try {
        //show the report
		report.show();

        //export the report to a pdf file
		report.toPdf(new FileOutputStream("c:/report.pdf"));
	} catch (DRException e) {
		e.printStackTrace();// logger.error(e.getMessage(),e);
	} catch (FileNotFoundException e) {
		e.printStackTrace();// logger.error(e.getMessage(),e);
	}
  }
}

*/