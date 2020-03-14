/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.dowhile.PriceBookDetail;
import com.dowhile.dao.PriceBookDetailDAO;

/**
 * @author Yameen Bashir
 *
 */
public class PriceBookDetailDAOImpl implements PriceBookDetailDAO{

	private SessionFactory sessionFactory;// private static Logger logger = Logger.getLogger(CashManagementController.class.getName());

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
	public PriceBookDetail addPriceBookDetail(PriceBookDetail priceBookDetail) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().save(priceBookDetail);
			return priceBookDetail;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public PriceBookDetail updatePriceBookDetail(PriceBookDetail priceBookDetail) {
		// TODO Auto-generated method stub
		try{
			/*if(priceBookDetail.getPriceBook().getOutlet()==null){
				getSessionFactory().getCurrentSession()
				.createSQLQuery("update price_book_detail set MARKUP =:markup ,"
						+ " DISCOUNT =:discount , ACTIVE_INDICATOR=:active"
						+ " where PRICE_BOOK_ASSOCICATION_ID =:pricebookId "
						+ "AND COMPANY_ASSOCIATION_ID =:companyId"
						+ " AND UUID =:uuId")
				.setParameter("markup", priceBookDetail.getMarkup())
				.setParameter("discount", priceBookDetail.getDiscount())
				.setParameter("active", priceBookDetail.isActiveIndicator())
				.setParameter("pricebookId", priceBookDetail.getPriceBook().getPriceBookId())
				.setParameter("companyId", priceBookDetail.getCompany().getCompanyId())
				.setParameter("uuId", priceBookDetail.getUuid()).executeUpdate();
				
			}else{
				getSessionFactory().getCurrentSession().update(priceBookDetail);
			}*/
			getSessionFactory().getCurrentSession()
			.createSQLQuery("update price_book_detail set MARKUP =:markup ,"
					+ " DISCOUNT =:discount , ACTIVE_INDICATOR=:active"
					+ " where PRICE_BOOK_ASSOCICATION_ID =:pricebookId "
					+ "AND COMPANY_ASSOCIATION_ID =:companyId"
					+ " AND UUID =:uuId")
			.setParameter("markup", priceBookDetail.getMarkup())
			.setParameter("discount", priceBookDetail.getDiscount())
			.setParameter("active", priceBookDetail.isActiveIndicator())
			.setParameter("pricebookId", priceBookDetail.getPriceBook().getPriceBookId())
			.setParameter("companyId", priceBookDetail.getCompany().getCompanyId())
			.setParameter("uuId", priceBookDetail.getUuid()).executeUpdate();
			
			return priceBookDetail;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}
	
	@Override
	public boolean deletePriceBookDetail(PriceBookDetail priceBookDetail) {
		// TODO Auto-generated method stub
		try{
			getSessionFactory().getCurrentSession().delete(priceBookDetail);
			return true;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PriceBookDetail getPriceBookDetailByPriceBookDetailIdCompanyId(
			int priceBookDetail, int companyId) {
		// TODO Auto-generated method stub
		try{
			List<PriceBookDetail> list = getSessionFactory().getCurrentSession()
					.createQuery("from PriceBookDetail where PRICE_BOOK_DETAIL_ID = ? AND COMPANY_ASSOCIATION_ID = ?")
					.setParameter(0, priceBookDetail)
					.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){
				return list.get(0);
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceBookDetail> getAllPriceBookDetailsByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<PriceBookDetail> list = getSessionFactory().getCurrentSession()
					.createQuery("from PriceBookDetail where COMPANY_ASSOCIATION_ID = ?")
					.setParameter(0, companyId).list();
			if(list!=null&& list.size()>0){
				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public List<PriceBookDetail> getPriceBookDetailsByPriceBookId(int pricebookId) {
		// TODO Auto-generated method stub
				try{
					@SuppressWarnings("unchecked")
					List<PriceBookDetail> list = getSessionFactory().getCurrentSession()
							.createQuery("from PriceBookDetail where PRICE_BOOK_ASSOCICATION_ID = ?")
							.setParameter(0, pricebookId).list();
					if(list!=null&& list.size()>0){
						return list;
					}
				}catch(HibernateException ex){
					ex.printStackTrace();// logger.error(ex.getMessage(),ex);
				}
				return null;
	}

	@Override
	public List<PriceBookDetail> getPriceBookDetailsByPriceBookIdGroupByUuidCompanyId(
			int pricebookId, String uUId, int companyId) {
		// TODO Auto-generated method stub
		try{
			@SuppressWarnings("unchecked")
			List<PriceBookDetail> list = getSessionFactory().getCurrentSession()
					.createQuery("from PriceBookDetail where PRICE_BOOK_ASSOCICATION_ID = ? AND UUID=? AND COMPANY_ASSOCIATION_ID = ?")
					.setParameter(0, pricebookId)
					.setParameter(1, uUId)
					.setParameter(2, companyId).list();
			if(list!=null&& list.size()>0){
				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	
	public void addPriceBookDetail(List<PriceBookDetail> priceBookDetails) {
		Session session = null;
		try{
			session =  getSessionFactory().getCurrentSession();
			for(PriceBookDetail bookDetail : priceBookDetails){
				session.save(bookDetail);
					
			}
		
		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		
		return ;
		
	}



	@Override
	public void updatePriceBookDetailList(List<PriceBookDetail> priceBookDetails) {
		try{
			Session session = null;
			session =  getSessionFactory().getCurrentSession();
			for(PriceBookDetail priceBookDetail: priceBookDetails){
				session.update(priceBookDetail);
				
				/*getSessionFactory().getCurrentSession()
				.createSQLQuery("update price_book_detail set MARKUP =:markup ,"
						+ " DISCOUNT =:discount , ACTIVE_INDICATOR=:active"
						+ " where PRICE_BOOK_ASSOCICATION_ID =:pricebookId "
						+ "AND COMPANY_ASSOCIATION_ID =:companyId"
						+ " AND UUID =:uuId")
						.setParameter("markup", priceBookDetail.getMarkup())
						.setParameter("discount", priceBookDetail.getDiscount())
						.setParameter("active", priceBookDetail.isActiveIndicator())
						.setParameter("pricebookId", priceBookDetail.getPriceBook().getPriceBookId())
						.setParameter("companyId", priceBookDetail.getCompany().getCompanyId())
						.setParameter("uuId", priceBookDetail.getUuid()).executeUpdate();*/
				/*if(priceBookDetail.getPriceBook().getOutlet()==null){
					getSessionFactory().getCurrentSession()
					.createSQLQuery("update price_book_detail set MARKUP =:markup ,"
							+ " DISCOUNT =:discount , ACTIVE_INDICATOR=:active"
							+ " where PRICE_BOOK_ASSOCICATION_ID =:pricebookId "
							+ "AND COMPANY_ASSOCIATION_ID =:companyId"
							+ " AND UUID =:uuId")
							.setParameter("markup", priceBookDetail.getMarkup())
							.setParameter("discount", priceBookDetail.getDiscount())
							.setParameter("active", priceBookDetail.isActiveIndicator())
							.setParameter("pricebookId", priceBookDetail.getPriceBook().getPriceBookId())
							.setParameter("companyId", priceBookDetail.getCompany().getCompanyId())
							.setParameter("uuId", priceBookDetail.getUuid()).executeUpdate();
				}else{
					getSessionFactory().getCurrentSession().update(priceBookDetail);
				}*/
			}
			return ;

		}catch(HibernateException ex){
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}

	}

}
