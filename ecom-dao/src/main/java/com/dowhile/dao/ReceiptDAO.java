/**
 * 
 */
package com.dowhile.dao;

import java.util.List;
import java.util.Map;

import com.dowhile.Receipt;
import com.dowhile.ReceiptCustom;

/**
 * @author T430s
 *
 */
public interface ReceiptDAO {
	Receipt addReceipt(Receipt receipt, int companyId);
	Receipt updateReceipt(Receipt receipt, int companyId);
	public List<Receipt> getAllReceipts(int companyId);
	public List<Receipt> getAllReceiptsByOutletId(int ouletid, int companyId);
	public  Receipt getAllReceiptsByReceiptId(int receiptid, int companyId);
	public List<Receipt> getAllReceiptsByInvoiceId(int invoiceid, int companyId);
	public List<Receipt> getAllReceiptsByCustomerid(int customerid, int companyId);
	public List<Receipt> getAllReceiptsByDailyRegister(int DailyRegisterId, int outletId, int companyId);
	public Map<Integer, List<ReceiptCustom>> getAllReceipts(int companyId,int outletId);
} 
