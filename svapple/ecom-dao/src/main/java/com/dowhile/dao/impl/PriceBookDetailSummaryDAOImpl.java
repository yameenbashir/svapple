/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import com.dowhile.PriceBookDetailSummary;
import com.dowhile.dao.PriceBookDetailSummaryDAO;

/**
 * @author Yameen Bashir
 *
 */
public class PriceBookDetailSummaryDAOImpl implements PriceBookDetailSummaryDAO{

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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PriceBookDetailSummary> getPriceBookDetailSummaryByPriceBookIdCompanyIdGroupByUuId(
			int priceBookId, int companyId) {
		// TODO Auto-generated method stub
		try{
			List<PriceBookDetailSummary> list = getSessionFactory().getCurrentSession()
					.createQuery("from PriceBookDetailSummary where PRICE_BOOK_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID = ?  GROUP BY UUID ORDER BY PRODUCT_NAME")
					.setParameter(0, priceBookId)
					.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){
				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PriceBookDetailSummary> getPriceBookDetailSummaryByPriceBookIdCompanyId(
			int priceBookId, int companyId) {
		// TODO Auto-generated method stub
		try{
			List<PriceBookDetailSummary> list = getSessionFactory().getCurrentSession()
					.createQuery("from PriceBookDetailSummary where PRICE_BOOK_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID = ?")
					.setParameter(0, priceBookId)
					.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){
				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

}
