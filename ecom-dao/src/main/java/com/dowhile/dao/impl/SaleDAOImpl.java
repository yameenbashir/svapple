/**
 * 
 */
package com.dowhile.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.dowhile.InvoiceDetail;
import com.dowhile.InvoiceDetailCustom;
import com.dowhile.InvoiceMain;
import com.dowhile.InvoiceMainCustom;
import com.dowhile.beans.DashBoardGraphData;
import com.dowhile.dao.SaleDAO;

/**
 * @author T430s
 *
 */
public class  SaleDAOImpl implements SaleDAO  {

	private SessionFactory sessionFactory;

	/**
	 * Get Hibernate Session Factory
	 * 
	 * @return SessionFactory - Hibernate Session Factory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Set Hibernate Session Factory
	 * 
	 * @param SessionFactory
	 *            - Hibernate Session Factory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public InvoiceMain addInvoiceMain(InvoiceMain invoice,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(invoice);
			return invoice;
		}
		catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	
	}

	@Override
	public InvoiceDetail addInvoiceDetail(InvoiceDetail invoiceDetail,int companyId) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(invoiceDetail);
			return invoiceDetail;
		}
		catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceMain> getAllInvoicesMain(int companyId) {
		try{
			List<InvoiceMain> list = getSessionFactory().getCurrentSession()
					.createQuery("from InvoiceMain where COMPANY_ASSOCIATION_ID=? order by  CREATED_DATE DESC")
					.setParameter(0, companyId).list();
					if(list!=null&& list.size()>0){

						return list;
					}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceDetail> getAllInvoicesDeail(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<InvoiceDetail> list = getSessionFactory().getCurrentSession()
					.createQuery("from InvoiceDetail where COMPANY_ASSOCIATION_ID=?")
					.setParameter(0, companyId).list();
					if(list!=null&& list.size()>0){

						return list;
					}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public InvoiceMain getInvoiceMainByID(int invoice_ID,int companyId) {
		try{
			@SuppressWarnings("unchecked")
			List<InvoiceMain> list = getSessionFactory().getCurrentSession()
			.createQuery("from InvoiceMain where INVOICE_MAIN_ID=? AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, invoice_ID)
			.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public InvoiceDetail getInvoiceDetailByDetailID(int invoiceDetail_ID,int companyId) {
		try{
			@SuppressWarnings("unchecked")
			List<InvoiceDetail> list = getSessionFactory().getCurrentSession()
			.createQuery("from InvoiceDetail where INVOICE_DETAIL_ID=? AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, invoiceDetail_ID)
			.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public InvoiceDetail getInvoiceDetailByMainID(int invoiceMain_ID,int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<InvoiceDetail> list = getSessionFactory().getCurrentSession()
			.createQuery("from InvoiceDetail where INVOICE_MAIN_ASSOCICATION_ID=? AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, invoiceMain_ID)
			.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public List<InvoiceDetail> addInvoiceDetails(List<InvoiceDetail> invoiceDetails,int companyId) {
		try{
			for(InvoiceDetail detail : invoiceDetails){
				getSessionFactory().getCurrentSession().save(detail);
				
			}
			return invoiceDetails;
		}
		catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
public List<InvoiceMainCustom> getAllInvoicesMainById(int outletId, int copmayId, int limit, String invoiceRefNum, String status, Date fromDate, Date toDate,Integer customerId  ) {
		
		List<InvoiceMainCustom> list = null;
		try{
		 
			if(limit == 0) // Get all
			{
			list= getSessionFactory().getCurrentSession()
			.createSQLQuery("CALL GetAllInvoiceByOutletId(?,?,?,?,?,?,?)" )
			.setParameter(1, outletId)
			.setParameter(0, copmayId)
			.setParameter(2, invoiceRefNum)
			.setParameter(3, status)
			.setParameter(4, fromDate)
			.setParameter(5, toDate)
			.setParameter(6, customerId)
			.setResultTransformer(Transformers.aliasToBean(InvoiceMainCustom.class))
			.list();
			}
			
			else if(limit > 0) //Get According to limit
			{
			 
			list= getSessionFactory().getCurrentSession()
					.createSQLQuery("CALL GetLimitedInvoiceByOutletId(?,?,?)" )
					.setParameter(1, outletId)
					.setParameter(0, copmayId)
					.setParameter(2, limit).setResultTransformer(Transformers.aliasToBean(InvoiceMainCustom.class))
					.list();
			
			}
		
		 if(list!=null&& list.size()>0){

				return list;
			}
			
			
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getGrossProfit(int outletId, int copmayId, Date startDate, Date endDate) {
		try{
			List<DashBoardGraphData> list = getSessionFactory().getCurrentSession()
					.createSQLQuery("SELECT DATE(INVOICE_GENERATION_DTE), (SUM(ITEM_SALE_PRICE * PRODUCT_QUANTITY ) - SUM(ITEM_ORIGNAL_AMT * PRODUCT_QUANTITY )) "
							+"FROM invoice_detail inner join invoice_main on INVOICE_MAIN_ID = INVOICE_MAIN_ASSOCICATION_ID "
							+ "WHERE  INVOICE_GENERATION_DTE between ? and ? and invoice_main.COMPANY_ASSOCIATION_ID = ? and invoice_main.OUTLET_ASSOCICATION_ID = ? and invoice_main.STATUS_ASSOCICATION_ID NOT IN ( 11)"
							+ "GROUP BY DATE(INVOICE_GENERATION_DTE) "
							+ "having (SUM(ITEM_SALE_PRICE * PRODUCT_QUANTITY ) -SUM(ITEM_ORIGNAL_AMT * PRODUCT_QUANTITY )) > 0 " )
					.setParameter(0, startDate)
					.setParameter(1, endDate)
					.setParameter(3, outletId)
					.setParameter(2, copmayId)
					.list();
					if(list!=null&& list.size()>0){

						return list;
					}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceDetail> getAllInvoicesDeailById(int outletId,
			int CopmayId, int invoiceMainId) {
		List<InvoiceDetail> list = null;
		try{
			list = getSessionFactory().getCurrentSession()
			.createQuery("from InvoiceDetail where INVOICE_MAIN_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID=?")
			.setParameter(0, invoiceMainId)
			.setParameter(1, CopmayId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public InvoiceMain updateInvoice(InvoiceMain invoice,int companyId) {
		try{
			getSessionFactory().getCurrentSession().update(invoice);
			return invoice;
		}
		catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	

	@Override
	public InvoiceDetail updateInvoiceDetailByDetailID(InvoiceDetail paramInvoiceDetl) {
		try{
			getSessionFactory().getCurrentSession().update(paramInvoiceDetl);
			return paramInvoiceDetl;
		}
		catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public double getTodaysRevenue(int outletId, int companyId,int dailyRegisterId) {
		double result = 0;
		try{
			List<Object[]> rows = getSessionFactory().getCurrentSession()
					.createSQLQuery("SELECT DATE(CREATED_DATE) as date, "
							+ "sum(SETTLED_AMT) as bigDecimal FROM invoice_main "
							+ "WHERE  DATE(CREATED_DATE) = CURRENT_DATE()  "
							+ "and COMPANY_ASSOCIATION_ID = ?  "
							+"and DAILY_REGISTER_ASSOCICATION_ID = ? "
							+ "and OUTLET_ASSOCICATION_ID = ? "
							+ "and STATUS_ASSOCICATION_ID NOT IN ( 11) "
							+ "GROUP BY DATE(CREATED_DATE) "
							).
					setParameter(0, companyId)
					.setParameter(1, dailyRegisterId)
					.setParameter(2, outletId)
				
					.list();
			/*List<Object[]> rows = getSessionFactory().getCurrentSession()
					.createSQLQuery("SELECT DATE(INVOICE_GENERATION_DTE) as date, sum(((`invoice_detail`.`ITEM_SALE_PRICE` * `invoice_detail`.`PRODUCT_QUANTITY`) - (coalesce(`invoice_main`.`INVOICE_DISCOUNT_AMT`,0)) /" +
"(select count(INVOICE_MAIN_ASSOCICATION_ID) from invoice_detail where invoice_detail.INVOICE_MAIN_ASSOCICATION_ID = invoice_main.INVOICE_MAIN_ID))) as bigDecimal FROM invoice_detail "
							+"inner join invoice_main on INVOICE_MAIN_ID = INVOICE_MAIN_ASSOCICATION_ID "
							+ "WHERE  DATE(INVOICE_GENERATION_DTE) = CURRENT_DATE()  and invoice_main.COMPANY_ASSOCIATION_ID = ?  and invoice_main.OUTLET_ASSOCICATION_ID = ? and invoice_main.STATUS_ASSOCICATION_ID NOT IN ( 11) "
							+ "GROUP BY DATE(INVOICE_GENERATION_DTE) "
							).
					setParameter(0, companyId)
					.setParameter(1, outletId)
				
					.list();*/
					if(rows!=null&& rows.size()>0){
						
						for(Object[] row : rows){
							result = Double.parseDouble(row[1].toString());
							}
						
						
						return result;
					}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return result;
	}

	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getRevenue(int outletId, int copmayId, Date startDate,Date endDate) {
		try{
			List<DashBoardGraphData> list = getSessionFactory().getCurrentSession()
					.createSQLQuery("SELECT DATE(CREATED_DATE) as date, "
							+ "sum(SETTLED_AMT) as bigDecimal FROM invoice_main "
							+ "WHERE  INVOICE_GENERATION_DTE between ? and ? "
							+ "and COMPANY_ASSOCIATION_ID = ?  "
							+ "and OUTLET_ASSOCICATION_ID = ? "
							+ "and STATUS_ASSOCICATION_ID NOT IN ( 11) "
							+ "GROUP BY DATE(CREATED_DATE) "
							)
					.setParameter(0, startDate)
					.setParameter(1, endDate)
					.setParameter(3, outletId)
					.setParameter(2, copmayId)
					.list();
					if(list!=null&& list.size()>0){

						return list;
					}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getSalesCount(int outletId, int copmayId, Date startDate,
			Date endDate) {
		try{
			List<DashBoardGraphData> list = getSessionFactory().getCurrentSession()
					.createSQLQuery("SELECT DATE(INVOICE_GENERATION_DTE), COUNT(INVOICE_MAIN_ID) from invoice_main "
							+ "WHERE  INVOICE_GENERATION_DTE between ? and ? and COMPANY_ASSOCIATION_ID = ? and OUTLET_ASSOCICATION_ID = ? and STATUS_ASSOCICATION_ID NOT IN (14,11) "
							+ "GROUP BY DATE(INVOICE_GENERATION_DTE) ORDER BY INVOICE_GENERATION_DTE")
					.setParameter(0, startDate)
					.setParameter(1, endDate)
					.setParameter(3, outletId)
					.setParameter(2, copmayId)
					.list();
					if(list!=null&& list.size()>0){

						return list;
					}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getCustomersCount(int outletId, int copmayId, Date startDate,
			Date endDate) {
		try{
			@SuppressWarnings("unchecked")
			List<DashBoardGraphData> list = getSessionFactory().getCurrentSession()
					.createSQLQuery("SELECT DATE(CREATED_DATE), COUNT(CONTACT_ID) FROM contact "
							+ "WHERE  CREATED_DATE between ? and ? and COMPANY_ASSOCIATION_ID = ?  and CONTACT_TYPE like '%CUSTOMER%' AND OUTLET_ASSOCIATION_ID=? "
							+ "GROUP BY DATE(CREATED_DATE) ORDER BY CREATED_DATE")
					.setParameter(0, startDate)
					.setParameter(1, endDate)
					.setParameter(2, copmayId)
					.setParameter(3, outletId)
					.list();
					if(list!=null&& list.size()>0){

						return list;
					}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getProductsCount(int outletId, int copmayId, Date startDate,
			Date endDate) {
		try{
			@SuppressWarnings("unchecked")
			List<DashBoardGraphData> list = getSessionFactory().getCurrentSession()
					.createSQLQuery("SELECT DATE(CREATED_DATE), COUNT(PRODUCT_ID) FROM product "
							+ "WHERE  CREATED_DATE between ? and ? and COMPANY_ASSOCIATION_ID = ?  AND OUTLET_ASSOCICATION_ID=? "
							+ "GROUP BY DATE(CREATED_DATE) ORDER BY CREATED_DATE")
					.setParameter(0, startDate)
					.setParameter(1, endDate)
					.setParameter(2, copmayId)
					.setParameter(3, outletId)
					.list();
					if(list!=null&& list.size()>0){

						return list;
					}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getDiscounts(int outletId, int copmayId, Date startDate,Date endDate) {
		try{
			List<DashBoardGraphData> list = getSessionFactory().getCurrentSession()
					.createSQLQuery(

"SELECT DATE(INVOICE_GENERATION_DTE),"  
+"ROUND((SUM(IFNULL(INVOICE_DISCOUNT_AMT,0)) /(select count(INVOICE_MAIN_ASSOCICATION_ID)" 
+"from invoice_detail where invoice_detail.INVOICE_MAIN_ASSOCICATION_ID = invoice_main.INVOICE_MAIN_ID)) " 
 +"+ (sum(CASE "
+"WHEN ITEM_SALE_PRICE > 0 THEN (ITEM_RETAIL_PRICE - ITEM_SALE_PRICE) * PRODUCT_QUANTITY "
+"WHEN ITEM_SALE_PRICE < 0 THEN -1* ((ITEM_RETAIL_PRICE - (-1* ITEM_SALE_PRICE)) * PRODUCT_QUANTITY) else 0 END))) as disc "

+"from invoice_main  inner join invoice_detail on INVOICE_MAIN_ID = INVOICE_MAIN_ASSOCICATION_ID "
+ "WHERE  INVOICE_GENERATION_DTE between ? and ? and invoice_main.COMPANY_ASSOCIATION_ID = ? and invoice_main.OUTLET_ASSOCICATION_ID = ? and STATUS_ASSOCICATION_ID NOT IN (11) "
+"GROUP BY DATE(INVOICE_GENERATION_DTE) ORDER BY INVOICE_GENERATION_DTE")
					.setParameter(0, startDate)
					.setParameter(1, endDate)
					.setParameter(3, outletId)
					.setParameter(2, copmayId)
					.list();
					if(list!=null&& list.size()>0){

						return list;
					}
					
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getBasketSize(int outletId, int copmayId, Date startDate,
			Date endDate) {
		try{
			@SuppressWarnings("unchecked")
			List<DashBoardGraphData> list = getSessionFactory().getCurrentSession()
					.createSQLQuery("SELECT DATE(CREATED_DATE), COUNT(INVOICE_MAIN_ASSOCICATION_ID)/COUNT(DISTINCT INVOICE_MAIN_ASSOCICATION_ID)  from invoice_detail "
							+ "WHERE  CREATED_DATE between ? and ? and COMPANY_ASSOCIATION_ID = ?  and OUTLET_ASSOCICATION_ID = ? "
							+ "GROUP BY DATE(CREATED_DATE) ORDER BY CREATED_DATE")
					.setParameter(0, startDate)
					.setParameter(1, endDate)
					.setParameter(2, copmayId)
					.setParameter(3, outletId)
					.list();
					if(list!=null&& list.size()>0){

						return list;
					}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getBasketValue(int outletId, int copmayId, Date startDate,
			Date endDate) {
		try{
				List<DashBoardGraphData> list = getSessionFactory().getCurrentSession()
						.createSQLQuery("SELECT DATE(INVOICE_GENERATION_DTE), AVG(INVOICE_NET_AMT) from invoice_main "
								+ "WHERE  INVOICE_GENERATION_DTE between ? and ? and COMPANY_ASSOCIATION_ID = ? and OUTLET_ASSOCICATION_ID = ? and STATUS_ASSOCICATION_ID<>14 and (INVC_TYPE_CDE IS NULL)"
								+ "GROUP BY DATE(INVOICE_GENERATION_DTE) ORDER BY INVOICE_GENERATION_DTE")
						.setParameter(0, startDate)
						.setParameter(1, endDate)
						.setParameter(3, outletId)
						.setParameter(2, copmayId)
						.list();
						if(list!=null&& list.size()>0){

							return list;
						}
			}catch(HibernateException ex){
				ex.printStackTrace();
			}
			return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getDiscountPercent(int outletId, int copmayId, Date startDate,
			Date endDate) {try{
				List<DashBoardGraphData> list = getSessionFactory().getCurrentSession()
						.createSQLQuery("SELECT DATE(INVOICE_GENERATION_DTE), AVG(IFNULL(INVOICE_DISCOUNT,0) + IFNULL(ITEM_DISCOUNT_PRCT,0)) from invoice_main "
								+ " inner join invoice_detail on INVOICE_MAIN_ID = INVOICE_MAIN_ASSOCICATION_ID "
								+ "WHERE  INVOICE_GENERATION_DTE between ? and ? and invoice_main.COMPANY_ASSOCIATION_ID = ? and invoice_main.OUTLET_ASSOCICATION_ID = ?  and STATUS_ASSOCICATION_ID NOT IN (11) "
								+ "GROUP BY DATE(INVOICE_GENERATION_DTE) ORDER BY INVOICE_GENERATION_DTE")
						.setParameter(0, startDate)
						.setParameter(1, endDate)
						.setParameter(3, outletId)
						.setParameter(2, copmayId)
						.list();
						if(list!=null&& list.size()>0){

							return list;
						}
			}catch(HibernateException ex){
				ex.printStackTrace();
			}
			return null;
	}

	@Override
	public InvoiceDetail DeleteInvoiceDetail(InvoiceDetail paramInvoiceDetail) {
		getSessionFactory().getCurrentSession().delete(paramInvoiceDetail);
		return paramInvoiceDetail;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, List<InvoiceDetailCustom>> getAllInvoicesDetails(
			int outletId, int CopmayId) {
		List<InvoiceDetailCustom> listDetails;

        Map<Integer, List<InvoiceDetailCustom>> InvoicesDetailsMap = new HashMap<>();
		try{
		 
			listDetails= getSessionFactory().getCurrentSession()
			.createSQLQuery("CALL GetAllInvoiceDetailByOutletCompanyId(?,?)" )
			.setParameter(1, outletId)
			.setParameter(0, CopmayId).setResultTransformer(Transformers.aliasToBean(InvoiceDetailCustom.class))
			.list();
			 
		
		 if(listDetails!=null&& listDetails.size()>0){

			 for (InvoiceDetailCustom invoiceDetailCustom : listDetails) {
				
				 List<InvoiceDetailCustom> invoiceDetails = InvoicesDetailsMap.get(invoiceDetailCustom.getInvoiceMainId());
					if(invoiceDetails!=null){
						invoiceDetails.add(invoiceDetailCustom);
						InvoicesDetailsMap.put(invoiceDetailCustom.getInvoiceMainId(), invoiceDetails);
					}else{
						invoiceDetails = new ArrayList<>();
						invoiceDetails.add(invoiceDetailCustom);
						InvoicesDetailsMap.put(invoiceDetailCustom.getInvoiceMainId(), invoiceDetails);
					}
			}
				return InvoicesDetailsMap;
			}
			
			
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, List<InvoiceDetailCustom>> getAllInvoicesDetailsByInvoiceIdRange(int outletId, int CopmayId, int fromInvoiceId, int toInvoiceId) {
		List<InvoiceDetailCustom> listDetails;

        Map<Integer, List<InvoiceDetailCustom>> InvoicesDetailsMap = new HashMap<>();
		try{
		 
			listDetails= getSessionFactory().getCurrentSession()
			.createSQLQuery("CALL GetAllInvoiceDetailByInvoiceLimit(?,?,?,?)" )
			.setParameter(1, outletId)
			.setParameter(0, CopmayId)
			.setParameter(2, fromInvoiceId)
			.setParameter(3, toInvoiceId)
			.setResultTransformer(Transformers.aliasToBean(InvoiceDetailCustom.class))
			.list();
			 
		
		 if(listDetails!=null&& listDetails.size()>0){

			 for (InvoiceDetailCustom invoiceDetailCustom : listDetails) {
				
				 List<InvoiceDetailCustom> invoiceDetails = InvoicesDetailsMap.get(invoiceDetailCustom.getInvoiceMainId());
					if(invoiceDetails!=null){
						invoiceDetails.add(invoiceDetailCustom);
						InvoicesDetailsMap.put(invoiceDetailCustom.getInvoiceMainId(), invoiceDetails);
					}else{
						invoiceDetails = new ArrayList<>();
						invoiceDetails.add(invoiceDetailCustom);
						InvoicesDetailsMap.put(invoiceDetailCustom.getInvoiceMainId(), invoiceDetails);
					}
			}
				return InvoicesDetailsMap;
			}
			
			
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}
	
}
