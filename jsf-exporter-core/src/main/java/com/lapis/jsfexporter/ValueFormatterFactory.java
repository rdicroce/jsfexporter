/*
 * #%L
 * Lapis JSF Exporter Core
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
package com.lapis.jsfexporter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lapis.jsfexporter.spi.IValueFormatter;

public class ValueFormatterFactory {

	private static final Logger L = LoggerFactory.getLogger(ValueFormatterFactory.class);
	
	private static final String LOADED_KEY = ValueFormatterFactory.class.getName() + "-loaded";
	private static final String COMPUTED_KEY = ValueFormatterFactory.class.getName() + "-computed";
	
	public static void initialize(FacesContext context) {
		List<IValueFormatter<?>> availableFormatters = new ArrayList<IValueFormatter<?>>();
		ServiceLoader<IValueFormatter> loader = ServiceLoader.load(IValueFormatter.class);
		for (IValueFormatter<?> loadedFormatter : loader) {
			availableFormatters.add(loadedFormatter);
			L.debug("Registered class {} for component type {}", loadedFormatter.getClass().getName(), loadedFormatter.getSupportedClass().getName());
		}
		
		Map<String, Object> applicationMap = context.getExternalContext().getApplicationMap();
		applicationMap.put(LOADED_KEY, availableFormatters);
		applicationMap.put(COMPUTED_KEY, new ConcurrentHashMap<Class<?>, IValueFormatter<?>>());
	}
	
	public static <T extends UIComponent> IValueFormatter<T> getValueFormatter(FacesContext context, T componentToExport) {
		Map<String, Object> applicationMap = context.getExternalContext().getApplicationMap();
		ConcurrentMap<Class<?>, IValueFormatter<?>> formatters = (ConcurrentMap<Class<?>, IValueFormatter<?>>) applicationMap.get(COMPUTED_KEY);
		Class<?> componentType = componentToExport.getClass();
		
		IValueFormatter<T> formatter = (IValueFormatter<T>) formatters.get(componentType);
		if (formatter == null) {
			List<IValueFormatter<?>> availableFormatters = (List<IValueFormatter<?>>) applicationMap.get(LOADED_KEY);
			for (IValueFormatter<?> availableFormatter : availableFormatters) {
				if (availableFormatter.getSupportedClass().isAssignableFrom(componentType) &&
						(formatter == null || formatter.getPrecedence() < availableFormatter.getPrecedence())) {
					formatter = (IValueFormatter<T>) availableFormatter;
				}
			}
			
			formatters.putIfAbsent(componentType, formatter);
			L.debug("Calculated formatter {} for component type {}", formatter.getClass().getName(), componentType.getName());
		}
		
		return formatter;
	}
	
}
