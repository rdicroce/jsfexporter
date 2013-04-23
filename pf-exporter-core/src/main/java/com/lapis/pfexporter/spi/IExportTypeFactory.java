package com.lapis.pfexporter.spi;

import com.lapis.pfexporter.api.IExportType;

public interface IExportTypeFactory<T, C, R> {

	IExportType<T, C, R> createNewExporter(C configOptions);
	C getDefaultConfigOptions();
	String getContentType();
	String getFileExtension();
	
}
