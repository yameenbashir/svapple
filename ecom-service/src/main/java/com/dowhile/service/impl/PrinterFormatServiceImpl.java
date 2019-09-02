/**
 * 
 */
package com.dowhile.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.PrinterFormat;
import com.dowhile.dao.PrinterFormatDAO;
import com.dowhile.service.PrinterFormatService;

/**
 * @author Hassan Rasheed
 *
 */
@Transactional(readOnly = false)
public class PrinterFormatServiceImpl implements PrinterFormatService{
	
	private PrinterFormatDAO printerFormatDAO;

	
	

	@Override
	public List<PrinterFormat> GetAllPrinterFormats() {
		return getPrinterFormatDAO().GetAllPrinterFormats();
	}




	/**
	 * @return the printerFormatDAO
	 */
	public PrinterFormatDAO getPrinterFormatDAO() {
		return printerFormatDAO;
	}




	/**
	 * @param printerFormatDAO the printerFormatDAO to set
	 */
	public void setPrinterFormatDAO(PrinterFormatDAO printerFormatDAO) {
		this.printerFormatDAO = printerFormatDAO;
	}




	@Override
	public PrinterFormat GetPrinterFormatByID(int printerFormatId) {
		return getPrinterFormatDAO().GetPrinterFormatByID(printerFormatId);
	}

	


	
	
}
