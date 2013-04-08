package com.lapis.pfexporter;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import javax.faces.context.FacesContext;

import com.lapis.pfexporter.spi.IExportType;

public class ExportTypeFactory {

	private static final String KEY = ExportTypeFactory.class.getName();
	
	public static IExportType<?, ?> getExportFormat(FacesContext context, String type) {
		Map<String, IExportType<?, ?>> types = (Map<String, IExportType<?, ?>>) context.getExternalContext().getApplicationMap().get(KEY);
		if (types == null) {
			types = new HashMap<String, IExportType<?,?>>();
			
			ServiceLoader<IExportType> loader = ServiceLoader.load(IExportType.class);
			for (IExportType<?, ?> availableType : loader) {
				types.put(availableType.getFileExtension(), availableType);
			}
			
			context.getExternalContext().getApplicationMap().put(KEY, types);
		}
		
		if (types.containsKey(type)) {
			return types.get(type);
		} else {
			throw new IllegalArgumentException("Could not find any IExportType for type " + type);
		}
	}
	
}
