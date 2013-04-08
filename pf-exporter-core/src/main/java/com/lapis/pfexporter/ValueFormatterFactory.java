package com.lapis.pfexporter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.lapis.pfexporter.spi.IValueFormatter;

public class ValueFormatterFactory {

	private static final String LOADED_KEY = ValueFormatterFactory.class.getName() + "-loaded";
	private static final String COMPUTED_KEY = ValueFormatterFactory.class.getName() + "-computed";
	
	public static <T extends UIComponent> IValueFormatter<T> getValueFormatter(FacesContext context, T componentToExport) {
		Map<String, Object> applicationMap = context.getExternalContext().getApplicationMap();
		
		ConcurrentMap<Class<?>, IValueFormatter<?>> formatters = (ConcurrentMap<Class<?>, IValueFormatter<?>>) applicationMap.get(COMPUTED_KEY);
		if (formatters == null) {
			formatters = new ConcurrentHashMap<Class<?>, IValueFormatter<?>>();
			applicationMap.put(COMPUTED_KEY, formatters);
		}
		
		Class<?> componentType = componentToExport.getClass();
		IValueFormatter<T> formatter = (IValueFormatter<T>) formatters.get(componentType);
		if (formatter == null) {
			List<IValueFormatter<?>> availableFormatters = (List<IValueFormatter<?>>) applicationMap.get(LOADED_KEY);
			if (availableFormatters == null) {
				availableFormatters = new ArrayList<IValueFormatter<?>>();
				
				ServiceLoader<IValueFormatter> loader = ServiceLoader.load(IValueFormatter.class);
				for (IValueFormatter<?> loadedFormatter : loader) {
					availableFormatters.add(loadedFormatter);
				}
				
				applicationMap.put(LOADED_KEY, availableFormatters);
			}
			
			for (IValueFormatter<?> availableFormatter : availableFormatters) {
				if (availableFormatter.getSupportedClass().isAssignableFrom(componentType) &&
						(formatter == null || formatter.getPrecedence() < availableFormatter.getPrecedence())) {
					formatter = (IValueFormatter<T>) availableFormatter;
				}
			}
			
			formatters.putIfAbsent(componentType, formatter);
		}
		
		return formatter;
	}
	
}
