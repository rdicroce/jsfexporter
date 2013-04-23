package com.lapis.pfexporter;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import javax.faces.context.FacesContext;

import com.lapis.pfexporter.spi.IExportTypeFactory;

public class ExportTypeFactoryFactory {

	private static final String KEY = ExportTypeFactoryFactory.class.getName();
	
	public static IExportTypeFactory<?, ?, ?> getExportType(FacesContext context, String type) {
		Map<String, IExportTypeFactory<?, ?, ?>> factories = (Map<String, IExportTypeFactory<?, ?, ?>>) context.getExternalContext().getApplicationMap().get(KEY);
		if (factories == null) {
			factories = new HashMap<String, IExportTypeFactory<?, ?, ?>>();
			
			ServiceLoader<IExportTypeFactory> loader = ServiceLoader.load(IExportTypeFactory.class);
			for (IExportTypeFactory<?, ?, ?> availableFactory : loader) {
				factories.put(availableFactory.getFileExtension(), availableFactory);
			}
			
			context.getExternalContext().getApplicationMap().put(KEY, factories);
		}
		
		if (factories.containsKey(type)) {
			return factories.get(type);
		} else {
			throw new IllegalArgumentException("Could not find any IExportType for type " + type);
		}
	}
	
}
