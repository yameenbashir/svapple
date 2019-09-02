/**
 * 
 */
package com.dowhile.service;

import java.util.List;

import com.dowhile.Currency;
import com.dowhile.PrinterFormat;
import com.dowhile.Register;

/**
 * @author Hassan 
 *
 */
public interface PrinterFormatService {

	public List<PrinterFormat> GetAllPrinterFormats();
	public PrinterFormat GetPrinterFormatByID(int printerFormatId);


}
