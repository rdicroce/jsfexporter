package com.lapis.pfexporter.spi;

import javax.faces.context.FacesContext;

import com.lapis.pfexporter.api.IDataProvider;

public interface IExportSource<T, C> {

	IDataProvider exportData(T source, C configOptions, FacesContext context) throws Exception;
	Class<T> getSourceType();
	C getDefaultConfigOptions();

}
