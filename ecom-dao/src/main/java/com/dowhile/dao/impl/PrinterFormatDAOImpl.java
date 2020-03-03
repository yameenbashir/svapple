/**
 * 
 */
package com.dowhile.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.dowhile.PrinterFormat;
import com.dowhile.dao.PrinterFormatDAO;

/**
 * @author MHR
 *
 */
public class PrinterFormatDAOImpl implements PrinterFormatDAO {

	private SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(PrinterFormatDAOImpl.class.getName());

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
	public List<PrinterFormat> GetAllPrinterFormats() {
		try {
			List<PrinterFormat> list = getSessionFactory().getCurrentSession()
					.createQuery("From PrinterFormat WHERE ACTIVE_INDICATOR = 1")
					.list();
			if (list != null && list.size() > 0) {

				return list;
			}

		} catch (HibernateException ex) {
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public PrinterFormat GetPrinterFormatByID(int printerFormatId) {
		try{
		Query query= sessionFactory.getCurrentSession()
				.createQuery("from PrinterFormat where PRINTER_FORMAT_ID =?")
		.setParameter(0, printerFormatId);
		PrinterFormat printerFormat = (PrinterFormat) query.uniqueResult();
		if(printerFormat!=null)
		{

			return printerFormat;
		}
		}catch(HibernateException ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}


		return null;
	}
	
	

}
