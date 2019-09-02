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
	List<DataPoints> dataPointsGrossProfit;
	List<DataPoints> dataPointsRevenue;
	List<DataPoints> dataPointsSaleCount;
	List<DataPoints> dataPointsCustomerCount;
	List<DataPoints> dataPointsDiscount;
	List<DataPoints> dataPointsDiscountPercent;
	List<DataPoints> dataPointsBasketSize;
	List<DataPoints> dataPointsBasketValue;
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
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
	public String getOutletName() {
		return outletName;
	}
	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}
	public boolean isHideSalesDetails() {
		return hideSalesDetails;
	}
	public void setHideSalesDetails(boolean hideSalesDetails) {
		this.hideSalesDetails = hideSalesDetails;
	}

}
