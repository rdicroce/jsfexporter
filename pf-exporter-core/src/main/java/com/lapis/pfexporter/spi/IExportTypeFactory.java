package com.lapis.pfexporter.spi;

import com.lapis.pfexporter.api.IExportType;

public interface IExportTypeFactory<T, C> {

	IExportType<T, C> createNewExporter(C configOptions);
	C getDefaultConfigOptions();
	String getContentType();
	String getFileExtension();
	
}
