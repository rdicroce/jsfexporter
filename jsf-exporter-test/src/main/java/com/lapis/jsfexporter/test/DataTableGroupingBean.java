/*
 * #%L
 * Lapis JSF Exporter - Test WAR
 * %%
 * Copyright (C) 2013 - 2015 Lapis Software Associates
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class DataTableGroupingBean {

	private static final String[] MAKES = new String[]
			{"Mercedes", "BMW", "Volvo", "Audi", "Renault", "Opal", "Volkswagen", "Chrysler", "Ferrari", "Ford"};
	
	private List<CarSale> sales;
	private int lastYearCountTotal;
	private int thisYearCountTotal;
	
	@PostConstruct
	protected void init() {
		Random rng = new Random();
		
		sales = new ArrayList<CarSale>();
		for (String make : MAKES) {
			CarSale sale = new CarSale(make, rng.nextDouble(), rng.nextDouble(), rng.nextInt(100000), rng.nextInt(100000));
			sales.add(sale);
			lastYearCountTotal += sale.getLastYearCount();
			thisYearCountTotal += sale.getThisYearCount();
		}
	}

	public List<CarSale> getSales() {
		return sales;
	}

	public int getLastYearCountTotal() {
		return lastYearCountTotal;
	}

	public int getThisYearCountTotal() {
		return thisYearCountTotal;
	}
	
}
