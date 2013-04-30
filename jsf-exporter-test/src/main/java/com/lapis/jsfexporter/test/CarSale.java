package com.lapis.jsfexporter.test;

public class CarSale {

	private String make;
	private double lastYearProfit;
	private double thisYearProfit;
	private int lastYearCount;
	private int thisYearCount;
	
	public CarSale(String make, double lastYearProfit, double thisYearProfit, int lastYearCount, int thisYearCount) {
		this.make = make;
		this.lastYearProfit = lastYearProfit;
		this.thisYearProfit = thisYearProfit;
		this.lastYearCount = lastYearCount;
		this.thisYearCount = thisYearCount;
	}

	public String getMake() {
		return make;
	}

	public double getLastYearProfit() {
		return lastYearProfit;
	}

	public double getThisYearProfit() {
		return thisYearProfit;
	}

	public int getLastYearCount() {
		return lastYearCount;
	}

	public int getThisYearCount() {
		return thisYearCount;
	}
	
}
