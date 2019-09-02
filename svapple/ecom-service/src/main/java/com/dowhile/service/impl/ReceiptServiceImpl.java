package com.dowhile.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.Receipt;
import com.dowhile.ReceiptCustom;
import com.dowhile.dao.ReceiptDAO;
import com.dowhile.service.ReceiptService;

@Transactional(readOnly = false)
public class ReceiptServiceImpl implements ReceiptService {

	private ReceiptDAO receiptDAO;
	
	

	public ReceiptDAO getReceiptDAO() {
		return receiptDAO;
	}

	public void setReceiptDAO(ReceiptDAO receiptDAO) {
		this.receiptDAO = receiptDAO;
	}

	@Override
	public Receipt addReceipt(Receipt receipt, int companyId) {
		return getReceiptDAO().addReceipt(receipt,companyId);
	}

	@Override
	public Receipt updateReceipt(Receipt receipt, int companyId) {
		return getReceiptDAO().updateReceipt(receipt,companyId);
	}

	@Override
	public List<Receipt> getAllReceipts(int companyId) {
		// TODO Auto-generated method stub
		return getReceiptDAO().getAllReceipts(companyId);
	}

	@Override
	public List<Receipt> getAllReceiptsByOutletId(int ouletid, int companyId) {
		return getReceiptDAO().getAllReceiptsByOutletId(ouletid,companyId);
	}

	@Override
	public Receipt getAllReceiptsByReceiptId(int receiptid, int companyId) {
		// TODO Auto-generated method stub
		return getReceiptDAO().getAllReceiptsByReceiptId(receiptid,companyId);
	}

	@Override
	public List<Receipt> getAllReceiptsByInvoiceId(int invoiceid, int companyId) {
		return getReceiptDAO().getAllReceiptsByInvoiceId(invoiceid,companyId);
	}

	@Override
	public List<Receipt> getAllReceiptsByCustomerid(int customerid, int companyId) {
		return getReceiptDAO().getAllReceiptsByCustomerid(customerid,companyId);
	}

	@Override
	public List<Receipt> getAllReceiptsByDailyRegister(int DailyRegisterId,
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		return receiptDAO.getAllReceiptsByDailyRegister(DailyRegisterId, outletId,companyId);
	}

	@Override
	public Map<Integer, List<ReceiptCustom>> getAllReceipts(int companyId,
			int outletId) {
		return receiptDAO.getAllReceipts(companyId, outletId);
	}


}
