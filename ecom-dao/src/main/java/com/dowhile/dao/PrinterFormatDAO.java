/**
 * 
 */
package com.dowhile.dao;

import java.util.List;

import com.dowhile.PrinterFormat;

/**
 * @author Hassan
 *
 */
public interface PrinterFormatDAO {
	
	List<PrinterFormat> GetAllPrinterFormats();
	
	PrinterFormat GetPrinterFormatByID(int printerFormatId);
}
