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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.lapis.jsfexporter.csv.CSVExportOptions;
import com.lapis.jsfexporter.primefaces.datatable.DataTableExportOptions;
import com.lapis.jsfexporter.primefaces.datatable.DataTableExportRange;

@ManagedBean
@ViewScoped
public class NonLazyTableBean {

	private static final String[] COLORS = new String[]
			{"Black", "White", "Green", "Red", "Blue", "Orange", "Silver", "Yellow", "Brown", "Maroon"};
	private static final String[] MAKES = new String[]
			{"Mercedes", "BMW", "Volvo", "Audi", "Renault", "Opal", "Volkswagen", "Chrysler", "Ferrari", "Ford"};
	
	private List<Car> cars;
	
	@PostConstruct
	protected void init() {
		Random rng = new Random();
		
		cars = new ArrayList<Car>();
		for (int i = 0; i < 10; i++) {
			cars.add(new Car(MAKES[rng.nextInt(10)], COLORS[rng.nextInt(10)], 1970 + rng.nextInt(43), new BigDecimal(rng.nextInt(100000))));
		}
	}
	
	public DataTableExportOptions getPFDTPageOnly() {
		return new DataTableExportOptions(DataTableExportRange.PAGE_ONLY);
	}
	
	public com.lapis.jsfexporter.richfaces.datatable.DataTableExportOptions getRFDTPageOnly() {
		return new com.lapis.jsfexporter.richfaces.datatable.DataTableExportOptions(
				com.lapis.jsfexporter.richfaces.datatable.DataTableExportRange.PAGE_ONLY);
	}
	
	public CSVExportOptions getCSVWithUTF8BOM() {
		CSVExportOptions options = new CSVExportOptions();
		options.setCharacterEncoding("UTF-8-with-bom");
		return options;
	}

	public List<Car> getCars() {
		return cars;
	}
	
}
