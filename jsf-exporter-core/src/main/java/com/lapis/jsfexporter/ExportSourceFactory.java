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
