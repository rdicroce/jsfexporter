package com.lapis.jsfexporter.test;

import java.math.BigDecimal;

public class Car {

	private String make;
	private String color;
	private int year;
	private BigDecimal price;
	
	public Car(String make, String color, int year, BigDecimal price) {
		this.make = make;
		this.color = color;
		this.year = year;
		this.price = price;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}
