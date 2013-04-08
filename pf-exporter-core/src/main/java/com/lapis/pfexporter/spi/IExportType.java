package com.lapis.pfexporter.spi;

import javax.faces.context.ExternalContext;

import com.lapis.pfexporter.api.IDataProvider;

public interface IExportType<T, C> {

	C getDefaultConfigOptions();
	T createNewContext(C configOptions) throws Exception;
	String getContentType();
	String getFileExtension();
	void generateExport(T context, C configOptions, IDataProvider data) throws Exception;
	void writeExport(T context, C configOptions, ExternalContext externalContext) throws Exception;
	
}
