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
