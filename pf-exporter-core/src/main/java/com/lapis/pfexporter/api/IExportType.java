package com.lapis.pfexporter.api;

import javax.faces.context.ExternalContext;

public interface IExportType<T, C, R> {
	
	T getContext();
	R exportRow(IExportRow row);
	void writeExport(ExternalContext externalContext) throws Exception;
	
}
