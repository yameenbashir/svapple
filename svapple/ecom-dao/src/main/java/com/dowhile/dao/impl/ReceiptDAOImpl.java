/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

import com.dowhile.InvoiceDetailCustom;
import com.dowhile.Receipt;
import com.dowhile.ReceiptCustom;
import com.dowhile.dao.ReceiptDAO;

/**
 * @author T430s
 *
 */
public class ReceiptDAOImpl implements ReceiptDAO {

	
	private SessionFactory sessionFactory;private static Logger logger = Logger.getLogger(CashManagementController.class.getName());

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
	public Receipt addReceipt(Receipt receipt, int companyID) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(receipt);
			return receipt;
		}
		catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public Receipt updateReceipt(Receipt receipt, int companyID) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().update(receipt);
			return receipt;
		}
		catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Receipt> getAllReceipts(int companyID) {
		// TODO Auto-generated method stub
		try{
			List<Receipt> list = getSessionFactory().getCurrentSession()
			.createQuery("from Receipt where COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, companyID).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	
	}



	@Override
	public Receipt getAllReceiptsByReceiptId(int receiptid, int companyID) {
		try{
			@SuppressWarnings("unchecked")
			List<Receipt> list = getSessionFactory().getCurrentSession()
			.createQuery("from Receipt where RECEIPT_ID = ? AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, receiptid).
			 setParameter(1, companyID).list();
			if(list!=null&& list.size()>0){

				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	

	@Override
	public List<Receipt> getAllReceiptsByCustomerid(int customerid, int companyID) {
		try{
			@SuppressWarnings("unchecked")
			List<Receipt> list = getSessionFactory().getCurrentSession()
			.createQuery("from Receipt where CUSTOMER_ASSOCICATION_ID=? AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, customerid)
			.setParameter(1, companyID).list();
			return list;
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<Receipt> getAllReceiptsByOutletId(int ouletid, int companyID) {
		try{
			@SuppressWarnings("unchecked")
			List<Receipt> list = getSessionFactory().getCurrentSession()
			.createQuery("from Receipt where OUTLET_ASSOCIATION_ID=? AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, ouletid)
			.setParameter(1, companyID).list();
			return list;
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<Receipt> getAllReceiptsByInvoiceId(int invoiceid, int companyID) {
		try{
			@SuppressWarnings("unchecked")
			List<Receipt> list = getSessionFactory().getCurrentSession()
			.createQuery("from Receipt where INVOICE_MAIN_ASSOCICATION_ID=? AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, invoiceid)
			.setParameter(1, companyID).list();
			return list;
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		
		return null;
	}

	@Override
	public List<Receipt> getAllReceiptsByDailyRegister(int DailyRegisterId, int outletId, int companyID) {
		try{
			@SuppressWarnings("unchecked")
			List<Receipt> list = getSessionFactory().getCurrentSession()
			.createQuery("from Receipt where OUTLET_ASSOCIATION_ID = ? AND DAILY_REGISTER_ASSOCICATION_ID = ?"
					+ "  AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, outletId)
			.setParameter(1, DailyRegisterId).
			setParameter(2, companyID).list();
			return list;
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		
		return null;
	}

	@Override
	public Map<Integer, List<ReceiptCustom>> getAllReceipts(int companyId,
			int outletId) {
		List<ReceiptCustom> allReceipts;

        Map<Integer, List<ReceiptCustom>> ReceiptsMap = new HashMap<>();
		try{
		 
			allReceipts = getSessionFactory().getCurrentSession()
			.createSQLQuery("CALL GetAllReceiptsByCompanyOutletId(?,?)" )
			.setParameter(0, companyId)
			.setParameter(1, outletId).setResultTransformer(Transformers.aliasToBean(ReceiptCustom.class))
			.list();
			 
		
		 if(allReceipts!=null&& allReceipts.size()>0){

			 for (ReceiptCustom receiptCustom : allReceipts) {
				
				 List<ReceiptCustom> receipts = ReceiptsMap.get(receiptCustom.getInvoiceMainId());
					if(receipts!=null){
						receipts.add(receiptCustom);
						ReceiptsMap.put(receiptCustom.getInvoiceMainId(), receipts);
					}else{
						receipts = new ArrayList<>();
						receipts.add(receiptCustom);
						ReceiptsMap.put(receiptCustom.getInvoiceMainId(), receipts);
					}
			}
				return ReceiptsMap;
			}
			
			
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	}
