/**
 * 
 */
package com.dowhile.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

import com.dowhile.InventoryReport;
import com.dowhile.beans.Row;
import com.dowhile.beans.TableData;
import com.dowhile.dao.InventoryReportDAO;

/**
 * @author Yameen Bashir
 *
 */
public class InventoryReportDAOImpl implements InventoryReportDAO{

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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InventoryReport> getInventoryReportByCompanyId(int companyId) {
		// TODO Auto-generated method stub
		try{
			List<InventoryReport> list = getSessionFactory().getCurrentSession()
			.createQuery("from InventoryReport where COMPANY_ASSOCIATION_ID = ?")
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
	public List<InventoryReport> getInventoryReportByOutletIdCompanyId(
			int outletId, int companyId) {
		// TODO Auto-generated method stub
		try{
			List<InventoryReport> list = getSessionFactory().getCurrentSession()
			.createQuery("from InventoryReport where OUTLET_ASSOCICATION_ID = ? AND COMPANY_ASSOCIATION_ID = ?")
			.setParameter(0, outletId)
			.setParameter(1, companyId).list();
			if(list!=null&& list.size()>0){

				return list;
			}
		}catch(HibernateException ex){
			ex.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({"rawtypes" })
	@Override
	public TableData getInventoryReportByCompanyIdOutletId(int companyId, int outletId,boolean isHeadOffice) {
		// TODO Auto-generated method stub
		try {
			TableData tableData = new TableData();
			List result =  null;
			try{
			if(isHeadOffice){
				result = getSessionFactory()
						.getCurrentSession().createSQLQuery(
						 "CALL GetInventoryReportForWarehouse(:param1,:param2)")
						 .setParameter("param1", companyId)
						 .setParameter("param2", outletId)
						 .list();
			}else{
				result = getSessionFactory()
						.getCurrentSession().createSQLQuery(
						 "CALL GetInventoryReportForOutlets(:param1,:param2)")
						 .setParameter("param1", companyId)
						 .setParameter("param2", outletId)
						 .list();
			}
				
			 
			}catch(Exception exception){
				
			}
			List<String> columns = new ArrayList<>();
			List<Row> rows = new ArrayList<>();
		
			if(result!=null){
				
				String dbColumns = "Product,Outlet,SKU,Brand,Supplier,Type,Current Stock,Stock Value,Item Value,Reorder Point, Reorder Amount";
				
				
				if(dbColumns!=null){
					for(String columnName: dbColumns.split(",")){
						if(!isHeadOffice&& columnName.equalsIgnoreCase("Supplier"))
							continue;
						columns.add(columnName);
					}
				}


				Row footerRow = new Row();
				int totalCurrentStock = 0;
				int totalStockValue = 0;
				for(int index=0; index<result.size(); index++){
					Row row = new Row();
					List<String> colums = new ArrayList<>();
					try{
						
						Object[] data = (Object[]) result.get(index);
						for(int i=0;i<data.length;i++){
							String value = String.valueOf(data[i]);
							if(i==6){
								totalCurrentStock = (int) (totalCurrentStock+Double.valueOf(String.valueOf(data[i])));
							}
							if(i==7){
								totalStockValue= (int) (totalStockValue+Double.valueOf(String.valueOf(data[i])));
							}
							if(!isHeadOffice && i==4){
								continue;
							}
							colums.add(value);
						}	
					}catch(Exception ex){
						ex.printStackTrace();
					}
					
					row.setColumns(colums);
					rows.add(row);
				}
				List<String> colums = new ArrayList<>();
				colums.add("-");
				colums.add("-");
				colums.add("-");
				colums.add("-");
				if(isHeadOffice){
					colums.add("-");
				}
				
				colums.add("Total Stock");
				colums.add(totalCurrentStock+"");
				colums.add("Total Stock Value");
				colums.add(totalStockValue+"");
				colums.add("-");
				colums.add("-");
				footerRow.setColumns(colums);
				rows.add(footerRow);
				System.out.println("totalCurrentStock: "+totalCurrentStock+" totalStockValue: "+totalStockValue);
			}
		
			tableData.setColumns(columns);
			if(rows.size()>0){
				
				tableData.setFooter(rows.get(rows.size()-1));
				rows.remove(rows.size()-1);
			}else{
				tableData.setFooter(new Row());
			}
			
			tableData.setRows(rows);
			return tableData;
		} catch (HibernateException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
