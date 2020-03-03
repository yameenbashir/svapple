package com.dowhile.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataSynchCandela {

	 private static final String FILE_NAME = "C://Users//IBM_ADMIN//desktop//Products_kites.xlsx";

	    public static void main(String[] args) {

	        try {

	            FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
	            Workbook workbook = new XSSFWorkbook(excelFile);
	            Sheet datatypeSheet = workbook.getSheetAt(1);
	            Iterator<Row> iterator = datatypeSheet.iterator();

	            while (iterator.hasNext()) {

	                Row currentRow = iterator.next();
	        
	              
	                String suuplierName = currentRow.getCell(1).getStringCellValue();
	             
	        
	                System.out.println(suuplierName);

	            }
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();logger.error(e.getMessage(),e);
	        } catch (IOException e) {
	            e.printStackTrace();logger.error(e.getMessage(),e);
	        }

	    }
}
