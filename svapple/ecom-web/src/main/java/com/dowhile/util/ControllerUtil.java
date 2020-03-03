/**
 * 
 */
package com.dowhile.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dowhile.User;
import com.dowhile.constants.HomeControllerBean;
import com.dowhile.frontend.mapping.bean.DataPoints;
import com.dowhile.frontend.mapping.bean.OutletBean;
import com.dowhile.frontend.mapping.bean.VarientValueBean;

/**
 * @author Yameen Bashir
 *
 */
public class ControllerUtil {

	public static int  getTotlalQuantityForAllOutlets(List<OutletBean> outletsist){
		int totalCount = 0;
		try{
			if(outletsist!=null){
				for(OutletBean outletBean:outletsist){
					if(outletBean.getCurrentInventory()!=null && !outletBean.getCurrentInventory().equalsIgnoreCase("")){
						totalCount = Integer.valueOf(outletBean.getCurrentInventory())+totalCount;
					}
					
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return totalCount;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map getTotalCurrentInventoryAgainstEeachOutlet(List<VarientValueBean> varientValuesBeanCollection){
		Map outletsCountMap = new HashMap<>();
		try{
			if(varientValuesBeanCollection!=null){
				for(VarientValueBean varientValueBean:varientValuesBeanCollection){
					List<OutletBean> variantOutletBeanList = varientValueBean.getVarientsOutletList();
					if(variantOutletBeanList!=null){
						for(OutletBean variantOutletBean:variantOutletBeanList){
							 if (outletsCountMap.containsKey(variantOutletBean.getOutletId())) {
						            //key exists
								 int currentCount = (Integer)(outletsCountMap.get(variantOutletBean.getOutletId()));
								 currentCount = Integer.valueOf(variantOutletBean.getCurrentInventory())+currentCount;
								 outletsCountMap.put(variantOutletBean.getOutletId(), currentCount);
						            
						        } else {
						            //key does not exists
						        	outletsCountMap.put(variantOutletBean.getOutletId(), Integer.valueOf(variantOutletBean.getCurrentInventory()));
						        }
						}
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();logger.error(ex.getMessage(),ex);
		}
		return outletsCountMap;
	}
	
	public static int getOutletIdByOutletName(String outletName,List<OutletBean> outletBeans){
		if(outletBeans!=null && outletBeans.size()>0){
			for(OutletBean outletBean:outletBeans){
				if(outletName.equalsIgnoreCase(outletBean.getOutletName())){
					return Integer.valueOf(outletBean.getOutletId());
				}
			}
		}
		
		return 0;
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static void   getDefaultData (List graphData , Calendar calendar, HomeControllerBean controllerBean, User currentUser , HttpServletRequest request,String type){
		List<DataPoints> dataPointsRevenue = new ArrayList<>();
		double todayValue = 0 , yesterdayValue ;
		try{
		if(graphData!=null){
			Calendar todayClaendar = Calendar.getInstance();
			todayClaendar.setTime(new Date());
			Map<String, String> weekmap =new HashMap<String, String>();
			for(int index=0; index<graphData.size(); index++){
				Object[] data = (Object[]) graphData.get(index);
				Calendar dateCalendaer = Calendar.getInstance();
				dateCalendaer.setTime((Date)data[0]);
				weekmap.put(dateCalendaer.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US)+" "+dateCalendaer.get (Calendar.DAY_OF_MONTH), String.valueOf(data[1]));
				
			}
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			for(int index=1; index<=7; index++){
				calendar.add(Calendar.DAY_OF_MONTH, 1);
				String date  = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US)+" "+calendar.get (Calendar.DAY_OF_MONTH);
				String todayClaendardate  = todayClaendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US)+" "+todayClaendar.get (Calendar.DAY_OF_MONTH);
				
				if(date.equals(todayClaendardate)){
					
					if(weekmap.get(todayClaendardate)!=null){
						if(weekmap.get(todayClaendardate)!="null"){
							todayValue = Double.parseDouble(weekmap.get(todayClaendardate));
							
						}else{
							todayValue = 0.00;
						}
						
						if(type.equals("revenue")){
							controllerBean.setTodayRevenue(todayValue);
						}
						if(type.equals("salesCount")){
							controllerBean.setTodaySalesCount(todayValue);
							
						}
						if(type.equals("customerCount")){
							controllerBean.setTodayCustomerCount(todayValue);
							
						}
						if(type.equals("grossProfit")){
							controllerBean.setTodayGrossProfit(todayValue);
							
						}
						if(type.equals("discount")){
							controllerBean.setTodayDiscount(todayValue);
							
						}
						if(type.equals("basketSize")){
							controllerBean.setTodayBasketSize(todayValue);
							
						}
						if(type.equals("basketValue")){
							controllerBean.setTodayBasketValue(todayValue);
							
						}if(type.equals("discountPerc")){
							controllerBean.setTodayDiscountPec(todayValue);
							
						}
						
					}else{
						if(type.equals("revenue")){
							controllerBean.setTodayRevenue(Double.valueOf("0.00"));
							
						}
						if(type.equals("salesCount")){
							controllerBean.setTodaySalesCount(Double.valueOf("0.00"));
						}
						if(type.equals("customerCount")){
							controllerBean.setTodayCustomerCount(Double.valueOf("0.00"));
							
						}
						if(type.equals("grossProfit")){
							controllerBean.setTodayGrossProfit(Double.valueOf("0.00"));
							
						}
						if(type.equals("discount")){
							controllerBean.setTodayDiscount(Double.valueOf("0.00"));
							
						}
						if(type.equals("basketSize")){
							controllerBean.setTodayBasketSize(Double.valueOf("0.00"));
							
						}
						if(type.equals("basketValue")){
							controllerBean.setTodayBasketValue(Double.valueOf("0.00"));
							
						}if(type.equals("discountPerc")){
							controllerBean.setTodayDiscountPec(Double.valueOf("0.00"));
							
						}
					}
					
					todayClaendar.add(Calendar.DAY_OF_MONTH, -1);
					 todayClaendardate  = todayClaendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US)+" "+todayClaendar.get (Calendar.DAY_OF_MONTH);
					
					if(weekmap.get(todayClaendardate)!=null && weekmap.get(todayClaendardate)!="null"){
						yesterdayValue = Double.parseDouble(weekmap.get(todayClaendardate));
						Double revenue = ((todayValue/yesterdayValue)*100)-100;
						if(type.equals("revenue")){
							controllerBean.setRevenuePercentage((round(revenue,2)));
						}
						if(type.equals("salesCount")){
							controllerBean.setSalesCountPercentage(round(revenue,2));
						}
						if(type.equals("customerCount")){
							controllerBean.setCustomerCountPercentage(round(revenue,2));
							
						}
						if(type.equals("grossProfit")){
							controllerBean.setGrossProfitPercentage(round(revenue,2));
							
						}
						if(type.equals("discount")){
							controllerBean.setDiscountPercentage(round(revenue,2));
							
						}
						if(type.equals("basketSize")){
							controllerBean.setBasketSizePercentage(round(revenue,2));
							
						}
						if(type.equals("basketValue")){
							controllerBean.setBasketValuePercentage(round(revenue,2));
							
						}if(type.equals("discountPerc")){
							controllerBean.setDiscountPecPercentage(round(revenue,2));
							
						}
						
						
					}else{
						yesterdayValue = 1.0;
						Double revenue = ((todayValue/yesterdayValue));
						
						if(type.equals("revenue")){
							controllerBean.setRevenuePercentage(revenue);
						}
						if(type.equals("salesCount")){
							controllerBean.setSalesCountPercentage(revenue);
							
						}
						if(type.equals("customerCount")){
							controllerBean.setCustomerCountPercentage(revenue);
							
						}
						if(type.equals("grossProfit")){
							controllerBean.setGrossProfitPercentage(revenue);
						}
						if(type.equals("discount")){
							controllerBean.setDiscountPercentage(revenue);
						}
						if(type.equals("basketSize")){
							controllerBean.setBasketSizePercentage(revenue);
							
						}
						if(type.equals("basketValue")){
							controllerBean.setBasketValuePercentage(revenue);
							
						}if(type.equals("discountPerc")){
							controllerBean.setDiscountPecPercentage(revenue);
						
						}
						
						
					}
					
					
				}
				
				DataPoints dataPoints = new DataPoints();
				if(weekmap.get(date)!=null && weekmap.get(date)!="null"){
					dataPoints.setLabel(date);
					dataPoints.setY(Double.parseDouble(weekmap.get(date)));
				}else{
					dataPoints.setLabel(date);
					dataPoints.setY(0.00);
				}
				dataPointsRevenue.add(dataPoints);
			}
			if(type.equals("revenue")){
				controllerBean.setDataPointsRevenue(dataPointsRevenue);
				
			}
			if(type.equals("salesCount")){
				controllerBean.setDataPointsSaleCount(dataPointsRevenue);
				
			}
			if(type.equals("customerCount")){
				controllerBean.setDataPointsCustomerCount(dataPointsRevenue);
				
			}
			if(type.equals("grossProfit")){
				controllerBean.setDataPointsGrossProfit(dataPointsRevenue);
			}
			if(type.equals("discount")){
				controllerBean.setDataPointsDiscount(dataPointsRevenue);
			}
			if(type.equals("basketSize")){
				controllerBean.setDataPointsBasketSize(dataPointsRevenue);
				
			}
			if(type.equals("basketValue")){
				controllerBean.setDataPointsBasketValue(dataPointsRevenue);
				
			}if(type.equals("discountPerc")){
				controllerBean.setDataPointsDiscountPercent(dataPointsRevenue);
				
			}
		}
		}catch(Exception e){
			e.printStackTrace();logger.error(e.getMessage(),e);
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
//			util.AuditTrail(request, currentUser, "HomeController.getRevenueData",
//					"Error Occured " + errors.toString(),true);
		}
		return ;
		
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
}
