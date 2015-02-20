package com.lapis.jsfexporter.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class DynamicColumnsTableBean {

	private List<Integer> columns;
	private List<Map<Integer, Integer>> data;
	
	@PostConstruct
	void init() {
		columns = new ArrayList<Integer>();
		for (int i = 0; i < 3; i++) {
			columns.add(i);
		}
		
		Random rng = new Random();
		data = new ArrayList<Map<Integer,Integer>>();
		for (int i = 0; i < 50; i++) {
			Map<Integer, Integer> row = new HashMap<Integer, Integer>();
			for (int j = 0; j < columns.size(); j++) {
				row.put(j, rng.nextInt());
			}
			data.add(row);
		}
	}

	public List<Integer> getColumns() {
		return columns;
	}

	public List<Map<Integer, Integer>> getData() {
		return data;
	}
	
}
