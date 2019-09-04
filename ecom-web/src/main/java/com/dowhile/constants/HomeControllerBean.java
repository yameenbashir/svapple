package com.dowhile.constants;

import java.util.List;

import com.dowhile.frontend.mapping.bean.DataPoints;

public class HomeControllerBean {
	
	private boolean hideSalesDetails;
	String startDate;
	String outletName;
	String lasttDate;
	String dashBoardType;
	double todayRevenue;
	double revenuePercentage;
	double todaySalesCount;
	double salesCountPercentage;
	double todayCustomerCount;
	double customerCountPercentage;
	double todayGrossProfit;
	double grossProfitPercentage;
	double todayDiscount;
	double discountPercentage;
	double todayDiscountPec;
	double discountPecPercentage;
	double todayBasketValue;
	double basketValuePercentage;
	double todayBasketSize;
	double basketSizePercentage;
	boolean revenue;
	boolean saleCount;
	boolean customerCount;
	boolean grossProfit;
	boolean discount;
	boolean discountPercent;
	boolean basketValue;
	boolean basketSize;
	List<DataPoints> dataPointsGrossProfit;
	List<DataPoints> dataPointsRevenue;
	List<DataPoints> dataPointsSaleCount;
	List<DataPoints> dataPointsCustomerCount;
	List<DataPoints> dataPointsDiscount;
	List<DataPoints> dataPointsDiscountPercent;
	List<DataPoints> dataPointsBasketSize;
	List<DataPoints> dataPointsBasketValue;
	/**
	 * 
	 */
	public HomeControllerBean() {
	}
	/**
	 * @param hideSalesDetails
	 * @param startDate
	 * @param outletName
	 * @param lasttDate
	 * @param dashBoardType
	 * @param todayRevenue
	 * @param revenuePercentage
	 * @param todaySalesCount
	 * @param salesCountPercentage
	 * @param todayCustomerCount
	 * @param customerCountPercentage
	 * @param todayGrossProfit
	 * @param grossProfitPercentage
	 * @param todayDiscount
	 * @param discountPercentage
	 * @param todayDiscountPec
	 * @param discountPecPercentage
	 * @param todayBasketValue
	 * @param basketValuePercentage
	 * @param todayBasketSize
	 * @param basketSizePercentage
	 * @param revenue
	 * @param saleCount
	 * @param customerCount
	 * @param grossProfit
	 * @param discount
	 * @param discountPercent
	 * @param basketValue
	 * @param basketSize
	 * @param dataPointsGrossProfit
	 * @param dataPointsRevenue
	 * @param dataPointsSaleCount
	 * @param dataPointsCustomerCount
	 * @param dataPointsDiscount
	 * @param dataPointsDiscountPercent
	 * @param dataPointsBasketSize
	 * @param dataPointsBasketValue
	 */
	public HomeControllerBean(boolean hideSalesDetails, String startDate,
			String outletName, String lasttDate, String dashBoardType,
			double todayRevenue, double revenuePercentage,
			double todaySalesCount, double salesCountPercentage,
			double todayCustomerCount, double customerCountPercentage,
			double todayGrossProfit, double grossProfitPercentage,
			double todayDiscount, double discountPercentage,
			double todayDiscountPec, double discountPecPercentage,
			double todayBasketValue, double basketValuePercentage,
			double todayBasketSize, double basketSizePercentage,
			boolean revenue, boolean saleCount, boolean customerCount,
			boolean grossProfit, boolean discount, boolean discountPercent,
			boolean basketValue, boolean basketSize,
			List<DataPoints> dataPointsGrossProfit,
			List<DataPoints> dataPointsRevenue,
			List<DataPoints> dataPointsSaleCount,
			List<DataPoints> dataPointsCustomerCount,
			List<DataPoints> dataPointsDiscount,
			List<DataPoints> dataPointsDiscountPercent,
			List<DataPoints> dataPointsBasketSize,
			List<DataPoints> dataPointsBasketValue) {
		this.hideSalesDetails = hideSalesDetails;
		this.startDate = startDate;
		this.outletName = outletName;
		this.lasttDate = lasttDate;
		this.dashBoardType = dashBoardType;
		this.todayRevenue = todayRevenue;
		this.revenuePercentage = revenuePercentage;
		this.todaySalesCount = todaySalesCount;
		this.salesCountPercentage = salesCountPercentage;
		this.todayCustomerCount = todayCustomerCount;
		this.customerCountPercentage = customerCountPercentage;
		this.todayGrossProfit = todayGrossProfit;
		this.grossProfitPercentage = grossProfitPercentage;
		this.todayDiscount = todayDiscount;
		this.discountPercentage = discountPercentage;
		this.todayDiscountPec = todayDiscountPec;
		this.discountPecPercentage = discountPecPercentage;
		this.todayBasketValue = todayBasketValue;
		this.basketValuePercentage = basketValuePercentage;
		this.todayBasketSize = todayBasketSize;
		this.basketSizePercentage = basketSizePercentage;
		this.revenue = revenue;
		this.saleCount = saleCount;
		this.customerCount = customerCount;
		this.grossProfit = grossProfit;
		this.discount = discount;
		this.discountPercent = discountPercent;
		this.basketValue = basketValue;
		this.basketSize = basketSize;
		this.dataPointsGrossProfit = dataPointsGrossProfit;
		this.dataPointsRevenue = dataPointsRevenue;
		this.dataPointsSaleCount = dataPointsSaleCount;
		this.dataPointsCustomerCount = dataPointsCustomerCount;
		this.dataPointsDiscount = dataPointsDiscount;
		this.dataPointsDiscountPercent = dataPointsDiscountPercent;
		this.dataPointsBasketSize = dataPointsBasketSize;
		this.dataPointsBasketValue = dataPointsBasketValue;
	}
	public boolean isHideSalesDetails() {
		return hideSalesDetails;
	}
	public void setHideSalesDetails(boolean hideSalesDetails) {
		this.hideSalesDetails = hideSalesDetails;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public String getLasttDate() {
		return lasttDate;
	}
	public void setLasttDate(String lasttDate) {
		this.lasttDate = lasttDate;
	}
	public String getDashBoardType() {
		return dashBoardType;
	}
	public void setDashBoardType(String dashBoardType) {
		this.dashBoardType = dashBoardType;
	}
	public double getTodayRevenue() {
		return todayRevenue;
	}
	public void setTodayRevenue(double todayRevenue) {
		this.todayRevenue = todayRevenue;
	}
	public double getRevenuePercentage() {
		return revenuePercentage;
	}
	public void setRevenuePercentage(double revenuePercentage) {
		this.revenuePercentage = revenuePercentage;
	}
	public double getTodaySalesCount() {
		return todaySalesCount;
	}
	public void setTodaySalesCount(double todaySalesCount) {
		this.todaySalesCount = todaySalesCount;
	}
	public double getSalesCountPercentage() {
		return salesCountPercentage;
	}
	public void setSalesCountPercentage(double salesCountPercentage) {
		this.salesCountPercentage = salesCountPercentage;
	}
	public double getTodayCustomerCount() {
		return todayCustomerCount;
	}
	public void setTodayCustomerCount(double todayCustomerCount) {
		this.todayCustomerCount = todayCustomerCount;
	}
	public double getCustomerCountPercentage() {
		return customerCountPercentage;
	}
	public void setCustomerCountPercentage(double customerCountPercentage) {
		this.customerCountPercentage = customerCountPercentage;
	}
	public double getTodayGrossProfit() {
		return todayGrossProfit;
	}
	public void setTodayGrossProfit(double todayGrossProfit) {
		this.todayGrossProfit = todayGrossProfit;
	}
	public double getGrossProfitPercentage() {
		return grossProfitPercentage;
	}
	public void setGrossProfitPercentage(double grossProfitPercentage) {
		this.grossProfitPercentage = grossProfitPercentage;
	}
	public double getTodayDiscount() {
		return todayDiscount;
	}
	public void setTodayDiscount(double todayDiscount) {
		this.todayDiscount = todayDiscount;
	}
	public double getDiscountPercentage() {
		return discountPercentage;
	}
	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}
	public double getTodayDiscountPec() {
		return todayDiscountPec;
	}
	public void setTodayDiscountPec(double todayDiscountPec) {
		this.todayDiscountPec = todayDiscountPec;
	}
	public double getDiscountPecPercentage() {
		return discountPecPercentage;
	}
	public void setDiscountPecPercentage(double discountPecPercentage) {
		this.discountPecPercentage = discountPecPercentage;
	}
	public double getTodayBasketValue() {
		return todayBasketValue;
	}
	public void setTodayBasketValue(double todayBasketValue) {
		this.todayBasketValue = todayBasketValue;
	}
	public double getBasketValuePercentage() {
		return basketValuePercentage;
	}
	public void setBasketValuePercentage(double basketValuePercentage) {
		this.basketValuePercentage = basketValuePercentage;
	}
	public double getTodayBasketSize() {
		return todayBasketSize;
	}
	public void setTodayBasketSize(double todayBasketSize) {
		this.todayBasketSize = todayBasketSize;
	}
	public double getBasketSizePercentage() {
		return basketSizePercentage;
	}
	public void setBasketSizePercentage(double basketSizePercentage) {
		this.basketSizePercentage = basketSizePercentage;
	}
	public boolean isRevenue() {
		return revenue;
	}
	public void setRevenue(boolean revenue) {
		this.revenue = revenue;
	}
	public boolean isSaleCount() {
		return saleCount;
	}
	public void setSaleCount(boolean saleCount) {
		this.saleCount = saleCount;
	}
	public boolean isCustomerCount() {
		return customerCount;
	}
	public void setCustomerCount(boolean customerCount) {
		this.customerCount = customerCount;
	}
	public boolean isGrossProfit() {
		return grossProfit;
	}
	public void setGrossProfit(boolean grossProfit) {
		this.grossProfit = grossProfit;
	}
	public boolean isDiscount() {
		return discount;
	}
	public void setDiscount(boolean discount) {
		this.discount = discount;
	}
	public boolean isDiscountPercent() {
		return discountPercent;
	}
	public void setDiscountPercent(boolean discountPercent) {
		this.discountPercent = discountPercent;
	}
	public boolean isBasketValue() {
		return basketValue;
	}
	public void setBasketValue(boolean basketValue) {
		this.basketValue = basketValue;
	}
	public boolean isBasketSize() {
		return basketSize;
	}
	public void setBasketSize(boolean basketSize) {
		this.basketSize = basketSize;
	}
	public List<DataPoints> getDataPointsGrossProfit() {
		return dataPointsGrossProfit;
	}
	public void setDataPointsGrossProfit(List<DataPoints> dataPointsGrossProfit) {
		this.dataPointsGrossProfit = dataPointsGrossProfit;
	}
	public List<DataPoints> getDataPointsRevenue() {
		return dataPointsRevenue;
	}
	public void setDataPointsRevenue(List<DataPoints> dataPointsRevenue) {
		this.dataPointsRevenue = dataPointsRevenue;
	}
	public List<DataPoints> getDataPointsSaleCount() {
		return dataPointsSaleCount;
	}
	public void setDataPointsSaleCount(List<DataPoints> dataPointsSaleCount) {
		this.dataPointsSaleCount = dataPointsSaleCount;
	}
	public List<DataPoints> getDataPointsCustomerCount() {
		return dataPointsCustomerCount;
	}
	public void setDataPointsCustomerCount(List<DataPoints> dataPointsCustomerCount) {
		this.dataPointsCustomerCount = dataPointsCustomerCount;
	}
	public List<DataPoints> getDataPointsDiscount() {
		return dataPointsDiscount;
	}
	public void setDataPointsDiscount(List<DataPoints> dataPointsDiscount) {
		this.dataPointsDiscount = dataPointsDiscount;
	}
	public List<DataPoints> getDataPointsDiscountPercent() {
		return dataPointsDiscountPercent;
	}
	public void setDataPointsDiscountPercent(
			List<DataPoints> dataPointsDiscountPercent) {
		this.dataPointsDiscountPercent = dataPointsDiscountPercent;
	}
	public List<DataPoints> getDataPointsBasketSize() {
		return dataPointsBasketSize;
	}
	public void setDataPointsBasketSize(List<DataPoints> dataPointsBasketSize) {
		this.dataPointsBasketSize = dataPointsBasketSize;
	}
	public List<DataPoints> getDataPointsBasketValue() {
		return dataPointsBasketValue;
	}
	public void setDataPointsBasketValue(List<DataPoints> dataPointsBasketValue) {
		this.dataPointsBasketValue = dataPointsBasketValue;
	}
}
