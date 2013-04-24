package com.lapis.pfexporter.api;

import javax.faces.context.ExternalContext;

public interface IExportType<T, C, R> {
	
	T getContext();
	void beginExport(int columnCount);
	R exportRow(IExportRow row);
	void endExport();
	void writeExport(ExternalContext externalContext) throws Exception;
	
}
