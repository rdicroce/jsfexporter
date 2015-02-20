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
