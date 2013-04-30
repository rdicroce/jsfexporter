package com.lapis.jsfexporter.spi;

import com.lapis.jsfexporter.api.IExportType;

public interface IExportTypeFactory<T, C, R> {

	IExportType<T, C, R> createNewExporter(C configOptions);
	C getDefaultConfigOptions();
	String getExportTypeId();
	
}
