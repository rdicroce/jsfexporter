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
import java.util.Map;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

@ManagedBean
@ViewScoped
public class LazyTableBean {

	private static final String[] COLORS = new String[]
			{"Black", "White", "Green", "Red", "Blue", "Orange", "Silver", "Yellow", "Brown", "Maroon"};
	private static final String[] MAKES = new String[]
			{"Mercedes", "BMW", "Volvo", "Audi", "Renault", "Opal", "Volkswagen", "Chrysler", "Ferrari", "Ford"};
	
	private LazyCarModel model;
	
	@PostConstruct
	protected void init() {
		model = new LazyCarModel();
	}
	
	public LazyCarModel getModel() {
		return model;
	}

	private class LazyCarModel extends LazyDataModel<Car> {
		private static final long serialVersionUID = 1L;
		
		private List<Car> cars;
		
		public LazyCarModel() {
			Random rng = new Random();
			
			cars = new ArrayList<Car>();
			for (int i = 0; i < 50; i++) {
				cars.add(new Car(MAKES[rng.nextInt(10)], COLORS[rng.nextInt(10)], 1970 + rng.nextInt(43), new BigDecimal(rng.nextInt(100000))));
			}
		}
		
		@Override
		public List<Car> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
			return cars.subList(first, Math.min(cars.size(), first + pageSize));
		}

		@Override
		public List<Car> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
			return load(first, pageSize, null, filters);
		}

		@Override
		public int getRowCount() {
			return cars.size();
		}
		
	}
	
}
