/*
 * #%L
 * Lapis JSF Exporter - Test WAR
 * %%
 * Copyright (C) 2013 Lapis Software Associates
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
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
