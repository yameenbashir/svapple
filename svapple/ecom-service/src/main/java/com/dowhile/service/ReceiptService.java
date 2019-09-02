package com.dowhile.service;

import java.util.List;
import java.util.Map;

import com.dowhile.InvoiceDetailCustom;
import com.dowhile.Receipt;
import com.dowhile.ReceiptCustom;

public interface ReceiptService {

	Receipt addReceipt(Receipt receipt, int companyId);
	Receipt updateReceipt(Receipt receipt, int companyId);
	List<Receipt> getAllReceipts(int companyId);
	List<Receipt> getAllReceiptsByOutletId(int ouletid, int companyId);
	 Receipt getAllReceiptsByReceiptId(int receiptid, int companyId);
	List<Receipt> getAllReceiptsByInvoiceId(int invoiceid, int companyId);
	List<Receipt> getAllReceiptsByCustomerid(int customerid, int companyId);
	 List<Receipt> getAllReceiptsByDailyRegister(int DailyRegisterId, int outletId, int companyId);	
	 Map<Integer, List<ReceiptCustom>> getAllReceipts(int companyId, int outletId);
	
}
