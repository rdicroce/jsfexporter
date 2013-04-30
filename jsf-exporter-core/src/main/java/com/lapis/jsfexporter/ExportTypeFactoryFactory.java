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
