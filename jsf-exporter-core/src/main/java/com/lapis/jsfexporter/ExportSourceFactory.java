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

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lapis.jsfexporter.spi.IExportSource;

public class ExportSourceFactory {

	private static final Logger L = LoggerFactory.getLogger(ExportSourceFactory.class);
	
	private static final String KEY = ExportSourceFactory.class.getName();

	public static void initialize(FacesContext context) {
		Map<Class<?>, IExportSource<?, ?>> sources = new HashMap<Class<?>, IExportSource<?, ?>>();

		ServiceLoader<IExportSource> loader = ServiceLoader.load(IExportSource.class);
		for (IExportSource<?, ?> source : loader) {
			sources.put(source.getSourceType(), source);
			L.debug("Registered class {} for export source type {}", source.getClass().getName(), source.getSourceType().getName());
		}

		context.getExternalContext().getApplicationMap().put(KEY, sources);
	}
	
	public static <T extends UIComponent, C> IExportSource<T, C> getExportSource(FacesContext context, T componentToExport) {
		Map<Class<?>, IExportSource<?, ?>> sources = (Map<Class<?>, IExportSource<?, ?>>) context.getExternalContext().getApplicationMap().get(KEY);
		
		if (sources.containsKey(componentToExport.getClass())) {
			return (IExportSource<T, C>) sources.get(componentToExport.getClass());
		} else {
			throw new IllegalArgumentException("Could not find an IExportSource implementation for export source " + componentToExport.getClass().getName());
		}
	}

}
