/*
 * #%L
 * Lapis JSF Exporter Core
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
package com.lapis.jsfexporter;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lapis.jsfexporter.spi.IExportTypeFactory;

public class ExportTypeFactoryFactory {

	private static final Logger L = LoggerFactory.getLogger(ExportTypeFactoryFactory.class);
	
	private static final String KEY = ExportTypeFactoryFactory.class.getName();
	
	public static void initialize(FacesContext context) {
		Map<String, IExportTypeFactory<?, ?, ?>> factories = new HashMap<String, IExportTypeFactory<?, ?, ?>>();
			
		ServiceLoader<IExportTypeFactory> loader = ServiceLoader.load(IExportTypeFactory.class);
		for (IExportTypeFactory<?, ?, ?> availableFactory : loader) {
			factories.put(availableFactory.getExportTypeId(), availableFactory);
			L.debug("Registered class {} for export type ID {}", availableFactory.getClass().getName(), availableFactory.getExportTypeId());
		}
		
		context.getExternalContext().getApplicationMap().put(KEY, factories);
	}
	
	public static IExportTypeFactory<?, ?, ?> getExportType(FacesContext context, String type) {
		Map<String, IExportTypeFactory<?, ?, ?>> factories = (Map<String, IExportTypeFactory<?, ?, ?>>) context.getExternalContext().getApplicationMap().get(KEY);
		
		if (factories.containsKey(type)) {
			return factories.get(type);
		} else {
			throw new IllegalArgumentException("Could not find an IExportType implementation for export type " + type);
		}
	}
	
}
