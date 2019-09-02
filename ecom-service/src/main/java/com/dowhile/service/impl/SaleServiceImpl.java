/**
 * 
 */
package com.dowhile.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.dowhile.InvoiceDetail;
import com.dowhile.InvoiceDetailCustom;
import com.dowhile.InvoiceMain;
import com.dowhile.InvoiceMainCustom;
import com.dowhile.dao.SaleDAO;
import com.dowhile.service.SaleService;

/**
 * @author T430s
 *
 */
@Transactional(readOnly = false)
public class SaleServiceImpl implements SaleService {

	private SaleDAO saleDAO;
	
	
	@Override
	public InvoiceMain addInvoiceMain(InvoiceMain invoice,int companyId) {
		// TODO Auto-generated method stub
		return getSaleDAO().addInvoiceMain(invoice,companyId);
	}

	@Override
	public InvoiceDetail addInvoiceDetail(InvoiceDetail invoiceDetail,int companyId) {
		// TODO Auto-generated method stub
		return getSaleDAO().addInvoiceDetail(invoiceDetail,companyId);
	}

	@Override
	public List<InvoiceMain> getAllInvoicesMain(int companyId) {
		// TODO Auto-generated method stub
		return getSaleDAO().getAllInvoicesMain(companyId);
	}

	@Override
	public List<InvoiceDetail> getAllInvoicesDeail(int companyId) {
		// TODO Auto-generated method stub
		return getSaleDAO().getAllInvoicesDeail(companyId);
	}

	@Override
	public InvoiceMain getInvoiceMainByID(int invoice_ID,int companyId) {
		// TODO Auto-generated method stub
		return getSaleDAO().getInvoiceMainByID(invoice_ID,companyId);
	}

	@Override
	public InvoiceDetail getInvoiceDetailByDetailID(int invoiceDetail_ID,int companyId) {
		// TODO Auto-generated method stub
		return getSaleDAO().getInvoiceDetailByDetailID(invoiceDetail_ID,companyId);
	}

	@Override
	public InvoiceDetail getInvoiceDetailByMainID(int invoiceMain_ID,int companyId) {
		// TODO Auto-generated method stub
		return getSaleDAO().getInvoiceDetailByMainID(invoiceMain_ID,companyId);
	}

	public SaleDAO getSaleDAO() {
		return saleDAO;
	}

	public void setSaleDAO(SaleDAO saleDAO) {
		this.saleDAO = saleDAO;
	}

	@Override
	public List<InvoiceDetail> addInvoiceDetails(
			List<InvoiceDetail> invoiceDetails,int companyId) {
		// TODO Auto-generated method stub
		return getSaleDAO().addInvoiceDetails(invoiceDetails,companyId);
	}

	
	@Override
	public List<InvoiceDetail> getAllInvoicesDeailById(int outletId,
			int CopmayId, int invoiceMainId) {
		// TODO Auto-generated method stub
		return getSaleDAO().getAllInvoicesDeailById(outletId, CopmayId, invoiceMainId);
	}

	@Override
	public InvoiceMain updateInvoiceMainByID(InvoiceMain invoice,int companyId) {
		return getSaleDAO().updateInvoice(invoice,companyId);
				
	}

	@Override
	@SuppressWarnings("rawtypes")
	public List  getGrossProfit(int outletId, int copmayId,
			Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return getSaleDAO().getGrossProfit(outletId, copmayId, startDate, endDate);
	}

	@Override
	public double getTodaysRevenue(int outletId, int companyId) {
		// TODO Auto-generated method stub
		return getSaleDAO().getTodaysRevenue(outletId, companyId);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getRevenue(int outletId, int copmayId, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		return  getSaleDAO().getRevenue(outletId, copmayId, startDate, endDate);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getSalesCount(int outletId, int copmayId, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		return getSaleDAO().getSalesCount(outletId, copmayId, startDate, endDate);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getCustomersCount(int outletId, int copmayId, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		return getSaleDAO().getCustomersCount(outletId, copmayId, startDate, endDate);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getDiscounts(int outletId, int copmayId, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		return getSaleDAO().getDiscounts(outletId, copmayId, startDate, endDate);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getBasketSize(int outletId, int copmayId, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		return getSaleDAO().getBasketSize(outletId, copmayId, startDate, endDate);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getBasketValue(int outletId, int copmayId, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		return getSaleDAO().getBasketValue(outletId, copmayId, startDate, endDate);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getDiscountPercent(int outletId, int copmayId, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		return getSaleDAO().getDiscountPercent(outletId, copmayId, startDate, endDate);
	}

	@Override
	public InvoiceDetail updateInvoiceDetailByDetailID(InvoiceDetail paramInvoiceDetl) {
		return getSaleDAO().updateInvoiceDetailByDetailID(paramInvoiceDetl);
				
	}

	@Override
	public InvoiceDetail DeleteInvoiceDetail(InvoiceDetail paramInvoiceDetail) {
		return getSaleDAO().DeleteInvoiceDetail(paramInvoiceDetail);
	}

	@Override
	public List<InvoiceMainCustom> getAllInvoicesMainById(int outletId, int copmayId, int limit, String invoiceRefNum, String status, Date fromDate, Date toDate  )
	{
		return getSaleDAO().getAllInvoicesMainById( outletId,  copmayId,  limit,  invoiceRefNum,  status,  fromDate,  toDate  );
		
	}
	@Override
	public Map<Integer, List<InvoiceDetailCustom>> getAllInvoicesDetailsByInvoiceIdRange(int outletId, int CopmayId, int fromInvoiceId, int toInvoiceId)
	{
		
		return getSaleDAO().getAllInvoicesDetailsByInvoiceIdRange( outletId,  CopmayId,  fromInvoiceId,  toInvoiceId );
		
	}

	@Override
	public Map<Integer, List<InvoiceDetailCustom>> getAllInvoicesDetails(
			int outletId, int CopmayId) {
		return getSaleDAO().getAllInvoicesDetails(outletId,CopmayId );
	}

	@SuppressWarnings("rawtypes")
	
	@Override
	public List getProductsCount(int outletId, int copmayId, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		return getSaleDAO().getProductsCount(outletId, copmayId, startDate, endDate);
	}

	

}
