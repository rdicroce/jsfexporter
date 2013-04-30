package com.lapis.jsfexporter.spi;

import javax.faces.context.FacesContext;

public interface IValueFormatter<T> {

	Class<T> getSupportedClass();
	int getPrecedence();
	String formatValue(FacesContext context, T component);
	
}
