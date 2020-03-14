/**
 * 
 */
package com.dowhile.dao.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;

import com.dowhile.beans.ReportParams;
import com.dowhile.beans.Row;
import com.dowhile.beans.TableData;
import com.dowhile.dao.TempSaleDAO;

/**
 * @author Yameen Bashir
 *
 */
public class TempSaleDAOImpl implements TempSaleDAO{

	private SessionFactory sessionFactory;
	// private static Logger logger = Logger.getLogger(TempSaleDAOImpl.class.getName());

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public TableData getAllTempSaleByCompanyId(ReportParams reportParams) {
		// TODO Auto-generated method stub
		try {
			TableData tableData = new TableData();
			List result =  null;
			try{
			
			 if(reportParams.getReportDateType().equals("Daily")){
				 result = getSessionFactory()
							.getCurrentSession().createSQLQuery(
							 "CALL Pivot(:param1,:param2,:param3,:param4,:param5,:param6,:param7,:param8)")
							 .setParameter("param1", reportParams.getTableName())
							 .setParameter("param2", reportParams.getMainBaseColumn())
							 .setParameter("param3", reportParams.getBaseColumn())
							 .setParameter("param4", reportParams.getPivotColumn())
							 .setParameter("param5", reportParams.getTallyColumn())
							 .setParameter("param6", reportParams.getWhereClause())
							 .setParameter("param7", "")
							 .setParameter("param8", reportParams.getGroupBy())
							 .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
			 }else{
				 result = getSessionFactory()
							.getCurrentSession().createSQLQuery(
							 "CALL Pivot_Summarize(:param1,:param2,:param3,:param4,:param5,:param6,:param7,:param8,:param9)")
							 .setParameter("param1", reportParams.getTableName())
							 .setParameter("param2", reportParams.getMainBaseColumn())
							 .setParameter("param3", reportParams.getBaseColumn())
							 .setParameter("param4", reportParams.getPivotColumn())
							 .setParameter("param5", reportParams.getTallyColumn())
							 .setParameter("param6", reportParams.getWhereClause())
							 .setParameter("param7", "")
							 .setParameter("param8", reportParams.getGroupBy())
							 .setParameter("param9", reportParams.getReportDateType())
							 .setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE).list();
			 }
			 
			}catch(Exception exception){
				
			}
			List<String> columns = new ArrayList<>();
			List<Row> rows = new ArrayList<>();
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
			if(reportParams.getReportDateType().equals("weekly")){
				 dt1 = new SimpleDateFormat("dd-MMM-yy");
				 Calendar calendar =  Calendar.getInstance();
				 calendar.setTime(reportParams.getStartDate());
				 calendar.add(Calendar.DAY_OF_MONTH, -7);
				 reportParams.setStartDate( calendar.getTime());
				 
				 Calendar calendar2 =  Calendar.getInstance();
				 calendar2.setTime(reportParams.getEndDate());
				 calendar2.add(Calendar.DAY_OF_MONTH, 7);
				 reportParams.setEndDate( calendar2.getTime());
			
					
			}
			if(reportParams.getReportDateType().equals("monthly")){
				 dt1 = new SimpleDateFormat("MMM-yy");
				
			}
			if(reportParams.getReportDateType().equals("yearly")){
				 dt1 = new SimpleDateFormat("yyyy");
				
			}
			columns.add(reportParams.getMainBaseColumn());
			Calendar calendar =  Calendar.getInstance();
		
			calendar.setTime(reportParams.getStartDate());
			if(result!=null){
				HashMap<String,String> colMap = (HashMap)result.get(0);
				List< String> dates = new ArrayList<>();
				while (reportParams.getStartDate().before(reportParams.getEndDate()) || reportParams.getStartDate().equals(reportParams.getEndDate())) {
					String date  = dt1.format(reportParams.getStartDate());
					
					reportParams.setStartDate(calendar.getTime());
					date  = dt1.format(reportParams.getStartDate());
					if(date.startsWith("0")){
						date = date.substring(1,date.length());
					} 
					if (colMap.get(date) !=null){
						boolean dateExist = false;
						for(String dateStr: dates){
							if(dateStr.equals(date)){
								dateExist = true;
							}
						}
						if(!dateExist){
							dates.add(date);
						}
					}
					if(reportParams.getReportDateType().equals("monthly")){
						calendar.add(Calendar.DAY_OF_MONTH, 30);
					}else if(reportParams.getReportDateType().equals("yearly")){
						calendar.add(Calendar.DAY_OF_MONTH, 365);
					}else{
						calendar.add(Calendar.DAY_OF_MONTH, 1);
					}
					
					
				}
				columns.addAll(dates);
				
				if(reportParams.getBaseColumn()!=null){
					for(String columnName: reportParams.getPrintColumns().split(",")){
						columns.add(columnName);
					}
				}
				List newResult = null;
				 if(reportParams.getReportDateType().equals("Daily")){
					 newResult = getSessionFactory()
								.getCurrentSession().createSQLQuery(
								 "CALL Pivot(:param1,:param2,:param3,:param4,:param5,:param6,:param7,:param8)")
								 .setParameter("param1", reportParams.getTableName())
								 .setParameter("param2", reportParams.getMainBaseColumn())
								 .setParameter("param3", reportParams.getBaseColumn())
								 .setParameter("param4", reportParams.getPivotColumn())
								 .setParameter("param5", reportParams.getTallyColumn())
								 .setParameter("param6", reportParams.getWhereClause())
								 .setParameter("param7", "")
								 .setParameter("param8", reportParams.getGroupBy())
								 .list();
				 }else{
					 newResult = getSessionFactory()
								.getCurrentSession().createSQLQuery(
								 "CALL Pivot_Summarize(:param1,:param2,:param3,:param4,:param5,:param6,:param7,:param8,:param9)")
								 .setParameter("param1", reportParams.getTableName())
								 .setParameter("param2", reportParams.getMainBaseColumn())
								 .setParameter("param3", reportParams.getBaseColumn())
								 .setParameter("param4", reportParams.getPivotColumn())
								 .setParameter("param5", reportParams.getTallyColumn())
								 .setParameter("param6", reportParams.getWhereClause())
								 .setParameter("param7", "")
								 .setParameter("param8", reportParams.getGroupBy())
								 .setParameter("param9", reportParams.getReportDateType())
								 .list();
				 }
//				Collections.reverse(columns); 

				
				for(int index=0; index<newResult.size(); index++){
					Row row = new Row();
					List<String> colums = new ArrayList<>();
					Object[] data = (Object[]) newResult.get(index);
					for(int i=0;i<data.length;i++){
						String value = String.valueOf(data[i]);
						if(value.equalsIgnoreCase("null")){
							value = "SUM";
						}
						colums.add(value);
					}	
					row.setColumns(colums);
					rows.add(row);
				}
			}

			
//			List<Row> rows = new ArrayList<>();
			
			
			
//			for(Object obj: result){
//				Row row = new Row();
//				List<String> colums = new ArrayList<>();
//				HashMap<String,String> map = (HashMap<String,String> ) obj;
//				for(Object value : map.values()){
//					colums.add(String.valueOf(value));
//				}
//				row.setColumns(colums);
//				rows.add(row);
//				}	
//				
				
			
						
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
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}

	@Override
	public TableData getSalesReportByStartDateEndDate(Date startDate,
			Date endDate, int companyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean runDailyScript() {
		// TODO Auto-generated method stub
		try{
			getSessionFactory()
			.getCurrentSession().createSQLQuery(
			 "CALL RunDaily()").executeUpdate();
			return true;
		}catch (HibernateException ex) {
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return false;
	}
	
	@SuppressWarnings({"rawtypes" })
	@Override
	public TableData getTodaySalesByCompanyIdOutletIdDate(int companyId, int outletId,Date date,boolean isHeadOffice) {
		// TODO Auto-generated method stub
		try {
			TableData tableData = new TableData();
			List result =  null;
			try{
				result = getSessionFactory()
						.getCurrentSession().createSQLQuery(
						 "CALL GetTodaySalesByProduct(:param1,:param2,:param3)")
						 .setParameter("param1", companyId)
						 .setParameter("param2", outletId)
						 .setParameter("param3", date)
						 .list();
				
			 
			}catch(Exception exception){
				
			}
			List<String> columns = new ArrayList<>();
			List<Row> rows = new ArrayList<>();
		
			if(result!=null){
				if(isHeadOffice){
					String dbColumns = "Product,Outlet,Date,Revenue,Revenue(tax incl),Cost Of Goods,Gross Profit,Margin,Items Sold,Tax";
					
					
					if(dbColumns!=null){
						for(String columnName: dbColumns.split(",")){
							/*if(!isHeadOffice&& columnName.equalsIgnoreCase("Supplier"))
								continue;*/
							columns.add(columnName);
						}
					}
				}else{
					String dbColumns = "Product,Outlet,Date,Revenue,Revenue(tax incl),Items Sold,Tax";
					
					
					if(dbColumns!=null){
						for(String columnName: dbColumns.split(",")){
							/*if(!isHeadOffice&& columnName.equalsIgnoreCase("Supplier"))
								continue;*/
							columns.add(columnName);
						}
					}
				}
				


				Row footerRow = new Row();
				double totalRevenue = 0;
				double totalRevenueIncTax = 0;
				double totalCostOfGoods = 0;
				double totalGrossProfit = 0;
				double totalMargin = 0;
				double totalItemsSold = 0;
				for(int index=0; index<result.size(); index++){
					Row row = new Row();
					List<String> colums = new ArrayList<>();
					try{
//						"Product,Outlet,Date,Revenue,Revenue(tax incl),Cost Of Goods,Gross Profit,Margin,Items Sold,Tax";
						Object[] data = (Object[]) result.get(index);
						if(isHeadOffice){
							colums.add(String.valueOf(data[0]));
							colums.add(String.valueOf(data[1]));
							colums.add(String.valueOf(data[2]));
							colums.add(String.valueOf(data[3]));
							totalRevenue = totalRevenue+Double.valueOf(String.valueOf(data[3]));
							colums.add(String.valueOf(data[4]));
							totalRevenueIncTax = totalRevenueIncTax+Double.valueOf(String.valueOf(data[4]));
							colums.add(String.valueOf(data[5]));
							totalCostOfGoods = totalCostOfGoods+Double.valueOf(String.valueOf(data[5]));
							colums.add(String.valueOf(data[6]));
							totalGrossProfit = totalGrossProfit+Double.valueOf(String.valueOf(data[6]));
							colums.add(String.valueOf(data[7]));
							totalMargin = totalMargin+Double.valueOf(String.valueOf(data[7]));
							colums.add(String.valueOf(data[8]));
							totalItemsSold = totalItemsSold+Double.valueOf(String.valueOf(data[8]));
							colums.add(String.valueOf(data[9]));
						}else{
							colums.add(String.valueOf(data[0]));
							colums.add(String.valueOf(data[1]));
							colums.add(String.valueOf(data[2]));
							colums.add(String.valueOf(data[3]));
							totalRevenue = totalRevenue+Double.valueOf(String.valueOf(data[3]));
							colums.add(String.valueOf(data[4]));
							totalRevenueIncTax = totalRevenueIncTax+Double.valueOf(String.valueOf(data[4]));
							//colums.add(String.valueOf(data[5]));
							//totalCostOfGoods = totalCostOfGoods+Double.valueOf(String.valueOf(data[5]));
						//	colums.add(String.valueOf(data[6]));
						//	totalGrossProfit = totalGrossProfit+Double.valueOf(String.valueOf(data[6]));
//							colums.add(String.valueOf(data[7]));
//							totalMargin = totalMargin+Double.valueOf(String.valueOf(data[7]));
							colums.add(String.valueOf(data[8]));
							totalItemsSold = totalItemsSold+Double.valueOf(String.valueOf(data[8]));
							colums.add(String.valueOf(data[9]));
						}
						
					}catch(Exception ex){
						ex.printStackTrace();// logger.error(ex.getMessage(),ex);
					}
					
					row.setColumns(colums);
					rows.add(row);
				}
				List<String> colums = new ArrayList<>();
				if(isHeadOffice){
					colums.add("Sum");
					colums.add("-");
					colums.add("-");
					colums.add(totalRevenue+"");
					
					colums.add(totalRevenueIncTax+"");
					colums.add(totalCostOfGoods+"");
					colums.add(totalGrossProfit+"");
					totalMargin = totalMargin/result.size();
					DecimalFormat fmt = new DecimalFormat ("0.##");
					colums.add(fmt.format(totalMargin)+"");
					colums.add(totalItemsSold+"");
					colums.add("-");
					footerRow.setColumns(colums);
					rows.add(footerRow);
				}else{
					colums.add("Sum");
					colums.add("-");
					colums.add("-");
					colums.add(totalRevenue+"");
					
					colums.add(totalRevenueIncTax+"");
//					colums.add(totalCostOfGoods+"");
//					colums.add(totalGrossProfit+"");
//					colums.add(totalMargin+"");
					colums.add(totalItemsSold+"");
					colums.add("-");
					footerRow.setColumns(colums);
					rows.add(footerRow);
				}

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
			ex.printStackTrace();// logger.error(ex.getMessage(),ex);
		}
		return null;
	}
}
