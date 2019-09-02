/**
 * 
 */
package com.dowhile.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dowhile.InvoiceDetail;
import com.dowhile.InvoiceDetailCustom;
import com.dowhile.InvoiceMain;
import com.dowhile.InvoiceMainCustom;



/**
 * @author T430s
 *
 */
public interface SaleService   {
	InvoiceMain addInvoiceMain(InvoiceMain invoice,int companyId);
	InvoiceDetail addInvoiceDetail(InvoiceDetail invoiceDetail,int companyId);
	List<InvoiceMain> getAllInvoicesMain(int companyId);
	List<InvoiceDetail> getAllInvoicesDeail(int companyId);
	InvoiceMain getInvoiceMainByID(int invoice_ID,int companyId);
	InvoiceMain updateInvoiceMainByID(InvoiceMain invoice,int companyId);
	InvoiceDetail updateInvoiceDetailByDetailID(InvoiceDetail paramInvoiceDetl);
	InvoiceDetail getInvoiceDetailByDetailID(int invoiceDetail_ID,int companyId);
	InvoiceDetail getInvoiceDetailByMainID(int invoiceMain_ID,int companyId);
	List<InvoiceDetail> addInvoiceDetails(List<InvoiceDetail> invoiceDetails,int companyId);
	List<InvoiceMainCustom> getAllInvoicesMainById(int outletId, int copmayId, int limit, String invoiceRefNum, String status, Date fromDate, Date toDate  );
	List<InvoiceDetail> getAllInvoicesDeailById(int outletId, int CopmayId, int invoiceMainId);
	@SuppressWarnings("rawtypes")
	List getGrossProfit(int outletId, int copmayId, Date startDate, Date endDate) ;
	@SuppressWarnings("rawtypes")
	List getRevenue(int outletId, int copmayId, Date startDate, Date endDate) ;
	@SuppressWarnings("rawtypes")
	List getSalesCount(int outletId, int copmayId, Date startDate, Date endDate) ;
	@SuppressWarnings("rawtypes")
	List getCustomersCount(int outletId, int copmayId, Date startDate, Date endDate) ;
	
	@SuppressWarnings("rawtypes")
	List getProductsCount(int outletId, int copmayId, Date startDate, Date endDate) ;
	
	
	@SuppressWarnings("rawtypes")
	List getDiscounts(int outletId, int copmayId, Date startDate, Date endDate) ;
	@SuppressWarnings("rawtypes")
	List getBasketSize(int outletId, int copmayId, Date startDate, Date endDate) ;
	double getTodaysRevenue(int outletId, int companyId);
	@SuppressWarnings("rawtypes")
	List getBasketValue(int outletId, int copmayId, Date startDate, Date endDate) ;
	@SuppressWarnings("rawtypes")
	List getDiscountPercent(int outletId, int copmayId, Date startDate, Date endDate) ;
	InvoiceDetail DeleteInvoiceDetail(InvoiceDetail paramInvoiceDetail) ;
	
	Map<Integer, List<InvoiceDetailCustom>> getAllInvoicesDetails(int outletId, int CopmayId);
	
	Map<Integer, List<InvoiceDetailCustom>> getAllInvoicesDetailsByInvoiceIdRange(int outletId, int CopmayId, int fromInvoiceId, int toInvoiceId);


	
}
